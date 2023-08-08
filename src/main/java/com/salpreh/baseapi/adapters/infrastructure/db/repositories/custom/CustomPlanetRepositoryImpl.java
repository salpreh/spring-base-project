package com.salpreh.baseapi.adapters.infrastructure.db.repositories.custom;

import com.salpreh.baseapi.adapters.infrastructure.db.models.PersonEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.PlanetEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Subgraph;

public class CustomPlanetRepositoryImpl implements CustomPlanetRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<PlanetEntity> findById(long id) {
    EntityGraph<PlanetEntity> graph = getFullPlanetGraph();
    Map<String, Object> properties = getPropertiesWithGraphFetchHint(graph);

    return Optional.ofNullable(entityManager.find(PlanetEntity.class, id, properties));
  }

  private EntityGraph<PlanetEntity> getFullPlanetGraph() {
    EntityGraph<PlanetEntity> graph = entityManager.createEntityGraph(PlanetEntity.class);
    graph.addAttributeNodes("name");

    graph.addSubgraph("affiliation")
      .addAttributeNodes("name");

    Subgraph<PersonEntity> personSubgraph = graph.addSubgraph("relevantPersons");
    personSubgraph.addAttributeNodes("name", "alias", "age", "race");
    personSubgraph.addSubgraph("affiliations")
      .addAttributeNodes("name");

    Subgraph<PlanetEntity> planetSubgraph = personSubgraph.addSubgraph("birthPlanet");
    planetSubgraph.addAttributeNodes("name");
    planetSubgraph.addSubgraph("affiliation")
      .addAttributeNodes("name");

    return graph;
  }

  private Map<String, Object> getPropertiesWithGraphFetchHint(EntityGraph<PlanetEntity> graph) {
    Map<String, Object> properties = new HashMap<>();
    properties.put("jakarta.persistence.fetchgraph", graph);

    return properties;
  }

}

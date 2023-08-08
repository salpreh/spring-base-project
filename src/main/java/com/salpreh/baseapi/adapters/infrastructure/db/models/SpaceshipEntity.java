package com.salpreh.baseapi.adapters.infrastructure.db.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "spaceships")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedEntityGraph(
  name = "full-spaceship",
  attributeNodes = {
    @NamedAttributeNode("name"),
    @NamedAttributeNode(value = "assignedPort", subgraph = "spaceship-planet-subgraph"),
    @NamedAttributeNode(value = "affiliation", subgraph = "faction-subgraph"),
    @NamedAttributeNode(value = "crew", subgraph = "spaceship-assignation-subgraph")
  },
  subgraphs = {
    @NamedSubgraph(
      name = "spaceship-planet-subgraph",
      attributeNodes = {
        @NamedAttributeNode("name"),
        @NamedAttributeNode(value = "affiliation", subgraph = "faction-subgraph")
      }
    ),
    @NamedSubgraph(
      name = "spaceship-assignation-subgraph",
      attributeNodes = {
        @NamedAttributeNode("position"),
        @NamedAttributeNode(value = "assignee", subgraph = "spaceship-person-subgraph")
      }
    ),
    @NamedSubgraph(
      name = "spaceship-person-subgraph",
      attributeNodes = {
        @NamedAttributeNode("name"),
        @NamedAttributeNode("alias"),
        @NamedAttributeNode("age"),
        @NamedAttributeNode("race"),
        @NamedAttributeNode(value = "birthPlanet", subgraph = "spaceship-planet-subgraph"),
        @NamedAttributeNode(value = "affiliations", subgraph = "faction-subgraph")
      }
    ),
    @NamedSubgraph(
      name = "faction-subgraph",
      attributeNodes = {
        @NamedAttributeNode("name"),
        @NamedAttributeNode(value = "affiliatedPlanets", subgraph = "faction-planet-subgraph")
      }
    ),
    @NamedSubgraph(
      name = "faction-planet-subgraph",
      attributeNodes = {
        @NamedAttributeNode("name")
      }
    )
  }
)
public class SpaceshipEntity {
  @Id
  @Column
  @SequenceGenerator(name = "spaceship_pk_gen", sequenceName = "spaceship_pk_gen", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spaceship_pk_gen")
  private Long id;

  @Column(unique = true)
  private String name;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "port_planet_id")
  private PlanetEntity assignedPort;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "affiliation_faction_id")
  private FactionEntity affiliation;

  @OneToMany(mappedBy = "assignation", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private Set<AssignationEntity> crew = new HashSet<>();

  public void setAffiliation(FactionEntity faction) {
    if (this.affiliation != null) this.affiliation.removeSpaceship(this);
    if (faction != null) faction.addSpaceship(this);

    this.affiliation = faction;
  }

  public void setCrew(Set<AssignationEntity> crew) {
    this.crew.clear();
    if (crew != null) crew.forEach(this::addCrew);
  }

  public void addCrew(AssignationEntity assignation) {
    assignation.setAssignation(this);
    crew.add(assignation);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (!(o instanceof SpaceshipEntity))
      return false;

    return
      id != null &&
        id.equals(((SpaceshipEntity) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}

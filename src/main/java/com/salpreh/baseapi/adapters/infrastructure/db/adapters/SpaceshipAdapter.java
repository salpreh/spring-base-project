package com.salpreh.baseapi.adapters.infrastructure.db.adapters;

import com.salpreh.baseapi.adapters.infrastructure.db.helpers.EntityMapping;
import com.salpreh.baseapi.adapters.infrastructure.db.mappers.DbMapper;
import com.salpreh.baseapi.adapters.infrastructure.db.models.SpaceshipEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.SpaceshipRepository;
import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.ports.infrastructure.SpaceshipDatasourcePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpaceshipAdapter implements SpaceshipDatasourcePort {

  private final SpaceshipRepository spaceshipRepository;
  private final DbMapper mapper;

  @Override
  public Optional<Spaceship> findById(Long id) {
    return spaceshipRepository.findById(id)
      .map(mapper::map);
  }

  @Override
  public Page<Spaceship> findAll(Pageable pageable) {
    return spaceshipRepository.findAll(pageable)
      .map(mapper::map);
  }

  @Override
  public Spaceship createSpaceship(Spaceship spaceship) {
    if (spaceship.getId() != null) throw new IllegalArgumentException("Entity already has an Id");
    SpaceshipEntity created = spaceshipRepository.save(mapper.map(spaceship));

    return mapper.map(created);
  }

  @Override
  public Spaceship updateSpaceship(long id, Spaceship spaceship) {
    SpaceshipEntity current = spaceshipRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Entity with ID " +id+ "do not exists in DB"));

    EntityMapping.mapNonNullAndNestedProperties(spaceship, current);
    current = spaceshipRepository.save(current);

    return mapper.map(current);
  }

  @Override
  public void deletePlanet(long id) {
    spaceshipRepository.deleteById(id);
  }
}

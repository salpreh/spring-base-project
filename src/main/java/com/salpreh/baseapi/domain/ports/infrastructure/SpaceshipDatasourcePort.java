package com.salpreh.baseapi.domain.ports.infrastructure;

import com.salpreh.baseapi.domain.models.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SpaceshipDatasourcePort {
  Optional<Spaceship> findById(Long id);

  Page<Spaceship> findAll(Pageable pageable);

  Spaceship createSpaceship(Spaceship spaceship);

  Spaceship updateSpaceship(long id, Spaceship spaceship);

  void deletePlanet(long id);
}

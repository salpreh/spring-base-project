package com.salpreh.baseapi.domain.ports.infrastructure;

import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SpaceshipDatasourcePort {
  Optional<Spaceship> findById(Long id);

  Page<Spaceship> findAll(Pageable pageable);

  Spaceship createSpaceship(SpaceshipCreateCommand spaceship);

  Spaceship updateSpaceship(long id, SpaceshipCreateCommand updateCommand);

  void deletePlanet(long id);
}

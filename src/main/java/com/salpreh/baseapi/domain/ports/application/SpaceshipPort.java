package com.salpreh.baseapi.domain.ports.application;

import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpaceshipPort {
  Optional<Spaceship> findById(long id);
  Page<Spaceship> findAll(Pageable pageable);
  Spaceship createSpaceship(@Valid SpaceshipCreateCommand spaceship);
  Spaceship updateSpaceship(long id, @Valid SpaceshipCreateCommand updateCommand);
  void deleteSpaceship(long id);
}

package com.salpreh.baseapi.domain.services;

import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import com.salpreh.baseapi.domain.ports.application.SpaceshipPort;
import com.salpreh.baseapi.domain.ports.infrastructure.SpaceshipDatasourcePort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceshipUseCase implements SpaceshipPort {

  private final SpaceshipDatasourcePort spaceshipDatasourcePort;


  @Override
  public Optional<Spaceship> findById(long id) {
    return spaceshipDatasourcePort.findById(id);
  }

  @Override
  public Page<Spaceship> findAll(Pageable pageable) {
    return spaceshipDatasourcePort.findAll(pageable);
  }

  @Override
  public Spaceship createSpaceship(SpaceshipCreateCommand spaceship) {
    return spaceshipDatasourcePort.createSpaceship(spaceship);
  }

  @Override
  public Spaceship updateSpaceship(long id, SpaceshipCreateCommand updateCommand) {
    return spaceshipDatasourcePort.updateSpaceship(id, updateCommand);
  }

  @Override
  public void deleteSpaceship(long id) {
    spaceshipDatasourcePort.deleteSpaceship(id);
  }
}

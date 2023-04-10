package com.salpreh.baseapi.domain.services;

import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import com.salpreh.baseapi.domain.models.contexts.DeviceContextHolder;
import com.salpreh.baseapi.domain.models.contexts.ProviderContext;
import com.salpreh.baseapi.domain.ports.application.SpaceshipPort;
import com.salpreh.baseapi.domain.ports.infrastructure.SpaceshipDatasourcePort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class SpaceshipUseCase implements SpaceshipPort {

  private final SpaceshipDatasourcePort spaceshipDatasourcePort;
  private final ProviderContext providerContext;

  @Override
  public Optional<Spaceship> findById(long id) {
    log.info("Current DeviceId: {}", DeviceContextHolder.getDeviceId());
    log.info("Current ProviderId: {}", providerContext.getProviderId());

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

package com.salpreh.baseapi.domain.services;

import com.salpreh.baseapi.domain.models.Planet;
import com.salpreh.baseapi.domain.models.commands.PlanetCreateCommand;
import com.salpreh.baseapi.domain.ports.application.PlanetPort;
import com.salpreh.baseapi.domain.ports.infrastructure.PlanetDatasourcePort;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class PlanetUseCase implements PlanetPort {

  private final PlanetDatasourcePort planetDatasourcePort;


  @Override
  public Optional<Planet> findById(long id) {
    return planetDatasourcePort.findById(id);
  }

  @Override
  public Page<Planet> findAll(Pageable pageable) {
    return planetDatasourcePort.findAll(pageable);
  }

  @Override
  public Planet createPlanet(@Valid PlanetCreateCommand createCommand) {
    return planetDatasourcePort.createPlanet(createCommand);
  }

  @Override
  public Planet updatePlanet(long id, @Valid PlanetCreateCommand updateCommand) {
    return planetDatasourcePort.updatePlanet(id, updateCommand);
  }

  @Override
  public void deletePlanet(long id) {
    planetDatasourcePort.deletePlanet(id);
  }
}

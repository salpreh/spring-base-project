package com.salpreh.baseapi.domain.ports.infrastructure;

import com.salpreh.baseapi.domain.models.Planet;
import com.salpreh.baseapi.domain.models.commands.PlanetCreateCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PlanetDatasourcePort {
  Optional<Planet> findById(Long id);

  Page<Planet> findAll(Pageable pageable);

  Planet createPlanet(PlanetCreateCommand createCommand);

  Planet updatePlanet(long id, PlanetCreateCommand updateCommand);

  void deletePlanet(long id);
}

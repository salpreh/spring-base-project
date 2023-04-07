package com.salpreh.baseapi.domain.ports.application;

import com.salpreh.baseapi.domain.models.Planet;
import com.salpreh.baseapi.domain.models.commands.PlanetCreateCommand;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanetPort {
  Optional<Planet> findById(long id);
  Page<Planet> findAll(Pageable pageable);
  Planet createPlanet(@Valid PlanetCreateCommand createCommand);
  Planet updatePlanet(long id, @Valid PlanetCreateCommand updateCommand);
  void deletePlanet(long id);
}

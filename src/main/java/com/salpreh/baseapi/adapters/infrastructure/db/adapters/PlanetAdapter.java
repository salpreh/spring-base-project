package com.salpreh.baseapi.adapters.infrastructure.db.adapters;

import com.salpreh.baseapi.adapters.infrastructure.db.mappers.DbMapper;
import com.salpreh.baseapi.adapters.infrastructure.db.models.FactionEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.PlanetEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.FactionRepository;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.PlanetRepository;
import com.salpreh.baseapi.domain.models.Planet;
import com.salpreh.baseapi.domain.models.commands.PlanetCreateCommand;
import com.salpreh.baseapi.domain.ports.infrastructure.PlanetDatasourcePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanetAdapter implements PlanetDatasourcePort {

  private final PlanetRepository planetRepository;
  private final FactionRepository factionRepository;

  private final DbMapper mapper;

  @Override
  public Optional<Planet> findById(Long id) {
    return planetRepository.findById(id)
      .map(mapper::map);
  }

  @Override
  public Page<Planet> findAll(Pageable pageable) {
    return planetRepository.findAll(pageable)
      .map(mapper::map);
  }

  @Override
  public Planet createPlanet(PlanetCreateCommand createCommand) {
    PlanetEntity entity = PlanetEntity.builder().build();
    entity = processCommand(createCommand, entity);

    return mapper.map(entity);
  }

  @Override
  public Planet updatePlanet(long id, PlanetCreateCommand updateCommand) {
    PlanetEntity entity = planetRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Unable to find planet with ID" + id));

    processCommand(updateCommand, entity);

    return mapper.map(entity);
  }

  private PlanetEntity processCommand(PlanetCreateCommand command, PlanetEntity entity) {
    entity.setName(command.getName());

    FactionEntity faction = null;
    if (command.getAffiliation() != null) {
      faction = factionRepository.findById(command.getAffiliation())
        .orElseThrow(() -> new RuntimeException("Unable to find faction with ID " + command.getAffiliation()));
    }

    entity.setAffiliation(faction);

    return entity;
  }

  @Override
  public void deletePlanet(long id) {
    planetRepository.deleteById(id);
  }
}

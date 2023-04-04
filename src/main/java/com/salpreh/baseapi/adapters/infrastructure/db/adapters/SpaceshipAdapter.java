package com.salpreh.baseapi.adapters.infrastructure.db.adapters;

import com.salpreh.baseapi.adapters.infrastructure.db.mappers.DbMapper;
import com.salpreh.baseapi.adapters.infrastructure.db.models.AssignationEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.FactionEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.PersonEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.PlanetEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.SpaceshipEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.FactionRepository;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.PersonRepository;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.PlanetRepository;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.SpaceshipRepository;
import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import com.salpreh.baseapi.domain.ports.infrastructure.SpaceshipDatasourcePort;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpaceshipAdapter implements SpaceshipDatasourcePort {

  private final SpaceshipRepository spaceshipRepository;
  private final PlanetRepository planetRepository;
  private final FactionRepository factionRepository;
  private final PersonRepository personRepository;

  private final DbMapper mapper;

  @Override
  public Optional<Spaceship> findById(long id) {
    return spaceshipRepository.findById(id)
      .map(mapper::map);
  }

  @Override
  public Page<Spaceship> findAll(Pageable pageable) {
    return spaceshipRepository.findAll(pageable)
      .map(mapper::map);
  }

  @Override
  @Transactional
  public Spaceship createSpaceship(SpaceshipCreateCommand createCommand) {
    SpaceshipEntity entity = SpaceshipEntity.builder().build();
    entity = processCommand(createCommand, entity);

    return mapper.map(spaceshipRepository.save(entity));
  }

  @Override
  public Spaceship updateSpaceship(long id, SpaceshipCreateCommand updateCommand) {
    SpaceshipEntity entity = spaceshipRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Entity with ID " +id+ "do not exists in DB"));

    entity = processCommand(updateCommand, entity);

    return mapper.map(spaceshipRepository.save(entity));
  }

  @Override
  public void deleteSpaceship(long id) {
    spaceshipRepository.deleteById(id);
  }

  private SpaceshipEntity processCommand(SpaceshipCreateCommand command, SpaceshipEntity entity) {
    entity.setName(command.getName());

    PlanetEntity assignedPort = null;
    if (command.getAssignedPort() != null) {
      assignedPort = planetRepository.findById(command.getAssignedPort())
        .orElseThrow(() -> new RuntimeException("Unable to fin planet with id " + command.getAssignedPort()));
    }
    entity.setAssignedPort(assignedPort);

    FactionEntity faction = null;
    if (command.getAffiliation() != null) {
      faction = factionRepository.findById(command.getAffiliation())
        .orElseThrow(() -> new RuntimeException("Unable to find faction with id " + command.getAffiliation()));
    }
    entity.setAffiliation(faction);

    if (command.getCrew() != null) {
      Set<AssignationEntity> crew = command.getCrew().stream()
        .map(ad -> {
          PersonEntity p = personRepository.findById(ad.getAssignee())
            .orElseThrow(() -> new RuntimeException("Unable to find person with id" + ad.getAssignee()));

          return AssignationEntity.builder()
            .position(ad.getPosition())
            .assignee(p)
            .build();
        })
        .collect(Collectors.toSet());

      entity.setCrew(crew);
    }

    return entity;
  }
}

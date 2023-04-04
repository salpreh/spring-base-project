package com.salpreh.baseapi.adapters.infrastructure.db.adapters;

import com.salpreh.baseapi.adapters.infrastructure.db.mappers.DbMapper;
import com.salpreh.baseapi.adapters.infrastructure.db.models.AssignationEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.FactionEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.PersonEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.PlanetEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.FactionRepository;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.PersonRepository;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.PlanetRepository;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.SpaceshipRepository;
import com.salpreh.baseapi.domain.models.Person;
import com.salpreh.baseapi.domain.models.commands.PersonCreateCommand;
import com.salpreh.baseapi.domain.ports.infrastructure.PersonDatasourcePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonAdapter implements PersonDatasourcePort {

  private final PersonRepository personRepository;
  private final PlanetRepository planetRepository;
  private final FactionRepository factionRepository;
  private final SpaceshipRepository spaceshipRepository;

  private final DbMapper mapper;

  @Override
  public Optional<Person> findById(long id) {
    return personRepository.findById(id)
      .map(mapper::map);
  }

  @Override
  public Page<Person> findAll(Pageable pageable) {
    return personRepository.findAll(pageable)
      .map(mapper::map);
  }

  @Override
  public Person createPerson(PersonCreateCommand createCommand) {
    PersonEntity entity = PersonEntity.builder().build();
    entity = processCommand(createCommand, entity);

    return mapper.map(personRepository.save(entity));
  }

  @Override
  public Person updatePerson(long id, PersonCreateCommand updateCommand) {
    PersonEntity entity = personRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Unable to find person with ID " + id));
    entity = processCommand(updateCommand, entity);

    return mapper.map(personRepository.save(entity));
  }

  @Override
  public void deletePerson(long id) {
    personRepository.deleteById(id);
  }

  private PersonEntity processCommand(PersonCreateCommand command, PersonEntity entity) {
    entity.setName(command.getName());
    entity.setAlias(command.getAlias());
    entity.setAge(command.getAge());
    entity.setRace(command.getRace());

    PlanetEntity birthPlanet = null;
    if (command.getBirthPlanet() != null) {
      birthPlanet = planetRepository.findById(command.getBirthPlanet())
        .orElseThrow(() -> new RuntimeException("Unable to find planet with ID " + command.getBirthPlanet()));
    }
    entity.setBirthPlanet(birthPlanet);

    if (command.getAffiliations() != null) {
      Set<FactionEntity> factions = command.getAffiliations().stream()
        .map(id -> factionRepository.findById(id).orElseThrow(() -> new RuntimeException("Unable to find faction")))
        .collect(Collectors.toSet());

      entity.setAffiliations(factions);
    }

    if (command.getAssignations() != null) {
      Set<AssignationEntity> assignations = command.getAssignations().stream()
        .map(dto -> {
          var spaceship = spaceshipRepository.findById(dto.getAssignation())
            .orElseThrow(() -> new RuntimeException("Unable to find spaceship with ID " + dto.getAssignation()));

          return AssignationEntity.builder()
            .position(dto.getPosition())
            .assignation(spaceship)
            .build();
        })
        .collect(Collectors.toSet());

      entity.setAssignations(assignations);
    }

    return entity;
  }
}

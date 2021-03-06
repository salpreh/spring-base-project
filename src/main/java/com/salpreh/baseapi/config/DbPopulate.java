package com.salpreh.baseapi.config;

import com.github.javafaker.Faker;
import com.salpreh.baseapi.domain.constants.RaceType;
import com.salpreh.baseapi.adapters.infrastructure.db.models.*;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.FactionRepository;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.PersonRepository;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.PlanetRepository;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.SpaceshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.stream.IntStream;

@Slf4j
@Configuration
@Profile("local")
public class DbPopulate {

  @Bean
  public ApplicationRunner run(
    PlatformTransactionManager transactionManager,
    FactionRepository factionRepository,
    PersonRepository personRepository,
    PlanetRepository planetRepository,
    SpaceshipRepository spaceshipRepository
  ) {
    return args -> {
      Faker faker = new Faker();
      RaceType[] races
        = new RaceType[]{RaceType.HUMAN, RaceType.ASARI, RaceType.KROGAN, RaceType.QUARIAN, RaceType.TURIAN, RaceType.SALARIAN};
      if (factionRepository.count() > 0) {
        log.warn("Found data in DB. Skiping DB dml");
        return;
      }

      log.info("Populating DB");
      TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

      // Create some factions
      var allianceFaction = factionRepository.save(
        FactionEntity.builder().name("Systems Alliance").build()
      );
      factionRepository.save(
        FactionEntity.builder().name("Turian Hierarchy").build()
      );

      // Create Planets
      var earth = planetRepository.save(
        PlanetEntity.builder()
          .name("Earth")
          .affiliation(allianceFaction)
          .build()
      );
      planetRepository.save(
        PlanetEntity.builder()
          .name("Mars")
          .affiliation(allianceFaction)
          .build()
      );
      var edenPrime = planetRepository.save(
        PlanetEntity.builder()
          .name("Eden Prime")
          .affiliation(allianceFaction)
          .build()
      );
      var planet = PlanetEntity.builder()
        .name("Thessia")
        .build();
      planet.setAffiliation(FactionEntity.builder()
        .name("Asari Republics")
        .build()
      );
      planetRepository.save(planet);

      // Create Spaceships
      var normandyShip = spaceshipRepository.save(
        SpaceshipEntity.builder()
          .name("Normandy SR-1")
          .assignedPort(earth)
          .affiliation(allianceFaction)
          .build()
      );

      var pictorShip = spaceshipRepository.save(
        SpaceshipEntity.builder()
          .name("Pictor")
          .assignedPort(edenPrime)
          .build()
      );

      // Create persons with assignments
      var person = PersonEntity.builder()
        .name("John Shepard")
        .alias("Shepard")
        .race(RaceType.HUMAN)
        .birthPlanet(earth)
        .build();
      person.addAffiliation(allianceFaction);
      person.addAssignation(AssignationEntity.builder()
        .position("Commander")
        .assignation(normandyShip)
        .build()
      );
      personRepository.save(person);

      IntStream.range(0, 10).forEach(i -> {
        var p = PersonEntity.builder()
          .name(faker.name().fullName())
          .alias(faker.name().username())
          .race(races[faker.random().nextInt(0, 5)])
          .age(faker.number().numberBetween(16, 90))
          .birthPlanet(edenPrime)
          .build();

        p.addAssignation(AssignationEntity.builder()
          .position(faker.company().profession())
          .assignation(pictorShip)
          .build()
        );

        personRepository.save(p);
      });

      transactionManager.commit(status);
      log.info("DB populated");
    };
  }
}

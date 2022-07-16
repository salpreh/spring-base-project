package com.salpreh.dbexp.config;

import com.github.javafaker.Faker;
import com.salpreh.dbexp.domain.constants.RaceType;
import com.salpreh.dbexp.models.*;
import com.salpreh.dbexp.repositories.FactionRepository;
import com.salpreh.dbexp.repositories.PersonRepository;
import com.salpreh.dbexp.repositories.PlanetRepository;
import com.salpreh.dbexp.repositories.SpaceshipRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.stream.IntStream;

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
            RaceType[] races = new RaceType[] {RaceType.HUMAN, RaceType.ASARI, RaceType.KROGAN, RaceType.QUARIAN, RaceType.TURIAN, RaceType.SALARIAN};
            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

            // Create some factions
            var allianceFaction = factionRepository.save(
                Faction.builder().name("Systems Alliance").build()
            );
            factionRepository.save(
                Faction.builder().name("Turian Hierarchy").build()
            );

            // Create Planets
            var earth = planetRepository.save(
                Planet.builder()
                    .name("Earth")
                    .affiliation(allianceFaction)
                    .build()
            );
            planetRepository.save(
                Planet.builder()
                    .name("Mars")
                    .affiliation(allianceFaction)
                    .build()
            );
            var edenPrime = planetRepository.save(
                Planet.builder()
                    .name("Eden Prime")
                    .affiliation(allianceFaction)
                    .build()
            );
            var planet = Planet.builder()
                .name("Thessia")
                .build();
            planet.setAffiliation(Faction.builder()
                    .name("Asari Republics")
                    .build()
            );
            planetRepository.save(planet);

            // Create Spaceships
            var normandyShip = spaceshipRepository.save(
                Spaceship.builder()
                    .name("Normandy SR-1")
                    .assignedPort(earth)
                    .affiliation(allianceFaction)
                    .build()
            );

            var pictorShip = spaceshipRepository.save(
                Spaceship.builder()
                    .name("Pictor")
                    .assignedPort(edenPrime)
                    .build()
            );

            // Create persons with assignments
            var person = Person.builder()
                .name("John Shepard")
                .alias("Shepard")
                .race(RaceType.HUMAN)
                .birthPlanet(earth)
                .build();
            person.addAffiliation(allianceFaction);
            person.addAssignation(Assignation.builder()
                .position("Commander")
                .assignation(normandyShip)
                .build()
            );
            personRepository.save(person);

            IntStream.range(0, 10).forEach(i -> {
                var p = Person.builder()
                    .name(faker.name().fullName())
                    .alias(faker.name().username())
                    .race(races[faker.random().nextInt(0, 5)])
                    .age(faker.number().numberBetween(16, 90))
                    .birthPlanet(edenPrime)
                    .build();

                p.addAssignation(Assignation.builder()
                    .position(faker.company().profession())
                    .assignation(pictorShip)
                    .build()
                );

                personRepository.save(p);
            });

            transactionManager.commit(status);
        };
    }
}

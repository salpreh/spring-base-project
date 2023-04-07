package com.salpreh.baseapi.helpers.test;

import com.salpreh.baseapi.domain.models.Assignation;
import com.salpreh.baseapi.domain.models.Faction;
import com.salpreh.baseapi.domain.models.Person;
import com.salpreh.baseapi.domain.models.Planet;
import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.SpaceshipRegistration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@UtilityClass
public class SpaceApiFaker {

  public static Spaceship createSpaceship(Long id, String name, SpaceshipRegistration registration, Planet planet, Faction faction) {
    return Spaceship.builder()
      .id(id)
      .name(name)
      .registration(registration)
      .assignedPort(planet)
      .affiliation(faction)
      .build();
  }

  public static SpaceshipRegistration createSpaceshipRegistration(Long id, String registrationNumber, String signature, OffsetDateTime registrationDate) {
    return SpaceshipRegistration.builder()
      .id(id)
      .registrationNumber(registrationNumber)
      .signature(signature)
      .registrationDate(registrationDate != null ? registrationDate : OffsetDateTime.now())
      .build();
  }

  public static Planet createPlanet(Long id, String name, Faction faction) {
    return Planet.builder()
      .id(id)
      .name(name)
      .affiliation(faction)
      .build();
  }

  public static Faction createFaction(Long id, String name) {
    return Faction.builder()
      .id(id)
      .name(name)
      .build();
  }

  public static Person createPerson(Long id, String name, String alias, int age, Planet birthPlanet, Faction faction) {
    return Person.builder()
      .id(id)
      .name(name)
      .alias(alias)
      .age(age)
      .birthPlanet(birthPlanet)
      .affiliations(Set.of(faction))
      .build();
  }

  public static Assignation createAssignation(Long id, String position, Person person, Spaceship spaceship) {
    return Assignation.builder()
      .id(id)
      .position(position)
      .assignee(person)
      .assignation(spaceship)
      .build();
  }

  public static <T> Page<T> createPage(List<T> data, int page, int size, int total) {
    return new PageImpl<>(data, PageRequest.of(page, size), total);
  }
}

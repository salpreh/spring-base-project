package com.salpreh.baseapi.adapters.infrastructure.db.helpers;

import com.salpreh.baseapi.adapters.infrastructure.db.models.PersonEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.PlanetEntity;
import com.salpreh.baseapi.domain.constants.RaceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityMappingTest {

  @Test
  void shouldMapNonNullProperties() {
    // given
    PersonEntity target = PersonEntity.builder()
      .id(1L)
      .name("Shepard")
      .alias("Commander")
      .age(32)
      .race(RaceType.HUMAN)
      .build();

    PersonEntity source = PersonEntity.builder()
      .name("Sal")
      .age(28)
      .build();

    // when
    EntityMapping.mapNonNullProperties(source, target);

    // then
    assertEquals(1L, target.getId());
    assertEquals("Sal", target.getName());
    assertEquals("Commander", target.getAlias());
    assertEquals(28, target.getAge());
    assertEquals(RaceType.HUMAN, target.getRace());
  }

  @Test
  void shouldCopyNestedEntitiesWithMapNonNull() {
    // given
    PersonEntity target = PersonEntity.builder()
      .id(1L)
      .name("Shepard")
      .alias("Commander")
      .age(32)
      .race(RaceType.HUMAN)
      .birthPlanet(PlanetEntity.builder()
        .id(1L)
        .name("Mars")
        .build())
      .build();

    PersonEntity source = PersonEntity.builder()
      .name("Sal")
      .age(28)
      .birthPlanet(PlanetEntity.builder()
        .name("Earth")
        .build()
      )
      .build();

    // when
    EntityMapping.mapNonNullProperties(source, target);

    // then
    PlanetEntity nestedTarget = target.getBirthPlanet();
    assertEquals("Earth", nestedTarget.getName());
    assertEquals(null, nestedTarget.getId());
  }

  @Test
  void shouldMapNestedEntitiesWithMapNonNullAndNested() {
    // given
    PersonEntity target = PersonEntity.builder()
      .id(1L)
      .name("Shepard")
      .alias("Commander")
      .age(32)
      .race(RaceType.HUMAN)
      .birthPlanet(PlanetEntity.builder()
        .id(1L)
        .name("Mars")
        .build())
      .build();

    PersonEntity source = PersonEntity.builder()
      .name("Sal")
      .age(28)
      .birthPlanet(PlanetEntity.builder()
        .name("Earth")
        .build()
      )
      .build();

    // when
    EntityMapping.mapNonNullAndNestedProperties(source, target);

    // then
    assertEquals(1L, target.getId());
    assertEquals("Sal", target.getName());
    assertEquals("Commander", target.getAlias());
    assertEquals(28, target.getAge());
    assertEquals(RaceType.HUMAN, target.getRace());

    PlanetEntity nestedTarget = target.getBirthPlanet();
    assertEquals(1L, nestedTarget.getId());
    assertEquals("Earth", nestedTarget.getName());
  }
}
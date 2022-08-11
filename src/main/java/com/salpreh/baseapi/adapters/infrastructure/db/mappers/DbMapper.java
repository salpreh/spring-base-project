package com.salpreh.baseapi.adapters.infrastructure.db.mappers;

import com.salpreh.baseapi.adapters.infrastructure.db.models.*;
import com.salpreh.baseapi.domain.models.*;
import com.salpreh.baseapi.domain.models.revisions.RevisionData;
import com.salpreh.baseapi.domain.models.revisions.RevisionModel;
import org.hibernate.envers.DefaultRevisionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.util.Pair;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface DbMapper {
  @Mapping(target = "affiliation", qualifiedByName = "withoutRelations")
  @Mapping(target = "assignedPort", qualifiedByName = "withoutRelations")
  @Mapping(target = "crew", qualifiedByName = "withoutAssigned")
  Spaceship map(SpaceshipEntity src);
  SpaceshipEntity map(Spaceship src);

  @Mapping(target = "affiliation", qualifiedByName = "withoutRelations")
  @Mapping(target = "relevantPersons", qualifiedByName = "withoutRelations")
  Planet map(PlanetEntity src);
  PlanetEntity map(Planet src);

  @Mapping(target = "affiliatedPersons", qualifiedByName = "withoutRelations")
  @Mapping(target = "affiliatedPlanets", qualifiedByName = "withoutRelations")
  @Mapping(target = "affiliatedSpaceships", qualifiedByName = "withoutRelations")
  Faction map(FactionEntity src);
  FactionEntity map(Faction src);

  @Mapping(target = "birthPlanet", qualifiedByName = "withoutRelations")
  @Mapping(target = "affiliations", qualifiedByName = "withoutRelations")
  @Mapping(target = "assignations", qualifiedByName = "withoutAssignee")
  Person map(PersonEntity src);
  PersonEntity map(Person src);

  Assignation map(AssignationEntity src);
  AssignationEntity map(Assignation src);

  @Named("withoutRelations")
  @Mapping(target = "affiliatedPersons", ignore = true)
  @Mapping(target = "affiliatedPlanets", ignore = true)
  @Mapping(target = "affiliatedSpaceships", ignore = true)
  Faction mapWithoutRelations(FactionEntity src);

  @Named("withoutRelations")
  @Mapping(target = "relevantPersons", ignore = true)
  @Mapping(target = "affiliation", qualifiedByName = "withoutRelations")
  Planet mapWithoutRelations(PlanetEntity src);

  @Named("withoutRelations")
  @Mapping(target = "birthPlanet", qualifiedByName = "withoutRelations")
  @Mapping(target = "affiliations", qualifiedByName = "withoutRelations")
  @Mapping(target = "assignations", ignore = true)
  Person mapWithoutRelations(PersonEntity src);

  @Named("withoutRelations")
  @Mapping(target = "assignedPort", qualifiedByName = "withoutRelations")
  @Mapping(target = "affiliation", qualifiedByName = "withoutRelations")
  @Mapping(target = "crew", ignore = true)
  Spaceship mapWithoutRelations(SpaceshipEntity src);

  @Named("withoutAssignee")
  @Mapping(target = "assignee", ignore = true)
  @Mapping(target = "assignation.crew", ignore = true)
  @Mapping(target = "assignation.assignedPort", qualifiedByName = "withoutRelations")
  @Mapping(target = "assignation.affiliation", qualifiedByName = "withoutRelations")
  Assignation mapWithoutAssignee(AssignationEntity src);

  @Named("withoutAssigned")
  @Mapping(target = "assignation", ignore = true)
  @Mapping(target = "assignee.assignations", ignore = true)
  @Mapping(target = "assignee.birthPlanet", qualifiedByName = "withoutRelations")
  @Mapping(target = "assignee.affiliations", qualifiedByName = "withoutRelations")
  Assignation mapWithoutAssigned(AssignationEntity src);

  default <T, S> RevisionModel<T> map(Pair<S, DefaultRevisionEntity> src, Function<S, T> mapping) {
    return RevisionModel.build(
      mapping.apply(src.getFirst()),
      Long.valueOf(src.getSecond().getId()),
      LocalDateTime.ofInstant(Instant.ofEpochMilli(src.getSecond().getTimestamp()), ZoneId.systemDefault())
    );
  }

  default RevisionData map(DefaultRevisionEntity src) {
    return RevisionData.of(
      Long.valueOf(src.getId()),
      LocalDateTime.ofInstant(Instant.ofEpochMilli(src.getTimestamp()), ZoneId.systemDefault())
    );
  }
}

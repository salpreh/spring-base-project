package com.salpreh.baseapi.adapters.infrastructure.db.mappers;

import com.salpreh.baseapi.adapters.infrastructure.db.models.AssignationEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.FactionEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.PlanetEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.models.SpaceshipEntity;
import com.salpreh.baseapi.domain.models.Assignation;
import com.salpreh.baseapi.domain.models.Faction;
import com.salpreh.baseapi.domain.models.Planet;
import com.salpreh.baseapi.domain.models.Spaceship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DbMapper {
  @Mapping(target = "affiliation", qualifiedByName = "withoutRelations")
  @Mapping(target = "assignedPort", qualifiedByName = "withoutRelations")
  @Mapping(target = "crew", qualifiedByName = "withoutAssigned")
  Spaceship map(SpaceshipEntity src);
  SpaceshipEntity map(Spaceship src);

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
}

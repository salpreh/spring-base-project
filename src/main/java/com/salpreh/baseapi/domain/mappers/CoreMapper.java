package com.salpreh.baseapi.domain.mappers;

import com.salpreh.baseapi.domain.models.SpaceshipRegistration;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CoreMapper {

  default SpaceshipRegistration map(SpaceshipCreateCommand src) {
    if (src == null || src.getRegistration() == null) {
      return null;
    }

    return mapFields(src);
  }

  @Mapping(target = "registrationDate", source = "registration.registrationDate", defaultExpression = "java(java.time.OffsetDateTime.now())")
  @Mapping(target = "registrationNumber", source = "registration.registrationNumber")
  @Mapping(target = "signature", source = "registration.signature")
  SpaceshipRegistration mapFields(SpaceshipCreateCommand src);
}

package com.salpreh.baseapi.domain.models.commands;

import com.salpreh.baseapi.domain.constants.RaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreateCommand {
  private String name;
  private String alias;
  private int age;
  private RaceType race;
  private Long birthPlanet;
  private Set<Long> affiliations;
  private Set<AssignationDto> assignations;

  @Data
  @AllArgsConstructor(staticName = "build")
  @NoArgsConstructor
  public static class AssignationDto {
    private String position;
    private Long assignation;
  }
}

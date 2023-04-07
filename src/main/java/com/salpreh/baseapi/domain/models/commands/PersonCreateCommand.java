package com.salpreh.baseapi.domain.models.commands;

import com.salpreh.baseapi.domain.constants.RaceType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
  @NotEmpty
  private String name;
  private String alias;
  @Min(1)
  private int age;
  @NotNull
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

package com.salpreh.baseapi.domain.models.commands;

import com.salpreh.baseapi.domain.config.validations.constraints.SpaceshipName;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceshipCreateCommand {
  @NotEmpty
  @SpaceshipName
  private String name;
  private Long assignedPort;
  private Long affiliation;

  // Not validated here to provide an example of manual validation
  private SpaceshipRegistrationDto registration;
  private Set<AssignationDto> crew;

  @Data
  @AllArgsConstructor(staticName = "build")
  @NoArgsConstructor
  public static class AssignationDto {
    private String position;
    private Long assignee;
  }

  @Data
  @AllArgsConstructor(staticName = "build")
  @NoArgsConstructor
  public static class SpaceshipRegistrationDto {
    private OffsetDateTime registrationDate;
    private String registrationNumber;
    private String signature;
  }
}

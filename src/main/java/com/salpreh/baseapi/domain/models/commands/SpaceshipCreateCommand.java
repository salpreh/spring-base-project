package com.salpreh.baseapi.domain.models.commands;

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
  @Pattern(regexp = "^[a-zA-Z0-9\\-\\s]+$")
  private String name;
  private Long assignedPort;
  private Long affiliation;
  private Set<AssignationDto> crew;

  @Data
  @AllArgsConstructor(staticName = "build")
  @NoArgsConstructor
  public static class AssignationDto {
    private String position;
    private Long assignee;
  }
}

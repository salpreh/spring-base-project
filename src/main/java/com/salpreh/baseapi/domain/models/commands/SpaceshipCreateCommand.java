package com.salpreh.baseapi.domain.models.commands;

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

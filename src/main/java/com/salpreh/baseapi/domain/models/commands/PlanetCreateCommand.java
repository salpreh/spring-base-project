package com.salpreh.baseapi.domain.models.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class PlanetCreateCommand {
  private String name;
  private Long affiliation;
}

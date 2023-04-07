package com.salpreh.baseapi.domain.models.commands;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class PlanetCreateCommand {
  @NotEmpty
  private String name;
  private Long affiliation;
}

package com.salpreh.baseapi.domain.models.commands;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FactionCreateCommand {
  @NotEmpty
  private String name;
}

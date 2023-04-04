package com.salpreh.baseapi.domain.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spaceship {
  private Long id;

  @NotEmpty
  @Pattern(regexp = "^[a-zA-Z0-9\\-\\s]+$")
  private String name;

  private Planet assignedPort;
  private Faction affiliation;
  private Set<Assignation> crew = new HashSet<>();

  public void setAffiliation(Faction faction) {
    if (this.affiliation != null) this.affiliation.removeSpaceship(this);
    if (faction != null) faction.addSpaceship(this);

    this.affiliation = faction;
  }
}

package com.salpreh.baseapi.domain.models;

import com.salpreh.baseapi.domain.config.validations.constraints.SpaceshipName;
import javax.validation.Valid;
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
  @SpaceshipName
  private String name;

  @Valid
  private SpaceshipRegistration registration;
  private Planet assignedPort;
  private Faction affiliation;
  @Builder.Default
  private Set<Assignation> crew = new HashSet<>();

  public void setAffiliation(Faction faction) {
    if (this.affiliation != null) this.affiliation.removeSpaceship(this);
    if (faction != null) faction.addSpaceship(this);

    this.affiliation = faction;
  }
}

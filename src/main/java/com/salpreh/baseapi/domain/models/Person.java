package com.salpreh.baseapi.domain.models;

import com.salpreh.baseapi.domain.constants.RaceType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  private Long id;

  @NotEmpty
  private String name;
  private String alias;

  @Min(1)
  private int age;

  @NotNull
  private RaceType race;
  private Planet birthPlanet;
  private Set<Faction> affiliations = new HashSet<>();
  private Set<Assignation> assignations = new HashSet<>();

  public void setBirthPlanet(Planet birthPlanet) {
    if (this.birthPlanet != null) birthPlanet.removeRelevantPerson(this);
    if (birthPlanet != null) birthPlanet.addRelevantPerson(this);

    this.birthPlanet = birthPlanet;
  }

  public void addAssignation(Assignation assignation) {
    assignation.setAssignee(this);
    assignations.add(assignation);
  }

  public void removeAssignation(Assignation assignation) {
    assignation.setAssignee(this);
    assignations.remove(assignation);
  }

  public void addAffiliation(Faction faction) {
    faction.addPerson(this);
    affiliations.add(faction);
  }

  public void removeAffiliation(Faction faction) {
    faction.removePerson(this);
    affiliations.remove(faction);
  }
}

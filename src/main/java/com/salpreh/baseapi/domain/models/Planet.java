package com.salpreh.baseapi.domain.models;

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
public class Planet {

  private Long id;
  private String name;
  private Faction affiliation;
  private Set<Person> relevantPersons = new HashSet<>();

  public void addRelevantPerson(Person person) {
    relevantPersons.add(person);
  }

  public void removeRelevantPerson(Person person) {
    relevantPersons.remove(person);
  }

  public void setAffiliation(Faction affiliation) {
    if (this.affiliation != null) this.affiliation.removePlanet(this);
    if (affiliation != null) affiliation.addPlanet(this);

    this.affiliation = affiliation;
  }
}

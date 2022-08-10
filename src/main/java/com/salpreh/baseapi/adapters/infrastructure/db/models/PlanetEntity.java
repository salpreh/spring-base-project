package com.salpreh.baseapi.adapters.infrastructure.db.models;

import com.salpreh.baseapi.adapters.infrastructure.db.models.base.AuditedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "planets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanetEntity extends AuditedEntity {

  @Id
  @Column
  @SequenceGenerator(name = "planet_pk_gen", sequenceName = "planet_pk_gen", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planet_pk_gen")
  private Long id;

  @Column(unique = true)
  private String name;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "affiliation_faction_id")
  private FactionEntity affiliation;

  @OneToMany(mappedBy = "birthPlanet")
  @Builder.Default
  private Set<PersonEntity> relevantPersons = new HashSet<>();

  public void addRelevantPerson(PersonEntity person) {
    relevantPersons.add(person);
  }

  public void removeRelevantPerson(PersonEntity person) {
    relevantPersons.remove(person);
  }

  public void setAffiliation(FactionEntity affiliation) {
    if (this.affiliation != null) this.affiliation.removePlanet(this);
    if (affiliation != null) affiliation.addPlanet(this);

    this.affiliation = affiliation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (!(o instanceof PlanetEntity))
      return false;

    return
      id != null &&
        id.equals(((PlanetEntity) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}

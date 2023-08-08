package com.salpreh.baseapi.adapters.infrastructure.db.models;

import com.salpreh.baseapi.domain.constants.RaceType;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "persons")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedEntityGraph(
  name = "full-person",
  attributeNodes = {
    @NamedAttributeNode("name"),
    @NamedAttributeNode("alias"),
    @NamedAttributeNode("age"),
    @NamedAttributeNode("race"),
    @NamedAttributeNode(value = "birthPlanet", subgraph = "person-planet-subgraph"),
    @NamedAttributeNode(value = "affiliations", subgraph = "faction-subgraph"),
    @NamedAttributeNode(value = "assignations", subgraph = "person-assignation-subgraph")
  },
  subgraphs = {
    @NamedSubgraph(
      name = "person-planet-subgraph",
      attributeNodes = {
        @NamedAttributeNode("name"),
        @NamedAttributeNode(value = "affiliation", subgraph = "faction-subgraph")
      }
    ),
    @NamedSubgraph(
      name = "person-assignation-subgraph",
      attributeNodes = {
        @NamedAttributeNode("position"),
        @NamedAttributeNode(value = "assignation", subgraph = "person-spaceship-subgraph")
      }
    ),
    @NamedSubgraph(
      name = "person-spaceship-subgraph",
      attributeNodes = {
        @NamedAttributeNode(value = "name"),
        @NamedAttributeNode(value = "assignedPort", subgraph = "person-planet-subgraph"),
        @NamedAttributeNode(value = "affiliation", subgraph = "faction-subgraph")
      }
    ),
    @NamedSubgraph(
      name = "faction-subgraph",
      attributeNodes = {
        @NamedAttributeNode("name")
      }
    )
  }
)
public class PersonEntity {

  @Id
  @Column
  @SequenceGenerator(name = "person_pk_gen", sequenceName = "person_pk_gen", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_pk_gen")
  private Long id;

  @Column
  private String name;

  @Column
  private String alias;

  @Column
  private int age;

  @Column
  @Enumerated(EnumType.STRING)
  private RaceType race;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "birth_planet_id")
  private PlanetEntity birthPlanet;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
    name = "person_affiliations",
    joinColumns = @JoinColumn(name = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "faction_id")
  )
  @Builder.Default
  private Set<FactionEntity> affiliations = new HashSet<>();

  @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private Set<AssignationEntity> assignations = new HashSet<>();

  public void setBirthPlanet(PlanetEntity birthPlanet) {
    if (this.birthPlanet != null) birthPlanet.removeRelevantPerson(this);
    if (birthPlanet != null) birthPlanet.addRelevantPerson(this);

    this.birthPlanet = birthPlanet;
  }

  public void setAssignations(Set<AssignationEntity> assignations) {
    this.assignations.forEach(this::removeAssignation);
    if (assignations != null) assignations.forEach(this::addAssignation);
  }

  public void addAssignation(AssignationEntity assignation) {
    assignation.setAssignee(this);
    assignations.add(assignation);
  }

  public void removeAssignation(AssignationEntity assignation) {
    assignation.setAssignee(null);
    assignations.remove(assignation);
  }

  public void setAffiliations(Set<FactionEntity> affiliations) {
    this.affiliations.forEach(this::removeAffiliation);
    if (affiliations != null) affiliations.forEach(this::addAffiliation);
  }

  public void addAffiliation(FactionEntity faction) {
    faction.addPerson(this);
    affiliations.add(faction);
  }

  public void removeAffiliation(FactionEntity faction) {
    faction.removePerson(this);
    affiliations.remove(faction);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (!(o instanceof PersonEntity))
      return false;

    return
      id != null &&
        id.equals(((PersonEntity) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}

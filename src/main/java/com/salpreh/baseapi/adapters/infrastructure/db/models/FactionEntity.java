package com.salpreh.baseapi.adapters.infrastructure.db.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "factions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class FactionEntity {

    @Id
    @Column
    @SequenceGenerator(name = "faction_pk_gen", sequenceName = "faction_pk_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faction_pk_gen")
    private Long id;

    @Column(unique = false)
    private String name;

    @OneToMany(mappedBy = "affiliation")
    @Builder.Default
    private Set<PlanetEntity> affiliatedPlanets = new HashSet<>();

    @ManyToMany(mappedBy = "affiliations")
    @Builder.Default
    private Set<PersonEntity> affiliatedPersons = new HashSet<>();

    @OneToMany(mappedBy = "affiliation")
    @Builder.Default
    private Set<SpaceshipEntity> affiliatedSpaceships = new HashSet<>();

    public void removePlanet(PlanetEntity planet) {
        affiliatedPlanets.remove(planet);
    }

    public void addPlanet(PlanetEntity planet) {
        affiliatedPlanets.add(planet);
    }

    public void addPerson(PersonEntity person) {
        affiliatedPersons.add(person);
    }

    public void removePerson(PersonEntity person) {
        affiliatedPersons.remove(person);
    }

    public void addSpaceship(SpaceshipEntity spaceship) {
        affiliatedSpaceships.add(spaceship);
    }

    public void removeSpaceship(SpaceshipEntity spaceship) {
        affiliatedSpaceships.remove(spaceship);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FactionEntity)) return false;

        FactionEntity other = (FactionEntity)o;
        return id != null && id.equals(other.getId());
    }

    public int hashCode() {
        return getClass().hashCode();
    }
}

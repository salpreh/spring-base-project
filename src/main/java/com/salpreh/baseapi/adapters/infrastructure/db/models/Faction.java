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
public class Faction {

    @Id
    @Column
    @SequenceGenerator(name = "faction_pk_gen", sequenceName = "faction_pk_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faction_pk_gen")
    private Long id;

    @Column(unique = false)
    private String name;

    @OneToMany(mappedBy = "affiliation")
    @Builder.Default
    private Set<Planet> affiliatedPlanets = new HashSet<>();

    @ManyToMany(mappedBy = "affiliations")
    @Builder.Default
    private Set<Person> affiliatedPersons = new HashSet<>();

    @OneToMany(mappedBy = "affiliation")
    @Builder.Default
    private Set<Spaceship> affiliatedSpaceships = new HashSet<>();

    public void removePlanet(Planet planet) {
        affiliatedPlanets.remove(planet);
    }

    public void addPlanet(Planet planet) {
        affiliatedPlanets.add(planet);
    }

    public void addPerson(Person person) {
        affiliatedPersons.add(person);
    }

    public void removePerson(Person person) {
        affiliatedPersons.remove(person);
    }

    public void addSpaceship(Spaceship spaceship) {
        affiliatedSpaceships.add(spaceship);
    }

    public void removeSpaceship(Spaceship spaceship) {
        affiliatedSpaceships.remove(spaceship);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faction)) return false;

        Faction other = (Faction)o;
        return id != null && id.equals(other.getId());
    }

    public int hashCode() {
        return getClass().hashCode();
    }
}

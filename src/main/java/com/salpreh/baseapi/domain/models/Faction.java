package com.salpreh.baseapi.domain.models;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Faction {

    private Long id;
    private String name;
    private Set<Planet> affiliatedPlanets = new HashSet<>();
    private Set<Person> affiliatedPersons = new HashSet<>();
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
}

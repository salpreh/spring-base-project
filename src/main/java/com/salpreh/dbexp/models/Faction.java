package com.salpreh.dbexp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "factions")
@Data
public class Faction {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "affiliation")
    private Set<Planet> affiliatedPersons;

    @ManyToMany(mappedBy = "affiliations")
    private Set<Person> affiliatedPlanets;

    @OneToMany(mappedBy = "affiliation")
    private Set<Spaceship> affiliatedSpaceships;
}

package com.salpreh.dbexp.models;

import com.salpreh.dbexp.domain.constants.RaceType;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "persons")
@Data
public class Person {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "birth_planet_id")
    private Planet birthPlanet;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "person_affiliations",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "faction_id")
    )
    private Set<Faction> affiliations;

    @OneToMany(mappedBy = "assignee")
    private Set<Assignation> assignations;
}

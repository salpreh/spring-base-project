package com.salpreh.dbexp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "planets")
@Data
public class Planet {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "affiliation_faction_id")
    private Faction affiliation;

    @OneToMany(mappedBy = "birthPlanet")
    private Set<Person> relevantPersons;
}

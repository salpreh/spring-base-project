package com.salpreh.dbexp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "spaceships")
@Data
public class Spaceship {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "port_planet_id")
    private Planet assignedPort;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "affiliation_faction_id")
    private Faction affiliation;

    @OneToMany(mappedBy = "assignation")
    private Set<Assignation> crew;
}

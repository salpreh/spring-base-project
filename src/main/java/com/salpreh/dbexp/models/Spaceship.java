package com.salpreh.dbexp.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "spaceships")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Spaceship {
    @Id
    @Column
    @SequenceGenerator(name = "spaceship_pk_gen", sequenceName = "spaceship_pk_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spaceship_pk_gen")
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "port_planet_id")
    private Planet assignedPort;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "affiliation_faction_id")
    private Faction affiliation;

    @OneToMany(mappedBy = "assignation", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Assignation> crew = new HashSet<>();

    public void setAffiliation(Faction faction) {
        if (this.affiliation != null) this.affiliation.removeSpaceship(this);
        if (faction != null) faction.addSpaceship(this);

        this.affiliation = faction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Spaceship))
            return false;

        return
            id != null &&
           id.equals(((Spaceship) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

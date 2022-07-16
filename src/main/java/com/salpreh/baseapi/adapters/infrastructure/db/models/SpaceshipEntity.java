package com.salpreh.baseapi.adapters.infrastructure.db.models;

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
public class SpaceshipEntity {
    @Id
    @Column
    @SequenceGenerator(name = "spaceship_pk_gen", sequenceName = "spaceship_pk_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spaceship_pk_gen")
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "port_planet_id")
    private PlanetEntity assignedPort;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "affiliation_faction_id")
    private FactionEntity affiliation;

    @OneToMany(mappedBy = "assignation", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<AssignationEntity> crew = new HashSet<>();

    public void setAffiliation(FactionEntity faction) {
        if (this.affiliation != null) this.affiliation.removeSpaceship(this);
        if (faction != null) faction.addSpaceship(this);

        this.affiliation = faction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof SpaceshipEntity))
            return false;

        return
            id != null &&
           id.equals(((SpaceshipEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

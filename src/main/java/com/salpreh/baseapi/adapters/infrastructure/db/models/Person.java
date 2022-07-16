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
@Getter @Setter
public class Person {

    @Id
    @Column
    @SequenceGenerator(name = "person_pk_gen", sequenceName = "person_pk_gen")
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
    private Planet birthPlanet;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "person_affiliations",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "faction_id")
    )
    @Builder.Default
    private Set<Faction> affiliations = new HashSet<>();

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Assignation> assignations = new HashSet<>();

    public void setBirthPlanet(Planet birthPlanet) {
        if (this.birthPlanet != null) birthPlanet.removeRelevantPerson(this);
        if (birthPlanet != null) birthPlanet.addRelevantPerson(this);

        this.birthPlanet = birthPlanet;
    }

    public void addAssignation(Assignation assignation) {
        assignation.setAssignee(this);
        assignations.add(assignation);
    }

    public void removeAssignation(Assignation assignation) {
        assignation.setAssignee(this);
        assignations.remove(assignation);
    }

    public void addAffiliation(Faction faction) {
        faction.addPerson(this);
        affiliations.add(faction);
    }

    public void removeAffiliation(Faction faction) {
        faction.removePerson(this);
        affiliations.remove(faction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Person))
            return false;

        return
            id != null &&
           id.equals(((Person) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

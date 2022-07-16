package com.salpreh.baseapi.adapters.infrastructure.db.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "planets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Planet {

    @Id
    @Column
    @SequenceGenerator(name = "planet_pk_gen", sequenceName = "planet_pk_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planet_pk_gen")
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "affiliation_faction_id")
    private Faction affiliation;

    @OneToMany(mappedBy = "birthPlanet")
    @Builder.Default
    private Set<Person> relevantPersons = new HashSet<>();

    public void addRelevantPerson(Person person) {
        relevantPersons.add(person);
    }

    public void removeRelevantPerson(Person person) {
        relevantPersons.remove(person);
    }

    public void setAffiliation(Faction affiliation) {
        if (this.affiliation != null) this.affiliation.removePlanet(this);
        if (affiliation != null) affiliation.addPlanet(this);

        this.affiliation = affiliation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Planet))
            return false;

        return
            id != null &&
           id.equals(((Planet) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

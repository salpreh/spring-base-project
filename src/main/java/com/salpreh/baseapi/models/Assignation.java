package com.salpreh.baseapi.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "asignations")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Assignation {
    @Id
    @Column
    @SequenceGenerator(name = "assignation_pk_gen", sequenceName = "assignation_pk_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignation_pk_gen")
    private Long id;

    @Column
    private String position;

    @ManyToOne
    @JoinColumn(name = "assignee_person_id")
    private Person assignee;

    @ManyToOne
    @JoinColumn(name = "assignation_spaceship_id")
    private Spaceship assignation;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Assignation))
            return false;

        return
            id != null &&
           id.equals(((Assignation) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

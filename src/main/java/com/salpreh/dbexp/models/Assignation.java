package com.salpreh.dbexp.models;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

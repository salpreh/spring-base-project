package com.salpreh.dbexp.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "asignations")
@Data
public class Assignation {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String position;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignee_person_id")
    private Person assignee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignation_spaceship_id")
    private Spaceship assignation;
}

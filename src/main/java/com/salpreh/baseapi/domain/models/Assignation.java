package com.salpreh.baseapi.domain.models;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assignation {
    private Long id;
    private String position;
    private Person assignee;
    private Spaceship assignation;
}

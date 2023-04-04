package com.salpreh.baseapi.adapters.infrastructure.db.models;


import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "spaceship_registrations")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SpaceshipRegistrationEntity {

  @Id
  @Column
  @SequenceGenerator(name = "spaceship_registration_pk_gen", sequenceName = "spaceship_registration_pk_gen", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spaceship_registration_pk_gen")
  private Long id;

  private OffsetDateTime registrationDate;
  private String registrationNumber;
  private String signature;

  @OneToOne(mappedBy = "registration")
  private SpaceshipEntity spaceship;

  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof SpaceshipRegistrationEntity)) return false;

      return id != null && id.equals(((SpaceshipRegistrationEntity) o).getId());
  }

  @Override
  public int hashCode() {
      return getClass().hashCode();
  }
}

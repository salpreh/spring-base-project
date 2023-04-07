package com.salpreh.baseapi.domain.models;

import java.time.OffsetDateTime;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceshipRegistration {
  private Long id;

  @PastOrPresent
  private OffsetDateTime registrationDate;

  @NotBlank
  @Digits(integer = 16, fraction = 0)
  private String registrationNumber;
  @NotBlank
  private String signature;
}

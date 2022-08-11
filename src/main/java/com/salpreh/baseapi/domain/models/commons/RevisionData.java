package com.salpreh.baseapi.domain.models.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class RevisionData {
  private Long revision;
  private LocalDateTime datetime;
}

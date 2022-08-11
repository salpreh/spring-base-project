package com.salpreh.baseapi.domain.models.revisions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RevisionModel<T> {
  private T data;
  private Long revision;
  private LocalDateTime datetime;
}

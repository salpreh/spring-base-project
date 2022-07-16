package com.salpreh.baseapi.adapters.application.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ApiPage<T> {
  private List<T> data;
  private int page;
  private int numPages;
}

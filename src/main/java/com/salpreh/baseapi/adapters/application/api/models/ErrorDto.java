package com.salpreh.baseapi.adapters.application.api.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ErrorDto {
  private String message;
  private List<String> details;

  public static ErrorDto of(String message) {
    var error = new ErrorDto();
    error.setMessage(message);

    return error;
  }
}

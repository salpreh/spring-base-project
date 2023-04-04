package com.salpreh.baseapi.adapters.application.api.config;

import com.salpreh.baseapi.adapters.application.api.models.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandlerConfig {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorDto> handleException(ResponseStatusException ex) {
    BodyBuilder response = ResponseEntity.status(ex.getStatus());
    if (ex.getReason() != null) {
      return response.body(ErrorDto.of(ex.getReason()));
    }

    return response.build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(Exception ex) {
    return ResponseEntity.internalServerError()
      .body(ErrorDto.of(ex.getMessage()));
  }
}

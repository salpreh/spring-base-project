package com.salpreh.baseapi.adapters.application.api.config;

import com.salpreh.baseapi.adapters.application.api.models.ErrorDto;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorDto> handleException(ConstraintViolationException ex) {
    List<String> constraintViolations = ex.getConstraintViolations().stream()
      .map(cv -> String.format("%s: %s", cv.getPropertyPath(), cv.getMessage()))
      .collect(Collectors.toList());

    return ResponseEntity.badRequest()
      .body(ErrorDto.of("Bad request values", constraintViolations));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleException(MethodArgumentNotValidException ex) {
    List<String> constraintViolations = ex.getBindingResult().getFieldErrors().stream()
      .map(fe -> String.format("%s: %s", fe.getField(), fe.getDefaultMessage()))
      .collect(Collectors.toList());

    return ResponseEntity.badRequest()
      .body(ErrorDto.of("Bad request values", constraintViolations));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(Exception ex) {
    return ResponseEntity.internalServerError()
      .body(ErrorDto.of(ex.getMessage()));
  }
}

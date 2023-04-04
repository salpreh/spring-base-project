package com.salpreh.baseapi.domain.config.validations;

import com.salpreh.baseapi.domain.config.validations.constraints.SpaceshipName;
import com.salpreh.baseapi.domain.ports.infrastructure.SpaceshipDatasourcePort;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SpaceshipNameValidator implements ConstraintValidator<SpaceshipName, String> {

  private final Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\-\\s]+$");
  private final SpaceshipDatasourcePort spaceshipDatasourcePort;

  public SpaceshipNameValidator(SpaceshipDatasourcePort spaceshipDatasourcePort) {
    this.spaceshipDatasourcePort = spaceshipDatasourcePort;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    boolean valid = true;
    if (!StringUtils.hasLength(value)) {
      context.buildConstraintViolationWithTemplate("Spaceship name not present")
        .addConstraintViolation()
        .disableDefaultConstraintViolation();

      valid = false;
    }

    if (!Character.isUpperCase(value.charAt(0)) && !Character.isDigit(value.charAt(1))) {
      context.buildConstraintViolationWithTemplate("Spaceship name must start with a capital letter and a number")
        .addConstraintViolation()
        .disableDefaultConstraintViolation();
      valid = false;
    }

    if (!pattern.matcher(value).matches()) {
      context.buildConstraintViolationWithTemplate("Invalid characters in spaceship name. Allowed: [a-zA-Z0-9\\-\\s]")
        .addConstraintViolation()
        .disableDefaultConstraintViolation();
      valid = false;
    }

    if (spaceshipDatasourcePort.findByName(value).isPresent()) {
      context.buildConstraintViolationWithTemplate("Spaceship name already in use")
        .addConstraintViolation()
        .disableDefaultConstraintViolation();
      valid = false;
    }

    return valid;
  }
}

package com.salpreh.baseapi.domain.config.validations.constraints;

import com.salpreh.baseapi.domain.config.validations.SpaceshipNameValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SpaceshipNameValidator.class)
@Documented
public @interface SpaceshipName {
  String message() default "Invalid spaceship name";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}

package com.salpreh.baseapi.domain.services;

import com.salpreh.baseapi.domain.mappers.CoreMapper;
import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.SpaceshipRegistration;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import com.salpreh.baseapi.domain.ports.application.SpaceshipPort;
import com.salpreh.baseapi.domain.ports.infrastructure.SpaceshipDatasourcePort;
import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Log4j2
@Service
@Validated
@RequiredArgsConstructor
public class SpaceshipUseCase implements SpaceshipPort {

  private final SpaceshipDatasourcePort spaceshipDatasourcePort;
  private final Validator validator;
  private final CoreMapper mapper;


  @Override
  public Optional<Spaceship> findById(long id) {
    return spaceshipDatasourcePort.findById(id);
  }

  @Override
  public Page<Spaceship> findAll(Pageable pageable) {
    return spaceshipDatasourcePort.findAll(pageable);
  }

  @Override
  public Spaceship createSpaceship(@Valid SpaceshipCreateCommand spaceship) {
    SpaceshipRegistration registration = mapper.map(spaceship);
    if (registration != null) {
      Set<ConstraintViolation<SpaceshipRegistration>> validations = validator.validate(registration);
      if (!validations.isEmpty()) {
        log.warn("Missing validations in spaceship registration: {}", validations);
        throw new ConstraintViolationException("Missing required fields", validations);
      }
    }

    return spaceshipDatasourcePort.createSpaceship(spaceship);
  }

  @Override
  public Spaceship updateSpaceship(long id, @Valid SpaceshipCreateCommand updateCommand) {
    return spaceshipDatasourcePort.updateSpaceship(id, updateCommand);
  }

  @Override
  public void deleteSpaceship(long id) {
    spaceshipDatasourcePort.deleteSpaceship(id);
  }
}

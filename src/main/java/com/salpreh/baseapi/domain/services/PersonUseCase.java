package com.salpreh.baseapi.domain.services;

import com.salpreh.baseapi.domain.models.Person;
import com.salpreh.baseapi.domain.models.commands.PersonCreateCommand;
import com.salpreh.baseapi.domain.ports.application.PersonPort;
import com.salpreh.baseapi.domain.ports.infrastructure.PersonDatasourcePort;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class PersonUseCase implements PersonPort {

  private final PersonDatasourcePort personDatasourcePort;

  @Override
  public Optional<Person> findById(long id) {
    return personDatasourcePort.findById(id);
  }

  @Override
  public Page<Person> findAll(Pageable pageable) {
    return personDatasourcePort.findAll(pageable);
  }

  @Override
  public Person createPerson(@Valid PersonCreateCommand createCommand) {
    return personDatasourcePort.createPerson(createCommand);
  }

  @Override
  public Person updatePerson(long id, @Valid PersonCreateCommand updateCommand) {
    return personDatasourcePort.updatePerson(id, updateCommand);
  }

  @Override
  public void deletePerson(long id) {
    personDatasourcePort.deletePerson(id);
  }
}

package com.salpreh.baseapi.domain.ports.infrastructure;

import com.salpreh.baseapi.domain.models.Person;
import com.salpreh.baseapi.domain.models.commands.PersonCreateCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersonDatasourcePort {
  Optional<Person> findById(long id);

  Page<Person> findAll(Pageable pageable);

  Person createPerson(PersonCreateCommand createCommand);

  Person updatePerson(long id, PersonCreateCommand updateCommand);

  void deletePerson(long id);
}

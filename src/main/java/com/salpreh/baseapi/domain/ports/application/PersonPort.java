package com.salpreh.baseapi.domain.ports.application;

import com.salpreh.baseapi.domain.models.Person;
import com.salpreh.baseapi.domain.models.commands.PersonCreateCommand;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonPort {
  Optional<Person> findById(long id);
  Page<Person> findAll(Pageable pageable);
  Person createPerson(@Valid PersonCreateCommand createCommand);
  Person updatePerson(long id, @Valid PersonCreateCommand updateCommand);
  void deletePerson(long id);
}

package com.salpreh.baseapi.adapters.application.api.controllers;

import com.salpreh.baseapi.adapters.application.api.config.PaginationConfig;
import com.salpreh.baseapi.adapters.application.api.mappers.ApiMapper;
import com.salpreh.baseapi.adapters.application.api.models.ApiPage;
import com.salpreh.baseapi.domain.models.Person;
import com.salpreh.baseapi.domain.models.commands.PersonCreateCommand;
import com.salpreh.baseapi.domain.ports.infrastructure.PersonDatasourcePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class PersonController {

  private final PersonDatasourcePort personDatasourcePort;
  private final ApiMapper mapper;

  public ApiPage<Person> getAll(
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE) int page,
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE_SIZE) int pageSize
  ) {
    var data = personDatasourcePort.findAll(PageRequest.of(page, pageSize));

    return mapper.map(data);
  }

  @GetMapping("{id}")
  public Person get(@PathVariable long id) {
    return personDatasourcePort.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public Person create(@RequestBody PersonCreateCommand command) {
    return personDatasourcePort.createPerson(command);
  }

  @PutMapping("{id}")
  public Person update(@PathVariable long id, @RequestBody PersonCreateCommand command) {
    return personDatasourcePort.updatePerson(id, command);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    personDatasourcePort.deletePerson(id);

    return ResponseEntity.noContent()
      .build();
  }
}

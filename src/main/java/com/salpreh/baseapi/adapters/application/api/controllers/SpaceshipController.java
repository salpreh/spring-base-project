package com.salpreh.baseapi.adapters.application.api.controllers;

import com.salpreh.baseapi.adapters.application.api.config.PaginationConfig;
import com.salpreh.baseapi.adapters.application.api.mappers.ApiMapper;
import com.salpreh.baseapi.adapters.application.api.models.ApiPage;
import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import com.salpreh.baseapi.domain.ports.infrastructure.SpaceshipDatasourcePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaceship")
public class SpaceshipController {

  private final SpaceshipDatasourcePort datasourcePort;
  private final ApiMapper mapper;

  @GetMapping
  public ApiPage<Spaceship> getAll(
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE) int page,
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE_SIZE) int pageSize
  ) {
    var data = datasourcePort.findAll(PageRequest.of(page, pageSize));

    return mapper.map(data);
  }

  @GetMapping("{id}")
  public Spaceship get(@PathVariable long id) {
    return datasourcePort.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public Spaceship create(@RequestBody SpaceshipCreateCommand command) {
    return datasourcePort.createSpaceship(command);
  }

  @PutMapping("{id}")
  public Spaceship update(@PathVariable long id, @RequestBody SpaceshipCreateCommand command) {
    return datasourcePort.updateSpaceship(id, command);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    datasourcePort.deleteSpaceship(id);

    return ResponseEntity.noContent()
      .build();
  }
}

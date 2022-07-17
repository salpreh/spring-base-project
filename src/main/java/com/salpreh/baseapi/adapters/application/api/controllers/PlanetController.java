package com.salpreh.baseapi.adapters.application.api.controllers;

import com.salpreh.baseapi.adapters.application.api.config.PaginationConfig;
import com.salpreh.baseapi.adapters.application.api.mappers.ApiMapper;
import com.salpreh.baseapi.adapters.application.api.models.ApiPage;
import com.salpreh.baseapi.domain.models.Planet;
import com.salpreh.baseapi.domain.models.commands.PlanetCreateCommand;
import com.salpreh.baseapi.domain.ports.infrastructure.PlanetDatasourcePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping( "planet")
public class PlanetController {
  private final PlanetDatasourcePort planetDatasourcePort;
  private final ApiMapper mapper;

  @GetMapping
  public ApiPage<Planet> getAll(
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE) int page,
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE_SIZE) int pageSize
  ) {
    var data = planetDatasourcePort.findAll(PageRequest.of(page, pageSize));

    return mapper.map(data);
  }

  @GetMapping("{id}")
  public Planet get(@PathVariable long id) {
    return planetDatasourcePort.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public Planet create(@RequestBody PlanetCreateCommand command) {
    return planetDatasourcePort.createPlanet(command);
  }

  @PutMapping("{id}")
  public Planet update(@PathVariable long id, @RequestBody PlanetCreateCommand command) {
    return planetDatasourcePort.updatePlanet(id, command);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    planetDatasourcePort.deletePlanet(id);

    return ResponseEntity.noContent()
      .build();
  }
}

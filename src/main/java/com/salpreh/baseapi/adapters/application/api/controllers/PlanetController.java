package com.salpreh.baseapi.adapters.application.api.controllers;

import static com.salpreh.baseapi.adapters.application.api.config.PathConfig.BASE_PATH;
import static com.salpreh.baseapi.adapters.application.api.config.PathConfig.PLANET_PATH;

import com.salpreh.baseapi.adapters.application.api.config.PaginationConfig;
import com.salpreh.baseapi.adapters.application.api.config.PathConfig;
import com.salpreh.baseapi.adapters.application.api.mappers.ApiMapper;
import com.salpreh.baseapi.adapters.application.api.models.ApiPage;
import com.salpreh.baseapi.adapters.application.api.services.ApiMetricService;
import com.salpreh.baseapi.domain.models.Planet;
import com.salpreh.baseapi.domain.models.commands.PlanetCreateCommand;
import com.salpreh.baseapi.domain.ports.application.PlanetPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(PLANET_PATH)
public class PlanetController {
  private final PlanetPort planetUseCase;
  private final ApiMetricService metricService;
  private final ApiMapper mapper;

  @GetMapping
  public ApiPage<Planet> getAll(
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE) int page,
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE_SIZE) int pageSize
  ) {
    metricService.registerPageRequest(PLANET_PATH);
    var data = planetUseCase.findAll(PageRequest.of(page, pageSize));

    return mapper.map(data);
  }

  @GetMapping("{id}")
  public Planet get(@PathVariable long id) {
    metricService.registerItemRequest(PLANET_PATH);
    return planetUseCase.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public Planet create(@RequestBody PlanetCreateCommand command) {
    return planetUseCase.createPlanet(command);
  }

  @PutMapping("{id}")
  public Planet update(@PathVariable long id, @RequestBody PlanetCreateCommand command) {
    return planetUseCase.updatePlanet(id, command);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    planetUseCase.deletePlanet(id);

    return ResponseEntity.noContent()
      .build();
  }
}

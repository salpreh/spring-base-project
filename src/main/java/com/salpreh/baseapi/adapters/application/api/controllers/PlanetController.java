package com.salpreh.baseapi.adapters.application.api.controllers;

import com.salpreh.baseapi.adapters.application.api.config.PaginationConfig;
import com.salpreh.baseapi.adapters.application.api.mappers.ApiMapper;
import com.salpreh.baseapi.adapters.application.api.models.ApiPage;
import com.salpreh.baseapi.domain.models.Planet;
import com.salpreh.baseapi.domain.models.commands.PlanetCreateCommand;
import com.salpreh.baseapi.domain.ports.application.PlanetUseCase;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@Valid
@RequestMapping( "planet")
public class PlanetController {
  private final PlanetUseCase planetUseCase;
  private final ApiMapper mapper;

  @GetMapping
  public ApiPage<Planet> getAll(
    @Min(0) @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE) int page,
    @Min(1) @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE_SIZE) int pageSize
  ) {
    var data = planetUseCase.findAll(PageRequest.of(page, pageSize));

    return mapper.map(data);
  }

  @GetMapping("{id}")
  public Planet get(@Min(1) @PathVariable long id) {
    return planetUseCase.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public Planet create(@RequestBody PlanetCreateCommand command) {
    return planetUseCase.createPlanet(command);
  }

  @PutMapping("{id}")
  public Planet update(@Min(1) @PathVariable long id, @RequestBody PlanetCreateCommand command) {
    return planetUseCase.updatePlanet(id, command);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@Min(1) @PathVariable long id) {
    planetUseCase.deletePlanet(id);

    return ResponseEntity.noContent()
      .build();
  }
}

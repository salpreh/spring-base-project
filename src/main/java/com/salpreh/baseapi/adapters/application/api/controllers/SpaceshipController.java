package com.salpreh.baseapi.adapters.application.api.controllers;

import com.salpreh.baseapi.adapters.application.api.config.PaginationConfig;
import com.salpreh.baseapi.adapters.application.api.mappers.ApiMapper;
import com.salpreh.baseapi.adapters.application.api.models.ApiPage;
import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import com.salpreh.baseapi.domain.ports.application.SpaceshipPort;
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
@RequestMapping("/spaceship")
public class SpaceshipController {

  private final SpaceshipPort spaceshipUseCase;
  private final ApiMapper mapper;

  @GetMapping
  public ApiPage<Spaceship> getAll(
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE) int page,
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE_SIZE) int pageSize
  ) {
    var data = spaceshipUseCase.findAll(PageRequest.of(page, pageSize));

    return mapper.map(data);
  }

  @GetMapping("{id}")
  public Spaceship get(@PathVariable long id) {
    return spaceshipUseCase.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public Spaceship create(@RequestBody SpaceshipCreateCommand command) {
    return spaceshipUseCase.createSpaceship(command);
  }

  @PutMapping("{id}")
  public Spaceship update(@PathVariable long id, @RequestBody SpaceshipCreateCommand command) {
    return spaceshipUseCase.updateSpaceship(id, command);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    spaceshipUseCase.deleteSpaceship(id);

    return ResponseEntity.noContent()
      .build();
  }
}

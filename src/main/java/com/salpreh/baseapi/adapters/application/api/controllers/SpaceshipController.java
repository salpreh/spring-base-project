package com.salpreh.baseapi.adapters.application.api.controllers;

import com.salpreh.baseapi.adapters.application.api.mapper.ApiMapper;
import com.salpreh.baseapi.adapters.application.api.model.ApiPage;
import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.ports.infrastructure.SpaceshipDatasourcePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaceship")
public class SpaceshipController {

  private final SpaceshipDatasourcePort datasourcePort;
  private final ApiMapper mapper;

  @GetMapping
  public ApiPage<Spaceship> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
    var data = datasourcePort.findAll(PageRequest.of(page, pageSize));

    return mapper.map(data);
  }

  @GetMapping("{id}")
  public Spaceship get(@PathVariable long id) {
    return datasourcePort.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}

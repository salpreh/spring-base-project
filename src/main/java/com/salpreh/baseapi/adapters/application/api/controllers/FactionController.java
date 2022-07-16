package com.salpreh.baseapi.adapters.application.api.controllers;

import com.salpreh.baseapi.adapters.application.api.mappers.ApiMapper;
import com.salpreh.baseapi.adapters.application.api.models.ApiPage;
import com.salpreh.baseapi.domain.models.Faction;
import com.salpreh.baseapi.domain.models.commands.FactionCreateCommand;
import com.salpreh.baseapi.domain.ports.infrastructure.FactionDatasourcePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("faction")
public class FactionController {

  private final FactionDatasourcePort factionDatasourcePort;
  private final ApiMapper mapper;

  @GetMapping
  public ApiPage<Faction> getAll(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int pageSize
  ) {
    var data = factionDatasourcePort.findAll(PageRequest.of(page, pageSize));

    return mapper.map(data);
  }

  @GetMapping("{id}")
  public Faction get(@PathVariable long id) {
    return factionDatasourcePort.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public Faction create(@RequestBody FactionCreateCommand command) {
    return factionDatasourcePort.createFaction(command);
  }

  @PutMapping("{id}")
  public Faction update(@PathVariable long id, @RequestBody FactionCreateCommand command) {
    return factionDatasourcePort.updateFaction(id, command);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    factionDatasourcePort.deletePlanet(id);

    return ResponseEntity.noContent()
      .build();
  }
}

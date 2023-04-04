package com.salpreh.baseapi.adapters.application.api.controllers;

import com.salpreh.baseapi.adapters.application.api.config.PaginationConfig;
import com.salpreh.baseapi.adapters.application.api.mappers.ApiMapper;
import com.salpreh.baseapi.adapters.application.api.models.ApiPage;
import com.salpreh.baseapi.domain.models.Faction;
import com.salpreh.baseapi.domain.models.commands.FactionCreateCommand;
import com.salpreh.baseapi.domain.ports.application.FactionUseCase;
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
@RequestMapping("faction")
public class FactionController {

  private final FactionUseCase factionUseCase;
  private final ApiMapper mapper;

  @GetMapping
  public ApiPage<Faction> getAll(
    @Min(0) @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE) int page,
    @Min(1) @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE_SIZE) int pageSize
  ) {
    var data = factionUseCase.findAll(PageRequest.of(page, pageSize));

    return mapper.map(data);
  }

  @GetMapping("{id}")
  public Faction get(@Min(1) @PathVariable long id) {
    return factionUseCase.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public Faction create(@RequestBody FactionCreateCommand command) {
    return factionUseCase.createFaction(command);
  }

  @PutMapping("{id}")
  public Faction update(@Min(1) @PathVariable long id, @RequestBody FactionCreateCommand command) {
    return factionUseCase.updateFaction(id, command);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@Min(1) @PathVariable long id) {
    factionUseCase.deleteFaction(id);

    return ResponseEntity.noContent()
      .build();
  }
}

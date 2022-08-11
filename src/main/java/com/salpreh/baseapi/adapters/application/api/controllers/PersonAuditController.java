package com.salpreh.baseapi.adapters.application.api.controllers;

import com.salpreh.baseapi.adapters.application.api.config.PaginationConfig;
import com.salpreh.baseapi.adapters.application.api.mappers.ApiMapper;
import com.salpreh.baseapi.adapters.application.api.models.ApiPage;
import com.salpreh.baseapi.domain.models.Person;
import com.salpreh.baseapi.domain.models.revisions.RevisionData;
import com.salpreh.baseapi.domain.models.revisions.RevisionModel;
import com.salpreh.baseapi.domain.ports.application.PersonRevisionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/audit/person")
public class PersonAuditController {

  private final PersonRevisionUseCase personRevisionUseCase;
  private final ApiMapper mapper;

  @GetMapping("{id}")
  public ApiPage<RevisionModel<Person>> getAllRevisions(
    @PathVariable("id") long personId,
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE) int page,
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE_SIZE) int pageSize
  ) {
    var data = personRevisionUseCase.findRevisions(personId, PageRequest.of(page, pageSize));

    return mapper.map(data);
  }

  @GetMapping("{id}/info")
  public ApiPage<RevisionData> getRevisionsInfo(
    @PathVariable("id") long personId,
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE) int page,
    @RequestParam(defaultValue = PaginationConfig.DEFAULT_PAGE_SIZE) int pageSize
  ) {
    var data = personRevisionUseCase.findRevisionsData(personId, PageRequest.of(page, pageSize));

    return mapper.map(data);
  }

  @GetMapping("{id}/{revisionId}")
  public RevisionModel<Person> getRevision(@PathVariable("id") long personId, @PathVariable long revisionId) {
    return personRevisionUseCase.findRevision(personId, revisionId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

}

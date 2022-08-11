package com.salpreh.baseapi.domain.ports.application;

import com.salpreh.baseapi.domain.models.Person;
import com.salpreh.baseapi.domain.models.commons.RevisionData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersonRevisionUseCase {
  Optional<Person> findRevision(long id, long revision);
  Page<Person> findRevisions(long id, Pageable pageable);
  Page<RevisionData> findRevisionsData(long id, Pageable pageable);
}

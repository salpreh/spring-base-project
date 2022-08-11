package com.salpreh.baseapi.domain.ports.application;

import com.salpreh.baseapi.domain.models.Person;
import com.salpreh.baseapi.domain.models.revisions.RevisionData;
import com.salpreh.baseapi.domain.models.revisions.RevisionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersonRevisionUseCase {
  Optional<RevisionModel<Person>> findRevision(long id, long revision);
  Page<RevisionModel<Person>> findRevisions(long id, Pageable pageable);
  Page<RevisionData> findRevisionsData(long id, Pageable pageable);
}

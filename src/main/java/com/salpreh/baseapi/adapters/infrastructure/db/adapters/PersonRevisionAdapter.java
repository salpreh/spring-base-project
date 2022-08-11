package com.salpreh.baseapi.adapters.infrastructure.db.adapters;

import com.salpreh.baseapi.adapters.infrastructure.db.mappers.DbMapper;
import com.salpreh.baseapi.adapters.infrastructure.db.models.PersonEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.RevisionsRepository;
import com.salpreh.baseapi.domain.models.Person;
import com.salpreh.baseapi.domain.models.commons.RevisionData;
import com.salpreh.baseapi.domain.ports.infrastructure.PersonRevisionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonRevisionAdapter implements PersonRevisionPort {

  private final RevisionsRepository revisionsRepository;
  private final DbMapper mapper;

  @Override
  public Optional<Person> findRevision(long id, long revision) {
    return revisionsRepository.findEntityRevision(PersonEntity.class, id, revision)
      .map(mapper::map);
  }

  @Override
  public Page<Person> findRevisions(long id, Pageable pageable) {
    return revisionsRepository.findEntityRevisions(PersonEntity.class, id, pageable)
      .map(mapper::map);
  }

  @Override
  public Page<RevisionData> findRevisionsData(long id, Pageable pageable) {
    return revisionsRepository.findEntityRevisionsData(PersonEntity.class, id, pageable)
      .map(mapper::map);
  }
}

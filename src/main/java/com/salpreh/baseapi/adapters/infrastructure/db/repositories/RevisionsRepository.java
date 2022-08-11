package com.salpreh.baseapi.adapters.infrastructure.db.repositories;

import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;

import java.util.Optional;

public interface RevisionsRepository {
  <T, ID> Optional<Pair<T, DefaultRevisionEntity>> findEntityRevision(Class<T> entityClass, ID id, Number revision);
  <T, ID> Page<Pair<T, DefaultRevisionEntity>> findEntityRevisions(Class<T> entityClass, ID id, Pageable pageable);
  <T, ID> Page<DefaultRevisionEntity> findEntityRevisionsData(Class<T> entityClass, ID id, Pageable pageable);
}

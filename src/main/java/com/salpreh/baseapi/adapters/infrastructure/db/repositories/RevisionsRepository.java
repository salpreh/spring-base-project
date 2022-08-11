package com.salpreh.baseapi.adapters.infrastructure.db.repositories;

import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RevisionsRepository {
  <T, ID> Optional<T> findEntityRevision(Class<T> entityClass, ID id, Number revision);
  <T, ID> Page<T> findEntityRevisions(Class<T> entityClass, ID id, Pageable pageable);
  <T, ID> Page<DefaultRevisionEntity> findEntityRevisionsData(Class<T> entityClass, ID id, Pageable pageable);
}

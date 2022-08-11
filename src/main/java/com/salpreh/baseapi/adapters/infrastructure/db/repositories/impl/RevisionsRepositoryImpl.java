package com.salpreh.baseapi.adapters.infrastructure.db.repositories.impl;

import com.salpreh.baseapi.adapters.infrastructure.db.repositories.RevisionsRepository;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.envers.query.AuditEntity.property;
import static org.hibernate.envers.query.AuditEntity.revisionNumber;

@Repository
public class RevisionsRepositoryImpl implements RevisionsRepository {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public <T, ID> Optional<T> findEntityRevision(Class<T> entityClass, ID id, Number revision) {
    return Optional.ofNullable(getAuditReader().find(entityClass, id, revision.intValue()));
  }

  @Override
  public <T, ID> Page<T> findEntityRevisions(Class<T> entityClass, ID id, Pageable pageable) {
    long total = getTotalCount(entityClass, id);

    List<T> revisions = getAuditReader().createQuery()
      .forRevisionsOfEntity(entityClass, true, false)
      .add(property("id").eq(id))
      .addOrder(revisionNumber().desc())
      .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
      .setMaxResults(pageable.getPageSize())
      .getResultList();

    return new PageImpl<>(revisions, pageable, total);
  }

  @Override
  public <T, ID> Page<DefaultRevisionEntity> findEntityRevisionsData(Class<T> entityClass, ID id, Pageable pageable) {
    long total = getTotalCount(entityClass, id);

    List<Object[]> revisionsData = getAuditReader().createQuery()
      .forRevisionsOfEntity(entityClass, false, false)
      .add(property("id").eq(id))
      .addOrder(revisionNumber().desc())
      .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
      .setMaxResults(pageable.getPageSize())
      .getResultList();

    return new PageImpl<>(
      revisionsData.stream()
        .map(d -> (DefaultRevisionEntity)d[1])
        .collect(Collectors.toList()),
      pageable,
      total
    );
  }

  private <T, ID> long getTotalCount(Class<T> entityClass, ID id) {
    return getAuditReader().getRevisions(entityClass, id).stream()
      .count();
  }

  private AuditReader getAuditReader() {
    return AuditReaderFactory.get(entityManager);
  }
}

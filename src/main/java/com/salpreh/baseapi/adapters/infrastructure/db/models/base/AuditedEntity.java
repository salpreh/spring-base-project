package com.salpreh.baseapi.adapters.infrastructure.db.models.base;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditedEntity {
  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @LastModifiedBy
  @Column(name = "modified_by")
  private String modifiedBy;

  @LastModifiedDate
  @Column(name = "modified_at")
  private LocalDateTime modifiedAt;
}

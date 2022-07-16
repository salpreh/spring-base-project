package com.salpreh.baseapi.adapters.infrastructure.db.repositories;

import com.salpreh.baseapi.adapters.infrastructure.db.models.FactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactionRepository extends JpaRepository<FactionEntity, Long> { }

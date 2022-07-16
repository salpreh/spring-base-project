package com.salpreh.baseapi.adapters.infrastructure.db.repositories;

import com.salpreh.baseapi.adapters.infrastructure.db.models.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}

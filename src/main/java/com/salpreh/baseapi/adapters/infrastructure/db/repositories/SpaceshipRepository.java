package com.salpreh.baseapi.adapters.infrastructure.db.repositories;

import com.salpreh.baseapi.adapters.infrastructure.db.models.SpaceshipEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<SpaceshipEntity, Long> {

  @EntityGraph("full-spaceship")
  Page<SpaceshipEntity> findAll(Pageable pageable);

  @EntityGraph("full-spaceship")
  Optional<SpaceshipEntity> findById(long id);
}

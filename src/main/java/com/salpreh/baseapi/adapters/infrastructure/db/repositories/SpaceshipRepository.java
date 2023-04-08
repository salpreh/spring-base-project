package com.salpreh.baseapi.adapters.infrastructure.db.repositories;

import com.salpreh.baseapi.adapters.infrastructure.db.models.SpaceshipEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<SpaceshipEntity, Long> {
  Optional<SpaceshipEntity> findByName(String name);
  @Query("SELECT s FROM SpaceshipEntity s LEFT JOIN s.crew AS c WHERE c.assignee.id = :personId")
  List<SpaceshipEntity> findByAssignedCrew(Long personId);
}

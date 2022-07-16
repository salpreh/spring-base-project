package com.salpreh.baseapi.adapters.infrastructure.db.repositories;

import com.salpreh.baseapi.adapters.infrastructure.db.models.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> { }

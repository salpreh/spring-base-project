package com.salpreh.baseapi.repositories;

import com.salpreh.baseapi.models.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> { }

package com.salpreh.dbexp.repositories;

import com.salpreh.dbexp.models.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> { }

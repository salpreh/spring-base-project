package com.salpreh.dbexp.repositories;

import com.salpreh.dbexp.models.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> { }

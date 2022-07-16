package com.salpreh.baseapi.repositories;

import com.salpreh.baseapi.models.Faction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactionRepository extends JpaRepository<Faction, Long> { }

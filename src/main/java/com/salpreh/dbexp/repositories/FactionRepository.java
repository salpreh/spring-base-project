package com.salpreh.dbexp.repositories;

import com.salpreh.dbexp.models.Faction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactionRepository extends JpaRepository<Faction, Long> { }

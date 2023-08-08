package com.salpreh.baseapi.adapters.infrastructure.db.repositories.custom;

import com.salpreh.baseapi.adapters.infrastructure.db.models.PlanetEntity;
import java.util.Optional;

public interface CustomPlanetRepository {
  Optional<PlanetEntity> findById(long id);
}

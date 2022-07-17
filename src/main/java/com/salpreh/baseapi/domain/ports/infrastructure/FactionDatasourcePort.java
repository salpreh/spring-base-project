package com.salpreh.baseapi.domain.ports.infrastructure;

import com.salpreh.baseapi.domain.models.Faction;
import com.salpreh.baseapi.domain.models.commands.FactionCreateCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FactionDatasourcePort {
  Optional<Faction> findById(Long id);

  Page<Faction> findAll(Pageable pageable);

  Faction createFaction(FactionCreateCommand createCommand);

  Faction updateFaction(long id, FactionCreateCommand updateCommand);

  void deleteFaction(long id);
}

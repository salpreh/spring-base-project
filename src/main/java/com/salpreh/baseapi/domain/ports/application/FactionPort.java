package com.salpreh.baseapi.domain.ports.application;

import com.salpreh.baseapi.domain.models.Faction;
import com.salpreh.baseapi.domain.models.commands.FactionCreateCommand;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FactionPort {
  Optional<Faction> findById(long id);
  Page<Faction> findAll(Pageable pageable);
  Faction createFaction(@Valid FactionCreateCommand createCommand);
  Faction updateFaction(long id, @Valid FactionCreateCommand updateCommand);
  void deleteFaction(long id);
}

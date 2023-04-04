package com.salpreh.baseapi.domain.services;

import com.salpreh.baseapi.domain.models.Faction;
import com.salpreh.baseapi.domain.models.commands.FactionCreateCommand;
import com.salpreh.baseapi.domain.ports.application.FactionPort;
import com.salpreh.baseapi.domain.ports.infrastructure.FactionDatasourcePort;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class FactionUseCase implements FactionPort {

  private final FactionDatasourcePort factionDatasourcePort;

  @Override
  public Optional<Faction> findById(long id) {
    return factionDatasourcePort.findById(id);
  }

  @Override
  public Page<Faction> findAll(Pageable pageable) {
    return factionDatasourcePort.findAll(pageable);
  }

  @Override
  public Faction createFaction(@Valid FactionCreateCommand createCommand) {
    return factionDatasourcePort.createFaction(createCommand);
  }

  @Override
  public Faction updateFaction(long id, @Valid FactionCreateCommand updateCommand) {
    return factionDatasourcePort.updateFaction(id, updateCommand);
  }

  @Override
  public void deleteFaction(long id) {
    factionDatasourcePort.deleteFaction(id);
  }
}

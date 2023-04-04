package com.salpreh.baseapi.adapters.infrastructure.db.adapters;

import com.salpreh.baseapi.adapters.infrastructure.db.mappers.DbMapper;
import com.salpreh.baseapi.adapters.infrastructure.db.models.FactionEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.FactionRepository;
import com.salpreh.baseapi.domain.models.Faction;
import com.salpreh.baseapi.domain.models.commands.FactionCreateCommand;
import com.salpreh.baseapi.domain.ports.infrastructure.FactionDatasourcePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FactionAdapter implements FactionDatasourcePort {

  private final FactionRepository factionRepository;
  private final DbMapper mapper;

  @Override
  public Optional<Faction> findById(long id) {
    return factionRepository.findById(id)
      .map(mapper::map);
  }

  @Override
  public Page<Faction> findAll(Pageable pageable) {
    return factionRepository.findAll(pageable)
      .map(mapper::map);
  }

  @Override
  public Faction createFaction(FactionCreateCommand createCommand) {
    FactionEntity entity = FactionEntity.builder().build();
    processCommand(createCommand, entity);

    return mapper.map(factionRepository.save(entity));
  }

  @Override
  public Faction updateFaction(long id, FactionCreateCommand updateCommand) {
    FactionEntity entity = factionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Unable to find faction with ID " + id));
    processCommand(updateCommand, entity);

    return mapper.map(factionRepository.save(entity));
  }

  @Override
  public void deleteFaction(long id) {
    factionRepository.deleteById(id);
  }

  private FactionEntity processCommand(FactionCreateCommand command, FactionEntity entity) {
    entity.setName(command.getName());

    return entity;
  }
}

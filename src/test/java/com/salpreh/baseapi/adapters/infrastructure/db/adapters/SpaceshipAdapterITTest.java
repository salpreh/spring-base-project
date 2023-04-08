package com.salpreh.baseapi.adapters.infrastructure.db.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import com.salpreh.baseapi.adapters.infrastructure.db.models.SpaceshipEntity;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.SpaceshipRepository;
import com.salpreh.baseapi.bootstrap.DbAdapterApplication;
import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand.AssignationDto;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand.SpaceshipRegistrationDto;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("integration")
@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase(type = DatabaseType.POSTGRES, provider = DatabaseProvider.DOCKER)
@SpringBootTest(classes = DbAdapterApplication.class)
class SpaceshipAdapterITTest {

  @Autowired
  private SpaceshipAdapter spaceshipAdapter;
  @Autowired
  private SpaceshipRepository spaceshipRepository;

  @Test
  @Transactional
  @Sql("classpath:/fixtures/spaceships-data.sql")
  void shouldFindOneById() {
    // when
    Spaceship spaceship = spaceshipAdapter.findById(1L)
      .orElseThrow(() -> new RuntimeException("Spaceship not found"));

    // then
    assertThat(spaceship.getId()).isEqualTo(1L);
    assertThat(spaceship.getName()).isEqualTo("Normandy SR-1");
  }

  @Test
  @Transactional
  @Sql("classpath:/fixtures/spaceships-data.sql")
  void shouldFindEmptyWhenIdDoesNotExist() {
    // when
    Optional<Spaceship> spaceship = spaceshipAdapter.findById(5L);

    // then
    assertThat(spaceship).isEmpty();
  }

  @Test
  @Transactional
  @Sql("classpath:/fixtures/spaceships-data.sql")
  void shouldCreateSpaceship() {
    // given
    SpaceshipCreateCommand createCommand = SpaceshipCreateCommand.builder()
      .name("Enterprise")
      .affiliation(1L)
      .assignedPort(2L)
      .crew(Set.of(
        AssignationDto.build("Captain", 1L),
        AssignationDto.build("First Officer", 2L)
      ))
      .registration(SpaceshipRegistrationDto.build(OffsetDateTime.now(), "250", "EP-250"))
      .build();

    // when
    Spaceship spaceship = spaceshipAdapter.createSpaceship(createCommand);

    // then
    assertThat(spaceship.getId()).isEqualTo(3L);
    assertThat(spaceship.getName()).isEqualTo("Enterprise");
    assertThat(spaceship.getAffiliation().getName()).isEqualTo("Systems Alliance");
    assertThat(spaceship.getAssignedPort().getName()).isEqualTo("Mars");
    assertThat(spaceship.getRegistration().getRegistrationNumber()).isEqualTo("250");
    assertThat(spaceship.getCrew()).hasSize(2);

    assertThat(spaceshipRepository.findById(3L)).isPresent();
  }

  @Test
  @Transactional
  @Sql("classpath:/fixtures/spaceships-data.sql")
  void shouldUpdateSpaceship() {
    // given
    SpaceshipCreateCommand updateCommand = SpaceshipCreateCommand.builder()
      .name("Enterprise")
      .affiliation(1L)
      .assignedPort(2L)
      .crew(Set.of(
        AssignationDto.build("Captain", 1L),
        AssignationDto.build("First Officer", 2L)
      ))
      .build();

    // when
    Spaceship spaceship = spaceshipAdapter.updateSpaceship(1L, updateCommand);

    // then
    assertThat(spaceship.getId()).isEqualTo(1L);
    assertThat(spaceship.getName()).isEqualTo("Enterprise");
    assertThat(spaceship.getAffiliation().getName()).isEqualTo("Systems Alliance");
    assertThat(spaceship.getAssignedPort().getName()).isEqualTo("Mars");
    assertThat(spaceship.getRegistration().getRegistrationNumber()).isEqualTo("0000000012");
    assertThat(spaceship.getCrew()).hasSize(2);

    assertThat(spaceshipRepository.findById(1L).map(SpaceshipEntity::getName)).hasValue("Enterprise");
  }

  @Test
  @Transactional
  @Sql("classpath:/fixtures/spaceships-data.sql")
  void shouldNotUpdatedWhenIdDoesNotExist() {
    // given
    SpaceshipCreateCommand updateCommand = SpaceshipCreateCommand.builder()
      .name("Enterprise")
      .affiliation(1L)
      .assignedPort(2L)
      .crew(Set.of(
        AssignationDto.build("Captain", 1L),
        AssignationDto.build("First Officer", 2L)
      ))
      .build();

    // when
    Executable executable = () -> spaceshipAdapter.updateSpaceship(5L, updateCommand);

    // then
    Assertions.assertThrows(RuntimeException.class, executable);
  }

  @Test
  @Transactional
  @Sql("classpath:/fixtures/spaceships-data.sql")
  void shouldDeleteSpaceship() {
    // when
    spaceshipAdapter.deleteSpaceship(1L);

    // then
    assertThat(spaceshipRepository.findById(1L)).isEmpty();
  }

  @Test
  @Transactional
  @Sql("classpath:/fixtures/spaceships-data.sql")
  void shouldNotDeleteWhenIdNotFound() {
    // when
    Executable executable = () -> spaceshipAdapter.deleteSpaceship(5L);

    // then
    assertThat(spaceshipRepository.findAll().size()).isEqualTo(2);
  }
}
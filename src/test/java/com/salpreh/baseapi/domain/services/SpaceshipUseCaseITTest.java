package com.salpreh.baseapi.domain.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.salpreh.baseapi.DomainApplication;
import com.salpreh.baseapi.adapters.infrastructure.db.repositories.SpaceshipRepository;
import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand.AssignationDto;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand.SpaceshipRegistrationDto;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType;
import java.time.OffsetDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase(type = DatabaseType.POSTGRES, provider = DatabaseProvider.DOCKER)
@SpringBootTest(classes = DomainApplication.class)
class SpaceshipUseCaseITTest {

  @Autowired
  private SpaceshipUseCase spaceshipUseCase;
  @Autowired
  private SpaceshipRepository spaceshipRepository;

  @Test
  @Transactional
  @Sql("classpath:/fixtures/spaceships-data.sql")
  void shouldCreateSpaceship() {
    // given
    SpaceshipCreateCommand command = SpaceshipCreateCommand.builder()
      .name("Normandy SR-2")
      .registration(SpaceshipRegistrationDto.build(OffsetDateTime.now(), "250", "EP-250"))
      .affiliation(1L)
      .assignedPort(2L)
      .crew(Set.of(
        AssignationDto.build("Captain", 1L),
        AssignationDto.build("First Officer", 2L)
      ))
      .build();

    // when
    Spaceship spaceship = spaceshipUseCase.createSpaceship(command);

    // then
    assertThat(spaceship.getId()).isEqualTo(3L);
    assertThat(spaceship.getName()).isEqualTo("Normandy SR-2");
    assertThat(spaceship.getAffiliation().getName()).isEqualTo("Systems Alliance");
    assertThat(spaceship.getAssignedPort().getName()).isEqualTo("Mars");
    assertThat(spaceship.getRegistration().getRegistrationNumber()).isEqualTo("250");
    assertThat(spaceship.getCrew()).hasSize(2);

    assertThat(spaceshipRepository.findById(3L)).isPresent();
  }

}
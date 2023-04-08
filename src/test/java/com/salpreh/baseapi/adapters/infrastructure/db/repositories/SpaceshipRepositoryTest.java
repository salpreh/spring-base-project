package com.salpreh.baseapi.adapters.infrastructure.db.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.salpreh.baseapi.adapters.infrastructure.db.models.SpaceshipEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
class SpaceshipRepositoryTest {

  @Autowired
  private SpaceshipRepository spaceshipRepository;

  @Test
  @Sql("classpath:/fixtures/spaceships-data.sql")
  void shouldFindByAssignedCrew() {
    // when
    var spaceships = spaceshipRepository.findByAssignedCrew(1L);

    // then
    assertThat(spaceships).hasSize(2);
    assertThat(spaceships).map(SpaceshipEntity::getId).contains(1L, 2L);
  }

}
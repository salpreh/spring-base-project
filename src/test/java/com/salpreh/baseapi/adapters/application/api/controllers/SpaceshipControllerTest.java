package com.salpreh.baseapi.adapters.application.api.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salpreh.baseapi.adapters.application.api.config.ExceptionHandlerConfig;
import com.salpreh.baseapi.config.test.MappersTestConfig;
import com.salpreh.baseapi.domain.models.Faction;
import com.salpreh.baseapi.domain.models.Planet;
import com.salpreh.baseapi.domain.models.Spaceship;
import com.salpreh.baseapi.domain.models.SpaceshipRegistration;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand;
import com.salpreh.baseapi.domain.models.commands.SpaceshipCreateCommand.SpaceshipRegistrationDto;
import com.salpreh.baseapi.domain.ports.application.SpaceshipPort;
import com.salpreh.baseapi.helpers.test.SpaceApiFaker;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SpaceshipController.class)
@ContextConfiguration(classes = {MappersTestConfig.class, ExceptionHandlerConfig.class, SpaceshipController.class})
class SpaceshipControllerTest {

  private static final String BASE_PATH = "/spaceship";

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private SpaceshipPort spaceshipUseCase;

  @Test
  void shouldGetAll() throws Exception {
    // given
    Faction faction = SpaceApiFaker.createFaction(1L, "UNSC");
    Planet planet = SpaceApiFaker.createPlanet(1L, "Earth", faction);
    SpaceshipRegistration registration = SpaceApiFaker.createSpaceshipRegistration(1L, "001", "UNSC-001", null);
    List<Spaceship> spaceships = List.of(
      SpaceApiFaker.createSpaceship(1L, "Pillar", registration, planet, faction),
      SpaceApiFaker.createSpaceship(2L, "Infinity", null, planet, faction),
      SpaceApiFaker.createSpaceship(3L, "Iron Hammer", null, planet, faction)
    );
    given(spaceshipUseCase.findAll(any(PageRequest.class)))
      .willReturn(SpaceApiFaker.createPage(spaceships, 0, 5, 10));

    // when
    ResultActions result = mockMvc.perform(get(BASE_PATH)
      .param("page", "1")
      .param("pageSize", "5")
      .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    result.andExpect(status().isOk());
    assertPageResults(result, 3, 0, 2);
    assertSpaceshipData(result, "$.data[0]", spaceships.get(0));
  }

  @Test
  void shouldGetAllWhenNoPagination() throws Exception {
    // given
    Faction faction = SpaceApiFaker.createFaction(1L, "UNSC");
    Planet planet = SpaceApiFaker.createPlanet(1L, "Earth", faction);
    SpaceshipRegistration registration = SpaceApiFaker.createSpaceshipRegistration(1L, "001", "UNSC-001", null);
    List<Spaceship> spaceships = List.of(
      SpaceApiFaker.createSpaceship(1L, "Pillar", registration, planet, faction),
      SpaceApiFaker.createSpaceship(2L, "Infinity", null, planet, faction),
      SpaceApiFaker.createSpaceship(3L, "Iron Hammer", null, planet, faction)
    );
    given(spaceshipUseCase.findAll(any(PageRequest.class)))
      .willReturn(SpaceApiFaker.createPage(spaceships, 0, 10, 3));

    // when
    ResultActions result = mockMvc.perform(get(BASE_PATH)
      .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    result.andExpect(status().isOk());
    assertPageResults(result, 3, 0, 1);
  }

  @Test
  void shouldGetOne() throws Exception {
    // given
    Faction faction = SpaceApiFaker.createFaction(1L, "UNSC");
    Planet planet = SpaceApiFaker.createPlanet(1L, "Earth", faction);
    SpaceshipRegistration registration = SpaceApiFaker.createSpaceshipRegistration(1L, "001", "UNSC-001", null);
    Spaceship spaceship = SpaceApiFaker.createSpaceship(1L, "Pillar", registration, planet, faction);
    given(spaceshipUseCase.findById(1L))
      .willReturn(Optional.of(spaceship));

    // when
    ResultActions result = mockMvc.perform(get(BASE_PATH + "/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    result.andExpect(status().isOk());
    assertSpaceshipData(result, "$", spaceship);
  }

  @Test
  void shouldReturn404WhenGetOneNotFound() throws Exception {
    // given
    given(spaceshipUseCase.findById(1L))
      .willReturn(Optional.empty());

    // when
    ResultActions result = mockMvc.perform(get(BASE_PATH + "/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    result.andExpect(status().isNotFound());
  }

  @Test
  void shouldCreate() throws Exception {
    // given
    Faction faction = SpaceApiFaker.createFaction(1L, "UNSC");
    Planet planet = SpaceApiFaker.createPlanet(1L, "Earth", faction);
    SpaceshipRegistration registration = SpaceApiFaker.createSpaceshipRegistration(1L, "001", "UNSC-001", null);
    Spaceship spaceship = SpaceApiFaker.createSpaceship(1L, "Pillar", registration, planet, faction);

    SpaceshipCreateCommand createCommand = SpaceshipCreateCommand.builder()
      .name(spaceship.getName())
      .registration(SpaceshipRegistrationDto.build(OffsetDateTime.now(), "001", "UNSC-001"))
      .assignedPort(planet.getId())
      .affiliation(faction.getId())
      .build();

    given(spaceshipUseCase.createSpaceship(any(SpaceshipCreateCommand.class)))
      .willReturn(spaceship);

    // when
    ResultActions result = mockMvc.perform(post(BASE_PATH)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(createCommand))
    );

    // then
    result.andExpect(status().isOk());
    assertSpaceshipData(result, "$", spaceship);
  }

  @Test
  void shouldReturn400WhenCreateAndInvalidInput() throws Exception {
    // given
    SpaceshipCreateCommand createCommand = SpaceshipCreateCommand.builder()
      .name("Pillar")
      .registration(SpaceshipRegistrationDto.build(OffsetDateTime.now(), "001", "UNSC-001"))
      .build();

    given(spaceshipUseCase.createSpaceship(any(SpaceshipCreateCommand.class)))
      .willThrow(new ConstraintViolationException("Invalid input", Set.of()));

    // when
    ResultActions result = mockMvc.perform(post(BASE_PATH)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(createCommand))
    );

    // then
    result.andExpect(status().isBadRequest());
  }

  @Test
  void shouldUpdate() throws Exception {
    // given
    Faction faction = SpaceApiFaker.createFaction(1L, "UNSC");
    Planet planet = SpaceApiFaker.createPlanet(1L, "Earth", faction);
    SpaceshipRegistration registration = SpaceApiFaker.createSpaceshipRegistration(1L, "001", "UNSC-001", null);
    Spaceship spaceship = SpaceApiFaker.createSpaceship(1L, "Pillar", registration, planet, faction);

    SpaceshipCreateCommand updateCommand = SpaceshipCreateCommand.builder()
      .name(spaceship.getName())
      .registration(SpaceshipRegistrationDto.build(OffsetDateTime.now(), "001", "UNSC-001"))
      .assignedPort(planet.getId())
      .affiliation(faction.getId())
      .build();

    given(spaceshipUseCase.updateSpaceship(eq(1L), any(SpaceshipCreateCommand.class)))
      .willReturn(spaceship);

    // when
    ResultActions result = mockMvc.perform(put(BASE_PATH + "/{id}", spaceship.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(updateCommand))
    );

    // then
    result.andExpect(status().isOk());
    assertSpaceshipData(result, "$", spaceship);
  }

  @Test
  void shouldReturn400WhenUpdateAndInvalidInput() throws Exception {
    // given
    SpaceshipCreateCommand updateCommand = SpaceshipCreateCommand.builder()
      .name("Pillar")
      .registration(SpaceshipRegistrationDto.build(OffsetDateTime.now(), "001", "UNSC-001"))
      .build();

    // when
    ResultActions result = mockMvc.perform(put(BASE_PATH + "/{id}", 0L)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(updateCommand))
    );

    // then
    result.andExpect(status().isBadRequest());
  }

  @Test
  void shouldDelete() throws Exception {
    // when
    ResultActions result = mockMvc.perform(delete(BASE_PATH + "/{id}", 1L)
      .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    result.andExpect(status().isNoContent());

    verify(spaceshipUseCase).deleteSpaceship(1L);
  }

  @Test
  void shouldReturn400WhenDeleteAndInvalidInput() throws Exception {
    // when
    ResultActions result = mockMvc.perform(delete(BASE_PATH + "/{id}", 0L)
      .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    result.andExpect(status().isBadRequest());
  }

  private void assertPageResults(ResultActions result, int resultSize, int page, int totalPages) throws Exception {
    result.andExpect(jsonPath("$.data", hasSize(resultSize)))
      .andExpect(jsonPath("$.page", is(page)))
      .andExpect(jsonPath("$.numPages", is(totalPages)));
  }

  private void assertSpaceshipData(ResultActions result, String baseJsonPath, Spaceship spaceship) throws Exception {
    result.andExpect(jsonPath(baseJsonPath + ".id", is(spaceship.getId().intValue())))
      .andExpect(jsonPath(baseJsonPath + ".name", is(spaceship.getName())))
      .andExpect(jsonPath(baseJsonPath + ".crew", hasSize(spaceship.getCrew().size())));

    if (spaceship.getAffiliation() != null) {
      result.andExpect(jsonPath(baseJsonPath + ".affiliation.id", is(spaceship.getAffiliation().getId().intValue())));
    }

    if (spaceship.getRegistration() != null) {
      result.andExpect(jsonPath(baseJsonPath + ".registration.id", is(spaceship.getRegistration().getId().intValue())));
    }

    if (spaceship.getAssignedPort() != null) {
      result.andExpect(jsonPath(baseJsonPath + ".assignedPort.id", is(spaceship.getAssignedPort().getId().intValue())));
    }
  }
}
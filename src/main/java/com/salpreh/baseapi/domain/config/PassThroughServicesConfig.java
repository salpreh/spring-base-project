package com.salpreh.baseapi.domain.config;

import com.salpreh.baseapi.domain.helpers.ProxyUtils;
import com.salpreh.baseapi.domain.ports.application.FactionUseCase;
import com.salpreh.baseapi.domain.ports.application.PersonUseCase;
import com.salpreh.baseapi.domain.ports.application.PlanetUseCase;
import com.salpreh.baseapi.domain.ports.application.SpaceshipUseCase;
import com.salpreh.baseapi.domain.ports.infrastructure.FactionDatasourcePort;
import com.salpreh.baseapi.domain.ports.infrastructure.PersonDatasourcePort;
import com.salpreh.baseapi.domain.ports.infrastructure.PlanetDatasourcePort;
import com.salpreh.baseapi.domain.ports.infrastructure.SpaceshipDatasourcePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PassThroughServicesConfig {

  @Bean
  public PersonUseCase personUseCase(PersonDatasourcePort personDatasourcePort) {
    return ProxyUtils.createPassThroughProxy(PersonUseCase.class, personDatasourcePort);
  }

  @Bean
  public FactionUseCase factionUseCase(FactionDatasourcePort factionDatasourcePort) {
    return ProxyUtils.createPassThroughProxy(FactionUseCase.class, factionDatasourcePort);
  }

  @Bean
  public PlanetUseCase planetUseCase(PlanetDatasourcePort planetDatasourcePort) {
    return ProxyUtils.createPassThroughProxy(PlanetUseCase.class, planetDatasourcePort);
  }

  @Bean
  public SpaceshipUseCase spaceshipUseCase(SpaceshipDatasourcePort spaceshipDatasourcePort) {
    return ProxyUtils.createPassThroughProxy(SpaceshipUseCase.class, spaceshipDatasourcePort);
  }
}

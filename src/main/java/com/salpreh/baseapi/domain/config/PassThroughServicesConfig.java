package com.salpreh.baseapi.domain.config;

import com.salpreh.baseapi.domain.helpers.ProxyUtils;
import com.salpreh.baseapi.domain.ports.application.FactionPort;
import com.salpreh.baseapi.domain.ports.application.PersonPort;
import com.salpreh.baseapi.domain.ports.application.PlanetPort;
import com.salpreh.baseapi.domain.ports.application.SpaceshipPort;
import com.salpreh.baseapi.domain.ports.infrastructure.FactionDatasourcePort;
import com.salpreh.baseapi.domain.ports.infrastructure.PersonDatasourcePort;
import com.salpreh.baseapi.domain.ports.infrastructure.PlanetDatasourcePort;
import com.salpreh.baseapi.domain.ports.infrastructure.SpaceshipDatasourcePort;
import org.springframework.context.annotation.Configuration;

/**
 * Disabled proxy use case beans to implement them manually
 */
@Configuration
public class PassThroughServicesConfig {

  public PersonPort personUseCase(PersonDatasourcePort personDatasourcePort) {
    return ProxyUtils.createPassThroughProxy(PersonPort.class, personDatasourcePort);
  }

  public FactionPort factionUseCase(FactionDatasourcePort factionDatasourcePort) {
    return ProxyUtils.createPassThroughProxy(FactionPort.class, factionDatasourcePort);
  }

  public PlanetPort planetUseCase(PlanetDatasourcePort planetDatasourcePort) {
    return ProxyUtils.createPassThroughProxy(PlanetPort.class, planetDatasourcePort);
  }

  public SpaceshipPort spaceshipUseCase(SpaceshipDatasourcePort spaceshipDatasourcePort) {
    return ProxyUtils.createPassThroughProxy(SpaceshipPort.class, spaceshipDatasourcePort);
  }
}

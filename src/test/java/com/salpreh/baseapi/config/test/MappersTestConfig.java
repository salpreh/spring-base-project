package com.salpreh.baseapi.config.test;

import com.salpreh.baseapi.adapters.application.api.mappers.ApiMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MappersTestConfig {

  @Bean
  public ApiMapper apiMapper() {
    return Mappers.getMapper(ApiMapper.class);
  }
}

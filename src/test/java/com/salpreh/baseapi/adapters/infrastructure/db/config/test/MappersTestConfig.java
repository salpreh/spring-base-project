package com.salpreh.baseapi.adapters.infrastructure.db.config.test;

import com.salpreh.baseapi.adapters.infrastructure.db.mappers.DbMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MappersTestConfig {

  @Bean
  public DbMapper dbMapper() {
    return Mappers.getMapper(DbMapper.class);
  }
}

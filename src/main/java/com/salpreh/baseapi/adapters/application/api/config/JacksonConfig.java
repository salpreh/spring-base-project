package com.salpreh.baseapi.adapters.application.api.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Configuration
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
    return builder -> builder.serializationInclusion(Include.NON_NULL);
  }
}

package com.salpreh.baseapi.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of("salpreh");
  }
}

package com.salpreh.baseapi.adapters.application.api.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiMetricService {

  private final MeterRegistry meterRegistry;

  public void registerPageRequest(String path) {
    Counter.builder("api.page.requests")
      .tag("path", path)
      .description("Number of paged requests")
      .register(meterRegistry)
      .increment();
  }

  public void registerItemRequest(String path) {
    Counter.builder("api.item.requests")
      .tag("path", path)
      .description("Number of item requests")
      .register(meterRegistry)
      .increment();
  }
}

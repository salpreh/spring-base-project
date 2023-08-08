package com.salpreh.baseapi.adapters.application.api.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
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

  public void registerPageSize(String path, int pageSize) {
    DistributionSummary.builder("api.page.size")
      .tag("path", path)
      .description("Page size")
      .publishPercentiles(0.5, 0.75, 0.95)
      .register(meterRegistry)
      .record(pageSize);
  }
}

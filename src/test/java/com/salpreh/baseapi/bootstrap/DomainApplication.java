package com.salpreh.baseapi.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.salpreh.baseapi.domain", "com.salpreh.baseapi.adapters.infrastructure"})
@EnableJpaRepositories(basePackages = "com.salpreh.baseapi.adapters.infrastructure.db.repositories")
@EntityScan(basePackages = "com.salpreh.baseapi.adapters.infrastructure.db.models")
public class DomainApplication {

  public static void main(String[] args) {
    SpringApplication.run(DomainApplication.class, args);
  }
}

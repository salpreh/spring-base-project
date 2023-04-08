package com.salpreh.baseapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.salpreh.baseapi.domain", "com.salpreh.baseapi.adapters.infrastructure"})
public class DomainApplication {

  public static void main(String[] args) {
    SpringApplication.run(DomainApplication.class, args);
  }
}

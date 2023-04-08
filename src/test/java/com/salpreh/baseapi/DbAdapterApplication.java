package com.salpreh.baseapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.salpreh.baseapi.adapters.infrastructure.db")
public class DbAdapterApplication {

  public static void main(String[] args) {
    SpringApplication.run(DbAdapterApplication.class, args);
  }
}

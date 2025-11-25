package com.example.ApuDaily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApuDailyApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApuDailyApplication.class, args);
  }
}

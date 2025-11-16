package com.orion.mdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(modifyOnCreate = false)
@SpringBootApplication
public class MddApplication {
    static void main(String[] args) {
        SpringApplication.run(MddApplication.class, args);
    }
}
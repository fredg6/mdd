package com.orion.mdd;

import com.orion.mdd.config.CustomAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAware", modifyOnCreate = false)
@SpringBootApplication
public class MddApplication {
    static void main(String[] args) {
        SpringApplication.run(MddApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new CustomAuditorAware();
    }
}
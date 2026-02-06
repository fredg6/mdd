package com.orion.mdd;

import com.orion.mdd.config.CustomAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;

@EnableJpaAuditing(auditorAwareRef = "auditorAware", modifyOnCreate = false)
@SpringBootApplication
public class MddApplication extends DelegatingWebMvcConfiguration {
    static void main(String[] args) {
        SpringApplication.run(MddApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new CustomAuditorAware();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "HEAD", "POST", "PATCH", "DELETE");
    }
}
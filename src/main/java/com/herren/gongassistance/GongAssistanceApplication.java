package com.herren.gongassistance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GongAssistanceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GongAssistanceApplication.class, args);
    }
}

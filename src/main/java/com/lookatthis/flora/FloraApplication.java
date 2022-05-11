package com.lookatthis.flora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FloraApplication {

    public static void main(String[] args) {
        SpringApplication.run(FloraApplication.class, args);
    }

}

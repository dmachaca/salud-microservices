package com.pe.den.authservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner testPassword(
            PasswordEncoder encoder
    ) {

        return args -> {

            String hash =
                    encoder.encode("123456");

            System.out.println(
                    "HASH => " + hash
            );

            System.out.println(
                    "MATCH => " +
                            encoder.matches(
                                    "123456",
                                    hash
                            )
            );
        };
    }
}

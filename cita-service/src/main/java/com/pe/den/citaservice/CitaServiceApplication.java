package com.pe.den.citaservice;

import com.pe.den.citaservice.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CitaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitaServiceApplication.class, args);
    }

}

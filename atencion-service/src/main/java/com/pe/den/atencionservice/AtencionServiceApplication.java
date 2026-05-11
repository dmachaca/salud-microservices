package com.pe.den.atencionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AtencionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtencionServiceApplication.class, args);
    }

}

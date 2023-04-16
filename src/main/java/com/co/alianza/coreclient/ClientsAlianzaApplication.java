package com.co.alianza.coreclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.co.alianza.coreclient.repository")
public class ClientsAlianzaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientsAlianzaApplication.class, args);
    }

}

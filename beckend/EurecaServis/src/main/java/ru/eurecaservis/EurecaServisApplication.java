package ru.eurecaservis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurecaServisApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurecaServisApplication.class, args);
    }

}

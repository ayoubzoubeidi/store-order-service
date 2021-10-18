package com.maz.store.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StoreOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreOrderServiceApplication.class, args);
    }

}

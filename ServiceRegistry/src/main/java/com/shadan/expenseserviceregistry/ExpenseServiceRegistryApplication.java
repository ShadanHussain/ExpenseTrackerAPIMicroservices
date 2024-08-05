package com.shadan.expenseserviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ExpenseServiceRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseServiceRegistryApplication.class, args);
    }

}

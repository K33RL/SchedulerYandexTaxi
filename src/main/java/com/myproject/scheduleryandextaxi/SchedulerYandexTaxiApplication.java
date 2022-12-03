package com.myproject.scheduleryandextaxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableConfigurationProperties
@EnableFeignClients
@SpringBootApplication
public class SchedulerYandexTaxiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerYandexTaxiApplication.class, args);
    }

}

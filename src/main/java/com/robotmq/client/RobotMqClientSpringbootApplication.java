package com.robotmq.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RobotMqClientSpringbootApplication {

    public static void main(String[] args)  {
        SpringApplication.run(RobotMqClientSpringbootApplication.class, args);}
}

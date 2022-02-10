package com.robotmq.client;

import com.robotmq.client.common.RobotMQStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Test {

    @Autowired
    private RobotMQStarter robotMQStarter;

    @PostConstruct
    void dosome() throws InterruptedException {

        robotMQStarter.build("com.robotmq.client");
    }
}

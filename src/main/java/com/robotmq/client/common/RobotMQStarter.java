package com.robotmq.client.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.robotmq.client.common.setup.RobotMQSetUp;
import com.robotmq.client.glob.CommonVars;
import com.robotmq.client.handler.RobotMQHandler;
import com.robotmq.client.handler.RobotMQProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class RobotMQStarter {

    private RobotMQSetUp robotMQSetUp = RobotMQSetUp.getINSTANCE();
    private RobotMQHandler handler = RobotMQHandler.getINSTANCE();

    public void build(String basePackage) throws InterruptedException {
            if (!StringUtils.hasText(basePackage)){
                throw new InterruptedException();
            }
            CommonVars.PACKAGE_NAME= basePackage;
            robotMQSetUp.setUp();
            handler.handler();
    }

}

package com.robotmq.client.handler;

import java.io.IOException;

public class RobotMQTemplate {

    private final RobotMQProducer robotMQProducer = RobotMQProducer.getINSTANCE();

    public void produce(String topic,Object data) throws InterruptedException, IOException {
        robotMQProducer.produce(topic,data);
    }

}

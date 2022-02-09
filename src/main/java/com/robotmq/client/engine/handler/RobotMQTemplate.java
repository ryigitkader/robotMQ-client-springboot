package com.robotmq.client.engine.handler;

import com.robotmq.client.common.setup.RobotMQProducer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RobotMQTemplate {

    private final RobotMQProducer robotMQProducer = RobotMQProducer.getINSTANCE();

    public void produce(String topic,Object data) throws InterruptedException, IOException {
        robotMQProducer.produce(topic,data);
    }

}

package com.robotmq.client.common.produce;

import com.robotmq.client.common.setup.RobotMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RobotMQTemplate {

    @Autowired
    private RobotMQProducer robotMQProducer;

    public void produce(String topic,Object data) throws InterruptedException, IOException {
        robotMQProducer.produce(topic,data);
    }

}

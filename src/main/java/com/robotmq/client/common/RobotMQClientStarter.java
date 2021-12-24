package com.robotmq.client.common;

import com.robotmq.client.common.setup.RobotMQSetUp;
import com.robotmq.client.engine.handler.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RobotMQClientStarter {

    @Autowired
    @Qualifier("robotMQSetup")
    private RobotMQSetUp robotMQSetUp;

    @Autowired
    @Qualifier("robotMQHandler")
    Handler handler;

    @PostConstruct
    void startRobotMQClient(){
        robotMQSetUp.setUp();
        handler.handler();

        ///Test Variables
        System.out.println("Base package : "+CommonVars.PACKAGE_NAME );
        System.out.println("Methods and topics : "+CommonVars.methodsAndTopicsMap );
        System.out.println("Will consume topics: "+CommonVars.WILL_CONSUME_TOPICS );
    }
}

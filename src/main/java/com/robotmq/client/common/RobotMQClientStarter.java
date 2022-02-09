package com.robotmq.client.common;

import com.robotmq.client.common.setup.RobotMQSetUp;
import com.robotmq.client.engine.handler.CommonVars;
import com.robotmq.client.engine.handler.Handler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RobotMQClientStarter {

    private final RobotMQSetUp robotMQSetUp ;

    private final Handler handler;

    public RobotMQClientStarter(RobotMQSetUp robotMQSetUp, Handler handler) {
        this.robotMQSetUp = robotMQSetUp;
        this.handler = handler;
    }

    @PostConstruct
    void startRobotMQClient(){
        robotMQSetUp.setUp();
        handler.handler();

        ///Test Variables
        System.out.println("Base package : "+ CommonVars.PACKAGE_NAME );
        System.out.println("Methods and topics : "+CommonVars.methodsAndTopicsMap );
        System.out.println("Will consume topics: "+CommonVars.WILL_CONSUME_TOPICS );
    }

}

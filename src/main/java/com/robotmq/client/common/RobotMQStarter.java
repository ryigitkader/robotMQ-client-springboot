package com.robotmq.client.common;

import com.robotmq.client.common.setup.RobotMQSetUp;
import com.robotmq.client.glob.CommonVars;
import com.robotmq.client.handler.RobotMQHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RobotMQStarter {

    /*@Autowired
    private ApplicationContext context;*/

    private RobotMQSetUp robotMQSetUp = RobotMQSetUp.getINSTANCE();
    private RobotMQHandler handler = RobotMQHandler.getINSTANCE();

    public void build(String basePackage) throws InterruptedException {
            if (!StringUtils.hasText(basePackage)){
                throw new InterruptedException();
            }
            CommonVars.PACKAGE_NAME= basePackage;
            robotMQSetUp.setUp();
            handler.handler();

        ///Test Variables
        System.out.println("Base package : "+ CommonVars.PACKAGE_NAME );
        System.out.println("Methods and topics : "+CommonVars.methodsAndTopicsMap );
        System.out.println("Will consume topics: "+CommonVars.WILL_CONSUME_TOPICS );

    }

}

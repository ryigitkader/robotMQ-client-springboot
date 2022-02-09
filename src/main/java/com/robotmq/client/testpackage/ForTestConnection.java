package com.robotmq.client.testpackage;

import com.robotmq.client.annotation.RobotMQConnection;
import com.robotmq.client.annotation.RobotMQListener;

public class ForTestConnection {

    public ForTestConnection(){}

    @RobotMQConnection(url = "localhost",port = "9988")
    void connection(){}

    @RobotMQListener(topics = "tekTopic")
    public void testForConsume1(String x){
        System.out.println("testForConsume1 : "+x);
    }

    @RobotMQListener(topics = {"topic1","topicyeni"})
    public void testForConsume2(Object x){
        System.out.println("testForConsume2 : "+x);
    }
}

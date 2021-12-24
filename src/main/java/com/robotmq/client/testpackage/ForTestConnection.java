package com.robotmq.client.testpackage;

import com.robotmq.client.annotation.RobotMQConnection;
import com.robotmq.client.annotation.RobotMQListener;

public class ForTestConnection {


    @RobotMQConnection(url = "localhost",port = "9988")
    void connection(){}

    @RobotMQListener(topics = "tekTopic")
    void testForConsume1(Object x){

    }

    @RobotMQListener(topics = {"topic1","topicyeni"})
    void testForConsume2(Object x){

    }
}

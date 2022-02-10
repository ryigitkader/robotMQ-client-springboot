package com.robotmq.client.testpackage;

import com.robotmq.client.annotation.RobotMQConnection;
import com.robotmq.client.annotation.RobotMQListener;

public class ForTestConnection {

    public ForTestConnection(){}

    @RobotMQConnection(url = "localhost",port = "9988")
    void connection(){}

    @RobotMQListener(topics = {"topic1,topic2"})
    public void testForConsume1(String x){
        System.out.println("testForConsume1 : "+x);
    }

    @RobotMQListener(topics = "topic3")
    public void testForConsume2(Person x){
        Person p = x;
        System.out.println("testForConsume2 : "+x);
    }
}



class Person{

    int id;
    String name;

    public Person(){}

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
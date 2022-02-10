package com.robotmq.client.exception;

public class RobotMQException extends RuntimeException{

    public RobotMQException(){
        super();
    }

    public RobotMQException(String message){
        super(message);
    }

    public RobotMQException(Throwable e,String message){
        super(message,e);
    }
}

package com.robotmq.client.exception;

public class RobotMQConnectionParametersNotFoundException extends RuntimeException{

    public RobotMQConnectionParametersNotFoundException(){
        super();
    }

    public RobotMQConnectionParametersNotFoundException(String message){
        super(message);
    }

    public RobotMQConnectionParametersNotFoundException(Throwable e,String message){
        super(message,e);
    }

}

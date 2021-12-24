package com.robotmq.client.exception;

public class RobotMQNotFoundWillConsumeTopicsException extends RuntimeException {

    public RobotMQNotFoundWillConsumeTopicsException(){
        super();
    }

    public RobotMQNotFoundWillConsumeTopicsException(String message){
        super(message);
    }

    public RobotMQNotFoundWillConsumeTopicsException(Throwable e,String message){
        super(message,e);
    }
}

package com.robotmq.client.handler;

import com.robotmq.client.common.setup.RobotMQConnectionParams;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

import java.util.logging.Logger;

@Component
public class RobotMQHandler implements Handler{

    private Socket client;
    private final Logger logger = Logger.getLogger(RobotMQHandler.class.getName());


    @Override
    public void handler() {
        try {
            client = createSocketConnection();
            new Thread(new RobotMQHandlerThread(client)).start();
        }catch (Exception e){
            logger.severe("Can not Connect To RobotMQ Cluster");
        }
    }


    private Socket createSocketConnection() throws IOException {
        return new Socket(RobotMQConnectionParams.HOST, Integer.parseInt(RobotMQConnectionParams.PORT));
    }



}

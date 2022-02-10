package com.robotmq.client.handler;


import com.robotmq.client.common.setup.RobotMQConnectionParams;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class RobotMQHandler implements Handler{

    private static RobotMQHandler INSTANCE = new RobotMQHandler();

    private Socket client;
    private final Logger logger = Logger.getLogger(RobotMQHandler.class.getName());

    private RobotMQHandler(){}

    public static RobotMQHandler getINSTANCE() {
        return INSTANCE;
    }

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

package com.robotmq.client.engine.handler;

import com.robotmq.client.common.RobotMQConnectionParams;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
@Qualifier("robotMQHandler")
public class RobotMQHandler implements Handler{

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private Socket client;
    Logger logger = Logger.getLogger(RobotMQHandler.class.getName());
    private static int connectionTryCount = 0;

    @Override
    public void handler() {
        try {
            startRetrySocketConnection();
            client = createSocketConnection();
            new Thread(new RobotMQHandlerThread(client)).start();
        }catch (Exception e){
            logger.severe("Can not Connect To RobotMQ Cluster");
            logger.severe("System Will Try To Reconnect To RobotMQ Cluster");
        }
    }


    private Socket createSocketConnection() throws IOException {
        return new Socket(RobotMQConnectionParams.HOST, Integer.parseInt(RobotMQConnectionParams.PORT));
    }

    private void startRetrySocketConnection() throws IOException, InterruptedException {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (client == null || !client.isConnected()){
                    try {
                        client = createSocketConnection();
                        new RobotMQHandlerThread(client).start();
                    } catch (Exception e) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        logger.info("Connection Try Count :" + connectionTryCount);
                    }

                    if (connectionTryCount > 10) {
                        logger.info("System will wait 1 minute to connect RobotMQ Cluster");
                        try {
                            TimeUnit.MINUTES.sleep(1);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        connectionTryCount = 0;
                    }
                    connectionTryCount++;
                }
            }
        },10,2,TimeUnit.SECONDS);
    }



}

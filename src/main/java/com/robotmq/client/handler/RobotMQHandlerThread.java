package com.robotmq.client.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.robotmq.client.glob.CommonVars;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

public class RobotMQHandlerThread extends Thread {

    Logger logger = Logger.getLogger(RobotMQHandlerThread.class.getName());

    private Socket socket;
    private BufferedReader in = null;
    private PrintWriter out = null;


    private RobotMQInvoker robotMQInvoker = RobotMQInvoker.getINSTANCE();

    public RobotMQHandlerThread(Socket socket) {
        this.socket = socket;
    }

    // TODO : If connection can not create , stop the application

    @Override
    public void run() {

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (true) {
                handleOutputStream();
                handleInputStream();
            }
        } catch (SocketTimeoutException e) {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket = null;
            }
        }
    }


    private void handleInputStream() throws IOException, InterruptedException {
        if (in != null && in.ready()) {
            String line = in.readLine();
            if (StringUtils.hasText(line)) {
                System.out.println(line);
                CommonVars.WILL_INVOKE_QUEUE.put(line);
            }
        }

        if (!CommonVars.WILL_INVOKE_QUEUE.isEmpty()) {

            CommonVars.WILL_INVOKE_QUEUE.forEach(o -> {
                JSONObject jsonObject = new JSONObject(o);
                String topic = jsonObject.getString("topic");
                String data = jsonObject.getString("data");
                CommonVars.methodsAndTopicsMap.forEach((key, value) -> {
                    value.forEach( v -> {
                        if (v.equals(topic)) {
                            try {
                                robotMQInvoker.invokeMethod(key, data);
                                logger.info("Invoked !!!");
                            } catch (JsonProcessingException | InvocationTargetException | IllegalAccessException
                                    | NoSuchMethodException | InstantiationException | ClassNotFoundException e) {

                                e.printStackTrace();
                            }
                            logger.info("Method : " + key + " invoked ,  Topic : " + topic + " , Data : " + data);
                            CommonVars.WILL_INVOKE_QUEUE.removeIf(x -> x.equals(o));
                        }
                    });
                });
            });

        }
    }

    private void handleOutputStream() {
        if (!CommonVars.OUTTA_QUEUE_TO_BROKER.isEmpty()) {
            if (out != null) {
                CommonVars.OUTTA_QUEUE_TO_BROKER.forEach(o -> {
                    out.println(CommonVars.OUTTA_QUEUE_TO_BROKER.poll() + "\n\r");
                    out.flush();
                });
            }
        }
    }

}
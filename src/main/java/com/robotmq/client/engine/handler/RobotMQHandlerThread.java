package com.robotmq.client.engine.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public RobotMQHandlerThread(Socket socket) throws IOException {
        this.socket = socket;
    }


    @Override
    public void run() {

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while(true){

                /// Outstream Process
                if (!CommonVars.OUTTA_QUEUE_TO_BROKER.isEmpty()) {
                    if (out != null){
                        CommonVars.OUTTA_QUEUE_TO_BROKER.forEach(o -> {
                            out.println(CommonVars.OUTTA_QUEUE_TO_BROKER.poll()+"\n\r");
                            out.flush();
                        });
                    }
                }


                //Instream Process
                if (in != null && in.ready()){
                    String line = in.readLine();
                    if (StringUtils.hasText(line)) {
                        System.out.println(line);
                        CommonVars.WILL_INVOKE_QUEUE.put(line);
                    }
                }

               if(!CommonVars.WILL_INVOKE_QUEUE.isEmpty()){
                    CommonVars.WILL_INVOKE_QUEUE.forEach(o -> {
                        JSONObject jsonObject = new JSONObject(o);
                        String topic = jsonObject.getString("topic");
                        String data = jsonObject.getString("data");
                        CommonVars.methodsAndTopicsMap.entrySet().forEach( t -> {
                            if(t.getValue().contains(topic)){
                                try {
                                    robotMQInvoker.invokeMethod(t.getKey(),data);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                logger.info("Method : "+t.getKey()+" invoked ,  Topic : "+topic+" , Data : "+data);
                                CommonVars.WILL_INVOKE_QUEUE.removeIf(x -> x.equals(o));
                            }
                        });


                        //robotMQInvoker.invokeMethod();
                    });
                }

            }
        } catch (SocketTimeoutException e){
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally{
            if(socket != null){
                socket = null;
            }
        }
    }

}

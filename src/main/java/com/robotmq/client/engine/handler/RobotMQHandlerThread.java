package com.robotmq.client.engine.handler;

import com.robotmq.client.common.CommonVars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class RobotMQHandlerThread extends Thread {

    Socket socket;

    public RobotMQHandlerThread(Socket socket) throws IOException {
        this.socket = socket;
    }


    @Override
    public void run() {

        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while(true){
                if (!CommonVars.OUTTA_QUEUE_TO_BROKER.isEmpty()) {
                    if (out != null){
                        out.println(CommonVars.OUTTA_QUEUE_TO_BROKER.poll()+"\n\r");
                        out.flush();
                    }

                }

                if (in != null){
                    String line = in.readLine();
                    if (line != null) {
                        System.out.println(line);
                        CommonVars.WILL_INVOKE_QUEUE.put(line);
                    }
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

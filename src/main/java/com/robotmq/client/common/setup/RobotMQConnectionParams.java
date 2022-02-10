package com.robotmq.client.common.setup;

import org.springframework.util.StringUtils;

/**
 * @author yigitkader
 */
public class RobotMQConnectionParams {

    public static String HOST ;
    public static String PORT ;

    public static boolean buildConnectionParameters(String host,String port){
        if (StringUtils.hasText(host.trim()) && StringUtils.hasText(port.trim())){
            HOST = host;
            PORT = port;
            return true;
        }
        return false;
    }
}
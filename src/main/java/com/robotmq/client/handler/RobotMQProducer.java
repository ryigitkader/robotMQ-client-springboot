package com.robotmq.client.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.robotmq.client.common.constants.RobotMQConstants;
import com.robotmq.client.common.util.RobotMQUtils;
import com.robotmq.client.glob.CommonVars;

import org.json.JSONObject;



/**
 * @author yigitkader
 */
public class RobotMQProducer {

    // TODO: 24.12.2021 will be add controls for security

    public static RobotMQProducer INSTANCE = new RobotMQProducer();

    private RobotMQProducer(){}

    public static RobotMQProducer getINSTANCE() {
        return INSTANCE;
    }


    public void produce(String topic, Object data) throws InterruptedException, JsonProcessingException {
        JSONObject collections = new JSONObject();
        String jsonData = RobotMQUtils.convertObjectToJsonString(data);
        collections.put("type", RobotMQConstants.PRODUCE_REQUEST);
        collections.put("topic",topic);
        collections.put("data",jsonData);
        CommonVars.OUTTA_QUEUE_TO_BROKER.put(collections.toString());
    }


}
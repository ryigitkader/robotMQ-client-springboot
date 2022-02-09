package com.robotmq.client.common.setup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.robotmq.client.engine.handler.CommonVars;
import com.robotmq.client.common.constants.RobotMqClientConstants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * @author yigitkader
 */
public class RobotMQProducer {

    // TODO: 24.12.2021 will be add controls for security

    private static RobotMQProducer INSTANCE = new RobotMQProducer();

    public static RobotMQProducer getINSTANCE() {
        return INSTANCE;
    }

    public void produce(String topic, Object data) throws InterruptedException, JsonProcessingException {
        JSONObject collections = new JSONObject();
        String jsonData = convertObjectToJsonString(data);
        collections.put("type", RobotMqClientConstants.PRODUCE_REQUEST);
        collections.put("topic",topic);
        collections.put("data",jsonData);
        CommonVars.OUTTA_QUEUE_TO_BROKER.put(collections.toString());
    }

    protected void produce(List<String> topics) throws InterruptedException {
        String  topicsJsonArrayStr = new JSONArray(topics).toString();
        JSONObject collections = new JSONObject();
        collections.put("type",RobotMqClientConstants.SAVE_TOPICS_WILL_CONSUME_REQUEST);
        collections.put("topics",topicsJsonArrayStr);

        CommonVars.OUTTA_QUEUE_TO_BROKER.put(collections.toString());
    }

    private String convertObjectToJsonString(Object obj) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(obj);
    }
}

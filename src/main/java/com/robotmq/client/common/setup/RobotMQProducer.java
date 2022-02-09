package com.robotmq.client.common.setup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.robotmq.client.common.CommonVars;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class RobotMQProducer {

    // TODO: 24.12.2021 will be add controls for security

    public void produce(String topic,Object data) throws InterruptedException, JsonProcessingException {
        JSONObject collect = new JSONObject();
        String jsonData = convertObjectToJsonString(data);
        collect.put("type","produce-request");
        collect.put("topic",topic);
        collect.put("data",jsonData);
        CommonVars.OUTTA_QUEUE_TO_BROKER.put(collect.toString());
    }

    protected void produce(List<String> topics) throws InterruptedException {
        String  topicsJsonArrayStr = new JSONArray(topics).toString();
        JSONObject collect = new JSONObject();
        collect.put("type","send-topics-request");
        collect.put("topics",topicsJsonArrayStr);

        CommonVars.OUTTA_QUEUE_TO_BROKER.put(collect.toString());
    }

    private String convertObjectToJsonString(Object obj) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(obj);
    }
}

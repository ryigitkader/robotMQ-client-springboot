package com.robotmq.client.common.setup;

import com.robotmq.client.common.CommonVars;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class RobotMQProducer {

    // TODO: 24.12.2021 will be add controls for security

    public void produce(String topic,Object data) throws InterruptedException {
        JSONObject collect = new JSONObject();
        String dataJsonStr = new JSONObject(data).toString();
        collect.put("type","produce-request");
        collect.put("topic",topic);
        collect.put("data",dataJsonStr);

        CommonVars.OUTTA_QUEUE_TO_BROKER.put(collect.toString());
    }

    protected void produce(List<String> topics) throws InterruptedException {
        String  topicsJsonArrayStr = new JSONArray(topics).toString();
        JSONObject collect = new JSONObject();
        collect.put("type","consume-request");
        collect.put("topics",topicsJsonArrayStr);

        CommonVars.OUTTA_QUEUE_TO_BROKER.put(collect.toString());
    }
}

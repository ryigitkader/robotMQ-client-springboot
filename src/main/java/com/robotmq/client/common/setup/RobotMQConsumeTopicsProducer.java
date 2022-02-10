package com.robotmq.client.common.setup;


import com.robotmq.client.common.constants.RobotMQConstants;
import com.robotmq.client.glob.CommonVars;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class RobotMQConsumeTopicsProducer {

    private static RobotMQConsumeTopicsProducer INSTANCE = new RobotMQConsumeTopicsProducer();

    private RobotMQConsumeTopicsProducer(){}

    public static RobotMQConsumeTopicsProducer getINSTANCE() {
        return INSTANCE;
    }

    protected void produce(List<String> topics) throws InterruptedException {
        String topicsJsonArrayStr = new JSONArray(topics).toString();
        JSONObject collections = new JSONObject();
        collections.put("type", RobotMQConstants.SAVE_TOPICS_WILL_CONSUME_REQUEST);
        collections.put("topics", topicsJsonArrayStr);

        CommonVars.OUTTA_QUEUE_TO_BROKER.put(collections.toString());
    }


}

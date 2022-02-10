package com.robotmq.client.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;

public class RobotMQUtils {

    private static ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private RobotMQUtils(){}

    public static String convertObjectToJsonString(Object obj) throws JsonProcessingException {
        return ow.writeValueAsString(obj);
    }

    public static JSONObject convertObjectToJson(Object obj) throws JsonProcessingException {
        return new JSONObject(ow.writeValueAsString(obj));
    }
}

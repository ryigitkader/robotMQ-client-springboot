package com.robotmq.client.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class RobotMQInvoker {

    private static RobotMQInvoker INSTANCE = new RobotMQInvoker();

    public static RobotMQInvoker getINSTANCE() {
        return INSTANCE;
    }

    private RobotMQInvoker() {
    }

    protected synchronized void invokeMethod(Method method, String data) throws JsonProcessingException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        Class<?> classOfParameter = Arrays.stream(method.getParameterTypes()).findFirst().orElseThrow(IllegalArgumentException::new);
        Class<?> declarationClassOfMethod = Class.forName(method.getDeclaringClass().getName());

        Object obj = null;
        if(!classOfParameter.equals(String.class)){
            try {
                obj = new ObjectMapper().readValue(new JSONObject(data).toString(), classOfParameter);
            }catch (JSONException e){
                obj = new ObjectMapper().readValue(new JSONArray(data).toString(), classOfParameter);
            }

        }else {
            obj = convertStringToObject(data, classOfParameter);
        }

        method.invoke(declarationClassOfMethod.getConstructor().newInstance(), obj);

    }

    private Object convertStringToObject(String data, Class clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(data, clazz);
    }
}

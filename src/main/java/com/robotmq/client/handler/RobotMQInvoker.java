package com.robotmq.client.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RobotMQInvoker {

    private static RobotMQInvoker INSTANCE = new RobotMQInvoker();

    public static RobotMQInvoker getINSTANCE() {
        return INSTANCE;
    }

    protected synchronized void invokeMethod(Method method, String data) throws JsonProcessingException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        Class<?> classOfParameter = Arrays.stream(method.getParameterTypes()).findFirst().orElseThrow(IllegalArgumentException::new);
        Class<?> declarationClassOfMethod = Class.forName(method.getDeclaringClass().getName());
        Object obj = convertStringToObject(data, classOfParameter);
        method.invoke(declarationClassOfMethod.getConstructor().newInstance(), obj);

    }

    private Object convertStringToObject(String data, Class clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(data, clazz);
    }
}

package com.robotmq.client.common.produce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotmq.client.common.CommonVars;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Optional;

@Component
public class RobotMQInvoker {

    public synchronized void invokeMethod(Method method,String data) throws JsonProcessingException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        Class<?> clazz = Arrays.stream(method.getParameterTypes()).findFirst().orElseThrow(() -> new IllegalArgumentException());
        Class<?> c = Class.forName(method.getDeclaringClass().getName());
        Object obj = convertStringToObject(data,clazz);
        method.invoke(c.getConstructor().newInstance(),obj);

    }


    private Object convertStringToObject(String data,Class clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(data, clazz);
    }
}

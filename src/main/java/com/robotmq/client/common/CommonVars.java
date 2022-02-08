package com.robotmq.client.common;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class CommonVars {


    public static volatile String PACKAGE_NAME ;
    public static volatile Map<String, Set<String>> methodsAndTopicsMap = new ConcurrentHashMap<>();
    public static volatile List<String> WILL_CONSUME_TOPICS = new LinkedList<>();

    public static volatile BlockingQueue<String> OUTTA_QUEUE_TO_BROKER = new LinkedBlockingQueue<>();
    public static volatile BlockingQueue<String> WILL_INVOKE_QUEUE = new LinkedBlockingQueue<>();



    private final static CommonVars INSTANCE = new CommonVars();

    private CommonVars(){}

    public static CommonVars getINSTANCE() {
        return INSTANCE;
    }



}

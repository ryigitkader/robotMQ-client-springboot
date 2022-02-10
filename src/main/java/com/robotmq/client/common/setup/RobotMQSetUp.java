package com.robotmq.client.common.setup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.robotmq.client.annotation.RobotMQConnection;
import com.robotmq.client.annotation.RobotMQListener;
import com.robotmq.client.exception.RobotMQConnectionParametersNotFoundException;
import com.robotmq.client.exception.RobotMQException;
import com.robotmq.client.exception.RobotMQNotFoundWillConsumeTopicsException;
import com.robotmq.client.glob.CommonVars;

import com.robotmq.client.handler.RobotMQProducer;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.reflect.Method;
import java.util.*;

import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author yigitkader
 */
public class RobotMQSetUp {


    private final Logger logger = Logger.getLogger(RobotMQSetUp.class.getName());

    private final static RobotMQSetUp INSTANCE = new RobotMQSetUp();

    private RobotMQConsumeTopicsProducer topicsProducer = RobotMQConsumeTopicsProducer.getINSTANCE();

    //RobotMQProducer producer = new RobotMQProducer();


    private RobotMQSetUp(){}

    public static RobotMQSetUp getINSTANCE() {
        return INSTANCE;
    }


    public void setUp() {
        try {
            setUpConnectionParameters();
            setUpWillConsumeTopics();
            topicsProducer.produce(CommonVars.WILL_CONSUME_TOPICS);
        }catch (RobotMQConnectionParametersNotFoundException e){
            logger.severe(e.getMessage());
            throw e;
        }catch (RobotMQNotFoundWillConsumeTopicsException e){
            logger.info(e.getMessage());
            throw e;
        }catch (RobotMQException e){
            logger.severe(e.getMessage());
        }catch (Exception e){
            logger.severe(e.toString());
        }
    }



    private void setUpConnectionParameters(){
        logger.info("[?] Starting To Find RobotMQ Connections Paramaters ..");

        Reflections ref =new Reflections(CommonVars.PACKAGE_NAME,new MethodAnnotationsScanner());
        Set<Method> methodsAnnotatedWith = Collections.singleton(ref.getMethodsAnnotatedWith(RobotMQConnection.class)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RobotMQConnectionParametersNotFoundException("ROBOTMQConnection Can Not Found! Parameters NULL or EMPTY. Please Check Parameters")));

        methodsAnnotatedWith.forEach(o -> {
            RobotMQConnection notation = o.getAnnotation(RobotMQConnection.class);
            String url = notation.url();
            String port = notation.port();
            logger.info("[+] RobotMQ Connection Parameters => url : "+url+" - port : "+port);

            if (!RobotMQConnectionParams.buildConnectionParameters(url,port) ){
                throw new RobotMQConnectionParametersNotFoundException("ROBOTMQConnection Can Not Found! Parameters NULL or EMPTY. Please Check Parameters");
            }

            logger.info("[+] RobotMQ Connection Paramaters Built");
        });
    }


    private void setUpWillConsumeTopics(){
        logger.info("[+] Starting To Find Topics For Consume ..");
        try {
            Reflections ref =new Reflections(CommonVars.PACKAGE_NAME,new MethodAnnotationsScanner());
            Set<Method> methodsAnnotatedWith = ref.getMethodsAnnotatedWith(RobotMQListener.class);
            methodsAnnotatedWith.forEach(o -> {
                final String methodName = o.getName();
                RobotMQListener notation = o.getAnnotation(RobotMQListener.class);
                final Set<String> setList = new HashSet<>(Arrays.asList(notation.topics()[0].split("\\s*,\\s*")));
                CommonVars.methodsAndTopicsMap.put(o,setList);
                CommonVars.WILL_CONSUME_TOPICS.addAll(setList);
            });
        }catch (Exception e){
            throw new RobotMQNotFoundWillConsumeTopicsException("Couldnt Find Topics For Will Consume");
        }
        logger.info("[+] Found Topics For Consume !");
    }



    @Deprecated
    private void setBasePackageName(){
    /* logger.info("[?] Starting To Find Base Package ..");
        Map<String, Object> annotatedBeans = context.getBeansWithAnnotation(SpringBootApplication.class);

        CommonVars.PACKAGE_NAME = annotatedBeans.isEmpty() ? null : annotatedBeans.values().toArray()[0].getClass().getPackageName();

        if (CommonVars.PACKAGE_NAME==null){
            logger.warning("[!] Base Package Can Not Found !");
            CommonVars.PACKAGE_NAME =  MethodHandles.lookup().lookupClass().getPackageName();
            /// todo : Can be stop here. Not urgent
        }else {
            logger.info("[+] Base Package Found !");
        }*/
    }

}

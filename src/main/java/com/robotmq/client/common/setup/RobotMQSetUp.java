package com.robotmq.client.common.setup;

import com.robotmq.client.annotation.RobotMQConnection;
import com.robotmq.client.annotation.RobotMQListener;
import com.robotmq.client.common.CommonVars;
import com.robotmq.client.common.RobotMQConnectionParams;
import com.robotmq.client.common.produce.RobotMQTemplate;
import com.robotmq.client.exception.RobotMQConnectionParametersNotFoundException;
import com.robotmq.client.exception.RobotMQException;
import com.robotmq.client.exception.RobotMQNotFoundWillConsumeTopicsException;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@Qualifier("robotMQSetup")
public class RobotMQSetUp {

    Logger logger = Logger.getLogger(RobotMQSetUp.class.getName());

    @Autowired
    private ApplicationContext context;

    @Autowired
    private RobotMQProducer producer;

    public void setUp() {
        try {
            setBasePackageName();
            setUpConnectionParameters();
            setUpWillConsumeTopics();

            producer.produce(CommonVars.WILL_CONSUME_TOPICS);
            producer.produce("tekTopic","TekTopicDataa");
            producer.produce("topic1","Topic1 Dataa");
            producer.produce("xxxxx","TekTopicDataa");

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


    private void setBasePackageName(){
        logger.info("[?] Starting To Find Base Package ..");
        Map<String, Object> annotatedBeans = context.getBeansWithAnnotation(SpringBootApplication.class);

        CommonVars.PACKAGE_NAME = annotatedBeans.isEmpty() ? null : annotatedBeans.values().toArray()[0].getClass().getPackageName();

        if (CommonVars.PACKAGE_NAME==null){
            logger.warning("[!] Base Package Can Not Found !");
            CommonVars.PACKAGE_NAME =  MethodHandles.lookup().lookupClass().getPackageName();
            /// todo : Can be stop here. Not urgent
        }else {
            logger.info("[+] Base Package Found !");
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
                final Set<String> setList = Arrays.stream(notation.topics()).collect(Collectors.toSet());

                CommonVars.methodsAndTopicsMap.put(methodName,setList);
                CommonVars.WILL_CONSUME_TOPICS.addAll(setList);
            });
        }catch (Exception e){
            throw new RobotMQNotFoundWillConsumeTopicsException("Couldnt Find Topics For Will Consume");
        }
        logger.info("[+] Found Topics For Consume !");
    }

}

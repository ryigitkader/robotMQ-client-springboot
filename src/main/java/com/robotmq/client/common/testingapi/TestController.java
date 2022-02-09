package com.robotmq.client.common.testingapi;

import com.robotmq.client.common.setup.RobotMQProducer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class TestController {

    private final RobotMQProducer producer = RobotMQProducer.getINSTANCE();

    @PostMapping
    public String produce(@RequestBody Body body) throws InterruptedException, IOException {
        producer.produce(body.getTopic(),body.getData());
        return "Produced";
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Body{
    private String topic;
    private String data;
}


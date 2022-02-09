package com.robotmq.client.common.setup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private RobotMQProducer producer;

    @PostMapping
    public String produce(@RequestBody Body body) throws InterruptedException, IOException {
        producer.produce(body.getTopic(),body.getData());
        return "Sended";
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Body{
    private String topic;
    private String data;
}

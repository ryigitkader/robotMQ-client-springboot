package com.robotmq.client.engine.handler.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDataVO {
    private String topic;
    private String data;
}

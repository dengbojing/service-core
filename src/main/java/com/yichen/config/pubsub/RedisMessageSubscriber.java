package com.yichen.config.pubsub;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengbojing
 */
@Getter
@Setter
public class RedisMessageSubscriber implements MessageListener {

    private static List<String> messageList = new ArrayList<>();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        messageList.add(message.toString());
        System.out.println("Message received: " + message.toString());
    }
}

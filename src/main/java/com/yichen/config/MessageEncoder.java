package com.yichen.config;

import com.alibaba.fastjson.JSON;
import com.yichen.major.entity.Message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author dengbojing
 */
public class MessageEncoder implements Encoder.Text<Message> {
    @Override
    public String encode(Message m) throws EncodeException {
        return JSON.toJSONString(m);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}

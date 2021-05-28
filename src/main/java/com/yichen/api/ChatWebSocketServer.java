package com.yichen.api;

import com.alibaba.fastjson.JSON;
import com.yichen.config.MessageDecoder;
import com.yichen.config.MessageEncoder;
import com.yichen.major.entity.Message;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author dengbojing
 */
//@ServerEndpoint(value = "/chat/{username}", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
//@Component
public class ChatWebSocketServer {
    private Session session;
    private static final Set<ChatWebSocketServer> CHAT_WEB_SOCKET_SERVERS = new CopyOnWriteArraySet<>();
    private static final HashMap<String,String> USERS = new HashMap<>();

    @OnOpen
    public void onOpen(@PathParam("username") String userName, Session session){
        this.session = session;
        CHAT_WEB_SOCKET_SERVERS.add(this);
        USERS.put(session.getId(), userName);
        Message message = new Message();
        message.setFrom(userName);
        message.setContent("connected");
        boardCast(message);
    }

    @OnMessage
    public void onMessage(Session session, Message message){
        message.setFrom(USERS.get(session.getId()));
        System.out.println(JSON.toJSON(message));
        boardCast(message);
    }

    @OnClose
    public void onClose(Session session){
        CHAT_WEB_SOCKET_SERVERS.remove(this);
        Message message = new Message();
        message.setFrom(USERS.get(session.getId()));
        message.setContent("disconnected");
        boardCast(message);


    }

    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println(throwable);
    }

    private static void boardCast(Message m){
       CHAT_WEB_SOCKET_SERVERS.forEach(chatEndpoint -> {
            synchronized (chatEndpoint){
                try {
                    chatEndpoint.session.getBasicRemote().sendObject(m);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

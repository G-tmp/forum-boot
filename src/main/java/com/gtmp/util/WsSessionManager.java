package com.gtmp.util;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class WsSessionManager {
    private static ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();


    public static void add(String key, WebSocketSession session) {
        SESSION_POOL.put(key, session);
    }


//    public static WebSocketSession remove(String key) {
//        return SESSION_POOL.remove(key);
//    }


    public static void remove(String key) {
        WebSocketSession session = SESSION_POOL.remove(key);
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
                // todo: exception handling during shutdown
                e.printStackTrace();
            }
        }
    }


    public static WebSocketSession get(String key) {
        return SESSION_POOL.get(key);
    }


    public static ConcurrentHashMap<String, WebSocketSession> list(){
        return SESSION_POOL;
    }


    public static void sendMessage(String key, String msg)  {
        if (get(key).isOpen()) {
            try {
                get(key).sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
package com.gtmp.util;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class WsSessionManager {
    private static ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * Add session
     *
     * @param key
     */
    public static void add(String key, WebSocketSession session) {
        // Add session
        SESSION_POOL.put(key, session);
    }

    /**
     * Deleting a session will return the deleted session
     *
     * @param key
     * @return
     */
    public static WebSocketSession remove(String key) {
        // Delete session
        return SESSION_POOL.remove(key);
    }

    /**
     * Delete and sync close connection
     *
     * @param key
     */
    public static void removeAndClose(String key) {
        WebSocketSession session = remove(key);
        if (session != null) {
            try {
                // Close connection
                session.close();
            } catch (IOException e) {
                // todo: exception handling during shutdown
                e.printStackTrace();
            }
        }
    }

    /**
     * Get session
     *
     * @param key
     * @return
     */
    public static WebSocketSession get(String key) {
        // Get session
        return SESSION_POOL.get(key);
    }


    public static ConcurrentHashMap<String, WebSocketSession> list(){
        return SESSION_POOL;
    }

}

package com.gtmp.config;

import com.gtmp.util.WsSessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class WebSocketTextHandler extends TextWebSocketHandler {

    // send message event
//    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
//        String payload = message.getPayload();
//        String jsessionId = (String) session.getAttributes().get("jsessionId");
//        System.out.println( jsessionId + " Says " + payload);
//        session.sendMessage(new TextMessage("server response: " + jsessionId + " news " + payload + " " + LocalDateTime.now().toString()));
//    }


    // onopen event
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String jsessionId = (String) session.getAttributes().get("JSESSIONID");

        if (jsessionId != null) {
            // The user is connected successfully and put into the online user cache
            if (WsSessionManager.get(jsessionId) == null){
                WsSessionManager.add(jsessionId, session);
            }
        } else {
            throw new RuntimeException("User login has expired!");
        }
    }

    // onclose event
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("close: " + session);
        String jsessionId = (String) session.getAttributes().get("JSESSIONID");
        if (jsessionId != null) {
            // User exit, remove cache
            WsSessionManager.remove(jsessionId);
        }
    }

}

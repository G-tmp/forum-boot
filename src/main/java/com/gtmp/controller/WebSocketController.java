package com.gtmp.controller;


import com.gtmp.util.WsSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;

@Controller
public class WebSocketController {

    @ResponseBody
    @RequestMapping(value = "send", method = RequestMethod.GET)
    public void send(@RequestParam String msg){
        Collection<WebSocketSession> sessions = WsSessionManager.list().values();
        try {
            for (WebSocketSession session:sessions){
                if (session.isOpen())
                    session.sendMessage(new TextMessage(msg));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

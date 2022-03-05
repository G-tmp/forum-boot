package com.gtmp.interceptor;

import com.gtmp.util.WsSessionManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("wsManager: "+ WsSessionManager.list());

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerRequest = (ServletServerHttpRequest) request;
            HttpServletRequest servletRequest = servletServerRequest.getServletRequest();
            Cookie cookie = WebUtils.getCookie(servletRequest, "JSESSIONID");
            Session session = SecurityUtils.getSubject().getSession();

            if (cookie != null && cookie.getValue().equals(session.getId())) {
                System.out.println("cookie: "+cookie.getValue());
                System.out.println("session: "+session.getId());
                attributes.put("JSESSIONID", cookie.getValue());
                return true;
            }
        }

        return false;
    }


    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("Shake hands success");
    }

}

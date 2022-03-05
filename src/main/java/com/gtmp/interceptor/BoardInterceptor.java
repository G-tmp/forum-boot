package com.gtmp.interceptor;

import com.gtmp.POJO.Board;
import com.gtmp.service.BoardServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 *  拦截所有请求，获取所有 board
 *  加载到 navbar.html
 */
@Component
public class BoardInterceptor implements HandlerInterceptor {

    @Autowired
    private BoardServer boardServer;

    private Logger log = LoggerFactory.getLogger(BoardInterceptor.class);



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        List<Board> boards = boardServer.listAllBoard();
        log.info("select * boards");
        if (boards != null && modelAndView != null) {
            modelAndView.addObject("boards", boards);
        }
    }
}

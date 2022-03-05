package com.gtmp.controller.advice;

import com.gtmp.util.ForumUtil;
import com.gtmp.util.JsonRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *  统一处理异常
 */
@ControllerAdvice(annotations = Controller.class) //只对标注Controller注解的类进行处理
public class GlobalExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);


    @ExceptionHandler({Exception.class}) //拦截的异常类型
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常: {}", e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            logger.error(element.toString());
        }

        if (isAjax(request)){
            response.setContentType("application/json; charset=utf-8");
            ServletOutputStream out = response.getOutputStream();
//            PrintWriter out = response.getWriter();
//            out.write(jsonRes.toString());
            JsonRes jsonRes = new JsonRes().setCode(JsonRes.ERROR_CODE).setMsg("服务器异常!");
            out.write(jsonRes.toString().getBytes());
//            ServletOutputStream outputStream = response.getOutputStream();
//            outputStream.write(ForumUtil.getJSONString(1, "服务器异常!").getBytes());
        }else {
            response.sendRedirect(ForumUtil.contextPathJudge(request.getContextPath()) + "/error");
        }

    }


    private boolean isAjax(HttpServletRequest request){
        String header = request.getHeader("x-requested-with");
        if ("XMLHttpRequest".equalsIgnoreCase(header))
            return true;

        return false;
    }
}
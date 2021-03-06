package com.gtmp.filter;

import com.gtmp.util.JsonRes;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ShiroAuthenticateFilter extends AuthenticationFilter {



    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o)  {
        Subject subject = this.getSubject(request, response);

        if(subject != null){
            if(subject.isRemembered()){
                return true;
            }

            if(subject.isAuthenticated()){
                return true;
            }
        }

        return false ;
    }


    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        JsonRes res = new JsonRes();
        res.setCode(JsonRes.ERROR_CODE).setMsg("please login");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json");
        response.getOutputStream().write(res.toJson().getBytes());
        return false ;
    }
}

package com.gtmp.filter;

import com.gtmp.util.JsonRes;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ShiroAuthorizeRolesFilter extends RolesAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        System.out.println("authorize role filter ");

        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[])((String[])mappedValue);
        System.out.println(Arrays.toString(rolesArray));

        if (rolesArray==null || rolesArray.length == 0) {
            return true;
        }

        for (String role:rolesArray){
            if (subject.hasRole(role))
                return true;
        }

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        JsonRes res = new JsonRes();
        res.setCode(JsonRes.ERROR_CODE).setMsg("Permission denied");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json");
        response.getOutputStream().write(res.toJson().getBytes());
        return false ;
    }
}

package com.gtmp.filter;

import com.gtmp.util.JsonRes;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ShiroAuthorizePermsFilter extends PermissionsAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);

        String[] permsArray = (String[])((String[])mappedValue);
        System.out.println(Arrays.toString(permsArray));

        if (permsArray==null || permsArray.length == 0) {
            return true;
        }

        for (String perm:permsArray){
            if (subject.isPermitted(perm))
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

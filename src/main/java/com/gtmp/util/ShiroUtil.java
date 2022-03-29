package com.gtmp.util;


import com.gtmp.POJO.User;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Component
public class ShiroUtil {

    private static SessionDAO sessionDAO;

    @Autowired
    private SessionDAO sessionDAO1;

    @PostConstruct
    public void init() {
        sessionDAO = sessionDAO1;
    }


    // from user object get shiro sessionId if the user is active
    public static String getActiveSessionId(User user){

        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        for (Session session:activeSessions){
            Object principal = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principal;
            User u = (User) principalCollection.getPrimaryPrincipal();
            if (u.equals(user)){
                return session.getId().toString();
            }
        }

        return null;
    }

}

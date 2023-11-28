package org.stock.portal.web.listeners;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;


public class SessionListener implements HttpSessionListener {

    private Logger logger = Logger.getLogger(SessionListener.class.getName());
       
    
    public SessionListener() {
        super();
    }
    
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        logger.debug("start session id : " + sessionEvent.getSession().getId());
    }
    
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        //User user = (User) session.getAttribute(SPConstants.LOGGED_IN_USER);
        logger.debug("end session id : " + sessionEvent.getSession().getId());
    }
}

package org.stock.portal.web.action;



import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.stock.portal.domain.User;
import org.stock.portal.web.util.Constants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public abstract class BaseAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = -2940028137931398347L;
	
    protected String retVal = SUCCESS;
    protected String FWD_ERROR = "generic-error";
    private static final Logger log = Logger.getLogger(BaseAction.class.getName());
    // Those two variables are used by the close-popup.ftl in order to know if a particular part of the screen
    // needs to be refreshed after the popup closes. This is useful to udpate messages lists upon a new message reception.
    private String reloadURL;
    private String reloadLocation;
    private boolean reloadCounters;
    protected Map<String, Object> session;
    
    public BaseAction() {
        super();
    }

    public String getRequest(String param) {
    	log.debug("getRequest : "+param);
        return ServletActionContext.getRequest().getParameter(param);
    }

    @SuppressWarnings("unchecked")
	public User getLoggedInUser() {
    	log.debug("getLoggedInUser : ");
        Map session = ActionContext.getContext().getSession();

        return ((User)session.get("User"));
    }
    
    @SuppressWarnings("unchecked")
	public void invalidateSession() {
        Map session = ActionContext.getContext().getSession();
        session.put(Constants.LOGGED_IN_USER, null);
        session.clear();
        if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
            try {
                ((org.apache.struts2.dispatcher.SessionMap)session).invalidate();
            } catch (IllegalStateException e) {
                log.error(e, e);
            }
        }
    }

    public String getReloadURL() {
        return reloadURL;
    }

    public void setReloadURL(String reloadURL) {
        this.reloadURL = reloadURL;
    }

    public String getReloadLocation() {
        return reloadLocation;
    }

    public void setReloadLocation(String reloadLocation) {
        this.reloadLocation = reloadLocation;
    }

    public boolean isReloadCounters() {
        return reloadCounters;
    }

    public void setReloadCounters(boolean reloadCounters) {
        this.reloadCounters = reloadCounters;
    }
    
    @Override
	public void setSession(Map<String, Object> session) {
		this.session = session;		
	}
	
	public Map<String, Object> getSession() {
		return this.session;		
	}
}

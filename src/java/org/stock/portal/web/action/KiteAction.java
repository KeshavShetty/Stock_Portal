package org.stock.portal.web.action;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.stock.portal.common.StringUtil;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.User;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class KiteAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(KiteAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
    
    private String request_token = "";
    private String clientId = "";
   
    /** The user accessor bean. */
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
   
    public KiteAction() {
        super();
    }

    public String execute() {
    	log.debug("--->Inside LoginAction [JR]");
        // TODO: Benchmark this call - if it is not too expensive we should move it to the BaseAction.
        //Map session = ActionContext.getContext().getSession();
        String retVal = INPUT;
        User account;
        try {
        	log.debug("request_token : "+getRequest_token()); 
        	log.debug("ClientId : "+ getClientId());
        	dataManager.saveKiteRequestToken(getClientId(), getRequest_token());
        	retVal = SUCCESS;
            
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        return retVal;
    }

	public String getRequest_token() {
		return request_token;
	}

	public void setRequest_token(String request_token) {
		this.request_token = request_token;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}

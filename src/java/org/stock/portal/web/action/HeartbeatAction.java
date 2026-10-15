package org.stock.portal.web.action;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.User;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.web.annotation.InjectEJB;


public class HeartbeatAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(HeartbeatAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
   
    /** The user accessor bean. */
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
   
    public HeartbeatAction() {
        super();
    }

    public String execute() {
    	log.debug("--->Inside LoginAction [JR]");
        // TODO: Benchmark this call - if it is not too expensive we should move it to the BaseAction.
        //Map session = ActionContext.getContext().getSession();
        String retVal = ERROR;
        try {        	
        	log.debug("Reached  HeartbeatAction ");
        	
        	boolean isEverythingFine = dataManager.getServerAndTickerDataStatus("NIFTY");
        	if (isEverythingFine) {
        		retVal = SUCCESS;
        	}
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        return retVal;
    }
}

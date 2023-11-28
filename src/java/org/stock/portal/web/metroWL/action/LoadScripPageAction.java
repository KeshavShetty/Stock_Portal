package org.stock.portal.web.metroWL.action;

import org.apache.log4j.Logger;
import org.stock.portal.web.action.BaseAction;

public class LoadScripPageAction extends BaseAction {
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(LoadScripPageAction.class.getName());
        
	public String execute(){
		String returnPage = SUCCESS;
		// Yet to add other Logic
		
		log.info("In LoadScripPageAction execute() returnPage="+returnPage);
		return returnPage;
	}
}

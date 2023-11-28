package org.stock.portal.web.metroWL.action;

import org.apache.log4j.Logger;
import org.stock.portal.web.action.BaseAction;

public class LoadMainMenuAction extends BaseAction{
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(LoadMainMenuAction.class.getName());
        
	public String loadMainMenu(){
		String returnPage = "showMainMenu";
		// Yet to add other Logic
		
		log.info("In LoadMainMenuAction loadMainMenu() returnPage="+returnPage);
		return returnPage;
	}
}

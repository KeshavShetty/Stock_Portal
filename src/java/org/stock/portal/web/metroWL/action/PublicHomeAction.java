package org.stock.portal.web.metroWL.action;

import org.apache.log4j.Logger;
import org.stock.portal.web.action.BaseAction;

public class PublicHomeAction extends BaseAction{
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(PublicHomeAction.class.getName());
        
	public String loadPublicHome(){
		String returnPage = "publicHome";
		// Yet to add other Logic
		
		log.info("In PublicHomeAction loadPublicHome() returnPage="+returnPage);
		return returnPage;
	}
}

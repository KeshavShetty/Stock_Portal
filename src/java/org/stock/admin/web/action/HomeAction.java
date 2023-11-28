package org.stock.admin.web.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.stock.portal.web.util.Constants;

public class HomeAction extends BaseAction {
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(HomeAction.class.getName());
        
	public String viewHome(){
		String returnPage = "defaultHome";
		
		if(this.session.get(Constants.LOGGED_IN_USER)!=null) {
			ServletActionContext.getRequest().setAttribute("pageToDisplay","DashboardPage");			
		} else {
			ServletActionContext.getRequest().setAttribute("pageToDisplay","PublicHomePage");
		}
		log.info("In HomeAction viewHome() returnPage="+returnPage);
		return returnPage;
	}
}

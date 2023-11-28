package org.stock.portal.web.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.stock.portal.domain.User;
import org.stock.portal.service.AccountsManager;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;

public class HomeAction extends BaseAction{
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(HomeAction.class.getName());
       
	/** The user accessor bean. */
    @InjectEJB (name ="AccountsManager")
    AccountsManager accountManager;
    
	public String viewHome(){
		String returnPage = "defaultHome";
		
		if(this.session.get(Constants.LOGGED_IN_USER)!=null) {
			ServletActionContext.getRequest().setAttribute("pageToDisplay","DashboardPage");			
		} else {
			String originIp = ServletActionContext.getRequest().getRemoteAddr();
			System.out.println("originIp="+originIp);
			try {
				User account = accountManager.authenticateAccount("keshav", "98ef8709c6e2657b12e7069dddd3b672");
				this.session.put(Constants.LOGGED_IN_USER, account);
				ServletActionContext.getRequest().setAttribute("pageToDisplay","DashboardPage");
			} catch(Exception ex) {
				ServletActionContext.getRequest().setAttribute("pageToDisplay","PublicHomePage");
			}			
		}
		log.info("In HomeAction viewHome() returnPage="+returnPage);
		return returnPage;
	}
}

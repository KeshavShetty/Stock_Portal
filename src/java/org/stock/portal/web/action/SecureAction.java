package org.stock.portal.web.action;

import org.stock.portal.domain.User;
import org.stock.portal.web.interceptor.UserAware;


public abstract class SecureAction extends BaseAction implements UserAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4385203278990247816L;

	protected User loggedInUser;

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
		
	}
	
	public User getLoggedInUser( ) {
		return this.loggedInUser;		
	}
}

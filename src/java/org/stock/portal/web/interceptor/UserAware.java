package org.stock.portal.web.interceptor;

import org.stock.portal.domain.User;

public interface UserAware {
	
	/**
	 * inject a user to the action
	 * 
	 * @param user
	 */
	public void setLoggedInUser( User loggedInUser );
}

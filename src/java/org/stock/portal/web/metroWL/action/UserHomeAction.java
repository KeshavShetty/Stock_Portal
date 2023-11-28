package org.stock.portal.web.metroWL.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.service.AccountsManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;

public class UserHomeAction extends BaseAction{
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(UserHomeAction.class.getName());
	
	private List<Watchlist> userWLs;
	
	@InjectEJB (name ="WatchlistManager")
    WatchlistManager watchlistManager;

	/** The user accessor bean. */
    @InjectEJB (name ="AccountsManager")
    AccountsManager accountManager;
    
	public List<Watchlist> getUserWLs() {
		return userWLs;
	}

	public void setUserWLs(List<Watchlist> userWLs) {
		this.userWLs = userWLs;
	}

	public String loadUserHome(){
		String returnPage = "userHome";
		// Yet to add other Logic
		try {
			User account = (User)this.session.get(Constants.LOGGED_IN_USER);
//			if (account==null) {
//				account = accountManager.authenticateAccount("keshav", "98ef8709c6e2657b12e7069dddd3b672");
//				this.session.put(Constants.LOGGED_IN_USER, account);
//			}
			
			userWLs = watchlistManager.getWatchlistByUserId(account.getId());			
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
		log.info("In UserHomeAction loadUserHome() returnPage="+returnPage);
		return returnPage;
	}
}

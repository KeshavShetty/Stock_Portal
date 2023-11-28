package org.stock.portal.web.metroWL.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class ManualWatchlistLoaderAction extends BaseAction {

	Logger log = Logger.getLogger(ManualWatchlistLoaderAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	    
    private List<Watchlist> watchlist;
    
    @InjectEJB (name ="WatchlistManager")
    WatchlistManager watchlistManager;
   
    public ManualWatchlistLoaderAction() {
        super();
    }
    
    public String getWatchlists() {
    	try {    		
    		String scripId = getScripIdFromRequest();
    		System.out.println("In ScripInfoAction prepareLoad scripId="+scripId);
    		
    		User loggedUser = (User)this.session.get(Constants.LOGGED_IN_USER);
    		watchlist = watchlistManager.getWatchlistItemByWlIdAndScripIdForManual(loggedUser.getId(), Long.parseLong(scripId));
	    	
    	} catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.scrip.info"));
        } 
    	return SUCCESS;
    }
    
    private String getScripIdFromRequest() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String scripId = request.getParameter("scripId");
		if (scripId==null || scripId.length()==0) { // Try from jqIndex
			String jqIndex = request.getParameter("jqIndex");
			if (jqIndex!=null) {
				if (jqIndex.indexOf("_")>=0) scripId = jqIndex.substring(0, jqIndex.indexOf("_"));
				else scripId = jqIndex;
			}
		}
		if (scripId==null || scripId.length()==0) { // Try from id
			scripId = request.getParameter("id");
		}
		return scripId;
    }

	public void setWatchlist(List<Watchlist> watchlist) {
		this.watchlist = watchlist;
	}

	public List<Watchlist> getWatchlist() {
		return watchlist;
	}
}

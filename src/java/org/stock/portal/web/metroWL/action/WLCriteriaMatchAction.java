package org.stock.portal.web.metroWL.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.stock.portal.domain.IntradaySnapshotData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.domain.dto.WLCriteriaMatchDto;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;

public class WLCriteriaMatchAction extends BaseAction {
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(WLCriteriaMatchAction.class.getName());
    
	private String tickerSymbol =  "BSE-BSE30";
	
	private List<Watchlist> userVirtualWatchlist = new ArrayList<Watchlist>();
	
	private Long selectedWatchlist = null;
	
	private List<WLCriteriaMatchDto> criteriaStatuslist = new ArrayList<WLCriteriaMatchDto>();
	
	
	@InjectEJB (name ="WatchlistManager")
	WatchlistManager watchlistManager;
	
	@InjectEJB (name ="ScripManager")
    ScripManager scripManager;
	
	public String loadViewPage(){
		String returnPage = "showPage";
		try {
			String scripId = getScripIdFromRequest();
			Scrip scrip = null;
			if (scripId!=null) {
				scrip = scripManager.getScripById(Long.parseLong(scripId));
			} else {
				scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
			}
	    	
	    	if (scrip!=null) {
	    		if (scrip.getNseCode()!=null) {
	    			tickerSymbol = "NSE-" + scrip.getNseCode();
	    		} else if (scrip.getBseName()!=null) {
	    			tickerSymbol = "BSE-" + scrip.getBseName();
	    		} else if (scrip.getBseCode()!=null) {
	    			tickerSymbol = "BSE-" + scrip.getBseCode();
	    		}
	    	}
	    	User loggedUser  = (User)this.session.get(Constants.LOGGED_IN_USER);
	    	userVirtualWatchlist = watchlistManager.getWatchlistByUserIdAndType(loggedUser.getId(), "VIRTUAL");
	    	if (userVirtualWatchlist!=null && userVirtualWatchlist.size()>0) {
	    		selectedWatchlist = userVirtualWatchlist.get(0).getId();
	    	}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return returnPage;
	}

	public String findWLMatch() {
		String returnPage = "showAnalysis";
		try {
			Scrip scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
			criteriaStatuslist = watchlistManager.matchScripToWLCriteria(scrip.getId(), selectedWatchlist);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return returnPage;
	}
	
	public String getTickerSymbol() {
		return tickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
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

	public List<Watchlist> getUserVirtualWatchlist() {
		return userVirtualWatchlist;
	}

	public void setUserVirtualWatchlist(List<Watchlist> userVirtualWatchlist) {
		this.userVirtualWatchlist = userVirtualWatchlist;
	}

	public List<WLCriteriaMatchDto> getCriteriaStatuslist() {
		return criteriaStatuslist;
	}

	public void setCriteriaStatuslist(List<WLCriteriaMatchDto> criteriaStatuslist) {
		this.criteriaStatuslist = criteriaStatuslist;
	}

	public Long getSelectedWatchlist() {
		return selectedWatchlist;
	}

	public void setSelectedWatchlist(Long selectedWatchlist) {
		this.selectedWatchlist = selectedWatchlist;
	}
}

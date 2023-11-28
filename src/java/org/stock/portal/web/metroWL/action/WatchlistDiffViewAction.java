package org.stock.portal.web.metroWL.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.EodData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class WatchlistDiffViewAction extends BaseAction {

	Logger log = Logger.getLogger(WatchlistDiffViewAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String leftWLDate; 
    private String rightWLDate; 
    
    private List<Watchlist> userWatchlists;

    private Long leftWatchlist;  
    private Long rightWatchlist;  

    
    private String sourceFormName;
    private String divToFill;
    
    private List<Scrip> leftWatchlistScrips;
    private List<Scrip> commonScrips;
    private List<Scrip> rightWatchlistScrips;
    
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
      
    @InjectEJB (name ="WatchlistManager")
    WatchlistManager watchlistManager;
    
    public WatchlistDiffViewAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	try {
    		User account = (User)this.session.get(Constants.LOGGED_IN_USER);
    		if (account!=null) {
    			userWatchlists = watchlistManager.getWatchlistByUserId(account.getId());
    		} else {
    			userWatchlists = new ArrayList<Watchlist>();
    		}
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
    }

    public String search() {    	
        String retVal = "resultPage";
        try {
        	leftWatchlistScrips = watchlistManager.getScripsByWatchlistId(leftWatchlist, leftWLDate);
        	rightWatchlistScrips = watchlistManager.getScripsByWatchlistId(rightWatchlist, rightWLDate);
        	
        	commonScrips = new ArrayList<Scrip>();
        	
        	for(int i=leftWatchlistScrips.size()-1;i>=0;i--) {
        		Scrip leftScrip = leftWatchlistScrips.get(i);
        		for(int j=0;j<rightWatchlistScrips.size();j++) {
        			Scrip rightScrip = rightWatchlistScrips.get(j);
        			if (leftScrip.getId().equals(rightScrip.getId())) {
        				commonScrips.add(0,leftScrip);
        				rightWatchlistScrips.remove(j);
        				leftWatchlistScrips.remove(i);
        				break;
        			}
        		}
        	}
        	
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        log.info("Returning "+retVal);
        return retVal;
    }

	public String getSourceFormName() {
		return sourceFormName;
	}

	public void setSourceFormName(String sourceFormName) {
		this.sourceFormName = sourceFormName;
	}

	public String getDivToFill() {
		return divToFill;
	}

	public void setDivToFill(String divToFill) {
		this.divToFill = divToFill;
	}

	public String getLeftWLDate() {
		return leftWLDate;
	}

	public void setLeftWLDate(String leftWLDate) {
		this.leftWLDate = leftWLDate;
	}

	public String getRightWLDate() {
		return rightWLDate;
	}

	public void setRightWLDate(String rightWLDate) {
		this.rightWLDate = rightWLDate;
	}

	public List<Watchlist> getUserWatchlists() {
		return userWatchlists;
	}

	public void setUserWatchlists(List<Watchlist> userWatchlists) {
		this.userWatchlists = userWatchlists;
	}

	public Long getLeftWatchlist() {
		return leftWatchlist;
	}

	public void setLeftWatchlist(Long leftWatchlist) {
		this.leftWatchlist = leftWatchlist;
	}

	public Long getRightWatchlist() {
		return rightWatchlist;
	}

	public void setRightWatchlist(Long rightWatchlist) {
		this.rightWatchlist = rightWatchlist;
	}

	public List<Scrip> getLeftWatchlistScrips() {
		return leftWatchlistScrips;
	}

	public void setLeftWatchlistScrips(List<Scrip> leftWatchlistScrips) {
		this.leftWatchlistScrips = leftWatchlistScrips;
	}

	public List<Scrip> getCommonScrips() {
		return commonScrips;
	}

	public void setCommonScrips(List<Scrip> commonScrips) {
		this.commonScrips = commonScrips;
	}

	public List<Scrip> getRightWatchlistScrips() {
		return rightWatchlistScrips;
	}

	public void setRightWatchlistScrips(List<Scrip> rightWatchlistScrips) {
		this.rightWatchlistScrips = rightWatchlistScrips;
	}

}

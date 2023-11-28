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
import org.stock.portal.domain.dto.ScripCompanyInfoDTO;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class IndexViewAction extends BaseAction {

	Logger log = Logger.getLogger(IndexViewAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String dataDate; 
    private String exchange;
    
    private String orderBy;
    private String orderType;
    
    private String sourceFormName;
    private String divToFill;
    
    private Long selectedIndexId;
    
    private List<ScripCompanyInfoDTO> resultList;
    
    private String freeTextSearchInput;
    
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
       
    @InjectEJB (name ="WatchlistManager")
    WatchlistManager watchlistManager;
    
    private List<Watchlist> userWatchlists;
    private Long selectedWatchlist;  
    
    public IndexViewAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	try {
    		this.exchange = "NSE";
    		this.orderBy = "name";
    		this.orderType = "ASC";
    		
    		Scrip scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
    		if (scrip!=null) {
    			if (scrip.getNseCode()!=null) this.freeTextSearchInput = "$"+ scrip.getNseCode();
    			else if (scrip.getBseCode()!=null) this.freeTextSearchInput = "$"+ scrip.getBseCode();
    		} else {
    			this.freeTextSearchInput = "Software";
    		}
    		
    		User account = (User)this.session.get(Constants.LOGGED_IN_USER);
    		if (account!=null) {
    			userWatchlists = watchlistManager.getWatchlistByUserId(account.getId());
    		} else {
    			userWatchlists = new ArrayList<Watchlist>();
    			userWatchlists.add(new Watchlist(1L, "BTST"));
    		}
    		
    		Calendar curDate = Calendar.getInstance();
    		curDate.add(Calendar.DATE, 1);
    		Date latestDate = dataManager.getMaxDataDate("BSE", curDate.getTime());
    		this.dataDate = SPConstants.SPCORE_DATE_FORMAT.format(latestDate);	
    		
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
    }

    public String search() {    	
        String retVal = "resultPage";
        try {
        	
        	Date selectedDate = null;
        	if (this.dataDate!=null && this.dataDate.trim().length()>0 ) {
        		selectedDate = SPConstants.SPCORE_DATE_FORMAT.parse(this.dataDate.trim());        	
        	} else {
        		Calendar cal = Calendar.getInstance();
        		cal.add(Calendar.MONTH, -1);
        		selectedDate =  cal.getTime();
        	}
        	System.out.println("this.orderBy="+this.orderBy + " this.orderType="+this.orderType);
        	        	
        	resultList = dataManager.getScripsByFreetextSearch(freeTextSearchInput.trim(), this.orderBy, this.orderType, this.selectedWatchlist);
        	System.out.println("resultList="+resultList.size());
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        log.info("Returning "+retVal);
        return retVal;
    }
    
    public String getIndexScrips() {    	
        String retVal = "scripsResultPage";
        try {
        	
        	Date selectedDate = null;
        	System.out.println("this.dataDate="+this.dataDate);
        	if (this.dataDate!=null && this.dataDate.trim().length()>0 ) {
        		if (this.dataDate.length()==8)selectedDate = SPConstants.SPCORE_SHORTYEAR_DATE_FORMAT.parse(this.dataDate.trim());
        		else selectedDate = SPConstants.SPCORE_DATE_FORMAT.parse(this.dataDate.trim());        	
        	} else {
        		Calendar cal = Calendar.getInstance();
        		cal.add(Calendar.MONTH, -1);
        		selectedDate =  cal.getTime();
        	}
        	resultList = dataManager.getScripsByFreetextSearch(freeTextSearchInput, this.orderBy, this.orderType, null);
        	System.out.println("resultList="+resultList.size());
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        log.info("Returning "+retVal);
        return retVal;
    }

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	
	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public List<ScripCompanyInfoDTO> getResultList() {
		return resultList;
	}

	public void setResultList(List<ScripCompanyInfoDTO> resultList) {
		this.resultList = resultList;
	}

	public Long getSelectedIndexId() {
		return selectedIndexId;
	}

	public void setSelectedIndexId(Long selectedIndexId) {
		this.selectedIndexId = selectedIndexId;
	}

	public String getFreeTextSearchInput() {
		return freeTextSearchInput;
	}

	public void setFreeTextSearchInput(String freeTextSearchInput) {
		this.freeTextSearchInput = freeTextSearchInput;
	}

	public List<Watchlist> getUserWatchlists() {
		return userWatchlists;
	}

	public void setUserWatchlists(List<Watchlist> userWatchlists) {
		this.userWatchlists = userWatchlists;
	}

	public Long getSelectedWatchlist() {
		return selectedWatchlist;
	}

	public void setSelectedWatchlist(Long selectedWatchlist) {
		this.selectedWatchlist = selectedWatchlist;
	}

}

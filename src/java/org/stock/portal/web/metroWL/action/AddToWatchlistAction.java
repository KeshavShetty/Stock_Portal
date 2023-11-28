package org.stock.portal.web.metroWL.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.common.ErrorCodeConstants;
import org.stock.portal.common.StringUtil;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class AddToWatchlistAction extends BaseAction {

	Logger log = Logger.getLogger(AddToWatchlistAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private Scrip selectedScrip;
    private List<Watchlist> userWatchlists;
    
    private Long scripId;
    private Long watchListId;
    private String watchlistName;
    private String description;
    
    private Boolean useExistingWatchList;
    
    
    private Boolean transactionType;
    private Boolean exchange;
    private String transactionDate;
    private Long quantity;
    private Float rate;
    private Float brokerage;
    private String settlementNumber;
    
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
    
    @InjectEJB (name ="WatchlistManager")
    WatchlistManager watchlistManager;
    
   
    public AddToWatchlistAction() {
        super();
    }
    
    public String prepareAddToWatchList() {  
    	String returnType = INPUT;
    	try {
			if(this.session.get(Constants.LOGGED_IN_USER)!=null) {
				if (scripId!=null) {
	    			selectedScrip = scripManager.getScripById(scripId);
				}
				userWatchlists = watchlistManager.getWatchlistByUserId(((User)this.session.get(Constants.LOGGED_IN_USER)).getId());
			} else {
				addActionError("You are not logged In. Please Login first to use this feature");
				returnType = "loginPage";
			}
			this.useExistingWatchList = Boolean.TRUE;
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return returnType;    	
    }

    public String addToWatchList() {    	
        String retVal = "reponsePage";
        try {
        	boolean errorFound = validateFormFields();
        	if (!errorFound) {
	        	if (this.session.get(Constants.LOGGED_IN_USER)!=null) {
		        	if (useExistingWatchList) {		        		
	        			Integer responseCode = watchlistManager.addToWatchList(scripId, watchListId, ((User)this.session.get(Constants.LOGGED_IN_USER)).getId() );
	        			
	        			// Transaction also fed by user save it.
	        			if (this.transactionType!=null || this.exchange!=null || !StringUtil.isEmptyOrNull(this.transactionDate) || this.quantity!=null || this.rate!=null || this.brokerage!=null || !StringUtil.isEmptyOrNull(this.settlementNumber)) {
	        				watchlistManager.addTransaction(scripId, watchListId, transactionType, exchange, transactionDate, quantity, rate, brokerage, settlementNumber);
	        			}
	        			
	        			if (responseCode!=0) {
	        				saveErrorResponseMessage(responseCode);
	        				retVal = INPUT;
	        			}		        		
		        	} else if (!useExistingWatchList) { //Create new watchlist		        		
	        			Integer responseCode = watchlistManager.createNewWatchList(scripId, watchlistName, ((User)this.session.get(Constants.LOGGED_IN_USER)).getId(), this.description );
	        			if (responseCode!=0) {
	        				saveErrorResponseMessage(responseCode);
	        				retVal = INPUT;
	        			}
		        	} 
	        	} else {
	        		//User not logged in - Add error message and send back to INUPUT
	        		addActionError("You are not logged In. Please Login first to use this feature");
	        		retVal = "loginPage";
	        	}
        	} else {
        		retVal = INPUT;
        	}
        	if (retVal == INPUT) { // Going back to input page, Need to reload watchlist and selected scrips
        		if (scripId!=null) {
        			selectedScrip = scripManager.getScripById(scripId);
        			if(this.session.get(Constants.LOGGED_IN_USER)!=null) {
        				userWatchlists = watchlistManager.getWatchlistByUserId(((User)this.session.get(Constants.LOGGED_IN_USER)).getId());
        			}
        		}
        	} else if (retVal == "reponsePage") {
        		addActionMessage("Scrip/Symbol added to watchlist successfully");
        	}
        } catch(BusinessException e){
        	log.error(e);
        	retVal = INPUT;
        	addActionError(e.getMessage());
        } catch (Exception e) {
        	log.error(e);
        	retVal = INPUT;
            addActionError(e.getMessage());
        } 
        log.info("Returning "+retVal);
        return retVal;
    }

	public Scrip getSelectedScrip() {
		return selectedScrip;
	}

	public void setSelectedScrip(Scrip selectedScrip) {
		this.selectedScrip = selectedScrip;
	}

	public List<Watchlist> getUserWatchlists() {
		return userWatchlists;
	}

	public void setUserWatchlists(List<Watchlist> userWatchlists) {
		this.userWatchlists = userWatchlists;
	}

	public Long getScripId() {
		return scripId;
	}

	public void setScripId(Long scripId) {
		this.scripId = scripId;
	}

	public Long getWatchListId() {
		return watchListId;
	}

	public void setWatchListId(Long watchListId) {
		this.watchListId = watchListId;
	}

	public String getWatchlistName() {
		return watchlistName;
	}

	public void setWatchlistName(String watchlistName) {
		this.watchlistName = watchlistName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getUseExistingWatchList() {
		return useExistingWatchList;
	}

	public void setUseExistingWatchList(Boolean useExistingWatchList) {
		this.useExistingWatchList = useExistingWatchList;
	}
	
	private boolean validateFormFields() {
		boolean retValue = false;
		System.out.println("In validateFormFields this.useExistingWatchList="+this.useExistingWatchList);
		if (this.useExistingWatchList==null) {
			addActionError("Please choose the option wether you want to create new watchlist or use existing one");
			retValue = true;
		} else {
			if (this.useExistingWatchList && this.watchListId.longValue()==-1) { //User not selected the WL from dropdown
				addActionError("Please select the watchlist to which you want to add this scrip");
				retValue = true;
			} else if (!this.useExistingWatchList) {
				if (this.watchlistName==null || this.watchlistName.length()==0) {
					addActionError("Please provide the name for the new watchlist to be created");
					retValue = true;
				}
				if (this.description==null || this.description.length()==0) {
					addActionError("Please provide the short description for the new watchlist");
					retValue = true;
				}
			}
		}
		
		if (this.transactionType!=null || this.exchange!=null || !StringUtil.isEmptyOrNull(this.transactionDate) || this.quantity!=null || this.rate!=null || this.brokerage!=null || !StringUtil.isEmptyOrNull(this.settlementNumber)) {
			System.out.println("this.transactionType="+this.transactionType);
			System.out.println("this.this.exchange="+this.exchange);
			System.out.println("this.transactionDate="+this.transactionDate);
			System.out.println("this.quantity="+this.quantity);
			System.out.println("this.rate="+this.rate);
			System.out.println("this.brokerage="+this.brokerage);
			System.out.println("this.settlementNumber="+this.settlementNumber);
			
			if (this.transactionType==null) {
				addActionError("Please select the transaction type. (Buy or Sell)");
				retValue = true;
			} 
			if (this.exchange==null) {
				addActionError("Please select the exchange. (BSE or NSE)");
				retValue = true;
			}
			if (this.transactionDate==null) {
				addActionError("Please select the transaction date.");
				retValue = true;
			}
			if (this.quantity==null) {
				addActionError("Please select the quantity.");
				retValue = true;
			}
			if (this.rate==null) {
				addActionError("Please select the rate.");
				retValue = true;
			}
			if (this.brokerage==null) {
				addActionError("Please select the brokerage.");
				retValue = true;
			}
			if (this.settlementNumber==null) {
				addActionError("Please select the settlementNumber or Reference number.");
				retValue = true;
			}
		}
		return retValue;
	}
	
	private void saveErrorResponseMessage(Integer responseCode) {
		if (responseCode.equals(ErrorCodeConstants.WATCHLIST_DUPLICATE_ITEMS)) {
			addActionError("This scrip already exist in the selected watchlist. Either select a different watchlist or create a new watchlist");
		} else if (responseCode.equals(ErrorCodeConstants.WATCHLIST_NO_RIGHTS)) {
			addActionError("You do not have teh access permission to modify this watchlist");
		} else if (responseCode.equals(ErrorCodeConstants.WATCHLIST_NUMBER_OF_ALLOWED_WL_CROSSED)) {
			addActionError("You are exceeded with maximum allowed watchlist. Please upgrade your subscription to add new watchlist");
		} else if (responseCode.equals(ErrorCodeConstants.WATCHLIST_NUMBER_OF_ALLOWED_WLITEM_CROSSED)) {
			addActionError("You are exceeded with maximum allowed scrips/symbols per watchlist. Please upgrade your subscription to add more symbols to this watchlist");		
		}
	}

	public Boolean getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Boolean transactionType) {
		this.transactionType = transactionType;
	}

	public Boolean getExchange() {
		return exchange;
	}

	public void setExchange(Boolean exchange) {
		this.exchange = exchange;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Float getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(Float brokerage) {
		this.brokerage = brokerage;
	}

	public String getSettlementNumber() {
		return settlementNumber;
	}

	public void setSettlementNumber(String settlementNumber) {
		this.settlementNumber = settlementNumber;
	}

	public ScripManager getScripManager() {
		return scripManager;
	}

	public void setScripManager(ScripManager scripManager) {
		this.scripManager = scripManager;
	}

	public WatchlistManager getWatchlistManager() {
		return watchlistManager;
	}

	public void setWatchlistManager(WatchlistManager watchlistManager) {
		this.watchlistManager = watchlistManager;
	}
}

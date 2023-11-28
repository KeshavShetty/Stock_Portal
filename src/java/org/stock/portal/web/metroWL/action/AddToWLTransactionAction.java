package org.stock.portal.web.metroWL.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.domain.WatchlistItem;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class AddToWLTransactionAction extends BaseAction {

	Logger log = Logger.getLogger(AddToWLTransactionAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private Scrip selectedScrip;
    private List<Watchlist> userWatchlists;
    
    private Long scripId;
    private Long watchListId;
    
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
    
   
    public AddToWLTransactionAction() {
        super();
    }
    
    public String prepareAddToWLTransaction() {  
    	String returnType = INPUT;
    	try {
			if(this.session.get(Constants.LOGGED_IN_USER)!=null) {
				if (scripId!=null) {
	    			selectedScrip = scripManager.getScripById(scripId);
				}
				userWatchlists = watchlistManager.getWatchlistByUserId(((User)this.session.get(Constants.LOGGED_IN_USER)).getId());
				WatchlistItem wlItem = watchlistManager.getWatchlistItemByWlIdAndScripId(watchListId, scripId);
				if (wlItem!=null) {
					if (wlItem.getStockInHand()==0) { //Mostly he is buying
						this.transactionType = false;
					} else {
						this.transactionType = true;
						this.quantity = wlItem.getStockInHand();
					}
					this.exchange = true;
					this.transactionDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
					this.settlementNumber = watchlistManager.getMaxSettlementNumber(watchListId);
					this.brokerage=0f;
				}
			} else {
				addActionError("You are not logged In. Please Login first to use this feature");
				returnType = "loginPage";
			}
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return returnType;    	
    }

    public String addToWLTransaction() {    	
        String retVal = "reponsePage";
        try {
        	boolean errorFound = validateFormFields();
        	if (!errorFound) {
	        	if (this.session.get(Constants.LOGGED_IN_USER)!=null) {	        		
	        		watchlistManager.addTransaction(scripId, watchListId, transactionType, exchange, transactionDate, quantity, rate, brokerage, settlementNumber);
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
	
	private boolean validateFormFields() {
		boolean retValue = false;
		    
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
		return retValue;
	}	

	public boolean isTransactionType() {
		return transactionType;
	}

	public void setTransactionType(boolean transactionType) {
		this.transactionType = transactionType;
	}

	public boolean isExchange() {
		return exchange;
	}

	public void setExchange(boolean exchange) {
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
}

package org.stock.portal.web.metroWL.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.domain.WatchlistItem;
import org.stock.portal.domain.WatchlistTransactions;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;

public class WatchlistAction extends BaseAction{
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(WatchlistAction.class.getName());
	
	private Long watchlistId;
		
	private String groupOrder;
	private boolean includeZeroHolidngItems;
	private String fromDate;
	private String toDate;
	
	private List<WatchlistItem> wlItems;
	
	private Watchlist watchlist;
	
	private List<WatchlistTransactions> wlTransactions;
	
    private String sourceFormName;
    private String divToFill;
    
    private Long totalRecords;
    
    private Long selectedTransactionId;
    private Long selectedWLItemId;
	
	@InjectEJB (name ="WatchlistManager")
    WatchlistManager watchlistManager;
        
	public Long getWatchlistId() {
		return watchlistId;
	}

	public void setWatchlistId(Long watchlistId) {
		this.watchlistId = watchlistId;
	}
	
	public String viewWatchlist() {    	
        String retVal = "scripwiseResultPage";
        try {
        	User account = (User)this.session.get(Constants.LOGGED_IN_USER);
        	if (this.groupOrder!=null && this.groupOrder.equals("Datewise")) {
        		retVal = "datewiseResultPage";
        		wlTransactions = watchlistManager.getWatchlistTransactionsByWlIdAndUserId(watchlistId, account.getId(), fromDate, toDate);
        		float maxCapitalUsed = 0;
        		float availableCapital = 0;
        		for (int i=0;i<wlTransactions.size();i++) {
        			WatchlistTransactions aTransaction = wlTransactions.get(i);
        			if (aTransaction.getTransactionType()==false) { // buy
        				float amountUsedForThisTransaction = aTransaction.getQuantity().floatValue()*aTransaction.getRate()+aTransaction.getBrokerage();
        				if (availableCapital<amountUsedForThisTransaction) {
        					float diffAmountToBorrow = amountUsedForThisTransaction - availableCapital;
        					maxCapitalUsed = maxCapitalUsed + diffAmountToBorrow;
        					availableCapital = availableCapital + diffAmountToBorrow;        					
        					availableCapital = availableCapital - amountUsedForThisTransaction;
        				} else {
        					availableCapital = availableCapital - amountUsedForThisTransaction;
        				}
        			} else { // Sell
        				float amountUsedForThisTransaction = aTransaction.getQuantity().floatValue()*aTransaction.getRate()-aTransaction.getBrokerage();
        				availableCapital = availableCapital + amountUsedForThisTransaction;
        			}
        		}
        		System.out.println("maxCapitalUsed="+maxCapitalUsed+" availableCapital="+availableCapital);
        	} else {
        		retVal = "scripwiseResultPage";
        		wlItems = watchlistManager.getWatchlistItemByWlIdAndUserId(watchlistId, account.getId(), includeZeroHolidngItems);
        		watchlist = watchlistManager.getWatchlisByWlIdAndUser(watchlistId, account.getId());
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
	
	public String deleteTransaction() {    	
        String retVal = "successPage";
        try {
        	//Todo check weather WL belng to the logged user
        	User account = (User)this.session.get(Constants.LOGGED_IN_USER);
        	if (account!=null) {
        		watchlistManager.deleteTransaction(selectedTransactionId);
        	}
        	addActionMessage("Operation successfull. Please reload the page");
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        log.info("Returning "+retVal);
        return retVal;
    }
	
	public String deleteWatchlistItem() {    	
        String retVal = "successPage";
        try {
        	//Todo check weather WL belng to the logged user
        	User account = (User)this.session.get(Constants.LOGGED_IN_USER);
        	if (account!=null) {
        		watchlistManager.deleteWatchlistItem(selectedWLItemId);
        	}
        	addActionMessage("Operation successfull. Please reload the page");
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        log.info("Returning "+retVal);
        return retVal;
    }
	
	public String rebuildWatchlistItem() {    	
        String retVal = "successPage";
        try {
        	//Todo check weather WL belng to the logged user
        	User account = (User)this.session.get(Constants.LOGGED_IN_USER);
        	if (account!=null) {
        		watchlistManager.rebuildWatchlistItem(selectedWLItemId);
        	}
        	addActionMessage("Operation successfull. Please reload the page");
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

	public List<WatchlistItem> getWlItems() {
		return wlItems;
	}

	public void setWlItems(List<WatchlistItem> wlItems) {
		this.wlItems = wlItems;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getGroupOrder() {
		return groupOrder;
	}

	public void setGroupOrder(String groupOrder) {
		this.groupOrder = groupOrder;
	}

	public boolean isIncludeZeroHolidngItems() {
		return includeZeroHolidngItems;
	}

	public void setIncludeZeroHolidngItems(boolean includeZeroHolidngItems) {
		this.includeZeroHolidngItems = includeZeroHolidngItems;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public List<WatchlistTransactions> getWlTransactions() {
		return wlTransactions;
	}

	public void setWlTransactions(List<WatchlistTransactions> wlTransactions) {
		this.wlTransactions = wlTransactions;
	}
	
	public Float getNetPAndL() {
		Float retValue=0f;
		for(int i=0;i<wlItems.size();i++) {
			WatchlistItem aItem = wlItems.get(i); //scrip.nseCmp*stockInHand + totalMoneyflow
			if (aItem.getScrip().getNseCmp()!=null && aItem.getScrip().getBseCmp()!=null) {
				if (aItem.getScrip().getNseCmp()>aItem.getScrip().getBseCmp()) {
					retValue = retValue + aItem.getScrip().getNseCmp()*aItem.getStockInHand().floatValue() + aItem.getTotalMoneyflow();
				} else {
					retValue = retValue + aItem.getScrip().getBseCmp()*aItem.getStockInHand().floatValue() + aItem.getTotalMoneyflow();
				}
			} else {
				if (aItem.getScrip().getNseCmp()!=null) {
					retValue = retValue + aItem.getScrip().getNseCmp()*aItem.getStockInHand().floatValue() + aItem.getTotalMoneyflow();
				} else {
					retValue = retValue + aItem.getScrip().getBseCmp()*aItem.getStockInHand().floatValue() + aItem.getTotalMoneyflow();
				}
			}
			
		}
		return retValue;
	}
	
	public Float getTotalBrokerageInDateWise() {
		Float retValue=0f;
		for(int i=0;i<wlTransactions.size();i++) {
			WatchlistTransactions aTrans = wlTransactions.get(i); //scrip.nseCmp*stockInHand + totalMoneyflow
			retValue = retValue + aTrans.getBrokerage();
		}
		return retValue;
	}
	
	public Float getTotalamountInDateWise() {
		Float retValue=0f;
		for(int i=0;i<wlTransactions.size();i++) {
			WatchlistTransactions aTrans = wlTransactions.get(i); //scrip.nseCmp*stockInHand + totalMoneyflow
			if (aTrans.getTransactionType()==false) retValue = retValue + aTrans.getQuantity().floatValue()*aTrans.getRate();
			else retValue = retValue - aTrans.getQuantity().floatValue()*aTrans.getRate();
		}
		return retValue;
	}
	
	public Float getSihWorthInScripWise() {
		Float retVal = 0f;
		if (wlItems!=null && wlItems.size()>0) {
			for(int i=0;i<wlItems.size();i++) {
				WatchlistItem aItem = wlItems.get(i);
				if (aItem.getStockInHand()>0) {
					
					if (aItem.getScrip().getNseCmp()!=null && aItem.getScrip().getBseCmp()!=null) {
						if (aItem.getScrip().getNseCmp()>aItem.getScrip().getBseCmp()) {
							retVal = retVal + (aItem.getStockInHand().floatValue()*aItem.getScrip().getNseCmp());
						} else {
							retVal = retVal + (aItem.getStockInHand().floatValue()*aItem.getScrip().getBseCmp());
						}
					} else {
						if (aItem.getScrip().getNseCmp()!=null) {
							retVal = retVal + (aItem.getStockInHand().floatValue()*aItem.getScrip().getNseCmp());
						} else {
							retVal = retVal + (aItem.getStockInHand().floatValue()*aItem.getScrip().getBseCmp());
						}
					}
				} 
			}
		}
		return retVal;
	}

	public Long getSelectedWLItemId() {
		return selectedWLItemId;
	}

	public void setSelectedWLItemId(Long selectedWLItemId) {
		this.selectedWLItemId = selectedWLItemId;
	}

	public WatchlistManager getWatchlistManager() {
		return watchlistManager;
	}

	public void setWatchlistManager(WatchlistManager watchlistManager) {
		this.watchlistManager = watchlistManager;
	}

	public Long getSelectedTransactionId() {
		return selectedTransactionId;
	}

	public void setSelectedTransactionId(Long selectedTransactionId) {
		this.selectedTransactionId = selectedTransactionId;
	}

	public Watchlist getWatchlist() {
		return watchlist;
	}

	public void setWatchlist(Watchlist watchlist) {
		this.watchlist = watchlist;
	}
}

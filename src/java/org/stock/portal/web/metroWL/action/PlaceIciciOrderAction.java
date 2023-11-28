package org.stock.portal.web.metroWL.action;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.common.ErrorCodeConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.IciciOrder;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.service.order.OrderManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class PlaceIciciOrderAction extends BaseAction {

	Logger log = Logger.getLogger(PlaceIciciOrderAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private Long scripId;
    private String exchangeCode;
    private String orderType; //Market or Limit
    private String transactionType; //Buy or sell    
    private Long quantity;
    private Float atPrice;
    private Float maximumBudget;    
    
    private Scrip selectedScrip;
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
    
    @InjectEJB (name ="OrderManager")
    OrderManager orderManager;
    
   
    public PlaceIciciOrderAction() {
        super();
    }
    
    public String preparePlaceOrder() {  
    	String returnType = INPUT;
    	try {
			if(this.session.get(Constants.LOGGED_IN_USER)!=null) {
				if (scripId!=null) {
	    			selectedScrip = scripManager.getScripById(scripId);
	    			atPrice = selectedScrip.getBseCmp();
				}	
				this.exchangeCode = "NSE";
				this.transactionType = "Buy";
				this.orderType = "Market";
			} else {
				addActionError("You are not logged In. Please Login first to use this feature");
				returnType = "loginPage";
			}
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return returnType;    	
    }

    public String placeOrder() {    	
        String retVal = "reponsePage";
        try {
        	boolean errorFound = validateFormFields();
        	if (!errorFound) {
	        	if (this.session.get(Constants.LOGGED_IN_USER)!=null) {
	        		User loggedUser = (User)this.session.get(Constants.LOGGED_IN_USER);
	        		selectedScrip = scripManager.getScripById(scripId);
	        		if (selectedScrip.getIciciCode()!=null && selectedScrip.getIciciCode().length()>0) {
		        		IciciOrder aOrder = new IciciOrder();
		        		
		        		aOrder.setExchangeCode(false);
		        		if (this.exchangeCode.equalsIgnoreCase("Nse")) {
		        			aOrder.setExchangeCode(true);
		        		}
		        		aOrder.setTransactionType(false);
		        		if (this.transactionType.equalsIgnoreCase("Sell")) {
		        			aOrder.setTransactionType(true);
		        		}
		        		aOrder.setOrderType(false);
		        		if (this.transactionType.equalsIgnoreCase("Limit")) {
		        			aOrder.setOrderType(true);
		        		} else {
		        			if (selectedScrip.getNseCmp()!=null && selectedScrip.getNseCmp()>0) aOrder.setAtPrice(selectedScrip.getNseCmp());
		        			else aOrder.setAtPrice(selectedScrip.getBseCmp());
		        		}
		        		aOrder.setOrderDate(new Date());
		        		aOrder.setOrderStatus("Placed");
		        		aOrder.setPlacedBy(loggedUser);
		        		aOrder.setScrip(selectedScrip);
		        		
		        		if (atPrice!=null && atPrice!=0) {
		        			aOrder.setAtPrice(atPrice);
		        			if (maximumBudget!=null || maximumBudget!=0) {
		        				Float qtyToOrder = maximumBudget/atPrice + 1;
		        				aOrder.setQuantity(qtyToOrder.longValue());	
		        				
		        				aOrder.setMaximumBudget(maximumBudget); //Only for record purpose
		        			} else { // So quantity mentioned
		        				aOrder.setQuantity(quantity);
		        			}
		        		} else { // Price null, 
		        			aOrder.setQuantity(quantity);
		        			//aOrder.setAtPrice(maximumBudget/quantity);
		        			aOrder.setMaximumBudget(maximumBudget);
		        		}
		        		orderManager.placeNewOrderWithIcici(aOrder);
	        		} else {
	        			addActionError("Cannot place the order as ICICI code missing");
	        			retVal = INPUT;
	        		}
		        } else {
					addActionError("You are not logged In. Please Login first to use this feature");
					retVal = "loginPage";
				}	        	
        	} else {
        		retVal = INPUT;
        	}
        	if (retVal == INPUT) { // Going back to input page, Need to reload selected scrips
        		if (scripId!=null) {
	    			selectedScrip = scripManager.getScripById(scripId);
				}
        	} else if (retVal == "reponsePage") {
        		addActionMessage("Order dispactged to ICICI");
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

	public Long getScripId() {
		return scripId;
	}

	public void setScripId(Long scripId) {
		this.scripId = scripId;
	}
	
	private boolean validateFormFields() {
		boolean retValue = false;
		System.out.println("In validateFormFields");
		int missingFields = 0;
		if (atPrice==null || atPrice==0) {
			missingFields++;
		}
		if (quantity==null || quantity==0) {
			missingFields++;
		}
		if (maximumBudget==null || maximumBudget==0) {
			missingFields++;			
		}
		if (missingFields>1) {
			addActionError("Two fields are mandatory among Quantity, Price, Maximum budget");
			retValue = true;
		}
		if (exchangeCode==null || exchangeCode.length()==0) {
			addActionError("Select the exchange");
			retValue = true;
		}
		if (orderType==null || orderType.length()==0) {
			addActionError("Select the order Type. (Market or Limit price)");
			retValue = true;
		} else if (orderType.equalsIgnoreCase("Limit") && (atPrice==null || atPrice<=0)) {
			addActionError("For Limit order, Price is mandatory");
			retValue = true;
		} else if (orderType.equalsIgnoreCase("Limit") && (atPrice!=0)) {
			if ((quantity==null || quantity==0) && (maximumBudget==null || maximumBudget==0)){
				addActionError("For Limit order, Quantity or budget is mandatory");
				retValue = true;
			}
		}
		if (transactionType==null || transactionType.length()==0) {
			addActionError("Select the transaction Type. (Buy of sell)");
			retValue = true;
		} 
		
		return retValue;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Float getAtPrice() {
		return atPrice;
	}

	public void setAtPrice(Float atPrice) {
		this.atPrice = atPrice;
	}

	public Float getMaximumBudget() {
		return maximumBudget;
	}

	public void setMaximumBudget(Float maximumBudget) {
		this.maximumBudget = maximumBudget;
	}
}

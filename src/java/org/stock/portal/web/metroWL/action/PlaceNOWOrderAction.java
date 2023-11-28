package org.stock.portal.web.metroWL.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.IciciOrder;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.User;
import org.stock.portal.domain.WatchlistItem;
import org.stock.portal.domain.dto.NOWOrderDTO;
import org.stock.portal.service.order.OrderManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class PlaceNOWOrderAction extends BaseAction {

	Logger log = Logger.getLogger(PlaceNOWOrderAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private Long scripId;
    private String exchangeCode;
    private int orderType; //Market or Limit
    private int transactionType; //Buy or sell  
    private String orderNature; // Live or AMO (After market hours)
    private Long quantity;
    private Float atPrice;
    private Float maximumBudget;    
    
    private Scrip selectedScrip;
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
    
    @InjectEJB (name ="OrderManager")
    OrderManager orderManager;
    
    @InjectEJB (name ="WatchlistManager")
    WatchlistManager  watchlistManager;
    
    public PlaceNOWOrderAction() {
        super();
    }
    
    public String preparePlaceOrder() {  
    	String returnType = INPUT;
    	try {
			if(this.session.get(Constants.LOGGED_IN_USER)!=null) {
				User loggedUser = (User)this.session.get(Constants.LOGGED_IN_USER);
				if (scripId!=null) {
	    			selectedScrip = scripManager.getScripById(scripId);	    			
	    			if (selectedScrip.getBseCmp()!=null && selectedScrip.getNseCmp()!=null) {
	    				if (selectedScrip.getBseCmp() > selectedScrip.getNseCmp()) {
		    				atPrice = selectedScrip.getBseCmp();
		    			} else {
		    				atPrice = selectedScrip.getNseCmp();
		    			}
	    			} else if (selectedScrip.getBseCmp()!=null) {
	    				atPrice = selectedScrip.getBseCmp();
	    			} else {
	    				atPrice = selectedScrip.getNseCmp();
	    			}
	    			
	    			List<WatchlistItem> sihList = watchlistManager.getSIHByUserIdAndScripId(scripId, loggedUser.getId());
	    			System.out.println("------------------ sihList size "+sihList.size());
	    			if (sihList!=null && sihList.size()>0 && sihList.get(0).getStockInHand()!=null && sihList.get(0).getStockInHand()>0) {
	    				this.quantity = sihList.get(0).getStockInHand();
	    				this.transactionType = 1;
	    			} else {
	    				this.transactionType = 0;
	    				this.quantity = 10000 / atPrice.longValue();
	    				this.maximumBudget = 10000f;
	    			}
	    			
	    			Calendar cal = Calendar.getInstance();
	    			if (cal.get(Calendar.HOUR_OF_DAY)>10 && cal.get(Calendar.HOUR_OF_DAY)<15 && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
	    				this.orderNature = "Live";
	    				this.orderType = 0;
	    			} else {
	    				this.orderNature = "AMO";
	    				this.orderType = 1;
	    			}
	    			this.exchangeCode = "NSE";
	    		}
			} else {
				addActionError("You are not logged In. Please Login first to use this feature");
				returnType = "loginPage";
			}
    	} catch (Exception ex) {
    		ex.printStackTrace();
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
	        		
	        		//Initialize the NOW client
	    			
    				NowWebClient nowClient = new NowWebClient();
    				nowClient.login();
    				if (nowClient.isLoggedin) {
    					System.out.println("------------------------------------------- NOW Client Success -------------------------------------------");
    					String scripCodeToUse = null;
	        			String seriesTypeToUse = null;
	        			if (this.exchangeCode.equalsIgnoreCase("BSE")) {
	        				scripCodeToUse = selectedScrip.getBseCode();
	        			} else {
	        				scripCodeToUse = selectedScrip.getNseCode();
	        				seriesTypeToUse = selectedScrip.getSeriesType();
	        			}
	        			String orderNumber = nowClient.placeOrder(this.exchangeCode, scripCodeToUse, seriesTypeToUse, this.transactionType, this.orderType, this.orderNature, this.quantity, this.atPrice);
	        			if (orderNumber!=null && orderNumber.length()>0) {
	        				System.out.println("---------------Order placed successfully. Your order number is: "+orderNumber);
	        				addActionMessage("Order placed successfully. Your order number is: "+orderNumber);
	        			} else {
	        				addActionError("Failed to place the order.");
	        				System.out.println("---------------Failed to place the order.");
	        			}
	        			System.out.println("----------------------------------------");
	        			List<NOWOrderDTO> nowOrders = nowClient.getOrderBook();
	        			for(int i=0;i<nowOrders.size();i++) {
	        				NOWOrderDTO aDto = nowOrders.get(i);
	        				if (aDto.getOrderNumber().equalsIgnoreCase(orderNumber)) {
	        					if (aDto.getRejectionReason()!=null && aDto.getRejectionReason().length()>0) {
	        						addActionError("Order rejected: "+aDto.getRejectionReason());
	        					} else {
	        						addActionMessage("Order status : "+aDto.getStatus());
	        					}
	        				}
	        				System.out.println("aDto:"+aDto);
	        			}
	        			System.out.println("----------------------------------------");
	        			nowClient.logout();
    				} else {
    					addActionError("Failed to intiialise the NOW Client");
    					System.out.println("------------------------------------------- Failed to intiialise the NOW Client -------------------------------------------");
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
        		//addActionMessage("Order dispactged to ICICI");
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
//		if (orderType==null || orderType.length()==0) {
//			addActionError("Select the order Type. (Market or Limit price)");
//			retValue = true;
//		} else if (orderType.equalsIgnoreCase("Limit") && (atPrice==null || atPrice<=0)) {
//			addActionError("For Limit order, Price is mandatory");
//			retValue = true;
//		} else if (orderType.equalsIgnoreCase("Limit") && (atPrice!=0)) {
//			if ((quantity==null || quantity==0) && (maximumBudget==null || maximumBudget==0)){
//				addActionError("For Limit order, Quantity or budget is mandatory");
//				retValue = true;
//			}
//		}
//		if (transactionType==null || transactionType.length()==0) {
//			addActionError("Select the transaction Type. (Buy of sell)");
//			retValue = true;
//		} 
		
		return retValue;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
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

	public String getOrderNature() {
		return orderNature;
	}

	public void setOrderNature(String orderNature) {
		this.orderNature = orderNature;
	}
}

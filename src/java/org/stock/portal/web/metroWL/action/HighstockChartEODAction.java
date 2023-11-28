package org.stock.portal.web.metroWL.action;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.domain.Scrip;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;

public class HighstockChartEODAction extends BaseAction {
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(HighstockChartEODAction.class.getName());
    
	private String tickerSymbol =  "BSE-BSE30";
	private String fromDate;
	private String toDate;
	
	@InjectEJB (name ="DataManager")
	DataManager dataManager;
	
	public String loadHighstock(){
		String returnPage = "showChart";
		try {
			Scrip scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
	    	
	    	if (scrip!=null) {
	    		if (scrip.getNseCode()!=null) {
	    			tickerSymbol = "NSE-" + scrip.getNseCode();
	    		} else if (scrip.getBseName()!=null) {
	    			tickerSymbol = "BSE-" + scrip.getBseName();
	    		} else if (scrip.getBseCode()!=null) {
	    			tickerSymbol = "BSE-" + scrip.getBseCode();
	    		}
	    	}
	    	Calendar curDate = Calendar.getInstance();
			curDate.add(Calendar.DATE, 1);
			Date latestDate = dataManager.getMaxDataDate("NSE", curDate.getTime());
			this.toDate = SPConstants.SPCORE_DATE_FORMAT.format(latestDate);
			curDate.add(Calendar.MONTH, -6);
			this.fromDate = SPConstants.SPCORE_DATE_FORMAT.format(curDate.getTime());
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
	
}

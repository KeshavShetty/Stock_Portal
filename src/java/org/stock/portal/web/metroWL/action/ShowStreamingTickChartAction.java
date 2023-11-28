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

public class ShowStreamingTickChartAction extends BaseAction {
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(ShowStreamingTickChartAction.class.getName());
    
	private String tickerSymbol =  "BSE-BSE30";
	private String dataDate;
	private Integer timeGap;
	private String startHour;
	private String endHour;
	
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
	    	this.timeGap = 1;
	    	this.startHour = "09:02";
	    	this.endHour = "09:16";
	    	Calendar curDate = Calendar.getInstance();
			curDate.add(Calendar.DATE, 1);
			Date latestDate = dataManager.getMaxDataDate("NSE", curDate.getTime());
			this.dataDate = SPConstants.SPCORE_DATE_FORMAT.format(latestDate);
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

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public Integer getTimeGap() {
		return timeGap;
	}

	public void setTimeGap(Integer timeGap) {
		this.timeGap = timeGap;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
}

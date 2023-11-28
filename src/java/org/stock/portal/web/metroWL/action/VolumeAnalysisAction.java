package org.stock.portal.web.metroWL.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.domain.IntradaySnapshotData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;

public class VolumeAnalysisAction extends BaseAction {
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(VolumeAnalysisAction.class.getName());
    
	private String tickerSymbol =  "BSE-BSE30";
	private String trendStartDate;
	private String trendEndDate;
	
	private Long outstandingVolume = null;
	private Float meanPriceAtleftover = null;
	
	private List<String> volumeCalculations = new ArrayList<String>();
	
	@InjectEJB (name ="DataManager")
	DataManager dataManager;
	
	public String loadViewPage(){
		String returnPage = "showPage";
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
			//curDate.add(Calendar.DATE, -1);
			this.trendEndDate = SPConstants.SPCORE_DATE_FORMAT.format(curDate.getTime());
			System.out.println("trendEndDate="+trendEndDate);
			curDate.add(Calendar.DATE, -10);
			this.trendStartDate = SPConstants.SPCORE_DATE_FORMAT.format(curDate.getTime());
			System.out.println("trendStartDate="+trendStartDate);
//			Date latestDate = dataManager.getMaxDataDate("NSE", curDate.getTime());
//			this.resultDataDate = SPConstants.SPCORE_DATE_FORMAT.format(latestDate);
//			Date previousDate = dataManager.getMaxDataDate("NSE", latestDate);
//			this.dataDate = SPConstants.SPCORE_DATE_FORMAT.format(previousDate);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return returnPage;
	}

	public String calculateVolumeAnalysis() {
		String returnPage = "showAnalysis";
		try {
			Scrip scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
			List<IntradaySnapshotData> snapshotData = dataManager.getSnapshotData(scrip.getId(), trendStartDate, trendEndDate);
			int bullPointer = -1;
			int bearPointer = snapshotData.size();
			outstandingVolume = 0L;
			while (bullPointer<bearPointer-1) {
				if (outstandingVolume>0) {
					bearPointer--;
					IntradaySnapshotData aDataPoint = snapshotData.get(bearPointer);
					outstandingVolume = outstandingVolume - aDataPoint.getTotalVolume();
				} else {
					bullPointer++;
					IntradaySnapshotData aDataPoint = snapshotData.get(bullPointer);
					outstandingVolume = outstandingVolume + aDataPoint.getTotalVolume();
				}
				IntradaySnapshotData indexBar =  snapshotData.get(volumeCalculations.size());
				String statusBar = "snapshot[" + volumeCalculations.size() + "] DataDate:" +  indexBar.getDataDate() + " MeanPrice:"+indexBar.getMeanPrice()+" Volume:"+indexBar.getTotalVolume();
				statusBar = statusBar + " " + "bullPointer:"+bullPointer+" bearPointer:"+bearPointer+" outstandingVolume:"+outstandingVolume;
				volumeCalculations.add(statusBar);
			}
			meanPriceAtleftover = snapshotData.get(bullPointer).getMeanPrice();
			System.out.println("Mean Price at bullPointer="+snapshotData.get(bullPointer).getMeanPrice()+" Mean Price at bearPointer="+snapshotData.get(bearPointer).getMeanPrice());
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

	public String getTrendStartDate() {
		return trendStartDate;
	}

	public void setTrendStartDate(String trendStartDate) {
		this.trendStartDate = trendStartDate;
	}

	public String getTrendEndDate() {
		return trendEndDate;
	}

	public void setTrendEndDate(String trendEndDate) {
		this.trendEndDate = trendEndDate;
	}

	public Long getOutstandingVolume() {
		return outstandingVolume;
	}

	public void setOutstandingVolume(Long outstandingVolume) {
		this.outstandingVolume = outstandingVolume;
	}

	public Float getMeanPriceAtleftover() {
		return meanPriceAtleftover;
	}

	public void setMeanPriceAtleftover(Float meanPriceAtleftover) {
		this.meanPriceAtleftover = meanPriceAtleftover;
	}

	public List<String> getVolumeCalculations() {
		return volumeCalculations;
	}

	public void setVolumeCalculations(List<String> volumeCalculations) {
		this.volumeCalculations = volumeCalculations;
	}
}

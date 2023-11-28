package org.stock.portal.web.action.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.stock.portal.common.ApplicationConfig;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.service.data.DailyProcessorManager;
import org.stock.portal.web.annotation.InjectEJB;

public class DataProcessorAction extends DataProcessorBaseAction {
	private static final long serialVersionUID = 1L;

	Logger log = Logger.getLogger(DataProcessorAction.class.getName());
	
	// For EOD Data
	private String exchangeCode = "BSE";
	private String dataDate;
		
	/** The daily processor accessor bean. */
    
        
    public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public String preparePage(){    	
    	return SUCCESS;
    }    
    
    public String updateEODData(){
    	log.info("EOD processing for dataDate="+ dataDate+" exchangeCode="+exchangeCode);
    	log.info("EXCHANGE_DOWNLOAD_FILE_LOCATION="+ApplicationConfig.EXCHANGE_DOWNLOAD_FILE_LOCATION);
    	try {
    		if (dataDate==null || dataDate.length()==0) dataDate = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
    		log.info("updateEODData dataDate="+dataDate);
    		if (exchangeCode!=null && exchangeCode.equals("NSE")) {
    			log.info("Processing EOD data for exchange NSE");
    			boolean isSuccess = downloadAndExtractNSEBhavcopy(ApplicationConfig.EXCHANGE_DOWNLOAD_FILE_LOCATION+ exchangeCode + "/eod/",dataDate);
    			if (isSuccess) {
    				procssNSEEOData(ApplicationConfig.EXCHANGE_DOWNLOAD_FILE_LOCATION+ exchangeCode + "/eod/",dataDate);
    			}
    		} else { // BSE
    			log.info("Processing EOD data for exchange BSE");
    			String bhavcopyFilename = dataDate.substring(0,2) + dataDate.substring(3,5) + dataDate.substring(8,10);
    			boolean isSuccess = downloadAndExtractBSEBhavcopy(ApplicationConfig.EXCHANGE_DOWNLOAD_FILE_LOCATION+ exchangeCode + "/eod/",bhavcopyFilename);
    			if (isSuccess) {
    				procssBSEEOData(ApplicationConfig.EXCHANGE_DOWNLOAD_FILE_LOCATION+ exchangeCode + "/eod/",bhavcopyFilename, this.dataDate);
    			}
    		}
    		  		
    	} catch(Exception ex) {
    		log.error(ex);
    	}
    	return SUCCESS;
    } 
    
    @InjectEJB (name ="DailyProcessorManager")
    DailyProcessorManager dailyProcessManager;
	public void populateEODDataAsyncronously(Map dataMap) {
		try{
			log.info("dailyProcessManager="+dailyProcessManager);
			dailyProcessManager.processEODData(dataMap);
    	}catch(BusinessException be){
    		be.printStackTrace();
    		addActionError("Error could not initiate the extraction process ");
    	}
		
	}
    
}
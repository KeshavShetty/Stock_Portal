package org.stock.portal.web.action.data;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.service.data.DailyProcessorManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;

public class DailyHalfdayIntradayDataProcessorAction extends BaseAction {
	
	Logger log = Logger.getLogger(DailyHalfdayIntradayDataProcessorAction.class.getName());
		
	/** The daily processor accessor bean. */
    @InjectEJB (name ="DailyProcessorManager")
    DailyProcessorManager dailyProcessManager;
        
    public String processDailyBseData(){
    	try{
	    	dailyProcessManager.processBseIntraData();
	    	addActionMessage("The daily process to extract the scrip data has been initiated.");
    	}catch(BusinessException be){
    		be.printStackTrace();
    		addActionError("Error could not initiate the extraction process ");
    	}
    	return SUCCESS;
    }
}
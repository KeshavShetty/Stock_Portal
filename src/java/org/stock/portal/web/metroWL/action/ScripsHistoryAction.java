package org.stock.portal.web.metroWL.action;

import org.apache.log4j.Logger;
import org.stock.portal.web.action.BaseAction;


public class ScripsHistoryAction extends BaseAction {

	Logger log = Logger.getLogger(ScripsHistoryAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	    
    public ScripsHistoryAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	return SUCCESS;
    }
    
    public String searchIndividualScripHistory() {    	
    	return "aScripHistory";
    }
}

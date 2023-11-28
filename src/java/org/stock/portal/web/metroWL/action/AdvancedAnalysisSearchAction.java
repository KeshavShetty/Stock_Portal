package org.stock.portal.web.metroWL.action;

import org.apache.log4j.Logger;
import org.stock.portal.web.action.BaseAction;


public class AdvancedAnalysisSearchAction extends BaseAction {

	Logger log = Logger.getLogger(AdvancedAnalysisSearchAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	    
    public AdvancedAnalysisSearchAction() {
        super();
    }
    
    public String prepareSearch() {
    	return SUCCESS;    	
    }

}

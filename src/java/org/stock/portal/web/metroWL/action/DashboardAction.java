package org.stock.portal.web.metroWL.action;

import org.apache.log4j.Logger;
import org.stock.portal.web.action.BaseAction;


public class DashboardAction extends BaseAction {

	Logger log = Logger.getLogger(DashboardAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	    
    public DashboardAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	return SUCCESS;
    }	
}

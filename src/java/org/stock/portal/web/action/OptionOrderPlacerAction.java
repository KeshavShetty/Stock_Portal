package org.stock.portal.web.action;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.stock.portal.common.StringUtil;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.User;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class OptionOrderPlacerAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(OptionOrderPlacerAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
    
    private String indexName = "";
    private String optiontype = "";
    
    public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getOptiontype() {
		return optiontype;
	}

	public void setOptiontype(String optiontype) {
		this.optiontype = optiontype;
	}	
   
    /** The user accessor bean. */
    @InjectEJB (name ="DataManager")
    DataManager dataManager; 
   
    public OptionOrderPlacerAction() {
        super();
    }

    public String execute() {
    	log.debug("--->Inside LoginAction [JR]");
        // TODO: Benchmark this call - if it is not too expensive we should move it to the BaseAction.
        //Map session = ActionContext.getContext().getSession();
        String retVal = INPUT;
        User account;
        try {
        	log.debug("indexName : "+getIndexName() +" optiontype="+getOptiontype()); 
        	
        	dataManager.saveOptionOrder(getIndexName(), getOptiontype());
        	retVal = SUCCESS;
            
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        return retVal;
    }
    

}

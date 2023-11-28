package org.stock.portal.web.action.scrips;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.Scrip;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class ScripInfoAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(ScripInfoAction.class.getName());
    private static final long serialVersionUID = 4205166422526662903L;    
    
    private Long scripId;
    
    private Scrip scrip;
    
    public Long getScripId() {
		return scripId;
	}

	public void setScripId(Long scripId) {
		this.scripId = scripId;
	}

	public Scrip getScrip() {
		return scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}

	/** The scrip accessor bean. */
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
   
    public ScripInfoAction() {
        super();
    }
    
    public String execute() {
    	log.debug("--->Inside Scrip Info Action [JR]"+scripId);        
        String retVal = SUCCESS;
        try {
        	scrip = scripManager.getScripById(scripId);
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.scrip.search.error"));
        } 
        return retVal;
    }
}

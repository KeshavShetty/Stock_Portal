package org.stock.portal.web.metroWL.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.stock.portal.domain.Scrip;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;

public class ShowQuickChartAction extends BaseAction {
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(ShowQuickChartAction.class.getName());
    
	private String scripCode = "BSE-BSE30";
	private Scrip scrip;
	
	@InjectEJB (name ="ScripManager")
    ScripManager scripManager;
	
	public String loadChart(){
		String returnPage = "showChart";
		try {			
			String scripId = getScripIdFromRequest();
			if (scripId!=null && scripId.length()>0) {
				this.scrip = scripManager.getScripById(Long.parseLong(scripId));
			} else {	    	
				scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
			}
	    	
	    	if (scrip!=null) {
	    		if (scrip.getNseCode()!=null) {
	    			scripCode = "NSE-" + scrip.getNseCode();
	    		} else if (scrip.getBseName()!=null) {
	    			scripCode = "BSE-" + scrip.getBseName();
	    		} else if (scrip.getBseCode()!=null) {
	    			scripCode = "BSE-" + scrip.getBseCode();
	    		}
	    	}	
		} catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.scrip.info"));
        }
		return returnPage;
	}
	
	private String getScripIdFromRequest() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String scripId = request.getParameter("scripId");
		if (scripId==null || scripId.length()==0) { // Try from jqIndex
			String jqIndex = request.getParameter("jqIndex");
			if (jqIndex!=null) {
				if (jqIndex.indexOf("_")>=0) scripId = jqIndex.substring(0, jqIndex.indexOf("_"));
				else scripId = jqIndex;
			}
		}
		if (scripId==null || scripId.length()==0) { // Try from id
			scripId = request.getParameter("id");
		}
		return scripId;
    }

	public String getScripCode() {
		return scripCode;
	}

	public void setScripCode(String scripCode) {
		this.scripCode = scripCode;
	}

	public Scrip getScrip() {
		return scrip;
	}

	public void setScrip(Scrip scrip) {
		this.scrip = scrip;
	}
}

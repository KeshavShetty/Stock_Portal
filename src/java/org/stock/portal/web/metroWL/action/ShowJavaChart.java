package org.stock.portal.web.metroWL.action;

import org.apache.log4j.Logger;
import org.stock.portal.domain.Scrip;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.util.Constants;

public class ShowJavaChart extends BaseAction {
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(ShowJavaChart.class.getName());
      
	//private String scripCode = "BSE-BSE30";
	private String scripCode = "";
	
	public String getScripCode() {
		return scripCode;
	}

	public void setScripCode(String scripCode) {
		this.scripCode = scripCode;
	}

	public String loadChart(){
		String returnPage = "showJavaChart";
		
		if (scripCode==null || scripCode.length()==0) {
			Scrip scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
	    	
	    	if (scrip!=null) {
	    		if (scrip.getNseCode()!=null) {
	    			scripCode = "NSE-" + scrip.getNseCode();
	    		} else if (scrip.getBseCode()!=null) {
	    			scripCode = "BSE-" + scrip.getBseCode();
	    		}
	    	}
		}
    	System.out.println(" showJavaChart scripCode="+scripCode);
		log.info("In ShowJavaChart loadChart() returnPage="+returnPage);
		return returnPage;
	}
}

package org.stock.portal.web.metroWL.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.FinancialResult;
import org.stock.portal.domain.Scrip;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class RatiochartAction extends BaseAction {

	Logger log = Logger.getLogger(RatiochartAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String scripCode;
   
    private String fromDate;
    private String toDate;
    
    private String sourceFormName;
    private String divToFill;
    
    private static Map<String, String> ratioParamsMap = new HashMap<String, String>();
    
    private String selectedFirstParameter;
    private String selectedNextParameter;
    
    private Map ratioDataMap;
        
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
      
    public RatiochartAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	try {
    		Scrip scrip = null;
    		String scripId = getScripIdFromRequest();
			if (scripId!=null && scripId.length()>0) {
				scrip = scripManager.getScripById(Long.parseLong(scripId));
			} else {	    	
				scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
			}
    		if (scrip!=null && scrip.getBseCode()!=null && !scrip.getBseCode().startsWith("9")) {
    			scripCode = "BSE-"+scrip.getBseCode();
    		} else if (scrip!=null && scrip.getNseCode()!=null) {
        			scripCode = "NSE-"+scrip.getNseCode();
    		} else {
    			scripCode = "NSE-RELIANCE";
    		}
    		populateRatioParamsMap();
    		this.selectedFirstParameter = "cmp";
    		this.selectedNextParameter = "avg_volume";
    		
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    		
    		Calendar cal = Calendar.getInstance();
    		toDate = sdf.format(cal.getTime());
    		cal.add(Calendar.MONTH, -3);
    		fromDate = sdf.format(cal.getTime());
    		
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
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
    
    private void populateRatioParamsMap() {
    	if (ratioParamsMap==null) ratioParamsMap = new HashMap<String, String>();
    	if (ratioParamsMap.size()==0) {
    		ratioParamsMap.put("average_turnover", "AvgTurnoverPerDay");
    		ratioParamsMap.put("avg_volume", "AvgVolume2W" );
	    	ratioParamsMap.put("cmp", "CMP");
	    	ratioParamsMap.put("ema30day", "30dEMA");
	    	ratioParamsMap.put("ema100day", "100dEMA");
	    	ratioParamsMap.put("eps_ttm", "EPS");
	    	ratioParamsMap.put("growth_rank", "Growth Rank");
	    	ratioParamsMap.put("number_day_close_above30dema", "Ratio6MCloseAbove30dEMA");
	    	ratioParamsMap.put("pe", "PE");
	    	ratioParamsMap.put("price_fiveday_before", "PriceBefore5d");
	    	ratioParamsMap.put("price_onemonth_before", "PriceBefore1M");
	    	ratioParamsMap.put("price_threemonth_before", "PriceBefore3M");
	    	ratioParamsMap.put("support_price_3m", "SupportPrice3M");
	    	ratioParamsMap.put("support_volume_leftover_3m", "SupportVolume");
	    	ratioParamsMap.put("wlcount", "WL-Count");
	    	ratioParamsMap.put("wl_roi_score", "WL-ROI-Scrore");
	    	ratioParamsMap.put("wl_score_rank", "WL-Score-Rank");
	    	
//	    	ratioParamsMap.put("", "");
//	    	ratioParamsMap.put("", "");
//	    	ratioParamsMap.put("", "");
//	    	ratioParamsMap.put("", "");
//	    	ratioParamsMap.put("", "");
//	    	ratioParamsMap.put("", "");
//	    	ratioParamsMap.put("", "");
//	    	ratioParamsMap.put("", "");
//	    	ratioParamsMap.put("", "");
	    	
    	}
    }
    

    public String showChart() {    	
        String retVal = "showChart";
        System.out.println("~~~~~ showGraph ");
        try {
        	if (this.scripCode!=null && this.scripCode.trim().length()>0 ) { 
        		Scrip selectedScrip = null;
        		if (scripCode!=null && scripCode.startsWith("BSE")) {
        			selectedScrip = scripManager.getScripByBSECode(scripCode.substring(4));
        		} else if (scripCode!=null && scripCode.startsWith("NSE")) {
        			selectedScrip = scripManager.getScripByNSECode(scripCode.substring(4));
        		}
        		if (selectedScrip!=null) {
        			ratioDataMap = scripManager.getScripRatioData(selectedScrip.getId(), fromDate, toDate, selectedFirstParameter, selectedNextParameter);
        		}
        	} 
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        log.info("Returning "+retVal);
        return retVal;
    }	

	public String getSourceFormName() {
		return sourceFormName;
	}

	public void setSourceFormName(String sourceFormName) {
		this.sourceFormName = sourceFormName;
	}

	public String getDivToFill() {
		return divToFill;
	}

	public void setDivToFill(String divToFill) {
		this.divToFill = divToFill;
	}

	public String getScripCode() {
		return scripCode;
	}

	public void setScripCode(String scripCode) {
		this.scripCode = scripCode;
	}
	
	public String getFirstParameterData() {
		String retStr = "";
		try {
			List firstData = (List)ratioDataMap.get("firstRatioData");
			for(int i=0;i<firstData.size();i++) {
				Object aRes = firstData.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				retStr = retStr + aRes.toString();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("In getFirstParameterData retStr="+retStr);
		return retStr;
	}
	
	public String getNextParameterData() {
		String retStr = "";
		try {
			List firstData = (List)ratioDataMap.get("nextRatioData");
			for(int i=0;i<firstData.size();i++) {
				Object aRes = firstData.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				retStr = retStr + aRes.toString();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("In getNextParameterData retStr="+retStr);
		return retStr;
	}	
	
	public String getDatesAsString() {
		String retStr = "";
		try {
			List firstData = (List)ratioDataMap.get("dateData");
			for(int i=0;i<firstData.size();i++) {
				Object aRes = firstData.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				retStr = retStr + "'"+ aRes.toString()+ "'";
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("In getDatesAsString retStr="+retStr);
		return retStr;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Map<String, String> getRatioParamsMap() {
		return ratioParamsMap;
	}

	public void setRatioParamsMap(Map<String, String> ratioParamsMap) {
		this.ratioParamsMap = ratioParamsMap;
	}

	public String getSelectedFirstParameter() {
		return selectedFirstParameter;
	}

	public void setSelectedFirstParameter(String selectedFirstParameter) {
		this.selectedFirstParameter = selectedFirstParameter;
	}

	public String getSelectedNextParameter() {
		return selectedNextParameter;
	}

	public void setSelectedNextParameter(String selectedNextParameter) {
		this.selectedNextParameter = selectedNextParameter;
	}
}

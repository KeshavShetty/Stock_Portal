package org.stock.portal.web.metroWL.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.FinancialResult;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.ZeordhaDomVO;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class DomAnalysisAction extends BaseAction {

	Logger log = Logger.getLogger(DomAnalysisAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String scripCode;
    
    private boolean finResultType;
    
    private String fromTime;
    
    private String toTime;
    
    
    private List<ZeordhaDomVO> resultList;
    
    private List<Scrip> latestScripList;    
    
    private Scrip selectedScrip = null;
    
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
      
    public DomAnalysisAction() {
        super();
    }
    
    public String prepareDomView() {    	
    	try {    		
    		Scrip scrip = null;
    		String scripId = getScripIdFromRequest();
			if (scripId!=null && scripId.length()>0) {
				scrip = scripManager.getScripById(Long.parseLong(scripId));
			} else {	    	
				scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
			}
    		if (scrip!=null && scrip.getNseCode()!=null) {
        			scripCode = "NSE-"+scrip.getNseCode();
    		} else {
    			scripCode = "NSE-RELIANCE";
    		}
    		finResultType = true;
    		Calendar cal = Calendar.getInstance();
    		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");	

    		this.toTime = dateFormat.format(cal.getTime()) + " 09:30:00";
    		    		
    		this.fromTime = dateFormat.format(cal.getTime()) + " 09:15:00";
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
   

    public String showGraph() {    	
        String retVal = "showGraph";
        System.out.println("~~~~~ showGraph ");
        try {
        	if (this.scripCode!=null && this.scripCode.trim().length()>0 ) {        		
        		if (scripCode!=null && scripCode.startsWith("BSE")) {
        			selectedScrip = scripManager.getScripByBSECode(scripCode.substring(4));
        		} else if (scripCode!=null && scripCode.startsWith("NSE")) {
        			selectedScrip = scripManager.getScripByNSECode(scripCode.substring(4));
        		}
        		System.out.println("~~~~~~~~~~this.showConsolidatedResult="+this.finResultType);
        		if (selectedScrip!=null) {
        			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        			
        			resultList = dataManager.getDomData(selectedScrip.getId(), dateFormat.parse(this.fromTime), dateFormat.parse(this.toTime));
        			System.out.println("~resultList size=" + resultList.size());
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

	public String getScripCode() {
		return scripCode;
	}

	public void setScripCode(String scripCode) {
		this.scripCode = scripCode;
	}
	
	public List<ZeordhaDomVO> getResultList() {
		return resultList;
	}

	public void setResultList(List<ZeordhaDomVO> resultList) {
		this.resultList = resultList;
	}

	public List<Scrip> getLatestScripList() {
		return latestScripList;
	}

	public void setLatestScripList(List<Scrip> latestScripList) {
		this.latestScripList = latestScripList;
	}

	public Scrip getSelectedScrip() {
		return selectedScrip;
	}

	public void setSelectedScrip(Scrip selectedScrip) {
		this.selectedScrip = selectedScrip;
	}
	
	public String getQuoteTimeAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			ZeordhaDomVO aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			String qtrAsString = aRes.getTickQuoteTime()+"";
			retStr = retStr + "'" + qtrAsString +"'";
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getLtpAsString() {
		String retStr = "";
		try {
			for(int i=0;i<resultList.size();i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = aRes.getLtp()+"";
				retStr = retStr + qtrAsString;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getVolumeBuySellDiff() {
		String retStr = "";
		try {
			for(int i=0;i<resultList.size();i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = aRes.getVolumeBuySellDiff()+"";
				retStr = retStr + qtrAsString ;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getBuyVvwapAsString() {
		String retStr = "";
		try {
			for(int i=0;i<resultList.size();i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = aRes.getBuyVvwap()+"";
				retStr = retStr + qtrAsString ;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getSellVvwapAsString() {
		String retStr = "";
		try {
			for(int i=0;i<resultList.size();i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = aRes.getSellVvwap()+"";
				retStr = retStr + qtrAsString ;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getLastTradedQtyAsString() {
		String retStr = "";
		try {
			for(int i=0;i<resultList.size();i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = aRes.getLastTradedQty()+"";
				retStr = retStr + qtrAsString ;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getBuyMeanAsString() {
		String retStr = "";
		try {
			for(int i=0;i<resultList.size();i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = aRes.getBuyMean()+"";
				retStr = retStr + qtrAsString ;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getSellMeanAsString() {
		String retStr = "";
		try {
			for(int i=0;i<resultList.size();i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = aRes.getSellMean()+"";
				retStr = retStr + qtrAsString ;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getBuyTotalAsString() {
		String retStr = "";
		try {
			for(int i=0;i<resultList.size();i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = aRes.getBuyTotal()+"";
				retStr = retStr + qtrAsString ;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getSellTotalAsString() {
		String retStr = "";
		try {
			for(int i=0;i<resultList.size();i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = aRes.getSellTotal()+"";
				retStr = retStr + qtrAsString ;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getBuyVwapGapAsString() {
		String retStr = "";
		try {
			for(int i=0;i<resultList.size();i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = ((aRes!=null?aRes.getLtp():0) - (aRes!=null?aRes.getBuyVvwap():0))+"";
				retStr = retStr + qtrAsString ;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getSellVwapGapAsString() {
		String retStr = "";
		try {
			for(int i=0;i<resultList.size();i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = ((aRes!=null?aRes.getSellVvwap():0) - (aRes!=null?aRes.getLtp():0))+"";
				retStr = retStr + qtrAsString ;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getVolumeTradedPerTickAsString() {
		String retStr = "0";
		try {
			for(int i=0;i<resultList.size()-1;i++) {
				ZeordhaDomVO aRes = resultList.get(i);
				ZeordhaDomVO nextRes = resultList.get(i+1);
				if (retStr.length()!=0) retStr = retStr + ",";
				String qtrAsString = (nextRes.getVolumeTradedToday()-aRes.getVolumeTradedToday())+"";
				retStr = retStr + qtrAsString ;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public boolean getFinResultType() {
		return finResultType;
	}

	public void setFinResultType(boolean finResultType) {
		this.finResultType = finResultType;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
}

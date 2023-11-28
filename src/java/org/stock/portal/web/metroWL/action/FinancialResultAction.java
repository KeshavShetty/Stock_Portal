package org.stock.portal.web.metroWL.action;

import java.util.List;

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


public class FinancialResultAction extends BaseAction {

	Logger log = Logger.getLogger(FinancialResultAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String scripCode;
    
    private boolean finResultType;
    
    private String orderBy;
    private String orderType;
    
    private String sourceFormName;
    private String divToFill;
    
    private Long selectedScripId;
    
    private List<FinancialResult> resultList;
    
    private List<Scrip> latestScripList;    
    
    private Scrip selectedScrip = null;
    
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
      
    public FinancialResultAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	try {
    		this.orderBy = "scrip.name";
    		this.orderType = "ASC";
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
    		finResultType = true;
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
    
    public String search() {    	
        String retVal = "resultPage";
        System.out.println("~~~~~ Search ");
        try {        	
        	if (this.scripCode!=null && this.scripCode.trim().length()>0 ) {        		
        		if (scripCode!=null && scripCode.startsWith("BSE")) {
        			selectedScrip = scripManager.getScripByBSECode(scripCode.substring(4));
        		} else if (scripCode!=null && scripCode.startsWith("NSE")) {
        			selectedScrip = scripManager.getScripByNSECode(scripCode.substring(4));
        		}
        		System.out.println("~~~~~~~~~~this.showConsolidatedResult="+this.finResultType+" selectedScrip="+selectedScrip);
        		if (selectedScrip!=null) {
        			resultList = dataManager.getFinancialResult(selectedScrip.getId(), this.finResultType);
        			// Reverse the order, So that in UI it will be chronological order
        			for (int i=0;i<resultList.size()/2;i++) {
        				FinancialResult aResult = resultList.get(i);
        				resultList.set(i, resultList.get(resultList.size()-i-1));
        				resultList.set(resultList.size()-i-1, aResult);
        			}
        		}
        	}        	
        	System.out.println("resultList="+resultList.size());
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        log.info("Returning "+retVal);
        return retVal;
    }
    
    
    
    public String latestResultSearch() {    	
        String retVal = "latestResults";
        try {
        	if (this.orderBy==null) this.orderBy = "resultDate";
    		if (this.orderType==null) this.orderType = "DESC";    		
        	log.info("Oops orderby="+this.orderBy+" Type="+this.orderType);
        	latestScripList = dataManager.getLatestFinancialResultDeclaredScrips(this.orderBy, this.orderType);
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        log.info("Returning "+retVal);
        return retVal;
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
        			resultList = dataManager.getFinancialResult(selectedScrip.getId(), this.finResultType);
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
    
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public Long getSelectedScripId() {
		return selectedScripId;
	}

	public void setSelectedScripId(Long selectedScripId) {
		this.selectedScripId = selectedScripId;
	}
	
	public List<FinancialResult> getResultList() {
		return resultList;
	}

	public void setResultList(List<FinancialResult> resultList) {
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
	
	public String getPeriodsAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			String qtrAsString = aRes.getFinanciaReportQuarterId()+"";
			retStr = retStr + "'" + qtrAsString.substring(0,4) + "-" + qtrAsString.substring(4,6)+ "(Q" + qtrAsString.substring(7,8) + ")'";
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getRevenueAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getRevenue()!=null) retStr = retStr + aRes.getRevenue();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getOtherIncomeAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getOtherIncome()!=null) retStr = retStr + aRes.getOtherIncome();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getTotalIncomeAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getTotalIncome()!=null) retStr = retStr + aRes.getTotalIncome();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getExpenditureAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getExpenditure()!=null) retStr = retStr + (aRes.getExpenditure()*-1.0f);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getPbdtAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getPbdt()!=null) retStr = retStr + aRes.getPbdt();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getDepreciationAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getDepreciation()!=null) retStr = retStr + (aRes.getDepreciation()*-1.0f);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getPbtAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getPbt()!=null) retStr = retStr + aRes.getPbt();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getTaxAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getTax()!=null) retStr = retStr + (aRes.getTax()*-1.0f);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getNetProfitAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getNetProfit()!=null) retStr = retStr + aRes.getNetProfit();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}

	public String getQtrGrowthAsString() {
		String retStr = "";
		try {
		float lastQtrNet = 0;
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (i!=0) {
				float changeInNet = (aRes.getNetProfit()-lastQtrNet)*100f/lastQtrNet;				
				retStr = retStr + "," + changeInNet;
			} else {
				retStr = retStr + "0";
			}
			lastQtrNet = aRes.getNetProfit();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getSameQtrGrowthAsString() {
		String retStr = "";
		try {
		float prev0QtrNet = 0;
		float prev1QtrNet = 0;
		float prev2QtrNet = 0;
		float prev3QtrNet = 0;
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			
			if (i>=4) {	
				float changeInNet = (aRes.getNetProfit()-prev3QtrNet)*100f/(prev3QtrNet>0?prev3QtrNet:(-1.0f*prev3QtrNet));
				//System.out.println("Current et profit="+aRes.getNetProfit()+" prev3QtrNet="+prev3QtrNet+" changeInNet="+changeInNet);
				retStr = retStr + "," + changeInNet;
			} else {
				if (retStr.length()!=0) retStr = retStr + ",";
				retStr = retStr + "0";
			}
			prev3QtrNet = prev2QtrNet;
			prev2QtrNet = prev1QtrNet;
			prev1QtrNet = prev0QtrNet;
			prev0QtrNet = aRes.getNetProfit();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getEpsAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getEps()!=null) retStr = retStr + aRes.getEps();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getCepsAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getCeps()!=null) retStr = retStr + aRes.getCeps();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getOpmPercentageAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getOpmPercentage()!=null) retStr = retStr + aRes.getOpmPercentage();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}

	public String getNpmPercentageAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getNpmPercentage()!=null) retStr = retStr + aRes.getNpmPercentage();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getQtClosePriceAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getQtClosePrice()!=null) retStr = retStr + aRes.getQtClosePrice();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getPeAsString() {
		String retStr = "";
		try {
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			if (retStr.length()!=0) retStr = retStr + ",";
			if (aRes.getQtClosePrice()!=null && aRes.getEps()!=null) retStr = retStr + (aRes.getQtClosePrice()/aRes.getEps());
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	public String getSameQtrRevenueGrowthAsString() {
		String retStr = "";
		try {
		float prev0QtrRevenue = 0;
		float prev1QtrRevenue = 0;
		float prev2QtrRevenue = 0;
		float prev3QtrRevenue = 0;
		for(int i=0;i<resultList.size();i++) {
			FinancialResult aRes = resultList.get(i);
			
			float curRevenue = 0;
			if (i>=4) {	
				try {
					float changeInRev = (aRes.getRevenue()-prev3QtrRevenue)*100f/(prev3QtrRevenue>0?prev3QtrRevenue:(-1.0f*prev3QtrRevenue));
					//System.out.println("Current et profit="+aRes.getNetProfit()+" prev3QtrNet="+prev3QtrNet+" changeInNet="+changeInNet);
					retStr = retStr + "," + changeInRev;
					curRevenue = aRes.getRevenue();
				} catch(Exception ex) {
					retStr = retStr + ",0";
				}
				
			} else {
				if (retStr.length()!=0) retStr = retStr + ",";
				retStr = retStr + "0";
			}
			prev3QtrRevenue = prev2QtrRevenue;
			prev2QtrRevenue = prev1QtrRevenue;
			prev1QtrRevenue = prev0QtrRevenue;
			prev0QtrRevenue = curRevenue;
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
}

package org.stock.portal.web.metroWL.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.TreePerformance;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class TreePerformanceAction extends BaseAction {

	Logger log = Logger.getLogger(TreePerformanceAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String scripCode;
    
    private String orderBy;
    private String orderType;
    
    private String sourceFormName;
    private String divToFill;
    
    private Long selectedScripId;
    
    private List<TreePerformance> resultList;
    
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
      
    public TreePerformanceAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	try {
    		this.orderBy = "joinIncrement";
    		this.orderType = "DESC";
    		
    		Scrip scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
    		if (scrip!=null && scrip.getNseCode()!=null) {
    			scripCode = "NSE-"+scrip.getNseCode();
    		} else {
    			scripCode = "NSE-CNX Nifty";
    		}
    		
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
    }

    public String search() {    	
        String retVal = "resultPage";
        try {        	
        	if (this.scripCode!=null && this.scripCode.trim().length()>0 ) {
        		Scrip aScrip = scripManager.getScripByNSECode(scripCode.substring(4));
        		if (aScrip!=null) resultList = dataManager.getTreePerformanceForScrip(aScrip.getId(),this.orderBy, this.orderType);
        		if (resultList==null || resultList.size()==0) {
        			processOffline(aScrip.getId());
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
    
    public String subSearch() {    	
        String retVal = "subResultPage";
        try {
        	
        	if (selectedScripId!=null) resultList = dataManager.getTreePerformanceForScrip(selectedScripId,this.orderBy, this.orderType);
        	if (resultList==null || resultList.size()==0) {
    			processOffline(selectedScripId);
    		}
        	System.out.println("Sub resultList="+resultList.size());
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
	
	private void processOffline(Long sourceScrip) {
		try {
			System.out.println("PopulateThruMDB!!!!!!!!!!!!!!! for "+sourceScrip);
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap.put("Action", "PopulateTreePerformance");
			messageMap.put("SourceScripId", sourceScrip);
			dataManager.sendMessage(messageMap);	
			System.out.println("Message Sent!!!!!!!!!!!!!!! for "+sourceScrip);
		} catch (Exception e) { 
        	e.printStackTrace();
        }
	}

	public List<TreePerformance> getResultList() {
		return resultList;
	}

	public void setResultList(List<TreePerformance> resultList) {
		this.resultList = resultList;
	}

	
	
}

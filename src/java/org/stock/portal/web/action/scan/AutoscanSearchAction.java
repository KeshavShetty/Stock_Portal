package org.stock.portal.web.action.scan;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.SessionAware;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.AutoscanMaster;
import org.stock.portal.domain.AutoscanResult;
import org.stock.portal.domain.dto.AutoscanSearchCriteriaDTO;
import org.stock.portal.service.autoscan.AutoscanManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class AutoscanSearchAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(AutoscanSearchAction.class.getName());
    private static final long serialVersionUID = 4205166422526662903L;    
    List<AutoscanResult> autoscanList = null;
    
    List<AutoscanMaster> scanCodeList = null;

    private String fromDate;    
    private String toDate;
    
    private Integer taTypeId;
    
    private String watchlistId;   
    private Long scanCodeId;

	private String orderBy;
    private String orderType; //Ascending/Descending      

    private Integer numberOfRecords;
    
    private Long averageVolume;
    
    private String exchangeCodeId;
    
	public Long getAverageVolume() {
		return averageVolume;
	}

	public void setAverageVolume(Long averageVolume) {
		this.averageVolume = averageVolume;
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
    
	public String getWatchlistId() {
		return watchlistId;
	}

	public void setWatchlistId(String watchlistId) {
		this.watchlistId = watchlistId;
	}
	
	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public List<AutoscanResult> getAutoscanList() {
		return autoscanList;
	}

	public void setAutoscanList(List<AutoscanResult> autoscanList) {
		this.autoscanList = autoscanList;
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

	public Long getScanCodeId() {
		return scanCodeId;
	}

	public void setScanCodeId(Long scanCodeId) {
		this.scanCodeId = scanCodeId;
	}

	public List<AutoscanMaster> getScanCodeList() {
		return scanCodeList;
	}

	public void setScanCodeList(List<AutoscanMaster> scanCodeList) {
		this.scanCodeList = scanCodeList;
	}

	public Integer getTaTypeId() {
		return taTypeId;
	}

	public void setTaTypeId(Integer taTypeId) {
		this.taTypeId = taTypeId;
	}

	public String getExchangeCodeId() {
		return exchangeCodeId;
	}

	public void setExchangeCodeId(String exchangeCodeId) {
		this.exchangeCodeId = exchangeCodeId;
	}

	/** The scrip accessor bean. */
    @InjectEJB (name ="AutoscanManager")
    AutoscanManager autoscanManager;
   
    public AutoscanSearchAction() {
        super();
    }

    @Action(value = "/prepareAutoscanSearch")
    public String prepareAutoscanSearch() {
    	//this.setOrderBy("(intraSummary.closePrice-intraSummary.previousdayClosePrice)*100/intraSummary.previousdayClosePrice");
    	this.setOrderType("DESC"); 
    	 try {
    		 scanCodeList = autoscanManager.getAutoscanMasterlist(); 
    	 } catch(BusinessException e){
    		 e.printStackTrace();
    	 }
        return "prepareAutoscanSearch";
    }
    
    public String execute() {
    	log.debug("--->Inside prepareAutoscanSearch");        
        String retVal = SUCCESS;
        try {
        	AutoscanSearchCriteriaDTO autoscanSearchCriteriaDTO = new AutoscanSearchCriteriaDTO();  
        	SimpleDateFormat uiDateFormat = new SimpleDateFormat("dd/MM/yyyy");        	
        	
        	if (this.fromDate!=null && this.fromDate.length()>0)  { 
        		autoscanSearchCriteriaDTO.setFromDate(uiDateFormat.parse(this.getFromDate())); 
        	}
        	if (this.toDate!=null && this.toDate.length()>0)  { 
        		autoscanSearchCriteriaDTO.setToDate(uiDateFormat.parse(this.getToDate())); 
        	}        	
        	if (this.watchlistId!=null && this.watchlistId.length()>0)  { 
        		autoscanSearchCriteriaDTO.setWatchlistId(Long.parseLong(this.watchlistId)); 
        	}
        	if (this.scanCodeId!=null)  { 
        		autoscanSearchCriteriaDTO.setScanCodeId(this.scanCodeId); 
        	}        	
        	if (this.averageVolume!=null) {
        		autoscanSearchCriteriaDTO.setAverageVolume(this.averageVolume);
        	}
        	
        	if (this.getTaTypeId()!=null) {
        		autoscanSearchCriteriaDTO.setTaTypeId(this.getTaTypeId());
        	}
        	log.info("getExchangeCodeId()="+this.getExchangeCodeId());
        	if (this.getExchangeCodeId()!=null && this.getExchangeCodeId().equals("BSE")) {
        		autoscanSearchCriteriaDTO.setExchangeCodeId(Boolean.FALSE);
        	} else if (this.getExchangeCodeId()!=null && this.getExchangeCodeId().equals("NSE")) {
        		autoscanSearchCriteriaDTO.setExchangeCodeId(Boolean.TRUE);
        	}
        	if (this.numberOfRecords==null) this.numberOfRecords=2000;
        	autoscanSearchCriteriaDTO.setNumberOfRecords(this.numberOfRecords);
        	if (this.orderBy!=null && this.orderBy.length()>0)  { autoscanSearchCriteriaDTO.setOrderBy(this.orderBy); }
        	else { this.setOrderBy("autoscan.scrip.name"); this.setOrderType("ASC"); autoscanSearchCriteriaDTO.setOrderBy(this.orderBy); }
        	if (this.orderType!=null && this.orderType.length()>0)  { autoscanSearchCriteriaDTO.setOrderType(this.orderType); }
        	else { this.setOrderType("ASC"); autoscanSearchCriteriaDTO.setOrderType(this.orderType); }
        	
        	autoscanList = autoscanManager.searchAutoscanByCriteria(autoscanSearchCriteriaDTO);
        	scanCodeList = autoscanManager.getAutoscanMasterlist(); // This need to changed to application config
        	log.info("In Action AutoScanList="+autoscanList.size());
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.scrip.search.error"));
        } 
        return retVal;
    }	
}

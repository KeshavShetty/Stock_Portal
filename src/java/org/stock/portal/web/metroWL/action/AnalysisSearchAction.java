package org.stock.portal.web.metroWL.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.AutoscanResult;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.metroWL.dto.AnalysisSearchCriteriaDTO;
import org.stock.portal.service.autoscan.AutoscanManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class AnalysisSearchAction extends BaseAction {

	Logger log = Logger.getLogger(AnalysisSearchAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
		
    private String scripIds;
    private Long selectedScripId;
    
    private String orderBy;
    private String orderType;
    private Integer pageNumber=1;
    private Integer recordsPerPage=10;
    
    private String fromDate;
    private String toDate;
    
    private String sourceFormName;
    private String divToFill;    
        
    private Long totalRecords;
    
    private Integer analysisType;
   
    List<Scrip> scripList;
    
    List<AutoscanResult> analysisList;
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
    
    @InjectEJB (name ="AutoscanManager")
    AutoscanManager autoscanManager;
   
    public AnalysisSearchAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	try {
    		//scripList = scripManager.getScripByIds(scripIds);
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
    }

    public String search() {    	
        String retVal = "resultPage";
        try {
        	AnalysisSearchCriteriaDTO analysisSrchDto = new AnalysisSearchCriteriaDTO();
        	if (this.selectedScripId!=null ) analysisSrchDto.setScripId(this.selectedScripId);
        	else if (this.scripIds!=null ) analysisSrchDto.setScripIds(this.scripIds.trim());
        	// Todo: Set from date and todate or scripwise

        	if (this.fromDate!=null && this.fromDate.trim().length()>0 ) analysisSrchDto.setFromDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.fromDate.trim()));        	
        	if (this.toDate!=null && this.toDate.trim().length()>0 ) analysisSrchDto.setToDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.toDate.trim()));
        	
        	if (this.analysisType!=null ) analysisSrchDto.setAnalysisType(this.analysisType);
        	
        	if (this.getOrderBy()!=null && this.getOrderBy().length()>0) analysisSrchDto.setOrderBy(this.orderBy);
        	else { 
        		this.setOrderBy("signalDate"); this.setOrderType("Desc");
        		analysisSrchDto.setOrderBy(this.orderBy);
        		analysisSrchDto.setOrderType(this.orderType);
        	}
        	if (this.getOrderType()!=null && this.getOrderType().length()>0) analysisSrchDto.setOrderType(this.orderType);
        	if (this.getPageNumber()!=null) analysisSrchDto.setPageNumber(this.pageNumber); else analysisSrchDto.setPageNumber(1);
        	if (this.getRecordsPerPage()!=null) analysisSrchDto.setRecordPerPage(this.recordsPerPage); 
        	
        	Map<String, Object> result = autoscanManager.searchAnalysis(analysisSrchDto);
        	analysisList = (List<AutoscanResult>)result.get("Result");
        	System.out.println("analysisList size="+analysisList.size());
        	totalRecords = (Long)result.get("RecordsCount");
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        log.info("Returning "+retVal);
        return retVal;
    }

	public String getScripIds() {
		return scripIds;
	}

	public void setScripIds(String scripIds) {
		this.scripIds = scripIds;
	}

	public Long getSelectedScripId() {
		return selectedScripId;
	}

	public void setSelectedScripId(Long selectedScripId) {
		this.selectedScripId = selectedScripId;
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

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
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

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<Scrip> getScripList() {
		return scripList;
	}

	public void setScripList(List<Scrip> scripList) {
		this.scripList = scripList;
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

	public List<AutoscanResult> getAnalysisList() {
		return analysisList;
	}

	public void setAnalysisList(List<AutoscanResult> analysisList) {
		this.analysisList = analysisList;
	}

	public Integer getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType(Integer analysisType) {
		this.analysisType = analysisType;
	}
}

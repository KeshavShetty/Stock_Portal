package org.stock.portal.web.metroWL.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.CandlePatternLibrary;
import org.stock.portal.domain.metroWL.dto.CandlePatternSearchCriteriaDTO;
import org.stock.portal.service.autoscan.AutoscanManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class CandlePatternSearchAction extends BaseAction {

	Logger log = Logger.getLogger(CandlePatternSearchAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private int minUp;
    private int minDown;    
    private float minCountRank;
    
    private String orderBy;
    private String orderType;
    private Integer pageNumber=1;
    private Integer recordsPerPage=10;
    
    private String sourceFormName;
    private String divToFill;   

    private Long totalRecords;
    
    private List<CandlePatternLibrary> patternList;
    
    @InjectEJB (name ="AutoscanManager")
    AutoscanManager autoscanManager;
   
    public CandlePatternSearchAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	try {
    		
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
    }

    public String searchCandle() {    	
        String retVal = "resultPage";
        try {
        	CandlePatternSearchCriteriaDTO criteriaDto = new CandlePatternSearchCriteriaDTO();
        	
        	System.out.println("this.minUp="+this.minUp);
        	
        	criteriaDto.setMinUp(this.minUp);
        	criteriaDto.setMinDown(this.minDown);
        	criteriaDto.setMinCountRank(this.minCountRank);

        	if (this.getOrderBy()!=null && this.getOrderBy().length()>0) criteriaDto.setOrderBy(this.orderBy);
        	else {
        		this.setOrderBy("countRank"); this.setOrderType("Desc");
        		criteriaDto.setOrderBy(this.orderBy);
        		criteriaDto.setOrderType(this.orderType);
        	}
        	if (this.getOrderType()!=null && this.getOrderType().length()>0) criteriaDto.setOrderType(this.orderType);
        	if (this.getPageNumber()!=null) criteriaDto.setPageNumber(this.pageNumber); else criteriaDto.setPageNumber(1);
        	if (this.getRecordsPerPage()!=null) criteriaDto.setRecordPerPage(this.recordsPerPage); 
        	
        	Map<String, Object> result = autoscanManager.searchCandlePatternByCriteria(criteriaDto);
        	patternList = (List<CandlePatternLibrary>)result.get("Result");
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

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
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
		
	public int getMinUp() {
		return minUp;
	}

	public void setMinUp(int minUp) {
		this.minUp = minUp;
	}

	public int getMinDown() {
		return minDown;
	}

	public void setMinDown(int minDown) {
		this.minDown = minDown;
	}

	public float getMinCountRank() {
		return minCountRank;
	}

	public void setMinCountRank(float minCountRank) {
		this.minCountRank = minCountRank;
	}

	public List<CandlePatternLibrary> getPatternList() {
		return patternList;
	}

	public void setPatternList(List<CandlePatternLibrary> patternList) {
		this.patternList = patternList;
	}	
	
}

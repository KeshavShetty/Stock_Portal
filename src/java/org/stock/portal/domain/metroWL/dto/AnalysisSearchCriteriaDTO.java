package org.stock.portal.domain.metroWL.dto;

import java.util.Date;


public class AnalysisSearchCriteriaDTO extends AbstractCriteriaDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date fromDate;
	private Date toDate;
    private Long scripId;
    private String scripIds;  
    
    private Integer analysisType;
	
    public Date getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public Date getToDate() {
		return toDate;
	}


	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	public Long getScripId() {
		return scripId;
	}


	public void setScripId(Long scripId) {
		this.scripId = scripId;
	}


	public String getScripIds() {
		return scripIds;
	}


	public void setScripIds(String scripIds) {
		this.scripIds = scripIds;
	}    
    
	public AnalysisSearchCriteriaDTO() {
		super();
	}


	public Integer getAnalysisType() {
		return analysisType;
	}


	public void setAnalysisType(Integer analysisType) {
		this.analysisType = analysisType;
	}

}

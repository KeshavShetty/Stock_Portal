package org.stock.portal.web.metroWL.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.NewsFeedScripMap;
import org.stock.portal.domain.NewsFeedSource;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.metroWL.dto.NewsSearchCriteriaDTO;
import org.stock.portal.service.news.NewsManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class NewsSearchAction extends BaseAction {

	Logger log = Logger.getLogger(NewsSearchAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
		
    private String scripIds;
    private Long selectedScripId;
    
    private Long feedSourceId;
    
    private String orderBy;
    private String orderType;
    private Integer pageNumber=1;
    private Integer recordsPerPage=10;
    
    private String fromDate;
    private String toDate;
    
    private String sourceFormName;
    private String divToFill;    
        
    private Long totalRecords;
   
    List<Scrip> scripList;
    
    List<NewsFeedScripMap> newsList;
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
    
    @InjectEJB (name ="NewsManager")
    NewsManager newsManager;
   
    public NewsSearchAction() {
    	super();
    }
    
    public String prepareSearch() {
    	return SUCCESS;    	
    }

    public String search() {    	
        String retVal = "resultPage";
        try {
        	NewsSearchCriteriaDTO newsSrchDto = new NewsSearchCriteriaDTO();
        	if (this.selectedScripId!=null ) newsSrchDto.setScripId(this.selectedScripId);
        	else if (this.scripIds!=null ) newsSrchDto.setScripIds(this.scripIds.trim());
        	
        	if (this.feedSourceId!=null ) newsSrchDto.setFeedSourceId(this.feedSourceId);
        	
        	if (this.fromDate!=null && this.fromDate.trim().length()>0 ) newsSrchDto.setFromDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.fromDate.trim()));        	
        	if (this.toDate!=null && this.toDate.trim().length()>0 ) newsSrchDto.setToDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.toDate.trim()));
        	
        	if (this.getOrderBy()!=null && this.getOrderBy().length()>0) newsSrchDto.setOrderBy(this.orderBy);
        	else { 
        		this.setOrderBy("newsFeedPost.publishDate"); this.setOrderType("Desc");
        		newsSrchDto.setOrderBy(this.orderBy);
        		newsSrchDto.setOrderType(this.orderType);
        	}
        	if (this.getOrderType()!=null && this.getOrderType().length()>0) newsSrchDto.setOrderType(this.orderType);
        	if (this.getPageNumber()!=null) newsSrchDto.setPageNumber(this.pageNumber); else newsSrchDto.setPageNumber(1);
        	if (this.getRecordsPerPage()!=null) newsSrchDto.setRecordPerPage(this.recordsPerPage); 
        	
        	Map<String, Object> result = newsManager.searchNews(newsSrchDto);
        	newsList = (List<NewsFeedScripMap>)result.get("Result");
        	System.out.println("newsList size="+newsList.size());
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

	public List<NewsFeedScripMap> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<NewsFeedScripMap> newsList) {
		this.newsList = newsList;
	}

	public Long getFeedSourceId() {
		return feedSourceId;
	}

	public void setFeedSourceId(Long feedSourceId) {
		this.feedSourceId = feedSourceId;
	}
}

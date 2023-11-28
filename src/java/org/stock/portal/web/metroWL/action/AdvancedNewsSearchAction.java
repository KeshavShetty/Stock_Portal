package org.stock.portal.web.metroWL.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.NewsFeedScripMap;
import org.stock.portal.domain.NewsFeedSource;
import org.stock.portal.domain.metroWL.dto.AdvancedNewsSearchCriteriaDTO;
import org.stock.portal.service.news.NewsManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class AdvancedNewsSearchAction extends BaseAction {

	Logger log = Logger.getLogger(AdvancedNewsSearchAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String fromDate;
    private String toDate;
    
    private String searchString;  
    
    private Long selectedSource;    
    
    private String scripName;
    private String bseCode;
    private String nseCode;
    
    private Float minCmp;
    private Float maxCmp;
    
    private Float minEps;
    private Float maxEps;
    
    private Float minPe;
    private Float maxPe;
    
    private Long averageVolume;
    
    private List<NewsFeedSource> feedSources;
    
    private String orderBy;
    private String orderType;
    private Integer pageNumber=1;
    private Integer recordsPerPage=10;
    
    private String sourceFormName;
    private String divToFill;   

    private Long totalRecords;
    
    private List<NewsFeedScripMap> newsResultList;
    
    @InjectEJB (name ="NewsManager")
    NewsManager newsManager;
   
    public AdvancedNewsSearchAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	try {
    		feedSources =  newsManager.getFeedSourceList();
    		this.recordsPerPage = 50;
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
    }

    public String search() {    	
        String retVal = "resultPage";
        try {
        	AdvancedNewsSearchCriteriaDTO newsSrchDto = new AdvancedNewsSearchCriteriaDTO();
        	
        	if (this.fromDate!=null && this.fromDate.trim().length()>0 ) newsSrchDto.setFromDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.fromDate.trim()));        	
        	if (this.toDate!=null && this.toDate.trim().length()>0 ) newsSrchDto.setToDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.toDate.trim()));
        	
        	if (this.searchString!=null ) newsSrchDto.setSearchString(this.searchString);
        	if (this.selectedSource!=null ) newsSrchDto.setSelectedSource(this.selectedSource);
        	
        	if (this.scripName!=null && this.scripName.trim().length()>0 ) newsSrchDto.setScripName(this.scripName.trim());
        	if (this.bseCode!=null && this.bseCode.trim().length()>0 ) newsSrchDto.setBseCode(this.bseCode.trim());
        	if (this.nseCode!=null && this.nseCode.trim().length()>0 ) newsSrchDto.setNseCode(this.nseCode.trim());
        	
        	if (this.minCmp!=null) newsSrchDto.setMinCmp(this.minCmp);
        	if (this.maxCmp!=null) newsSrchDto.setMaxCmp(this.maxCmp);        	
        	if (this.minEps!=null) newsSrchDto.setMinEps(this.minEps);
        	if (this.maxEps!=null) newsSrchDto.setMaxEps(this.maxEps);        	
        	if (this.minPe!=null) newsSrchDto.setMinPe(this.minPe);
        	if (this.maxPe!=null) newsSrchDto.setMaxPe(this.maxPe);
        	if (this.averageVolume!=null) newsSrchDto.setAverageVolume(this.averageVolume);
        	
        	if (this.getOrderBy()!=null && this.getOrderBy().length()>0) newsSrchDto.setOrderBy(this.orderBy);
        	else {
        		this.setOrderBy("newsFeedPost.publishDate"); this.setOrderType("Desc");
        		newsSrchDto.setOrderBy(this.orderBy);
        		newsSrchDto.setOrderType(this.orderType);
        	}
        	if (this.getOrderType()!=null && this.getOrderType().length()>0) newsSrchDto.setOrderType(this.orderType);
        	if (this.getPageNumber()!=null) newsSrchDto.setPageNumber(this.pageNumber); else newsSrchDto.setPageNumber(1);
        	if (this.getRecordsPerPage()!=null) newsSrchDto.setRecordPerPage(this.recordsPerPage); 
        	
        	Map<String, Object> result = newsManager.searchNewsByAdvancedCriteria(newsSrchDto);
        	newsResultList = (List<NewsFeedScripMap>)result.get("Result");
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

	public String getScripName() {
		return scripName;
	}

	public void setScripName(String scripName) {
		this.scripName = scripName;
	}

	public String getBseCode() {
		return bseCode;
	}

	public void setBseCode(String bseCode) {
		this.bseCode = bseCode;
	}

	public String getNseCode() {
		return nseCode;
	}

	public void setNseCode(String nseCode) {
		this.nseCode = nseCode;
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
	
	public Float getMinCmp() {
		return minCmp;
	}

	public void setMinCmp(Float minCmp) {
		this.minCmp = minCmp;
	}

	public Float getMaxCmp() {
		return maxCmp;
	}

	public void setMaxCmp(Float maxCmp) {
		this.maxCmp = maxCmp;
	}

	public Float getMinEps() {
		return minEps;
	}

	public void setMinEps(Float minEps) {
		this.minEps = minEps;
	}

	public Float getMaxEps() {
		return maxEps;
	}

	public void setMaxEps(Float maxEps) {
		this.maxEps = maxEps;
	}

	public Float getMinPe() {
		return minPe;
	}

	public void setMinPe(Float minPe) {
		this.minPe = minPe;
	}

	public Float getMaxPe() {
		return maxPe;
	}

	public void setMaxPe(Float maxPe) {
		this.maxPe = maxPe;
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
	
	public Long getSelectedSource() {
		return selectedSource;
	}

	public void setSelectedSource(Long selectedSource) {
		this.selectedSource = selectedSource;
	}

	public Long getAverageVolume() {
		return averageVolume;
	}

	public void setAverageVolume(Long averageVolume) {
		this.averageVolume = averageVolume;
	}

	public List<NewsFeedSource> getFeedSources() {
		return feedSources;
	}

	public void setFeedSources(List<NewsFeedSource> feedSources) {
		this.feedSources = feedSources;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public List<NewsFeedScripMap> getNewsResultList() {
		return newsResultList;
	}

	public void setNewsResultList(List<NewsFeedScripMap> newsResultList) {
		this.newsResultList = newsResultList;
	}
}

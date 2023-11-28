package org.stock.portal.web.metroWL.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.domain.dto.ScripPerformanceDTO;
import org.stock.portal.domain.metroWL.dto.PastAchieversSearchCriteriaDTO;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class PastAchieversSearchAction extends BaseAction {

	Logger log = Logger.getLogger(PastAchieversSearchAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String firstDate;
    private String lastDate;    
    private String exchange;
    
    private Float minCmp;
    private Float maxCmp;
    
    private Float minEps;
    private Float maxEps;
    
    private Float minPe;
    private Float maxPe;
    
    private Float minPerformance;
    private Float maxPerformance;
    
    private Float minVolumePerformance;
    private Float maxVolumePerformance;
    
    private Long averageVolume;
    
    private String orderBy;
    private String orderType;
    private Integer pageNumber=1;
    private Integer recordsPerPage=10;
    
    private String sourceFormName;
    private String divToFill;   

    private Long totalRecords;
    
    private List<Watchlist> userWatchlists;
    private Long selectedWatchlist;  
    private Long selectedSecondWatchlist;  
    
    private List<ScripPerformanceDTO> scripList;
    
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
   
    @InjectEJB (name ="WatchlistManager")
    WatchlistManager watchlistManager;
    
    public PastAchieversSearchAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	try {    		
    		this.recordsPerPage = 50;    		
    		
    		this.exchange = "NSE";
    		//this.maxCmp = 500f;
    		//this.minEps = 0f;
    		//this.minPe = 0f;
    		//this.averageVolume = 50000L;
    		//this.minPerformance=0f;
    		//this.maxPerformance=10f;
    		//this.minVolumePerformance=3f;
    		this.selectedWatchlist = 1L;
    		
    		Calendar curDate = Calendar.getInstance();
    		curDate.add(Calendar.DATE, 1);
    		Date latestDate = dataManager.getMaxDataDate(this.exchange, curDate.getTime());
    		this.lastDate = SPConstants.SPCORE_DATE_FORMAT.format(latestDate);
    		Date previousDate = dataManager.getMaxDataDate(this.exchange, latestDate);
    		this.firstDate = SPConstants.SPCORE_DATE_FORMAT.format(previousDate);
    		
    		User account = (User)this.session.get(Constants.LOGGED_IN_USER);
    		if (account!=null) {
    			userWatchlists = watchlistManager.getWatchlistByUserId(account.getId());
    		} else {
    			userWatchlists = new ArrayList<Watchlist>();
    			userWatchlists.add(new Watchlist(1L, "BTST"));
    		}
    		
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
    }

    public String search() {    	
        String retVal = "resultPage";
        try {
        	PastAchieversSearchCriteriaDTO criteriaDto = new PastAchieversSearchCriteriaDTO();
        	
        	if (this.firstDate!=null && this.firstDate.trim().length()>0 ) {
        		criteriaDto.setFirstDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.firstDate.trim()));        	
        	} else {
        		Calendar cal = Calendar.getInstance();
        		cal.add(Calendar.MONTH, -1);
        		criteriaDto.setFirstDate(cal.getTime());
        	}
        	if (this.lastDate!=null && this.lastDate.trim().length()>0 ) {
        		criteriaDto.setLastDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.lastDate.trim()));
        	} else {
        		criteriaDto.setLastDate(new Date());
        	}
        	
        	if (this.exchange!=null ) criteriaDto.setExchange(this.exchange);
        	
        	if (this.minCmp!=null) criteriaDto.setMinCmp(this.minCmp);
        	if (this.maxCmp!=null) criteriaDto.setMaxCmp(this.maxCmp);        	
        	if (this.minEps!=null) criteriaDto.setMinEps(this.minEps);
        	if (this.maxEps!=null) criteriaDto.setMaxEps(this.maxEps);        	
        	if (this.minPe!=null) criteriaDto.setMinPe(this.minPe);
        	if (this.maxPe!=null) criteriaDto.setMaxPe(this.maxPe);
        	
        	if (this.minPerformance!=null) criteriaDto.setMinPerformance(this.minPerformance);
        	if (this.maxPerformance!=null) criteriaDto.setMaxPerformance(this.maxPerformance);        	
        	if (this.minVolumePerformance!=null) criteriaDto.setMinVolumePerformance(this.minVolumePerformance);
        	if (this.maxVolumePerformance!=null) criteriaDto.setMaxVolumePerformance(this.maxVolumePerformance);
        	
        	if (this.averageVolume!=null) criteriaDto.setAverageVolume(this.averageVolume);
        	if (this.selectedWatchlist!=null) criteriaDto.setSelectedWatchlist(this.selectedWatchlist);
        	if (this.selectedSecondWatchlist !=null) criteriaDto.setSelectedSecondWatchlist(this.selectedSecondWatchlist);

        	if (this.getOrderBy()!=null && this.getOrderBy().length()>0) criteriaDto.setOrderBy(this.orderBy);
        	else {
        		this.setOrderBy("performance"); this.setOrderType("DESC");
        		criteriaDto.setOrderBy(this.orderBy);
        		criteriaDto.setOrderType(this.orderType);
        	}
        	if (this.getOrderType()!=null && this.getOrderType().length()>0) criteriaDto.setOrderType(this.orderType);
        	if (this.getPageNumber()!=null) criteriaDto.setPageNumber(this.pageNumber); else criteriaDto.setPageNumber(1);
        	if (this.getRecordsPerPage()!=null) criteriaDto.setRecordPerPage(this.recordsPerPage); 
        	
        	Map<String, Object> result = dataManager.searchPastPerformanceByCriteria(criteriaDto);
        	scripList = (List<ScripPerformanceDTO>)result.get("Result");
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
	

	public List<ScripPerformanceDTO> getScripList() {
		return scripList;
	}

	public void setScripList(List<ScripPerformanceDTO> scripList) {
		this.scripList = scripList;
	}

	public String getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
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

	public Long getAverageVolume() {
		return averageVolume;
	}

	public void setAverageVolume(Long averageVolume) {
		this.averageVolume = averageVolume;
	}

	public Float getMinPerformance() {
		return minPerformance;
	}

	public void setMinPerformance(Float minPerformance) {
		this.minPerformance = minPerformance;
	}

	public Float getMaxPerformance() {
		return maxPerformance;
	}

	public void setMaxPerformance(Float maxPerformance) {
		this.maxPerformance = maxPerformance;
	}

	public Float getMinVolumePerformance() {
		return minVolumePerformance;
	}

	public void setMinVolumePerformance(Float minVolumePerformance) {
		this.minVolumePerformance = minVolumePerformance;
	}

	public Float getMaxVolumePerformance() {
		return maxVolumePerformance;
	}

	public void setMaxVolumePerformance(Float maxVolumePerformance) {
		this.maxVolumePerformance = maxVolumePerformance;
	}

	public List<Watchlist> getUserWatchlists() {
		return userWatchlists;
	}

	public void setUserWatchlists(List<Watchlist> userWatchlists) {
		this.userWatchlists = userWatchlists;
	}

	public Long getSelectedWatchlist() {
		return selectedWatchlist;
	}

	public void setSelectedWatchlist(Long selectedWatchlist) {
		this.selectedWatchlist = selectedWatchlist;
	}

	public WatchlistManager getWatchlistManager() {
		return watchlistManager;
	}

	public void setWatchlistManager(WatchlistManager watchlistManager) {
		this.watchlistManager = watchlistManager;
	}

	public Long getSelectedSecondWatchlist() {
		return selectedSecondWatchlist;
	}

	public void setSelectedSecondWatchlist(Long selectedSecondWatchlist) {
		this.selectedSecondWatchlist = selectedSecondWatchlist;
	}
}

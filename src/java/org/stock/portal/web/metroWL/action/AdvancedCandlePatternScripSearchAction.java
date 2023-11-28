package org.stock.portal.web.metroWL.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.CandlePatternScripMatch;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.domain.metroWL.dto.AdvancedAnalysisSearchCriteriaDTO;
import org.stock.portal.service.autoscan.AutoscanManager;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class AdvancedCandlePatternScripSearchAction extends BaseAction {

	Logger log = Logger.getLogger(AdvancedCandlePatternScripSearchAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String fromDate;
    private String toDate;
    
    private String minUpCount;
    private String minDownCount;
    
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
        
    private String orderBy;
    private String orderType;
    private Integer pageNumber=1;
    private Integer recordsPerPage=10;
    
    private String sourceFormName;
    private String divToFill;   

    private Long totalRecords;
    
    private List<CandlePatternScripMatch> candlePatternScripList;
    
    private List<Watchlist> userWatchlists;
    private Long selectedWatchlist;  
    
    @InjectEJB (name ="AutoscanManager")
    AutoscanManager autoscanManager;
    
    @InjectEJB (name ="WatchlistManager")
    WatchlistManager watchlistManager;
    
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
   
    public AdvancedCandlePatternScripSearchAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	try {
    		User account = (User)this.session.get(Constants.LOGGED_IN_USER);
    		if (account!=null) {
    			userWatchlists = watchlistManager.getWatchlistByUserId(account.getId());
    		} else {
    			userWatchlists = new ArrayList<Watchlist>();
    			userWatchlists.add(new Watchlist(1L, "BTST"));
    		}
    		this.recordsPerPage = 25;
    		
    		Calendar curDate = Calendar.getInstance();
    		curDate.add(Calendar.DATE, 1);
    		Date latestDate = dataManager.getMaxDataDate("NSE", curDate.getTime());
    		this.fromDate = SPConstants.SPCORE_DATE_FORMAT.format(latestDate);
    		this.toDate = SPConstants.SPCORE_DATE_FORMAT.format(latestDate);
    		this.maxCmp = 500f;
    		this.minEps = 0f;
    		this.minPe = 0f;
    		this.averageVolume = 50000L;
    		this.minUpCount = "2";
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
    }

    public String search() {    	
        String retVal = "resultPage";
        try {
        	AdvancedAnalysisSearchCriteriaDTO analysisSrchDto = new AdvancedAnalysisSearchCriteriaDTO();
        	
        	if (this.fromDate!=null && this.fromDate.trim().length()>0 ) analysisSrchDto.setFromDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.fromDate.trim()));        	
        	if (this.toDate!=null && this.toDate.trim().length()>0 ) analysisSrchDto.setToDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.toDate.trim()));
        	
        	if (this.minUpCount!=null && this.getMinUpCount().trim().length()>0) analysisSrchDto.setMinUpCount(Integer.parseInt(this.minUpCount));
        	if (this.minDownCount!=null && this.getMinDownCount().trim().length()>0) analysisSrchDto.setMinDownCount(Integer.parseInt(this.minDownCount));
        			
        	if (this.scripName!=null && this.scripName.trim().length()>0 ) analysisSrchDto.setScripName(this.scripName.trim());
        	if (this.bseCode!=null && this.bseCode.trim().length()>0 ) analysisSrchDto.setBseCode(this.bseCode.trim());
        	if (this.nseCode!=null && this.nseCode.trim().length()>0 ) analysisSrchDto.setNseCode(this.nseCode.trim());
        	
        	if (this.minCmp!=null) analysisSrchDto.setMinCmp(this.minCmp);
        	if (this.maxCmp!=null) analysisSrchDto.setMaxCmp(this.maxCmp);        	
        	if (this.minEps!=null) analysisSrchDto.setMinEps(this.minEps);
        	if (this.maxEps!=null) analysisSrchDto.setMaxEps(this.maxEps);        	
        	if (this.minPe!=null) analysisSrchDto.setMinPe(this.minPe);
        	if (this.maxPe!=null) analysisSrchDto.setMaxPe(this.maxPe);
        	if (this.averageVolume!=null) analysisSrchDto.setAverageVolume(this.averageVolume);
        	if (this.selectedWatchlist!=null) analysisSrchDto.setSelectedWatchlist(this.selectedWatchlist);
        	
        	if (this.getOrderBy()!=null && this.getOrderBy().length()>0) analysisSrchDto.setOrderBy(this.orderBy);
        	else {
        		this.setOrderBy("fiveDayPattern.countRank"); this.setOrderType("Desc");
        		analysisSrchDto.setOrderBy(this.orderBy);
        		analysisSrchDto.setOrderType(this.orderType);
        	}
        	if (this.getOrderType()!=null && this.getOrderType().length()>0) analysisSrchDto.setOrderType(this.orderType);
        	if (this.getPageNumber()!=null) analysisSrchDto.setPageNumber(this.pageNumber); else analysisSrchDto.setPageNumber(1);
        	if (this.getRecordsPerPage()!=null) analysisSrchDto.setRecordPerPage(this.recordsPerPage); 
        	
        	Map<String, Object> result = autoscanManager.searchCandlePatternScripByAdvancedCriteria(analysisSrchDto);//searchAnalysisByAdvancedCriteria
        	candlePatternScripList = (List<CandlePatternScripMatch>)result.get("Result");
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

	public String getMinUpCount() {
		return minUpCount;
	}

	public void setMinUpCount(String minUpCount) {
		this.minUpCount = minUpCount;
	}

	public String getMinDownCount() {
		return minDownCount;
	}

	public void setMinDownCount(String minDownCount) {
		this.minDownCount = minDownCount;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Long getAverageVolume() {
		return averageVolume;
	}

	public void setAverageVolume(Long averageVolume) {
		this.averageVolume = averageVolume;
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

	public List<CandlePatternScripMatch> getCandlePatternScripList() {
		return candlePatternScripList;
	}

	public void setCandlePatternScripList(
			List<CandlePatternScripMatch> candlePatternScripList) {
		this.candlePatternScripList = candlePatternScripList;
	}
}

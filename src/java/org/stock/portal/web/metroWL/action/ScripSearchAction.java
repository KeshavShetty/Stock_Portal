package org.stock.portal.web.metroWL.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.NewsFeedSource;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.Sector;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.domain.metroWL.dto.ScripSearchCriteriaDTO;
import org.stock.portal.service.master.MasterManager;
import org.stock.portal.service.news.NewsManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class ScripSearchAction extends BaseAction {

	Logger log = Logger.getLogger(ScripSearchAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String scripName;
    private String bseCode;
    private String nseCode;
    private Long sectorId;
    
    private Long averageVolume;
    private Float averageTurnover;
    
    private String orderBy;
    private String orderType;
    private Integer pageNumber=1;
    private Integer recordsPerPage=10;
    
    private String sourceFormName;
    private String divToFill;
    
    private Float minCmp;
    private Float maxCmp;
    
    private Float minEps;
    private Float maxEps;
    
    private Float minPe;
    private Float maxPe;
    
    String resultIds = "";
    
    private String scripStatus;
    
    private Long totalRecords;
   
    private List<Sector> sectorList;
    List<Scrip> scripList;
    
    private List<Watchlist> userWatchlists;
    private Long selectedWatchlist;
    
    List<NewsFeedSource> newsFeedSourceList;
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
    
    @InjectEJB (name ="MasterManager")
    MasterManager masterManager;
   
    @InjectEJB (name ="NewsManager")
    NewsManager newsManager;
    
    @InjectEJB (name ="WatchlistManager")
    WatchlistManager watchlistManager;
    
    public ScripSearchAction() {
        super();
    }
    
    public String prepareSearch() {    	
    	try {
    		sectorList = masterManager.getAllSectors();    	
    		newsFeedSourceList = newsManager.getFeedSourceList();
    		User account = (User)this.session.get(Constants.LOGGED_IN_USER);
    		if (account!=null) {
    			userWatchlists = watchlistManager.getWatchlistByUserId(account.getId());
    		} else {
    			userWatchlists = new ArrayList<Watchlist>();
    		}
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
    }

    public String search() {    	
        String retVal = "resultPage";
        try {
        	ScripSearchCriteriaDTO scrpSrchDto = new ScripSearchCriteriaDTO();
        	if (this.scripName!=null && this.scripName.trim().length()>0 ) scrpSrchDto.setScripName(this.scripName.trim());
        	if (this.bseCode!=null && this.bseCode.trim().length()>0 ) scrpSrchDto.setBseCode(this.bseCode.trim());
        	if (this.nseCode!=null && this.nseCode.trim().length()>0 ) scrpSrchDto.setNseCode(this.nseCode.trim());
        	if (this.sectorId!=null && this.sectorId!=-1) scrpSrchDto.setSectorId(this.sectorId);
        	
        	if (this.minCmp!=null) scrpSrchDto.setMinCmp(this.minCmp);
        	if (this.maxCmp!=null) scrpSrchDto.setMaxCmp(this.maxCmp);
        	
        	if (this.minEps!=null) scrpSrchDto.setMinEps(this.minEps);
        	if (this.maxEps!=null) scrpSrchDto.setMaxEps(this.maxEps);
        	
        	if (this.minPe!=null) scrpSrchDto.setMinPe(this.minPe);
        	if (this.maxPe!=null) scrpSrchDto.setMaxPe(this.maxPe);
        	
        	if (this.averageVolume!=null) scrpSrchDto.setAverageVolume(this.averageVolume);
        	if (this.averageTurnover!=null) scrpSrchDto.setAverageTurnover(this.averageTurnover);
        	if (this.selectedWatchlist!=null) scrpSrchDto.setSelectedWatchlist(this.selectedWatchlist);
        	if (this.getOrderBy()!=null && this.getOrderBy().length()>0) scrpSrchDto.setOrderBy(this.orderBy);
        	if (this.getOrderType()!=null && this.getOrderType().length()>0) scrpSrchDto.setOrderType(this.orderType);
        	if (this.getPageNumber()!=null) scrpSrchDto.setPageNumber(this.pageNumber); else scrpSrchDto.setPageNumber(1);
        	if (this.getRecordsPerPage()!=null) scrpSrchDto.setRecordPerPage(this.recordsPerPage); 
        	if (this.getScripStatus()!=null) scrpSrchDto.setScripStatus(this.getScripStatus()); 
        	System.out.println("In Action scrpSrchDto="+scrpSrchDto.getOrderBy()+" ordertype"+scrpSrchDto.getOrderType());
        	Map<String, Object> result = scripManager.searchScrip(scrpSrchDto);
        	scripList = (List<Scrip>)result.get("Result");
        	totalRecords = (Long)result.get("RecordsCount");
        	setResultIds();
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

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public List<Sector> getSectorList() {
		return sectorList;
	}

	public void setSectorList(List<Sector> sectorList) {
		this.sectorList = sectorList;
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

	public List<Scrip> getScripList() {
		return scripList;
	}

	public void setScripList(List<Scrip> scripList) {
		this.scripList = scripList;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getScripStatus() {
		return scripStatus;
	}

	public void setScripStatus(String scripStatus) {
		this.scripStatus = scripStatus;
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
	
	public void setResultIds(String resultIds) {
		this.resultIds = resultIds;
	}
	
	public void setResultIds() {
		String newVal = "";
		if (scripList!=null && scripList.size()>0) {
			System.out.println(scripList.size());
			for(int i=0;i<scripList.size();i++) {
				if (i!=0) newVal = newVal + ",";
				newVal = newVal + ((Scrip)scripList.get(i)).getId();
				System.out.println("i="+i+" newVal="+newVal);
			}
		}	
		setResultIds(newVal);
	}
	
	public String getResultIds() {		
		return resultIds;
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
	
	public List<NewsFeedSource> getNewsFeedSourceList() {
		return newsFeedSourceList;
	}

	public void setNewsFeedSourceList(List<NewsFeedSource> newsFeedSourceList) {
		this.newsFeedSourceList = newsFeedSourceList;
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

	public Float getAverageTurnover() {
		return averageTurnover;
	}

	public void setAverageTurnover(Float averageTurnover) {
		this.averageTurnover = averageTurnover;
	}
	
}

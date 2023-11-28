package org.stock.portal.web.action.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.SessionAware;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.dto.ScripSearchCriteriaDTO;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.domain.BSEIntraSummaryData;

import org.stock.portal.domain.dto.IntradaySummarySearchCriteriaDTO;
import org.stock.portal.service.data.DataManager;


public class IntradayDataSearchAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(IntradayDataSearchAction.class.getName());
    private static final long serialVersionUID = 4205166422526662903L;    
    List<BSEIntraSummaryData> intradayDataList = null;

    private String dataDate;
    
    private String watchlistId;   

	private String orderBy;
    private String orderType; //Ascending/Descending      

    private String percentageAwayFrom52weekHigh;
    
    private Integer numberOfRecords;
    
    private Long averageVolume;
    
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
    
    public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public String getWatchlistId() {
		return watchlistId;
	}

	public void setWatchlistId(String watchlistId) {
		this.watchlistId = watchlistId;
	}
	
	public List<BSEIntraSummaryData> getIntradayDataList() {
		return intradayDataList;
	}

	public void setIntradayDataList(List<BSEIntraSummaryData> intradayDataList) {
		this.intradayDataList = intradayDataList;
	}

	public String getPercentageAwayFrom52weekHigh() {
		return percentageAwayFrom52weekHigh;
	}

	public void setPercentageAwayFrom52weekHigh(String percentageAwayFrom52weekHigh) {
		this.percentageAwayFrom52weekHigh = percentageAwayFrom52weekHigh;
	}

	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	/** The scrip accessor bean. */
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
   
    public IntradayDataSearchAction() {
        super();
    }

    @Action(value = "/prepareIntradaySummarySearch")
    public String prepareIntradaySummarySearch() {
    	this.setOrderBy("(intraSummary.closePrice-intraSummary.previousdayClosePrice)*100/intraSummary.previousdayClosePrice");
    	this.setOrderType("DESC"); 
        return "prepareIntradaySummarySearch";
    }
    
    public String execute() {
    	log.debug("--->Inside intraday summary data Search Action [JR]");        
        String retVal = SUCCESS;
        try {
        	IntradaySummarySearchCriteriaDTO intradaySearchCriteriaDTO = new IntradaySummarySearchCriteriaDTO();  
        	SimpleDateFormat uiDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        	if (this.dataDate==null||this.dataDate.length()==0)  { 
        		this.setDataDate(uiDateFormat.format(new Date()));
        	}
        	if (this.dataDate!=null)  { 
        		intradaySearchCriteriaDTO.setDataDate(uiDateFormat.parse(this.getDataDate())); 
        	}
        	
        	if (this.watchlistId!=null && this.watchlistId.length()>0)  { 
        		intradaySearchCriteriaDTO.setWatchlistId(Long.parseLong(this.watchlistId)); 
        	}
        	
        	if (this.percentageAwayFrom52weekHigh!=null && this.percentageAwayFrom52weekHigh.length()>0)  { 
        		intradaySearchCriteriaDTO.setPercentage52wHigh(Integer.parseInt(this.percentageAwayFrom52weekHigh)); 
        	}
        	if (this.averageVolume!=null) {
        		intradaySearchCriteriaDTO.setAverageVolume(this.averageVolume);
        	}
        	if (this.numberOfRecords==null) this.numberOfRecords=100;
        	intradaySearchCriteriaDTO.setNumberOfRecords(this.numberOfRecords);
        	if (this.orderBy!=null && this.orderBy.length()>0)  { intradaySearchCriteriaDTO.setOrderBy(this.orderBy); }
        	else { this.setOrderBy("intraSummary.dataDate"); intradaySearchCriteriaDTO.setOrderBy(this.orderBy); }
        	if (this.orderType!=null && this.orderType.length()>0)  { intradaySearchCriteriaDTO.setOrderType(this.orderType); }
        	else { this.setOrderType("ASC"); intradaySearchCriteriaDTO.setOrderType(this.orderType); }
        	
        	intradayDataList = dataManager.searchIntradayDataByCriteria(intradaySearchCriteriaDTO);
        	log.info("In Action intradayDataList="+intradayDataList.size());
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.scrip.search.error"));
        } 
        return retVal;
    }	
}

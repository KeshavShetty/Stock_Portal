package org.stock.portal.web.action.scan;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.SessionAware;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.BSEEodData;
import org.stock.portal.domain.dto.EODForPastPerformanceCriteriaDTO;
import org.stock.portal.service.autoscan.AutoscanManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class ViewPastPerformanceAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(ViewPastPerformanceAction.class.getName());
    private static final long serialVersionUID = 4205166422526662903L;
    
    List<BSEEodData> eodDataList = null;
    
    private String forDate;    
    private String watchlistId;   

	private String orderBy;
    private String orderType; //Ascending/Descending      

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

	public String getForDate() {
		return forDate;
	}

	public void setForDate(String forDate) {
		this.forDate = forDate;
	}

	public List<BSEEodData> getEodDataList() {
		return eodDataList;
	}

	public void setEodData(List<BSEEodData> eodDataList) {
		this.eodDataList = eodDataList;
	}

	/** The scrip accessor bean. */
    @InjectEJB (name ="AutoscanManager")
    AutoscanManager autoscanManager;
   
    public ViewPastPerformanceAction() {
        super();
    }

    @Action(value = "/prepareViewPastPerformance")
    public String prepareViewPastPerformance() {    	
    	this.setOrderType("DESC");    	 
        return "prepareViewPastPerformance";
    }
    
    public String execute() {
    	log.debug("--->Inside prepareAutoscanSearch");        
        String retVal = SUCCESS;
        try {
        	EODForPastPerformanceCriteriaDTO eodForPastPerformanceCriteriaDTO = new EODForPastPerformanceCriteriaDTO();  
        	SimpleDateFormat uiDateFormat = new SimpleDateFormat("dd/MM/yyyy");        	
        	
        	if (this.forDate!=null && this.forDate.length()>0)  { 
        		eodForPastPerformanceCriteriaDTO.setForDate(uiDateFormat.parse(this.forDate)); 
        	}
        	      	
        	if (this.watchlistId!=null && this.watchlistId.length()>0)  { 
        		eodForPastPerformanceCriteriaDTO.setWatchlistId(Long.parseLong(this.watchlistId)); 
        	}     	
        	if (this.averageVolume!=null) {
        		eodForPastPerformanceCriteriaDTO.setAverageVolume(this.averageVolume);
        	}
        	if (this.numberOfRecords==null) this.numberOfRecords=100;
        	eodForPastPerformanceCriteriaDTO.setNumberOfRecords(this.numberOfRecords);
        	if (this.orderBy!=null && this.orderBy.length()>0)  { eodForPastPerformanceCriteriaDTO.setOrderBy(this.orderBy); }
        	else { this.setOrderBy("bseEodData.closePrice"); eodForPastPerformanceCriteriaDTO.setOrderBy(this.orderBy); }
        	if (this.orderType!=null && this.orderType.length()>0)  { eodForPastPerformanceCriteriaDTO.setOrderType(this.orderType); }
        	else { this.setOrderType("ASC"); eodForPastPerformanceCriteriaDTO.setOrderType(this.orderType); }
        	
        	eodDataList = autoscanManager.getEodForPastPerformance(eodForPastPerformanceCriteriaDTO);
        	log.info("In Action Past performance="+eodDataList.size());
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.scrip.search.error"));
        } 
        return retVal;
    }	
}

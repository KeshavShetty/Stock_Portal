package org.stock.portal.web.action.scan;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.dto.ScripSearchCriteriaDTO;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class ScripFundentalSearchAction extends BaseAction {

	Logger log = Logger.getLogger(ScripFundentalSearchAction.class.getName());
    private static final long serialVersionUID = 4205166422526662903L;    
    List<Scrip> scripList;

    String scripName;
	String isinCode;
    String sector;
    String scripType;
    
    String bseCode;   
    String bseName; 
    String bseGroup;
    String bseIndex;
    
    String nseCode;    
    String seriesType;
    
    String iciciCode;
    String mcCode;
    String etCode;
    
    String status;
    Date dateAdded;
    
    private String orderBy;
    private String orderType; //Ascending/Descending    
    
    public String getScripName() {
		return scripName;
	}

	public void setScripName(String scripName) {
		this.scripName = scripName;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getScripType() {
		return scripType;
	}

	public void setScripType(String scripType) {
		this.scripType = scripType;
	}

	public String getBseCode() {
		return bseCode;
	}

	public void setBseCode(String bseCode) {
		this.bseCode = bseCode;
	}

	public String getBseName() {
		return bseName;
	}

	public void setBseName(String bseName) {
		this.bseName = bseName;
	}

	public String getBseGroup() {
		return bseGroup;
	}

	public void setBseGroup(String bseGroup) {
		this.bseGroup = bseGroup;
	}

	public String getBseIndex() {
		return bseIndex;
	}

	public void setBseIndex(String bseIndex) {
		this.bseIndex = bseIndex;
	}

	public String getNseCode() {
		return nseCode;
	}

	public void setNseCode(String nseCode) {
		this.nseCode = nseCode;
	}

	public String getSeriesType() {
		return seriesType;
	}

	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}

	public String getIciciCode() {
		return iciciCode;
	}

	public void setIciciCode(String iciciCode) {
		this.iciciCode = iciciCode;
	}

	public String getMcCode() {
		return mcCode;
	}

	public void setMcCode(String mcCode) {
		this.mcCode = mcCode;
	}

	public String getEtCode() {
		return etCode;
	}

	public void setEtCode(String etCode) {
		this.etCode = etCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
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
    
    /** The scrip accessor bean. */
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
   
    public ScripFundentalSearchAction() {
        super();
    }

    @Action(value = "/prepareScripSearch")
    public String prepareScripSearch() {
        return "prepareScripSearch";
    }
    
    public String execute() {
    	log.debug("--->Inside Scrip Search Action [JR]");        
        String retVal = SUCCESS;
        try {
        	ScripSearchCriteriaDTO scripSearchCriteriaDTO = new ScripSearchCriteriaDTO();  
        	if (this.scripName!=null && this.scripName.length()>0)  { scripSearchCriteriaDTO.setName(this.scripName); }
        	if (this.isinCode!=null && this.isinCode.length()>0)  { scripSearchCriteriaDTO.setIsinCode(this.isinCode); }
        	if (this.scripType!=null && this.scripType.length()>0)  { scripSearchCriteriaDTO.setScripType(this.scripType); }
        	if (this.bseCode!=null && this.bseCode.length()>0)  { scripSearchCriteriaDTO.setBseCode(this.bseCode); } 
        	if (this.bseName!=null && this.bseName.length()>0)  { scripSearchCriteriaDTO.setBseName(this.bseName); }
        	if (this.bseGroup!=null && this.bseGroup.length()>0)  { scripSearchCriteriaDTO.setBseGroup(this.bseGroup); }
        	if (this.bseIndex!=null && this.bseIndex.length()>0)  { scripSearchCriteriaDTO.setBseIndex(this.bseIndex); }
        	if (this.nseCode!=null && this.nseCode.length()>0)  { scripSearchCriteriaDTO.setNseCode(this.nseCode); }
        	if (this.seriesType!=null && this.seriesType.length()>0)  { scripSearchCriteriaDTO.setSeriesType(this.seriesType); }
        	if (this.iciciCode!=null && this.iciciCode.length()>0)  { scripSearchCriteriaDTO.setIciciCode(this.iciciCode); }
        	if (this.mcCode!=null && this.mcCode.length()>0)  { scripSearchCriteriaDTO.setMcCode(this.mcCode); }
        	if (this.etCode!=null && this.etCode.length()>0)  { scripSearchCriteriaDTO.setEtCode(this.etCode); }
        	if (this.status!=null && this.status.length()>0)  { scripSearchCriteriaDTO.setStatus(this.status); }
        	if (this.dateAdded!=null)  { scripSearchCriteriaDTO.setDateAdded(this.dateAdded); }
        	if (this.sector!=null && this.sector.length()>0)  { scripSearchCriteriaDTO.setSectorName(this.sector); }
        	
        	if (this.orderBy!=null && this.orderBy.length()>0)  { scripSearchCriteriaDTO.setOrderBy(this.orderBy); }
        	else { this.setOrderBy("name"); scripSearchCriteriaDTO.setOrderBy(this.orderBy); }
        	if (this.orderType!=null && this.orderType.length()>0)  { scripSearchCriteriaDTO.setOrderType(this.orderType); }
        	else { this.setOrderType("ASC"); scripSearchCriteriaDTO.setOrderType(this.orderType); }
        	
        	scripList = scripManager.searchScripByCriteria(scripSearchCriteriaDTO);
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.scrip.search.error"));
        } 
        return retVal;
    }

	public List<Scrip> getScripList() {
		return scripList;
	}

	public void setScripList(List<Scrip> scripList) {
		this.scripList = scripList;
	}

	
}

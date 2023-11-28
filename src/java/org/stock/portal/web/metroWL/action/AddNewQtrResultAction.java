package org.stock.portal.web.metroWL.action;

import org.apache.log4j.Logger;
import org.stock.portal.common.ErrorCodeConstants;
import org.stock.portal.domain.FinancialResult;
import org.stock.portal.domain.Scrip;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class AddNewQtrResultAction extends BaseAction {

	Logger log = Logger.getLogger(AddNewQtrResultAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	    
    private Long scripId;
    private Boolean isConsolidated;
    private Integer financialQtrId; // 20151601
    private Float revenue;
    private Float otherIncome;
    private Float totalIncome;
    
    private Float expenditure;
    private Float interest;
    private Float pbdt;
    private Float depreciation;
    private Float pbt;
    private Float tax;
    private Float netprofit;
    private Float eps;
    private Float ceps;
    private Float opmPercentage;
    private Float npmPercentage;
    private Float carPercentage;
    private Float qtrClosePrice;
       
    
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
    
    public AddNewQtrResultAction() {
        super();
    }
    
    public String prepareAddNewQtrResult() {  
    	String returnType = INPUT;
    	try {
			this.setIsConsolidated(false);
			Integer latestFinQtrId = dataManager.getMaxFinancialQtrId();
			if (latestFinQtrId!=null) this.setFinancialQtrId(latestFinQtrId); 
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return returnType;    	
    }

    public String addNewQtrResult() {    	
        String retVal = "reponsePage";
        try {
        	boolean errorFound = validateFormFields();
        	if (!errorFound) {
        		Scrip aScrip = scripManager.getScripById(scripId);
        		FinancialResult newQtrResult = new FinancialResult();
        		newQtrResult.setIsConsolidated(this.isConsolidated);
        		newQtrResult.setFinanciaReportQuarterId(this.financialQtrId);
        		newQtrResult.setScrip(aScrip);
        		if (this.revenue != null) newQtrResult.setRevenue(this.revenue);;
        		if (this.otherIncome != null) newQtrResult.setOtherIncome(this.otherIncome);
        		if (this.totalIncome != null) newQtrResult.setTotalIncome(this.totalIncome);
        		if (this.expenditure != null) newQtrResult.setExpenditure(this.expenditure);
        		if (this.interest != null) newQtrResult.setInterest(this.interest);
        		if (this.pbdt != null) newQtrResult.setPbdt(this.pbdt);
        		if (this.depreciation != null) newQtrResult.setDepreciation(this.depreciation);
        		if (this.pbt != null) newQtrResult.setPbt(this.pbt);
        		if (this.tax != null) newQtrResult.setTax(this.tax);
        		if (this.netprofit != null) newQtrResult.setNetProfit(this.netprofit);
        		if (this.eps != null) newQtrResult.setEps(this.eps);
        		if (this.ceps != null) newQtrResult.setCeps(this.ceps);
        		if (this.opmPercentage != null) newQtrResult.setOpmPercentage(this.opmPercentage);
        		if (this.npmPercentage != null) newQtrResult.setNpmPercentage(this.npmPercentage);
        		if (this.carPercentage != null) newQtrResult.setCarPercentage(this.carPercentage);
        		if (this.qtrClosePrice != null) newQtrResult.setQtClosePrice(this.qtrClosePrice);
        		dataManager.saveQtrResult(newQtrResult);
        	} else {
        		retVal = INPUT;
        	}
        	if (retVal == "reponsePage") {
        		addActionMessage("New Qtr result added successfully");
        	}
        } catch (Exception e) {
        	log.error(e);
        	retVal = INPUT;
            addActionError(e.getMessage());
        } 
        log.info("Returning "+retVal);
        return retVal;
    }

	
	private boolean validateFormFields() {
		boolean retValue = false;
		
		if (this.isConsolidated==null) {
			addActionError("Please select Standalone or Consolidated option");
			retValue = true;
		} 
		if (this.financialQtrId==null) {
			addActionError("Financial Qtr ID is mandatory. e.g: 20151601");
			retValue = true;
		}
		return retValue;
	}
	
	private void saveErrorResponseMessage(Integer responseCode) {
		if (responseCode.equals(ErrorCodeConstants.WATCHLIST_DUPLICATE_ITEMS)) {
			addActionError("This scrip already exist in the selected watchlist. Either select a different watchlist or create a new watchlist");
		} else if (responseCode.equals(ErrorCodeConstants.WATCHLIST_NO_RIGHTS)) {
			addActionError("You do not have teh access permission to modify this watchlist");
		} else if (responseCode.equals(ErrorCodeConstants.WATCHLIST_NUMBER_OF_ALLOWED_WL_CROSSED)) {
			addActionError("You are exceeded with maximum allowed watchlist. Please upgrade your subscription to add new watchlist");
		} else if (responseCode.equals(ErrorCodeConstants.WATCHLIST_NUMBER_OF_ALLOWED_WLITEM_CROSSED)) {
			addActionError("You are exceeded with maximum allowed scrips/symbols per watchlist. Please upgrade your subscription to add more symbols to this watchlist");		
		}
	}

	public Long getScripId() {
		return scripId;
	}

	public void setScripId(Long scripId) {
		this.scripId = scripId;
	}

	public Boolean getIsConsolidated() {
		return isConsolidated;
	}

	public void setIsConsolidated(Boolean isConsolidated) {
		this.isConsolidated = isConsolidated;
	}

	public Integer getFinancialQtrId() {
		return financialQtrId;
	}

	public void setFinancialQtrId(Integer financialQtrId) {
		this.financialQtrId = financialQtrId;
	}

	public Float getRevenue() {
		return revenue;
	}

	public void setRevenue(Float revenue) {
		this.revenue = revenue;
	}

	public Float getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(Float otherIncome) {
		this.otherIncome = otherIncome;
	}

	public Float getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(Float totalIncome) {
		this.totalIncome = totalIncome;
	}

	public Float getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(Float expenditure) {
		this.expenditure = expenditure;
	}

	public Float getInterest() {
		return interest;
	}

	public void setInterest(Float interest) {
		this.interest = interest;
	}

	public Float getPbdt() {
		return pbdt;
	}

	public void setPbdt(Float pbdt) {
		this.pbdt = pbdt;
	}

	public Float getDepreciation() {
		return depreciation;
	}

	public void setDepreciation(Float depreciation) {
		this.depreciation = depreciation;
	}

	public Float getPbt() {
		return pbt;
	}

	public void setPbt(Float pbt) {
		this.pbt = pbt;
	}

	public Float getTax() {
		return tax;
	}

	public void setTax(Float tax) {
		this.tax = tax;
	}

	public Float getNetprofit() {
		return netprofit;
	}

	public void setNetprofit(Float netprofit) {
		this.netprofit = netprofit;
	}

	public Float getEps() {
		return eps;
	}

	public void setEps(Float eps) {
		this.eps = eps;
	}

	public Float getCeps() {
		return ceps;
	}

	public void setCeps(Float ceps) {
		this.ceps = ceps;
	}

	public Float getOpmPercentage() {
		return opmPercentage;
	}

	public void setOpmPercentage(Float opmPercentage) {
		this.opmPercentage = opmPercentage;
	}

	public Float getNpmPercentage() {
		return npmPercentage;
	}

	public void setNpmPercentage(Float npmPercentage) {
		this.npmPercentage = npmPercentage;
	}

	public Float getCarPercentage() {
		return carPercentage;
	}

	public void setCarPercentage(Float carPercentage) {
		this.carPercentage = carPercentage;
	}

	public Float getQtrClosePrice() {
		return qtrClosePrice;
	}

	public void setQtrClosePrice(Float qtrClosePrice) {
		this.qtrClosePrice = qtrClosePrice;
	}

}

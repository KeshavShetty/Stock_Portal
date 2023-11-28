package org.stock.portal.web.metroWL.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.Sector;
import org.stock.portal.domain.User;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.domain.dto.ScripBubbleGraphDTO;
import org.stock.portal.domain.metroWL.dto.BubblegraphCriteriaDTO;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.service.watchlist.WatchlistManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;


public class BubbleGraphAction extends BaseAction {

	Logger log = Logger.getLogger(BubbleGraphAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String bseCode;
    private String nseCode; 
    
    private String fromDate;
    private String toDate;
    
    private List<Watchlist> userWatchlists;
    private List<Sector> sectors;    
    
    private Long selectedWatchlist;  
    private Long selectedSector;
    
    private Long selectedRankingOption=1L;
    
    private List<ScripBubbleGraphDTO> scripList;
    
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
    
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
    
   
    @InjectEJB (name ="WatchlistManager")
    WatchlistManager watchlistManager;
    
    public BubbleGraphAction() {
        super();
    }
    
    public String prepareBubbleGraph() {    	
    	try {
    		this.selectedWatchlist = 1L;
    		
    		Calendar curDate = Calendar.getInstance();
    		curDate.add(Calendar.DATE, 1);
    		Date latestDate = dataManager.getMaxDataDate("NSE", curDate.getTime());
    		this.fromDate = SPConstants.SPCORE_DATE_FORMAT.format(latestDate);
    		this.toDate = this.fromDate;
    		
    		User account = (User)this.session.get(Constants.LOGGED_IN_USER);
    		if (account!=null) {
    			userWatchlists = watchlistManager.getWatchlistByUserId(account.getId());
    		} else {
    			userWatchlists = new ArrayList<Watchlist>();
    		}
    		sectors = dataManager.getAllSectors();
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
    }

    public String generateBubleGraph() {    	
        String retVal = "showGraph";
        try {
        	BubblegraphCriteriaDTO criteriaDto = new BubblegraphCriteriaDTO();
        	
        	if (this.fromDate!=null && this.fromDate.trim().length()>0 ) {
        		criteriaDto.setFromDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.fromDate.trim()));        	
        	} else {
        		criteriaDto.setFromDate(new Date());
        	}
        	if (this.toDate!=null && this.toDate.trim().length()>0 ) {
        		criteriaDto.setToDate(SPConstants.SPCORE_DATE_FORMAT.parse(this.toDate.trim()));
        	} else {
        		criteriaDto.setToDate(new Date());
        	}
        	
        	if (this.bseCode!=null && this.bseCode.trim().length()>0) criteriaDto.setBseCode(this.bseCode);
        	if (this.nseCode!=null && this.nseCode.trim().length()>0) criteriaDto.setNseCode(this.nseCode);
        	
        	if (this.selectedWatchlist!=null) criteriaDto.setSelectedWatchlist(this.selectedWatchlist);
        	if (this.selectedSector!=null) criteriaDto.setSelectedSector(this.selectedSector);
        	
        	scripList = scripManager.searchScripForBubbleGraphByCriteria(criteriaDto);
        	
        	if (selectedRankingOption==2) retVal = "showGraphSqg";
        	if (selectedRankingOption==3) retVal = "showGraphSqgMargin";
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        log.info("Returning "+retVal);
        return retVal;
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

	public List<Watchlist> getUserWatchlists() {
		return userWatchlists;
	}

	public void setUserWatchlists(List<Watchlist> userWatchlists) {
		this.userWatchlists = userWatchlists;
	}

	public List<Sector> getSectors() { 
		return sectors;
	}

	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}

	public Long getSelectedWatchlist() {
		return selectedWatchlist;
	}

	public void setSelectedWatchlist(Long selectedWatchlist) {
		this.selectedWatchlist = selectedWatchlist;
	}

	public Long getSelectedSector() {
		return selectedSector;
	}

	public void setSelectedSector(Long selectedSector) {
		this.selectedSector = selectedSector;
	}

	public List<ScripBubbleGraphDTO> getScripList() {
		return scripList;
	}

	public void setScripList(List<ScripBubbleGraphDTO> scripList) {
		this.scripList = scripList;
	}

	public String getBubbleDataAsString() {
		StringBuffer retStr = new StringBuffer();
		if (scripList!=null && scripList.size()>0) {
			for(int i=0;i<scripList.size();i++) {
				ScripBubbleGraphDTO aDto = scripList.get(i);
				if (i!=0) retStr.append(",");
				// { x: 95, y: 95, z: 13.8, name: 'BE', country: 'Belgium' },
				
				// revenuesameqtrchangepercent
				//lastSameQtrGrowthPercentage
				//System.out.println("selectedRankingOption="+selectedRankingOption);
				if (selectedRankingOption==2) {
					float sqgRevenueToUse = 0;
					if (aDto.getSqgRevenue()!=null) sqgRevenueToUse = aDto.getSqgRevenue();
					
					float sqgProfit = 0;
					if (aDto.getSqgProfit()!=null) sqgProfit = aDto.getSqgProfit();
					retStr.append("{ x: " + sqgRevenueToUse +", y: " + sqgProfit + ", z: " + aDto.getWlCount() +", ");
				} else if (selectedRankingOption==3) {
					float sqgProfit = 0;
					if (aDto.getSqgProfit()!=null) sqgProfit = aDto.getSqgProfit();
					
					float sqgMarginProfit = 0;
					if (aDto.getSqgProfitMargin()!=null) sqgMarginProfit = aDto.getSqgProfitMargin();
					
					float actualProfitMarginLastQtr = 0;
					if (aDto.getActualProfitMarginLastQtr()!=null) 		actualProfitMarginLastQtr= aDto.getActualProfitMarginLastQtr();
					if (actualProfitMarginLastQtr>100) actualProfitMarginLastQtr = 100;
					
					retStr.append("{ x: " + sqgProfit +", y: " + sqgMarginProfit + ", z: " + actualProfitMarginLastQtr +", ");
					
				} else {
					retStr.append("{ x: " + aDto.getGrowthRank() +", y: " + aDto.getWlScoreRank() + ", z: " + aDto.getWlCount() +", ");
				}
				if (aDto.getNseCode()!=null) retStr.append(" scripCode: '" + aDto.getNseCode() + "',");
				else if (aDto.getBseCode()!=null) retStr.append(" scripCode: '" + aDto.getBseCode() + "',");
				retStr.append(" scripName: '" + aDto.getName() + " on " + aDto.getDataDate() + "', jqIndex: '" + aDto.getId() + "' }");
			}
		}
		//System.out.println("In getBubbleDataAsString retSTr=" +retStr.toString());
		return retStr.toString();
	}

	public Long getSelectedRankingOption() {
		return selectedRankingOption;
	}

	public void setSelectedRankingOption(Long selectedRankingOption) {
		this.selectedRankingOption = selectedRankingOption;
	}
	
}

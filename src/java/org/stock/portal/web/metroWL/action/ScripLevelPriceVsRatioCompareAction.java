package org.stock.portal.web.metroWL.action;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.dto.ScripBubbleGraphDTO;
import org.stock.portal.domain.dto.ScripLevelPriceVsRatioDTO;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class ScripLevelPriceVsRatioCompareAction extends BaseAction {

	Logger log = Logger.getLogger(ScripLevelPriceVsRatioCompareAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String scripCode="";
    private String scripName="";
    
    private Long selectedWatchlist;  
    
    private List<ScripLevelPriceVsRatioDTO> scripLevelPriceVsRatioDTOs; 
    
	private List<ScripBubbleGraphDTO> scripList;
    
	private SimpleDateFormat etDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
   
    public ScripLevelPriceVsRatioCompareAction() {
        super();
    }

    public String generateGraph() {    	
        String retVal = "showScripCompareResultChart";
        try {
        	System.out.println("scripCodes="+scripCode);
        	
        	if (scripCode.length()>0) {
        		Scrip scrip = scripManager.getScripByExCode(scripCode);
        		if (scrip!=null) {
        			this.scripName = scrip.getName();
        		}
        		scripLevelPriceVsRatioDTOs = scripManager.getScripPriceVsRatio(scripCode);
        	} else {
        		this.scripName = "No scrip found";
        		scripLevelPriceVsRatioDTOs = new ArrayList<ScripLevelPriceVsRatioDTO>(); 
        		scripLevelPriceVsRatioDTOs.add(new ScripLevelPriceVsRatioDTO(new Date(), 100f, 1f, 1.2f, 23f, 11f));
        		scripLevelPriceVsRatioDTOs.add(new ScripLevelPriceVsRatioDTO(new Date(), 100f, 2f, 2.2f, 13f, 21f));
        	}        
        } catch (Exception e) { 
        	log.error(e);
        } 
        log.info("Returning "+retVal);
        return retVal;
    }
	
    
    public String getDateAsString() { //  'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
		String retStr = "";
		for (ScripLevelPriceVsRatioDTO scripLevelPriceVsRatioDTO : scripLevelPriceVsRatioDTOs) {
			if (retStr.length()!=0) retStr = retStr + ",";
			retStr = retStr + "'" + etDateFormat.format(scripLevelPriceVsRatioDTO.getDataDate())+ "'" ;			
		}
		return retStr;		
	}
        
    public String getDatewiseRatioAsString() {
    	String retStr = "";
    	
    	
    	DecimalFormat df=new DecimalFormat("#.##");
    	
    	
    	retStr = retStr + "{ name: 'Price', visible: true, data: ["; 
		int count = 0;
		for (ScripLevelPriceVsRatioDTO scripLevelPriceVsRatioDTO : scripLevelPriceVsRatioDTOs) {
			if (count!=0) retStr = retStr + ",";
			retStr = retStr + df.format(scripLevelPriceVsRatioDTO.getCmp());
			count++;
		}
		retStr = retStr + "]}";
		
		retStr = retStr + ", { name: 'PE', visible: true, yAxis: 1,  data: ["; 
		count = 0;
		for (ScripLevelPriceVsRatioDTO scripLevelPriceVsRatioDTO : scripLevelPriceVsRatioDTOs) {
			if (count!=0) retStr = retStr + ",";
			retStr = retStr + df.format(scripLevelPriceVsRatioDTO.getPe());
			count++;
		}
		retStr = retStr + "]}";
		
		retStr = retStr + ", { name: 'PB', visible: false, yAxis: 1, data: [";
    	count = 0;
    	for (ScripLevelPriceVsRatioDTO scripLevelPriceVsRatioDTO : scripLevelPriceVsRatioDTOs) {
			if (count!=0) retStr = retStr + ",";
			retStr = retStr + df.format(scripLevelPriceVsRatioDTO.getPb());
			count++;
		}
		retStr = retStr + "]}";
		
    	retStr = retStr + ", { name: 'ROCE', visible: false, yAxis: 1, data: [";
    	count = 0;
    	for (ScripLevelPriceVsRatioDTO scripLevelPriceVsRatioDTO : scripLevelPriceVsRatioDTOs) {
			if (count!=0) retStr = retStr + ",";
			retStr = retStr + df.format(scripLevelPriceVsRatioDTO.getRoce());
			count++;
		}
		retStr = retStr + "]}";
		
		retStr = retStr + ", { name: 'ROCE Change', visible: false, yAxis: 1, data: [";
    	count = 0;
    	for (ScripLevelPriceVsRatioDTO scripLevelPriceVsRatioDTO : scripLevelPriceVsRatioDTOs) {
			if (count!=0) retStr = retStr + ",";
			retStr = retStr + df.format(scripLevelPriceVsRatioDTO.getRoceChange());
			count++;
		}
		retStr = retStr + "]}";
		
		retStr = retStr + ", { name: 'BOOK Value', visible: false, yAxis: 1, data: [";
    	count = 0;
    	for (ScripLevelPriceVsRatioDTO scripLevelPriceVsRatioDTO : scripLevelPriceVsRatioDTOs) {
			if (count!=0) retStr = retStr + ",";
			float bookValue = 0f;
			if (scripLevelPriceVsRatioDTO.getPb()!=0f) bookValue = scripLevelPriceVsRatioDTO.getCmp()/scripLevelPriceVsRatioDTO.getPb();
			
			retStr = retStr + df.format(bookValue);
			count++;
		}
		retStr = retStr + "]}";
		
		retStr = retStr + ", { name: 'EPS(ttm)', visible: false, yAxis: 1, data: [";
    	count = 0;
    	for (ScripLevelPriceVsRatioDTO scripLevelPriceVsRatioDTO : scripLevelPriceVsRatioDTOs) {
			if (count!=0) retStr = retStr + ",";
			float epsValue = 0f;
			if (scripLevelPriceVsRatioDTO.getPe()!=0f) epsValue = scripLevelPriceVsRatioDTO.getCmp()/scripLevelPriceVsRatioDTO.getPe();
			
			retStr = retStr + df.format(epsValue);
			count++;
		}
		retStr = retStr + "]}";
		
		
		return retStr;
    }

	public String getScripCode() {
		return scripCode;
	}

	public void setScripCode(String scripCode) {
		this.scripCode = scripCode;
	}

	public Long getSelectedWatchlist() {
		return selectedWatchlist;
	}

	public void setSelectedWatchlist(Long selectedWatchlist) {
		this.selectedWatchlist = selectedWatchlist;
	}

	public List<ScripLevelPriceVsRatioDTO> getScripLevelPriceVsRatioDTOs() {
		return scripLevelPriceVsRatioDTOs;
	}

	public void setScripLevelPriceVsRatioDTOs(List<ScripLevelPriceVsRatioDTO> scripLevelPriceVsRatioDTOs) {
		this.scripLevelPriceVsRatioDTOs = scripLevelPriceVsRatioDTOs;
	}

	public String getScripName() {
		return scripName;
	}

	public void setScripName(String scripName) {
		this.scripName = scripName;
	}
}

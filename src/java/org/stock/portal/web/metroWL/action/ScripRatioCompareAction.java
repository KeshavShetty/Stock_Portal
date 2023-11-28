package org.stock.portal.web.metroWL.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.domain.dto.ScripBubbleGraphDTO;
import org.stock.portal.domain.dto.ScripCompareDTO;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class ScripRatioCompareAction extends BaseAction {

	Logger log = Logger.getLogger(ScripRatioCompareAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String scripCodes="";
    
    private Long selectedWatchlist;  
    
    private List<ScripCompareDTO> scripDtos; 
    
    public List<ScripCompareDTO> getScripDtos() {
		return scripDtos;
	}

	public void setScripDtos(List<ScripCompareDTO> scripDtos) {
		this.scripDtos = scripDtos;
	}

	private List<ScripBubbleGraphDTO> scripList;
    
        
    @InjectEJB (name ="ScripManager")
    ScripManager scripManager;
   
    public ScripRatioCompareAction() {
        super();
    }

    public String generateGraph() {    	
        String retVal = "showScripCompareResultChart";
        try {
        	System.out.println("scripCodes="+scripCodes);
        	
        	if (scripCodes.length()>0) {
	        	String[] scripExcodes = this.scripCodes.split("\\s*,\\s*"); 
	        	scripDtos = scripManager.getScripRatios(scripExcodes, null);
        	} else if (this.selectedWatchlist!=null) {
        		scripDtos = scripManager.getScripRatios(null, selectedWatchlist);
        	} else {
        		scripDtos = new ArrayList<ScripCompareDTO>(); 
	        	ScripCompareDTO aDto = new ScripCompareDTO();
	        	aDto.setExchangeCode("Nothing Selected");
	        	aDto.setPb(0f);
	        	aDto.setPe(0f);
	        	aDto.setQtrProfit(0f);
	        	aDto.setQtrProfitMargin(0f);
	        	aDto.setQtrRevenue(0f);
	        	scripDtos.add(aDto);
        	}
        	

        
        } catch (Exception e) { 
        	log.error(e);
        } 
        log.info("Returning "+retVal);
        return retVal;
    }
	
    
    public String getCompaniesAsString() {
		String retStr = "";
		for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (retStr.length()!=0) retStr = retStr + ",";
			if (scripCompareDTO.getName().length()<=12) retStr = retStr + "'" + scripCompareDTO.getName()+ "'" ; 
			else retStr = retStr + "'" + scripCompareDTO.getName().substring(0, 12) + "..'";
			
			// categories: ['<a href="http://www.google.co.in">Service</a>','Product','Process']
			//if (scripCompareDTO.getName().length()<=12) retStr = retStr + "'<a href=\"http://localhost/portal/scripInfo.do?jqIndex=" + scripCompareDTO.getId()+ "\">" + scripCompareDTO.getName()+ "</a>'" ; // categories: ['<a href="http://www.google.co.in">Service</a>','Product','Process']
			//else retStr = retStr + "'<a href=\"http://localhost/portal/scripInfo.do?jqIndex=" + scripCompareDTO.getId()+ "\">" + scripCompareDTO.getName().substring(0, 12)+ "</a>'" ;
			
		}
		return retStr;		
	}
    
    
    public String getAllCompanyRatioAsString() {
    	
    	
    	String retStr = "";
    	retStr = retStr + "{ name: 'Revenue', visible: true,  tooltip: { valueSuffix: ' Cr' }, data: ["; 
		int count = 0;
		for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (count!=0) retStr = retStr + ",";
			retStr = retStr + scripCompareDTO.getQtrRevenue();
			count++;
		}
		retStr = retStr + "]}";
		
		
    	retStr = retStr + ", { name: 'PB', visible: false,  data: [";
    	count = 0;
		for (ScripCompareDTO scripCompareDTO : scripDtos) { 
			if (count!=0) retStr = retStr + ",";
			retStr = retStr + scripCompareDTO.getPb();
			count++;
		}
		retStr = retStr + "]}";
		
    	
		retStr = retStr + ", { name: 'PE', visible: false, data: [";
		count = 0;
		for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (count!=0) retStr = retStr + ",";
			retStr = retStr + scripCompareDTO.getPe();
			count++;
		}
		retStr = retStr + "]}";
				
		retStr = retStr + ", { name: 'Profit Margin', visible: false, tooltip: { valueSuffix: ' %' }, data: [";
		count=0;
		for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (count!=0) retStr = retStr + ",";
			retStr = retStr + scripCompareDTO.getQtrProfitMargin();
			count++;
		}
		retStr = retStr + "]}";
		
		retStr = retStr + ", { name: 'Profit', visible: false, tooltip: { valueSuffix: ' Cr' }, data: [";
		count = 0;
		for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (count!=0)  retStr = retStr + ",";
			retStr = retStr + scripCompareDTO.getQtrProfit();
			count++;
		}
		retStr = retStr + "]}";
		
		
		retStr = retStr + ", { name: 'SQG', visible: false, tooltip: { valueSuffix: ' %' }, data: [";
		count = 0;
		for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (count!=0)  retStr = retStr + ",";
			retStr = retStr + scripCompareDTO.getSqg();
			count++;
		}
		retStr = retStr + "]}";
		
		retStr = retStr + ", { name: 'ROCE', visible: false, tooltip: { valueSuffix: ' ' }, data: [";
		count = 0;
		for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (count!=0)  retStr = retStr + ",";
			retStr = retStr + scripCompareDTO.getRoce();
			count++;
		}
		retStr = retStr + "]}";
		
		retStr = retStr + ", { name: 'ROCE Change', visible: false, tooltip: { valueSuffix: ' %' }, data: [";
		count = 0;
		for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (count!=0)  retStr = retStr + ",";
			retStr = retStr + scripCompareDTO.getRoceChange();
			count++;
		}
		retStr = retStr + "]}";
		
		retStr = retStr + ", { name: 'ForecastPriceByPBVsPrice', visible: false, tooltip: { valueSuffix: ' %' }, data: [";
		count = 0;
		for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (count!=0)  retStr = retStr + ",";
			retStr = retStr + scripCompareDTO.getPriceByPBVsPrice();
			count++;
		}
		retStr = retStr + "]}";
		
		retStr = retStr + ", { name: 'ForecastPriceByPEVsPrice', visible: false, tooltip: { valueSuffix: ' %' }, data: [";
		count = 0;
		for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (count!=0)  retStr = retStr + ",";
			retStr = retStr + scripCompareDTO.getPriceByPEVsPrice();
			count++;
		}
		retStr = retStr + "]}";
		
		
		return retStr;
    }
    
    public String getRevenueRatio() {
    	String retStr = "";
    	
    	int count = 0;
    	for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (count!=0)  retStr = retStr + ",";
			
			retStr = retStr + "{ name: '" + scripCompareDTO.getName() +"', y: " + scripCompareDTO.getQtrRevenue() + ", color: Highcharts.getOptions().colors[" + count + "], jqIndex:" + scripCompareDTO.getId() + "}"; 
			count++;
    	}
        //System.out.println("retStr="+retStr);
        
    	return retStr;
    }
    
    public String getProfitRatio() {
    	String retStr = "";
    	
    	int count = 0;
    	for (ScripCompareDTO scripCompareDTO : scripDtos) {
			if (count!=0)  retStr = retStr + ",";
			
			retStr = retStr + "{ name: '" + scripCompareDTO.getName() +"', y: " + scripCompareDTO.getQtrProfit() + ", color: Highcharts.getOptions().colors[" + count + "], jqIndex:" + scripCompareDTO.getId() + "}"; 
			count++;
    	}
        
    	return retStr;
    }

	public String getScripCodes() {
		return scripCodes;
	}

	public void setScripCodes(String scripCodes) {
		this.scripCodes = scripCodes;
	}

	public Long getSelectedWatchlist() {
		return selectedWatchlist;
	}

	public void setSelectedWatchlist(Long selectedWatchlist) {
		this.selectedWatchlist = selectedWatchlist;
	}
}

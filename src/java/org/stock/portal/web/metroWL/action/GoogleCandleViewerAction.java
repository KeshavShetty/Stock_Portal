package org.stock.portal.web.metroWL.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.domain.dto.ScripEOD;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;


public class GoogleCandleViewerAction extends BaseAction {

	Logger log = Logger.getLogger(GoogleCandleViewerAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private Long scripId;
    private String dataDate;
    private Boolean exchangeCode;
    
    private List<ScripEOD> eodData;
    
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
   
    public GoogleCandleViewerAction() {
        super();
    }
    
    public String execute() {    	
    	try {
    		DateFormat LOCAL_DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");
    		Date dataDateAsDate = LOCAL_DATE_FORMAT.parse(dataDate);
    		System.out.println("dataDateAsDate="+dataDateAsDate);
    		eodData = dataManager.getEodDataUptoDateByScripId(scripId, dataDateAsDate, new Integer(5), exchangeCode);
    	} catch (Exception ex) {
    		log.error(ex.getMessage());
    	}
    	return SUCCESS;    	
    }

	public List<ScripEOD> getEodData() {
		return eodData;
	}

	public void setEodData(List<ScripEOD> eodData) {
		this.eodData = eodData;
	}

	public Long getScripId() {
		return scripId;
	}

	public void setScripId(Long scripId) {
		this.scripId = scripId;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public Boolean getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(Boolean exchangeCode) {
		this.exchangeCode = exchangeCode;
	}    
}

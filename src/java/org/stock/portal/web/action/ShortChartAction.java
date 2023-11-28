package org.stock.portal.web.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.stock.portal.domain.BSEEodData;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.annotation.InjectEJB;



@Results( { @Result(name = "showChart", location = "/WEB-INF/jsp/shortchart.jsp")
        } )
@InterceptorRefs( { @InterceptorRef("i18n")
        , @InterceptorRef("portalStack")
        } )
public class ShortChartAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(ShortChartAction.class.getName());
	
	List<BSEEodData> dataList;
	
	Long scripId;
   
	public Long getScripId() {
		return scripId;
	}

	public void setScripId(Long scripId) {
		this.scripId = scripId;
	}

	/** The Data accessor bean. */
    @InjectEJB (name ="DataManager")
    DataManager dataManager;
    
    public List<BSEEodData> getDataList() {
		return dataList;
	}

	public void setDataList(List<BSEEodData> dataList) {
		this.dataList = dataList;
	}

	public ShortChartAction() {
        super();
    }

    @Action(value = "/shortChart")
    public String shortChart() {
    	try {
    		dataList = dataManager.getEodDataByScripId(scripId, 10);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}    	
        return "shortChart";
    }
    
    public void setSession(Map<String, Object> map) {
    }


}

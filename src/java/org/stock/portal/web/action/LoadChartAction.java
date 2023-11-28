package org.stock.portal.web.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;



@Results( { @Result(name = "showChart", location = "/WEB-INF/jsp/chart.jsp")
        } )
@InterceptorRefs( { @InterceptorRef("i18n")
        , @InterceptorRef("portalStack")
        } )
public class LoadChartAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(LoadChartAction.class.getName());
   
    public LoadChartAction() {
        super();
    }

    @Action(value = "/loadChart")
    public String loadChart() {
        return "showChart";
    }
    
    public void setSession(Map<String, Object> map) {
    }


}

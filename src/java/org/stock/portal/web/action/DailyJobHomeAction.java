package org.stock.portal.web.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;



@Results( { @Result(name = "showDailyJobHome", location = "/WEB-INF/jsp/dailyJobHome.jsp")
        } )
@InterceptorRefs( { @InterceptorRef("i18n")
        , @InterceptorRef("portalStack")
        } )
public class DailyJobHomeAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(DailyJobHomeAction.class.getName());
   
    public DailyJobHomeAction() {
        super();
    }

    @Action(value = "/dailyJobHome")
    public String dailyJobHome() {
        return "showDailyJobHome";
    }
    
    public void setSession(Map<String, Object> map) {
    }


}

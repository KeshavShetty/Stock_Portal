package org.stock.portal.web.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;



@Results( { @Result(name = "prepareLogin", location = "/WEB-INF/jsp/login.jsp"),
			@Result(name = "INPUT", location = "/WEB-INF/jsp/login.jsp"),
			@Result(name = "SUCCESS", location = "/WEB-INF/jsp/home.jsp")
        } )
@InterceptorRefs( { @InterceptorRef("i18n")
        , @InterceptorRef("portalStack")
        } )
public class PrepareLoginAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(PrepareLoginAction.class.getName());
   
    public PrepareLoginAction() {
        super();
    }

    @Action(value = "/prepareLogin")
    public String prepareLogin() {
        return "prepareLogin";
    }
    
    public void setSession(Map<String, Object> map) {
    }


}

package org.stock.portal.web.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.interceptor.SessionAware;


@InterceptorRefs( { @InterceptorRef("i18n")
        , @InterceptorRef("portalStack")
        } )
public class ScripSearchAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(ScripSearchAction.class.getClass());
   
    public ScripSearchAction() {
        super();
    }

    @Action(value = "/scripSearch")
    public String execute() {
        return "showScrips";
    }
    
    public void setSession(Map<String, Object> map) {
    }


}

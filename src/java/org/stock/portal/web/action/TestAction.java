package org.stock.portal.web.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

@ParentPackage("portal")
@InterceptorRefs({@InterceptorRef("chain"), @InterceptorRef("portalStack")})
@Results( @Result(name = "test", location = "/WEB-INF/index.jsp",
                  type = "chain") )
public class TestAction extends BaseAction{
    
    private static final Logger log = Logger.getLogger(TestAction.class.getName());
//    @InjectEJB(name = "RulesEngineFacade")
//    private RulesEngineFacade rulesEngineFacade;


    public TestAction() {
        super();        
    }
    
    public String execute(){
        
        log.debug("TestAction");
        log.debug(ActionContext.getContext().getParameters());
        
//        System.out.println("**********************************************************");
//        System.out.println("**********************************************************");        
//        System.out.println("**********************************************************");
//        System.out.println("**********************************************************");
        

        //rulesEngineFacade.getActionsForContentElement()        
        
        return "test";
    }
}

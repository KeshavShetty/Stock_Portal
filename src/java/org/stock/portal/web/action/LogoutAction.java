package org.stock.portal.web.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.stock.portal.web.metroWL.action.NowWebClient;
import org.stock.portal.web.util.Constants;

import com.opensymphony.xwork2.ActionContext;
@Namespace(value="portal")
@ParentPackage("portal")
@Results( { @Result(name = "Logout", location = "/prepareLogin", type = "redirect"),
			@Result(name = "INPUT", location = "/WEB-INF/jsp/login.jsp"),
			@Result(name = "SUCCESS", location = "/WEB-INF/jsp/home.jsp"),
			@Result(name = "ERROR", location = "/WEB-INF/jsp/login.jsp")
})
@InterceptorRefs( { @InterceptorRef("i18nStack"),
		@InterceptorRef("portalStack") })
public class LogoutAction extends BaseAction {
	private static final Logger log = Logger.getLogger(LogoutAction.class.getName());

	public LogoutAction() {
		super();
	}
	@Action(value = "/logout")
	public String execute() {
		if (this.session.get(Constants.NOW_HANDLER)!=null) {
			NowWebClient nowClient = (NowWebClient)this.session.get(Constants.NOW_HANDLER);
			nowClient.logout();
			System.out.println("------------------------------------------- LOGOUT -------------------------------------------");
		}
		invalidateSession();
		ActionContext.getContext().getSession().clear();
		return SUCCESS;
	}
}
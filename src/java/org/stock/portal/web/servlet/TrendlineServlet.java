package org.stock.portal.web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import org.stock.portal.common.ServiceLocator;
import org.stock.portal.domain.BSEEodData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.User;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.util.Constants;

import java.io.*;
import java.util.*;

/**
 *  @web.servlet name="TrendlineServlet"
 *      description="Scrip or Symbol SearchServlet"
 * 
 *  @web.servlet-mapping url-pattern="/servlet/TrendlineServlet"
 * 
 * @author kshe
 *
 */

public class TrendlineServlet extends HttpServlet
{

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {
        try 
        {        
        	printAllRequestParams(request);
        	HttpSession session = request.getSession();
        	if (session !=null && session.getAttribute(Constants.LOGGED_IN_USER)!=null) {
        		User loggedUser = (User)session.getAttribute(Constants.LOGGED_IN_USER);
        		System.out.println("loggedUser="+loggedUser.getUserName());   
        		if (request.getParameter("action")!=null && request.getParameter("action").equals("trendline.save")) {
        			String symbol = request.getParameter("symbol");
        			String trendlineValue = request.getParameter("value");
        			DataManager dataManager = (DataManager)ServiceLocator.getInstance().getServiceFacade(DataManager.class);
        			dataManager.saveTrendline(loggedUser.getId(), symbol, trendlineValue);
        		}
        	}
        	ServletOutputStream stream = response.getOutputStream();
            stream.write("response.end".getBytes());
            stream.close();
        } catch (Exception e) {}

    }
    
    private void printAllRequestParams(HttpServletRequest request) {
		System.out.println("In TrendlineServlet printAllRequestParams");
		System.out.println("-----------------------------------------");
		Enumeration enums = request.getParameterNames();
		while(enums.hasMoreElements()) {
			String aParamName = (String)enums.nextElement();
			String[] paramValues = request.getParameterValues(aParamName);
			if (paramValues!=null && paramValues.length>0) {
				for(int i=0;i<paramValues.length;i++) {
					System.out.println(aParamName + " with value of "+"["+i+"]="+paramValues[i]);
				}
			}
		}
		System.out.println("=========================================");
	}
}
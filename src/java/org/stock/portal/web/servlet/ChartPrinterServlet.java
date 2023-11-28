package org.stock.portal.web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import org.stock.portal.common.ServiceLocator;
import org.stock.portal.domain.BSEEodData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.service.scrip.ScripManager;

import java.io.*;
import java.util.*;

/**
 *  @web.servlet name="ChartPrinterServlet"
 *      description="Scrip or Symbol SearchServlet"
 * 
 *  @web.servlet-mapping url-pattern="/prophet/plugin/ChartPrinter.class"
 * 
 * @author kshe
 *
 */

public class ChartPrinterServlet extends HttpServlet
{

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {
        try 
        {        
        	printAllRequestParams(request);
        	ServletOutputStream stream = response.getOutputStream();
            stream.write("response.end".getBytes());
            stream.close();
        } catch (Exception e) {}

    }
    
    private void printAllRequestParams(HttpServletRequest request) {
		System.out.println("In AddNotePluginServlet printAllRequestParams");
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
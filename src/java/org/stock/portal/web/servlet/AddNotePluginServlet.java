package org.stock.portal.web.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  @web.servlet name="AddNotePluginServlet"
 *      description="Scrip or Symbol SearchServlet"
 * 
 *  @web.servlet-mapping url-pattern="/prophet/plugin/AddNotePlugin.class"
 * 
 * @author kshe
 *
 */

public class AddNotePluginServlet extends HttpServlet
{

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {
        try 
        {        
        	printAllRequestParams(request);
        	response.setContentType("application/octet-stream");                    
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
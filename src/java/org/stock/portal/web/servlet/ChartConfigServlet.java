package org.stock.portal.web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;

/**
 *  @web.servlet name="ChartConfigServlet"
 *      description="User preference"
 * 
 *  @web.servlet-mapping url-pattern="/chart/ChartConfig"
 * 
 * @author kshe
 *
 */

public class ChartConfigServlet extends HttpServlet
{

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException
    {
        try 
        {        
        	printAllRequestParams(request);
        	ServletContext ctx = this.getServletContext();
            String realBasePath = ctx.getRealPath("/");            
            String  fileName = realBasePath + "/WEB-INF/ChartConfig.properties";
            File target = new File(fileName);
            System.out.println("target="+target);
            
            response.setContentType("application/octet-stream");
            ServletOutputStream stream = response.getOutputStream(); 

            BufferedInputStream fif = new BufferedInputStream(new FileInputStream(target)); 
            int data; 
            String eachline = "";
            while((data = fif.read()) != -1)
            { 
            	stream.write(data);
            	System.out.println("CData="+data);
//            	if (data=='\n') {
//            		eachline = eachline + data;
//            		System.out.println("Writing eachline="+eachline);
//            		//for(int i=0;i<eachline.length();i++) stream.write((int)eachline.charAt(i));
//            		eachline="";
//            	} else {
//            		eachline = eachline + data;
//            	}                
            } 
            fif.close(); 
            stream.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }

    }
    
    private void printAllRequestParams(HttpServletRequest request) {
		System.out.println("In ChartConfigServlet printAllRequestParams");
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
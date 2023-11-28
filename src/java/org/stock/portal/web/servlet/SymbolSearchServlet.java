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
 *  @web.servlet name="SymbolSearchServlet"
 *      description="Scrip or Symbol SearchServlet"
 * 
 *  @web.servlet-mapping url-pattern="/applets/JavaChart/lookupSymbols.jsp"
 * 
 * @author kshe
 *
 */

public class SymbolSearchServlet extends HttpServlet
{

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {
        try 
        {        
        	printAllRequestParams(request);
        	String searchString = request.getParameter("search");
        	ScripManager scripManager = (ScripManager)ServiceLocator.getInstance().getServiceFacade(ScripManager.class);
            List<Scrip> scripList = scripManager.getScripsBySearchString("EQ",searchString);
            
            ServletOutputStream stream = response.getOutputStream();
            for(int i=0;i<scripList.size();i++) {
            	Scrip aScrip = (Scrip)scripList.get(i);
            	if (aScrip.getBseName()!=null) {
            		stream.write((aScrip.getName()+" (BSE-"+aScrip.getBseName()+")").getBytes());
            		stream.write('\n');
            	} 
            	if (aScrip.getNseCode()!=null) {
            		stream.write((aScrip.getName()+" (NSE-"+aScrip.getNseCode()+")").getBytes());
            		stream.write('\n');
            	} 
            }
            
            List<String> extSerach = scripManager.getScripsBySearchStringExtendedSymbol(scripList);
            if (extSerach!=null && extSerach.size() >0) {
            	for(int i=0;i<extSerach.size();i++) {
            		stream.write((extSerach.get(i)).getBytes());
            		stream.write('\n');
            	}
            }
            stream.close();	
        } catch (Exception e) {}

    }
    
    private void printAllRequestParams(HttpServletRequest request) {
		System.out.println("In SymbolSearchServlet printAllRequestParams");
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
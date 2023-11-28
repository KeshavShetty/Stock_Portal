package org.stock.portal.web.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stock.portal.common.ServiceLocator;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.User;
import org.stock.portal.service.jqGrid.JqGridManager;
import org.stock.portal.web.util.Constants;

public class JqGridDataProviderServlet extends HttpServlet 
{

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException
    {
        try 
        {        
        	//printAllRequestParams(request);        	
        	Map<String, String> allRequestQueryParams = getAllRequestQueryParams(request);
        	String loggedUserId = "";
        	if (request.getSession(false)!=null && request.getSession(false).getAttribute((Constants.LOGGED_IN_USER))!=null) {
        		loggedUserId = ((User)request.getSession().getAttribute(Constants.LOGGED_IN_USER)).getId() + "";
        	}
        	allRequestQueryParams.put("UserID", loggedUserId);
        	
        	if (allRequestQueryParams.get("viewTableIdentifier")!=null && allRequestQueryParams.get("viewTableIdentifier").startsWith("ScripPage_")) {
        		Scrip aScrip = (Scrip)request.getSession(false).getAttribute(Constants.LAST_ACCESSED_SCRIP);   
        		if (aScrip!=null) {
        			if (allRequestQueryParams.get("viewTableIdentifier").startsWith("ScripPage_PeerTable")) {
        				allRequestQueryParams.put("ID", aScrip.getSector().getId()+"");
        			} else {
        				allRequestQueryParams.put("ID", aScrip.getId()+"");
        			}
        		}
        	}
        	String jsonOutput = getJsonDataOutput(allRequestQueryParams);
        	response.setContentType("application/json");
            ServletOutputStream stream = response.getOutputStream();
            
        	stream.write(request.getParameter("callback").getBytes());
        	stream.write(jsonOutput.getBytes());
        	stream.flush();
        	stream.close();
             
//            File target = new File("D:\\downloads\\getjsonp.txt");
//            BufferedInputStream fif = new BufferedInputStream(new FileInputStream(target)); 
//            int data; 
//            String eachline = "";
//            
//            while((data = fif.read()) != -1)
//            { 
//            	stream.write(data);              
//            } 
//            fif.close(); 
//            stream.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    private String getJsonDataOutput(Map<String, String> allRequestQueryParams) {
    	String retStr = "";
    	try {
    		JqGridManager jqGridManager = (JqGridManager)ServiceLocator.getInstance().getServiceFacade(JqGridManager.class);
    		retStr = jqGridManager.getJsonData(allRequestQueryParams);
    		if (allRequestQueryParams.get("UserID")!=null && allRequestQueryParams.get("UserID").length()>0) {
    			String jsonFilterString = allRequestQueryParams.get("filters");
    			if (jsonFilterString==null || jsonFilterString.length()==0) {
    				jsonFilterString = allRequestQueryParams.get("customFilters");
    			}
    			if (jsonFilterString!=null && jsonFilterString.length()>0) {
    				jqGridManager.saveUserSearch(allRequestQueryParams.get("UserID"), allRequestQueryParams.get("viewTableIdentifier"), jsonFilterString, "LastAccessed");
    			}
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return retStr;
    }
    
    private Map<String, String> getAllRequestQueryParams(HttpServletRequest request) {
    	Map<String, String> retMap = new HashMap<String, String>();
		System.out.println("In JqGridDummyDataServlet getAllRequestQueryParams");
		System.out.println("-----------------------------------------");
		Enumeration<String> enums = request.getParameterNames();
		while(enums.hasMoreElements()) {
			String aParamName = (String)enums.nextElement();
			String[] paramValues = request.getParameterValues(aParamName);
			if (paramValues!=null && paramValues.length>0) {
				for(int i=0;i<paramValues.length;i++) {
					retMap.put(aParamName, paramValues[i].trim());
					System.out.println("Param=" + aParamName + " Value="+paramValues[i]);
				}
			}
		}
		System.out.println("-----------------------------------------");
		return retMap;
    }
    
    private void printAllRequestParams(HttpServletRequest request) {
		System.out.println("In JqGridDummyDataServlet printAllRequestParams");
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
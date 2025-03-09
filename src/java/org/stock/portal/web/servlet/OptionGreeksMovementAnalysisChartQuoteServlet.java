
package org.stock.portal.web.servlet;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stock.portal.common.ServiceLocator;
import org.stock.portal.domain.dto.OptionSummaryDto;
import org.stock.portal.service.data.DataManager;

/**
 *  @web.servlet name="OptionGreeksMovementAnalysisChartQuoteServlet" 
 *      description="Stock data"
 * 
 *  @web.servlet-mapping url-pattern="/chart/OptionGreeksMovementAnalysisChartQuote"
 * 
 * @author kshe
 *
 */

public class OptionGreeksMovementAnalysisChartQuoteServlet extends HttpServlet
{	
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException
	{
		try
		{
			ServletOutputStream stream = response.getOutputStream();
	        DataOutputStream os = new DataOutputStream(stream);
	        
	        DataManager dataManager = (DataManager)ServiceLocator.getInstance().getServiceFacade(DataManager.class);
	        
	        String indexname = request.getParameter("indexname");
	        String forDate = request.getParameter("forDate"); 
	        
	        String csvFilename = dataManager.getOptionGreeksMovmentAnalysis(indexname, forDate);	
	        
	        InputStream is = null;
	        is = new FileInputStream(csvFilename);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	        is.close();
	        os.close();
	        stream.close();	        
		}catch(Exception ex){
			ex.printStackTrace();
		}			
	}
}
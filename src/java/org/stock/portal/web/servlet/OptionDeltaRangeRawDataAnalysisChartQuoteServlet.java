
package org.stock.portal.web.servlet;

import java.io.ByteArrayInputStream;
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
 *  @web.servlet name="OptionVegaValueAnalysisChartQuoteServlet" 
 *      description="Stock data"
 * 
 *  @web.servlet-mapping url-pattern="/chart/OptionVegaValueAnalysisChartQuote"
 * 
 * @author kshe
 *
 */

public class OptionDeltaRangeRawDataAnalysisChartQuoteServlet extends HttpServlet
{	
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException
	{
		try
		{
			Long beginTime = System.currentTimeMillis();
			
			ServletOutputStream stream = response.getOutputStream();
	        DataOutputStream os = new DataOutputStream(stream);
	        
	        DataManager dataManager = (DataManager)ServiceLocator.getInstance().getServiceFacade(DataManager.class);
	        
	        Long mainInstrumentId = Long.parseLong(request.getParameter("mainInstrumentId"));
	        String forDate = request.getParameter("forDate"); 
	        float baseDelta = Float.parseFloat(request.getParameter("baseDelta"));
	        
	        // String csvFilename = dataManager.getOptionDeltaRangeRawDataAnalysis(mainInstrumentId, forDate, baseDelta);	
	        byte[] csvContents = dataManager.getOptionDeltaRangeRawDataAnalysisAsByteArray(mainInstrumentId, forDate, baseDelta);
	        
	        InputStream is = null;
	        is = new ByteArrayInputStream(csvContents);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	        is.close();
	        os.close();
	        stream.close();
	        
	        Long endTime = System.currentTimeMillis();
	        Long timeTaken = endTime-beginTime;
	        
			System.out.println("OptionDeltaRangeRawDataAnalysisChartQuoteServlet time taken(ms) " + timeTaken);
		}catch(Exception ex){
			ex.printStackTrace();
		}			
	}
}
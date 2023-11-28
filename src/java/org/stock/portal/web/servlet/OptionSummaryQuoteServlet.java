
package org.stock.portal.web.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
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
 *  @web.servlet name="OptionSummaryQuoteServlet"
 *      description="Stock data"
 * 
 *  @web.servlet-mapping url-pattern="/chart/OptionSummaryQuote"
 * 
 * @author kshe
 *
 */

public class OptionSummaryQuoteServlet extends HttpServlet
{	
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException
	{
		try
		{
			ServletOutputStream stream = response.getOutputStream();
	        DataOutputStream outStream = new DataOutputStream(stream);
	        
	        outStream.write(("Time, TotalCEOI, TotalPEOI, TotalChangeInCEOI, TotalChangeInPEOI, MinCE, MaxPE, UnderlyingValue, pcr, pcr5min, SellerMeanPrice, SellerMeanPriceByWorth, BestCostEffectiveStrike, BestCostEffectiveStrikeByWorth, SellerMeanPriceFrom5, BestCostEffectiveStrikeFrom5\n").getBytes());
	        
	        DataManager dataManager = (DataManager)ServiceLocator.getInstance().getServiceFacade(DataManager.class);	        
	        String indexName = request.getParameter("indexName");
	        String forDate = request.getParameter("forDate");
	        
	        List<OptionSummaryDto> oiData = dataManager.getOptionOIData(indexName, forDate);	        
	        for(int i=0;i<oiData.size();i++) {
	        	OptionSummaryDto aDatapoint = oiData.get(i);
	        	
	        	String aRow = aDatapoint .getRecordDate() + "," + aDatapoint.getTotalCEOI() + "," + aDatapoint.getTotalPEOI() + "," + aDatapoint.getTotalChangeInCEOI() + "," + aDatapoint.getTotalChangeInPEOI() 
	        			+ ", " + aDatapoint.getMinCEWithHighestOI() +", " + aDatapoint.getMaxPEWithHighestOI() + "," +  aDatapoint.getUnderlyingValue() + "," + aDatapoint.getPcr();
	        	if (i>5) {
	        		float ceChangeIn5Min = aDatapoint.getTotalCEOI() - oiData.get(i-5).getTotalCEOI();
	        		float peChangeIn5Min = aDatapoint.getTotalPEOI() - oiData.get(i-5).getTotalPEOI();
	        		float pcrin5min = peChangeIn5Min/ceChangeIn5Min;
	        		aRow = aRow + "," + (pcrin5min);
	        	} else {
	        		aRow = aRow + ",0";
	        	}
	        	aRow = aRow + ",";
	        	if (aDatapoint.getOptionSellerMeanValue()!=0) aRow = aRow + aDatapoint.getOptionSellerMeanValue();
	        	aRow = aRow + ",";
	        	if (aDatapoint.getOptionSellerMeanValueByWorth()!=0) aRow = aRow + aDatapoint.getOptionSellerMeanValueByWorth();
	        	aRow = aRow + ",";
	        	if (aDatapoint.getBestCostEffectiveStrike()!=0) aRow = aRow + aDatapoint.getBestCostEffectiveStrike();
	        	aRow = aRow + ",";
	        	if (aDatapoint.getBestCostEffectiveStrikeByWorth()!=0) aRow = aRow + aDatapoint.getBestCostEffectiveStrikeByWorth();
	        	aRow = aRow + ",";
	        	if (aDatapoint.getOptionSellerMeanValueFrom5()!=0) aRow = aRow + aDatapoint.getOptionSellerMeanValueFrom5();
	        	aRow = aRow + ",";
	        	if (aDatapoint.getBestCostEffectiveStrikeFrom5()!=0) aRow = aRow + aDatapoint.getBestCostEffectiveStrikeFrom5();
	        	aRow = aRow + "\n";
	        	
	        	outStream.write(aRow.getBytes());
	        }    
	        
	        outStream.close();
	        stream.close();	        
		}catch(Exception ex){
			ex.printStackTrace();
		}			
	}
}
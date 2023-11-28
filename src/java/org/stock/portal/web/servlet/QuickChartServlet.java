package org.stock.portal.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stock.portal.common.ServiceLocator;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.dto.ScripEOD;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.util.QuickChartImageGenerator;

import ChartDirector.BaseChart;
import ChartDirector.Chart;
import ChartDirector.WebChartViewer;

/**
 *  @web.servlet name="QuickChartServlet"
 *      description="Stock data"
 * 
 *  @web.servlet-mapping url-pattern="/chart/quickchart/"
 * 
 * @author kshe
 *
 */

public class QuickChartServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		try {
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("1. " +request.getLocalAddr());
			System.out.println("2. " +request.getLocalName());
			System.out.println("3. " +request.getLocalPort());
			System.out.println("4. " +request.getPathInfo());
			System.out.println("5. " +request.getContextPath());
			System.out.println("6. " +request.getProtocol());
			System.out.println("8. " +request.getRemoteAddr());
			System.out.println("9. " +request.getRemoteHost());
			System.out.println("10. " +request.getRemotePort());
			System.out.println("11. " +request.getRequestURI());
			System.out.println("12. " +request.getServerName());
			System.out.println("13. " +request.getServerPort());
			System.out.println("14. " +request.getRequestURL());
			
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			printAllRequestParams(request);
			String duration = request.getParameter("TimeRange"); //=1d
			
			String symbol="";
			
			if (request.getParameter("TickerSymbol")!=null) {
				symbol = request.getParameter("TickerSymbol").trim();
				System.out.println("Symbol from request="+symbol);
			}
			
			if (request.getParameter("TickerID")!=null) {
				String tickerId = request.getParameter("TickerID").trim();
				if (tickerId!=null && tickerId.length()>0) { //Use this to get Symbol
					ScripManager scripManager = (ScripManager)ServiceLocator.getInstance().getServiceFacade(ScripManager.class);
					Scrip aScrip = scripManager.getScripById(new Long(tickerId));
					if (aScrip.getNseCode()!=null) symbol = "NSE-"+aScrip.getNseCode();
					else if (aScrip.getBseCode()!=null) symbol = "BSE-"+aScrip.getBseName();
				}
			}
			Calendar cal = Calendar.getInstance(); 
			if (request.getParameter("maxDataDate")!=null && request.getParameter("maxDataDate").length()>0) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				cal.setTime(sdf.parse(request.getParameter("maxDataDate").trim()));
			} else {
				cal.add(Calendar.DATE, 1);
			}
			
			Date toDate = cal.getTime();
			
			cal.add(Calendar.DATE, -1*(Integer.parseInt(duration)));
			Date fromDate = cal.getTime();
			System.out.println("fromDate="+fromDate+" toDate="+toDate);
			String compareKey = "";
			if (request.getParameter("CompareWith")!=null) compareKey = request.getParameter("CompareWith").trim();
			
			getEODData(request, response, symbol, fromDate, toDate, compareKey);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}			
	}
	
	private void printAllRequestParams(HttpServletRequest request) {
		System.out.println("In QuoteDataServlet printAllRequestParams");
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
	
	protected Map getStockData(String symbol,Date fromDate, Date toDate){//Scripname should be exchangecode-scripcode e.g: BSE-ACC
    	Map retMap = new HashMap();
        List retList = new ArrayList();
        String scripName="";
        String exchange = "BSE";
        if (symbol!=null && symbol.indexOf("-")==3) {
        	exchange = symbol.substring(0,3);
        	scripName=symbol.substring(4);                
        } else {
        	scripName=symbol;
        }
        
        try {
            String symbolName="";
            DataManager dataManager = (DataManager)ServiceLocator.getInstance().getServiceFacade(DataManager.class);
            Map dataMap = dataManager.getEodData(exchange,scripName,fromDate, toDate);
            retList = (List<ScripEOD>)dataMap.get("EODataList");
            Scrip selectedScrip = (Scrip)dataMap.get("Scrip");
            if (selectedScrip!=null) {
            	symbolName = selectedScrip.getName();
            }                
            System.out.println("In QuoteDataservlet size="+retList.size());
            if (retList.size()>0) {
            	ScripEOD lastEntry = (ScripEOD) retList.get(retList.size()-1);
            	retMap.put("CMP",new Float(lastEntry.getClosePrice()));
            	if (retList.size()>1) {
            		ScripEOD previousEntry = (ScripEOD) retList.get(retList.size()-2);
            		retMap.put("PreviousClose",new Float(previousEntry.getClosePrice()));
            		retMap.put("TodaysGain",new Float(lastEntry.getClosePrice()-previousEntry.getClosePrice()));
            	}
            }                
            System.out.println("In QuoteDataservlet size="+retList.size());
            retMap.put("symbolName",symbolName);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        retMap.put("dataList",retList);
        return retMap;
    }

	private void getEODData(HttpServletRequest request, HttpServletResponse response, String symbol, Date fromDate, Date toDate, String compareSymbol) throws Exception {
		System.out.println("------New getEODData"); 
        Map dataMap = getStockData(symbol, fromDate, toDate);//Symbol should be exchangecode-scripcode e.g: BSE-ACC     
        List dataList = (List)dataMap.get("dataList");
        String scripName=(String)dataMap.get("symbolName");
        
        List compareDataList = null;
        String compareScripName = "";
        if (compareSymbol!=null && compareSymbol.length()>0) {
        	Map compareDataMap = getStockData(compareSymbol, fromDate, toDate);     
            compareDataList = (List)compareDataMap.get("dataList");
            compareScripName=(String)compareDataMap.get("symbolName");
        }
        if (compareDataList!=null && dataList!=null) { // You need to merge and make sure no data missing for ant particualr date
        	
        }
        String paddingFlag = request.getParameter("padding");
        if (paddingFlag!=null && paddingFlag.length()>0) {
        	List<ScripEOD> eodData = (List<ScripEOD>)dataMap.get( "dataList");
        	if (eodData!=null && eodData.size()>0) {
        		ScripEOD lastEod = eodData.get(eodData.size()-1);
        		for(int i=0;i<5;i++) {
        			float openPrice = 0;
        			float highPrice = 0;
        			float lowPrice = 0;
        			float closePrice = 0;
        			Long volume = 0L;
        			
        			if (paddingFlag.equalsIgnoreCase("above")) {
        				lowPrice = lastEod.getClosePrice();
        				openPrice = lowPrice * (100f + 0.5f)/100f;
        				closePrice = openPrice * (100f + 2f)/100f;
        				highPrice = closePrice * (100f + 0.5f)/100f;
        				volume = lastEod.getVolume();
            		} else if (paddingFlag.equalsIgnoreCase("below")) {
            			highPrice = lastEod.getClosePrice();
            			openPrice = highPrice * (100f - 0.5f)/100f;
            			closePrice = openPrice * (100f - 2f)/100f;
            			lowPrice = closePrice * (100f - 0.5f)/100f;
        				volume = lastEod.getVolume();
            		} else if (paddingFlag.equalsIgnoreCase("Sideway")) {
            			lowPrice = lastEod.getLowPrice();
            			openPrice = lowPrice * (100f + 0.5f)/100f;
            			closePrice = openPrice * (100f + 2f)/100f;
            			highPrice = closePrice * (100f + 0.5f)/100f;
        				volume = lastEod.getVolume();
            		}
        			ScripEOD newEod = new ScripEOD(openPrice, highPrice, lowPrice, closePrice, volume, getNextDate(lastEod.getDataDate()));
        			eodData.add(newEod);
        			lastEod = newEod;
        		}
        		dataMap.put( "dataList", eodData);
        	}
        	
        }
        
        response.setContentType("application/octet-stream");                    
        ServletOutputStream stream = response.getOutputStream();        
        boolean usePercentageData = false;
        System.out.println("PercentageChartFlag=" + request.getParameter("PercentageChartFlag"));
        if (request.getParameter("PercentageChartFlag")!=null && "1".equals(request.getParameter("PercentageChartFlag")) ) {
        	usePercentageData = true;
        }
        List finalDataList = null;
        if (usePercentageData) {
        	finalDataList = new ArrayList<ScripEOD>();
        	ScripEOD prevDay = null;
        	for(int i=0;i<dataList.size();i++) {
        		ScripEOD aDayData = (ScripEOD)dataList.get(i);
        		if (i>0) {
        			ScripEOD newData = new ScripEOD();
        			newData.setOpenPrice( (aDayData.getOpenPrice()-prevDay.getOpenPrice())*100f/prevDay.getOpenPrice());
        			newData.setHighPrice( (aDayData.getHighPrice()-prevDay.getHighPrice())*100f/prevDay.getHighPrice());
        			newData.setLowPrice( (aDayData.getLowPrice()-prevDay.getLowPrice())*100f/prevDay.getLowPrice());
        			newData.setClosePrice( (aDayData.getClosePrice()-prevDay.getClosePrice())*100f/prevDay.getClosePrice());
        			newData.setDataDate(aDayData.getDataDate());
        			newData.setVolume(aDayData.getVolume());
        			newData.setScripId(aDayData.getScripId());
        			finalDataList.add(newData);
        		}
        			prevDay = aDayData;
        	}
        } else {
        	finalDataList = dataList;
        }
        QuickChartImageGenerator imageGenerator = new QuickChartImageGenerator(scripName+"("+symbol+")",finalDataList, compareSymbol, compareDataList);
        BaseChart c = imageGenerator.drawChart(request);

        try {            
            if (WebChartViewer.streamChart(response, c.makeChart2(Chart.PNG)))
                return;
        } catch (IllegalStateException e) {            
            response.sendRedirect(response.encodeRedirectURL("getchart.jsp?" + c.makeSession(request, "chart1")));
            return;
        }
	}   
	
	private Date getNextDate(Date curDate) {
		Date retVal = curDate;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(curDate);
			cal.add(Calendar.DATE, 1);
			retVal = cal.getTime();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retVal;
	}
}

package org.stock.portal.web.servlet;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import javax.servlet.http.HttpSession;

import org.stock.portal.common.SPConstants;
import org.stock.portal.common.ServiceLocator;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.User;
import org.stock.portal.domain.dto.ScripEOD;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.web.util.Constants;

/**
 *  @web.servlet name="QuoteDataServlet"
 *      description="Stock data"
 * 
 *  @web.servlet-mapping url-pattern="/chart/QuoteData"
 * 
 * @author kshe
 *
 */

public class QuoteDataServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException
	{
		try
		{ 
			printAllRequestParams(request);
			String duration = request.getParameter("duration"); //=1d
			String symbol=request.getParameter("symbol");			
			System.out.println("Symbol from request="+symbol);
			
			String[] symbolParts = symbol.split("-"); // Request input is in the form NSE-INFY-EW1 (Where EW is elliot wave for llowed by a number
			
			String mainSymbol = symbolParts[0]+"-"+symbolParts[1];
			String extendedSymbol = null;
			if (symbolParts.length>2) {
				extendedSymbol = symbolParts[2];
			}
			System.out.println("mainSymbol="+mainSymbol + " extendedSymbol="+extendedSymbol);
			if (duration!=null && duration.length()>0 && duration.endsWith("d")) {
				getIntraDayData(request, response);
			} else {
				Calendar cal = Calendar.getInstance(); cal.add(Calendar.DATE, 1);
            	Date toDate = cal.getTime();
            	Date fromDate = toDate;
            	if (request.getParameter("client")!=null && request.getParameter("client").equalsIgnoreCase("highChart")) {
            		if (request.getParameter("startDate")!=null) { //Form date
	            		cal.setTime(SPConstants.SPCORE_DATE_FORMAT.parse(request.getParameter("startDate")));
	            		fromDate = cal.getTime();
	            	}
	            	if (request.getParameter("endDate")!=null) { //Form date
	            		cal.setTime(SPConstants.SPCORE_DATE_FORMAT.parse(request.getParameter("endDate")));
	            		toDate = cal.getTime();
	            	}
            	}else {
	            	if (request.getParameter("startDate")!=null) { //Form date
	            		cal.setTime(SPConstants.SPCORE_MONTH_DATE_FORMAT.parse(request.getParameter("startDate")));
	            		fromDate = cal.getTime();
	            	}
	            	if (request.getParameter("endDate")!=null) { //Form date
	            		cal.setTime(SPConstants.SPCORE_MONTH_DATE_FORMAT.parse(request.getParameter("endDate")));
	            		toDate = cal.getTime();
	            	}
	            	if (duration!=null && duration.length()>0) {
	            		if (duration.equalsIgnoreCase("All")){
	            			System.out.println("get All");
	            			cal.add(Calendar.YEAR,-30);
	                		fromDate = cal.getTime();
	            		} else if (duration.endsWith("y")) {
	            			System.out.println("noOfYears="+duration.substring(0,duration.length()-1));
	    					int noOfYears = Integer.parseInt(duration.substring(0,duration.length()-1));
	    					cal.add(Calendar.YEAR,noOfYears*-1);
	    					cal.add(Calendar.MONTH,-1);
	    					fromDate = cal.getTime();
	            		} else if (duration.endsWith("m")) {
	            			System.out.println("noOfmonths="+duration.substring(0,duration.length()-1));
	    					int noOfMonths = Integer.parseInt(duration.substring(0,duration.length()-1));
	    					cal.add(Calendar.MONTH,noOfMonths*-1);
	    					fromDate = cal.getTime();
	            		}            		
	            	}
				}
            	getEODData(request, response, mainSymbol, fromDate, toDate, extendedSymbol);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
	}
	
	private Map getNSESnapshotData(String symbol, Date fromDate, Date toDate) {
		Map returnMap = new HashMap();
		
		 String scripName="";
         String exchange = "NSE";
         if (symbol!=null && symbol.indexOf("-")==3) {
         	exchange = symbol.substring(0,3);
         	scripName=symbol.substring(4);                
         } else {
         	scripName=symbol;
         }         
         try {
             DataManager dataManager = (DataManager)ServiceLocator.getInstance().getServiceFacade(DataManager.class);
             returnMap = dataManager.getNSESnapshotData(exchange,scripName,fromDate, toDate);
         } catch(Exception ex) {
        	 ex.printStackTrace();
         }
		
		return returnMap;
	}

	private void getEODData(HttpServletRequest request, HttpServletResponse response, String symbol, Date fromDate, Date toDate, String extendedSymbol) throws Exception {
		System.out.println("------New getEODData fromDate="+fromDate+" toDate="+toDate); 
        Map dataMap = getStockData(symbol, fromDate, toDate);//Symbol should be exchangecode-scripcode e.g: BSE-ACC     
        List dataList = (List)dataMap.get("dataList");        
        String scripName=(String)dataMap.get("symbolName");
        if (request.getParameter("outputFormat")!=null && request.getParameter("outputFormat").equalsIgnoreCase("json")) {
        	Map<Date, ScripEOD> snapshotData = getNSESnapshotData(symbol, fromDate, toDate);
        	response.setContentType("text/javascript");
			ServletOutputStream servletStream = response.getOutputStream();
			servletStream.write("[".getBytes());servletStream.write(System.getProperty("line.separator").getBytes()); 
			boolean isFirstLineFinished = false;
			for(int i=0;i<dataList.size();i++){                        
				ScripEOD aData = (ScripEOD)dataList.get(i);
				if (isFirstLineFinished) { 
					servletStream.write(",".getBytes()); servletStream.write(System.getProperty("line.separator").getBytes()); 
				}
				ScripEOD snapshotDataOnDate = snapshotData.get(aData.getDataDate());
				isFirstLineFinished = true;
				servletStream.write(  ("[" + aData.getDataDate().getTime() + "," + aData.getOpenPrice()+ "," + aData.getHighPrice() +  "," + aData.getLowPrice() + "," + aData.getClosePrice() + "," + aData.getVolume()+"," + (snapshotDataOnDate!=null?snapshotDataOnDate.getMeanPrice():"0")+"," + (snapshotDataOnDate!=null?snapshotDataOnDate.getCfWeightage():"0") +"]").getBytes());
					
			 }
			servletStream.write("]".getBytes());servletStream.write(System.getProperty("line.separator").getBytes());
			servletStream.flush();
			servletStream.close();
        } else {        
	        response.setContentType("application/octet-stream");                    
	        ServletOutputStream stream = response.getOutputStream();
	        DataOutputStream outStream = new DataOutputStream(stream); 
	        //if (dataList!=null && dataList.size()>0) {
		        outStream.writeBytes(getDataHeader(symbol,scripName,dataList.size()));
		        outStream.write('\n');
		        ScripEOD lastRow = null; 
		        for(int i=0;i<dataList.size();i++){                        
		            ScripEOD aData = (ScripEOD)dataList.get(i);
		            outStream.writeInt(aData.getDataDate().getYear()); //Write year
		            outStream.writeInt(aData.getDataDate().getMonth()*32+aData.getDataDate().getDate()); //Write month & date
		                        
		            outStream.writeFloat(aData.getOpenPrice()); //Write open
		            outStream.writeFloat(aData.getHighPrice()); //Write high
		            outStream.writeFloat(aData.getLowPrice()); //Write low
		            outStream.writeFloat(aData.getClosePrice()); //Write close
		            
		            outStream.writeLong(aData.getVolume()); //Write volume
		            outStream.writeInt(1); //Write dummy int
		            lastRow = aData;
		        }
	//	        if (lastRow!=null) { // Adding one extra row (Same as last candle)        	                   
	// 	            ScripEOD aData = lastRow;
	// 	            outStream.writeInt(aData.getDataDate().getYear()); //Write year
	// 	            outStream.writeInt(aData.getDataDate().getMonth()*32+aData.getDataDate().getDate()); //Write month & date
	// 	                        
	// 	            outStream.writeFloat(aData.getOpenPrice()); //Write open
	// 	            outStream.writeFloat(aData.getHighPrice()); //Write high
	// 	            outStream.writeFloat(aData.getLowPrice()); //Write low
	// 	            outStream.writeFloat(aData.getClosePrice()); //Write close
	// 	            
	// 	            outStream.writeLong(aData.getVolume()); //Write volume
	// 	            outStream.writeInt(1); //Write dummy int
	// 	            lastRow = aData;	 	       
	//	        }
		        outStream.write('\n');
		        Float todaysGain = 0f;
		        if (dataMap.get("TodaysGain")!=null) {
		        	todaysGain = (Float)dataMap.get("TodaysGain");
		        }
		        
		        Float prevClose = 0f;
		        if (dataMap.get("PreviousClose")!=null) {
		        	prevClose = (Float)dataMap.get("PreviousClose");
		        }
		        
		        Float cmp = 0f;
		        if (dataMap.get("CMP")!=null) {
		        	cmp = (Float)dataMap.get("CMP");
		        }
		        writeDataFooter(request, symbol,outStream,cmp,todaysGain,prevClose, extendedSymbol);
	        //}
	        outStream.close();
	        stream.close();
        }
	}
	
	public void getIntraDayData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String realBasePath = this.getServletContext().getRealPath("/");            
        String  fileName = realBasePath + "WEB-INF/IntrdayData";
        File target = new File(fileName);

        response.setContentType("application/octet-stream");
        ServletOutputStream stream = response.getOutputStream(); 

        BufferedInputStream fif = new BufferedInputStream(new FileInputStream(target)); 
        int data; 
        while((data = fif.read()) != -1) { 
            stream.write(data); 
        } 
        fif.close(); 
        stream.close();
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
		enums = request.getHeaderNames();
		while(enums.hasMoreElements()) {
			String aParamName = (String)enums.nextElement();
			String paramValues = request.getHeader(aParamName);
			System.out.println(aParamName + " Header name="+aParamName+ " value=" + paramValues);
		}
//		User account = (User)request.getSession(false).getAttribute(Constants.LOGGED_IN_USER);
//		if (account!=null) {
//			System.out.println("===Account in session==="+account.getUserName());
//		}
		System.out.println("=========================================");
	}
        
        private String getDataHeader(String symbol,String scripName,int dataSsize){            
            return "stock|"+symbol.substring(4)+"|"+scripName+"|"+dataSsize;            
        }
        
        private void writeDataFooter(HttpServletRequest request, String scripCode,DataOutputStream outStream,Float cmp, Float todayasGain,Float previousClose, String extendedSymbol){
            try{
                Date curDate = new Date();
                outStream.write('\n');
                outStream.write('\n');
                
                String symbolName2Use = scripCode;
                if (extendedSymbol!=null) symbolName2Use = symbolName2Use + "-" + extendedSymbol;
                
                outStream.writeBytes("barsofar|"+symbolName2Use+"|"+(curDate.getYear()+1900)+"-"+curDate.getMonth()+"-"+curDate.getDate()+"T183000|23.910|23.920|23.930|" + cmp + "|" + (cmp.floatValue()-previousClose.floatValue()) +"|0|-"); outStream.write('\n');
                outStream.writeBytes("markethours|"+symbolName2Use+"|930|1600"); outStream.write('\n');
                outStream.writeBytes("exchangeId|"+symbolName2Use+"|18"); outStream.write('\n');            
                outStream.writeBytes("server|PATIENCE/QDS.872"); outStream.write('\n');

                outStream.writeBytes("tcpip|127.0.0.1"); outStream.write('\n');
                HttpSession session = request.getSession();
            	if (session !=null && session.getAttribute(Constants.LOGGED_IN_USER)!=null) {
            		User loggedUser = (User)session.getAttribute(Constants.LOGGED_IN_USER);
            		DataManager dataManager = (DataManager)ServiceLocator.getInstance().getServiceFacade(DataManager.class);
            		
            		String trendLine = dataManager.getTrendline(loggedUser.getId(), scripCode, extendedSymbol);
            		System.out.println("In Quote data servlet trendLine="+trendLine);
            		if (trendLine!=null && trendLine.length()>0) {
            			outStream.writeBytes("trendline." + symbolName2Use + "|" + trendLine); outStream.write('\n');		
            		}
            	}
                
                outStream.writeBytes("status|Tall=62, Tdata=0, Tbsf=46, Tet=0, Ttrends=0, Tnotes=0"); outStream.write('\n');
                
                //outStream.writeBytes("studyset." + scripCode + "|STO_BS,14,5|PREFS=.last"); outStream.write('\n'); //Tried this but didn't work
                
                outStream.writeBytes("end.of.file");
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }        
        
        protected Map getStockData(String symbol,Date fromDate, Date toDate){//Scripname should be exchangecode-scripcode e.g: BSE-ACC
        	Map retMap = new HashMap();
            List retList = new ArrayList();
            String scripName="";
            String exchange = "NSE";
            if (symbol!=null && symbol.indexOf("-")==3) {
            	exchange = symbol.substring(0,3);
            	scripName=symbol.substring(4);                
            } else {
            	scripName=symbol;
            }
            
            try {
                String symbolName="";
                //float cmp = 0;
                //float todaysGain = 0;
                //float previousClose = 0;
                //float currentClose = 0;
                DataManager dataManager = (DataManager)ServiceLocator.getInstance().getServiceFacade(DataManager.class);
                Map dataMap = dataManager.getEodData(exchange,scripName,fromDate, toDate);
                retList = (List<ScripEOD>)dataMap.get("EODataList");
                Scrip selectedScrip = (Scrip)dataMap.get("Scrip");
                if (selectedScrip!=null) {
                	symbolName = selectedScrip.getName();
                }
//                for(int i=0;i<eodData.size();i++) {
//                	previousClose = currentClose;
//                	EodData aEodData = null;
//                	if (exchange.equals("BSE")) aEodData = (BSEEodData) eodData.get(i);
//                	else  aEodData = (NSEEodData) eodData.get(i);
//                	
//                    ScripEOD aStockData = new ScripEOD();                    
//                    aStockData.setDataDate(aEodData.getDataDate());
//                    aStockData.setOpenPrice(aEodData.getOpenPrice());
//                    aStockData.setHighPrice(aEodData.getHighPrice());
//                    aStockData.setLowPrice(aEodData.getLowPrice());
//                    aStockData.setClosePrice(aEodData.getClosePrice());
//                    aStockData.setVolume(aEodData.getVolume());
//                    retList.add(aStockData);
//                    cmp = aEodData.getClosePrice();
//                    currentClose = cmp;
//                    todaysGain = currentClose - previousClose;
//                    
//                }
                
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
}
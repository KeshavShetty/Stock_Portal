package org.stock.portal.web.servlet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.stock.portal.common.ServiceLocator;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.dto.ScripEOD;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.service.scrip.ScripManager;
import org.stock.portal.web.util.Constants;

/**
 *  @web.servlet name="StreamingTickChartServlet"
 *      description="Stock data"
 * 
 *  @web.servlet-mapping url-pattern="/chart/streamingTickchart"
 * 
 * @author kshe
 *
 */

public class StreamingTickChartServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		try {
			printAllRequestParams(request);
			
			String dataDate = request.getParameter("dataDate"); //=1d
			String startHour = request.getParameter("startHour");
			String endHour = request.getParameter("endHour");
			String timeGap = request.getParameter("timeGap");
			
			String symbol=null;			
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
			if (symbol==null) {
				Scrip scrip = (Scrip)request.getSession().getAttribute(Constants.LAST_ACCESSED_SCRIP);
				if (scrip.getNseCode()!=null) symbol = "NSE-"+scrip.getNseCode();
				else if (scrip.getBseCode()!=null) symbol = "BSE-"+scrip.getBseName();
			}
			if (startHour==null || startHour.length()==0) {
				startHour = "09:15";
			}
			if (endHour==null || endHour.length()==0) {
				endHour = "15:15";
			}
			getIntradayData(request, response, symbol, dataDate, startHour, endHour, timeGap);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}			
	}
	
	private void printAllRequestParams(HttpServletRequest request) {
		System.out.println("In Streaming Tick Chart printAllRequestParams");
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
	
	private void getIntradayData(HttpServletRequest request, HttpServletResponse response, String symbol, String dataDate, String startHour, String endHour, String timeGap) throws Exception {
        
		String actualSymbol = symbol.substring(4);
		System.out.println("------actualSymbol="+actualSymbol);	
		
		SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {						
			response.setContentType("text/javascript");
			ServletOutputStream servletStream = response.getOutputStream();
			servletStream.write("[".getBytes());servletStream.write(System.getProperty("line.separator").getBytes()); 
						
			Calendar calStart = Calendar.getInstance();
			calStart.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(dataDate));
			
			String startHourPart = (startHour.split(":"))[0];
			if (startHourPart.startsWith("0")) startHourPart = startHourPart.substring(1);
			String startMinutePart = (startHour.split(":"))[1];
			if (startMinutePart.startsWith("0")) startMinutePart = startMinutePart.substring(1);
			
			calStart.set(Calendar.HOUR,Integer.parseInt(startHourPart));
			calStart.set(Calendar.MINUTE,Integer.parseInt(startMinutePart));
			
			Date startTime = calStart.getTime();
			System.out.println("Calculated startTime="+startTime);
			
			String endHourPart = (endHour.split(":"))[0];
			if (endHourPart.startsWith("0")) endHourPart = endHourPart.substring(1);
			String endMinutePart = (endHour.split(":"))[1];
			if (endMinutePart.startsWith("0")) endMinutePart = endMinutePart.substring(1);
			
			calStart.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(dataDate));
			
			calStart.set(Calendar.HOUR,Integer.parseInt(endHourPart));
			calStart.set(Calendar.MINUTE,Integer.parseInt(endMinutePart));
			
			Date endTime = calStart.getTime();
			System.out.println("Calculated endTime="+endTime);
			
			DataManager dataManager = (DataManager)ServiceLocator.getInstance().getServiceFacade(DataManager.class);
			
			List<ScripEOD> tickList = dataManager.getTickData(actualSymbol, startTime, endTime);
			boolean isFirstLineFinished = false;
			int tickGapAsInt = Integer.parseInt(timeGap);
			
			for(int i=0;i<tickList.size();i=i+tickGapAsInt) {
				Float openPrice = 0f;
				Float highPrice = 0f;
				Float lowPrice = 0f;
				Float closePrice = 0f;
				Long volume = 0L;
				int upperLimit = (i+tickGapAsInt)<tickList.size()?(i+tickGapAsInt):tickList.size();
				Date lastTickTime = null;
				for(int j=i;j<upperLimit;j=j+1) {
					ScripEOD aTick = tickList.get(j);
					if (j==i) {
						openPrice = aTick.getClosePrice();
						highPrice = aTick.getClosePrice();
						lowPrice = aTick.getClosePrice();						
					} else {
						if ( aTick.getClosePrice()>highPrice) highPrice = aTick.getClosePrice();
						if ( aTick.getClosePrice()<lowPrice) lowPrice = aTick.getClosePrice();						
					}
					closePrice = aTick.getClosePrice();
					volume = volume + aTick.getVolume();
					lastTickTime = aTick.getDataDate();
				}
					
				if (isFirstLineFinished) { servletStream.write(",".getBytes()); servletStream.write(System.getProperty("line.separator").getBytes()); } 
				isFirstLineFinished = true;
				servletStream.write(  ("[" + lastTickTime.getTime() + "," + openPrice+ "," + highPrice +  "," + lowPrice + "," + closePrice + "," + volume +"]").getBytes());	
			}
			servletStream.write("]".getBytes());servletStream.write(System.getProperty("line.separator").getBytes());
			servletStream.flush();
			servletStream.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
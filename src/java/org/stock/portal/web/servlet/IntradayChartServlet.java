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
 *  @web.servlet name="IntradayChartServlet"
 *      description="Stock data"
 * 
 *  @web.servlet-mapping url-pattern="/chart/intradaychart"
 * 
 * @author kshe
 *
 */

public class IntradayChartServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final String IEOD_FILE_PATH="D:\\Data\\intra\\NSE\\"; 
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		try {
			printAllRequestParams(request);
			
			String dataDate = request.getParameter("dataDate"); //=1d
			String nextDate = request.getParameter("nextDate"); //=1d
			System.out.println("nextDate="+nextDate);
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
			//boolean onFlyCreated = retrieveIntradayDataIfNotExist(symbol.substring(4), dataDate);
			//getIntradayData(request, response, dataDate, timeGap, symbol);
			processIntradayCandle(request, response, dataDate, timeGap, symbol);
			//writeFileToStream(response);
			//if (onFlyCreated) deleteFile(dataDate, symbol.substring(4));
		}catch(Exception ex){
			ex.printStackTrace();
		}			
	}
	
	protected HttpPost getHttpPostObject(String postUrl) {
		HttpPost httpPost = new HttpPost(postUrl);
		httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
		httpPost.addHeader("Host", "nseindia.com");
		httpPost.addHeader("Referer", "https://nseindia.com/ChartApp/install/charts/mainpage.jsp");
		
		httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpPost.setHeader("Accept-Language", "en-GB,en;q=0.5");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		//httpPost.setHeader("Cookie", getCookies());
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		return httpPost;
	}
	
	private boolean retrieveIntradayDataIfNotExist(String nseCode, String argumentDateStr) {
		boolean retVal = false;
		System.out.println("In populateData for "+ nseCode);
		if (!isFileExist(argumentDateStr, nseCode)) {
			try {
				
				DefaultRoutePlanner routePlanner = null; 
		        Proxy proxy = (Proxy) ProxySelector.getDefault().select(new URI("http://www.google.com/")).iterator().next();
		       
				InetSocketAddress addr = (InetSocketAddress) proxy.address();
				HttpHost proxyServer = null;
				if (addr == null) {
				    System.out.println("No Proxy");
				    routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
				} else {
					proxyServer = new HttpHost(addr.getHostName(), addr.getPort());
					routePlanner = new DefaultProxyRoutePlanner(proxyServer);
					System.out.println("Using proxy: "+ addr.getHostName() + ":" + addr.getPort() );
				}
				
				CloseableHttpClient httpClient = HttpClients.custom().setRoutePlanner(routePlanner).build();
				
				HttpClientContext httpContext =  HttpClientContext.create();
				
				HttpPost httpPost = getHttpPostObject("https://www.nseindia.com/ChartApp/install/charts/data/GetDataAll.jsp?PeriodType=2");
				
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("Periodicity", "1"));
				nvps.add(new BasicNameValuePair("ct0", "g1|1|1"));
				nvps.add(new BasicNameValuePair("ct1", "g2|2|1"));
				nvps.add(new BasicNameValuePair("ctcount", "2"));
				nvps.add(new BasicNameValuePair("Segment", "CM"));
				nvps.add(new BasicNameValuePair("Series", "EQ"));
				nvps.add(new BasicNameValuePair("CDSymbol", nseCode));
				
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				
				CloseableHttpResponse httpResponse = httpClient.execute(httpPost, httpContext);
				
				System.out.println(httpResponse.getStatusLine());
			    HttpEntity httpEntity = httpResponse.getEntity();
			    
			    DataInputStream inputStream = new DataInputStream(httpEntity.getContent());
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
//				URL exchangeLink = new URL("https://www.nseindia.com/ChartApp/install/charts/data/GetHistoricalNew.jsp?PeriodType=2&Periodicity=1&ct0=g1|1|1&ct1=g2|2|1&ctcount=2&Segment=CM&Series=EQ&CDSymbol="+nseCode);			
//				
//				URLConnection urlCon = exchangeLink.openConnection();
//				urlCon.setConnectTimeout(30000);
//				urlCon.setReadTimeout(30000);			
//				DataInputStream inputStream = new DataInputStream(urlCon.getInputStream());
//				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String strLine;
				FileOutputStream fos = null;
				//Long scripId = getScripByNseCode(nseCode,"EQ", false);
				//boolean fileWriteSuccess = false;
				String dateStr = null;
				while ((strLine = bufferedReader.readLine()) != null)   {
					String[] allRow = strLine.split("~");
					for (int i=1;i<allRow.length;i++) { // 0 is header
						String aRow = allRow[i];
						String[] dataRow = aRow.split("\\|");
						if (dataRow!=null && dataRow.length>5) {
							if (fos==null) {
								dateStr = dataRow[0].substring(0,10);
								fos = getOutputFile(dateStr, nseCode);
								//fileWriteSuccess = true;
								//fos = new FileOutputStream(IEOD_FILE_PATH+dataRow[0].substring(0,10)+"-"+nseCode);
							}
							storeData(fos, dataRow[0], dataRow[4], dataRow[5]);
						}
					}
				}
				if (fos!=null) fos.close();
				inputStream.close();
				
				EntityUtils.consume(httpEntity);
			    httpResponse.close();
	//			if (fileWriteSuccess) {
	//				processAndExtractItradayData(dateStr, nseCode);
	//			}
				retVal = true;
			}catch(Exception ex) {
				System.out.println("Error while populateData for "+ nseCode);
				ex.printStackTrace();
			}
		}
		System.out.println("In retrieveIntradayDataIfNotExist returning "+ retVal);
		return retVal;
	}
	
	private FileOutputStream getOutputFile(String datStr, String nseCode) {
		FileOutputStream retFos = null;
		try {
			File f = new File(IEOD_FILE_PATH+datStr);
			if (!f.exists()) {
				new File(IEOD_FILE_PATH+datStr).mkdir();
			}
			retFos = new FileOutputStream(IEOD_FILE_PATH+datStr+"\\"+nseCode+".txt");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retFos;
	}
	
	private void storeData(FileOutputStream fos, String dateString, String price, String volume) {
		try {	
			SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date dataDate = inFormat.parse(dateString);
//				String dataInsertSql = " INSERT INTO intraday_data (id,f_scrip,data_time,price,volume,exchange_code) " +
//						" values(nextval('intraday_data_id_seq')," + scripId + ",TIMESTAMP '" + outFormat.format(dataDate) + "'," + price + "," + volume + ",TRUE)";
//				System.out.println(dataInsertSql);		
//				executeSQL(dataInsertSql);
			Date actualDate = inFormat.parse(dateString);
			String toWrite = outFormat.format(actualDate) + ","+price+","+volume;
			fos.write(toWrite.getBytes());
			fos.write('\n');
		} catch(Exception ex) {
			System.out.print("Error inserting nseCode with SQL=");
			ex.printStackTrace(System.out);
		}
	}
	
	private boolean isFileExist(String argumentDateStr, String nseCode) {
		boolean retVal = false;
		try {
			String normalDateStr = argumentDateStr.replaceAll("/", "-");
			File folder = new File(IEOD_FILE_PATH+normalDateStr);
			if (folder.exists()) {
				File dataFile = new File(IEOD_FILE_PATH+normalDateStr+"\\"+nseCode+".txt");
				if (dataFile.exists()) {
					//System.out.print("File already exist, Skipping");
					retVal = true;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retVal;
	}
	
	private void deleteFile(String argumentDateStr, String nseCode) {
		System.out.println("Preparing Deleting file "+IEOD_FILE_PATH+argumentDateStr.replaceAll("/", "-")+"\\"+nseCode+".txt");
		try {
			String normalDateStr = argumentDateStr.replaceAll("/", "-");
			File folder = new File(IEOD_FILE_PATH+normalDateStr);
			if (folder.exists()) {
				File dataFile = new File(IEOD_FILE_PATH+normalDateStr+"\\"+nseCode+".txt");
				if (dataFile.exists()) {
					System.out.println("Deleting file "+IEOD_FILE_PATH+normalDateStr+"\\"+nseCode+".txt");
					dataFile.delete();
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void writeFileToStream(HttpServletResponse response) {
		try {
			FileInputStream fis = new FileInputStream("D:\\temp\\jsonp.php");
			response.setContentType("text/javascript");
			ServletOutputStream stream = response.getOutputStream();
			byte[] buffer = new byte[1024];
	        int length;
	        while ((length = fis.read(buffer)) > 0) {
	            stream.write(buffer, 0, length);
	        }
			stream.flush();
			stream.close();
			fis.close();
		} catch(Exception exe) {
			exe.printStackTrace();
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
	
	private void processIntradayCandle(HttpServletRequest request, HttpServletResponse response, String dataDate, String timeGap, String symbol) throws Exception {
		String actualSymbol = symbol.substring(4);
		System.out.println("------actualSymbol="+actualSymbol);	
		SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			DataManager dataManager = (DataManager)ServiceLocator.getInstance().getServiceFacade(DataManager.class);
			
			List<ScripEOD> zerodhaCandleDataList = dataManager.getZerodhaCandleMinuteData(actualSymbol, dataDate);
			
			response.setContentType("text/javascript");
			ServletOutputStream servletStream = response.getOutputStream();
			servletStream.write("[".getBytes());servletStream.write(System.getProperty("line.separator").getBytes()); 
			boolean isFirstLineFinished = false;
			
			for(int i=0;i<zerodhaCandleDataList.size();i++) {
				ScripEOD aDataPoint = zerodhaCandleDataList.get(i);
				if (isFirstLineFinished) { servletStream.write(",".getBytes()); servletStream.write(System.getProperty("line.separator").getBytes()); } 
				isFirstLineFinished = true;
				servletStream.write(  ("[" + aDataPoint.getDataDate().getTime() + "," + aDataPoint.getOpenPrice()+ "," + aDataPoint.getHighPrice() +  "," + aDataPoint.getLowPrice() + "," + aDataPoint.getClosePrice() + "," + aDataPoint.getVolume() +"]").getBytes());
			}
			servletStream.write("]".getBytes());servletStream.write(System.getProperty("line.separator").getBytes());
			servletStream.flush();
			servletStream.close();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private void getIntradayData(HttpServletRequest request, HttpServletResponse response, String dataDate, String timeGap, String symbol) throws Exception {
        
		String actualSymbol = symbol.substring(4);
		System.out.println("------actualSymbol="+actualSymbol);		
		String filePath = IEOD_FILE_PATH+dataDate.replaceAll("/", "-")+"\\"+actualSymbol+".txt"; // As of now only NSE intraday data are populated
		
		SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			FileInputStream fis = new FileInputStream(filePath);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
						
			response.setContentType("text/javascript");
			ServletOutputStream servletStream = response.getOutputStream();
			servletStream.write("[".getBytes());servletStream.write(System.getProperty("line.separator").getBytes()); 
			String strLine = null;
			boolean isFirstLineFinished = false;
			
			Calendar calStart = Calendar.getInstance();
			calStart.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(dataDate));
			calStart.set(Calendar.HOUR,9);
			calStart.set(Calendar.MINUTE,15);
			
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(calStart.getTime());
			calEnd.add(Calendar.SECOND,-1);
			calEnd.add(Calendar.MINUTE,Integer.parseInt(timeGap));
			
			Float openPrice = 0f;
			Float highPrice = 0f;
			Float lowPrice = 0f;
			Float closePrice = 0f;
			Long volume = 0L;
			Date lastDataDate = calStart.getTime();
			while ((strLine = bufferedReader.readLine()) != null)   {
				String[] datas = strLine.split(",");
				Date dataDateFromFile = dataDateFormat.parse(datas[0]);
				Float cureentPrice = Float.parseFloat(datas[1]);
				Long currentVolume = Long.parseLong(datas[2]);
				
				if (dataDateFromFile.after(calEnd.getTime())) {
					// First write accumulated data
					if (isFirstLineFinished) { servletStream.write(",".getBytes()); servletStream.write(System.getProperty("line.separator").getBytes()); } 
					isFirstLineFinished = true;
					servletStream.write(  ("[" + lastDataDate.getTime() + "," + openPrice+ "," + highPrice +  "," + lowPrice + "," + closePrice + "," + volume +"]").getBytes());
					openPrice = 0f;
					highPrice = 0f;
					lowPrice = 0f;
					closePrice = 0f;
					volume = 0L;
					calEnd.add(Calendar.MINUTE,Integer.parseInt(timeGap));
				}
				closePrice = cureentPrice;
				if (openPrice==0) { openPrice = cureentPrice; highPrice = cureentPrice; lowPrice = cureentPrice; }
				if (cureentPrice<lowPrice) lowPrice = cureentPrice;
				if (cureentPrice>highPrice) highPrice = cureentPrice;
				volume = volume + currentVolume;
				lastDataDate = dataDateFromFile;
			}
			
			if (openPrice!=0) { // Last one pedning to write
				if (isFirstLineFinished) { servletStream.write(",".getBytes()); servletStream.write(System.getProperty("line.separator").getBytes()); } 
				isFirstLineFinished = true;
				servletStream.write(  ("[" + (calEnd.getTime()).getTime() + "," + openPrice+ "," + highPrice +  "," + lowPrice + "," + closePrice + "," + volume +"]").getBytes());				
			}
			
			bufferedReader.close();
			servletStream.write("]".getBytes());servletStream.write(System.getProperty("line.separator").getBytes());
			servletStream.flush();
			servletStream.close();
			fis.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void getIntradayDataDummy(HttpServletRequest request, HttpServletResponse response, String dataDate, String timeGap, String symbol) throws Exception {
		        
		String actualSymbol = symbol.substring(4);
		System.out.println("------actualSymbol="+actualSymbol);		
		String filePath = IEOD_FILE_PATH+dataDate.replaceAll("/", "-")+"\\"+actualSymbol+".txt"; // As of now only NSE intraday data are populated
		
		try {
//			FileInputStream fis = new FileInputStream(filePath);
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
//			String strLine = null;
//			while ((strLine = bufferedReader.readLine()) != null)   {
//				String[] datas = strLine.split(",");
//				
//			}
//			bufferedReader.close();
//			fis.close();
			response.setContentType("text/javascript");
			 ServletOutputStream stream = response.getOutputStream();
			 //stream.write("?( /* AAPL historical OHLC data from the Google Finance API */".getBytes());stream.write(System.getProperty("line.separator").getBytes());			 
			 stream.write("[".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1147651200000,67.37,68.38,67.12,67.79,18921051],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1147737600000,68.10,68.25,64.75,64.98,33470860],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1147824000000,64.70,65.70,64.07,65.26,26941146],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1147910400000,65.68,66.26,63.12,63.18,23524811],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1147996800000,63.26,64.88,62.82,64.51,35221586],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1148256000000,63.87,63.99,62.77,63.38,25680800],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1148342400000,64.86,65.19,63.00,63.15,24814061],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1148428800000,62.99,63.65,61.56,63.34,32722949],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1148515200000,64.26,64.45,63.29,64.33,16563319],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1148601600000,64.31,64.56,63.14,63.55,15464811],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1148947200000,63.29,63.30,61.22,61.22,20125338],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 
			 
			 stream.write("[1157651200000,67.37,68.38,67.12,67.79,18921051],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1157737600000,68.10,68.25,64.75,64.98,33470860],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1157824000000,64.70,65.70,64.07,65.26,26941146],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1157910400000,65.68,66.26,63.12,63.18,23524811],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1157996800000,63.26,64.88,62.82,64.51,35221586],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1158256000000,63.87,63.99,62.77,63.38,25680800],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1158342400000,64.86,65.19,63.00,63.15,24814061],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1158428800000,62.99,63.65,61.56,63.34,32722949],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1158515200000,64.26,64.45,63.29,64.33,16563319],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1158601600000,64.31,64.56,63.14,63.55,15464811],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1158947200000,63.29,63.30,61.22,61.22,20125338],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 
			 stream.write("[1167651200000,67.37,68.38,67.12,67.79,18921051],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1167737600000,68.10,68.25,64.75,64.98,33470860],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1167824000000,64.70,65.70,64.07,65.26,26941146],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1167910400000,65.68,66.26,63.12,63.18,23524811],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1167996800000,63.26,64.88,62.82,64.51,35221586],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1168256000000,63.87,63.99,62.77,63.38,25680800],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1168342400000,64.86,65.19,63.00,63.15,24814061],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1168428800000,62.99,63.65,61.56,63.34,32722949],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1168515200000,64.26,64.45,63.29,64.33,16563319],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1168601600000,64.31,64.56,63.14,63.55,15464811],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.write("[1168947200000,63.29,63.30,61.22,61.22,20125338],".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 
			 stream.write("[1179033600000,61.76,61.79,58.69,59.77,45755325]".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 
			 stream.write("]".getBytes());stream.write(System.getProperty("line.separator").getBytes());
			 stream.flush();
			 stream.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
//		Map dataMap = getStockData(symbol, fromDate, toDate);//Symbol should be exchangecode-scripcode e.g: BSE-ACC     
//        List dataList = (List)dataMap.get("dataList");
//        String scripName=(String)dataMap.get("symbolName");
//        
//        List compareDataList = null;
//        String compareScripName = "";
//        if (compareSymbol!=null && compareSymbol.length()>0) {
//        	Map compareDataMap = getStockData(compareSymbol, fromDate, toDate);     
//            compareDataList = (List)compareDataMap.get("dataList");
//            compareScripName=(String)compareDataMap.get("symbolName");
//        }
//        
//        response.setContentType("application/octet-stream");                    
//        ServletOutputStream stream = response.getOutputStream();        
//        
//        QuickChartImageGenerator imageGenerator = new QuickChartImageGenerator(scripName+"("+symbol+")",dataList, compareSymbol, compareDataList);
//        BaseChart c = imageGenerator.drawChart(request);
//
//        try {            
//            if (WebChartViewer.streamChart(response, c.makeChart2(Chart.PNG)))
//                return;
//        } catch (IllegalStateException e) {            
//            response.sendRedirect(response.encodeRedirectURL("getchart.jsp?" + c.makeSession(request, "chart1")));
//            return;
//        }
	}   
}
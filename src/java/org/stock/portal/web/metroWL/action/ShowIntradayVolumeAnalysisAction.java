package org.stock.portal.web.metroWL.action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.domain.NSEEodData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.dto.IntradayTickData;
import org.stock.portal.service.data.DataManager;
import org.stock.portal.web.action.BaseAction;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;

public class ShowIntradayVolumeAnalysisAction extends BaseAction {
	
	private static final long serialVersionUID = -5812083195015892565L;
	private static Logger log = Logger.getLogger(ShowIntradayVolumeAnalysisAction.class.getName());
	
	static final String IEOD_FILE_PATH="D:\\Data\\intra\\NSE\\"; 
    
	private String intraTickerSymbol =  "BSE-BSE30";
	private String intraDataDate;
	
	private List<NSEEodData> pastdaysEodData = null;
	
	private List<IntradayTickData> intradayTickData = null;
	
	@InjectEJB (name ="DataManager")
	DataManager dataManager;
	
	public String loadIntradayVolumeAnalysis(){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!Yep this.intraDataDate="+this.intraDataDate);
		String returnPage = "showIntraVolumeAnalysis";
		try {
			Scrip scrip = (Scrip)this.session.get(Constants.LAST_ACCESSED_SCRIP);
	    	
	    	if (scrip!=null) {
	    		if (scrip.getNseCode()!=null) {
	    			intraTickerSymbol = "NSE-" + scrip.getNseCode();
	    		} else if (scrip.getBseName()!=null) {
	    			intraTickerSymbol = "BSE-" + scrip.getBseName();
	    		} else if (scrip.getBseCode()!=null) {
	    			intraTickerSymbol = "BSE-" + scrip.getBseCode();
	    		}
	    	}
	    	String prevEodDate = "";
	    	if (intraDataDate!=null && intraDataDate.length()>0) {
	    		Calendar curDate = Calendar.getInstance();
	    		curDate.setTime(SPConstants.SPCORE_DATE_FORMAT.parse(this.intraDataDate));
	    		
	    		Date latestDate = dataManager.getMaxDataDate("NSE", curDate.getTime());
				this.intraDataDate = SPConstants.SPCORE_DATE_FORMAT.format(latestDate);
				
				Date previousDate = dataManager.getMaxDataDate("NSE", latestDate);
				prevEodDate = SPConstants.SPCORE_DATE_FORMAT.format(previousDate);
				
	    	} else {	    	
		    	Calendar curDate = Calendar.getInstance();
				curDate.add(Calendar.DATE, 1);
				Date latestDate = dataManager.getMaxDataDate("NSE", curDate.getTime());
				this.intraDataDate = SPConstants.SPCORE_DATE_FORMAT.format(latestDate);
				
				Date previousDate = dataManager.getMaxDataDate("NSE", latestDate);
				prevEodDate = SPConstants.SPCORE_DATE_FORMAT.format(previousDate);
	    	}
			
			
			boolean onFlyCreated = retrieveIntradayDataIfNotExist(intraTickerSymbol.substring(4), intraDataDate);
			getIntradayData(intraDataDate, intraTickerSymbol);
			if (onFlyCreated) deleteFile(intraDataDate, intraTickerSymbol.substring(4));
			
			pastdaysEodData = dataManager.getNSEEodData(intraTickerSymbol.substring(4), prevEodDate);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return returnPage;
	}

	public String getTickerSymbol() {
		return intraTickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		this.intraTickerSymbol = tickerSymbol;
	}

	public String getDataDate() {
		return intraDataDate;
	}

	public void setDataDate(String dataDate) {
		this.intraDataDate = dataDate;
	}
	
	private void getIntradayData(String dataDate, String symbol) throws Exception {
        
		String actualSymbol = symbol.substring(4);
		System.out.println("------actualSymbol="+actualSymbol);		
		String filePath = IEOD_FILE_PATH+dataDate.replaceAll("/", "-")+"\\"+actualSymbol+".txt"; // As of now only NSE intraday data are populated
		
		SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			FileInputStream fis = new FileInputStream(filePath);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
			
			intradayTickData = new ArrayList<IntradayTickData>();
			
			String strLine = null;
				
			Long totalVolume = 0L;
			while ((strLine = bufferedReader.readLine()) != null)   {
				String[] datas = strLine.split(",");
				Date dataDateFromFile = dataDateFormat.parse(datas[0]);
				Float cureentPrice = Float.parseFloat(datas[1]);
				Long currentVolume = Long.parseLong(datas[2]);
				totalVolume = totalVolume + currentVolume;
				
				IntradayTickData aTickData = new IntradayTickData(dataDateFromFile, cureentPrice, currentVolume, totalVolume);
				intradayTickData.add(aTickData);
						
				//System.out.println("Ticktime=" + dataDateFromFile + " cureentPrice="+ cureentPrice + " currentVolume=" + currentVolume + " totalVolume="+totalVolume);
			}
			
			bufferedReader.close();
			fis.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
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

	private boolean retrieveIntradayDataIfNotExist(String nseCode, String argumentDateStr) {
		boolean retVal = false;
		System.out.println("In populateData for "+ nseCode+ " argumentDateStr="+argumentDateStr);
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

	public List<NSEEodData> getPastdaysEodData() {
		return pastdaysEodData;
	}

	public void setPastdaysEodData(List<NSEEodData> pastdaysEodData) {
		this.pastdaysEodData = pastdaysEodData;
	}

	public List<IntradayTickData> getIntradayTickData() {
		return intradayTickData;
	}

	public void setIntradayTickData(List<IntradayTickData> intradayTickData) {
		this.intradayTickData = intradayTickData;
	}
}

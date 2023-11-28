package org.stock.portal.web.metroWL.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.stock.portal.domain.dto.NOWOrderDTO;


public class NowWebClient extends WebClient {
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		logout();
	}

	public boolean isLoggedin = false;
	
	private static String NOW_SESSION_NAME = null;
	private static String NOW_SESSION_ACCNTID = null;;
	
	private static String NOW_BROKER_ID = "09643"; 
	private static String NOW_LOGIN_ID = "YKAK30-09643"; 
	private static String NOW_LOGIN_PASSWORD = "O20saro76!$"; // M20saro76!$
	private static String NOW_TRANSACTION_PASSWORD = "U20saro76!$"; // M20saro76!$
	
	public NowWebClient() {	
		super();
		questions = new ArrayList<NameValuePair>();
		questions.add(new BasicNameValuePair("What is the colour of your first car", "White"));
		questions.add(new BasicNameValuePair("How many floors does your building have", "19"));
		questions.add(new BasicNameValuePair("What was the brand name of your first vehicle", "Maruti"));
		questions.add(new BasicNameValuePair("Which bank offered your first credit card", "HSBC"));
		questions.add(new BasicNameValuePair("Which is your daughter's place of birth", "Belgaum"));
	}
	

	public static void main(String[] args) {
		System.setProperty("java.net.useSystemProxies", "true");
		
		NowWebClient nowClient = new NowWebClient();
//		nowClient.consumeUrl("http://localhost/chart/QuoteData?version=1&dataFormat=ppz&duration=1y&frequency=0&symbol=NSE-ICICIBANK&sessionId=5sWVDCP3gYFG+CB8EGUGj4MU&symbology=yes&includeExchange=true&disableProxyKey=143906296109281520&heatindex=1&recognia=0&AutoChartist=0");
		nowClient.login();	
		if (nowClient.isLoggedin) {
			//nowClient.getLimits();
			//nowClient.placeAMOOrder("BSE", "CONCOR", "", 6, 1643f, TRANSACTION_TYPE_BUY); // For NSE ("NSE", "CONCOR", "EQ", 6, 1643f); 
			//nowClient.placeLiveOrder("NSE", "CONCOR", "EQ", 6, 1642f, TRANSACTION_TYPE_BUY, PRICE_TYPE_LIMIT);
			//nowClient.getBSEPrice("500209");
			
			IntradayInfo bsePrice = nowClient.getBSEPrice("500209");
			IntradayInfo nsePrice = nowClient.getNSEPrice("CONCOR", "EQ");
			System.out.println("Now: " + (new Date()) + " BSE Tick time: " + bsePrice.getTickTime() + " Tick Price: "+ bsePrice.getCurrentTickPrice());
			System.out.println("Now: " + (new Date()) + " NSE Tick time: " + nsePrice.getTickTime() + " Tick Price: "+ nsePrice.getCurrentTickPrice());

			nowClient.logout();
		}
		
	}
	
	private HttpPost getHttpPostObject(String postUrl) {
		HttpPost httpPost = new HttpPost(postUrl);
		// Add http headers and other attributes	
		httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
		httpPost.addHeader("Referer", "https://www.nowonline.in//now_home.html");
		return httpPost;
	}	
	
	public void login() {
		try {
			HttpPost httpPost = getHttpPostObject("https://www.nowonline.in/NOW/2FA_Auth/ShowImage.jsp");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("sBroker", javascriptExecutor("Convert", NOW_BROKER_ID)));
			nvps.add(new BasicNameValuePair("sLoginID", javascriptExecutor("Convert", NOW_LOGIN_ID)));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    HttpEntity httpEntity = httpResponse.getEntity();
		    //System.out.println(EntityUtils.toString(httpEntity));
		    BasicNameValuePair bnvp = retrieveNowSessionNameAndAccountId(httpEntity);
		    if (bnvp!=null) {
		    	//System.out.println("nameElement="+bnvp.getName());
				//System.out.println("accntidElement="+bnvp.getValue());
				NOW_SESSION_NAME = bnvp.getName();
				NOW_SESSION_ACCNTID = bnvp.getValue();
		    } else {
		    	EntityUtils.consume(httpEntity);
			    httpResponse.close();
		    	return; // Failed to pass first stage
		    }
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();
		    // ------------------- Stage 1 END ------------------------
		    
		    // ------------------- Stage 2 BEGIN ------------------------
		    String vData = NOW_BROKER_ID + "|" + NOW_LOGIN_ID + "|"+ NOW_LOGIN_PASSWORD;
		    String retObj = javascriptExecutor("encryptNowWay", vData, NOW_SESSION_ACCNTID);
		    //System.out.println("retObj="+retObj);
		    
		    HttpGet httpGet = getHttpGetObject("https://www.nowonline.in/NOW/2FA_Auth/ValidPassword.jsp?"+NOW_SESSION_NAME+"="+retObj);
		    httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    httpEntity = httpResponse.getEntity();
		    boolean stagePassed = stageTwoLoginSuccessCheck(httpEntity);
		    if (!stagePassed) {
		    	EntityUtils.consume(httpEntity);
			    httpResponse.close();
		    	return; // Failed to pass first stage
		    }
		    //System.out.println("Stage passed successfully");
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();
		    // ------------------- Stage 2 END ------------------------
		    
		    // ------------------- Stage 3 Begin Part 1 SetQuestions ------------------------
		    httpGet = getHttpGetObject("https://www.nowonline.in/NOW/2FA_Auth/SetQuestions.jsp");
		    httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    httpEntity = httpResponse.getEntity();
		    //System.out.println(EntityUtils.toString(httpEntity));
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();
		    
		    // ------------------- Stage 3 Begin Part 2 QuestionsAuth ------------------------
		    httpGet = getHttpGetObject("https://www.nowonline.in/NOW/2FA_Auth/QuestionsAuth.jsp");
		    httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    httpEntity = httpResponse.getEntity();
		    Map<String, String> questionsData = getQuestions(httpEntity);
		    
		    vData = questionsData.get("sBroker") + "|" + questionsData.get("sLoginID") + "|"+ questionsData.get("answer1")+ "|"+ questionsData.get("answer2")+ "|"+ questionsData.get("st1")+ "|"+ questionsData.get("st2");
		    retObj = javascriptExecutor("encryptNowWay", vData, questionsData.get("key_questauth"));
		    //System.out.println("retObj="+retObj);
		    

		    httpGet = getHttpGetObject("https://www.nowonline.in/NOW/2FA_Auth/ValidAnswers.jsp?"+questionsData.get("name_questauth")+"="+retObj);
		    httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    httpEntity = httpResponse.getEntity();
		    //System.out.println(EntityUtils.toString(httpEntity));
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();
		    
		    consumeUrl("https://www.nowonline.in/NOW/Trade/DefaultLogin.jsp");

		    //consumeUrl("https://www.nowonline.in/NOW/Trade/AMOMarketWatch.jsp");
		    consumeUrl("https://www.nowonline.in/NOW/Trade/Trading.jsp");
		    
		    
		    isLoggedin = true;
		    System.out.println("In NowWebClient login()::Success -------------------------------------------");
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			
		}
	}
	
	public void logout() {
		System.out.print("Logout called");
		try {
			HttpGet httpGet = getHttpGetObject("https://www.nowonline.in/NOW/Trade/Temp_Logout.jsp"); // Alternative URL https://www.nowonline.in/NOW/Trade/Logout.jsp?Block=L
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    HttpEntity httpEntity = httpResponse.getEntity();
		    //System.out.println(EntityUtils.toString(httpEntity));
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void getLimits() {
		try {
			HttpGet httpGet = getHttpGetObject("https://www.nowonline.in/NOW/Trade/RMSLIMITS.jsp");
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
			HttpEntity httpEntity = httpResponse.getEntity();
		    //System.out.println(EntityUtils.toString(httpEntity));
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String placeOrder(String exchangeCode, String scripCodeToUse, String seriesTypeToUse, int transactionType, int orderType, String orderNature, Long qty, Float rate) {
		
		System.out.println("exchangeCode="+exchangeCode+" scripCodeToUse="+scripCodeToUse+" seriesTypeToUse="+seriesTypeToUse+" transactionType="+transactionType+" orderType="+orderType+" orderNature="+orderNature+" qty="+qty+" rate="+rate);
		String orderNumber = null;
		
		if (orderNature.equalsIgnoreCase("Live")) {
			orderNumber = placeLiveOrder(exchangeCode, scripCodeToUse, seriesTypeToUse, qty, rate, transactionType, orderType);
		} else {
			orderNumber = placeAMOOrder(exchangeCode, scripCodeToUse, seriesTypeToUse, qty, rate, transactionType);
		}
		return orderNumber;
	}
	
	private String placeAMOOrder(String exchangeCode, String scripCode, String seriesType, Long qty, float rate, int transactioType) {
		String orderNumber = null;
		try {
			//consumeUrl("https://www.nowonline.in/NOW/Trade/AMOMarketWatch.jsp");
			String firstUrl = "https://www.nowonline.in/NOW/Trade/AMOPlaceOrderSearch.jsp?sType=BUY&searchfor="+scripCode;
			
			if (exchangeCode.equalsIgnoreCase("BSE")) {
				firstUrl = firstUrl + "&Exchange=bse_cm";
			} else {
				firstUrl = firstUrl + "&Exchange=nse_cm";
			}
			if (seriesType!=null && seriesType.equals("EQ")) {
				firstUrl = firstUrl + "&group="+seriesType;
			} else {
				firstUrl = firstUrl + "&group=ALL";
			}
			HttpGet httpGet = getHttpGetObject(firstUrl);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			System.out.println(httpResponse.getStatusLine());
			HttpEntity httpEntity = httpResponse.getEntity();
			Map<String, String> paramData = getParamsForAMO(httpEntity);
		    
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();	    
		    
		    // Place the order
		    String orderValues = "";
			if (exchangeCode.equalsIgnoreCase("BSE")) orderValues = orderValues + "bse_cm"; else orderValues = orderValues + "nse_cm";
			if (seriesType!=null && seriesType.length()>0) orderValues = orderValues + "|" + seriesType; else orderValues = orderValues + "|ALL";
			orderValues = orderValues + "|" + paramData.get("Token"); //Todo add Token here from Element "Name" value ( after Part | )
			orderValues = orderValues + "|" + paramData.get("compname"); //Todo add Token here from Element "Name" text
			if (seriesType!=null && seriesType.length()>0) orderValues = orderValues + "|" + scripCode+"-"+seriesType; else orderValues = orderValues + "|" + scripCode; //sTradSym
			if (transactioType==TRANSACTION_TYPE_BUY) orderValues = orderValues + "|B"; else orderValues = orderValues + "|S"; // buysell
			orderValues = orderValues + "|CNC"; // For AMO ProductCode it is CNC -> Cash N Carry
			orderValues = orderValues + "|L"; // For AMO PriceType it is LIMIT order
			orderValues = orderValues + "|DAY"; // Retention
			orderValues = orderValues + "|" + qty; // Quantity
			orderValues = orderValues + "|" + rate; // Price
			orderValues = orderValues + "|0"; // DiscQty
			orderValues = orderValues + "|0"; // TriggerPrice is 0 for both Market and Limit order
			orderValues = orderValues + "|" + paramData.get("sSymbolName"); //Todo add Token here from Element "Name1" value
			orderValues = orderValues + "|ABC"; // DatesDays ???
			
			//System.out.println("orderValues="+orderValues);
			String retObj = javascriptExecutor("encryptNowWay", orderValues, paramData.get("orderKey"));
		    //System.out.println("retObj="+retObj);
		    
		    httpGet = getHttpGetObject("https://www.nowonline.in/NOW/Trade/AMOPutOrder.jsp?"+paramData.get("orderName")+"="+retObj);
			httpResponse = httpClient.execute(httpGet, httpContext);
			System.out.println(httpResponse.getStatusLine());
			httpEntity = httpResponse.getEntity();
			Map<String, String> orderData = getAMOParamsForExecuteOrder(httpEntity);
		    
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();	
		    
		    // Final step of Place order
		    HttpPost httpPost = getHttpPostObject("https://www.nowonline.in/NOW/Trade/AMOExecuteOrder.jsp");
		    retObj = javascriptExecutor("encryptNowWayForPost", NOW_TRANSACTION_PASSWORD, orderData.get("orderKey"));
		    
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Transaction_password", retObj));
			
			Iterator iter = orderData.keySet().iterator();
			while (iter.hasNext()) {
				String mapKey = (String)iter.next();
				if (!mapKey.equals("orderKey")) {
					nvps.add(new BasicNameValuePair(mapKey, orderData.get(mapKey)));
				}
			}
			
			
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpResponse = httpClient.execute(httpPost, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    httpEntity = httpResponse.getEntity();
		    httpEntity = httpResponse.getEntity();
		    orderNumber = getOrderNumber(httpEntity);
		    //System.out.println(EntityUtils.toString(httpEntity));
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return orderNumber;
	}
	
	
	private String placeLiveOrder(String exchangeCode, String scripCode, String seriesType, Long qty, float rate, int transactioType, int priceType) {
		String orderNumber = null;
		try {
			//consumeUrl("https://www.nowonline.in/NOW/Trade/AMOMarketWatch.jsp");
			String firstUrl = "https://www.nowonline.in/NOW/Trade/PlaceOrderSearch.jsp?sType=BUY&searchfor="+scripCode;
			
			if (exchangeCode.equalsIgnoreCase("BSE")) {
				firstUrl = firstUrl + "&Exchange=bse_cm";
			} else {
				firstUrl = firstUrl + "&Exchange=nse_cm";
			}
			if (seriesType!=null && seriesType.length()>0) {
				firstUrl = firstUrl + "&group="+seriesType;
			} else {
				firstUrl = firstUrl + "&group=ALL";
			}
			HttpGet httpGet = getHttpGetObject(firstUrl);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			System.out.println(httpResponse.getStatusLine());
			HttpEntity httpEntity = httpResponse.getEntity();
			Map<String, String> paramData = getParamsForLive(httpEntity);
		    
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();	    
		    
		    // Place the order
		    String orderValues = "";
			if (exchangeCode.equalsIgnoreCase("BSE")) orderValues = orderValues + "bse_cm"; else orderValues = orderValues + "nse_cm";
			if (seriesType!=null && seriesType.length()>0) orderValues = orderValues + "|" + seriesType; else orderValues = orderValues + "|ALL";
			orderValues = orderValues + "|" + paramData.get("Token"); //Todo add Token here from Element "Name" value ( after Part | )
			orderValues = orderValues + "|" + paramData.get("compname"); //Todo add Token here from Element "Name" text
			if (seriesType!=null && seriesType.length()>0) orderValues = orderValues + "|" + scripCode+"-"+seriesType; else orderValues = orderValues + "|" + scripCode; //sTradSym
			if (transactioType==TRANSACTION_TYPE_BUY) orderValues = orderValues + "|B"; else orderValues = orderValues + "|S"; // buysell
			orderValues = orderValues + "|CNC"; // For Live ProductCode it is CNC -> Cash N Carry (Need to check NRML option)
			if (priceType==PRICE_TYPE_LIMIT) orderValues = orderValues + "|L";
			else if (priceType==PRICE_TYPE_MARKET) orderValues = orderValues + "|MKT";
			orderValues = orderValues + "|DAY"; // Retention
			orderValues = orderValues + "|" + qty; // Quantity
			orderValues = orderValues + "|" + rate; // Price
			orderValues = orderValues + "|0"; // DiscQty
			orderValues = orderValues + "|0"; // TriggerPrice is 0 for both Market and Limit order
			orderValues = orderValues + "|" + paramData.get("sSymbolName"); //Todo add Token here from Element "Name1" value
			orderValues = orderValues + "|ABC"; // DatesDays ???
			
			//System.out.println("orderValues="+orderValues);
			String retObj = javascriptExecutor("encryptNowWay", orderValues, paramData.get("orderKey"));
		    //System.out.println("retObj="+retObj);
		    
		    httpGet = getHttpGetObject("https://www.nowonline.in/NOW/Trade/PutOrder.jsp?"+paramData.get("orderName")+"="+retObj);
			httpResponse = httpClient.execute(httpGet, httpContext);
			System.out.println(httpResponse.getStatusLine());
			httpEntity = httpResponse.getEntity();
			Map<String, String> orderData = getLiveParamsForExecuteOrder(httpEntity);
		    
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();	
		    
		    // Final step of Place order
		    HttpPost httpPost = getHttpPostObject("https://www.nowonline.in/NOW/Trade/ExecuteOrder.jsp");
		    retObj = javascriptExecutor("encryptNowWayForPost", NOW_TRANSACTION_PASSWORD, orderData.get("orderKey"));
		    
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Transaction_password", retObj));
			
			Iterator iter = orderData.keySet().iterator();
			while (iter.hasNext()) {
				String mapKey = (String)iter.next();
				if (!mapKey.equals("orderKey")) {
					nvps.add(new BasicNameValuePair(mapKey, orderData.get(mapKey)));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpResponse = httpClient.execute(httpPost, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    httpEntity = httpResponse.getEntity();
		    orderNumber = getOrderNumber(httpEntity);
		    System.out.println("orderNumber-"+orderNumber+"-");
		    //System.out.println(EntityUtils.toString(httpEntity));
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return orderNumber;
	}
	
	public IntradayInfo getBSEPrice(String bseCode) {
		IntradayInfo retVal = null;
		try {
			String firstUrl = "https://www.nowonline.in/NOW/Trade/ScripSearch.jsp?Exchange=bse_cm&group=ALL&searchfor="+bseCode;
			
			HttpGet httpGet = getHttpGetObject(firstUrl);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			//System.out.println(httpResponse.getStatusLine());
			HttpEntity httpEntity = httpResponse.getEntity();
			Map<String, String> paramData = getParamsForBseQuote(httpEntity);
		    
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();	    
		    
		    // Actual search
		   
		    httpGet = getHttpGetObject("https://www.nowonline.in/NOW/Trade/ShowQuote.jsp?Exchange=bse_cm&SearchFor="+paramData.get("searchFor"));
			httpResponse = httpClient.execute(httpGet, httpContext);
			//System.out.println(httpResponse.getStatusLine());
			httpEntity = httpResponse.getEntity();
			retVal = getPrice(httpEntity); 
			//System.out.println(EntityUtils.toString(httpEntity));
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retVal;
	}
	
	public IntradayInfo getNSEPrice(String nseCode, String seriesType) {
		IntradayInfo retVal = null;
		try {
			String firstUrl = "https://www.nowonline.in/NOW/Trade/ScripSearch.jsp?Exchange=nse_cm&group=" + seriesType + "&searchfor="+nseCode;
			
			HttpGet httpGet = getHttpGetObject(firstUrl);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			//System.out.println(httpResponse.getStatusLine());
			HttpEntity httpEntity = httpResponse.getEntity();
			Map<String, String> paramData = getParamsForBseQuote(httpEntity);
		    
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();	    
		    
		    // Actual search
		   
		    httpGet = getHttpGetObject("https://www.nowonline.in/NOW/Trade/ShowQuote.jsp?Exchange=nse_cm&SearchFor="+paramData.get("searchFor"));
			httpResponse = httpClient.execute(httpGet, httpContext);
			//System.out.println(httpResponse.getStatusLine());
			httpEntity = httpResponse.getEntity();
			
			retVal = getPrice(httpEntity); 
			
			//System.out.println(EntityUtils.toString(httpEntity));
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retVal;
	}
	
	public List<NOWOrderDTO> getOrderBook() {
		List<NOWOrderDTO> retList = new ArrayList<NOWOrderDTO>();
		try {
			String firstUrl = "https://www.nowonline.in/NOW/Trade/OrderBook.jsp?Exchange=&OrderType=All";
			
			HttpGet httpGet = getHttpGetObject(firstUrl);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			System.out.println(httpResponse.getStatusLine());
			
			HttpEntity httpEntity = httpResponse.getEntity();
			String htmlContent = readFromStream(httpEntity).replaceAll("&nbsp;", "");
			Document doc = Jsoup.parse(htmlContent);
			Elements trElements = doc.select("table tr[id^=TR]"); //"a[id^=nav]"
			for (int i = 0; i < trElements.size(); i++) { //first row is the col names so skip it.
				NOWOrderDTO aOrderDto = new NOWOrderDTO();
			    Element row = trElements.get(i);
			    Elements tdElements = row.select("td");
			    for (int j = 0; j < tdElements.size(); j++) {
			    	Element aTD = tdElements.get(j);
			    	switch (j) {
					case 0:	// Todo First column which contains Radio button.					
						break;
					case 1:
						aOrderDto.setExchangeCode(aTD.text().trim());
						break;
					case 2:
						aOrderDto.setScripCode(aTD.text().trim());
						break;
					case 3:
						aOrderDto.setOrderNumber(aTD.text().trim());
						break;
					case 5:
						aOrderDto.setTransactiontype(aTD.text().trim());
						break;
					case 7:
						aOrderDto.setPrice(aTD.text().trim());
						break;
					case 8:
						aOrderDto.setAvgPrice(aTD.text().trim());
						break;
					case 9:
						aOrderDto.setTotalQuantity(aTD.text().trim());
						break;
					case 10:
						aOrderDto.setPendingQuantity(aTD.text().trim());
						break;
					case 11:
						aOrderDto.setDisclosedQuantity(aTD.text().trim());
						break;
					case 12:
						aOrderDto.setFilledQuantity(aTD.text().trim());
						break;
					case 14:
						aOrderDto.setExchangeOrderNo(aTD.text().trim());
						break;
					case 15:
						aOrderDto.setStatus(aTD.text().trim());
						break;
					case 16:
						aOrderDto.setRejectionReason(aTD.text().trim());
						break;
					case 17:
						aOrderDto.setOrderType(aTD.text().trim());
						break;
					case 18:
						aOrderDto.setOrderTime(aTD.text().trim());
						break;
					default:
						break;
					}
			    }
			    retList.add(aOrderDto);
			}
			
		    EntityUtils.consume(httpEntity);
		    httpResponse.close();	
		} catch(Exception ex) {
			ex.printStackTrace();
		}		
		return retList;
	}
}

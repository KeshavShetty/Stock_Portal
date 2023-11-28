package org.stock.portal.web.metroWL.action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ProxySelector;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.stock.portal.common.ApplicationConfig;

public class WebClient {
	
	boolean isLoggedin = false;
	
	CloseableHttpClient httpClient = null;
	HttpClientContext httpContext = null;
	ScriptEngine engine = null;
	
	protected List<NameValuePair> questions = null;	

	
	// Constants
	static int TRANSACTION_TYPE_BUY = 0 ;
	static int TRANSACTION_TYPE_SELL = 1 ;
	
	static int PRICE_TYPE_MARKET = 0;
	static int PRICE_TYPE_LIMIT = 1; 
	
	public WebClient() {		
		try { 
			SSLContextBuilder builder = new SSLContextBuilder();
	        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	
	
	        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
	                .register("http", new PlainConnectionSocketFactory())
	                .register("https", sslsf)
	                .build();
	
	
	        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
	        cm.setMaxTotal(2000);//max connection
	
	        CookieStore cookieStore = new BasicCookieStore();
	        this.httpContext = HttpClientContext.create();
	        httpContext.setCookieStore(cookieStore);
	
	        SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault()); // Use System proxy
	       	        
	        //System.setProperty("jsse.enableSNIExtension", "false"); //""
	        this.httpClient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .setConnectionManager(cm)
	                .setRoutePlanner(routePlanner)
	                .build();
	        
	        // For javascript executor
	        ScriptEngineManager factory = new ScriptEngineManager();
	        // create JavaScript engine
	        this.engine = factory.getEngineByName("JavaScript");
	        // evaluate JavaScript code from given file - specified by first argument
	        URL propertyFileUrl = ApplicationConfig.class.getResource("/" + "Encrypt.js");
            InputStream inpstr = propertyFileUrl.openStream();
	        Reader reader = new InputStreamReader(inpstr); 
	        engine.eval(reader);
	        reader.close();
	        inpstr.close();
	        
	        propertyFileUrl = ApplicationConfig.class.getResource("/" + "Encode.js");
            inpstr = propertyFileUrl.openStream();
            reader = new InputStreamReader(inpstr); 
	        engine.eval(reader);
	        reader.close();
	        inpstr.close();
	        
	        propertyFileUrl = ApplicationConfig.class.getResource("/" + "base64.js");
            inpstr = propertyFileUrl.openStream();
	        reader = new InputStreamReader(inpstr); 
	        engine.eval(reader);
	        reader.close();
	        inpstr.close();
	        
//	        engine.eval(new java.io.FileReader("Encrypt.js"));
//	        engine.eval(new java.io.FileReader("Encode.js"));
//	        engine.eval(new java.io.FileReader("base64.js"));
	        
		} catch(Exception ex) {
			ex.printStackTrace();
		}
        
	}
	
	protected HttpGet getHttpGetObject(String postUrl) {
		HttpGet httpGet = new HttpGet(postUrl);
		// Add http headers and other attributes	
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
		httpGet.addHeader("Referer", "https://www.nowonline.in//now_home.html");
		return httpGet;
	}
	
	protected String javascriptExecutor(String methodName, String inputData) {
		String retValue = "";
		try {			
	        Invocable inv = (Invocable) engine;
	        Object retObj = inv.invokeFunction(methodName, inputData );
	        retValue = retObj.toString();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retValue;
	}
	
	protected String javascriptExecutor(String methodName, String inputData1, String inputData2) {
		String retValue = "";
		try {			
	        Invocable inv = (Invocable) engine;
	        Object retObj = inv.invokeFunction(methodName, inputData1, inputData2 );
	        retValue = retObj.toString();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retValue;
	}
	
	protected BasicNameValuePair retrieveNowSessionNameAndAccountId(HttpEntity httpEntity) {
		BasicNameValuePair retValue = null;
		try {
			Document doc = Jsoup.parse(readFromStream(httpEntity));
			Element nameElement = doc.select("#name").first();
			Element accntidElement = doc.select("#accntid").first();
			
			//System.out.println("nameElement="+nameElement);
			//System.out.println("accntidElement="+accntidElement);

			if (nameElement!=null && accntidElement!=null) {
				retValue = new BasicNameValuePair(nameElement.attr("value"), accntidElement.attr("value"));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retValue;
	}
	
	protected boolean stageTwoLoginSuccessCheck(HttpEntity httpEntity) {
		boolean retValue = false;
		try {
			String responseHtml = readFromStream(httpEntity);
			if (responseHtml!=null && responseHtml.contains("OK")) {
				retValue = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retValue;
	}
	
	protected String readFromStream(HttpEntity httpEntity) {
		StringBuffer htmlContent = new StringBuffer();
		try {		
			DataInputStream inputStream = new DataInputStream(httpEntity.getContent());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			String strLine = null;
			while ((strLine = bufferedReader.readLine()) != null)   {
				htmlContent.append(strLine);
			}
			inputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//System.out.println(htmlContent);
		return htmlContent.toString();
	}
	
	protected Map<String, String> getQuestions(HttpEntity httpEntity) {
		Map<String, String> retMap = new HashMap<String, String>();
		try {
			String htmlContent = readFromStream(httpEntity);
			Document doc = Jsoup.parse(htmlContent);
			Element loginIdElement = doc.select("#sLoginID").first(); retMap.put("sLoginID", loginIdElement.attr("value"));
			Element sBrokerElement = doc.select("#sBroker").first(); retMap.put("sBroker", sBrokerElement.attr("value"));
			Element st1Element = doc.select("#st1").first(); retMap.put("st1", st1Element.attr("value"));
			Element st2Element = doc.select("#st2").first(); retMap.put("st2", st2Element.attr("value"));
			Element nameQuestAuthElement = doc.select("#name_questauth").first(); retMap.put("name_questauth", nameQuestAuthElement.attr("value")); 
			Element keyQuestAuthElement = doc.select("#key_questauth").first(); retMap.put("key_questauth", keyQuestAuthElement.attr("value"));

			String answer1 = null;
			String answer2 = null;
			
			int[] questionIds = new int[2];
			int[] questionPositonInHtml = new int[2];
			String[] answers = new String[2];

			int qCount =0;
			for(int i=0;i<questions.size();i++) {
				NameValuePair aQuestion = questions.get(i);
				if (htmlContent.contains(aQuestion.getName())) {
					questionIds[qCount] = i;
					questionPositonInHtml[qCount] = htmlContent.indexOf(aQuestion.getName());
					answers[qCount] = aQuestion.getValue();
					qCount++;
				}
			}
			if (questionPositonInHtml[0] < questionPositonInHtml[1]) {
				answer1 = answers[0];
				answer2 = answers[1];
			} else {
				answer1 = answers[1];
				answer2 = answers[0];
			}
			retMap.put("answer1", answer1);
			retMap.put("answer2", answer2);
			
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retMap;
	}
	
	protected Map<String, String> getParamsForAMO(HttpEntity httpEntity) {
		Map<String, String> retMap = new HashMap<String, String>();
		try {
			String htmlContent = readFromStream(httpEntity);
			Document doc = Jsoup.parse(htmlContent);
			
			Element options = doc.select("select.listbox_order_window4 > option").first(); 
			//System.out.println("First options="+options);
			String tokenNameCombo = options.attr("value").trim();
			retMap.put("Token", tokenNameCombo.substring(0,tokenNameCombo.indexOf("|")));
			int boardLot = Integer.parseInt(  tokenNameCombo.substring(tokenNameCombo.indexOf("|")+1)   ); // Don't know what to do with this
						
			retMap.put("compname", options.text());
			
			Element name1Otions = doc.select("select.listbox_order_window3 > option").first();
			//System.out.println("name1Otions="+name1Otions);
			retMap.put("sTradSym", name1Otions.attr("value"));
			retMap.put("sSymbolName", name1Otions.text());

			String orderName = htmlContent.substring(htmlContent.indexOf("var name =")+10);
			orderName = orderName.substring(0,orderName.indexOf("';"));
			orderName = orderName.trim();
			if (orderName.startsWith("'")) orderName = orderName.substring(1);
			retMap.put("orderName", orderName);
			
			String orderKey = htmlContent.substring(htmlContent.indexOf("var key  =")+10);
			orderKey = orderKey.substring(0,orderKey.indexOf("';"));
			orderKey = orderKey.trim();
			if (orderKey.startsWith("'")) orderKey = orderKey.substring(1);
			retMap.put("orderKey", orderKey);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retMap;
	}
	
	protected Map<String, String> getParamsForLive(HttpEntity httpEntity) {
		Map<String, String> retMap = new HashMap<String, String>();
		try {
			String htmlContent = readFromStream(httpEntity);
			Document doc = Jsoup.parse(htmlContent);
			
			Element options = doc.select("select.listbox_order_window4 > option").first(); 
			//System.out.println("First options="+options);
			String tokenNameCombo = options.attr("value").trim();
			retMap.put("Token", tokenNameCombo.substring(0,tokenNameCombo.indexOf("|")));
			int boardLot = Integer.parseInt(  tokenNameCombo.substring(tokenNameCombo.indexOf("|")+1)   ); // Don't know what to do with this
						
			retMap.put("compname", options.text());
			
			Element name1Otions = doc.select("select.listbox_order_windowExpiry > option").first();
			//System.out.println("name1Otions="+name1Otions);
			retMap.put("sTradSym", name1Otions.attr("value"));
			retMap.put("sSymbolName", name1Otions.text());

			String orderName = htmlContent.substring(htmlContent.indexOf("var name =")+10);
			orderName = orderName.substring(0,orderName.indexOf("';"));
			orderName = orderName.trim();
			if (orderName.startsWith("'")) orderName = orderName.substring(1);
			retMap.put("orderName", orderName);
			
			String orderKey = htmlContent.substring(htmlContent.indexOf("var key  =")+10);
			orderKey = orderKey.substring(0,orderKey.indexOf("';"));
			orderKey = orderKey.trim();
			if (orderKey.startsWith("'")) orderKey = orderKey.substring(1);
			retMap.put("orderKey", orderKey);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retMap;
	}
	
	protected Map<String, String> getAMOParamsForExecuteOrder(HttpEntity httpEntity) {
		Map<String, String> retMap = new HashMap<String, String>();
		try {
			String htmlContent = readFromStream(httpEntity);
			//System.out.println("in getAMOParamsForExecuteOrder htmlContent="+htmlContent);
			String orderKey = htmlContent.substring(htmlContent.indexOf("AESEncryptCtr(document.SubmitOrder.Transaction_password.value,'")+63,htmlContent.indexOf("',256)"));
			retMap.put("orderKey", orderKey);			
			
			Document doc = Jsoup.parse(htmlContent);			
			Element options = doc.select("form[name=executeorder] > input").first(); 
			retMap.put(options.attr("name"), options.attr("value"));	
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retMap;
	}
	
	protected Map<String, String> getLiveParamsForExecuteOrder(HttpEntity httpEntity) {
		Map<String, String> retMap = new HashMap<String, String>();
		try {
			String htmlContent = readFromStream(httpEntity);
			//System.out.println("in getAMOParamsForExecuteOrder htmlContent="+htmlContent);
			String orderKey = htmlContent.substring(htmlContent.indexOf("AESEncryptCtr(document.SubmitOrder.Transaction_password.value, '")+64,htmlContent.indexOf("',"));
			retMap.put("orderKey", orderKey);			
			
			Document doc = Jsoup.parse(htmlContent);			
			Element options = doc.select("form[name=executeorder] > input").first(); 
			retMap.put(options.attr("name"), options.attr("value"));	
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retMap;
	}
	
	protected String getOrderNumber(HttpEntity httpEntity) {
		String retStr = "";
		try {
			String htmlContent = readFromStream(httpEntity);
			if (htmlContent.indexOf("NOW Order Number :")>0) {
				retStr = htmlContent.substring(htmlContent.indexOf("NOW Order Number :")+18);
				if (retStr.indexOf("</font>")>0) retStr = retStr.substring(0,retStr.indexOf("</font>"));
				if (retStr.indexOf("</br>")>0) retStr = retStr.substring(0,retStr.indexOf("</br>"));
				retStr = retStr.trim();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}
	
	protected void consumeUrl(String urlStr) {
		System.out.println("In consumeUrl for: "+urlStr);
		try {
			HttpGet httpGet = getHttpGetObject(urlStr);
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
	
	protected Map<String, String> getParamsForBseQuote(HttpEntity httpEntity) {
		Map<String, String> retMap = new HashMap<String, String>();
		try {
			String htmlContent = readFromStream(httpEntity);
			Document doc = Jsoup.parse(htmlContent);
			Element searchForOption = doc.select("select.listbox_order_window4 > option").first();
			retMap.put("searchFor", searchForOption.attr("value"));
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retMap;
	}
	
	protected IntradayInfo getPrice(HttpEntity httpEntity) {
		IntradayInfo retVal = null;
		try {
			retVal = new IntradayInfo();
			String htmlContent = readFromStream(httpEntity);
			//System.out.println(htmlContent);
			Document doc = Jsoup.parse(htmlContent);
			Elements tdElements = doc.select("table.content-denote tr td.head_secur");
			for (Element element : tdElements) {
				String textPart = element.text();
			    if (textPart.contains("Last Traded Date :")) {
			    	SimpleDateFormat dateFomrat = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
			    	String datePart = textPart.substring(textPart.indexOf("Last Traded Date :")+18);
			    	datePart = datePart.trim();
			    	retVal.setTickTime(dateFomrat.parse(datePart));
			    }
			}
			boolean ltpElementFound = false;
			tdElements = doc.select("table.content-table tr td");
			for (Element element : tdElements) {
				String textPart = element.text();
				//System.out.println("textPart="+textPart);
				if (textPart.contains("LTPrice")) { // Next immediate td element Contains LTP
					ltpElementFound = true;
					continue;
				}
				if (ltpElementFound) { //Current node is LTP
					retVal.setCurrentTickPrice(Float.parseFloat(textPart.substring(0,textPart.length()-1)));
					break;
				}
			}
			//System.out.println("ltpElementFound:"+ltpElementFound);
		} catch(Exception ex) {
			ex.printStackTrace();
		}		
		return retVal;
	}
}

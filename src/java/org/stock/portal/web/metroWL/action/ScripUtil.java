package org.stock.portal.web.metroWL.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ScripUtil {

	public String getIdFromMarketsmojo (String bseCode, String nseCode) {
		String retStr = null;
		//HttpURLConnection httpConn = null;
		try {
			
			CloseableHttpClient httpClient = HttpClients.custom().setRoutePlanner(getRoutePlanner()).disableContentCompression().build();
			HttpClientContext httpContext =  HttpClientContext.create();
			
			String symbolCodeToUse = bseCode;
			if (symbolCodeToUse==null || symbolCodeToUse.trim().length()==0) symbolCodeToUse = nseCode;
			
//			URL exchangeLink = new URL("https://www.marketsmojo.com/portfolio-plus/frontendsearch?SearchPhrase="+ symbolCodeToUse);
//			httpConn = (HttpURLConnection)exchangeLink.openConnection();
//			httpConn.setRequestMethod("GET");
//			httpConn.setDoOutput(true);			
//			httpConn.connect();
//			
			
			String finalUrl = "https://www.marketsmojo.com/portfolio-plus/frontendsearch?SearchPhrase="+ symbolCodeToUse;
			HttpGet httpGet = getHttpGetObject(finalUrl, "www.marketsmojo.com", "www.marketsmojo.com"); 
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    HttpEntity httpEntity = httpResponse.getEntity();
		    
			BufferedReader bufferedReader = new BufferedReader(	new InputStreamReader(httpEntity.getContent()));
			String strLine = null;
			while ((strLine = bufferedReader.readLine()) != null)   {
				if (strLine.trim().length()>0) {
					System.out.println("strLine="+strLine);
					if(strLine!=null && strLine.length()>0) {
						JSONParser parser = new JSONParser();
						Object obj = parser.parse(strLine);
						
						JSONArray jsonArry = (JSONArray) obj;
						for(int i=0;i<jsonArry.size();i++) {
							JSONObject aScripData = (JSONObject) jsonArry.get(i);
							System.out.println("aIndexData="+aScripData);
							
							String idPart = "";
							Object aObj = aScripData.get("Id");
							if (aObj instanceof Long) {
								idPart = (Long) aScripData.get("Id")+"";								
							} else if (aObj instanceof String) {
								idPart = (String) aScripData.get("Id");								
							}
							
							String scriptCodePart = "";
							aObj = aScripData.get("ScriptCode");
							if (aObj instanceof Long) {
								scriptCodePart = (Long) aScripData.get("ScriptCode") +"";	
							} else if (aObj instanceof String) {
								scriptCodePart = (String) aScripData.get("ScriptCode");						
							} 
							
							String symbolPart = (String) aScripData.get("Symbol");
							
							if (scriptCodePart!=null && scriptCodePart.equalsIgnoreCase(symbolCodeToUse)) {
								retStr = idPart+"";
								break;
							} else if (symbolPart!=null && symbolPart.equalsIgnoreCase(symbolCodeToUse)) {
								retStr = idPart+"";
								break;
							}
						}					
					}
				}
			}		
			EntityUtils.consume(httpEntity);
		    httpResponse.close();
			bufferedReader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;		
	}
	
	
	public String getIdFromSockadda (String bseCode, String nseCode) {
		String retStr = null;
		HttpURLConnection httpConn = null;
		try {
			CloseableHttpClient httpClient = HttpClients.custom().setRoutePlanner(getRoutePlanner()).disableContentCompression().build();
			HttpClientContext httpContext =  HttpClientContext.create();
			
			
			String symbolCodeToUse = bseCode;
			if (symbolCodeToUse==null || symbolCodeToUse.trim().length()==0) symbolCodeToUse = nseCode;
			
			String finalUrl = "https://www.stockadda.com/modules/mod_company_search/helper.php?task=ajaxsearch&searchterm="+ symbolCodeToUse;			
			HttpGet httpGet = getHttpGetObject(finalUrl, "www.stockadda.com", "www.stockadda.com"); 
			
				
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    HttpEntity httpEntity = httpResponse.getEntity();
			
		
//			URL exchangeLink = new URL("https://www.stockadda.com/modules/mod_company_search/helper.php?task=ajaxsearch&searchterm="+ symbolCodeToUse);
//	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exchangeLink.openStream()));
//	        
	        
//			URL exchangeLink = new URL("https://www.stockadda.com/modules/mod_company_search/helper.php?task=ajaxsearch&searchterm="+ symbolCodeToUse);
//			httpConn = (HttpURLConnection)exchangeLink.openConnection();
//			httpConn.setRequestMethod("GET");
//			httpConn.setDoOutput(true);			
//			httpConn.connect();			
//			BufferedReader bufferedReader = new BufferedReader(	new InputStreamReader(httpConn.getInputStream()));
		    
		    
		    BufferedReader bufferedReader = new BufferedReader(	new InputStreamReader(httpEntity.getContent()));
		    
			String strLine = null;
			while ((strLine = bufferedReader.readLine()) != null)   {
				System.out.println("strLine="+strLine);
				if(strLine!=null && strLine.length()>0) {
					JSONParser parser = new JSONParser();
					JSONObject aScripDataHead = (JSONObject) parser.parse(strLine);
					
//					System.out.print((String) aScripData.get("stock_alias") +"");
//					System.out.print((String) aScripData.get("scripcode") +"");
//					System.out.print((String) aScripData.get("symbol") +"");
//					System.out.print((String) aScripData.get("companies") +"");
					
					JSONObject dataSection = (JSONObject) aScripDataHead.get("data");
					
					//System.out.print((String) dataSection.get("companies") +"");
					
					JSONArray jsonArry = (JSONArray) dataSection.get("companies");
					for(int i=0;i<jsonArry.size();i++) {
						JSONObject aScripData = (JSONObject) jsonArry.get(i);
						System.out.println("aIndexData="+aScripData);
						
						String scripcode = (String) aScripData.get("scripcode") +"";
						String symbol = (String) aScripData.get("symbol") +"";
						
						String scriptCodePart = (String) aScripData.get("stock_alias") +"";
						
						if (scripcode!=null && scripcode.equalsIgnoreCase(symbolCodeToUse)) {
							retStr = scriptCodePart+"";
							break;
						} else if (symbol!=null && symbol.equalsIgnoreCase(symbolCodeToUse)) {
							retStr = scriptCodePart+"";
							break;
						}
					}					
				}
			}
			EntityUtils.consume(httpEntity);
		    httpResponse.close();
			bufferedReader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;		
	}
	
	public String getIdFromTijoriFinance(String bseCode, String nseCode) {
		String retStr = null;
		HttpURLConnection httpConn = null;
		try {
			CloseableHttpClient httpClient = HttpClients.custom().setRoutePlanner(getRoutePlanner()).disableContentCompression().build();
			HttpClientContext httpContext =  HttpClientContext.create();
			
			
			String symbolCodeToUse = bseCode;
			if (symbolCodeToUse==null || symbolCodeToUse.trim().length()==0) symbolCodeToUse = nseCode;
			
			String finalUrl = "https://tijorifinance.com/search?prefix="+ symbolCodeToUse;			
			HttpGet httpGet = getHttpGetObject(finalUrl, "www.tijorifinance.com", "www.tijorifinance.com"); 	
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    HttpEntity httpEntity = httpResponse.getEntity();
			
		
		    
		    BufferedReader bufferedReader = new BufferedReader(	new InputStreamReader(httpEntity.getContent()));
		    
			String strLine = null;
			while ((strLine = bufferedReader.readLine()) != null)   {
				System.out.println("strLine="+strLine);
				if(strLine!=null && strLine.length()>0) {
					JSONParser parser = new JSONParser();
					JSONObject aScripDataHead = (JSONObject) parser.parse(strLine);
					
//					System.out.print((String) aScripData.get("stock_alias") +"");
//					System.out.print((String) aScripData.get("scripcode") +"");
//					System.out.print((String) aScripData.get("symbol") +"");
//					System.out.print((String) aScripData.get("companies") +"");
					
					JSONArray jsonArry = (JSONArray) aScripDataHead.get("suggestions");
					
					//System.out.print((String) dataSection.get("companies") +"");
					
					for(int i=0;i<jsonArry.size();i++) {
						JSONObject aScripData = (JSONObject) jsonArry.get(i);
						System.out.println("aIndexData="+aScripData);
						
						String tijorifinanceCodePart = (String) aScripData.get("slug") +"";
						System.out.println("slug="+tijorifinanceCodePart);
						if (tijorifinanceCodePart!=null) {
							retStr = tijorifinanceCodePart+"";
							break;
						}
					}					
				}
			}
			EntityUtils.consume(httpEntity);
		    httpResponse.close();
			bufferedReader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;		
	}
	
	public String getIdFromReuters(String bseCode, String nseCode) {
		String retStr = null;
		HttpURLConnection httpConn = null;
		try {
			CloseableHttpClient httpClient = HttpClients.custom().setRoutePlanner(getRoutePlanner()).disableContentCompression().build();
			HttpClientContext httpContext =  HttpClientContext.create();
			
			
			String symbolCodeToUse = bseCode;
			if (symbolCodeToUse==null || symbolCodeToUse.trim().length()==0) symbolCodeToUse = nseCode;
			
			String finalUrl = "https://www.reuters.com/search/news?blob="+ symbolCodeToUse;			
			HttpGet httpGet = getHttpGetObject(finalUrl, "www.reuters.com", "www.reuters.com"); 	
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    HttpEntity httpEntity = httpResponse.getEntity();
			
		    BufferedReader bufferedReader = new BufferedReader(	new InputStreamReader(httpEntity.getContent()));
		    
			String strLine = null;
			while ((strLine = bufferedReader.readLine()) != null)   {
				//System.out.println("strLine="+strLine);
				if(strLine!=null && strLine.length()>0) {
					if (strLine.contains("www.reuters.com/companies")) {
						Document doc = Jsoup.parse(strLine);
						Element anchorElement = doc.select("a").first();
						String[] urlParts = anchorElement.attr("href").split("/");
						retStr = urlParts[urlParts.length-1];
					}
				}
			}
			EntityUtils.consume(httpEntity);
		    httpResponse.close();
			bufferedReader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;		
	}
	
	
	public String getIdFromTrendlyne(String bseCode, String nseCode) {
		String retStr = null;
		HttpURLConnection httpConn = null;
		try {
			CloseableHttpClient httpClient = HttpClients.custom().setRoutePlanner(getRoutePlanner()).disableContentCompression().build();
			HttpClientContext httpContext =  HttpClientContext.create();
			
			String symbolCodeToUse = nseCode;
			if (symbolCodeToUse==null || symbolCodeToUse.trim().length()==0) symbolCodeToUse = bseCode;
			
			String finalUrl = "https://trendlyne.com/member/api/ac_snames/stock/?term="+ symbolCodeToUse;			
			HttpGet httpGet = getHttpGetObject(finalUrl, "https://trendlyne.com/alerts/portfolio-real-time-alerts/", "trendlyne.com"); 	
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    HttpEntity httpEntity = httpResponse.getEntity();
		    
		    BufferedReader bufferedReader = new BufferedReader(	new InputStreamReader(httpEntity.getContent()));
		    
			String strLine = null;
			while ((strLine = bufferedReader.readLine()) != null)   {
				System.out.println("strLine="+strLine);
				if(strLine!=null && strLine.length()>0) {
					JSONParser parser = new JSONParser();
					JSONArray jsonArry = (JSONArray) parser.parse(strLine);
						
					for(int i=0;i<jsonArry.size();i++) {
						JSONObject aScripData = (JSONObject) jsonArry.get(i);
						
						retStr = (String) aScripData.get("nexturl");
						System.out.println("tUrl="+retStr);
					}
					//
				}
				System.out.println("DONE");
			}
			EntityUtils.consume(httpEntity);
		    httpResponse.close();
			bufferedReader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;		
	}
	
	
	public String getIdFromTickertape(String bseCode, String nseCode) {
		String retStr = null;
		HttpURLConnection httpConn = null;
		try {
			CloseableHttpClient httpClient = HttpClients.custom().setRoutePlanner(getRoutePlanner()).disableContentCompression().build();
			HttpClientContext httpContext =  HttpClientContext.create();
			
			String symbolCodeToUse = nseCode;
			if (symbolCodeToUse==null || symbolCodeToUse.trim().length()==0) symbolCodeToUse = bseCode;
			
			String finalUrl = "https://api.tickertape.in/search?types=stock&text=" + symbolCodeToUse;	 		
			HttpGet httpGet = getHttpGetObject(finalUrl, "https://www.tickertape.in", "api.tickertape.in"); 	
			
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    HttpEntity httpEntity = httpResponse.getEntity();
		    
		    BufferedReader bufferedReader = new BufferedReader(	new InputStreamReader(httpEntity.getContent()));
		    
			String strLine = null;
			while ((strLine = bufferedReader.readLine()) != null)   {
				System.out.println("strLine="+strLine);
				if(strLine!=null && strLine.length()>0) {
					JSONParser parser = new JSONParser();
					JSONObject rootNode = (JSONObject) parser.parse(strLine);
					JSONObject dataNode = (JSONObject) rootNode.get("data");
					JSONArray jsonArry = (JSONArray) dataNode.get("stocks");
					
					for(int i=0;i<jsonArry.size();i++) {
						JSONObject aScripData = (JSONObject) jsonArry.get(i);
						String tickerNode = (String) aScripData.get("ticker");

						if (tickerNode.equals(symbolCodeToUse)) {
							retStr = (String) aScripData.get("slug");
							System.out.println("tUrl="+retStr);							
						}
					}
				}
				System.out.println("DONE");
			}
			EntityUtils.consume(httpEntity);
		    httpResponse.close();
			bufferedReader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;		
	
	}
	
	public String getIdFromSimplywallst(String bseCode, String nseCode) {
		String retStr = null;
		HttpURLConnection httpConn = null;
		try {
			CloseableHttpClient httpClient = HttpClients.custom().setRoutePlanner(getRoutePlanner()).disableContentCompression().build();
			HttpClientContext httpContext =  HttpClientContext.create();
			
			String symbolCodeToUse = nseCode;
			if (symbolCodeToUse==null || symbolCodeToUse.trim().length()==0) symbolCodeToUse = bseCode;
			
			String finalUrl = "https://17iqhzwxzw-1.algolianet.com/1/indexes/companies/query?x-algolia-agent=Algolia%20for%20JavaScript%20(4.4.0)%3B%20Browser%20(lite)&x-algolia-api-key=be7c37718f927d0137a88a11b69ae419&x-algolia-application-id=17IQHZWXZW";
			HttpPost httpPost = getHttpPostObject(finalUrl, "https://simplywall.st/", "17iqhzwxzw-1.algolianet.com"); 
				
			String JSON_STRING="{\"query\":\"" + symbolCodeToUse + "\"}"; //,\"highlightPostTag\":\" \",\"highlightPreTag\":\" \",\"restrictHighlightAndSnippetArrays\":true}";
			System.out.println("JSON_STRING="+JSON_STRING);
		    HttpEntity stringEntity = new StringEntity(JSON_STRING,ContentType.APPLICATION_JSON);
		    httpPost.setEntity(stringEntity);
			
			//{"query":"532966","highlightPostTag":" ","highlightPreTag":" ","restrictHighlightAndSnippetArrays":true}: 
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost, httpContext);
			
			System.out.println(httpResponse.getStatusLine());
		    HttpEntity httpEntity = httpResponse.getEntity();
		    
		    BufferedReader bufferedReader = new BufferedReader(	new InputStreamReader(httpEntity.getContent()));
		    
			String strLine = null;
			while ((strLine = bufferedReader.readLine()) != null)   {
				//System.out.println("strLine="+strLine);
				if(strLine!=null && strLine.length()>0) {
					JSONParser parser = new JSONParser();
					JSONObject rootNode = (JSONObject) parser.parse(strLine);
					
					JSONArray jsonArry = (JSONArray) rootNode.get("hits");
					System.out.println(jsonArry);
					for(int i=0;i<jsonArry.size();i++) {
						JSONObject aScripData = (JSONObject) jsonArry.get(i);
						String uniqueSymbol = (String) aScripData.get("uniqueSymbol");
						String url = (String) aScripData.get("url");
						
						System.out.println("uniqueSymbol="+uniqueSymbol);
						System.out.println("url="+url);
						
						if (nseCode!=null && uniqueSymbol.equals("NSEI:"+nseCode.trim())) {
							retStr = url;
						} else if (bseCode!=null && uniqueSymbol.equals("BSE:"+bseCode.trim())) {
							retStr = url;
						}
					}
					
				}
				
			}
			System.out.println("DONE");
			EntityUtils.consume(httpEntity);
		    httpResponse.close();
			bufferedReader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retStr;		
	
	}
	
	private HttpGet getHttpGetObject(String getUrl, String refererUrl, String hostname) {
		HttpGet httpGet = new HttpGet(getUrl);
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0");
		httpGet.addHeader("Host", hostname);
		httpGet.addHeader("Referer", refererUrl);
		
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Accept-Language", "en-GB,en;q=0.5");
		//httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
		//httpGet.setHeader("Cookie", getCookies());
		//httpGet.setHeader("csrftoken","bmN8StbPTNZSjsayfyAE67IR4IswoaCE");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
		return httpGet;
	}
	
	private HttpPost getHttpPostObject(String getUrl, String refererUrl, String hostname) {
		HttpPost httpPost = new HttpPost(getUrl);
		httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0");
		httpPost.addHeader("Host", hostname);
		httpPost.addHeader("Referer", refererUrl);
		
		httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpPost.setHeader("Accept-Language", "en-GB,en;q=0.5");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		return httpPost;
	}
	
	private DefaultRoutePlanner getRoutePlanner() {
		
		DefaultRoutePlanner routePlanner = null; 
		try {
	        Proxy proxy = (Proxy) ProxySelector.getDefault().select(new URI("http://www.google.com/")).iterator().next();;
	       
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
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return routePlanner;
	}
	
}

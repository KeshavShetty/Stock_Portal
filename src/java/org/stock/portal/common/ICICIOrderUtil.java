package org.stock.portal.common;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class ICICIOrderUtil {
	
	private String loginId;
	private String password;
	private String idNumber;
	
	private String iciciAcNumber;
	
	private DefaultHttpClient httpClient = null;
	private HttpContext httpContext = null;
	
	public ICICIOrderUtil(String loginId, String pwd, String panIdn, String iciciAcNumber) {
		super();
		this.loginId = loginId; //"";
		this.password = pwd; //"";
		this.idNumber = panIdn; //"";
		this.iciciAcNumber = iciciAcNumber;
		try {
			httpClient = new DefaultHttpClient();
			//configureProxy();
			configureCookieStore();
			configureSSLHandling();
		    
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (httpClient!=null) {
			System.out.println("In Finalize: Closing httpClient");
			httpClient = null;
		}
	}
	
	private HttpPost getICICILoginForm() {
		HttpPost retPostObj = null;
		try {
			retPostObj = new HttpPost("https://secure.icicidirect.com/Trading/LBS/Customer/validlogon.asp");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("FML_USR_ID", loginId));
			nvps.add(new BasicNameValuePair("FML_USR_USR_PSSWRD", password));
			nvps.add(new BasicNameValuePair("FML_USR_DT_BRTH", idNumber.toUpperCase()));		
			nvps.add(new BasicNameValuePair("BrowsVer", "FF32. | Windows 7"));
			retPostObj.setEntity(new UrlEncodedFormEntity(nvps));
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return retPostObj;
	}
	
	private HttpPost getICICIOrderForm(String iciciCode, int quantity, String orderTpe, float price) {
		HttpPost retPostObj = null;
		try {
			retPostObj = new HttpPost("/Trading/LBS/Equity/Trading_verifyOrder.asp");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("HelpType", ""));
			nvps.add(new BasicNameValuePair("FML_XCHNG_ST0", "C"));
			nvps.add(new BasicNameValuePair("FML_XCHNG_ST1", "C"));
			nvps.add(new BasicNameValuePair("FML_ACCOUNT", iciciAcNumber));
			nvps.add(new BasicNameValuePair("Temp_FML_ORD_PRDCT_TYP", "C"));
			nvps.add(new BasicNameValuePair("FML_SQ_FLAG", "M"));
			nvps.add(new BasicNameValuePair("FML_ORD_PRDCT_TYP", "C"));
			nvps.add(new BasicNameValuePair("FML_STCK_CD", iciciCode));
			nvps.add(new BasicNameValuePair("FML_QTY", Integer.toString(quantity)));
			nvps.add(new BasicNameValuePair("FML_DOTNET_FLG", "Y"));
			nvps.add(new BasicNameValuePair("FML_URL_FLG", "http://getquote.icicidirect.com/trading_stock_quote.aspx"));
			nvps.add(new BasicNameValuePair("FML_POINT_TYPE", "T")); //Order Validity
			if (orderTpe.equalsIgnoreCase("Limit")) {
				nvps.add(new BasicNameValuePair("FML_ORD_TYP", "L" ));
				nvps.add(new BasicNameValuePair("FML_ORD_LMT_RT", Float.toString(price)));
			} else {
				nvps.add(new BasicNameValuePair("FML_ORD_TYP", "M" )); 
			}
			nvps.add(new BasicNameValuePair("FML_GMS_CSH_PRDCT_PRCNTG", ""));
			nvps.add(new BasicNameValuePair("FML_ORD_DSCLSD_QTY", ""));
			
			nvps.add(new BasicNameValuePair("FML_ORD_STP_LSS", ""));
//			nvps.add(new BasicNameValuePair("", ));
//			nvps.add(new BasicNameValuePair("", ));
//			nvps.add(new BasicNameValuePair("", ));
//			nvps.add(new BasicNameValuePair("", ));
			
			
			
			nvps.add(new BasicNameValuePair("BrowsVer", "FF32. | Windows 7"));
			retPostObj.setEntity(new UrlEncodedFormEntity(nvps));
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return retPostObj;
	}
	
	private HttpPost getICICIHomeForm() {
		HttpPost retPostObj = null;
		
		try {
			retPostObj = new HttpPost("https://secure.icicidirect.com/NewSiteTrading/trading/equity/trading_SelectMDP.asp");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("tt", System.currentTimeMillis()+""));
			retPostObj.setEntity(new UrlEncodedFormEntity(nvps));
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		//retPostObj.setConfig(proxyConfig);
		return retPostObj;
	}
		
	private HttpGet getEquityLimitForm() {
		HttpGet retPostObj = null;
		
		try {
			retPostObj = new HttpGet("https://secure.icicidirect.com/Trading/LBS/equity/trading_limit.asp");
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		//retPostObj.setConfig(proxyConfig);
		return retPostObj;
	}
	
	private HttpGet getLogoutForm() {
		HttpGet retPostObj = null;		
		try {
			retPostObj = new HttpGet("https://secure.icicidirect.com/Trading/LBS/Logout.asp");
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		//retPostObj.setConfig(proxyConfig);
		return retPostObj;
	}
	
	public double getLimit() {
		double retVal = 0;
		
		try {
			//Login
			HttpPost httpHandle = getICICILoginForm();
			System.out.println("1");
		    HttpResponse response = httpClient.execute(httpHandle, httpContext);
		    System.out.println("2");
		    System.out.println(response.getStatusLine());
		    HttpEntity entity = response.getEntity();
		    //printHttpResponse(entity);
		    EntityUtils.consume(entity);
		    
		    
//		    httpHandle = getICICIHomeForm();
//		    response = httpClient.execute(httpHandle, httpContext);
//		    System.out.println(response.getStatusLine());
//		    entity = response.getEntity();
//		    //printHttpResponse(entity);	     
//		    EntityUtils.consume(entity);
		    
		    //Get limit
		    HttpGet httpGetHandle = getEquityLimitForm();
		    response = httpClient.execute(httpGetHandle, httpContext);
		    System.out.println(response.getStatusLine());
		    entity = response.getEntity();
		    printHttpResponse(entity);	
		    EntityUtils.consume(entity);
		    
		    //Logout
		    httpGetHandle = getLogoutForm();
		    response = httpClient.execute(httpGetHandle, httpContext);
		    System.out.println(response.getStatusLine());
		    entity = response.getEntity();
		    //printHttpResponse(entity);	
		    EntityUtils.consume(entity);
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		return retVal;
	}
	
	public void placeBuyOrder(String iciciCode, int quantity, float price, String orderType) {
		
	}
	
	private void printHttpResponse(HttpEntity httpEntity) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
	        String line;
	        while ((line = br.readLine())!= null) {
	            System.out.println("-"+line);
	        }
	        br.close();
	        System.out.println("---------------------------------------------------");
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {	
		//System.setProperty("java.net.useSystemProxies", "true");
		ICICIOrderUtil isisiHandler = new ICICIOrderUtil("SHIAVASH", "M20vsdk76!$", "ASYPS5579C","");		
		isisiHandler.getLimit();
	}	
	
	private void configureProxy() {
		HttpHost proxy = new HttpHost("neon.kooud.com", 3128);
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}
		  
	private void configureCookieStore() {
		CookieStore cStore = new BasicCookieStore();
		httpClient.setCookieStore(cStore);
	}
		  
	private void configureSSLHandling() {
		Scheme http = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
		SSLSocketFactory sf = buildSSLSocketFactory();
		Scheme https = new Scheme("https", 443, sf);
		SchemeRegistry sr = httpClient.getConnectionManager().getSchemeRegistry();
		sr.register(http);
		sr.register(https);
	}
	
	private SSLSocketFactory buildSSLSocketFactory() {
		TrustStrategy ts = new TrustStrategy() {
			 @Override
			 public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
			 return true; // heck yea!
			 }
		 };
		  
		 SSLSocketFactory sf = null;
		  
		 try {
			 /* build socket factory with hostname verification turned off. */
			 sf = new SSLSocketFactory(ts, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		 } catch (NoSuchAlgorithmException e) {
			 //log.error("Failed to initialize SSL handling.", e);
		 } catch (KeyManagementException e) {
			 //log.error("Failed to initialize SSL handling.", e);
		 } catch (KeyStoreException e) {
			 //log.error("Failed to initialize SSL handling.", e);
		 } catch (UnrecoverableKeyException e) {
			 //log.error("Failed to initialize SSL handling.", e);
		 }
		 return sf;
	}
	
}

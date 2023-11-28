package org.stock.portal.web.servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetLiveBSEQuoteServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException {
		HttpURLConnection httpConn = null;
		try
		{
			response.setContentType("application/octet-stream");                    
	        ServletOutputStream stream = response.getOutputStream();
	        DataOutputStream outStream = new DataOutputStream(stream);
	        
	        String bseCode = request.getParameter("bseCode");			
	        outStream.writeBytes(getCMPFromBSE(bseCode));
	        
	        outStream.close();
	        stream.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}			
	}
	
	private String getCMPFromBSE(String bseCode) {
		String retVal = "keshav,shetty";
		HttpURLConnection httpConn = null;
		try {
			URL exchangeLink = new URL("http://www.bseindia.com/bseplus/StockReach/AdvStockReach.aspx?section=tab1&scripcode="+ bseCode);
			httpConn = (HttpURLConnection)exchangeLink.openConnection();
			httpConn.setRequestMethod("GET");
			httpConn.setDoOutput(true);			
			httpConn.connect();
			
			BufferedReader in = new BufferedReader(	new InputStreamReader(httpConn.getInputStream()));
			String strData1 = in.readLine();
			String openprice="";
			String lastTradedprice="";
			String highPrice="";
			String lowPrice="";
			if (strData1!=null && strData1.indexOf("#$#")!=-1) {
				String strData = strData1.substring(strData1.indexOf("#$#") + 3);
				
				if (strData.indexOf("#SECTION#") != -1) {
			        String[] astr = strData.split("#SECTION#");
			        if (astr.length>1) {
			        	String[] str1 = astr[1].split("#@#");
			        	lastTradedprice = str1[6].trim().replace(",", "");	
			        	openprice = str1[11].trim().replace(",", "");	
//			        	for(int i=0;i<str1.length;i++) {
//			        		System.out.println("i=" + i+ " str1[i]="+str1[i]);
//			        	}
//			        	System.out.println("--------------------");
			        }		        
			        if (astr.length>2) {
			        	String[] str2 = astr[2].split("#@#");	
			        	String highAndLow = str2[0].trim();
			        	highPrice = (highAndLow.substring(0, highAndLow.indexOf("/")).trim()).replace(",", "");
			        	lowPrice = (highAndLow.substring(highAndLow.indexOf("/")+1).trim()).replace(",", "");			        	
			        }
			    } else {
			    	System.out.println("processBSEAdvStockReachData: No reposnse data found");
			    }
			}
			retVal = openprice+","+highPrice+","+lowPrice+","+lastTradedprice;
			in.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if(httpConn != null) {
				httpConn.disconnect(); 
			}
		}
		return retVal;
	}

}
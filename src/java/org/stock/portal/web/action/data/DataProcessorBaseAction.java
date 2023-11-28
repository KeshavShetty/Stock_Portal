package org.stock.portal.web.action.data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;
import org.stock.portal.web.action.BaseAction;

abstract class DataProcessorBaseAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	Logger log = Logger.getLogger(DataProcessorBaseAction.class.getName());
	
	
	
	protected boolean downloadAndExtractBSEBhavcopy(String basePath, String relativebhavCopyFilename) {
		boolean retflag = false;
		try {
			URL exchangeLink = new URL("http://www.bseindia.com/download/BhavCopy/Equity/eq" + relativebhavCopyFilename+ "_csv.zip");
			ZipInputStream zis = new ZipInputStream(exchangeLink.openStream());
			ZipEntry zEntry = zis.getNextEntry();
			byte[] buffer = new byte[4096];
			if (zEntry != null) {
				System.out.print("-Extracting: "+zEntry);
	            FileOutputStream fos = new FileOutputStream(basePath+relativebhavCopyFilename+".csv");
	            int numBytes;
	            while ((numBytes = zis.read(buffer, 0, buffer.length)) != -1) fos.write(buffer, 0, numBytes);
	            fos.close();
	        }
	        zis.closeEntry();
	        retflag = true;
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
		return retflag ;
	}
	
	protected boolean downloadAndExtractNSEBhavcopy(String basePath, String nseDateArg) {
		boolean retflag = false;
		try {			
			URL exchangeLink = new URL("https://www.nseindia.com/content/historical/EQUITIES/" + getYear(nseDateArg) +"/" + getMonth(nseDateArg) + "/" + getNseBhavfilename(nseDateArg) + ".zip");
			ZipInputStream zis = new ZipInputStream(exchangeLink.openStream());
			ZipEntry zEntry = zis.getNextEntry();
			byte[] buffer = new byte[4096];
			if (zEntry != null) {
				System.out.println("Extracting: "+zEntry);
	            FileOutputStream fos = new FileOutputStream(basePath+getNseBhavfilename(nseDateArg));
	            int numBytes;
	            while ((numBytes = zis.read(buffer, 0, buffer.length)) != -1) fos.write(buffer, 0, numBytes);
	            fos.close();
	        }
	        zis.closeEntry();
	        retflag = true;
		}catch(Exception ex) {
			System.out.println("Error while downloading Nse bhav copy for "+nseDateArg);
			ex.printStackTrace();
		}
		return retflag;
	}
	
	private String getYear(String argstr) {
		return argstr.substring(6,10);
	}
	
	private String getMonth(String argstr) {
		String monStr = argstr.substring(3,5);
		if (monStr.equals("01") || monStr.equals("1")) return "JAN";
		else if (monStr.equals("02") || monStr.equals("2")) return "FEB";
		else if (monStr.equals("03") || monStr.equals("3")) return "MAR";
		else if (monStr.equals("04") || monStr.equals("4")) return "APR";
		else if (monStr.equals("05") || monStr.equals("5")) return "MAY";
		else if (monStr.equals("06") || monStr.equals("6")) return "JUN";
		else if (monStr.equals("07") || monStr.equals("7")) return "JUL";
		else if (monStr.equals("08") || monStr.equals("8")) return "AUG";
		else if (monStr.equals("09") || monStr.equals("9")) return "SEP";
		else if (monStr.equals("10")) return "OCT";
		else if (monStr.equals("11")) return "NOV";
		else return "DEC";
	}
	
	private String getNseBhavfilename(String argStr) {
		//Return like cm13NOV2012bhav.csv.zip
		return "cm"+ argStr.substring(0,2) + getMonth(argStr) + getYear(argStr)+"bhav.csv";
	}
	
	protected void procssBSEEOData(String basePath, String relativebhavCopyFilename, String dataDate) {
		try {
			FileInputStream fstream = new FileInputStream(basePath+relativebhavCopyFilename+".csv");
			
			String dataDateAsString = getTimestamp(relativebhavCopyFilename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			boolean isHeaderRead = false;
			while ((strLine = br.readLine()) != null)   {
				if (!isHeaderRead) { //Skip first header line from bhav copy 
					isHeaderRead = true;
				} else if (strLine!=null && strLine.trim().length()>0) {
					Map<String, String> dataMap = new HashMap<String, String>();
					dataMap.put("exchangeCode", "BSE");
					dataMap.put("dataDate", dataDate);
					String datas[] = strLine.split(",");
					String bseCode = datas[0].trim(); dataMap.put("bseCode", bseCode);
					String tempScripName = datas[1].trim(); dataMap.put("tempScripName", tempScripName);
					String scGroup = datas[2].trim(); dataMap.put("scGroup", scGroup);
					String scType = datas[3].trim(); dataMap.put("scType", scType);
					String openPrice = datas[4].trim(); dataMap.put("openPrice", openPrice);
					String highPrice = datas[5].trim(); dataMap.put("highPrice", highPrice);
					String lowPrice = datas[6].trim(); dataMap.put("lowPrice", lowPrice);
					String closePrice = datas[7].trim(); dataMap.put("closePrice", closePrice);
					String prevClosePrice = datas[9].trim(); dataMap.put("prevClosePrice", prevClosePrice);
					String volume = datas[11].trim(); dataMap.put("volume", volume);
					populateEODDataAsyncronously(dataMap);
				}
			}
			in.close();
			//updateAverageVolume(dataDateAsString);
		} catch(Exception ex) {
			ex.printStackTrace();
			log.error(ex);
		}
	}	
	
	protected void procssNSEEOData(String basePath, String nseDateArg) {
		try {
			FileInputStream fstream = new FileInputStream(basePath+getNseBhavfilename(nseDateArg));
			
			String dataDateAsString = getTimestamp(nseDateArg);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			boolean isHeaderRead = false;
			while ((strLine = br.readLine()) != null)   {
				if (!isHeaderRead) { //Skip first header line from bhav copy 
					isHeaderRead = true;
				} else if (strLine!=null && strLine.trim().length()>0) {
					Map<String, String> dataMap = new HashMap<String, String>();
					dataMap.put("exchangeCode", "NSE");
					dataMap.put("dataDate", nseDateArg);
					
					String datas[] = strLine.split(","); 
					String nseCode = datas[0].trim(); dataMap.put("nseCode", nseCode);
					String seriesType = datas[1].trim(); dataMap.put("seriesType", seriesType);
					String isinCode = null; 
					if (datas.length>12) {
						isinCode = datas[12].trim();
					}
					dataMap.put("isinCode", isinCode);
					String openPrice = datas[2].trim(); dataMap.put("openPrice", openPrice);
					String highPrice = datas[3].trim(); dataMap.put("highPrice", highPrice);
					String lowPrice = datas[4].trim(); dataMap.put("lowPrice", lowPrice);
					String closePrice = datas[5].trim(); dataMap.put("closePrice", closePrice);
					String prevClosePrice = datas[7].trim(); dataMap.put("prevClosePrice", prevClosePrice);		
					String volume = datas[8].trim(); dataMap.put("volume", volume);
										
					populateEODDataAsyncronously(dataMap);	
				}
			}
			in.close();
		} catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	private String getTimestamp(String bhavCopyFilename) {
		String retString = ""; //"2012-10-26 17:10:10"; // Filename is EQ261012 convert into yyyy-dd-mm hh24:mm:ss e.g: 2011-05-16 17:10:10 
		String tempString = bhavCopyFilename;//.substring(2, 8);		
		retString = retString + "20" + tempString.substring(4,6) + "-" + tempString.substring(2,4) + "-" + tempString.substring(0,2) + " 16:59:59";
		System.out.println("In getTimestamp dateString="+retString);
		return retString;
	}
	
	abstract void populateEODDataAsyncronously(Map dataMap);
	
	
}
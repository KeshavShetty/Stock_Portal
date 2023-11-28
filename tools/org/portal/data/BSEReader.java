package org.portal.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Run with comnnad line 
 * SET CLASSPATH=%CLASSPATH%;D:\Projects\scc-cpms\libs\build\postgresql-9.0-801.jdbc4.jar
 * java -Dhttp.proxyHost=192.168.200.8 -Dhttp.proxyPort=3128 org/portal/data/BSEReader
 * 
 * @author Keshav
 *
 */
public class BSEReader {
	Connection conn=null;
	
	public void initialise(){
		try {
			//Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:6543/stockPortal_database", "postgres", "jijikos");
		} catch(Exception exec) {
			exec.printStackTrace(System.out);	
			System.out.println("Exception in initialise");
		}
	}
	
	public void releaseDb() {
		try {
			conn.close();
		} catch(Exception exec) {
			System.out.println("Exception in releaseDb");
		}
	}
	private String getBseName(String bseCode) {
		String retVal =null;
		try {
			URL exchangeLink = new URL("http://charting.bseindia.com/charting/scripcode_search.asp?scripcode="+ bseCode +"&category=0&duration=0");			
			BufferedReader in = new BufferedReader(	new InputStreamReader(exchangeLink.openStream()));
			String inputLine;			
			while ((inputLine = in.readLine()) != null){				
				if (inputLine!=null && inputLine.length()>0 && inputLine.trim().indexOf(" ")==-1) {
					retVal = inputLine;
				}
				System.out.print("In getBseName retVal="+retVal);
			}
			in.close();	
		} catch(Exception exec) {
			exec.printStackTrace();
		}
		return retVal;
	}
	
	public static String getFormattedDate(String dateStr) {
		String retVal = "";
		retVal = dateStr.substring(6)+"-"+dateStr.substring(0,2) + "-" + dateStr.substring(3,5);		
		return retVal;
	}
	
	private void processHistoricalData(int scripId,String bseName) {
		try {
			URL exchangeLink = new URL("http://charting.bseindia.com/charting/history.asp?SYMBOL=" + bseName + "&period=days&ENDDATE=2-25-2011&STARTDATE=12-14-2011&CHARTTYPE=0");
			BufferedReader in = new BufferedReader(	new InputStreamReader(exchangeLink.openStream()));
			String inputLine;	
			int initialCount=0;
			while ((inputLine = in.readLine()) != null){
				initialCount++;				
				if (inputLine!=null && inputLine.length()>0 && initialCount>4) {
					String[] dataValues=(inputLine.substring(8)).split(",");
					String dateString = dataValues[0];
					//System.out.println("dateString="+dateString);
					String open = dataValues[1];
					String high = dataValues[2];
					String low = dataValues[3];
					String close = dataValues[4];
					String volume = dataValues[5];
					String eodInsertSql = "insert into eod_data (id, data_date,exchange_code,f_scrip,open_price,high_price,low_price,close_price,volume) "
						+ " values((select nextval('eod_data_id_seq')), '"+ getFormattedDate(dateString) + "',false," + scripId + "," 
						+ open + "," + high +"," + low + "," + close + "," + volume  +")";
					//System.out.println("In processHistoricalData eodInsertSql="+eodInsertSql);
					Statement stmt = conn.createStatement();
					stmt.execute(eodInsertSql);
					stmt.close();
				}				
			}
			in.close();	
		} catch(Exception exec) {
		}
		
	}
	
	private int updateScrip(String bseCode, String bseName) {
		int retVal =0;
		try	{
			Statement stmt = conn.createStatement();			
			String updateSqlStr = "update scrips set bse_name = '" + bseName + "' where bse_code like '" +bseCode +"'";
			stmt.execute(updateSqlStr);
			
			String sqlString = "select id from scrips where bse_code like '" +bseCode +"'";
			ResultSet rs = stmt.executeQuery(sqlString);
			//System.out.println("2. In getScripId sqlString"+sqlString);
			if (rs.next()) {
				retVal = rs.getInt("id");
			}
			rs.close();
			stmt.close();
		} catch(Exception ex) {
			ex.printStackTrace(System.out);	
		}
		return retVal;
	}
	
	private List getScripList() {
		List retList = new ArrayList();
		try	{
			Statement stmt = conn.createStatement();
			//String sqlString = "select bse_code from scrips where id in (select f_scrip from watchlist_item) and bse_name is null";
			String sqlString = "select bse_code from scrips where bse_name is not null";
			//String sqlString = "select bse_code from scrips where bse_code = '900010'";
			ResultSet rs = stmt.executeQuery(sqlString);
			//System.out.println("2. In getScripId sqlString"+sqlString);
			while (rs.next()) {
				retList.add(rs.getString("bse_code").trim());
			}
			rs.close();
			stmt.close();
		} catch(Exception ex) {
			ex.printStackTrace(System.out);	
		}
		//retList.add("507685");
		System.out.println("getScripList size="+retList.size());
		return retList;
	}
	
	public static void main(String[] args) throws Exception 
    {
		System.out.println("Start-"+new Date());
		//System.setProperty("http.proxyHost", "192.168.200.8");
		//System.setProperty("http.proxyPort", "3128");
		BSEReader aDataReader = new BSEReader();		
		aDataReader.initialise();
		List scripList = aDataReader.getScripList(); //new ArrayList(1);
		//List scripList = new ArrayList(1);
		//scripList.add("900010"); //BSE Sensex
		//scripList.add("532454"); //Bharti Airtel
		//scripList.add("500209"); //Infosys
		for(int i=0;i<scripList.size();i++) {
			String curBseCode = (String)scripList.get(i);
			String curBseName = null;
			if (curBseCode.equals("900010")) curBseName = "BSE30";
			else curBseName = aDataReader.getBseName(curBseCode);
			System.out.print("In main curBseName="+curBseName);
			if (curBseName!=null && curBseName.length()>0){
				System.out.println("Now processing["+i+"]-"+curBseCode);
				int scrip_id = aDataReader.updateScrip(curBseCode,curBseName);
				aDataReader.processHistoricalData(scrip_id,curBseName);
			} else {
				System.out.println("Now skipping "+curBseCode);
			}
		}			
		aDataReader.releaseDb();
		System.out.println("End-"+new Date());
    }
}

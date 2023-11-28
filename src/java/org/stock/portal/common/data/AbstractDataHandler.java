package org.stock.portal.common.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.stock.portal.common.ApplicationConfig;


public class AbstractDataHandler {
	
	private static Logger log = Logger.getLogger(AbstractDataHandler.class.getName());
	
	protected Connection conn=null;
	protected Statement stmt = null;
	
	public void initialise(){
		try {
			//Class.forName(ApplicationConfig.DS_JDBC_DRIVER);
			conn = DriverManager.getConnection(ApplicationConfig.DS_JDBC_URL, ApplicationConfig.DS_JDBC_USERNAME, ApplicationConfig.DS_JDBC_PASSWORD);
			stmt = conn.createStatement();
		} catch(Exception exec) {
			log.error(exec);
		}
	}
	
	public void releaseDb() {
		try {
			log.info("releaseDb called from finalize");
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		} catch(Exception exec) {
			log.error(exec);
		}
	}

	public AbstractDataHandler() {
		super();
		initialise();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		releaseDb();
	}
		
	protected void updateMCCodes(String mcCode,String isinCode,String bCode,String nCode, String compName) {	
		if (mcCode!=null && mcCode.length()>1 && (bCode != null || nCode != null ) ) {
			String sqlString = "";
			try	{
				
				int rowCount = 0;
				if (bCode!=null && bCode.length()>1) {
					sqlString = "update scrips set mc_code='" + mcCode + "'";
					if (nCode != null && nCode.length()>2) {
						sqlString = sqlString + ", nse_code = '" + nCode + "' ";
					}
					if (isinCode != null && isinCode.length()>2) { // money control gives "-" Hyphen when code not found
						sqlString = sqlString + ", isin_code = '" + isinCode + "' ";
					}
					if (compName != null && compName.length()>0) { 
						sqlString = sqlString + ", name = '" + compName + "' ";
					}
					sqlString = sqlString + " where bse_code like '" + bCode + "'";
					
					rowCount = executeSQL(sqlString);					
				} 
				if (rowCount==0 && nCode!=null && nCode.length()>1) {
					sqlString = "update scrips set mc_code='" + mcCode + "' ";
					if (isinCode != null && isinCode.length()>2) {
						sqlString = sqlString + ", isin_code = '" + isinCode + "' ";
					}
					if (bCode != null && bCode.length()>2) {
						sqlString = sqlString + ", bse_code = '" + bCode + "' ";
					}
					if (compName != null && compName.length()>0) { 
						sqlString = sqlString + ", name = '" + compName + "' ";
					}
					sqlString = sqlString +"where nse_code like '" + nCode + "'";
					
					executeSQL(sqlString);	
				}						
							
			} catch(Exception ex) {
				log.error("updateMCCodes: Error mcCode="+ mcCode + " isinCode=" + isinCode + " bCode=" + bCode + " nCode=" + nCode+" with sql\n"+sqlString);
				log.error(ex);
			}	
		}		
	}
	
	protected String updateMCCodeByNSECode(String nseCode) {
		String retCode = "";
		try {
			String curChar=nseCode;			
			URL exchangeLink = new URL("http://www.moneycontrol.com/portfolio_new/search_api.php?type=1&companyname="+ curChar.replaceAll("&", "%26").replaceAll(" ", "+"));			
			BufferedReader in = new BufferedReader(	new InputStreamReader(exchangeLink.openStream()));
			String firstInputLine;			
			while ((firstInputLine = in.readLine()) != null){
				String mcCode = ""; //Moneycontrol code
				String isinCode = ""; //ISIN code
				String bCode = ""; // BSE code 
				String nCode = ""; // NSE code
				String compName = "";
				
				if (firstInputLine.indexOf("javascript:add_stock_m(")!=-1) {
					String firstCodes = firstInputLine.substring(firstInputLine.indexOf("javascript:add_stock_m(")+23);
					firstCodes = firstCodes.substring(0,firstCodes.indexOf(")")-1);
					//System.out.println("firstCodes="+firstCodes);
					String[] codes = firstCodes.split(",");
					if (codes!=null && codes.length>0 && codes[0]!=null && codes[0].length()>0) {
						mcCode = codes[0].replace("'", "");
					}
					//System.out.println("mcCode="+mcCode);
					String secondInputLine;		
					if (mcCode!=null && mcCode.length()>0) {
						secondInputLine = in.readLine();
						if (secondInputLine!=null && secondInputLine.indexOf("b-12")!=-1) {
							secondInputLine = secondInputLine.replace("<strong>","");
							secondInputLine = secondInputLine.replace("</strong>","");
							compName = secondInputLine;
							compName = compName.substring(compName.indexOf("'b-12'")+7);							
							compName = compName.substring(0,compName.indexOf("<span"));
							compName = escapeQuote(compName).trim();
							secondInputLine = secondInputLine.substring(secondInputLine.indexOf("gL_11 PL20")+12);
							secondInputLine = secondInputLine.substring(0,secondInputLine.indexOf("</span>"));
							//System.out.println("secondInputLine="+secondInputLine);
							String[] secCodes = secondInputLine.split(",");
							isinCode = (secCodes[0].substring(secCodes[0].indexOf(":")+1)).trim();
							nCode = (secCodes[1].substring(secCodes[1].indexOf(":")+1)).trim();
							bCode = (secCodes[2].substring(secCodes[2].indexOf(":")+1)).trim();
							retCode = bCode + "~" + nCode;
						}						
					}					
				}	
				updateMCCodes(mcCode,isinCode,bCode,nCode, compName);				
			}
			in.close();		
		} catch(Exception ex) {
			log.error("updateMCCodeByNSECode: Error for " + nseCode); 
			log.error(ex);
		}
		return retCode;
	}
	
	protected String updateETCodeByExchangeCode(String exchangeCode, boolean isBSE){
		String retCode = "";
		try {
			String etCode = null;
			String curChar=exchangeCode;			
			URL exchangeLink = new URL("http://economictimes.indiatimes.com/getcurrentquote1.cms?ticker="+ curChar.replaceAll("&", "%26").replaceAll(" ", "+") );			
			BufferedReader in = new BufferedReader(	new InputStreamReader(exchangeLink.openStream()));
			String inputLine;
			List<String> allPossibleList = new ArrayList<String>();
			while ((inputLine = in.readLine()) != null){
				//System.out.println("Response for curChar ="+curChar + " "+inputLine); //Response : <span companyid="10960">Infosys Ltd</span><br>				
				if (inputLine!=null && inputLine.length()>0 && inputLine.trim().indexOf("companyid")!=-1) {
					etCode =  inputLine.substring(inputLine.trim().indexOf("companyid")+11);					
					etCode = etCode.substring(0,etCode.indexOf(">")-1);		
					allPossibleList.add(etCode);
				}
			}
			in.close();
			
			for(int i=0;i<allPossibleList.size();i++) {
				retCode = updateISINAndOtherCodesFromET(allPossibleList.get(i),isBSE);
			}
		} catch(Exception exec) {
			log.error("updateETCodeByExchangeCode: Error for "+ exchangeCode +" isBSE "+ isBSE);
			log.error(exec);
		}
		return retCode;
	}
	
	protected String updateMCCodeByBSECode(String bseCode) {
		return updateMCCodeByNSECode(bseCode); ///Same code can be used to search Money control
	}	
	
	protected String updateISINAndOtherCodesFromET(String etCode, boolean updateSeries) {
		String retCode = "";
		String sql2Execute = "";
		try {	
			String bCode=null;
			String nCode=null;
			String isinCode=null;
			String seriesType=null;
			
			URL exchangeLink = new URL("http://economictimes.indiatimes.com/infosys-technologies-ltd/stocks/companyid-" + etCode +".cms" );			
			BufferedReader in = new BufferedReader(	new InputStreamReader(exchangeLink.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null){
				//System.out.println(inputLine);		
				if (inputLine!=null && inputLine.length()>0) {
					if (inputLine.trim().indexOf("BSE:")!=-1) {
						String tempLine = inputLine.substring(inputLine.trim().indexOf("BSE:")+7);	
						bCode = tempLine.substring(0,tempLine.indexOf("<"));
					}
					if (inputLine.trim().indexOf("NSE:")!=-1) {
						String tempLine = inputLine.substring(inputLine.trim().indexOf("NSE:")+7);	
						nCode = tempLine.substring(0,tempLine.indexOf("<"));
						seriesType =  nCode.substring(nCode.length()-2);						
						nCode = nCode.substring(0,nCode.length()-2);
						nCode.replaceAll("&amp;", "&");
					}
					if (inputLine.trim().indexOf("ISIN code:")!=-1) {
						String tempLine = inputLine.substring(inputLine.trim().indexOf("ISIN code:")+13);	
						isinCode = tempLine.substring(0,tempLine.indexOf("<"));
					}
				}
			}
			in.close();
			if (etCode!=null && etCode.length()>0) {
				
				sql2Execute = " update scrips set et_code='" + etCode + "'";
//				if (bCode!=null && bCode.length()>0) sql2Execute = sql2Execute + ", bse_code='" + bCode +"' "; 
//				if (nCode!=null && nCode.length()>0) sql2Execute = sql2Execute + ", nse_code='" + nCode +"' "; 
//				if (seriesType!=null && seriesType.length()>0 && updateSeries) sql2Execute = sql2Execute + ", series_type='" + seriesType +"' "; 
//				if (isinCode!=null && isinCode.length()>0) sql2Execute = sql2Execute + ", isin_code='" + isinCode +"' ";
				sql2Execute = sql2Execute + " where et_code like '" + etCode + "' ";
				if (bCode!=null && bCode.length()>0) sql2Execute = sql2Execute + " or bse_code like '" + bCode +"'";
				if (nCode!=null && nCode.length()>0) sql2Execute = sql2Execute + " or nse_code like '" + nCode +"'";
				//System.out.println("In updateISINAndOtherCodesFromET sql2Execute\n"+sql2Execute);
				executeSQL(sql2Execute);
				
				if (bCode!=null && bCode.length()>0) retCode = bCode;
				retCode = retCode +"~";
				if (nCode!=null && nCode.length()>0) retCode = retCode + nCode;
			}
		} catch(Exception exec) {
			log.error("updateISINAndOtherCodesFromET: Error for "+etCode+ " with sql\n" + sql2Execute);
			log.error(exec);
		}
		return retCode;
	}
	
	protected void processNSEAdvStockReachData(String aNseCode) {
		try {
			URL exchangeLink = new URL("https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol="+aNseCode.replaceAll("&", "%26").replaceAll(" ", "+"));
			BufferedReader in = new BufferedReader(	new InputStreamReader(exchangeLink.openStream()));
			
			String seriesType="";
			String isinCode="";
			String companyName="";
			String faceValue="";
			String high52="";
			String highdate="";
			String low52="";
			String lowdate="";
			String change="";
			
			String inputLine = in.readLine();
			while ((inputLine = in.readLine()) != null){				
				if (inputLine.indexOf("{\"futLink\"")>=0) { //Found a scrip
					if (inputLine.indexOf("\"series\"")!=-1) {
						seriesType = inputLine.substring(inputLine.indexOf("\"series\"")+10);	
						seriesType = seriesType.substring(0,2);
					}
					if (inputLine.indexOf("\"isinCode\"")!=-1) {
						isinCode = inputLine.substring(inputLine.indexOf("\"isinCode\"")+12);	
						isinCode = isinCode.substring(0,12);
					}
					if (inputLine.indexOf("\"companyName\"")!=-1) {
						companyName = inputLine.substring(inputLine.indexOf("\"companyName\"")+15);	
						companyName = companyName.substring(0,companyName.indexOf("\""));
					}
					if (inputLine.indexOf("\"faceValue\"")!=-1) {
						faceValue = inputLine.substring(inputLine.indexOf("\"faceValue\"")+13);	
						faceValue = faceValue.substring(0,faceValue.indexOf("\""));
					}
					if (inputLine.indexOf("\"high52\"")!=-1) {
						high52 = inputLine.substring(inputLine.indexOf("\"high52\"")+10);	
						high52 = high52.substring(0,high52.indexOf("\""));
					}
					if (inputLine.indexOf("\"cm_adj_high_dt\"")!=-1) {
						highdate = inputLine.substring(inputLine.indexOf("\"cm_adj_high_dt\"")+18);	
						highdate = highdate.substring(0,highdate.indexOf("\""));
					}
					
					if (inputLine.indexOf("\"low52\"")!=-1) {
						low52 = inputLine.substring(inputLine.indexOf("\"low52\"")+9);	
						low52 = low52.substring(0,low52.indexOf("\""));
					}
					if (inputLine.indexOf("\"cm_adj_low_dt\"")!=-1) {
						lowdate = inputLine.substring(inputLine.indexOf("\"cm_adj_low_dt\"")+17);	
						lowdate = lowdate.substring(0,lowdate.indexOf("\""));
					}
					if (inputLine.indexOf("\"change\"")!=-1) {
						change = inputLine.substring(inputLine.indexOf("\"change\"")+10);	
						change = change.substring(0,change.indexOf("\""));
					}
					populateNSEAdvancedData(aNseCode, seriesType, isinCode, companyName, faceValue, high52, highdate, low52, lowdate, change);
				}
			}			
			in.close();	
		} catch(Exception exec) {	
			log.error("Exception while processAdvStockReachData-"+exec.getMessage());
			log.error(exec);
		}
	}
	
	private void populateNSEAdvancedData(String aNseCode, String seriesType, String isinCode, String companyName, String faceValue, String high52, String highdate, String low52, String lowdate, String change) {
		String sql2Execute = "";
		try {
			sql2Execute = " update scrips set nse_code = '" + aNseCode +"' ";
			
			if (seriesType!=null && seriesType.length()>0) sql2Execute = sql2Execute + ", series_type='" + seriesType +"' ";
			if (isinCode!=null && isinCode.length()>0) sql2Execute = sql2Execute + ", isin_code='" + isinCode +"' ";			
			//if (companyName!=null && companyName.length()>0) sql2Execute = sql2Execute + ", name='" + escapeQuote(companyName) +"' ";			
			if (faceValue!=null && faceValue.length()>0 && !faceValue.startsWith("-")) sql2Execute = sql2Execute + ", face_value='" + faceValue.replaceAll(",", "") +"' ";
			
			if (high52!=null && high52.length()>0) sql2Execute = sql2Execute + ", nse_52week_high='" + high52.replaceAll(",", "") +"' ";
			if (highdate!=null && highdate.trim().length()>2) sql2Execute = sql2Execute + ", nse_52week_high_date='" + getSqlDate(highdate, "-") +"' ";
			if (low52!=null && low52.length()>0) sql2Execute = sql2Execute + ", nse_52week_low='" + low52.replaceAll(",", "") +"' ";
			if (lowdate!=null && lowdate.trim().length()>2) sql2Execute = sql2Execute + ", nse_52week_low_date='" + getSqlDate(lowdate, "-") +"' ";
			
			if (change!=null && change.length()>0) sql2Execute = sql2Execute + ", nse_todays_gain='" + change.replaceAll(",", "") +"' ";
			
			String whereClause = " where nse_code like '" + aNseCode +"' and series_type like '"+seriesType+"'";
			//sql2Execute = sql2Execute + " where nse_code like '" + aNseCode +"' and series_type like '"+seriesType+"'";
			
			//System.out.println("In populateNSEAdvancedData sql2Execute\n"+sql2Execute);
			int updatedRows = executeSQL(sql2Execute+whereClause);
			if (updatedRows==0) {
				whereClause = " where nse_code like '" + aNseCode+"'";
				executeSQL(sql2Execute+whereClause);
			}
			
		} catch(Exception ex) {
			log.error("Exception while update populateNSEAdvancedData-"+ex.getMessage() +"\n for sql "+sql2Execute);
			log.error(ex);
		}
	}
	
	protected String escapeQuote(String inpStr) {		
		String retStr = inpStr.replace("'", "''");
		return retStr;
	}
	
	private String getSqlDate(String bseDateString, String separator) {
		String retStr = "";
		String[] allFields = bseDateString.split(separator);
		if (allFields!=null && allFields.length>2) {
			if (allFields[2].length()>2) retStr = retStr + allFields[2];
			else retStr = retStr + "20" + allFields[2];
			retStr = retStr + "-";		
			if (allFields[1].equalsIgnoreCase("Jan")) retStr = retStr + "01";
			else if (allFields[1].equalsIgnoreCase("Feb")) retStr = retStr + "02";
			else if (allFields[1].equalsIgnoreCase("Mar")) retStr = retStr + "03";
			else if (allFields[1].equalsIgnoreCase("Apr") || allFields[1].equalsIgnoreCase("April")) retStr = retStr + "04";
			else if (allFields[1].equalsIgnoreCase("May")) retStr = retStr + "05";
			else if (allFields[1].equalsIgnoreCase("Jun") || allFields[1].equalsIgnoreCase("June")) retStr = retStr + "06";
			else if (allFields[1].equalsIgnoreCase("Jul") || allFields[1].equalsIgnoreCase("July")) retStr = retStr + "07";
			else if (allFields[1].equalsIgnoreCase("Aug")) retStr = retStr + "08";
			else if (allFields[1].equalsIgnoreCase("Sep")) retStr = retStr + "09";
			else if (allFields[1].equalsIgnoreCase("Oct")) retStr = retStr + "10";
			else if (allFields[1].equalsIgnoreCase("Nov")) retStr = retStr + "11";
			else if (allFields[1].equalsIgnoreCase("Dec")) retStr = retStr + "12";
			retStr = retStr + "-";
			retStr = retStr + allFields[0];
		} else {
			System.out.println("In getSqlDate - cannot process "+bseDateString+" with separator"+separator);
		}
		return retStr;
	}
	
	protected void processBSEAdvStockReachData(String aBseCode) {		
		try {
			URL exchangeLink = new URL("http://www.bseindia.com/bseplus/StockReach/AdvStockReach.aspx?section=tab1&IsPF=undefined&scripcode="+aBseCode.replaceAll("&", "%26").replaceAll(" ", "+"));
			BufferedReader in = new BufferedReader(	new InputStreamReader(exchangeLink.openStream()));
			String strData1 = in.readLine();
			//System.out.println("Response from server = "+strData1);
			if (strData1!=null && strData1.indexOf("#$#")!=-1) {
				String strData = strData1.substring(strData1.indexOf("#$#") + 3);
			
				Map<String, String> bseDataMap = new HashMap<String, String>();
			
				if (strData.indexOf("#SECTION#") != -1) {
			        String[] astr = strData.split("#SECTION#");
			        if (astr.length>0) {
			        	String[] str0 = astr[0].split("#@#"); 		        	
			        	for(int i=0;i<str0.length;i++) {
			        		if (str0[i]!=null && str0[i].length()>0) {		        			
			        			bseDataMap.put("str0.str_"+i, escapeQuote(str0[i].trim()));
			        		}
				        }		        		        	
			        }
			        if (astr.length>1) {
			        	String[] str1 = astr[1].split("#@#");		        	
			        	for(int i=0;i<str1.length;i++) {
			        		if (str1[i]!=null && str1[i].length()>0) {
			        			bseDataMap.put("str1.str_"+i, escapeQuote(str1[i].trim()));
			        		}
				        }		        	
			        }		        
			        if (astr.length>2) {
			        	String[] str2 = astr[2].split("#@#");		        	
			        	for(int i=0;i<str2.length;i++) {
			        		if (str2[i]!=null && str2[i].length()>0) {
			        			bseDataMap.put("str2.str_"+i, escapeQuote(str2[i].trim()));
			        		}
				        }
			        }		        
			        if (astr.length>3) {
			        	String[] str3 = astr[3].split("#@#");		        	
			        	for(int i=0;i<str3.length;i++) {
			        		if (str3[i]!=null && str3[i].length()>0) {
			        			bseDataMap.put("str3.str_"+i, escapeQuote(str3[i].trim()));
			        		}
					    }
			        }		        
			        if (astr.length>4) {		        	
			        	String[] str4 = astr[4].split("#@#");		        	
			        	for(int i=0;i<str4.length;i++) {
			        		if (str4[i]!=null && str4[i].length()>0) {
			        			bseDataMap.put("str4.str_"+i, escapeQuote(str4[i].trim()));
			        		}
				        }
			        }		        
			        if (astr.length>5) {
			        	String[] str5 = astr[5].split("#@#");
			        	for(int i=0;i<str5.length;i++) {
			        		if (str5[i]!=null && str5[i].length()>0) {
			        			bseDataMap.put("str5.str_"+i, escapeQuote(str5[i].trim()));
			        		}
				        }
			        }		        
			        if (astr.length>6) {
			        	String[] str6 = astr[6].split("#@#");
			        	for(int i=0;i<str6.length;i++) {
			        		if (str6[i]!=null && str6[i].length()>0) {
			        			bseDataMap.put("str6.str_"+i, escapeQuote(str6[i].trim()));
			        		}
				        }
			        }		        
			        if (astr.length>7) {
			        	String[] str7 = astr[7].split("#@#");		        	
			        	for(int i=0;i<str7.length;i++) {
			        		if (str7[i]!=null && str7[i].length()>0) {
			        			bseDataMap.put("str7.str_"+i, escapeQuote(str7[i].trim()));
			        		}
				        }
			        }		        
			        if (astr.length>8) {		        
			        	String[] str8 = astr[8].split("#@#");
			        	
			        	for(int i=0;i<str8.length;i++) {
			        		if (str8[i]!=null && str8[i].length()>0) {
			        			bseDataMap.put("str8.str_"+i, escapeQuote(str8[i].trim()));
			        		}
				        }
			        }
			        populateBSEAdvancedData(aBseCode,bseDataMap);
			    } else {
			    	System.out.println("processBSEAdvStockReachData: No reposnse data found");
			    }
			} else {
		    	System.out.println("processBSEAdvStockReachData: No proper reposnse data found");
		    }
			in.close();	
		} catch(Exception exec) {	
			log.error("Exception while processBSEAdvStockReachData-"+exec.getMessage());
			log.error(exec);
		}
	}
	
	private void populateBSEAdvancedData(String aBsecode, Map<String, String> bseDataMap) {
		String sql2Execute = "";
		try {
			sql2Execute = " update scrips set bse_code = '" + aBsecode +"' ";
			
			//if (bseDataMap.get("str0.str_0")!=null) sql2Execute = sql2Execute + ", name='" + bseDataMap.get("str0.str_0") +"' ";
			if (bseDataMap.get("str0.str_3")!=null) sql2Execute = sql2Execute + ", bse_group='" + bseDataMap.get("str0.str_3") +"' ";
			if (bseDataMap.get("str0.str_4")!=null) sql2Execute = sql2Execute + ", face_value='" + bseDataMap.get("str0.str_4").replaceAll(",", "") +"' ";
			if (bseDataMap.get("str0.str_5")!=null) sql2Execute = sql2Execute + ", bse_name='" + bseDataMap.get("str0.str_5") +"' ";
			if (bseDataMap.get("str0.str_6")!=null) sql2Execute = sql2Execute + ", bse_index='" + bseDataMap.get("str0.str_6") +"' ";			
			if (bseDataMap.get("str0.str_7")!=null) sql2Execute = sql2Execute + ", f_sector=(select id from sector where name like '" + bseDataMap.get("str0.str_7") +"') ";
			
			if (bseDataMap.get("str1.str_7")!=null && bseDataMap.get("str1.str_7").indexOf("-")==-1) sql2Execute = sql2Execute + ", bse_todays_gain='" + bseDataMap.get("str1.str_7").replaceAll(",", "") +"' ";			
			if (bseDataMap.get("str4.str_4")!=null) sql2Execute = sql2Execute + ", bse_52week_high='" + bseDataMap.get("str4.str_4").replaceAll(",", "").replaceAll("-", "0") +"' ";
			if (bseDataMap.get("str4.str_5")!=null) sql2Execute = sql2Execute + ", bse_52week_low='" + bseDataMap.get("str4.str_5").replaceAll(",", "").replaceAll("-", "0") +"' ";
			if (bseDataMap.get("str4.str_6")!=null && bseDataMap.get("str4.str_6").trim().length()>2) sql2Execute = sql2Execute + ", bse_52week_high_date='" + getSqlDate(bseDataMap.get("str4.str_6")," ") +"' ";
			if (bseDataMap.get("str4.str_7")!=null && bseDataMap.get("str4.str_7").trim().length()>2) sql2Execute = sql2Execute + ", bse_52week_low_date='" + getSqlDate(bseDataMap.get("str4.str_7")," ") +"' ";
								
			sql2Execute = sql2Execute + " where bse_code like '" + aBsecode +"'";
			
			//System.out.println("In populateBSEAdvancedData sql2Execute=\n"+sql2Execute);
			executeSQL(sql2Execute);
			
		} catch(Exception ex) {
			log.error("Exception while update-"+ex.getMessage()+"\nfor sql "+sql2Execute);
			log.error(ex);
		}
	}
	
	protected String convertDate(String inputStr,String fromFormat,String toFormat) { // Formats should foloow notation yyyy for year MM for month dd for date HH for our mm for minute ss for second
		String retString = inputStr.replaceAll("x", "");
		SimpleDateFormat inFormat = new SimpleDateFormat(fromFormat);
		SimpleDateFormat outFormat = new SimpleDateFormat(toFormat);
		try {
			retString = outFormat.format(inFormat.parse(retString));
		}catch(Exception ex) {
			log.error(ex);
		}				
		return retString;
	}
	
	protected Long getScripByNseCode(String nseCode) {
		Long retVal = null;
		try {
			
			ResultSet rs = stmt.executeQuery("select id from scrips where nse_code like '"+nseCode+"'");			
			while (rs.next()) {
				retVal = rs.getLong("id");
				break;
			}
			rs.close();
			
		}catch(Exception ex) {
			log.error(ex);
		}
		return retVal;
	}
	
	protected Long getScripByNseCode(String nseCode, String seriesType, boolean useEmptySeries) {
		Long retVal = null;
		try {
			
			ResultSet rs = stmt.executeQuery("select id from scrips where nse_code like '"+nseCode+"' and series_type like '" + seriesType + "'" );			
			while (rs.next()) {
				retVal = rs.getLong("id");
				break;
			}
			rs.close();
			if (retVal==null && useEmptySeries==true) {
				rs = stmt.executeQuery("select id, series_type from scrips where nse_code like '"+nseCode+"'" );			
				while (rs.next()) {
					String exsitingEmptySeries = rs.getString("series_type");
					if (exsitingEmptySeries==null || exsitingEmptySeries.trim().length()==0) {
						retVal = rs.getLong("id");
						executeSQL("update scrips set series_type='"+seriesType+"' where id="+retVal);
						break;
					}					
				}
				rs.close();
			}
		}catch(Exception ex) {
			log.error(ex);
		}
		return retVal;
	}
	
	protected int executeSQL(String sqlString) throws SQLException {
		log.info("In AbstractDataHandler sqlString="+sqlString);		
		int retCount = stmt.executeUpdate(sqlString);	
		log.debug("return count="+retCount);
		return retCount;
	}
	
	
	protected String getBseNameFromBSESite(String bseCode) {
		String retVal =null;
		try {
			URL exchangeLink = new URL("http://charting.bseindia.com/charting/scripcode_search.asp?scripcode="+ bseCode +"&category=0&duration=0");			
			BufferedReader in = new BufferedReader(	new InputStreamReader(exchangeLink.openStream()));
			String inputLine;			
			while ((inputLine = in.readLine()) != null){
				//System.out.print("inputLine="+inputLine);
				if (inputLine!=null && inputLine.length()>0 && inputLine.trim().indexOf(" ")==-1 && !inputLine.contains("No Records Found")) {
					retVal = inputLine;
				}
				//System.out.print("In getBseName retVal="+retVal);
			}
			in.close();	
		} catch(Exception exec) {
			log.error(exec);
		}
		return retVal;
	}
	
	private String getFormattedDate(String dateStr) {
		String retVal = "";
		retVal = dateStr.substring(6)+"-"+dateStr.substring(0,2) + "-" + dateStr.substring(3,5);		
		return retVal;
	}
	
	private String getEndDate() {
		String retString="";
		SimpleDateFormat outFormat = new SimpleDateFormat("MM-dd-yyyy");
		try {
			retString = outFormat.format(new Date());
		}catch(Exception ex) {
			log.error(ex);
		}
		return retString;
	}
	
	protected int updateBSEHistoricalData(int scripId,String bseName, Date fromDate, Date toDate) {
		//System.out.println("In processHistoricalData scripId="+scripId+" bseName="+bseName);
		int returnCount = 0;
		int skipCount = 0;
		String sql2Execute="";
		try {
			String fromDateStr = null;
			String toDateStr = null;
			if (fromDate!=null) fromDateStr = (new SimpleDateFormat("MM-dd-yyyy")).format(fromDate); else fromDateStr = "01-01-2001";
			if (toDate!=null) toDateStr = (new SimpleDateFormat("MM-dd-yyyy")).format(toDate); else toDateStr = getEndDate();

			String url2Use = "http://charting.bseindia.com/charting/history.asp?SYMBOL=" + bseName.replaceAll("&", "%26").replaceAll(" ", "+") + "&period=days&ENDDATE="+toDateStr+"&STARTDATE="+fromDateStr+"&CHARTTYPE=0";
			System.out.println("processHistoricalData-url2Use="+url2Use);
			URL exchangeLink = new URL(url2Use);
			BufferedReader in = new BufferedReader(	new InputStreamReader(exchangeLink.openStream()));
			String inputLine;	
			int initialCount=0;
			String previousClose = "0";			
			while ((inputLine = in.readLine()) != null){
				initialCount++;	
				if (initialCount==4) {
					// Todo update name of the scrip.
				}
				if (inputLine!=null && inputLine.length()>0 && initialCount>4 && inputLine.startsWith("History")) {
					String[] dataValues=(inputLine.substring(8)).split(",");
					String dateString = dataValues[0];
					//System.out.println("dateString="+dateString);
					String open = dataValues[1]; if (open.trim().length()==0) open="0";
					String high = dataValues[2]; if (high.trim().length()==0) high="0";
					String low = dataValues[3]; if (low.trim().length()==0) low="0";
					String close = dataValues[4]; if (close.trim().length()==0) close="0";
					String volume = dataValues[5]; if (volume.trim().length()==0) volume="0";
					if (!open.equals("0")) {
						String eodInsertSql = "insert into bse_eq_eod_data (id, data_date,f_scrip,open_price,high_price,low_price,close_price,volume,previous_close) "
							+ " values((select nextval('bse_eq_eod_data_id_seq')), '"+ getFormattedDate(dateString) + "'," + scripId + "," 
							+ open + "," + high +"," + low + "," + close + "," + volume + "," + previousClose +")";
						//System.out.println("In processHistoricalData eodInsertSql="+eodInsertSql);
						sql2Execute=eodInsertSql ;						
						stmt.execute(eodInsertSql);						
						returnCount++;
						previousClose = close;
					} else {
						skipCount++;
					}
				}				
			}
			in.close();	
		} catch(Exception exec) {
			log.error("Error when exec "+sql2Execute);
			log.error(exec);
		}
		System.out.println("Inserted for "+bseName+" record count="+returnCount+" skipCount="+skipCount);
		return returnCount;
	}
	
	protected int updateBSENameForScrip(String bseCode, String bseName) {
		int retVal =0;
		try	{
			String updateSqlStr = "update scrips set bse_name = '" + bseName + "' where bse_code like '" +bseCode +"'";
			executeSQL(updateSqlStr);
			String sqlString = "select id from scrips where bse_code like '" +bseCode +"'";
			ResultSet rs = stmt.executeQuery(sqlString);
			int dupCount = 0;
			while(rs.next()) {
				retVal = rs.getInt("id");
				dupCount++;
			}
			rs.close();
			if (dupCount>1) {
				retVal = 0; 
			}
		} catch(Exception ex) {
			log.error(ex);
		}		
		return retVal;
	}
	
	protected void processBSEHistoricalData(String bseCode) {
		String curBseName = getBseNameFromBSESite(bseCode.trim());
		System.out.println("processBSEHistoricalData for "+bseCode);
		if (curBseName!=null && curBseName.trim().length()>0){
			int scrip_id = updateBSENameForScrip(bseCode.trim(),curBseName.trim());
			if (scrip_id!=0) {
				updateBSEHistoricalData(scrip_id,curBseName.trim(), null, null);
			} else {
				System.out.println("Error? - Skipping (Duplicate?)-"+curBseName);
			}
		} else {
			System.out.println("Error? - bsename null?");
		}
	}
	
	protected int getScripByBSECode(String bseCode) {
		int retVal =0;
		try	{
			String sqlString = "select id from scrips where bse_code like '" +bseCode +"'";
			ResultSet rs = stmt.executeQuery(sqlString);
			while(rs.next()) {
				retVal = rs.getInt("id");
				break;
			}
			rs.close();
		} catch(Exception ex) {
			log.error(ex);
		}		
		return retVal;
	}
}

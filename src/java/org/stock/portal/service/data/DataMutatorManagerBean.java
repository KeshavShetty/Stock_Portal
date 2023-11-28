package org.stock.portal.service.data;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.dao.DataDao;
import org.stock.portal.dao.ScripDao;
import org.stock.portal.web.util.Constants;

@Stateless(name="DataMutatorManager", mappedName="org.stock.portal.service.data.DataMutatorManager")
public class DataMutatorManagerBean implements DataMutatorManager {
	@Resource 
	private SessionContext context;
	
	@PersistenceContext(unitName = Constants.ProjectConfig.PERSISTENCE_UNIT)
    private EntityManager entityManager;

	
	private static Logger log = Logger.getLogger(DailyProcessorManager.class.getName());
	
	@TransactionAttribute( TransactionAttributeType.REQUIRED )
	public void insertEodData(Map<String, Object> dataMap) throws BusinessException {
		//log.info("In DataMutatorManagerBean - Inserting EOD data for ExChG="+dataMap.get("exchangeCode")+" dataDate="+dataMap.get("dataDate"));
		if (dataMap.get("exchangeCode").equals("BSE")) {
			insertBSEEODData(dataMap);
		} else {// NSE data
			insertNSEEODData(dataMap);
		}
	}
	
	private int executeNativeSQL(String nativeSql) {
		log.info("In executeNativeSQL nativeSql="+nativeSql);
		Query query = entityManager.createNativeQuery( nativeSql);
		return query.executeUpdate();
	}
	
	private void insertBSEEODData(Map<String, Object> dataMap) throws BusinessException {
		ScripDao scrpDao = new ScripDao(entityManager);
		Long scripId = scrpDao.getScripByBseCode((String)dataMap.get("bseCode"));
		if (scripId==null) {
			//Create new scrip here from BSE data
			scripId = createNewScripByBSECode((String)dataMap.get("bseCode"), (String)dataMap.get("tempScripName"), (String)dataMap.get("scGroup"));
		}
		if (scripId!=null) {
			String dataInsertSql = " INSERT INTO bse_eq_eod_data (id,f_scrip,data_date,open_price,high_price,low_price,close_price,volume,previous_close, previous_volume) " +
			" values(nextval('bse_eq_eod_data_id_seq')," + scripId + ",TIMESTAMP '" + (String)dataMap.get("dataDate") + "'," + (String)dataMap.get("openPrice") + "," + (String)dataMap.get("highPrice") + "," + (String)dataMap.get("lowPrice") + "," + (String)dataMap.get("closePrice") + "," + (String)dataMap.get("volume") + "," + (String)dataMap.get("prevClosePrice") + ",(select volume from bse_eq_eod_data where f_scrip="+scripId+" and data_date = (select max(data_date) from bse_eq_eod_data where f_scrip="+scripId+"))"+ ")";
			executeNativeSQL(dataInsertSql);
			
			executeNativeSQL("update scrips set bse_previous_close = bse_cmp, bse_previous_volume=bse_todays_volume where id="+scripId);
			executeNativeSQL("update scrips set bse_todays_volume = " + (String)dataMap.get("volume") + " where id="+scripId);
			executeNativeSQL("update scrips set status='Active',bse_cmp="+(String)dataMap.get("closePrice")+", last_updated = '"+(String)dataMap.get("dataDate")+"' where id="+scripId);
			executeNativeSQL("update scrips set bse_52week_high = " + (String)dataMap.get("highPrice") + ", bse_52week_high_date='" + (String)dataMap.get("dataDate") +"' where id="+scripId + " and bse_52week_high<="+(String)dataMap.get("highPrice"));
			executeNativeSQL("update scrips set bse_52week_low = " + (String)dataMap.get("lowPrice") + ", bse_52week_low_date='" + (String)dataMap.get("dataDate") +"' where id="+(String)dataMap.get("scripId") + " and bse_52week_low>="+(String)dataMap.get("lowPrice"));
			executeNativeSQL("update scrips set bse_todays_gain = bse_cmp - bse_previous_close where id="+scripId);
			executeNativeSQL("update scrips set pe = bse_cmp/eps_ttm where id="+scripId+" and eps_ttm!=0");
			executeNativeSQL("update scrips scr set bse_avg_volume=(select avg(volume) from bse_eq_eod_data where data_date<= (current_date) and data_date>=(current_date - interval '14 day') and f_scrip=scr.id) where scr.id="+ scripId );
			
		} else {
			log.debug("~~~~~~~~~~~~~~~~~~~DataMutatorManager::insertBSEEODData()~~~~~~~~~~~~~~~~~~~No Scrip found for bseCode "+(String)dataMap.get("bseCode"));
		}
	}

	private void insertNSEEODData(Map<String, Object> dataMap) throws BusinessException {
		ScripDao scrpDao = new ScripDao(entityManager);
		Long scripId = scrpDao.getScripByNseCodeOrSeries((String)dataMap.get("nseCode"),(String)dataMap.get("seriesType"));		
		if (scripId==null) {
			// Create new scrip here from NSE data
			scripId = createNewScripByNSECode((String)dataMap.get("nseCode"), (String)dataMap.get("seriesType"), (String)dataMap.get("isinCode"));
		}
		if (scripId!=null) {
			String dataInsertSql = " INSERT INTO nse_eq_eod_data (id,f_scrip,data_date,open_price,high_price,low_price,close_price,volume,previous_close,previous_volume) " +
			" values(nextval('nse_eq_eod_data_id_seq')," + scripId + ",TIMESTAMP '" + (String)dataMap.get("dataDate") + "'," + (String)dataMap.get("openPrice") + "," + (String)dataMap.get("highPrice") + "," + (String)dataMap.get("lowPrice") + "," + (String)dataMap.get("closePrice") + "," + (String)dataMap.get("volume") + "," + (String)dataMap.get("prevClosePrice") + ",(select volume from nse_eq_eod_data where f_scrip="+scripId+" and data_date = (select max(data_date) from nse_eq_eod_data where f_scrip="+scripId+"))"+ ")";
						
			executeNativeSQL(dataInsertSql);
						
			executeNativeSQL("update scrips set nse_previous_close = nse_cmp where id="+scripId);
			executeNativeSQL("update scrips set status='Active',nse_cmp="+(String)dataMap.get("closePrice")+", last_updated = '"+(String)dataMap.get("dataDate")+"' where id="+scripId);
			executeNativeSQL("update scrips set nse_52week_high = " + (String)dataMap.get("highPrice") + ", nse_52week_high_date='" + (String)dataMap.get("dataDate") +"' where id="+scripId + " and nse_52week_high<="+(String)dataMap.get("highPrice"));
			executeNativeSQL("update scrips set nse_52week_low = " + (String)dataMap.get("lowPrice") + ", nse_52week_low_date='" + (String)dataMap.get("dataDate") +"' where id="+scripId + " and nse_52week_low>="+(String)dataMap.get("lowPrice"));
			executeNativeSQL("update scrips set nse_todays_gain = nse_cmp - nse_previous_close where id="+scripId);
		} else {
			log.debug("~~~~~~~~~~~~~~~~~~~DataMutatorManager::insertNSEEODData()~~~~~~~~~~~~~~~~~~~No Scrip found for nseCode "+(String)dataMap.get("nseCode"));
		}
	}
	
	private Long createNewScripByNSECode(String nseCode,String seriesType,String isinCode) throws BusinessException {
		log.debug((new Date()) + "In createNewScrip nseCode="+ nseCode + " seriesType="+seriesType+ " isinCode="+isinCode);
		//Create new scrip here
		try {			
			
			String sql2Execute = "insert into scrips (id, name, nse_code,series_type, isin_code, date_added, symbol_type)  values(nextval('scrips_id_seq'),'" + nseCode + "','" + nseCode + "','" + seriesType +"','"+ isinCode + "', now(),'EQ')";
			if (isinCode==null) {
				sql2Execute = "insert into scrips (id, name, nse_code,series_type, date_added, symbol_type)  values(nextval('scrips_id_seq'),'" + nseCode + "','" + nseCode + "','" + seriesType +"', now(),'EQ')";
			}
			//log.debug("createNewScrip sql\n"+ sql2Execute);
			executeNativeSQL(sql2Execute);			
				
			String returnedExchangeCodes1 = updateMCCodeByNSECode(nseCode);
			String returnedExchangeCodes2 = updateETCodeByExchangeCode(nseCode, false);
			
			log.debug("In Create NSE scrip - returnedExchangeCodes1="+returnedExchangeCodes1+" returnedExchangeCodes2="+returnedExchangeCodes2);			
			processNSEAdvStockReachData(nseCode);
			
			//Process NSE and NSE advanced stock reach - Begins
			String firstBseCode = ""; String secondBseCode = ""; String firstNseCode = ""; String secondNseCode = "";
			String[] firstCodes = returnedExchangeCodes1.split("~");
			if (firstCodes!=null && firstCodes.length>=1) {
				firstBseCode = firstCodes[0].trim();
				if (firstCodes.length>1) firstNseCode = firstCodes[1].trim();
			}
			String[] secondCodes = returnedExchangeCodes2.split("~");
			if (secondCodes!=null && secondCodes.length>=1) {
				secondBseCode = secondCodes[0].trim();
				if (secondCodes.length>1) secondNseCode = secondCodes[1].trim();
			}
			log.debug("firstBseCode-"+firstBseCode+" secondBseCode="+secondBseCode+" firstNseCode="+ firstNseCode +" secondNseCode="+secondNseCode);
			firstBseCode = firstBseCode.replaceAll("-", "");
			secondBseCode = secondBseCode.replaceAll("-", "");
			firstNseCode = firstNseCode.replaceAll("-", "");
			secondNseCode = secondNseCode.replaceAll("-", "");
			
			if (firstBseCode.length()>0) processBSEAdvStockReachData(firstBseCode);
			if (secondBseCode.length()>0 && !secondBseCode.equals(firstBseCode)) processBSEAdvStockReachData(secondBseCode);
			
			if (firstNseCode.length()>0 && !nseCode.equals(firstNseCode)) processNSEAdvStockReachData(firstNseCode);
			if (secondNseCode.length()>0 && !secondNseCode.equals(firstNseCode) && !nseCode.equals(secondNseCode)) processNSEAdvStockReachData(secondNseCode);
			//Process NSE and NSE advanced stock reach - ends
		} catch(Exception ex) {
			log.error("Error while inserting new NSE code "+ nseCode + " seriesType="+seriesType+ " isinCode="+isinCode);
			log.error("Error "+ ex);
		}
		return (new ScripDao(entityManager)).getScripByNseCodeOrSeries(nseCode,seriesType);
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
					//log.debug("firstCodes="+firstCodes);
					String[] codes = firstCodes.split(",");
					if (codes!=null && codes.length>0 && codes[0]!=null && codes[0].length()>0) {
						mcCode = codes[0].replace("'", "");
					}
					//log.debug("mcCode="+mcCode);
					String secondInputLine;		
					if (mcCode!=null && mcCode.length()>0) {
						secondInputLine = in.readLine();
						if (secondInputLine!=null && secondInputLine.indexOf("b-12")!=-1) {
							secondInputLine = secondInputLine.replace("<strong>","");
							secondInputLine = secondInputLine.replace("</strong>","");
							compName = secondInputLine;
							compName = compName.substring(compName.indexOf("'b-12'")+7);							
							compName = compName.substring(0,compName.indexOf("<span"));
							compName = SPConstants.escapeQuote(compName).trim();
							secondInputLine = secondInputLine.substring(secondInputLine.indexOf("gL_11 PL20")+12);
							secondInputLine = secondInputLine.substring(0,secondInputLine.indexOf("</span>"));
							//log.debug("secondInputLine="+secondInputLine);
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
			log.debug("updateMCCodeByNSECode: Error for " + nseCode); 
			log.error(ex);
		}
		return retCode;
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
					
					rowCount = executeNativeSQL(sqlString);					
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
					
					executeNativeSQL(sqlString);	
				}						
							
			} catch(Exception ex) {
				log.debug("updateMCCodes: Error mcCode="+ mcCode + " isinCode=" + isinCode + " bCode=" + bCode + " nCode=" + nCode+" with sql\n"+sqlString);
				log.error(ex);
			}	
		}		
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
				//log.debug("Response for curChar ="+curChar + " "+inputLine); //Response : <span companyid="10960">Infosys Ltd</span><br>				
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
			log.debug("updateETCodeByExchangeCode: Error for "+ exchangeCode +" isBSE "+ isBSE);
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
			log.debug("Exception while processAdvStockReachData-"+exec.getMessage());
			log.error(exec);
		}
	}
	
	protected void processBSEAdvStockReachData(String aBseCode) {		
		try {
			URL exchangeLink = new URL("http://www.bseindia.com/bseplus/StockReach/AdvStockReach.aspx?section=tab1&IsPF=undefined&scripcode="+aBseCode.replaceAll("&", "%26").replaceAll(" ", "+"));
			BufferedReader in = new BufferedReader(	new InputStreamReader(exchangeLink.openStream()));
			String strData1 = in.readLine();
			//log.debug("Response from server = "+strData1);
			if (strData1!=null && strData1.indexOf("#$#")!=-1) {
				String strData = strData1.substring(strData1.indexOf("#$#") + 3);
			
				Map<String, String> bseDataMap = new HashMap<String, String>();
			
				if (strData.indexOf("#SECTION#") != -1) {
			        String[] astr = strData.split("#SECTION#");
			        if (astr.length>0) {
			        	String[] str0 = astr[0].split("#@#"); 		        	
			        	for(int i=0;i<str0.length;i++) {
			        		if (str0[i]!=null && str0[i].length()>0) {		        			
			        			bseDataMap.put("str0.str_"+i, SPConstants.escapeQuote(str0[i].trim()));
			        		}
				        }		        		        	
			        }
			        if (astr.length>1) {
			        	String[] str1 = astr[1].split("#@#");		        	
			        	for(int i=0;i<str1.length;i++) {
			        		if (str1[i]!=null && str1[i].length()>0) {
			        			bseDataMap.put("str1.str_"+i, SPConstants.escapeQuote(str1[i].trim()));
			        		}
				        }		        	
			        }		        
			        if (astr.length>2) {
			        	String[] str2 = astr[2].split("#@#");		        	
			        	for(int i=0;i<str2.length;i++) {
			        		if (str2[i]!=null && str2[i].length()>0) {
			        			bseDataMap.put("str2.str_"+i, SPConstants.escapeQuote(str2[i].trim()));
			        		}
				        }
			        }		        
			        if (astr.length>3) {
			        	String[] str3 = astr[3].split("#@#");		        	
			        	for(int i=0;i<str3.length;i++) {
			        		if (str3[i]!=null && str3[i].length()>0) {
			        			bseDataMap.put("str3.str_"+i, SPConstants.escapeQuote(str3[i].trim()));
			        		}
					    }
			        }		        
			        if (astr.length>4) {		        	
			        	String[] str4 = astr[4].split("#@#");		        	
			        	for(int i=0;i<str4.length;i++) {
			        		if (str4[i]!=null && str4[i].length()>0) {
			        			bseDataMap.put("str4.str_"+i, SPConstants.escapeQuote(str4[i].trim()));
			        		}
				        }
			        }		        
			        if (astr.length>5) {
			        	String[] str5 = astr[5].split("#@#");
			        	for(int i=0;i<str5.length;i++) {
			        		if (str5[i]!=null && str5[i].length()>0) {
			        			bseDataMap.put("str5.str_"+i, SPConstants.escapeQuote(str5[i].trim()));
			        		}
				        }
			        }		        
			        if (astr.length>6) {
			        	String[] str6 = astr[6].split("#@#");
			        	for(int i=0;i<str6.length;i++) {
			        		if (str6[i]!=null && str6[i].length()>0) {
			        			bseDataMap.put("str6.str_"+i, SPConstants.escapeQuote(str6[i].trim()));
			        		}
				        }
			        }		        
			        if (astr.length>7) {
			        	String[] str7 = astr[7].split("#@#");		        	
			        	for(int i=0;i<str7.length;i++) {
			        		if (str7[i]!=null && str7[i].length()>0) {
			        			bseDataMap.put("str7.str_"+i, SPConstants.escapeQuote(str7[i].trim()));
			        		}
				        }
			        }		        
			        if (astr.length>8) {		        
			        	String[] str8 = astr[8].split("#@#");
			        	
			        	for(int i=0;i<str8.length;i++) {
			        		if (str8[i]!=null && str8[i].length()>0) {
			        			bseDataMap.put("str8.str_"+i, SPConstants.escapeQuote(str8[i].trim()));
			        		}
				        }
			        }
			        populateBSEAdvancedData(aBseCode,bseDataMap);
			    } else {
			    	log.debug("processBSEAdvStockReachData: No reposnse data found");
			    }
			} else {
		    	log.debug("processBSEAdvStockReachData: No proper reposnse data found");
		    }
			in.close();	
		} catch(Exception exec) {	
			log.debug("Exception while processBSEAdvStockReachData-"+exec.getMessage());
			log.error(exec);
		}
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
				//log.debug(inputLine);		
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
				//log.debug("In updateISINAndOtherCodesFromET sql2Execute\n"+sql2Execute);
				executeNativeSQL(sql2Execute);
				
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
			
			//log.debug("In populateNSEAdvancedData sql2Execute\n"+sql2Execute);
			int updatedRows = executeNativeSQL(sql2Execute+whereClause);
			if (updatedRows==0) {
				whereClause = " where nse_code like '" + aNseCode+"'";
				executeNativeSQL(sql2Execute+whereClause);
			}
			
		} catch(Exception ex) {
			log.debug("Exception while update populateNSEAdvancedData-"+ex.getMessage() +"\n for sql "+sql2Execute);
			log.error(ex);
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
			
			//log.debug("In populateBSEAdvancedData sql2Execute=\n"+sql2Execute);
			executeNativeSQL(sql2Execute);
			
		} catch(Exception ex) {
			log.debug("Exception while update-"+ex.getMessage()+"\nfor sql "+sql2Execute);
			log.error(ex);
		}
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
			log.debug("In getSqlDate - cannot process "+bseDateString+" with separator"+separator);
		}
		return retStr;
	}
	
	private Long createNewScripByBSECode(String bseCode, String tempScripName, String scGroup) throws BusinessException {
		//log.debug("In createNewScrip bseCode="+ bseCode + " tempScripName="+tempScripName+ " scGroup="+scGroup);
		//Create new scrip here
		String sql2Execute = "";
		try {			
			
			sql2Execute = "insert into scrips (id, name, bse_code,bse_group, date_added, symbol_type)  values(nextval('scrips_id_seq'),'" + SPConstants.escapeQuote(tempScripName) + "','" + bseCode + "','" + scGroup + "', now(),'EQ')";
			//log.debug(sql2Execute);
			executeNativeSQL(sql2Execute );			
				
			String returnedExchangeCodes1 = updateMCCodeByBSECode(bseCode); //returnedExchangeCodes will have the format bsecode~nsecode
			String returnedExchangeCodes2 = updateETCodeByExchangeCode(bseCode, true);			
			processBSEAdvStockReachData(bseCode);
			
			//Process NSE and NSE advanced stock reach - Begins
			String firstBseCode = ""; String secondBseCode = ""; String firstNseCode = ""; String secondNseCode = "";
			String[] firstCodes = returnedExchangeCodes1.split("~");
			if (firstCodes!=null && firstCodes.length>=1) {
				firstBseCode = firstCodes[0].trim();
				if (firstCodes.length>1) firstNseCode = firstCodes[1].trim();
			}
			String[] secondCodes = returnedExchangeCodes2.split("~");
			if (secondCodes!=null && secondCodes.length>=1) {
				secondBseCode = secondCodes[0].trim();
				if (secondCodes.length>1) secondNseCode = secondCodes[1].trim();
			}
			log.debug("firstBseCode-"+firstBseCode+" secondBseCode="+secondBseCode+" firstNseCode="+ firstNseCode +" secondNseCode="+secondNseCode);
			firstBseCode = firstBseCode.replaceAll("-", "");
			secondBseCode = secondBseCode.replaceAll("-", "");
			firstNseCode = firstNseCode.replaceAll("-", "");
			secondNseCode = secondNseCode.replaceAll("-", "");
			
			if (firstBseCode.length()>0 && !firstBseCode.equals(bseCode)) processBSEAdvStockReachData(firstBseCode);
			if (secondBseCode.length()>0 && !secondBseCode.equals(firstBseCode) && !secondBseCode.equals(bseCode)) processBSEAdvStockReachData(secondBseCode);
			
			if (firstNseCode.length()>0) processNSEAdvStockReachData(firstNseCode);
			if (secondNseCode.length()>0 && !secondNseCode.equals(firstNseCode)) processNSEAdvStockReachData(secondNseCode);
			//Process NSE and NSE advanced stock reach - ends
			processBSEHistoricalData(bseCode); // Update till date data from BSE charting site 
		} catch(Exception ex) {
			log.debug("createNewScrip: error with sql "+sql2Execute);
			log.error(ex);
		}
		return new ScripDao(entityManager).getScripByBseCode(bseCode);
	}
	
	protected String updateMCCodeByBSECode(String bseCode) {
		return updateMCCodeByNSECode(bseCode); ///Same code can be used to search Money control
	}
	
	protected void processBSEHistoricalData(String bseCode) {
		String curBseName = getBseNameFromBSESite(bseCode.trim());
		log.debug("processBSEHistoricalData for "+bseCode);
		if (curBseName!=null && curBseName.trim().length()>0){
			Long scrip_id = updateBSENameForScrip(bseCode.trim(),curBseName.trim());
			if (scrip_id!=0) {
				updateBSEHistoricalData(scrip_id,curBseName.trim(), null, null);
			} else {
				log.debug("Error? - Skipping (Duplicate?)-"+curBseName);
			}
		} else {
			log.debug("Error? - bsename null?");
		}
	}
	
	protected String getBseNameFromBSESite(String bseCode) {
		String retVal =null;
		try {
			URL exchangeLink = new URL("http://www.bseindia.com/SiteCache/90D/GetQuoteData.aspx?Type=EQ&text="+ bseCode);			
			BufferedReader in = new BufferedReader(	new InputStreamReader(exchangeLink.openStream()));
			String inputLine;			
			while ((inputLine = in.readLine()) != null){
				//System.out.print("inputLine="+inputLine);
				if (inputLine!=null && inputLine.length()>0 && inputLine.trim().indexOf("<span class='leftspan'>")>0 && !inputLine.contains("No Match found")) {
					String partialString = inputLine.substring(inputLine.trim().indexOf("<span class='leftspan'>"));
					partialString = partialString.substring(0,inputLine.trim().indexOf("</span>"));
					retVal = partialString;
				}
				//System.out.print("In getBseName retVal="+retVal);
			}
			in.close();	
			log.info("In getBseNameFromBSESite for BSECode:" + bseCode + " retVal="+retVal);
		} catch(Exception exec) {
			log.error(exec);
		}		
		return retVal;
	}
	
	protected Long updateBSENameForScrip(String bseCode, String bseName) {
		Long retVal =0L;
		try	{
			String updateSqlStr = "update scrips set bse_name = '" + bseName + "' where bse_code like '" +bseCode +"'";
			executeNativeSQL(updateSqlStr);
			
			Long scripId = new ScripDao(entityManager).getScripByBseCode(bseCode);
			
			retVal = scripId; 
		} catch(Exception ex) {
			retVal = 0L; 
			log.error(ex);
		}		
		return retVal;
	}
	
	protected int updateBSEHistoricalData(Long scripId,String bseName, Date fromDate, Date toDate) {
		//log.debug("In processHistoricalData scripId="+scripId+" bseName="+bseName);
		int returnCount = 0;
		int skipCount = 0;
		String sql2Execute="";
		try {
			String fromDateStr = null;
			String toDateStr = null;
			if (fromDate!=null) fromDateStr = (new SimpleDateFormat("MM-dd-yyyy")).format(fromDate); else fromDateStr = "01-01-2001";
			if (toDate!=null) toDateStr = (new SimpleDateFormat("MM-dd-yyyy")).format(toDate); else toDateStr = getEndDate();

			String url2Use = "http://charting.bseindia.com/charting/history.asp?SYMBOL=" + bseName.replaceAll("&", "%26").replaceAll(" ", "+") + "&period=days&ENDDATE="+toDateStr+"&STARTDATE="+fromDateStr+"&CHARTTYPE=0";
			log.debug("processHistoricalData-url2Use="+url2Use);
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
					//log.debug("dateString="+dateString);
					String open = dataValues[1]; if (open.trim().length()==0) open="0";
					String high = dataValues[2]; if (high.trim().length()==0) high="0";
					String low = dataValues[3]; if (low.trim().length()==0) low="0";
					String close = dataValues[4]; if (close.trim().length()==0) close="0";
					String volume = dataValues[5]; if (volume.trim().length()==0) volume="0";
					if (!open.equals("0")) {
						String eodInsertSql = "insert into bse_eq_eod_data (id, data_date,f_scrip,open_price,high_price,low_price,close_price,volume,previous_close,previous_volume) "
							+ " values((select nextval('bse_eq_eod_data_id_seq')), '"+ getFormattedDate(dateString) + "'," + scripId + "," 
							+ open + "," + high +"," + low + "," + close + "," + volume + "," + previousClose + ",(select volume from bse_eq_eod_data where f_scrip="+scripId+" and data_date = (select max(data_date) from bse_eq_eod_data where f_scrip="+scripId+"))" +")";
						//log.debug("In processHistoricalData eodInsertSql="+eodInsertSql);
						sql2Execute=eodInsertSql ;						
						executeNativeSQL(eodInsertSql);						
						returnCount++;
						previousClose = close;
					} else {
						skipCount++;
					}
				}				
			}
			in.close();	
		} catch(Exception exec) {
			log.debug("Error when exec "+sql2Execute);
			log.error(exec);
		}
		log.debug("Inserted for "+bseName+" record count="+returnCount+" skipCount="+skipCount);
		return returnCount;
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
	
	private String getFormattedDate(String dateStr) {
		String retVal = "";
		retVal = dateStr.substring(6)+"-"+dateStr.substring(0,2) + "-" + dateStr.substring(3,5);		
		return retVal;
	}
	
	@TransactionAttribute( TransactionAttributeType.REQUIRED )
	public void populateTreePerformance(Long sourceScripId)throws BusinessException  {		
		System.out.println("In datamutatir populateTreePerformance sourceScripId="+sourceScripId);
		ScripDao scrpDao = new ScripDao(entityManager);		
		DataDao dataDao = new DataDao(entityManager);
		
		List<Object> scrIds = scrpDao.getAllScripForNSE(null);
		System.out.println("scrIds  size="+scrIds.size());
		
		Calendar iterCal = Calendar.getInstance();		
		iterCal.set(Calendar.DATE, 1);
		iterCal.set(Calendar.MONTH, 1);
		iterCal.set(Calendar.YEAR, 2013);
		Date endDate = iterCal.getTime();		
		
		iterCal.set(Calendar.YEAR, 2012);		
		do {
			//System.out.println("For date="+iterCal.getTime());
			Float closePriceOnDate = dataDao.getPriceForScripOn(sourceScripId, iterCal.getTime(), "close_price");
			if (closePriceOnDate!=null && closePriceOnDate>0) {
				Float highPriceOnDate = dataDao.getPriceForScripOn(sourceScripId, iterCal.getTime(), "high_price", 20);
				if (highPriceOnDate!=null && highPriceOnDate>0) {
					double roiPercentage = (highPriceOnDate-closePriceOnDate)*100.0/closePriceOnDate;
					if (roiPercentage>7.5) {
						for(int i=0;i<scrIds.size();i++) {
							Long tgtDtoId = null;
							Object aObj = scrIds.get(i);
							if (aObj instanceof BigInteger ) {
								tgtDtoId = ((BigInteger)aObj).longValue();
							} else {
								tgtDtoId = (Long)aObj;
							}
							if (tgtDtoId!=sourceScripId) {
								Float targetClosePriceOnDate = dataDao.getPriceForScripOn(tgtDtoId, iterCal.getTime(), "close_price");
								if (targetClosePriceOnDate!=null && targetClosePriceOnDate>0) {
									Float targetHighPriceOnDate = dataDao.getPriceForScripOn(tgtDtoId, iterCal.getTime(), "high_price", 20);
									if (targetHighPriceOnDate!=null && targetHighPriceOnDate>0) {
										double targetRoiPercentage = (targetHighPriceOnDate-targetClosePriceOnDate)*100.0/targetClosePriceOnDate;
										if (targetRoiPercentage>7.5) {									
											dataDao.storeTreePerformance(sourceScripId, tgtDtoId, roiPercentage, targetRoiPercentage);											
										}
									}
								}
							}
						}
					}
				}
			}			
			iterCal.add(Calendar.DATE, 1);
		} while (endDate.after(iterCal.getTime()));
	}
}
  

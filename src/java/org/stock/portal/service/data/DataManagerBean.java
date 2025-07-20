package org.stock.portal.service.data;



import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.dao.DataDao;
import org.stock.portal.dao.ScripDao;
import org.stock.portal.domain.BSEEodData;
import org.stock.portal.domain.BSEIntraSummaryData;
import org.stock.portal.domain.EodData;
import org.stock.portal.domain.FinancialResult;
import org.stock.portal.domain.IntradaySnapshotData;
import org.stock.portal.domain.NSEEodData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.Sector;
import org.stock.portal.domain.TreePerformance;
import org.stock.portal.domain.ZeordhaDomVO;
import org.stock.portal.domain.dto.IntradaySummarySearchCriteriaDTO;
import org.stock.portal.domain.dto.OptionSummaryDto;
import org.stock.portal.domain.dto.ScripCompanyInfoDTO;
import org.stock.portal.domain.dto.ScripEOD;
import org.stock.portal.domain.metroWL.dto.IntradaySnapshotSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.PastAchieversSearchCriteriaDTO;
import org.stock.portal.web.util.Constants;




@Stateless(name="DataManager", mappedName="org.stock.portal.service.data.DataManager")
public class DataManagerBean implements DataManager {
	@Resource 
	private SessionContext context;

	private static Logger log = Logger.getLogger(DataManager.class.getName());
	
	@PersistenceContext(unitName = Constants.ProjectConfig.PERSISTENCE_UNIT)
    private EntityManager entityManager;

	@Resource( mappedName="java:/JmsXA" )
	private QueueConnectionFactory jmsConnectionFactory;

	@Resource( mappedName="java:/"+SPConstants.DATA_PROCESS_QUEUE_NAME)
	private Queue dataRepoDestination;
	
	/**
     * Authenticates  an <CODE>Account</CODE> given  the  username and
     * the password. If  the  authentication is successful, returns the
     * <CODE>Account</CODE> instance which corresponds to these credentials.
     *
     * @param userName <code>String</code>
     *        The account username.
     * @param password <code>String</code>
     *        The account password.
     * @return <code>Account</code>
     *        The account that matches the parameters.
     * @throws BusinessLayerException
     *        Thrown if there is a problem with the back-end.
     *
     */
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<BSEEodData> getEodData(String exchangeCode, String scripCode)
        throws BusinessException {
    	log.debug("In DataManager getEodData()-exchangeCode="+exchangeCode+" scripCode="+scripCode);
    	return (new DataDao(entityManager)).getEodData(scripCode);// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    @SuppressWarnings(value="unchecked")
    public Map getEodData(String exchangeCode, String scripCode, Date fromDate, Date toDate) throws BusinessException {
    	System.out.println("In DataManager getEodData()-exchangeCode="+exchangeCode+" scripCode="+scripCode+" fromDate="+fromDate+"toDate="+toDate);
    	Map returnMap = new HashMap();
    	
    	DataDao dataDao = new DataDao(entityManager);
    	ScripDao scripDao = new ScripDao(entityManager);
    	
    	List<ScripEOD> eodData = null;
    	if (scripCode.endsWith("_MP")) { // Mean Price
    		eodData = dataDao.getEquityEodDataMeanPriceBased(scripCode, fromDate, toDate, exchangeCode);
    	} else if (scripCode.endsWith("_SP")) { // Support price from history table
    		eodData = dataDao.getEquityEodDataSupportPriceBased(scripCode, fromDate, toDate, exchangeCode);
    	} else {
    		eodData = dataDao.getEquityEodData(scripCode, fromDate, toDate, exchangeCode);
    	}
    	
    	Scrip aScrip = null;
    	if (eodData!=null && eodData.size()>0) {
			aScrip =  scripDao.getScripById(((ScripEOD)eodData.get(0)).getScripId());
    	}    			
		returnMap.put( "Scrip",aScrip);
    	returnMap.put( "EODataList",eodData);
//    	System.out.println("eodData Size="+eodData.size());
//    	System.out.println("1St eodData="+eodData.get(0).getDataDate());
//    	System.out.println("Last eodData="+eodData.get(eodData.size()-1).getDataDate());
    	return returnMap;
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    @SuppressWarnings(value="unchecked")
    public List<BSEIntraSummaryData> searchIntradayDataByCriteria(IntradaySummarySearchCriteriaDTO intradaySearchCriteriaDTO) throws BusinessException {
    	log.debug("In DataManager searchIntradayDataByCriteria()");
    	return (new DataDao(entityManager)).searchIntradayDataByCriteria(intradaySearchCriteriaDTO);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<BSEEodData> getEodDataByScripId(Long scripId, int numberOfDays) throws BusinessException {
    	log.debug("In DataManager getEodDataByScripId()-scripId="+scripId+" scripCode="+numberOfDays);
    	return (new DataDao(entityManager)).getEodDataByScripId(scripId, numberOfDays);// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Map<String, Object> searchPastPerformanceByCriteria(PastAchieversSearchCriteriaDTO criteriaDto) throws BusinessException {    	
    	return (new DataDao(entityManager)).searchPastPerformanceByCriteria(criteriaDto);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<ScripEOD> getEodDataUptoDateByScripId(Long scripId, Date dataDate, Integer numberOfDays, boolean exchangeCode) throws BusinessException {
    	log.debug("In DataManager getEodDataByScripId()-scripId="+scripId+" scripCode="+numberOfDays);
    	return (new DataDao(entityManager)).getEodDataUptoDateByScripId(scripId, dataDate, numberOfDays, exchangeCode);// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Date getMaxDataDate(String exchangeCode, Date limitDate) throws BusinessException {
    	log.debug("In DataManager getMaxDataDate()-limitDate="+limitDate);
    	return (new DataDao(entityManager)).getMaxDataDate(exchangeCode,limitDate);// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Map<String, Object> searchIntradaySnapshotByCriteria(IntradaySnapshotSearchCriteriaDTO criteriaDto) throws BusinessException {    	
    	return (new DataDao(entityManager)).searchIntradaySnapshotByCriteria(criteriaDto);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    @SuppressWarnings(value="unchecked")
    public Map<Date, ScripEOD> getNSESnapshotData(String exchangeCode, String scripCode, Date fromDate, Date toDate) throws BusinessException {
    	log.debug("In DataManager getEodData()-exchangeCode="+exchangeCode+" scripCode="+scripCode+" fromDate="+fromDate+"toDate="+toDate);
    	Map returnMap = new HashMap();
    	
    	DataDao dataDao = new DataDao(entityManager);
    	
    	Map snapshotData = dataDao.getNSESnapshotData(scripCode, fromDate, toDate, exchangeCode);
    
    	return snapshotData;
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Map<String, Object> searchIntradayBTSTByCriteria(IntradaySnapshotSearchCriteriaDTO criteriaDto) throws BusinessException {    	
    	return (new DataDao(entityManager)).searchIntradayBTSTByCriteria(criteriaDto);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<EodData> getIndexViewResult(Date dataDate, String exchange, String orderBy, String orderType) throws BusinessException {    	
    	return (new DataDao(entityManager)).getIndexViewResult(dataDate, exchange, orderBy, orderType);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<ScripCompanyInfoDTO> getScripsByFreetextSearch(String freeTextSearchInput, String orderBy, String orderType, Long watchlistId) throws BusinessException {    	
    	return (new ScripDao(entityManager)).getScripsByFreetextSearch(freeTextSearchInput, orderBy, orderType, watchlistId);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<EodData> getIndexScripsResult(Long indexId, Date dataDate, String orderBy, String orderType) throws BusinessException {    	
    	String exchange = "BSE";
    	ScripDao scripDao = new ScripDao(entityManager);
    	Scrip scrip = scripDao.getScripById(indexId);
    	if (scrip.getNseCode()!=null && scrip.getNseCode().length()>0) exchange = "NSE";
    	return (new DataDao(entityManager)).getIndexScripsResult(indexId, exchange, dataDate, orderBy, orderType);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<TreePerformance> getTreePerformanceForScrip(Long sourceScripId, String orderBy, String orderType) throws BusinessException {    	
    	return (new DataDao(entityManager)).getTreePerformanceForScrip(sourceScripId, orderBy, orderType);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public void sendMessage(Object aObj) throws BusinessException {
		javax.jms.Message message = null;
		javax.jms.QueueConnection jmsConnection = null;
		javax.jms.QueueSession jmsSession = null;
		QueueSender jmsSender = null;
		try {
			jmsConnection = jmsConnectionFactory.createQueueConnection();
			jmsSession = jmsConnection.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			jmsSender = jmsSession.createSender(dataRepoDestination);
			
			message = jmsSession.createObjectMessage();
			((ObjectMessage) message).setObject((Serializable)aObj);
			jmsSender.send(message);			
			log.info("Begin processData...JMS message sent");
		} catch (Exception e) {
			throw new BusinessException("errors.process.data", e);
		} finally {
			try {
				if (jmsConnection!=null) jmsConnection.close();                
			}catch (Exception e) {
				throw new BusinessException("errors.process.data", e);
			}
		}
	}
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<FinancialResult> getFinancialResult(Long scripId, boolean useConsolidatedResult) throws BusinessException {    	
    	return (new DataDao(entityManager)).getFinancialResult(scripId, useConsolidatedResult);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Scrip> getLatestFinancialResultDeclaredScrips(String orderBy, String orderType) throws BusinessException {    	
    	return (new DataDao(entityManager)).getLatestFinancialResultDeclaredScrips(orderBy, orderType);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<IntradaySnapshotData> getSnapshotData(Long scripId, String starDate, String endDate) throws BusinessException {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Date trendStartDate = null;
    	Date trendEndDate = null;
    	try {
    		trendStartDate = sdf.parse(starDate);
    		trendEndDate = sdf.parse(endDate);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return (new DataDao(entityManager)).getSnapshotData(scripId, trendStartDate, trendEndDate);
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void saveTrendline(Long userId, String symbol, String trendlineValue) throws BusinessException {
    	try {
    		Query q = entityManager.createNativeQuery("update javachart_trendlines set trendline_value='"+ trendlineValue+"' where f_user="+userId+" and symbol='" + symbol + "'");
    		int effCount = q.executeUpdate();
    		if (effCount==0) {
    			q = entityManager.createNativeQuery("insert into javachart_trendlines (id, f_user, symbol, trendline_value) "
    					+ " values (nextval('javachart_trendlines_id_seq')," + userId + ", '" + symbol + "', '" + trendlineValue + "' )"); // set trendline_value='"+ trendlineValue+"' where f_user="+userId+" and symbol='" + symbol + "'");
    			q.executeUpdate();
    		}
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getTrendline(Long userId, String symbol, String extendedSymbol) throws BusinessException {
    	String retValue = "";
    	try {
    		String trlnQuery = "select trendline_value from javachart_trendlines where f_user="+userId+" and symbol='" + symbol + "'";
    		
    		if (extendedSymbol!=null) {
    			trlnQuery =  "select trendline_value from javachart_trendlines where f_user="+userId+" and symbol='" + symbol + "-" +extendedSymbol + "'";;
    		}
    		System.out.println("trlnQuery="+trlnQuery);
    		Query q = entityManager.createNativeQuery(trlnQuery);
    		List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object aObj = iter.next();
				retValue = (String) aObj;
			}
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return retValue;
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Sector> getAllSectors() throws BusinessException {    	
    	return (new DataDao(entityManager)).getAllSectors();
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void saveQtrResult(FinancialResult financialResult) throws BusinessException {
    	try {
    		String sqlFirstPart = "insert into " + (financialResult.getIsConsolidated()?" consolidated_financial_result ":" financial_result ") + " (id, f_scrip, financial_report_quarter_id";
    		String sqlSecondPart = " values( nextval('" + (financialResult.getIsConsolidated()?"consolidated_financial_result_id_seq":"financial_result_id_seq") + "')";
    		sqlSecondPart = sqlSecondPart + "," + financialResult.getScrip().getId();
    		sqlSecondPart = sqlSecondPart + "," + financialResult.getFinanciaReportQuarterId();
    		
    		if (financialResult.getRevenue() != null) {
    			sqlFirstPart = sqlFirstPart + ",revenue";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getRevenue() ;
    		}
    		if (financialResult.getOtherIncome() != null) {
    			sqlFirstPart = sqlFirstPart + ",other_income";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getOtherIncome() ;
    		}
    		if (financialResult.getTotalIncome() != null) {
    			sqlFirstPart = sqlFirstPart + ",total_income";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getTotalIncome() ;
    		}
    		if (financialResult.getExpenditure() != null) {
    			sqlFirstPart = sqlFirstPart + ",expenditure";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getExpenditure() ;
    		}
    		if (financialResult.getInterest() != null) {
    			sqlFirstPart = sqlFirstPart + ",interest";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getInterest();
    		}
    		if (financialResult.getPbdt() != null) {
    			sqlFirstPart = sqlFirstPart + ",pbdt";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getPbdt() ;
    		}
    		if (financialResult.getDepreciation() != null) {
    			sqlFirstPart = sqlFirstPart + ",depreciation";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getDepreciation() ;
    		}
    		if (financialResult.getPbt() != null) {
    			sqlFirstPart = sqlFirstPart + ",pbt";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getPbt() ;
    		}
    		if (financialResult.getTax() != null) {
    			sqlFirstPart = sqlFirstPart + ",tax";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getTax() ;
    		}
    		if (financialResult.getNetProfit() != null) {
    			sqlFirstPart = sqlFirstPart + ",net_profit";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getNetProfit() ;
    		}
    		if (financialResult.getEps() != null) {
    			sqlFirstPart = sqlFirstPart + ",eps";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getEps() ;
    		}
    		if (financialResult.getCeps() != null) {
    			sqlFirstPart = sqlFirstPart + ",ceps";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getCeps() ;
    		}
    		if (financialResult.getOpmPercentage() != null) {
    			sqlFirstPart = sqlFirstPart + ",opm_percentage";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getOpmPercentage() ;
    		}
    		if (financialResult.getNpmPercentage() != null) {
    			sqlFirstPart = sqlFirstPart + ",npm_percentage";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getNpmPercentage() ;
    		}
    		if (financialResult.getCarPercentage() != null) {
    			sqlFirstPart = sqlFirstPart + ",car_percentage";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getCarPercentage() ;
    		}
    		if (financialResult.getQtClosePrice() != null) {
    			sqlFirstPart = sqlFirstPart + ",qtr_close_price";
    			sqlSecondPart = sqlSecondPart + "," + financialResult.getQtClosePrice() ;
    		}
    		sqlFirstPart = sqlFirstPart + ") ";
    		sqlSecondPart = sqlSecondPart + ") ";
    		System.out.println("Final SQl = "+sqlFirstPart + sqlSecondPart);
    		Query q = entityManager.createNativeQuery(sqlFirstPart + sqlSecondPart);
    		int effCount = q.executeUpdate();
    		if (effCount>0) { // Update eps ttm for standalone only
    			if (financialResult.getIsConsolidated()==false) {
    				q = entityManager.createNativeQuery("update scrips set eps_ttm ="
    		    			+ "(select sum(eps) from financial_result where f_scrip=" + financialResult.getScrip().getId() + " and financial_report_quarter_id in "
    		    			+ " ( select financial_report_quarter_id from financial_result where f_scrip=" + financialResult.getScrip().getId() + " order by financial_report_quarter_id desc limit 4)"
    		    			+ " ) where id=" + financialResult.getScrip().getId() + "");
    		    			q.executeUpdate();
    			}
    			q = entityManager.createNativeQuery(" update scrips set result_date=now() where id="+financialResult.getScrip().getId());
    			q.executeUpdate();
    		}
    		System.out.println("effCount="+effCount);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Integer getMaxFinancialQtrId() throws BusinessException {
    	Integer retVal = null;
    	try {
    		Query q = entityManager.createNativeQuery("select max(financial_report_quarter_id) from financial_result");
    		List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object aObj = iter.next();
				retVal = (Integer) aObj;
			}
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	System.out.println("In getMaxFinancialQtrId retVal="+retVal) ;
    	return retVal;
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<NSEEodData> getNSEEodData(String nseCode, String uptoDateStr) 
        throws BusinessException {
    	log.debug("In DataManager getNSEEodData()-nseCode="+nseCode+" uptoDateStr="+uptoDateStr);
    	return (new DataDao(entityManager)).getNSEEodData(nseCode, uptoDateStr);// Now return nse data
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void saveKiteRequestToken(String clientId, String requestToken) throws BusinessException {
    	try {
    		Query q = entityManager.createNativeQuery("UPDATE nap_users set zerodha_service_token='" + requestToken +"' WHERE zerodha_user_id='" + clientId + "'");
    		int effCount = q.executeUpdate();
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void saveKiteRequestToken(String clientId, String requestToken, String zerodha_user_pin) throws BusinessException {
    	try {
    		String insertSql = "select * from dblink('dbname=nexcorio_db port=5432 host=localhost user=postgres password=jijikos', "
    				+ " 'UPDATE nexcorio_users set zerodha_service_token=''" + requestToken +"'' WHERE zerodha_user_id=''" + clientId + "'' and zerodha_user_id_pin=''" + zerodha_user_pin + "''" + " '"
    				+ ") AS tblProducts(record text)";
    		
    		Query q = entityManager.createNativeQuery(insertSql);
    		q.getSingleResult();
    		System.out.println("Done");
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void saveOptionOrder(String indexName, String optiontype) throws BusinessException {
    	try {
    		String sql2Execute = "INSERT INTO option_orders (id, nse_code, option_type, status, order_execution_method, target_percent, stoploss, exit_By_Time_In_Second ) VALUES " 
					+ " (nextval('option_orders_id_seq'),'" + indexName + "','" + optiontype + "','PENDING','Manual', 50, 20, 1200)";
    		
    		Query q = entityManager.createNativeQuery(sql2Execute);
    		int effCount = q.executeUpdate();
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<ScripEOD> getTickData(String symbol, Date startTime, Date endTime) throws BusinessException {
    	log.debug("In DataManager getTickData()-symbol="+symbol+" startTime="+startTime);
    	return (new DataDao(entityManager)).getTickData(symbol, startTime, endTime);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<ZeordhaDomVO> getDomData(Long scripId, Date fromTime, Date toTime) throws BusinessException {  
    	if (scripId<1000) {
    		return (new DataDao(entityManager)).getDomDataForIndex(scripId, fromTime, toTime);
    	} else {
    		return (new DataDao(entityManager)).getDomData(scripId, fromTime, toTime);
    	}
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<OptionSummaryDto> getOptionOIData(String indexName, String forDate) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionOIData(indexName, forDate);    	
    }
    
    public List<ScripEOD> getZerodhaCandleMinuteData(String symbol, String dataDate)throws BusinessException {
    	return (new DataDao(entityManager)).getZerodhaCandleMinuteData(symbol, dataDate);    	
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getIndividualOptionOIData(String indexName, String forDate) throws BusinessException {
    	return (new DataDao(entityManager)).getIndividualOptionOIData(indexName, forDate);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOISpikeData(String indexName, String forDate,int nooftopois, boolean filterOptionWorth) throws BusinessException {
    	return (new DataDao(entityManager)).getOISpikeData(indexName, forDate, nooftopois, filterOptionWorth);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getPandLOfOrder(String algoIds, String forDate) throws BusinessException {
    	return (new DataDao(entityManager)).getPandLOfOrder(algoIds, forDate);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOption1MGreeksMovements(String forDate, String optionnames) throws BusinessException {
    	return (new DataDao(entityManager)).getOption1MGreeksMovements(forDate, optionnames);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionCePeIVRatio(String indexname, String forDate, String forDelta, String expiryStr) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionCePeIVRatio(indexname, forDate, forDelta, expiryStr);    	
    } 
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getDOMSummary(String forDate, String scripName) throws BusinessException {
    	return (new DataDao(entityManager)).getDOMSummary(forDate, scripName);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOption1MPremiumDecay(String forDate, String optionnames) throws BusinessException {
    	return (new DataDao(entityManager)).getOption1MPremiumDecay(forDate, optionnames);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionGreeksData(String optionName, String forDate) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionGreeksData(optionName, forDate);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionSpikeData(String indexName, String forDate) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionSpikeData(indexName, forDate);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionDeltaNeutralPriceDisparity(String indexName, String forDate) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionDeltaNeutralPriceDisparity(indexName, forDate);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionMaxOIWorthReversionData(String indexName, String forDate) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionMaxOIWorthReversionData(indexName, forDate);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionOptimalStrikeData(String indexName, String forDate, Integer noOfTopOis) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionOptimalStrikeData(indexName, forDate, noOfTopOis);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionGreeksRateOfChange(String indexName, String forDate, Integer noOfTopOis, String method, boolean filterOptionWorth) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionGreeksRateOfChange(indexName, forDate, noOfTopOis, method, filterOptionWorth);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionOIDescrepancy(String indexName, String forDate, Integer noOfTopOis, boolean filterOptionWorth) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionOIDescrepancy(indexName, forDate, noOfTopOis, filterOptionWorth);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getTrendDecidingOptionGreeksROC(String indexName, String forDate, Integer noOfTopOis, String method) throws BusinessException {
    	return (new DataDao(entityManager)).getTrendDecidingOptionGreeksROC(indexName, forDate, noOfTopOis, method);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionPriceRateOfChange(String indexName, String forDate, float basedelta) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionPriceRateOfChange(indexName, forDate, basedelta);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionATMOTMOIRateOfChange(String indexName, String forDate) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionATMOTMOIRateOfChange(indexName, forDate);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionTimeValueAnalysis(String indexname, String forDate) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionTimeValueAnalysis(indexname, forDate);    	
    } 
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionVegaValueAnalysis(String indexname, String forDate, int noOfTopOis) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionVegaValueAnalysis(indexname, forDate, noOfTopOis);    	
    } 
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionATMMovmentAnalysis(String indexname, String forDate) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionATMMovmentAnalysis(indexname, forDate);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionATMMovmentRawDataAnalysis(Long mainInstrumentId, String forDate, float baseDelta) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionATMMovmentRawDataAnalysis(mainInstrumentId, forDate, baseDelta);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionGreeksMovmentAnalysis(String indexname, String forDate) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionGreeksMovmentAnalysis(indexname, forDate);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String getOptionDeltaRangeRawDataAnalysis(Long mainInstrumentId, String forDate, float baseDelta) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionDeltaRangeRawDataAnalysis(mainInstrumentId, forDate, baseDelta);    	
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public byte[] getOptionDeltaRangeRawDataAnalysisAsByteArray(Long mainInstrumentId, String forDate, float baseDelta) throws BusinessException {
    	return (new DataDao(entityManager)).getOptionDeltaRangeRawDataAnalysisAsByteArray(mainInstrumentId, forDate, baseDelta);    	
    }
}
  

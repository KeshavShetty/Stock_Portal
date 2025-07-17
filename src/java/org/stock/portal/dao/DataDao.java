
package org.stock.portal.dao;

import java.io.FileWriter;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.BSEEodData;
import org.stock.portal.domain.BSEIntraSummaryData;
import org.stock.portal.domain.ConsolidatedFinancialResult;
import org.stock.portal.domain.EodData;
import org.stock.portal.domain.FinancialResult;
import org.stock.portal.domain.IntradayBTSTAutoscanResult;
import org.stock.portal.domain.IntradaySnapshotData;
import org.stock.portal.domain.NSEEodData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.Sector;
import org.stock.portal.domain.TreePerformance;
import org.stock.portal.domain.ZeordhaDomVO;
import org.stock.portal.domain.dto.IntradaySummarySearchCriteriaDTO;
import org.stock.portal.domain.dto.OptionAlgoOrderDto;
import org.stock.portal.domain.dto.OptionOI;
import org.stock.portal.domain.dto.OptionSummaryDto;
import org.stock.portal.domain.dto.ScripEOD;
import org.stock.portal.domain.dto.ScripPerformanceDTO;
import org.stock.portal.domain.dto.ZerodhaOptionCandleVO;
import org.stock.portal.domain.metroWL.dto.IntradaySnapshotSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.PastAchieversSearchCriteriaDTO;


/**
 * 
 * @author European Dynamics
 */
public class DataDao {
	
	/** Logger instance for the class. */
    protected static final Logger log = Logger.getLogger(DataDao.class.getName());
    		
	/** Session EJB EntityManager - Injected by constructor to DAO. */
	private EntityManager entityManager = null;
	
	public static int EXPIRY_DAY_IN_WEEK_NUMBER_NIFTY = Calendar.THURSDAY;
	public static int EXPIRY_DAY_IN_WEEK_NUMBER_BANKNIFTY = Calendar.THURSDAY;
	public static int EXPIRY_DAY_IN_WEEK_NUMBER_FINNIFTY = Calendar.TUESDAY;
	
	/**
	 * 
	 * @param eManager
	 */
	public DataDao(final EntityManager eManager) {
		this.entityManager = eManager;
	}
	
	public List<BSEEodData> getEodData(String scripCode) throws BusinessException {
	
		log.debug(" DAO-HEADER: getEodData(String scripCode: "+ scripCode+" uniquecode="+scripCode.substring(4));
		List<BSEEodData> eodData = null;
		
		try {
			eodData = (List<BSEEodData>)this.entityManager.createNamedQuery("EodData.getEodDataByBseCode")
					.setParameter("bseCode", scripCode).getResultList();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			log.error(e);
			//e.printStackTrace();
		}
		if (eodData != null) {
			log.debug(" -- eodData : size = " + eodData.size());
		}
		return eodData;
	}
	
	public List<BSEEodData> getBSEEodData(String scripCode, Date fromDate, Date toDate) throws BusinessException {
		
		log.info(" DAO-HEADER: getEodData(String scripCode: "+ scripCode+" fromdate="+fromDate+" todate="+toDate);
		List<BSEEodData> eodData = null;
		
		try {
			eodData = (List<BSEEodData>)this.entityManager.createNamedQuery("BSEEodData.getBSEEodDataByBseCodeAndDateRange")
					.setParameter("bseCode", scripCode)
					.setParameter("bseName", scripCode)
					.setParameter("fromDate", fromDate)
					.setParameter("toDate", toDate)
					.getResultList();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		if (eodData != null) {
			log.debug(" -- eodData : size = " + eodData.size());
		}
		return eodData;
	}
	
public List getNSEEodData(String scripCode, Date fromDate, Date toDate) throws BusinessException {
		
		log.debug(" DAO-HEADER: getEodData(String scripCode: "+ scripCode+" uniquecode="+scripCode.substring(4));
		List eodData = null;
		
		try {
			eodData = (List<NSEEodData>)this.entityManager.createNamedQuery("NSEEodData.getNSEEodDataByNseCodeAndDateRange")
					.setParameter("nseCode", scripCode)
					.setParameter("fromDate", fromDate)
					.setParameter("toDate", toDate)
					.getResultList();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		if (eodData != null) {
			log.debug(" -- eodData : size = " + eodData.size());
		}
		return eodData;
	}

	public List<ScripEOD> getEquityEodData(String scripCode, Date fromDate, Date toDate, String exchangeCode) throws BusinessException {
		
		log.debug(" DAO-HEADER: getEquityEodData(String scripCode: "+ scripCode+" uniquecode="+scripCode);
		List eodData = new ArrayList<ScripEOD>();
		
		try {
			Long scripId = null;
			ScripDao scrDao = new ScripDao(entityManager);
			if (exchangeCode.equalsIgnoreCase("bse")) {
				scripId = scrDao.getScripByBseCodeOrName(scripCode);
			} else {
				scripId = scrDao.getScripByNseCodeOrSeries(scripCode, "EQ");
				if (scripId==null) scripId = scrDao.getScripByNseCode(scripCode);
			}
			
			String query = "select data_date, open_price, high_price, low_price, close_price, volume, f_scrip from ";
			if (exchangeCode.equalsIgnoreCase("bse")) query = query + " bse_eq_eod_data ";
			else query = query + " nse_eq_eod_data ";
			
			query = query + " where f_scrip = ?";
			
			query = query + " and data_date >= ? and data_date <=? order by data_date asc";	
			Query q = entityManager.createNativeQuery(query);
			
			System.out.println("scripId-"+scripId);
			System.out.println("fromDate-"+fromDate);
			System.out.println("toDate-"+toDate);
			if (exchangeCode.equalsIgnoreCase("bse")) {
				q.setParameter( 1, scripId);
				q.setParameter( 2, fromDate);
				q.setParameter( 3, toDate);
			} else {
				q.setParameter( 1, scripId);
				q.setParameter( 2, fromDate);
				q.setParameter( 3, toDate);
			}
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				ScripEOD aDto = new ScripEOD();
				aDto.setDataDate((Date)rowdata[0]);
				aDto.setOpenPrice((Float)rowdata[1]);
				aDto.setHighPrice((Float)rowdata[2]);
				aDto.setLowPrice((Float)rowdata[3]);
				aDto.setClosePrice((Float)rowdata[4]);
				aDto.setVolume(((BigInteger)rowdata[5]).longValue());
				aDto.setScripId(((BigInteger)rowdata[6]).longValue());
				eodData.add(aDto);
			}			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		if (eodData != null) {
			log.debug(" -- eodData : size = " + eodData.size());
		}
		return eodData;
	}
	
public List<ScripEOD> getEquityEodDataMeanPriceBased(String paddedScripCode, Date fromDate, Date toDate, String exchangeCode) throws BusinessException {
		String scripCode = paddedScripCode.substring(0,paddedScripCode.indexOf("_MP"));
		log.debug(" DAO-HEADER: getEquityEodData(String scripCode: "+ scripCode+" uniquecode="+scripCode);
		List eodData = new ArrayList<ScripEOD>();
		
		try {
			Long scripId = null;
			ScripDao scrDao = new ScripDao(entityManager);
			if (exchangeCode.equalsIgnoreCase("bse")) {
				scripId = scrDao.getScripByBseCodeOrName(scripCode);
			} else {
				scripId = scrDao.getScripByNseCodeOrSeries(scripCode, "EQ");
				if (scripId==null) scripId = scrDao.getScripByNseCode(scripCode);
			}
			
			String query = "select data_date, mean_price, f_scrip from intraday_snapshot_data";						
			query = query + " where f_scrip = ? and data_date >= ? and data_date <=? order by data_date asc";	
			Query q = entityManager.createNativeQuery(query);
			
			System.out.println("scripId-"+scripId);
			System.out.println("fromDate-"+fromDate);
			System.out.println("toDate-"+toDate);
			if (exchangeCode.equalsIgnoreCase("bse")) {
				q.setParameter( 1, scripId);
				q.setParameter( 2, fromDate);
				q.setParameter( 3, toDate);
			} else {
				q.setParameter( 1, scripId);
				q.setParameter( 2, fromDate);
				q.setParameter( 3, toDate);
			}
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				ScripEOD aDto = new ScripEOD();
				aDto.setDataDate((Date)rowdata[0]);
				
				float meanPrice = (Float)rowdata[1];
				aDto.setOpenPrice(meanPrice);
				aDto.setHighPrice(meanPrice);
				aDto.setLowPrice(meanPrice);
				aDto.setClosePrice(meanPrice);
				aDto.setVolume(0L);
				aDto.setScripId(((BigInteger)rowdata[2]).longValue());
				eodData.add(aDto);
			}			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		if (eodData != null) {
			log.debug(" -- eodData : size = " + eodData.size());
		}
		return eodData;
	}

public List<ScripEOD> getEquityEodDataSupportPriceBased(String paddedScripCode, Date fromDate, Date toDate, String exchangeCode) throws BusinessException {
	String scripCode = paddedScripCode.substring(0,paddedScripCode.indexOf("_SP"));
	log.debug(" DAO-HEADER: getEquityEodDataSupportPriceBased(String scripCode: "+ scripCode+" uniquecode="+scripCode);
	List eodData = new ArrayList<ScripEOD>();
	
	try {
		Long scripId = null;
		ScripDao scrDao = new ScripDao(entityManager);
		if (exchangeCode.equalsIgnoreCase("bse")) {
			scripId = scrDao.getScripByBseCodeOrName(scripCode);
		} else {
			scripId = scrDao.getScripByNseCodeOrSeries(scripCode, "EQ");
			if (scripId==null) scripId = scrDao.getScripByNseCode(scripCode);
		}
		
		String query = "select last_updated, support_price_3m, support_volume_leftover_3m, f_scrip from scrips_history";						
		query = query + " where f_scrip = ? and last_updated >= ? and last_updated <=? and support_price_3m is not null order by last_updated asc";	
		Query q = entityManager.createNativeQuery(query);
		
		System.out.println("scripId-"+scripId);
		System.out.println("fromDate-"+fromDate);
		System.out.println("toDate-"+toDate);
		if (exchangeCode.equalsIgnoreCase("bse")) {
			q.setParameter( 1, scripId);
			q.setParameter( 2, fromDate);
			q.setParameter( 3, toDate);
		} else {
			q.setParameter( 1, scripId);
			q.setParameter( 2, fromDate);
			q.setParameter( 3, toDate);
		}
		List<Object[]> listResults = q.getResultList();
		Iterator<Object[]> iter = listResults.iterator();
		while (iter.hasNext()) {
			Object[] rowdata = iter.next();
			ScripEOD aDto = new ScripEOD();
			aDto.setDataDate((Date)rowdata[0]);
			
			float supportPrice = (Float)rowdata[1];
			aDto.setOpenPrice(supportPrice);
			aDto.setHighPrice(supportPrice);
			aDto.setLowPrice(supportPrice);
			aDto.setClosePrice(supportPrice);
			aDto.setVolume(((BigInteger)rowdata[2]).longValue());
			aDto.setScripId(((BigInteger)rowdata[3]).longValue());
			eodData.add(aDto);
		}			
	} catch (NoResultException e) {
		log.debug(" -- HERE : No account found with these credentials ");
		//e.printStackTrace();
		log.error(e);
	} catch (Exception e) {
		e.printStackTrace();
		log.error(e);
	}
	if (eodData != null) {
		log.debug(" -- eodData : size = " + eodData.size());
	}
	return eodData;
}

	@SuppressWarnings("unchecked")
	public List<BSEIntraSummaryData> searchIntradayDataByCriteria(IntradaySummarySearchCriteriaDTO intradaySearchCriteriaDTO) {	
		log.debug(" DAO-HEADER: searchScripByCriteria");		
		List<BSEIntraSummaryData> retList = null;
		
		try {
			StringBuffer query = new StringBuffer("SELECT OBJECT(intraSummary) FROM BSEIntraSummaryData intraSummary join fetch intraSummary.scrip");
			boolean whereAdded = false;			
			
			if (intradaySearchCriteriaDTO.getDataDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" intraSummary.dataDate = :dataDate");				
			}
			
			if (intradaySearchCriteriaDTO.getAverageVolume()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" intraSummary.scrip.averageVolume > :avgVolume");				
			}
			
			
			if (intradaySearchCriteriaDTO.getWatchlistId()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" intraSummary.scrip.id in (select wlItem.scrip.id from WatchlistItem wlItem where wlItem.watchlist.id="+intradaySearchCriteriaDTO.getWatchlistId()+")");				
			}
			
			if (intradaySearchCriteriaDTO.getPercentage52wHigh()!=null) {
				int percent52 = intradaySearchCriteriaDTO.getPercentage52wHigh();
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" intraSummary.scrip.bse52weekHigh<=(intraSummary.lastPrice*(100 + " + percent52 +")/100)");
			}
			
			if (intradaySearchCriteriaDTO.getOrderBy()!=null)  { 
				query.append(" ORDER BY " + intradaySearchCriteriaDTO.getOrderBy());
				if (intradaySearchCriteriaDTO.getOrderType()==null) query.append(" ASC "); 
				else query.append(" "+ intradaySearchCriteriaDTO.getOrderType() + " "); 
			}
	    	
			Query jpaQry = this.entityManager.createQuery(query.toString());			
			
			if (intradaySearchCriteriaDTO.getDataDate()!=null) { 
				jpaQry.setParameter("dataDate", intradaySearchCriteriaDTO.getDataDate());
			}
			if (intradaySearchCriteriaDTO.getAverageVolume()!=null) { 
				jpaQry.setParameter("avgVolume", intradaySearchCriteriaDTO.getAverageVolume());
			}
			jpaQry.setMaxResults(intradaySearchCriteriaDTO.getNumberOfRecords());
			retList = (List<BSEIntraSummaryData>)jpaQry.getResultList();			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No intraday data found with these criteria ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		if (retList != null) {
			log.info(" -- Intraday summary data : size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public List<BSEEodData> getEodDataByScripId(Long scripId, int numberOfDays) throws BusinessException {
		
		log.info(" DAO-HEADER: getEodDataByScripId(String scripId: "+ scripId+" numberOfDays="+numberOfDays);
		List<BSEEodData> eodData = null;
		
		try {
			String jpaQry = "SELECT OBJECT(eodData) FROM BSEEodData eodData where eodData.scrip.id = :scrId order by eodData.dataDate desc";
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("scrId", scripId);
			query.setMaxResults(numberOfDays);
			eodData = (List<BSEEodData>)query.getResultList();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		if (eodData != null) {			
			log.info(" -- getEodDataByScripId : size = " + eodData.size());
		}
		Date maxDate = null;
		if (eodData.size()>0) {
			maxDate = (eodData.get(0)).getDataDate();
			log.info("maxDate="+maxDate);			
			Collections.reverse(eodData);
			if (maxDate!=null) {
				String jpaQry = "SELECT OBJECT(intrData) FROM BSEIntraSummaryData intrData where intrData.scrip.id = :scrId and intrData.dataDate > :afterDate order by intrData.dataDate asc";
				Query query = this.entityManager.createQuery(jpaQry);
				query.setParameter("scrId", scripId);
				query.setParameter("afterDate", maxDate);
				List<BSEIntraSummaryData> intraList = (List<BSEIntraSummaryData>)query.getResultList();
				if (intraList!=null && intraList.size()>0) {
					log.info("intraList size="+intraList.size());
					for(int i=0;i<intraList.size();i++) {
						BSEIntraSummaryData aData = intraList.get(i);
						BSEEodData newIntrData = new BSEEodData();
						newIntrData.setDataDate(aData.getDataDate());
						newIntrData.setOpenPrice(aData.getOpenPrice());
						newIntrData.setHighPrice(aData.getHighPrice());
						newIntrData.setLowPrice(aData.getLowPrice());
						newIntrData.setClosePrice(aData.getClosePrice());
						newIntrData.setVolume(aData.getTotalVolume());
						eodData.add(newIntrData);
					}
				}
			}
		}	
		return eodData;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchPastPerformanceByCriteria(PastAchieversSearchCriteriaDTO criteriaDto) throws BusinessException {
		
		log.debug(" DAO-HEADER: searchScripByCriteria");		
		Map<String, Object> retMap = new HashMap<String, Object>();;
		
		try {
			StringBuffer query = new StringBuffer();
			String watchlistQuery = null;
			
			String firstPart = "select scr.id as id, scr.name as name, eod1.close_price as firstData, eod2.close_price as lastData, 100*(eod2.close_price-eod1.close_price)/NULLIF(eod1.close_price,0) as performance, scr.bse_code as bseCode, scr.nse_code as nseCode, eod1.volume as firstVolume, eod2.volume as lastVolume, cast(eod2.volume as real)/NULLIF(cast(eod1.volume as real),0) as volumePerformance, scrHist.avg_volume as avgVolume, cast(eod2.volume as real)/NULLIF(cast(scrHist.avg_volume as real),0) as volumeVsAvgVolume, eod2.close_price*eod2.volume as turnover, (eod2.low_price-eod1.high_price)*100/NULLIF(eod1.high_price,0) as upGap, (eod1.low_price-eod2.high_price)*100/NULLIF(eod1.low_price,0) as downGap, (eod2.close_price-eod2.open_price)*100/NULLIF(eod2.open_price,0) as barSizeOfTheDay, cast(eod2.volume as real)/NULLIF(cast(eod1.highest_1m_volume as real),0) as volumeVsLastDay1MVol";
			String countPart = "select count(*) ";
			if (criteriaDto.getExchange().equalsIgnoreCase("bse")) {
				query.append(" from scrips scr, scrips_history scrHist, bse_eq_eod_data eod1, bse_eq_eod_data eod2 where scr.id=scrHist.f_scrip and scrHist.last_updated=eod1.data_date and eod1.f_scrip=scr.id and eod2.f_scrip=scr.id and eod1.data_date=? and eod2.data_date=? and eod1.close_price>0 and eod1.volume>0");
			} else {
				query.append(" from scrips scr, scrips_history scrHist, nse_eq_eod_data eod1, nse_eq_eod_data eod2 where scr.id=scrHist.f_scrip and scrHist.last_updated=eod1.data_date and eod1.f_scrip=scr.id and eod2.f_scrip=scr.id and eod1.data_date=? and eod2.data_date=?");
			}
			
			
			if (criteriaDto.getMinCmp()!=null)  {
				query.append(" and scr.bse_cmp >= ?");	
			}
			if (criteriaDto.getMaxCmp()!=null)  {
				query.append(" and scr.bse_cmp <= ?");	
			}
			
			if (criteriaDto.getMinEps()!=null)  {
				query.append(" and scr.eps_ttm >= ?");	
			}
			if (criteriaDto.getMaxEps()!=null)  {
				query.append(" and scr.eps_ttm <= ?");	
			}
			
			if (criteriaDto.getMinPe()!=null)  {
				query.append(" and scr.pe >= ?");	
			}
			if (criteriaDto.getMaxPe()!=null)  {
				query.append(" and scr.pe <= ?");	
			}			
			if (criteriaDto.getAverageVolume()!=null)  {
				query.append(" and scr.nse_avg_volume >= ?");	
			}
			
			if (criteriaDto.getMinPerformance()!=null)  {
				query.append(" and 100*(eod2.close_price-eod1.close_price)/eod1.close_price >= ?");	
			}
			if (criteriaDto.getMaxPerformance()!=null)  {
				query.append(" and 100*(eod2.close_price-eod1.close_price)/eod1.close_price <= ?");	
			}
			query.append(" and eod1.close_price > 0 ");
			if (criteriaDto.getMinVolumePerformance()!=null)  {
				query.append(" and eod2.volume/eod1.volume >= ?");	
			}
			if (criteriaDto.getMaxVolumePerformance()!=null)  {
				query.append(" and eod2.volume/eod1.volume <= ?");	
			}
			query.append(" and eod1.volume > 0 ");
			
			WatchlistDao watchlistDao = new WatchlistDao(entityManager);
			if (criteriaDto.getSelectedWatchlist()!=null)  {
				watchlistQuery = watchlistDao.getWatchlistById(criteriaDto.getSelectedWatchlist()).getScripFecthSql();
				query.append(" and scr.id in (" + watchlistQuery + ")");	
			}
			
			if (criteriaDto.getSelectedSecondWatchlist()!=null)  {				
				String secondWatchlistQuery = watchlistDao.getWatchlistById(criteriaDto.getSelectedSecondWatchlist()).getScripFecthSql();
				query.append(" and scr.id in (" + secondWatchlistQuery + ")");	
			}
			
			String orderPart = "";
			if (criteriaDto.getOrderBy()!=null)  { 
				orderPart = " ORDER BY " + criteriaDto.getOrderBy();
				if (criteriaDto.getOrderType()==null) orderPart = orderPart + " ASC "; 
				else orderPart = orderPart + " " + criteriaDto.getOrderType() + " "; 
			} 
			System.out.println("criteriaDto.getFirstDate()="+criteriaDto.getFirstDate());
			System.out.println("criteriaDto.getLasstDate()="+criteriaDto.getLastDate());
			Query q = entityManager.createNativeQuery(firstPart + query.toString() + orderPart);
			q.setParameter( 1, criteriaDto.getFirstDate());
			q.setParameter( 2, criteriaDto.getLastDate());
			
			int cursorPos = 3;
			if (criteriaDto.getMinCmp()!=null)  {
				q.setParameter(cursorPos, criteriaDto.getMinCmp());
				cursorPos++;
			}
			if (criteriaDto.getMaxCmp()!=null)  {
				q.setParameter(cursorPos, criteriaDto.getMaxCmp());
				cursorPos++;
			}
			
			if (criteriaDto.getMinEps()!=null)  {
				q.setParameter(cursorPos, criteriaDto.getMinEps());
				cursorPos++;
			}
			if (criteriaDto.getMaxEps()!=null)  {
				q.setParameter(cursorPos, criteriaDto.getMaxEps());
				cursorPos++;
			}
			
			if (criteriaDto.getMinPe()!=null)  {
				q.setParameter(cursorPos, criteriaDto.getMinPe());
				cursorPos++;	
			}
			if (criteriaDto.getMaxPe()!=null)  {
				q.setParameter(cursorPos, criteriaDto.getMaxPe());
				cursorPos++;
			}			
			if (criteriaDto.getAverageVolume()!=null)  {
				q.setParameter(cursorPos, criteriaDto.getAverageVolume());
				cursorPos++;
			}			
			if (criteriaDto.getMinPerformance()!=null)  {
				q.setParameter(cursorPos, criteriaDto.getMinPerformance());
				cursorPos++;
			}
			if (criteriaDto.getMaxPerformance()!=null)  {
				q.setParameter(cursorPos, criteriaDto.getMaxPerformance());
				cursorPos++;
			}
			
			if (criteriaDto.getMinVolumePerformance()!=null)  {
				q.setParameter(cursorPos, criteriaDto.getMinVolumePerformance());
				cursorPos++;
			}
			if (criteriaDto.getMaxVolumePerformance()!=null)  {
				q.setParameter(cursorPos, criteriaDto.getMaxVolumePerformance());
				cursorPos++;
			}
//			if (criteriaDto.getSelectedWatchlist()!=null)  {
//				q.setParameter(cursorPos, criteriaDto.getSelectedWatchlist());
//				cursorPos++;
//			}
			q.setFirstResult((criteriaDto.getPageNumber()-1)*criteriaDto.getRecordPerPage());
			q.setMaxResults(criteriaDto.getRecordPerPage());
			
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			List<ScripPerformanceDTO> returnList = new ArrayList<ScripPerformanceDTO>();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				ScripPerformanceDTO aDto = new ScripPerformanceDTO();
				aDto.setId(((BigInteger)rowdata[0]).longValue());
				aDto.setName((String)rowdata[1]);
				aDto.setFirstPrice((Float)rowdata[2]);
				aDto.setLastPrice((Float)rowdata[3]);
				aDto.setPerformance(((Double)rowdata[4]).floatValue());
				aDto.setBseCode((String)rowdata[5]);
				aDto.setNseCode((String)rowdata[6]);
				aDto.setFirstVolume(((BigInteger)rowdata[7]).longValue());
				aDto.setLastVolume(((BigInteger)rowdata[8]).longValue());				
				aDto.setVolumePerformance(((Float)rowdata[9]).floatValue());
				
				aDto.setAvgVolume(((BigInteger)rowdata[10]).longValue());
				aDto.setVolumeVsAvgVolume(((Float)rowdata[11]).floatValue());
				aDto.setTurnover(((Double)rowdata[12]).floatValue());
				aDto.setUpGap(((Double)rowdata[13]).floatValue());
				aDto.setDownGap(((Double)rowdata[14]).floatValue());
				Object aObj = rowdata[15];
				if (aObj!=null) aDto.setBarSizeOfTheDay(((Double)aObj).floatValue());
				Object aObj1 = rowdata[16];
				if (aObj1!=null) aDto.setVolumeVsLastDay1MVol(((Float)aObj1).floatValue());
				returnList.add(aDto);
			}
			
			retMap.put("Result", returnList);
			Query countQry = entityManager.createNativeQuery(countPart + query.toString());
			countQry.setParameter( 1, criteriaDto.getFirstDate());
			countQry.setParameter( 2, criteriaDto.getLastDate());
			
			
			cursorPos = 3;
			if (criteriaDto.getMinCmp()!=null)  {
				countQry.setParameter(cursorPos, criteriaDto.getMinCmp());
				cursorPos++;
			}
			if (criteriaDto.getMaxCmp()!=null)  {
				countQry.setParameter(cursorPos, criteriaDto.getMaxCmp());
				cursorPos++;
			}
			
			if (criteriaDto.getMinEps()!=null)  {
				countQry.setParameter(cursorPos, criteriaDto.getMinEps());
				cursorPos++;
			}
			if (criteriaDto.getMaxEps()!=null)  {
				countQry.setParameter(cursorPos, criteriaDto.getMaxEps());
				cursorPos++;
			}
			
			if (criteriaDto.getMinPe()!=null)  {
				countQry.setParameter(cursorPos, criteriaDto.getMinPe());
				cursorPos++;	
			}
			if (criteriaDto.getMaxPe()!=null)  {
				countQry.setParameter(cursorPos, criteriaDto.getMaxPe());
				cursorPos++;
			}			
			if (criteriaDto.getAverageVolume()!=null)  {
				countQry.setParameter(cursorPos, criteriaDto.getAverageVolume());
				cursorPos++;
			}			
			if (criteriaDto.getMinPerformance()!=null)  {
				countQry.setParameter(cursorPos, criteriaDto.getMinPerformance());
				cursorPos++;
			}
			if (criteriaDto.getMaxPerformance()!=null)  {
				countQry.setParameter(cursorPos, criteriaDto.getMaxPerformance());
				cursorPos++;
			}
			
			if (criteriaDto.getMinVolumePerformance()!=null)  {
				countQry.setParameter(cursorPos, criteriaDto.getMinVolumePerformance());
				cursorPos++;
			}
			if (criteriaDto.getMaxVolumePerformance()!=null)  {
				countQry.setParameter(cursorPos, criteriaDto.getMaxVolumePerformance());
				cursorPos++;
			}
//			if (criteriaDto.getSelectedWatchlist()!=null)  {
//				countQry.setParameter(cursorPos, criteriaDto.getSelectedWatchlist());
//				cursorPos++;
//				
//			}			
			Long recordsCount = ((BigInteger)countQry.getSingleResult()).longValue();
			System.out.println("recordsCount="+recordsCount);
			retMap.put("RecordsCount", recordsCount);			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return retMap;
	}
	
	@SuppressWarnings("unchecked")
	public List<ScripEOD> getEodDataUptoDateByScripId(Long scripId, Date dataDate, Integer numberOfDays, boolean exchangeCode) throws BusinessException {
		
		System.out.println("DAO getEodDataUptoDateByScripId scripId: "+ scripId+" dataDate="+dataDate+" numberOfDays="+numberOfDays+" exchangeCode="+exchangeCode);
		List<ScripEOD> retList = new ArrayList<ScripEOD>();
		
		try {
			String dataTable = "BSEEodData";
			if (exchangeCode) dataTable = "NSEEodData";
			String jpaQry = "SELECT OBJECT(eodData) FROM " + dataTable + " eodData where eodData.scrip.id = :scrId and eodData.dataDate <= :dataDate order by eodData.dataDate desc";
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("scrId", scripId);
			query.setParameter("dataDate", dataDate);
			query.setMaxResults(numberOfDays);
			if (exchangeCode) {
				List<NSEEodData> dataList =  (List<NSEEodData>)query.getResultList();
				System.out.println("dataList size="+dataList.size());
				for(int i=dataList.size()-1;i>=0;i--) {
					NSEEodData aEodData = dataList.get(i);
					ScripEOD aDto = new ScripEOD();
					aDto.setDataDate(aEodData.getDataDate());
					aDto.setOpenPrice(aEodData.getOpenPrice());
					aDto.setHighPrice(aEodData.getHighPrice());
					aDto.setLowPrice(aEodData.getLowPrice());
					aDto.setClosePrice(aEodData.getClosePrice());
					retList.add(aDto);
				}
			} else {
				List<BSEEodData> dataList =  (List<BSEEodData>)query.getResultList();
				System.out.println("dataList size="+dataList.size());
				for(int i=dataList.size()-1;i>=0;i--) {
					BSEEodData aEodData = dataList.get(i);
					ScripEOD aDto = new ScripEOD();
					aDto.setDataDate(aEodData.getDataDate());
					aDto.setOpenPrice(aEodData.getOpenPrice());
					aDto.setHighPrice(aEodData.getHighPrice());
					aDto.setLowPrice(aEodData.getLowPrice());
					aDto.setClosePrice(aEodData.getClosePrice());
					retList.add(aDto);
				}
			}
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public Date getMaxDataDate(String exchangeCode, Date limitDate) throws BusinessException {
		Date returnDate = null;
		log.debug(" DAO-HEADER: getMaxDataDate");
		
		try {
			String tblToUse = "bse_eq_eod_data";
			if (exchangeCode.equalsIgnoreCase("NSE")) tblToUse = "nse_eq_eod_data";
			String strSql = "select max(data_date) from "+tblToUse+" eodData where eodData.data_date<?";
			
			Query countQry = entityManager.createNativeQuery(strSql);
			countQry.setParameter(1, limitDate);
			returnDate = (Date)countQry.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return returnDate;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchIntradaySnapshotByCriteria(IntradaySnapshotSearchCriteriaDTO criteriaDto) throws BusinessException {

		
		log.debug(" DAO-HEADER: searchCandlePatternScripByAdvancedCriteria");
		
		Map<String, Object> retMap = new HashMap<String, Object>();;
		
		try {
			StringBuffer query = new StringBuffer();			
			boolean whereAdded = false;			
			
			if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
			query.append(" asRes.volumeBefore3 > 0");
			
			if (criteriaDto.getDataDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.dataDate = :dataDate");	
			}
			
			if (criteriaDto.getMinVolumePerformance()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.volumeAfter3Before315/asRes.volumeBefore3 >= :minVolumePerformance");	
			}
			
			if (criteriaDto.getMaxVolumePerformance()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.volumeAfter3Before315/asRes.volumeBefore3 <= :maxVolumePerformance");	
			}
						
			StringBuffer countQuery = new StringBuffer(query.toString()); //Order by and type not required for count query
			        	
			StringBuffer internalScripQuery = new StringBuffer();
			StringBuffer internalScripCountQuery = new StringBuffer();
			
			if (!whereAdded) { 
				internalScripQuery.append(" WHERE "); 
				internalScripCountQuery.append(" WHERE ");
				whereAdded=true; 
			} else { 
				internalScripQuery.append(" AND "); 
				internalScripCountQuery.append(" AND "); 
			}
			internalScripQuery.append(" asRes.scrip.id in (select id from Scrip scrInternal ");
			internalScripCountQuery.append(" asRes.scrip.id in (select id from Scrip scrInternal ");
			boolean internalWhereAdded = false;	
				
			if (criteriaDto.getScripName()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" lower(scrInternal.name) like :scripName");	
				internalScripCountQuery.append(" lower(scrInternal.name) like :scripName");	
			}
			if (criteriaDto.getBseCode()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCode like :bseCode");
				internalScripCountQuery.append(" scrInternal.bseCode like :bseCode");
			}
			if (criteriaDto.getNseCode()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.nseCode like :nseCode");
				internalScripCountQuery.append(" scrInternal.nseCode like :nseCode");	
			}
			
			if (criteriaDto.getMinCmp()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCmp >= :minCmp");
				internalScripCountQuery.append(" scrInternal.bseCmp >= :minCmp");
			}
			if (criteriaDto.getMaxCmp()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCmp <= :maxCmp");
				internalScripCountQuery.append(" scrInternal.bseCmp <= :maxCmp");
			}			
			if (criteriaDto.getMinEps()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.eps >= :minEps");
				internalScripCountQuery.append(" scrInternal.eps >= :minEps");
			}
			if (criteriaDto.getMaxEps()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.eps <= :maxEps");
				internalScripCountQuery.append(" scrInternal.eps <= :maxEps");
			}			
			if (criteriaDto.getMinPe()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.pe >= :minPe");	
				internalScripCountQuery.append(" scrInternal.pe >= :minPe");
			}
			if (criteriaDto.getMaxPe()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.pe <= :maxPe");	
				internalScripCountQuery.append(" scrInternal.pe <= :maxPe");
			}			
			
			if (criteriaDto.getAverageVolume()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.nseAverageVolume >= :averageVolume");	
				internalScripCountQuery.append(" scrInternal.nseAverageVolume >= :averageVolume");
			}	
			if (criteriaDto.getSelectedWatchlist()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.id in (select scrip.id from WatchlistItem WL where watchlist.id = :watchlistId)");	
				internalScripCountQuery.append(" scrInternal.id in (select scrip.id from WatchlistItem WL where watchlist.id = :watchlistId)");
			}
			internalScripQuery.append(")"); //Close internal sql query
			internalScripCountQuery.append(")");
			
			System.out.println("criteriaDto.getOrderBy()="+criteriaDto.getOrderBy()+" ordertype="+criteriaDto.getOrderType());
			if (criteriaDto.getOrderBy()!=null)  { 
				if (criteriaDto.getOrderBy().equalsIgnoreCase("volumePerformance")) internalScripQuery.append(" ORDER BY " + "cast(asRes.volumeAfter3Before315 as float)/cast(asRes.volumeBefore3 as float)");
				else internalScripQuery.append(" ORDER BY " + criteriaDto.getOrderBy());
				if (criteriaDto.getOrderType()==null) internalScripQuery.append(" ASC "); 
				else internalScripQuery.append(" "+ criteriaDto.getOrderType() + " "); 
			}
			
			Query jpaQry = this.entityManager.createQuery("SELECT OBJECT(asRes) FROM IntradaySnapshotData asRes " + query.toString() + internalScripQuery );
			Query countQry = this.entityManager.createQuery("SELECT count(*) FROM IntradaySnapshotData asRes " + countQuery.toString() + internalScripCountQuery);
			
			if (criteriaDto.getDataDate()!=null)  { 
				jpaQry.setParameter("dataDate", criteriaDto.getDataDate());
				countQry.setParameter("dataDate", criteriaDto.getDataDate());
			}
			
			if (criteriaDto.getMinVolumePerformance()!=null)  { 
				jpaQry.setParameter("minVolumePerformance", criteriaDto.getMinVolumePerformance().longValue());
				countQry.setParameter("minVolumePerformance", criteriaDto.getMinVolumePerformance().longValue());
			}
			
			if (criteriaDto.getMaxVolumePerformance()!=null)  {
				jpaQry.setParameter("maxVolumePerformance", criteriaDto.getMaxVolumePerformance().longValue());
				countQry.setParameter("maxVolumePerformance", criteriaDto.getMaxVolumePerformance().longValue());
			}
			
			if (criteriaDto.getScripName()!=null)  { 
				jpaQry.setParameter("scripName", "%"+criteriaDto.getScripName().toLowerCase()+"%");
				countQry.setParameter("scripName", "%"+criteriaDto.getScripName().toLowerCase()+"%");	
			}
			if (criteriaDto.getBseCode()!=null)  { 
				jpaQry.setParameter("bseCode", criteriaDto.getBseCode());
				countQry.setParameter("bseCode", criteriaDto.getBseCode());
			}
			if (criteriaDto.getNseCode()!=null)  { 
				jpaQry.setParameter("nseCode", criteriaDto.getNseCode());
				countQry.setParameter("nseCode", criteriaDto.getNseCode());
			}
			
			if (criteriaDto.getMinCmp()!=null)  { 
				jpaQry.setParameter("minCmp", criteriaDto.getMinCmp());
				countQry.setParameter("minCmp", criteriaDto.getMinCmp());
			}
			if (criteriaDto.getMaxCmp()!=null)  { 
				jpaQry.setParameter("maxCmp", criteriaDto.getMaxCmp());
				countQry.setParameter("maxCmp", criteriaDto.getMaxCmp());
			}			
			if (criteriaDto.getMinEps()!=null)  { 
				jpaQry.setParameter("minEps", criteriaDto.getMinEps());
				countQry.setParameter("minEps", criteriaDto.getMinEps());
			}
			if (criteriaDto.getMaxEps()!=null)  { 
				jpaQry.setParameter("maxEps", criteriaDto.getMaxEps());
				countQry.setParameter("maxEps", criteriaDto.getMaxEps());
			}			
			if (criteriaDto.getMinPe()!=null)  { 
				jpaQry.setParameter("minPe", criteriaDto.getMinPe());
				countQry.setParameter("minPe", criteriaDto.getMinPe());
			}
			if (criteriaDto.getMaxPe()!=null)  { 
				jpaQry.setParameter("maxPe", criteriaDto.getMaxPe());
				countQry.setParameter("maxPe", criteriaDto.getMaxPe());
			}
			
			if (criteriaDto.getAverageVolume()!=null)  { 
				jpaQry.setParameter("averageVolume", criteriaDto.getAverageVolume());
				countQry.setParameter("averageVolume", criteriaDto.getAverageVolume());
			}			
			if (criteriaDto.getSelectedWatchlist()!=null)  { 
				jpaQry.setParameter("watchlistId", criteriaDto.getSelectedWatchlist());
				countQry.setParameter("watchlistId", criteriaDto.getSelectedWatchlist());
			}
			if (criteriaDto.getPageNumber()!=null) {
				System.out.println("First result="+(criteriaDto.getPageNumber()-1)*criteriaDto.getRecordPerPage());
				jpaQry.setFirstResult((criteriaDto.getPageNumber()-1)*criteriaDto.getRecordPerPage());			}
			if (criteriaDto.getRecordPerPage()!=null) {	
				System.out.println("criteriaDto.getRecordPerPage()="+criteriaDto.getRecordPerPage());
				jpaQry.setMaxResults(criteriaDto.getRecordPerPage());
			}			
			List<IntradaySnapshotData> analysisList = (List<IntradaySnapshotData>)jpaQry.getResultList();
			retMap.put("Result", analysisList);
			Long recordsCount = (Long)countQry.getSingleResult();
			System.out.println("recordsCount="+recordsCount);
			retMap.put("RecordsCount", recordsCount);
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return retMap;	
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchIntradayBTSTByCriteria(IntradaySnapshotSearchCriteriaDTO criteriaDto) throws BusinessException {

		
		log.debug(" DAO-HEADER: searchIntradayBTSTByCriteria");
		
		Map<String, Object> retMap = new HashMap<String, Object>();;
		
		try {
			StringBuffer query = new StringBuffer();			
			boolean whereAdded = false;			
			
			if (criteriaDto.getDataDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.signalDate = :dataDate");	
			}
						
			StringBuffer countQuery = new StringBuffer(query.toString()); //Order by and type not required for count query
			        	
			StringBuffer internalScripQuery = new StringBuffer();
			StringBuffer internalScripCountQuery = new StringBuffer();
			
			if (!whereAdded) { 
				internalScripQuery.append(" WHERE "); 
				internalScripCountQuery.append(" WHERE ");
				whereAdded=true; 
			} else { 
				internalScripQuery.append(" AND "); 
				internalScripCountQuery.append(" AND "); 
			}
			internalScripQuery.append(" asRes.scrip.id in (select id from Scrip scrInternal ");
			internalScripCountQuery.append(" asRes.scrip.id in (select id from Scrip scrInternal ");
			boolean internalWhereAdded = false;	
								
			if (criteriaDto.getMinCmp()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCmp >= :minCmp");
				internalScripCountQuery.append(" scrInternal.bseCmp >= :minCmp");
			}
			if (criteriaDto.getMaxCmp()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCmp <= :maxCmp");
				internalScripCountQuery.append(" scrInternal.bseCmp <= :maxCmp");
			}			
			if (criteriaDto.getMinEps()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.eps >= :minEps");
				internalScripCountQuery.append(" scrInternal.eps >= :minEps");
			}
			if (criteriaDto.getMaxEps()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.eps <= :maxEps");
				internalScripCountQuery.append(" scrInternal.eps <= :maxEps");
			}			
			if (criteriaDto.getMinPe()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.pe >= :minPe");	
				internalScripCountQuery.append(" scrInternal.pe >= :minPe");
			}
			if (criteriaDto.getMaxPe()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.pe <= :maxPe");	
				internalScripCountQuery.append(" scrInternal.pe <= :maxPe");
			}			
			
			if (criteriaDto.getAverageVolume()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.nseAverageVolume >= :averageVolume");	
				internalScripCountQuery.append(" scrInternal.nseAverageVolume >= :averageVolume");
			}	
			if (criteriaDto.getSelectedWatchlist()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.id in (select scrip.id from WatchlistItem WL where watchlist.id = :watchlistId)");	
				internalScripCountQuery.append(" scrInternal.id in (select scrip.id from WatchlistItem WL where watchlist.id = :watchlistId)");
			}
			internalScripQuery.append(")"); //Close internal sql query
			internalScripCountQuery.append(")");
			
			System.out.println("criteriaDto.getOrderBy()="+criteriaDto.getOrderBy()+" ordertype="+criteriaDto.getOrderType());
			if (criteriaDto.getOrderBy()!=null)  { 
				internalScripQuery.append(" ORDER BY " + criteriaDto.getOrderBy());
				if (criteriaDto.getOrderType()==null) internalScripQuery.append(" ASC "); 
				else internalScripQuery.append(" "+ criteriaDto.getOrderType() + " "); 
			}
			
			Query jpaQry = this.entityManager.createQuery("SELECT OBJECT(asRes) FROM IntradayBTSTAutoscanResult asRes " + query.toString() + internalScripQuery );
			Query countQry = this.entityManager.createQuery("SELECT count(*) FROM IntradayBTSTAutoscanResult asRes " + countQuery.toString() + internalScripCountQuery);
			
			if (criteriaDto.getDataDate()!=null)  { 
				jpaQry.setParameter("dataDate", criteriaDto.getDataDate());
				countQry.setParameter("dataDate", criteriaDto.getDataDate());
			}
									
			if (criteriaDto.getMinCmp()!=null)  { 
				jpaQry.setParameter("minCmp", criteriaDto.getMinCmp());
				countQry.setParameter("minCmp", criteriaDto.getMinCmp());
			}
			if (criteriaDto.getMaxCmp()!=null)  { 
				jpaQry.setParameter("maxCmp", criteriaDto.getMaxCmp());
				countQry.setParameter("maxCmp", criteriaDto.getMaxCmp());
			}			
			if (criteriaDto.getMinEps()!=null)  { 
				jpaQry.setParameter("minEps", criteriaDto.getMinEps());
				countQry.setParameter("minEps", criteriaDto.getMinEps());
			}
			if (criteriaDto.getMaxEps()!=null)  { 
				jpaQry.setParameter("maxEps", criteriaDto.getMaxEps());
				countQry.setParameter("maxEps", criteriaDto.getMaxEps());
			}			
			if (criteriaDto.getMinPe()!=null)  { 
				jpaQry.setParameter("minPe", criteriaDto.getMinPe());
				countQry.setParameter("minPe", criteriaDto.getMinPe());
			}
			if (criteriaDto.getMaxPe()!=null)  { 
				jpaQry.setParameter("maxPe", criteriaDto.getMaxPe());
				countQry.setParameter("maxPe", criteriaDto.getMaxPe());
			}
			
			if (criteriaDto.getAverageVolume()!=null)  { 
				jpaQry.setParameter("averageVolume", criteriaDto.getAverageVolume());
				countQry.setParameter("averageVolume", criteriaDto.getAverageVolume());
			}			
			if (criteriaDto.getSelectedWatchlist()!=null)  { 
				jpaQry.setParameter("watchlistId", criteriaDto.getSelectedWatchlist());
				countQry.setParameter("watchlistId", criteriaDto.getSelectedWatchlist());
			}
			if (criteriaDto.getPageNumber()!=null) {
				System.out.println("First result="+(criteriaDto.getPageNumber()-1)*criteriaDto.getRecordPerPage());
				jpaQry.setFirstResult((criteriaDto.getPageNumber()-1)*criteriaDto.getRecordPerPage());			}
			if (criteriaDto.getRecordPerPage()!=null) {	
				System.out.println("criteriaDto.getRecordPerPage()="+criteriaDto.getRecordPerPage());
				jpaQry.setMaxResults(criteriaDto.getRecordPerPage());
			}			
			List<IntradayBTSTAutoscanResult> analysisList = (List<IntradayBTSTAutoscanResult>)jpaQry.getResultList();
			retMap.put("Result", analysisList);
			Long recordsCount = (Long)countQry.getSingleResult();
			System.out.println("recordsCount="+recordsCount);
			retMap.put("RecordsCount", recordsCount);
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return retMap;	
	}
	
	
	public Map<Date, ScripEOD> getNSESnapshotData(String scripCode, Date fromDate, Date toDate, String exchangeCode) throws BusinessException {
		log.debug(" DAO-HEADER: getEquityEodData(String scripCode: "+ scripCode+" uniquecode="+scripCode);

		Map returnMap = new HashMap<Date, ScripEOD>();
		
		try {
			Long scripId = null;
			ScripDao scrDao = new ScripDao(entityManager);
			if (exchangeCode.equalsIgnoreCase("bse")) {
				scripId = scrDao.getScripByBseCodeOrName(scripCode);
			} else {
				scripId = scrDao.getScripByNseCodeOrSeries(scripCode, "EQ");
			}
			
			String query = "select data_date, mean_price, total_volume, cf_weightage from intraday_snapshot_data "
				+ " where f_scrip = ? and data_date >= ? and data_date <=? order by data_date asc";	
			
			Query q = entityManager.createNativeQuery(query);		
			
			q.setParameter( 1, scripId);
			q.setParameter( 2, fromDate);
			q.setParameter( 3, toDate);
			
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			float previousMean = 0;
			//float cfWeightage = 0;
			while (iter.hasNext()) {				
				Object[] rowdata = iter.next();
				ScripEOD aDto = new ScripEOD();
				aDto.setDataDate((Date)rowdata[0]);
				aDto.setMeanPrice((Float)rowdata[1]);
				aDto.setVolume(((BigInteger)rowdata[2]).longValue());
				aDto.setCfWeightage((Float)rowdata[3]);
				
				returnMap.put(aDto.getDataDate(), aDto);
				
				previousMean = aDto.getMeanPrice();
			}			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return returnMap;
	}
	
	public List<EodData> getIndexViewResult(Date dataDate, String exchange, String orderBy, String orderType) throws BusinessException {

		List<EodData> returnList = new ArrayList<EodData>();
		try {
			String hqlQry = "SELECT OBJECT(asRes) FROM " ;
			if (exchange.equalsIgnoreCase("BSE")) hqlQry = hqlQry + " BSEEodData"; 
			else hqlQry = hqlQry + " NSEEodData";
			hqlQry = hqlQry + " asRes WHERE asRes.dataDate=:dataDate";
			
			if (exchange.equalsIgnoreCase("BSE")) hqlQry = hqlQry + " and asRes.scrip.bseCode is not null";
			else hqlQry = hqlQry + " and asRes.scrip.nseCode is not null";
			hqlQry = hqlQry + " and asRes.scrip.scripType ='IN'";
			hqlQry = hqlQry + " and asRes.stochasticValue is not null";
			hqlQry = hqlQry + " order by " + orderBy + " " + orderType;
			System.out.println("hqlQry="+hqlQry);
			
			Query jpaQry = this.entityManager.createQuery(hqlQry);
			jpaQry.setParameter("dataDate", dataDate);
			returnList = (List<EodData>)jpaQry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return returnList;
	}
	
	public List<EodData> getIndexScripsResult(Long indexId, String exchange, Date dataDate, String orderBy, String orderType) throws BusinessException {
		System.out.println("In DAO indexId="+indexId+" dataDate="+dataDate+" orderBy="+orderBy+" orderType="+orderType);
		List<EodData> returnList = new ArrayList<EodData>();
		try {
			String hqlQry = "SELECT OBJECT(asRes) FROM " ;
			if (exchange.equalsIgnoreCase("BSE")) hqlQry = hqlQry + " BSEEodData"; 
			else hqlQry = hqlQry + " NSEEodData";
			hqlQry = hqlQry + " asRes WHERE asRes.dataDate=:aDataDate";
			
			if (exchange.equalsIgnoreCase("BSE")) hqlQry = hqlQry + " and asRes.scrip.bseCode is not null";
			else hqlQry = hqlQry + " and asRes.scrip.nseCode is not null";
			
			hqlQry = hqlQry + " and asRes.scrip.id in (select scrip.id from IndexScrips indexTable where indexTable.indexScrip.id = :indexId)";
			
			hqlQry = hqlQry + " order by " + orderBy + " " + orderType;
			System.out.println("hqlQry="+hqlQry);
			
			Query jpaQry = this.entityManager.createQuery(hqlQry);
			jpaQry.setParameter("aDataDate", dataDate);
			jpaQry.setParameter("indexId", indexId);
			returnList = (List<EodData>)jpaQry.getResultList();
			System.out.println("In Dao returnList size="+returnList.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return returnList;
	}
	
	public List<TreePerformance> getTreePerformanceForScrip(Long sourceScripId, String orderBy, String orderType) throws BusinessException {

		List<TreePerformance> returnList = new ArrayList<TreePerformance>();
		try {
			String hqlQry = "SELECT OBJECT(asRes) FROM TreePerformance asRes WHERE asRes.sourceScrip.id=:srcScripId";			
			hqlQry = hqlQry + " order by " + orderBy + " " + orderType;
			System.out.println("hqlQry="+hqlQry);
			
			Query jpaQry = this.entityManager.createQuery(hqlQry);
			jpaQry.setParameter("srcScripId", sourceScripId);
			jpaQry.setMaxResults(20);
			returnList = (List<TreePerformance>)jpaQry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return returnList;
	}
	
	public Float getPriceForScripOn(Long scripId, Date dataDate, String fieldName)	throws BusinessException {	
		//log.debug(" DAO-HEADER: getScripByBseCode(String bseScripCode: " + bseScripCode);		
		Float retValue = null;
		try {
			String sqlQry = "select " + fieldName +" from nse_eq_eod_data where f_scrip="+scripId+" and data_date = '" + dataDate +"'";
			retValue = (Float)this.entityManager.createNativeQuery(sqlQry).getSingleResult();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No scrip found with these code ");
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		return retValue;
	}
	
	public Float getPriceForScripOn(Long scripId, Date dataDate, String fieldName, int futureDays)	throws BusinessException {	
		//log.debug(" DAO-HEADER: getScripByBseCode(String bseScripCode: " + bseScripCode);		
		Float retValue = null;
		try {
			Calendar localCalendar = Calendar.getInstance();
			localCalendar.setTime(dataDate);
			localCalendar.add(Calendar.DATE, futureDays);
			String sqlQry = "select max(" + fieldName +") from nse_eq_eod_data where f_scrip="+scripId+" " +
					" and data_date >= '" + dataDate +"' and data_date <= '" + localCalendar.getTime() +"'";
			
			
			retValue = (Float)this.entityManager.createNativeQuery(sqlQry).getSingleResult();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No scrip found with these code ");
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		return retValue;
	}
	
	public void storeTreePerformance(Long srcScripId, Long tgtScripId, double srcRoi, double tgtRoi)	throws BusinessException {	
		System.out.println("sourceScripId="+srcScripId+" tgtDtoId="+tgtScripId+" roiPercentage="+srcRoi+" targetRoiPercentage="+tgtRoi);
		try {
			String sqlStr = "update tree_performance set joint_increment=joint_increment + 1, total_source_gain=total_source_gain + " + srcRoi + ", total_target_gain=total_target_gain+" + tgtRoi +" where f_source_scrip=" + srcScripId + " and f_target_scrip = " + tgtScripId + "";
			
			int resultCount = this.entityManager.createNativeQuery(sqlStr).executeUpdate();	
			if (resultCount==0) {
				sqlStr = "insert into tree_performance (id, f_source_scrip, f_target_scrip, joint_increment, total_source_gain, total_target_gain) values( nextval('tree_performance_id_seq'), " + srcScripId + "," + tgtScripId + ",1," + srcRoi+"," + tgtRoi +")";
				this.entityManager.createNativeQuery(sqlStr).executeUpdate();
			}
		} catch (NoResultException e) {
			log.debug(" -- HERE : No scrip found with these code ");
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
	}
	
	public List<FinancialResult> getFinancialResult(Long scripId, boolean useConsolidatedResult) throws BusinessException {

		List<FinancialResult> returnList = new ArrayList<FinancialResult>();
		try {
			if (useConsolidatedResult) {
				String hqlQry = "SELECT OBJECT(asRes) FROM ConsolidatedFinancialResult asRes WHERE asRes.scrip.id=:scripId and asRes.financiaReportQuarterId>0 and asRes.netProfit is not null order by asRes.financiaReportQuarterId";			
				Query jpaQry = this.entityManager.createQuery(hqlQry);
				jpaQry.setParameter("scripId", scripId);
				List<ConsolidatedFinancialResult> consolidateList = (List<ConsolidatedFinancialResult>)jpaQry.getResultList();
				
				// Get Standalone result as well
				hqlQry = "SELECT OBJECT(asRes) FROM FinancialResult asRes WHERE asRes.scrip.id=:scripId and asRes.financiaReportQuarterId>0 and asRes.netProfit is not null order by asRes.financiaReportQuarterId";			
				jpaQry = this.entityManager.createQuery(hqlQry);
				jpaQry.setParameter("scripId", scripId);
				List<FinancialResult> standaloneList = (List<FinancialResult>)jpaQry.getResultList();
				
				int consolidatedPtr = consolidateList.size()-1;
				int standalonePtr = standaloneList.size()-1;
				
				while(standalonePtr>=0 && consolidatedPtr>=0) {
					//System.out.println("Standalone="+standaloneList.get(standalonePtr).getFinanciaReportQuarterId() + " Consolidate="+ consolidateList.get(consolidatedPtr).getFinanciaReportQuarterId());
					if (standaloneList.get(standalonePtr).getFinanciaReportQuarterId().equals(consolidateList.get(consolidatedPtr).getFinanciaReportQuarterId())) {
						FinancialResult resultToAdd = new FinancialResult(consolidateList.get(consolidatedPtr));
						//System.out.println("In resultToAdd.getPbt="+resultToAdd.getPbt());
						resultToAdd.setIsConsolidated(true);
						returnList.add(0, resultToAdd);
						consolidatedPtr--;
						standalonePtr--;
					} else if (consolidateList.get(consolidatedPtr).getFinanciaReportQuarterId().intValue() > standaloneList.get(standalonePtr).getFinanciaReportQuarterId().intValue() ) {
						FinancialResult resultToAdd = new FinancialResult(consolidateList.get(consolidatedPtr));
						//System.out.println("In resultToAdd.pbt="+resultToAdd.getPbt());
						resultToAdd.setIsConsolidated(true);
						returnList.add(0, resultToAdd);
						consolidatedPtr--;
					} else {
						FinancialResult resultToAdd = standaloneList.get(standalonePtr);
						resultToAdd.setIsConsolidated(false);
						returnList.add(0, resultToAdd);
						standalonePtr--;
					}
				}
				if (standalonePtr>=0) {
					for(int i=standalonePtr;i>=0;i--) {
						FinancialResult resultToAdd = standaloneList.get(i);
						resultToAdd.setIsConsolidated(false);
						returnList.add(0, resultToAdd);
					}
				} else if (consolidatedPtr>=0) {
					for(int i=consolidatedPtr;i>=0;i--) {
						FinancialResult resultToAdd = new FinancialResult(consolidateList.get(i));
						resultToAdd.setIsConsolidated(true);
						returnList.add(0, resultToAdd);
					}
				}
				// Todo in case if Consolidated data missing for selective qtr, need to fetch from standalone result. 
			} else {
				String hqlQry = "SELECT OBJECT(asRes) FROM FinancialResult asRes WHERE asRes.scrip.id=:scripId and asRes.financiaReportQuarterId>0 and asRes.netProfit is not null order by asRes.financiaReportQuarterId";			
				Query jpaQry = this.entityManager.createQuery(hqlQry);
				jpaQry.setParameter("scripId", scripId);
				returnList = (List<FinancialResult>)jpaQry.getResultList();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return returnList;
	}
	
	public List<Scrip> getLatestFinancialResultDeclaredScrips(String orderBy, String orderType) throws BusinessException {

		List<Scrip> returnList = new ArrayList<Scrip>();
		try {
			String hqlQry = "SELECT OBJECT(asRes) FROM Scrip asRes where asRes.resultDate is not null and asRes.changeProfit is not null order by " + orderBy + " " + orderType + ", asRes.id";			
			
			System.out.println("hqlQry="+hqlQry);			
			Query jpaQry = this.entityManager.createQuery(hqlQry);
			jpaQry.setMaxResults(50);
			returnList = (List<Scrip>)jpaQry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return returnList;
	}
	
	public List<IntradaySnapshotData> getSnapshotData(Long scripId, Date starDate, Date endDate) throws BusinessException {

		List<IntradaySnapshotData> returnList = new ArrayList<IntradaySnapshotData>();
		try {
			String hqlQry = "SELECT OBJECT(asRes) FROM IntradaySnapshotData asRes where asRes.scrip.id = :scripId and asRes.dataDate >= :trendStartDate and asRes.dataDate <= :trendEndDate order by asRes.dataDate";			
			
			System.out.println("hqlQry="+hqlQry);			
			Query jpaQry = this.entityManager.createQuery(hqlQry);
			jpaQry.setParameter("scripId", scripId);
			jpaQry.setParameter("trendStartDate", starDate);
			jpaQry.setParameter("trendEndDate", endDate);
			
			returnList = (List<IntradaySnapshotData>)jpaQry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Sector> getAllSectors() throws BusinessException {
		
		log.info(" DAO-HEADER: getAllSectors()");
		List<Sector> sectors = null;
		
		try {
			String jpaQry = "SELECT OBJECT(sect) FROM Sector sect where sect.name is not null order by sect.name asc";
			Query query = this.entityManager.createQuery(jpaQry);			
			sectors = (List<Sector>)query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return sectors;
	}
	
	@SuppressWarnings("unchecked")
	public List<NSEEodData> getNSEEodData(String nseCode, String uptoDateStr) throws BusinessException {
		System.out.println("In DataDao getNSEEodData()-nseCode="+nseCode+" uptoDateStr="+uptoDateStr);
		List<NSEEodData> eodData = null;
		
		try {
			Calendar cal = Calendar.getInstance();
			Date upperDate = SPConstants.SPCORE_DATE_FORMAT.parse(uptoDateStr);
			cal.setTime(upperDate);
			cal.add(Calendar.DATE, -7);
			Date lowerDate = cal.getTime();
			
			eodData = (List<NSEEodData>)this.entityManager.createNamedQuery("NSEEodData.getNSEEodDataByNseCodeAndDateRangeDesc")
					.setParameter("nseCode", nseCode)
					.setParameter("fromDate", lowerDate)
					.setParameter("toDate", upperDate)
					.getResultList();
			
			System.out.println(" -- eodData : size = " + eodData.size());
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		if (eodData != null) {
			log.debug(" -- eodData : size = " + eodData.size());
		}
		return eodData;
	}
	
	@SuppressWarnings("unchecked")
	public List<ScripEOD> getTickData(String symbol, Date startTime, Date endTime) throws BusinessException {
		List<ScripEOD> retTickList = new ArrayList<ScripEOD>();
		SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String instrumentToken = (String)this.entityManager.createNativeQuery("select instrument_token from scrips where series_type='EQ' and instrument_token is not null and nse_code='"+symbol+"'").getSingleResult();
			System.out.println("instrumentToken = "+instrumentToken);
			
			String query = "select quote_time, last_traded_price, volume_traded_today from zerodha_intraday_streaming_data"
					+ " where instrument_token='" + instrumentToken + "' "
					+ " and quote_time >= '" + dataDateFormat.format(startTime)+ "' "
					+ " and quote_time <= '" + dataDateFormat.format(endTime)+ "' "
					+ " order by quote_time ";	
				
				Query q = entityManager.createNativeQuery(query);	
				
				List<Object[]> listResults = q.getResultList();
				Iterator<Object[]> iter = listResults.iterator();
				long lastTradedVolume = 0;
				boolean setLastTradedVolume = false;
				while (iter.hasNext()) {
					Object[] rowdata = iter.next();
					long currentTradedVolume = ((Float)rowdata[2]).longValue();					
					if (setLastTradedVolume==true) {
						ScripEOD aDto = new ScripEOD();
						aDto.setDataDate((Date)rowdata[0]);
						aDto.setClosePrice((Float)rowdata[1]);
						aDto.setVolume(currentTradedVolume-lastTradedVolume);
						retTickList.add(aDto);
					} else {
						setLastTradedVolume = true;
					}
					lastTradedVolume = currentTradedVolume;
				}	
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retTickList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ZeordhaDomVO> getDomData(Long scripId, Date fromTime, Date toTime) throws BusinessException {
		List<ZeordhaDomVO> retTickList = new ArrayList<ZeordhaDomVO>();
		
		SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			
			String query = "SELECT streamData.last_traded_price as ltp, streamData.diff_buy_sell_volume, streamData.quote_time,"
					+ " streamData.last_traded_quantity, streamData.volume_traded_today, streamData.total_buyQuantity, streamData.total_sellQuantity"
					+ " FROM zerodha_intraday_streaming_data streamData"
					+ " where streamData.instrument_token=(select cast(instrument_token as integer) from scrips where id=" + scripId + ") "
					+ " and streamData.quote_time>='" + dataDateFormat.format(fromTime)+ "'"
					+ "	and streamData.quote_time<='" + dataDateFormat.format(toTime)+ "'"
					+ "	order by streamData.quote_time, streamData.id";	
				
				Query q = entityManager.createNativeQuery(query);	
				
				List<Object[]> listResults = q.getResultList();
				Iterator<Object[]> iter = listResults.iterator();
				
				while (iter.hasNext()) {
					Object[] rowdata = iter.next();
					ZeordhaDomVO aVO = new ZeordhaDomVO();
					
					 aVO.setLtp((Float)rowdata[0]);
					 aVO.setVolumeBuySellDiff((Float)rowdata[1]);
					 aVO.setTickQuoteTime((Date)rowdata[2]);
					 
					 aVO.setLastTradedQty((Float)rowdata[3]);
					 
					 aVO.setVolumeTradedToday((Float)rowdata[4]);
					 aVO.setBuyTotal((Float)rowdata[5]);
					 aVO.setSellTotal((Float)rowdata[6]);
					retTickList.add(aVO);					
				}	
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retTickList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ZeordhaDomVO> getDomDataForIndex(Long scripId, Date fromTime, Date toTime) throws BusinessException {
		List<ZeordhaDomVO> retTickList = new ArrayList<ZeordhaDomVO>();
		
		SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			
			String query = "SELECT streamData.last_traded_price as ltp, streamData.diff_buy_sell_volume, streamData.quote_time,"
					+ " streamData.last_traded_quantity, streamData.volume_traded_today"
					+ " FROM zerodha_intraday_streaming_data streamData "
					+ " where streamData.instrument_token=(select cast(instrument_token as integer) from scrips where id=" + scripId + ") "
					+ " and streamData.quote_time>='" + dataDateFormat.format(fromTime)+ "'"
					+ "	and streamData.quote_time<='" + dataDateFormat.format(toTime)+ "'"
					+ "	order by streamData.quote_time, streamData.id";	
				
				Query q = entityManager.createNativeQuery(query);	
				
				List<Object[]> listResults = q.getResultList();
				Iterator<Object[]> iter = listResults.iterator();
				long lastTradedVolume = 0;
				boolean setLastTradedVolume = false;
				while (iter.hasNext()) {
					Object[] rowdata = iter.next();
					ZeordhaDomVO aVO = new ZeordhaDomVO();
					 
					 aVO.setLtp((Float)rowdata[0]);
					 aVO.setVolumeBuySellDiff((Float)rowdata[1]);
					 aVO.setTickQuoteTime((Date)rowdata[2]);					 
					 aVO.setBuyCV(0f);
					 aVO.setBuyMean(0f);
					 aVO.setBuyStddev(0f);
					 aVO.setBuyVvwap(0f);
					 aVO.setSellCV(0f);
					 aVO.setSellMean(0f);
					 aVO.setSellStddev(0f);
					 aVO.setSellVvwap(0f);
					 aVO.setLastTradedQty((Float)rowdata[3]);
					 aVO.setBuyTotal(0f);
					 aVO.setSellTotal(0f);
					 aVO.setVolumeTradedToday((Float)rowdata[4]);
					 
					retTickList.add(aVO);					
				}	
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retTickList;
	}
	
	public String getOISpikeData(String indexname, String forDate, int nooftopois, boolean filterOptionWorth)  throws BusinessException {
		log.info("In getOISpikeData forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OISpike" + indexname + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,CE-OI,PE-OI,IndexLtp\r\n");
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(stdFormat.parse(forDate));
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.set(Calendar.MINUTE, 14);
			String dateStrBegin = postgresFormat.format(cal.getTime());	
			
			cal.set(Calendar.MINUTE, 30);
			String endStrikeForSpot = postgresFormat.format(cal.getTime());	
			
			cal.set(Calendar.HOUR_OF_DAY, 16);			
			String dateStrEnd = postgresFormat.format(cal.getTime());
			
            String shortIndexName = indexname.startsWith("NIFTY")?"NIFTY 50":indexname.startsWith("BANKNIFTY")?"NIFTY BANK":"NIFTY FIN SERVICE";
            
			String fetchSql = "select record_time, changeinceoi, changeinpeoi, indexltp from option_oi_rate_of_change where indexname like '" + shortIndexName + "' and filteroptionworth=" + filterOptionWorth + " and nooftopois=" + nooftopois+ " and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float ceOI = (Float) rowdata[1];
				float peOI = (Float) rowdata[2];
				float indexLtp = (Float) rowdata[3];
				
				writer.write(postgresFormat.format(quoteTime)+"," +ceOI+","+peOI+","+indexLtp+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getOption1MPremiumDecay(String forDate, String optionnames) throws BusinessException {
		String csvFilename = "D:\\temp\\junk\\Option1MPremiumDecay" ;
		try {
			SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat postgresShortFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			csvFilename = csvFilename + postgresShortFormat.format(stdFormat.parse(forDate)) + ".csv";
			String[] allPptionnames = optionnames.split(",");
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexLtp");
            for(String optiononame : allPptionnames) {
            	writer.write(","+optiononame+"-LTP");
            }
            writer.write(",Total Premium, Total TimeValue\r\n");
            
            Calendar cal = Calendar.getInstance();
			cal.setTime(stdFormat.parse(forDate));
			
			cal.set(Calendar.HOUR_OF_DAY, 15);
			cal.set(Calendar.MINUTE, 30);
			cal.set(Calendar.SECOND, 0);
			Date endTime = cal.getTime();
			
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.set(Calendar.MINUTE, 20);
			Date beginTime = cal.getTime();
			
			Date lastRecordDate = null;
			
			Map<String, List<ZerodhaOptionCandleVO>> alldataMap = new HashMap<String, List<ZerodhaOptionCandleVO>>();
			for(String optiononame : allPptionnames) {
				List<ZerodhaOptionCandleVO> optionGreekList = new ArrayList<ZerodhaOptionCandleVO>();
				
				String fetchSql = "select quote_time, close_price, underlying_index_value, time_value from option_1m_candle where optionname = '" + optiononame + "' and quote_time >= '" + postgresFormat.format(beginTime) + "' and quote_time <= '" + postgresFormat.format(endTime) + "' order by quote_time";
				Query q = entityManager.createNativeQuery(fetchSql);	
				List<Object[]> listResults = q.getResultList();
				Iterator<Object[]> iter = listResults.iterator();
				while (iter.hasNext()) {
					Object[] rowdata = iter.next();
					Date quoteTime = (Timestamp) rowdata[0];
					float ltp = (Float) rowdata[1];
					float underlyingIndexValue = (Float) rowdata[2];
					float timeValue = (Float) rowdata[3];
					
					ZerodhaOptionCandleVO aVo = new ZerodhaOptionCandleVO();
					aVo.setQuoteTime(quoteTime);
					aVo.setClosePrice(ltp);
					aVo.setUnderlyingIndexValue(underlyingIndexValue);
					aVo.setTimeValue(timeValue);
					if (optionGreekList.size()>0 &&
							optionGreekList.get(optionGreekList.size()-1).getQuoteTime().getHours()==quoteTime.getHours() && optionGreekList.get(optionGreekList.size()-1).getQuoteTime().getMinutes()==quoteTime.getMinutes() ) {
						log.info("Same minute data repeat skipping" );
					} else {
						optionGreekList.add(aVo);
					}
					lastRecordDate = quoteTime;
				}
				alldataMap.put(optiononame, optionGreekList);
			}
			
			String firstOptionName = null;
            do {
            	float currentTotalOptionPrice = 0f;
            	float currentTotalTimeValue = 0f;
            	for(String optiononame : allPptionnames) {
            		if (firstOptionName==null) firstOptionName = optiononame;
            		ZerodhaOptionCandleVO aVo = alldataMap.get(optiononame).get(0);
            		
            		log.info("~~~~ Time aVo.getQuoteTime()"+aVo.getQuoteTime()+" cal.getTime()"+cal.getTime());
            		
            		if (aVo!=null) {
	            		if (optiononame.equals(firstOptionName)) {
	            			writer.write(postgresFormat.format(aVo.getQuoteTime())+","+aVo.getUnderlyingIndexValue());
	            		}
	            		if ( (aVo.getQuoteTime().getHours()==cal.getTime().getHours()) && (aVo.getQuoteTime().getMinutes()==cal.getTime().getMinutes()) ) {
	            			currentTotalOptionPrice = currentTotalOptionPrice + aVo.getClosePrice();
	            			currentTotalTimeValue = currentTotalTimeValue + aVo.getTimeValue();
	            			writer.write("," + aVo.getClosePrice());
	            			alldataMap.get(optiononame).remove(0);
	            		} else {
	            			log.info("~~~~ Time mismatch aVo.getQuoteTime()"+aVo.getQuoteTime()+" cal.getTime()"+cal.getTime());
	            			writer.write(",,,,,,");
	            		}
            		} else {
            			writer.write(",,,,,,");
            		}
            	}
            	writer.write("," + (currentTotalOptionPrice) + "," + currentTotalTimeValue );
            	writer.write("\r\n");
            	cal.add(Calendar.MINUTE, 1);
            } while(cal.getTime().before(lastRecordDate));
            writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getDOMSummary(String forDate, String scripName) throws BusinessException {
		String csvFilename = "D:\\temp\\junk\\DomSummary"+scripName ;
		try {
			SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat postgresShortFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			csvFilename = csvFilename + postgresShortFormat.format(stdFormat.parse(forDate)) + ".csv";
			FileWriter writer = new FileWriter(csvFilename);
			writer.write("QuoteTime,Ltp,TotalBuyQty,TotalSellQty,Diff\r\n");
			
			SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			String dateStrBegin = "";
			String dateStrEnd = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 14);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 16);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select quote_time, ltp, totalbuyquantity, totalsellquantity  from zerodha_intrady_tick_flat_table where trading_symbol = '" + scripName + "' and quote_time >= '" + dateStrBegin + "' and quote_time <= '" + dateStrEnd + "' order by quote_time";
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			float prevDiff = 0;
			Date currentCandleQuoteTime = null;
			float currentCandleLtp = 0f;
			float currentCandleTotalbuyquantity = 0f;
			float currentCandleTotalsellquantity = 0f;
			float currentCandleDiff = 0f;
			
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];
				float ltp = (Float) rowdata[1];
				float totalbuyquantity = (Float) rowdata[2];
				float totalsellquantity = (Float) rowdata[3];
				float diff = (totalbuyquantity-totalsellquantity);
				//writer.write(postgresFormat.format(quoteTime)+","+ltp+","+totalbuyquantity+","+ totalsellquantity );
				if (prevDiff!=0f) {
					float changeInDiff = (diff-prevDiff)*100f/prevDiff;
					if (changeInDiff>-25f && changeInDiff<25f) {
						//writer.write(","+diff);
						prevDiff = diff;
					} else {
						//writer.write(","+prevDiff);
					}
				} else {
					//writer.write(","+diff);
					prevDiff = diff;
				}
				//writer.write("\r\n");
				
				if (currentCandleQuoteTime==null) {
					currentCandleQuoteTime = quoteTime;
					currentCandleLtp = ltp;
					currentCandleTotalbuyquantity = totalbuyquantity;
					currentCandleTotalsellquantity = totalsellquantity;
					currentCandleDiff = diff;
				} else {
					if (currentCandleQuoteTime.getHours()==quoteTime.getHours() && currentCandleQuoteTime.getMinutes()==quoteTime.getMinutes()) {
						currentCandleLtp = ltp;
						currentCandleTotalbuyquantity = totalbuyquantity;
						currentCandleTotalsellquantity = totalsellquantity;
						currentCandleDiff = diff;
					} else {
						writer.write(postgresFormat.format(currentCandleQuoteTime)+","+currentCandleLtp+","+currentCandleTotalbuyquantity+","+ currentCandleTotalsellquantity+"," + currentCandleDiff+"\r\n");
						currentCandleQuoteTime = quoteTime;
						currentCandleLtp = ltp;
						currentCandleTotalbuyquantity = totalbuyquantity;
						currentCandleTotalsellquantity = totalsellquantity;
						currentCandleDiff = diff;
					}
				}
				
				
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getOptionCePeIVRatio(String indexname, String forDate, String forDelta, String expiryStr)  throws BusinessException {
		log.info("In getOptionGreeksData forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionCePeIvRatio" + indexname +forDelta+ ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,CePeIvRatio,CeDelta,PeDelta, ThetaDiff"
            		+ ",Delta abs. Diff, Gamma abs. Diff, Vega abs. Diff, Theta abs. Diff, IV abs. Diff, Price abs. Diff"
            		+ "\r\n");
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 14);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 16);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}

			if (expiryStr==null || expiryStr.trim().length()==0) {
				String shortIndexName = "NIFTY";
				if (indexname.contains("BANK")) shortIndexName = "BANKNIFTY";
				if (indexname.contains("FIN")) shortIndexName = "FINNIFTY";
				expiryStr = shortIndexName+getNextExpiryDateForOptionnameStr(shortIndexName, cal.getTime()); 
			}
			
			float forDeltaLow = Float.parseFloat(forDelta) - 0.01f;
			float forDeltaUp  = Float.parseFloat(forDelta) + 0.01f;
			
			String fetchSql = "select record_time, ce_pe_iv_ratio, ce_delta, pe_delta, indexltp, theta_percent_diff"
					+ ", delta_absolute_diff, gamma_absolute_diff, vega_absolute_diff, theta_absolute_diff, iv_absolute_diff, price_absolute_diff"
					+ " from option_cepe_ivratio"
					+ " where for_delta > " + forDeltaLow
					+ " and for_delta < " + forDeltaUp
					+ " and expiry_str_prefix = '" + expiryStr + "'"
					+ " and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float ce_pe_iv_ratio = (Float) rowdata[1];
				float ce_delta = (Float) rowdata[2];
				float pe_delta = (Float) rowdata[3];
				float indexltp = (Float) rowdata[4];
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp+"," +ce_pe_iv_ratio+","+ce_delta+","+pe_delta+","+ (Float) rowdata[5]
						+","+ (Float) rowdata[6]+","+ (Float) rowdata[7]+","+ (Float) rowdata[8]+","+ (Float) rowdata[9]+","+ (Float) rowdata[10]+","+ (Float) rowdata[11]+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}

	public String getOptionTimeValueAnalysis(String indexname, String forDate)  throws BusinessException {
		log.info("In getOptionTimeValueAnalysis forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionTimeValue" + indexname + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,ThisWeekCETimeValue,ThisWeekPETimeValue,ThisWeekPercentDiff,NextWeekCETimeValue,NextWeekPETimeValue,NextWeekPercentDiff,ThisWeekCEThetaValue,ThisWeekPEThetaValue,ThisWeekCEVolume,ThisWeekPEVolume"
            		+ "\r\n");
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 19);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, indexltp, thisweektotalcetimevalue, thisweektotalpetimevalue, nextweektotalcetimevalue, nextweektotalpetimevalue, thisweektotalcethetavalue, thisweektotalpethetavalue"
					+ ", thisweektotalcevolumevalue,thisweektotalpevolumevalue"
					+ " from option_timevalue_analysis"
					+ " where indexname = '" + indexname + "'"
					+ " and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];
				float indexltp = (Float) rowdata[1];
				float thisweektotalcetimevalue = (Float) rowdata[2];
				float thisweektotalpetimevalue = (Float) rowdata[3];
				float nextweektotalcetimevalue = (Float) rowdata[4];
				float nextweektotalpetimevalue = (Float) rowdata[5];
				
				float thisweektotalcethetavalue = (Float) rowdata[6];
				float thisweektotalpethetavalue = (Float) rowdata[7];
				
				float thisweektotalcevolumevalue = (Float) rowdata[8];
				float thisweektotalpevolumevalue = (Float) rowdata[9];
				
				float thisWeekPercentDiff = getPercentDiff(thisweektotalcetimevalue, thisweektotalpetimevalue);
				float nextWeekPercentDiff = getPercentDiff(nextweektotalcetimevalue, nextweektotalpetimevalue);
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp
						+"," +thisweektotalcetimevalue+","+thisweektotalpetimevalue+","+thisWeekPercentDiff
						+"," +nextweektotalcetimevalue+","+nextweektotalpetimevalue+","+nextWeekPercentDiff
						+"," +thisweektotalcethetavalue + "," + thisweektotalpethetavalue
						+"," + thisweektotalcevolumevalue +"," + thisweektotalpevolumevalue
						+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getOptionVegaValueAnalysis(String indexname, String forDate, int noOfTopOis) throws BusinessException {
		log.info("In getOptionTimeValueAnalysis forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionVegaValue" + indexname + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,changeincevega ,changeinpevega,changeincevega5minBack,changeinpevega5minBack,CEWorth,PEWorth,AtmStraddlePremium,CEPEPremiumRatio,ATMPriceDiffPercent" + "\r\n");
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 20);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, indexltp, cetotalvegathen,cetotalveganow,petotalvegathen,petotalveganow,changeincevega ,changeinpevega,changeincevega5min,changeinpevega5min,ceworthincr,peworthincr, atmStraddlePremium, cEPEPremiumRatio, atmPriceDiffPercent"
					+ " from option_vega_movement_analysis"
					+ " where indexname = '" + indexname + "'"
					+ " and nooftopois="+noOfTopOis
					+ " and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];
				float indexltp = (Float) rowdata[1];
//				float cetotalvegathen = (Float) rowdata[2];
//				float cetotalveganow = (Float) rowdata[3];
//				float petotalvegathen = (Float) rowdata[4];
//				float petotalveganow = (Float) rowdata[5];
				
				float changeincevega = (Float) rowdata[6];
				float changeinpevega = (Float) rowdata[7];
				
				float changeincevega5minback = (Float) rowdata[8];
				float changeinpevega5minback = (Float) rowdata[9];
				
				float ceworthincr = (Float) rowdata[10];
				float peworthincr = (Float) rowdata[11];
				float atmStraddlePremium = (Float) rowdata[12];
				float cEPEPremiumRatio = (Float) rowdata[13];
				float atmPriceDiffPercent = 0f;
				if (rowdata[14]!=null) atmPriceDiffPercent = (Float) rowdata[14];
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp
						
						+","+changeincevega+","+changeinpevega
						+","+changeincevega5minback+"," +changeinpevega5minback
						+","+ceworthincr+","+peworthincr
						+"," +atmStraddlePremium +"," +cEPEPremiumRatio +"," + atmPriceDiffPercent
						+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	
	public String getOptionATMMovmentAnalysis(String indexname, String forDate) throws BusinessException {
		log.info("In getOptionTimeValueAnalysis forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionATMMovementAnalysis" + indexname + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,"
            		+ "ATMStradlePremium, ATMStradleDiffpercent, OTMStradlePremium, OTMStradleDiffpercent, ITMStradlePremium, ITMStradleDiffpercent, "
            		+ "AdjustedATMStradlePremium, AdjustedATMStradleDiffpercent, AdjustedOTMStradlePremium, AdjustedOTMStradleDiffpercent, AdjustedITMStradlePremium, AdjustedITMStradleDiffpercent,"
            		+ "atmstrength, otmstrength, itmstrength, atmivdiffpercent, otmivdiffpercent, itmivdiffpercent, atmdeltadiffpercent, otmdeltadiffpercent, itmdeltadiffpercent,"
            		+ "atmoptimumivdiff, otmoptimumivdiff, itmoptimumivdiff, bestindexat, minindexat, Multi Strike PriceDiff, Vega based Pricediff, Multi Strike IVDiff" + "\r\n");
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 15);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, indexltp, atmprice_point5_premium, atmprice_point5_diffpercent, otmprice_point4_premium, otmprice_point4_diffpercent, itmprice_point6_premium, itmprice_point6_diffpercent, adjusted_atmprice_point5_premium, adjusted_atmprice_point5_diffpercent, adjusted_otmprice_point4_premium, adjusted_otmprice_point4_diffpercent, adjusted_itmprice_point6_premium, adjusted_itmprice_point6_diffpercent,"
					+ " atmstrength, otmstrength, itmstrength, atmivdiffpercent, otmivdiffpercent, itmivdiffpercent, atmdeltadiffpercent, otmdeltadiffpercent, itmdeltadiffpercent,"
					+ " atmoptimumivdiff, otmoptimumivdiff, itmoptimumivdiff, bestindexat, minindexat, multiStrikePriceDiff, vegabasedpricediff, multiStrikeIVDiff"
					+ " from option_atm_movement_analysis"
					+ " where indexname = '" + indexname + "'"
					+ " and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];
				float indexltp = (Float) rowdata[1];
				
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp
						+"," + (Float) rowdata[2] +"," + (Float) rowdata[3]
						+"," + (Float) rowdata[4] +"," + (Float) rowdata[5]
						+"," + (Float) rowdata[6] +"," + (Float) rowdata[7]
												
						+"," + (Float) rowdata[8] +"," + (Float) rowdata[9]
						+"," + (Float) rowdata[10] +"," + (Float) rowdata[11]
						+"," + (Float) rowdata[12] +"," + (Float) rowdata[13]
												
						+"," + (Float) rowdata[14] +"," + (Float) rowdata[15] +"," + (Float) rowdata[16]
						+"," + (Float) rowdata[17] +"," + (Float) rowdata[18] +"," + (Float) rowdata[19]
						+"," + (Float) rowdata[20] +"," + (Float) rowdata[21] +"," + (Float) rowdata[22]
						+"," + (Float) rowdata[23] +"," + (Float) rowdata[24] +"," + (Float) rowdata[25]
						+"," + (Float) rowdata[26] +"," + (Float) rowdata[27] +"," + (Float) rowdata[28]
						+"," + (Float) rowdata[29] +"," + (Float) rowdata[30]
						+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getOptionATMMovmentRawDataAnalysis(Long mainInstrumentId, String forDate, float baseDelta) throws BusinessException {
		log.info("In getOptionTimeValueAnalysis forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionATMMovementRawDataAnalysis" + mainInstrumentId +forDate.replace("/", "-")+"_"+ baseDelta+ ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,"
            		+ "CE Delta, PE Delta,"
            		+ "Delta diff,"
            		+ "CE Gamma, PE Gamma,"
            		+ "Adjusted Gamma Diff,"
            		+ "CE Vega, PE Vega,"
            		+ "CE Theta, PE Theta,"
            		+ "CE IV, PE IV,"
            		+ "Adjusted IV Diff,"
            		+ "CE Ltp, PE Ltp," 
            		+ "CE OI, PE OI,"
            		+ "Straddle Premium, Adjusted Straddle Premium, Adjusted Premium Diff,"
            		+ "PriceDiffPercent, IVDiffPercent, GammaDiffPercent, Futures Bullish Point, Futures Bearish Point, Total CE OI, Total PE OI, CEIVByDelta, PEIVByDelta,"
            		+ "Avg CEIV, Avg PE IV, Total CE Gamma, Total PE Gamma, Total CE Vega, Total PE Vega, Total CE OI by ATM Ltp, Total PE OI by ATM Ltp, Total CEOIbyPEOI Ratio, AvgCEIVByAvgPEIV Ratio,"
            		+ "AvgCeGamma, AvgPeGamma, SelectiveStrike_AvgCeGamma, SelectiveStrike_AvgPeGamma, Futures Ltp, SelectiveStrike_AvgCeIV, SelectiveStrike_AvgPeIV"+ "\r\n");
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 15);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, instrumentLtp, "
					+ " cedelta, pedelta, cegamma, pegamma, cevega, pevega, cetheta, petheta, ceiv, peiv, celtp, peltp, ceoi, peoi, totalfuturepoints, bullishfuturepoints, totalceoi, totalpeoi,"
					+ " totalceiv, totalpeiv,"
					+ " totalcegamma, totalpegamma,"
					+ " totalcevega, totalpevega,"
					+ " avgcegamma, avgpegamma,"
					+ " selectiveStrike_AvgCeGamma, selectiveStrike_AvgPeGamma,"
					+ " futures_Ltp,"
					+ " selectiveStrike_AvgCeIv, selectiveStrike_AvgPeIv"
					+ " from db_link_option_atm_movement_data oamd"
					+ " where f_main_instrument = '" + mainInstrumentId + "'"
					+ " and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];
				float indexltp = (Float) rowdata[1];
				float cedelta = (Float) rowdata[2];
				float pedelta = (Float) rowdata[3];
				float cegamma = (Float) rowdata[4];
				float pegamma = (Float) rowdata[5];
				float cevega = (Float) rowdata[6];
				float pevega = (Float) rowdata[7];
				float cetheta = (Float) rowdata[8];
				float petheta = (Float) rowdata[9];
				float ceiv = (Float) rowdata[10];
				float peiv = (Float) rowdata[11];
				float celtp = (Float) rowdata[12];
				float peltp = (Float) rowdata[13];
				
				float ceOi = (Float) rowdata[14];
				float peOi = (Float) rowdata[15];
				
				
				Object rawData = rowdata[16];
				int totalfuturepoints = rawData!=null? (Integer) rawData:0;
				
				rawData = rowdata[17];
				int bullishfuturepoints =  rawData!=null? (Integer) rawData:0;
				
				float bullishDecimal =  totalfuturepoints>0?(float)bullishfuturepoints/(float)totalfuturepoints:0;
				
				float bearishDecimal =  totalfuturepoints>0?(float)(totalfuturepoints-bullishfuturepoints)/(float)totalfuturepoints:0;
				
				float totalCeOi = (Float) rowdata[18];
				float totalPeOi = (Float) rowdata[19];
				
				float totalCeIV = (Float) rowdata[20];
				float totalPeIV = (Float) rowdata[21];
				
				float totalCeGamma = (Float) rowdata[22];
				float totalPeGamma = (Float) rowdata[23];
				
				float totalCeVega = (Float) rowdata[24];
				float totalPeVega = (Float) rowdata[25];
				
				float avgCeGamma = (Float) rowdata[26];
				float avgPeGamma = (Float) rowdata[27];
				
				float selectiveStrikeAvgCeGamma = (Float) rowdata[28];
				float selectiveStrikeAvgPeGamma = (Float) rowdata[29];
				
				float futuresLtp = (Float) rowdata[30];
				
				float selectiveStrikeAvgCeIv = (Float) rowdata[31];
				float selectiveStrikeAvgPeIv = (Float) rowdata[32];
				
				float priceDiffPercent = getPercentDiff(celtp, peltp);
				if (celtp < peltp) priceDiffPercent = -priceDiffPercent;
				
				float ivDiffPercent = getPercentDiff(ceiv, peiv);
				if (ceiv > peiv) ivDiffPercent = -ivDiffPercent;
				
				float gammaDiffPercent = getPercentDiff(cegamma, pegamma);
				if (cegamma < pegamma) gammaDiffPercent = -gammaDiffPercent;
				
				float totalCeoiByAtmLtp = totalCeOi/celtp;
				float totalPeoiByAtmLtp = totalPeOi/peltp;
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp
						+ "," + cedelta + "," +  pedelta
						+ "," + Math.abs(cedelta + pedelta)
						
						+ "," + cegamma + "," + pegamma
						+ "," + getLinearAdjustedDifference(baseDelta, cedelta, cegamma, pedelta, pegamma)
						
						+ "," + cevega + "," + pevega
						+ "," + cetheta + "," + petheta
						+ "," + ceiv + "," + peiv
						+ "," + getLinearAdjustedDifference(baseDelta, cedelta, ceiv, pedelta, peiv)
						
						+ "," + celtp + "," + peltp
						+ "," + ceOi + "," + peOi
						+ "," + (celtp +  peltp)
						+ "," + getLinearAdjustedSum(baseDelta, cedelta, celtp, pedelta, peltp)
						+ "," + getLinearAdjustedDifference(baseDelta, cedelta, celtp, pedelta, peltp)
						+ "," + priceDiffPercent
						+ "," + ivDiffPercent
						+ "," + gammaDiffPercent
						+ "," + bullishDecimal
						+ "," + bearishDecimal
						+ "," + totalCeOi
						+ "," + totalPeOi
						+ "," + Math.abs(ceiv/cedelta)
						+ "," + Math.abs(peiv/pedelta)
						+ "," + totalCeIV
						+ "," + totalPeIV
						+ "," + totalCeGamma
						+ "," + totalPeGamma
						+ "," + totalCeVega
						+ "," + totalPeVega
						+ "," + totalCeoiByAtmLtp
						+ "," + totalPeoiByAtmLtp
						+ "," + totalCeOi/totalPeOi
						+ "," + totalCeIV/totalPeIV
						+ "," + avgCeGamma
						+ "," + avgPeGamma
						+ "," + selectiveStrikeAvgCeGamma
						+ "," + selectiveStrikeAvgPeGamma
						+ "," + futuresLtp
						+ "," + selectiveStrikeAvgCeIv
						+ "," + selectiveStrikeAvgPeIv
						+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	private float getLinearAdjustedSum(float baseDelta, float cedelta,float ceValue,float pedelta,float peValue) {
		float retVal = 0f;
		retVal = retVal + baseDelta*ceValue/Math.abs(cedelta);
		retVal = retVal + baseDelta*peValue/Math.abs(pedelta);
		return retVal;
	}
	
	private float getLinearAdjustedDifference(float baseDelta, float cedelta,float ceValue,float pedelta,float peValue) {
		float retVal = 0f;
		retVal = retVal + baseDelta*ceValue/cedelta;
		retVal = retVal - baseDelta*peValue/Math.abs(pedelta);
		return retVal;
	}
	
	public String getOptionGreeksMovmentAnalysis(String indexname, String forDate) throws BusinessException {
		log.info("In getOptionTimeValueAnalysis forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionGreeksMovementsAnalysis" + indexname + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt, Total Premium, "
            		+ "Change In CE Vega, Change In CE IV, Change In CE Delta, Change In CE Ltp, "
            		+ "Change In PE Vega, Change In PE IV, Change In PE Delta, Change In PE Ltp, "
            		+ "Change In CE Vega%, Change In CE IV%, Change In CE Delta%, Change In CE Ltp%, "
            		+ "Change In PE Vega%, Change In PE IV%, Change In PE Delta%, Change In PE Ltp%, 5Min Avg Change In CE Vega, 5Min Avg Change In PE Vega" + "\r\n");
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 15);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, indexltp, totalpremium,"
					+ " changeincevega, changeinceiv, changeincedelta, changeinceltp, "
					+ " changeinpevega, changeinpeiv, changeinpedelta, changeinpeltp, "
					+ " changeincevegapercent, changeinceivpercent, changeincedeltapercent, changeinceltppercent, "
					+ " changeinpevegapercent, changeinpeivpercent, changeinpedeltapercent, changeinpeltppercent, "
					+ " changeincevega5minavg, changeinpevega5minavg"
					+ " from option_greek_movement"
					+ " where indexname = '" + indexname + "'"
					+ " and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];
				float indexltp = (Float) rowdata[1];
				float totalPremium = (Float) rowdata[2];
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp+","+totalPremium
						+"," + (Float) rowdata[3] +"," + (Float) rowdata[4] +"," + (Float) rowdata[5] +"," + (Float) rowdata[6]
						+"," + (Float) rowdata[7] +"," + (Float) rowdata[8] +"," + (Float) rowdata[9] +"," + (Float) rowdata[10]
						+"," + (Float) rowdata[11] +"," + (Float) rowdata[12] +"," + (Float) rowdata[13] +"," + (Float) rowdata[14]
						+"," + (Float) rowdata[15] +"," + (Float) rowdata[16] +"," + (Float) rowdata[17] +"," + (Float) rowdata[18]
						+"," + (Float) rowdata[19] +"," + (Float) rowdata[20]
						+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	private float getPercentDiff(float firstValue, float secondValue) {
		float retVal = 0f;
		if(firstValue<secondValue) {
			retVal = (secondValue-firstValue)*100f/firstValue;
		} else {
			retVal = (firstValue-secondValue)*100f/secondValue;
		}
		return retVal;
	}
	public String getOption1MGreeksMovements(String forDate, String optionnames) throws BusinessException {
		String csvFilename = "D:\\temp\\junk\\Option1MGreeksMovement" ;
		try {
			SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat postgresShortFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			csvFilename = csvFilename + postgresShortFormat.format(stdFormat.parse(forDate)) + ".csv";
			String[] allPptionnames = optionnames.split(",");
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexLtp");
            for(String optiononame : allPptionnames) {
            	writer.write(","+optiononame+"-LTP"+",IV, Delta, Gamma, Vega, Theta");
            }
            writer.write(",P/L\r\n");
            
            Calendar cal = Calendar.getInstance();
			cal.setTime(stdFormat.parse(forDate));
			
			cal.set(Calendar.HOUR_OF_DAY, 15);
			cal.set(Calendar.MINUTE, 30);
			cal.set(Calendar.SECOND, 0);
			Date endTime = cal.getTime();
			
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.set(Calendar.MINUTE, 20);
			Date beginTime = cal.getTime();
			
			Date lastRecordDate = null;
			
			Map<String, List<ZerodhaOptionCandleVO>> alldataMap = new HashMap<String, List<ZerodhaOptionCandleVO>>();
			for(String optiononame : allPptionnames) {
				List<ZerodhaOptionCandleVO> optionGreekList = new ArrayList<ZerodhaOptionCandleVO>();
				
				String fetchSql = "select quote_time, close_price, underlying_index_value, implied_volatility,Delta, Gamma, Vega, Theta  from option_1m_candle where optionname = '" + optiononame + "' and quote_time >= '" + postgresFormat.format(beginTime) + "' and quote_time <= '" + postgresFormat.format(endTime) + "' order by quote_time";
				Query q = entityManager.createNativeQuery(fetchSql);	
				List<Object[]> listResults = q.getResultList();
				Iterator<Object[]> iter = listResults.iterator();
				while (iter.hasNext()) {
					Object[] rowdata = iter.next();
					Date quoteTime = (Timestamp) rowdata[0];
					float ltp = (Float) rowdata[1];
					float underlyingIndexValue = (Float) rowdata[2];
					float impliedVolatility = (Float) rowdata[3];
					
					float delta = (Float) rowdata[4];
					float gamma = (Float) rowdata[5];
					float vega = (Float) rowdata[6];
					float theta = (Float) rowdata[7];
					
					ZerodhaOptionCandleVO aVo = new ZerodhaOptionCandleVO();
					aVo.setQuoteTime(quoteTime);
					aVo.setClosePrice(ltp);
					aVo.setUnderlyingIndexValue(underlyingIndexValue);
					aVo.setImpliedVolatility(impliedVolatility);
					aVo.setDelta(Math.abs(delta));
					aVo.setGamma(gamma);
					aVo.setVega(vega);
					aVo.setTheta(theta);
					if (optionGreekList.size()>0 &&
							optionGreekList.get(optionGreekList.size()-1).getQuoteTime().getHours()==quoteTime.getHours() && optionGreekList.get(optionGreekList.size()-1).getQuoteTime().getMinutes()==quoteTime.getMinutes() ) {
						log.info("Same minute data repeat skipping" );
					} else {
						optionGreekList.add(aVo);
					}
					lastRecordDate = quoteTime;
				}
				alldataMap.put(optiononame, optionGreekList);
			}
			
			String firstOptionName = null;
			boolean sellPriceRecorded = false;
			float totalSellCost = 0f;
            do {
            	float currentTotalOptionPrice = 0f;
            	for(String optiononame : allPptionnames) {
            		if (firstOptionName==null) firstOptionName = optiononame;
            		ZerodhaOptionCandleVO aVo = alldataMap.get(optiononame).get(0);
            		
            		log.info("~~~~ Time aVo.getQuoteTime()"+aVo.getQuoteTime()+" cal.getTime()"+cal.getTime());
            		
            		if (aVo!=null) {
	            		if (optiononame.equals(firstOptionName)) {
	            			writer.write(postgresFormat.format(aVo.getQuoteTime())+","+aVo.getUnderlyingIndexValue());
	            		}
	            		if ( (aVo.getQuoteTime().getHours()==cal.getTime().getHours()) && (aVo.getQuoteTime().getMinutes()==cal.getTime().getMinutes()) ) {
	            			currentTotalOptionPrice = currentTotalOptionPrice + aVo.getClosePrice();
	            			writer.write("," + aVo.getClosePrice()+"," + aVo.getImpliedVolatility() +"," + aVo.getDelta() +"," + aVo.getGamma() +"," + aVo.getVega() +"," + aVo.getTheta()  );
	            			alldataMap.get(optiononame).remove(0);
	            		} else {
	            			log.info("~~~~ Time mismatch aVo.getQuoteTime()"+aVo.getQuoteTime()+" cal.getTime()"+cal.getTime());
	            			writer.write(",,,,,,");
	            		}
            		} else {
            			writer.write(",,,,,,");
            		}
            	}
            	if (sellPriceRecorded==false) {
            		totalSellCost = currentTotalOptionPrice;
            		sellPriceRecorded = true;
        		}
            	writer.write("," + (totalSellCost-currentTotalOptionPrice) );
            	writer.write("\r\n");
            	cal.add(Calendar.MINUTE, 1);
            } while(cal.getTime().before(lastRecordDate));
            writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getPandLOfOrder(String algoIds, String forDate)  throws BusinessException {
		log.info("In getPandLOfOrder forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\getPandLOfOrder" ;
		try {
			SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat postgresShortFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			csvFilename = csvFilename + postgresShortFormat.format(stdFormat.parse(forDate)) + ".csv";
			String[] allAlgoIds = algoIds.split(",");
				
			String algonameInQuotesForQuery = "";
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexLtp");
            for(String aLgoId : allAlgoIds) {
            	writer.write(","+aLgoId);
            	if (algonameInQuotesForQuery.length()!=0) algonameInQuotesForQuery = algonameInQuotesForQuery + ",";
            	algonameInQuotesForQuery = algonameInQuotesForQuery + "'" + aLgoId +"'";
            }
            writer.write(",Total");
            writer.write("\r\n");
            
            String orderFetchSql = "select id, option_name, algoname, sell_price, entry_time, exit_time from db_link_nexcorio_options_algo_orders where f_strategy in(" + algoIds+") and short_date = '" + postgresShortFormat.format(stdFormat.parse(forDate)) +"' ";
            
			log.info("orderFetchSql "+orderFetchSql);
			List<OptionAlgoOrderDto> runningOrders = new ArrayList<OptionAlgoOrderDto>();
			Query q = entityManager.createNativeQuery(orderFetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Long id= ((BigInteger) rowdata[0]).longValue();
				String optionName = (String) rowdata[1];
				String algoname= (String) rowdata[2];
				float sellPrice = (Float) rowdata[3];
				Date entryTime = (Timestamp) rowdata[4];
				Date exitTime = (Timestamp) rowdata[5];
				
				OptionAlgoOrderDto aDto = new OptionAlgoOrderDto(id, optionName, algoname, sellPrice, entryTime, exitTime);
				runningOrders.add(aDto);
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(stdFormat.parse(forDate));
			
			cal.set(Calendar.HOUR_OF_DAY, 15);
			cal.set(Calendar.MINUTE, 30);
			Date endTime = cal.getTime();
			
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.set(Calendar.MINUTE, 16);
			Map<String, Float> allOrderPrice = new HashMap<String, Float>();
			
            do {            	
            	String fetchIndexPriceQuery = "select last_traded_price, quote_time from db_link_nexcorio_tick_data where trading_symbol = 'NIFTY' and quote_time < '" + postgresFormat.format(cal.getTime())+ "' order by quote_time desc limit 1"; 
            	q = entityManager.createNativeQuery(fetchIndexPriceQuery);
            	listResults = q.getResultList();
    			iter = listResults.iterator();
    			float indexAt = 0f;
    			while (iter.hasNext()) {
    				Object[] rowdata = iter.next();
    				indexAt = (Float) rowdata[0];
    			}
    			writer.write(postgresFormat.format(cal.getTime())+"," + indexAt);
    			
    			for(int i=runningOrders.size()-1; i>=0;i--) {
    				OptionAlgoOrderDto aOrder = runningOrders.get(i);
    				if (aOrder.getEntryTime().before(cal.getTime()) && aOrder.getExitTime().after(cal.getTime()) ) {
    					
    					String optionPriceQuery = "select last_traded_price, quote_time from db_link_nexcorio_tick_data where trading_symbol = '" + aOrder.getOptionName()+ "' and quote_time<'" + postgresFormat.format(cal.getTime()) + "' order by quote_time desc limit 1";
    					q = entityManager.createNativeQuery(optionPriceQuery);
    	            	listResults = q.getResultList();
    	    			iter = listResults.iterator();
    	    			float optionPrice = 0f;
    	    			while (iter.hasNext()) {
    	    				Object[] rowdata = iter.next();
    	    				optionPrice = (Float) rowdata[0];
    	    			}
    	    			float profit = aOrder.getSellPrice() - optionPrice;
    	    			allOrderPrice.put(aOrder.getAlgoname()+"ID"+aOrder.getId(), profit);
    				}
    				if (aOrder.getExitTime()!=null && aOrder.getExitTime().before(cal.getTime()) ) {
    					runningOrders.remove(i);
    				}
    			}
    			float totalProfit = 0f;
    			for(String algoname : allAlgoIds) {
    				
    				Iterator mapKeys = allOrderPrice.keySet().iterator();
    				Float profitFromAlgo = 0f;
    				while(mapKeys.hasNext()) {
    					String aKey = (String)mapKeys.next();
    					if (aKey.startsWith(algoname+"ID")) {
    						profitFromAlgo =  profitFromAlgo + allOrderPrice.get(aKey);
    					}
    				}    				
    				if (profitFromAlgo!=0)  {
    					writer.write("," + profitFromAlgo);
    					totalProfit = totalProfit + profitFromAlgo;
    				} else writer.write("," );
    			}
    			writer.write("," +totalProfit);
    			writer.write("\r\n");
            	cal.add(Calendar.SECOND, 10);
            } while(cal.getTime().before(endTime));
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getOptionGreeksData(String optionName, String forDate)  throws BusinessException {
		log.info("In getOptionGreeksData forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionGreeks" + optionName + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,IV,Delta,Vega,Psi,Theta,Gamma,Volga,OptionPrice\r\n");
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 14);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 16);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, delta, vega, psi, theta, gamma, volga, underlying_index_value, implied_volatility, lastPrice_4m_option_chain from option_greeks where optionname like '" + optionName + "'  and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float delta = (Float) rowdata[1];
				float vega = (Float) rowdata[2];
				float psi = (Float) rowdata[3];
				float theta = (Float) rowdata[4];
				float gamma = (Float) rowdata[5];
				float volga = (Float) rowdata[6];
				float indexLtp = (Float) rowdata[7];
				float iv = (Float) rowdata[8];
				float optionPrice = (Float) rowdata[9];
				
				writer.write(postgresFormat.format(quoteTime)+","+indexLtp+"," +iv+","+delta+","+vega+","+psi+ "," + theta+ "," + gamma+ ","+volga+"," + optionPrice+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getOptionSpikeData(String indexName, String forDate)  throws BusinessException {
		log.info("In getOptionGreeksData forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionSpike" + indexName + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,Index Diff,CE ATM Diff, PE ATM Diff, CE Cutoff, PE CutOff, CE Delta Diff, PE Delta Diff\r\n"); 
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 14);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, indexltp, indexpricediff,ceatmpricediff,peatmpricediff,ceATMDeltaDiff, peATMDeltaDiff from option_atm_price_spike where indexname like '" + indexName + "'  and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			Date lastQuoteTime = null;
			int cutoffLine = 9;
			if (indexName.contains("BANK")) cutoffLine = 9*2; 
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float indexltp = (Float) rowdata[1];
				float indexpricediff = (Float) rowdata[2];
				float ceatmpricediff = (Float) rowdata[3];
				float peatmpricediff = (Float) rowdata[4];
				float ceATMDeltaDiff =  (Float) rowdata[5];
				float peATMDeltaDiff =  (Float) rowdata[6];
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp+"," +indexpricediff+","+ceatmpricediff+","+peatmpricediff+"," + cutoffLine + "," + (-cutoffLine)+"," + ceATMDeltaDiff+"," + peATMDeltaDiff+"\r\n");
				lastQuoteTime = quoteTime;
			}
			if (lastQuoteTime!=null) {
				cal.setTime(lastQuoteTime);
				Date loopBeginTime = cal.getTime();
				cal.set(Calendar.HOUR_OF_DAY, 15);
				cal.set(Calendar.MINUTE, 30);
				Date loopEndTime = cal.getTime();
				
				cal.setTime(loopBeginTime);
				do {
					//writer.write(postgresFormat.format(cal.getTime())+",,,,\r\n");
					cal.add(Calendar.MINUTE, 1);
				} while(cal.getTime().before(loopEndTime));
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	
	public String getOptionDeltaNeutralPriceDisparity(String indexName, String forDate)  throws BusinessException {
		log.info("In getOptionGreeksData forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionSpike" + indexName + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,Delta 2, Delta 3, Delta 4, Delta 5, Delta 6, Avg Price Diff\r\n"); 
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 14);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} 
			  
			String fetchSql = "select record_time, indexltp, price_diff_percent_of_delta2, price_diff_percent_of_delta3, price_diff_percent_of_delta4, price_diff_percent_of_delta5, price_diff_percent_of_delta6, avgPriceDiff "
					+ " from option_delta_neutral_price_disparity where indexname like '" + indexName + "'  and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			 
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float indexltp = (Float) rowdata[1];
				
				float price_diff_percent_of_delta2 = (Float) rowdata[2];
				float price_diff_percent_of_delta3 = (Float) rowdata[3];
				float price_diff_percent_of_delta4 = (Float) rowdata[4];
				float price_diff_percent_of_delta5 = (Float) rowdata[5];
				float price_diff_percent_of_delta6 = (Float) rowdata[6];
				float avgPriceDiff = 	 (Float) rowdata[7];
				writer.write(postgresFormat.format(quoteTime)+","+indexltp+"," +price_diff_percent_of_delta2+","+price_diff_percent_of_delta3+","+price_diff_percent_of_delta4+","+price_diff_percent_of_delta5+","+price_diff_percent_of_delta6+"," + avgPriceDiff+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	
	public String getOptionMaxOIWorthReversionData(String indexName, String forDate)  throws BusinessException {
		log.info("In getOptionGreeksData forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionMaxOIWorthReversionData" + indexName + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,optionstrike, distance\r\n"); 
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 14);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, indexltp, optionstrike, distance from option_max_worth_mean_reversion where indexname like '" + indexName + "'  and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float indexltp = (Float) rowdata[1];
				Integer optionstrike = (Integer) rowdata[2];
				float distance = (Float) rowdata[3];
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp+"," +optionstrike+","+distance+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getOptionOptimalStrikeData(String indexName, String forDate, Integer noOfTopOis) throws BusinessException {
		log.info("In getOptionGreeksData forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionOptimalStrikeData" + indexName + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,Optimal Strike, Max Profir Ratio\r\n"); 
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 14);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, indexltp, optimal_strike, max_profit_ratio from option_optimal_strike where indexname like '" + indexName + "' "
					+ " and no_of_topoi="+noOfTopOis
					+ " and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float indexltp = (Float) rowdata[1];
				float optimalStrike = (Float) rowdata[2];
				float maxProfitRatio = (Float) rowdata[3];
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp+"," +optimalStrike+","+maxProfitRatio+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getOptionGreeksRateOfChange(String indexName, String forDate, Integer noOfTopOis, String method, boolean filterOptionWorth)  throws BusinessException {
		log.info("In getOptionGreeksRateOfChange forDate="+forDate);
		String csvFilename = "D:\\temp\\junk\\OptionGreeksRateOfChange" + indexName +noOfTopOis+method+filterOptionWorth+ ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,ChangeIn CE IV, ChangeIn PE IV, ChangeIn CE Delta, ChangeIn PE Delta, ChangeIn CE Vega, ChangeIn PE Vega, ChangeIn CE Theta, ChangeIn PE Theta, CE Vega Value, PE Vega Value\r\n"); 
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 30);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 14);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, indexltp, changeinceiv, changeinpeiv, changeincedelta, changeinpedelta, changeincevega, changeinpevega, changeincetheta, changeinpetheta, ce_vega_value, pe_vega_value from option_greek_rate_of_change where "
					+ " indexname like '" + indexName + "'  and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' "
					+ " and method = '" + method +"' and nooftopois=" + noOfTopOis +" and filterOptionWorth="+filterOptionWorth
					+ " order by record_time";
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			Date lastQuoteTime = null; 
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float indexltp = (Float) rowdata[1];
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp+"," +(Float) rowdata[2]+","+(Float) rowdata[3]+","+(Float) rowdata[4]+"," + (Float) rowdata[5] + "," + (Float) rowdata[6]+"," + (Float) rowdata[7]+"," + (Float) rowdata[8]+"," + (Float) rowdata[9]+"," + (Float) rowdata[10]+"," + (Float) rowdata[11]+"\r\n");
				lastQuoteTime = quoteTime;
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	public String getOptionOIDescrepancy(String indexName, String forDate, Integer noOfTopOis, boolean filterOptionWorth)  throws BusinessException {
		log.info("In getOptionGreeksRateOfChange forDate="+forDate);
		String csvFilename = "D:\\temp\\junk\\OptionOIDescrepancy" + indexName +noOfTopOis+filterOptionWorth+ ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,Center Point By OI,Center Point By worth\r\n"); 
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 30);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 14);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 25);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, indexltp, centerpointbyoi, centerpointbyworth from options_oi_discrepancy where "
					+ " indexname like '" + indexName + "'  and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' "
					+ " and nooftopois=" + noOfTopOis +" and filterOptionWorth="+filterOptionWorth
					+ " and centerpointbyoi>0 and centerpointbyworth>0"
					+ " order by record_time";
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float indexltp = (Float) rowdata[1];
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp+"," +(Float) rowdata[2]+","+(Float) rowdata[3]+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}

	public String getOptionPriceRateOfChange(String indexName, String forDate, float basedelta)  throws BusinessException {
		log.info("In getOptionPriceRateOfChange forDate="+forDate);
		String csvFilename = "D:\\temp\\junk\\OptionPriceRateOfChange" + indexName +basedelta+ ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,Index Price Diff, CE ATM Price Diff 1M, PE ATM Price Diff 1M, CE Avg, PE Avg\r\n"); 
                        
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 30);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 14);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, indexltp, indexpricediff, ceatmpricediff1m, peatmpricediff1m, ceprice, peprice from option_price_rate_of_change where "
					+ " indexname = '" + indexName + "'  and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' "
					+ " and basedelta > " + (basedelta-0.01) +" and basedelta < " + (basedelta+0.01)
					+ " order by record_time";
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);
			
			List<Float> cePastData = new ArrayList<Float>();
			List<Float> pePastData = new ArrayList<Float>();
			
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			Date lastQuoteTime = null; 
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float indexltp = (Float) rowdata[1];
				
				float ceatmpricediff1m = (Float) rowdata[3];
				cePastData.add(ceatmpricediff1m);
				
				float peatmpricediff1m = (Float) rowdata[4];
				pePastData.add(peatmpricediff1m);
				
				if (cePastData.size()>20) cePastData.remove(0);
				if (pePastData.size()>20) pePastData.remove(0);
				
				OptionalDouble ceAverage = cePastData.stream().mapToDouble(a -> a).average();
				OptionalDouble peAverage = pePastData.stream().mapToDouble(a -> a).average();
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp+"," +(Float) rowdata[2]+","+ceatmpricediff1m+","+peatmpricediff1m+"," + ceAverage.getAsDouble() +"," + peAverage.getAsDouble()+"\r\n");
				lastQuoteTime = quoteTime;
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getOptionATMOTMOIRateOfChange(String indexName, String forDate)  throws BusinessException {
		log.info("In getOptionPriceRateOfChange forDate="+forDate);
		String csvFilename = "D:\\temp\\junk\\OptionATMOTMOIRateOfChange" + indexName + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,CE Avg, PE Avg, Net, CE Vega, PE Vega, CE Gamma, PE Gamma, CE TimeValue, PE TimeValue\r\n"); 
                        
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 30);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 18);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, indexltp, ceavg, peavg, ceavgvega, peavgvega, ceavggamma, peavggamma, cetotaltimevalue, petotaltimevalue from option_atmotm_oi_rate_of_change where "
					+ " indexname = '" + indexName + "'  and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' "					
					+ " order by record_time";
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			Date lastQuoteTime = null; 
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float indexltp = (Float) rowdata[1];
				
				float ceavg = (Float) rowdata[2];
				float peavg = (Float) rowdata[3];
				writer.write(postgresFormat.format(quoteTime)+","+indexltp+"," +ceavg+","+peavg+"," + (peavg-ceavg) +","+(Float) rowdata[4]+","+(Float) rowdata[5]+","+(Float) rowdata[6]
						+"," + (Float) rowdata[7]+"," + (Float) rowdata[8]+"," + (Float) rowdata[9]+"\r\n");
				lastQuoteTime = quoteTime;
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public String getTrendDecidingOptionGreeksROC(String indexName, String forDate, Integer noOfTopOis, String method)  throws BusinessException {
		log.info("In getTrendDecidingOptionGreeksROC forDate="+forDate);
		String csvFilename = "D:\\temp\\junk\\TrendDecidingOptionGreeksROC" + indexName + ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
			writer.write("QuoteTime,IndexAt, ce_change_in_vega, pe_change_in_vega, ce_percent_change_in_vega, pe_percent_change_in_vega\r\n");
            //writer.write("QuoteTime,IndexAt,ce_change_in_iv, pe_change_in_iv, ce_change_in_delta, pe_change_in_delta, ce_change_in_vega, pe_change_in_vega, ce_change_in_gamma, pe_change_in_gamma, ce_change_in_theta, pe_change_in_theta, ce_percent_change_in_iv, pe_percent_change_in_iv, ce_percent_change_in_delta, pe_percent_change_in_delta, ce_percent_change_in_vega, pe_percent_change_in_vega, ce_percent_change_in_gamma, pe_percent_change_in_gamma, ce_percent_change_in_theta, pe_percent_change_in_theta\r\n"); 
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 30);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 14);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.HOUR_OF_DAY, 15);
				cal.set(Calendar.MINUTE, 0);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			//String fetchSql = "select record_time, indexltp, ce_change_in_iv, pe_change_in_iv, ce_change_in_delta, pe_change_in_delta, ce_change_in_vega, pe_change_in_vega, ce_change_in_gamma, pe_change_in_gamma, ce_change_in_theta, pe_change_in_theta, ce_percent_change_in_iv, pe_percent_change_in_iv, ce_percent_change_in_delta, pe_percent_change_in_delta, ce_percent_change_in_vega, pe_percent_change_in_vega, ce_percent_change_in_gamma, pe_percent_change_in_gamma, ce_percent_change_in_theta, pe_percent_change_in_theta"
			String fetchSql = "select record_time, indexltp, ce_change_in_vega, pe_change_in_vega, ce_percent_change_in_vega, pe_percent_change_in_vega"
					+ " from advance_monitor_trend_deciding_option_greeks"
					+ " where indexname = '" + indexName + "' and method = '" + method + "' and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' "
					+ " and nooftopois=" + noOfTopOis
					+ " order by record_time";
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			Date lastQuoteTime = null; 
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];				
				float indexltp = (Float) rowdata[1];;
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp
						+"," +(Float) rowdata[2]+","+(Float) rowdata[3]+","+(Float) rowdata[4]+"," + (Float) rowdata[5] +"\r\n");
				lastQuoteTime = quoteTime;
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	public static String getOptionLongMonthName(int month) {
		if (month==10) return "O";
		if (month==11) return "N";
		if (month==12) return "D";
		return "-";
	}
	
	private String getNextExpiryDateForOptionnameStr(String indexname, Date forDate) {
		String expiryDateAsOptionNaming = null;
		
		Connection conn = null;
		try {
			
			String fetchSql = "SELECT expiry_date from option_expiry_dates where index_short_name = '" + indexname + "' order by expiry_date";
			Query q = entityManager.createNativeQuery(fetchSql);
			
			List<Object[]> listResults = q.getResultList();
			Iterator iter = listResults.iterator();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(forDate);
			cal.add(Calendar.DATE, -1);
			Date nextExpiryDate = null;
			while (iter.hasNext()) {
				Object rowdata = iter.next();
				nextExpiryDate = (Date)rowdata;
				if (nextExpiryDate.after(cal.getTime())) break;
			}
			log.info("nextExpiryDate="+nextExpiryDate);
    		
    		Calendar pCal = Calendar.getInstance();
    		pCal.setTime(forDate);
    		int expiryDayInWeekNumber = (indexname.equals("NIFTY") || indexname.equals("NIFTY 50"))?EXPIRY_DAY_IN_WEEK_NUMBER_NIFTY:
										(indexname.equals("NIFTY BANK") || indexname.equals("BANKNIFTY"))?EXPIRY_DAY_IN_WEEK_NUMBER_BANKNIFTY:EXPIRY_DAY_IN_WEEK_NUMBER_FINNIFTY;
    		 
    		pCal.set(GregorianCalendar.DAY_OF_WEEK,expiryDayInWeekNumber);
    		pCal.set(GregorianCalendar.DAY_OF_WEEK_IN_MONTH, -1);
    		Date lastExpiryday = pCal.getTime();
    		System.out.println("lastThursday="+lastExpiryday+" nextExpiryDate="+nextExpiryDate);
    		if (nextExpiryDate.getYear()==lastExpiryday.getYear()
    				&& nextExpiryDate.getMonth()==lastExpiryday.getMonth()
    				&& nextExpiryDate.getDate()==lastExpiryday.getDate()) { // If expiry date is last thursday
    			SimpleDateFormat monthEndFormat = new SimpleDateFormat("YYMMM");
    			expiryDateAsOptionNaming = monthEndFormat.format(nextExpiryDate).toUpperCase();
    			
    		} else {
    			pCal.set(GregorianCalendar.DAY_OF_WEEK,expiryDayInWeekNumber-1);
        		pCal.set(GregorianCalendar.DAY_OF_WEEK_IN_MONTH, -1);
        		Date lastMonday = pCal.getTime();
        		if (nextExpiryDate.getYear()==lastMonday.getYear()
        				&& nextExpiryDate.getMonth()==lastMonday.getMonth()
        				&& nextExpiryDate.getDate()==lastMonday.getDate()) {
        			SimpleDateFormat monthEndFormat = new SimpleDateFormat("YYMMM");
        			expiryDateAsOptionNaming = monthEndFormat.format(nextExpiryDate).toUpperCase();
        		} else {
        			pCal.set(GregorianCalendar.DAY_OF_WEEK,expiryDayInWeekNumber-2);
            		pCal.set(GregorianCalendar.DAY_OF_WEEK_IN_MONTH, -1);
            		lastMonday = pCal.getTime();
        			if (nextExpiryDate.getYear()==lastMonday.getYear()
            				&& nextExpiryDate.getMonth()==lastMonday.getMonth()
            				&& nextExpiryDate.getDate()==lastMonday.getDate()) {
            			SimpleDateFormat monthEndFormat = new SimpleDateFormat("YYMMM");
            			expiryDateAsOptionNaming = monthEndFormat.format(nextExpiryDate).toUpperCase();
        			} else {
		    			cal.setTime(nextExpiryDate);
		    			int currentMonth = cal.get(Calendar.MONTH)+1;
		    			if (currentMonth>9) {
		    				expiryDateAsOptionNaming = (cal.get(Calendar.YEAR)%100) + "" + getOptionLongMonthName((cal.get(Calendar.MONTH)+1)) +"" + (cal.get(Calendar.DATE)<10? "0" + cal.get(Calendar.DATE):cal.get(Calendar.DATE));
		    			} else {
		    				expiryDateAsOptionNaming = (cal.get(Calendar.YEAR)%100) + "" + (cal.get(Calendar.MONTH)+1) +"" + (cal.get(Calendar.DATE)<10? "0" + cal.get(Calendar.DATE):cal.get(Calendar.DATE));
		    			}
        			}
        		}
    		}
		} catch(Exception ex) {
    		ex.printStackTrace();
    	}		
		return expiryDateAsOptionNaming;
	}
	
	public String getIndividualOptionOIData(String indexName, String forDate)  throws BusinessException {
		log.info("In getIndividualOptionOIData indexName="+indexName+" forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OI"+indexName+".csv";
		try {
			//int instrumentToken = indexName.startsWith("NIFTY")? 256265 : 260105; // Bank Nifty
			int instrumentToken =   indexName.startsWith("NIFTY")? 256265 : indexName.startsWith("BANKNIFTY")?260105:257801; // 257801 for FINNIFTY
			
			SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(stdFormat.parse(forDate));
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.set(Calendar.MINUTE, 14);
			String dateStrBegin = postgresFormat.format(cal.getTime());	
			
			cal.set(Calendar.MINUTE, 45);
			String endStrikeForSpot = postgresFormat.format(cal.getTime());	
			
			cal.set(Calendar.HOUR_OF_DAY, 16);			
			String dateStrEnd = postgresFormat.format(cal.getTime());
			
			String fetchSql = "select last_traded_price as ltp, quote_time as QuoteTime from zerodha_intraday_streaming_data where instrument_token = " + instrumentToken 
					+" and quote_time >='" +  dateStrBegin +"'"
					+" and quote_time <='" +  endStrikeForSpot +"'"
					+ " order by quote_time desc limit 1";
			log.info(fetchSql);
			
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			
			int spotPrice = 0;
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				spotPrice = ((Float)rowdata[0]).intValue();
			}
			log.info("spotPrice="+spotPrice);
			
			spotPrice = spotPrice/100; // To drop last 2 digit
			spotPrice = spotPrice*100;
			
			List<String> optionNames = new ArrayList<String>();
			String expirtStr = "BANKNIFTY22O20" ; //"BANKNIFTY22SEP"; //"BANKNIFTY22922"; // Hardcoded	 // BANKNIFTY22O0638200CE	BANKNIFTY22O0638200CE
			String currentExprStr = getNextExpiryDateForOptionnameStr(indexName, cal.getTime());
			
			String fetchExpiryDatePrefixStr = "select trading_symbol from zerodha_intraday_streaming_data where trading_symbol like '" + indexName + currentExprStr+ "%E' and quote_time  >='" +  dateStrBegin + "' and quote_time <= '" + endStrikeForSpot + "' and openinterest>0 limit 1";			
			log.info(fetchExpiryDatePrefixStr);
			
			q = entityManager.createNativeQuery(fetchExpiryDatePrefixStr);	
			listResults = q.getResultList();
			iter = listResults.iterator();
			
			while (iter.hasNext()) {
				Object rowdata = iter.next();
				expirtStr = (String)rowdata;
				expirtStr = expirtStr.substring(0, expirtStr.length()-7);
			}
			log.info("expirtStr="+expirtStr);
			
			
			int nearesrtStrikeprice = spotPrice;
			for(int i=-4;i<20;i++) {
				int nextStrike = nearesrtStrikeprice + i*(indexName.startsWith("NIFTY")||indexName.startsWith("FINNIFTY")?50:100);
				optionNames.add(expirtStr+nextStrike+"CE");
			}
			// PE
			for(int i=-4;i<20;i++) {
				int nextStrike = nearesrtStrikeprice - i*(indexName.startsWith("NIFTY")||indexName.startsWith("FINNIFTY")?50:100);
				optionNames.add(expirtStr+nextStrike+"PE");
			}
			log.info(optionNames);
			List<String> timeKeys = new ArrayList<String>();
			
			fetchSql = "select trading_symbol, quote_time, openinterest from zerodha_intraday_streaming_data where trading_symbol in (" + getQuotesString(optionNames) + ") and quote_time > '" + dateStrBegin +"' and quote_time < '" + dateStrEnd + "' order by quote_time";
			//fetchSql = "select trading_symbol, quote_time, openinterest from zerodha_intraday_streaming_data where trading_symbol like '" + indexName + "%E' and quote_time > '" + dateStrBegin +"' and quote_time < '" + dateStrEnd + "' order by quote_time";
			log.info("fetchSql "+fetchSql);
			q = entityManager.createNativeQuery(fetchSql);	
			listResults = q.getResultList();
			iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				String tradingSymbol = (String)rowdata[0];
				Date quoteTime = (Timestamp) rowdata[1];
				float openInterest = (Float) rowdata[2];
				if (quoteTime.getSeconds()==0) {
					OptionOI optionOI = new OptionOI(tradingSymbol, openInterest, quoteTime);
					String mapTimeKey = postgresFormat.format(quoteTime);
					List<OptionOI> dataList = oiDataMap.get(mapTimeKey);
					if (dataList==null)  {
						dataList = new ArrayList<OptionOI>();
						timeKeys.add(mapTimeKey);
					}
					dataList.add(optionOI);
					oiDataMap.put(mapTimeKey, dataList);
				}
			}
			// Write to xls
			
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime");
            for(int j=0;j<optionNames.size();j++) {
            	 writer.write("," + optionNames.get(j));
            }
            writer.write("\r\n");
			for(int i=0;i<timeKeys.size();i++) {
				String aTimeKey = timeKeys.get(i);
				writer.write(aTimeKey);
				List<OptionOI> optionDataAtaTime = oiDataMap.get(aTimeKey);
				for(int j=0;j<optionNames.size();j++) {
					boolean found = false;
					for(int k=0;k<optionDataAtaTime.size();k++) {
						if (optionDataAtaTime.get(k).getTradingSymbol().equals(optionNames.get(j))) {
							writer.write("," + optionDataAtaTime.get(k).getOpenInterest());
							found = true;
							break;
						}
					}
					if (found==false) {
						writer.write(",");
					}
				}
				 writer.write("\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
	
	private String getQuotesString(List<String> optionNames) {
		String retStr = "";
		
		for(int i=0;i<optionNames.size();i++) {
			if (retStr.length()!=0) retStr = retStr + ",";
			retStr = retStr + "'" + optionNames.get(i)+"'";
		}
		
		return retStr;
	}
	
	public List<OptionSummaryDto> getOptionOIData(String indexName, String forDate)  throws BusinessException {
		List<OptionSummaryDto> retList = new ArrayList<OptionSummaryDto>();
		
		try {
			SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(stdFormat.parse(forDate));
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.set(Calendar.MINUTE, 14);
			String dateStrBegiN = postgresFormat.format(cal.getTime());			
			cal.set(Calendar.HOUR_OF_DAY, 20);
			//cal.add(Calendar.DATE, 1);
			
			String dateStreND = postgresFormat.format(cal.getTime());

			String query = "SELECT optionSummary.id as id, optionSummary.record_date as recordDate, optionSummary.totalceoi as totalceoi, optionSummary.totalpeoi as totalpeoi, optionSummary.totalchangeinceoi as totalchangeinceoi, optionSummary.totalchangeinpeoi as totalchangeinpeoi"
					+ ", optionSummary.changeinceoifromprevioustick as changeinceoifromprevioustick, optionSummary.changeinpeoifromprevioustick as changeinpeoifromprevioustick, optionSummary.changein_total_ceoi_since_920 as changein_total_ceoi_since_920, optionSummary.changein_total_peoi_since_920 as changein_total_peoi_since_920"
					+ ", optionSummary.pcr as pcr"
					+ ", optionSummary.ce_highestoi_1st_strike as ce_highestoi_1st_strike, optionSummary.ce_highestoi_2nd_strike as ce_highestoi_2nd_strike ,optionSummary.ce_highestoi_3rd_strike as ce_highestoi_3rd_strike"
					+ ", optionSummary.pe_highestoi_1st_strike as pe_highestoi_1st_strike, optionSummary.pe_highestoi_2nd_strike as pe_highestoi_2nd_strike ,optionSummary.pe_highestoi_3rd_strike as pe_highestoi_3rd_strike"
					+ ", optionSummary.underlyingValue as underlyingValue"
					+ ", optionSummary.option_seller_mean_point as optionSellerMeanPoint"
					+ ", optionSummary.option_seller_mean_point_by_worth as optionSellerMeanPointByWorth"
					+ ", optionSummary.best_cost_effective_strike as best_cost_effective_strike"
					+ ", optionSummary.best_cost_effective_strike_by_worth as best_cost_effective_strike_by_worth"
					+ ", optionSummary.option_seller_mean_point_from_5 as option_seller_mean_point_from_5"
					+ ", optionSummary.best_cost_effective_strike_from_5 as best_cost_effective_strike_from_5"
					+ " FROM option_market_direction optionSummary "
					+ " where optionSummary.group_name like '" + indexName + "' and optionSummary.underlyingValue is not null"
					+ " and optionSummary.record_date> '" + dateStrBegiN + "'"
					+ " and optionSummary.record_date< '" + dateStreND + "'"
					+ "	order by optionSummary.record_date desc";	// + " and optionSummary.record_date > '" + + "'"
				
			log.info(query);
				
			Query q = entityManager.createNativeQuery(query);	
				
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				OptionSummaryDto aVO = new OptionSummaryDto();
					 
				aVO.setId( ((BigInteger)rowdata[0]).longValue());
				aVO.setRecordDate((Date)rowdata[1]);
				log.info(indexName + "-" + aVO.getRecordDate());
				if (rowdata[2]!=null) aVO.setTotalCEOI((Integer)rowdata[2]); else aVO.setTotalCEOI(retList.get(0).getTotalCEOI());
				if (rowdata[3]!=null) aVO.setTotalPEOI((Integer)rowdata[3]); else aVO.setTotalPEOI(retList.get(0).getTotalPEOI());
				if (rowdata[4]!=null) aVO.setTotalChangeInCEOI((Integer)rowdata[4]); else aVO.setTotalChangeInCEOI(retList.get(0).getTotalChangeInCEOI());
				if (rowdata[5]!=null) aVO.setTotalChangeInPEOI((Integer)rowdata[5]); else aVO.setTotalChangeInPEOI(retList.get(0).getTotalChangeInPEOI());
				if (rowdata[6]!=null) aVO.setChangeInCEOIFromPreviousTick((Float)rowdata[6]); else aVO.setChangeInCEOIFromPreviousTick(retList.get(0).getChangeInCEOIFromPreviousTick());
				if (rowdata[7]!=null) aVO.setChangeInPEOIFromPreviousTick((Float)rowdata[7]); else aVO.setChangeInPEOIFromPreviousTick(retList.get(0).getChangeInPEOIFromPreviousTick());
					 
				if (rowdata[8]!=null) aVO.setChangeInCEOIFrom920Tick((Float)rowdata[8]); else aVO.setChangeInCEOIFrom920Tick(retList.get(0).getChangeInCEOIFrom920Tick());
				if (rowdata[9]!=null) aVO.setChangeInPEOIFrom920Tick((Float)rowdata[9]); else aVO.setChangeInPEOIFrom920Tick(retList.get(0).getChangeInPEOIFrom920Tick());
				if (rowdata[10]!=null) aVO.setPcr((Float)rowdata[10]); else aVO.setPcr(retList.get(0).getPcr());
				
				if (rowdata[11]!=null) aVO.setMinCEWithHighestOI( Math.min((Float)rowdata[11], Math.min((Float)rowdata[12], (Float)rowdata[13])));  else aVO.setMinCEWithHighestOI(retList.get(0).getMinCEWithHighestOI());
				//Math.min((Float)rowdata[11], Math.min((Float)rowdata[12], (Float)rowdata[13])); //  
				if (rowdata[14]!=null) aVO.setMaxPEWithHighestOI(Math.max((Float)rowdata[14], Math.max((Float)rowdata[15], (Float)rowdata[16])));  else aVO.setMaxPEWithHighestOI(retList.get(0).getMaxPEWithHighestOI());
				//Math.max((Float)rowdata[14], Math.max((Float)rowdata[15], (Float)rowdata[16])); // 
				
				
				if (rowdata[17]!=null)  aVO.setUnderlyingValue((Float)rowdata[17]); else aVO.setUnderlyingValue(retList.get(0).getUnderlyingValue());
				if (rowdata[18]!=null)  aVO.setOptionSellerMeanValue((Float)rowdata[18]); else aVO.setOptionSellerMeanValue(0f);
				if (rowdata[19]!=null)  aVO.setOptionSellerMeanValueByWorth((Float)rowdata[19]); else aVO.setOptionSellerMeanValueByWorth(0f);
				if (rowdata[20]!=null)  aVO.setBestCostEffectiveStrike((Float)rowdata[20]); else aVO.setBestCostEffectiveStrike(0f);
				if (rowdata[21]!=null)  aVO.setBestCostEffectiveStrikeByWorth((Float)rowdata[21]); else aVO.setBestCostEffectiveStrikeByWorth(0f);
				
				if (rowdata[22]!=null)  aVO.setOptionSellerMeanValueFrom5((Float)rowdata[22]); else aVO.setOptionSellerMeanValueFrom5(0f);
				if (rowdata[23]!=null)  aVO.setBestCostEffectiveStrikeFrom5((Float)rowdata[23]); else aVO.setBestCostEffectiveStrikeFrom5(0f);
				
				
				retList.add(0, aVO);					
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
		return retList;
	}
	
	public List<ScripEOD> getZerodhaCandleMinuteData(String symbol, String dataDate)throws BusinessException {
		List<ScripEOD> retList = new ArrayList<ScripEOD>();
		try {
			SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(inputFormat.parse(dataDate));
			
			Date beginTime = cal.getTime();
			cal.set(Calendar.HOUR, 16);
			Date endTime = cal.getTime();
			
			String query = "select quote_time, open_price, high_price, low_price, close_price, volume, weighted_volume from option_zerodha_candle";
			
			query = query + " where nse_code like '" + symbol + "'";
			
			query = query + " and quote_time >= '" + dataDateFormat.format(beginTime)+ "' and quote_time <= '" +  dataDateFormat.format(endTime) + "' order by quote_time asc";
			log.info("In getZerodhaCandleMinuteData Query="+query);
			Query q = entityManager.createNativeQuery(query);
			
			System.out.println("symbol-"+symbol);
			System.out.println("dataDate-"+dataDate);
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				ScripEOD aDto = new ScripEOD();
				aDto.setDataDate((Date)rowdata[0]);
				aDto.setOpenPrice((Float)rowdata[1]);
				aDto.setHighPrice((Float)rowdata[2]);
				aDto.setLowPrice((Float)rowdata[3]);
				aDto.setClosePrice((Float)rowdata[4]);
				if (symbol.contains("NIFTY")) {
					Float volume =  (Float)rowdata[6];
					if (volume!=null) {volume = volume*1000f;
						aDto.setVolume((volume).longValue());
					} else {
						aDto.setVolume(0L);
					}
				} else {
					aDto.setVolume(((Float)rowdata[5]).longValue());
				}
				retList.add(aDto);
			}			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return retList;
	}
	
	public String getOptionDeltaRangeRawDataAnalysis(Long mainInstrumentId, String forDate, float baseDelta) throws BusinessException {
		log.info("In getOptionTimeValueAnalysis forDate="+forDate);
		Map<String, List<OptionOI>> oiDataMap = new HashMap<String, List<OptionOI>>();
		String csvFilename = "D:\\temp\\junk\\OptionDeltaRangeRawDataAnalysis" + mainInstrumentId +forDate.replace("/", "-")+"_"+ baseDelta+ ".csv";
		try {
			FileWriter writer = new FileWriter(csvFilename);
            writer.write("QuoteTime,IndexAt,Futures Ltp,"
            		+ "Straddle Premium,"
            		+ "CE Gamma, PE Gamma,"
            		+ "Avg CE IV, Avg PE IV,"
            		+ "D-R CE Avg-Ltp,"
            		+ "D-R PE Avg-Ltp," 
            		+ "D-R CE Avg-IV," 
            		+ "D-R PE Avg-IV," 
            		+ "D-R CE Avg-Delta," 
            		+ "D-R PE Avg-Delta," 
            		+ "D-R CE Avg-Gamma," 
            		+ "D-R PE Avg-Gamma," 
            		+ "D-R CE Avg-Vega,"
            		+ "D-R PE Avg-Vega,"
            		+ "D-R CE Worth(Cr.),"
            		+ "D-R PE Worth(Cr.),"
            		+ "D-R CE OI(Cr.),"
            		+ "D-R PE OI(Cr.),"
            		+ "D-R CE Delta OI(Cr.),"
            		+ "D-R PE Delta OI(Cr.),"
            		+ "D-R CE Gamma OI(Cr.),"
            		+ "D-R PE Gamma OI(Cr.),"
            		+ "D-R CE Full Delta OI(Cr.),"
            		+ "D-R PE Full Delta OI(Cr.),"
            		+ "SS-5 Strike Avg CE IV,"
            		+ "SS-5 Strike Avg PE IV,"
            		+ "SS-10 Strike Avg CE IV,"
            		+ "SS-10 Strike Avg PE IV,"
            		+ "SS-20 Strike Avg CE IV,"
            		+ "SS-20 Strike Avg PE IV,"
            		
					+ "D-R CE Full Avg IV,"
					+ "D-R PE Full Avg IV,"
					
					+ "D-R CE Hybrid Avg IV,"
					+ "D-R PE Hybrid Avg IV,"

					+ "D-R CE Volume 1M,"
					+ "D-R PE Volume 1M"

            		+ "\r\n");
            
            SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat stdFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			 
			String dateStrEnd = "";
			String dateStrBegin = "";
			Calendar cal = Calendar.getInstance();
			if (forDate.length()>12) {
				cal.setTime(longFormat.parse(forDate));
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.add(Calendar.MINUTE, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			} else  {
				cal.setTime(stdFormat.parse(forDate));
				cal.set(Calendar.HOUR_OF_DAY, 9);
				cal.set(Calendar.MINUTE, 15);
				
				dateStrBegin = postgresFormat.format(cal.getTime());
				cal.set(Calendar.MINUTE, 30);
				cal.set(Calendar.HOUR_OF_DAY, 15);
				dateStrEnd = postgresFormat.format(cal.getTime());
			}
			
			String fetchSql = "select record_time, instrumentLtp, futures_Ltp, celtp, peltp, cegamma, pegamma, totalceiv, totalpeiv,"
					+ " deltaRangeCEAvgLtp, deltaRangePEAvgLtp, deltaRangeCEAvgIv, deltaRangePEAvgIv, deltaRangeCEAvgDelta,"
					+ " deltaRangePEAvgDelta, deltaRangeCEAvgGamma, deltaRangePEAvgGamma, deltaRangeCEAvgVega, deltaRangePEAvgVega,"
					+ " deltarangeceworth, deltarangepeworth,"
					+ " deltarangeceoi, deltarangepeoi,"
					+ " deltarangecedeltaoi, deltarangepedeltaoi,"
					
					+ " deltarangecegammaoi, deltarangepegammaoi,"
					+ " deltarangecefulldeltaoi, deltarangepefulldeltaoi,"
					
					+ " selectivestrike_avgceiv, selectivestrike_avgpeiv,"
					+ " selective10strike_avgceiv, selective10strike_avgpeiv,"
					+ " selective20strike_avgceiv, selective20strike_avgpeiv,"
					
					+ " deltaRangeCEFullAvgIv, deltaRangePEFullAvgIv,"
					+ " deltaRangeHybridCEAvgIv, deltaRangeHybridPEAvgIv,"
					+ " deltaRangeCEvolume1min, deltaRangePEvolume1min"
					
					+ " from db_link_option_atm_movement_data oamd"
					+ " where f_main_instrument = '" + mainInstrumentId + "'"
					+ " and record_time > '" + dateStrBegin +"' and record_time < '" + dateStrEnd + "' order by record_time";
			
			log.info("fetchSql "+fetchSql);
			Query q = entityManager.createNativeQuery(fetchSql);	
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				Date quoteTime = (Timestamp) rowdata[0];
				float indexltp = (Float) rowdata[1];
				float futuresLtp = (Float) rowdata[2];
				
				float ceLtp = (Float) rowdata[3];
				float peLtp = (Float) rowdata[4];
				
				float cegamma = (Float) rowdata[5];
				float pegamma = (Float) rowdata[6];
				float totalCeOi = (Float) rowdata[7];
				float totalPeOi = (Float) rowdata[8];
				
				float drCELtp = (Float) rowdata[9];
				float drPELtp = (Float) rowdata[10];
				float drCEIV = (Float) rowdata[11];
				float drPEIV = (Float) rowdata[12];
				float drCEDelta = (Float) rowdata[13];
				float drPEDelta = (Float) rowdata[14];
				
				float drCEGamma = (Float) rowdata[15];
				float drPEGamma = (Float) rowdata[16];
				
				float drCEVega = (Float) rowdata[17];
				float drPEVega = (Float) rowdata[18];
				
				float drCEWorth = (Float) rowdata[19];
				float drPEWorth = (Float) rowdata[20];
				
				float drCEOI = (Float) rowdata[21];
				float drPEOI = (Float) rowdata[22];
				
				float drCEDeltaOI = (Float) rowdata[23];
				float drPEDeltaOI = (Float) rowdata[24];
				
				float drCEGammaOI = (Float) rowdata[25];
				float drPEGammaOI = (Float) rowdata[26];
				
				float drCEFullDeltaOI = (Float) rowdata[27];
				float drPEFullDeltaOI = (Float) rowdata[28];
				
				float ss5StrikeAvgCEIV = (Float) rowdata[29];
				float ss5StrikeAvgPEIV = (Float) rowdata[30];
				
				float ss10StrikeAvgCEIV = (Float) rowdata[31];
				float ss10StrikeAvgPEIV = (Float) rowdata[32];
				
				float ss20StrikeAvgCEIV = (Float) rowdata[33];
				float ss20StrikeAvgPEIV = (Float) rowdata[34];
				
				float deltaRangeCEFullAvgIv = (Float) rowdata[35];
				float deltaRangePEFullAvgIv = (Float) rowdata[36];
				
				float deltaRangeHybridCEAvgIv = (Float) rowdata[37];
				float deltaRangeHybridPEAvgIv = (Float) rowdata[38];
				
				float deltaRangeCEVolume1M = (Float) rowdata[39];
				float deltaRangePEVolume1M = (Float) rowdata[40];
				
				
				writer.write(postgresFormat.format(quoteTime)+","+indexltp + "," + futuresLtp + "," +  (ceLtp+peLtp)
						+ "," + cegamma + "," + pegamma + "," + totalCeOi+ "," + totalPeOi
						+ "," + drCELtp + "," + drPELtp
						+ "," + drCEIV + "," + drPEIV
						+ "," + drCEDelta + "," + drPEDelta
						+ "," + drCEGamma + "," + drPEGamma
						+ "," + drCEVega + "," + drPEVega
						+ "," + decimalFormat.format(drCEWorth) + "," + decimalFormat.format(drPEWorth)
						+ "," + decimalFormat.format(drCEOI) + "," + decimalFormat.format(drPEOI)
						+ "," + decimalFormat.format(drCEDeltaOI) + "," + decimalFormat.format(drPEDeltaOI)
						
						+ "," + decimalFormat.format(drCEGammaOI) + "," + decimalFormat.format(drPEGammaOI)
						+ "," + decimalFormat.format(drCEFullDeltaOI) + "," + decimalFormat.format(drPEFullDeltaOI)
						
						+ "," + ss5StrikeAvgCEIV + "," + ss5StrikeAvgPEIV
						+ "," + ss10StrikeAvgCEIV + "," + ss20StrikeAvgPEIV
						+ "," + ss10StrikeAvgCEIV + "," + ss20StrikeAvgPEIV
						+ "," + deltaRangeCEFullAvgIv + "," + deltaRangePEFullAvgIv
						+ "," + deltaRangeHybridCEAvgIv + "," + deltaRangeHybridPEAvgIv
						+ "," + deltaRangeCEVolume1M + "," + deltaRangePEVolume1M
						+"\r\n");
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvFilename;
	}
}

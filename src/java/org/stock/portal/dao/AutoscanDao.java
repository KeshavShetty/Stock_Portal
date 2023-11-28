
package org.stock.portal.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.AutoscanMaster;
import org.stock.portal.domain.AutoscanResult;
import org.stock.portal.domain.BSEEodData;
import org.stock.portal.domain.CandlePatternLibrary;
import org.stock.portal.domain.CandlePatternScripMatch;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.dto.AutoscanSearchCriteriaDTO;
import org.stock.portal.domain.dto.EODForPastPerformanceCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.AdvancedAnalysisSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.AnalysisSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.CandlePatternSearchCriteriaDTO;


/**
 * 
 * @author European Dynamics
 */
public class AutoscanDao {
	
	/** Logger instance for the class. */
    protected static final Logger log = Logger.getLogger(AutoscanDao.class.getName());
    		
	/** Session EJB EntityManager - Injected by constructor to DAO. */
	private EntityManager entityManager = null;
	
	/**
	 * 
	 * @param eManager
	 */
	public AutoscanDao(final EntityManager eManager) {
		this.entityManager = eManager;
	}	

	@SuppressWarnings("unchecked")
	public List<AutoscanResult> searchAutoscanByCriteria(AutoscanSearchCriteriaDTO autoscanSearchCriteriaDTO) {	
		log.debug(" DAO-HEADER: searchAutoscanByCriteria");		
		List<AutoscanResult> retList = null;
		
		try {
			StringBuffer query = new StringBuffer("SELECT OBJECT(autoscan) FROM AutoscanResult autoscan join fetch autoscan.scrip");
			boolean whereAdded = false;			
			
			if (autoscanSearchCriteriaDTO.getFromDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" autoscan.signalDate >= :fromDate");				
			}
			if (autoscanSearchCriteriaDTO.getToDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" autoscan.signalDate <= :toDate");				
			}
			
			if (autoscanSearchCriteriaDTO.getAverageVolume()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" autoscan.scrip.averageVolume > :avgVolume");				
			}
			if (autoscanSearchCriteriaDTO.getScanCodeId()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" autoscan.studyMaster.id = :scanCodeId");				
			}
			
			if (autoscanSearchCriteriaDTO.getWatchlistId()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" autoscan.scrip.id in (select wlItem.scrip.id from WatchlistItem wlItem where wlItem.watchlist.id="+autoscanSearchCriteriaDTO.getWatchlistId()+")");				
			}
			if (autoscanSearchCriteriaDTO.getTaTypeId()!=null) {
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" autoscan.studyMaster.studyType = :taStudyTypeId");	
			}
			if (autoscanSearchCriteriaDTO.getExchangeCodeId()!=null) {
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" autoscan.exchangeCode = :exchangeCode");	
			}
			if (autoscanSearchCriteriaDTO.getOrderBy()!=null)  { 
				query.append(" ORDER BY " + autoscanSearchCriteriaDTO.getOrderBy());
				if (autoscanSearchCriteriaDTO.getOrderType()==null) query.append(" ASC "); 
				else query.append(" "+ autoscanSearchCriteriaDTO.getOrderType() + " "); 
			}
	    	
			Query jpaQry = this.entityManager.createQuery(query.toString());			
			
			if (autoscanSearchCriteriaDTO.getFromDate()!=null)  { 
				jpaQry.setParameter("fromDate", autoscanSearchCriteriaDTO.getFromDate());
			}
			if (autoscanSearchCriteriaDTO.getToDate()!=null)  { 
				jpaQry.setParameter("toDate", autoscanSearchCriteriaDTO.getToDate());
			}			
			if (autoscanSearchCriteriaDTO.getAverageVolume()!=null) { 
				jpaQry.setParameter("avgVolume", autoscanSearchCriteriaDTO.getAverageVolume());
			}			
			if (autoscanSearchCriteriaDTO.getScanCodeId()!=null)  { 
				jpaQry.setParameter("scanCodeId", autoscanSearchCriteriaDTO.getScanCodeId());
			}
			if (autoscanSearchCriteriaDTO.getTaTypeId()!=null) {
				jpaQry.setParameter("taStudyTypeId", autoscanSearchCriteriaDTO.getTaTypeId());
			}
			if (autoscanSearchCriteriaDTO.getExchangeCodeId()!=null) {
				jpaQry.setParameter("exchangeCode", autoscanSearchCriteriaDTO.getExchangeCodeId());	
			}
			jpaQry.setMaxResults(autoscanSearchCriteriaDTO.getNumberOfRecords());
			retList = (List<AutoscanResult>)jpaQry.getResultList();			
		} catch (NoResultException e) {
			log.debug(" -- searchautoAutoscanByCriteria : No intraday data found with these criteria ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		if (retList != null) {
			log.info(" -- searchautoAutoscanByCriteria : size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public List<AutoscanMaster> getAutoscanMasterlistByType(Integer scanType) {
		log.debug(" DAO-HEADER: getAutoscanMasterlistByType scanType="+scanType);		
		List<AutoscanMaster> retList = null;
		
		try {
			StringBuffer query = new StringBuffer("SELECT OBJECT(scanMaster) FROM AutoscanMaster scanMaster where scanMaster.studyType = :scanType order by scanMaster.id");
			Query jpaQry = this.entityManager.createQuery(query.toString());
			jpaQry.setParameter("scanType", scanType);
			retList = (List<AutoscanMaster>)jpaQry.getResultList();	
		} catch (NoResultException e) {
			log.debug(" -- searchautoAutoscanByCriteria : No intraday data found with these criteria ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		if (retList != null) {
			log.info(" -- searchautoAutoscanByCriteria : size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public List<AutoscanMaster> getAutoscanMasterlist() {
		log.debug(" DAO-HEADER: getAutoscanMasterlist scanType=");		
		List<AutoscanMaster> retList = null;
		
		try {
			StringBuffer query = new StringBuffer("SELECT OBJECT(scanMaster) FROM AutoscanMaster scanMaster order by scanMaster.studyType, scanMaster.shortName");
			Query jpaQry = this.entityManager.createQuery(query.toString());
			retList = (List<AutoscanMaster>)jpaQry.getResultList();	
		} catch (NoResultException e) {
			log.debug(" -- searchautoAutoscanByCriteria : No intraday data found with these criteria ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		if (retList != null) {
			log.info(" -- searchautoAutoscanByCriteria : size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public List<BSEEodData> getEodForPastPerformance(EODForPastPerformanceCriteriaDTO eodForPastPerformanceCriteriaDTO) {	
		log.debug(" DAO-HEADER: getEodForPastPerformance");		
		List<BSEEodData> retList = null;
		
		try {
			StringBuffer query = new StringBuffer("SELECT OBJECT(bseEodData) FROM BSEEodData bseEodData join fetch bseEodData.scrip");
			boolean whereAdded = false;			
			
			if (eodForPastPerformanceCriteriaDTO.getForDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" bseEodData.dataDate = :forDate");				
			}
			
			if (eodForPastPerformanceCriteriaDTO.getAverageVolume()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" bseEodData.scrip.averageVolume > :avgVolume");				
			}
			
			if (eodForPastPerformanceCriteriaDTO.getWatchlistId()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				query.append(" bseEodData.scrip.id in (select wlItem.scrip.id from WatchlistItem wlItem where wlItem.watchlist.id="+eodForPastPerformanceCriteriaDTO.getWatchlistId()+")");				
			}
			
			if (eodForPastPerformanceCriteriaDTO.getOrderBy()!=null)  { 
				query.append(" ORDER BY " + eodForPastPerformanceCriteriaDTO.getOrderBy());
				if (eodForPastPerformanceCriteriaDTO.getOrderType()==null) query.append(" ASC "); 
				else query.append(" "+ eodForPastPerformanceCriteriaDTO.getOrderType() + " "); 
			}
	    	
			Query jpaQry = this.entityManager.createQuery(query.toString());			
			
			if (eodForPastPerformanceCriteriaDTO.getForDate()!=null)  { 
				jpaQry.setParameter("forDate", eodForPastPerformanceCriteriaDTO.getForDate());
			}		
			if (eodForPastPerformanceCriteriaDTO.getAverageVolume()!=null) { 
				jpaQry.setParameter("avgVolume", eodForPastPerformanceCriteriaDTO.getAverageVolume());
			}	
			
			jpaQry.setMaxResults(eodForPastPerformanceCriteriaDTO.getNumberOfRecords());
			retList = (List<BSEEodData>)jpaQry.getResultList();			
		} catch (NoResultException e) {
			log.debug(" -- searchautoAutoscanByCriteria : No intraday data found with these criteria ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		if (retList != null) {
			log.info(" -- searchautoAutoscanByCriteria : size = " + retList.size());
		}
		return retList;
	}
	
	private List<Long> getLongIdsCollection(String commaSeparatedIdString) {
		List<Long> retList = new ArrayList<Long>();
		if (commaSeparatedIdString.trim().length()>0) {
			String[] allVal = commaSeparatedIdString.split(",");
			
			for(int i=0;i<allVal.length;i++) {
				retList.add(Long.parseLong(allVal[i].trim()));
			}
		} 
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchAnalysis(AnalysisSearchCriteriaDTO analysisSearchCriteriaDTO) throws BusinessException {
		
		log.debug(" DAO-HEADER: searchScripByCriteria");
		
		Map<String, Object> retMap = new HashMap<String, Object>();;
		System.out.println("analysisSearchCriteriaDTO.getScripId()="+analysisSearchCriteriaDTO.getScripId()+" getScripIds="+analysisSearchCriteriaDTO.getScripIds());
		try {
			StringBuffer query = new StringBuffer();			
			boolean whereAdded = false;
			
			if (analysisSearchCriteriaDTO.getScripId()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.scrip.id = :scripId");				
			} else if (analysisSearchCriteriaDTO.getScripIds()!=null && analysisSearchCriteriaDTO.getScripIds().length()>0)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.scrip.id in (:scripIds)");				
			}
			if (analysisSearchCriteriaDTO.getFromDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.signalDate >= :fromDate");	
			}
			if (analysisSearchCriteriaDTO.getToDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.signalDate <= :toDate");	
			}
			if (analysisSearchCriteriaDTO.getAnalysisType()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.studyMaster.studyType = :analysisType");	
			}
			
			StringBuffer countQuery = new StringBuffer(query.toString()); //Order by and type not required for count query
			System.out.println("analysisSearchCriteriaDTO.getOrderBy()="+analysisSearchCriteriaDTO.getOrderBy()+" ordertype="+analysisSearchCriteriaDTO.getOrderType());
			if (analysisSearchCriteriaDTO.getOrderBy()!=null)  { 
				query.append(" ORDER BY " + analysisSearchCriteriaDTO.getOrderBy());
				if (analysisSearchCriteriaDTO.getOrderType()==null) query.append(" ASC "); 
				else query.append(" "+ analysisSearchCriteriaDTO.getOrderType() + " "); 
			}
        	
			Query jpaQry = this.entityManager.createQuery("SELECT OBJECT(asRes) FROM AutoscanResult asRes " + query.toString());
			Query countQry = this.entityManager.createQuery("SELECT count(*) FROM AutoscanResult asRes " + countQuery.toString());
			
			
			// SELECT OBJECT(nFeed) FROM NewsFeed nFeed
			
			
			if (analysisSearchCriteriaDTO.getScripId()!=null)  {  
				jpaQry.setParameter("scripId", (analysisSearchCriteriaDTO.getScripId()));
				countQry.setParameter("scripId", (analysisSearchCriteriaDTO.getScripId()));
			} else if (analysisSearchCriteriaDTO.getScripIds()!=null && analysisSearchCriteriaDTO.getScripIds().length()>0)  { 
				jpaQry.setParameter("scripIds", getLongIdsCollection(analysisSearchCriteriaDTO.getScripIds()));
				countQry.setParameter("scripIds", getLongIdsCollection(analysisSearchCriteriaDTO.getScripIds()));
			}   
			
			if (analysisSearchCriteriaDTO.getFromDate()!=null)  { 
				jpaQry.setParameter("fromDate", analysisSearchCriteriaDTO.getFromDate());
				countQry.setParameter("fromDate", analysisSearchCriteriaDTO.getFromDate());
			}
			if (analysisSearchCriteriaDTO.getToDate()!=null)  { 
				jpaQry.setParameter("toDate", analysisSearchCriteriaDTO.getToDate());
				countQry.setParameter("toDate", analysisSearchCriteriaDTO.getToDate());
			}
			if (analysisSearchCriteriaDTO.getAnalysisType()!=null)  { 
				jpaQry.setParameter("analysisType", analysisSearchCriteriaDTO.getAnalysisType());
				countQry.setParameter("analysisType", analysisSearchCriteriaDTO.getAnalysisType());
			}
			if (analysisSearchCriteriaDTO.getPageNumber()!=null) {
				System.out.println("First result="+(analysisSearchCriteriaDTO.getPageNumber()-1)*analysisSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setFirstResult((analysisSearchCriteriaDTO.getPageNumber()-1)*analysisSearchCriteriaDTO.getRecordPerPage());			}
			if (analysisSearchCriteriaDTO.getRecordPerPage()!=null) {	
				System.out.println("analysisSearchCriteriaDTO.getRecordPerPage()="+analysisSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setMaxResults(analysisSearchCriteriaDTO.getRecordPerPage());
			}			
			List<AutoscanResult> analysisList = (List<AutoscanResult>)jpaQry.getResultList();
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
	public Map<String, Object> searchAnalysisByAdvancedCriteria(AdvancedAnalysisSearchCriteriaDTO analysisSearchCriteriaDTO) throws BusinessException {
		
		log.debug(" DAO-HEADER: searchAnalysisByAdvancedCriteria");
		
		Map<String, Object> retMap = new HashMap<String, Object>();;
		
		try {
			StringBuffer query = new StringBuffer();			
			boolean whereAdded = false;			
			
			if (analysisSearchCriteriaDTO.getFromDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.signalDate >= :fromDate");	
			}
			if (analysisSearchCriteriaDTO.getToDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.signalDate <= :toDate");	
			}
			if (analysisSearchCriteriaDTO.getAnalysisType()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.studyMaster.studyType = :analysisType");	
			}
			if (analysisSearchCriteriaDTO.getSelectedAnalysis()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.studyMaster.id = :selectedAnalysis");	
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
			
			if (analysisSearchCriteriaDTO.getScripName()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" lower(scrInternal.name) like :scripName");	
				internalScripCountQuery.append(" lower(scrInternal.name) like :scripName");	
			}
			if (analysisSearchCriteriaDTO.getBseCode()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCode like :bseCode");
				internalScripCountQuery.append(" scrInternal.bseCode like :bseCode");
			}
			if (analysisSearchCriteriaDTO.getNseCode()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.nseCode like :nseCode");
				internalScripCountQuery.append(" scrInternal.nseCode like :nseCode");	
			}			
			if (analysisSearchCriteriaDTO.getMinCmp()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCmp >= :minCmp");
				internalScripCountQuery.append(" scrInternal.bseCmp >= :minCmp");
			}
			if (analysisSearchCriteriaDTO.getMaxCmp()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCmp <= :maxCmp");
				internalScripCountQuery.append(" scrInternal.bseCmp <= :maxCmp");
			}			
			if (analysisSearchCriteriaDTO.getMinEps()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.eps >= :minEps");
				internalScripCountQuery.append(" scrInternal.eps >= :minEps");
			}
			if (analysisSearchCriteriaDTO.getMaxEps()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.eps <= :maxEps");
				internalScripCountQuery.append(" scrInternal.eps <= :maxEps");
			}			
			if (analysisSearchCriteriaDTO.getMinPe()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.pe >= :minPe");	
				internalScripCountQuery.append(" scrInternal.pe >= :minPe");
			}
			if (analysisSearchCriteriaDTO.getMaxPe()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.pe <= :maxPe");	
				internalScripCountQuery.append(" scrInternal.pe <= :maxPe");
			}
			if (analysisSearchCriteriaDTO.getAverageVolume()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.nseAverageVolume >= :averageVolume");	
				internalScripCountQuery.append(" scrInternal.nseAverageVolume >= :averageVolume");
			}	
			if (analysisSearchCriteriaDTO.getSelectedWatchlist()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.id in (select scrip.id from WatchlistItem WL where watchlist.id = :watchlistId)");	
				internalScripCountQuery.append(" scrInternal.id in (select scrip.id from WatchlistItem WL where watchlist.id = :watchlistId)");
			}
			internalScripQuery.append(")"); //Close internal sql query
			internalScripCountQuery.append(")");
			
			System.out.println("analysisSearchCriteriaDTO.getOrderBy()="+analysisSearchCriteriaDTO.getOrderBy()+" ordertype="+analysisSearchCriteriaDTO.getOrderType());
			if (analysisSearchCriteriaDTO.getOrderBy()!=null)  { 
				internalScripQuery.append(" ORDER BY " + analysisSearchCriteriaDTO.getOrderBy());
				if (analysisSearchCriteriaDTO.getOrderType()==null) internalScripQuery.append(" ASC "); 
				else internalScripQuery.append(" "+ analysisSearchCriteriaDTO.getOrderType() + " "); 
			}
			
			System.out.println("Main query="+"SELECT OBJECT(asRes) FROM AutoscanResult asRes " + query.toString() + internalScripQuery);
			System.out.println("Count query="+"SELECT count(*) FROM AutoscanResult asRes " + countQuery.toString() + internalScripCountQuery);
			Query jpaQry = this.entityManager.createQuery("SELECT OBJECT(asRes) FROM AutoscanResult asRes " + query.toString() + internalScripQuery );
			Query countQry = this.entityManager.createQuery("SELECT count(*) FROM AutoscanResult asRes " + countQuery.toString() + internalScripCountQuery);
			
			if (analysisSearchCriteriaDTO.getFromDate()!=null)  { 
				jpaQry.setParameter("fromDate", analysisSearchCriteriaDTO.getFromDate());
				countQry.setParameter("fromDate", analysisSearchCriteriaDTO.getFromDate());
			}
			if (analysisSearchCriteriaDTO.getToDate()!=null)  { 
				jpaQry.setParameter("toDate", analysisSearchCriteriaDTO.getToDate());
				countQry.setParameter("toDate", analysisSearchCriteriaDTO.getToDate());
			}
			if (analysisSearchCriteriaDTO.getAnalysisType()!=null)  { 
				jpaQry.setParameter("analysisType", analysisSearchCriteriaDTO.getAnalysisType());
				countQry.setParameter("analysisType", analysisSearchCriteriaDTO.getAnalysisType());
			}
			if (analysisSearchCriteriaDTO.getSelectedAnalysis()!=null)  { 
				jpaQry.setParameter("selectedAnalysis", analysisSearchCriteriaDTO.getSelectedAnalysis());
				countQry.setParameter("selectedAnalysis", analysisSearchCriteriaDTO.getSelectedAnalysis());
			}
			
			
			if (analysisSearchCriteriaDTO.getScripName()!=null)  { 
				jpaQry.setParameter("scripName", "%"+analysisSearchCriteriaDTO.getScripName().toLowerCase()+"%");
				countQry.setParameter("scripName", "%"+analysisSearchCriteriaDTO.getScripName().toLowerCase()+"%");	
			}
			if (analysisSearchCriteriaDTO.getBseCode()!=null)  { 
				jpaQry.setParameter("bseCode", analysisSearchCriteriaDTO.getBseCode());
				countQry.setParameter("bseCode", analysisSearchCriteriaDTO.getBseCode());
			}
			if (analysisSearchCriteriaDTO.getNseCode()!=null)  { 
				jpaQry.setParameter("nseCode", analysisSearchCriteriaDTO.getNseCode());
				countQry.setParameter("nseCode", analysisSearchCriteriaDTO.getNseCode());
			}			
			if (analysisSearchCriteriaDTO.getMinCmp()!=null)  { 
				jpaQry.setParameter("minCmp", analysisSearchCriteriaDTO.getMinCmp());
				countQry.setParameter("minCmp", analysisSearchCriteriaDTO.getMinCmp());
			}
			if (analysisSearchCriteriaDTO.getMaxCmp()!=null)  { 
				jpaQry.setParameter("maxCmp", analysisSearchCriteriaDTO.getMaxCmp());
				countQry.setParameter("maxCmp", analysisSearchCriteriaDTO.getMaxCmp());
			}			
			if (analysisSearchCriteriaDTO.getMinEps()!=null)  { 
				jpaQry.setParameter("minEps", analysisSearchCriteriaDTO.getMinEps());
				countQry.setParameter("minEps", analysisSearchCriteriaDTO.getMinEps());
			}
			if (analysisSearchCriteriaDTO.getMaxEps()!=null)  { 
				jpaQry.setParameter("maxEps", analysisSearchCriteriaDTO.getMaxEps());
				countQry.setParameter("maxEps", analysisSearchCriteriaDTO.getMaxEps());
			}			
			if (analysisSearchCriteriaDTO.getMinPe()!=null)  { 
				jpaQry.setParameter("minPe", analysisSearchCriteriaDTO.getMinPe());
				countQry.setParameter("minPe", analysisSearchCriteriaDTO.getMinPe());
			}
			if (analysisSearchCriteriaDTO.getMaxPe()!=null)  { 
				jpaQry.setParameter("maxPe", analysisSearchCriteriaDTO.getMaxPe());
				countQry.setParameter("maxPe", analysisSearchCriteriaDTO.getMaxPe());
			}
			if (analysisSearchCriteriaDTO.getAverageVolume()!=null)  { 
				jpaQry.setParameter("averageVolume", analysisSearchCriteriaDTO.getAverageVolume());
				countQry.setParameter("averageVolume", analysisSearchCriteriaDTO.getAverageVolume());
			}			
			if (analysisSearchCriteriaDTO.getSelectedWatchlist()!=null)  { 
				jpaQry.setParameter("watchlistId", analysisSearchCriteriaDTO.getSelectedWatchlist());
				countQry.setParameter("watchlistId", analysisSearchCriteriaDTO.getSelectedWatchlist());
			}
			if (analysisSearchCriteriaDTO.getPageNumber()!=null) {
				System.out.println("First result="+(analysisSearchCriteriaDTO.getPageNumber()-1)*analysisSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setFirstResult((analysisSearchCriteriaDTO.getPageNumber()-1)*analysisSearchCriteriaDTO.getRecordPerPage());			}
			if (analysisSearchCriteriaDTO.getRecordPerPage()!=null) {	
				System.out.println("analysisSearchCriteriaDTO.getRecordPerPage()="+analysisSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setMaxResults(analysisSearchCriteriaDTO.getRecordPerPage());
			}			
			List<AutoscanResult> analysisList = (List<AutoscanResult>)jpaQry.getResultList();
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
	public Map<String, Object> searchCandlePatternScripByAdvancedCriteria(AdvancedAnalysisSearchCriteriaDTO analysisSearchCriteriaDTO) throws BusinessException {
		
		log.debug(" DAO-HEADER: searchCandlePatternScripByAdvancedCriteria");
		
		Map<String, Object> retMap = new HashMap<String, Object>();;
		
		try {
			StringBuffer query = new StringBuffer();			
			boolean whereAdded = false;			
			
			if (analysisSearchCriteriaDTO.getFromDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.patternDate >= :fromDate");	
			}
			if (analysisSearchCriteriaDTO.getToDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.patternDate <= :toDate");	
			}
			
			if (analysisSearchCriteriaDTO.getMinUpCount()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.fiveDayPattern.upCount >= :minUpCount");	
			}
			
			if (analysisSearchCriteriaDTO.getMinDownCount()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.fiveDayPattern.downCount >= :minDownCount");	
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
			
			if (analysisSearchCriteriaDTO.getScripName()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" lower(scrInternal.name) like :scripName");	
				internalScripCountQuery.append(" lower(scrInternal.name) like :scripName");	
			}
			if (analysisSearchCriteriaDTO.getBseCode()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCode like :bseCode");
				internalScripCountQuery.append(" scrInternal.bseCode like :bseCode");
			}
			if (analysisSearchCriteriaDTO.getNseCode()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.nseCode like :nseCode");
				internalScripCountQuery.append(" scrInternal.nseCode like :nseCode");	
			}			
			if (analysisSearchCriteriaDTO.getMinCmp()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCmp >= :minCmp");
				internalScripCountQuery.append(" scrInternal.bseCmp >= :minCmp");
			}
			if (analysisSearchCriteriaDTO.getMaxCmp()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCmp <= :maxCmp");
				internalScripCountQuery.append(" scrInternal.bseCmp <= :maxCmp");
			}			
			if (analysisSearchCriteriaDTO.getMinEps()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.eps >= :minEps");
				internalScripCountQuery.append(" scrInternal.eps >= :minEps");
			}
			if (analysisSearchCriteriaDTO.getMaxEps()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.eps <= :maxEps");
				internalScripCountQuery.append(" scrInternal.eps <= :maxEps");
			}			
			if (analysisSearchCriteriaDTO.getMinPe()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.pe >= :minPe");	
				internalScripCountQuery.append(" scrInternal.pe >= :minPe");
			}
			if (analysisSearchCriteriaDTO.getMaxPe()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.pe <= :maxPe");	
				internalScripCountQuery.append(" scrInternal.pe <= :maxPe");
			}
			if (analysisSearchCriteriaDTO.getAverageVolume()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.nseAverageVolume >= :averageVolume");	
				internalScripCountQuery.append(" scrInternal.nseAverageVolume >= :averageVolume");
			}	
			if (analysisSearchCriteriaDTO.getSelectedWatchlist()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.id in (select scrip.id from WatchlistItem WL where watchlist.id = :watchlistId)");	
				internalScripCountQuery.append(" scrInternal.id in (select scrip.id from WatchlistItem WL where watchlist.id = :watchlistId)");
			}
			internalScripQuery.append(")"); //Close internal sql query
			internalScripCountQuery.append(")");
			
			System.out.println("analysisSearchCriteriaDTO.getOrderBy()="+analysisSearchCriteriaDTO.getOrderBy()+" ordertype="+analysisSearchCriteriaDTO.getOrderType());
			if (analysisSearchCriteriaDTO.getOrderBy()!=null)  { 
				internalScripQuery.append(" ORDER BY " + analysisSearchCriteriaDTO.getOrderBy());
				if (analysisSearchCriteriaDTO.getOrderType()==null) internalScripQuery.append(" ASC "); 
				else internalScripQuery.append(" "+ analysisSearchCriteriaDTO.getOrderType() + " "); 
			}
			
			System.out.println("Main query="+"SELECT OBJECT(asRes) FROM CandlePatternScripMatch asRes " + query.toString() + internalScripQuery);
			System.out.println("Count query="+"SELECT count(*) FROM CandlePatternScripMatch asRes " + countQuery.toString() + internalScripCountQuery);
			Query jpaQry = this.entityManager.createQuery("SELECT OBJECT(asRes) FROM CandlePatternScripMatch asRes " + query.toString() + internalScripQuery );
			Query countQry = this.entityManager.createQuery("SELECT count(*) FROM CandlePatternScripMatch asRes " + countQuery.toString() + internalScripCountQuery);
			
			if (analysisSearchCriteriaDTO.getFromDate()!=null)  { 
				jpaQry.setParameter("fromDate", analysisSearchCriteriaDTO.getFromDate());
				countQry.setParameter("fromDate", analysisSearchCriteriaDTO.getFromDate());
			}
			if (analysisSearchCriteriaDTO.getToDate()!=null)  { 
				jpaQry.setParameter("toDate", analysisSearchCriteriaDTO.getToDate());
				countQry.setParameter("toDate", analysisSearchCriteriaDTO.getToDate());
			}
			
			if (analysisSearchCriteriaDTO.getMinUpCount()!=null)  { 
				jpaQry.setParameter("minUpCount", analysisSearchCriteriaDTO.getMinUpCount());
				countQry.setParameter("minUpCount", analysisSearchCriteriaDTO.getMinUpCount());	
			}
			
			if (analysisSearchCriteriaDTO.getMinDownCount()!=null)  { 
				jpaQry.setParameter("minDownCount", analysisSearchCriteriaDTO.getMinDownCount());
				countQry.setParameter("minDownCount", analysisSearchCriteriaDTO.getMinDownCount());	
			}
			
			if (analysisSearchCriteriaDTO.getMinDownCount()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" asRes.fiveDayPattern.downCount >= :minDownCount");	
			}
			
			
			if (analysisSearchCriteriaDTO.getScripName()!=null)  { 
				jpaQry.setParameter("scripName", "%"+analysisSearchCriteriaDTO.getScripName().toLowerCase()+"%");
				countQry.setParameter("scripName", "%"+analysisSearchCriteriaDTO.getScripName().toLowerCase()+"%");	
			}
			if (analysisSearchCriteriaDTO.getBseCode()!=null)  { 
				jpaQry.setParameter("bseCode", analysisSearchCriteriaDTO.getBseCode());
				countQry.setParameter("bseCode", analysisSearchCriteriaDTO.getBseCode());
			}
			if (analysisSearchCriteriaDTO.getNseCode()!=null)  { 
				jpaQry.setParameter("nseCode", analysisSearchCriteriaDTO.getNseCode());
				countQry.setParameter("nseCode", analysisSearchCriteriaDTO.getNseCode());
			}			
			if (analysisSearchCriteriaDTO.getMinCmp()!=null)  { 
				jpaQry.setParameter("minCmp", analysisSearchCriteriaDTO.getMinCmp());
				countQry.setParameter("minCmp", analysisSearchCriteriaDTO.getMinCmp());
			}
			if (analysisSearchCriteriaDTO.getMaxCmp()!=null)  { 
				jpaQry.setParameter("maxCmp", analysisSearchCriteriaDTO.getMaxCmp());
				countQry.setParameter("maxCmp", analysisSearchCriteriaDTO.getMaxCmp());
			}			
			if (analysisSearchCriteriaDTO.getMinEps()!=null)  { 
				jpaQry.setParameter("minEps", analysisSearchCriteriaDTO.getMinEps());
				countQry.setParameter("minEps", analysisSearchCriteriaDTO.getMinEps());
			}
			if (analysisSearchCriteriaDTO.getMaxEps()!=null)  { 
				jpaQry.setParameter("maxEps", analysisSearchCriteriaDTO.getMaxEps());
				countQry.setParameter("maxEps", analysisSearchCriteriaDTO.getMaxEps());
			}			
			if (analysisSearchCriteriaDTO.getMinPe()!=null)  { 
				jpaQry.setParameter("minPe", analysisSearchCriteriaDTO.getMinPe());
				countQry.setParameter("minPe", analysisSearchCriteriaDTO.getMinPe());
			}
			if (analysisSearchCriteriaDTO.getMaxPe()!=null)  { 
				jpaQry.setParameter("maxPe", analysisSearchCriteriaDTO.getMaxPe());
				countQry.setParameter("maxPe", analysisSearchCriteriaDTO.getMaxPe());
			}
			if (analysisSearchCriteriaDTO.getAverageVolume()!=null)  { 
				jpaQry.setParameter("averageVolume", analysisSearchCriteriaDTO.getAverageVolume());
				countQry.setParameter("averageVolume", analysisSearchCriteriaDTO.getAverageVolume());
			}			
			if (analysisSearchCriteriaDTO.getSelectedWatchlist()!=null)  { 
				jpaQry.setParameter("watchlistId", analysisSearchCriteriaDTO.getSelectedWatchlist());
				countQry.setParameter("watchlistId", analysisSearchCriteriaDTO.getSelectedWatchlist());
			}
			if (analysisSearchCriteriaDTO.getPageNumber()!=null) {
				System.out.println("First result="+(analysisSearchCriteriaDTO.getPageNumber()-1)*analysisSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setFirstResult((analysisSearchCriteriaDTO.getPageNumber()-1)*analysisSearchCriteriaDTO.getRecordPerPage());			}
			if (analysisSearchCriteriaDTO.getRecordPerPage()!=null) {	
				System.out.println("analysisSearchCriteriaDTO.getRecordPerPage()="+analysisSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setMaxResults(analysisSearchCriteriaDTO.getRecordPerPage());
			}			
			List<CandlePatternScripMatch> analysisList = (List<CandlePatternScripMatch>)jpaQry.getResultList();
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
	public Map<String, Object> searchCandlePatternByCriteria(CandlePatternSearchCriteriaDTO criteriaDto) throws BusinessException {
		
		log.debug(" DAO-HEADER: searchScripByCriteria");		
		Map<String, Object> retMap = new HashMap<String, Object>();;
		
		try {
			StringBuffer query = new StringBuffer();
			
			query.append(" WHERE candlePattern.dayNumber = :dayNumber AND candlePattern.isDayOrLevel=:isDayOrLevel");
			
			if (criteriaDto.getMinUp()!=null)  {
				query.append(" AND candlePattern.upCount >= :upCount");				
			}
			if (criteriaDto.getMinDown()!=null)  {
				query.append(" AND candlePattern.downCount >= :downCount");				
			}
			if (criteriaDto.getMinCountRank()!=null)  {
				query.append(" AND candlePattern.countRank >= :countRank");				
			}			
			
			StringBuffer countQuery = new StringBuffer(query.toString()); //Order by and type not required for count query
			
			if (criteriaDto.getOrderBy()!=null)  { 
				query.append(" ORDER BY " + criteriaDto.getOrderBy());
				if (criteriaDto.getOrderType()==null) query.append(" ASC "); 
				else query.append(" "+ criteriaDto.getOrderType() + " "); 
			}
        	
			Query jpaQry = this.entityManager.createQuery("SELECT OBJECT(candlePattern) FROM CandlePatternLibrary candlePattern  " + query.toString());
			Query countQry = this.entityManager.createQuery("SELECT count(*) FROM CandlePatternLibrary candlePattern " + countQuery.toString());
			
			jpaQry.setParameter("dayNumber", 5);
			countQry.setParameter("dayNumber", 5);
			
			jpaQry.setParameter("isDayOrLevel", Boolean.FALSE);
			countQry.setParameter("isDayOrLevel", Boolean.FALSE);
						
			if (criteriaDto.getMinUp()!=null)  {
				jpaQry.setParameter("upCount", criteriaDto.getMinUp());
				countQry.setParameter("upCount", criteriaDto.getMinUp());				
			}
			if (criteriaDto.getMinDown()!=null)  {
				jpaQry.setParameter("downCount", criteriaDto.getMinDown());
				countQry.setParameter("downCount", criteriaDto.getMinDown());
			}
			if (criteriaDto.getMinCountRank()!=null)  {
				jpaQry.setParameter("countRank", criteriaDto.getMinCountRank());
				countQry.setParameter("countRank", criteriaDto.getMinCountRank());
			}		
			
			if (criteriaDto.getPageNumber()!=null) {
				System.out.println("First result="+(criteriaDto.getPageNumber()-1)*criteriaDto.getRecordPerPage());
				jpaQry.setFirstResult((criteriaDto.getPageNumber()-1)*criteriaDto.getRecordPerPage());			}
			if (criteriaDto.getRecordPerPage()!=null) {	
				System.out.println("scripSearchCriteriaDTO.getRecordPerPage()="+criteriaDto.getRecordPerPage());
				jpaQry.setMaxResults(criteriaDto.getRecordPerPage());
			}			
			List<CandlePatternLibrary> patternList = (List<CandlePatternLibrary>)jpaQry.getResultList();
			retMap.put("Result", patternList);
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
	public List<AutoscanResult> getSummaryAutoscanResult(Long scripId, int recentDays) throws BusinessException {
		List<AutoscanResult> retList = null;
		log.debug(" DAO-HEADER: searchScripByCriteria");		
		Map<String, Object> retMap = new HashMap<String, Object>();;
		
		try {
			String dateQueryFilterSql = "select distinct dataDate from NSEEodData order by dataDate desc";
			Query dateQuery =  this.entityManager.createQuery(dateQueryFilterSql);
			dateQuery.setMaxResults(recentDays);
			List dateList = dateQuery.getResultList();
			System.out.println("dateList="+dateList);
			String query = "SELECT OBJECT(asRes) FROM AutoscanResult asRes where asRes.scrip.id=:scripId and asRes.signalDate in (:dateList) and asRes.studyMaster.retrieveOrder is not null order by signalDate desc, asRes.studyMaster.retrieveOrder";
			Query jpaQry = this.entityManager.createQuery(query);
			jpaQry.setParameter("dateList", dateList);
			jpaQry.setParameter("scripId", scripId);
			retList = jpaQry.getResultList();
			System.out.println("In getSummaryAutoscanResult retList="+retList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return retList;
	}
}

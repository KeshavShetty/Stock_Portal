
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
import org.stock.portal.domain.NewsFeedScripMap;
import org.stock.portal.domain.NewsFeedSource;
import org.stock.portal.domain.metroWL.dto.AdvancedNewsSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.NewsSearchCriteriaDTO;


/**
 * 
 * @author European Dynamics
 */
public class NewsDao {
	
	/** Logger instance for the class. */
    protected static final Logger log = Logger.getLogger(NewsDao.class.getName());
    		
	/** Session EJB EntityManager - Injected by constructor to DAO. */
	private EntityManager entityManager = null;
	
	/**
	 * 
	 * @param eManager
	 */
	public NewsDao(final EntityManager eManager) {
		this.entityManager = eManager;
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
	
	public List<NewsFeedSource> getFeedSourceList() {		
		log.debug(" DAO-HEADER: getFeedSourceList");		
		List<NewsFeedSource> retList = null;
		
		try {
			retList = (List<NewsFeedSource>)this.entityManager.createNamedQuery("News.getFeedSource").getResultList();
			System.out.println("In getFeedSourceList retList="+retList.size());
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchNews(NewsSearchCriteriaDTO newsSearchCriteriaDTO) throws BusinessException {
		
		log.debug(" DAO-HEADER: searchScripByCriteria");
		
		Map<String, Object> retMap = new HashMap<String, Object>();;
		System.out.println("newsSearchCriteriaDTO.getScripId()="+newsSearchCriteriaDTO.getScripId()+" getScripIds="+newsSearchCriteriaDTO.getScripIds());
		try {
			StringBuffer query = new StringBuffer();			
			boolean whereAdded = false;
			
			if (newsSearchCriteriaDTO.getScripId()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" nfsm.scrip.id = :scripId");				
			} else if (newsSearchCriteriaDTO.getScripIds()!=null && newsSearchCriteriaDTO.getScripIds().length()>0)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" nfsm.scrip.id in (:scripIds)");				
			}
			if (newsSearchCriteriaDTO.getFromDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" nfsm.newsFeedPost.publishDate >= :fromPublishDate");	
			}
			if (newsSearchCriteriaDTO.getToDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" nfsm.newsFeedPost.publishDate <= :toPublishDate");	
			}
			if (newsSearchCriteriaDTO.getFeedSourceId()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" nfsm.newsFeedPost.newsFeedSource.id = :feedSourceId");	
			}
			StringBuffer countQuery = new StringBuffer(query.toString()); //Order by and type not required for count query
			System.out.println("newsSearchCriteriaDTO.getOrderBy()="+newsSearchCriteriaDTO.getOrderBy()+" ordertype="+newsSearchCriteriaDTO.getOrderType());
			if (newsSearchCriteriaDTO.getOrderBy()!=null)  { 
				query.append(" ORDER BY " + newsSearchCriteriaDTO.getOrderBy());
				if (newsSearchCriteriaDTO.getOrderType()==null) query.append(" ASC "); 
				else query.append(" "+ newsSearchCriteriaDTO.getOrderType() + " "); 
			}
        	
			Query jpaQry = this.entityManager.createQuery("SELECT OBJECT(nfsm) FROM NewsFeedScripMap nfsm " + query.toString());
			Query countQry = this.entityManager.createQuery("SELECT count(*) FROM NewsFeedScripMap nfsm " + countQuery.toString());
			
			
			// SELECT OBJECT(nFeed) FROM NewsFeed nFeed
			
			
			if (newsSearchCriteriaDTO.getScripId()!=null)  {  
				jpaQry.setParameter("scripId", (newsSearchCriteriaDTO.getScripId()));
				countQry.setParameter("scripId", (newsSearchCriteriaDTO.getScripId()));
			} else if (newsSearchCriteriaDTO.getScripIds()!=null && newsSearchCriteriaDTO.getScripIds().length()>0)  { 
				jpaQry.setParameter("scripIds", getLongIdsCollection(newsSearchCriteriaDTO.getScripIds()));
				countQry.setParameter("scripIds", getLongIdsCollection(newsSearchCriteriaDTO.getScripIds()));
			}   
			if (newsSearchCriteriaDTO.getFromDate()!=null)  { 
				jpaQry.setParameter("fromPublishDate", newsSearchCriteriaDTO.getFromDate());
				countQry.setParameter("fromPublishDate", newsSearchCriteriaDTO.getFromDate());
			}
			if (newsSearchCriteriaDTO.getToDate()!=null)  { 
				jpaQry.setParameter("toPublishDate", newsSearchCriteriaDTO.getToDate());
				countQry.setParameter("toPublishDate", newsSearchCriteriaDTO.getToDate());
			}
			if (newsSearchCriteriaDTO.getFeedSourceId()!=null)  { 
				jpaQry.setParameter("feedSourceId", newsSearchCriteriaDTO.getFeedSourceId());
				countQry.setParameter("feedSourceId", newsSearchCriteriaDTO.getFeedSourceId());
			}
			if (newsSearchCriteriaDTO.getPageNumber()!=null) {
				System.out.println("First result="+(newsSearchCriteriaDTO.getPageNumber()-1)*newsSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setFirstResult((newsSearchCriteriaDTO.getPageNumber()-1)*newsSearchCriteriaDTO.getRecordPerPage());
			}
			if (newsSearchCriteriaDTO.getRecordPerPage()!=null) {	
				System.out.println("newsSearchCriteriaDTO.getRecordPerPage()="+newsSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setMaxResults(newsSearchCriteriaDTO.getRecordPerPage());
			}			
			List<NewsFeedScripMap> newsFeedList = (List<NewsFeedScripMap>)jpaQry.getResultList();
			retMap.put("Result", newsFeedList);
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
	public Map<String, Object> searchNewsByAdvancedCriteria(AdvancedNewsSearchCriteriaDTO newsSearchCriteriaDTO) throws BusinessException {
		
		log.debug(" DAO-HEADER: searchAnalysisByAdvancedCriteria");
		
		Map<String, Object> retMap = new HashMap<String, Object>();;
		
		try {
			StringBuffer query = new StringBuffer();			
			boolean whereAdded = false;			
			
			if (newsSearchCriteriaDTO.getFromDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" nfsm.newsFeedPost.publishDate >= :fromDate");	
			}
			if (newsSearchCriteriaDTO.getToDate()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" nfsm.newsFeedPost.publishDate <= :toDate");	
			}
			System.out.println("newsSearchCriteriaDTO.getSearchString()="+newsSearchCriteriaDTO.getSearchString());
			if (newsSearchCriteriaDTO.getSearchString()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" lower(nfsm.newsFeedPost.postTitle) LIKE :searchString");	
			}
			if (newsSearchCriteriaDTO.getSelectedSource()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" nfsm.newsFeedPost.newsFeedSource.id = :selectedSource");	
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
			internalScripQuery.append(" nfsm.scrip.id in (select id from Scrip scrInternal ");
			internalScripCountQuery.append(" nfsm.scrip.id in (select id from Scrip scrInternal ");
			boolean internalWhereAdded = false;	
			
			if (newsSearchCriteriaDTO.getScripName()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" lower(scrInternal.name) like :scripName");	
				internalScripCountQuery.append(" lower(scrInternal.name) like :scripName");	
			}
			if (newsSearchCriteriaDTO.getBseCode()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCode like :bseCode");
				internalScripCountQuery.append(" scrInternal.bseCode like :bseCode");
			}
			if (newsSearchCriteriaDTO.getNseCode()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.nseCode like :nseCode");
				internalScripCountQuery.append(" scrInternal.nseCode like :nseCode");	
			}			
			if (newsSearchCriteriaDTO.getMinCmp()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCmp >= :minCmp");
				internalScripCountQuery.append(" scrInternal.bseCmp >= :minCmp");
			}
			if (newsSearchCriteriaDTO.getMaxCmp()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.bseCmp <= :maxCmp");
				internalScripCountQuery.append(" scrInternal.bseCmp <= :maxCmp");
			}			
			if (newsSearchCriteriaDTO.getMinEps()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.eps >= :minEps");
				internalScripCountQuery.append(" scrInternal.eps >= :minEps");
			}
			if (newsSearchCriteriaDTO.getMaxEps()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.eps <= :maxEps");
				internalScripCountQuery.append(" scrInternal.eps <= :maxEps");
			}			
			if (newsSearchCriteriaDTO.getMinPe()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.pe >= :minPe");	
				internalScripCountQuery.append(" scrInternal.pe >= :minPe");
			}
			if (newsSearchCriteriaDTO.getMaxPe()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.pe <= :maxPe");	
				internalScripCountQuery.append(" scrInternal.pe <= :maxPe");
			}
			if (newsSearchCriteriaDTO.getAverageVolume()!=null)  { 
				if (!internalWhereAdded) { internalScripQuery.append(" WHERE "); internalScripCountQuery.append(" WHERE "); internalWhereAdded=true; } else { internalScripQuery.append(" AND "); internalScripCountQuery.append(" AND "); }
				internalScripQuery.append(" scrInternal.nseAverageVolume >= :averageVolume");	
				internalScripCountQuery.append(" scrInternal.nseAverageVolume >= :averageVolume");
			}			
			internalScripQuery.append(")"); //Close internal sql query
			internalScripCountQuery.append(")");
			
			System.out.println("newsSearchCriteriaDTO.getOrderBy()="+newsSearchCriteriaDTO.getOrderBy()+" ordertype="+newsSearchCriteriaDTO.getOrderType());
			if (newsSearchCriteriaDTO.getOrderBy()!=null)  { 
				internalScripQuery.append(" ORDER BY " + newsSearchCriteriaDTO.getOrderBy());
				if (newsSearchCriteriaDTO.getOrderType()==null) internalScripQuery.append(" ASC "); 
				else internalScripQuery.append(" "+ newsSearchCriteriaDTO.getOrderType() + " "); 
			}
			
			
			Query jpaQry = this.entityManager.createQuery("SELECT OBJECT(nfsm) FROM NewsFeedScripMap nfsm " + query.toString() + internalScripQuery );
			Query countQry = this.entityManager.createQuery("SELECT count(*) FROM NewsFeedScripMap nfsm " + countQuery.toString() + internalScripCountQuery);
			
			if (newsSearchCriteriaDTO.getFromDate()!=null)  { 
				jpaQry.setParameter("fromDate", newsSearchCriteriaDTO.getFromDate());
				countQry.setParameter("fromDate", newsSearchCriteriaDTO.getFromDate());
			}
			if (newsSearchCriteriaDTO.getToDate()!=null)  { 
				jpaQry.setParameter("toDate", newsSearchCriteriaDTO.getToDate());
				countQry.setParameter("toDate", newsSearchCriteriaDTO.getToDate());
			}
			if (newsSearchCriteriaDTO.getSearchString()!=null)  { 
				jpaQry.setParameter("searchString", "%"+newsSearchCriteriaDTO.getSearchString().toLowerCase()+"%");
				countQry.setParameter("searchString","%"+newsSearchCriteriaDTO.getSearchString().toLowerCase()+"%");
			}
			if (newsSearchCriteriaDTO.getSelectedSource()!=null)  { 
				jpaQry.setParameter("selectedSource", newsSearchCriteriaDTO.getSelectedSource());
				countQry.setParameter("selectedSource", newsSearchCriteriaDTO.getSelectedSource());
			}
			
			
			if (newsSearchCriteriaDTO.getScripName()!=null)  { 
				jpaQry.setParameter("scripName", "%"+newsSearchCriteriaDTO.getScripName().toLowerCase()+"%");
				countQry.setParameter("scripName", "%"+newsSearchCriteriaDTO.getScripName().toLowerCase()+"%");	
			}
			if (newsSearchCriteriaDTO.getBseCode()!=null)  { 
				jpaQry.setParameter("bseCode", newsSearchCriteriaDTO.getBseCode());
				countQry.setParameter("bseCode", newsSearchCriteriaDTO.getBseCode());
			}
			if (newsSearchCriteriaDTO.getNseCode()!=null)  { 
				jpaQry.setParameter("nseCode", newsSearchCriteriaDTO.getNseCode());
				countQry.setParameter("nseCode", newsSearchCriteriaDTO.getNseCode());
			}			
			if (newsSearchCriteriaDTO.getMinCmp()!=null)  { 
				jpaQry.setParameter("minCmp", newsSearchCriteriaDTO.getMinCmp());
				countQry.setParameter("minCmp", newsSearchCriteriaDTO.getMinCmp());
			}
			if (newsSearchCriteriaDTO.getMaxCmp()!=null)  { 
				jpaQry.setParameter("maxCmp", newsSearchCriteriaDTO.getMaxCmp());
				countQry.setParameter("maxCmp", newsSearchCriteriaDTO.getMaxCmp());
			}			
			if (newsSearchCriteriaDTO.getMinEps()!=null)  { 
				jpaQry.setParameter("minEps", newsSearchCriteriaDTO.getMinEps());
				countQry.setParameter("minEps", newsSearchCriteriaDTO.getMinEps());
			}
			if (newsSearchCriteriaDTO.getMaxEps()!=null)  { 
				jpaQry.setParameter("maxEps", newsSearchCriteriaDTO.getMaxEps());
				countQry.setParameter("maxEps", newsSearchCriteriaDTO.getMaxEps());
			}			
			if (newsSearchCriteriaDTO.getMinPe()!=null)  { 
				jpaQry.setParameter("minPe", newsSearchCriteriaDTO.getMinPe());
				countQry.setParameter("minPe", newsSearchCriteriaDTO.getMinPe());
			}
			if (newsSearchCriteriaDTO.getMaxPe()!=null)  { 
				jpaQry.setParameter("maxPe", newsSearchCriteriaDTO.getMaxPe());
				countQry.setParameter("maxPe", newsSearchCriteriaDTO.getMaxPe());
			}
			if (newsSearchCriteriaDTO.getAverageVolume()!=null)  { 
				jpaQry.setParameter("averageVolume", newsSearchCriteriaDTO.getAverageVolume());
				countQry.setParameter("averageVolume", newsSearchCriteriaDTO.getAverageVolume());
			}			
			
			if (newsSearchCriteriaDTO.getPageNumber()!=null) {
				System.out.println("First result="+(newsSearchCriteriaDTO.getPageNumber()-1)*newsSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setFirstResult((newsSearchCriteriaDTO.getPageNumber()-1)*newsSearchCriteriaDTO.getRecordPerPage());			}
			if (newsSearchCriteriaDTO.getRecordPerPage()!=null) {	
				System.out.println("newsSearchCriteriaDTO.getRecordPerPage()="+newsSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setMaxResults(newsSearchCriteriaDTO.getRecordPerPage());
			}			
			List<NewsFeedScripMap> newsFeedList = (List<NewsFeedScripMap>)jpaQry.getResultList();
			retMap.put("Result", newsFeedList);
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
}

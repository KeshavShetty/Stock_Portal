
package org.stock.portal.dao;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.domain.WatchlistItem;
import org.stock.portal.domain.WatchlistTransactions;


/**
 * 
 * @author European Dynamics
 */
public class WatchlistDao {
	
	/** Logger instance for the class. */
    protected static final Logger log = Logger.getLogger(WatchlistDao.class.getName());
    		
	/** Session EJB EntityManager - Injected by constructor to DAO. */
	private EntityManager entityManager = null;
	
	/**
	 * 
	 * @param eManager
	 */
	public WatchlistDao(final EntityManager eManager) {
		this.entityManager = eManager;
	}
	
	@SuppressWarnings("unchecked")
	public List<Watchlist> getWatchlistByUserId(Long userId) throws BusinessException {
		log.debug(" DAO-HEADER: getWatchlistByUserId(userId: "+ userId);
		
		List<Watchlist> retList = null;
		
		try {
			retList = (List<Watchlist>)this.entityManager.createNamedQuery("WL.getWatchlistByUserId").setParameter("userId", userId).getResultList();			
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- Watchlist : size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public List<WatchlistItem> getStockInHandByUserId(Long userId) throws BusinessException {
		log.debug(" DAO-HEADER: getWatchlistByUserId(userId: "+ userId);
		
		List<WatchlistItem> retList = null;
		
		try {
			retList = (List<WatchlistItem>)this.entityManager.createNamedQuery("WLItem.getSIHByUserId").setParameter("userId", userId).getResultList();			
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- Watchlist : size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public List<WatchlistItem> getWatchlistItemByWlIdAndUserId(Long wlId, Long userId) throws BusinessException {
		log.debug(" DAO-HEADER: getWatchlistItemByWlIdAndUserId(wlId: "+ wlId+" userId="+userId);
		
		List<WatchlistItem> retList = null;
		
		try {
			retList = (List<WatchlistItem>)this.entityManager.createNamedQuery("WLItem.getWlItemByWLIdAndUserId")
			.setParameter("userId", userId)
			.setParameter("wlId", wlId)
			.setMaxResults(10).getResultList();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- Watchlist : size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public List<WatchlistItem> getWatchlistItemByWlIdAndUserId(Long wlId, Long userId, boolean includeZeroHolidngItems) {
		System.out.println(" DAO-HEADER: getWatchlistItemByWlIdAndUserId(wlId: "+ wlId+" userId="+userId+" includeZeroHolidngItems="+includeZeroHolidngItems);
		
		List<WatchlistItem> retList = null;
		
		try {
			String jpaQry = "SELECT OBJECT(wlItems) FROM WatchlistItem wlItems where wlItems.watchlist.user.id = :userId and wlItems.watchlist.id = :wlId";
			if (includeZeroHolidngItems==false) {
				jpaQry = jpaQry + " and wlItems.stockInHand>0";
			}
			jpaQry = jpaQry + " order by wlItems.scrip.nseCode";
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("userId", userId);
			query.setParameter("wlId", wlId);
			
			retList = (List<WatchlistItem>)query.getResultList();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- In getWatchlistItemByWlIdAndUserId : Return size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public WatchlistItem getWatchlistItemByWlItemId(Long wlItemId) {
		
		WatchlistItem retItem = null;
		
		try {
			String jpaQry = "SELECT OBJECT(wlItems) FROM WatchlistItem wlItems where wlItems.id = :wlItemId";
			
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("wlItemId", wlItemId);
			
			retItem = (WatchlistItem)query.getSingleResult();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return retItem;
	}
	
	@SuppressWarnings("unchecked")
	public List<WatchlistTransactions> getWatchlistTransactionsByWlIdAndUserId(Long wlId, Long userId, String fromDate, String toDate) {
		System.out.println(" DAO-HEADER: getWatchlistTransactionsByWlIdAndUserId(wlId: "+ wlId+" userId="+userId+" fromDate="+fromDate +" toDate="+toDate);
		
		List<WatchlistTransactions> retList = null;
		
		try {			
			String jpaQry = "SELECT OBJECT(wlTransactions) FROM WatchlistTransactions wlTransactions where wlTransactions.watchlistItem.watchlist.user.id = :userId and wlTransactions.watchlistItem.watchlist.id = :wlId";
			if (fromDate!=null && fromDate.length()>0) {
				jpaQry = jpaQry + " and wlTransactions.transactionDate >=:fromDate";
			}
			if (toDate!=null && toDate.length()>0) {
				jpaQry = jpaQry + " and wlTransactions.transactionDate <=:toDate";
			}
			jpaQry = jpaQry + " order by wlTransactions.transactionDate, wlTransactions.watchlistItem.scrip.nseCode";
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("userId", userId);
			query.setParameter("wlId", wlId);
			if (fromDate!=null && fromDate.length()>0) { 
				query.setParameter("fromDate", (new SimpleDateFormat("dd/MM/yyyy")).parse(fromDate));
			}
			if (toDate!=null && toDate.length()>0) {
				query.setParameter("toDate", (new SimpleDateFormat("dd/MM/yyyy")).parse(toDate));
			}
			retList = (List<WatchlistTransactions>)query.getResultList();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- Watchlist : size = " + retList.size());
		}
		return retList;
	}
	@SuppressWarnings("unchecked")
	public List<WatchlistTransactions> getWatchlistTransactionsByItemId(Long wlItemId) {
		List<WatchlistTransactions> retList = null;
		
		try {			
			String jpaQry = "SELECT OBJECT(wlTransactions) FROM WatchlistTransactions wlTransactions where wlTransactions.watchlistItem.id = :wlItemId " +
					" order by wlTransactions.transactionDate, wlTransactions.id";
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("wlItemId", wlItemId);
			retList = (List<WatchlistTransactions>)query.getResultList();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- Watchlist : size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public WatchlistItem getWatchlistItemByWlIdAndScripId(Long watchlistId, Long scripId) {
		System.out.println(" DAO-HEADER: getWatchlistItemByWlIdAndScripId(watchlistId: "+ watchlistId+" scripId="+scripId);
		
		WatchlistItem retObject = null;
		
		try {			
			String jpaQry = "SELECT OBJECT(wlItems) FROM WatchlistItem wlItems where wlItems.scrip.id = :scripId and wlItems.watchlist.id = :wlId";
			
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("scripId", scripId);
			query.setParameter("wlId", watchlistId);
			retObject = (WatchlistItem)query.getSingleResult();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}		
		return retObject;
	}
	
	@SuppressWarnings("unchecked")
	public String getMaxSettlementNumber(Long watchListId) {
		System.out.println(" DAO-HEADER: getMaxSettlementNumber(watchlistId: "+ watchListId);
		
		String retString = null;
		
		try {			
			String jpaQry = "SELECT settlementNumber FROM WatchlistTransactions wlTrans where wlTrans.watchlistItem.watchlist.id = :watchListId" +
					" and wlTrans.id = (select max(id) from WatchlistTransactions)";
			
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("watchListId", watchListId);
			retString = (String)query.getSingleResult();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}		
		return retString;
	}
	
	@SuppressWarnings("unchecked")
	public List<WatchlistItem> getWatchlistItemsByUserIdAndScripId(Long scripId, Long userId) {
		System.out.println(" DAO-HEADER: getWatchlistItemsByUserIdAndScripId(scripId: "+ scripId+" userId="+userId);
		
		List<WatchlistItem> retList = null;
		
		try {
			String jpaQry = "SELECT OBJECT(wlItems) FROM WatchlistItem wlItems where wlItems.watchlist.user.id = :userId and wlItems.scrip.id = :scripId";
			
			jpaQry = jpaQry + " order by wlItems.watchlist.name";
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("userId", userId);
			query.setParameter("scripId", scripId);
			
			retList = (List<WatchlistItem>)query.getResultList();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- In getWatchlistItemByWlIdAndUserId : Return size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public List<WatchlistItem> getSIHByUserIdAndScripId(Long scripId, Long userId) {
		System.out.println(" DAO-HEADER: getSIHByUserIdAndScripId(scripId: "+ scripId+" userId="+userId);
		
		List<WatchlistItem> retList = null;
		
		try {
			String jpaQry = "SELECT OBJECT(wlItems) FROM WatchlistItem wlItems where wlItems.watchlist.user.id = :userId and wlItems.scrip.id = :scripId and wlItems.stockInHand>0";
			
			jpaQry = jpaQry + " order by wlItems.scrip.name";
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("userId", userId);
			query.setParameter("scripId", scripId);
			
			retList = (List<WatchlistItem>)query.getResultList();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- In getWatchlistItemByWlIdAndUserId : Return size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public Watchlist getWatchlistById(Long wlId) {
		System.out.println(" DAO-HEADER: getWatchlistById(wlId: "+ wlId +")");
		
		Watchlist retWl = null;
		
		try {
			String jpaQry = "SELECT OBJECT(wl) FROM Watchlist wl where wl.id = :wlId";
			
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("wlId", wlId);
			
			retWl = (Watchlist)query.getSingleResult();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return retWl;
	}
	
	@SuppressWarnings("unchecked")
	public List<Scrip> getScripsByWatchlistId(Long wlId, String historyDate) {
		System.out.println(" DAO-HEADER: getScripsByWatchlistId(wlId: "+ wlId +")");
		
		List<Scrip> retWlList = new ArrayList<Scrip>();
		
		try {
			Watchlist watchlist = getWatchlistById(wlId);
			String wlFetchSql = watchlist.getScripFecthSql();
			if (watchlist.getWatchlistType().equalsIgnoreCase("VIRTUAL") && historyDate!=null && historyDate.length()>0) {
				wlFetchSql = wlFetchSql.replaceAll(" from scrips_view " , " from scrips_history_view ");
				wlFetchSql = wlFetchSql.replaceAll(" from scrips " , " from scrips_history_view ");
				wlFetchSql = wlFetchSql.replaceAll("scr.id" , "scr.f_scrip");
				SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
				wlFetchSql = wlFetchSql + " AND scr.last_updated='" + dbFormat.format(dmyFormat.parse(historyDate)) + "'";
			}
			System.out.println(wlFetchSql);
			String finalSql = "select id, name from scrips where id in (" + wlFetchSql + ") order by name";
			Query q = entityManager.createNativeQuery(finalSql);
			
			List<Object[]> listResults = q.getResultList();
			System.out.println("listResults size-=" + listResults.size());
			Iterator<Object[]> iter = listResults.iterator();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				//System.out.println("rowdata="+rowdata);
				Scrip aScrip = new Scrip();
				aScrip.setId(((BigInteger)rowdata[0]).longValue());
				aScrip.setName((String)rowdata[1]);
				retWlList.add(aScrip);
			}	
		} catch (NoResultException e) {
			e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return retWlList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Watchlist> getWatchlistItemByWlIdAndScripIdForManual(Long userId, Long scripId) {
		System.out.println(" DAO-HEADER: getScripsByWatchlistId(userId: "+ userId + " scripId=" + scripId +")");
		List<Watchlist> retWlList = new ArrayList<Watchlist>();
		
		try {
			
			String jpaQry = "SELECT OBJECT(watchlist) FROM Watchlist watchlist where watchlist.user.id = :userId and watchlist.watchlistType = :wlType order by watchlist.name";
			
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("userId", userId);
			query.setParameter("wlType", "VIRTUAL");
			
			List<Watchlist> userWls = (List<Watchlist>)query.getResultList();
			
			for(int i=0;i<userWls.size();i++) {
				Watchlist aWL = userWls.get(i);
				String filterQuery = aWL.getScripFecthSql();
				String finalQuery = filterQuery + " AND scr.id=" + scripId;
				query = this.entityManager.createNativeQuery(finalQuery);
				List reList = query.getResultList();
				if (reList!=null && reList.size()>0) {
					retWlList.add(aWL);
				}
			}	
			//retWlList.add(new Watchlist(1L, "Test Wl 1"));
			//retWlList.add(new Watchlist(2L, "Test Wl 2"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return retWlList;
	}
	
	@SuppressWarnings("unchecked")
	public Watchlist getWatchlisByWlIdAndUser(Long wlId, Long userId) throws BusinessException {
		log.debug(" DAO-HEADER: getWatchlisByWlIdAndUser(wlId: "+ wlId+" userId="+userId);
		
		Watchlist retObj = null;
		
		try {
			retObj = (Watchlist)this.entityManager.createNamedQuery("WL.getWlByWLIdAndUserId")
			.setParameter("userId", userId)
			.setParameter("wlId", wlId)
			.setMaxResults(10).getSingleResult();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return retObj;
	}
	
	@SuppressWarnings("unchecked")
	public List<Watchlist> getWatchlistByUserIdAndType(Long userId, String wlType) {
		
		List<Watchlist> retWlList = new ArrayList<Watchlist>();		
		try {
			String jpaQry = "SELECT OBJECT(watchlist) FROM Watchlist watchlist where watchlist.user.id = :userId and watchlist.watchlistType = :wlType order by watchlist.name";
			
			Query query = this.entityManager.createQuery(jpaQry);
			query.setParameter("userId", userId);
			query.setParameter("wlType", wlType.toUpperCase());
			
			retWlList = (List<Watchlist>)query.getResultList();
		} catch (NoResultException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return retWlList;
	}
	
	@SuppressWarnings("unchecked")
	public String getWatchlistQuery(Long wlId) {	
		Watchlist watchlist = getWatchlistById(wlId);
		return watchlist.getScripFecthSql();
	}
	
	@SuppressWarnings("unchecked")
	public Boolean matchCriteria(Long scripId, String aCriteria) {	

		String finalSql = "select scr.id from scrips scr where " + aCriteria + " scr.id=" + scripId;
		Query q = entityManager.createNativeQuery(finalSql);
		
		List<Object[]> listResults = q.getResultList();
		if (listResults.size()>0) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getActualValueForMatchCriteria(Long scripId, String aCriteria) {
		String fieldname = "";
		DecimalFormat df = new DecimalFormat("###.##");
		if (aCriteria.indexOf(">=")>=0) {
			fieldname = aCriteria.substring(0, aCriteria.indexOf(">="));
		} else if (aCriteria.indexOf("<=")>=0) {
			fieldname = aCriteria.substring(0, aCriteria.indexOf("<="));			
		} else if (aCriteria.indexOf(">")>=0) {
			fieldname = aCriteria.substring(0, aCriteria.indexOf(">"));
		} else if (aCriteria.indexOf("<")>=0) {
			fieldname = aCriteria.substring(0, aCriteria.indexOf("<"));
		} else if (aCriteria.indexOf("=")>=0) {
			fieldname = aCriteria.substring(0, aCriteria.indexOf("="));
		} else if (aCriteria.indexOf("!=")>=0) {
			fieldname = aCriteria.substring(0, aCriteria.indexOf("!="));
		} else if (aCriteria.indexOf("like")>=0) {
			fieldname = aCriteria.substring(0, aCriteria.indexOf("like"));
		}
		String retValue = "";
		try {
			if (fieldname!=null && fieldname.length()>0) {
				String finalSql = "select " + fieldname + " as fieldValue from scrips scr where scr.id=" + scripId;
				Query countQry = entityManager.createNativeQuery(finalSql);
				
				Object rsObj = countQry.getSingleResult();
				System.out.println("rsObj instanceof Float=" + (rsObj instanceof Float));
				if ( rsObj instanceof Float || rsObj instanceof Double) {
					retValue = df.format(rsObj);
				} else {
					retValue = rsObj.toString();
				}
				
				System.out.println("for [" + fieldname + "] rsObj type=" + rsObj.getClass());
				
				
			} else retValue = "Missing evaluation condition";
		} catch(Exception ex) {
			System.out.println("Failed to evaluate-"+aCriteria);
			retValue = "Cannot evaluate";
		}
		return retValue;
	}
	
}

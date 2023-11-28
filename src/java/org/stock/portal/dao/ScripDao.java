
package org.stock.portal.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.EodData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.dto.ScripBubbleGraphDTO;
import org.stock.portal.domain.dto.ScripCompanyInfoDTO;
import org.stock.portal.domain.dto.ScripCompareDTO;
import org.stock.portal.domain.dto.ScripLevelPriceVsRatioDTO;
import org.stock.portal.domain.dto.ScripSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.BubblegraphCriteriaDTO;


/**
 * 
 * @author European Dynamics
 */
public class ScripDao {
	
	/** Logger instance for the class. */
    protected static final Logger log = Logger.getLogger(ScripDao.class.getName());
    		
	/** Session EJB EntityManager - Injected by constructor to DAO. */
	private EntityManager entityManager = null;
	
	/**
	 * 
	 * @param eManager
	 */
	public ScripDao(final EntityManager eManager) {
		this.entityManager = eManager;
	}
	
	
	
	public Long getScripByBseCode(String bseScripCode)
		throws BusinessException {
		
		//log.debug(" DAO-HEADER: getScripByBseCode(String bseScripCode: " + bseScripCode);		
		Long scripId = null;
		try {
			BigInteger tmpScripId = (BigInteger)this.entityManager.createNativeQuery("select id from scrips where bse_code like '"+bseScripCode+"'").getSingleResult();
			if (tmpScripId!=null) scripId = tmpScripId.longValue();
			log.info("bseScripCode scripId="+scripId);
		} catch (NoResultException e) {
			log.debug(" -- HERE : No scrip found with these code ");
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		return scripId;
	}
	
	public Long getScripByBseCodeOrNseCode(String exchangeCode)
			throws BusinessException {
			
			//log.debug(" DAO-HEADER: getScripByBseCode(String bseScripCode: " + bseScripCode);		
			Long scripId = null;
			try {
				BigInteger tmpScripId = (BigInteger)this.entityManager.createNativeQuery("select id from scrips where bse_code like '"+exchangeCode+"' or nse_code like '" +exchangeCode+"' ").getSingleResult();
				if (tmpScripId!=null) scripId = tmpScripId.longValue();
				log.info("bseScripCode scripId="+scripId);
			} catch (NoResultException e) {
				log.debug(" -- HERE : No scrip found with these code ");
				log.error(e);
				//e.printStackTrace();
			} catch (Exception e) {
				//e.printStackTrace();
				log.error(e);
			}
			return scripId;
		}
	
	public Long getScripByBseCodeOrName(String scripCode)
	throws BusinessException {
	
	//log.debug(" DAO-HEADER: getScripByBseCode(String bseScripCode: " + bseScripCode);		
	Long scripId = null;
	try {
		BigInteger tmpScripId = (BigInteger)this.entityManager.createNativeQuery("select id from scrips where bse_code like '"+scripCode+"' or bse_name like '" +scripCode+"'" ).getSingleResult();
		if (tmpScripId!=null) scripId = tmpScripId.longValue();
		log.info("bseScripCode scripId="+scripId);
	} catch (NoResultException e) {
		log.debug(" -- HERE : No scrip found with these code ");
		log.error(e);
		//e.printStackTrace();
	} catch (Exception e) {
		//e.printStackTrace();
		log.error(e);
	}
	return scripId;
}
	
	public Long getScripByNseCodeOrSeries(String nseCode, String seriesType)	throws BusinessException {	
		System.out.println(" DAO-HEADER: getScripByBseCode(String nseCode: " + nseCode+" seriesType="+seriesType);		
		Long scripId = null;
		try {
			System.out.println(" 0. tmpScripId=");
			BigInteger tmpScripId = (BigInteger)this.entityManager.createNativeQuery("select id from scrips where nse_code like '"+nseCode+"' and series_type in ('EQ','BE')").getSingleResult();
			System.out.println(" 1. tmpScripId="+tmpScripId);
			if (tmpScripId!=null) scripId = tmpScripId.longValue();
			if (scripId==null) {
				if (seriesType.equals("EQ") || seriesType.equals("BE")) {
					tmpScripId = (BigInteger)this.entityManager.createNativeQuery("select id from scrips where nse_code like '"+nseCode+"' and series_type in ('EQ','BE')").getSingleResult();;
					System.out.println(" 2. tmpScripId="+tmpScripId);
					if (tmpScripId!=null) scripId = tmpScripId.longValue();
				}
			}
			if (scripId==null) {
				tmpScripId = (BigInteger)this.entityManager.createNativeQuery("select id from scrips where nse_code like '"+nseCode+"'").getSingleResult();;
				System.out.println(" 3. tmpScripId="+tmpScripId);
				if (tmpScripId!=null) scripId = tmpScripId.longValue();
			}
		} catch (NoResultException e) {
			System.out.println(" -1.1 tmpScripId=");
			log.debug(" -- HERE : No scrip found with these code ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(" -1. tmpScripId=");
			//e.printStackTrace();
			log.error(e);
		}
		return scripId;
	}
	
	public Long getScripByNseCode(String nseCode)	throws BusinessException {	
		//log.debug(" DAO-HEADER: getScripByBseCode(String bseScripCode: " + bseScripCode);		
		Long scripId = null;
		try {
			String refinedNseCode = nseCode.replaceAll("_", " ");
			BigInteger tmpScripId = (BigInteger)this.entityManager.createNativeQuery("select id from scrips where UPPER(nse_code) like '"+refinedNseCode.toUpperCase()+"'").getSingleResult();;
			if (tmpScripId!=null) scripId = tmpScripId.longValue();
		} catch (NoResultException e) {
			log.debug(" -- HERE : No scrip found with these code ");
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		return scripId;
	}
		
	public List<Scrip> getScripsBySearchString(String scripType, String searchString) throws BusinessException {
		// Nedd to consider scrip type in future i.e Index, Future, Option etc
		log.debug(" DAO-HEADER: getScripsBySearchString(String scripType: "+ scripType+" searchString="+searchString);
		
		List<Scrip> retList = null;
		
		try {
			retList = (List<Scrip>)this.entityManager.createNamedQuery("Scrip.getScripsBySearchString")
					.setParameter("bseCode", searchString+"%")
					.setParameter("nseCode", searchString.toUpperCase()+"%")
					.setParameter("bseName", searchString.toUpperCase()+"%")
					.setParameter("scripName", "%"+searchString.toUpperCase()+"%").getResultList();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- scrip : size = " + retList.size());
		}
		return retList;
	}
	
	
	public List<String> getScripsBySearchStringExtendedSymbol(List<Scrip> scripList) throws BusinessException {
		// Nedd to consider scrip type in future i.e Index, Future, Option etc
		log.debug(" DAO-HEADER: getScripsBySearchString(String scripType: "+ scripList.size());
		
		List<String> scrList = new ArrayList<String>();
		
		for(int i=0;i<scripList.size();i++) {
        	Scrip aScrip = (Scrip)scripList.get(i);
        	String searchParam = "";
        	if (aScrip.getNseCode()!=null) {
        		if (searchParam.length()>0) searchParam = searchParam + ",";
        		searchParam = searchParam + "'NSE-" + aScrip.getNseCode() + "%'";
        	} else if (aScrip.getBseCode()!=null) {
        		if (searchParam.length()>0) searchParam = searchParam + ",";
        		searchParam = searchParam + "'BSE-" + aScrip.getBseCode() + "%'";
        	}
        	if (searchParam!=null) {
        		String sqlStr = "select tr.symbol, tr.f_user from javachart_trendlines tr where tr.symbol like " + searchParam ;
        		System.out.println("sqlStr="+sqlStr);
        			
        		Query q = entityManager.createNativeQuery(sqlStr);
        		List<Object[]> listResults = q.getResultList();
        		Iterator<Object[]> iter = listResults.iterator();
        		
        		
        		while (iter.hasNext()) {
        			Object[] rowdata = iter.next(); // Long id, String exchangeCode, String name, float pe, float pb, float qtrRevenue, float qtrProfit, float qtrProfitMargin
        			String symbolCode = (String)rowdata[0];
        			
        			scrList.add(symbolCode+" ("+symbolCode+")");
        		}
        	}
		}
		
		System.out.println("In dao getScripsBySearchStringExtendedSymbol scrList="+scrList.size());
		return scrList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Scrip> searchScripByCriteria(ScripSearchCriteriaDTO scripSearchCriteriaDTO) throws BusinessException {
		
		log.debug(" DAO-HEADER: searchScripByCriteria");
		
		List<Scrip> retList = null;
		
		try {
			StringBuffer query = new StringBuffer("SELECT OBJECT(scr) FROM Scrip scr ");
			boolean whereAdded = false;
			
			if (scripSearchCriteriaDTO.getName()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getName())) query.append(" scr.name is null "); else query.append(" scr.name like :scripName");				
			}
			if (scripSearchCriteriaDTO.getIsinCode()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getIsinCode())) query.append(" scr.isinCode is null "); else query.append(" scr.isinCode like :isinCode");				
			}        	
			if (scripSearchCriteriaDTO.getScripType()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getScripType())) query.append("  is null "); else query.append(" scr.scripType like :scripType");				
			}			
			if (scripSearchCriteriaDTO.getBseCode()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getBseCode())) query.append(" scr.bseCode is null "); else query.append(" scr.bseCode like :bseCode");				
			}
			
			if (scripSearchCriteriaDTO.getBseName()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getBseName())) query.append(" scr.bseName is null "); else query.append(" scr.bseName like :bseName");				
			}
			if (scripSearchCriteriaDTO.getBseGroup()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getBseGroup())) query.append(" scr.bseGroup  is null "); else query.append(" scr.bseGroup like :bseGroup");				
			}
			if (scripSearchCriteriaDTO.getBseIndex()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getBseIndex())) query.append(" scr.bseIndex is null "); else query.append(" scr.bseIndex like :bseIndex");				
			}
			if (scripSearchCriteriaDTO.getNseCode()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getNseCode())) query.append(" scr.nseCode is null "); else query.append(" scr.nseCode like :nseCode");				
			}
			if (scripSearchCriteriaDTO.getSeriesType()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getSeriesType())) query.append(" scr.seriesType is null "); else query.append(" scr.seriesType like :seriesType");				
			}
			if (scripSearchCriteriaDTO.getIciciCode()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getIciciCode())) query.append(" scr.iciciCode is null "); else query.append(" scr.iciciCode like :iciciCode");				
			}
			if (scripSearchCriteriaDTO.getMcCode()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getMcCode())) query.append(" scr.mcCode is null "); else query.append(" scr.mcCode like :mcCode");				
			}
			if (scripSearchCriteriaDTO.getEtCode()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getEtCode())) query.append(" scr.etCode is null "); else query.append(" scr.etCode like :etCode");				
			}
			if (scripSearchCriteriaDTO.getStatus()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getStatus())) query.append(" scr.status is null "); else query.append(" scr.status like :status");				
			}
			if (scripSearchCriteriaDTO.getDateAdded()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getDateAdded())) query.append(" scr.dateAdded is null "); else query.append(" scr.dateAdded = :dateAdded");				
			}
			if (scripSearchCriteriaDTO.getSectorName()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; }  else { query.append(" AND "); }
				if ("null".equals(scripSearchCriteriaDTO.getSectorName())) query.append(" scr.sector is null "); else query.append(" scr.sector.name like :sectorName");				
			}
			if (scripSearchCriteriaDTO.getOrderBy()!=null)  { 
				query.append(" ORDER BY " + scripSearchCriteriaDTO.getOrderBy());
				if (scripSearchCriteriaDTO.getOrderType()==null) query.append(" ASC "); 
				else query.append(" "+ scripSearchCriteriaDTO.getOrderType() + " "); 
			}
        	
			Query jpaQry = this.entityManager.createQuery(query.toString());
			
			if (scripSearchCriteriaDTO.getName()!=null && !"null".equals(scripSearchCriteriaDTO.getName())) {
				jpaQry.setParameter("scripName", scripSearchCriteriaDTO.getName()+"%");				
			}
			if (scripSearchCriteriaDTO.getIsinCode()!=null && !"null".equals(scripSearchCriteriaDTO.getIsinCode())) {
				jpaQry.setParameter("isinCode", scripSearchCriteriaDTO.getIsinCode()+"%");
			}        	
			if (scripSearchCriteriaDTO.getScripType()!=null && !"null".equals(scripSearchCriteriaDTO.getScripType())) {
				jpaQry.setParameter("scripType", scripSearchCriteriaDTO.getScripType()+"%");		
			}			
			if (scripSearchCriteriaDTO.getBseCode()!=null && !"null".equals(scripSearchCriteriaDTO.getBseCode())) {
				jpaQry.setParameter("bseCode", scripSearchCriteriaDTO.getBseCode()+"%");
			}			
			if (scripSearchCriteriaDTO.getBseName()!=null && !"null".equals(scripSearchCriteriaDTO.getBseName())) {
				jpaQry.setParameter("bseName", scripSearchCriteriaDTO.getBseName()+"%");
			}
			if (scripSearchCriteriaDTO.getBseGroup()!=null && !"null".equals(scripSearchCriteriaDTO.getBseGroup())) {
				jpaQry.setParameter("bseGroup", scripSearchCriteriaDTO.getBseGroup()+"%");
			}
			if (scripSearchCriteriaDTO.getBseIndex()!=null && !"null".equals(scripSearchCriteriaDTO.getBseIndex())) {
				jpaQry.setParameter("bseIndex", scripSearchCriteriaDTO.getBseIndex()+"%");
			}
			if (scripSearchCriteriaDTO.getNseCode()!=null && !"null".equals(scripSearchCriteriaDTO.getNseCode())) {
				jpaQry.setParameter("nseCode", scripSearchCriteriaDTO.getNseCode()+"%");
			}
			if (scripSearchCriteriaDTO.getSeriesType()!=null && !"null".equals(scripSearchCriteriaDTO.getSeriesType())) {
				jpaQry.setParameter("seriesType", scripSearchCriteriaDTO.getSeriesType()+"%");
			}
			if (scripSearchCriteriaDTO.getIciciCode()!=null && !"null".equals(scripSearchCriteriaDTO.getIciciCode())) {
				jpaQry.setParameter("iciciCode", scripSearchCriteriaDTO.getIciciCode()+"%");
			}
			if (scripSearchCriteriaDTO.getMcCode()!=null && !"null".equals(scripSearchCriteriaDTO.getMcCode())) {
				jpaQry.setParameter("mcCode", scripSearchCriteriaDTO.getMcCode()+"%");		
			}
			if (scripSearchCriteriaDTO.getEtCode()!=null && !"null".equals(scripSearchCriteriaDTO.getEtCode())) {
				
			}
			if (scripSearchCriteriaDTO.getStatus()!=null && !"null".equals(scripSearchCriteriaDTO.getStatus())) {
				jpaQry.setParameter("status", scripSearchCriteriaDTO.getStatus()+"%");
			}
			if (scripSearchCriteriaDTO.getDateAdded()!=null && !"null".equals(scripSearchCriteriaDTO.getDateAdded())) {
				jpaQry.setParameter("dateAdded", scripSearchCriteriaDTO.getDateAdded()+"%");
			}
			if (scripSearchCriteriaDTO.getSectorName()!=null && !"null".equals(scripSearchCriteriaDTO.getSectorName())) {
				jpaQry.setParameter("sectorName", scripSearchCriteriaDTO.getSectorName()+"%");
			}
//			if (scripSearchCriteriaDTO.getOrderBy()!=null)  { 
//				jpaQry.setParameter("orderBy", scripSearchCriteriaDTO.getOrderBy());
//			}
			//jpaQry.setMaxResults(10);
			//jpaQry.setFirstResult(21);
			retList = (List<Scrip>)jpaQry.getResultList();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- scrip : size = " + retList.size());
		}
		return retList;
	}
	
	public List<Scrip> getBseScripByAverageVolume(long volume) throws BusinessException {
		log.debug(" DAO-HEADER: getBseScripByAverageVolume(volume: "+ volume);
		
		List<Scrip> retList = null;
		
		try {
			retList = (List<Scrip>)this.entityManager.createQuery("select OBJECT(scr) FROM Scrip scr where scr.averageVolume > :avgVoume and scr.bseName is not null and scr.scripType like :scrType order by scr.id")
					.setParameter("avgVoume", volume)
					.setParameter("scrType", "EQ")
					.getResultList();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- scrip : size = " + retList.size());
		}
		return retList;
	}
	
	public List<Scrip> getBseScripByAverageVolumeDummy(long volume) throws BusinessException {
		log.debug(" DAO-HEADER: getBseScripByAverageVolume(volume: "+ volume);
		
		List<Scrip> retList = null;
		
		try {
			retList = (List<Scrip>)this.entityManager.createQuery("select OBJECT(scr) FROM Scrip scr where scr.averageVolume > :avgVoume and scr.bseName is not null and scr.scripType like :scrType and scr.bseCode in ('532181')")
					.setParameter("avgVoume", volume)
					.setParameter("scrType", "EQ")
					.getResultList();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		if (retList != null) {
			log.debug(" -- scrip : size = " + retList.size());
		}
		return retList;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchScrip(org.stock.portal.domain.metroWL.dto.ScripSearchCriteriaDTO scripSearchCriteriaDTO) throws BusinessException {
		
		log.debug(" DAO-HEADER: searchScripByCriteria");
		
		Map<String, Object> retMap = new HashMap<String, Object>();;
		
		try {
			StringBuffer query = new StringBuffer();			
			boolean whereAdded = false;
			
			if (scripSearchCriteriaDTO.getScripName()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" lower(scr.name) like :scripName");				
			}
			if (scripSearchCriteriaDTO.getBseCode()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.bseCode like :bseCode");				
			}
			if (scripSearchCriteriaDTO.getNseCode()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.nseCode like :nseCode");				
			}
			if (scripSearchCriteriaDTO.getSectorId()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.sector.id = :sectorId");				
			}
			if (scripSearchCriteriaDTO.getScripStatus()!=null && scripSearchCriteriaDTO.getScripStatus().length()>0)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.status = :scripStatus");				
			}
			if (scripSearchCriteriaDTO.getMinCmp()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.bseCmp >= :minCmp");	
			}
			if (scripSearchCriteriaDTO.getMaxCmp()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.bseCmp <= :maxCmp");	
			}
			
			if (scripSearchCriteriaDTO.getMinEps()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.eps >= :minEps");	
			}
			if (scripSearchCriteriaDTO.getMaxEps()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.eps <= :maxEps");	
			}
			
			if (scripSearchCriteriaDTO.getMinPe()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.pe >= :minPe");	
			}
			if (scripSearchCriteriaDTO.getMaxPe()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.pe <= :maxPe");	
			}			
			if (scripSearchCriteriaDTO.getAverageVolume()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.averageVolume >= :averageVolume");	
			}
			if (scripSearchCriteriaDTO.getAverageTurnover()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.averageTurnover >= :averageTurnover");	
			}
			if (scripSearchCriteriaDTO.getSelectedWatchlist()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.id in (select scrip.id from WatchlistItem WL where watchlist.id = :watchlistId)");
			}
			if (scripSearchCriteriaDTO.getOrderBy()!=null && scripSearchCriteriaDTO.getOrderBy().contains("/bookValue"))  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(" scr.bookValue > 0 ");	
			}			
			if (scripSearchCriteriaDTO.getOrderBy()!=null)  { 
				if (!whereAdded) { query.append(" WHERE "); whereAdded=true; } else { query.append(" AND "); }
				query.append(scripSearchCriteriaDTO.getOrderBy() + " is not null ");	
			}
			
			StringBuffer countQuery = new StringBuffer(query.toString()); //Order by and type not required for count query
			System.out.println("scripSearchCriteriaDTO.getOrderBy()="+scripSearchCriteriaDTO.getOrderBy()+" ordertype="+scripSearchCriteriaDTO.getOrderType());
			if (scripSearchCriteriaDTO.getOrderBy()!=null)  { 
				query.append(" ORDER BY " + scripSearchCriteriaDTO.getOrderBy());
				if (scripSearchCriteriaDTO.getOrderType()==null) query.append(" ASC "); 
				else query.append(" "+ scripSearchCriteriaDTO.getOrderType() + " "); 
			}
        	
			Query jpaQry = this.entityManager.createQuery("SELECT OBJECT(scr) FROM Scrip scr " + query.toString());
			Query countQry = this.entityManager.createQuery("SELECT count(*) FROM Scrip scr " + countQuery.toString());
			
			if (scripSearchCriteriaDTO.getScripName()!=null)  { 
				jpaQry.setParameter("scripName", (scripSearchCriteriaDTO.getScripName().toLowerCase())+"%");
				countQry.setParameter("scripName", (scripSearchCriteriaDTO.getScripName().toLowerCase())+"%");
			}
			if (scripSearchCriteriaDTO.getBseCode()!=null)  {
				jpaQry.setParameter("bseCode", scripSearchCriteriaDTO.getBseCode()+"%");
				countQry.setParameter("bseCode", scripSearchCriteriaDTO.getBseCode()+"%");
			}        	
			if (scripSearchCriteriaDTO.getNseCode()!=null)  {				
				jpaQry.setParameter("nseCode", (scripSearchCriteriaDTO.getNseCode().toUpperCase())+"%");	
				countQry.setParameter("nseCode", (scripSearchCriteriaDTO.getNseCode().toUpperCase())+"%");
			}			
			if (scripSearchCriteriaDTO.getSectorId()!=null)  { 
				jpaQry.setParameter("sectorId", scripSearchCriteriaDTO.getSectorId());
				countQry.setParameter("sectorId", scripSearchCriteriaDTO.getSectorId());
			}
			if (scripSearchCriteriaDTO.getScripStatus()!=null && scripSearchCriteriaDTO.getScripStatus().length()>0)  { 
				jpaQry.setParameter("scripStatus", scripSearchCriteriaDTO.getScripStatus());
				countQry.setParameter("scripStatus", scripSearchCriteriaDTO.getScripStatus());
			}
			
			if (scripSearchCriteriaDTO.getMinCmp()!=null)  { 
				jpaQry.setParameter("minCmp", scripSearchCriteriaDTO.getMinCmp());
				countQry.setParameter("minCmp", scripSearchCriteriaDTO.getMinCmp());	
			}
			if (scripSearchCriteriaDTO.getMaxCmp()!=null)  { 
				jpaQry.setParameter("maxCmp", scripSearchCriteriaDTO.getMaxCmp());
				countQry.setParameter("maxCmp", scripSearchCriteriaDTO.getMaxCmp());
			}
			
			if (scripSearchCriteriaDTO.getMinEps()!=null)  { 
				jpaQry.setParameter("minEps", scripSearchCriteriaDTO.getMinEps());
				countQry.setParameter("minEps", scripSearchCriteriaDTO.getMinEps());
			}
			if (scripSearchCriteriaDTO.getMaxEps()!=null)  { 
				jpaQry.setParameter("maxEps", scripSearchCriteriaDTO.getMaxEps());
				countQry.setParameter("maxEps", scripSearchCriteriaDTO.getMaxEps());	
			}
			
			if (scripSearchCriteriaDTO.getMinPe()!=null)  { 
				jpaQry.setParameter("minPe", scripSearchCriteriaDTO.getMinPe());
				countQry.setParameter("minPe", scripSearchCriteriaDTO.getMinPe());
			}
			if (scripSearchCriteriaDTO.getMaxPe()!=null)  { 
				jpaQry.setParameter("maxPe", scripSearchCriteriaDTO.getMaxPe());
				countQry.setParameter("maxPe", scripSearchCriteriaDTO.getMaxPe());	
			}
			if (scripSearchCriteriaDTO.getAverageVolume()!=null)  { 
				jpaQry.setParameter("averageVolume", scripSearchCriteriaDTO.getAverageVolume());
				countQry.setParameter("averageVolume", scripSearchCriteriaDTO.getAverageVolume());
			}
			if (scripSearchCriteriaDTO.getAverageTurnover()!=null)  { 
				jpaQry.setParameter("averageTurnover", scripSearchCriteriaDTO.getAverageTurnover());
				countQry.setParameter("averageTurnover", scripSearchCriteriaDTO.getAverageTurnover());
			}
			if (scripSearchCriteriaDTO.getSelectedWatchlist()!=null)  { 
				jpaQry.setParameter("watchlistId", scripSearchCriteriaDTO.getSelectedWatchlist());
				countQry.setParameter("watchlistId", scripSearchCriteriaDTO.getSelectedWatchlist());
			}
			
			if (scripSearchCriteriaDTO.getPageNumber()!=null) {
				System.out.println("First result="+(scripSearchCriteriaDTO.getPageNumber()-1)*scripSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setFirstResult((scripSearchCriteriaDTO.getPageNumber()-1)*scripSearchCriteriaDTO.getRecordPerPage());			}
			if (scripSearchCriteriaDTO.getRecordPerPage()!=null) {	
				System.out.println("scripSearchCriteriaDTO.getRecordPerPage()="+scripSearchCriteriaDTO.getRecordPerPage());
				jpaQry.setMaxResults(scripSearchCriteriaDTO.getRecordPerPage());
			}			
			List<Scrip> scripList = (List<Scrip>)jpaQry.getResultList();
			retMap.put("Result", scripList);
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
	
	public Scrip getScripById(Long scripId) throws BusinessException {
		Scrip returnScrip = null;
		log.debug(" ScripDAO getScripById() scripId="+scripId);		
		
		try {
			returnScrip = (Scrip)this.entityManager.createNamedQuery("Scrip.getScripsById").setParameter("scripId", scripId).getSingleResult();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnScrip;
	}
	
	public List<EodData> getIndexScripByScripId(Long scripId) throws BusinessException {
		List<EodData> returnScrip = new ArrayList<EodData>();
		log.debug(" ScripDAO getScripById() scripId="+scripId);		
		
		try {
			String outerHqlQry = "SELECT OBJECT(scr) FROM Scrip scr where scr.id in (select indexScrip.id from IndexScrips idxScr where idxScr.scrip.id =:scripId)";
			
			Query jpaQry = this.entityManager.createQuery(outerHqlQry);
			jpaQry.setParameter("scripId", scripId);
			List<Scrip> scripList = (List<Scrip>)jpaQry.getResultList();
			if (scripList!=null) {
				for(int i=0;i<scripList.size();i++) {
					Scrip aScrip = scripList.get(i);
					
					if (aScrip.getBseCode()!=null) {
						String hqlQry = "SELECT OBJECT(asRes) FROM BSEEodData asRes WHERE asRes.dataDate=(select max(dataDate) from BSEEodData where stochasticValue is not null) and asRes.scrip.id = :internalScripId" ;
						Query internalQry = this.entityManager.createQuery(hqlQry);
						internalQry.setParameter("internalScripId", aScrip.getId());					
						returnScrip.addAll((List<EodData>)internalQry.getResultList());
					}
					if (aScrip.getNseCode()!=null) {
						String hqlQry = "SELECT OBJECT(asRes) FROM NSEEodData asRes WHERE asRes.dataDate=(select max(dataDate) from NSEEodData where stochasticValue is not null) and asRes.scrip.id = :internalScripId" ;
						Query internalQry = this.entityManager.createQuery(hqlQry);
						internalQry.setParameter("internalScripId", aScrip.getId());					
						returnScrip.addAll((List<EodData>)internalQry.getResultList());
					}
				}
				System.out.println("In getIndexScripByScripId returnScrip size="+returnScrip.size()+" Of size scripList="+scripList.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnScrip;
	}
	
	public Scrip getScripByNSECode(String nseCode) throws BusinessException {
		Scrip returnScrip = null;
		log.debug(" ScripDAO getScripByNSECode() nseCode="+nseCode);		
		
		try {
			Long scripId = null;
			if (nseCode.contains(" ")) scripId = getScripByNseCode(nseCode); //Mostly IndexScrip
			else scripId = getScripByNseCodeOrSeries(nseCode,"EQ");
			if (scripId!=null) returnScrip = (Scrip)this.entityManager.createNamedQuery("Scrip.getScripsById").setParameter("scripId", scripId).getSingleResult();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnScrip;
	}
	
	public List<Object> getAllScripForNSE(String excludeNseCode)	throws BusinessException {	
		//log.debug(" DAO-HEADER: getScripByBseCode(String bseScripCode: " + bseScripCode);		
		List<Object> retList = null;
		try {
			String sqlQry = "select id from scrips where id>0 and nse_code is not null";
			if (excludeNseCode!=null && excludeNseCode.trim().length()>0) {
				sqlQry = sqlQry + " and nse_code not like '" + excludeNseCode +"'";
			}
			sqlQry = sqlQry + " and (series_type='EQ' or symbol_type='IN')" ;
			sqlQry = sqlQry + " order by id";
			retList = this.entityManager.createNativeQuery(sqlQry).getResultList();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No scrip found with these code ");
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		return retList;
	}
	
	public List<ScripBubbleGraphDTO> searchScripForBubbleGraphByCriteria(BubblegraphCriteriaDTO criteriaDTO) throws BusinessException {

		
		log.debug(" DAO-HEADER: searchScripForBubbleGraphByCriteria");		
		List<ScripBubbleGraphDTO> returnList = new ArrayList<ScripBubbleGraphDTO>();
		
		try {
			StringBuffer query = new StringBuffer();
			String watchlistQuery = null;
			
			String firstPart = "select scr.id as id, "
					+ " scr.name as name, "
					+ " scr.bse_code as bseCode, "
					+ " scr.nse_code as nseCode, "
					+ " scr.growth_rank as growthrank, "
					+ " scr.wl_score_rank as wlrank, "
					+ " scr.wl_roi_score as wlroiscore, "
					+ " scr.last_updated as datadate, "
					+ " scr.wlcount as wlcount, "
					
					+ " scr.revenuesameqtrchangepercent as sqgRevenue, "
					+ " scr.last_same_qtr_growth_percentage as sqgProfit, "
					+ " scr.profitmarginsameqtrchangepercent as sqgProfitMargin, "
					+ " scr.profit_margin_lastqtr as actualProfitMargin"
					

					+ " from scrips_history_view scr"
					+ " where scr.growth_rank is not null "
					+ " and scr.wl_score_rank is not null "
					+ " and scr.wl_roi_score is not null ";
			
			if (criteriaDTO.getBseCode()!=null) {
				firstPart = firstPart + " and scr.bse_code=?";
			}
			if (criteriaDTO.getNseCode()!=null) {
				firstPart = firstPart + " and scr.nse_code=?";
			}
			if (criteriaDTO.getFromDate()!=null) {
				firstPart = firstPart + " and scr.last_updated>=?";
			}
			if (criteriaDTO.getToDate()!=null) {
				firstPart = firstPart + " and scr.last_updated<=?";
			}
			if (criteriaDTO.getSelectedSector()!=null) {
				firstPart = firstPart + " and scr.f_sector=?";
			}
			if (criteriaDTO.getSelectedWatchlist()!=null)  {
				watchlistQuery = (new WatchlistDao(entityManager)).getWatchlistById(criteriaDTO.getSelectedWatchlist()).getScripFecthSql();
				firstPart = firstPart + " and scr.id in (" + watchlistQuery + ")";	
			}
			
			Query q = entityManager.createNativeQuery(firstPart);
			
			int cursorPos = 1;
			
			if (criteriaDTO.getBseCode()!=null) {
				q.setParameter( cursorPos, criteriaDTO.getBseCode());
				cursorPos++;
			}
			if (criteriaDTO.getNseCode()!=null) {
				q.setParameter( cursorPos, criteriaDTO.getNseCode());
				cursorPos++;
			}
			if (criteriaDTO.getFromDate()!=null) {
				q.setParameter( cursorPos, criteriaDTO.getFromDate());
				cursorPos++;
			}
			if (criteriaDTO.getToDate()!=null) {
				q.setParameter( cursorPos, criteriaDTO.getToDate());
				cursorPos++;
			}
			if (criteriaDTO.getSelectedSector()!=null) {
				q.setParameter( cursorPos, criteriaDTO.getSelectedSector());
				cursorPos++;
			}
//			if (criteriaDTO.getSelectedWatchlist()!=null)  {
//				q.setParameter( cursorPos, criteriaDTO.getSelectedWatchlist());
//				cursorPos++;	
//			}
			
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			returnList = new ArrayList<ScripBubbleGraphDTO>();
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				ScripBubbleGraphDTO aDto = new ScripBubbleGraphDTO();
				aDto.setId(((BigInteger)rowdata[0]).longValue());
				aDto.setName((String)rowdata[1]);
				aDto.setBseCode((String)rowdata[2]);
				aDto.setNseCode((String)rowdata[3]);
				aDto.setGrowthRank((Integer)rowdata[4]);
				aDto.setWlScoreRank((Integer)rowdata[5]);
				aDto.setWlScore((Integer)rowdata[6]);
				aDto.setDataDate((Date)rowdata[7]);
				aDto.setWlCount((Integer)rowdata[8]);
				
				aDto.setSqgRevenue((Float)rowdata[9]);
				aDto.setSqgProfit((Float)rowdata[10]);
				aDto.setSqgProfitMargin((Float)rowdata[11]);
				aDto.setActualProfitMarginLastQtr((Float)rowdata[12]);
				
				
				returnList.add(aDto);
			}
			System.out.println("recordsCount="+returnList.size());
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return returnList;
	
	}
	
	public Map getScripRatioData(Long scripId, String fromDate, String toDate, String selectedFirstParameter, String selectedNextParameter) throws BusinessException {
		Map returnData = new HashMap();
		List dateList = new ArrayList();
		List firstParamDataList = new ArrayList();
		List nextParamDataList = new ArrayList();
		try {
			SimpleDateFormat normalDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat postgresFormat = new SimpleDateFormat("yyyy-MM-dd");
			String sqlStr = "select last_updated, "+selectedFirstParameter+"," + selectedNextParameter + " from scrips_history where last_updated >= '" + postgresFormat.format(normalDateFormat.parse(fromDate))+ "' and last_updated <= '" + postgresFormat.format(normalDateFormat.parse(toDate)) + "' and f_scrip=" + scripId + " order by last_updated ";
			Query q = entityManager.createNativeQuery(sqlStr);
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				dateList.add((Date)rowdata[0]);
				Object obj = rowdata[1];
				if (obj!=null) firstParamDataList.add(obj.toString());
				else firstParamDataList.add("0");
				
				Object nextObj = rowdata[2]; 
				if (nextObj!=null) nextParamDataList.add(nextObj.toString());
				else nextParamDataList.add("0");
				System.out.println("dateList="+ dateList.get(dateList.size()-1) + " firstParamDataList="+firstParamDataList.get(firstParamDataList.size()-1)+" nextParamDataList="+nextParamDataList.get(nextParamDataList.size()-1));
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		returnData.put("dateData", dateList);
		returnData.put("firstRatioData", firstParamDataList);
		returnData.put("nextRatioData", nextParamDataList);
		return returnData;
	}
	
	public void persistMojoId(Long scripId, String mojoId) throws BusinessException  {
		String sqlStr = "update scrips set marketsmojo_id='"+mojoId +"' where id="+scripId;
		this.entityManager.createNativeQuery(sqlStr).executeUpdate();
	}
	
	public void persistStockaddaId(Long scripId, String stockaddaId) throws BusinessException  {
		String sqlStr = "update scrips set stockadda_id='"+stockaddaId +"' where id="+scripId;
		this.entityManager.createNativeQuery(sqlStr).executeUpdate();
	}
	
	public void persistTijoriFinanceId(Long scripId, String tijorifinanceId) throws BusinessException  {
		String sqlStr = "update scrips set tijorifinance_id='"+tijorifinanceId +"' where id="+scripId;
		this.entityManager.createNativeQuery(sqlStr).executeUpdate();
	}
	
	public void persistReutersId(Long scripId, String reutersId) throws BusinessException  {
		String sqlStr = "update scrips set reuters_id='"+reutersId +"' where id="+scripId;
		this.entityManager.createNativeQuery(sqlStr).executeUpdate();
	}
	
	public void persistTrendlyneId(Long scripId, String trendlyneId) throws BusinessException  {
		String sqlStr = "update scrips set trendlyne_id='"+trendlyneId +"' where id="+scripId;
		this.entityManager.createNativeQuery(sqlStr).executeUpdate();
	}
	
	public void persistTickertapeId(Long scripId, String tickertapeId) throws BusinessException  {
		String sqlStr = "update scrips set tickertape_id='"+tickertapeId +"' where id="+scripId;
		this.entityManager.createNativeQuery(sqlStr).executeUpdate();
	}
	
	public void persistSimplywallstId(Long scripId, String simplywallstId) throws BusinessException  {
		String sqlStr = "update scrips set simplywallst_id='"+simplywallstId +"' where id="+scripId;
		this.entityManager.createNativeQuery(sqlStr).executeUpdate();
	}
	
	public List<ScripCompanyInfoDTO> getScripsByFreetextSearch(String freeTextSearchInput, String orderBy, String orderType, Long watchlistId) throws BusinessException {
		List<ScripCompanyInfoDTO> returnList = new ArrayList<ScripCompanyInfoDTO>(); 
		
		WatchlistDao watchlistDao = new WatchlistDao(entityManager);
		String wlFilterSql = null;
		if (watchlistId!=null)  {				
			String secondWatchlistQuery = watchlistDao.getWatchlistById(watchlistId).getScripFecthSql();
			wlFilterSql = " and scr.id in (" + secondWatchlistQuery + ")";	
		}
		
		if (freeTextSearchInput.startsWith("$")) {
			String exchangeCode = freeTextSearchInput.substring(1);
			Long scripId = getScripByBseCodeOrNseCode(exchangeCode.toUpperCase());
			
			String sqlStr = "select scr.id, scr.name, scr.bse_code, scr.nse_code, ts_rank_cd(word_tokens, replace(query::::text, '&', '|')::::tsquery, 32) as rank from scrips scr, company_info ci, (select plainto_tsquery('english', details) as query, id from company_info where f_scrip=" + scripId + ") AS  b  where scr.id=ci.f_scrip ";
			
			if (wlFilterSql!=null) {
				sqlStr = sqlStr + wlFilterSql;
			}
			sqlStr = sqlStr + " ORDER BY rank DESC limit 50";
			
			System.out.println("sqlStr="+sqlStr);
			
			Query q = entityManager.createNativeQuery(sqlStr);
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				returnList.add(new ScripCompanyInfoDTO( ((BigInteger)rowdata[0]).longValue(), (String)rowdata[1], (String)rowdata[2], (String)rowdata[3], (Float)rowdata[4]));
			}
		} else {
			
			String[] splitWords = freeTextSearchInput.split(" ");			
			
			String sqlStr = "select scr.id, scr.name, scr.bse_code, scr.nse_code from scrips scr where scr.id in ( select f_scrip from company_info where word_tokens @@ to_tsquery('" + freeTextSearchInput.replaceAll(" ", " & ") + "') ) ";
			System.out.println("SqlSqtr= " + sqlStr);
			if (wlFilterSql!=null) {
				sqlStr = sqlStr + wlFilterSql;
			}
			sqlStr = sqlStr + " order by " + orderBy +" " + orderType;
			
			Query q = entityManager.createNativeQuery(sqlStr);
			List<Object[]> listResults = q.getResultList();
			Iterator<Object[]> iter = listResults.iterator();
			
			while (iter.hasNext()) {
				Object[] rowdata = iter.next();
				returnList.add(new ScripCompanyInfoDTO( ((BigInteger)rowdata[0]).longValue(), (String)rowdata[1], (String)rowdata[2], (String)rowdata[3], 0f));
			}
		}
		return returnList;
	}
	
	public static String convert(String[] name) { 
	    StringBuilder sb = new StringBuilder();
	    for (String st : name) { 
	        sb.append('\'').append(st).append('\'').append(',');
	    }
	    if (name.length != 0) sb.deleteCharAt(sb.length()-1);
	    return sb.toString();
	}
	
	private float getLatestQtrRevenue(Long scripId) {
		float retVal = 0f;
		// First get consolidated data
		
		String sqlStr = "select financial_report_quarter_id, revenue from consolidated_financial_result where f_scrip="+ scripId 
				+ " and financial_report_quarter_id = (select max(financial_report_quarter_id) from consolidated_financial_result where f_scrip=" + scripId + ")";
		System.out.println(sqlStr);
		Query q = entityManager.createNativeQuery(sqlStr);
		List<Object[]> listResults = q.getResultList();
		Iterator<Object[]> iter = listResults.iterator();
		Float consolidatedRevenue = null;
		Long consolidatedQtrId = null;
		while (iter.hasNext()) {
			Object[] rowdata = iter.next();
			consolidatedQtrId = ((Integer)rowdata[0]).longValue();
			consolidatedRevenue = (Float)rowdata[1];
		}	
		
		System.out.println("consolidatedQtrId="+consolidatedQtrId + " consolidatedRevenue="+consolidatedRevenue);
		
		sqlStr = "select financial_report_quarter_id, revenue from financial_result where f_scrip="+ scripId 
				+ " and financial_report_quarter_id = (select max(financial_report_quarter_id) from financial_result where f_scrip=" + scripId + ")";
		System.out.println(sqlStr);
		q = entityManager.createNativeQuery(sqlStr);
		listResults = q.getResultList();
		iter = listResults.iterator();
		Float standaloneRevenue = null;
		Long standaloneQtrId = null;
		while (iter.hasNext()) {
			Object[] rowdata = iter.next();
			standaloneQtrId = ((Integer)rowdata[0]).longValue();
			standaloneRevenue = (Float)rowdata[1];
		}	
		System.out.println("standaloneQtrId="+standaloneQtrId + " standaloneRevenue="+standaloneRevenue);
		
		
		if (consolidatedQtrId==null && standaloneQtrId!=null) {
			retVal = standaloneRevenue;
		} else if (consolidatedQtrId!=null && standaloneQtrId!=null && consolidatedQtrId>=standaloneQtrId) {
			retVal = consolidatedRevenue;
		} else if(standaloneQtrId!=null) {
			retVal = standaloneRevenue;
		}
		return retVal;
	}
	
	
	public List<ScripLevelPriceVsRatioDTO> getScripPriceVsRatio(String scripExcode) throws BusinessException {
		List<ScripLevelPriceVsRatioDTO> scripLevelPriceVsRatioDTOs = new ArrayList<ScripLevelPriceVsRatioDTO>();
		
		String sqlStr = "select scr.last_updated, scr.cmp, scr.cmp/NULLIF(scr.book_value,0) as PB, scr.cmp/NULLIF(scr.eps_ttm,0) as pe, scr.return_on_capital_employed , scr.change_in_roce"
				+ " from scrips_history scr where f_scrip in (select id from scrips where nse_code like '" + scripExcode + "' OR bse_code like '" + scripExcode + "' LIMIT 1)"
				+ " order by last_updated desc limit 1000";		
		
		System.out.println("sqlStr="+sqlStr);		
		
		Query q = entityManager.createNativeQuery(sqlStr);
		List<Object[]> listResults = q.getResultList();
		Iterator<Object[]> iter = listResults.iterator();
		
		while (iter.hasNext()) {
			Object[] rowdata = iter.next();
			Date dataDate =  (Date)rowdata[0];
			Float price = rowdata[1]!=null ? (Float)rowdata[1]: 0f;
			Float pb = rowdata[2]!=null ? (Float)rowdata[2]: 0f;
			Float pe = rowdata[3]!=null ? (Float)rowdata[3]: 0f;
			Float roce = rowdata[4]!=null ? (Float)rowdata[4]: 0f;
			Float roceChange = rowdata[5]!=null ? (Float)rowdata[5]: 0f;
			scripLevelPriceVsRatioDTOs.add(0,new ScripLevelPriceVsRatioDTO(dataDate, price, pe, pb, roce, roceChange));
		}
		return scripLevelPriceVsRatioDTOs;
	}
	 
	
	public List<ScripCompareDTO> getScripRatios(String[] scripExcodes, Long selectedWatchlist) throws BusinessException {
		
		System.out.println("scripExcodes="+scripExcodes);
		
		List<ScripCompareDTO> scripDtos = new ArrayList<ScripCompareDTO>(); 

		String scripExCodes = "";
		if (scripExcodes!=null) {			
			for (int i=0;i<scripExcodes.length; i++) {
				if (i!=0) scripExCodes = scripExCodes + ",";
				scripExCodes = scripExCodes + "'" + scripExcodes[i] + "'";
			}
		}
		
		String sqlStr = "select scr.id, scr.nse_code, scr.name, scr.cmp/NULLIF(scr.book_value,0) as PB, scr.cmp/NULLIF(scr.eps_ttm,0) as pe, scr.lastqtrnetprofitamount, scr.profit_margin_lastqtr, "
				+ " scr.last_same_qtr_growth_percentage , scr.return_on_capital_employed , scr.change_in_roce as roceChange, scr.targetpricebypb/NULLIF(scr.cmp,0) as pbypb, scr.targetpricebype/NULLIF(scr.cmp,0) as pbype from scrips scr where ";
		if (selectedWatchlist!=null) {
			sqlStr = sqlStr +  " scr.id in (select f_scrip from watchlist_item where f_watchlist = " + selectedWatchlist + ")";
		} else { 
			sqlStr = sqlStr +  " scr.nse_code in (" + scripExCodes + ") or scr.bse_code in (" + scripExCodes + ")";
		}
		
		Query q = entityManager.createNativeQuery(sqlStr);
		List<Object[]> listResults = q.getResultList();
		Iterator<Object[]> iter = listResults.iterator();
		
		while (iter.hasNext()) {
			Object[] rowdata = iter.next(); // Long id, String exchangeCode, String name, float pe, float pb, float qtrRevenue, float qtrProfit, float qtrProfitMargin
			long scripId = ((BigInteger)rowdata[0]).longValue();
			scripDtos.add(new ScripCompareDTO( scripId, (String)rowdata[1], (String)rowdata[2], 
					((Float)rowdata[4]).floatValue(),  
					((Float)rowdata[3]).floatValue(), 
					getLatestQtrRevenue(scripId),
					((Float)rowdata[5]).floatValue(),  
					((Float)rowdata[6]).floatValue(),
					((Float)rowdata[7]).floatValue(),
					((Float)rowdata[8]).floatValue(),
					rowdata[9]!=null? ((Float)rowdata[9]).floatValue():0,
					((Float)rowdata[10]).floatValue(),
					((Float)rowdata[11]).floatValue()
					));
		}
    	
		Collections.sort(scripDtos, new RevenueComparator());
		
    	return scripDtos;
	}

	
	
}

class RevenueComparator implements Comparator<ScripCompareDTO> {
	  
    // override the compare() method
    public int compare(ScripCompareDTO s1, ScripCompareDTO s2)
    {
        if (s1.getQtrRevenue() == s2.getQtrRevenue())
            return 0;
        else if (s1.getQtrRevenue() > s2.getQtrRevenue())
            return 1;
        else
            return -1;
    }
}

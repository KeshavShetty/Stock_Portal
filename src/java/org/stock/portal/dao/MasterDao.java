
package org.stock.portal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.Sector;
import org.stock.portal.domain.dto.ScripSearchCriteriaDTO;

public class MasterDao {
	
	/** Logger instance for the class. */
    protected static final Logger log = Logger.getLogger(MasterDao.class.getName());
    		
	/** Session EJB EntityManager - Injected by constructor to DAO. */
	private EntityManager entityManager = null;
	
	/**
	 * 
	 * @param eManager
	 */
	public MasterDao(final EntityManager eManager) {
		this.entityManager = eManager;
	}
	
	public List<Sector> getAllSectors() throws BusinessException {		
		List<Sector> retList = null;		
		try {
			retList = (List<Sector>)this.entityManager.createNamedQuery("Sector.getAllSectors").getResultList();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
		}
		return retList;
	}
	
	public int saveMasterValue(Long pkId,String tableName, String columnName, String newValue) throws BusinessException {
		System.out.println("In saveMasterValue pkId="+pkId+" tableName="+tableName + " columnName="+columnName+" newValue="+newValue);
		int retVal = 0;
		String queryStr = "update " + tableName + " set "+columnName+"='"+newValue+"' where id="+pkId;
		Query q = entityManager.createNativeQuery(queryStr);
		retVal = q.executeUpdate();
		return retVal;
	}
}

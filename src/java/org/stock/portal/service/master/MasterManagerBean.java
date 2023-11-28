package org.stock.portal.service.master;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.dao.MasterDao;
import org.stock.portal.domain.Sector;
import org.stock.portal.web.util.Constants;

@Stateless(name="MasterManager", mappedName="org.stock.portal.service.master.MasterManager")
public class MasterManagerBean implements MasterManager {
	@Resource 
	private SessionContext context;

	private static Logger log = Logger.getLogger(MasterManager.class.getName());
	
	@PersistenceContext(unitName = Constants.ProjectConfig.PERSISTENCE_UNIT)
    private EntityManager entityManager;

	
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Sector> getAllSectors() throws BusinessException {
    	return (new MasterDao(entityManager)).getAllSectors();// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public int saveMasterValue(Long pkId,String tableName, String columnName, String newValue) throws BusinessException {
    	return (new MasterDao(entityManager)).saveMasterValue(pkId, tableName, columnName, newValue);
    }
    
}
  

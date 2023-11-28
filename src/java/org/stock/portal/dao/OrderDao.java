
package org.stock.portal.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.stock.portal.domain.IciciOrder;


/**
 * 
 * @author European Dynamics
 */
public class OrderDao {
	
	/** Logger instance for the class. */
    protected static final Logger log = Logger.getLogger(OrderDao.class.getName());
    		
	/** Session EJB EntityManager - Injected by constructor to DAO. */
	private EntityManager entityManager = null;
	
	/**
	 * 
	 * @param eManager
	 */
	public OrderDao(final EntityManager eManager) {
		this.entityManager = eManager;
	}	

	@SuppressWarnings("unchecked")
	public IciciOrder placeNewOrderWithIcici(IciciOrder newOrder) {	
		log.debug(" DAO-HEADER: searchAutoscanByCriteria");	
		
		try {
			entityManager.persist(newOrder);
		} catch (NoResultException e) {
			log.debug(" -- searchautoAutoscanByCriteria : No intraday data found with these criteria ");
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return newOrder;
	}
	
}

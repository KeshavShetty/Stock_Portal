package org.stock.portal.service.order;



import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.stock.portal.common.ApplicationConfig;
import org.stock.portal.common.ICICIOrderUtil;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.dao.OrderDao;
import org.stock.portal.domain.IciciOrder;
import org.stock.portal.web.util.Constants;




@Stateless(name="OrderManager", mappedName="org.stock.portal.service.order.OrderManager")
public class OrderManagerBean implements OrderManager {
	@Resource 
	private SessionContext context;

	private static Logger log = Logger.getLogger(OrderManager.class.getName());
	
	@PersistenceContext(unitName = Constants.ProjectConfig.PERSISTENCE_UNIT)
    private EntityManager entityManager;
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    @SuppressWarnings(value="unchecked")
   public void placeNewOrderWithIcici(IciciOrder newOrder) throws BusinessException {
    	log.debug("In AutoscanManager searchAutoscanByCriteria()");
    	IciciOrder placedOrder = (new OrderDao(entityManager)).placeNewOrderWithIcici(newOrder);
    	System.out.println("placedOrder id="+placedOrder.getId());
    	//Todo now call Icici place order HttpClient
    	ICICIOrderUtil iciciUtil = new ICICIOrderUtil(ApplicationConfig.ICICI_LOGIN, ApplicationConfig.ICICI_PWD, ApplicationConfig.ICICI_PAN, ApplicationConfig.ICICI_ACCOUNT_NUMBER);
    	iciciUtil.getLimit();
    }
    
}
  

package org.stock.portal.service.order;


import javax.ejb.Local;

import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.IciciOrder;

@Local
public interface OrderManager {
	public void placeNewOrderWithIcici(IciciOrder newOrder) throws BusinessException;	
}
  

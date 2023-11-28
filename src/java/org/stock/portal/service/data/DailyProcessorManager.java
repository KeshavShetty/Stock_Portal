package org.stock.portal.service.data;

import java.util.Map;

import javax.ejb.Local;

import org.stock.portal.common.exception.BusinessException;

@Local
public interface DailyProcessorManager {
	public void processBseIntraData()throws BusinessException ;
	public void processEODData(Map dataMap) throws BusinessException;
}
  

package org.stock.portal.service.data;

import java.util.Map;

import javax.ejb.Local;

import org.stock.portal.common.exception.BusinessException;

@Local
public interface DataMutatorManager {
	public void insertEodData(Map<String, Object> dataMap)throws BusinessException ;
	public void populateTreePerformance(Long sourceScripId)throws BusinessException ;
}
  

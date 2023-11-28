package org.stock.portal.service.master;


import java.util.List;

import javax.ejb.Local;

import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.Sector;

@Local
public interface MasterManager {
	
	public List<Sector> getAllSectors() throws BusinessException ;
	
	public int saveMasterValue(Long pkId,String tableName, String columnName, String newValue) throws BusinessException ;
}
  

package org.stock.portal.service.jqGrid;


import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.dto.KeyValueDTO;

@Local
public interface JqGridManager {	
	public Map<String, Object> getJqGridTableDetails(String tableIdentifier, String loggedUserId) throws BusinessException;
	public String getJsonData(Map<String, String> allRequestQueryParams) throws BusinessException;
	public List<KeyValueDTO> getSelectList(Long jqColumnId, String userId) throws BusinessException;
	public void saveUserSearch(String userId, String taleIdentifier, String filter, String searchName)throws BusinessException; 
}
  

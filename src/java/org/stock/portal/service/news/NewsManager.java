package org.stock.portal.service.news;


import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.NewsFeedSource;
import org.stock.portal.domain.metroWL.dto.AdvancedNewsSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.NewsSearchCriteriaDTO;

@Local
public interface NewsManager {
	
	public Map<String, Object> searchNews(NewsSearchCriteriaDTO newsSearchCriteriaDTO) throws BusinessException;
	
	public List<NewsFeedSource> getFeedSourceList() throws BusinessException;
	
	public Map<String, Object> searchNewsByAdvancedCriteria(AdvancedNewsSearchCriteriaDTO analysisSearchCriteriaDTO) throws BusinessException;

}
  

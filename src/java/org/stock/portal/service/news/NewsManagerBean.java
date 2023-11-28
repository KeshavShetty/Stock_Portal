package org.stock.portal.service.news;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.dao.NewsDao;
import org.stock.portal.domain.NewsFeedSource;
import org.stock.portal.domain.metroWL.dto.AdvancedNewsSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.NewsSearchCriteriaDTO;
import org.stock.portal.web.util.Constants;

@Stateless(name="NewsManager", mappedName="org.stock.portal.service.news.NewsManager")
public class NewsManagerBean implements NewsManager {
	@Resource 
	private SessionContext context;

	private static Logger log = Logger.getLogger(NewsManager.class.getName());
	
	@PersistenceContext(unitName = Constants.ProjectConfig.PERSISTENCE_UNIT)
    private EntityManager entityManager;
	    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public  Map<String, Object> searchNews(NewsSearchCriteriaDTO newsSearchCriteriaDTO) throws BusinessException {
    	log.debug("In ScripManager searchScripByCriteria()");
    	return (new NewsDao(entityManager)).searchNews(newsSearchCriteriaDTO);// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<NewsFeedSource> getFeedSourceList() throws BusinessException {
    	log.debug("In ScripManager searchScripByCriteria()");
    	return (new NewsDao(entityManager)).getFeedSourceList();// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public  Map<String, Object> searchNewsByAdvancedCriteria(AdvancedNewsSearchCriteriaDTO analysisSearchCriteriaDTO) throws BusinessException {
    	log.debug("In News manager searchNewsByAdvancedCriteria()");
    	return (new NewsDao(entityManager)).searchNewsByAdvancedCriteria(analysisSearchCriteriaDTO);// Now return bse data
    }
    
}
  

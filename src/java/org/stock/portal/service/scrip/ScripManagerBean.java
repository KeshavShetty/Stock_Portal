package org.stock.portal.service.scrip;



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
import org.stock.portal.dao.ScripDao;
import org.stock.portal.domain.EodData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.dto.ScripBubbleGraphDTO;
import org.stock.portal.domain.dto.ScripCompareDTO;
import org.stock.portal.domain.dto.ScripLevelPriceVsRatioDTO;
import org.stock.portal.domain.dto.ScripSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.BubblegraphCriteriaDTO;
import org.stock.portal.web.util.Constants;





@Stateless(name="ScripManager", mappedName="org.stock.portal.service.scrip.ScripManager")
public class ScripManagerBean implements ScripManager {
	@Resource 
	private SessionContext context;

	private static Logger log = Logger.getLogger(ScripManager.class.getName());
	
	@PersistenceContext(unitName = Constants.ProjectConfig.PERSISTENCE_UNIT)
    private EntityManager entityManager;

	/**
     * Authenticates  an <CODE>Account</CODE> given  the  username and
     * the password. If  the  authentication is successful, returns the
     * <CODE>Account</CODE> instance which corresponds to these credentials.
     *
     * @param userName <code>String</code>
     *        The account username.
     * @param password <code>String</code>
     *        The account password.
     * @return <code>Account</code>
     *        The account that matches the parameters.
     * @throws BusinessLayerException
     *        Thrown if there is a problem with the back-end.
     *
     */
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Scrip> getScripsBySearchString(String scripType, String searchString)
        throws BusinessException {
    	log.debug("In ScripManager getScripsBySearchString()-scripType="+scripType+" searchString="+searchString);
    	ScripDao scripDao = new ScripDao(entityManager);
    	List<Scrip> retList = scripDao.getScripsBySearchString(scripType, searchString);
    	return retList;
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<String> getScripsBySearchStringExtendedSymbol(List<Scrip> scripList) throws BusinessException {
    	ScripDao scripDao = new ScripDao(entityManager);
    	return scripDao.getScripsBySearchStringExtendedSymbol(scripList);
    }
    
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Scrip> searchScripByCriteria(ScripSearchCriteriaDTO scripSearchCriteriaDTO) throws BusinessException {
    	log.debug("In ScripManager searchScripByCriteria()");
    	return (new ScripDao(entityManager)).searchScripByCriteria(scripSearchCriteriaDTO);// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public  Map<String, Object> searchScrip(org.stock.portal.domain.metroWL.dto.ScripSearchCriteriaDTO scripSearchCriteriaDTO) throws BusinessException {
    	log.debug("In ScripManager searchScripByCriteria()");
    	return (new ScripDao(entityManager)).searchScrip(scripSearchCriteriaDTO);// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Scrip getScripById(Long scripId) throws BusinessException {
    	return (new ScripDao(entityManager)).getScripById(scripId);
    }
    
    public List<EodData> getIndexScripByScripId(Long scripId) throws BusinessException {
    	return (new ScripDao(entityManager)).getIndexScripByScripId(scripId);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Scrip getScripByNSECode(String nseCode) throws BusinessException {
    	return (new ScripDao(entityManager)).getScripByNSECode(nseCode);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Scrip getScripByBSECode(String bseCode) throws BusinessException {
    	Scrip retScr = null;
    	ScripDao scrDao = new ScripDao(entityManager);
    	Long scrId = scrDao.getScripByBseCode(bseCode);
    	if (scrId!=null) {
    		retScr = scrDao.getScripById(scrId);
    	}
    	return retScr;
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Scrip getScripByExCode(String exCode) throws BusinessException {
    	Scrip retScr = null;
    	ScripDao scrDao = new ScripDao(entityManager);
    	Long scrId = scrDao.getScripByNseCode(exCode);
    	if (scrId==null) {
    		scrId = scrDao.getScripByBseCode(exCode);
    	}
    	
    	if (scrId!=null) {
    		retScr = scrDao.getScripById(scrId);
    	}
    	return retScr;
    }
    
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<ScripBubbleGraphDTO> searchScripForBubbleGraphByCriteria(BubblegraphCriteriaDTO criteriaDTO) throws BusinessException {
    	ScripDao scrDao = new ScripDao(entityManager);
    	return scrDao.searchScripForBubbleGraphByCriteria(criteriaDTO);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Map getScripRatioData(Long scripId, String fromDate, String toDate, String selectedFirstParameter, String selectedNextParameter) throws BusinessException {
    	ScripDao scrDao = new ScripDao(entityManager);
    	return scrDao.getScripRatioData(scripId, fromDate, toDate, selectedFirstParameter, selectedNextParameter);
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void persistMojoId(Long scripId, String mojoId) throws BusinessException {
    	ScripDao scrDao = new ScripDao(entityManager);
    	scrDao.persistMojoId(scripId, mojoId);
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void persistStockaddaId(Long scripId, String stockaddaId) throws BusinessException {
    	ScripDao scrDao = new ScripDao(entityManager);
    	scrDao.persistStockaddaId(scripId, stockaddaId);
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void persistTijoriFinanceId(Long scripId, String tijorifinanceId) throws BusinessException {
    	ScripDao scrDao = new ScripDao(entityManager);
    	scrDao.persistTijoriFinanceId(scripId, tijorifinanceId);
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void persistReutersId(Long scripId, String reutersId) throws BusinessException {
    	ScripDao scrDao = new ScripDao(entityManager);
    	scrDao.persistReutersId(scripId, reutersId);
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void persistTrendlyneId(Long scripId, String trendlyneId) throws BusinessException {
    	ScripDao scrDao = new ScripDao(entityManager);
    	scrDao.persistTrendlyneId(scripId, trendlyneId);
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void persistTickertapeId(Long scripId, String tickertapeId) throws BusinessException {
    	ScripDao scrDao = new ScripDao(entityManager);
    	scrDao.persistTickertapeId(scripId, tickertapeId);
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void persistSimplywallstId(Long scripId, String simplywallstId) throws BusinessException {
    	ScripDao scrDao = new ScripDao(entityManager);
    	scrDao.persistSimplywallstId(scripId, simplywallstId);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public  List<ScripCompareDTO> getScripRatios(String[] scripExcodes, Long selectedWatchlist) throws BusinessException {
    	ScripDao scrDao = new ScripDao(entityManager);
    	return scrDao.getScripRatios(scripExcodes, selectedWatchlist);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public  List<ScripLevelPriceVsRatioDTO> getScripPriceVsRatio(String scripExcode) throws BusinessException {
    	ScripDao scrDao = new ScripDao(entityManager);
    	return scrDao.getScripPriceVsRatio(scripExcode);
    } 
    
}
  

package org.stock.portal.service.autoscan;



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
import org.stock.portal.dao.AutoscanDao;
import org.stock.portal.dao.DataDao;
import org.stock.portal.dao.NewsDao;
import org.stock.portal.domain.AutoscanMaster;
import org.stock.portal.domain.AutoscanResult;
import org.stock.portal.domain.BSEEodData;
import org.stock.portal.domain.dto.AutoscanSearchCriteriaDTO;
import org.stock.portal.domain.dto.EODForPastPerformanceCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.AdvancedAnalysisSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.AnalysisSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.CandlePatternSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.NewsSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.PastAchieversSearchCriteriaDTO;
import org.stock.portal.web.util.Constants;




@Stateless(name="AutoscanManager", mappedName="org.stock.portal.service.autoscan.AutoscanManager")
public class AutoscanManagerBean implements AutoscanManager {
	@Resource 
	private SessionContext context;

	private static Logger log = Logger.getLogger(AutoscanManager.class.getName());
	
	@PersistenceContext(unitName = Constants.ProjectConfig.PERSISTENCE_UNIT)
    private EntityManager entityManager;
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    @SuppressWarnings(value="unchecked")
    public List<AutoscanResult> searchAutoscanByCriteria(AutoscanSearchCriteriaDTO autoscanSearchCriteriaDTO) throws BusinessException {
    	log.debug("In AutoscanManager searchAutoscanByCriteria()");
    	return (new AutoscanDao(entityManager)).searchAutoscanByCriteria(autoscanSearchCriteriaDTO);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    @SuppressWarnings(value="unchecked")
    public List<AutoscanMaster> getAutoscanMasterlistByType(Integer scanType) throws BusinessException {
    	log.debug("In AutoscanManager searchCandleAutoscanByCriteria()");
    	return (new AutoscanDao(entityManager)).getAutoscanMasterlistByType(scanType);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    @SuppressWarnings(value="unchecked")
    public List<AutoscanMaster> getAutoscanMasterlist() throws BusinessException {
    	log.debug("In AutoscanManager searchCandleAutoscanByCriteria()");
    	return (new AutoscanDao(entityManager)).getAutoscanMasterlist();
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    @SuppressWarnings(value="unchecked")
    public List<BSEEodData> getEodForPastPerformance(EODForPastPerformanceCriteriaDTO eodForPastPerformanceCriteriaDTO) throws BusinessException {
    	log.debug("In AutoscanManager searchCandleAutoscanByCriteria()");
    	return (new AutoscanDao(entityManager)).getEodForPastPerformance(eodForPastPerformanceCriteriaDTO);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public  Map<String, Object> searchAnalysis(AnalysisSearchCriteriaDTO analysisSearchCriteriaDTO) throws BusinessException {
    	log.debug("In AutoscanManager searchAnalysis()");
    	return (new AutoscanDao(entityManager)).searchAnalysis(analysisSearchCriteriaDTO);// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public  Map<String, Object> searchAnalysisByAdvancedCriteria(AdvancedAnalysisSearchCriteriaDTO analysisSearchCriteriaDTO) throws BusinessException {
    	log.debug("In AutoscanManager searchAnalysisByAdvancedCriteria()");
    	return (new AutoscanDao(entityManager)).searchAnalysisByAdvancedCriteria(analysisSearchCriteriaDTO);// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Map<String, Object> searchCandlePatternByCriteria(CandlePatternSearchCriteriaDTO criteriaDto) throws BusinessException {    	
    	return (new AutoscanDao(entityManager)).searchCandlePatternByCriteria(criteriaDto);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public  Map<String, Object> searchCandlePatternScripByAdvancedCriteria(AdvancedAnalysisSearchCriteriaDTO analysisSearchCriteriaDTO) throws BusinessException {
    	log.debug("In AutoscanManager searchAnalysisByAdvancedCriteria()");
    	return (new AutoscanDao(entityManager)).searchCandlePatternScripByAdvancedCriteria(analysisSearchCriteriaDTO);// Now return bse data
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    @SuppressWarnings(value="unchecked")
    public List<AutoscanResult> getSummaryAutoscanResult(Long scripId, int recentDays) throws BusinessException {
    	log.debug("In AutoscanManager searchAutoscanByCriteria()");
    	return (new AutoscanDao(entityManager)).getSummaryAutoscanResult(scripId, recentDays);
    }
    
}
  

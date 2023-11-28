package org.stock.portal.service.autoscan;


import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.AutoscanMaster;
import org.stock.portal.domain.AutoscanResult;
import org.stock.portal.domain.BSEEodData;
import org.stock.portal.domain.dto.AutoscanSearchCriteriaDTO;
import org.stock.portal.domain.dto.EODForPastPerformanceCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.AdvancedAnalysisSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.AnalysisSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.CandlePatternSearchCriteriaDTO;

@Local
public interface AutoscanManager {
	public List<AutoscanResult> searchAutoscanByCriteria(AutoscanSearchCriteriaDTO autoscanSearchCriteriaDTO) throws BusinessException;
	public List<AutoscanMaster> getAutoscanMasterlistByType(Integer scanType) throws BusinessException;
	public List<AutoscanMaster> getAutoscanMasterlist() throws BusinessException;
	public List<BSEEodData> getEodForPastPerformance(EODForPastPerformanceCriteriaDTO eodForPastPerformanceCriteriaDTO) throws BusinessException;
	public Map<String, Object> searchAnalysis(AnalysisSearchCriteriaDTO analysisSearchCriteriaDTO) throws BusinessException;	
	public Map<String, Object> searchAnalysisByAdvancedCriteria(AdvancedAnalysisSearchCriteriaDTO analysisSearchCriteriaDTO) throws BusinessException;
	public Map<String, Object> searchCandlePatternByCriteria(CandlePatternSearchCriteriaDTO criteriaDto) throws BusinessException;
	public Map<String, Object> searchCandlePatternScripByAdvancedCriteria(AdvancedAnalysisSearchCriteriaDTO analysisSearchCriteriaDTO) throws BusinessException;
	public List<AutoscanResult> getSummaryAutoscanResult(Long scripId, int recentDays) throws BusinessException;
}
  

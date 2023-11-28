package org.stock.portal.service.scrip;


import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.EodData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.dto.ScripBubbleGraphDTO;
import org.stock.portal.domain.dto.ScripCompareDTO;
import org.stock.portal.domain.dto.ScripLevelPriceVsRatioDTO;
import org.stock.portal.domain.dto.ScripSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.BubblegraphCriteriaDTO;

@Local
public interface ScripManager {
	
	public List<Scrip> getScripsBySearchString(String scripType, String searchString) throws BusinessException ;
	
	public List<String> getScripsBySearchStringExtendedSymbol(List<Scrip> scripList) throws BusinessException ;
	
	public List<Scrip> searchScripByCriteria(ScripSearchCriteriaDTO scripSearchCriteriaDTO) throws BusinessException;
	public Map<String, Object> searchScrip(org.stock.portal.domain.metroWL.dto.ScripSearchCriteriaDTO scripSearchCriteriaDTO) throws BusinessException;
	public Scrip getScripById(Long scripId) throws BusinessException;
	public List<EodData> getIndexScripByScripId(Long scripId) throws BusinessException;
	
	public Scrip getScripByNSECode(String nseCode) throws BusinessException;
	public Scrip getScripByBSECode(String bseCode) throws BusinessException ;
	
	public List<ScripBubbleGraphDTO> searchScripForBubbleGraphByCriteria(BubblegraphCriteriaDTO criteriaDTO) throws BusinessException;
	
	public Map getScripRatioData(Long scripId, String fromDate, String toDate, String selectedFirstParameter, String selectedNextParameter) throws BusinessException;
	public void persistMojoId(Long scripId, String mojoId) throws BusinessException;
	public void persistStockaddaId(Long scripId, String stockaddaId) throws BusinessException;
	public void persistTijoriFinanceId(Long scripId, String tijorifinanceId) throws BusinessException;
	public void persistReutersId(Long scripId, String reutersId) throws BusinessException;
	public void persistTrendlyneId(Long scripId, String TrendlyneId) throws BusinessException;	
	public void persistTickertapeId(Long scripId, String TrendlyneId) throws BusinessException;	
	public void persistSimplywallstId(Long scripId, String TrendlyneId) throws BusinessException;
	public  List<ScripCompareDTO> getScripRatios(String[] scripExcodes, Long selectedWatchlist) throws BusinessException;
	
	public  List<ScripLevelPriceVsRatioDTO> getScripPriceVsRatio(String scripExcode) throws BusinessException;
	
	public Scrip getScripByExCode(String exCode) throws BusinessException ;
}
  

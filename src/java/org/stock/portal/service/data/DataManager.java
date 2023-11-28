package org.stock.portal.service.data;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.BSEEodData;
import org.stock.portal.domain.BSEIntraSummaryData;
import org.stock.portal.domain.EodData;
import org.stock.portal.domain.FinancialResult;
import org.stock.portal.domain.IntradaySnapshotData;
import org.stock.portal.domain.NSEEodData;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.Sector;
import org.stock.portal.domain.TreePerformance;
import org.stock.portal.domain.ZeordhaDomVO;
import org.stock.portal.domain.dto.IntradaySummarySearchCriteriaDTO;
import org.stock.portal.domain.dto.OptionSummaryDto;
import org.stock.portal.domain.dto.ScripCompanyInfoDTO;
import org.stock.portal.domain.dto.ScripEOD;
import org.stock.portal.domain.metroWL.dto.IntradaySnapshotSearchCriteriaDTO;
import org.stock.portal.domain.metroWL.dto.PastAchieversSearchCriteriaDTO;

@Local
public interface DataManager {

	public List<BSEEodData> getEodData(String exchangeCode, String scripCode) throws BusinessException ;	
	public Map getEodData(String exchangeCode, String scripCode, Date fromDate, Date toDate) throws BusinessException ;
	public List<BSEIntraSummaryData> searchIntradayDataByCriteria(IntradaySummarySearchCriteriaDTO intradaySearchCriteriaDTO) throws BusinessException;
	public List<BSEEodData> getEodDataByScripId(Long scripId, int numberOfDays) throws BusinessException ;
	public Map<String, Object> searchPastPerformanceByCriteria(PastAchieversSearchCriteriaDTO criteriaDto) throws BusinessException;
	public List<ScripEOD> getEodDataUptoDateByScripId(Long scripId, Date dataDate, Integer numberOfDays, boolean exchangeCode) throws BusinessException;
	public Date getMaxDataDate(String exchangeCode, Date limitDate) throws BusinessException;
	public Map<String, Object> searchIntradaySnapshotByCriteria(IntradaySnapshotSearchCriteriaDTO criteriaDto) throws BusinessException;
	public Map<Date, ScripEOD> getNSESnapshotData(String exchangeCode, String scripCode, Date fromDate, Date toDate) throws BusinessException ;
	public Map<String, Object> searchIntradayBTSTByCriteria(IntradaySnapshotSearchCriteriaDTO criteriaDto) throws BusinessException;
	public List<EodData> getIndexViewResult(Date dataDate, String exchange, String orderBy, String orderType) throws BusinessException;
	public List<EodData> getIndexScripsResult(Long indexId, Date dataDate, String orderBy, String orderType) throws BusinessException;
	public List<TreePerformance> getTreePerformanceForScrip(Long sourceScripId, String orderBy, String orderType) throws BusinessException;
	
	public void sendMessage(Object aObj) throws BusinessException;
	
	public List<FinancialResult> getFinancialResult(Long scripId, boolean useConsolidatedResult) throws BusinessException;
	
	public List<Scrip> getLatestFinancialResultDeclaredScrips(String orderBy, String orderType) throws BusinessException;
	
	public List<IntradaySnapshotData> getSnapshotData(Long scripId, String starDate, String endDate) throws BusinessException;
	
	public void saveTrendline(Long userId, String symbol, String trendlineValue) throws BusinessException;
	public String getTrendline(Long userId, String symbol, String extendedSymbol) throws BusinessException; 
	
	public List<Sector> getAllSectors() throws BusinessException;
	public void saveQtrResult(FinancialResult financialResult) throws BusinessException;
	public Integer getMaxFinancialQtrId() throws BusinessException;
	public List<NSEEodData> getNSEEodData(String nseCode, String uptoDateStr) throws BusinessException;
	
	public void saveKiteRequestToken(String clientId, String requestToken) throws BusinessException;
	public List<ScripEOD> getTickData(String symbol, Date startTime, Date endTime) throws BusinessException;
	
	public List<ZeordhaDomVO> getDomData(Long scripId, Date fromTime, Date toTime) throws BusinessException;
	
	public List<ScripCompanyInfoDTO> getScripsByFreetextSearch(String freeTextSearchInput, String orderBy, String orderType, Long watchlistId) throws BusinessException;
	
	public List<OptionSummaryDto> getOptionOIData(String indexName, String forDate) throws BusinessException;
	
	public void saveOptionOrder(String indexName, String optiontype) throws BusinessException;
	
	public List<ScripEOD> getZerodhaCandleMinuteData(String symbol, String dataDate)throws BusinessException;

	public String getIndividualOptionOIData(String indexName, String forDate) throws BusinessException;
	
	public String getOISpikeData(String indexName, String forDate,int nooftopois,boolean filterOptionWorth) throws BusinessException;
	
	public String getOptionGreeksData(String optionName, String forDate) throws BusinessException;
	
	public String getOptionSpikeData(String indexName, String forDate) throws BusinessException;
	public String getOptionDeltaNeutralPriceDisparity(String indexName, String forDate) throws BusinessException;
	
	public String getOptionMaxOIWorthReversionData(String indexName, String forDate) throws BusinessException;
	public String getOptionOptimalStrikeData(String indexName, String forDate, Integer noOfTopOis) throws BusinessException;
	
	public String getPandLOfOrder(String indexName, String algonames, String forDate) throws BusinessException;
	
	public String getOptionGreeksRateOfChange(String indexName, String forDate, Integer noOfTopOis, String method, boolean filterOptionWorth) throws BusinessException;
	public String getTrendDecidingOptionGreeksROC(String indexName, String forDate, Integer noOfTopOis, String method) throws BusinessException;
	
	public String getOptionPriceRateOfChange(String indexName, String forDate, float basedelta) throws BusinessException;
	public String getOptionATMOTMOIRateOfChange(String indexName, String forDate) throws BusinessException;
	
	public String getOption1MGreeksMovements(String forDate, String optionnames) throws BusinessException;
	public String getOption1MPremiumDecay(String forDate, String optionnames) throws BusinessException;
	public String getOptionOIDescrepancy(String indexName, String forDate, Integer noOfTopOis, boolean filterOptionWorth) throws BusinessException;
	
	public String getDOMSummary(String forDate, String scripName) throws BusinessException;
}
  

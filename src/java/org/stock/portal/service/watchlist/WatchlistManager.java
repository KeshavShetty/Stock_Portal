package org.stock.portal.service.watchlist;


import java.util.List;

import javax.ejb.Local;

import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.domain.WatchlistItem;
import org.stock.portal.domain.WatchlistTransactions;
import org.stock.portal.domain.dto.WLCriteriaMatchDto;

@Local
public interface WatchlistManager {	
	public List<Watchlist> getWatchlistByUserId(Long userId) throws BusinessException ;
	public List<WatchlistItem> getStockInHandByUserId(Long userId) throws BusinessException;
	public List<WatchlistItem> getWatchlistItemByWlIdAndUserId(Long wlId, Long userId) throws BusinessException;
	public Integer addToWatchList(Long scripId, Long wlId, Long userId) throws BusinessException;
	public Integer createNewWatchList(Long scripId,String watchlistName, Long userId, String description) throws BusinessException;
	public List<WatchlistItem> getWatchlistItemByWlIdAndUserId(Long wlId, Long userId, boolean includeZeroHolidngItems) throws BusinessException;
	public List<WatchlistTransactions> getWatchlistTransactionsByWlIdAndUserId(Long wlId, Long userId, String fromDate, String toDate) throws BusinessException;
	public void addTransaction(Long scripId, Long watchListId, boolean transactionType, boolean exchange, String transactionDate, Long quantity, Float rate, Float brokerage, String settlementNumber) throws BusinessException;
	public WatchlistItem getWatchlistItemByWlIdAndScripId(Long watchListId, Long scripId) throws BusinessException;
	public String getMaxSettlementNumber(Long watchListId) throws BusinessException;
	public void deleteTransaction(Long selectedTransactionId) throws BusinessException;
	public void deleteWatchlistItem(Long selectedWLItemId) throws BusinessException ;
	public void rebuildWatchlistItem(Long selectedWLItemId) throws BusinessException;	
	public List<WatchlistItem> getWatchlistItemsByUserIdAndScripId(Long scripId, Long userId) throws BusinessException;
	public List<Scrip> getScripsByWatchlistId(Long wlId, String historyDate) throws BusinessException;
	public List<WatchlistItem> getSIHByUserIdAndScripId(Long scripId, Long userId) throws BusinessException;
	public List<Watchlist> getWatchlistItemByWlIdAndScripIdForManual(Long userId, Long scripId) throws BusinessException;
	public Watchlist getWatchlisByWlIdAndUser(Long wlId, Long userId) throws BusinessException;
	
    public List<Watchlist> getWatchlistByUserIdAndType(Long userId, String wlType) throws BusinessException;
    
    public List<WLCriteriaMatchDto> matchScripToWLCriteria(Long scripId, Long wlId) throws BusinessException;


}
  

package org.stock.portal.service.watchlist;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.stock.portal.common.ErrorCodeConstants;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.dao.WatchlistDao;
import org.stock.portal.domain.Scrip;
import org.stock.portal.domain.Watchlist;
import org.stock.portal.domain.WatchlistItem;
import org.stock.portal.domain.WatchlistTransactions;
import org.stock.portal.domain.dto.WLCriteriaMatchDto;
import org.stock.portal.web.util.Constants;

@Stateless(name="WatchlistManager", mappedName="org.stock.portal.service.watchlist.WatchlistManager")
public class WatchlistManagerBean implements WatchlistManager {
	
	private static Logger log = Logger.getLogger(WatchlistManager.class.getName());
	
	@PersistenceContext(unitName = Constants.ProjectConfig.PERSISTENCE_UNIT)
    private EntityManager entityManager;

	
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Watchlist> getWatchlistByUserId(Long userId) throws BusinessException {
    	log.debug("In WatchlistManager getWatchlistByUserId()-userId="+userId);
    	return (new WatchlistDao(entityManager)).getWatchlistByUserId(userId);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Watchlist> getWatchlistByUserIdAndType(Long userId, String wlType) throws BusinessException {
    	log.debug("In WatchlistManager getWatchlistByUserIdAndType()-userId="+userId+" wlType="+wlType);
    	return (new WatchlistDao(entityManager)).getWatchlistByUserIdAndType(userId, wlType);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<WatchlistItem> getStockInHandByUserId(Long userId) throws BusinessException {
    	log.debug("In WatchlistManager getStickInHandByUserId()-userId="+userId);
    	return (new WatchlistDao(entityManager)).getStockInHandByUserId(userId);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<WatchlistItem> getWatchlistItemByWlIdAndUserId(Long wlId, Long userId) throws BusinessException {
    	return (new WatchlistDao(entityManager)).getWatchlistItemByWlIdAndUserId(wlId, userId);
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public Integer addToWatchList(Long scripId, Long wlId, Long userId) throws BusinessException {
    	Integer returnCode = ErrorCodeConstants.NO_ERRORS;
    	
    	System.out.println("addToWatchList scripId="+scripId+" wlId="+wlId+" userId="+userId);
    	
    	//Check weather this WL belongs to this user
    	Query q = entityManager.createNativeQuery("SELECT wl.id FROM watchlist wl WHERE wl.id=? and wl.f_owner = ?");
    	q.setParameter( 1, wlId);
    	q.setParameter( 2, userId);
    	BigInteger userWLID = (BigInteger)q.getSingleResult();
    	System.out.println(" userWLID="+userWLID);
    	if (userWLID!=null) { // This watchlist belongs to user
    		//Todo: allowed number of scrips per WL = 10 
    		q = entityManager.createNativeQuery("SELECT count(*) FROM watchlist_item wlItem WHERE wlItem.f_watchlist=?");
        	q.setParameter( 1, wlId);
        	BigInteger exsitingScripCount = (BigInteger)q.getSingleResult();
        	System.out.println(" exsitingScripCount="+exsitingScripCount);
        	if (exsitingScripCount.intValue()<1000) { // (Change 10 to read from user subscription)
        		//Check weather this scrip already exist in the WL
        		q = entityManager.createNativeQuery("SELECT count(*) FROM watchlist_item wlItem WHERE wlItem.f_watchlist=? and wlItem.f_scrip=?");
            	q.setParameter( 1, wlId);
            	q.setParameter( 2, scripId);
            	BigInteger scripInWLCount = (BigInteger)q.getSingleResult();
            	System.out.println(" scripInWLCount="+scripInWLCount);
            	if (scripInWLCount.intValue()==0) {
            		q = entityManager.createNativeQuery("INSERT INTO watchlist_item (id, f_watchlist, f_scrip) VALUES ((select nextval('watchlist_item_id_seq')),?,?)");
                	q.setParameter( 1, wlId);
                	q.setParameter( 2, scripId);
                	q.executeUpdate();
            	} else {
            		//returnCode = ErrorCodeConstants.WATCHLIST_DUPLICATE_ITEMS;
            		// Item already exist in watchlist just ignore
            	}
        	} else {
        		returnCode = ErrorCodeConstants.WATCHLIST_NUMBER_OF_ALLOWED_WLITEM_CROSSED;
        	}
    	} else {
    		returnCode = ErrorCodeConstants.WATCHLIST_NO_RIGHTS;
    	}
    	return returnCode;
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )    
	public Integer createNewWatchList(Long scripId,String watchlistName, Long userId, String description) throws BusinessException {
    	System.out.println("createNewWatchList scripId="+scripId+" watchlistName="+watchlistName+" userId="+userId+" description="+description);
    	
    	Integer returnCode = ErrorCodeConstants.NO_ERRORS;
    	
    	// Add a check weather user can add new watchlist - Check for limit on number of watchlist
    	Query q = entityManager.createNativeQuery("SELECT count(*) FROM watchlist wl WHERE wl.f_owner = ?");
    	q.setParameter( 1, userId);
    	BigInteger userWLCount = (BigInteger)q.getSingleResult();
    	if (userWLCount.intValue()<1000) { // (Change 10 to read from user subscription)
    		q = entityManager.createNativeQuery("INSERT INTO watchlist (id, wl_name, f_owner, description) VALUES ((select nextval('watchlist_id_seq')),?,?,?)");
        	q.setParameter( 1, watchlistName);
        	q.setParameter( 2, userId);
        	q.setParameter( 3, description);        	
        	q.executeUpdate();
        	// Insert the scrip to newly created watchlist
        	q = entityManager.createNativeQuery("INSERT INTO watchlist_item (id, f_watchlist, f_scrip) VALUES ((select nextval('watchlist_item_id_seq')),(select max(id) from watchlist wl where wl.f_owner=?),?)");
        	q.setParameter( 1, userId);
        	q.setParameter( 2, scripId);
        	q.executeUpdate();
    	} else {
    		returnCode = ErrorCodeConstants.WATCHLIST_NUMBER_OF_ALLOWED_WL_CROSSED;
    	}
		return returnCode;
	}
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )   
    public List<WatchlistItem> getWatchlistItemByWlIdAndUserId(Long wlId, Long userId, boolean includeZeroHolidngItems) throws BusinessException {
    	return (new WatchlistDao(entityManager)).getWatchlistItemByWlIdAndUserId(wlId, userId, includeZeroHolidngItems);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )   
    public List<WatchlistTransactions> getWatchlistTransactionsByWlIdAndUserId(Long wlId, Long userId, String fromDate, String toDate) throws BusinessException {
    	return (new WatchlistDao(entityManager)).getWatchlistTransactionsByWlIdAndUserId(wlId, userId, fromDate, toDate);
    }  
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )   
    public WatchlistItem getWatchlistItemByWlIdAndScripId(Long watchListId, Long scripId) throws BusinessException {
    	WatchlistDao wlDao = new WatchlistDao(entityManager);
    	WatchlistItem wlItem = wlDao.getWatchlistItemByWlIdAndScripId(watchListId, scripId);
    	return wlItem;
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )   
    public String getMaxSettlementNumber(Long watchListId) throws BusinessException {
    	WatchlistDao wlDao = new WatchlistDao(entityManager);
    	return wlDao.getMaxSettlementNumber(watchListId);
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )   
    public void addTransaction(Long scripId, Long watchListId, boolean transactionType, boolean exchange, String transactionDate, Long quantity, Float rate, Float brokerage, String settlementNumber) throws BusinessException {
    	try {
    		WatchlistDao wlDao = new WatchlistDao(entityManager);
	    	WatchlistItem wlItem = wlDao.getWatchlistItemByWlIdAndScripId(watchListId, scripId);
	    	WatchlistTransactions newTrans = new WatchlistTransactions();
	    	newTrans.setBrokerage(brokerage);
	    	newTrans.setExchange(exchange);
	    	newTrans.setQuantity(quantity);
	    	newTrans.setRate(rate);
	    	newTrans.setSettlementNumber(settlementNumber);
	    	newTrans.setTransactionDate( (new SimpleDateFormat("dd/MM/yyyy")).parse(transactionDate));
	    	newTrans.setTransactionType(transactionType);
	    	newTrans.setWatchlistItem(wlItem);
	    	entityManager.persist(newTrans);
	    	entityManager.flush();
	    	entityManager.refresh(wlItem);
	    	// Add other logic to update Avg buying rate and SIH
	    	if (transactionType==false) { //Buy
	    		Float totalTurnOver = wlItem.getStockInHand().floatValue()*wlItem.getAverageBuyRate() + (quantity.floatValue()*rate + brokerage);
	    		Long totalQuantity = wlItem.getStockInHand() + quantity;
	    		wlItem.setAverageBuyRate(totalTurnOver/totalQuantity);
	    		wlItem.setStockInHand(totalQuantity);
	    	} else { //Sell
	    		Float totalTurnOver = wlItem.getStockInHand().floatValue()*wlItem.getAverageBuyRate() - (quantity.floatValue()*rate) + brokerage;
	    		Long totalQuantity = wlItem.getStockInHand() - quantity;
	    		if (totalQuantity.longValue()==0) {
		    		wlItem.setAverageBuyRate(0f);
		    		wlItem.setStockInHand(totalQuantity);
	    		} else {
	    			//wlItem.setAverageBuyRate(totalTurnOver/totalQuantity.floatValue()); // Commented so that it will retain original buy rate
		    		wlItem.setStockInHand(totalQuantity);
	    		}
	    	}
	    	wlItem.setLastUpdated(newTrans.getTransactionDate());
	    	entityManager.persist(wlItem);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )   
    public void deleteTransaction(Long selectedTransactionId) throws BusinessException {
    	System.out.println("!!!!! deleteTransaction selectedTransactionId="+selectedTransactionId);
    	//Save the watchlist id for rebuild
    	Query q = entityManager.createNativeQuery("select wlItem.id from watchlist_item as wlItem, watchlist_transaction as wlTrans where wlTrans.f_watchlist_item = wlItem.id and wlTrans.id = ?");
    	q.setParameter( 1, selectedTransactionId);
    	BigInteger wlItemWLId = (BigInteger)q.getSingleResult();
    	    	
    	q = entityManager.createNativeQuery("DELETE FROM watchlist_transaction WHERE id=?"); //Todo check weather WL belng to the logged user
    	q.setParameter( 1, selectedTransactionId);
    	q.executeUpdate();
    	
    	rebuildWatchlistItem(wlItemWLId.longValue());
    }
	
    @TransactionAttribute( TransactionAttributeType.REQUIRED )   
    public void rebuildWatchlistItem(Long selectedWLItemId) throws BusinessException {
    	System.out.println("!!!!! rebuildWatchlistItem selectedWLItemId="+selectedWLItemId);
    	WatchlistDao wlDao = new WatchlistDao(entityManager);
    	List<WatchlistTransactions> wlTransactions = wlDao.getWatchlistTransactionsByItemId(selectedWLItemId);
    	Float averageBuyRate = 0f;
    	Long stockInHand = 0L;
    	
    	for(int i=0;i<wlTransactions.size();i++) {
    		WatchlistTransactions aTrans = wlTransactions.get(i);
    		if (aTrans.getTransactionType()==false) { //Buy
	    		Float totalTurnOver = stockInHand.floatValue()*averageBuyRate + (aTrans.getQuantity().floatValue()*aTrans.getRate() + aTrans.getBrokerage());
	    		stockInHand = stockInHand + aTrans.getQuantity();
	    		averageBuyRate = totalTurnOver/stockInHand.floatValue();
	    	} else { //Sell
	    		Float totalTurnOver = stockInHand.floatValue()*averageBuyRate - (aTrans.getQuantity().floatValue()*aTrans.getRate()) + aTrans.getBrokerage();
	    		stockInHand = stockInHand - aTrans.getQuantity();
	    		// averageBuyRate = totalTurnOver/stockInHand.floatValue(); // Commented so that it will retain original buy rate
	    		
	    		if (stockInHand.longValue()==0) {
	    			averageBuyRate = 0f;
	    		}
	    	}
    	}
    	WatchlistItem wlItem = wlDao.getWatchlistItemByWlItemId(selectedWLItemId);
    	wlItem.setAverageBuyRate(averageBuyRate);
		wlItem.setStockInHand(stockInHand);
		entityManager.persist(wlItem);
    }
    
    @TransactionAttribute( TransactionAttributeType.REQUIRED )   
    public void deleteWatchlistItem(Long selectedWLItemId) throws BusinessException {
    	System.out.println("!!!!! deleteWatchlistItem selectedWLItemId="+selectedWLItemId);
    	
    	Query q = entityManager.createNativeQuery("DELETE FROM watchlist_item WHERE id=?"); //Todo check weather WL belng to the logged user
    	q.setParameter( 1, selectedWLItemId);
    	q.executeUpdate();
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<WatchlistItem> getWatchlistItemsByUserIdAndScripId(Long scripId, Long userId) throws BusinessException {
    	return (new WatchlistDao(entityManager)).getWatchlistItemsByUserIdAndScripId(scripId, userId);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Scrip> getScripsByWatchlistId(Long wlId, String historyDate) throws BusinessException {
    	return (new WatchlistDao(entityManager)).getScripsByWatchlistId(wlId, historyDate);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<WatchlistItem> getSIHByUserIdAndScripId(Long scripId, Long userId) throws BusinessException {
    	return (new WatchlistDao(entityManager)).getSIHByUserIdAndScripId(scripId, userId);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Watchlist> getWatchlistItemByWlIdAndScripIdForManual(Long userId, Long scripId) throws BusinessException {
    	return (new WatchlistDao(entityManager)).getWatchlistItemByWlIdAndScripIdForManual(userId, scripId);
    }
    
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Watchlist getWatchlisByWlIdAndUser(Long wlId, Long userId) throws BusinessException {
    	return (new WatchlistDao(entityManager)).getWatchlisByWlIdAndUser(wlId, userId);
    }
    
    public List<WLCriteriaMatchDto> matchScripToWLCriteria(Long scripId, Long wlId) throws BusinessException {
    	WatchlistDao wlDao = new WatchlistDao(entityManager);
    	String wlQuery = wlDao.getWatchlistQuery(wlId);
    	List<WLCriteriaMatchDto> retList = matchScripToWLCriteria(scripId, wlQuery, wlDao);
    	return retList;
    }

    private List<WLCriteriaMatchDto> matchScripToWLCriteria(Long scripId, String wlQuery, WatchlistDao wlDao) {
		System.out.println("In matchScripToWLCriteria scripId="+scripId+" wlQuery="+wlQuery);
		List<WLCriteriaMatchDto> criteriaList = new ArrayList<WLCriteriaMatchDto>();
		try {
			
			String afterWhere = "";
			boolean customQry = false;
			if (wlQuery.indexOf("where scr.id>0 AND (")>=0) {
				afterWhere = wlQuery.substring(wlQuery.indexOf("where scr.id>0 AND (") + 20).trim();
				customQry = true;
			} else {
				afterWhere = wlQuery.substring(wlQuery.indexOf("where") + 5).trim();
			}
			String currentCrtiteria = "";
			while(afterWhere.length()>0) {
				//System.out.println("afterWhere="+afterWhere);
				String nextWord = "";
				if ( afterWhere.indexOf(" ")>=0) {
					nextWord = afterWhere.substring(0, afterWhere.indexOf(" ")).trim();
				} else {
					nextWord = afterWhere;
				}
				
				if (nextWord.startsWith("(")) {
					int openBrackets = 1;
					int curPosition = 1;
					currentCrtiteria = currentCrtiteria + "(";
					while (openBrackets>0) {
						char aChar = afterWhere.charAt(curPosition);
						if (aChar=='(') openBrackets++;
						else if (aChar==')') openBrackets--;
						currentCrtiteria = currentCrtiteria + aChar;
						curPosition++;
					}
					criteriaList.add(new WLCriteriaMatchDto(currentCrtiteria));
					currentCrtiteria="";
					afterWhere = afterWhere.substring(curPosition).trim();
				}else if (nextWord.equalsIgnoreCase("AND") || nextWord.equalsIgnoreCase("OR") ) {
					String lastCriteria = criteriaList.size()>0?criteriaList.get(criteriaList.size()-1).getWlCriteria():null;
					if (criteriaList.size()>0 && !( lastCriteria.endsWith("AND") || lastCriteria.endsWith("OR"))) {
						
						criteriaList.set(criteriaList.size()-1, new WLCriteriaMatchDto(lastCriteria + currentCrtiteria + " " + nextWord));
					} else {
						criteriaList.add(new WLCriteriaMatchDto(currentCrtiteria + " " + nextWord));
					}
					currentCrtiteria="";	
					afterWhere = afterWhere.substring(nextWord.length()).trim();
				} else {
					currentCrtiteria = currentCrtiteria + " " + nextWord;
					afterWhere = afterWhere.substring(nextWord.length()).trim();
				}
			}
			if (currentCrtiteria.length()>0) criteriaList.add(new WLCriteriaMatchDto(currentCrtiteria));
			if (customQry) {
				String curStr = criteriaList.get(criteriaList.size()-1).getWlCriteria();
				curStr = curStr.substring(0,  curStr.length()-1);
				criteriaList.set(criteriaList.size()-1, new WLCriteriaMatchDto(curStr + " AND"));
			} else {
				String curStr = criteriaList.get(criteriaList.size()-1).getWlCriteria();
				criteriaList.set(criteriaList.size()-1, new WLCriteriaMatchDto(curStr + " AND"));
			}
			List<Boolean> criteriaFlag = new ArrayList<Boolean>(criteriaList.size());
			for(int i=0;i<criteriaList.size();i++) {
				WLCriteriaMatchDto aDto = criteriaList.get(i);
				Boolean hasMatched = wlDao.matchCriteria(scripId, aDto.getWlCriteria());
				String actualValue = wlDao.getActualValueForMatchCriteria(scripId, aDto.getWlCriteria());
				aDto.setMatched(hasMatched);
				aDto.setActualValue(actualValue);
				criteriaList.set(i, aDto);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace(System.out);
		}
		return criteriaList;
	}
    
}
  

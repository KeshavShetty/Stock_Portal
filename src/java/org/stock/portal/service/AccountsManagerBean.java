package org.stock.portal.service;



import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.dao.UserDao;
import org.stock.portal.domain.User;
import org.stock.portal.web.util.Constants;





@Stateless(name="AccountsManager", mappedName="org.stock.portal.service.AccountsManager")
public class AccountsManagerBean implements AccountsManager {
	@Resource 
	private SessionContext context;

	private static Logger log = Logger.getLogger(AccountsManager.class.getName());
	
	@PersistenceContext(unitName = Constants.ProjectConfig.PERSISTENCE_UNIT)
    private EntityManager entityManager;

//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
//	public AccountTransaction getLatesttransaction(Long ownerId) throws Exception {
//		AccountTransaction retData = null;
//		try {
//			Session session = HibernateUtils.getSession();
//			AccountsDAO accDao = new AccountsDAO(session);
//			retData = accDao.getLatesttransaction(ownerId);
//			HibernateUtils.closeSession();		
//		} catch(HibernateException e) {
//			log.error(e);
//			e.printStackTrace();
//			HibernateUtils.forceCloseSession();
//		 throw new EJBException(e);
//		}		
//		return retData;
//	}
//	
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void addtransaction(AccountTransaction newTransaction) throws Exception {
//	  try {
//			Session session = HibernateUtils.getSession();
//			UserDAO userDao = new UserDAO(session);
//			Long ownerId = newTransaction.getOwner().getId();
//			User owner = userDao.getUserById(ownerId);			
//			newTransaction.setOwner(owner);
//			session.saveOrUpdate(newTransaction);	
//			session.flush();
//		 	HibernateUtils.closeSession();		
//		} catch(HibernateException e) {
//			log.error(e);
//			e.printStackTrace();
//			HibernateUtils.forceCloseSession();
//		 throw new EJBException(e);
//		}
//	}
//	
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
//	public Map getAccountSummary(Long ownerId) throws Exception {
//		Map<String, Float> retMap = new HashMap<String, Float>();		
//	  try {
//			Session session = HibernateUtils.getSession();
//			AccountsDAO accDao = new AccountsDAO(session);
//			
//			Float totalDeposits = accDao.getTotalAmountOfTranscation(ownerId,1); // 1 for deposit
//			Float totalIncomes = accDao.getTotalAmountOfTranscation(ownerId,2); // 2 for income
//			Float totalWithdrawal = accDao.getTotalAmountOfTranscation(ownerId,3); // 3 for Withdrawl
//			Float totalExpenses = accDao.getTotalAmountOfTranscation(ownerId,4); // 4 for Expences
//			Float totalStockPurchase = accDao.getTotalAmountOfStockTranscation(ownerId,0); // 0 for prchase
//			Float totalStockSales = accDao.getTotalAmountOfStockTranscation(ownerId,1); // 1 for sales
//			Float totalBrokerage = accDao.getTotalBrokerageAmountOfStockTranscation(ownerId); 
//			retMap.put("TotalDeposits",totalDeposits);
//			retMap.put("TotalIncomes",totalIncomes);
//			retMap.put("TotalWithdrawal",totalWithdrawal);
//			retMap.put("TotalExpenses",totalExpenses);
//			retMap.put("TotalStockPurchase",totalStockPurchase);
//			retMap.put("TotalStockSales",totalStockSales);
//			retMap.put("TotalBrokerage",totalBrokerage);
//			
//		 	HibernateUtils.closeSession();		
//		} catch(HibernateException e) {
//			log.error(e);
//			e.printStackTrace();
//			HibernateUtils.forceCloseSession();
//		 throw new EJBException(e);
//		}
//		return retMap;
//	}
//	
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
//	public Map searchTransaction(Map params) throws Exception {
//		Map retData = new HashMap();
//		try {
//			Session session = HibernateUtils.getSession();
//			AccountsDAO accDao = new AccountsDAO(session);
//			List transList = accDao.getTransactionBySearchCriteria(params);
//			retData.put("AccountTransactionList",transList);
//			HibernateUtils.closeSession();		
//		} catch(HibernateException e) {
//			log.error(e);
//			e.printStackTrace();
//			HibernateUtils.forceCloseSession();
//		 throw new EJBException(e);
//		}		
//		return retData;
//	}
//	
	
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
    @SuppressWarnings(value="unchecked")
    public User authenticateAccount(String userName, String password)
        throws BusinessException {
    	log.debug(" Inside Method authenticateAccount (Usename :"+userName +
    			" Password :"+password +")");
    	User user = (new UserDao(entityManager))
    		.getAccountByCredentials(userName, password);
    	
    	return user;
    }
}
  

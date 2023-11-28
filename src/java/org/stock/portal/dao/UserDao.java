
package org.stock.portal.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.User;


/**
 * 
 * @author European Dynamics
 */
public class UserDao {
	
	/** Logger instance for the class. */
    protected static final Logger log = Logger.getLogger(UserDao.class.getName());
    		
	/** Session EJB EntityManager - Injected by constructor to DAO. */
	private EntityManager entityManager = null;
	
	/**
	 * 
	 * @param eManager
	 */
	public UserDao(final EntityManager eManager) {
		this.entityManager = eManager;
	}
	
	/**
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @return
	 * 
	 * @throws BusinessLayerException
	 */
	public User getAccountByCredentials(final String username, final String password)
		throws BusinessException {
		
		log.debug(" DAO-HEADER: getAccountByCredentials(String username: "
				+ username + ", String password: " + password + ") ");
		
		User userAccount = null;
		
		try {
			userAccount = (User)
				this.entityManager.createNamedQuery("User.getAccountByCredentials")
					.setParameter("pUsername", username)
					.setParameter("pPassword", password).getSingleResult();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
		}
		if (userAccount != null) {
			log.debug(" -- Account : id = " + userAccount.getId());
		}
		return userAccount;
	}
	
	public User getAccountById(Long userId) throws BusinessException {	
		log.debug(" UserDAO: getAccountById() accountId=" + userId );
	
		User userAccount = null;
	
		try {
			userAccount = (User)
				this.entityManager.createNamedQuery("User.getAccountById")
					.setParameter("userId", userId).getSingleResult();
			
		} catch (NoResultException e) {
			log.debug(" -- HERE : No account found with these credentials ");
		}
		if (userAccount != null) {
			log.debug(" -- Account : id = " + userAccount.getId());
		}
		return userAccount;
	}
	
}

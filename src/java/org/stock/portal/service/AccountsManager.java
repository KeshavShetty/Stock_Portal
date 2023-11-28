package org.stock.portal.service;


import javax.ejb.Local;

import org.stock.portal.common.exception.BusinessException;
import org.stock.portal.domain.User;

@Local
public interface AccountsManager {
//	public AccountTransaction getLatesttransaction(Long ownerId) throws Exception ;
//	public void addtransaction(AccountTransaction newTransaction) throws Exception ;	
//	public Map getAccountSummary(Long ownerId) throws Exception ;
//	public Map searchTransaction(Map params) throws Exception ;
	
	public User authenticateAccount(String userName, String password)
    throws BusinessException ;	
}
  

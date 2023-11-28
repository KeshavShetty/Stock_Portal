package org.stock.portal.web.action;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.stock.portal.common.StringUtil;
import org.stock.portal.domain.User;
import org.stock.portal.service.AccountsManager;
import org.stock.portal.web.annotation.InjectEJB;
import org.stock.portal.web.util.Constants;
import org.stock.portal.common.exception.BusinessException;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;


public class LoginAction extends BaseAction implements SessionAware {

	Logger log = Logger.getLogger(LoginAction.class.getName());
    
    private static final long serialVersionUID = 4205166422526662903L;
	
    private String username;
    private String password;
   
    /** The user accessor bean. */
    @InjectEJB (name ="AccountsManager")
    AccountsManager accountManager;
   
    public LoginAction() {
        super();
    }

    public String execute() {
    	log.debug("--->Inside LoginAction [JR]");
        // TODO: Benchmark this call - if it is not too expensive we should move it to the BaseAction.
        //Map session = ActionContext.getContext().getSession();
        String retVal = INPUT;
        User account;
        try {
        	log.debug("UserName : "+getUsername()); 
        	log.debug("Password : "+getPassword());
        	log.debug("Password Encrypted : "+StringUtil
                    .md5HashSalted(getPassword()));
        	
        	account = accountManager.authenticateAccount(getUsername(), StringUtil
                    .md5HashSalted(getPassword()));           
            if (account != null) {
            	addActionMessage("Logged In as "+account.getFirstName()+" "+account.getLastName());
            	this.session.put(Constants.LOGGED_IN_USER, account);
            	retVal = SUCCESS;
                
            } else {
                log.debug(" -- incorrect credentials = > ERROR ");
                addActionError(super.getText("error.login.incorrect.credentials.parametric"));
            }
            
        } catch(BusinessException e){
        	log.error(e); 
        } catch (Exception e) { 
        	log.error(e); 
            addActionError(super.getText("error.login.generic.application.error"));
        } 
        return retVal;
    }

    

    public String getUsername() {
        return username;
    }

    @RequiredStringValidator(key = "error.form.field.username.required")
    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    @RequiredStringValidator(key = "error.form.field.password.required")
    public void setPassword(String password) {
        this.password = password;
    }


}

package org.stock.portal.web.util.security;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * abstract class that needs to be extended in order to provide
 * an authorisation mechanism ( not an interface in order to be used in
 * the SecurityRealm annotation )
 * 
 * @author Stock Portal
 *
 */
public abstract class Realm {

    protected String[] parameters;
    protected ActionInvocation actionInvocation;
    
    /**
     * standard realm works only with the ActionInvocation and the parameters
     * @param parameters
     */
    public Realm( ActionInvocation actionInvocation, String[] parameters ){
        this.actionInvocation = actionInvocation;
        this.parameters = parameters;
    }
    
    /**
     * return true if authorized
     * @param params
     * @return
     */
    public abstract boolean isAuthorized();
}

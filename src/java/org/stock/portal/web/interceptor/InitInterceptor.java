package org.stock.portal.web.interceptor;

import java.util.Map;

import org.stock.portal.common.SPConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * ONLY FOR <code>UserAware</code> implementing classes
 * 
 * 
 * This interceptor handles generic initialisation stuff
 * 
 */
public class InitInterceptor implements Interceptor {

    private static final long serialVersionUID = -4763080892504588785L;

    public void destroy() {
    }

    public void init() {
    }

    public String intercept( ActionInvocation actionInvocation ) throws Exception {
    	Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
        //add action messages listener
        actionInvocation.addPreResultListener( new MessagesPreResultListener() );
        
        //check if lang is in session, ele set en
        if( !session.containsKey( SPConstants.ACTIVE_LOCALE ) ){
        	session.put( SPConstants.ACTIVE_LOCALE, "en" );
        }
        
        
        //check if the noscript is part of the request        
        Map<String, Object> params = actionInvocation.getInvocationContext().getParameters();
        if( params.containsKey( SPConstants.NO_SCRIPT ) ){
            try{
            
            String noscript =  ((String[])params.get(SPConstants.NO_SCRIPT ))[0];
            session.put( SPConstants.NO_SCRIPT, noscript);
            }catch( Exception ex ){
                //do nothing
                ex.printStackTrace();
            }
        }                
        //proceed to the security checks ...
        return actionInvocation.invoke();
    }

}

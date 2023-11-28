package org.stock.portal.web.interceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import org.stock.portal.domain.User;
import org.stock.portal.web.annotation.Secure;
import org.stock.portal.web.annotation.SecurityRealm;
import org.stock.portal.web.util.security.Realm;
import org.stock.portal.common.SPConstants;
import org.stock.portal.common.UserUtil;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.AnnotationUtils;

/**
 * ONLY FOR <code>UserAware</code> implementing classes
 * 
 * 
 * This interceptor provides authentication for the secure actions of the application.
 * It does two things.  First, it checks the session scope map to see if there's user 
 * object present, which indicates that the current user is already logged in.  If this
 * object is not present, the interceptor alters the workflow of the request by returning 
 * a login control string that causes the request to forward to the login page.
 * 
 * If the user object is present in the session map, then the interceptor injects the user
 * object into the action by calling the setUser method, and then allows the processing of 
 * the request to continue.  
 */
public class SecureInterceptor implements Interceptor {

    private static final long serialVersionUID = 6017159731989352916L;
    
    private String domain;

    public void destroy() {
    }

    public void init() {
    }

    public String intercept( ActionInvocation actionInvocation ) throws Exception {  
        
        //set the domain if the action is PackageAware
        handleDomain( actionInvocation );
        return handleSecurity( actionInvocation );
    }

    private String handleDomain(ActionInvocation actionInvocation) {
        String packageName= null;
        Action action = ( Action ) actionInvocation.getAction();

/*        if( action instanceof PackageAware ){
            packageName = actionInvocation.getProxy().getConfig().getPackageName();
            ((PackageAware) action).setActionPackage( packageName );
        }
*/        return packageName;
    }

    private String handleSecurity( ActionInvocation actionInvocation ) throws Exception {
        Action action = ( Action ) actionInvocation.getAction();
        //check if there is a user in the session
        Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
        User loggedInUser = (User) session.get( SPConstants.LOGGED_IN_USER );        

        //if the action is user agnostic ... we let it execute ( Unless someone is logged in with the wrong domain )
        if( !(action instanceof UserAware) ){
            if( loggedInUser != null ){
                return handleUserPresent( loggedInUser, actionInvocation );
            }else{
                return actionInvocation.invoke();
            }
        }else{
            //set the user to the action
            ((UserAware)action).setLoggedInUser( loggedInUser );
            //if the user is missing we jump to the login page ( default is front office ... )
            if (loggedInUser == null) {
                return handleUserMissing();
            }else{
                return securityCheck( actionInvocation );                        
            }
        }
    }

    /*
     * Helpers
     */
   
    /**
     * if a user is present check his domain against the interceptor's. 
     * If wrong domain return to global result ( access denied )
     * 
     * @param qualifiedDomain
     * @param loggedInUser
     * @param actionInvocation
     * @return
     */
    private String handleUserPresent( User loggedInUser, ActionInvocation actionInvocation ) throws Exception{
    	
        if( UserUtil.canAccessSite(loggedInUser,this.domain) ){
            return actionInvocation.invoke();
        }else{
            addError( actionInvocation );
            return "home";
        } 
    	//return actionInvocation.invoke();
    }    
    
    private String handleUserMissing(){
        String result = "login";                 
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader(MessagesPreResultListener.SESSION_EXPIRED_RESPONSE_HEADER, "true");
        return result;
    }    

    /**
     * loop through all secure annotations and run isAuthorized ...
     * if there are no annotations on the method level, we use the class level security annotations
     * 
     * @param qualifiedDomain
     * @param loggedInUser
     * @param actionInvocation
     * @return
     */
    @SuppressWarnings("unchecked")
    private String securityCheck( ActionInvocation actionInvocation ) throws Exception{
        Action action = ( Action ) actionInvocation.getAction();
        Method method = getActionMethod(action.getClass(), actionInvocation.getProxy().getMethod());        

        boolean result = false;
               
        Secure secureAnnotation = null;
        
        Collection<Method> annotatedMethods = AnnotationUtils.getAnnotatedMethods(action.getClass(), Secure.class);
        if( annotatedMethods.contains(method) ){
            secureAnnotation = method.getAnnotation(Secure.class);
        }else{
            secureAnnotation = action.getClass().getAnnotation(Secure.class);
        }
        
        //secureAnnotation = action.getClass().getAnnotation(Secure.class);
        if( secureAnnotation != null ){
            SecurityRealm[] realms = secureAnnotation.value();
            for( int i=0; i<realms.length; i++ ){
                SecurityRealm realm = realms[i];
                Class<? extends Realm> realmClass = (Class)realm.type();
                String[] realmParams = realm.parameters();
                //realmClass.getC
                Constructor<?> c = realmClass.getConstructor( ActionInvocation.class, String[].class );
               if( c != null ){
                   Realm theRealm = realmClass.cast( c.newInstance(actionInvocation,realmParams ) );
                   result |= theRealm.isAuthorized();
               }
            }
        }else{
            //if there is no rule specified, allow ... ( just logged in is enough )
            result = true;
        }
                
        if( result ){
            return actionInvocation.invoke();
        }else{
            addError( actionInvocation );
            return "home";
        }         
    }
    
    /*
     * Helpers
     */
    
    private void addError(ActionInvocation actionInvocation) {
        Action action = ( Action ) actionInvocation.getAction();
        if( action instanceof ActionSupport ){
            ((ActionSupport)action).addActionError("error.access.denied");
        }
        
    }

    /**
     * try to get the action method for this action
     * @param actionClass
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     */
    protected Method getActionMethod(Class<?> actionClass, String methodName) throws NoSuchMethodException {
        Method method;
            method = actionClass.getMethod(methodName, new Class[0]);
        return method;
    }    

    /*
     * Getters / Setters
     */

    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }	

}
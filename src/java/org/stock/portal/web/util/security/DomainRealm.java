package org.stock.portal.web.util.security;

import java.util.Map;

import org.stock.portal.common.SPConstants;
import org.stock.portal.common.UserUtil;
import org.stock.portal.domain.User;

import com.opensymphony.xwork2.ActionInvocation;

public class DomainRealm extends Realm {
    
    

    /**
     * just call super
     * @param actionInvocation
     * @param parameters
     */
    public DomainRealm(ActionInvocation actionInvocation, String[] parameters) {
        super(actionInvocation, parameters);
    }

    /**
     * check user domain against the specified domain ...
     */
    @Override
    public boolean isAuthorized() {
        boolean result = false;
        
        if( parameters == null || parameters.length != 1 ){            
            result = true;
        }else{
            Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
            User loggedInUser = (User) session.get( SPConstants.LOGGED_IN_USER ); 
            String requiredDomain = parameters[0];                       
            result = UserUtil.canAccessSite(loggedInUser,requiredDomain );
        }
        return result;
    }      

}

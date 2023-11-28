package org.stock.portal.common;

import java.util.Set;

import org.stock.portal.domain.User;

public class UserUtil {
    
    /**
     * Check if the user has a role that corresponds to the given site (domain)
     * @param u
     * @param site
     * @return true / false
     */
    public static boolean canAccessSite( User u, String site ){
        boolean result = false;
        if( u != null ){
        	u.getUserType().equals(site);
        	result = true;
        }
        /*if( u != null ){
            Set<Role> userRoles = u.getRoles();
            for( Role r : userRoles ){
                if( site.equals(r.getSite()) ){
                    result = true;
                    break;
                }
            }
        
        }*/
        return result;
    }
    
    /**
     * Check if any of the role names for this user matches the given role name
     * @param u
     * @param role
     * @return true / false
     */
    public static boolean hasRole( User u, String role ){
        boolean result = false;
        if( u != null ){
        	u.getUserType().equals(role);
        	result = true;
        }
        return result;
    }    
    
}

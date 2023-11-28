
package org.stock.portal.web.interceptor;

import java.lang.reflect.Field;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.stock.portal.web.annotation.InjectEJB;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Looks for any fields annotated with the InjectEJB annotation and injects the
 * given EJB into that field.
 */
public class InjectEJBInterceptor extends AbstractInterceptor implements Interceptor {

    private static final long serialVersionUID = -2078783172069592011L;

    private final static String LOCAL = "/local";
    private final static String REMOTE = "/remote";
    
    /**
     * default application name (ex. portal in bean name 'portal/ejbname/remote' )
     */
    private String appname;

    public InjectEJBInterceptor() {
        super();
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {

        Object action = actionInvocation.getAction();

        for (Field f : action.getClass().getDeclaredFields()) {

            if (f.isAnnotationPresent(InjectEJB.class)) {
                this.injectEJB(action, f);
            }
        }		

        return actionInvocation.invoke();
    }

    private void injectEJB(Object action, Field f) throws Exception {

        InjectEJB annotation = f.getAnnotation(InjectEJB.class);
        StringBuilder serviceName = new StringBuilder("java:global/");
        serviceName.append(this.appname).append("/"+this.appname+"-service/");
        serviceName.append(annotation.name()+"!");
        serviceName.append(f.getType().getName());
        System.out.println(" serviceName= "+serviceName);
        // try to access the service from the (global) JNDI
        Object service = null;
        InitialContext ic = new InitialContext();
        try {
            service = ic.lookup(serviceName.toString());
        }
        catch (NamingException ex) {
            //if the annotation appname is empty, use the appname specified in the struts.xml
            //default to ""
            String applicationName = "";
            if( !(annotation.appname() == null || "".equals( annotation.appname())) ){
                applicationName = annotation.appname();
            }else if( !( this.appname == null || "".equals( this.appname)) ){
                applicationName = this.appname;
            }
            if( !"".equals(applicationName) ){
                serviceName.insert(0, applicationName + "/");
                serviceName.append("-"+f.getType().getName());
            }
            service = ic.lookup(serviceName.toString());
        }
        finally {
            if (service != null) {
                //change accessibility to set the value ...
                boolean wasAccessible = f.isAccessible();
                f.setAccessible(true);
                f.set(action, service);
                //reset accessibility
                f.setAccessible(wasAccessible);
            }
        }
    }

    /**
     * @return the appname
     */
    public String getAppname() {
        return appname;
    }

    /**
     * @param appname the appname to set
     */
    public void setAppname(String appname) {
        this.appname = appname;
    }

}

package org.stock.portal.common;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;


import org.apache.log4j.Logger;
import org.stock.portal.web.util.Constants;


/**
 * @author Stock Portal
 *
 */
public class ServiceLocator {
	
	/** ServiceLocator logger. */
	private static org.apache.log4j.Logger log = Logger.getLogger(ServiceLocator.class.getName());
	
	private static ServiceLocator serviceLocator;
    
	/** Mapping of EJB 3.0 business interfaces to Proxies. */
	private Map<Class, Object> sessionFacades;
	
	/**
	 * Private constructor - Denotes singleton.
	 */
	private ServiceLocator() {
		sessionFacades = new HashMap<Class, Object>();
	}
	
	/**
	 * Instatiates ServiceLocator singleton.
	 * 
	 * @return <code>ServiceLocator</code>
	 */
	public static synchronized ServiceLocator getInstance() {
		
        if (serviceLocator == null) { 
            serviceLocator = new ServiceLocator();
        }
        return serviceLocator;
    }
	
	/**
	 * 
	 * @param ejbInterface
	 * 
	 * @return
	 * 
	 * @throws BusinessLayerException
	 */
	public Object getServiceFacade(final Class ejbInterface) {
		return getSessionFacade(ejbInterface);
	}
	
	/**
	 * 
	 * @param beanInterface
	 * 
	 * @return
	 * 
	 * @throws BusinessLayerException
	 */
	private Object getSessionFacade(final Class beanInterface) {
        
        Object sessionFacade = null;
        try {
            sessionFacade = sessionFacades.get(beanInterface);
            boolean wasCached = true;
            
            if (sessionFacade == null) {
                synchronized (sessionFacades) {
                    sessionFacade = sessionFacades.get(beanInterface);
                    if (sessionFacade == null) {
                        wasCached = false;
                        
                        
                        StringBuilder serviceName = new StringBuilder("java:global");
                        serviceName.append(Constants.ProjectConfig.INJECTION_APNAME_PREFIX).append(Constants.ProjectConfig.INJECTION_APNAME_PREFIX+"-service/");
                        serviceName.append(beanInterface.getSimpleName()+"!");
                        serviceName.append(beanInterface.getName());
                       
                        log.debug("nameWithPrefix="+serviceName);
                        try {
                        	InitialContext iContext = new InitialContext();
                            log.debug(" JNDI-LOOKUP (1): " + serviceName);
                            sessionFacade = iContext.lookup(serviceName.toString());
                            iContext.close();
                        } catch (NamingException nException) {
                        	log.error("-------------------------------------nException");
                        	nException.printStackTrace();
                            log.debug(" --> Warning: JNDI_LOOKUP with name "
                            		+ serviceName + " failed ! ");
                            try {
                            	InitialContext iContext = new InitialContext();
                            	log.debug(" JNDI-LOOKUP (2): " + serviceName);
                                sessionFacade = iContext.lookup(serviceName.toString());
                                iContext.close();
                            } catch (NamingException ex2) {
                            	log.error("-------------------------------------ex2");
                            	//ex2.printStackTrace();
                            	log.error(ex2);
                            	log.debug(" --> Warning: JNDI_LOOKUP with name "
                                		+ serviceName + " failed ! ");
                                throw new RuntimeException(" Could not lookup : "
                                		+ beanInterface.getSimpleName()
                                		+ ". NamingException - Message: "
                                		+ ex2.getMessage());
                            }
                        }
                        sessionFacades.put(beanInterface, sessionFacade);
                    }
                }
            }

            if (!wasCached) {
                log.debug(" LOOKUP DONE: sessionFacade is: " + sessionFacade);
            }

        } catch (Exception e) {
        	log.debug("-------------------------------------e-");
        	//e.printStackTrace();
        	log.error(e);
        	log.debug(" --> Generic ServiceLocator exception : ");
            /* log.error(e); */
            throw new RuntimeException(e.getMessage(), e);
        }
        return sessionFacade;
    }
}


package org.stock.portal.hibernate;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Basic Hibernate helper class, handles SessionFactory, Session.
 * <p>
 * Uses a static initializer for the initial SessionFactory and bounds the
 * SessionFactory to JNDI if configured (in hibernate.cfg.xml).</p>
 * <p>
 * Creates and holds Session(s) in thread local variables.</p>
 * <p>
 * Transactions are not managed since this class is created to be in a CMT
 * environment.</p>
 *
 * @author kshe
 */
public class HibernateUtils {

	private static Logger log = Logger.getLogger(HibernateUtils.class.getName());

	private static Configuration configuration;
	
	private static final ThreadLocal threadSession = new ThreadLocal();
	private static final ThreadLocal threadInterceptor = new ThreadLocal();
	
	/* This counter keeps track how many clients have requested a Session
	 * object in the current thread. When the counter is zero and the
	 * closeSession() is called then the Session is closed.  
	 */
	private static final ThreadLocal threadCounter = new ThreadLocal() {
         protected synchronized Object initialValue() {
             return new Integer(0);
         }
     };
	
	/**
	 * The JNDI name of the Hibernate <code>SessionFactory</code>.
	 */
	public static final String HIBERNATE_JNDI_NAME;

	// Create the initial SessionFactory from the default configuration files
	static {
		try {
			configuration = new Configuration();
			configuration.configure().buildSessionFactory();
			HIBERNATE_JNDI_NAME = configuration.getProperty("hibernate.session_factory_name");
		} catch (Throwable ex) {
			// We have to catch Throwable, otherwise we will miss
			// NoClassDefFoundError and other subclasses of Error
			log.error("Building SessionFactory failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Returns the SessionFactory used for this static class.
	 *
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() throws NamingException {
		SessionFactory sessions = null;
		Context ctx = new InitialContext();
		sessions = (SessionFactory)ctx.lookup(HIBERNATE_JNDI_NAME);
		
		return sessions;
	}

	/**
	 * Returns the Hibernate configuration used to build the HibernateSession.
	 *
	 * @return Configuration
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}

	

	/**
	 * Retrieves the current Session local to the thread.
	 * <p>
	 * If no Session is open, opens a new Session for the running thread.</p>
	 *
	 * @return Session
	 */
	public static Session getSession() throws HibernateException {
		Session s = (Session) threadSession.get();
		try {
			if (s == null) {
				log.debug("Opening new Session for this thread.");
				if (getInterceptor() != null) {
					log.debug("Using interceptor: " + getInterceptor().getClass());
					s = getSessionFactory().openSession(getInterceptor());
				} else {
					s = getSessionFactory().openSession();
				}
				threadSession.set(s);
			}
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
		// increment counter
		int counter = ((Integer) threadCounter.get()).intValue();
		counter++;
		threadCounter.set(new Integer(counter));
		log.debug("getSession(): session counter has value = " + counter);
		return s;
	}

	/**
	 * Closes the Session local to the thread.
	 */
	public static void closeSession() throws HibernateException {
			Session s = (Session) threadSession.get();
			if (s != null) {
				int counter = ((Integer) threadCounter.get()).intValue();
				--counter;
				threadCounter.set(new Integer(counter));
				log.debug("closeSession(): session counter has value = " + counter);
				if (counter == 0) {
					threadSession.set(null);
					if (s.isOpen()) {
						log.debug("Closing Session of this thread.");
						s.flush();
						s.close();
					}
				}
			}
	}
	
	/**
	 * This method should be called every time an exception occurs
	 * and and the user is unable to recover.
	 * <p>
	 * This method closes the <code>Session</code> and removes the
	 * <code>Session</code> from the <code>ThreadLocal</code>.</p>
	 * <p>
	 * The objects that were associated with the <code>Session</code>
	 * must be discarded since their state is not guaranted to be
	 * correct.</p>  
	 *
	 */
	public static void forceCloseSession() {
	    Session s = (Session) threadSession.get();
	    if (s != null) {
	        log.warn("HibernateUtils.forceCloseSession() - User is discarding the current thread local session.");
	        log.warn("HibernateUtils.forceCloseSession() - This usually happen when recovering from a fatal error.");
	        try { s.close(); }
	        catch (HibernateException ignore) { }
	        threadCounter.set(new Integer(0));
	        threadSession.set(null);
	    }
	}

//	/**
//	 * Reconnects a Hibernate Session to the current Thread.
//	 *
//	 * @param session The Hibernate Session to be reconnected.
//	 */
//	public static void reconnect(Session session) throws HibernateException {
//			session.reconnect();
//			threadSession.set(session);
//	}
//
//	/**
//	 * Disconnect and return Session from current Thread.
//	 *
//	 * @return Session the disconnected Session
//	 */
//	public static Session disconnectSession() throws HibernateException {
//		Session session = getSession();
//			threadSession.set(null);
//			if (session.isConnected() && session.isOpen())
//				session.disconnect();
//		
//		return session;
//	}

	/**
	 * Register a Hibernate interceptor with the current thread.
	 * <p>
	 * Every Session opened is opened with this interceptor after
	 * registration. Has no effect if the current Session of the
	 * thread is already open, effective on next close()/getSession().
	 */
	public static void registerInterceptor(Interceptor interceptor) {
		threadInterceptor.set(interceptor);
	}

	private static Interceptor getInterceptor() {
		Interceptor interceptor =
			(Interceptor) threadInterceptor.get();
		return interceptor;
	}

}



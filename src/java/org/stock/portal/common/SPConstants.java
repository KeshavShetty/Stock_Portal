package org.stock.portal.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SPConstants {

    /**
     * application name
     */
    public static final String APP_NAME = "portal";  

    /**
     * default persistence unit name
     */
    public static final String PORTAL_UNIT = "portalPU";  
    
    /**
     * EMail JMX Queue name
     */
    public static final String EMAIL_QUEUE_NAME = "queue/portal_mailsQueue";
    
    /**
     * DataProcess JMX Queue name
     */
    public static final String DATA_PROCESS_QUEUE_NAME = "queue/portal_dataProcessQueue";

    /**
     * this key will hold the user object in the session
     */
    public static final String LOGGED_IN_USER = "loggedInUser";
    /**
     * this key will hold the locale object in the session
     */
    public static final String ACTIVE_LOCALE = "activeLocale";   
    
    /**
     * this key will hold a value for the presense ( or not ) of javascript
     * actualy its whether the user want's to proceed with noscript or not.
     */
    public static final String NO_SCRIPT = "noScript";        
    
    /**
     * Web packages
     */
    public static final String PKG_PORTAL = "portal";
   
    /**
     * Web Namespaces
     */
    public static final String NS_PORTAL = "/" + PKG_PORTAL;  
    
    
    
    public static final String ACCESS_DENIED = "access-denied";
    
    public static final String DEFAULT_LOGIN = "home";
    
    public static final String PORTAL_HOME = "portal-home";
    
    /*
     * ROLES
     */
    public static final String ROLE_ADMIN = "role.admin";
    public static final String ROLE_POWER_USER = "role.super.user";
    public static final String ROLE_SIMPLE_USER = "role.simple.user";
    public static final String ROLE_WEB_USER = "role.web.user";   
    
    
    /*
     * EJB LOCAL
     */
    public static final String LOCAL = "/local";
    
    /*
     * REGISTRATION STATUES
     */
    public static final String STATUS_PENDING = "prompt.status.pending";

    public static final String STATUS_ACTIVE = "prompt.status.active";

    public static final String STATUS_REJECTED = "prompt.status.rejected";   

    public static final String SALT = "4958fkgjfkgj4";
    
    /*
     * DATE FORMATS
     */
    public final static DateFormat SPCORE_SHORTYEAR_DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");
    public final static DateFormat SPCORE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public final static DateFormat SPCORE_MONTH_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public final static DateFormat SPCORE_DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /** The SPcore database date format. */
    public static final DateFormat SPCORE_DB_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    public final static DateFormat SPCORE_DB_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public final static DateFormat SPORE_DATE_TIME_NOSECS_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public final static DateFormat SPORE_DATE_TIME_NOSECS_ZONE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm z");

    public final static String SPCORE_DATE_TIME_NOSECS_ZONE_DASHED_PATTERN = "dd-MM-yyyy HH:mm z";
    
    /*
     * EMAIL TEMPLATING
     */

    /** Keys for email templates. */
    public static final String EMAIL_TEMPLATES_FILE = "emailTemplates_";
    
    /** Keys for default email templates. */
    public static final String DEFAULT_EMAIL_TEMPLATES_FILE = "emailTemplates";
    
    public static final String DEFAULT_EMAIL_TEMPLATE = "defaultEmailTemplate";
    
    /** Keys for action parameter. */
    public static final String ACTION_PARAM = "action";
    
    /** Keys for date created. */
    public static final String DATE_CREATED_PARAM = "DATE_CREATED";    
    
    /** Keys for server url  parameter. */
    public static final String SERVER_URL = "SERVER_URL";    
    
    public static final Integer TECHNICAL_AUTOSCAN = new Integer(2);
    public static final Integer CANDLE_AUTOSCAN = new Integer(3);
    
    public static String escapeQuote(String inpStr) {		
		String retStr = inpStr.replace("'", "''");
		return retStr;
	}
}
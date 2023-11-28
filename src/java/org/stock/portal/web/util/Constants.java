package org.stock.portal.web.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author kshe
 */
public class Constants {

    /*
     *Class names
     */
    public static final String SESSION_USER_KEY = "UserKey";
    /**
	 * Extra 
	 * 
	 * @author Eu Dynamics
	 *
	 */
	public static class ProjectConfig {
		
		public static final String PERSISTENCE_UNIT = "portalPU";
		
		public static final String INJECTION_APNAME_PREFIX = "/portal";
		public static final String INJECTION_APNAME_SUFFIX = "portal/";
		
		public static final String INJECTION_PREFIX = "portal/";
        public static final String INJECTION_SUFFIX = "/remote";
        
        public static final String PREFIX = "portal/";
        public static final String SUFFIX = "/remote";
	}
	public final static DateFormat PORTAL_DATE_FORMAT             			= new SimpleDateFormat("dd/MM/yyyy");
    public final static DateFormat PORTAL_DATE_FORMAT_MM_DD_YYYY   			= new SimpleDateFormat("MM/dd/yyyy");
    public final static DateFormat PORTAL_DATE_TIME_FORMAT        			= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public final static DateFormat PORTAL_DATE_TIME_NOSECS_FORMAT 			= new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public final static DateFormat PORTAL_DATE_TIME_NOSECS_ZONE_FORMAT 		= new SimpleDateFormat("dd/MM/yyyy HH:mm z");
    public final static DateFormat PORTAL_DATE_FORMAT_YEAR_FORMAT 			= new SimpleDateFormat("yyyy-MM-dd");

    public final static String     PORTAL_DATE_TIME_NOSECS_ZONE_DASHED_PATTERN = "dd-MM-yyyy HH:mm z";

    public final static DecimalFormat CURRENCY_FORMAT             			= new DecimalFormat("#,###,#####0.00");

    /**
     * Keys for email service
     */
    public static final String EMAIL_SERVER_HOST_NAME = "mail.smtp.host";
    public static final String EMAIL_TRANSPORT_PROTOCOL_NAME = "mail.transport.protocol";
    public static final String EMAIL_TRANSPORT_PROTOCOL_VALUE = "SMTP";
    public static final String EMAIL_SERVICE_AUTHENTICATION = "mail.smtp.auth";
    public static final String EMAIL_SERVICE_AUTHENTICATION_VALUE = "false";
    public static final String EMAIL_PLAIN_TEXT_CONTENT_TYPE = "text/plain;charset=\"UTF-8\"";
    public static final String EMAIL_HEADER = "X-Mailer";
    public static final String EMAIL_HEADER_VALUE = "JavaMailer";

    public static final int ANALYSIS_TYPE_TECHNICAL = 1;
    public static final int ANALYSIS_TYPE_CANDLE = 2;
    public static final int ANALYSIS_TYPE_HIGHLOW = 3;
    
    public static final String FONT_COLOR_BUY = "red";
    public static final String FONT_COLOR_SELL = "green";
    
    /**
     * this key will hold the user object in the session
     */
    public static final String LOGGED_IN_USER = "loggedInUser";
    public static final String LAST_ACCESSED_SCRIP = "lastAccessedScrip";
    
    public static final String NOW_HANDLER = "nowClientHandler";
	
}

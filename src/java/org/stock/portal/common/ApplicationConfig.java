package org.stock.portal.common;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * A set of application properties that are initialized from the application's property file
 * (portal.properties)
 * 
 * @author Stock Portal
 *
 */
public class ApplicationConfig {
    
        
    protected final static Logger log = Logger.getLogger(ApplicationConfig.class.getName());
    
    protected static Properties APPLICATION_PROPS = initProps("portal.properties");
    
    public static String SERVER_DIR = APPLICATION_PROPS.getProperty("server.dir");
    public static String SERVER_TMP_DIR = APPLICATION_PROPS.getProperty("server.tmpDeploy.dir");
    
    public static String DS_JDBC_URL = APPLICATION_PROPS.getProperty("ds.jdbc.url");
    public static String DS_JDBC_DRIVER = APPLICATION_PROPS.getProperty("ds.jdbc.driver");
    public static String DS_JDBC_USERNAME = APPLICATION_PROPS.getProperty("ds.jdbc.user");
    public static String DS_JDBC_PASSWORD = APPLICATION_PROPS.getProperty("ds.jdbc.pass");
    
    public static String EXCHANGE_DOWNLOAD_FILE_LOCATION = APPLICATION_PROPS.getProperty("basefile.location.for.downloaded.from.exchange");
    
    public static String ICICI_LOGIN = APPLICATION_PROPS.getProperty("icici.login");
    public static String ICICI_PWD = APPLICATION_PROPS.getProperty("icici.pwd");
    public static String ICICI_PAN = APPLICATION_PROPS.getProperty("icici.pan");
    public static String ICICI_ACCOUNT_NUMBER = APPLICATION_PROPS.getProperty("icici.account.number");
    
            
    protected static Properties initProps(String propertyName) {
        Properties retVal = null;
        try {
            URL propertyFileUrl = ApplicationConfig.class.getResource("/" + propertyName);
            InputStream in = propertyFileUrl.openStream();
            retVal = new Properties();
            retVal.load(in);
            in.close();
        }catch (Exception e) {
            log.error(e);
            retVal = null;
        }
        return retVal;
    }
   
}

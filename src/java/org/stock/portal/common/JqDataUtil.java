package org.stock.portal.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * [No description provided for this class].
 *
 */
public class JqDataUtil {
        
    /** Logger for the class. */
    private static final Logger LOG = Logger.getLogger(JqDataUtil.class.getName());

    private static SimpleDateFormat ddMMyyyyFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public static String getLastThursday() {
    	String retStr = "";
    	try {
    		Calendar pCal = Calendar.getInstance();
    		
    		pCal.set(GregorianCalendar.DAY_OF_WEEK,Calendar.THURSDAY);
    		pCal.set(GregorianCalendar.DAY_OF_WEEK_IN_MONTH, -1);
    		retStr = ddMMyyyyFormat.format(pCal.getTime());
    		System.out.println("!!!retStr="+retStr);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return retStr;
    }
}

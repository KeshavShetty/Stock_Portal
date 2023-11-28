package org.stock.portal.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.log4j.Logger;

public class StringUtil {

    private static Logger log = Logger.getLogger(StringUtil.class.getName());

    public static String md5Hash(String string) 
    throws NoSuchAlgorithmException {

        log.debug("Creating md5 hash");
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] md5Hash = md.digest(string.getBytes());

        BigInteger md5BigInt = new BigInteger(1, md5Hash);

        return md5BigInt.toString(16);
        //return StringUtils.md5HashToHex(md5Hash);
    }

    public static String md5HashSalted(String string)
    throws NoSuchAlgorithmException {
        log.debug("Creating salted md5 hash");
        return StringUtil.md5Hash(string + SPConstants.SALT);
    }
    
    /**
     * converts Strings (ignoring case): 
     * <strong>true|false, 1|0, yes|no, on|off</strong> to boolean
     * 
     * @param s
     * @param bDefault
     * @return
     */
    public static boolean getBoolean(String s, boolean bDefault) {
        if (s == null)
            return bDefault;
        if (s.equalsIgnoreCase("true"))
            return true;
        else if (s.equalsIgnoreCase("false"))
            return false;
        else if (s.equalsIgnoreCase("1"))
            return true;
        else if (s.equalsIgnoreCase("0"))
            return false;
        else if (s.equalsIgnoreCase("yes"))
            return true;
        else if (s.equalsIgnoreCase("no"))
            return false;
        else if (s.equalsIgnoreCase("on"))
            return true;
        else if (s.equalsIgnoreCase("off"))
            return false;
        else
            return bDefault;
    }
    
    /**
     * tries to convert a String into a Long, 
     * return null if a number format exception occurs
     *      
     * @param s
     * @return
     */
    public static Long getLong(String s) {
        Long l = null;
        if (s != null && s.length() > 0) {
            try {
                l = new Long(s);
            } catch (NumberFormatException e) {
                log.warn(e);
            }
        }
        return l;
    }    
    
    /**
     * Convert a List of Strings to a 'delim' delimeted String
     * @param strList
     * @param delim
     * @return
     */
    public static String toDelimitedString(List<String> strList,
            String delim) {

        String resStr = new String();

        if ( !(strList == null || strList.isEmpty()) ) {
            for ( String str: strList ) {
                resStr = resStr + str + delim;
            }
            // removing last instance of delimiter
            resStr = resStr.substring(0, resStr.lastIndexOf(delim));
        }
        return resStr;
    }
    
    /**
     * Checks weather the input String is empty or null (including null string
     * If empty or null returns true
     * else false
     * 
     * @param inputString
     *            The string needed to be checked
     * 
     * @return boolean  the result of null or empty check
     * 
     */
    public static boolean isEmptyOrNull(String inputString) {       
        if (inputString==null || inputString.length()==0 || inputString.equals("null")) {
            return true;
        } else {
            return false;
        }       
    }    
    
}

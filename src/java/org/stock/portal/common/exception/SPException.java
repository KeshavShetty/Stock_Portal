package org.stock.portal.common.exception;

/**
 * Application-speccific exception class. Immediate successor of
 * <code>RepException</code>. This Exception should be used as the end point for all unexpected exceptions
 *<br/>
 *For business specific exceptions that are 'expected' use the <code>BusinessException</code> 
 *
 * 
 * @author Stock Portal
 */
public class SPException extends RepException {
    
    private static final long serialVersionUID = -924235550665734681L;

    /** 
     * Default constructor.
     * 
     * @see RepException#RepException()
     */
    public SPException() {
        super();
    }
    
    /**
     * Constructor with messageKey parameter.
     * 
     * @param msgKey <code>String</code>
     *        The messageKey to set as exception message.
     *        
     * @see RepException#RepException(String)
     */
    public SPException(String msgKey) {
        super(msgKey);
    }
    
    /**
     * Constructor with messageKey and <code>Throwable</code> parameter.
     * 
     * @param msgKey <code>String</code>
     *        The messageKey to set as exception message.
     * @param t <code>Throwable</code>
     *        The throwable instance.
     * @see RepException#RepException(String, Throwable)
     */
    public SPException(String msgKey, Throwable t) {
        super(msgKey, t);
    }

}

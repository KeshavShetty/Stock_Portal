package org.stock.portal.common.exception;

/**
 * Application-speccific exception class. Immediate successor of
 * <code>RepException</code>. This Exception should be used as the end point for all 'expected' exceptions
 * ( i.e Exceptions that should be handled more gracefully  )
 * <br/>
 * For 'unexpected' exceptions use <code>SPException</code> 
 *
 * 
 * @author Stock Portal
 */
public class BusinessException extends RepException {
    
    private static final long serialVersionUID = -924235550665734681L;

    /** 
     * Default constructor.
     * 
     * @see RepException#RepException()
     */
    public BusinessException() {
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
    public BusinessException(String msgKey) {
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
    public BusinessException(String msgKey, Throwable t) {
        super(msgKey, t);
    }

}

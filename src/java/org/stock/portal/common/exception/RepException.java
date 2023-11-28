package org.stock.portal.common.exception;

import org.stock.portal.common.MiscUtil;

/**
 * Entry point exception, with localized messages and unique id generation
 * @author Stock Portal
 *
 */
public class RepException extends java.lang.Exception {
    
    private static final long serialVersionUID = -2500887719229187997L;
    
    protected String id;
    protected String messageKey;

    public RepException() {
        super();
        id = MiscUtil.generateGUID(this);
        messageKey="error.generic";
    }

    public RepException(String messageKey_) {
        super(messageKey_);
        id = MiscUtil.generateGUID(this);
        messageKey=messageKey_;
    }

    public RepException(String messageKey_, Throwable t) {
        super(messageKey_, t);
        messageKey=messageKey_;
        if (t instanceof RepException) {
            id = ((RepException)t).id;
        } else {
            id = MiscUtil.generateGUID(this);
        }
    }

    public String getId() {
        return id;
    }

    public RepException getInitRepException() {
        Throwable inner1 = this.getCause();
        Throwable inner2 = this;
        while (inner1!=null && inner1 instanceof RepException) {
            inner2 = inner1;
            inner1 = inner1.getCause();
        }
        return (RepException) inner2;
    }

    public Throwable getInitCheckedException() {
        return getInitRepException().getCause();
    }

    public String getInitRepExceptionMessage() {
        return getInitRepException().getLocalizedMessage();
    }

    public String getInitCheckedExceptionMessage() {
        return getInitCheckedException().getLocalizedMessage();
    }

    public String getMessage() {
        return "id "+getId()+", "+super.getMessage();
    }


    public String getLocalizedMessage() {
        return messageKey;
    }
}



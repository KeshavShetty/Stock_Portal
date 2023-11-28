package org.stock.portal.web.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import org.stock.portal.common.StringUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.PreResultListener;

public class MessagesPreResultListener implements PreResultListener {

    public static final String MESSAGE_SEPERATOR_CHAR = "|||";
    public static final String ACTION_MESSAGES_RESPONSE_HEADER = "portal-action-messages";
    public static final String ACTION_ERRORS_RESPONSE_HEADER = "portal-action-errors";
    public static final String SESSION_EXPIRED_RESPONSE_HEADER = "portal-sessionexpired";
    
    @Override
    public void beforeResult(ActionInvocation invocation, String arg1) {
        Object action = invocation.getAction();
        if (ActionSupport.class.isAssignableFrom(action.getClass())) {
            ActionSupport actionSupport = (ActionSupport) action;
            
            HttpServletResponse response = ServletActionContext.getResponse();
            
            List<String> actionMessagesTranslated = new ArrayList<String>();
            for(String actionMessage : actionSupport.getActionMessages()){
                actionMessagesTranslated.add(actionSupport.getText(actionMessage));
            }
            List<String> actionErrorsTranslated = new ArrayList<String>();
            for(String actionError : actionSupport.getActionErrors()){
                actionErrorsTranslated.add(actionSupport.getText(actionError));
            }
            
            String actionMessagesStr = StringUtil.toDelimitedString( actionMessagesTranslated, MESSAGE_SEPERATOR_CHAR );
            String actionErrorsStr = StringUtil.toDelimitedString( actionErrorsTranslated, MESSAGE_SEPERATOR_CHAR );

            response.setHeader(ACTION_MESSAGES_RESPONSE_HEADER, actionMessagesStr);
            response.setHeader(ACTION_ERRORS_RESPONSE_HEADER, actionErrorsStr);            
        }
        
    }

}

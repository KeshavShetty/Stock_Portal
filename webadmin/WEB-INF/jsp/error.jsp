<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:i18n name="application_errors">
    <s:if test="%{exception instanceof org.stock.portal.common.exception.RepException}">
        <p>
            <strong><s:text name="error.message"/>:</strong>&nbsp;<s:text name="%{exception.localizedMessage}" />
        </p>    
        <p>
            <strong><s:text name="error.id"/>:</strong>&nbsp;<s:property value="exception.id" />
        </p>
    </s:if>
    <s:else>
        <p>
            <strong><s:text name="error.generic"/>:</strong>&nbsp;<s:text name="%{exception.message}" />
        </p>
    </s:else>
</s:i18n>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>

<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<s:iterator value="criteriaStatuslist">
	<tr>
		<td>
			<s:if test="%{matched==true}">
				<font color="green">
			</s:if>
			<s:else>
				<font color="red">
			</s:else>
				<s:property value="wlCriteria"/>
			</font>
			<font color="blue"> [<s:property value="actualValue"/>]</font>
		</td>
	</tr>
	</s:iterator>	
</table>
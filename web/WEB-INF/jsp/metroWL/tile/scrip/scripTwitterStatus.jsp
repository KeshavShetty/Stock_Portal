<%@ taglib prefix="s" uri="/struts-tags" %>
<table class="datatable" id="genericTableFormtable" summary="Deployments">

	<s:iterator value="scripTwitterStatusList" status="rowStatus">
		<tr class="rowEven">
			<td>
				<s:property value="%{#rowStatus.index+1}" />). 
				<a href="https://twitter.com/<s:property value="user.screenName"/>" target="_blank">
					@<s:property value="user.screenName"/></td>
				</a>
			
			
			<td><s:property value="createdAt"/></td>
			<td>
				<a href="https://twitter.com/<s:property value="user.screenName"/>/status/<s:property value="id"/>" target="_blank">
					<s:property value="text"/>
				</a>
			</td>
		</tr>
	</s:iterator>
</table>
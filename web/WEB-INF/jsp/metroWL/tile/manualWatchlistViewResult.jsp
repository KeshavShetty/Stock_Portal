<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>


<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>	
		<th scope="col">
			Dynamic Watchlist Name
		</th>
	</tr>
	<s:iterator value="watchlist">
		<tr>
			<td>
				<s:property value="name"/>
			</td>
		</tr>
	</s:iterator>
</table>
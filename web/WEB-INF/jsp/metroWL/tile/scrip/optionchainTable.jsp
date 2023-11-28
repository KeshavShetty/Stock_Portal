<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>

<JqGridTable:addTable tableIdentifier="ScripPage_OptionchainTable" formRequired="true"/>

<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>
		<td>
			<a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=NSE OptionChain <s:property value="%{scrip.id}"/>">[NFO Option Chain]</a>
		</td>
	</tr>
</table>
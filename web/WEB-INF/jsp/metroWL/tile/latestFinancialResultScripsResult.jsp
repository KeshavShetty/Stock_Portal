<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">
			<a title="Collapse" href="javascript:void(0)" onclick="javascript:toggleDiv('populatedLatestFinancialScripResultTable');" >
				<img src="<%=request.getContextPath()%>/metroWL/images/expand.png">
			</a>
			Latest companies declared results
		</td>		
	</tr>
</table>
<table class="datatable" id="populatedLatestFinancialScripResultTable" summary="Deployments">	
	<tr>
		<th scope="col" title="Sort table by Name">
			<s:if test="%{orderBy=='name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="javascript:populateBodyDivThruAjax('/latestFinancialDeclaredScripsResult.do?orderBy=name&orderType=DESC', 'latestFinancialScripResultTable', true);">Name<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="javascript:populateBodyDivThruAjax('/latestFinancialDeclaredScripsResult.do?orderBy=name&orderType=ASC', 'latestFinancialScripResultTable', true);">Name<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th scope="col" title="Sort table by Updated On">
			<s:if test="%{orderBy=='resultDate' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="javascript:populateBodyDivThruAjax('/latestFinancialDeclaredScripsResult.do?orderBy=resultDate&orderType=DESC', 'latestFinancialScripResultTable', true);">Result Date<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="javascript:populateBodyDivThruAjax('/latestFinancialDeclaredScripsResult.do?orderBy=resultDate&orderType=ASC', 'latestFinancialScripResultTable', true);">Result Date<span class="nobr">&nbsp;<s:if test="%{orderBy=='resultDate' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th scope="col" title="Sort table by Net Profit">
			<s:if test="%{orderBy=='changeProfit' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="javascript:populateBodyDivThruAjax('/latestFinancialDeclaredScripsResult.do?orderBy=changeProfit&orderType=DESC', 'latestFinancialScripResultTable', true);">Change in Net Profit(Fin)<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="javascript:populateBodyDivThruAjax('/latestFinancialDeclaredScripsResult.do?orderBy=changeProfit&orderType=ASC', 'latestFinancialScripResultTable', true);">Change in Net Profit(Fin)<span class="nobr">&nbsp;<s:if test="%{orderBy=='changeProfit' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
	</tr>
	<s:iterator value="latestScripList">
		<tr class="rowEven">	
			<td>	
				<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="id"/>,800,600)"><s:property value="name"/></a>
				<a title="Switch to this scrip" href="javascript:void(0)" onclick='document.getElementById("searchFinancialResultScripCode").value="BSE-<s:property value="bseCode"/>";submitScreenForm("searchFinancialResult","searchFinancialResultTable");toggleDiv("populatedLatestFinancialScripResultTable");document.getElementById("scripCodeHidden").value=document.getElementById("searchFinancialResultScripCode").value;submitScreenForm("showFinancialResultGraph","highchartsFinancialGrpahContainer");' >
					(View financial results)
				</a>
			</td>
			<td><s:property value="resultDate"/></td>
			<td><s:property value="changeProfit"/></td>
		</tr>		
	</s:iterator>	
</table>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Search results</td>		
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>
		<th width="15%">BSE Code</th>
		<th width="15%">
			<s:if test="%{orderBy=='nse_code' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','nse_code','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">NSE Code<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','nse_code','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">NSE Code<span class="nobr">&nbsp;<s:if test="%{orderBy=='nse_code' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th>
		Rank
		</th>	
		<th scope="col" title="Sort table Title">
			<s:if test="%{orderBy=='name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','name','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','name','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
	</tr>
	<s:iterator value="resultList">
		<tr class="rowEven">
			<td><s:property value="bseCode"/></td>
			<td><s:property value="nseCode"/></td>
			<td><s:property value="rank"/></td>		
			<td>				
				<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="id"/>,800,600)" onmouseover="showtrail('<%=request.getContextPath()%>/chart/quickchart?CompareWith=&TimeRange=90&ChartSize=L&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=SMA&movAvg1=10&avgType2=SMA&movAvg2=25&Indicator1=SStoch&Indicator2=MACD&Indicator3=CCI&Indicator4=RSI&Button1=Update Chart&TickerID=<s:property value="id"/>', '<s:property value="name"/>',780,565)" onmouseout="hidetrail()">
					<s:property value="name"/>
					<img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" id="chart-holder-<s:property value="id"/>" style="zindex: 100; position: absolute; margin: auto;" />					
				</a>
			</td>
		</tr>		
	</s:iterator>	
</table>


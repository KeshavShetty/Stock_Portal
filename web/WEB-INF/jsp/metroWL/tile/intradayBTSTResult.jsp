<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>


<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Intraday BTST results</td>
		<td class="tablenavigation">			
			<paging:paging currentPageNumber="${pageNumber}" formName="${sourceFormName}" resultDivName="${divToFill}" totalRecords="${totalRecords}" recordsPerPage="${recordsPerPage}"/>
		</td>
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>	
		<th width="25%" scope="col" title="Sort table Title">
			<s:if test="%{orderBy=='scrip.name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.name','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.name','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table Scrip">
			Scrip Code
		</th>
		<th width="15%" scope="col" title="Sort table Scrip">
			Signal Date
		</th>
		<th width="15%" scope="col" title="CMP">
			<s:if test="%{orderBy=='scrip.nseCmp' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.nseCmp','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">CMP<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.nseCmp','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">CMP<span class="nobr">&nbsp;<s:if test="%{orderBy=='scrip.nseCmp' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table Scrip">
			Price@Signal
		</th>
		<th width="15%" scope="col" title="Average Volume"> 
			<s:if test="%{orderBy=='scrip.nseAverageVolume' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.nseAverageVolume','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Average Volume<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.nseAverageVolume','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Average Volume<span class="nobr">&nbsp;<s:if test="%{orderBy=='scrip.averageVolume' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
	</tr>
	<s:iterator value="resultList">
		<tr class="rowEven">		
			<td>
				<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="scrip.id"/>,800,600)" onmouseover="showtrail('<%=request.getContextPath()%>/chart/quickchart?CompareWith=&TimeRange=90&ChartSize=L&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=SMA&movAvg1=10&avgType2=SMA&movAvg2=25&Indicator1=SStoch&Indicator2=MACD&Indicator3=CCI&Indicator4=RSI&Button1=Update Chart&TickerID=<s:property value="scrip.id"/>', '<s:property value="scrip.name"/>',780,565)" onmouseout="hidetrail()">
					<s:property value="scrip.name"/>
					<img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" id="chart-holder-<s:property value="scrip.id"/>" style="zindex: 100; position: absolute; margin: auto;" />					
				</a>
			</td>
			<td><s:property value="scrip.bseCode"/>(BSE) - <s:property value="scrip.nseCode"/>-<s:property value="scrip.seriesType"/>(NSE)</td>
			<td><s:property value="signalDate"/></td>
			<td>
				<a href="javascript:void(0)">
					<s:text name="rupees.format" >
						<s:param name="scrip.cmp" value="%{scrip.cmp}"/>
					</s:text>
				</a>
			</td>
			<td><s:property value="signalPrice"/></td>
			<td><s:property value="scrip.nseAverageVolume"/></td>
		</tr>		
	</s:iterator>	
</table>
<table class="tablebuttonbar">
	<tr>
		<td class="tablecontrols">
			<JqGridTable:addTable tableIdentifier="Nse_FUT_BTST_PickTable" formRequired="true"/>
		</td>
	</tr>
</table>
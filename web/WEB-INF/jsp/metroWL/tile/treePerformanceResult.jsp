<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Index View results</td>		
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>	
		<th width="20%" scope="col" title="Sort table Title">
			<s:if test="%{orderBy=='sourceScrip.name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','sourceScrip.name','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Source Scrip<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','sourceScrip.name','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Source Scrip<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="20%" scope="col" title="Sort table Title">
			<s:if test="%{orderBy=='targetScrip.name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','targetScrip.name','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Target Scrip<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','targetScrip.name','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Target Scrip<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="20%" scope="col" title="Sort table Title">
			<s:if test="%{orderBy=='joinIncrement' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','joinIncrement','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Joint Move<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','joinIncrement','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Joint Move<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="20%" scope="col" title="Sort table Title">
			<s:if test="%{orderBy=='totalSourceGain' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','totalSourceGain','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Source Gain<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','totalSourceGain','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Source Gain<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="20%" scope="col" title="Sort table Title">
			<s:if test="%{orderBy=='totalTargetGain' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','totalTargetGain','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Target Gain<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','totalTargetGain','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Target Gain<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
	</tr>
	<s:iterator value="resultList">
		<tr class="rowEven">		
			<td>
				<a title="Expand" href="javascript:void(0)" onclick="javascript:toggleTreePerformanceDiv(<s:property value="targetScrip.id"/>, 'Tree_<s:property value="targetScrip.id"/>', '<s:property value="%{orderBy}"/>', '<s:property value="%{orderType}"/>')">
					<img src="<%=request.getContextPath()%>/metroWL/images/expand.png">
				</a>				
				<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="sourceScrip.id"/>,800,600)" onmouseover="showtrail('<%=request.getContextPath()%>/chart/quickchart?CompareWith=&TimeRange=90&ChartSize=L&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=SMA&movAvg1=10&avgType2=SMA&movAvg2=25&Indicator1=SStoch&Indicator2=MACD&Indicator3=CCI&Indicator4=RSI&Button1=Update Chart&TickerID=<s:property value="sourceScrip.id"/>', '<s:property value="sourceScrip.name"/>',780,565)" onmouseout="hidetrail()">
					<s:property value="sourceScrip.name"/>
					<img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" id="chart-holder-<s:property value="sourceScrip.id"/>" style="zindex: 100; position: absolute; margin: auto;" />					
				</a>
			</td>
			<td>				
				<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="targetScrip.id"/>,800,600)" onmouseover="showtrail('<%=request.getContextPath()%>/chart/quickchart?CompareWith=&TimeRange=90&ChartSize=L&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=SMA&movAvg1=10&avgType2=SMA&movAvg2=25&Indicator1=SStoch&Indicator2=MACD&Indicator3=CCI&Indicator4=RSI&Button1=Update Chart&TickerID=<s:property value="targetScrip.id"/>', '<s:property value="targetScrip.name"/>',780,565)" onmouseout="hidetrail()">
					<s:property value="targetScrip.name"/>
					<img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" id="chart-holder-<s:property value="targetScrip.id"/>" style="zindex: 100; position: absolute; margin: auto;" />					
				</a>
			</td>
			<td>
				<s:property value="joinIncrement"/>
			</td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="totalSourceGain" value="%{totalSourceGain}"/>
				</s:text>
			</td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="totalTargetGain" value="%{totalTargetGain}"/>
				</s:text>
			</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="4" id="Tree_<s:property value="targetScrip.id"/>">				
			</td>
		</tr>
	</s:iterator>	
</table>
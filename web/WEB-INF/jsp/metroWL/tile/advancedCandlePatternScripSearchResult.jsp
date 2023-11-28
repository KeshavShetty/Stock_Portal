<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>

<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Candle Pattern Scrip Match results</td>
		<td class="tablenavigation">			
			<paging:paging currentPageNumber="${pageNumber}" formName="${sourceFormName}" resultDivName="${divToFill}" totalRecords="${totalRecords}" recordsPerPage="${recordsPerPage}"/>
		</td>
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>	
		<th scope="col" title="Sort table Scrip">
			<s:if test="%{orderBy=='scrip.name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.name','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.name','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<s:if test="%{orderBy=='scrip.name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th scope="col" title="Sort table Signal Date">
			<s:if test="%{orderBy=='patternDate' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','patternDate','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Ptrn Date<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','patternDate','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Ptrn Date<span class="nobr">&nbsp;<s:if test="%{orderBy=='patternDate' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>		
		<th scope="col">PATTERN</th>	
		<th scope="col" title="Sort table Up Percentage">
			<s:if test="%{orderBy=='fiveDayPattern.upPercent' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.upPercent','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Up%<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.upPercent','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Up%<span class="nobr">&nbsp;<s:if test="%{orderBy=='fiveDayPattern.upPercent' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th scope="col" title="Sort table Down Percentage">
			<s:if test="%{orderBy=='fiveDayPattern.downPercent' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.downPercent','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Dwn%<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.downPercent','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Dwn%<span class="nobr">&nbsp;<s:if test="%{orderBy=='fiveDayPattern.downPercent' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th scope="col" title="Sort table Up Count">
			<s:if test="%{orderBy=='fiveDayPattern.upCount' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.upCount','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Up++<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.upCount','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Up++<span class="nobr">&nbsp;<s:if test="%{orderBy=='fiveDayPattern.upCount' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th scope="col" title="Sort table Down Count">
			<s:if test="%{orderBy=='fiveDayPattern.downCount' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.downCount','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Down--t<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.downCount','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Down--<span class="nobr">&nbsp;<s:if test="%{orderBy=='fiveDayPattern.downCount' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th scope="col" title="Sort table Count Rank">
			<s:if test="%{orderBy=='fiveDayPattern.countRank' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.countRank','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Rank<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.countRank','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Rank<span class="nobr">&nbsp;<s:if test="%{orderBy=='fiveDayPattern.countRank' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th scope="col" title="Sort table ROI Rank">
			<s:if test="%{orderBy=='fiveDayPattern.roiRank' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.roiRank','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">ROI<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.roiRank','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">ROI<span class="nobr">&nbsp;<s:if test="%{orderBy=='fiveDayPattern.roiRank' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th>CMP@Signal</th>
		<th>CMP</th>
		<th scope="col" title="Sort table 3Day Up Count">
			<s:if test="%{orderBy=='fiveDayPattern.upCount3Day' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.upCount3Day','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">3DUp++<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.upCount3Day','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">3DUp++<span class="nobr">&nbsp;<s:if test="%{orderBy=='fiveDayPattern.upCount3Day' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th scope="col" title="Sort table 3Day Count Rank">
			<s:if test="%{orderBy=='fiveDayPattern.countRank3Day' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.countRank3Day','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">3DRank<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','fiveDayPattern.countRank3Day','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">3DRank<span class="nobr">&nbsp;<s:if test="%{orderBy=='fiveDayPattern.countRank3Day' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
	</tr>
	<s:iterator value="candlePatternScripList">
		<tr class="rowEven">
			<td>
				<a href="javascript:void(0)" onclick="javascript:getScripInfoAndPopulateDiv('<%=request.getContextPath()%>/scripInfo.do',<s:property value="scrip.id"/>,'ScripInfoDiv')" onmouseover="showtrail('<%=request.getContextPath()%>/chart/quickchart?CompareWith=&TimeRange=90&ChartSize=L&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=SMA&movAvg1=10&avgType2=SMA&movAvg2=25&Indicator1=SStoch&Indicator2=MACD&Indicator3=CCI&Indicator4=RSI&Button1=Update Chart&TickerID=<s:property value="scrip.id"/>', '<s:property value="scrip.name"/>',780,565)" onmouseout="hidetrail()">
					<s:property value="scrip.shortName"/>
				</a>
			</td>
			<td><s:property value="patternDate"/></td>			
			<td>
				<a href="<%=request.getContextPath()%>/googleCandle.do?scripId=<s:property value="scrip.id"/>&dataDate=<s:property value="patternDate"/>&exchangeCode=<s:property value="exchangeCode"/>" target="_blank" title="
				5): <s:property value="fiveDayPattern.countRank"/>
				4): <s:property value="fourDayPattern.countRank"/> 
				3): <s:property value="threeDayPattern.countRank"/> 
				2): <s:property value="twoDayPattern.countRank"/> 
				1): <s:property value="oneDayPattern.countRank"/>">
					<s:property value="fiveDayPattern.PatternName"/>
				</a>
			</td>			
			<td>
				<s:text name="rupees.format" >
					<s:param name="fiveDayPattern.upPercent" value="%{fiveDayPattern.upPercent}"/>
				</s:text>
			</td>
			<td><s:text name="rupees.format" >
					<s:param name="fiveDayPattern.downPercent" value="%{fiveDayPattern.downPercent}"/>
				</s:text>
			</td>
			<td><s:property value="fiveDayPattern.upCount"/></td>
			<td><s:property value="fiveDayPattern.downCount"/></td>
			<td><s:property value="fiveDayPattern.countRank"/></td>
			<td><s:property value="fiveDayPattern.roiRank"/></td>
			<td><s:property value="priceAtSignal"/></td>
			<td>
				<s:if test="%{scrip.cmp>priceAtSignal}">
					<font color="green"><s:property value="scrip.cmp"/>	</font>
				</s:if>
				<s:if test="%{scrip.cmp<=priceAtSignal}">
					<font color="red"><s:property value="scrip.cmp"/>	</font>
				</s:if>
			</td>
			<td>
				<a href="javascript:void(0)" title="DownCount:<s:property value="fiveDayPattern.downCount3Day"/>">
					<s:property value="fiveDayPattern.upCount3Day"/>					
				</a>
			</td>
			<td><s:property value="fiveDayPattern.countRank3Day"/></td>	
		</tr>		
	</s:iterator>	
</table>
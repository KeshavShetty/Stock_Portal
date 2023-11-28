<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Past Achievers results</td>
		<td class="tablenavigation">			
			<paging:paging currentPageNumber="${pageNumber}" formName="${sourceFormName}" resultDivName="${divToFill}" totalRecords="${totalRecords}" recordsPerPage="${recordsPerPage}"/>
		</td>
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>	
		<th width="40%" scope="col" title="Sort table Title">
			<s:if test="%{orderBy=='name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','name','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Title<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','name','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Title<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table Scrip">
			First price
		</th>
		<th width="15%" scope="col" title="Sort table Scrip">
			Last price
		</th>		
		<th width="15%" scope="col" title="Sort table Performance">
			<s:if test="%{orderBy=='performance' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','performance','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Performance%<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','performance','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Performance%<span class="nobr">&nbsp;<s:if test="%{orderBy=='performance' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table Volume Performance">
			<s:if test="%{orderBy=='volumePerformance' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','volumePerformance','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Volume Ratio<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','volumePerformance','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Volume Ratio<span class="nobr">&nbsp;<s:if test="%{orderBy=='volumePerformance' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>		
		<th width="15%" scope="col" title="Sort table Volume Vs Avg Volume">
			<s:if test="%{orderBy=='volumeVsAvgVolume' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','volumeVsAvgVolume','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Vol Vs Avg Volume<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','volumeVsAvgVolume','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Vol Vs Avg Volume<span class="nobr">&nbsp;<s:if test="%{orderBy=='volumeVsAvgVolume' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table Turnover">
			<s:if test="%{orderBy=='turnover' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','turnover','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Turnover(Cr)<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','turnover','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Turnover(Cr)<span class="nobr">&nbsp;<s:if test="%{orderBy=='turnover' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table UpGap">
			<s:if test="%{orderBy=='upGap' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','upGap','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Up Gap<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','upGap','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Up Gap<span class="nobr">&nbsp;<s:if test="%{orderBy=='upGap' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table Down Gap">
			<s:if test="%{orderBy=='downGap' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','downGap','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Down Gap<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','downGap','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Down Gap<span class="nobr">&nbsp;<s:if test="%{orderBy=='downGap' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table Bar Size">
			<s:if test="%{orderBy=='barSizeOfTheDay' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','barSizeOfTheDay','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Bar Size<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','barSizeOfTheDay','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Bar Size<span class="nobr">&nbsp;<s:if test="%{orderBy=='barSizeOfTheDay' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table Volume Vs LastDay1M Volume">
			<s:if test="%{orderBy=='volumeVsLastDay1MVol' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','volumeVsLastDay1MVol','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Vol Vs LastDay1M Volume<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','volumeVsLastDay1MVol','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Vol Vs LastDay1M Volume<span class="nobr">&nbsp;<s:if test="%{orderBy=='volumeVsLastDay1MVol' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
	</tr>
	<s:iterator value="scripList">
		<tr class="rowEven">		
			<td>
				<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="id"/>,800,600)" onmouseover="showtrail('<%=request.getContextPath()%>/chart/quickchart?CompareWith=&TimeRange=180&ChartSize=L&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=SMA&movAvg1=10&avgType2=SMA&movAvg2=25&Indicator1=SStoch&Indicator2=MACD&Indicator3=CCI&Indicator4=RSI&Button1=Update Chart&TickerID=<s:property value="id"/>', '<s:property value="name"/>',780,565)" onmouseout="hidetrail()">
					<s:property value="name"/>
					<img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" id="chart-holder-<s:property value="id"/>" style="zindex: 100; position: absolute; margin: auto;" />
				</a>
			</td>
			<td><s:property value="firstPrice"/></td>
			<td><s:property value="lastPrice"/></td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="performance" value="%{performance}"/>
				</s:text>
			</td>
			<td>
				<a href="javascript:void(0)" title='<s:property value="lastVolume"/>/<s:property value="firstVolume"/>'>
					<s:text name="rupees.format" >
						<s:param name="volumePerformance" value="%{volumePerformance}"/>
					</s:text>
				</a>
			</td>
			<td>
				<a href="javascript:void(0)" title='Avg Volume: <s:property value="avgVolume"/>'>
					<s:text name="rupees.format" >
						<s:param name="volumeVsAvgVolume" value="%{volumeVsAvgVolume}"/>
					</s:text>
				</a>
			</td>
			<td>
				<a href="javascript:void(0)">
					<s:text name="rupees.format" >
						<s:param name="turnoverInCr" value="%{turnoverInCr}"/>
					</s:text>
				</a> Cr
			</td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="upGap" value="%{upGap}"/>
				</s:text>
			</td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="downGap" value="%{downGap}"/>
				</s:text>
			</td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="barSizeOfTheDay" value="%{barSizeOfTheDay}"/>
				</s:text>
			</td>
			<td>
				<a href="javascript:void(0)" title='Vol/LastDay1M Volume: <s:property value="avgVolume"/>'>
					<s:text name="rupees.format" >
						<s:param name="volumeVsLastDay1MVol" value="%{volumeVsLastDay1MVol}"/>
					</s:text>
				</a>
			</td>
		</tr>		
	</s:iterator>	
</table>
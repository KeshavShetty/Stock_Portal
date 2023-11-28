<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Intraday Snapshot results</td>
		<td class="tablenavigation">			
			<paging:paging currentPageNumber="${pageNumber}" formName="${sourceFormName}" resultDivName="${divToFill}" totalRecords="${totalRecords}" recordsPerPage="${recordsPerPage}"/>
		</td>
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>	
		<th width="12%" scope="col" title="Sort table Title">
			<s:if test="%{orderBy=='scrip.name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.name','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.name','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="8%" scope="col" title="Sort table Scrip">
			Volume < 3PM
		</th>
		<th width="8%" scope="col" title="Sort table Scrip(Volume after 3PM before 3:15PM)">
			Volume < 3:15PM
		</th>		
		<th width="8%" scope="col" title="Sort table Scrip">
			Total Volume
		</th>	
		<th width="8%" scope="col" title="Sort table Volume Performance">
			<s:if test="%{orderBy=='volumePerformance' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','volumePerformance','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Volume Ratio<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','volumePerformance','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Volume Ratio<span class="nobr">&nbsp;<s:if test="%{orderBy=='volumePerformance' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="8%" scope="col" title="Sort table cfWeightage">
			<s:if test="%{orderBy=='cfWeightage' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','cfWeightage','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">CF Weightage<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','cfWeightage','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">CF Weightage<span class="nobr">&nbsp;<s:if test="%{orderBy=='cfWeightage' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="8%" scope="col" title="Sort table cfRating">
			<s:if test="%{orderBy=='cfRating' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','cfRating','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">CF Rating<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','cfRating','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">CF Rating<span class="nobr">&nbsp;<s:if test="%{orderBy=='cfRating' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="8%" scope="col" title="Sort table lowestLow(15Days)">
			<s:if test="%{orderBy=='lowestLow' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','lowestLow','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Lowest Low<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','lowestLow','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Lowest Low<span class="nobr">&nbsp;<s:if test="%{orderBy=='lowestLow' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="8%" scope="col" title="Sort table highestHigh(15Days)">
			<s:if test="%{orderBy=='highestHigh' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','highestHigh','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Highest High<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','highestHigh','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Highest High<span class="nobr">&nbsp;<s:if test="%{orderBy=='highestHigh' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="8%" scope="col" title="Sort table Scrip">
			Bar Size%
		</th>
		<th width="8%" scope="col" title="Sort table highest Onemin Volume">
			<s:if test="%{orderBy=='highestOneminVolume' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','highestOneminVolume','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Highest Onemin Volume<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','highestOneminVolume','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Highest Onemin Volume<span class="nobr">&nbsp;<s:if test="%{orderBy=='highestOneminVolume' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="8%" scope="col" title="Sort table Highest Onemin Turnover">
			<s:if test="%{orderBy=='highestOneminTurnover' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','highestOneminTurnover','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Highest Onemin Turnover<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','highestOneminTurnover','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Highest Onemin Turnover<span class="nobr">&nbsp;<s:if test="%{orderBy=='highestOneminTurnover' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
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
				<a href="javascript:openScripPage('<s:property value="scrip.nseCode"/>','<s:property value="scrip.bseCode"/>')">
					<img src="<%=request.getContextPath()%>/metroWL/images/view_detail.png" title="View Scrip details">
				</a>
			</td>
			<td><s:property value="volumeBefore3"/></td>
			<td><s:property value="volumeAfter3Before315"/></td>
			<td><s:property value="totalVolume"/></td>
			<td>
				<a href="javascript:void(0)">
					<s:text name="rupees.format" >
						<s:param name="volumePerformance" value="%{volumePerformance}"/>
					</s:text>
				</a>
			</td>
			<td>
				<a href="javascript:void(0)">
					<s:text name="rupees.format" >
						<s:param name="cfWeightage" value="%{cfWeightage}"/>
					</s:text>
				</a>
			</td>
			<td><s:property value="cfRating"/></td>
			<td>
				<s:if test="%{lowestLow==true}">
					<img src="<%=request.getContextPath()%>/metroWL/images/tick.png" title="Lowest in three month">
				</s:if>
			</td>
			<td>
				<s:if test="%{highestHigh==true}">
					<img src="<%=request.getContextPath()%>/metroWL/images/tick.png" title="Highest in three month">
				</s:if>
			</td>
			<td>
				<s:text name="rupees.format" >
						<s:param name="barSize" value="%{barSize}"/>
					</s:text>
			</td>
			<td>
				<s:property value="highestOneminVolume"/>
			</td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="highestOneminTurnover" value="%{highestOneminTurnover}"/>
				</s:text>
			</td>			
		</tr>		
	</s:iterator>	
</table>
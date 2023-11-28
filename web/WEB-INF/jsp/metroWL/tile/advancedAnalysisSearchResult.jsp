<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>

<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Analysis Search results</td>
		<td class="tablenavigation">			
			<paging:paging currentPageNumber="${pageNumber}" formName="${sourceFormName}" resultDivName="${divToFill}" totalRecords="${totalRecords}" recordsPerPage="${recordsPerPage}"/>
		</td>
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>	
		<th width="20%" scope="col" title="Sort table Scrip">
			<s:if test="%{orderBy=='scrip.name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.name','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.name','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<s:if test="%{orderBy=='scrip.name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="16%" scope="col" title="Sort table Signal Date">
			<s:if test="%{orderBy=='signalDate' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','signalDate','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Signal Date<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','signalDate','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Signal Date<span class="nobr">&nbsp;<s:if test="%{orderBy=='signalDate' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>		
		<th width="16%" scope="col" title="Sort table Signal name">
			<s:if test="%{orderBy=='studyMaster.shortName' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','studyMaster.shortName','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Analysis<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','studyMaster.shortName','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Analysis<span class="nobr">&nbsp;<s:if test="%{orderBy=='studyMaster.shortName' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>	
		<th width="16%" scope="col" title="Sort table Bullish/Bearish">
			<s:if test="%{orderBy=='signalCode' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','signalCode','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Bull/Bear<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','signalCode','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Bull/Bear<span class="nobr">&nbsp;<s:if test="%{orderBy=='signalCode' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="16%" scope="col" title="Sort table Price on Signal Date">
			<s:if test="%{orderBy=='previousClose' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','previousClose','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Price@Signal<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','previousClose','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Price@Signal<span class="nobr">&nbsp;<s:if test="%{orderBy=='previousClose' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="16%" scope="col" title="Sort table CMP">
			<s:if test="%{orderBy=='scrip.bseCmp' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.bseCmp','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">CMP(Last EOD)<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.bseCmp','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">CMP(Last EOD)<span class="nobr">&nbsp;<s:if test="%{orderBy=='scrip.bseCmp' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="16%" scope="col" title="Sort table ROI">
			<s:if test="%{orderBy=='(scrip.bseCmp-previousClose)/previousClose' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','(scrip.bseCmp-previousClose)/previousClose','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">ROI<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','(scrip.bseCmp-previousClose)/previousClose','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">ROI<span class="nobr">&nbsp;<s:if test="%{orderBy=='(scrip.bseCmp-previousClose)/previousClose' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
	</tr>
	<s:iterator value="analysisAutoscanResultList">
		<tr class="rowEven">
			<td>
				<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do', 'jqIndex='+<s:property value="scrip.id"/> , 800, 600)" onmouseover="showtrail('<%=request.getContextPath()%>/chart/quickchart?CompareWith=&TimeRange=90&ChartSize=L&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=SMA&movAvg1=10&avgType2=SMA&movAvg2=25&Indicator1=SStoch&Indicator2=MACD&Indicator3=CCI&Indicator4=RSI&Button1=Update Chart&TickerID=<s:property value="scrip.id"/>', '<s:property value="scrip.name"/>',780,565)" onmouseout="hidetrail()">
					<s:property value="scrip.name"/>
				</a>								
			</td>
			<td><s:property value="signalDate"/></td>			
			<td><s:property value="studyMaster.shortName"/>(<s:property value="studyMaster.study_parameter"/>)</td>
			<td>
				<s:if test="%{signalCode>0}">
					<img src="<%=request.getContextPath()%>/metroWL/images/up_g.gif">
				</s:if>
				<s:if test="%{signalCode<0}">
					<img src="<%=request.getContextPath()%>/metroWL/images/down_r.gif">
				</s:if>
			</td>
			<td><s:property value="previousClose"/></td>
			<td>
				<s:if test="%{scrip.cmp>previousClose}">
					<font color="green"><s:property value="scrip.cmp"/>	</font>
				</s:if>
				<s:if test="%{scrip.cmp<=previousClose}">
					<font color="red"><s:property value="scrip.cmp"/>	</font>
				</s:if>			
			</td>	
			<td>
				<s:text name="rupees.format" >
					<s:param name="roi" value="%{(scrip.bseCmp-previousClose)/previousClose}"/>
				</s:text>
			</td>			
		</tr>		
	</s:iterator>	
</table>
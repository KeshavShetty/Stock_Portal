<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">News Search results</td>
		<td class="tablenavigation">			
			<paging:paging currentPageNumber="${pageNumber}" formName="${sourceFormName}" resultDivName="${divToFill}" totalRecords="${totalRecords}" recordsPerPage="${recordsPerPage}"/>
		</td>
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>
		<th width="25%" scope="col" title="Sort table Scrip">
			<s:if test="%{orderBy=='scrip.name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.name','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','scrip.name','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Scrip<span class="nobr">&nbsp;<s:if test="%{orderBy=='scrip.name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="60%" scope="col" title="Sort table Title">
			<s:if test="%{orderBy=='newsFeedPost.postTitle' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','newsFeedPost.postTitle','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Title<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','newsFeedPost.postTitle','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Title<span class="nobr">&nbsp;<s:if test="%{orderBy=='newsFeedPost.postTitle' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>		
		<th width="15%" scope="col" title="Sort table Publish date">
			<s:if test="%{orderBy=='newsFeedPost.publishDate' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','newsFeedPost.publishDate','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Publish Date<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','newsFeedPost.publishDate','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Publish Date<span class="nobr">&nbsp;<s:if test="%{orderBy=='newsFeedPost.publishDate' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
	</tr>
	<s:iterator value="newsResultList">
		<tr class="rowEven">
			<td>
				<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="scrip.id"/>,800,600)" onmouseover="showtrail('<%=request.getContextPath()%>/chart/quickchart?CompareWith=&TimeRange=90&ChartSize=L&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=SMA&movAvg1=10&avgType2=SMA&movAvg2=25&Indicator1=SStoch&Indicator2=MACD&Indicator3=CCI&Indicator4=RSI&Button1=Update Chart&TickerID=<s:property value="scrip.id"/>', '<s:property value="scrip.name"/>',780,565)" onmouseout="hidetrail()">
					<s:property value="scrip.name"/>(BSE:<s:property value="scrip.bseCode"/>)(NSE:<s:property value="scrip.nseCode"/>)
				</a>
			</td>
			<td>
				<a href="<s:property value="newsFeedPost.postURL"/>" target="_blank" title="<s:property value="newsFeedPost.postShortContent"/>(Click URL to read more)">
					<s:property value="@java.lang.String@format('%.90s', newsFeedPost.postTitle)" />...(Read more)
				</a>
			</td>
			<td><s:property value="newsFeedPost.publishDate"/></td>			
		</tr>		
	</s:iterator>	
</table>
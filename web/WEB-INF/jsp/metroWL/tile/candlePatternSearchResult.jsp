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
		<th width="15%" scope="col">PATTERN</th>	
		<th width="15%" scope="col" title="Sort table Up Percentage">
			<s:if test="%{orderBy=='upPercent' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','upPercent','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Up%<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','upPercent','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Up%<span class="nobr">&nbsp;<s:if test="%{orderBy=='upPercent' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table Down Percentage">
			<s:if test="%{orderBy=='downPercent' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','downPercent','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Down%<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','downPercent','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Down%<span class="nobr">&nbsp;<s:if test="%{orderBy=='downPercent' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table Up Count">
			<s:if test="%{orderBy=='upCount' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','upCount','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Up Count<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','upCount','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Up Count<span class="nobr">&nbsp;<s:if test="%{orderBy=='upCount' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table Down Count">
			<s:if test="%{orderBy=='downCount' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','downCount','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Down Count<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','downCount','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Down Count<span class="nobr">&nbsp;<s:if test="%{orderBy=='downCount' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="10%" scope="col" title="Sort table Count Rank">
			<s:if test="%{orderBy=='countRank' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','countRank','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Count Rank<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','countRank','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">Count Rank<span class="nobr">&nbsp;<s:if test="%{orderBy=='countRank' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="15%" scope="col" title="Sort table ROI Rank">
			<s:if test="%{orderBy=='roiRank' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','roiRank','DESC','1','<s:property value="recordsPerPage"/>','${divToFill}')">ROI Rank<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','roiRank','ASC','1','<s:property value="recordsPerPage"/>','${divToFill}')">ROI Rank<span class="nobr">&nbsp;<s:if test="%{orderBy=='roiRank' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
	</tr>
	<s:iterator value="patternList">
		<tr class="rowEven">		
			<td><s:property value="PatternName"/></td>
			<td><s:property value="upPercent"/></td>
			<td><s:property value="downPercent"/></td>
			<td><s:property value="upCount"/></td>
			<td><s:property value="downCount"/></td>
			<td><s:property value="countRank"/></td>
			<td><s:property value="roiRank"/></td>
		</tr>		
	</s:iterator>	
</table>
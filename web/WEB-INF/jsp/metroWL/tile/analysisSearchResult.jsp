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
	</tr>
	<s:iterator value="analysisList">
		<tr class="rowEven">
			<td><s:property value="scrip.name"/></td>
			<td><s:property value="signalDate"/></td>			
			<td><s:property value="studyMaster.shortName"/>(<s:property value="studyMaster.study_parameter"/>)</td>
			<td><s:property value="signalCode"/></td>
			<td><s:property value="previousClose"/></td>
			<td>
				<s:if test="%{scrip.nseCmp!=null}">
					<s:property value="scrip.nseCmp"/>
				</s:if>
				<s:else>
					<s:property value="scrip.bseCmp"/>
				</s:else>
			</td>				
		</tr>		
	</s:iterator>	
</table>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>

<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Search results</td>
		<td class="tablenavigation">			
			<paging:paging currentPageNumber="${pageNumber}" formName="scripSearch" resultDivName="scripSearchResultTable" totalRecords="${totalRecords}" recordsPerPage="${recordsPerPage}"/>
		</td>
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>
		<th width="2%" scope="col">&nbsp;</th>
		<th width="25%" scope="col" title="Sort table by Name">
			<s:if test="%{orderBy=='name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','name','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Scrip name<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','name','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Scrip name<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="20%" scope="col" title="Sort table by Sector">
			<s:if test="%{orderBy=='sector.name' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','sector.name','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Sector(Industry)<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','sector.name','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Sector(Industry)<span class="nobr">&nbsp;<s:if test="%{orderBy=='sector.name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>		
			</s:else>
		</th>
		<th width="5%" scope="col" title="Sort table by BSECode">
			<s:if test="%{orderBy=='bseCode' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','bseCode','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">BSECode<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','bseCode','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">BSECode<span class="nobr">&nbsp;<s:if test="%{orderBy=='bseCode' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="5%" scope="col" title="Sort table by NSECode">
			<s:if test="%{orderBy=='nseCode' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','nseCode','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">NSECode<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','nseCode','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">NSECode<span class="nobr">&nbsp;<s:if test="%{orderBy=='nseCode' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="10%" scope="col" title="Sort table by ISIN">
			<s:if test="%{orderBy=='isinCode' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','isinCode','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">ISIN<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','isinCode','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">ISIN<span class="nobr">&nbsp;<s:if test="%{orderBy=='isinCode' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="10%" scope="col" title="Sort table by CMP">
			<s:if test="%{orderBy=='bseCmp' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','bseCmp','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">CMP<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','bseCmp','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">CMP<span class="nobr">&nbsp;<s:if test="%{orderBy=='bseCmp' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="5%" scope="col" title="Sort table by EPS">
			<s:if test="%{orderBy=='eps' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','eps','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">EPS<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','eps','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">EPS<span class="nobr">&nbsp;<s:if test="%{orderBy=='eps' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="5%" scope="col" title="Sort table by PE">
			<s:if test="%{orderBy=='pe' && orderType=='ASC'}">
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','pe','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">PE<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
			</s:if>
			<s:else>
				<a href="javascript:void(0)" onclick="sortMainPageTable('scripSearch','pe','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">PE<span class="nobr">&nbsp;<s:if test="%{orderBy=='pe' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
			</s:else>
		</th>
		<th width="13%">&nbsp;</th>
	</tr>
	<s:iterator value="scripList">
		<tr class="rowEven">
			<td><input type="radio" name="scripSelect" onclick="javascript:showScripDetails(<s:property value="id"/>)"/></td>
			<td><a href="javascript:void(0)" onclick="javascript:showScripDetails(<s:property value="id"/>)"><s:property value="name"/></a></a></td>
			<td><a href="javascript:void(0)" onclick="javascript:showScripDetails(<s:property value="id"/>)"><s:property value="sector.name"/></a></td>
			<td><a href="javascript:void(0)" onclick="javascript:showScripDetails(<s:property value="id"/>)"><s:property value="bseCode"/></a></td>
			<td><a href="javascript:void(0)" onclick="javascript:showScripDetails(<s:property value="id"/>)"><s:property value="nseCode"/></a></td>			
			<td><a href="javascript:void(0)" onclick="javascript:showScripDetails(<s:property value="id"/>)"><s:property value="isinCode"/></a></td>
			<td>
				<a href="javascript:void(0)" onclick="javascript:showScripDetails(<s:property value="id"/>)"><s:if test="%{bseCmp!=null}"><s:property value="bseCmp"/>(BSE)&nbsp;</s:if><s:if test="%{nseCmp!=null}"><s:property value="nseCmp"/>(NSE)</s:if></a>
			</td>
			<td style="text-align:right;">
				<s:if test="%{eps!=null}">
					<s:text name="rupees.format" >
						<s:param name="eps" value="%{eps}"/>
					</s:text>
				</s:if>
			</td>
			<td style="text-align:right;">
				<s:if test="%{pe!=null}">
					<s:text name="rupees.format" >
						<s:param name="pe" value="%{pe}"/>
					</s:text>
				</s:if>
			</td>
			<td><a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/prepareAddToWatchList.do','scripId=<s:property value="id"/>')">Add to watchlist</a></td>
		</tr>		
	</s:iterator>	
</table>
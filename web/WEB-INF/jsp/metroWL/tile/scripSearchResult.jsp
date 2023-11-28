<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>

<script language="Javascript">
	$(document).ready( function(){
		$("#scripPageScripSearchResultTable").tableSet({hidden: [2,6,8,9,10,11,12,13,14,15], icon: "<%=request.getContextPath()%>/metroWL/images/sh_icon.gif"});
		$('form#newsSearch #scripIds').val('${resultIds}');	
		$('form#newsSearch #pageNumber').val('1');	
		if (${resultIds}!='') {
			submitScreenForm('newsSearch','newsSearchResultTable');
		}		
		
		$('form#analysisSearch #scripIds').val('${resultIds}');	
		$('form#analysisSearch #pageNumber').val('1');
		if (${resultIds}!='') {
			submitScreenForm('analysisSearch','analysisSearchResultTable');
		}		
	});
</script>
<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Search results</td>
		<td class="tablenavigation">			
			<paging:paging currentPageNumber="${pageNumber}" formName="${sourceFormName}" resultDivName="scripSearchResultTable" totalRecords="${totalRecords}" recordsPerPage="${recordsPerPage}"/>
		</td>
	</tr>
</table>
<table class="datatable" id="scripPageScripSearchResultTable" summary="Deployments">
	<thead>
		<tr>		
			<th scope="col" title="Sort table by Name">
				<s:if test="%{orderBy=='name' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','name','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Scrip name<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','name','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Scrip name<span class="nobr">&nbsp;<s:if test="%{orderBy=='name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>
			<th scope="col" title="Sort table by Sector">
				<s:if test="%{orderBy=='sector.name' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','sector.name','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Sector<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','sector.name','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Sector<span class="nobr">&nbsp;<s:if test="%{orderBy=='sector.name' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>		
				</s:else>
			</th>
			<th scope="col" title="Sort table by BSECode">
				<s:if test="%{orderBy=='bseCode' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','bseCode','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">BSECode<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','bseCode','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">BSECode<span class="nobr">&nbsp;<s:if test="%{orderBy=='bseCode' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>
			<th scope="col" title="Sort table by NSECode">
				<s:if test="%{orderBy=='nseCode' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','nseCode','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">NSECode<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','nseCode','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">NSECode<span class="nobr">&nbsp;<s:if test="%{orderBy=='nseCode' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>		
			<th scope="col" title="Sort table by CMP">
				<s:if test="%{orderBy=='bseCmp' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','bseCmp','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">CMP<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','bseCmp','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">CMP<span class="nobr">&nbsp;<s:if test="%{orderBy=='bseCmp' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>
			<th scope="col" title="Sort table by EPS">
				<s:if test="%{orderBy=='eps' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','eps','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">EPS<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','eps','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">EPS<span class="nobr">&nbsp;<s:if test="%{orderBy=='eps' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>
			<th scope="col" title="Sort table by PE">
				<s:if test="%{orderBy=='pe' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','pe','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">PE<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','pe','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">PE<span class="nobr">&nbsp;<s:if test="%{orderBy=='pe' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>
			<th scope="col" title="Sort table by EPS/Book Percentage">
				<s:if test="%{orderBy=='(eps/bookValue)*100' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','(eps/bookValue)*100','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">EB%<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','(eps/bookValue)*100','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">EB%<span class="nobr">&nbsp;<s:if test="%{orderBy=='(eps/bookValue)*100' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>
			<th scope="col" title="Sort table by PB">
				<s:if test="%{orderBy=='(bseCmp/bookValue)*100' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','(bseCmp/bookValue)*100','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">PB<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','(bseCmp/bookValue)*100','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">PB<span class="nobr">&nbsp;<s:if test="%{orderBy=='(bseCmp/bookValue)*100' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>
			<th scope="col" title="Sort table by Net Profit">
				<s:if test="%{orderBy=='changeProfit' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','changeProfit','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Net Profit%<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','changeProfit','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Net Profit%<span class="nobr">&nbsp;<s:if test="%{orderBy=='changeProfit' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>		
			<th scope="col" title="Sort table by Average Four Qtr Netprofit">
				<s:if test="%{orderBy=='averageFourQtrNetprofit' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','averageFourQtrNetprofit','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Avg%Of4Qtr<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','averageFourQtrNetprofit','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Avg4Qtr<span class="nobr">&nbsp;<s:if test="%{orderBy=='averageFourQtrNetprofit' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>
			<th scope="col" title="Sort table by Consecutive Raising Profit Qtr Count">
				<s:if test="%{orderBy=='raisingProfitQtrCount' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','raisingProfitQtrCount','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">ProfitQtrCount<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','raisingProfitQtrCount','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">ProfitQtrCount<span class="nobr">&nbsp;<s:if test="%{orderBy=='raisingProfitQtrCount' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>		
			<th scope="col" title="Sort table by Result Updated Date">
				<s:if test="%{orderBy=='resultDate' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','resultDate','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Result Date<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','resultDate','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Result Date<span class="nobr">&nbsp;<s:if test="%{orderBy=='resultDate' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>
			<th scope="col" title="Sort table by dividendYield">
				<s:if test="%{orderBy=='dividendYield' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','dividendYield','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Dividend Yield<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','dividendYield','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">Dividend Yield<span class="nobr">&nbsp;<s:if test="%{orderBy=='dividendYield' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>
			<th scope="col" title="Sort table by Profit Margin Percent">
				<s:if test="%{orderBy=='profitMarginPercentage' && orderType=='ASC'}">
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','profitMarginPercentage','DESC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">ProfitMargin<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a>
				</s:if>
				<s:else>
					<a href="javascript:void(0)" onclick="sortMainPageTable('${sourceFormName}','profitMarginPercentage','ASC','1','<s:property value="recordsPerPage"/>','scripSearchResultTable')">ProfitMargin<span class="nobr">&nbsp;<s:if test="%{orderBy=='profitMarginPercentage' && orderType=='DESC'}"><img src="<%=request.getContextPath()%>/metroWL/images/sort_down.gif" alt="Sorted Descending "></s:if></span></a>
				</s:else>
			</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="scripList">
			<tr class="rowEven">			
				<td>
					<a title="<s:property value="name"/>" href="javascript:void(0)" onclick="javascript:getScripInfoAndPopulateDiv('<%=request.getContextPath()%>/scripInfo.do',<s:property value="id"/>,'ScripInfoDiv')">
						<s:property value="shortName"/>
					</a>
					<a href="javascript:openScripPage('<s:property value="nseCode"/>','<s:property value="bseCode"/>')">
						<img src="<%=request.getContextPath()%>/metroWL/images/stock_filter.png" title="Filter this Scrip">
					</a>
					<a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/prepareAddToWatchList.do','scripId=<s:property value="id"/>')">
						<img src="<%=request.getContextPath()%>/metroWL/images/addnew.png" title="Add to Watchlist">
					</a>
				</td>
				<td><a href="javascript:void(0)" onclick="javascript:getScripInfoAndPopulateDiv('<%=request.getContextPath()%>/scripInfo.do',<s:property value="id"/>,'ScripInfoDiv')"><s:property value="sector.name"/></a></td>
				<td><a href="javascript:void(0)" onclick="javascript:getScripInfoAndPopulateDiv('<%=request.getContextPath()%>/scripInfo.do',<s:property value="id"/>,'ScripInfoDiv')"><s:property value="bseCode"/></a></td>
				<td><a title="<s:property value="isinCode"/>" href="javascript:void(0)" onclick="javascript:getScripInfoAndPopulateDiv('<%=request.getContextPath()%>/scripInfo.do',<s:property value="id"/>','ScripInfoDiv')"><s:property value="nseCode"/>(<s:property value="seriesType"/>)</a></td>			
				<td>
					<a href="javascript:void(0)" onclick="javascript:getScripInfoAndPopulateDiv('<%=request.getContextPath()%>/scripInfo.do',<s:property value="id"/>,'ScripInfoDiv')">
						<s:property value="cmp"/>
					</a>
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
				<td style="text-align:right;">
					<s:text name="rupees.format" >
							<s:param name="epsToBookPercentage" value="%{epsToBookPercentage}"/>
						</s:text>
				</td>
				<td style="text-align:right;">
					<s:text name="rupees.format" >
							<s:param name="pbRatio" value="%{pbRatio}"/>
						</s:text>
				</td>
				<td style="text-align:right;">
					<s:text name="rupees.format" >
							<s:param name="changeProfit" value="%{changeProfit}"/>
						</s:text>
				</td>
				<td style="text-align:right;">
					<s:text name="rupees.format" >
							<s:param name="averageFourQtrNetprofit" value="%{averageFourQtrNetprofit}"/>
						</s:text>
				</td>
				<td style="text-align:center;"><s:property value="raisingProfitQtrCount"/></td>
				<td><s:property value="resultDate"/></td>
				<td><s:property value="dividendYield"/></td>
				<td style="text-align:right;">
					<s:text name="rupees.format" >
							<s:param name="profitMarginPercentage" value="%{profitMarginPercentage}"/>
						</s:text>
				</td>
			</tr>		
		</s:iterator>
	</tbody>
</table>
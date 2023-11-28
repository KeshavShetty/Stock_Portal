<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Watchlist diff results</td>		
	</tr>
</table>
<table id="genericTableFormtable" summary="Deployments">	
	<tr>
		<th width="33%">First Watchlist Scrips</th>
		<th width="33%">Common Scrips</th>
		<th width="33%">Next Watchlist Scrips</th>
	</tr>	
	<tr>
		<td style="vertical-align: top;">
			<table class="datatable" id="genericTableFormtable" summary="Deployments">	
				<s:iterator value="leftWatchlistScrips" status="rowStatus">
					<tr <s:if test="#rowStatus.even == true">class="rowEven"</s:if><s:else>class="rowOdd"</s:else> >
						<td>
							<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="id"/>,800,600)">
								<s:property value="#rowStatus.count"/>.<s:property value="name"/>								
							</a>
						</td>
					</tr>
				</s:iterator>				
			</table>
		</td>
		<td style="vertical-align: top;">
			<table class="datatable" id="genericTableFormtable" summary="Deployments">	
				<s:iterator value="commonScrips" status="rowStatus">
					<tr <s:if test="#rowStatus.even == true">class="rowEven"</s:if><s:else>class="rowOdd"</s:else>>
						<td>
							<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="id"/>,800,600)">
								<s:property value="#rowStatus.count"/>.<s:property value="name"/>								
							</a>
						</td>					
					</tr>
				</s:iterator>				
			</table>
		</td>
		<td style="vertical-align: top;">
			<table class="datatable" id="genericTableFormtable" summary="Deployments">	
				<s:iterator value="rightWatchlistScrips" status="rowStatus">
					<tr <s:if test="#rowStatus.even == true">class="rowEven"</s:if><s:else>class="rowOdd"</s:else>>
						<td>
							<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="id"/>,800,600)">
								<s:property value="#rowStatus.count"/>.<s:property value="name"/>								
							</a>
						</td>					
					</tr>
				</s:iterator>				
			</table>
		</td>
	</tr>
</table>
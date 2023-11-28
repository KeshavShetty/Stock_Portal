<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>

<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Watchlst Transaction</td>
		<td class="tablenavigation">			
			Todo: Table navigation???
		</td>
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>	
		<th width="10%" scope="col">
			Transaction Date
		</th>
		<th width="10%" scope="col">
			Scrip
		</th>	
		<th width="10%" scope="col">
			Transaction Type
		</th>
		<th width="10%" scope="col">
			Exchange
		</th>
		<th width="10%" scope="col">
			Reference
		</th>
		<th width="10%" scope="col">
			Quantity
		</th>
		<th width="10%" scope="col">
			Rate
		</th>
		<th width="10%" scope="col">
			Amount
		</th>
		<th width="10%" scope="col">
			Brokerage
		</th>
		<th width="10%" scope="col">
			Total
		</th>						
	</tr>
	<s:iterator value="wlTransactions">
		<tr class="rowEven">
			<td><s:property value="transactionDate"/></td>
			<td>
				<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="watchlistItem.scrip.id"/>,800,600)"><s:property value="watchlistItem.scrip.name"/></a>
				<a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/prepareAddWLTransaction.do','watchListId=<s:property value="watchlistItem.watchlist.id"/>&scripId=<s:property value="watchlistItem.scrip.id"/>')">
					<img src="<%=request.getContextPath()%>/metroWL/images/addnew.png" title="Add new transaction">
				</a>
				<a href="javascript:openScripPage('<s:property value="watchlistItem.scrip.nseCode"/>','<s:property value="watchlistItem.scrip.bseCode"/>')">
					<img src="<%=request.getContextPath()%>/metroWL/images/view_detail.png" title="View Scrip details">
				</a>
				
			</td>			
			<s:if test="%{transactionType==false}">
				<td style="background: none repeat scroll 0% 0% rgb(50, 60, 107); color: #FFF;">Buy</td>
			</s:if>
			<s:else>
				<td style="background: none repeat scroll 0% 0% rgb(246, 26, 10); color: #FFF;">Sell</td>
			</s:else>			
			<td>
				<s:if test="%{exchange==false}">
					BSE
				</s:if>
				<s:else>
					NSE
				</s:else>
			</td>
			<td><s:property value="settlementNumber"/></td>
			<td><s:property value="quantity"/></td>
			<td><s:property value="rate"/></td>
			<td><s:property value="amount"/></td>
			<td><s:property value="brokerage"/></td>
			<td><s:property value="total"/></td>
		</tr>
	</s:iterator>
	<tr class="rowEven">
		<td colspan="8">Total</td>
		<td>
			<s:text name="rupees.format" >
				<s:param name="totalBrokerageInDateWise" value="%{totalBrokerageInDateWise}"/>					
			</s:text>
		</td>
		<td>
			<s:text name="rupees.format" >
				<s:param name="totalamountInDateWise" value="%{totalamountInDateWise}"/>					
			</s:text>
		</td>	
	</tr>	
</table>
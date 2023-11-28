<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>


<script language="Javascript">
	$(document).ready( function(){
		$('.datatableInner').toggle();
	});
</script>

<table class="tablebuttonbar" summary>
	<tr>
		<td class="tablecontrols">Watchlst Items</td>
		<td class="tablenavigation">			
			Todo: Table navigation???
		</td>
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>	
		<th width="20%" scope="col">
			Scrip
		</th>	
		<th width="10%" scope="col">
			Stock In Hand
		</th>
		<th width="10%" scope="col">
			Buy rate(Avg)
		</th>
		<th width="20%" scope="col">
			CMP
		</th>
		<th width="10%" scope="col">
			Gain (P/L)
		</th>
		<th width="10%" scope="col">
			SIH Worth
		</th>
		<th width="10%" scope="col">
			MoneyFlow
		</th>
		<th width="10%" scope="col">
			Net P/L
		</th>
	</tr>
	<s:iterator value="wlItems">
		<tr class="rowEven">
			<td>
				<a title="Expand" href="javascript:void(0)" onclick="javascript:toggleDiv('<s:property value="id"/>_<s:property value="scrip.id"/>')">
					<img src="<%=request.getContextPath()%>/metroWL/images/expand.png">
				</a>
				<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do', 'jqIndex='+<s:property value="scrip.id"/> , 800, 600)"><s:property value="scrip.name"/></a>
				<a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/prepareAddWLTransaction.do','watchListId=<s:property value="watchlist.id"/>&scripId=<s:property value="scrip.id"/>')">
					<img src="<%=request.getContextPath()%>/metroWL/images/addnew.png" title="Add new transaction">
				</a>
				<a href="javascript:openScripPage('<s:property value="scrip.nseCode"/>','<s:property value="scrip.bseCode"/>')">
					<img src="<%=request.getContextPath()%>/metroWL/images/view_detail.png" title="View Scrip details">
				</a>
				<a onclick="javascript:showFloatingPopup('/portal/deleteWatchlistItem.do','selectedWLItemId=<s:property value="id"/>')" href="javascript:void(0)">
					<img src="<%=request.getContextPath()%>/metroWL/images/delete.png" title="Delete Scrip from Watchlist">
				</a>
				<a onclick="javascript:showFloatingPopup('/portal/rebuildWatchlistItem.do','selectedWLItemId=<s:property value="id"/>')" href="javascript:void(0)">
					<img src="<%=request.getContextPath()%>/metroWL/images/rebuild.png" title="Rebuild Watchlist Item">
				</a>
			</td>
			<td><s:property value="stockInHand"/></td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="averageBuyRate" value="%{averageBuyRate}"/>					
				</s:text>
			</td>
			<td><s:property value="scrip.bseCmp"/>(BSE)/<s:property value="scrip.nseCmp"/>(NSE)</td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="PAndL" value="%{(scrip.cmp-averageBuyRate)*stockInHand}"/>					
				</s:text>
			</td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="SIHWorth" value="%{scrip.cmp*stockInHand}"/>					
				</s:text>
			</td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="totalMoneyflow" value="%{totalMoneyflow}"/>
				</s:text>
			</td>
			<td>
				<s:text name="rupees.format" >
					<s:param name="netPL" value="%{scrip.cmp*stockInHand + totalMoneyflow}"/>
				</s:text>
			</td>
		</tr>
		
			<s:if test="%{wlTransactions.size>0}">
				<tr>
					<td colspan="8">
						<table class="datatableInner" id="<s:property value="id"/>_<s:property value="scrip.id"/>" summary="Deployments" width="95%">
							<tr>	
								<th width="12%" scope="col">
									Transaction Date
								</th>	
								<th width="12%" scope="col">
									Transaction Type
								</th>
								<th width="12%" scope="col">
									Exchange
								</th>
								<th width="14%" scope="col">
									Reference
								</th>
								<th width="12%" scope="col">
									Quantity
								</th>
								<th width="12%" scope="col">
									Rate
								</th>
								<th width="12%" scope="col">
									Brokerage
								</th>
								<th width="13%" scope="col">
									Total
								</th>						
							</tr>
							<s:iterator value="wlTransactions">
								<tr class="rowEven">
									<td>
										<a onclick="javascript:showFloatingPopup('/portal/deleteTransaction.do','selectedTransactionId=<s:property value="id"/>')" href="javascript:void(0)">
											<img src="<%=request.getContextPath()%>/metroWL/images/delete.png" title="Delete Scrip from Transaction">
										</a>
										<s:property value="transactionDate"/>
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
									<td><s:property value="brokerage"/></td>
									<td><s:property value="total"/></td>
								</tr>
							</s:iterator>
							<tr class="rowEven">
								<td colspan="7" style="text-align: right;">
									Total money flow:
								</td>
								<td>
									<s:text name="rupees.format" >
										<s:param name="totalMoneyflow" value="%{totalMoneyflow}"/>
									</s:text>
								</td>
							</tr>
						</table>
					</td>
				</tr>				
			</s:if>
	</s:iterator>	
	<tr class="rowEven">
		<td colspan="5"> Total</td>
		<td>
			<s:text name="rupees.format" >
				<s:param name="sihWorthInScripWise" value="%{sihWorthInScripWise}"/>					
			</s:text>
		</td>
		<td>&nbsp;</td>
		<td>
			<s:text name="rupees.format" >
				<s:param name="netPAndL" value="%{netPAndL}"/>					
			</s:text>
		</td>
	</tr>
	<tr class="rowEven">
		<td colspan="6">&nbsp;</td>
		<td>ROI Percentage:</td>
		<td>
			<s:text name="rupees.format" >
				<s:param name="roi" value="%{netPAndL*100f/(sihWorthInScripWise-netPAndL)}"/>					
			</s:text>%
		</td>
	</tr>
	<tr class="rowEven">
		<td>Max capital used:</td>
		<td>
			<s:text name="rupees.format" >
				<s:param name="maxCapitalUsed" value="%{watchlist.maxCapitalUsed}"/>					
			</s:text>
		</td>
		<td>Available capital:</td>
		<td>
			<s:text name="rupees.format" >
				<s:param name="availableCapital" value="%{watchlist.availableCapital}"/>					
			</s:text>
		</td>
		<td>SIH worth</td>
		<td>
			<s:text name="rupees.format" >
				<s:param name="sihWorthInScripWise" value="%{sihWorthInScripWise}"/>					
			</s:text>
		</td>
		<td colspan="2">ROI
			(<s:text name="rupees.format" >
				<s:param name="netRoiAmount" value="%{(watchlist.availableCapital+sihWorthInScripWise)-watchlist.maxCapitalUsed}"/>					
			</s:text>)&nbsp;
			<s:text name="rupees.format" >
				<s:param name="netRoi" value="%{((watchlist.availableCapital+sihWorthInScripWise)-watchlist.maxCapitalUsed)*100f/watchlist.maxCapitalUsed}"/>					
			</s:text>
		</td>
	</tr>
</table>
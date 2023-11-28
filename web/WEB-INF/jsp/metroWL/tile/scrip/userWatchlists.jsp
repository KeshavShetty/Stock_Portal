<%@ taglib prefix="s" uri="/struts-tags" %>
<table class="datatable" id="genericTableFormtable" summary="Deployments">

	<s:if test="%{watchlistItems.size>0}">
		<tr class="rowEven">
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr class="rowEven">
			<td colspan="4">
				<table class="datatable" id="genericTableFormtable" summary="Deployments">	
					<tr>	
						<th width="20%" scope="col">
							Member of Watchlist
						</th>							
						<th width="10%" scope="col">
							Stock In Hand
						</th>
						<th width="10%" scope="col">
							Buy rate(Avg)
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
						<th width="20%" scope="col">
							Action
						</th>
					</tr>
					<s:iterator value="watchlistItems">
						<tr class="rowEven">
							<td>
								<s:property value="watchlist.name"/>
							</td>							
							<td>
								<s:property value="stockInHand"/>
								<s:if test="%{holdSuggestion!=null}">
									<s:if test="%{stockInHand>0}">
										(<b><s:property value="holdSuggestion"/></b>)
									</s:if>
								</s:if>
							</td>
							<td>
								<s:text name="rupees.format" >
									<s:param name="averageBuyRate" value="%{averageBuyRate}"/>					
								</s:text>
							</td>
							
							<td>
								<s:if test="%{scrip.nseCmp!=null}">
									<s:text name="rupees.format" >
										<s:param name="PAndL" value="%{(scrip.nseCmp-averageBuyRate)*stockInHand}"/>					
									</s:text>
								</s:if>
								<s:else>
									<s:text name="rupees.format" >
										<s:param name="PAndL" value="%{(scrip.bseCmp-averageBuyRate)*stockInHand}"/>					
									</s:text>
								</s:else>											
							</td>
							<td>
								<s:if test="%{scrip.nseCmp!=null}">
									<s:text name="rupees.format" >
										<s:param name="SIHWorth" value="%{scrip.nseCmp*stockInHand}"/>					
									</s:text>
								</s:if>
								<s:else>
									<s:text name="rupees.format" >
										<s:param name="SIHWorth" value="%{scrip.bseCmp*stockInHand}"/>					
									</s:text>
								</s:else>											
							</td>
							<td>
								<s:if test="%{stockInHand>0}">
									<s:text name="rupees.format" >
										<s:param name="totalMoneyflow" value="%{totalMoneyflow}"/>
									</s:text>
								</s:if>
							</td>
							<td>
								<s:if test="%{scrip.nseCmp!=null}">
									<s:if test="%{stockInHand>0}">
										<s:text name="rupees.format" >
											<s:param name="netPL" value="%{scrip.nseCmp*stockInHand + totalMoneyflow}"/>
										</s:text>
										(<s:text name="rupees.format" >
											<s:param name="netPLPercent" value="%{(scrip.nseCmp*stockInHand + totalMoneyflow)*-100/totalMoneyflow}"/>
										</s:text>%)
									</s:if>
								</s:if>
								<s:else>
									<s:if test="%{stockInHand>0}">
										<s:text name="rupees.format" >
											<s:param name="netPL" value="%{scrip.bseCmp*stockInHand + totalMoneyflow}"/>
										</s:text>
										(<s:text name="rupees.format" >
											<s:param name="netPLPercent" value="%{(scrip.bseCmp*stockInHand + totalMoneyflow)*-100/totalMoneyflow}"/>
										</s:text>%)
									</s:if>
								</s:else>
							</td>
							<td>
								<a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/prepareAddWLTransaction.do','watchListId=<s:property value="watchlist.id"/>&scripId=<s:property value="scrip.id"/>')">
									<img src="<%=request.getContextPath()%>/metroWL/images/addnew.png" title="Add new transaction">
								</a>
								<a onclick="javascript:showFloatingPopup('/portal/deleteWatchlistItem.do','selectedWLItemId=<s:property value="id"/>')" href="javascript:void(0)">
									<img src="<%=request.getContextPath()%>/metroWL/images/delete.png" title="Delete Scrip from Watchlist">
								</a>
								<a onclick="javascript:showFloatingPopup('/portal/rebuildWatchlistItem.do','selectedWLItemId=<s:property value="id"/>')" href="javascript:void(0)">
									<img src="<%=request.getContextPath()%>/metroWL/images/rebuild.png" title="Rebuild Watchlist Item">
								</a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</td>
		</tr>
	</s:if>
	<s:else>
		<tr class="rowEven">
			<th colspan="4">No Watchlist found</th>
		</tr>
	</s:else>
	
	<s:if test="%{indexScripsEod.size>0}">
		<tr class="rowEven">
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr class="rowEven">
			<td colspan="4">
				<table class="datatable" id="genericTableFormtable" summary="Deployments">	
					<tr>	
						<th scope="col">
							Member of Index
						</th>
						<th scope="col">
							Stochastic
						</th>
					</tr>
					<s:iterator value="indexScripsEod">
						<tr>
							<td>
								<s:property value="scrip.name"/>
							</td>
							<td>
								<s:if test="%{priceMoveTrend==false}">
									<img src="<%=request.getContextPath()%>/metroWL/images/down_r.gif">
								</s:if>
								<s:if test="%{priceMoveTrend==true}">
									<img src="<%=request.getContextPath()%>/metroWL/images/up_g.gif">
								</s:if>
								<s:text name="rupees.format" >
									(<s:param name="stochasticValue" value="%{stochasticValue}"/>)
								</s:text>
							</td>
						</tr>
					</s:iterator>
				</table>
			</td>
		</tr>
	</s:if>
	
	<tr class="rowEven">
		<td colspan="4">
			<div id="manualWatlistLoader">
				<table class="datatable" id="genericTableFormtable" summary="Deployments">
					<tr>
						<td>
						<a href="javascript:void(0)" onclick="javascript:populateBodyDivThruAjax('/loadWatchlistManually.do?id=${scrip.id}','manualWatlistLoader', true)">
							Load other Dynamic Virtual Watchlist in which this scrip is a member
						</a>
					</tr>
				</table>
			</div>
		</td>
	</tr>	
</table>
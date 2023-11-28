<%@ taglib prefix="s" uri="/struts-tags" %>

<script language="Javascript">
function updateStoplossTarget() {
	var stoplosspercent = document.getElementById("stoplosspercent").value;
	var targetpercent = document.getElementById("targetpercent").value;
	var ltp = document.getElementById("ltp").value;
	document.getElementById("stoplosspointsLong").value = ltp - ltp*stoplosspercent/100;
	document.getElementById("targetpointsLong").value = ltp - ltp*targetpercent/100;
}
</script>

<table>
	<tr>
		<td>
			<table class="datatable" id="genericTableFormtable" summary="Deployments">
				
				<tr class="rowOdd">
					<td colspan="2">
						<span class="fh_1">C</span>
						<s:if test="%{scrip.bseCmp!=null}"> CMP: <b>${scrip.bseCmp}</b>(BSE) - [Prev close: ${scrip.bsePreviousClose}]</s:if>
					</td>
					<td colspan="2">
						<span class="fh_1">C</span>
						<s:if test="%{scrip.nseCmp!=null}"> 
							CMP: <b>${scrip.nseCmp}</b>(NSE) - [Prev close: ${scrip.nsePreviousClose}]
							SL[4%]=<b>
							<s:text name="rupees.format" >
								<s:param name="scrip.ema100dayPercent" value="%{scrip.nseCmp-(scrip.nseCmp*4.0/100.0)}"/>
							</s:text></b>&nbsp;&nbsp;&nbsp;
							Tgt[4%]=<b>
							<s:text name="rupees.format" >
								<s:param name="scrip.ema100dayPercent" value="%{scrip.nseCmp+(scrip.nseCmp*4.0/100.0)}"/>
							</s:text></b>
						</s:if>
					</td>
				</tr>
				<tr class="rowEven">
					<td colspan="2"><span class="fh_2">H</span> 52 Week: <s:if test="%{scrip.bse52weekHigh!=null}">High:<b>${scrip.bse52weekHigh}</b></s:if> <s:if test="%{scrip.bse52weekLow!=null}"><span class="fh_2">L</span> Low:<b>${scrip.bse52weekLow}</b></s:if></td>
					<td>
						<span class="fh_3">V</span> Volume:<s:if test="%{scrip.todaysVolume!=null}"><b>${scrip.todaysVolume}</b>(Previous:${scrip.previousvolume})</s:if>
					</td>
					<td>
						<span class="fh_4">G</span>
						Gain: 
						<s:if test="%{scrip.bseTodaysGain!=null}">
							<s:text name="rupees.format" >
								<s:param name="scrip.bseTodaysGain" value="%{scrip.bseTodaysGain}"/>
							</s:text>
							(BSE)
						</s:if>
						<s:if test="%{scrip.nseTodaysGain!=null}">
							<s:text name="rupees.format" >
								<s:param name="scrip.nseTodaysGain" value="%{scrip.nseTodaysGain}"/>
							</s:text>
							(NSE)
						</s:if>
						<s:if test="%{scrip.dayBarSizePercent!=null}">Day Bar Size % : <b>${scrip.dayBarSizePercent}</b></s:if>
					</td>
				</tr>
				<tr class="rowOdd">
					<td colspan="2">
						<span class="fh_5">AV</span> Avg Volume(2W): 
						<s:if test="%{scrip.bseAverageVolume!=null}"><b>${scrip.bseAverageVolume}</b>(BSE)</s:if>
						<s:if test="%{scrip.nseAverageVolume!=null}"><b>${scrip.nseAverageVolume}</b>(NSE)</s:if>
					</td>
					<td>
						<s:if test="%{scrip.averageTurnover!=null}"><span class="fh_5">AT</span> Avg Trading Turnover(2W):
							<b><s:text name="rupees.format" >
								<s:param name="scrip.averageTurnover" value="%{scrip.averageTurnover}"/>
							</s:text></b>
						</s:if>		   
					</td>
					<td>
						<span class="fh_6">VA</span> Volume/Avg Volume(NSE):<s:if test="%{scrip.nseVolumeVsAvgVolumeRatio!=null}"><b>${scrip.nseVolumeVsAvgVolumeRatio}</b>(Spike in turnover amount <b><s:text name="rupees.format" ><s:param name="scrip.nseVolumeVsAvgVolumeAmount" value="%{scrip.nseVolumeVsAvgVolumeAmount}"/></s:text> Cr</b>)</s:if>	
					</td>
				</tr>
				
				<tr class="rowEven">
					<td colspan="2"><span class="fh_5"> MA</span> AverageVolume (1M): <s:if test="%{scrip.averageVolume1M!=null}"><b>${scrip.averageVolume1M}</b></s:if></td>
					<td>
						<span class="fh_7">SP</span> Support Price:<s:if test="%{scrip.supportPrice3M!=null}"><b>${scrip.supportPrice3M}</b></s:if>
					</td>
					<td>
						<span class="fh_7">SV</span> Support Volume:<s:if test="%{scrip.supportVolume3M!=null}"><b>${scrip.supportVolume3M}</b></s:if>
					</td>
				</tr>
				
				<tr class="rowEven">
					<td colspan="2"><span class="fh_14">LQ</span>  Last Qtr result announced for: <s:if test="%{scrip.latestFinancialReportQuarterId!=null}"><b>${scrip.latestFinancialReportQuarterId}</b></s:if></td>
					<td colspan="2">
						<span class="fh_7">RD</span> Last result updated on:
						<s:if test="%{scrip.resultDate!=null}"><b>
							<s:date name="scrip.resultDate" format="dd/MM/yyyy" /></b>
						</s:if>&nbsp;&nbsp;
						<s:if test="%{scrip.nextResultDate!=null}">
							(Actual result date: <b><s:date name="scrip.nextResultDate" format="dd/MM/yyyy" /></b>)
						</s:if>
					</td>
				</tr>
				<tr class="rowEven">
					<td>
						<span class="fh_5">WR</span> WL Score Rank: <s:if test="%{scrip.wlScoreRank!=null}"><b>${scrip.wlScoreRank}</b></s:if>&nbsp;
						<span class="fh_5">GR</span> Growth Rank: <s:if test="%{scrip.growthRank!=null}"><b>${scrip.growthRank}</b></s:if>
					</td>
					<td>
						
					</td>
					<td>
						<span class="fh_14">WLC</span> WL Count: <s:if test="%{scrip.wlCount!=null}"><b>${scrip.wlCount}</b></s:if>&nbsp;
						<span class="fh_14">WS</span> WL Score: <s:if test="%{scrip.wlScore!=null}"><b>${scrip.wlScore}</b></s:if>
					</td>
					<td>
						<span class="fh_7">SGPP</span> 
						Standalone GPP: <s:if test="%{scrip.standlaoneGrowthPromisePercentage!=null}"><b>${scrip.standlaoneGrowthPromisePercentage}</b></s:if>
						<s:if test="%{scrip.consolidatedGrowthPromisePercentage!=null}">
							<span class="fh_7">CGPP</span> Consolidated GPP:<b>${scrip.consolidatedGrowthPromisePercentage}</b>
						</s:if>
					</td>
					
				</tr>
				<tr class="rowOdd">
					<td>
						<span class="fh_7">ENQSQG</span> 
						Estimated Next Qtr SQG: <s:if test="%{scrip.estimatedNextQtrSqg!=null}"><b>${scrip.estimatedNextQtrSqg}</b></s:if>						
					</td>
					<td>
						<span class="fh_7">Lot Size</span> 
						FnO Lot size: <s:if test="%{scrip.estimatedNextQtrSqg!=null}"><b>${scrip.lotSize}</b></s:if>
					</td>
					<td>
						<span class="fh_7">SGPP</span> 
						Grahams Score Vs CMP: 
						<b>
						<s:text name="rupees.format" >
							<s:param name="scrip.grahamScoreVsCmp" value="%{scrip.grahamScoreVsCmp}"/>
						</s:text></b>						
					</td>
					<td>
						Entry Wl Score:
						<b>
						<s:text name="rupees.format" >
							<s:param name="scrip.entryWlScore" value="%{scrip.entryWlScore}"/>
						</s:text>
						</b>
					</td>
				</tr>
				<tr class="rowEven">
					<td colspan="4" style="text-align: right;">
					<s:if test="%{scrip.nseCmp!=null}"> 
							SL[1%]=<b>
							<s:text name="rupees.format" >
								<s:param name="scrip.ema100dayPercent" value="%{(scrip.nseCmp*1.0/100.0)}"/>
							</s:text></b>&nbsp;&nbsp;&nbsp;
							Tgt[4%]=<b>
							<s:text name="rupees.format" >
								<s:param name="scrip.ema100dayPercent" value="%{(scrip.nseCmp*4.0/100.0)}"/>
							</s:text></b>
						</s:if>
					</td>
				</tr>
				<tr class="rowEven">
					<td colspan="4">
						<table class="datatable" id="genericTableFormtable" summary="Deployments">	
							<tbody>
								<tr>	
									<th scope="col" colspan="4">
										 Financial Ratios
									</th>
								</tr>
								<tr>
									<td><s:if test="%{scrip.bookValue!=null}"><span class="fh_8">B</span> Book Value: <b></b>${scrip.bookValue}</b></s:if></td>
									<td><s:if test="%{scrip.faceValue!=null}"><span class="fh_8">F</span> Face Value: <b>${scrip.faceValue}</b></s:if></td>
									<td><s:if test="%{scrip.eps!=null}"><span class="fh_8">E</span> EPS:<b>${scrip.eps}</b></s:if></td>
									<td>
										<s:if test="%{scrip.pe!=null}"><span class="fh_9"> PE</span>PE:<b>
											<s:text name="rupees.format" >
												<s:param name="scrip.pe" value="%{scrip.pe}"/>
											</s:text></b>
										</s:if>
									</td>
								</tr>
								<tr>
									<td>
										<span class="fh_9">PB</span> PB:<b>
										<s:text name="rupees.format" >
											<s:param name="scrip.pbRatio" value="%{scrip.pbRatio}"/>
										</s:text></b>
									</td>
									<td><s:if test="%{scrip.dividendYield!=null}"><span class="fh_9">D</span> Dividend Yield:<b>${scrip.dividendYield}</b></s:if></td>
									<td><s:if test="%{scrip.marketcap!=null}"><span class="fh_9">MCAP</span> Marketcap :<b>${scrip.marketcap}</b></s:if></td>
									<td>
										<s:if test="%{scrip.industryPe!=null}"><span class="fh_9">IndPE</span>Industry PE:<b>
											<s:text name="rupees.format" >
												<s:param name="scrip.industryPe" value="%{scrip.industryPe}"/>
											</s:text></b>
										</s:if>
									</td>
								</tr>
								<tr>
									<td><s:if test="%{scrip.quick_ratio!=null}"><span class="fh_11">Q</span> Quick Ratio:<b>${scrip.quick_ratio}</b></s:if></td>
									<td><s:if test="%{scrip.current_ratio!=null}"><span class="fh_11">C</span> Current Ratio:<b>${scrip.current_ratio}</b></s:if></td>
									<td><s:if test="%{scrip.cashRatio!=null}"><span class="fh_11">CR</span> Cash Ratio:<b>${scrip.cashRatio}</b></s:if></td>
									<td><s:if test="%{scrip.debtEquityRatio!=null}"><span class="fh_11">DR</span> Debt Equity Ratio:<b>${scrip.debtEquityRatio}</b></s:if></td>
								</tr>
								<tr>
									<td><s:if test="%{scrip.returnOnEquity!=null}"><span class="fh_12">RE</span> ROE:<b>${scrip.returnOnEquity}(% Change ${scrip.changeInRoe})</b></s:if></td>
									<td><s:if test="%{scrip.returnOnAssets!=null}"><span class="fh_12">RA</span> ROA :<b>${scrip.returnOnAssets}</b></s:if></td>
									<td><s:if test="%{scrip.returnOnCapitalEmployed!=null}"><span class="fh_12">RC</span> ROCE:<b>${scrip.returnOnCapitalEmployed}(% Change ${scrip.changeInRoce})</b></s:if></td>
									<td><s:if test="%{scrip.fixedAssetsTurnover!=null}"><span class="fh_12">FA</span> Fixed Assets Turnover:<b>${scrip.fixedAssetsTurnover}</b></s:if></td>
								</tr>
								<tr>
									<td><s:if test="%{scrip.cashFlowsToLongTermDebt!=null}"><span class="fh_13">CD</span> Cash Flow To Debt(LongTerm):<b>${scrip.cashFlowsToLongTermDebt}</b></s:if></td>
									<td><s:if test="%{scrip.netProfitMargin!=null}"><span class="fh_13">NM</span> Profit Margin(Net):<b>${scrip.netProfitMargin}</b></s:if></td>
									<td><s:if test="%{scrip.operatingProfitMargin!=null}"><span class="fh_13">OM</span> Profit Margin(Operating):<b>${scrip.operatingProfitMargin}</b></s:if></td>
									<td><s:if test="%{scrip.grossProfitMargin!=null}"><span class="fh_13">GM</span> Profit Margin(Gross):<b>${scrip.grossProfitMargin}</b></s:if></td>
								</tr>
								
								<tr>
									<td><span class="fh_13">PBT</span> PBT Margin Change% (LastQtr):<s:if test="%{scrip.profitmarginPbtchangepercent!=null}"><b>${scrip.profitmarginPbtchangepercent}</b></s:if></td>
									<td><span class="fh_13">PBT SQG</span> PBT Margin Change% (SQG):<s:if test="%{scrip.profitmarginPbtsameqtrchangepercent!=null}"><b>${scrip.profitmarginPbtsameqtrchangepercent}</b></s:if></td>
									<td><span class="fh_13">OM</span> Target Price By PB:<s:if test="%{scrip.targetpricebypb!=null}"><b>${scrip.targetpricebypb}</b></s:if></td>
									<td><span class="fh_13">GM</span> Target Price By PE:<s:if test="%{scrip.targetpricebype!=null}"><b>${scrip.targetpricebype}</b></s:if></td>
								</tr>
								
							</tbody>
						</table>
					</td>
				</tr>
				<tr class="rowEven">
						<td colspan="4">&nbsp;</td>
					</tr>
				<tr class="rowEven">
					<td colspan="4">
						<table class="datatable" id="genericTableFormtable" summary="Deployments">	
							<tbody>
								<tr>	
									<th scope="col" colspan="4">
										Custom statistics
									</th>
								</tr>
								<tr>
									<td>
										<s:if test="%{scrip.profitMarginPercentage!=null}"><span class="fh_14">PMQ</span> Profit Margin(Last Qtr):<b>
											<s:text name="rupees.format" >
												<s:param name="scrip.profitMarginPercentage" value="%{scrip.profitMarginPercentage}"/>
											</s:text></b>
										</s:if>
									</td>
									<td>
										<s:if test="%{scrip.averageFourSameQuarterNetprofit!=null}"><span class="fh_15">NPSQ</span> Avg 4 Same Qtr Net Profit:<b>
											<s:text name="rupees.format" >
												<s:param name="scrip.averageFourSameQuarterNetprofit" value="%{scrip.averageFourSameQuarterNetprofit}"/>
											</s:text></b>
										</s:if>		
									</td>
									<td>
										<s:if test="%{scrip.averageFourQtrNetprofit!=null}"><span class="fh_16">NPLQ</span> Avg4QtrNetprofit:<b>
											<s:text name="rupees.format" >
												<s:param name="scrip.averageFourQtrNetprofit" value="%{scrip.averageFourQtrNetprofit}"/>
											</s:text></b>
										</s:if>
									</td>
									<td>
										<s:if test="%{scrip.ema100day!=null}"><span class="fh_17">EMA</span> EMA100day: <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.ema100day" value="%{scrip.ema100day}"/>
											</s:text>
											(<s:text name="rupees.format" >
												<s:param name="scrip.ema100dayPercent" value="%{(scrip.cmp-scrip.ema100day)*100.0/scrip.cmp}"/>
											</s:text>%)
											</b></s:if>&nbsp;
										<s:if test="%{scrip.stochasticValue!=null}"><span class="fh_18">S</span> Stochastic: <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.stochasticValue" value="%{scrip.stochasticValue}"/>
											</s:text></b></s:if>&nbsp;
										<s:if test="%{scrip.raising_stochastic==false}"><img src="<%=request.getContextPath()%>/metroWL/images/down_r.gif"></s:if>
										<s:if test="%{scrip.raising_stochastic==true}"><img src="<%=request.getContextPath()%>/metroWL/images/up_g.gif"></s:if>
									</td>
								</tr>
								<tr>
									<td>
										<span class="fh_19">1D</span> Price Before 1D: <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.priceOnedayBefore" value="%{scrip.priceOnedayBefore}"/>
										</s:text></b>
									</td>
									<td>
										<span class="fh_19">5D</span> Price Before 5D: <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.priceFivedayBefore" value="%{scrip.priceFivedayBefore}"/>
										</s:text></b>		
									</td>
									<td>
										<span class="fh_19">1M</span> Price Before 1M: <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.priceOnemonthBefore" value="%{scrip.priceOnemonthBefore}"/>
										</s:text></b>
									</td>
									<td>
										<span class="fh_19">3M</span> Price Before 3M: <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.priceThreemonthBefore" value="%{scrip.priceThreemonthBefore}"/>
										</s:text></b>
									</td>
								</tr>
								<tr>
									<td>
										<span class="fh_20">QNRC</span> Avg Qtrly Net Return (Closepricewise): <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.avgQtrNetReturnByCloseprice" value="%{scrip.avgQtrNetReturnByCloseprice}"/>
										</s:text></b>		
									</td>
									<td>
										<span class="fh_20">SQRN</span> Same Qtr raising net profit count: <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.raisingSameQtrCount" value="%{scrip.raisingSameQtrCount}"/>
										</s:text></b>		
									</td>
									<td>
										Todo
									</td>
									<td>
										Todo
									</td>									
								</tr>
								<tr>
									<td>
										<span class="fh_21">AC</span> Close Above 30D EMA(6M): <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.countOfDayCloseAbove30demaIn52W" value="%{scrip.countOfDayCloseAbove30demaIn52W}"/>
										</s:text></b>
									</td>
									<td>
										<span class="fh_21">BC</span> Close Below 30D EMA(6M): <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.countOfDayCloseBelow30demaIn52W" value="%{scrip.countOfDayCloseBelow30demaIn52W}"/>
										</s:text></b>		
									</td>
									<td>
										<span class="fh_21">R</span> Ratio of 30D EMA Above/Below: <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.ratioOf52WCloseAboveBelow30DEMA" value="%{scrip.ratioOf52WCloseAboveBelow30DEMA}"/>
										</s:text></b>
									</td>
									<td>
										<span class="fh_22">LQCP</span> Last Fin Qtr Closeprice: <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.lastQtrCloseprice" value="%{scrip.lastQtrCloseprice}"/>
										</s:text></b>
									</td>
								</tr>
								<tr>
									<td>
										<s:if test="%{scrip.raisingfourqtrprofitcount!=null}"><span class="fh_1">4QPC</span> Raising 4 Qtr Profit Count: <b>${scrip.raisingfourqtrprofitcount}</b></s:if>				
									</td>
									<td>
										<span class="fh_2">4QCC</span> Raising 4 Qtr Closeprice Count: <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.raisingfourqtrclosepricecount" value="%{scrip.raisingfourqtrclosepricecount}"/>
										</s:text></b>
									</td>
									<td>
										<s:if test="%{scrip.raisingProfitQtrCount!=null}"><span class="fh_3">PQC</span> Raising Profit Qtr Count: <b>${scrip.raisingProfitQtrCount}</b></s:if>				
									</td>
									<td>
										<span class="fh_4">CQC</span> Raising Closeprice Qtrly: <b>
										<s:text name="rupees.format" >
											<s:param name="scrip.raisingClosepriceCount" value="%{scrip.raisingClosepriceCount}"/>
										</s:text></b>
									</td>
								</tr>
								
								
								<tr>
									<td>
										<s:if test="%{scrip.changeProfit!=null}"><span class="fh_20">CNLQ</span> Change In NetProfit (last Qtr): <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.changeProfit" value="%{scrip.changeProfit}"/>
											</s:text>%</b>
										</s:if>
									</td>
									<td>
										<s:if test="%{scrip.changeProfit!=null}"><span class="fh_20">CNSQ</span> Change In NetProfit(Same Qtr last year): <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.lastSameQtrGrowthPercentage" value="%{scrip.lastSameQtrGrowthPercentage}"/>
											</s:text>%</b>
										</s:if>
									</td>
									<td>
										<s:if test="%{scrip.revenuechangepercent!=null}"><span class="fh_20">CRLQ</span> Change In Revenue (last Qtr): <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.revenuechangepercent" value="%{scrip.revenuechangepercent}"/>
											</s:text>%</b>
										</s:if>
									</td>
									<td>
										<s:if test="%{scrip.revenuesameqtrchangepercent!=null}"><span class="fh_20">CRSQ</span> Change In Revenue(SQG): <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.revenuesameqtrchangepercent" value="%{scrip.revenuesameqtrchangepercent}"/>
											</s:text>%</b>
										</s:if>
									</td>
								</tr>
								
								<tr>
									<td>
										<s:if test="%{scrip.lastQtrNetprofitAmount!=null}"><span class="fh_19">NPLQ</span> Net profit Amount (Last Qtr): <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.lastQtrNetprofitAmount" value="%{scrip.lastQtrNetprofitAmount}"/>
											</s:text></b>
										</s:if>
									</td>
									<td>
										<s:if test="%{scrip.lastQtrInterestAmount!=null}"><span class="fh_19">IALQ</span> Interest Amount paid in Last Qtr: <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.lastQtrInterestAmount" value="%{scrip.lastQtrInterestAmount}"/>
											</s:text></b>
										</s:if>
									</td>
									<td>
										<s:if test="%{scrip.oneyearNetprofitAmount!=null}"><span class="fh_19">NPOY</span> Net profit in One Full Year: <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.oneyearNetprofitAmount" value="%{scrip.oneyearNetprofitAmount}"/>
											</s:text></b>
										</s:if>
									</td>
									<td>
										<s:if test="%{scrip.oneyearInterestAmount!=null}"><span class="fh_20">IAOY</span> Interest Amount paid in One year: <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.oneyearInterestAmount" value="%{scrip.oneyearInterestAmount}"/>
											</s:text></b>
										</s:if>
									</td>
								</tr>
								<tr>
									<td>
										<s:if test="%{scrip.profitmarginchangepercent!=null}"><span class="fh_19">PMLQ</span>Profit Margin (Last Qtr): <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.profitmarginchangepercent" value="%{scrip.profitmarginchangepercent}"/>
											</s:text></b>
										</s:if>
									</td>
									<td>
										<s:if test="%{scrip.profitmarginSameQtrchangepercent!=null}"><span class="fh_19">PMSQG</span>Profit Margin (SQG): <b>
											<s:text name="rupees.format" >
												<s:param name="scrip.profitmarginSameQtrchangepercent" value="%{scrip.profitmarginSameQtrchangepercent}"/>
											</s:text></b>
										</s:if>
									</td>
									<td>
										Todo:
									</td>
									<td>
										Todo:
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				
				<s:if test="%{autoscanResultList.size>0}">
					<tr class="rowEven">
						<td colspan="4">
							<table class="datatable" id="genericTableFormtable" summary="Deployments">	
								<tbody>
									<tr>	
										<th scope="col">
											Major Technical Indicators
										</th>
									</tr>
									<s:iterator value="autoscanResultList">
										<tr>
											<td>
													<s:if test="%{signalCode>0}">
														<font color="green">
														Bullish&nbsp;
													</s:if>
													<s:else>
														<font color="red">
														Bearish&nbsp;
													</s:else>
													
													<s:if test="%{studyMaster.id==93}">
														<b>Stochastic(<s:property value="studyMaster.study_parameter"/>) Crossover</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==99}">
														<b>Support Price Crossover </b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==77}">													
														 <b>Stochastic(<s:property value="studyMaster.study_parameter"/>) Breakout Signal</b> Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==95}">													
														 <b>EMA 15 vs EMA 30 Crossover</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==96}">													
														 <b>EMA 30 vs EMA 50 Crossover</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==97}">													
														 <b>EMA 50 vs EMA 100 Crossover</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==98}">													
														 <b>EMA 100 vs EMA 200 Crossover</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==102}">													
														 <b>EMA 5 vs EMA 20 Crossover</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.shortName=='EMA'}">													
														 <b>Price closed <s:if test="%{signalCode>0}">Above</s:if><s:else>Below</s:else> EMA(<s:property value="studyMaster.study_parameter"/>)</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.shortName=='MA'}">													
														 <b>Price closed <s:if test="%{signalCode>0}">Above</s:if><s:else>Below</s:else> MA(<s:property value="studyMaster.study_parameter"/>)</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													
													<s:if test="%{studyMaster.id==92}">													
														 <b>FibonacciRetracement(<s:property value="signalPath"/>)</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==2}">													
														 <b>Touched 52 Week LOW</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>	
													<s:if test="%{studyMaster.id==83}">													
														 <b>Near 52 Week high(<s:property value="signalCode"/>% away)</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==1}">													
														 <b>Touched 52 Week HIGH</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>	
													<s:if test="%{studyMaster.id==74}">													
														 <b>MACD(<s:property value="studyMaster.study_parameter"/>) Breakout Signal</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==4}">													
														 <b>MACD(<s:property value="studyMaster.study_parameter"/>) Breakout Signal</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==75}">													
														 <b>Momentum(<s:property value="studyMaster.study_parameter"/>) Breakout Signal</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==76}">													
														 <b>RSI(<s:property value="studyMaster.study_parameter"/>) Breakout Signal</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==71}">													
														 <b>CCI(<s:property value="studyMaster.study_parameter"/>) Breakout Signal</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==84}">													
														 <b>TTF(<s:property value="studyMaster.study_parameter"/>) Breakout Signal</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{studyMaster.id==94}">													
														 <b>Trix(<s:property value="studyMaster.study_parameter"/>) Breakout Signal</b> - Appeared on <s:property value="signalDate"/> 
													</s:if>
													<s:if test="%{exchangeCode==true}">
														(NSE)
													</s:if>
													<s:else>
														(BSE)
													</s:else>
													</font>
												
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</td>
					</tr>
				</s:if>				
				<tr class="rowEven">
					<td>		
						<s:if test="%{scrip.bseName!=null}">
							<a target="_blank" href="https://chart.finance.yahoo.com/z?t=1y&q=c&l=on&z=l&p=e50,b,s,v&a=m26-12-9,f14,r14&lang=en-IN&region=IN&s=${scrip.bseName}.BO">	
								<img src="https://chart.finance.yahoo.com/t?s=${scrip.bseName}.BO&width=200&height=120&rand=<%=System.currentTimeMillis()%>">
							</a>
						</s:if>
					</td>
					<td>
						<s:if test="%{scrip.bseName!=null}">
							<a target="_blank" href="https://chart.finance.yahoo.com/z?t=1y&q=c&l=on&z=l&p=e50,b,s,v&a=m26-12-9,f14,r14&lang=en-IN&region=IN&s=${scrip.nseCode}.NS">	
								<img src="https://chart.finance.yahoo.com/t?s=${scrip.nseCode}.NS&width=200&height=120&rand=<%=System.currentTimeMillis()%>">
							</a>
						</s:if>
					</td>
					<td>			
						<s:if test="%{scrip.etCode!=null}">
							<img src="http://marketgraphs.economictimes.indiatimes.com/charting/CompanySparkLine1.aspx?companyid=${scrip.etCode}&exchange=50&width=125&height=75&chartmode=intraday&companytype=">
						</s:if>&nbsp;
					</td>
					<td>
						Todo:
					</td>
				</tr>
				<tr class="rowEven">
					<td colspan="4"> Execute
						
						0. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=Code All ${scrip.bseCode} ${scrip.nseCode}">[Code All]</a>
						1. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=Code BSE ${scrip.bseCode}">[Code BSE]</a>
						2. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=Code NSE ${scrip.nseCode}">[Code NSE]</a>
						3. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=Code MC ${scrip.bseCode} ${scrip.nseCode}">[Code MC]</a>
						4. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=Code ET ${scrip.nseCode}">[Code ET]</a>
						5. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=Fundamental ET ${scrip.id} ${scrip.etCode}">[Fundamental ET]</a>
					
						0. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=NSE Intra ${scrip.nseCode}">[NSE Intra]</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

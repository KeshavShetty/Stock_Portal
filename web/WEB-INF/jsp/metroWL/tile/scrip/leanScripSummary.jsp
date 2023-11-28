<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.opensymphony.xwork2.ActionContext" %> 
<%@ page import="com.opensymphony.xwork2.util.ValueStack" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map.Entry" %> 

<div class="spui-window-content">	
	<div class="contenttable">
		<div>
			<table>
				<tr>
					<td width="75%"><font color="red">[Disclaimer]: This is not recommendation or not trading tips. 
					This pick is based on my understanding of fundamental & technical analysis as well as computer generated signals. 
					Quality & accuracy of the data may not be good for trading purpose. Please check with your own source before making any decision. 
					</font>
					</td>
					<td width="25%">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div>
			<table class="datatable" id="genericTableFormtable" summary="Deployments">
				<tr class="rowOdd">
					<td>
						<h2>
							${scrip.name}&nbsp;
								<s:if test="%{scrip.bseCode!=null}">BSE: ${scrip.bseCode}[${scrip.bseName}]</s:if> 
								<s:if test="%{scrip.nseCode!=null}">NSE: ${scrip.nseCode}[${scrip.seriesType}]</s:if> 
								<s:if test="%{scrip.isinCode!=null}">ISIN: ${scrip.isinCode}</s:if>
							<br>
							<s:if test="%{scrip.sector.name!=null}"> Sector: ${scrip.sector.name}&nbsp;</s:if>&nbsp;
							<s:if test="%{scrip.mcsector.name!=null}"> (Alternative Industry/Sector: ${scrip.mcsector.name})&nbsp;</s:if>							
						</h2>
					</td>
				</tr>
			</table>
		</div>
		<div>
		
		<table class="datatable" id="genericTableFormtable" summary="Deployments">
			<tr class="rowEven">
				<td>
					CMP: 
					<s:if test="%{scrip.bseCmp!=null}"> BSE - <b>${scrip.bseCmp}</b> (
						Previous close: ${scrip.bsePreviousClose} -
						Gain:
							<s:text name="rupees.format" >
								<s:param name="scrip.myBseTodaysGain" value="%{(scrip.bseCmp-scrip.bsePreviousClose)*100/scrip.bsePreviousClose}"/>
							</s:text>%
						)
					</s:if>
					<s:if test="%{scrip.nseCmp!=null}"> NSE - <b>${scrip.nseCmp}</b> (
						Previous close: ${scrip.nsePreviousClose} - 
						Gain:
							<s:text name="rupees.format" >
								<s:param name="scrip.myNseTodaysGain" value="%{(scrip.nseCmp-scrip.nsePreviousClose)*100/scrip.nsePreviousClose}"/>
							</s:text>%
						)
					</s:if>
					<s:if test="%{scrip.dayBarSizePercent!=null}">Day Bar Size(Open Vs Close) : <b>${scrip.dayBarSizePercent}%</b></s:if>
				</td>
			</tr>
		</table>
		<div>
			<table>
				<tr>
					<td>
						<table class="datatable" id="genericTableFormtable" summary="Deployments">
							
							<tr class="rowOdd">
								<td>
									<span class="fh_2">H</span> 52 Week: <s:if test="%{scrip.bse52weekHigh!=null}">High:<b>${scrip.bse52weekHigh}</b></s:if> <s:if test="%{scrip.bse52weekLow!=null}"><span class="fh_2">L</span> Low:<b>${scrip.bse52weekLow}</b></s:if>
								</td>
								<td>
									<span class="fh_3">V</span> Volume:<s:if test="%{scrip.todaysVolume!=null}"><b>${scrip.todaysVolume}</b>(Previous:${scrip.previousvolume})</s:if>
								</td>
							</tr>
							<tr class="rowEven">
								<td>
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
							</tr>							
							<tr class="rowOdd">
								<td>
									<span class="fh_6">VA</span> Volume/Avg Volume(NSE):<s:if test="%{scrip.nseVolumeVsAvgVolumeRatio!=null}"><b>${scrip.nseVolumeVsAvgVolumeRatio}</b></s:if>
								</td>
								<td>
									<span class="fh_5"> MA</span> AverageVolume (1M): <s:if test="%{scrip.averageVolume1M!=null}"><b>${scrip.averageVolume1M}</b></s:if>
								</td>
							</tr>
								
							<tr class="rowEven">								
								<td>
									<span class="fh_7">SP</span> Support Price:<s:if test="%{scrip.supportPrice3M!=null}"><b>${scrip.supportPrice3M}</b></s:if>
								</td>
								<td>
									<span class="fh_7">SV</span> Support Volume:<s:if test="%{scrip.supportVolume3M!=null}"><b>${scrip.supportVolume3M}</b></s:if>
								</td>
							</tr>
							
							<tr class="rowEven">
								<td colspan="2">&nbsp;</td>
							</tr>
								
							<s:if test="%{autoscanResultList.size>0}">
							<tr class="rowEven">
								<td colspan="2">
									<table class="datatable" id="genericTableFormtable" summary="Deployments">	
										<tbody>
											<tr>	
												<th scope="col">
													Major Technical Indicators and breakout signals (last two days)
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
															<b>Support Price Crossover</b> - Appeared on <s:property value="signalDate"/> 
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
							<tr class="rowEven">
								<td colspan="2">&nbsp;</td>
							</tr>
						</s:if>
						<tr class="rowEven">
							<td colspan="2">
								<table class="datatable" id="genericTableFormtable" summary="Deployments">	
									<tbody>
										<tr>	
											<th>
												 Quick Chart
											</th>
										</tr>
										<tr>
											<td>
												<s:if test="%{scrip.nseCode!=null}">
													<img src="<%=request.getContextPath()%>/chart/quickchart/NSE-${scrip.nseCode}?TickerSymbol=NSE-${scrip.nseCode}&TimeRange=720&ChartSize=H&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=EMA&movAvg1=50&avgType2=EMA&movAvg2=100&avgType3=EMA&movAvg3=200&Indicator1=RSI&Indicator2=MACD&Indicator3=CCI&Indicator4=SStoch" border="0">
												</s:if>
												<s:else>
													<img src="<%=request.getContextPath()%>/chart/quickchart/BSE-${scrip.bseCode}?TickerSymbol=BSE-${scrip.bseCode}&TimeRange=720&ChartSize=H&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=EMA&movAvg1=50&avgType2=EMA&movAvg2=100&avgType3=EMA&movAvg3=200&Indicator1=RSI&Indicator2=MACD&Indicator3=CCI&Indicator4=SStoch" border="0">
												</s:else>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>	
							<tr class="rowEven">
								<td colspan="2">
									<table class="datatable" id="genericTableFormtable" summary="Deployments">	
										<tbody>
											<tr>	
												<th scope="col" colspan="2">
													 Financial Ratios
												</th>
											</tr>
											<tr>
												<td><s:if test="%{scrip.bookValue!=null}"><span class="fh_8">B</span> Book Value: <b></b>${scrip.bookValue}</b></s:if></td>
												<td><s:if test="%{scrip.faceValue!=null}"><span class="fh_8">F</span> Face Value: <b>${scrip.faceValue}</b></s:if></td>
											</tr>
											<tr>
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
											</tr>
											<tr>
												<td><s:if test="%{scrip.cashFlowsToLongTermDebt!=null}"><span class="fh_13">CD</span> Cash Flow To Debt(LongTerm):<b>${scrip.cashFlowsToLongTermDebt}</b></s:if></td>
												<td><s:if test="%{scrip.fixedAssetsTurnover!=null}"><span class="fh_12">FA</span> Fixed Assets Turnover:<b>${scrip.fixedAssetsTurnover}</b></s:if></td>
											</tr>
											<tr>
												<td><s:if test="%{scrip.quick_ratio!=null}"><span class="fh_11">Q</span> Quick Ratio:<b>${scrip.quick_ratio}</b></s:if></td>
												<td><s:if test="%{scrip.current_ratio!=null}"><span class="fh_11">C</span> Current Ratio:<b>${scrip.current_ratio}</b></s:if></td>
											</tr>
											<tr>
												<td><s:if test="%{scrip.cashRatio!=null}"><span class="fh_11">CR</span> Cash Ratio:<b>${scrip.cashRatio}</b></s:if></td>
												<td><s:if test="%{scrip.debtEquityRatio!=null}"><span class="fh_11">DR</span> Debt Equity Ratio:<b>${scrip.debtEquityRatio}</b></s:if></td>
											</tr>
											<tr>
												<td><s:if test="%{scrip.returnOnEquity!=null}"><span class="fh_12">RE</span> ROE:<b>${scrip.returnOnEquity}</b></s:if></td>
												<td><s:if test="%{scrip.returnOnAssets!=null}"><span class="fh_12">RA</span> ROA :<b>${scrip.returnOnAssets}</b></s:if></td>
											</tr>
											<tr>
												<td><s:if test="%{scrip.returnOnCapitalEmployed!=null}"><span class="fh_12">RC</span> ROCE:<b>${scrip.returnOnCapitalEmployed}</b></s:if></td>
												<td><s:if test="%{scrip.grossProfitMargin!=null}"><span class="fh_13">GM</span> Profit Margin(Gross):<b>${scrip.grossProfitMargin}</b></s:if></td>
											</tr>
											<tr>
												<td><s:if test="%{scrip.netProfitMargin!=null}"><span class="fh_13">NM</span> Profit Margin(Net):<b>${scrip.netProfitMargin}</b></s:if></td>
												<td><s:if test="%{scrip.operatingProfitMargin!=null}"><span class="fh_13">OM</span> Profit Margin(Operating):<b>${scrip.operatingProfitMargin}</b></s:if></td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
							<tr class="rowEven">
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr class="rowEven">
								<td colspan="2">
									<table class="datatable" id="genericTableFormtable" summary="Deployments">	
										<tbody>
											<tr>	
												<th scope="col" colspan="2">
													Custom statistics
												</th>
											</tr>
											<tr>
												<td>
													<s:if test="%{scrip.ema100day!=null}"><span class="fh_17">EMA</span> EMA100day: <b>
														<s:text name="rupees.format" >
															<s:param name="scrip.ema100day" value="%{scrip.ema100day}"/>
														</s:text>
														(<s:text name="rupees.format" >
															<s:param name="scrip.ema100dayPercent" value="%{(scrip.cmp-scrip.ema100day)*100.0/scrip.cmp}"/>
														</s:text>%)
														</b></s:if>&nbsp;
												</td>
												<td>
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
													<s:if test="%{scrip.profitMarginPercentage!=null}"><span class="fh_14">PMQ</span> Profit Margin(Last Qtr):<b>
														<s:text name="rupees.format" >
															<s:param name="scrip.profitMarginPercentage" value="%{scrip.profitMarginPercentage}"/>
														</s:text></b>
													</s:if>
												</td>
												<td>
													<span class="fh_17">EMA</span> EMA15 vs EMA30 %: <b>
													<s:text name="rupees.format" >
														<s:param name="scrip.ema15Diff" value="%{(scrip.ema15day-scrip.ema30day)*100/scrip.ema15day}"/>
													</s:text></b>
													&nbsp;		
												</td>
											</tr>
											<tr>
												<td>
													<s:if test="%{scrip.averageFourQtrNetprofit!=null}"><span class="fh_16">NPLQ</span> Avg4QtrNetprofit:<b>
														<s:text name="rupees.format" >
															<s:param name="scrip.averageFourQtrNetprofit" value="%{scrip.averageFourQtrNetprofit}"/>
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
											</tr>
											<tr>
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
													<s:if test="%{scrip.changeProfit!=null}"><span class="fh_20">CNLQ</span> Change In NetProfit (last Qtr): <b>
														<s:text name="rupees.format" >
															<s:param name="scrip.changeProfit" value="%{scrip.changeProfit}"/>
														</s:text></b>
													</s:if>
												</td>
												<td>
													<s:if test="%{scrip.changeProfit!=null}"><span class="fh_20">CNSQ</span> Change In NetProfit(Same Qtr last year): <b>
														<s:text name="rupees.format" >
															<s:param name="scrip.lastSameQtrGrowthPercentage" value="%{scrip.lastSameQtrGrowthPercentage}"/>
														</s:text>%</b>
													</s:if>
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
											</tr>
											<tr>
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
											</tr>
											<tr>
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
											<tr class="rowEven">
												<td colspan="2">&nbsp;</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
							
														
							<s:if test="%{indexScripsEod.size>0}">
								<tr class="rowEven">
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr class="rowEven">
									<td colspan="2">
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
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
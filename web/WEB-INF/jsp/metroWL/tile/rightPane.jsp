<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<div id="portal-nav-col">

	<div id="QuickLinksBook" class="none">
		<div class="spui-book-content">
			<div id="QuickLinksPage" class="spui-page">
				<div id="QuickLinks" class="spui-frame">
					<div class="top">
						<div>
							<div>
								&nbsp;
							</div>
						</div>
					</div>
					<div class="middle">
						<div class="r">
							<div class="c">
								<div class="c2">
									<div class="spui-titlebar">
										<div class="float-container">
											<div class="spui-titlebar-title-panel">
												<h2>Scrip/Equity Link</h2>
											</div>
											<div class="spui-titlebar-button-panel">
												&nbsp;<a href="#console.portal?_nfpb=true&amp;_windowLabel=QuickLinks&amp;_state=minimized"><img src="<%=request.getContextPath()%>/metroWL/images/minimize.png" class="" title="Minimize" alt="Minimize "></a>
											</div>
										</div>
									</div>
									<div class="spui-window-content">
										<ul>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionGreeks.html?optionName=NIFTY&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%> 10:30:00" target="_blank">Option Greeks Visualizer</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionPriceSpikes.html?indexName=NIFTY 50&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>" target="_blank">Nifty 50 Option Spikes</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionPriceSpikes.html?indexName=NIFTY BANK&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>" target="_blank">Nifty Bank Option Spikes</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionMaxOIWorthRevesion.html?indexName=NIFTY 50&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>" target="_blank">Nifty 50 MAX OI Worth Reversion</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionOptimalStrike.html?indexName=NIFTY 50&noOfTopOis=5&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>" target="_blank">Nifty 50 Optimal Strike</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="javascript:void(0)" onclick="javascript:switchToPage('QuikChartPage');">
													Quick Chart
												</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionDeltaNeutralPriceDisparity.html?indexName=NIFTY 50&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>" target="_blank">Nifty 50 Option Price Disparity</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionDeltaNeutralPriceDisparity.html?indexName=NIFTY BANK&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>" target="_blank">Nifty Bank Option Price Disparity</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="javascript:void(0)" onclick="javascript:switchToPage('ChartPage');">
													Java Chart(Embed)
												</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionAlgoOrderMovement.html?indexName=BANKNIFTY&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&algonames=AtmOtmOIROCBasedIndexIronCondOr-baseDelta0.5-cutoffPercent5.0" target="_blank">Nifty Bank Algo Order Movement</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionGreeksRateOfChange.html?indexName=NIFTY BANK&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&method=ByTopOIs&nooftopois=10&filterOptionWorth=true" target="_blank">Nifty Bank Option Greek ROC</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionPriceRateOfChange.html?indexName=NIFTY BANK&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&basedelta=0.5" target="_blank">Nifty Bank Option Price ROC</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionATMOTMOIRateOfChange.html?indexName=NIFTY 50&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>" target="_blank">Nifty ATM/OTM OI ROC</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionATMOTMOIRateOfChange.html?indexName=NIFTY BANK&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>" target="_blank">BN ATM/OTM OI ROC</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionOIDescrepancy.html?indexName=NIFTY 50&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&method=ByTopOIs&nooftopois=5&filterOptionWorth=true" target="_blank">Nifty OI Discrepancy</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="javascript:void(0)" onclick="javascript:switchToPage('HihgstockChartPage');">
													Intraday Chart
												</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/ShowJavaChart.do?scripCode=NSE-NIFTY 50" target="_blank">Javachart(External)</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="javascript:void(0)" onclick="javascript:switchToPage('HighchartsEODPage');">
													Highcharts(EOD)
												</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/trendDecidingOptionGreeksROC.html?indexName=NIFTY BANK&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&nooftopois=4&method=TopOI" target="_blank">BN Trend Deciding OptionGreeks ROC</a>
											</li>
											
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/option1MGreeksMovement.html?forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&optionnames=NIFTY2382419350CE,NIFTY2382419350PE" target="_blank">Option 1M Greek movements</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/option1MPremiumDecayChart.html?forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&optionnames=NIFTY2382419350CE,NIFTY2382419350PE" target="_blank">Option 1M Premium Decay</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="javascript:void(0)" onclick="javascript:switchToPage('TreePerformancePage');">
													Tree Relation View
												</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="javascript:void(0)" onclick="javascript:switchToPage('FinancialResultsPage');">
													Financial Results
												</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuoteFO.jsp?underlying=NIFTY&instrument=FUTIDX" target="_blank">
													Nifty 50
												</a>&nbsp;
												<a href="<%=request.getContextPath()%>/scripInfo.do?jqIndex=26" target="_blank">
													[Option Chain]
												</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuoteFO.jsp?underlying=BANKNIFTY&instrument=FUTIDX" target="_blank">
													Bank Nifty
												</a>&nbsp;
												<a href="<%=request.getContextPath()%>/scripInfo.do?jqIndex=36" target="_blank">
													[Option Chain]
												</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionsummary.html?indexName=NIFTY&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&nooftopois=10&filterOptionWorth=true" target="_blank">NIFTY 50 Live Option Summary</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionsummary.html?indexName=BANKNIFTY&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&nooftopois=10&filterOptionWorth=true" target="_blank">BANK NIFTY Live Option Summary</a>
											</li>
											
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/domsummary.html?scripName=HDFCBANK&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>" target="_blank">HDFC BANK DOM Summary</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/domsummary.html?scripName=ICICIBANK&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>" target="_blank">ICICI BANK DOM Summary</a>
											</li>
											
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionCePeIVRatioChart.html?forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&indexname=NIFTY 50&forDelta=0.5&expiryStr=" target="_blank">Option CE PE IV Ratio</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionTimeValueAnalysisChart.html?forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&indexname=NIFTY 50" target="_blank">Option Time Value Analysis</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												<a href="<%=request.getContextPath()%>/optionVegaValueAnalysisChart.html?forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&indexname=NIFTY 50&noOfTopOis=10" target="_blank">Option Vega Value Analysis</a>
											</li>
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												ATM Option movement Analysis
												<a href="<%=request.getContextPath()%>/optionATMMovementAnalysisChart.html?forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&indexname=NIFTY 50" target="_blank">[Nifty]</a>&nbsp;
												<a href="<%=request.getContextPath()%>/optionATMMovementAnalysisChart.html?forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&indexname=NIFTY BANK" target="_blank">[BN]</a>
											</li>
											
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												Option Greek movement Analysis
												<a href="<%=request.getContextPath()%>/optionGreeksMovementAnalysisChart.html?forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&indexname=NIFTY 50" target="_blank">[Nifty]</a>&nbsp;
												<a href="<%=request.getContextPath()%>/optionGreeksMovementAnalysisChart.html?forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&indexname=NIFTY BANK" target="_blank">[BN]</a>
											</li>
											
											<li onmouseover="this.className='quicklinksrowover';" onmouseout="this.className='quicklinksrowout';">
												ATM Movement Raw Data Analysis
												<a href="<%=request.getContextPath()%>/optionATMMovementRawDataAnalysisChart.html?baseDelta=0.5&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&indexname=NIFTY 50" target="_blank">[Nifty]</a>&nbsp;
												<a href="<%=request.getContextPath()%>/optionATMMovementRawDataAnalysisChart.html?baseDelta=0.5&forDate=<%=(new SimpleDateFormat("dd/MM/yyyy")).format(new Date())%>&indexname=NIFTY BANK" target="_blank">[BN]</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="bottom">
						<div>
							<div>
								&nbsp;
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="QuickLinksBook" class="none">
		<div class="spui-book-content">
			<div id="QuickLinksPage" class="spui-page">
				<div id="QuickLinks" class="spui-frame">
					<div class="top">
						<div>
							<div>
								&nbsp;
							</div>
						</div>
					</div>
					<div class="middle">
						<div class="r">
							<div class="c">
								<div class="c2">
									<div class="spui-titlebar">
										<div class="float-container">
											<div class="spui-titlebar-title-panel">
												<h2>Screener Tips</h2>
											</div>
											<div class="spui-titlebar-button-panel">
												&nbsp;<a href="#console.portal?_nfpb=true&amp;_windowLabel=QuickLinks&amp;_state=minimized"><img src="<%=request.getContextPath()%>/metroWL/images/minimize.png" class="" title="Minimize" alt="Minimize "></a>
											</div>
										</div>
									</div>
									<div class="spui-window-content" id="ScripInfoDiv" style="overflow: scroll; height: 650px;">
										<p class="changeliststatus">
											<em class="med">.</em>Fundamental<br/>
											<em class="med"><a href="https://en.wikipedia.org/wiki/Return_on_equity" target="_blank">ROE</a></em><br/>
											<em class="med"><a href="https://en.wikipedia.org/wiki/Return_on_assets" target="_blank">ROA</a></em><br/>
											<em class="med"><a href="https://en.wikipedia.org/wiki/Return_on_capital_employed" target="_blank">ROCE</a></em><br/>
											<em class="med"><a href="https://en.wikipedia.org/wiki/Fixed-asset_turnover" target="_blank">Fixed Asset Turnover</a></em><br/>											
											<em class="med"><a href="https://en.wikipedia.org/wiki/Quick_ratio" target="_blank">QuickRatio</a></em><br/>
											<em class="med"><a href="https://en.wikipedia.org/wiki/Current_ratio" target="_blank">Current Ratio</a></em><br/>
											<em class="med"><a href="https://en.wikipedia.org/wiki/Debt-to-equity_ratio" target="_blank">Debt To Equity Ratio</a></em><br/>
											<em class="med"><a href="https://en.wikipedia.org/wiki/Investment#Debt_equity_and_free_cash_flow" target="_blank">Debt Equity & Free Cash Flow</a></em><br/>
											<em class="med"><a href="http://stockmarket.adityon.com/2016/08/some-additional-parameters-i-use-in-my-system/" target="_blank">Some additional parameters I use in my System</a></em><br/><br/>
											
											<em class="med"><a href="https://en.wikipedia.org/wiki/Financial_ratio" target="_blank">Financial Ratios</a></em><br/><br/>
											<em class="med">Stochastic Crossover:</em> B30 <br/>
											<em class="med">Emalgok:</em>E30<br/>
											<em class="med">Malgok:</em>M30<br/>
											<em class="med">.</em>Candles<br/>
											<em class="med">1. CDL3OUTSIDE</em><br/>
											<em class="med">2. CDLDOJI</em><br/>
											<em class="med">3. CDLDRAGONFLYDOJI</em><br/>
											<em class="med">4. CDLTAKURI</em><br/>
											<em class="med">5. CDLUNIQUE3RIVER</em><br/>
											<em class="med">6. CDLUPSIDEGAP2CROWS</em><br/><br/>
											<em class="med"><a href="http://www.bseindia.com/markets/equity/EQReports/bulk_deals.aspx?expandable=3" target="_blank">BSE Bulk deals</a></em><br/>
											<em class="med"><a href="http://www.nseindia.com/products/content/equities/equities/bulk.htm" target="_blank">NSE Bulk deals</a></em><br/>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="bottom">
						<div>
							<div>
								&nbsp;
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
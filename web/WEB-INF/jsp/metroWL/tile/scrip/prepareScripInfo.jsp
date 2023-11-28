<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.opensymphony.xwork2.ActionContext" %> 
<%@ page import="com.opensymphony.xwork2.util.ValueStack" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map.Entry" %> 

<script language="JavaScript">
	$( document ).ready(function() {
		$('#ajax-tab-container').easytabs({
			  animate: false
		});
	});
</script>
<script language="Javascript">
	function openNseLink(urlToLoad) { 
		alert(urlToLoad);
		var windowName = "asdf";
		window.open("http://nseindia.com/", windowName, "height=200,width=200");
		window.open(urlToLoad, windowName, "height=200,width=200");
	}
	
</script>
<div class="spui-window-content">	
	<div class="contenttable">
		<div id="HiddenFormDiv">
			<form name="dummyFormForScrip" id="dummyFormForScrip" method="post" action="/DummyAction">
				<input type="hidden" name="scripId" value="${scrip.id}">														
			</form>
		</div>
		<div>
			<table class="datatable" id="genericTableFormtable" summary="Deployments">
				<tr class="rowOdd">
					<td>
						<h2>
							${scrip.name} <s:if test="%{scrip.sector.name!=null}"> (${scrip.sector.name} - <b>MC Sector: ${scrip.mcsector.name}</b>)</s:if> <s:if test="%{scrip.bseCode!=null}">BSE:${scrip.bseCode}[${scrip.bseName}]</s:if> <s:if test="%{scrip.nseCode!=null}">NSE:${scrip.nseCode}[${scrip.seriesType}]</s:if> <s:if test="%{scrip.isinCode!=null}">ISIN:${scrip.isinCode}</s:if>
							
						</h2>
					</td>
				</tr>
				<tr class="rowEven">
					<td>
						<h2>
							<a href="<%=request.getContextPath()%>/scripInfo.do?jqIndex=${scrip.id}" target="_blank">
								<img src="<%=request.getContextPath()%>/metroWL/images/detach.png" width="16px" height="16px">
							</a>
							<a href="<%=request.getContextPath()%>/leanScripSummary.do?jqIndex=${scrip.id}" target="_blank">
								<img src="<%=request.getContextPath()%>/metroWL/images/info.png" width="16px" height="16px">
							</a>
							<s:if test="%{scrip.bseCode!=null}">
								<a href="http://www.bseindia.com/stock-share-price/anything/anything/${scrip.bseCode}/" target="_blank" title="BSE-${scrip.bseCode}">
									<img src="<%=request.getContextPath()%>/metroWL/images/bse.jpg" width="16px" height="16px">
								</a>
								
								<a href="http://www.thehindubusinessline.com/companies/data/${scrip.bseName}/score-board/${scrip.bseCode}/" target="_blank" title="Business Line-${scrip.bseCode}">
									<img src="<%=request.getContextPath()%>/metroWL/images/BusinessLine.ico" width="16px" height="16px">
								</a>
								
								<a href="https://www.google.com/finance?q=BOM:${scrip.bseCode}" target="_blank" title="BSE@Google">
									<img src="<%=request.getContextPath()%>/metroWL/images/google.png">
								</a>
								<a href="http://www.screener.in/company/${scrip.bseCode}/" title="Screener.In(BSE)" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/screener.png" width="16px" height="16px">
								</a>
								<a href="https://www.marketwatch.com/investing/stock/${scrip.bseCode}?countrycode=in&mod=over_search" title="Market Watch" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/mw.png" width="16px" height="16px">
								</a>								
							</s:if>
							<s:if test="%{scrip.bseName!=null}">
								<a href="https://in.finance.yahoo.com/q?s=${scrip.bseName}.BO" target="_blank" title="BSE@Yahoo">
									<img src="<%=request.getContextPath()%>/metroWL/images/yahoo.png">
								</a>
							</s:if>
							<s:if test="%{scrip.iciciCode!=null}">
								<a href="http://content.icicidirect.com/newsiteContent/Research/TechnicalAnalysis.asp?icicicode=${scrip.iciciCode}&button2=Go" target="_blank" title="ICICI(${scrip.iciciCode})">
									<img src="<%=request.getContextPath()%>/metroWL/images/icici.png" width="16px" height="16px">
								</a>
							</s:if>
							<s:if test="%{scrip.mcCode!=null}">
								<a href="http://www.moneycontrol.com/india/stockpricequote/anything/anything/${scrip.mcCode}" title="MoneyControl(${scrip.mcCode})" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/mc.gif" width="16px" height="16px">
								</a>
							</s:if>
							<s:if test="%{scrip.etCode!=null}">
								<a href="http://economictimes.indiatimes.com/kirloskar-electric-company-ltd/stocks/companyid-${scrip.etCode}.cms" title="EconomicTimes(${scrip.etCode})" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/et.jpg" width="16px" height="16px">
								</a>
							</s:if>
							<s:if test="%{scrip.nseCode!=null}">
								<a href="http://www.screener.in/company/${scrip.nseCode}/" title="Screener.In(NSE)" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/screener.png" width="16px" height="16px">
								</a>
								<a href="http://www.bloomberg.com/quote/${scrip.nseCode}:IN" title="Bloomberg" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/bloomberg.jpg" width="16px" height="16px">
								</a>
								<a href="https://www.nseindia.com/get-quotes/equity?symbol=${scrip.nseCode}" title="NSE-${scrip.nseCode}(${scrip.seriesType}" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/nselogo.gif" width="16px" height="16px">
								</a>
								<a href="https://in.finance.yahoo.com/q?s=${scrip.nseCode}.NS" target="_blank" title="NSE@Yahoo">
									<img src="<%=request.getContextPath()%>/metroWL/images/yahoo.png">
								</a>
								<a href="https://www.google.com/finance?q=NSE:${scrip.nseCode}" target="_blank" title="NSE@Google">
									<img src="<%=request.getContextPath()%>/metroWL/images/google.png">
								</a>
								<a href="https://ticker.finology.in/company/${scrip.nseCode}" target="_blank" title="NSE@Finology">
									<img src="<%=request.getContextPath()%>/metroWL/images/finology.png">
								</a>
								<a href="https://online.capitalcube.com/stock/IN/NSE/${scrip.nseCode}/summary" target="_blank" title="NSE@Finology">
									<img src="<%=request.getContextPath()%>/metroWL/images/cc.png" width="16px" height="16px">
								</a>
							</s:if>
							
							<s:if test="%{scrip.tickertapeId!=null}">
								<a href="https://www.tickertape.in${scrip.tickertapeId}/" title="TickerTape" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/tickertape.png" width="16px" height="16px">
								</a>
							</s:if>
						
							<a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/prepareAddToWatchList.do','scripId=${scrip.id}')" title="Add to Watchlist">
								<img src="<%=request.getContextPath()%>/metroWL/images/addnew.png">
							</a>
							<a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/preparePlaceNOWOrder.do','scripId=${scrip.id}')" title="Buy/Sell">
								<img src="<%=request.getContextPath()%>/metroWL/images/buy_sell.png" width="16px" height="16px">
							</a>
							<a href="https://www.google.com/finance/company_news?q=BOM:${scrip.bseCode}&output=rss" target="_blank">Gf</a>
							<a href="<%=request.getContextPath()%>/ShowJavaChart.do" target="_blank">
								<img src="<%=request.getContextPath()%>/metroWL/images/jChart.png" width="16px" height="16px">
								JC
							</a>
							<a href="https://twitter.com/search?s=follows&f=live&src=typd&q='${scrip.name}'&pf=on'" title="Twitter" target="_blank">
								<img src="<%=request.getContextPath()%>/metroWL/images/twitter.png" width="16px" height="16px">
							</a>
							<s:if test="%{scrip.marketsmojoId!=null}">
								<a href="https://www.marketsmojo.com/Stocks?StockId=${scrip.marketsmojoId}" title="Market Mojo" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/marketmojo.png" width="16px" height="16px">
								</a>
							</s:if>
							<s:if test="%{scrip.stockaddaId!=null}">
								<a href="https://www.stockadda.com/quotes/stockview/${scrip.stockaddaId}" title="Market Mojo" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/stockadda.png" width="16px" height="16px">
								</a>
							</s:if>
							<s:if test="%{scrip.tijoriFinanceId!=null}">
								<a href="https://tijorifinance.com/company/${scrip.tijoriFinanceId}" title="Tijori Finance" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/tijoriFinance.ico" width="16px" height="16px">
								</a>
							</s:if>
							<s:if test="%{scrip.reutersId!=null}">
								<a href="https://www.reuters.com/companies/${scrip.reutersId}" title="Reuter Finance" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/reuter.png" width="16px" height="16px">
								</a>
							</s:if>
							<s:if test="%{scrip.trendlyneId!=null}">
								<a href="https://trendlyne.com${scrip.trendlyneId}" title="Trendlyne Finance" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/Trendlyne.png" width="16px" height="16px">
								</a>
							</s:if>
							
							<s:if test="%{scrip.simplywallstId!=null}">
								<a href="https://simplywall.st${scrip.simplywallstId}" title="Simplywallst" target="_blank">
									<img src="<%=request.getContextPath()%>/metroWL/images/simplywlst.png" width="16px" height="16px">
								</a>
							</s:if>
							
							
							<a href="https://kite.zerodha.com/chart/ext/ciq/NSE/${scrip.nseCode}/${scrip.instrumentToken}" title="Kite" target="_blank">
								<img src="<%=request.getContextPath()%>/metroWL/images/kite.png" width="16px" height="16px">
							</a>
						</h2>
					</td>
				</tr>
			</table>
		</div>
		<!-- Easy tab start here -->	
		<div id="ajax-tab-container" class='tab-container'>
 			<ul class='tabs'>
   				<li class='tab'><a href="<%=request.getContextPath()%>/scripKeysRatios.do?jqIndex=${scrip.id}" data-target="#tabs-Key-Ratios">Key Ratios</a></li>
   				<li class='tab'><a href="<%=request.getContextPath()%>/LoadQuickChart.do?jqIndex=${scrip.id}" data-target="#tabs-Quick-Chart">QChart</a></li>
   				<li class='tab'><a href="<%=request.getContextPath()%>/scripTechnicalAnalysis.do?jqIndex=${scrip.id}" data-target="#tabs-Technical-Analysis">Tech.. Screener</a></li>
   				<li class='tab'><a href="<%=request.getContextPath()%>/scripIntradaySnapshot.do?jqIndex=${scrip.id}" data-target="#tabs-Intraday-Snapshot">IDay Snapshot</a></li>
   				<li class='tab'><a href="<%=request.getContextPath()%>/LoadHighstockChart.do?jqIndex=${scrip.id}" data-target="#tabs-Highchart-Intraday">IDay HS Chart</a></li>
   				<li class='tab'><a href="<%=request.getContextPath()%>/financialResultView.do?jqIndex=${scrip.id}" data-target="#tabs-Financial-Result">Results</a></li>
   				<li class='tab'><a href="<%=request.getContextPath()%>/highchartsEOD.do?jqIndex=${scrip.id}" data-target="#tabs-Highchart-EOD">EOD HS Chart</a></li>
   				<!--  <li class='tab'><a href="<%=request.getContextPath()%>/scripNewsAndFeeds.do?jqIndex=${scrip.id}" data-target="#tabs-News-Feed">News</a></li> -->
   				<li class='tab'><a href="<%=request.getContextPath()%>/volumeAnalysis.do?jqIndex=${scrip.id}" data-target="#tabs-Volume-Analysis">Volume Analysis</a></li>
   				<li class='tab'><a href="<%=request.getContextPath()%>/aScripsHistory.do?jqIndex=${scrip.id}" data-target="#tabs-Scrip-History">Scr History</a></li>   				
   				<li class='tab'><a href="<%=request.getContextPath()%>/userWatchlists.do?jqIndex=${scrip.id}" data-target="#tabs-User-WL">Watchlist</a></li> 
   				<li class='tab'><a href="<%=request.getContextPath()%>/scripPeerAnalysis.do?jqIndex=${scrip.id}" data-target="#tabs-Peers">Peers</a></li>
   				<li class='tab'><a href="<%=request.getContextPath()%>/ratioChartView.do?jqIndex=${scrip.id}" data-target="#tabs-Ratio-Chart">R-Chart</a></li>
   				<li class='tab'><a href="<%=request.getContextPath()%>/scripDailyAchivementView.do?jqIndex=${scrip.id}" data-target="#tabs-Daily-Achievement">Achievement</a></li>
   				<li class='tab'><a href="<%=request.getContextPath()%>/wlCriteriaMatch.do?jqIndex=${scrip.id}" data-target="#tabs-scriptowl-criteria-match">Match WL</a></li>
   				<li class='tab'><a href="<%=request.getContextPath()%>/LoadStreamingHighstockChart.do?jqIndex=${scrip.id}" data-target="#tabs-Streaming">Streaming Chart</a></li>
 				<li class='tab'><a href="<%=request.getContextPath()%>/optionChainView.do?jqIndex=${scrip.id}" data-target="#tabs-OptionChain">Option Chain</a></li>
 				<li class='tab'><a href="<%=request.getContextPath()%>/domAnalysisView.do?jqIndex=${scrip.id}" data-target="#tabs-dom-analysis">DOM</a></li>
 				
 			</ul>
 			<div class='panel-container'>
  				<div id="tabs-Key-Ratios"></div>
  				<div id="tabs-Quick-Chart"></div>
  				<div id="tabs-Technical-Analysis"></div>
  				<div id="tabs-Intraday-Snapshot"></div>
  				<div id="tabs-Highchart-Intraday"></div>
  				<div id="tabs-Financial-Result"></div>
  				<div id="tabs-Highchart-EOD"></div>
  				<!-- <div id="tabs-News-Feed"></div>-->
  				<div id="tabs-Volume-Analysis"></div>
  				<div id="tabs-Scrip-History"></div>
  				<div id="tabs-User-WL"></div>
  				<div id="tabs-Peers"></div>
  				<div id="tabs-Ratio-Chart"></div>
  				<div id="tabs-Daily-Achievement"></div>
  				<div id="tabs-scriptowl-criteria-match"></div>
  				<div id="tabs-Streaming"></div>
  				<div id="tabs-OptionChain"></div>
  				<div id="tabs-dom-analysis"></div>
 			</div>
		</div>		
		<!-- Easy tab ends here -->
	</div>
</div>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>

<script language="Javascript">
	function openNseLink(urlToLoad) { 
		alert(urlToLoad);
		var windowName = "asdf";
		window.open("http://nseindia.com/", windowName, "height=200,width=200");
		windowName.location.href=urlToLoad;
	}
	
</script>

<p class="changeliststatus">
	<table class="datatable" id="genericTableFormtable" summary="Deployments">
		<s:if test="%{scrip.nseCode!=null}">
			<tr class="rowOdd">
				<td colspan="2">				
					<iframe style="" vspace="0" hspace="0" marginwidth="0" marginheight="0" scrolling="no" id="LyrIntrumentSelector" name="LyrIntrumentSelector" src="http://www.nseindia.com//charts/webtame/webchart.jsp?CDSymbol=${scrip.nseCode}&amp;Segment=CM&amp;Series=EQ&amp;CDExpiryMonth=&amp;FOExpiryMonth=&amp;IRFExpiryMonth=&amp;CDDate1=&amp;CDDate2=&amp;PeriodType=2&amp;Periodicity=1&amp;Template=tame_intraday_getQuote_closing_redgreen.jsp" frameborder="0" height="100%" width="100%"></iframe>								
				</td>
			</tr>
		</s:if>
		<s:if test="%{scrip.bseName!=null}">
			<tr class="rowOdd">
				<td colspan="2">
					<a target="_blank" href="https://chart.finance.yahoo.com/z?t=1y&q=c&l=on&z=l&p=e50,b,s,v&a=m26-12-9,f14,r14&lang=en-IN&region=IN&s=${scrip.bseName}.BO">	
						<img src="https://chart.finance.yahoo.com/t?s=${scrip.bseName}.BO&width=200&height=120&rand=<%=System.currentTimeMillis()%>">
					</a>
				</td>
			</tr>
		</s:if>
		<tr class="rowEven">
			<td colspan="2">
				<a href="http://www.bseindia.com/stock-share-price/anything/anything/${scrip.bseCode}/" target="_blank" title="BSE-${scrip.bseCode}">
					<img src="<%=request.getContextPath()%>/metroWL/images/bse.jpg" width="16px" height="16px">
				</a>
				<s:if test="%{scrip.bseName!=null}">
					<a href="https://in.finance.yahoo.com/q?s=${scrip.bseName}.BO" target="_blank" title="BSE@Yahoo">
						<img src="<%=request.getContextPath()%>/metroWL/images/yahoo.png">
					</a>
				</s:if>	
				<s:if test="%{scrip.bseCode!=null}">
					<a href="https://www.google.com/finance?q=BOM:${scrip.bseCode}" target="_blank" title="BSE@Google">
						<img src="<%=request.getContextPath()%>/metroWL/images/google.png">
					</a>
				</s:if>
				<a href="http://content.icicidirect.com/newsiteContent/Research/TechnicalAnalysis.asp?icicicode=${scrip.iciciCode}&button2=Go" target="_blank" title="ICICI(${scrip.iciciCode})">
					<img src="<%=request.getContextPath()%>/metroWL/images/icici.png" width="16px" height="16px">
				</a>
				<a href="http://www.moneycontrol.com/india/stockpricequote/anything/anything/${scrip.mcCode}" title="MoneyControl(${scrip.mcCode})" target="_blank">
					<img src="<%=request.getContextPath()%>/metroWL/images/mc.gif" width="16px" height="16px">
				</a>
				
				<a href="http://economictimes.indiatimes.com/kirloskar-electric-company-ltd/stocks/companyid-${scrip.etCode}.cms" title="EconomicTimes(${scrip.etCode})" target="_blank">
					<img src="<%=request.getContextPath()%>/metroWL/images/et.jpg" width="16px" height="16px">
				</a>
				<s:if test="%{scrip.bseCode!=null}">
					<a href="http://www.screener.in/company/${scrip.bseCode}/" title="Screener.In(BSE)" target="_blank">
						<img src="<%=request.getContextPath()%>/metroWL/images/screener.png" width="16px" height="16px">
					</a>
				</s:if>
				<s:else>
					<a href="http://www.screener.in/company/${scrip.nseCode}/" title="Screener.In(NSE)" target="_blank">
						<img src="<%=request.getContextPath()%>/metroWL/images/screener.png" width="16px" height="16px">
					</a>
				</s:else>
				<a href="http://in.reuters.com/finance/stocks/overview?symbol=${scrip.nseCode}.NS" title="Reuters" target="_blank">
					<img src="<%=request.getContextPath()%>/metroWL/images/reuter.png" width="16px" height="16px">
				</a>
				<a href="http://www.bloomberg.com/quote/${scrip.nseCode}:IN" title="Bloomberg" target="_blank">
					<img src="<%=request.getContextPath()%>/metroWL/images/bloomberg.jpg" width="16px" height="16px">
				</a>
			</td>
		</tr>
		<tr class="rowEven">
			<td colspan="2">
				<a href="javascript:void(0)" onclick="openNseLink('http://nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=${scrip.nseCode}&illiquid=0&smeFlag=0&itpFlag=0" title="NSE-${scrip.nseCode}(${scrip.seriesType})')" >
					<img src="<%=request.getContextPath()%>/metroWL/images/nselogo.gif" width="16px" height="16px">
				</a>
				<s:if test="%{scrip.nseCode!=null}">
					<a href="https://in.finance.yahoo.com/q?s=${scrip.nseCode}.NS" target="_blank" title="NSE@Yahoo">
						<img src="<%=request.getContextPath()%>/metroWL/images/yahoo.png">
					</a>
					<a href="https://www.google.com/finance?q=NSE:${scrip.nseCode}" target="_blank" title="NSE@Google">
						<img src="<%=request.getContextPath()%>/metroWL/images/google.png">
					</a>
				</s:if>
				
				<a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/prepareAddToWatchList.do','scripId=${scrip.id}')" title="Add to Watchlist">
					<img src="<%=request.getContextPath()%>/metroWL/images/addnew.png">
				</a>
				<a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/preparePlaceNOWOrder.do','scripId=${scrip.id}')" title="Buy/Sell">
					<img src="<%=request.getContextPath()%>/metroWL/images/buy_sell.png" width="16px" height="16px">
				</a>
				<a href="javascript:openScripPage('${scrip.nseCode}','${scrip.bseCode}')">
					<img src="<%=request.getContextPath()%>/metroWL/images/view_detail.png" title="Filter this Scrip">
				</a>
			</td>
		</tr>
		<tr class="rowOdd">
			<td colspan="2">
				<a href="https://www.google.com/finance/company_news?q=BOM:${scrip.bseCode}&output=rss" target="_blank">Google News Feed</a>
			</td>
		</tr>
		<tr class="rowEven"><td>Name</td><td>${scrip.name}</td></tr>
		<tr class="rowEven"><td><b>CMP(Last EOD)</b></td><td><b>${scrip.bseCmp}(BSE)/${scrip.nseCmp}(NSE)</b></td></tr>
		<tr class="rowOdd"><td>Volume(Last EOD)</td><td>${scrip.todaysVolume}(BSE)</td></tr>
		<tr class="rowEven"><td>Todays Gain</td><td>${scrip.bseTodaysGain}(BSE)/${scrip.nseTodaysGain}(NSE)</td></tr>
		<tr class="rowOdd"><td>Previous close</td><td>${scrip.bsePreviousClose}(BSE)/${scrip.nsePreviousClose}(NSE)</td></tr>
		<tr class="rowEven"><td>52 WH</td><td>${scrip.bse52weekHigh}(BSE)</td></tr>
		<tr class="rowOdd"><td>52 WL</td><td>${scrip.bse52weekLow}(BSE)</td></tr>
		<tr class="rowEven"><td>Average Volume</td><td>${scrip.averageVolume}(BSE)/${scrip.nseAverageVolume}(NSE)</td></tr>
		
		<tr class="rowOdd"><td>Book Value</td><td>${scrip.bookValue}</td></tr>
		<tr class="rowEven"><td>Face Value</td><td>${scrip.faceValue}</td></tr>
		<tr class="rowOdd"><td>EPS</td><td>${scrip.eps}</td></tr>
		
		<tr class="rowEven"><td>PE</td><td>${scrip.pe}</td></tr>
		<tr class="rowOdd"><td>Dividend Yield</td><td>${scrip.dividendYield}</td></tr>
				
		<tr class="rowOdd"><td>Sector</td><td>${scrip.sector.name}</td></tr>
		<tr class="rowEven"><td>BSE Name</td><td>${scrip.bseName}</td></tr>
		
		<tr class="rowOdd"><td>ISIN Code</td><td>${scrip.isinCode}</td></tr>
		<tr class="rowOdd">
			<td>MC Code</td>
			<td>
				<a href="javascript:void(0)" title="MoneyControl" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/prepareEditMasters.do','pkId=<s:property value="id"/>&tbl=scrips&col=mc_code')">
					<img src="<%=request.getContextPath()%>/metroWL/images/edit.png">
				</a>
			</td>
		</tr>		
		<tr class="rowEven">
			<td colspan="2">
				Member of below Indices
			</td>
		</tr>
			
		<s:iterator value="indexScripsEod">
			<tr class="rowOdd">
				<td>					
					<a href="javascript:void(0)" onclick="javascript:getScripInfoAndPopulateDiv('<%=request.getContextPath()%>/scripInfo.do',<s:property value="scrip.id"/>,'ScripInfoDiv')">
						<s:property value="scrip.name"/>
					</a>
					<a href="javascript:openScripPage('<s:property value="scrip.nseCode"/>','<s:property value="scrip.bseCode"/>')">
						<img src="<%=request.getContextPath()%>/metroWL/images/stock_filter.png" title="Filter this Scrip">
					</a>				
				</td>
				<td>
					<s:if test="%{priceMoveTrend==false}">
						<img src="<%=request.getContextPath()%>/metroWL/images/down_r.gif">
					</s:if>
					<s:if test="%{priceMoveTrend==true}">
						<img src="<%=request.getContextPath()%>/metroWL/images/up_g.gif">
					</s:if>
					<s:text name="rupees.format" >
						<s:param name="stochasticValue" value="%{stochasticValue}"/>
					</s:text>
				</td>
			</tr>
		</s:iterator>
	</table>
</p>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script language="Javascript">
	$(document).ready( function(){		
		$('#scripImageHolder img').on('mousemove', null, [$('#horizontal'), $('#vertical')],function(e){
		    e.data[1].css('left', e.offsetX==undefined?e.originalEvent.layerX:e.offsetX);
		    e.data[0].css('top', e.offsetY==undefined?e.originalEvent.layerY:e.offsetY);
		});
		$('#scripImageHolder').on('mouseenter', null, [$('#horizontal'), $('#vertical')], function(e){
		    e.data[0].show();
		    e.data[1].show();
		}).on('mouseleave', null, [$('#horizontal'), $('#vertical')], function(e){
		        e.data[0].hide();
		        e.data[1].hide();
		});
		
		updateQuickChart('<%=request.getContextPath()%>/chart/quickchart','quickChartForm','ScripImage');
		
	});
	
	function changeDate(formName, fieldName, noofDays) {
		var curDate = document.getElementById(formName).elements[fieldName].value;
		
		if (curDate=='') {
				var today = new Date();
				var dd = today.getDate();
				var mm = today.getMonth()+1; //January is 0!
				var yyyy = today.getFullYear();

				if(dd<10) {
				    dd='0'+dd;
				} 

				if(mm<10) {
				    mm='0'+mm;
				}
				curDate = dd+'/'+mm+'/'+yyyy;
				document.getElementById(formName).elements[fieldName].value = curDate;
		} 
		
		//alert('curDate='+curDate);
		
		var result = moment(curDate, "DD/MM/YYYY");
		
		result.add('days', noofDays);	
		
		
		var newdd = result.toDate().getDate();
		var newmm = result.toDate().getMonth()+1; //January is 0!
		var newyyyy = result.toDate().getFullYear();

		if(newdd<10) {
			newdd='0'+newdd;
		} 

		if(newmm<10) {
			newmm='0'+newmm;
		}
		//alert('New after adding '+newdd+'/'+newmm+'/'+newyyyy)

		document.getElementById(formName).elements[fieldName].value = newdd+'/'+newmm+'/'+newyyyy;
		document.getElementById(formName).submit();
	}
</script>

<div id="portal-content-area" class="none">
	<div class="spui-book-content">
		<div id="AppDeploymentsBook" class="spui-frame">
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
							<div class="spui-book-content">
								<div class="spui-titlebar">
									<div class="float-container">
										<div class="spui-titlebar-title-panel">
											<h1>Quick Chart</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											&nbsp;
										</div>
									</div>
								</div>
								<div id="AppDeploymentsPages" class="spui-page">
									<div id="AppDeploymentsTableBook" class="spui-book">
										<div class="spui-book-content">
											<div id="AppDeploymentsControlPage" class="page-content">
												<div id="AppDeploymentsControlPortlet" class="spui-window  ">
													<div class="spui-window-content">
	
														<div class="contenttable">
															<table cellspacing="0" cellpadding="0" border="0">															    
															    <tr valign="top">
															        <td style="width:150px; background:#bbddff">
															            <form id="quickChartForm" action="javascript:updateQuickChart('<%=request.getContextPath()%>/chart/quickchart','quickChartForm','ScripImage')">
																            <div class="inputtitle">
																                <b>Ticker Symbol</b><br />
																                
																                 <select id="TickerSymbol" name="TickerSymbol" class="textinput" style="width:140px;" onchange="this.form.submit();">
																                 	<s:if test="%{scrip.nseCode !=null}">
																                 		<option value="NSE-<s:property value="scrip.nseCode"/>">NSE-<s:property value="scrip.nseCode"/></option>
																                 	</s:if>	
																                 	<s:if test="%{scrip.bseCode !=null}">
																                 		<option value="BSE-<s:property value="scrip.bseCode"/>">BSE-<s:property value="scrip.bseCode"/></option>
																                 	</s:if>
																                 </select>
																            </div>
																            <div class="inputtitle">
																                <b>Compare With</b><br />
																                <select id="CompareWith" name="CompareWith" class="textinput" style="width:140px;" onchange="this.form.submit();">
																                	<s:if test="%{scrip.nseCode !=null}">
																                		<option selected value="">None</option>
																                		<option value="NSE-<s:property value="scrip.nseCode"/>_MP">NSE-<s:property value="scrip.nseCode"/>(Mean Price)</option>
																                		<option value="NSE-<s:property value="scrip.nseCode"/>_SP">NSE-<s:property value="scrip.nseCode"/>(Support Price)</option>
																                	</s:if>
																                </select>
																                
																            </div>
																            <div class="inputtitle">
																                <b>Time Period</b><br />
																                <select id="TimeRange" name="TimeRange" class="textinput" style="width:140px;" onchange="this.form.submit();">															                    
																                    <option value="7">1 week</option>
																                    <option value="14">2 week</option>
																                    <option value="30">1 month</option>
																                    <option value="60">2 months</option>
																                    <option value="90">3 months</option>
																                    <option value="180">6 months</option>
																                    <option value="270">9 months</option>
																                    <option value="360">1 year</option>
																                    <option value="720" selected>2 years</option>
																                    <option value="1080">3 years</option>
																                    <option value="1440">4 years</option>
																                    <option value="1800">5 years</option>
																                    <option value="3600">10 years</option>
																                </select>
																            </div>
																            <div class="inputtitle">
																                <b>Date Till</b><br />
																                 	--<a href="javascript:void(0)" onclick="javascript:changeDate('quickChartForm', 'maxDataDate', -183)">[/Y]</a>
																                 	<a href="javascript:void(0)" onclick="javascript:changeDate('quickChartForm', 'maxDataDate', -30)">[M]</a>
																                 	<a href="javascript:void(0)" onclick="javascript:changeDate('quickChartForm', 'maxDataDate', -7)">[W]</a>
																                 	<a href="javascript:void(0)" onclick="javascript:changeDate('quickChartForm', 'maxDataDate', -1)">[D]</a><br />
																                	<input name="maxDataDate" value="" id="maxDataDate" class="textinput hasDatepicker" type="text"><br />++
																                	<a href="javascript:void(0)" onclick="javascript:changeDate('quickChartForm', 'maxDataDate', 1)">[D]</a>
																                	<a href="javascript:void(0)" onclick="javascript:changeDate('quickChartForm', 'maxDataDate', 7)">[W]</a>
																                	<a href="javascript:void(0)" onclick="javascript:changeDate('quickChartForm', 'maxDataDate', 30)">[M]</a>
																                	<a href="javascript:void(0)" onclick="javascript:changeDate('quickChartForm', 'maxDataDate', 183)">[/Y]</a>
																            </div>
																            <div class="inputtitle">
																                <b>Chart Size</b><br />
																                <select id="ChartSize" name="ChartSize" class="textinput" style="width:140px;" onchange="this.form.submit();">
																                    <option value="S">Small</option>
																                    <option value="M">Medium</option>
																                    <option value="L">Large</option>
																                    <option value="H" selected>Huge</option>
																                </select><br />
																            </div>
																            <div class="inputtitle">
																                <input type="checkbox" id="Volume" name="Volume" checked><label for="Volume">Show Volume Bars</label><br />
																                <input type="checkbox" id="ParabolicSAR" name="ParabolicSAR"><label for="ParabolicSAR">Parabolic SAR</label><br />
																                <input type="checkbox" id="LogScale" name="LogScale" checked><label for="LogScale">Log Scale</label><br />
																                <input type="checkbox" id="PercentageScale" name="PercentageScale"><label for="PercentageScale">Percentage Scale</label><br />
																                <input type="checkbox" id="PercentageChartFlag" name="PercentageChartFlag"><label for="PercentageChartFlag">Percentage Chart</label><br />
																            </div>
																            <div class="inputtitle">
																                <b>Chart Type</b><br />
																                <select id="ChartType" name="ChartType" class="textinput" style="width:140px;">
																                    <option value="None">None</option>
																                    <option value="CandleStick" selected>CandleStick</option>
																                    <option value="Close">Closing Price</option>
																                    <option value="Median">Median Price</option>
																                    <option value="OHLC">OHLC</option>
																                    <option value="TP">Typical Price</option>
																                    <option value="WC">Weighted Close</option>
																                </select>
																            </div>
																            <div class="inputtitle">
																                <b>Price Band</b><br />
																                <select id="Band" name="Band" class="textinput" style="width:140px;">
																                    <option value="None">None</option>
																                    <option value="BB" selected>Bollinger Band</option>
																                    <option value="DC">Donchian Channel</option>
																                    <option value="Envelop">Envelop (SMA 20 +/- 10%)</option>
																                </select>
																            </div>
																            <div class="inputtitle">
																                <b>Moving Averages</b><br />
																                <nobr><select id="avgType1" name="avgType1" class="textinput" style="width:105px;">
																                    <option value="None">None</option>
																                    <option value="SMA">Simple</option>
																                    <option value="EMA" selected>Exponential</option>
																                    <option value="TMA">Triangular</option>
																                    <option value="WMA">Weighted</option>
																                </select>
																                <select id="movAvg1" name="movAvg1" class="textinput" style="width:40px;">
																                	<option value="15">15</option>
																                    <option value="30">30</option>
																                    <option value="50" selected>50</option>
																                    <option value="100">100</option>
																                    <option value="200">200</option>
																                </select></nobr><br />
																                <nobr><select id="avgType2" name="avgType2" class="textinput" style="width:105px;">
																                    <option value="None">None</option>
																                    <option value="SMA">Simple</option>
																                    <option value="EMA" selected>Exponential</option>
																                    <option value="TMA">Triangular</option>
																                    <option value="WMA">Weighted</option>
																                </select>
																                
																                <select id="movAvg2" name="movAvg2" class="textinput" style="width:40px;"> 
																                    <option value="15">15</option>
																                    <option value="30" selected>30</option>
																                    <option value="50">50</option>
																                    <option value="100" selected>100</option>
																                    <option value="200">200</option>
																                </select></nobr><br />
																                
																                <nobr><select id="avgType3" name="avgType3" class="textinput" style="width:105px;">
																                    <option value="None">None</option>
																                    <option value="SMA">Simple</option>
																                    <option value="EMA" selected>Exponential</option>
																                    <option value="TMA">Triangular</option>
																                    <option value="WMA">Weighted</option>
																                </select>
																                
																                <select id="movAvg3" name="movAvg3" class="textinput" style="width:40px;">
																                    <option value="15">15</option>
																                    <option value="30" selected>30</option>
																                    <option value="50">50</option>
																                    <option value="100">100</option>
																                    <option value="200" selected>200</option>
																                </select></nobr><br />
																                
																            </div>
																            <div class="inputtitle">
																                <b>Technical Indicators</b><br />
																                <select id="Indicator1" name="Indicator1" class="textinput" style="width:140px;">
																                    <option value="None">None</option>
																                    <option value="AccDist">Accumulation/Distribution</option>
																                    <option value="AroonOsc">Aroon Oscillator</option>
																                    <option value="Aroon">Aroon Up/Down</option>
																                    <option value="ADX">Avg Directional Index</option>
																                    <option value="ATR">Avg True Range</option>
																                    <option value="BBW">Bollinger Band Width</option>
																                    <option value="CMF">Chaikin Money Flow</option>
																                    <option value="COscillator">Chaikin Oscillator</option>
																                    <option value="CVolatility">Chaikin Volatility</option>
																                    <option value="CLV">Close Location Value</option>
																                    <option value="CCI">Commodity Channel Index</option>
																                    <option value="DPO">Detrended Price Osc</option>
																                    <option value="DCW">Donchian Channel Width</option>
																                    <option value="EMV">Ease of Movement</option>
																                    <option value="FStoch">Fast Stochastic</option>
																                    <option value="MACD">MACD</option>
																                    <option value="MDX">Mass Index</option>
																                    <option value="Momentum">Momentum</option>
																                    <option value="MFI">Money Flow Index</option>
																                    <option value="NVI">Neg Volume Index</option>
																                    <option value="OBV">On Balance Volume</option>
																                    <option value="Performance">Performance</option>
																                    <option value="PPO">% Price Oscillator</option>
																                    <option value="PVO">% Volume Oscillator</option>
																                    <option value="PVI">Pos Volume Index</option>
																                    <option value="PVT">Price Volume Trend</option>
																                    <option value="ROC">Rate of Change</option>
																                    <option value="RSI" selected>RSI</option>
																                    <option value="SStoch">Slow Stochastic</option>
																                    <option value="StochRSI">StochRSI</option>
																                    <option value="TRIX">TRIX</option>
																                    <option value="UO">Ultimate Oscillator</option>
																                    <option value="Vol">Volume</option>
																                    <option value="WilliamR">William's %R</option>
																                </select><br />
																                <select id="Indicator2" name="Indicator2" class="textinput" style="width:140px;">
																                    <option value="None">None</option>
																                    <option value="AccDist">Accumulation/Distribution</option>
																                    <option value="AroonOsc">Aroon Oscillator</option>
																                    <option value="Aroon">Aroon Up/Down</option>
																                    <option value="ADX">Avg Directional Index</option>
																                    <option value="ATR">Avg True Range</option>
																                    <option value="BBW">Bollinger Band Width</option>
																                    <option value="CMF">Chaikin Money Flow</option>
																                    <option value="COscillator">Chaikin Oscillator</option>
																                    <option value="CVolatility">Chaikin Volatility</option>
																                    <option value="CLV">Close Location Value</option>
																                    <option value="CCI">Commodity Channel Index</option>
																                    <option value="DPO">Detrended Price Osc</option>
																                    <option value="DCW">Donchian Channel Width</option>
																                    <option value="EMV">Ease of Movement</option>
																                    <option value="FStoch">Fast Stochastic</option>
																                    <option value="MACD" selected>MACD</option>
																                    <option value="MDX">Mass Index</option>
																                    <option value="Momentum">Momentum</option>
																                    <option value="MFI">Money Flow Index</option>
																                    <option value="NVI">Neg Volume Index</option>
																                    <option value="OBV">On Balance Volume</option>
																                    <option value="Performance">Performance</option>
																                    <option value="PPO">% Price Oscillator</option>
																                    <option value="PVO">% Volume Oscillator</option>
																                    <option value="PVI">Pos Volume Index</option>
																                    <option value="PVT">Price Volume Trend</option>
																                    <option value="ROC">Rate of Change</option>
																                    <option value="RSI">RSI</option>
																                    <option value="SStoch">Slow Stochastic</option>
																                    <option value="StochRSI">StochRSI</option>
																                    <option value="TRIX">TRIX</option>
																                    <option value="UO">Ultimate Oscillator</option>
																                    <option value="Vol">Volume</option>
																                    <option value="WilliamR">William's %R</option>
																                </select><br />
																                <select id="Indicator3" name="Indicator3" class="textinput" style="width:140px;">
																                    <option value="None">None</option>
																                    <option value="AccDist">Accumulation/Distribution</option>
																                    <option value="AroonOsc">Aroon Oscillator</option>
																                    <option value="Aroon">Aroon Up/Down</option>
																                    <option value="ADX">Avg Directional Index</option>
																                    <option value="ATR">Avg True Range</option>
																                    <option value="BBW">Bollinger Band Width</option>
																                    <option value="CMF">Chaikin Money Flow</option>
																                    <option value="COscillator">Chaikin Oscillator</option>
																                    <option value="CVolatility">Chaikin Volatility</option>
																                    <option value="CLV">Close Location Value</option>
																                    <option value="CCI" selected>Commodity Channel Index</option>
																                    <option value="DPO">Detrended Price Osc</option>
																                    <option value="DCW">Donchian Channel Width</option>
																                    <option value="EMV">Ease of Movement</option>
																                    <option value="FStoch">Fast Stochastic</option>
																                    <option value="MACD">MACD</option>
																                    <option value="MDX">Mass Index</option>
																                    <option value="Momentum">Momentum</option>
																                    <option value="MFI">Money Flow Index</option>
																                    <option value="NVI">Neg Volume Index</option>
																                    <option value="OBV">On Balance Volume</option>
																                    <option value="Performance">Performance</option>
																                    <option value="PPO">% Price Oscillator</option>
																                    <option value="PVO">% Volume Oscillator</option>
																                    <option value="PVI">Pos Volume Index</option>
																                    <option value="PVT">Price Volume Trend</option>
																                    <option value="ROC">Rate of Change</option>
																                    <option value="RSI">RSI</option>
																                    <option value="SStoch">Slow Stochastic</option>
																                    <option value="StochRSI">StochRSI</option>
																                    <option value="TRIX">TRIX</option>
																                    <option value="UO">Ultimate Oscillator</option>
																                    <option value="Vol">Volume</option>
																                    <option value="WilliamR">William's %R</option>
																                </select><br />
																                <select id="Indicator4" name="Indicator4" class="textinput" style="width:140px;">
																                    <option value="None">None</option>
																                    <option value="AccDist">Accumulation/Distribution</option>
																                    <option value="AroonOsc">Aroon Oscillator</option>
																                    <option value="Aroon">Aroon Up/Down</option>
																                    <option value="ADX">Avg Directional Index</option>
																                    <option value="ATR">Avg True Range</option>
																                    <option value="BBW">Bollinger Band Width</option>
																                    <option value="CMF">Chaikin Money Flow</option>
																                    <option value="COscillator">Chaikin Oscillator</option>
																                    <option value="CVolatility">Chaikin Volatility</option>
																                    <option value="CLV">Close Location Value</option>
																                    <option value="CCI">Commodity Channel Index</option>
																                    <option value="DPO">Detrended Price Osc</option>
																                    <option value="DCW">Donchian Channel Width</option>
																                    <option value="EMV">Ease of Movement</option>
																                    <option value="FStoch">Fast Stochastic</option>
																                    <option value="MACD">MACD</option>
																                    <option value="MDX">Mass Index</option>
																                    <option value="Momentum">Momentum</option>
																                    <option value="MFI">Money Flow Index</option>
																                    <option value="NVI">Neg Volume Index</option>
																                    <option value="OBV">On Balance Volume</option>
																                    <option value="Performance">Performance</option>
																                    <option value="PPO">% Price Oscillator</option>
																                    <option value="PVO">% Volume Oscillator</option>
																                    <option value="PVI">Pos Volume Index</option>
																                    <option value="PVT">Price Volume Trend</option>
																                    <option value="ROC">Rate of Change</option>
																                    <option value="RSI">RSI</option>
																                    <option value="SStoch" selected>Slow Stochastic</option>
																                    <option value="StochRSI">StochRSI</option>
																                    <option value="TRIX">TRIX</option>
																                    <option value="UO">Ultimate Oscillator</option>
																                    <option value="Vol">Volume</option>
																                    <option value="WilliamR">William's %R</option>
																                </select>
																			</div>
																			<div class="inputtitle">
																            	<b>Dummy Price Projecton</b><br />
																				<select id="padding" name="padding" class="textinput" style="width:140px;">
																                	<option value="">None</option>
																                    <option value="Above">Above</option>
																                    <option value="Below">Below</option>
																                    <option value="Sideway">Sideway</option>
																				</select>
																            </div>
																            <div class="inputtitle" style="text-align:center">
																                <input id="Button1" name="Button1" type="submit" class="formButton" value="Update Chart">
																            </div>
															            </form>
															        </td>
															        <td>
															            <div id="scripImageHolder">
																		    <div id="horizontal"></div>
																		    <div id="vertical"></div>
																		    <img id="ScripImage" align="top" border="0">
																		</div>
															        </td>
															    </tr>
															    <tr>
															    	<td colspan="2">
															    		1. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=LONGDURATIONEOD BSE <s:property value="%{scrip.id}"/>">[LONGDURATIONEOD BSE]</a>
																		2. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=LONGDURATIONEOD NSE <s:property value="%{scrip.id}"/>">[LONGDURATIONEOD NSE]</a>
																		2. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=ELLIOTWAVE <s:property value="%{scrip.id}"/>">[ELLIOTWAVE Only NSE]</a>
															    	</td>
															    </tr>
															</table>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
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
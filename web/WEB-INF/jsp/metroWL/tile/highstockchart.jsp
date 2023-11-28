<%@ taglib prefix="s" uri="/struts-tags" %>

<script language="Javascript">
	$(function() {
		$( "#HSCdataDate" ).datepicker();
		$( "#HSCresultDate" ).datepicker();
		$( "#intraDataDate" ).datepicker();
	});	
</script>


<script language="Javascript">

	Highcharts.setOptions({
		global: {
			useUTC: false
		}
	});

	function reloadChart(tickerId, timeGapId, dataDateId, containerId ) {
		$.getJSON("<%=request.getContextPath()%>/chart/intradayChartData?TickerSymbol=" + $('#'+tickerId).val() + "&timeGap=" + $('#'+timeGapId).val() + "&dataDate=" + $('#'+dataDateId).val() , function (data) {

	        // split the data set into ohlc and volume
	        var ohlc = [],
	            volume = [],
	            dataLength = data.length,
	            // set the allowed units for data grouping
	            groupingUnits = [[
	                'minute',                         // unit name
	                [1]                             // allowed multiples
	            ]],

	            i = 0;

	        for (i; i < dataLength; i += 1) {
	            ohlc.push([
	                data[i][0], // the date
	                data[i][1], // open
	                data[i][2], // high
	                data[i][3], // low
	                data[i][4] // close
	            ]);

	            volume.push([
	                data[i][0], // the date
	                data[i][5] // the volume
	            ]);
	        }


	        // create the chart
	        $('#'+containerId).highcharts('StockChart', {

	        	 rangeSelector: {
	            
	            buttons: [{
	                type: 'minute',
	                count: 30,
	                text: '30m'
	            }, {
	                type: 'hour',
	                count: 1,
	                text: '1H'
	            }, {
	                type: 'hour',
	                count: 2,
	                text: '2H'
	            }, {
	                type: 'hour',
	                count: 4,
	                text: '4H'
	            }, {
	                type: 'hour',
	                count: 6,
	                text: '6H'
	            }, {
	                type: 'all',
	                text: 'All'
	            }],
	            selected: 5
	        },

	            title: {
	                text: $('#'+tickerId).val()
	            },

	            scrollbar : {
	                enabled : false
	            },

	            navigator : {
	                enabled : false
	            },
	            
	            yAxis: [{
	                labels: {
	                    align: 'right',
	                    x: -3
	                },
	                title: {
	                    text: 'OHLC'
	                },
	                height: '60%',
	                lineWidth: 2
	            }, {
	                labels: {
	                    align: 'right',
	                    x: -3
	                },
	                title: {
	                    text: 'Volume'
	                },
	                top: '65%',
	                height: '35%',
	                offset: 0,
	                lineWidth: 2
	            }],

	            series: [{
	                type: 'candlestick',
	                name: $('#'+tickerId).val(),
	                data: ohlc,
	                dataGrouping: {
	                    units: groupingUnits
	                }
	            }, {
	                type: 'column',
	                name: 'Volume',
	                data: volume,
	                yAxis: 1,
	                dataGrouping: {
	                    units: groupingUnits
	                }
	            }]
	        });
	    });
	}

	$('#HighstockButton').click(function(){
		reloadChart('HSCTickerSymbol', 'HSCtimeGap','HSCdataDate', 'highstock_chart_leftContainer');
		reloadChart('HSCTickerSymbol', 'HSCtimeGap','HSCresultDate', 'highstock_chart_rightContainer');		
	});
	$('#intraHighstockButton').click(function(){
		submitScreenForm("LoadIntraVolumeAnalysis","intraVolumeHighstock_chart_leftContainer");
	});
</script>

<script language="Javascript">
	$(document).ready( function(){
		reloadChart('HSCTickerSymbol', 'HSCtimeGap','HSCdataDate', 'highstock_chart_leftContainer');
		reloadChart('HSCTickerSymbol', 'HSCtimeGap','HSCresultDate', 'highstock_chart_rightContainer');
	});
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
											<h1>Highstock Intraday Chart</h1>
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
															<table cellspacing="0" cellpadding="0" border="0" width="100%">															    
															    <tr valign="top">
															        <td style="width:150px; background:#bbddff" colspan="2">
															        	<s:form action="loadHighstock.do" method="post">
																            <div class="inputtitle">
																                <b>Ticker Symbol: </b>
																                <s:textfield name="tickerSymbol" id="HSCTickerSymbol" cssClass="textinput"/>&nbsp;&nbsp;
																                <b>Date</b>
																                <s:textfield name="dataDate" id="HSCdataDate" cssClass="textinput"/>&nbsp;&nbsp;
																                <b>Time Period(Minutes):</b>
																                <s:textfield name="timeGap" id="HSCtimeGap" cssClass="textinput"/>&nbsp;&nbsp;
																                <b>Next Date:</b>
																                <s:textfield name="nextDate" id="HSCresultDate" cssClass="textinput"/>&nbsp;&nbsp;																                
																                <input id="HighstockButton" name="Button1" type="button" class="formButton" value="Update Chart">
																            </div>
															            </s:form>
															        </td>
															    </tr>
															    <tr>
															    	<td>
															    		<div id="highstock_chart_leftContainer" style="height: 376px; min-width: 520px"></div>
															    	</td>
															    	<td>
															    		<div id="highstock_chart_rightContainer" style="height: 376px; min-width: 520px"></div>
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
<div id="extraBottom">
				<div class="contenttable">
					<table cellspacing="0" cellpadding="0" border="0" width="100%">															    
					    <tr valign="top">
					        <td style="width:150px; background:#bbddff">
					        	<s:form action="LoadIntraVolumeAnalysis.do" method="post">
						            <div class="inputtitle">
						                <b>Ticker Symbol: </b>
						                <s:textfield name="intraTickerSymbol" id="intraHSCTickerSymbol" cssClass="textinput"/>&nbsp;&nbsp;
						                <b>Date</b>
						                <s:textfield name="intraDataDate" id="intraDataDate" cssClass="textinput"/>&nbsp;&nbsp;
						                <input id="intraHighstockButton" name="intraButton1" type="button" class="formButton" value="Update Chart">
						            </div>
					            </s:form>
					        </td>
					    </tr>
					    <tr>
					    	<td>
					    		<div id="intraVolumeHighstock_chart_leftContainer"></div>
					    	</td>
					    </tr>
					</table>
				</div>
			</div>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>


<script language="Javascript">
	$(function() {
		$( "#HSCtickdataDate" ).datepicker();
	});	
</script>


<script language="Javascript">

	Highcharts.setOptions({
		global: {
			useUTC: false
		}
	});

	function reloadChart(tickerId, dataDateId, startHour, endHour, timeGapId, containerId ) {
		$.getJSON("<%=request.getContextPath()%>/chart/streamingTickchart?TickerSymbol=" + $('#'+tickerId).val() + "&startHour=" + $('#'+startHour).val() + "&endHour=" + $('#'+endHour).val() + "&timeGap=" + $('#'+timeGapId).val() + "&dataDate=" + $('#'+dataDateId).val() , function (data) {

	        // split the data set into ohlc and volume
	        var ohlc = [],
	            volume = [],
	            dataLength = data.length,
	            // set the allowed units for data grouping
	            groupingUnits = [[
	                'second',                         // unit name
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
	                type: 'second',
	                count: 1,
	                text: '1s'
	            }, {
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

	$('#HighstockStreamingButton').click(function(){
		reloadChart('HSCTickerSymbol', 'HSCtickdataDate','HSCstartHour', 'HSCendHour', 'HSCStreamtimeGap', 'streaming_chart_Container');
	});
</script>

<script language="Javascript">
	$(document).ready( function(){
		reloadChart('HSCTickerSymbol', 'HSCtickdataDate','HSCstartHour', 'HSCendHour', 'HSCStreamtimeGap', 'streaming_chart_Container');
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
											<h1>Streaming Tick Chart</h1>
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
																                <s:textfield name="dataDate" id="HSCtickdataDate" cssClass="textinput"/>&nbsp;&nbsp;
																                
																                <b>Start Hour:</b>
																                <s:textfield name="startHour" id="HSCstartHour" cssClass="textinput"/>&nbsp;&nbsp;
																                
																                <b>End Hour:</b>
																                <s:textfield name="endHour" id="HSCendHour" cssClass="textinput"/>&nbsp;&nbsp;
																                
																                <b>Time Period(Ticks):</b>
																                <s:textfield name="timeGap" id="HSCStreamtimeGap" cssClass="textinput"/>&nbsp;&nbsp;  
																                <input id="HighstockStreamingButton" name="Button1" type="button" class="formButton" value="Update Chart">
																            </div>
															            </s:form>
															        </td>
															    </tr>
															    <tr>
															    	<td  colspan="2">
															    		<div id="streaming_chart_Container" style="height: 376px; min-width: 520px"></div>
															    	</td>
															    </tr>
															    <tr>
															    	<td  colspan="2">
															    		<JqGridTable:addTable tableIdentifier="ScripPage_Intraday_ZerodhaOrdersTable" formRequired="true"/>
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
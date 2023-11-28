<%@ taglib prefix="s" uri="/struts-tags" %>

<script language="Javascript">
	$(function() {
		$( "#HSCEODFromDate" ).datepicker();
		$( "#HSCEODToDate" ).datepicker();
	});	
</script>


<script language="Javascript">

	Highcharts.setOptions({
		global: {
			useUTC: false
		}
	});

	function reloadEODChart(tickerId, fromDateId, toDateId, containerId ) {
		$.getJSON("<%=request.getContextPath()%>/chart/QuoteData?client=highChart&outputFormat=json&symbol=" + $('#'+tickerId).val() + "&startDate=" + $('#'+fromDateId).val()+ "&endDate=" + $('#'+toDateId).val() , function (data) {

	        // split the data set into ohlc and volume
	        var ohlc = [],
	            volume = [],
	            meanPrice = [],
	            cfWeightage = [],
	            dataLength = data.length,
	            // set the allowed units for data grouping
	            groupingUnits = [[
	                'day',                         // unit name
	                [1,15,30,60,90]                             // allowed multiples
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

	            meanPrice.push([
    				data[i][0], // the date
    	            data[i][6] // the close
    			]);
    			
	            cfWeightage.push([
            		data[i][0], // the date
          	        data[i][7] // Carry forward weightage
          		]);
	        }


	        // create the chart
	        $('#'+containerId).highcharts('StockChart', {

	        	 rangeSelector: {
	            
	            buttons: [{
	                type: 'month',
	                count: 1,
	                text: '1M'
	            }, {
	                type: 'month',
	                count: 2,
	                text: '2M'
	            }, {
	                type: 'month',
	                count: 3,
	                text: '3M'
	            }, {
	                type: 'month',
	                count: 4,
	                text: '4M'
	            }, {
	                type: 'month',
	                count: 6,
	                text: '6M'
	            }, {
	                type: 'all',
	                text: 'All'
	            }],
	            selected: 0
	        },

	            title: {
	                text: $('#'+tickerId).val()
	            },

	            scrollbar : {
	                enabled : true
	            },

	            navigator : {
	                enabled : true
	            },
	            
	            yAxis: [{
	                labels: {
	                    align: 'right',
	                    x: -3
	                },
	                title: {
	                    text: 'OHLC'
	                },
	                height: '50%',
	                lineWidth: 2
	            }, {
	                labels: {
	                    align: 'left',
	                    x: -3
	                },
	                title: {
	                    text: 'CF'
	                },
	                opposite: true,	 
	                top: '52%',               
	                height: '23%',
	                offset: 0,
	                lineWidth: 2
	            }, {
	                labels: {
	                    align: 'right',
	                    x: -3
	                },
	                title: {
	                    text: 'Volume'
	                },
	                top: '77%',
	                height: '23%',
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
	                yAxis: 2,
	                dataGrouping: {
	                    units: groupingUnits
	                }
	            }, {
	            	type: 'line',
	                name: 'MeanPrice',
	                data: meanPrice,
	                dataGrouping: {
	                    units: groupingUnits
	                }
	            }, {
	            	type: 'line',
	                name: 'CF-Weightage',
	                data: cfWeightage,
	                yAxis: 1,
	                dataGrouping: {
	                    units: groupingUnits
	                }  
	            }]
	        });
	    });
	}

	$('#HighstockButton').click(function(){
		reloadEODChart('HSCEODTickerSymbol', 'HSCEODFromDate', 'HSCEODToDate', 'highstock_eod_chart_leftContainer');
	});
</script>

<script language="Javascript">
	$(document).ready( function(){
		reloadEODChart('HSCEODTickerSymbol','HSCEODFromDate', 'HSCEODToDate', 'highstock_eod_chart_leftContainer');
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
											<h1>Highstock EOD Chart</h1>
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
																                <s:textfield name="tickerSymbol" id="HSCEODTickerSymbol" cssClass="textinput"/>&nbsp;&nbsp;
																                <b>From Date</b>
																                <s:textfield name="fromDate" id="HSCEODFromDate" cssClass="textinput"/>&nbsp;&nbsp;
																                <b>To Date:</b>
																                <s:textfield name="toDate" id="HSCEODToDate" cssClass="textinput"/>&nbsp;&nbsp;																                																                
																                <input id="HighstockButton" name="Button1" type="button" class="formButton" value="Update Chart">
																            </div>
															            </s:form>
															        </td>
															    </tr>
															    <tr>
															    	<td>
															    		<div id="highstock_eod_chart_leftContainer" style="height: 576px; min-width: 520px"></div>
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
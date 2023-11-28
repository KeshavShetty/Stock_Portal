<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<script language="Javascript">
	$(function () {
	    $('#highchartsDOMGrpahContainerChartHolder').highcharts({
	    	chart: {
	    		height: 600,
            	zoomType: 'xy'
        },
        plotOptions:{
            series:{
                turboThreshold:15000//set it to a larger threshold, it is by default to 1000
            }
        },
        
        scrollbar : {
            enabled : true
        },

        navigator : {
            enabled : true
        },
        title: {
            text: 'DOM <s:property value="%{selectedScrip.name}"/>'
        },
        xAxis: [{
            categories: [<s:property value="%{quoteTimeAsString}"/>]
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value}',
                style: {
                    color: Highcharts.getOptions().colors[2]
                }
            },
            
            opposite: true

        }, { // Secondary yAxis
            gridLineWidth: 0,
            
            labels: {
                format: '{value}',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            }

        }, { // Tertiary yAxis
            gridLineWidth: 0,
            
            labels: {
                format: '{value}',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 60,
            verticalAlign: 'top',
            y: 10,
            floating: true,
            draggable: true,
            zIndex: 20,
            title: {
                text: ':: Legend'
            },
            borderWidth: 1,
            borderRadius: 0,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },
        series: [{
            name: 'LTP',
            type: 'spline',
            yAxis: 1,
            data: [<s:property value="%{ltpAsString}"/>],
            visible: true,
            zIndex: 1,
            tooltip: {
                valueSuffix: ' Rs'
            }

        },{
            name: 'VolumeDiff',
            type: 'column',
            yAxis: 2,
            data: [<s:property value="%{volumeBuySellDiff}"/>],
            visible: false,
            marker: {
                enabled: true
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' No.'
            }

        },{
            name: 'BuyVvwap',
            type: 'spline',
            yAxis: 1,
            data: [<s:property value="%{buyVvwapAsString}"/>],
            visible: true,
           
            tooltip: {
                valueSuffix: ' Rs'
            }

        },{
            name: 'SellVvwap',
            type: 'spline',
            yAxis: 1,
            data: [<s:property value="%{sellVvwapAsString}"/>],
            visible: true,
           
            tooltip: {
                valueSuffix: ' Rs'
            }

        },{
            name: 'LastTradedQty',
            type: 'column',
            yAxis: 2,
            data: [<s:property value="%{lastTradedQtyAsString}"/>],
            visible: true,
            marker: {
                enabled: true
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' No.'
            }

        },{
            name: 'BuyTotal',
            type: 'column',
            yAxis: 2,
            data: [<s:property value="%{buyTotalAsString}"/>],
            visible: false,
            marker: {
                enabled: true
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' No.'
            }

        },{
            name: 'SellTotal',
            type: 'column',
            yAxis: 2,
            data: [<s:property value="%{sellTotalAsString}"/>],
            visible: false,
            marker: {
                enabled: true
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' No.'
            }

        },{
            name: 'BuyVWAPGap',
            type: 'column',
            yAxis: 0,
            data: [<s:property value="%{buyVwapGapAsString}"/>],
            visible: false,
            marker: {
                enabled: true
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' No.'
            }

        },{
            name: 'SellVWAPGap',
            type: 'column',
            yAxis: 0,
            data: [<s:property value="%{sellVwapGapAsString}"/>],
            visible: false,
            marker: {
                enabled: true
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' No.'
            }

        },{
            name: 'BuyMean',
            type: 'column',
            yAxis: 2,
            data: [<s:property value="%{buyMeanAsString}"/>],
            visible: false,
            marker: {
                enabled: true
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' No.'
            }

        },{
            name: 'SellMean',
            type: 'column',
            yAxis: 2,
            data: [<s:property value="%{sellMeanAsString}"/>],
            visible: false,
            marker: {
                enabled: true
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' No.'
            }

        },{
            name: 'VolumeTradedPerTick',
            type: 'column',
            yAxis: 2,
            data: [<s:property value="%{volumeTradedPerTickAsString}"/>],
            visible: true,
            marker: {
                enabled: true
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' No.'
            }

        }]
    });
});
</script>
<div id="highchartsDOMGrpahContainerChartHolder"></div>
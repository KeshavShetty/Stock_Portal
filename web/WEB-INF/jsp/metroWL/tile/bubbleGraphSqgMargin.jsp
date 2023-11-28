<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<script language="Javascript">
$(function () {
    $('#bubbleGraphContainerChartHolder').highcharts({

        chart: {
            type: 'bubble',
            plotBorderWidth: 1,
            zoomType: 'xy'
        },

        legend: {
            enabled: false
        },

        title: {
            text: 'Profit Growth Percentag Vs Profir Margin Growth Percentage'
        },

       
        xAxis: {
            gridLineWidth: 1,
            title: {
                text: 'Profit Growth Percentage'
            },
            labels: {
                format: '{value}'
            },
            plotLines: [{
                color: 'black',
                dashStyle: 'dot',
                width: 1,
                value: 700,
                label: {
                    rotation: 0,
                    y: 15,
                    style: {
                        fontStyle: 'italic'
                    },
                    text: 'Ideal Growth Rank 700'
                },
                zIndex: 3
            }]
        },

        yAxis: {
            startOnTick: false,
            endOnTick: false,
            title: {
                text: 'Profir Margin Growth Percentage'
            },
            labels: {
                format: '{value}'
            },
            maxPadding: 0.2,
            plotLines: [{
                color: 'black',
                dashStyle: 'dot',
                width: 2,
                value: 150,
                label: {
                    align: 'right',
                    style: {
                        fontStyle: 'italic'
                    },
                    text: 'Ideal WL Score Rank 150',
                    x: -10
                },
                zIndex: 3
            }]
        },

        tooltip: {
            useHTML: true,
            headerFormat: '<table>',
            pointFormat: '<tr><th colspan="2"><h3>{point.scripName}</h3></th></tr>' +
                '<tr><th>SQG Profit%:</th><td>{point.x}</td></tr>' +
                '<tr><th>Profit Margin %:</th><td>{point.y}</td></tr>' +
                '<tr><th>Actual Profit Margin LastQtr:</th><td>{point.z}</td></tr>',
            footerFormat: '</table>',
            followPointer: true
        },

        plotOptions: {
            series: {
            	allowPointSelect: true,
                dataLabels: {
                    enabled: true,
                    format: '{point.scripCode}'
                },
                
				point:{
                	events:{
                    	select: function(e){
                    		showFloatingColorBox('/portal/scripInfo.do', 'jqIndex='+this.jqIndex , 800, 600);
						}
					}
				}                
                
            }
        },

        series: [{
            data: [
				<s:property value="%{bubbleDataAsString}"/>

            ]
        }]

    });
});
</script>
<div id="bubbleGraphContainerChartHolder"></div>
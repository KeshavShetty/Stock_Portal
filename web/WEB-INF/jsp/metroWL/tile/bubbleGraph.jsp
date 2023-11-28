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
            text: 'Growth and Score Rank per Scrip'
        },

       
        xAxis: {
            gridLineWidth: 1,
            title: {
                text: 'Growth Rank'
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
                text: 'WL Score Rank'
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
                '<tr><th>Growth Rank:</th><td>{point.x}</td></tr>' +
                '<tr><th>Score Rank:</th><td>{point.y}</td></tr>' +
                '<tr><th>Wl Count:</th><td>{point.z}</td></tr>',
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
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<script language="Javascript">
$(function () {
    $('#highchartsRatioChartContainerChartHolder').highcharts({
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: 'Fin Ratio Chart'
        },
        xAxis: [{
            categories: [<s:property value="%{datesAsString}"/>],
            crosshair: true
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value}',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            title: {
                text: '<s:property value="%{selectedFirstParameter}"/>',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            }
        }, { // Secondary yAxis
            title: {
                text: '<s:property value="%{selectedNextParameter}"/>',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                format: '{value}',
                style: {
                    color: Highcharts.getOptions().colors[0]
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
            x: 120,
            verticalAlign: 'top',
            y: 100,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },
        series: [{
            name: '<s:property value="%{selectedFirstParameter}"/>',
            type: 'spline',
            yAxis: 0,
            data: [<s:property value="%{firstParameterData}"/>],
            tooltip: {
                valueSuffix: ' '
            }

        }, {
            name: '<s:property value="%{selectedNextParameter}"/>',
            type: 'spline',
            yAxis: 1,
            data: [<s:property value="%{nextParameterData}"/>],
            tooltip: {
                valueSuffix: ' '
            }
        }]
    });
});


</script>
<div id="highchartsRatioChartContainerChartHolder"></div>
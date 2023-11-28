<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<script language="Javascript">
$(function () {
    $('#scripLevelOriceVaRatioCompareContainerChartHolder').highcharts({chart: {
        type: 'line'
    },
    title: {
        text: '<s:property value="%{scripName}"/> - Price vs Ratios'
    },
    xAxis: {
        categories: [<s:property value="%{dateAsString}"/>]
    },
    yAxis: [{
        title: {
            text: 'Rs'
        },
        opposite: true
    },
    { // Secondary yAxis
        gridLineWidth: 0,
        
        labels: {
            format: '{value}',
            style: {
                color: Highcharts.getOptions().colors[0]
            }
        }

    }],
    plotOptions: {
        line: {
            dataLabels: {
                enabled: true,
                formatter: function () {
                    return Highcharts.numberFormat(this.y,2);
                }
            },
            enableMouseTracking: true
        }
    },
    series: [<s:property value="%{datewiseRatioAsString}"/>]
	});
});
</script>
<div id="scripLevelOriceVaRatioCompareContainerChartHolder"></div>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<script language="Javascript">
$(function () {
    $('#scripCompareContainerChartHolder').highcharts({

        chart: {
            type: 'column'
        },
        title: {
            text: 'Scrip Ratio comparison(Pie chart Ravenue vs Profit share)'
        },
        xAxis: {
            categories: [            	
            	<s:property value="%{companiesAsString}"/>
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Value'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            valueDecimals: 2,
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
        	series: {
                dataLabels: {
                    enabled: true,
                    align: 'center',
                    formatter: function () {
                        return Highcharts.numberFormat(this.y,2);
                    }
                }
            },
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            },
            pie: {                
                events:{
                  click: function (event, i) {
                	  showFloatingColorBox('/portal/scripInfo.do', 'jqIndex='+event.point.jqIndex, 800, 600);
                  }
              }
            }
        },
        
        series: [<s:property value="%{allCompanyRatioAsString}"/>,
        	{
	            type: 'pie',
	            name: 'Total Revenue Portion',
	            data: [<s:property value="%{revenueRatio}"/>],
	            center: [50, 50],
	            size: 100,
	            showInLegend: false,
	            dataLabels: {
	                enabled: true,
	                formatter: function() {
                        return Math.round(this.percentage*100)/100 + ' %';
                    }
	            }
	        },
        	{
	            type: 'pie',
	            name: 'Total Profit Portion',
	            data: [<s:property value="%{profitRatio}"/>],
	            center: [200, 50],
	            size: 100,
	            showInLegend: false,
	            dataLabels: {
	                enabled: true,
	                formatter: function() {
                        return Math.round(this.percentage*100)/100 + ' %';
                    }
	            }
	        }
        ]

    });
});
</script>
<div id="scripCompareContainerChartHolder"></div>
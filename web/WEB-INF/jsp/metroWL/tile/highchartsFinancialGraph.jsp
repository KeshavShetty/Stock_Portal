<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<script language="Javascript">
	$(function () {
	    $('#highchartsFinancialGrpahContainerChartHolder').highcharts({
	    	chart: {
            zoomType: 'xy'
        },
        title: {
            text: 'Quarterly results of <s:property value="%{selectedScrip.name}"/>'
        },
        xAxis: [{
            categories: [<s:property value="%{periodsAsString}"/>]
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
            floating: false,
            draggable: false,
            zIndex: 20,
            title: {
                text: ':: Legend'
            },
            borderWidth: 1,
            borderRadius: 0,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },
        series: [{
            name: 'Revenue',
            type: 'column',
            yAxis: 1,
            data: [<s:property value="%{revenueAsString}"/>],
            visible: false,
            tooltip: {
                valueSuffix: ' Cr'
            }

        },{
            name: 'Other Income',
            type: 'column',
            yAxis: 1,
            data: [<s:property value="%{otherIncomeAsString}"/>],
            visible: false,
            tooltip: {
                valueSuffix: ' Cr'
            } 

        },{
            name: 'Total Income',
            type: 'column',
            yAxis: 1,
            data: [<s:property value="%{totalIncomeAsString}"/>],
            visible: false,
            tooltip: {
                valueSuffix: ' Cr'
            }

        },{
            name: 'Expenditure',
            type: 'column',
            yAxis: 1,
            data: [<s:property value="%{expenditureAsString}"/>],
            visible: false,
            tooltip: {
                valueSuffix: ' Cr'
            }

        },{
            name: 'NetProfit',
            type: 'column',
            yAxis: 1,
            data: [<s:property value="%{netProfitAsString}"/>],
            visible: true,
            tooltip: {
                valueSuffix: ' Cr'
            }

        },{
            name: 'Close Price',
            type: 'spline',
            data: [<s:property value="%{qtClosePriceAsString}"/>],
            tooltip: {
                valueSuffix: ' Rs(On Qtr End)'
            }
        },{
            name: 'QtrGrowth',
            type: 'column',
            yAxis: 1,
            data: [<s:property value="%{qtrGrowthAsString}"/>],
            visible: false,
            tooltip: {
                valueSuffix: ' %'
            }

        },{
            name: 'SameQtrGrowth',
            type: 'column',
            yAxis: 1,
            visible: true,
            data: [<s:property value="%{sameQtrGrowthAsString}"/>],
            tooltip: {
                valueSuffix: ' %'
            }

        },{
            name: 'SameQtrRevenueGrowth',
            type: 'column',
            yAxis: 1,
            visible: false,
            data: [<s:property value="%{sameQtrRevenueGrowthAsString}"/>],
            tooltip: {
                valueSuffix: ' %'
            }

        }, {
            name: 'Depreciation',
            type: 'column',
            yAxis: 1,
            data: [<s:property value="%{depreciationAsString}"/>],
            visible: false,
            tooltip: {
                valueSuffix: ' Cr'
            }

        },{
            name: 'PBDT',
            type: 'column',
            yAxis: 1,
            data: [<s:property value="%{pbdtAsString}"/>],
            visible: false,
            tooltip: {
                valueSuffix: ' Cr'
            }

        },{
            name: 'PBT',
            type: 'column',
            yAxis: 1,
            data: [<s:property value="%{pbtAsString}"/>],
            visible: false,
            tooltip: {
                valueSuffix: ' Cr'
            }

        },{
            name: 'TAX',
            type: 'column',
            yAxis: 1,
            data: [<s:property value="%{taxAsString}"/>],
            visible: false,
            tooltip: {
                valueSuffix: ' Cr'
            }

        }, {
            name: 'NPMPercentage',
            type: 'spline',
            yAxis: 2,
            data: [<s:property value="%{npmPercentageAsString}"/>],
            visible: false,
            marker: {
                enabled: false
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' Cr'
            }

        }, {
            name: 'OPMPercentage',
            type: 'spline',
            yAxis: 2,
            data: [<s:property value="%{opmPercentageAsString}"/>],
            visible: false,
            marker: {
                enabled: false
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' Cr'
            }

        }, {
            name: 'EPS',
            type: 'spline',
            yAxis: 2,
            data: [<s:property value="%{epsAsString}"/>],
            visible: false,
            marker: {
                enabled: false
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' Rs(Qtr)'
            }

        }, {
            name: 'CEPS',
            type: 'spline',
            yAxis: 2,
            data: [<s:property value="%{cepsAsString}"/>],
            visible: false,
            marker: {
                enabled: false
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' Rs(Qtr)'
            }

        }, {
            name: 'PE',
            type: 'spline',
            yAxis: 2,
            data: [<s:property value="%{peAsString}"/>],
            visible: false,
            marker: {
                enabled: false
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' Ratio(Qtr)'
            }

        }]
    });
});
</script>
<div id="highchartsFinancialGrpahContainerChartHolder"></div>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
  function drawChart() {
    var data = google.visualization.arrayToDataTable([
	<s:iterator value="eodData" status="rowStatus">                                                      
      ['<s:property value="dataDate"/>', <s:property value="lowPrice"/>, <s:property value="closePrice"/>, <s:property value="openPrice"/>, <s:property value="highPrice"/>]
       <s:if test="#rowStatus.last != true">
      	,
      	</s:if>
       </s:iterator>    
      // Treat first row as data as well.
    ], true);

    var options = {
      legend:'none'
    };

    var chart = new google.visualization.CandlestickChart(document.getElementById('chart_div'));

    chart.draw(data, options);
  }
    </script>
  </head>
  <body>
    <div id="chart_div" style="width: 900px; height: 500px;"></div>
  </body>
</html>

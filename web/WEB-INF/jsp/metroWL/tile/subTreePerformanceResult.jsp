<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>

<table style="display: table;" class="datatableInner" summary="Deployments" width="95%">
	<tbody>
		<tr>	
			<th width="20%" scope="col" title="Sort table Title">
				Source Scrip
			</th>
			<th width="20%" scope="col" title="Sort table Scrip">
				Target Scrip
			</th>
			<th width="20%" scope="col" title="Sort table Title">
				Joint Move
			</th>	
			<th width="20%" scope="col" title="Sort table Title">
				Source ROI
			</th>
			<th width="20%" scope="col" title="Sort table Title">
				Target ROI
			</th>					
		</tr>		
		<s:iterator value="resultList">
			<tr class="rowEven">		
				<td>									
					<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="sourceScrip.id"/>,800,600)" onmouseover="showtrail('<%=request.getContextPath()%>/chart/quickchart?CompareWith=&TimeRange=90&ChartSize=L&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=SMA&movAvg1=10&avgType2=SMA&movAvg2=25&Indicator1=SStoch&Indicator2=MACD&Indicator3=CCI&Button1=Update Chart&TickerID=<s:property value="sourceScrip.id"/>', '<s:property value="sourceScrip.name"/>',780,565)" onmouseout="hidetrail()">
						<s:property value="sourceScrip.name"/>
						<img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" id="chart-holder-<s:property value="sourceScrip.id"/>" style="zindex: 100; position: absolute; margin: auto;" />					
					</a>
				</td>
				<td>									
					<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="targetScrip.id"/>,800,600)" onmouseover="showtrail('<%=request.getContextPath()%>/chart/quickchart?CompareWith=&TimeRange=90&ChartSize=L&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=SMA&movAvg1=10&avgType2=SMA&movAvg2=25&Indicator1=SStoch&Indicator2=MACD&Indicator3=CCI&Button1=Update Chart&TickerID=<s:property value="targetScrip.id"/>', '<s:property value="targetScrip.name"/>',780,565)" onmouseout="hidetrail()">
						<s:property value="targetScrip.name"/>
						<img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" id="chart-holder-<s:property value="targetScrip.id"/>" style="zindex: 100; position: absolute; margin: auto;" />					
					</a>
				</td>
				<td>
					<s:property value="joinIncrement"/>
				</td>
				<td>
					<s:text name="rupees.format" >
						<s:param name="totalSourceGain" value="%{totalSourceGain}"/>
					</s:text>
				</td>
				<td>
					<s:text name="rupees.format" >
						<s:param name="totalTargetGain" value="%{totalTargetGain}"/>
					</s:text>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>













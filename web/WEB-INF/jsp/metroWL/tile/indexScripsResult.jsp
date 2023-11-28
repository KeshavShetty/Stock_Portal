<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>

<table style="display: table;" class="datatableInner" summary="Deployments" width="95%">
	<tbody>
		<tr>	
			<th width="25%" scope="col" title="Sort table Title">
				Scrip Name
			</th>
			<th width="15%" scope="col" title="Sort table Scrip">
				CMP
			</th>
			<th width="25%" scope="col" title="Sort table Title">
				Stochastic Value
			</th>						
		</tr>		
		<s:iterator value="resultList">
			<tr class="rowEven">		
				<td>									
					<a href="javascript:void(0)" onclick="javascript:showFloatingColorBox('<%=request.getContextPath()%>/scripInfo.do','jqIndex='+<s:property value="scrip.id"/>,800,600)" onmouseover="showtrail('<%=request.getContextPath()%>/chart/quickchart?CompareWith=&TimeRange=90&ChartSize=L&Volume=1&ParabolicSAR=0&LogScale=0&PercentageScale=0&ChartType=CandleStick&Band=BB&avgType1=SMA&movAvg1=10&avgType2=SMA&movAvg2=25&Indicator1=SStoch&Indicator2=MACD&Indicator3=CCI&Button1=Update Chart&TickerID=<s:property value="scrip.id"/>', '<s:property value="scrip.name"/>',780,565)" onmouseout="hidetrail()">
						<s:property value="scrip.name"/>
						<img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" id="chart-holder-<s:property value="scrip.id"/>" style="zindex: 100; position: absolute; margin: auto;" />					
					</a>
				</td>
				<td>
					<a href="javascript:void(0)">
						<s:text name="rupees.format" >
							<s:param name="scrip.cmp" value="%{scrip.cmp}"/>
						</s:text>
					</a>
				</td>
				<td>
					<s:if test="%{priceMoveTrend==false}">
						<img src="<%=request.getContextPath()%>/metroWL/images/down_r.gif">
					</s:if>
					<s:if test="%{priceMoveTrend==true}">
						<img src="<%=request.getContextPath()%>/metroWL/images/up_g.gif">
					</s:if>
					<s:text name="rupees.format" >
						<s:param name="stochasticValue" value="%{stochasticValue}"/>
					</s:text>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>













<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>
<table class="tablebuttonbar">
	<tr>
		<td class="tablecontrols">Quarterly results of 
			<a title="Latest result announcement on BSE" href="https://www.bseindia.com/stock-share-price/alkyl-amines-chemicals-ltd/alkylamine/<s:property value="%{selectedScrip.bseCode}"/>/corp-announements/" target="_blank"><b><s:property value="%{selectedScrip.name}"/> (BSE-<s:property value="%{selectedScrip.bseCode}"/>)</b></a>
		</td>
		<td class="tablecontrols" style="text-align:right">Past results
			<a title="Check on BSE" href="http://www.bseindia.com/stock-share-price/stockreach_financials.aspx?scripcode=<s:property value="%{selectedScrip.bseCode}"/>&expandable=0" target="_blank"><b>(BSE-<s:property value="%{selectedScrip.bseCode}"/>)</b></a>
			<a title="Check on NSE" href="http://www.nseindia.com/marketinfo/companyTracker/resultsCompare.jsp?symbol=<s:property value="%{selectedScrip.nseCode}"/>" target="_blank"><b>(NSE-<s:property value="%{selectedScrip.nseCode}"/>)</b></a>
			<a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/prepareAddNewQtrResult.do','scripId=<s:property value="%{selectedScrip.id}"/>')" title="Add new Qtr Result">
				<img src="<%=request.getContextPath()%>/metroWL/images/addnew.png">
			</a>	
			<a title="Standalone - Check on MC" href="http://www.moneycontrol.com/financials/voltas/results/quarterly-results/<s:property value="%{selectedScrip.mcCode}"/>" target="_blank">
				<img src="<%=request.getContextPath()%>/metroWL/images/mc.gif" width="16px" height="16px">
			</a>
			<a title="Consolidated - Check on MC" href="http://www.moneycontrol.com/financials/voltas/results/consolidated-quarterly-results/<s:property value="%{selectedScrip.mcCode}"/>" target="_blank">
				<img src="<%=request.getContextPath()%>/metroWL/images/mc.gif" width="16px" height="16px">
			</a>
					
		</td>
	</tr>
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	 
	<tr>
		<th>Period</th>	
		<th>Revenue</th>
		<th>OtherIncome</th>
		<th>TotalIncome</th>
		<th>Expenditure</th>
		<th>PBDT</th>
		<th>Depreciation</th>
		<th>Interest</th>
		<th>PBT(Gross Margin%)</th>
		<th>Tax (Tax%) </th>
		<th>NetProfit (Net Margin%)</th>
		<th>Equity</th>
		<th>EPS</th>
		<th>Price</th>
		<th>IsConsolidated</th>
	</tr>
	<s:iterator value="resultList" status="incr">
		<s:if test="#incr.index%4 == 0">
			<tr style="background-color: #fcd2ae;">
		</s:if>
		<s:elseif test="#incr.index%4 == 1">
			<tr style="background-color: #aefcf7;">
		</s:elseif>
		<s:elseif test="#incr.index%4 == 2">
			<tr style="background-color: #fcfcae;">
		</s:elseif>
		<s:else>
			<tr style="background-color: #E7E7E7;">
		</s:else>
			<td><s:property value="financiaReportQuarterId"/></td>
			<td><s:text name="rupees.format" ><s:param name="revenue" value="%{revenue}"/></s:text></td>
			<td><s:text name="rupees.format" ><s:param name="otherIncome" value="%{otherIncome}"/></s:text></td>
			<td><s:text name="rupees.format" ><s:param name="totalIncome" value="%{totalIncome}"/></s:text></td>
			<td><s:text name="rupees.format" ><s:param name="expenditure" value="%{expenditure}"/></s:text></td>
			<td><s:text name="rupees.format" ><s:param name="pbdt" value="%{pbdt}"/></s:text></td>
			<td><s:text name="rupees.format" ><s:param name="depreciation" value="%{depreciation}"/></s:text></td>
			<td><s:text name="rupees.format" ><s:param name="interest" value="%{interest}"/></s:text></td>
			<td>
				<s:text name="rupees.format" ><s:param name="pbt" value="%{pbt}"/></s:text>
				(<s:text name="rupees.format"><s:param name="pbtVsIncome" value="%{pbtVsIncome}"/></s:text>)
			</td>
			<td>
				<s:text name="rupees.format" ><s:param name="tax" value="%{tax}"/></s:text>(<s:text name="rupees.format"><s:param name="taxVsNP" value="%{taxVsNP}"/></s:text>)
			</td>
			<td>
				<s:text name="rupees.format" ><s:param name="netProfit" value="%{netProfit}"/></s:text>(<s:text name="rupees.format"><s:param name="nPVsIncome" value="%{nPVsIncome}"/></s:text>) 
			</td>
			<td><s:text name="rupees.format" ><s:param name="equity" value="%{equity}"/></s:text></td>
			<td><s:text name="rupees.format" ><s:param name="eps" value="%{eps}"/></s:text></td>
			<td><s:text name="rupees.format" ><s:param name="qtClosePrice" value="%{qtClosePrice}"/></s:text></td>
			<td><s:property value="isConsolidated"/></td>
		</tr>		
	</s:iterator>	
</table>
<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>
		<td>
			Execute	
						0. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=QtrResult AllSource <s:property value="%{selectedScrip.id}"/>">[QtrResult AllSource]</a>
						1. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=QtrResult Summary <s:property value="%{selectedScrip.id}"/>">[QtrResult Summary]</a>						
						2. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=QtrResult Screener <s:property value="%{selectedScrip.id}"/>">[QtrResult Screener]</a>
						3. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=QtrResult MC <s:property value="%{selectedScrip.id}"/>">[QtrResult MC]</a>
						4. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=LONGDURATIONEOD BSE <s:property value="%{selectedScrip.id}"/>">[LONGDURATIONEOD BSE]</a>
						5. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=LONGDURATIONEOD NSE <s:property value="%{selectedScrip.id}"/>">[LONGDURATIONEOD NSE]</a>
						5. <a target="_blank" href="http://localhost/portal/misc/commandlineServlet?command=NSE OptionChain <s:property value="%{selectedScrip.id}"/>">[Old:NFO Option Chain]</a>					
		</td>
	</tr>
</table>
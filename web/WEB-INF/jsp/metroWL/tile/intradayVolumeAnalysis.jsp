<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>


<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<tr>	
		<th scope="col">Past few days EOD Data</th>
		<th scope="col">Current intraday tick Data</th>
	</tr>
	<tr>
		<td style="vertical-align: top">
			<table class="datatable" id="genericTableFormtable">
				<tr>
					<th>Data Date</th>
					<th>ClosePrice</th>
					<th>Volume</th>
				</tr>
				<s:iterator value="pastdaysEodData">
					<tr class="rowEven">
						<td><s:property value="dataDate"/></td>
						<td><s:property value="closePrice"/></td>
						<td><s:property value="getText('{0,number,#,##0}',{volume})"/></td>
					</tr>
				</s:iterator>
			</table>
		</td>
		<td style="vertical-align: top">
			<table class="datatable" id="genericTableFormtable">
				<tr>
					<th>tickDatastamp</th>
					<th>cureentPrice</th>
					<th>cureentVolume</th>
					<th>totalVolume</th>
				</tr>
				<s:iterator value="intradayTickData">
					<tr class="rowEven">
						<td><s:date name="tickDatastamp" format="dd/MM/yyyy HH:mm" /></td>
						<td><s:property value="cureentPrice"/></td>
						<td><s:property value="getText('{0,number,#,##0}',{currentVolume})"/></td>
						<td><s:property value="getText('{0,number,#,##0}',{totalVolume})"/></td>
					</tr>
				</s:iterator>
			</table>
		</td>
	</tr>	
</table>
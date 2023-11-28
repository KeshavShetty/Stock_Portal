<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/Paginator" %>

<table class="datatable" id="genericTableFormtable" summary="Deployments">	
	<s:iterator value="volumeCalculations">
	<tr>
		<td>
			<s:property/>
		</td>
	</tr>
	</s:iterator>
	<tr>
		<td class="rowOdd">&nbsp;</td>
	</tr>
	<tr>
		<td class="rowOdd">Outstanding Volume:<b><u>${outstandingVolume}</u></b> Mean Price At Leftover Position: <b><u>${meanPriceAtleftover}</u></b></td>
	</tr>
	<tr>
		<td class="rowOdd">&nbsp;</td>
	</tr>
	<tr>
		<td>
			This is in experimental stage. In a rising trend choose lowest low day in last 10 days and select recent highest high as start and end trend dates.
    		<br/>If Outstanding Volume remains positive, The price is expected to rise or remain in same origin. A negative Outstanding Volume indicates most of the early traders and investors already exited.
    		<br/>Mean Price At Leftover Position: It is the price at which it supposed to provide support and bounce back until Outstanding volume changes sign.
    		<br/>Uses intraday data (1M tick data) for calculation of mean price.
		</td>
	</tr>
</table>
<%@ taglib prefix="s" uri="/struts-tags" %>

Default data page

<a href="javascript:void(0)" onclick="javascript:showMiscPage('/processBseIntradayData.do');">GetIntraday</a><br/>

<s:form action="updateEODData.do" method="post">
	Exchange code: <s:textfield name="exchangeCode" id="exchangeCode" cssClass="textinput"/><br/>
	Data date: <s:textfield name="dataDate" id="dataDate" cssClass="textinput"/><br/>
		
	<button type="button" onclick="submitMainScreenForm('updateEODData','MiscPageDiv')" name="Back" class="formButton">Submit</button>
</s:form>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.opensymphony.xwork2.ActionContext" %> 
<%@ page import="com.opensymphony.xwork2.util.ValueStack" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map.Entry" %> 
 
<% 
    ActionContext actionContext = ActionContext.getContext();    
    ValueStack valueStack = actionContext.getValueStack();    		 
    Map<String, Object> contextMap = valueStack.getContext();        
%>

<script language="Javascript">
	$(function() {
	 $( "#wlTransactionDate" ).datepicker();
	 });
</script>

<div id="portal-content-area" class="none">	
	<div class="spui-book-content">
		<div id="AppDeploymentsBook" class="spui-frame">
			<div class="top">
				<div>
					<div>
						&nbsp;
					</div>
				</div>
			</div>
			<div class="middle">
				<div class="r">
					<div class="c">
						<div class="c2">
							<div class="spui-book-content">
								
								<div id="AppApplicationInstallPage" class="page-assistant">
									<div id="AppApplicationInstallPortlet" class="spui-window  ">
										<div class="spui-window-content">
	
											<div class="contenttable">		
												<s:form action="addToWLTransaction.do" method="post">
													<table class="formTable" summary="" datatable="0">														
														<tbody>
															<tr class="row">
																<td colspan="4" style="text-align: center;">
																	<label>Add the Transaction to the Scrip </label><STRONG><s:property value="selectedScrip.name" />(BSE:<s:property value="selectedScrip.bseCode" />)&nbsp;(NSE:<s:property value="selectedScrip.nseCode" />)</STRONG>
																</td>
															</tr>
															<tr class="row">
																<td colspan="4" style="text-align: center;">
																	<div>
																		<s:radio list="#{'false':'Buy','true':'Sell'}" name="transactionType"></s:radio>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td colspan="4" style="text-align: center;">
																	<div>
																		<s:radio list="#{'false':'BSE','true':'NSE'}" name="exchange"></s:radio>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td colspan="4" style="text-align: center;">
																	<div>
																		<div><s:select cssClass="textinput" headerKey="-1" headerValue="Select the WL" name="watchListId" id="watchListId" list="userWatchlists" listValue="name" listKey="id" /></div>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td><label>Transaction Date</label></td>
																<td class="inputField" colspan="3">
																	<div><s:textfield name="transactionDate" id="wlTransactionDate" cssClass="textinput"/></div>
																</td>
															</tr>															
															<tr class="row">
																<td><label>Quantity</label></td>
																<td class="inputField" colspan="3">
																	<div><s:textfield name="quantity" id="quantity" cssClass="textinput"/></div>
																</td>
															</tr>															
															<tr class="row">
																<td><label>Rate</label></td>
																<td class="inputField" colspan="3">
																	<div><s:textfield name="rate" id="rate" cssClass="textinput"/></div>
																</td>
															</tr>
															<tr class="row">
																<td><label>Brokerage</label></td>
																<td class="inputField" colspan="3">
																	<div><s:textfield name="brokerage" id="brokerage" cssClass="textinput"/></div>
																</td>
															</tr>
															<tr class="row">
																<td><label>Settlement Number</label></td>
																<td class="inputField" colspan="3">
																	<div><s:textfield name="settlementNumber" id="settlementNumber" cssClass="textinput"/></div>
																</td>
															</tr>
														</tbody>
													</table>
													<div>
														<input name="AppApplicationInstallPortletfrsc" id="AppApplicationInstallPortletfrsc" value="0x0515ea8d8631aa072898773c41616eacc8b26e7a487d840d" type="hidden">
													</div>		
													<div class="lowerButtonBar">
														<div class="buttonBar">
															&nbsp;
															<button type="button" id="submitLoginButton" name="submitLoginButton" class="formButton" onclick="javascript:submitFloatingForm('addToWLTransaction');">
																	Save
															</button>  
															&nbsp;&nbsp;
															<s:reset id="Clear" cssClass="formButton" type="input" value="%{getText('msg.reset')}"/>   
															
														</div>
													</div>	
													<s:hidden name="scripId" id="scripId"/>										
												</s:form>
																							
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="bottom">
				<div>
					<div>
						&nbsp;
					</div>
				</div>
			</div>

		</div>
	</div>
</div>
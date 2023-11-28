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
												<s:form action="placeNOWOrder.do" method="post">
													<table class="formTable" summary="" datatable="0">														
														<tbody>
															<tr class="row">
																<td colspan="2" style="text-align: center;">
																	<label>Place NOW Order for </label><STRONG><s:property value="selectedScrip.name" />(BSE:<s:property value="selectedScrip.bseCode" /> NSE:<s:property value="selectedScrip.nseCode" />-<s:property value="selectedScrip.seriesType" />)</STRONG>
																</td>
															</tr>
															<tr class="row">
																<td><label>Buy or Sell?</label></td>
																<td class="inputField">
																	<div>
																		<s:select cssClass="textinput" headerKey="" headerValue="Select one" name="transactionType" id="transactionType" list="#{0:'Buy', 1:'Sell'}" />
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td><label>Exchange BSE or NSE?</label></td>
																<td class="inputField">
																	<div>
																		<s:select cssClass="textinput" headerKey="" headerValue="Select one" name="exchangeCode" id="exchangeCode" list="#{'BSE':'Bse', 'NSE':'Nse'}" />
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td><label>Type of order. Market or Limit?</label></td>
																<td class="inputField">
																	<div>
																		<s:select cssClass="textinput" headerKey="" headerValue="Select one" name="orderType" id="orderType" list="#{0:'Market', 1:'Limit'}" />
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td><label>Nature of order. Live or AMO?</label></td>
																<td class="inputField">
																	<div>
																		<s:select cssClass="textinput" headerKey="" headerValue="Select one" name="orderNature" id="orderType" list="#{'Live':'Live', 'AMO':'AMO'}" />
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td><label>Quantity:</label></td>
																<td class="inputField">
																	<div><s:textfield name="quantity" id="quantity" cssClass="textinput" onChange="javascript:calculateFields('quantity','atPrice','*','maximumBudget')"/></div>
																</td>
															</tr>
															<tr class="row">
																<td><label>At price:</label></td>
																<td class="inputField">
																	<div>
																		<s:textfield name="atPrice" id="atPrice" cssClass="textinput" onChange="javascript:calculateFields('maximumBudget','atPrice','/','quantity')"/>
																		<a href="javascript:void(0)" onclick="javascript:window.open('http://getquote.icicidirect.com/trading_stock_quote.aspx?Symbol=<s:property value="selectedScrip.iciciCode" />','QuoteWindow','width=980,height=500,status,scrollbars,resizable');">Get Quote</a>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td><label>Maximum budget:</label></td>
																<td class="inputField">
																	<div><s:textfield name="maximumBudget" id="maximumBudget" cssClass="textinput" onChange="javascript:calculateFields('maximumBudget','atPrice','/','quantity')"/></div>
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
															<button type="button" id="submitLoginButton" name="submitLoginButton" class="formButton" onclick="javascript:submitFloatingForm('placeNOWOrder');">
																	Place
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
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
												<s:form action="addNewQtrResult.do" method="post">
													<table class="formTable" summary="" datatable="0">														
														<tbody>
															<tr class="row">
																<td style="text-align: center;">
																	<label>Add New Qtr Result.</label></br> All amount should be in Crores
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		<s:radio list="#{'false':'Standalone','true':'Consolidated'}" value="SelectWL" name="isConsolidated"></s:radio>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		Financial Qtr ID: <s:textfield name="financialQtrId" id="financialQtrId" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		Revenue: <s:textfield name="revenue" id="revenue" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		Other Income: <s:textfield name="otherIncome" id="otherIncome" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		Total Income: <s:textfield name="totalIncome" id="totalIncome" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		Expenditure: <s:textfield name="expenditure" id="expenditure" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		Interest: <s:textfield name="interest" id="interest" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		PBDT: <s:textfield name="pbdt" id="pbdt" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		Depreciation: <s:textfield name="depreciation" id="depreciation" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		PBT: <s:textfield name="pbt" id="pbt" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		TAX: <s:textfield name="tax" id="tax" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		Net Profit: <s:textfield name="netprofit" id="netprofit" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		EPS: <s:textfield name="eps" id="eps" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		CEPS: <s:textfield name="ceps" id="ceps" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		OPM Percentage: <s:textfield name="opmPercentage" id="opmPercentage" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		NPM Percentage: <s:textfield name="npmPercentage" id="npmPercentage" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		CAR Percentage: <s:textfield name="carPercentage" id="carPercentage" cssClass="textinput"/>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td style="text-align: center;">
																	<div>
																		Qtr Close Price: <s:textfield name="qtrClosePrice" id="qtrClosePrice" cssClass="textinput"/>
																	</div>
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
															<button type="button" id="submitLoginButton" name="submitLoginButton" class="formButton" onclick="javascript:submitFloatingForm('addNewQtrResult');">
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
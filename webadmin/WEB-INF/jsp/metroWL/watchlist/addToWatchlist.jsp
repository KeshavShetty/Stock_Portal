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
												<s:form action="addToWatchList.do" method="post">
													<table class="formTable" summary="" datatable="0">														
														<tbody>
															<tr class="row">
																<td colspan="4" style="text-align: center;">
																	<label>Add the Scrip </label><STRONG><s:property value="selectedScrip.name" />(BSE:<s:property value="selectedScrip.bseCode" />)&nbsp;(NSE:<s:property value="selectedScrip.nseCode" />)</STRONG> to watchlist.<br/>Please select an existing watchlist or create new
																</td>
															</tr>
															<tr class="row">
																<td colspan="4" style="text-align: center;">
																	<div>
																		<s:radio list="#{'false':'Create new Watchlist','true':'Add to Existing watchlist'}" value="SelectWL" name="useExistingWatchList"></s:radio>
																	</div>
																</td>
															</tr>
															<tr class="row">
																<td><label>New Watchlist Name</label></td>
																<td class="inputField">
																	<div><s:textfield name="watchlistName" id="watchlistName" cssClass="textinput"/></div>
																</td>
																<td><label>Select Watchlist</label></td>
																<td class="inputField">
																	<div><s:select cssClass="textinput" headerKey="-1" headerValue="Select the WL" name="watchListId" id="watchListId" list="userWatchlists" listValue="name" listKey="id" /></div>
																</td>
															</tr>	
															<tr class="row">
																<td><label>Description</label></td>
																<td class="inputField"><div><s:textfield name="description" id="description" cssClass="textinput"/></div></td>
																<td colspan="2">&nbsp;</td>
															</tr>
														</tbody>
													</table>
													<div>
														<input name="AppApplicationInstallPortletfrsc" id="AppApplicationInstallPortletfrsc" value="0x0515ea8d8631aa072898773c41616eacc8b26e7a487d840d" type="hidden">
													</div>		
													<div class="lowerButtonBar">
														<div class="buttonBar">
															&nbsp;
															<button type="button" id="submitLoginButton" name="submitLoginButton" class="formButton" onclick="javascript:submitFloatingForm('addToWatchList');">
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
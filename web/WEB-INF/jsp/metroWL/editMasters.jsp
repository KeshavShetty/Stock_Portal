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
												<s:form action="saveMasters.do" method="post">
													<table class="formTable" summary="" datatable="0">														
														<tbody>
															<tr class="row">
																<td colspan=2" style="text-align: center;">
																	<label>Edit masters</label>
																</td>
															</tr>															
															<tr class="row">
																<td><label>New value</label></td>
																<td class="inputField">
																	<div><s:textfield name="newValue" id="newValue" cssClass="textinput"/></div>
																</td>																
															</tr>
														</tbody>
													</table>
													<div class="lowerButtonBar">
														<div class="buttonBar">
															&nbsp;
															<button type="button" id="submitLoginButton" name="submitLoginButton" class="formButton" onclick="javascript:submitFloatingForm('saveMasters');">
																	Save
															</button>  
															&nbsp;&nbsp;
															<s:reset id="Clear" cssClass="formButton" type="input" value="%{getText('msg.reset')}"/>   
															
														</div>
													</div>	
													<s:hidden name="pkId" id="pkId"/>
													<s:hidden name="tbl" id="tbl"/>
													<s:hidden name="col" id="col"/>
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
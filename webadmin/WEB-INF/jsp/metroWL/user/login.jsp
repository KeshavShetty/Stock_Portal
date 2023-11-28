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
												<s:form action="login.do" method="post">
													<table class="formTable" summary="" datatable="0">
														<colgroup>
															<col class="labelCol">
															<col class="inputCol">
															<col class="paddingCol">
														</colgroup>
														<tbody>
															<tr class="rowIntro">
																<td colspan="3"><span class="dialog-info">Sign in: Please enter your username and password to login
																	<br>
																</span></td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletname_row">
																<td><label for="AppApplicationInstallPortletname"><s:text name="logon.username.displayname"/></label></td><td class="inputField">
																<div>
																	<s:textfield name="username" id="username" cssClass="textinput"/>																	
																</div></td><td>&nbsp;</td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletname_row">
																<td><label for="AppApplicationInstallPortletname"><s:text name="logon.password.displayname"/></label></td><td class="inputField">
																<div>
																	<s:password name="password" id="password" cssClass="textinput"/>																	
																</div></td><td>&nbsp;</td>
															</tr>														
															
														</tbody>
													</table>
													<div>
														<input name="AppApplicationInstallPortletfrsc" id="AppApplicationInstallPortletfrsc" value="0x0515ea8d8631aa072898773c41616eacc8b26e7a487d840d" type="hidden">
													</div>		
													<div class="lowerButtonBar">
														<div class="buttonBar">
															&nbsp;
															<button type="button" id="submitLoginButton" name="submitLoginButton" class="formButton" onclick="javascript:submitFloatingForm('login');">
																	Login
															</button>  
															&nbsp;&nbsp;
															<s:reset id="Clear" cssClass="formButton" type="input" value="%{getText('msg.reset')}"/>   
															&nbsp;&nbsp;
															<input type="button" name="closeFormButton" class="formButton" id="closeFormButton" value="Close"/>
														</div>
													</div>											
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
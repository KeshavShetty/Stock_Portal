<%@ taglib prefix="s" uri="/struts-tags" %>

<script language="Javascript">
	$(document).ready( function(){
		submitScreenForm('searchFinancialResult','searchFinancialResultTable');
		document.getElementById("scripCodeHidden").value=document.getElementById("searchFinancialResultScripCode").value;
		document.getElementById("finResultTypeIdHidden").value=document.getElementById("finResultTypeId").value;
		submitScreenForm('showFinancialResultGraph','highchartsFinancialGrpahContainer');
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
								<div class="spui-titlebar">
									<div class="float-container">
										<div class="spui-titlebar-title-panel">
											<h1>Financial results:</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											&nbsp;
										</div>
									</div>
								</div>
								<div id="pop_form_body">
									<div id="advancedAnalysisSearchFormTable">
										<s:form action="searchFinancialResult.do" method="post">												
											<table class="formTable" summary="" datatable="0">																	
												<tbody>	
													<tr class="row" id="AppApplicationInstallPortletname_row">
														<td><label class="fieldLabel">ScripCode:</label></td>
														<td class="inputField">
															<div>
																<s:textfield name="scripCode" id="searchFinancialResultScripCode" cssClass="textinput"/>
															</div>
														</td>
														<td class="inputField">
															<div>
																<s:select cssClass="textinput" name="finResultType" id="finResultTypeId" list="#{'true':'Consolidated','false':'Standalone'}" />
															</div>
														</td>
														<td>
															<button type="button" 
																onclick="this.form.pageNumber.value=1;submitScreenForm('searchFinancialResult','searchFinancialResultTable');document.getElementById('scripCodeHidden').value=document.getElementById('searchFinancialResultScripCode').value;document.getElementById('finResultTypeIdHidden').value=document.getElementById('finResultTypeId').value;submitScreenForm('showFinancialResultGraph','highchartsFinancialGrpahContainer');" name="Back" class="formButton">
																Show
															</button>
														</td>																		
													</tr>																																																					
												</tbody>
											</table>
											<s:hidden name="orderBy" id="orderBy"/>
											<s:hidden name="orderType" id="orderType"/>
											<s:hidden name="pageNumber" id="pageNumber"/>
											<s:hidden name="recordsPerPage" id="recordsPerPage"/>
											<s:hidden name="sourceFormName" id="sourceFormName"/>
											<s:hidden name="divToFill" id="divToFill"/>
										</s:form>
									</div>
									<table class="dialog" style="width: 100%;">
										<tbody>
											<tr>
												<td style="text-align: center;">
													<div id="highchartsFinancialGrpahContainer">
																																	
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div id="pop_form_body">
									<div id="advancedAnalysisSearchFormTable">
										<s:form action="showFinancialResultGraph.do" method="post">
											<s:hidden name="sourceFormName" id="sourceFormName"/>
											<s:hidden name="divToFill" id="divToFill"/>
											<s:hidden name="scripCode" id="scripCodeHidden"/>
											<s:hidden name="finResultType" id="finResultTypeIdHidden"/>											
										</s:form>
									</div>
									<table class="dialog" style="width: 100%;">
										<tbody>
											<tr>
												<td style="text-align: center;">
													<div id="searchFinancialResultTable">																			
													</div>
												</td>
											</tr>
										</tbody>
									</table>														
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
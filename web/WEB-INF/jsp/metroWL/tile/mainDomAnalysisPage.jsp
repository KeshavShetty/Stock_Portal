<%@ taglib prefix="s" uri="/struts-tags" %>

<script language="Javascript">
	$(document).ready( function(){
		submitScreenForm('showDomAnalysisGraph','highchartsDomGrpahContainer');
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
										<s:form action="showDomAnalysisGraph.do" method="post">												
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
																From:<s:textfield name="fromTime" id="searchFinancialResultfromTime" cssClass="textinputLong"/>
															</div>
														</td>
														<td class="inputField">
															<div>
																To:<s:textfield name="toTime" id="searchFinancialResulttoTime" cssClass="textinputLong"/>
															</div>
														</td>
														<td>
															<button type="button" 
																onclick="submitScreenForm('showDomAnalysisGraph','highchartsDomGrpahContainer');" name="Back" class="formButton">
																Show
															</button>
														</td>																		
													</tr>																																																					
												</tbody>
											</table>
										</s:form>
									</div>
									<table class="dialog" style="width: 100%;">
										<tbody>
											<tr>
												<td style="text-align: center;">
													<div id="highchartsDomGrpahContainer">
																																	
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
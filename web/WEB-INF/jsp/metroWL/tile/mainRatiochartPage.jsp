<%@ taglib prefix="s" uri="/struts-tags" %>

<script language="Javascript">

	$(function() {
	 	$( "#dataFromDate" ).datepicker();
	 	$( "#dataToDate" ).datepicker();
	 });
	 

	$(document).ready( function(){
		submitScreenForm('searchRatioChartView','highchartsRatioChartContainer');		
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
											<h1>Financial Ratio Chart:</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											&nbsp;
										</div>
									</div>
								</div>
								<div id="pop_form_body">
									<div id="advancedAnalysisSearchFormTable">
										<s:form action="searchRatioChartView.do" method="post">												
											<table class="formTable" summary="" datatable="0">																	
												<tbody>	
													<tr class="row" id="AppApplicationInstallPortletname_row">
														<td><label class="fieldLabel">ScripCode:</label></td>
														<td class="inputField">
															<div>
																<s:textfield name="scripCode" id="ratioChartScripCode" cssClass="textinput"/>
															</div>
														</td>
														
														<td><label class="fieldLabel">From Date:</label></td>
														<td class="inputField">
															<div>
																<s:textfield name="fromDate" id="dataFromDate" cssClass="textinput"/>
															</div>
														</td>
														<td><label class="fieldLabel">To Date:</label></td>
														<td class="inputField">
															<div>
																<s:textfield name="toDate" id="dataToDate" cssClass="textinput"/>
															</div>
														</td>
														<td><label class="fieldLabel">First Parameter:</label></td>
														<td class="inputField">
															<div>
																<s:select cssClass="textinput" headerKey="" headerValue="Select the Parameter" name="selectedFirstParameter" id="selectedFirstParameter" list="ratioParamsMap"/>																					
															</div>
														</td>
														<td><label class="fieldLabel">Next Parameter:</label></td>
														<td class="inputField">
															<div>
																<s:select cssClass="textinput" headerKey="" headerValue="Select the Parameter" name="selectedNextParameter" id="selectedNextParameter" list="ratioParamsMap" />																					
															</div>
														</td>
														<td>
															<button type="button" 
																onclick="submitScreenForm('searchRatioChartView','highchartsRatioChartContainer');" name="Back" class="formButton">
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
													<div id="highchartsRatioChartContainer">
																																	
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								
								
								
								<div id="pop_form_body">
									<div id="advancedAnalysisSearchFormTable">
										<s:form action="generateScripLevelPriceVsRatioCompareGraph.do" method="post">												
											<table class="formTable" summary="" datatable="0">																	
												<tbody>	
													<tr class="row" id="AppApplicationInstallPortletname_row">
														<td><label class="fieldLabel">Scrip Code:</label></td>
														<td class="inputField">
															<div>
																<s:textfield name="scripCode" id="scripCode" cssClass="textinputVeryLong"/>
															</div>
														</td>
														<td>
															<button type="button" onclick="submitScreenForm('generateScripLevelPriceVsRatioCompareGraph','scripLevelRatioSearchResultTable')" name="Back" class="formButton">
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
													<div id="scripLevelRatioSearchResultTable">																																	
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
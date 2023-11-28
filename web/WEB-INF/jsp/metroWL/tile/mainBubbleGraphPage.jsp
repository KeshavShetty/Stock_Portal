<%@ taglib prefix="s" uri="/struts-tags" %>

<script language="Javascript">
	$(function() {
	 $( "#fromDate" ).datepicker();
	 $( "#toDate" ).datepicker();
	 });
	$(document).ready( function(){
		submitScreenForm('generateBubbleGraph','bubbleGraphSearchResultTable');
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
						<div>
							<div class="spui-book-content">								
								<div id="AppApplicationInstallPage" class="page-assistant">
									<div id="AppApplicationInstallPortlet" class="spui-window  ">
										<div class="spui-window-content">	
											<div class="contenttable">
												<div style="padding: 0px; left: -2px; top: 13px; width:99.6%;" class="popform">
													<div style="cursor: move; white-space: nowrap;" class="hdr" onmousedown="on_div_drag_start(event,this);">
														<div style="float: right; font-size: 15px; margin: -2px -2px 0 4px;">
															<a href="javascript:void(0)" onclick="gui_pop_form_remove(); return false;" title="Close">×</a>
														</div>
														<div style="float: right; margin-top: -2px;" class="sml span-q" onmouseover="pb_show(event,'ab');">&nbsp;</div>
														<em class="med">Scrip Ratio Comparison Chart:</em>
													</div>
													<div id="pop_form_body">
														<div id="bubbleGraphAnalysisSearchFormTable">
															<s:form action="generateScripCompareGraph.do" method="post">												
																<table class="formTable" summary="" datatable="0">																	
																	<tbody>	
																		<tr class="row" id="AppApplicationInstallPortletname_row">
																			<td><label class="fieldLabel">Scrip Codes:</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="scripCodes" id="scripCodes" cssClass="textinputVeryLong"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">Watchlist:</label></td>
																			<td class="inputField">
																				<div>
																					<s:select cssClass="textinput" headerKey="" headerValue="Select the Watchlist" name="selectedWatchlist" id="selectedWatchlist" list="userWatchlists" listValue="name" listKey="id" />																					
																				</div>
																			</td>
																			<td>
																				<button type="button" onclick="submitScreenForm('generateScripCompareGraph','bubbleGraphSearchResultTableDem')" name="Back" class="formButton">
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
																		<div id="bubbleGraphSearchResultTableDem">																			
																		</div>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
												<div style="padding: 0px; left: -2px; top: 495px; width:99.6%;" class="popform">
													<div style="cursor: move; white-space: nowrap;" class="hdr" onmousedown="on_div_drag_start(event,this);">
														<div style="float: right; font-size: 15px; margin: -2px -2px 0 4px;">
															<a href="javascript:void(0)" onclick="gui_pop_form_remove(); return false;" title="Close">×</a>
														</div>
														<div style="float: right; margin-top: -2px;" class="sml span-q" onmouseover="pb_show(event,'ab');">&nbsp;</div>
														<em class="med">Scrip Level Price Movement vs Ratio:</em>
													</div>
													<div id="pop_form_body">
														<div id="scripLevelRatioSearchFormTable">
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
												<div style="padding: 0px; left: -2px; top: 980px; width:99.6%;" class="popform">
													<div style="cursor: move; white-space: nowrap;" class="hdr" onmousedown="on_div_drag_start(event,this);">
														<div style="float: right; font-size: 15px; margin: -2px -2px 0 4px;">
															<a href="javascript:void(0)" onclick="gui_pop_form_remove(); return false;" title="Close">×</a>
														</div>
														<div style="float: right; margin-top: -2px;" class="sml span-q" onmouseover="pb_show(event,'ab');">&nbsp;</div>
														<em class="med">Bubble Graph:</em>
													</div>
													<div id="pop_form_body">
														<div id="bubbleGraphAnalysisSearchFormTable">
															<s:form action="generateBubbleGraph.do" method="post">												
																<table class="formTable" summary="" datatable="0">																	
																	<tbody>	
																		<tr class="row" id="AppApplicationInstallPortletname_row">
																			<td><label class="fieldLabel">BSE Code:</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="bseCode" id="bseCode" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">NSE Code:</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="nseCode" id="nseCode" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">From Date:</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="fromDate" id="fromDate" cssClass="textinput"/>
																				</div>
																			</td>
																			<td class="inputField">
																				<div>To Date: <s:textfield name="toDate" id="toDate" cssClass="textinput"/></div>
																			</td>																			
																		</tr>
																		<tr class="row" id="AppApplicationInstallPortletname_row">
																			<td><label class="fieldLabel">Watchlist:</label></td>
																			<td class="inputField">
																				<div>
																					<s:select cssClass="textinput" headerKey="" headerValue="Select the Watchlist" name="selectedWatchlist" id="selectedWatchlist" list="userWatchlists" listValue="name" listKey="id" />																					
																				</div>
																			</td>
																			
																			<td><label class="fieldLabel">Sector:</label></td>
																			<td class="inputField">
																				<div>
																					<s:select cssClass="textinput" headerKey="" headerValue="Select the Sector" name="selectedSector" id="selectedSector" list="sectors" listValue="name" listKey="id" />																					
																				</div>
																			</td>
																			
																			<td><label class="fieldLabel">Todo:</label></td>
																			<td class="inputField">
																				<div>
																					<s:select cssClass="textinput" headerKey="" headerValue="Select one" name="selectedRankingOption"  id="selectedRankingOption" list="#{1:'WLRank', 2:'SqGrowth', 3:'MarginGrowth'}" />
																				</div>
																			</td>
																			
																			<td>
																				<button type="button" onclick="submitScreenForm('generateBubbleGraph','bubbleGraphSearchResultTable')" name="Back" class="formButton">
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
																		<div id="bubbleGraphSearchResultTable">																			
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
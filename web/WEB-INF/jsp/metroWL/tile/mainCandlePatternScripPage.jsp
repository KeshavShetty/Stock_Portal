<%@ taglib prefix="s" uri="/struts-tags" %>

<script language="Javascript">
	$(function() {
	 $( "#candlePatternScripFromDate" ).datepicker();
	 $( "#candlePatternScripToDate" ).datepicker();
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
												<div style="padding: 0px; left: -2px; top: 1px; width:99.6%;" class="popform">
													<div style="cursor: move; white-space: nowrap;" class="hdr" onmousedown="on_div_drag_start(event,this);">
														<div style="float: right; font-size: 15px; margin: -2px -2px 0 4px;">
															<a href="javascript:void(0)" onclick="gui_pop_form_remove(); return false;" title="Close">�</a>
														</div>
														<div style="float: right; margin-top: -2px;" class="sml span-q" onmouseover="pb_show(event,'ab');">&nbsp;</div>
														<em class="med">Candle Pattern Scrip Match Search:</em>
													</div>
													<div id="pop_form_body">
														<div id="candlePatternScripSearchFormTable">
															<s:form action="advancedCandlePatternScrip.do" method="post">												
																<table class="formTable" summary="" datatable="0">																	
																	<tbody>	
																		<tr class="row" id="AppApplicationInstallPortletname_row">
																			<td><label class="fieldLabel">From Date:</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="fromDate" id="candlePatternScripFromDate" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">To Date:</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="toDate" id="candlePatternScripToDate" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">UpCount(Min):</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="minUpCount" id="minUpCount" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">DownCount(Min):</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="minDownCount" id="minDownCount" cssClass="textinput"/>
																				</div>
																			</td>																			
																		</tr>																																
																		<tr class="row" id="AppApplicationInstallPortletname_row">
																			<td><label class="fieldLabel">Symbol Name :</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="scripName" id="scripName" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">BSE Code :</label></td>
																			<td class="inputField">
																				<div><s:textfield name="bseCode" id="bseCode" cssClass="textinput"/></div>
																			</td>
																			<td><label class="fieldLabel">NSE Code :</label></td>
																			<td class="inputField">
																				<div><s:textfield name="nseCode" id="nseCode" cssClass="textinput"/></div>
																			</td>
																			<td><label class="fieldLabel">Avg Volume:</label></td>
																			<td class="inputField">
																				<div><s:textfield name="averageVolume" id="averageVolume" cssClass="textinput"/></div>
																			</td>
																		</tr>
																		<tr class="row" id="AppApplicationInstallPortletname_row">
																			<td><label class="fieldLabel">CMP(b/w):</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="minCmp" id="minCmp" cssClass="textinput"/>
																					<s:textfield name="maxCmp" id="maxCmp" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">EPS(b/w):</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="minEps" id="minEps" cssClass="textinput"/>
																					<s:textfield name="maxEps" id="maxEps" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">PE(b/w):</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="minPe" id="minPe" cssClass="textinput"/>
																					<s:textfield name="maxPe" id="maxPe" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">Watchlist:</label></td>
																			<td class="inputField">
																				<div>
																					<s:select cssClass="textinput" headerKey="" headerValue="Select the Watchlist" name="selectedWatchlist" id="selectedWatchlist" list="userWatchlists" listValue="name" listKey="id" />																					
																				</div>
																			</td>
																			<td>
																				<button type="button" onclick="this.form.pageNumber.value=1;submitScreenForm('advancedCandlePatternScrip','candlePatternScripSearchSearchResultTable')" name="Back" class="formButton">
																					Search
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
																		<div id="candlePatternScripSearchSearchResultTable">																			
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
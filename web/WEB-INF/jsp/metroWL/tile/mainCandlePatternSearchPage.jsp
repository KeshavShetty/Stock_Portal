<%@ taglib prefix="s" uri="/struts-tags" %>

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
															<a href="javascript:void(0)" onclick="gui_pop_form_remove(); return false;" title="Close">×</a>
														</div>
														<div style="float: right; margin-top: -2px;" class="sml span-q" onmouseover="pb_show(event,'ab');">&nbsp;</div>
														<em class="med">Candle Pattern Search:</em>
													</div>
													<div id="pop_form_body">
														<div id="CandlePatternSearchFormTable">
															<s:form action="searchCandlePattern.do" method="post">												
																<table class="formTable" summary="" datatable="0">																	
																	<tbody>	
																		<tr class="row" id="AppApplicationInstallPortletname_row">
																			<td><label class="fieldLabel">Min Up count:</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="minUp" id="minUp" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">Min Down Count:</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="minDown" id="minDown" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">Min Count Rank:</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="minCountRank" id="minCountRank" cssClass="textinput"/>%
																				</div>
																			</td>
																			<td>
																				<button type="button" onclick="this.form.pageNumber.value=1;submitScreenForm('searchCandlePattern','CandlePatternResultTable')" name="Back" class="formButton">
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
																		<div id="CandlePatternResultTable">																			
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
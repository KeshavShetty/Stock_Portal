<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>

<script language="Javascript">
	$(function() {
	 $( "#wlDiffViewLeftDate" ).datepicker();
	 $( "#wlDiffViewRightDate" ).datepicker();
	 });
	$(document).ready( function(){ 
		//submitScreenForm('resultWatchlistDiffView','watchlistDiffViewResultTable');
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
												<div style=" padding: 0px; left: -2px; top: 1px; width:99.6%;" class="popform">
													<div style="cursor: move; white-space: nowrap;" class="hdr" onmousedown="on_div_drag_start(event,this);">
														<div style="float: right; font-size: 15px; margin: -2px -2px 0 4px;">
															<a href="javascript:void(0)" onclick="gui_pop_form_remove(); return false;" title="Close">×</a>
														</div>
														<div style="float: right; margin-top: -2px;" class="sml span-q" onmouseover="pb_show(event,'ab');">&nbsp;</div>
														<em class="med">Watch-list Difference View:</em>
													</div>
													<div id="pop_form_body">
														<div id="advancedAnalysisSearchFormTable">
															<s:form action="resultWatchlistDiffView.do" method="post">												
																<table class="formTable" summary="" datatable="0">																	
																	<tbody>	
																		<tr class="row" id="AppApplicationInstallPortletname_row">
																			<td><label class="fieldLabel">Compare Watchlist:</label></td>
																			<td class="inputField">
																				<div>
																					<s:select cssClass="textinput" headerKey="" headerValue="Select the Watchlist" name="leftWatchlist" id="leftWatchlist" list="userWatchlists" listValue="name" listKey="id" />																					
																				</div>
																			</td>																			
																			<td><label class="fieldLabel">On Date:</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="leftWLDate" id="wlDiffViewLeftDate" cssClass="textinput"/>
																				</div>
																			</td>
																			<td><label class="fieldLabel">With Watchlist:</label></td>
																			<td class="inputField">
																				<div>
																					<s:select cssClass="textinput" headerKey="" headerValue="Select the Watchlist" name="rightWatchlist" id="rightWatchlist" list="userWatchlists" listValue="name" listKey="id" />																					
																				</div>
																			</td>																			
																			<td><label class="fieldLabel">On Date:</label></td>
																			<td class="inputField">
																				<div>
																					<s:textfield name="rightWLDate" id="wlDiffViewRightDate" cssClass="textinput"/>
																				</div>
																			</td>
																			<td>
																				<button type="button" onclick="submitScreenForm('resultWatchlistDiffView','watchlistDiffViewResultTable')" name="Back" class="formButton">
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
																		<div id="watchlistDiffViewResultTable">																			
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
<div id="WatchlistSectorAnalysisTable">
	<JqGridTable:addTable tableIdentifier="Watchlist_SectorAnalysisTable" formRequired="true"/>
</div>
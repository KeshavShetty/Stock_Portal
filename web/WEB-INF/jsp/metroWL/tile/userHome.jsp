<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>

<script language="Javascript">
	$(function() {
	 $( "#wlTransactionFromDate" ).datepicker();
	 $( "#wlTransactionToDate" ).datepicker();
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
											<JqGridTable:addTable tableIdentifier="WatchlistPage_ScripwiseTable" formRequired="true"/>
											<JqGridTable:addTable tableIdentifier="WatchlistPage_WLPerformanceTable" formRequired="true"/>
											<JqGridTable:addTable tableIdentifier="HomePage_ForthcomingResultsTable" formRequired="true"/>
											
												TODO: Under development
												<div style="padding: 0px; width:99.6%;" class="popform">
													<div style="cursor: move; white-space: nowrap;" class="hdr">
														<div style="float: right; font-size: 15px; margin: -2px -2px 0 4px;">
															<a href="javascript:void(0)" onclick="gui_pop_form_remove(); return false;" title="Close">×</a>
														</div>
														<div style="float: right; margin-top: -2px;" class="sml span-q" onmouseover="pb_show(event,'ab');">&nbsp;</div>
														<em class="med">User Home: Select Watchlist</em>
													</div>
													<div id="pop_form_body">
														<div id="scripSearchFormTable">
															<s:form action="viewWatchlist.do" method="post">												
																<table class="formTable" summary="" datatable="0">																	
																	<tbody>																																	
																		<tr class="row" id="AppApplicationInstallPortletname_row">
																			<td><label class="fieldLabel">Watchlist :</label></td>
																			<td class="inputField">
																				<div>
																					<s:select cssClass="textinput" name="watchlistId" id="watchlistId" list="userWLs" listValue="name" listKey="id" />
																				</div>																	
																			</td>
																			<td>
																				<div>
																					<s:select cssClass="textinput" name="groupOrder" id="groupOrder" list="#{'Scripwise':'Scripwise', 'Datewise':'Datewise'}" />
																				</div>
																			</td>	
																			<td>
																				<div>
																					From Date:<s:textfield name="fromDate" id="wlTransactionFromDate" cssClass="textinput"/>&nbsp;
																					To Date:<s:textfield name="toDate" id="wlTransactionToDate" cssClass="textinput"/>&nbsp;
																				</div>
																			</td>																		
																			<td>
																				<s:checkbox name="includeZeroHolidngItems" fieldValue="true" value="false" label="Include zero holidng Items?"/>Include zero holidng Items?
																			</td>
																			<td>
																				<button type="button" onclick="submitScreenForm('viewWatchlist','userWatchlistResultTable')" name="Back" class="formButton">
																					Load
																				</button>
																			</td>
																			<td>&nbsp;</td>
																		</tr>																		
																	</tbody>
																</table>																
																<s:hidden name="sourceFormName" id="sourceFormName"/>
																<s:hidden name="divToFill" id="divToFill"/>
															</s:form>
														</div>
														<table class="dialog" style="width: 100%;">
															<tbody>
																<tr>
																	<td style="text-align: center;">
																		<div id="userWatchlistResultTable">
																			
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
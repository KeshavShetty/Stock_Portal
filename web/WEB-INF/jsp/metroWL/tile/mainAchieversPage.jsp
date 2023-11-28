<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>

<script language="Javascript">
	$(function() {
	 $( "#firstDate" ).datepicker();
	 $( "#lastDate" ).datepicker();
	 });
	$(document).ready( function(){
		submitScreenForm('searchPastAchievers','pastAchieversSearchResultTable');
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
											<h1>Achievers Page</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('analysisSearchFormTable')">
												<img src="<%=request.getContextPath()%>/metroWL/images/minimize.png" class="" title="Minimize" alt="Minimize ">
											</a>
										</div>
									</div>
								</div>
								<div id="AppDeploymentsPages" class="spui-page">
									<div id="ScripScripDivBody" class="spui-book">
										<div class="spui-book-content">
											<div id="AppDeploymentsControlPage" class="page-content">
												<div id="AppDeploymentsControlPortlet" class="spui-window  ">
													<div class="spui-window-content">
														<div id="analysisSearchFormTable">
															<div class="contenttable">
															
																<s:form action="searchPastAchievers.do" method="post">												
																	<table class="formTable" summary="" datatable="0">																	
																		<tbody>	
																			<tr class="row" id="AppApplicationInstallPortletname_row">
																				<td><label class="fieldLabel">First Date:</label></td>
																				<td class="inputField">
																					<div>
																						<s:textfield name="firstDate" id="firstDate" cssClass="textinput"/>
																					</div>
																				</td>
																				<td><label class="fieldLabel">Last Date:</label></td>
																				<td class="inputField">
																					<div>
																						<s:textfield name="lastDate" id="lastDate" cssClass="textinput"/>
																					</div>
																				</td>
																				<td><label class="fieldLabel">Exchange:</label></td>
																				<td class="inputField">
																					<div>
																						<s:select cssClass="textinput" headerKey="" headerValue="Select the Exchange" name="exchange" id="exchange" list="#{'BSE':'BSE', 'NSE':'NSE'}" />
																					</div>
																				</td>
																				<td class="inputField">
																					<div>Avg Volume: <s:textfield name="averageVolume" id="averageVolume" cssClass="textinput"/></div>
																				</td>																			
																			</tr>
																			<tr class="row" id="AppApplicationInstallPortletname_row">
																				<td><label class="fieldLabel">Performance(b/w):</label></td>
																				<td class="inputField">
																					<div>
																						<s:textfield name="minPerformance" id="minPerformance" cssClass="textinput"/>
																						<s:textfield name="maxPerformance" id="maxPerformance" cssClass="textinput"/>
																					</div>
																				</td>
																				<td><label class="fieldLabel">Vol Performance(b/w):</label></td>
																				<td class="inputField">
																					<div>
																						<s:textfield name="minVolumePerformance" id="minVolumePerformance" cssClass="textinput"/>
																						<s:textfield name="maxVolumePerformance" id="maxVolumePerformance" cssClass="textinput"/>
																					</div>
																				</td>
																				<td><label class="fieldLabel">Watchlist:</label></td>
																				<td class="inputField">
																					<div>
																						<s:select cssClass="textinput" headerKey="" headerValue="Select the Watchlist" name="selectedWatchlist" id="selectedWatchlist" list="userWatchlists" listValue="name" listKey="id" />																					
																					</div>
																				</td>
																				<td class="inputField">
																					<div>2nd Watchlist
																						<s:select cssClass="textinput" headerKey="" headerValue="Select the Watchlist" name="selectedSecondWatchlist" id="selectedSecondWatchlist" list="userWatchlists" listValue="name" listKey="id" />																					
																					</div>
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
																				<td>
																					<button type="button" onclick="this.form.pageNumber.value=1;submitScreenForm('searchPastAchievers','pastAchieversSearchResultTable')" name="Back" class="formButton">
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
															
															<table class="dialog" style="width: 100%;">
															<tbody>
																<tr>
																	<td style="text-align: center;">
																		<div id="pastAchieversSearchResultTable">																			
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
											<h1>NSE JQGridBased Achievers Page</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('JqGridBasedNSEAchieversPageAnalysisFormTable')">
												<img src="<%=request.getContextPath()%>/metroWL/images/minimize.png" class="" title="Minimize" alt="Minimize ">
											</a>
										</div>
									</div>
								</div>
								<div id="AppDeploymentsPages" class="spui-page">
									<div id="ScripScripDivBody" class="spui-book">
										<div class="spui-book-content">
											<div id="AppDeploymentsControlPage" class="page-content">
												<div id="AppDeploymentsControlPortlet" class="spui-window  ">
													<div class="spui-window-content">
														<div class="contenttable">
															<div id="JqGridBasedNSEAchieversPageAnalysisFormTable">
																<JqGridTable:addTable tableIdentifier="NSE_AchieversPage_AchieverTable" formRequired="true"/>
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
											<h1>BSE JQGridBased Achievers Page</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('JqGridBasedBSEAchieversPageAnalysisFormTable')">
												<img src="<%=request.getContextPath()%>/metroWL/images/minimize.png" class="" title="Minimize" alt="Minimize ">
											</a>
										</div>
									</div>
								</div>
								<div id="AppDeploymentsPages" class="spui-page">
									<div id="ScripScripDivBody" class="spui-book">
										<div class="spui-book-content">
											<div id="AppDeploymentsControlPage" class="page-content">
												<div id="AppDeploymentsControlPortlet" class="spui-window  ">
													<div class="spui-window-content">
														<div class="contenttable">
															<div id="JqGridBasedBSEAchieversPageAnalysisFormTable">
																<JqGridTable:addTable tableIdentifier="BSE_AchieversPage_AchieverTable" formRequired="true"/>
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
											<h1>Zerodha Intraday Orders</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('JqGridBasedZerodhaIntradayOrdersFormTable')">
												<img src="<%=request.getContextPath()%>/metroWL/images/minimize.png" class="" title="Minimize" alt="Minimize ">
											</a>
										</div>
									</div>
								</div>
								<div id="AppDeploymentsPages" class="spui-page">
									<div id="ScripScripDivBody" class="spui-book">
										<div class="spui-book-content">
											<div id="AppDeploymentsControlPage" class="page-content">
												<div id="AppDeploymentsControlPortlet" class="spui-window  ">
													<div class="spui-window-content">
														<div class="contenttable">
															<div id="JqGridBasedZerodhaIntradayOrdersFormTable">
																<JqGridTable:addTable tableIdentifier="Zerodha_IntradayOrdersTable" formRequired="true"/>
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
				</div>
			</div>		
		</div>	
	</div>
</div>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>

<script language="Javascript">
	$(function() {
	 $( "#newsSearchfromDate" ).datepicker();
	 $( "#newsSearchtoDate" ).datepicker();
	 
	 $( "#analysisSearchfromDate" ).datepicker();	 
	 $( "#analysisSearchtoDate" ).datepicker();
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
										<JqGridTable:addTable tableIdentifier="ScripsPage_ScripTable"/>
										TODO: Under development
										<div class="spui-titlebar-title-panel">
											<h1>Scrip Search:</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('ScripScripDivBody')">
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
															<div id="scripSearchFormTable">
																<s:form action="scripSearch.do" method="post">												
																	<table class="formTable" summary="" datatable="0">																	
																		<tbody>																																	
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
																			</tr>
																			<tr class="row" id="AppApplicationInstallPortletname_row">
																				<td><label class="fieldLabel">Watchlist:</label></td>
																				<td class="inputField">
																					<div>
																						<s:select cssClass="textinput" headerKey="" headerValue="Select the Watchlist" name="selectedWatchlist" id="selectedWatchlist" list="userWatchlists" listValue="name" listKey="id" />																					
																					</div>
																				</td>
																				<td><label class="fieldLabel">Sector :</label></td>
																				<td class="inputField">
																					<div>
																						<s:select cssClass="textinput" headerKey="-1" headerValue="Select the Sector" name="sectorId" id="sectorId" list="sectorList" listValue="name" listKey="id" />
																					</div>																	
																				</td>
																				<td><label class="fieldLabel">Avg Volume(2Weeks):</label></td>
																				<td class="inputField">
																					<div><s:textfield name="averageVolume" id="averageVolume" cssClass="textinput"/>																				
																						<input value="Reset" class="formButton" type="reset">&nbsp;&nbsp;
																						<button type="button" onclick="this.form.pageNumber.value=1;submitScreenForm('scripSearch','scripSearchResultTable')" name="Back" class="formButton">
																							Search
																						</button>
																					</div>
																				</td>
																			</tr>
																			<tr class="row" id="AppApplicationInstallPortletname_row">
																				<td><label class="fieldLabel">Todo:</label></td>
																				<td class="inputField"></td>
																				<td><label class="fieldLabel">Todo :</label></td>
																				<td class="inputField"></td>
																				<td><label class="fieldLabel">Avg Turnover per day(2Weeks):</label></td>
																				<td class="inputField">
																					<div><s:textfield name="averageTurnover" id="averageTurnover" cssClass="textinput"/>
																					</div>
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
																	<s:hidden name="newsForm" id="newsForm"/>
																	<s:hidden name="screenerForm" id="screenerForm"/>
																</s:form>
															</div>
															<table class="dialog" style="width: 100%;">
																<tbody>
																	<tr>
																		<td style="text-align: center;">
																			<div id="scripSearchResultTable">
																				
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
			<div class="middle">
				<div class="r">	
					<div class="c">
						<div class="c2">							
							<div class="spui-book-content">
								<div class="spui-titlebar">
									<div class="float-container">
										<div class="spui-titlebar-title-panel">
											<h1>News Search result:</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('ScripNewsDivBody')">
												<img src="<%=request.getContextPath()%>/metroWL/images/minimize.png" class="" title="Minimize" alt="Minimize ">
											</a>
										</div>
									</div>
								</div>
								<div id="AppDeploymentsPages" class="spui-page">
									<div id="ScripNewsDivBody" class="spui-book">
										<div class="spui-book-content">
											<div id="AppDeploymentsControlPage" class="page-content">
												<div id="AppDeploymentsControlPortlet" class="spui-window  ">
													<div class="spui-window-content">
	
														<div class="contenttable">
															<div id="newsSearchFormTable">
																<s:form action="newsSearch.do" method="post">
																	<table class="formTable" summary="" datatable="0">																	
																		<tbody>																																	
																			<tr class="row" id="AppApplicationInstallPortletname_row">
																				<td><label class="fieldLabel">From Date:</label></td>
																				<td class="inputField">
																					<div>
																						<s:textfield name="fromDate" id="newsSearchfromDate" cssClass="textinput"/>
																					</div>
																				</td>
																				<td><label class="fieldLabel">To Date:</label></td>
																				<td class="inputField">
																					<div>
																						<s:textfield name="toDate" id="newsSearchtoDate" cssClass="textinput"/>
																					</div>
																				</td>
																				<td><label class="fieldLabel">Feed Source :</label></td>
																				<td class="inputField">
																					<div>
																						<s:select cssClass="textinput" headerKey="" headerValue="Select the Feed source" name="feedSourceId" id="feedSourceId" list="newsFeedSourceList" listValue="sourceName" listKey="id" />
																					</div>																	
																				</td>
																				<td>
																					<button type="button" onclick="this.form.pageNumber.value=1;submitScreenForm('newsSearch','newsSearchResultTable')" name="Back" class="formButton">
																						Search
																					</button>
																				</td>
																			</tr>																		
																		</tbody>
																	</table>
																	<s:hidden name="scripIds" id="scripIds"/>
																	<s:hidden name="selectedScripId" id="selectedScripId"/>
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
																			<div id="newsSearchResultTable">
																				
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
			<div class="middle">
				<div class="r">
					<div class="c">
						<div class="c2">
							<div class="spui-book-content">
								<div class="spui-titlebar">
									<div class="float-container">
										<div class="spui-titlebar-title-panel">
											<h1>Analysis result:</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('ScripAnalysisDivBody')">
												<img src="<%=request.getContextPath()%>/metroWL/images/minimize.png" class="" title="Minimize" alt="Minimize ">
											</a>
										</div>
									</div>
								</div>
								<div id="AppDeploymentsPages" class="spui-page">
									<div id="ScripAnalysisDivBody" class="spui-book">
										<div class="spui-book-content">
											<div id="AppDeploymentsControlPage" class="page-content">
												<div id="AppDeploymentsControlPortlet" class="spui-window  ">
													<div class="spui-window-content">
	
														<div class="contenttable">
															<div id="analysisSearchFormTable">
																<s:form action="analysisSearch.do" method="post">
																	<table class="formTable" summary="" datatable="0">																	
																		<tbody>																																	
																			<tr class="row" id="AppApplicationInstallPortletname_row">
																				<td><label class="fieldLabel">From Date:</label></td>
																				<td class="inputField">
																					<div>
																						<s:textfield name="fromDate" id="analysisSearchfromDate" cssClass="textinput"/>
																					</div>
																				</td>
																				<td><label class="fieldLabel">To Date:</label></td>
																				<td class="inputField">
																					<div>
																						<s:textfield name="toDate" id="analysisSearchtoDate" cssClass="textinput"/>
																					</div>
																				</td>
																				<td class="inputField">
																					<div>
																						<s:select cssClass="textinput" headerKey="" headerValue="All" name="analysisType" id="analysisType" list="#{'1':'Price H/L', '2':'Technical', '3':'Candle'}" />
																					</div>
																				</td>
																				<td>
																					<button type="button" onclick="this.form.pageNumber.value=1;submitScreenForm('analysisSearch','analysisSearchResultTable')" name="Back" class="formButton">
																						Search
																					</button>
																				</td>
																			</tr>																		
																		</tbody>
																	</table>
																	<s:hidden name="scripIds" id="scripIds"/>
																	<s:hidden name="selectedScripId" id="selectedScripId"/>
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
																			<div id="analysisSearchResultTable">
																				
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
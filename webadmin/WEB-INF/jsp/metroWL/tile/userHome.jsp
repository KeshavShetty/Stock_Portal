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
											<h1>User home: Summary of Deployments</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											&nbsp;
										</div>
									</div>
								</div>
								<div id="AppDeploymentsPages" class="spui-page">
									<div id="AppDeploymentsTableBook" class="spui-book">
										<div class="spui-book-content">
											<div id="AppDeploymentsControlPage" class="page-content">
												<div id="AppDeploymentsControlPortlet" class="spui-window  ">
													<div class="spui-window-content">
	
														<div class="contenttable">
															<div class="introText">
																<p>
																	User home: Who thought making great looking financial charts could be so easy? We have the tools, educational information, expert opinions, and support you need to make more money in the market. Just remember that while anyone can use our free tools, only subscribers have access to our most powerful features. Why not sign up for a 10-day FREE trial to see for yourself?
																</p>
															</div>
															<form id="genericTableForm" name="genericTableForm" action="#console.portal" method="POST">
																<div class="tabletitle">
																	Deployments
																</div>
																<div class="tablectrl">
																	<table class="tablebuttonbar" summary>
																		<tr>
																			<td class="tablecontrols"></td><td class="tablenavigation">Todo</td>
																		</tr>
																	</table>
																	<table class="datatable" id="genericTableFormtable" summary="Deployments">
																		<colgroup>
																			<col class="checkboxColumn">
																		</colgroup>
																		<tr>
																			<th scope="col">
																			<input type="checkbox" name="all" class="radioAndCheckbox" onclick="checkAll(this, this.form);" title="Click to select all rows">
																			</th><th scope="col" title="Sort table by Name"><a href="#console.portal?_nfpb=true&amp;_pageLabel=AppDeploymentsControlPage&amp;AppDeploymentsControlPortletsortby=name&amp;AppDeploymentsControlPortletsortdir=1">Name<span class="nobr">&nbsp;<img src="<%=request.getContextPath()%>/metroWL/images/sort_up.gif" alt="Sorted Ascending "></span></a></th><th scope="col" title="Sort table by State"><a href="#console.portal?_nfpb=true&amp;_pageLabel=AppDeploymentsControlPage&amp;AppDeploymentsControlPortletsortby=state">State<span class="nobr">&nbsp;</span></a></th><th scope="col" title="Sort table by Health"><a href="#console.portal?_nfpb=true&amp;_pageLabel=AppDeploymentsControlPage&amp;AppDeploymentsControlPortletsortby=health">Health<span class="nobr">&nbsp;</span></a></th><th scope="col" title="Sort table by Type"><a href="#console.portal?_nfpb=true&amp;_pageLabel=AppDeploymentsControlPage&amp;AppDeploymentsControlPortletsortby=type">Type<span class="nobr">&nbsp;</span></a></th><th scope="col" title="Sort table by Deployment Order"><a href="#console.portal?_nfpb=true&amp;_pageLabel=AppDeploymentsControlPage&amp;AppDeploymentsControlPortletsortby=deploymentOrder">Deployment Order<span class="nobr">&nbsp;</span></a></th>
																		</tr>
																		<tr class="rowEven">
																			<td>
																			<input id="AppDeploymentsControlPortletchosenContents" onclick="unCheck(this, this.form);" type="checkbox" name="AppDeploymentsControlPortletchosenContents" title="Select sira" class="radioAndCheckbox" value="com.bea.console.handles.AppDeploymentHandle%28%22com.bea%3AName%3Dsira%2CType%3DAppDeployment%22%29">
																			</td><td id="name1" width="50%"><img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" alt="" style="height:1px;" width="0" border="0"><a href="#console.portal?_pageLabel=AppDeploymentsControlPage&amp;_nfpb=true&amp;AppDeploymentsControlPortletexpandNode=ROOTCHILDNODE1"><img src="<%=request.getContextPath()%>/metroWL/images/leafclos.gif" border="0" height="16" width="16" alt="sira Expand Node " title="sira Expand Node "></a><img align="middle" src="<%=request.getContextPath()%>/metroWL/images/ear.gif" alt="Enterprise Application "><a href="#console.portal?_nfpb=true&amp;_pageLabel=AppApplicationDispatcherPage&amp;AppApplicationDispatcherPortlethandle=com.bea.console.handles.AppDeploymentHandle%28%22com.bea%3AName%3Dsira%2CType%3DAppDeployment%22%29" title="sira, Level 1, Collapsed, 1 of 1">sira</a></td><td id="state1"><img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" alt="" style="height:1px;" width="0" border="0"><a href="#console.portal?_nfpb=true&amp;_pageLabel=AppDeploymentStatusPage&amp;AppDeploymentStatusPortlethandle=com.bea.console.handles.AppDeploymentHandle%28%22com.bea%3AName%3Dsira%2CType%3DAppDeployment%22%29">Active</a></td><td id="health1"><img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" alt="" style="height:1px;" width="0" border="0"><img src="<%=request.getContextPath()%>/metroWL/images/checkmar.gif" alt="">&nbsp;OK</td><td id="type1"><img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" alt="" style="height:1px;" width="0" border="0">Enterprise Application</td><td id="deploymentOrder1"><img src="<%=request.getContextPath()%>/metroWL/images/spacer.gif" alt="" style="height:1px;" width="0" border="0">100</td>
																		</tr>
																	</table>
																	<table class="tablebuttonbar" summary>
																		<tr>
																			<td class="tablecontrols">
																			<div class="buttonBar">
																				<button type="button" onclick="disableButtons();self.location.href=&quot;#console.portal?_nfpb=true&amp;_pageLabel=AppApplicationInstallPage&quot;;return false;" name="Install" class="formButton">
																					Install
																				</button>
																				&nbsp;
																				<button type="button" onclick="disableButtons();switchPortlet(&quot;AppApplicationUpdatePage&quot;,&quot;AppApplicationUpdatePortlet&quot;,&quot;genericTableForm&quot;);return false;" name="Update" class="formButton">
																					Update
																				</button>
																				&nbsp;
																				<button type="button" onclick="disableButtons();switchPortlet(&quot;AppApplicationUninstallPage&quot;,&quot;AppApplicationUninstallPortlet&quot;,&quot;genericTableForm&quot;);return false;" name="Delete" class="formButton">
																					Delete
																				</button>
																				&nbsp;
	
																				<img src="<%=request.getContextPath()%>/metroWL/images/buttonSe.gif" alt="">
																				<button type="button" onclick="return showMenu(this,event);return false;" name="Start" class="formMenuButton">
																					Start<span>&nbsp;</span>
																				</button>
																				&nbsp;
																				<div class="button-menu-outer">
																					<div class="menudstr"></div>
																					<div class="menudsbl">
																						<div class="menuds">
																							<ul class="button-menu">
																								<li onmouseout="buttonMenuMouseOut(this);" onmouseover="buttonMenuMouseOver(this);" onclick="disableButtons();switchPortlet(&quot;AppControlStartPage&quot;,&quot;AppControlStartPortlet&quot;,&quot;genericTableForm&quot;);return false;">
																									<a href="#console.portal?_nfpb=true&amp;_pageLabel=AppDeploymentsControlPage#">Servicing all requests</a>
																								</li>
																								<li onmouseout="buttonMenuMouseOut(this);" onmouseover="buttonMenuMouseOver(this);" onclick="disableButtons();switchPortlet(&quot;AppControlAdminPage&quot;,&quot;AppControlAdminPortlet&quot;,&quot;genericTableForm&quot;);return false;">
																									<a href="#console.portal?_nfpb=true&amp;_pageLabel=AppDeploymentsControlPage#">Servicing only administration requests</a>
																								</li>
																							</ul>
																						</div>
																					</div>
																				</div>
	
																				<button type="button" onclick="return showMenu(this,event);return false;" name="Stop" class="formMenuButton">
																					Stop<span>&nbsp;</span>
																				</button>
																				&nbsp;
																				<div class="button-menu-outer">
																					<div class="menudstr"></div>
																					<div class="menudsbl">
																						<div class="menuds">
																							<ul class="button-menu">
																								<li onmouseout="buttonMenuMouseOut(this);" onmouseover="buttonMenuMouseOver(this);" onclick="disableButtons();switchPortlet(&quot;AppControlStopPage&quot;,&quot;AppControlStopPortlet&quot;,&quot;genericTableForm&quot;);return false;">
																									<a href="#console.portal?_nfpb=true&amp;_pageLabel=AppDeploymentsControlPage#">When work completes</a>
																								</li>
																								<li onmouseout="buttonMenuMouseOut(this);" onmouseover="buttonMenuMouseOver(this);" onclick="disableButtons();switchPortlet(&quot;AppControlForceStopPage&quot;,&quot;AppControlForceStopPortlet&quot;,&quot;genericTableForm&quot;);return false;">
																									<a href="#console.portal?_nfpb=true&amp;_pageLabel=AppDeploymentsControlPage#">Force Stop Now</a>
																								</li>
																								<li onmouseout="buttonMenuMouseOut(this);" onmouseover="buttonMenuMouseOver(this);" onclick="disableButtons();switchPortlet(&quot;AppControlAdminPage&quot;,&quot;AppControlAdminPortlet&quot;,&quot;genericTableForm&quot;);return false;">
																									<a href="#console.portal?_nfpb=true&amp;_pageLabel=AppDeploymentsControlPage#">Stop, but continue servicing administration requests</a>
																								</li>
																							</ul>
																						</div>
																					</div>
																				</div>
	
																			</div></td><td class="tablenavigation">Showing 1 to 1 of 1&nbsp;&nbsp;&nbsp;Previous<img src="<%=request.getContextPath()%>/metroWL/images/buttonSe.gif" alt="">&nbsp;Next</td>
																		</tr>
																	</table>
																</div>
																<input type="hidden" name="_pageLabel" value="AppDeploymentsControlPage">
																<input type="hidden" name="_nfpb" value="true">
																<input type="hidden" name="AppDeploymentsControlPortletfrsc" id="AppDeploymentsControlPortletfrsc" value="0x0515ea8d8631aa072898773c41616eacc8b26e7a487d840d">
															</form>
	
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
											<h1>Install Application Assistant</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											&nbsp;
										</div>
									</div>
								</div>
								<div id="AppApplicationInstallPage" class="page-assistant">
									<div id="AppApplicationInstallPortlet" class="spui-window  ">
										<div class="spui-window-content">
	
											<div class="contenttable">
												<div class="upperButtonBar">
													<div class="buttonBar">
														<button type="button" onclick="disableButtons();nextAction(&quot;/com/bea/console/actions/app/install/beforeIdentity&quot;);return false;" name="Back" class="formButton">
															Back
														</button>
														&nbsp;
														<button type="button" onclick="disableButtons();nextAction(&quot;/com/bea/console/actions/app/install/saveIdentity&quot;);return false;" name="Next" class="formButton">
															Next
														</button>
														&nbsp; <img src="<%=request.getContextPath()%>/metroWL/images/buttonSe.gif" alt="">
														<button type="button" onclick="disableButtons();nextAction(&quot;/com/bea/console/actions/app/install/finish&quot;);return false;" name="Finish" class="formButton">
															Finish
														</button>
														&nbsp; <img src="<%=request.getContextPath()%>/metroWL/images/buttonSe.gif" alt="">
														<button type="button" onclick="disableButtons();nextAction(&quot;/com/bea/console/actions/app/install/cancel&quot;);return false;" name="Cancel" class="formButton">
															Cancel
														</button>
														&nbsp;
													</div>
												</div>
												<div class="stepTitle">
													Optional Settings
												</div>
												<div class="stepIntro">
													You can modify these settings or accept the defaults
												</div>
												<form name="form" method="post" action="#console.portal" onsubmit="wls.console.doingSubmit();">
													<table class="formTable" summary="" datatable="0">
														<colgroup>
															<col class="labelCol">
															<col class="inputCol">
															<col class="paddingCol">
														</colgroup>
														<tbody>
															<tr class="formSeparator">
																<td colspan="3"><h3>General</h3></td>
															</tr>
															<tr class="rowIntro">
																<td colspan="3"><span class="dialog-info"> What do you want to name this deployment?
																	<br>
																</span></td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletname_row">
																<td><label for="AppApplicationInstallPortletname">Name:</label></td><td class="inputField">
																<div>
																	<input name="AppApplicationInstallPortletname" id="AppApplicationInstallPortletname" maxlength="10240" size="30" value="sira" class="textinput" type="text">
																</div></td><td>&nbsp;</td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletspecVersion_row">
																<td><span class="likeLabel">Specification Version:</span></td><td class="inputFieldRO">
																<div>
																	1.0
																</div></td><td>&nbsp;</td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletimplVersion_row">
																<td><span class="likeLabel">Implementation Version:</span></td><td class="inputFieldRO">
																<div>
																	1.0
																</div></td><td>&nbsp;</td>
															</tr>
															<tr class="formSeparator">
																<td colspan="3"><h3>Security</h3></td>
															</tr>
															<tr class="rowIntro">
																<td colspan="3"><span class="dialog-info"> What security model do you want to use with this application?
																	<br>
																</span></td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletsecurityModel_row">
																<td colspan="2" class="labeledField">
																<input name="AppApplicationInstallPortletsecurityModel" value="DDOnly" checked="" onclick="checkboxChanged(this);" class="radioAndCheckbox" type="radio">
																<label for="">DD Only: Use only roles and policies that are defined in the deployment descriptors.</label></td><td>&nbsp;</td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletsecurityModel_row">
																<td colspan="2" class="labeledField">
																<input name="AppApplicationInstallPortletsecurityModel" value="CustomRoles" onclick="checkboxChanged(this);" class="radioAndCheckbox" type="radio">
																<label for="">Custom Roles: Use roles that are defined in the Administration Console; use policies that are defined in the deployment descriptor.</label></td><td>&nbsp;</td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletsecurityModel_row">
																<td colspan="2" class="labeledField">
																<input name="AppApplicationInstallPortletsecurityModel" value="CustomRolesAndPolicies" onclick="checkboxChanged(this);" class="radioAndCheckbox" type="radio">
																<label for="">Custom Roles and Policies: Use only roles and policies that are defined in the Administration Console.</label></td><td>&nbsp;</td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletsecurityModel_row">
																<td colspan="2" class="labeledField">
																<input name="AppApplicationInstallPortletsecurityModel" value="Advanced" onclick="checkboxChanged(this);" class="radioAndCheckbox" type="radio">
																<label for="">Advanced: Use a custom model that you have configured on the realm's configuration page.</label></td><td>&nbsp;</td>
															</tr>
															<tr class="formSeparator">
																<td colspan="3"><h3>Source accessibility</h3></td>
															</tr>
															<tr class="rowIntro">
																<td colspan="3"><span class="dialog-info"> How should the source files be made accessible?
																	<br>
																</span></td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletstagingStyle_row">
																<td colspan="2" class="labeledField">
																<input name="AppApplicationInstallPortletstagingStyle" value="Default" checked="" onclick="checkboxChanged(this);" class="radioAndCheckbox" type="radio">
																<label for="">Use the defaults defined by the deployment's targets</label></td><td>&nbsp;</td>
															</tr>
															<tr class="rowIntro">
																<td colspan="3"><span class="dialog-info"> Recommended selection.
																	<br>
																</span></td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletstagingStyle_row">
																<td colspan="2" class="labeledField">
																<input name="AppApplicationInstallPortletstagingStyle" value="Stage" onclick="checkboxChanged(this);" class="radioAndCheckbox" type="radio">
																<label for="">Copy this application onto every target for me</label></td><td>&nbsp;</td>
															</tr>
															<tr class="rowIntro">
																<td colspan="3"><span class="dialog-info"> During deployment, the files will be copied automatically to the managed servers to which the application is targeted.
																	<br>
																</span></td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletstagingStyle_row">
																<td colspan="2" class="labeledField">
																<input name="AppApplicationInstallPortletstagingStyle" value="NoStage" onclick="checkboxChanged(this);" class="radioAndCheckbox" type="radio">
																<label for="">I will make the deployment accessible from the following location</label></td><td>&nbsp;</td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletnoStageSourcePath_row">
																<td><label for="AppApplicationInstallPortletnoStageSourcePath">Location:</label></td><td class="inputField">
																<div>
																	<input name="AppApplicationInstallPortletnoStageSourcePath" id="AppApplicationInstallPortletnoStageSourcePath" maxlength="10240" cols="80" size="50" value="D:\projects\Office\gsc-sira\dist\sira.ear" class="textinput" type="text">
																</div></td><td>&nbsp;</td>
															</tr>
															<tr class="rowIntro">
																<td colspan="3"><span class="dialog-info"> Provide the location from where all targets will access this application's files. This is often a shared directory. You must ensure the application files exist in this location and that each target can reach the location.
																	<br>
																</span></td>
															</tr>
														</tbody>
													</table>
													<div>
														<input name="AppApplicationInstallPortletfrsc" id="AppApplicationInstallPortletfrsc" value="0x0515ea8d8631aa072898773c41616eacc8b26e7a487d840d" type="hidden">
													</div>
												</form>
	
												<div class="lowerButtonBar">
													<div class="buttonBar">
														<button type="button" onclick="disableButtons();nextAction(&quot;/com/bea/console/actions/app/install/beforeIdentity&quot;);return false;" name="Back" class="formButton">
															Back
														</button>
														&nbsp;
														<button type="button" onclick="disableButtons();nextAction(&quot;/com/bea/console/actions/app/install/saveIdentity&quot;);return false;" name="Next" class="formButton">
															Next
														</button>
														&nbsp; <img src="<%=request.getContextPath()%>/metroWL/images/buttonSe.gif" alt="">
														<button type="button" onclick="disableButtons();nextAction(&quot;/com/bea/console/actions/app/install/finish&quot;);return false;" name="Finish" class="formButton">
															Finish
														</button>
														&nbsp; <img src="<%=request.getContextPath()%>/metroWL/images/buttonSe.gif" alt="">
														<button type="button" onclick="disableButtons();nextAction(&quot;/com/bea/console/actions/app/install/cancel&quot;);return false;" name="Cancel" class="formButton">
																Cancel
															</button>
															&nbsp;
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
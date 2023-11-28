<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>

<div id="portal-content-area" class="none">	
	<div class="spui-book-content">
		<div id="AppDeploymentsBook" class="spui-frame">
			<div class="middle">
				<div class="r">
					<div class="c">
						<div class="c2">
							<div class="spui-book-content">
								<div class="spui-titlebar">
									<div class="float-container">										
										<div class="spui-titlebar-title-panel">
											<h1>Scrips History</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('scripHistorySearchFormTable')">
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
															<div id="scripHistorySearchFormTable">
																<JqGridTable:addTable tableIdentifier="ScripHistory_ScripTable" formRequired="true"/> 
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="spui-book-content">
								<div class="spui-titlebar">
									<div class="float-container">										
										<div class="spui-titlebar-title-panel">
											<h1>Bulk deal</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('scripHistorySearchFormTable')">
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
															<div id="scripHistorySearchFormTable">
																<JqGridTable:addTable tableIdentifier="MiscPage_BulkdealTable" formRequired="true"/>
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
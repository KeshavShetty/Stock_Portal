<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>

<script language="Javascript">


function placeAjaxOrder(indexName, optionType) {
    $.ajax({
        // Our sample url to make request 
        url:'<%=request.getContextPath()%>/optionOrderPlacer.do?indexName='+indexName+'&optiontype='+optionType,

        // Type of Request
        type: "GET",

        // Function to call when to
        // request is ok 
        success: function (data) {
            alert('success');
        },
        // Error handling 
        error: function (error) {
            alert('failed');
        }
    });
}

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
											<h1>Index View</h1>
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
															<table cellspacing="1" cellpadding="1" border="1">
																<tr valign="top">
																	<td style="padding-left:5px; line-height: 50px; overflow: hidden">
																		<a href="javascript:placeAjaxOrder('NIFTY 50', 'CALL');">NIFTY Bullish(CALL)</a>
																	</td>
																	<td style="padding-left:5px; line-height: 50px; overflow: hidden">
																		<a href="javascript:placeAjaxOrder('NIFTY 50', 'PUT');">NIFTY Bearish(PUT)</a>
																	</td>															        
																</tr>
																<tr valign="top">
																	<td style="padding-left:5px; line-height: 50px; overflow: hidden">
																		<a href="javascript:placeAjaxOrder('NIFTY BANK', 'CALL');">BANK NIFTY Bullish(CALL)</a>
																	</td>
																	<td style="padding-left:5px; line-height: 50px; overflow: hidden">
																		<a href="javascript:placeAjaxOrder('NIFTY BANK', 'PUT');">BANK NIFTY Bearish(PUT)</a>
																	</td>															        
																</tr>
															</table>
														</div>
													</div>
												</div>
											</div>
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
																<JqGridTable:addTable tableIdentifier="Option_OpenInterestTable" formRequired="true"/> 
															</div>
														</div>
													</div>
												</div>
											</div>
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
															<div id="Option_AlgoOrdersTable">
																<JqGridTable:addTable tableIdentifier="Option_AlgoOrdersTable" formRequired="true"/> 
															</div>
														</div>
													</div>
												</div>
											</div>
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
															<div id="Individual_Option_AlgoOrdersTable">
																<JqGridTable:addTable tableIdentifier="Individual_Option_AlgoOrdersTable" formRequired="true"/> 
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
			
			<br></br>
			
			<div class="middle">
				<div class="r">
					<div class="c">
						<div class="c2">
							<div class="spui-book-content">
								<div class="spui-titlebar">
									<div class="float-container">										
										<div class="spui-titlebar-title-panel">
											<h1>Options Daily Summary of AlgoOrders</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('Summary_Option_AlgoOrdersTable')">
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
															<div id="Summary_Option_AlgoOrdersTable">
																<JqGridTable:addTable tableIdentifier="Summary_Option_AlgoOrdersTable" formRequired="true"/> 
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
			
			<br></br>
			
			<div class="middle">
				<div class="r">
					<div class="c">
						<div class="c2">
							<div class="spui-book-content">
								<div class="spui-titlebar">
									<div class="float-container">										
										<div class="spui-titlebar-title-panel">
											<h1>Options Monthly ROI of AlgoOrders</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('Option_Monthly_AlgoOrdersTable')">
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
															<div id="Option_Monthly_AlgoOrdersTable">
																<JqGridTable:addTable tableIdentifier="Option_Monthly_AlgoOrdersTable" formRequired="true"/> 
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
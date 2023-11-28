<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="JqGridTable" uri="/WEB-INF/tlds/JqGridTableBuilder" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/highstock.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/highcharts-more.js" charset="utf-8"></script>
		
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
											<h1>NSE Option Chain Page</h1>
										</div>
										<div class="spui-titlebar-button-panel">
											<a href="javascript:void()" onclick="javascript:toggleDiv('JqGridBasedNSEOptionPageAnalysisFormTable')">
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
															<div id="JqGridBasedNSEOptionPageAnalysisFormTable">
																<JqGridTable:addTable tableIdentifier="NSE_OptionsPage_Table" formRequired="true"/>
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
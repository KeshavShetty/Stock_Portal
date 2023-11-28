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
						<div class="c2">
							<div class="spui-book-content">
								<div id="AppDeploymentsPages" class="spui-page">
									<div id="AppDeploymentsTableBook" class="spui-book">
										<div class="spui-book-content">
											<div id="AppDeploymentsControlPage" class="page-content">
												<div id="AppDeploymentsControlPortlet" class="spui-window  ">
													<div class="spui-window-content">
	
														<div class="contenttable">	
														
														
														<applet align=baseline
																	codebase="http://<%=request.getServerName()%>:<%=request.getServerPort()%>/portal/"
																	code=org.stock.chart.applet.CloudChart.class
																	height=500		
																	name=CloudChart
																	id=CloudChart
																	width=100%		
															  		archive="JFreeChart.jar"
															  		style="border: 1px solid rgb(191, 198, 201);">
															  	<PARAM name="java_arguments" value="-Xmx256m">
																<param name="host" value="http://<%=request.getServerName()%>:<%=request.getServerPort()%>/portal/">  
															  	<param name="hashkey" value="MD5HashKey">	
																<param name="print.hide" value="no">
																<param name="mail.hide" value="no">
																<param name="feedback.hide" value="no">
																<param name="Applet.version" value="1.0">
																<param name="watermark" value="images/partners/portal.gif">  
																<param name="codebase" value="<%=request.getContextPath()%>/">
																<param name="username" value="kks">
																<param name="symbol" value="<s:property value="scripCode"/>">
																<param name="signup" value="Eq,Fu,Op">
																<param name="ApplicationClass" value="org.stock.chart.applet.CloudChart.class">
																<param name="banner" value="yes">
																<param name="height" value="500">
																<param name="initialPreloadMode" value="Yes">
																<param name="width" value="100%">	
																<param name="coHost" value="Adityon">
																<param name="signupEq" value="yes">
																<param name="signupFu" value="yes">
																<param name="signupOp" value="yes">
																<param name="scope" value="yes">
																<param name="locale" value="en">	
																<param name="tdc.hide" value="no">
																<param name="recognia" value="yes">
																<param name="wizard.hide" value="no">
																<param name="toplist.hide" value="no">
																<param name="profile.hide" value="no">
																<param name="volume.hide" value="no">
																<param name="search.hide" value="no">
																<param name="liveonly" value="no">
																<param name="detachlet.mode" value="noclose">
																<param name="detachlet.hide" value="false">
																<param name="logEnabled" value="true">
																<param name="logLevel" value="5">
															</applet>
															
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
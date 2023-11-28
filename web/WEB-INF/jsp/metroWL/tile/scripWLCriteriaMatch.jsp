<%@ taglib prefix="s" uri="/struts-tags" %>

<script language="Javascript">
	$(document).ready( function(){
		submitScreenForm('findWlCriteriaMatch','wlCriteriaMatch_leftContainer');
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
											<h1>Scrip to Watchlist Criteria Match</h1>
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
															<table cellspacing="0" cellpadding="0" border="0" width="100%">															    
															    <tr valign="top">
															        <td style="width:150px; background:#bbddff" colspan="2">
															        	<s:form action="findWlCriteriaMatch.do" method="post">
																            <div class="inputtitle">
																                <b>Ticker Symbol: </b>
																                <s:textfield name="tickerSymbol" id="VATickerSymbol" cssClass="textinput"/>&nbsp;&nbsp;
																                <b>Watchlist: </b>
																               	<s:select cssClass="textinput" headerKey="" headerValue="Select the Watchlist" name="selectedWatchlist" id="selectedWatchlist" list="userVirtualWatchlist" listValue="name" listKey="id" />																			
																               	<input id="VAButton" name="Button1" type="button" class="formButton" value="Calculate" onclick="submitScreenForm('findWlCriteriaMatch','wlCriteriaMatch_leftContainer')"">
																            </div>
															            </s:form>
															        </td>
															    </tr>
															    <tr>
															    	<td colspan="2">
															    		<div id="wlCriteriaMatch_leftContainer" style="width: 100%"></div>
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
<%@ taglib prefix="s" uri="/struts-tags" %>

<script language="Javascript">
	$(function() {
		$( "#VAtrendStartDate" ).datepicker();
		$( "#VAtrendEndDate" ).datepicker();
	});	
</script>

<script language="Javascript">
	$(document).ready( function(){
		submitScreenForm('calculateVolumeAnalysis','volumeAnalysis_leftContainer')
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
											<h1>Volume Analysis</h1>
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
															        	<s:form action="calculateVolumeAnalysis.do" method="post">
																            <div class="inputtitle">
																                <b>Ticker Symbol: </b>
																                <s:textfield name="tickerSymbol" id="VATickerSymbol" cssClass="textinput"/>&nbsp;&nbsp;
																                <b>Trend Start Date</b>
																                <s:textfield name="trendStartDate" id="VAtrendStartDate" cssClass="textinput"/>&nbsp;&nbsp;
																                <b>Next Date:</b>
																                <s:textfield name="trendEndDate" id="VAtrendEndDate" cssClass="textinput"/>&nbsp;&nbsp;																                
																                <input id="VAButton" name="Button1" type="button" class="formButton" value="Calculate" onclick="submitScreenForm('calculateVolumeAnalysis','volumeAnalysis_leftContainer')"">
																            </div>
															            </s:form>
															        </td>
															    </tr>
															    <tr>
															    	<td colspan="2">
															    		<div id="volumeAnalysis_leftContainer" style="width: 100%"></div>
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
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
								<div class="spui-titlebar">
									<div class="float-container">
										<div class="spui-titlebar-title-panel">
											<h1>Scrip/Symbol search :- Please enter the search criteria like BSE or NSE code, Scrip name or industry.</h1>
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
																<td>&nbsp;</td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletname_row">
																<td><label class="fieldLabel">Status :</label></td>
																<td class="inputField">
																	<div>
																		<s:select cssClass="textinput" headerKey="" headerValue="Select the Status" name="scripStatus" id="scripStatus" list="#{'Active':'Active', 'InActive':'InActive'}" />
																	</div>																	
																</td>
																<td><label class="fieldLabel">Todo :</label></td>
																<td class="inputField">
																	<div>Todo</div>
																</td>
																<td>&nbsp;</td>
															</tr>
															<tr class="row" id="AppApplicationInstallPortletname_row">
																<td><label class="fieldLabel">Sector :</label></td>
																<td class="inputField">
																	<div>
																		<s:select cssClass="textinput" headerKey="-1" headerValue="Select the Sector" name="sectorId" id="sectorId" list="sectorList" listValue="name" listKey="id" />
																	</div>																	
																</td>
																<td><label class="fieldLabel">NSE Code :</label></td>
																<td class="inputField">
																	<div><s:textfield name="nseCode" id="nseCode" cssClass="textinput"/></div>
																</td>
																<td>
																	<button type="button" onclick="this.form.pageNumber.value=1;submitMainScreenForm('scripSearch','scripSearchResultTable')" name="Back" class="formButton">
																		Search
																	</button>
																</td>
															</tr>
															
															
														</tbody>
													</table>
													<div>
														<input name="AppApplicationInstallPortletfrsc" id="AppApplicationInstallPortletfrsc" value="0x0515ea8d8631aa072898773c41616eacc8b26e7a487d840d" type="hidden">
													</div>
													<div id="scripSearchResultTable">
														
													</div>
													<s:hidden name="orderBy" id="orderBy"/>
													<s:hidden name="orderType" id="orderType"/>
													<s:hidden name="pageNumber" id="pageNumber"/>
													<s:hidden name="recordsPerPage" id="recordsPerPage"/>
												</s:form>										
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<head>
		<meta http-equiv="Content-Script-Type" content="text/javascript">
		<title>Page layoutBest financial charts for Indian stock market</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/metroWL/css/styles.css">		
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/jquery.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/global.js"></script>
	</head>
	<body>
		<tiles:insertAttribute name="topHeader" />

		<div id="Home" class="spui-book">
			<div class="spui-book-content">
				<div id="page" class="spui-page">
					<div class="spui-2col-layout">
						<div id="portal-content-col">
							<div id="portal-content-col-inner">
								<div id="ToolbarBook" class="none">
									<div class="spui-book-content">
										<div id="ToolbarPage" class="spui-page">
											<div id="portlet_toolbar" class="spui-window  ">
												<div class="spui-window-content">
													<tiles:insertAttribute name="mainMenu" />
												</div>
											</div>
										</div>
									</div>
								</div>

								<tiles:insertAttribute name="messages" />

								<tiles:insertAttribute name="body" />
							</div>
						</div>
						<tiles:insertAttribute name="rightAdPane" />
					</div>
				</div>
			</div>
		</div>
		<div class="spui-footer">
			<tiles:insertAttribute name="footer" />
		</div>
		<div id="fb-service-dialog_c" style="display: none;">
			<div id="fb-service-dialog">
				<div id="fb-service-dialog_h">
					Title: Best financial charts for Indian stock market 
				</div>
				<div id="xxforms-dialog-body">
					Loading
				</div>
				<a href="javascript:void(0)" id="container-close">Close</a>
			</div>
		</div>
	</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<head>
		<meta http-equiv="Content-Script-Type" content="text/javascript">
		<title>Best financial charts for Indian stock market</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/metroWL/css/styles.css">	
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/metroWL/css/ecstyles.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/metroWL/other/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/metroWL/css/dynamic.image.popup.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/metroWL/jqGrid/themes/cupertino/jquery-ui.css" media="screen" />
		
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/metroWL/colorbox/colorbox.css" media="screen" />
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/other/jquery-1.10.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/jqGrid/themes/cupertino/jquery-ui.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/moment.js"></script>
				
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/page-flow.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/global.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/navga.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/dynamic.image.popup.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/exporting.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/highstock.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/highcharts-more.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/draggable-legend.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/js/jqTableSet.js"></script>
		
		<script type="text/ecmascript" src="<%=request.getContextPath()%>/metroWL/jqGrid/js/ui.multiselect.js"></script>
		<script type="text/ecmascript" src="<%=request.getContextPath()%>/metroWL/jqGrid/js/trirand/jquery.jqGrid.min.js"></script>		
    	<script type="text/ecmascript" src="<%=request.getContextPath()%>/metroWL/jqGrid/js/trirand/i18n/grid.locale-en.js"></script>
    	
    	<script type="text/javascript" src="<%=request.getContextPath()%>/metroWL/colorbox/js/jquery.colorbox.js"></script>
    	
    	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/metroWL/jqGrid/css/ui.multiselect.css" />
    	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/metroWL/jqGrid/css/trirand/ui.jqgrid.css" />			
	</head>
	<body>
		<div style="display: none; position: absolute; z-index: 110; left: 400; top: 100; width: 15; height: 15" id="preview_div"></div>
		<!-- <tiles:insertAttribute name="topHeader" /> -->
		<div id="Home" class="spui-book">
			<div class="spui-book-content">
				<div id="page" class="spui-page">
					<div class="spui-2col-layout">
						<div id="portal-content-col">
							<div id="portal-content-col-inner">
								<div id="ToolbarBook" class="none" style="position:absolute; z-index:1; width:100%;">
									<div class="spui-book-content">
										<div id="ToolbarPage" class="spui-page">
											<div id="portlet_toolbar" class="spui-window  ">
												
													<tiles:insertAttribute name="mainMenu" />
												
											</div>
										</div>
									</div>
								</div>
								<div id="PublicHomePageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="UserHomePageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="DashboardPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="ChartPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="QuickChartPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="ScreenerPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="ForumPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="SchoolPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="MiscPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="NewsPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="AchieversPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="CandlePatternPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="CandlePatternScripPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="HihgstockChartPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="IntradaySnapshotPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="HighchartsEODPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="IntradayBTSTPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="IndexViewPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="TreePerformancePageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="FinancialResultPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="WatchlistDiffViewPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>
								<div id="ScripsHistoryPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>	
								<div id="BubbleGraphPageDiv" class="body-dynamic-page-div"><img src="<%=request.getContextPath()%>/metroWL/images/load.gif"/></div>							
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
		<div id="shadowing" style="display: none; top:90px"></div>
		<div id="fb-service-dialog_c" style="display: none; top:92px;  width: -moz-available;">
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
		<%
			String pageToDisplay="PublicHomePage";
			if (request.getParameter("pageToDisplay")!=null || request.getAttribute("pageToDisplay")!=null) {
				pageToDisplay = (request.getParameter("pageToDisplay") != null ? request.getParameter("pageToDisplay") : (String)request.getAttribute("pageToDisplay"));
			}			
		%>
		<script language="Javascript">
			setContextPathForJS('<%=request.getContextPath()%>')
			switchToPage('<%=pageToDisplay%>');
		</script>
		<script>
			$(document).ready(function(){
				//Examples of how to assign the Colorbox event to elements
				$(".scripIframe").colorbox({rel:'scripGroup'});
			});
			
			$(document).ready(function(){
				$(document).bind('cbox_open', function() {
				    $('html').css({ overflow: 'hidden' });
				}).bind('cbox_closed', function() {
				    $('html').css({ overflow: '' });
				});
			});
			
		</script>
	</body>
</html>
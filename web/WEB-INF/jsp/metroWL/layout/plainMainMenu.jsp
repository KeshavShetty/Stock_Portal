<%@page import="java.util.Date"%>
<%@page import="org.stock.portal.web.util.Constants"%>
<%@page import="org.stock.portal.domain.User"%>
<div class="spui-window-content">
	<div class="toolbar">
		<div class="toolbar-menu">
			<div class="tbframe">
				<div class="tbframeTop">
					<div>
						<div>
							&nbsp;
						</div>
					</div>
				</div>
	
				<div id="topMenu" class="tbframeContent">
					<a href="javascript:void(0)" onclick="javascript:switchToPage('PublicHomePage');">
						<div class="metroIcon" id="PublicHomeMenuId">
							<img src="<%=request.getContextPath()%>/metroWL/images/world.png">
							<h2>&nbsp;&nbsp;Market</h2>
						</div>
					</a>
					<% if (session.getAttribute(Constants.LOGGED_IN_USER)!=null) { %>
						<a href="javascript:void(0)" onclick="javascript:switchToPage('UserHomePage');">
							<div class="metroIcon" id="UserHomeMenuId">
								<img src="<%=request.getContextPath()%>/metroWL/images/home.png">
								<h2>&nbsp;MyHome</h2>
							</div>
						</a>
					<% } %>
					<a href="javascript:void(0)" onclick="javascript:switchToPage('DashboardPage');">
						<div class="metroIcon" id="DashboardMenuId">
							<img src="<%=request.getContextPath()%>/metroWL/images/simple.png">
							<h2>&nbsp;&nbsp;&nbsp;&nbsp;Dashboard</h2>
						</div>
					</a>
					<a href="javascript:void(0)" onclick="javascript:switchToPage('NewsPage');"> 
						<div class="metroIcon" id="NewsMenuId">
							<img src="<%=request.getContextPath()%>/metroWL/images/rss_feeds.png">
							<h2>&nbsp;&nbsp;Options</h2>
						</div>
					</a>				
					<a href="javascript:void(0)">
						<div class="metroIcon" id="SchoolMenuId">
							<img src="<%=request.getContextPath()%>/metroWL/images/school.png">
							<h2>&nbsp;&nbsp;&nbsp;School</h2>
						</div> 
					</a>	
					<a href="javascript:void(0)" onclick="javascript:switchToPage('AnalysisPage');">
						<div class="metroIcon" id="AnalysisMenuId">
							<img src="<%=request.getContextPath()%>/metroWL/images/indicator_filter.png">
							<h2>&nbsp;&nbsp;Screener</h2>
						</div>
					</a>
					<a href="javascript:void(0)" onclick="javascript:switchToPage('AchieversPage');">
						<div class="metroIcon" id="AchieversMenuId">
							<img src="<%=request.getContextPath()%>/metroWL/images/forum.png">
							<h2>&nbsp;&nbsp;Achievers</h2> 
						</div>
					</a>				
					<a href="javascript:void(0)" onclick="javascript:switchToPage('IntradaySnapshotPage');">
						<div class="metroIcon" id="IntradaySnapshotMenuId">
							<img src="<%=request.getContextPath()%>/metroWL/images/forum.png">
							<h2>&nbsp;&nbsp;iSnapshot</h2>
						</div>
					</a>
					<a href="javascript:void(0)" onclick="javascript:switchToPage('IntradayBTSTPage');">
						<div class="metroIcon" id="IntradayBTSTMenuId">
							<img src="<%=request.getContextPath()%>/metroWL/images/forum.png">
							<h2>&nbsp;&nbsp;BTST</h2>
						</div>
					</a>
					<a href="javascript:void(0)" onclick="javascript:switchToPage('IndexViewPage');">
						<div class="metroIcon" id="IndexViewMenuId">
							<img src="<%=request.getContextPath()%>/metroWL/images/forum.png">
							<h2>Search</h2>
						</div>
					</a>
					<% if (session.getAttribute(Constants.LOGGED_IN_USER)!=null) { %>
						<a href="javascript:void(0)" onclick="javascript:switchToPage('WatchlistDiffPage');">
							<div class="metroIcon" id="WatchlistDiffViewMenuId">
								<img src="<%=request.getContextPath()%>/metroWL/images/forum.png">
								<h2>WLDiff</h2>
							</div>
						</a>
					<% } %>
					<a href="javascript:void(0)" onclick="javascript:switchToPage('ScripsHistoryPage');">
						<div class="metroIcon" id="ScripsHistoryMenuId">
							<img src="<%=request.getContextPath()%>/metroWL/images/simple.png">
							<h2>&nbsp;&nbsp;&nbsp;&nbsp;ScripsHistory</h2>
						</div>
					</a>
					<a href="javascript:void(0)" onclick="javascript:switchToPage('BubbleGraphPage');">
						<div class="metroIcon" id="BubbleGraphMenuId">
							<img src="<%=request.getContextPath()%>/metroWL/images/forum.png">
							<h2>&nbsp;&nbsp;BubbleGraph</h2>
						</div>
					</a>
				</div>
	
				<div class="tbframeBottom">
					<div>
						<div>
							&nbsp;
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<div class="toolbar-info">
			<div class="tbframe">
				<div class="tbframeTop">
					<div>
						<div>
							&nbsp;
						</div>
					</div>
				</div>
	
				<div class="tbframeContent">
					<div id="welcome">
						<p>
							<% if (request.getSession(false)!=null && request.getSession(false).getAttribute(Constants.LOGGED_IN_USER)!=null) { 
								User loggedUser = (User) request.getSession(false).getAttribute(Constants.LOGGED_IN_USER); %>
								Welcome <strong><%=loggedUser.getUserName()%></strong>
								<a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/data/editProfile.do')"><strong>(Edit profile)</strong></a>&nbsp;
								&nbsp;<a href="<%=request.getContextPath()%>/logout.do"><strong>(Logout)</strong></a>
							<% } else { %>
								Welcome to YellowCharts<br/>Already have an Id? <a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/prepareLogin.do')"><strong>Login</strong></a>
								<br/>New user? Get a free account <a href="javascript:void(0)" onclick="javascript:showFloatingPopup('<%=request.getContextPath()%>/prepareSignup.do')"><strong>Signup</strong></a>
							<% } %>
							
						</p>
					</div>
					<div id="domain">
						<p style="text-align: center;">
							(<%=(new Date()) %>)
						</p>
					</div>
				</div>
	
				<div class="tbframeBottom">
					<div>
						<div>
							&nbsp;
						</div>
					</div>
				</div>
			</div>
		</div>
	
	</div>
</div>
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
						<div class="metroIcon">
							<img src="<%=request.getContextPath()%>/metroWL/images/world.png">
							<h2>&nbsp;&nbsp;&nbsp;&nbsp;World</h2>
						</div>
					</a>
					<% if (session.getAttribute(Constants.LOGGED_IN_USER)!=null) { %>
						<a href="javascript:void(0)" onclick="javascript:switchToPage('UserHomePage');">
							<div class="metroIcon">
								<img src="<%=request.getContextPath()%>/metroWL/images/home.png">
								<h2>&nbsp;&nbsp;MyHome</h2>
							</div>
						</a>
					<% } %>
					<a href="javascript:void(0)" onclick="javascript:switchToPage('ScripPage');">
						<div class="metroIcon">
							<img src="<%=request.getContextPath()%>/metroWL/images/simple.png">
							<h2>&nbsp;&nbsp;&nbsp;&nbsp;Scrip</h2>
						</div>
					</a>
					<a href="javascript:void(0)" onclick="javascript:switchToPage('ChartPage');">
						<div class="metroIcon">
							<img src="<%=request.getContextPath()%>/metroWL/images/chart.png">
							<h2>&nbsp;&nbsp;JavaChart</h2>
						</div>
					</a>
					<a href="javascript:void(0)" onclick="javascript:switchToPage('QuikChartPage');">
						<div class="metroIcon">
							<img src="<%=request.getContextPath()%>/metroWL/images/chart.png">
							<h2>&nbsp;&nbsp;QuickChart</h2>
						</div>
					</a>
					<div class="metroIcon">
						<img src="<%=request.getContextPath()%>/metroWL/images/simple.png">
						<h2>&nbsp;&nbsp;Screener</h2>
					</div>
					<div class="metroIcon">
						<img src="<%=request.getContextPath()%>/metroWL/images/forum.png">
						<h2>&nbsp;&nbsp;&nbsp;&nbsp;Forum</h2>
					</div>
					<a href="javascript:void(0)">
						<div class="metroIcon">
							<img src="<%=request.getContextPath()%>/metroWL/images/school.png">
							<h2>&nbsp;&nbsp;&nbsp;School</h2>
						</div> 
					</a>
					<a href="javascript:void(0)" onclick="javascript:showMiscPage('/miscDataPage.do');">
						<div class="metroIcon">
							<img src="<%=request.getContextPath()%>/metroWL/images/school.png">
							<h2>&nbsp;Data</h2>
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
							(Time: 12:36:12 IST)
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
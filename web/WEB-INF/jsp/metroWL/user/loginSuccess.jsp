<script type="text/javascript">
	resetAllPageLoadStatus();
	switchToPage('DashboardPage');
	populateBodyDivThruAjax('/LoadMainMenu.do', 'portlet_toolbar', true);
	setTimeout(function(){
		hideFloatingPopup();
	},1000);	
</script>
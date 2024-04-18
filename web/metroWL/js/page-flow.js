// For each main menu item define a variable to check weather page is loaded or not.
var Is_PublicHomePageLoaded = false;
var Is_UserHomePageLoaded = false;
var Is_DashboardPageLoaded = false;
var Is_ChartPageLoaded = false;
var Is_QuickChartPageLoaded = false; 
var Is_ScreenerPageLoaded = false;
var Is_ForumPageLoaded = false;
var Is_SchoolPageLoaded = false;
var Is_NewsPageLoaded = false;
var Is_AchieversPageLoaded = false;
var Is_BubbleGraphPageLoaded = false;
var Is_CandlePatternPageLoaded = false;
var Is_CandlePatternScripPageLoaded = false;
var Is_HihgstockChartPageLoaded = false;
var Is_IntradaySnapshotPageLoaded = false;
var Is_HighchartsEODPageLoaded = false;
var Is_IntradayBTSTPageLoaded = false;
var Is_IndexViewPageLoaded = false; 
var Is_WatchlistDiffViewPageLoaded = false;
var Is_ScripsHistoryPageLoaded = false;


var contextPath = "/portal";

function setContextPathForJS(passedContextPath) {
	contextPath = passedContextPath;
}

function resetAllPageLoadStatus() {
	Is_PublicHomePageLoaded = false;
	Is_UserHomePageLoaded = false;
	Is_DashboardPageLoaded = false;
	Is_ChartPageLoaded = false;
	Is_QuickChartPageLoaded = false;
	Is_ScreenerPageLoaded = false;
	Is_ForumPageLoaded = false;
	Is_SchoolPageLoaded = false;
	Is_NewsPageLoaded = false;
	Is_AchieversPageLoaded = false;
	Is_BubbleGraphPageLoaded = false;
	
	Is_CandlePatternPageLoaded = false;
	Is_CandlePatternScripPageLoaded = false;
	Is_HihgstockChartPageLoaded = false;
	Is_IntradaySnapshotPageLoaded = false;
	Is_HighchartsEODPageLoaded = false;	
	Is_IntradayBTSTPagePageLoaded = false;
	Is_IndexViewPageLoaded = false; 
	Is_WatchlistDiffViewPageLoaded = false;
	Is_ScripsHistoryPageLoaded = false;
}

function populateBodyDivThruAjax(urlToLoad, divToFill, isAsyncRequest) {	
	$.ajax({
        url: contextPath + urlToLoad,
        type: "POST",
        dataType: "html",
        async: isAsyncRequest,
        success: function(data) {
			$('#'+divToFill).html(data);
		},
        error: function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    })
}

// Hide all pages - By setting display=none for all page div tags
function hideAllpage() {
	$('#PublicHomePageDiv').css('display','none');
	$('#UserHomePageDiv').css('display','none');
	$('#DashboardPageDiv').css('display','none');
	$('#ChartPageDiv').css('display','none');
	$('#QuickChartPageDiv').css('display','none');
	$('#ScreenerPageDiv').css('display','none');
	$('#ForumPageDiv').css('display','none');
	$('#SchoolPageDiv').css('display','none');
	$('#MiscPageDiv').css('display','none');
	$('#NewsPageDiv').css('display','none');
	$('#AchieversPageDiv').css('display','none');
	$('#BubbleGraphPageDiv').css('display','none');
	
	$('#CandlePatternPageDiv').css('display','none');
	$('#CandlePatternScripPageDiv').css('display','none');
	$('#HihgstockChartPageDiv').css('display','none');
	$('#IntradaySnapshotPageDiv').css('display','none');
	$('#HighchartsEODPageDiv').css('display','none');
	$('#IntradayBTSTPageDiv').css('display','none');
	$('#IndexViewPageDiv').css('display','none');
	$('#TreePerformancePageDiv').css('display','none');
	$('#FinancialResultPageDiv').css('display','none');
	$('#WatchlistDiffViewPageDiv').css('display','none');
	$('#ScripsHistoryPageDiv').css('display','none');
	
	$('#PublicHomeMenuId').attr("class", "metroIcon");
	$('#UserHomeMenuId').attr("class", "metroIcon");
	$('#DashboardMenuId').attr("class", "metroIcon");
	$('#AnalysisMenuId').attr("class", "metroIcon");
	$('#NewsMenuId').attr("class", "metroIcon");
	$('#AchieversMenuId').attr("class", "metroIcon");
	$('#BubbleGraphMenuId').attr("class", "metroIcon");
	
	$('#CandlePatternScripMenuId').attr("class", "metroIcon");
	$('#SchoolMenuId').attr("class", "metroIcon");
	$('#IntradaySnapshotMenuId').attr("class", "metroIcon");
	$('#IntradayBTSTMenuId').attr("class", "metroIcon");
	$('#IndexViewMenuId').attr("class", "metroIcon");
	$('#WatchlistDiffViewMenuId').attr("class", "metroIcon");
	$('#ScripsHistoryMenuId').attr("class", "metroIcon");
}
// For each there will be three methods. 
// One to load (If not loaded already), second method to activate the page. Activate means starting thread/timer related to specific page. 
// Third to de-Activate the page, means to stop threads/timers.

function switchToPage(pageToDisplay) {
	if (pageToDisplay=='PublicHomePage') loadPublicHomePage();
	else if (pageToDisplay=='UserHomePage') loadUserHomePage();
	else if (pageToDisplay=='ChartPage') loadChartPage();
	else if (pageToDisplay=='QuikChartPage') loadQuickChartPage();
	else if (pageToDisplay=='DashboardPage') loadDashboardPage();
	else if (pageToDisplay=='AnalysisPage') loadAnalysisPage();
	else if (pageToDisplay=='NewsPage') loadNewsPage();
	else if (pageToDisplay=='AchieversPage') loadAchieversPage();
	else if (pageToDisplay=='CandlePatternPage') loadCandlePatternPage();
	else if (pageToDisplay=='CandlePatternScripPage') loadCandlePatternScripPage();
	else if (pageToDisplay=='HihgstockChartPage') loadHihgstockChartPage();
	else if (pageToDisplay=='IntradaySnapshotPage') loadIntradaySnapshotPage();
	else if (pageToDisplay=='HighchartsEODPage') loadHighchartsEODPage();
	else if (pageToDisplay=='IntradayBTSTPage') loadIntradayBTSTPage();
	else if (pageToDisplay=='IndexViewPage') loadIndexViewPage();
	else if (pageToDisplay=='TreePerformancePage') loadTreePerformancePage();
	else if (pageToDisplay=='FinancialResultsPage') loadFinancialResultsPage();
	else if (pageToDisplay=='WatchlistDiffPage') loadWatchlistDiffPage();
	else if (pageToDisplay=='ScripsHistoryPage') loadScripsHistoryPage();
	else if (pageToDisplay=='BubbleGraphPage') loadBubbleGraphPage();
	scrollToTop();
}

function scrollToTop() {
	var body = $("html, body");
	body.animate({scrollTop:0}, '500', 'swing', function() { 
	   
	});
}

function showMiscPage(urlToUse) {
	hideAllpage();
	$('#MiscPageDiv').css('display','block');
	populateBodyDivThruAjax(urlToUse, 'MiscPageDiv', true);;
}

function loadPublicHomePage() {
	hideAllpage();
	$('#PublicHomeMenuId').attr("class", "metroIconSelected");
	$('#PublicHomePageDiv').css('display','block');
	if (Is_PublicHomePageLoaded==false) {
		populateBodyDivThruAjax('/PublicHome.do', 'PublicHomePageDiv', true);
		// Need to start timer/thread etc		
		Is_PublicHomePageLoaded = true;
	} else {
		//Resume timer/threads
	}
}

function activatePublicHomePage() {
	hideAllpage();
	$('#PublicHomePageDiv').css('display','block');
	// Todo: Add below to start/restart any timer/thread related to public home page
}

function deActivatePublicHomePage() {
	// Todo: Add below to stop/suspend any timer/thread related to public home page
}

function loadUserHomePage() {
	hideAllpage();
	$('#UserHomeMenuId').attr("class", "metroIconSelected");
	$('#UserHomePageDiv').css('display','block');
	if (Is_UserHomePageLoaded==false) {
		populateBodyDivThruAjax('/UserHome.do', 'UserHomePageDiv', true);
		// Need to start timer/thread etc		
		Is_UserHomePageLoaded = true;
	}
}

function loadChartPage() {
	hideAllpage();
	$('#ChartPageDiv').css('display','block');
	if (Is_ChartPageLoaded==false) {
		populateBodyDivThruAjax('/ShowJavaChart.do', 'ChartPageDiv', true);
		Is_ChartPageLoaded = true;
	}
}

function loadAnalysisPage() { 
	hideAllpage();
	$('#AnalysisMenuId').attr("class", "metroIconSelected");
	$('#ScreenerPageDiv').css('display','block');	
	if (Is_ScreenerPageLoaded==false) {
		populateBodyDivThruAjax('/LoadAnalysisPage.do', 'ScreenerPageDiv', true);
		Is_ScreenerPageLoaded = true;
	}
}

function loadCandlePatternScripPage() { 
	hideAllpage();
	$('#CandlePatternScripMenuId').attr("class", "metroIconSelected");
	$('#CandlePatternScripPageDiv').css('display','block');	
	if (Is_CandlePatternScripPageLoaded==false) {
		populateBodyDivThruAjax('/LoadCandlePatternScripPage.do', 'CandlePatternScripPageDiv', true);
		Is_CandlePatternScripPageLoaded = true;
	}
}

function loadHihgstockChartPage() { 
	hideAllpage();
	$('#HihgstockChartPageDiv').css('display','block');	
	if (Is_HihgstockChartPageLoaded==false) {
		populateBodyDivThruAjax('/LoadHighstockChart.do', 'HihgstockChartPageDiv', true);
		// Is_HihgstockChartPageLoaded = true; // Always reload the page
	}
}
function loadIntradaySnapshotPage() { 
	hideAllpage();
	$('#IntradaySnapshotMenuId').attr("class", "metroIconSelected");
	$('#IntradaySnapshotPageDiv').css('display','block');	
	if (Is_IntradaySnapshotPageLoaded==false) {
		populateBodyDivThruAjax('/intradaySnapshot.do', 'IntradaySnapshotPageDiv', true);
		Is_IntradaySnapshotPageLoaded = true; // Always reload the page
	}
}

function loadHighchartsEODPage() { 
	hideAllpage();
	$('#HighchartsEODPageDiv').css('display','block');	
	if (Is_HighchartsEODPageLoaded==false) {
		populateBodyDivThruAjax('/highchartsEOD.do', 'HighchartsEODPageDiv', true);
		//Is_HighchartsEODPageLoaded = true; // Always reload the page
	}
}

function loadIntradayBTSTPage() { 
	hideAllpage();
	$('#IntradayBTSTMenuId').attr("class", "metroIconSelected");
	$('#IntradayBTSTPageDiv').css('display','block');	
	if (Is_IntradayBTSTPageLoaded==false) {
		populateBodyDivThruAjax('/intradayBTST.do', 'IntradayBTSTPageDiv', true);
		Is_IntradayBTSTPageLoaded = true;
	}
}

function loadTreePerformancePage() { 
	hideAllpage();
	$('#TreePerformancePageDiv').css('display','block');	
	populateBodyDivThruAjax('/treePerformanceView.do', 'TreePerformancePageDiv', true);
}

function loadFinancialResultsPage() { 
	hideAllpage();
	$('#FinancialResultPageDiv').css('display','block');	
	populateBodyDivThruAjax('/financialResultView.do', 'FinancialResultPageDiv', true);
}

function loadIndexViewPage() { 
	hideAllpage();
	$('#IndexViewMenuId').attr("class", "metroIconSelected");
	$('#IndexViewPageDiv').css('display','block');	
	if (Is_IndexViewPageLoaded==false) {
		populateBodyDivThruAjax('/indexView.do', 'IndexViewPageDiv', true);
		Is_IndexViewPageLoaded = true;
	}
}

function loadWatchlistDiffPage() { 
	hideAllpage();
	$('#WatchlistDiffViewMenuId').attr("class", "metroIconSelected");
	$('#WatchlistDiffViewPageDiv').css('display','block');	
	if (Is_WatchlistDiffViewPageLoaded==false) {
		populateBodyDivThruAjax('/watchlistDiffView.do', 'WatchlistDiffViewPageDiv', true);
		Is_WatchlistDiffViewPageLoaded = true;
	}
}

function loadNewsPage() { 
	hideAllpage();
	$('#NewsMenuId').attr("class", "metroIconSelected");
	$('#NewsPageDiv').css('display','block');
	if (Is_NewsPageLoaded==false) {
		populateBodyDivThruAjax('/LoadNewsPage.do', 'NewsPageDiv', true);
		Is_NewsPageLoaded = true;
	}
}

function loadDashboardPage() {
	hideAllpage();
	$('#DashboardMenuId').attr("class", "metroIconSelected");
	$('#DashboardPageDiv').css('display','block');
	if (Is_DashboardPageLoaded==false) {
		populateBodyDivThruAjax('/LoadDashboardPage.do', 'DashboardPageDiv', true);
		Is_DashboardPageLoaded = true;
	}
}

function loadScripsHistoryPage() {
	hideAllpage();
	$('#ScripsHistoryMenuId').attr("class", "metroIconSelected");
	$('#ScripsHistoryPageDiv').css('display','block');
	if (Is_ScripsHistoryPageLoaded==false) {
		populateBodyDivThruAjax('/LoadScripsHistoryPage.do', 'ScripsHistoryPageDiv', true);
		Is_ScripsHistoryPageLoaded = true;
	}
}

function loadQuickChartPage() {
	hideAllpage();
	$('#QuickChartPageDiv').css('display','block');
	if (Is_QuickChartPageLoaded==false) {
		populateBodyDivThruAjax('/LoadQuickChart.do', 'QuickChartPageDiv', true);
		//Is_QuickChartPageLoaded = true;
	}
}

function loadAchieversPage() {
	hideAllpage();
	$('#AchieversMenuId').attr("class", "metroIconSelected");
	$('#AchieversPageDiv').css('display','block');
	if (Is_AchieversPageLoaded==false) {
		populateBodyDivThruAjax('/pastAchievers.do', 'AchieversPageDiv', true); 
		Is_AchieversPageLoaded = true;
	}
}

function loadBubbleGraphPage() {
	hideAllpage();
	$('#BubbleGraphMenuId').attr("class", "metroIconSelected");
	$('#BubbleGraphPageDiv').css('display','block');
	if (Is_BubbleGraphPageLoaded==false) {
		populateBodyDivThruAjax('/bubbleGraph.do', 'BubbleGraphPageDiv', true);
		Is_BubbleGraphPageLoaded = true;
	}
}

function loadCandlePatternPage() {
	hideAllpage();
	$('#CandlePatternPageDiv').css('display','block');
	if (Is_AchieversPageLoaded==false) {
		populateBodyDivThruAjax('/prepareSearchCandlePattern.do', 'CandlePatternPageDiv', true);
		Is_CandlePatternPageLoaded = true;
	}
}

//function openScripPage(nseCode, bseCode) {
//	hideAllpage();
//	$('#ScripMenuId').attr("class", "metroIconSelected");
//	$('#ScripPageDiv').css('display','block');
//	if (Is_ScripPageLoaded) {
//		$('form#scripSearch')[0].reset();
//	} else {		
//		populateBodyDivThruAjax('/LoadScripPage.do', 'ScripPageDiv', false);
//		Is_ScripPageLoaded = true;
//	}
//	if (nseCode != '') {
//		$('form#scripSearch #nseCode').val(nseCode);	
//	} 
//	if (bseCode != '') {
//		$('form#scripSearch #bseCode').val(bseCode);
//	}
//	submitScreenForm('scripSearch','scripSearchResultTable');
//	scriooToTop();
//}

function toggleDiv(divId) { 
	$('#'+divId).toggle();
}

function toggleIndexDiv(indexScripId, divId, forDate, orderBy, orderType) { // This is not working need investigation (Tried for both div and table)
	
	if(!/[\S]/.test($('#'+divId).html())) { // for one element
		var urlToUse = '/getIndexScrips.do?selectedIndexId='+indexScripId+'&dataDate='+forDate+'&orderBy=' + orderBy+'&orderType='+orderType +'&divToFill='+divId;
		populateBodyDivThruAjax(urlToUse, divId, true);		
	} else {
		$('#'+divId).toggle();
	}
}

function toggleTreePerformanceDiv(scripId, divId, orderBy, orderType) { // This is not working need investigation (Tried for both div and table)
	
	if(!/[\S]/.test($('#'+divId).html())) { // for one element
		var urlToUse = '/searchSubTreePerformanceView.do?selectedScripId='+scripId+'&orderBy=' + orderBy+'&orderType='+orderType +'&divToFill='+divId;
		populateBodyDivThruAjax(urlToUse, divId, true);		
	} else {
		$('#'+divId).toggle();
	}
}

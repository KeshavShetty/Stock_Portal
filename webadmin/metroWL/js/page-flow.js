// For each main menu item define a variable to check weather page is loaded or not.
var Is_PublicHomePageLoaded = false;
var Is_UserHomePageLoaded = false;
var Is_ScripPageLoaded = false;
var Is_ChartPageLoaded = false;
var Is_QuickChartPageLoaded = false;
var Is_ScreenerPageLoaded = false;
var Is_ForumPageLoaded = false;
var Is_SchoolPageLoaded = false;
var contextPath = "/portal";

function setContextPathForJS(passedContextPath) {
	contextPath = passedContextPath;
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
	$('#ScripPageDiv').css('display','none');
	$('#ChartPageDiv').css('display','none');
	$('#QuickChartPageDiv').css('display','none');
	$('#ScreenerPageDiv').css('display','none');
	$('#ForumPageDiv').css('display','none');
	$('#SchoolPageDiv').css('display','none');
	$('#MiscPageDiv').css('display','none');
}
// For each there will be three methods. 
// One to load (If not loaded already), second method to activate the page. Activate means starting thread/timer related to specific page. 
// Third to de-Activate the page, means to stop threads/timers.

function switchToPage(pageToDisplay) {
	if (pageToDisplay=='PublicHomePage') loadPublicHomePage();
	else if (pageToDisplay=='UserHomePage') loadUserHomePage();
	else if (pageToDisplay=='ChartPage') loadChartPage();
	else if (pageToDisplay=='QuikChartPage') loadQuickChartPage();
	else if (pageToDisplay=='ScripPage') loadScripPage();
}

function showMiscPage(urlToUse) {
	hideAllpage();
	$('#MiscPageDiv').css('display','block');
	populateBodyDivThruAjax(urlToUse, 'MiscPageDiv', true);;
}

function loadPublicHomePage() {
	hideAllpage();
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

function loadScripPage() {
	hideAllpage();
	$('#ScripPageDiv').css('display','block');
	if (Is_ScripPageLoaded==false) {
		populateBodyDivThruAjax('/LoadScripPage.do', 'ScripPageDiv', true);
		Is_ScripPageLoaded = true;
	}
}

function loadQuickChartPage() {
	hideAllpage();
	$('#QuickChartPageDiv').css('display','block');
	if (Is_QuickChartPageLoaded==false) {
		populateBodyDivThruAjax('/ShowQuickChart.do', 'QuickChartPageDiv', true);
		Is_QuickChartPageLoaded = true;
	}
}

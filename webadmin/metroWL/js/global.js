
var Browser_UserAgent = navigator.userAgent.toLowerCase();
var IsBrowser_Opera = (Browser_UserAgent.indexOf('opera') != -1); 
var IsBrowser_Opera8 = ((Browser_UserAgent.indexOf('opera 8') != -1 || Browser_UserAgent.indexOf('opera/8') != -1) ? 1 : 0); 
var IsBrowser_NS4 = (document.layers) ? true : false; 
var IsBrowser_IE4 = (document.all && !document.getElementById) ? true : false; 
var IsBrowser_IE5 = (document.all && document.getElementById) ? true : false; 
var IsBrowser_NS6 = (!document.all && document.getElementById) ? true : false; 
var IsBrowser_FireFox = (Browser_UserAgent.indexOf("firefox/") != -1); 
var IsBrowser_Transitions = (IsBrowser_IE5 || IsBrowser_IE4) ? true : false 

$(document).ready( function(){
	
	$("#container-close").click( function( evt ){
		hideFloatingPopup();
		$('#xxforms-dialog-body').html("Loading");
	});
	$("#closeFormButton").click( function( evt ){
		hideFloatingPopup();
		$('#xxforms-dialog-body').html("Loading");
	});
	
					
});

function hideDiv(divCtrl) {	
	$('#'+divCtrl).each(function( index ) {
		 $(this).hide();
	});
}

function showFloatingPopup(urlToLoad) {
	var windowLeft = ($(window).width() - 550)/2;
	
	$('#fb-service-dialog_c').css('display','block');
	$('#shadowing').css('display','block');
	$('#fb-service-dialog_c').css('left', windowLeft + 'px');
		
	$('#xxforms-dialog-body').html("Loading");
	$.ajax({
        url: urlToLoad,
        data: "fromWindow=true",
        type: "POST",
        dataType: "html",
        async: true,
        success: function(data) {
		$('#xxforms-dialog-body').html(data);
	},
        error: function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    })
}

function hideFloatingPopup() {
	$('#fb-service-dialog_c').css('display','none');
	$('#shadowing').css('display','none');
}

function showFloatingPopup(urlToLoad, requestParams) {
	var windowLeft = ($(window).width() - 550)/2;
	
	$('#fb-service-dialog_c').css('display','block');
	$('#shadowing').css('display','block');
	$('#fb-service-dialog_c').css('left', windowLeft + 'px');
		
	$('#xxforms-dialog-body').html("Loading");
	$.ajax({
        url: urlToLoad,
        data: "fromWindow=true&"+requestParams,
        type: "POST",
        dataType: "html",
        async: true,
        success: function(data) {
		$('#xxforms-dialog-body').html(data);
	},
        error: function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    })
}

function submitFloatingForm(formName) {
	
	$.ajax({
        url: $('form#'+formName).attr('action'),
        data: $('form#'+formName).serialize(),
        type: "POST",
        dataType: "html",
        async: true,
        success: function(data) {
			$('#xxforms-dialog-body').html(data);
		},
        error: function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    })
}

function submitMainScreenForm(formName, divToFill) {	
	$.ajax({
		url: $('form#'+formName).attr('action'),
		data: $('form#'+formName).serialize(),
		type: "POST",
        dataType: "html",
        async: true,
        success: function(data) {
			$('#'+divToFill).html(data);
		},
        error: function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    })
}

function sortMainPageTable(formToSubmit, sortByField, sortByOrder, pageNumber, recordPerPage, divToFill) {
	//alert("formToSubmit="+formToSubmit+" sortByField="+sortByField+" sortByOrder="+sortByOrder+" pageNumber="+pageNumber+" recordPerPage="+recordPerPage+" divToFill="+divToFill);
	//submitMainScreenForm(formToSubmit, divToFill)
	if (sortByField!='') $('form#'+formToSubmit + " #orderBy").val(sortByField);
	if (sortByOrder!='') $('form#'+formToSubmit + " #orderType").val(sortByOrder);
	if (pageNumber!='') $('form#'+formToSubmit + " #pageNumber").val(pageNumber);
	if (recordPerPage!='') $('form#'+formToSubmit + " #recordsPerPage").val(recordPerPage);
	
	//alert($('form#'+formToSubmit + " #orderBy").val());
	submitMainScreenForm(formToSubmit, divToFill);
}
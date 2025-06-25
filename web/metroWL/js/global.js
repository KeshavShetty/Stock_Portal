
var Browser_UserAgent = navigator.userAgent.toLowerCase();
var IsBrowser_Opera = (Browser_UserAgent.indexOf('opera') != -1); 
var IsBrowser_Opera8 = ((Browser_UserAgent.indexOf('opera 8') != -1 || Browser_UserAgent.indexOf('opera/8') != -1) ? 1 : 0); 
var IsBrowser_NS4 = (document.layers) ? true : false; 
var IsBrowser_IE4 = (document.all && !document.getElementById) ? true : false; 
var IsBrowser_IE5 = (document.all && document.getElementById) ? true : false; 
var IsBrowser_NS6 = (!document.all && document.getElementById) ? true : false; 
var IsBrowser_FireFox = (Browser_UserAgent.indexOf("firefox/") != -1); 
var IsBrowser_Transitions = (IsBrowser_IE5 || IsBrowser_IE4) ? true : false ;

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

function showFloatingPopup(urlToLoad, requestParams, windowWidth, windowHeight) {
	var maxDivWidth = $(window).width() - 100;
	var maxDivHeight = $(window).height() - 100;
	
	var divLeft = ($(window).width() - maxDivWidth)/2;
	var divTop = ($(window).height() - maxDivHeight)/2;
	
	//alert('Screen width='+$(window).width()+" maxDivWidth="+maxDivWidth+" Screen height="+$(window).height()+" maxDivHeight=" + maxDivHeight+" divLeft="+divLeft+" divTop="+divTop);
	
	$('#fb-service-dialog_c').css('width', maxDivWidth + 'px');
	$('#fb-service-dialog_c').css('max-height', maxDivHeight + 'px');
	$('#fb-service-dialog_c').css('left', divLeft + 'px');
	$('#fb-service-dialog_c').css('top', divTop + 'px');
	
	$('#fb-service-dialog_c').css('display','block');
	$('#shadowing').css('display','block');
		
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

function showFloatingColorBox(urlToLoad, requestParams, windowWidth, windowHeight) {
	var finalUrl = urlToLoad+"?"+requestParams; 
	//alert(finalUrl);
	$.colorbox({iframe: true, href: finalUrl , innerWidth: '90%', innerHeight: '90%'});
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
	$('#'+divToFill).html("<img src=\"/portal/metroWL/images/animated_loading.gif\">");
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

function submitScreenForm(formToSubmit, divToFill) {	
	if (formToSubmit!='') $('form#'+formToSubmit + " #sourceFormName").val(formToSubmit);
	if (divToFill!='') $('form#'+formToSubmit + " #divToFill").val(divToFill);
	submitMainScreenForm(formToSubmit, divToFill);
}

function sortMainPageTable(formToSubmit, sortByField, sortByOrder, pageNumber, recordPerPage, divToFill) {
	//alert("formToSubmit="+formToSubmit+" sortByField="+sortByField+" sortByOrder="+sortByOrder+" pageNumber="+pageNumber+" recordPerPage="+recordPerPage+" divToFill="+divToFill);
	//submitMainScreenForm(formToSubmit, divToFill)
	if (sortByField!='') $('form#'+formToSubmit + " #orderBy").val(sortByField);
	if (sortByOrder!='') $('form#'+formToSubmit + " #orderType").val(sortByOrder);
	if (pageNumber!='') $('form#'+formToSubmit + " #pageNumber").val(pageNumber);
	if (recordPerPage!='') $('form#'+formToSubmit + " #recordsPerPage").val(recordPerPage);
	if (formToSubmit!='') $('form#'+formToSubmit + " #sourceFormName").val(formToSubmit);
	if (divToFill!='') $('form#'+formToSubmit + " #divToFill").val(divToFill);
	//alert($('form#'+formToSubmit + " #orderBy").val());
	submitMainScreenForm(formToSubmit, divToFill);
}

function getRequestAndPopulateDiv(urlToLoad, divToFill) {	
	$.ajax({
		url: urlToLoad,		
		type: "GET",
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

function getScripInfoAndPopulateDiv(urlToLoad, id, divToFill) {	
	$.ajax({
		url: urlToLoad,	
		 data: "id="+id,
		type: "GET",
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

function populateDivThruAjax(urlToLoad, dataToPass, divToFill) {	
	$.ajax({
		url: urlToLoad,	
		data: dataToPass,
		type: "GET",
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

function getObject(id)
{
    if (document.getElementById)
        //IE 5.x or NS 6.x or above
        return document.getElementById(id);
    else if (document.all)
        //IE 4.x
        return document.all[id];
    else
        //Netscape 4.x
        return document[id];
}

//Update the chart according to user selection
function updateQuickChart(imgUrl, dataForm, targetImageObjectId)
{
    //
    //we encode the values of all form elements as query parameters
    //
    var elements = getObject(dataForm).elements;
    var url = imgUrl + "?";
    for (var i = 0; i < elements.length; ++i)
    {
        var e = elements[i];
        if (e.type == "checkbox")
            url = url + e.id + "=" + (e.checked ? "1" : "0") + "&";
        else
            url = url + e.id + "=" + escape(e.value) + "&";
    }
    //Now we update the URL of the image to update the chart
    getObject(targetImageObjectId).src = url;
}

function calculateFields(inputField1Id, inputField2Id, operation, outputFieldId) {
	var value1 = document.getElementById(inputField1Id).value;	
	var value2 = document.getElementById(inputField2Id).value;
	if (operation == '+') document.getElementById(outputFieldId).value = value1 + value2;
	else if (operation == '-') document.getElementById(outputFieldId).value = value1 - value2;
	else if (operation == '*') document.getElementById(outputFieldId).value = value1 * value2;
	else if (operation == '/' && value2!=0 ) document.getElementById(outputFieldId).value = Math.floor(value1 / value2);
}

function getFormToJsonFilter(formName) {
	var retJson = '{"groupOp":"AND","rules":[';
	
	var isFirstElementAdded = false; 
	var $inputs = $('#'+formName+' :input');
    $inputs.each(function() {
    	var fieldName = this.name;
    	var fieldValue = $(this).val();
    	if (fieldValue.length>0 && !fieldName.startsWith('preSavedSearch')) {
	    	if (isFirstElementAdded) {
	    		retJson = retJson + ',';
	    	}
	    	retJson = retJson + '{"field":"'; 
			//alert(this.type)
	    	if (fieldName.substring(0,3)=='min') {
	    		retJson = retJson + fieldName.substring(3) + '","op":"ge","data":"' + fieldValue + '"}';    		
	    	} else if (fieldName.substring(0,3)=='max') {	    		
	    		retJson = retJson + fieldName.substring(3) + '","op":"le","data":"' + fieldValue + '"}';
	    	} else if (this.type=='select-one' || this.type=='select-multiple'){
	    		retJson = retJson + fieldName + '","op":"eq","data":"' + fieldValue + '"}';
			} else if (fieldName.startsWith('Unique')) {
				retJson = retJson + fieldName.substring(6) + '","op":"eq","data":"' + fieldValue + '"}';
	    	} else {
	    		retJson = retJson + fieldName + '","op":"bw","data":"' + fieldValue + '"}';
	    	}
	    	isFirstElementAdded = true;
    	}
    });
	
    retJson = retJson + ']}';
    if (isFirstElementAdded==false) retJson="";
	return retJson;
}

function clearForm(formName) {
	var $inputs = $('#'+formName+' :input');
	$inputs.each(function() {
		if (this.type=='select-one' || this.type=='select-multiple'){
			$(this).val("");
		} else {
			$(this).val("");
		}
	});
}

function submitJqGridFilter(jqgridName) {
	$("#"+jqgridName).jqGrid().trigger("reloadGrid");
}

function populateSavedSearch(selectOption, formName, tableIdentifier) {
	var unescapeStr = unescape(selectOption.value);
	
	var rootJsonElement = JSON.parse(unescapeStr);
	var rulesJsonElement = rootJsonElement["rules"];
	//clearForm(formName);
	for(var i=0;i<rulesJsonElement.length;i++) {
		var eachRuleElement = rulesJsonElement[i];
		var fiedName = eachRuleElement["field"];
		var opName = eachRuleElement["op"];
		var dataName = eachRuleElement["data"];
		
		if (opName=='bw') { // Begins with			
			var formFieldName = "#"+tableIdentifier+"_"+fiedName;
			$(formFieldName).val(dataName);
		} else if (opName=='ge') { // >=			
			var formFieldName = "#min"+tableIdentifier+"_"+fiedName;
			$(formFieldName).val(dataName);
		} else if (opName=='le') { // <=			
			var formFieldName = "#max"+tableIdentifier+"_"+fiedName;
			$(formFieldName).val(dataName);
		} else if (opName=='eq') { // <=			
			var formFieldName = "#"+tableIdentifier+"_"+fiedName;
			$(formFieldName).val(dataName);
		}
	}
	submitJqGridFilter('jqGrid_'+tableIdentifier);
}

function jqGridFormChangeDate(formName, fieldName, direction) {
	//alert('In jqGridFormChangeDate formName='+formName+" fieldName="+fieldName+" direction="+direction);
	var minDateFieldName = 'min' + fieldName;
	var maxDateFieldName = 'max' + fieldName;
	//alert('minDateFieldName='+minDateFieldName+ ' maxDateFieldName='+maxDateFieldName);
	
	var curDate = document.getElementById(formName).elements[minDateFieldName].value;
		
	if (curDate=='' || direction == 0) {
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1; //January is 0!
		var yyyy = today.getFullYear();

		if(dd<10) {
		    dd='0'+dd;
		} 

		if(mm<10) {
		    mm='0'+mm;
		}
		curDate = dd+'/'+mm+'/'+yyyy;
	} 
	if (direction == 0) {
		document.getElementById(formName).elements[minDateFieldName].value = curDate;
		document.getElementById(formName).elements[maxDateFieldName].value = curDate;
	} else if (direction == -1) {
		var result = moment(curDate, "DD/MM/YYYY");		
		result.add('days', -1);	
		var newdd = result.toDate().getDate();
		var newmm = result.toDate().getMonth()+1; //January is 0!
		var newyyyy = result.toDate().getFullYear();

		if(newdd<10) {
			newdd='0'+newdd;
		}
		if(newmm<10) {
			newmm='0'+newmm;
		}
		document.getElementById(formName).elements[minDateFieldName].value = newdd+'/'+newmm+'/'+newyyyy;
		document.getElementById(formName).elements[maxDateFieldName].value = newdd+'/'+newmm+'/'+newyyyy;
	} else { // +1
		var result = moment(curDate, "DD/MM/YYYY");
		result.add('days', 1);
		var newdd = result.toDate().getDate();
		var newmm = result.toDate().getMonth()+1; //January is 0!
		var newyyyy = result.toDate().getFullYear();

		if(newdd<10) {
			newdd='0'+newdd;
		} 

		if(newmm<10) {
			newmm='0'+newmm;
		}
		document.getElementById(formName).elements[minDateFieldName].value = newdd+'/'+newmm+'/'+newyyyy;
		document.getElementById(formName).elements[maxDateFieldName].value = newdd+'/'+newmm+'/'+newyyyy;
	}	
}
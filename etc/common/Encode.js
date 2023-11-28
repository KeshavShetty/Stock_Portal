function encryptNowWay(data, encKey) {
    var ret_data = Base64.encode(AESEncryptCtr(data, encKey,"256"));
    ret_data = escape(ret_data);
    return ret_data;
}

function encryptNowWayForPost(data, encKey) {
    var ret_data = Base64.encode(AESEncryptCtr(data, encKey,"256"));
    return ret_data;
}

function Convert(data)
{
//	alert("Hello!");
	var appStr = '';	
	//alert('data: '+data);
	
	//converting string to binary data
	for(var i=0; i<data.length; i++)
	{
		var temp1 = data.charCodeAt(i);	
			//alert(temp1);
		var temp = getBinaryString(temp1);		
		while(temp.length < 8)
		{			
			temp = '0' + temp;		
		}
//		alert('string '+temp);
		appStr += temp;
	}
	//alert(appStr+' '+appStr.length);
	
//	Document.write(appStr);
		
	var strlen = parseInt(appStr.length/6);
	if(appStr.length % 6 != 0)
	{		
		strlen++;
//		alert('in '+strlen);
		var temp9 = strlen*6 - appStr.length;
//		alert('temp9 '+temp9);
		while(parseInt(temp9) > 0)
		{
			appStr += '0';
			temp9--;
		}
//		alert('appstr '+appStr+' '+appStr.length);
			
	}
//	alert('after loop strlen '+strlen);
	var arrStr = new Array(strlen);
			
	for(var ii=0; ii<strlen; ii++)
    {		
			//alert('hello');
			arrStr[ii] = '' + appStr.slice(0,6);
			//alert(arrStr[ii]);
			appStr = appStr.slice(6,appStr.length);	
	}
	
	

	var newString = '';
	
    for(var i=0; i<(arrStr.length); i++)
    {
//		alert('init '+arrStr[i]);
        var temp2 = toInt(arrStr[i]);
//		alert('after '+temp2);
		temp2 = base64(temp2);
//		alert(temp2);
		newString += temp2;
    }            
	//alert('new string '+newString);
	return newString ;
}

function toInt(str)
{
	var i=0;
	var tempp=0;
	for(i = 0 ; i < str.length; i++)
	{
		tempp = tempp*10;
		if(str.charAt(i) == '1')	tempp++;
	}
	return tempp;
}


function getBinaryString(data)
{
	var temp = data;
	var temp1 = '';
	while(parseInt(temp)>0)
	{		
		temp1 = parseInt(temp%2) + temp1;		
		temp = parseInt(temp / 2);
//		alert(temp);
	}
//	alert(temp);
	return temp1;
}

function base64(strSix)
	{                    
    var rt;         
    switch(strSix)
    {
            
        case 0: rt= 'A';
			break;
        case 1: rt= 'B';
            break;
        case 10: rt= 'C';
            break;
        case 11: rt= 'D';
            break;
        case 100: rt= 'E';
            break;
        case 101: rt= 'F';
            break;
        case 110: rt= 'G';
            break;
        case 111: rt= 'H';
            break;
        case 1000: rt= 'I';
            break;
        case 1001: rt= 'J';
            break;
        case 1010: rt= 'K';
            break;
        case 1011: rt= 'L';
            break;
        case 1100: rt= 'M';
            break;
        case 1101: rt= 'N';
            break;
        case 1110: rt= 'O';
            break;
        case 1111: rt= 'P';
            break;
        case 10000: rt= 'Q';
            break;
        case 10001: rt= 'R';
            break;
        case 10010: rt= 'S';
            break;
        case 10011: rt= 'T';
            break;
        case 10100: rt= 'U';
            break;
        case 10101: rt= 'V';
            break;
        case 10110: rt= 'W';
            break;
        case 10111: rt= 'X';
            break;
        case 11000: rt= 'Y';
            break;
        case 11001: rt= 'Z';
            break;
        case 11010: rt= 'a';
            break;
        case 11011: rt= 'b';
            break;
        case 11100: rt= 'c';
            break;
        case 11101: rt= 'd';
            break;
        case 11110: rt= 'e';
            break;
        case 11111: rt= 'f';
            break;
        case 100000: rt= 'g';
            break;
        case 100001: rt= 'h';
            break;
        case 100010: rt= 'i';
            break;
        case 100011: rt= 'j';
            break;
        case 100100: rt= 'k';
            break;
        case 100101: rt= 'l';
            break;
        case 100110: rt= 'm';
            break;
        case 100111: rt= 'n';
            break;
        case 101000: rt= 'o';
            break;
        case 101001: rt= 'p';
            break;
        case 101010: rt= 'q';
            break;
        case 101011: rt= 'r';
            break;
        case 101100: rt= 's';
            break;
        case 101101: rt= 't';
            break;
        case 101110: rt= 'u';
            break;
        case 101111: rt= 'v';
            break;
        case 110000: rt= 'w';
            break;
        case 110001: rt= 'x';
            break;
        case 110010: rt= 'y';
            break;
        case 110011: rt= 'z';
            break;
        case 110100: rt= '0';
            break;
        case 110101: rt= '1';
            break;
        case 110110: rt= '2';
            break;
        case 110111: rt= '3';
            break;
        case 111000: rt= '4';
            break;
        case 111001: rt= '5';
            break;
        case 111010: rt= '6';
            break;
        case 111011: rt= '7';
            break;
        case 111100: rt= '8';
            break;
        case 111101: rt= '9';
            break;
        case 111110: rt= '+';
            break;
        case 111111: rt= '/';
            break;
        } 
    return rt;
    }
	
	
	
	// Password Generator
var numChars = '0123456789'; 
var lowerChars = 'abcdefghijklmnopqrstuvwxyz'; 
var upperChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'; 
var otherChars = '~!@#$%^&*()-_=+[{]}\\|<>/?'; 
function randOrd(a, b){return (Math.round(Math.random())-0.5);} 
function getrandomBytes(form,len1) {
var charSet = ''; 
var chars = []; 
var i = 0; var fnc = true; var flc = true; var fuc = true; var foc = true; var ffnc = true; var fflc = true; var ffuc = true; var len = len1; 


 if (fnc) 
 {
	chars[i++] = numChars.charAt(Math.floor(Math.random()*10)); 
	charSet += numChars;
 } 
 if (flc) 
 {
	chars[i++] = lowerChars.charAt(Math.floor(Math.random()*26)); 
	charSet += lowerChars;
 } 
 if (fuc) 
 {
	chars[i++] = upperChars.charAt(Math.floor(Math.random()*26)); 
	charSet += upperChars;
 } 
 if (foc) 
 {
	chars[i++] = otherChars.charAt(Math.floor(Math.random()*25)); 
	charSet += otherChars;
 } 
 var y = charSet.length; 
 while (i < len-1) 
 {
	chars[i++] = charSet.charAt(Math.floor(Math.random()*y));
 } 
	chars.sort(randOrd); charSet = ''; 
	if (ffnc) 
	charSet += numChars; 
	if (fflc) 
	charSet += lowerChars; 
	if (ffuc) 
	charSet += upperChars; 
	y = charSet.length; 
	chars.unshift(charSet.charAt(Math.floor(Math.random()*y))); 
	form.simple.value = chars.join('');
	var simple_simple = chars.join('');
	return simple_simple;
}


function getrandomBytes2(form,len1) {
var charSet = ''; 
var chars = []; 
var i = 0; var fnc = true; var flc = true; var fuc = true; var foc = false; var ffnc = true; var fflc = true; var ffuc = true; var len = len1; 


 if (fnc) 
 {
	chars[i++] = numChars.charAt(Math.floor(Math.random()*10)); 
	charSet += numChars;
 } 
 if (flc) 
 {
	chars[i++] = lowerChars.charAt(Math.floor(Math.random()*26)); 
	charSet += lowerChars;
 } 
 if (fuc) 
 {
	chars[i++] = upperChars.charAt(Math.floor(Math.random()*26)); 
	charSet += upperChars;
 } 
 if (foc) 
 {
	chars[i++] = otherChars.charAt(Math.floor(Math.random()*25)); 
	charSet += otherChars;
 } 
 var y = charSet.length; 
 while (i < len-1) 
 {
	chars[i++] = charSet.charAt(Math.floor(Math.random()*y));
 } 
	chars.sort(randOrd); charSet = ''; 
	if (ffnc) 
	charSet += numChars; 
	if (fflc) 
	charSet += lowerChars; 
	if (ffuc) 
	charSet += upperChars; 
	y = charSet.length; 
	chars.unshift(charSet.charAt(Math.floor(Math.random()*y))); 
	form.simple.value = chars.join('');
	var simple_simple = chars.join('');
	return simple_simple;
}
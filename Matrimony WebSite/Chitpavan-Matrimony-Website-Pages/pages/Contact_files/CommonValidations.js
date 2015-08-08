function FieldsList()
{
	this.instanceOf= function()
	{
		var self=new Array();
		self.Add = function (obj)
		{
			if(obj)
			{
				this[this.length]=obj;
			}
		}
		return self;
	}
}

function CheckAlphanumeric(alphane)
{
	var numaric = alphane;
	for(var j=0; j<numaric.length; j++)
		{
		  var alphaa = numaric.charAt(j);
		  var hh = alphaa.charCodeAt(0);
		  if((hh > 47 && hh<58) || (hh > 64 && hh<91) || (hh > 96 && hh<123)|| hh==46 || hh==95)
		  {
		  }
		else	{
			 return false;
		  }
		}
 return true;
}

function Field(FieldName,DefaultValue,FieldType,ValidationType,Message,EmptyMessage,OptionalArgs,IsOptional)
{

	this.FieldName=FieldName;
	this.FieldType=FieldType;
	this.ValidationType=ValidationType;
	this.OptionalArgs=OptionalArgs;
	this.Message=Message;
	this.ErrorDiv="Err"+FieldName;
	this.IsOptional=IsOptional;
	if(this.IsOptional==null)
		this.IsOptional=false;
	if(EmptyMessage==null)
	{
		this.EmptyMessage=Message;
	}	
	else
	{
		this.EmptyMessage=EmptyMessage;
	}

	this.DefaultValue=DefaultValue;

	if(DefaultValue==null)
	{
		switch(ValidationType)
		{
			case "string":
			this.DefaultValue="";
			break;
			case "alphanumeric":
			this.DefaultValue="";
			break;
			case "numeric":
			this.DefaultValue=0;
			break;
			case "range":
			this.DefaultValue=0;
			break;
		}
	}
	
}

function Range(MinVal,MaxVal)
{
	this.Type="Range";
	this.MinVal=MinVal;
	this.MaxVal=MaxVal;
}

function Comparer(FieldName)
{
	this.Type="Comparer";
	this.FieldName=FieldName;
}

function ConditionalCheck(FieldName,FieldType,CompareValue)
{
	this.Type="ConditionalCheck";
	this.FieldName=FieldName;
	this.FieldType=FieldType;
	this.CompareValue=CompareValue;
}

function DisplayError(Target,Message)
{	
	if(!document.getElementById(Target) && Message!="")
	alert(Message);
	
	if(document.getElementById(Target))
	{
		MVJS.SetValue(Target,Message);
	}

}

function ValidateForm(FormName,ObjFields)
{
	var Excluded;
	var Valid=true;

	for(x=0;x<ObjFields.length;x++)
	{
		if(document.getElementById(ObjFields[x].ErrorDiv).value)
			MVJS.SetValue(ObjFields[x].ErrorDiv,'');
	}

	for(x=0;x<ObjFields.length;x++)
	{

		var Value="";

		Value=MVJS.GetValue(ObjFields[x].FieldName);
		Value=Value.replace(/<Item>/gi,"");
		Value=Value.replace(/<\/Item>/gi,"");
		if(Value=="null") Value="";
		
		if(ObjFields[x].OptionalArgs!=null && ObjFields[x].OptionalArgs.Type=="ConditionalCheck")
		{
			var ConditionValue="";			
			ConditionValue=MVJS.GetValue(ObjFields[x].OptionalArgs.FieldName);
			ConditionValue=ConditionValue.replace(/<Item>/gi,"");
			ConditionValue=ConditionValue.replace(/<\/Item>/gi,"");
			if(ObjFields[x].OptionalArgs.CompareValue==ConditionValue)
			{
				ObjFields[x].IsOptional=false;
			}
			else
			{
				ObjFields[x].IsOptional=true;
			}
		}

		if(ObjFields[x].IsOptional==false && ObjFields[x].FieldType!="radio" && Value==ObjFields[x].DefaultValue)
		{
			
			DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].EmptyMessage);
			MVJS.Focus(ObjFields[x].FieldName);
			return false;
		}
		
		switch(ObjFields[x].ValidationType)
		{
			
			case "string":
			if(Value!="" && isInteger(Value))
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}
			break;
			case "alphanumeric":
			if(Value=="")
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}			
			break;
			case "checkalphanumeric":
			if(Value=="")
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}
			else if(!CheckAlphanumeric(Value))
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}
			break;
			case "compare":
			if(Value!="" && document.getElementById(ObjFields[x].OptionalArgs.FieldName).value!=Value)
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}
			break;
			case "checked":
			if(!IsRadioSelected(ObjFields[x].FieldName))
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);				
				return false;
			}
			break;
			case "date":
			if(Value!="" && !IsDate(Value))
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}
			break;
			case "email":
			if(Value!="" && !IsValidEmail(Value))
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}
			break;
			case "multiemail":
			var InvalidEmails = IsValidEmails(Value);
			if(Value!="" && !(InvalidEmails == ""))
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message + InvalidEmails);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}
			break;
			case "numeric":
			if(Value!="" && !isInteger(Value)) //parseInt(Value)
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}
			break;
			case "float":
			if(Value!="" && !isFloat(Value))
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}
			break;
			case "range":
			if(Value!="" && !IsBetweenRange(parseInt(Value),ObjFields[x].OptionalArgs.MinVal,ObjFields[x].OptionalArgs.MaxVal) || Value!="" && !isInteger(Value))
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}
			break;
			case "length":
			if(Value!="" && !IsLengthValid(Value,ObjFields[x].OptionalArgs.MinVal,ObjFields[x].OptionalArgs.MaxVal))
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;
			}
			break;
			case "phone":
			if(Value!="" && !IsValidPhone(Value))
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;	
			}
			break;
			case "mobile":
			if(Value!="" && !IsValidMobile(Value))
			{
				DisplayError(ObjFields[x].ErrorDiv,ObjFields[x].Message);
				MVJS.Focus(ObjFields[x].FieldName);
				return false;	
			}
			break;
		}
		DisplayError(ObjFields[x].ErrorDiv,"");
	}
	return true;
}

function IsValidPhone(Value)
{
	var objRegExp  =/(^[+]{1}[0-9]{2,4}\s[0-9]{2,6}\s[0-9]{3,10}$)/i;
	return objRegExp.test(Value);
}

function IsValidMobile(Value)
{
	var objRegExp  =/(^[+]{1}[0-9]{2,4}\s[0-9]{5,10}$)/i;
	return objRegExp.test(Value);
}

function GetRadioValue(RadioGroupName) 
{
	var RadioGroup = document.getElementsByName(RadioGroupName);
	for(i=0;i<=RadioGroup.length-1;i++)
	{
		if(RadioGroup[i].checked)
		{
			return RadioGroup[i].value;
		}
	}
	return false;
}

function IsProvided(FieldName)
{
	if(document.getElementById(FieldName).value == "")
	{
		return false;
	}
	else
	{
		return true;
	}
}


function IsValidEmail(strValue)
{
	var objRegExp  =/(^[a-z0-9]([a-z0-9_\-\.]*)@([a-z0-9_\-\.]*)([.][a-z]{3,4})$)|(^[a-z0-9]([a-z0-9_\.]*)@([a-z0-9_\-\.]*)(\.[a-z]{2,3})(\.[a-z]{2})*$)/i;
	return objRegExp.test(strValue);
}


function IsBetweenRange(Value,RangeStart,RangeEnd)
{
	if(Value>RangeStart)
	{
		
		if(Value>RangeEnd)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	else
	{
		return false;
	}
}

function IsLengthValid(Value,RangeStart,RangeEnd)
{

	if(Value.length>RangeStart)
	{
		if(Value.length>RangeEnd)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	else
	{
		return false;
	}
}

function IsRadioSelected(RadioGroupName) 
{
	var RadioGroup = document.getElementsByName(RadioGroupName);
	for(i=0;i<=RadioGroup.length-1;i++)
	{
		if(RadioGroup[i].checked)
			return true;
	}
	return false;
}

function IsItemSelected(OptionName,DefaultValue)
{
	var temp = document.getElementById(OptionName).value;
	if(temp.options[temp.selectedIndex].value == DefaultValue)
	{
		return false;
	}
	else
	{
		return true;
	}
}

function AreEquel(FieldName_1,FieldName_2)
{
	if(document.getElementById(FieldName_1).value != document.getElementById(FieldName_2).value)
	{
		return false;
	}
	else
	{
		return true;
	}
}

var dtCh= "-";
var minYear=1900;
var maxYear=2100;

function isInteger(s)
{
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}
function isFloat(s)
{
	var objRegExp  =/^((\d+(\.\d*)?)|((\d*\.)?\d+))$/;
	return objRegExp.test(s);
}
function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function IsDate(dateStr)
{
	var datePat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
	var matchArray = dateStr.match(datePat); // is the format ok?

	if (matchArray == null)
	{
		//alert("Please enter date as either dd/mm/yyyy or dd-mm-yyyy.");
		return false;
	}

	month = matchArray[1]; // p@rse date into variables
	day = matchArray[3];
	year = matchArray[5];

//alert(day + ':' + month + ':' + year);
	if (month < 1 || month > 12)
	{ // check month range
		//alert("Month must be between 1 and 12.");
		return false;
	}

	if (day < 1 || day > 31)
	{
		//alert("Day must be between 1 and 31.");
		return false;
	}

	if ((month==4 || month==6 || month==9 || month==11) && day==31)
	{
		//alert("Month "+month+" doesn`t have 31 days!")
		return false;
	}

	if (month == 2)
	{
		// check for february 29th
		var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		if (day > 29 || (day==29 && !isleap))
		{
			//alert("February " + year + " doesn`t have " + day + " days!");
			return false;
		}
	}
	return true; // date is valid
}

function IsValidEmails(Values)
{
	var InvaildMailStr = "";		
	var Mailstr = MVJS.GetValue(SourceControl);
	var Mails = Mailstr.split('\n');
	for(x = 0; x < Mails.length; x++)
	{
		Mails[x]=MVJS.Trim(Mails[x]);
		if(Mails[x].length>0)
		{
			if(!IsValidEmail(Mails[x]))
			{				
				InvaildMailStr += Mails[x]+",";
			}
		}			
	}		
	if(InvaildMailStr.length > 0)
	{
		InvaildMailStr = InvaildMailStr.substr(0,InvaildMailStr.length-1);
		return InvalidMailStr;
	}
	return "";
}


//-2 => Invalid Second Date Format
//-1 => Invalid First Date Format
//0 => Dates are Equal
//1 => First Date is Greater than Second
//2 => Second Date is Greater than First

function CompareDates(StartDate,EndDate)
{

	//Date Format[ dd-MM-yyyy ]

	var datePat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
	var matchArray_Start = StartDate.match(datePat);
	var matchArray_End = EndDate.match(datePat);

	if (matchArray_Start == null)
	{
		return -1;
	}
	if (matchArray_End == null)
	{
		return -2;
	}

	StartMonth = parseInt(matchArray_Start[1],10);
	StartDay = parseInt(matchArray_Start[3],10);
	StartYear = parseInt(matchArray_Start[5],10);
	EndMonth = parseInt(matchArray_End[1],10);
	EndDay = parseInt(matchArray_End[3],10);
	EndYear = parseInt(matchArray_End[5],10);

//alert(StartMonth + ':' + StartDay + ':' + StartYear);
//alert(EndMonth + ':' + EndDay + ':' + EndYear);
	if((StartYear == EndYear) && (StartMonth == EndMonth) && (StartDay == EndDay))
		return 0;

	var ObjStartDate = new Date(StartYear,StartMonth,StartDay);
	var ObjEndDate = new Date(EndYear,EndMonth,EndDay);

	if(ObjStartDate > ObjEndDate)
		return 1;
	else
		return 2;
}
function HoursBetweenDates(StartDate,EndDate)
{
	//Date Format[ dd-MM-yyyy ]
	var datePat = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
	var matchArray_Start = StartDate.match(datePat);
	var matchArray_End = EndDate.match(datePat);

	if (matchArray_Start == null)
	{
		return -1;
	}
	if (matchArray_End == null)
	{
		return -2;
	}
	StartMonth = parseInt(matchArray_Start[1],10);
	StartDay = parseInt(matchArray_Start[3],10);
	StartYear = parseInt(matchArray_Start[5],10);
	EndMonth = parseInt(matchArray_End[1],10);
	EndDay = parseInt(matchArray_End[3],10);
	EndYear = parseInt(matchArray_End[5],10);

//alert(StartMonth + '-' + StartDay + '-' + StartYear);
//alert(EndMonth + '-' + EndDay + '-' + EndYear);
	var ObjFirstDate = new Date(StartYear, StartMonth, StartDay);
	var ObjSecondDate = new Date(EndYear, EndMonth, EndDay);
	var msPerDay = 24 * 60 * 60 * 1000
	var DiffDays = Math.floor((ObjSecondDate-ObjFirstDate)/ msPerDay) + 1;
//alert(DiffDays*24);
	return DiffDays*24;
	
}
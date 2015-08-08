if(typeof(MVJS)=="undefined")
	MVJS={};
MVJS.CookieHandler={
	Set:function(name,value,days) {
		var expires = "";
		if (days) {
			var date = new Date();
			date.setTime(date.getTime()+(days*24*60*60*1000));
			expires = "; expires="+date.toGMTString();
		}
		
		document.cookie = name+"="+value+expires+"; path=/";
	},
	Get:function(name) {
		var nameEQ = name + "=";
		var ca = document.cookie.split(';');
		for(var i=0;i < ca.length;i++) {
			var c = ca[i];
			while (c.charAt(0)==' ') c = c.substring(1,c.length);
			if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length,c.length);
		}
		return null;
	},
	Delete:function(name) {
		this.Set(name,-1);
	},
	IsSupported:function() {
		return true;
	}
};


if(typeof(MVJS)=="undefined")
	MVJS={};
MVJS.DateHandler={
	LongMonths :['January','February','March','April',	'May','June','July','August','September','October','November','December'],
	ShortMonths:['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
	LongDays:['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],
	ShortDays:['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],	
	GetLongMonths:function(){ return this.LongMonths;},
	GetShortMonths:function(){ return this.ShortMonths;},
	GetLongDays:function(){ return this.LongDays;},
	GetShortDays:function(){ return this.ShortDays;},
	Initialize:function(LongMonths,ShortMonths,LongDays,ShortDays)
	{
		if(LongMonths!=null) this.LongMonths=LongMonths;
		if(ShortMonths!=null) this.ShortMonths=ShortMonths;
		if(LongDays!=null) this.LongDays=LongDays;
		if(ShortDays!=null) this.ShortDays=ShortDays;
	},
	GetTimeArray : function (Delay)
	{		
		if(!Delay) Delay =30;
		var t = 60/Delay;
		var Today = new Date();	
		var TimeArray = new Array();
		Today.setHours(0);		
		Today.setSeconds(0);
		for (var k=0;k<t * 24;k++)
		{
			var t1= parseInt(k*Delay);			
			Today.setHours(0);
			Today.setMinutes(t1);			
			TimeArray[k]=MVJS.DateHandler.Format(Today,"h:mm tt");			
		}
		return TimeArray;		
	},	
	Pad : function (val, len)
	{
		val = String(val);
		len = len || 2;
		while (val.length < len) val = "0" + val;
		return val;
	},
	Format:function(date,mask){
	
							var d = date["getDate"]();
							var D = date["getDay"]();
							var m = date["getMonth"]();
							var y = date["getFullYear"]();
							var H = date["getHours"]();
							var M = date["getMinutes"]();
							var s = date["getSeconds"]();
							var L = date["getMilliseconds"]();							
							var flags = {
								d:    d,
								dd:   this.Pad(d),
								ddd:  this.ShortDays[D],
								dddd: this.LongDays[D],
								M:    m + 1,
								MM:   this.Pad(m + 1),
								MMM:  this.ShortMonths[m],
								MMMM: this.LongMonths[m],
								yy:   String(y).slice(2),
								yyyy: y,
								h:    H % 12 || 12,
								hh:   this.Pad(H % 12 || 12),
								H:    H,
								HH:   this.Pad(H),
								m:    M,
								mm:   this.Pad(M),
								s:    s,
								ss:   this.Pad(s),
								l:    this.Pad(L, 3),
								L:    this.Pad(L > 99 ? Math.round(L / 10) : L),
								t:    H < 12 ? "a"  : "p",
								tt:   H < 12 ? "am" : "pm",
								T:    H < 12 ? "A"  : "P",
								TT:   H < 12 ? "AM" : "PM",					
								
								S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
							};
							
						return mask.replace(/d{1,4}|M{1,4}|yy(?:yy)?|([HhmsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g, function ($0) {
							
				return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);});
	},
	GetDaysInMonth:function(DateValue){
		var m = parseInt(parseFloat(DateValue.getMonth()));
		var y = parseInt(parseFloat(DateValue.getYear()));

		var tmpDate = new Date(y, m, 28);
		var checkMonth = tmpDate.getMonth();
		var lastDay = 27;

		while(lastDay <= 31){
			var temp = tmpDate.setDate(lastDay + 1);
			if(checkMonth != tmpDate.getMonth())
			    break;
			lastDay++;
		}
		return lastDay;
	},
	IsValid:function(DateValue,DateFormat) {
		var ReturnDate=this.GetDateFromFormat(DateValue,DateFormat);
		
		if (ReturnDate==0) { return false; }
		return true;
	},
	
	DateAdd:function(DateValue,AddTo,Value){
		var intMonth = DateValue.getMonth();
		var intDate = DateValue.getDate();
		var intYear = DateValue.getFullYear();
		switch(AddTo)
		{
			case "Year":
			intYear+=Value;
			break;
			case "Month":
			intMonth+=Value;
			break;
			case "Day":
			intDate+=Value;
			break;
		}
		return new Date(intYear,intMonth,intDate);
	},
	TimeToText:function(time) {
		var system_date = new Date(time);
		var user_date = new Date();
		var delta_minutes = Math.floor((user_date - system_date) / (60 * 1000));
		if (Math.abs(delta_minutes) <= (8*7*24*60)) { 
		var distance = this.MinutesToText(delta_minutes);
		return distance + ((delta_minutes < 0) ? ' from now' : ' ago');
		} else {
		return 'on ' + system_date.toLocaleDateString();
		}
	},

	MinutesToText:function(minutes) {
		if (minutes.isNaN) return "";
		minutes = Math.abs(minutes);
		if (minutes < 1) return ('less than a minute');
		if (minutes < 50) return (minutes + ' minute' + (minutes == 1 ? '' : 's'));
		if (minutes < 90) return ('about one hour');
		if (minutes < 1080) return (Math.round(minutes / 60) + ' hours');
		if (minutes < 1440) return ('one day');
		if (minutes < 2880) return ('about one day');
		else return (Math.round(minutes / 1440) + ' days');
	},
	GetDateFromFormat:function(val,format) {
		val=val+"";
		format=format.replace(new RegExp( "!", "gi" ),"");
		format=format+"";
		var i_val=0;
		var i_format=0;
		var c="";
		var token="";
		var token2="";
		var x,y;
		var now=new Date();
		var year=now.getYear();
		var month=now.getMonth()+1;
		var date=1;
		var hh=now.getHours();
		var mm=now.getMinutes();
		var ss=now.getSeconds();
		var ampm="";
		
		while (i_format < format.length) {
			// Get next token from format string
			c=format.charAt(i_format);
			token="";
			while ((format.charAt(i_format)==c) && (i_format < format.length)) {
				token += format.charAt(i_format++);
				}
			// Extract contents of value based on format token
			if (token=="yyyy" || token=="yy" || token=="y") {
				if (token=="yyyy") { x=4;y=4; }
				if (token=="yy")   { x=2;y=2; }
				if (token=="y")    { x=2;y=4; }
				year=this.GetInt(val,i_val,x,y);
				if (year==null) { return 0; }
				i_val += year.length;
				if (year.length==2) {
					if (year > 70) { year=1900+(year-0); }
					else { year=2000+(year-0); }
					}
				}
			else if (token=="MMM"||token=="NNN"){
				month=0;
				for (var i=0; i<this.LongMonths.length; i++) {
					var month_name=this.LongMonths[i];
					if (val.substring(i_val,i_val+month_name.length).toLowerCase()==month_name.toLowerCase()) {
						if (token=="MMM"||(token=="NNN"&&i>11)) {
							month=i+1;
							if (month>12) { month -= 12; }
							i_val += month_name.length;
							break;
							}
						}
					}
				if ((month < 1)||(month>12)){return 0;}
				}
			else if (token=="EE"||token=="E"){
				for (var i=0; i<this.LongDays.length; i++) {
					var day_name=this.LongDays[i];
					if (val.substring(i_val,i_val+day_name.length).toLowerCase()==day_name.toLowerCase()) {
						i_val += day_name.length;
						break;
						}
					}
				}
			else if (token=="MM"||token=="M") {
				month=this.GetInt(val,i_val,token.length,2);
				if(month==null||(month<1)||(month>12)){return 0;}
				i_val+=month.length;}
			else if (token=="dd"||token=="d") {
				date=this.GetInt(val,i_val,token.length,2);
				if(date==null||(date<1)||(date>31)){return 0;}
				i_val+=date.length;}
			else if (token=="hh"||token=="h") {
				hh=this.GetInt(val,i_val,token.length,2);
				if(hh==null||(hh<1)||(hh>12)){return 0;}
				i_val+=hh.length;}
			else if (token=="HH"||token=="H") {
				hh=this.GetInt(val,i_val,token.length,2);
				if(hh==null||(hh<0)||(hh>23)){return 0;}
				i_val+=hh.length;}
			else if (token=="KK"||token=="K") {
				hh=this.GetInt(val,i_val,token.length,2);
				if(hh==null||(hh<0)||(hh>11)){return 0;}
				i_val+=hh.length;}
			else if (token=="kk"||token=="k") {
				hh=this.GetInt(val,i_val,token.length,2);
				if(hh==null||(hh<1)||(hh>24)){return 0;}
				i_val+=hh.length;hh--;}
			else if (token=="mm"||token=="m") {
				mm=this.GetInt(val,i_val,token.length,2);
				if(mm==null||(mm<0)||(mm>59)){return 0;}
				i_val+=mm.length;}
			else if (token=="ss"||token=="s") {
				ss=this.GetInt(val,i_val,token.length,2);
				if(ss==null||(ss<0)||(ss>59)){return 0;}
				i_val+=ss.length;}
			else if (token=="a") {
				if (val.substring(i_val,i_val+2).toLowerCase()=="am") {ampm="AM";}
				else if (val.substring(i_val,i_val+2).toLowerCase()=="pm") {ampm="PM";}
				else {return 0;}
				i_val+=2;}
			else {
				if (val.substring(i_val,i_val+token.length)!=token) {return 0;}
				else {i_val+=token.length;}
				}
			}
		// If there are any trailing characters left in the value, it doesn't match
		if (i_val != val.length) { return 0; }
		// Is date valid for month?
		if (month==2) {
			// Check for leap year
			if ( ( (year%4==0)&&(year%100 != 0) ) || (year%400==0) ) { // leap year
				if (date > 29){ return 0; }
				}
			else { if (date > 28) { return 0; } }
			}
		if ((month==4)||(month==6)||(month==9)||(month==11)) {
			if (date > 30) { return 0; }
			}
		// Correct hours value
		if (hh<12 && ampm=="PM") { hh=hh-0+12; }
		else if (hh>11 && ampm=="AM") { hh-=12; }
		var newdate=new Date(year,month-1,date,hh,mm,ss);
		return newdate;
	},
	
	GetInt:function(str,i,minlength,maxlength) {
		for (var x=maxlength; x>=minlength; x--) {
			var token=str.substring(i,i+x);
			if (token.length < minlength) { return null; }
			if (this.IsInteger(token)) { return token; }
			}
		return null;
	},
	IsInteger:function(val) {
	var digits="1234567890";
	for (var i=0; i < val.length; i++) {
		if (digits.indexOf(val.charAt(i))==-1) { return false; }
		}
	return true;
	},
	CompareDates:function(date1,dateformat1,date2,dateformat2) {
		
		var d1=this.GetDateFromFormat(date1,dateformat1);
		var d2=this.GetDateFromFormat(date2,dateformat2);
		if (d1==0 || d2==0) {
			return -1;
			}
		else if (d1 > d2) {
			return 1;
			}
		return 0;
	},
	Compare : function(StartDate,EndDate,DateFormat)
	{
		/*
		Author : Arivnd
		Date   : 10:55 AM 4/25/2008
		Note   : Verify that the StartDate and EndDate values are not blank or null
		Returns:
			-1 => Invalid Start Date
			-2 => Invalid End Date
			 0 => Same Dates
			+1 => Start Date is Greater
			+2 => End Date is Greater
		*/
		if(typeof(StartDate) != 'object')
		{
			StartDate = this.GetDateFromFormat(StartDate,DateFormat);
		}
		if(typeof(EndDate) != 'Object')
		{
			EndDate = this.GetDateFromFormat(EndDate,DateFormat);
		}
		if(StartDate == 0)
		{
			return -1;
		}
		if(EndDate == 0)
		{
			return -2;
		}
		var _Year  = StartDate.getFullYear();
		var _Month = (StartDate.getMonth().toString().length == 2)?(StartDate.getMonth().toString()):('0' + StartDate.getMonth().toString());
		var _Day = (StartDate.getDate().toString().length == 2)?(StartDate.getDate().toString()):('0' + StartDate.getDate().toString());
		var CompareStartDateValue = parseInt(_Year.toString() + _Month.toString() + _Day.toString());
		_Year  = EndDate.getFullYear();
		_Month = (EndDate.getMonth().toString().length == 2)?(EndDate.getMonth().toString()):('0' + EndDate.getMonth().toString());
		_Day = (EndDate.getDate().toString().length == 2)?(EndDate.getDate().toString()):('0' + EndDate.getDate().toString());
		var CompareEndDateValue = parseInt(_Year.toString() + _Month.toString() + _Day.toString());
		if(CompareStartDateValue > CompareEndDateValue)
		{
			return 1;
		}
		else if(CompareEndDateValue > CompareStartDateValue)
		{
			return 2;
		}
		else
		{
			return 0;
		}
	},
	IsValidDate : function (DateString)
	{
		var DateValue=this.GetDateFromFormat(DateString,MVJS.GetValue("hidCalendarControl_DateFormat").toString().replace(/!/g,""));
		if(DateValue==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
};
if(typeof(MVJS)=="undefined")
{
	MVJS={};
}

MVJS.EventHandler=function(){
	return new MVJS._EventHandler();
};

MVJS._EventHandler= function(){
	this.EventReference=null;

	
	this.GetMouseCoords = function (e,HandleScroll) {
		try
		{
		if(MVJS.IsIE())
		{
			if(document.getElementById('MVContainer'))
			document.getElementById('MVContainer').scrollTop = 0;
			document.body.scrollTop = 0;
		}

		var x=(!window.event)?e.pageX:window.event.clientX+(document.documentElement.scrollLeft?document.documentElement.scrollLeft:document.body.scrollLeft);
		var y=(!window.event)?e.pageY:window.event.clientY+(document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop);
		
		if(HandleScroll==null)
		{
			HandleScroll=true;
		}
		
		if(MVJS.Nuggets.TopMenu_Main && MVJS.Nuggets.TopMenu_Main.ScrollTop > 0)
		{
			
			if(MVJS.IsIE())
			{
				if(HandleScroll)
				y+=parseFloat(MVJS.Nuggets.TopMenu_Main.ScrollTop);
			}
			else
			{
				y-=parseFloat(MVJS.Nuggets.TopMenu_Main.ScrollTop);
			}
		}
		return {X:x,Y:y};
		}
		catch(e)
		{
			alert(e);
		}
	};
	
	this.FilterKeys = function(ControlID,FilterChars)
	{
		MVJS.StorageHandler.SetData(ControlID,FilterChars);
		this.AttachEvent(ControlID,"keydown","MVJS.EventHandler().onFilterEvent");
	};
	this.onFilterEvent = function(event)
	{
		try
		{
			var ControlID="";
			var KeyCode=null;
			if(event==null) event=window.event;
			

			if(MVJS.IsIE())
			{
				ControlID=window.event.srcElement.id;
				KeyCode=event.keyCode;
				
			}
			if(MVJS.IsFireFox())
			{
				ControlID=arguments[0].target.id;
				KeyCode=event.which;
			
			}
			var FilterChars=MVJS.StorageHandler.GetData(ControlID);
			
			for(var x=0;x<FilterChars.length;x++)
			{
				
				if(KeyCode==FilterChars[x].charCodeAt(0))
				{
					
					if(MVJS.IsIE())
					{
						event.cancelBubble = true;
    						event.returnValue = false;    					
					}
					return false;
					
				}
			}
		}
		catch(e)
		{
			throw "MVException : " + e.toString();
		}
		
	};

	this.LimitChars = function (ControlID,MaxLimit,CounterControlID)
	{		
		var objControl;
		var objCounterControlID=null;
		if(typeof(ControlID)=='string')
		{
			objControl= document.getElementById(ControlID);
		}
		else
		{
			objControl= ControlID;
		}
		
		if(CounterControlID!='' && CounterControlID!=null)
		{
			if(typeof(CounterControlID)=='string')
			 objCounterControlID= document.getElementById(CounterControlID);
			else
			 objCounterControlID= CounterControlID;
		}
				
		var _objCounterControlID = objCounterControlID;
		objControl.onkeyup = function (event)
		{			
			if(this.value.length > MaxLimit)
			this.value = this.value.substr(0, MaxLimit);
			else if(_objCounterControlID)
			{
				if(_objCounterControlID.nodeName == "INPUT")
				_objCounterControlID.value= MaxLimit- this.value.length;
				else
				_objCounterControlID.innerHTML = MaxLimit- this.value.length;
			}

		};
	
	};

	this.onLimitCharsEvent = function(event)
	{
		try
		{
			var ControlID="";
			var NumberChars=0;
			if(event==null) event=window.event;
			var KeyCode;

			if(MVJS.IsIE())
			{
				ControlID=window.event.srcElement.id;	
				KeyCode=event.keyCode;
				
			}
			if(MVJS.IsFireFox())
			{
				ControlID=arguments[0].target.id;
				KeyCode=event.which;
			}

			NumberChars = ControlID.value.length;

			var FilterChars=MVJS.StorageHandler.GetData(ControlID);
			
			for(var x=0;x<FilterChars.length;x++)
			{
				
				if(KeyCode==FilterChars[x].charCodeAt(0))
				{
					
					if(MVJS.IsIE())
					{
						event.cancelBubble = true;
    						event.returnValue = false;    					
					}
					return false;
				}
			}
		}
		catch(e)
		{
			throw "MVException : " + e.toString();
		}
		
	};	

};

MVJS._EventHandler.prototype.AttachEvent = function(ControlID,Event,EventHandler){
	var ObjControl;
	if(typeof(ControlID)=='string')
	{
		ObjControl= document.getElementById(ControlID);
	}
	else
	{
		ObjControl= ControlID;
	}
	if( ObjControl.addEventListener )
	{
	  ObjControl.addEventListener(Event,EventHandler,false);
	}
	else if( ObjControl.attachEvent)
	{
	  ObjControl.attachEvent('on'+Event,EventHandler);
	}
};

MVJS._EventHandler.prototype.RemoveEvent = function(ControlID,Event,EventHandler){
	var ObjControl;
	if(typeof(ControlID)=='string')
	{
		ObjControl= document.getElementById(ControlID);
	}
	else
	{
		ObjControl= ControlID;
	}
	if( ObjControl.addEventListener )
	{
	  ObjControl.removeEventListener(Event,EventHandler,false);
	}
	else if( ObjControl.attachEvent)
	{
	  ObjControl.detachEvent('on'+Event,EventHandler);
	}
};

if(typeof(MVJS)=="undefined")
{
	MVJS={};
}

MVJS.StorageHandler=function(){
	return new MVJS._StorageHandler();
};

MVJS._StorageHandler= function(){
	this.SimpleStorage=null;
	this.Storage=null;
	this.length=function()
	{
		if(this.SimpleStorage!=null)
			return this.SimpleStorage.length;
		else if(this.Storage!=null)
			return this.Storage.length;
		else
			return 0;
	};
	this.SetData = function (Value,Key) {
		
		if(Key==null)
		{
			if(this.SimpleStorage==null)
			{
				this.SimpleStorage=new Array();
			}
			this.SimpleStorage[this.SimpleStorage.length]=Value;
		}
		else
		{
			if(this.Storage==null)
			{
				this.Storage=new Array();
			}
			var Found=false;
			for(var x=0;x<this.Storage.length;x++)
			{
				if(this.Storage[x][0]==Key)
				{
					Found=true;
					break;
				}
			}
			
			if(!Found)
			{
				x=this.Storage.length;
				this.Storage[x]=new Array();
			}
			

			if(typeof(Value)=="object")	Value.sIndex=x;
			this.Storage[x][0]=Key;
			this.Storage[x][1]=Value;
		}
	};
	
	this.GetData = function (Key) {
		if(this.SimpleStorage!=null)
		{
			return this.SimpleStorage[Key];
		}
		else
		{
			if(isNaN(Key))
			{
				var Found=false;
				for(var x=0;x<this.Storage.length;x++)
				{
					if(this.Storage[x][0]==Key)
					{
						Found=true;
						break;
					}
				}
				if(!Found) throw "MVException: Invalid Key";
				return this.Storage[x][1];
			}
			else
			{
				return this.Storage[Key][1];
			}
		}
	};
	this.Contains=function(Key) {
		if(this.Storage==null)
		{
			this.Storage=new Array();
		}
		var Found=false;
		for(var x=0;x<this.Storage.length;x++)
		{
			if(this.Storage[x][0]==Key)
			{
				Found=true;
				break;
			}
		}
		return Found;
	};
	this.Remove = function(Key)
	{
	};
	
	this.Clear = function()
	{
	};
};


String.prototype.endsWith = function(str){
	return (this.match(str+"$")==str);
};
String.prototype.startsWith = function(str){
	return (this.match("^"+str)==str)
};
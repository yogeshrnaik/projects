if(typeof(MVJS)=="undefined")
{
	MVJS={};
};

MVJS.QuickLaunch=function(ControlId,Target,Preferences){
	return new MVJS._QuickLaunch(ControlId,Target,Preferences);
};

MVJS._QuickLaunch =function(ControlId,Target,Preferences)
{
	if(!ControlId) return;
	if(typeof(ControlId)=="string")
	{
		ControlId = document.getElementById(ControlId);
	}
	
	this.ControlId  = ControlId;
	
	if(typeof(Target)=="string")
	{
		Target = document.getElementById(Target);
	}
	
	this.Target = Target;
	this.Preferences = Preferences;
	
	MVJS.Show(this.ControlId);
	this.OriginalHeight=MVJS.StyleHandler.GetHeight(this.ControlId);
	this.OriginalWidth=MVJS.StyleHandler.GetWidth(this.ControlId);
	this.HSlideStyle = "LeftToRight";
	if (this.Preferences && this.Preferences.HSlideStyle)
	{
		this.HSlideStyle = this.Preferences.HSlideStyle;
	}
	this.OriginalLeft = 0;
	MVJS.StyleHandler.SetHeight(this.ControlId,10);
	MVJS.StyleHandler.SetWidth(this.ControlId,10);
	MVJS.StyleHandler.SetProperty(this.ControlId,'overflow','hidden');
	
	MVJS.Hide(this.ControlId);
	this.CurrentHeight = 10;
	this.CurrentWidth = 10;
	this.CurrentLeft = 0;
	
	var _this = this;
	this.TimeSpan = null;
	this.TargetMouseOut = true;
	this.ControlMouseOut = true;
	this.HideTimeSpan = null;
	this.Mode = "Show";
	Target.onmouseover = function ()
	{
		if(_this.TimeSpan) clearTimeout(_this.TimeSpan);
		_this.TargetMouseOut = false;
		_this.Show();
	};
	
	Target.onmouseout = function ()
	{		
		_this.TargetMouseOut = true;		
		if(_this.HideTimeSpan) clearTimeout(_this.HideTimeSpan);		
		_this.HideTimeSpan = setTimeout(function () {_this.Hide();},300);
	};
	this.ControlId.onmouseover = function ()
	{
		_this.ControlMouseOut = false;
		
	};
	
	this.ControlId.onmouseout = function ()
	{		
		_this.ControlMouseOut = true;		
		if(_this.HideTimeSpan) clearTimeout(_this.HideTimeSpan);		
		_this.HideTimeSpan=setTimeout(function () {_this.Hide();},300);
	};
	
	document.body.oncontextmenu = function ()
	{
		return false;
	};
};

MVJS._QuickLaunch.prototype.Show = function() {	
	this.CurrentHeight = 0;
	this.CurrentWidth = 0;
	this.CurrentLeft =0;
	if (this.HSlideStyle =="LeftToRight")
	{
		var ReferenceCords=MVJS.CoordinatesHandler.GetCoordsByControlID(this.Target,"LeftBottom",false);
	}
	else
	{
		var ReferenceCords=MVJS.CoordinatesHandler.GetCoordsByControlID(this.Target,"RightBottom",false);
	}
	MVJS.Show(this.ControlId);	
	this.OriginalLeft = ReferenceCords.X;
	this.CurrentLeft = ReferenceCords.X;
	if (this.HSlideStyle =="LeftToRight")
	{
		MVJS.CoordinatesHandler.MoveControl(this.ControlId,"LeftTop",ReferenceCords.X,ReferenceCords.Y,null,false);
	}
	else
	{
		MVJS.CoordinatesHandler.MoveControl(this.ControlId,"RightTop",ReferenceCords.X,ReferenceCords.Y,null,false);
	}
	if(this.TimeSpan) clearTimeout(this.TimeSpan);
	MVJS.StyleHandler.SetOpacity(this.ControlId , 0);
	this.Mode = "Show";
	this.ResizeControl(100,true);
};
MVJS._QuickLaunch.prototype.Hide = function() {
	
	if(this.ControlMouseOut && this.TargetMouseOut)
	{
		
		this.CurrentHeight = this.OriginalHeight-(parseFloat(this.OriginalHeight)/5);
		this.CurrentWidth = this.OriginalWidth-(parseFloat(this.OriginalWidth)/5);
		this.CurrentLeft = this.OriginalLeft - this.CurrentWidth;
		this.Mode = "Hide";
		if(this.TimeSpan) clearTimeout(this.TimeSpan);
		this.ResizeControl(100);
	}
};

MVJS._QuickLaunch.prototype.ResizeControl = function(timer,Expand) {

	var CurrentOpacity = MVJS.StyleHandler.GetProperty(this.ControlId,'opacity') * 10;
	var _this = this;
	var resizeHeightby=parseFloat((this.OriginalHeight-this.CurrentHeight)/10);
	var resizeWidthby=parseFloat((this.OriginalWidth-this.CurrentWidth)/10);
	timer--;
	if(this.Mode== "Show") Expand = true;
	if(this.Mode== "Hide") Expand = false;
	if(Expand)
	{
		if(CurrentOpacity!=10)
		MVJS.StyleHandler.SetOpacity(this.ControlId , (CurrentOpacity +1));
		
		this.CurrentHeight = (this.CurrentHeight + resizeHeightby);
		this.CurrentWidth = (this.CurrentWidth + resizeWidthby);
		this.CurrentLeft = (this.CurrentLeft - resizeWidthby);
		this.ControlId.style.height=this.CurrentHeight+"px";
		this.ControlId.style.width=this.CurrentWidth+"px";
		if (this.HSlideStyle =="RightToLeft")
		{
			this.ControlId.style.left=this.CurrentLeft+"px";
		}
		
		if(this.CurrentWidth < this.OriginalWidth)
		{
			this.TimeSpan = setTimeout(function(){_this.ResizeControl(timer,true)},(timer * 200/1000));
		}
		else
		{			
			this.ControlId.style.height=this.CurrentHeight+"px";
			this.ControlId.style.width=this.CurrentWidth+"px";
			MVJS.StyleHandler.SetOpacity(this.ControlId , 10);
		}
	}
	else
	{
		if(CurrentOpacity>0)
		MVJS.StyleHandler.SetOpacity(this.ControlId , (CurrentOpacity -1));
		
		this.CurrentHeight = parseFloat( parseFloat(this.CurrentHeight) -  parseFloat(resizeHeightby));
		this.CurrentWidth = parseFloat( parseFloat(this.CurrentWidth) -  parseFloat(resizeWidthby));
		this.CurrentLeft = parseFloat(parseFloat(this.CurrentLeft) + parseFloat(resizeWidthby));
		if(this.CurrentWidth > 0)
		{
			this.ControlId.style.height=parseFloat(this.CurrentHeight)+"px";
			this.ControlId.style.width=parseFloat(this.CurrentWidth)+"px";
			if (this.HSlideStyle =="RightToLeft")
			{
				this.ControlId.style.left=parseFloat(this.CurrentLeft)+"px";
			}
			this.TimeSpan = setTimeout(function(){_this.ResizeControl(timer)},(timer * 200/1000));
		}
		else
		{	
			MVJS.Hide(this.ControlId);
			this.ControlId.style.height=10;
			this.ControlId.style.width=10;
			if (this.HSlideStyle =="RightToLeft")
			{
				this.ControlId.style.left = this.OriginalLeft+"px";
			}
			MVJS.StyleHandler.SetOpacity(this.ControlId , 0);
		}
	}
	
	
};
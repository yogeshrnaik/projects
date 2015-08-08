if(typeof(MVJS)=="undefined")
	MVJS={};
MVJS.CoordinatesHandler={
	
	GetCoordsByControlID : function (ControlID,Anchor,HandleScroll){
		
		var XCoordinate=0;
		var YCoordinate=0;
		var ControlWidth=0;
		var ControlHeight=0;
		if(MVJS.IsIE() && document && document.body)
		{
			if(document.getElementById('MVContainer'))			
			document.getElementById('MVContainer').scrollTop = 0;
			document.body.scrollTop = 0;
		}
		
		if(HandleScroll==null)
		{
			HandleScroll=true;
		}
		if(typeof(ControlID)=="string")
		{
			ControlID=document.getElementById(ControlID);
		}
		if(!ControlID)
		{
			ControlWidth=document.body.clientWidth;
			ControlHeight=document.body.clientHeight;
		}
		else
		{
			var Coords=MVJS.StyleHandler.GetPosition(ControlID);
			XCoordinate=Coords.X;
			YCoordinate=Coords.Y;			
			ControlWidth=parseInt(MVJS.StyleHandler.GetWidth(ControlID));
			ControlHeight=parseInt(MVJS.StyleHandler.GetHeight(ControlID));
		}
			
		var AdjustX=parseInt(XCoordinate);
		var AdjustY=parseInt(YCoordinate);
		
		if(Anchor==null) Anchor="LeftTop";
		
		switch(Anchor)
		{
				
			case "RightTop":
				AdjustX=XCoordinate + ControlWidth;
				break;
				
			case "LeftBottom":
				AdjustY=parseInt(YCoordinate + ControlHeight);
				break;				
			
			case "RightBottom":
				AdjustX=parseInt(XCoordinate + ControlWidth);
				AdjustY=parseInt(YCoordinate + ControlHeight);				
				break;
			
			case "TopCenter":
				AdjustX=parseInt(XCoordinate + (ControlWidth/2));
				break;
			
			case "LeftCenter":				
				AdjustY=parseInt(YCoordinate + (ControlHeight/2));			
				break;
			
			case "BottomCenter":
				AdjustX=parseInt(XCoordinate + (ControlWidth/2));
				AdjustY=parseInt(YCoordinate + ControlHeight);				
				break;
			
			case "RightCenter":
				AdjustX=parseInt(XCoordinate + ControlWidth);
				AdjustY=parseInt(YCoordinate + (ControlHeight/2));				
				break;
			case "CenterCenter":
				AdjustX=parseInt(XCoordinate + (ControlWidth/2));
				AdjustY=parseInt(YCoordinate + (ControlHeight/2));
				break;
		}
		if(MVJS.IsFireFox() && HandleScroll)
		{			
			if(MVJS.Nuggets.TopMenu_Main)
			AdjustY-=parseFloat(MVJS.Nuggets.TopMenu_Main.ScrollTop);						
		}
		
		return {X:AdjustX,Y:AdjustY};

	},
	
	GetControlIDByCoords : function (XCoordinate,YCoordinate) {
		alert(document.body.componentFromPoint(XCoordinate,YCoordinate).type);
	},
		
	GetControlRegion : function (ControlID,HandleScroll) {
		if(HandleScroll==null)
		{
			HandleScroll=true;
		}
		
		var Coords=this.GetCoordsByControlID(ControlID,"LeftTop",HandleScroll);
		var XCoordinate= Coords.X;
		var YCoordinate= Coords.Y;
		var ControlHeight=parseFloat(MVJS.StyleHandler.GetHeight(ControlID));
		var ControlWidth=parseFloat(MVJS.StyleHandler.GetWidth(ControlID));		
		
		return {X1:XCoordinate,Y1:YCoordinate,X2:XCoordinate+ControlWidth,Y2:YCoordinate+ControlHeight};
	},
	IsPositionOutsideScreen : function (ControlID, TargetX, TargetY) {
	
		var ControlWidth=MVJS.StyleHandler.GetWidth(ControlID);
		var ControlHeight=MVJS.StyleHandler.GetHeight(ControlID);
		
		if(TargetX <0|| TargetY <0 || screen.availWidth < (TargetX + ControlWidth) || screen.availHeight < (TargetY + ControlHeight))
			return true;
		else
			return false;
	},
	IsMouseOnEdge : function(Control,e,EdgeType)
	{
		var x = (window.event) ? window.event.x-6:e.pageX-6; 
		var y = (window.event) ? window.event.y+10:e.pageY+10 ;
		
		
		var Region={};
		var Position=MVJS.StyleHandler.GetPosition(Control);
		Region.X1=Position.X;
		Region.Y1=Position.Y;
		if(MVJS.IsIE())
		{
			Region.X2=Position.X+Control.offsetWidth;
			Region.Y2=Position.Y+Control.offsetHeight;
		}
		else
		{
			Region.X2=Position.X+MVJS.StyleHandler.GetWidth(Control);
			Region.Y2=Position.Y+MVJS.StyleHandler.GetHeight(Control);
		}
		
		var Value=false;
		
		if(EdgeType=="Both" || EdgeType=="Horizontal")
		{
			if( (x>=Region.X2-5 && x < Region.X2))
			{
				Value=true;
			}
		}
		if(EdgeType=="Both" || EdgeType=="Vertical")
		{
			if((y>Region.Y1-1 && y < Region.Y1+1) || (y>Region.Y2-1 && y < Region.Y2+1))
			{
				Value=true;
			}
		}
		return Value;
	},
	GetExtreamMoveCoords : function (ControlID, TargetX, TargetY) {
		var ControlWidth=MVJS.StyleHandler.GetWidth(ControlID);
		var ControlHeight=MVJS.StyleHandler.GetHeight(ControlID);
		
		var AjustX=TargetX ,AdjustY=TargetY;
		if(TargetX < 0)
		AjustX=0;
		
		if(TargetY <0)
		AdjustY=0;
		
		if(screen.availWidth < (TargetX + ControlWidth))
		AjustX = screen.availWidth - ControlWidth;
		
		if(screen.availHeight < (TargetY + ControlHeight))
		AdjustY = screen.availHeight - ControlHeight;
		
		return {X:AjustX ,Y:AdjustY};
		
	},
	AlignControls : function(Control1ID, Control1Anchor,Control2ID, Control2Anchor,ScrollHandle){		
		var Coords=this.GetCoordsByControlID(Control1ID,Control1Anchor,false);		
		this.MoveControl(Control2ID, Control2Anchor, Coords.X, Coords.Y,null,ScrollHandle);
	},
	
	MoveControl : function (ControlID,  Anchor, TargetX, TargetY,AdustToMaxCoords,HandleScroll) {
		
		var ControlWidth=MVJS.StyleHandler.GetWidth(ControlID);
		var ControlHeight=MVJS.StyleHandler.GetHeight(ControlID);					
		var AdjustX=TargetX,AdjustY=TargetY;
		
		if(HandleScroll==null)
		{
			HandleScroll = true;
		}
		
		MVJS.StyleHandler.SetProperty(ControlID,"position","absolute");
		
		switch(Anchor)
		{		
			
			case "RightTop":
				AdjustX = parseInt(TargetX - ControlWidth);
				break;
				
			case "LeftBottom":
				AdjustY = parseInt(TargetY - ControlHeight);
				break;				
			
			case "RightBottom":
				AdjustX = parseInt(TargetX) - ControlWidth;
				AdjustY =parseInt(TargetY) - ControlHeight;
				break;
			
			case "TopCenter":
				AdjustX = parseInt(TargetX) - parseInt(ControlWidth/2);				
				break;
			
			case "LeftCenter":
				AdjustY = parseInt(TargetY) - parseInt(ControlHeight/2);
				break;
			
			case "BottomCenter":
				AdjustX = parseInt(TargetX) - parseInt(ControlWidth/2);
				AdjustY = parseInt(TargetY) - ControlHeight;
				break;
			
			case "RightCenter":
				AdjustX = parseInt(TargetX) - ControlWidth;
				AdjustY = parseInt(TargetY) - parseInt(ControlHeight/2);
				break;
			case "CenterCenter":
				
				AdjustX = parseInt(TargetX) - parseInt(ControlWidth/2);
				AdjustY = parseInt(TargetY) - parseInt(ControlHeight/2);
				break;
		}
		
		if(AdustToMaxCoords!=null)
		{
			if(AdustToMaxCoords==true)
			{
				
				var Coords=this.GetExtreamMoveCoords(ControlID,AdjustX,AdjustY);
				AdjustX=Coords.X;
				AdjustY=Coords.Y;
			}
		}
		
		
		if(MVJS.IsFireFox() && HandleScroll)
		{
			if(MVJS.Nuggets.TopMenu_Main)
			{
				AdjustY-=MVJS.Nuggets.TopMenu_Main.ScrollTop;
				
			}
		}
		
			
		MVJS.StyleHandler.SetPosition(ControlID,AdjustX,AdjustY);		
		
	},
	
	IsControlInRegion : function (ControlID,Region) {
		
		var ControlRegion=this.GetControlRegion(ControlID);
		
		
		if(Region.X1> ControlRegion.X1 || Region.Y1>  ControlRegion.Y1 || Region.X2< ControlRegion.X2 || Region.Y2< ControlRegion.Y2)
			return false;
		else
			return true;
	},
	
	ScrollToControl : function (ControlID)	{
			
		var Coords=this.GetCoordsByControlID(ControlID);		
		this.ScrollTo(Coords.X,Coords.Y);		
	},
	
	ScrollTo : function (HorizontalValue,VerticalValue) {
		window.scrollTo(HorizontalValue,VerticalValue);
	}
	
	
	
};
	
		


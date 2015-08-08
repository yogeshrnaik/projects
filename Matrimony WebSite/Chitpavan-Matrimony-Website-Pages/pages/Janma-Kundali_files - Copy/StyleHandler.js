if(typeof(MVJS)=="undefined")
	MVJS={};
MVJS.StyleHandler= {	
				
		SetProperty : function (ControlID, PropertyName, Value) {
			
			try
			{
				var objControl;
				if(typeof(ControlID)=='string')
				{
					objControl= document.getElementById(ControlID);
				}
				else
				{
					objControl= ControlID;
				}
				
				eval("objControl.style."+PropertyName+"='"+Value+"';");
				
			}
			catch(e)
			{				
				MVJS.ReportError(e);
			}
		},
		
		GetProperty: function(ControlID, PropertyName) {			
			try
			{
				
				var objControl;
				var strValue;
				if(typeof(ControlID)=='string')
				{
					objControl= document.getElementById(ControlID);
				}
				else
				{
					objControl= ControlID;
				}
				
				if (objControl.currentStyle) /*For IE*/
				{
					strValue = objControl.currentStyle[PropertyName];
				}	
				else if (window.getComputedStyle)
				{
					strValue = document.defaultView.getComputedStyle(objControl,null).getPropertyValue(PropertyName);
				}
				return strValue ;
				
			}
			catch(e)
			{				
				MVJS.ReportError(e);
			}			
		},
		GetWidth : function (ControlID){
			try
			{
				var ControlWidth=parseInt(this.GetProperty(ControlID, 'width'));
				if(isNaN(ControlWidth) || ControlWidth==0)
				{
					var objControl;
					if(typeof(ControlID)=='string')
					{
						objControl= document.getElementById(ControlID);
					}
					else
					{
						objControl= ControlID;
					}

					ControlWidth=objControl.offsetWidth;
				}
				return ControlWidth;
			}
			catch(e)
			{				
				MVJS.ReportError(e);			
			}	
		},
		GetHeight : function (ControlID){
			try
			{
				var VisibilityState = MVJS.StyleHandler.GetProperty(ControlID,"display");
				if(VisibilityState=="none")
				{
					this.SetVisible(ControlID,true);
				}
				var ControlHeight=parseInt(this.GetProperty(ControlID, 'height'));
				if(isNaN(ControlHeight) || ControlHeight==0)
				{
					var objControl;
					if(typeof(ControlID)=='string')
					{
						objControl= document.getElementById(ControlID);
					}
					else
					{
						objControl= ControlID;
					}

					ControlHeight=objControl.offsetHeight;
				}
				if(VisibilityState=="none")
				{
					this.SetVisible(ControlID,false);
				}
				return ControlHeight;
			}
			catch(e)
			{
				MVJS.ReportError(e);
			}
		},
			
		GetPosition : function (ControlID){
				
			try
			{				
				var CurrentTop = parseInt(this.GetProperty(ControlID,"top"));
				var CurrentLeft = parseInt(this.GetProperty(ControlID,"left"));
				
				if(isNaN(CurrentTop) || isNaN(CurrentLeft) || CurrentTop==0 || CurrentLeft==0)
				{
					
					var objControl;
					if(typeof(ControlID)=='string')
					{
						objControl= document.getElementById(ControlID);
					}
					else
					{
						objControl= ControlID;
					}
					
					if (objControl.offsetParent) {
						CurrentLeft = objControl.offsetLeft;
						CurrentTop = objControl.offsetTop;
						while (objControl.offsetParent) {
							objControl= objControl.offsetParent;
							CurrentLeft += objControl.offsetLeft;
							CurrentTop += objControl.offsetTop;
						}
					}								
				}
				return {X:CurrentLeft,Y:CurrentTop};
			}
			catch(e)
			{
				MVJS.ReportError(e);
			}		
		},
	
		SetColor : function (ControlID,ForeColor,BackColor) {
			try
			{
				if(ForeColor!=null)				
				this.SetProperty(ControlID,"color",ForeColor);
				
				if(BackColor!=null)
				this.SetProperty(ControlID,"backgroundColor",BackColor);
			}
			catch(e)
			{
				MVJS.ReportError(e);
			}
		},

		SetDimensions : function (ControlID,Width,Height) {			
			try
			{
				if(Width!=null)
				this.SetProperty(ControlID,"width",Width);
				
				if(Height!=null)
				this.SetProperty(ControlID,"height",Height);
			}
			catch(e)
			{
				MVJS.ReportError(e);
			}
		},
		
		SetWidth : function (ControlID,Width) {					
			try
			{
				if(Width!=null)
				this.SetProperty(ControlID,"width",Width);
				
			}
			catch(e)
			{
				MVJS.ReportError(e);
			}
		},
		
		SetHeight : function (ControlID,Height) {					
			try
			{
				if(Height!=null)
				this.SetProperty(ControlID,"height",Height);
			}
			catch(e)
			{
				MVJS.ReportError(e);
			}
		},

		SetOpacity : function (ControlID,Value) {
			try
			{
				
				this.SetProperty(ControlID,"opacity",Value/10);
				this.SetProperty(ControlID,"filter ","alpha(opacity=" + Value*10 + ")");
			}
			catch(e)
			{
				MVJS.ReportError(e);
			}
		},

		SetPosition : function (ControlID,X,Y) {
			try
			{
				this.SetProperty(ControlID,"left",X);
				this.SetProperty(ControlID,"top",Y);
			}
			catch(e)
			{
				MVJS.ReportError(e);
			}
		},

		SetVisible : function (ControlID,Value) { 

			try
			{
				if(Value)
					this.SetProperty(ControlID,"display","block");
				else
					this.SetProperty(ControlID,"display","none");
			}
			catch(e)
			{
				MVJS.ReportError(e);
			}
		},

		SetFont : function (ControlID,FontFamily,FontSize,FontWeight,TextDecoration,TextTransform) {
			try
			{
				if(FontFamily!=null)
				this.SetProperty(ControlID,"fontFamily",FontFamily);
				
				if(FontSize!=null)
				this.SetProperty(ControlID,"fontSize",FontSize);
				
				if(FontWeight!=null)
				this.SetProperty(ControlID,"fontWeight",FontWeight);
				
				if(TextDecoration!=null)
				this.SetProperty(ControlID,"textDecoration",TextDecoration);
				
				if(TextTransform!=null)
				this.SetProperty(ControlID,"textTransform",TextTransform);				
			}
			catch(e)
			{
				MVJS.ReportError(e);
			}
		},
		
		SetEnable : function (ControlID,Value) {
			try
			{
				var objControl;
				if(typeof(ControlID)=='string')
				{
					objControl= document.getElementById(ControlID);
				}
				else
				{
					objControl= ControlID;
				}
				
				if(Value)
				objControl.disabled=false;
				else
				objControl.disabled=true;
			}
			catch(e)
			{
				MVJS.ReportError(e);
			}
		}		
	};
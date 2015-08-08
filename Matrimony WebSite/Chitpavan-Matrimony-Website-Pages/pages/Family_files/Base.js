var MVJS={
	Nuggets:new Object(),
	JSON:new Object(),
	PopupWindows:null,
	PopupBackgrounds:null,
	PopupZIndex:0,
	BlankSpace:' ',
	
	SingleLayout:"<div style=\"width:1000px;\"><div style=\"padding:10px;\"><div id=\"{0}_MidContainer\"></div></div></div>",
	MidRightLayout:"<div style=\"padding-left:10px;padding-bottom:10px;\"><div style=\"float:left;width:780px;\"><div id=\"{0}_MidContainer\"></div></div><div style=\"width:9px;float:left;\">&nbsp;</div><div style=\"width:190px;float:left;padding-top:20px;\"><div id=\"{0}_RightContainer\"></div></div><br style=\"clear:both;\"/></div>",
	OneColumnLayout:"<div class=\"Container\"><div id=\"{0}_MidContainer\" style=\"height:100%;\"></div></div>",
	TwoColumnLayout:"<div class=\"Container\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" height=\"100%\"><tr><td class=\"JobsCSS_LeftBar\" width=\"167px\" valign=\"top\"><div style=\"text-align:left;width:auto;\" id=\"{0}_LeftContainer\"></div></td><td style=\"width:15px;\">&nbsp;</td><td style=\"width:815px;\" valign=\"top\"><div id=\"{0}_MidContainer\"></div></td></tr></table></div>",
	ThreeColumnLayout:"<div class=\"Container\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" height=\"100%\"><tr><td class=\"JobsCSS_LeftBar\" width=\"167px\" valign=\"top\"><div style=\"text-align:left;width:auto;\" id=\"{0}_LeftContainer\"></div></td><td style=\"width:10px;\">&nbsp;</td><td style=\"width:617px;\" valign=\"top\"><div id=\"{0}_MidContainer\"></div></td><td style=\"width:10px;\">&nbsp;</td><td style=\"width:180px;padding-top:20px;\" valign=\"top\"><div id=\"{0}_RightContainer\"></div></td></tr></table></div>",
	ThreeColumnWithTopLayout:"<div class=\"Container\" style=\"width:980px;margin:10px;margin-bottom:0px;padding-bottom:10px;\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"100%\"><tr><td style=\"height:28px;\"><div id=\"{0}_TopContainer\"></div></td></tr><tr><td class=\"Comp_MainDiv\" valign=\"top\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"100%\"><tr><td class=\"JobsCSS_LeftBar\" valign=\"top\"><div style=\"text-align:left;\" id=\"{0}_LeftContainer\"></div></td><td class=\"JobsCSS_BorderRight\" style=\"width:650px;\" valign=\"top\"><div id=\"{0}_MidContainer\"></div></td><td style=\"width:163px;\"><div id=\"{0}_RightContainer\"></div></td></tr></table></td></tr></table></div>",
	OneColumnTopLayout:"<div><div style=\"width:980px;padding:10px;\"><div id=\"{0}_TopContainer\"></div><div style=\"clear:both;display:block;\"><div class=\"BorderClass\" style=\"border-top:0px;\"><div class=\"BorderClass\" id=\"{0}_MidContainer\" style=\"float:left;width:815px;border-top:0px;border-left:0px;border-bottom:0px;\"></div><div style=\"float:right;width:145px;\" id=\"{0}_RightContainer\"></div><div style=\"clear:both;font-size:0px;\"></div></div></div></div></div>",
	MailColumnLayout:"<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"100%\"><tr><td class=\"LeftSideBarMailView\" valign=\"top\"><div style=\"text-align:left;\" id=\"{0}_LeftContainer\"></div></td><td width=\"10px\">&nbsp;</td><td valign=\"top\"><div id=\"{0}_MidContainer\"></div></td><td width=\"10px\">&nbsp;</td></tr></table>",
	DatingTwoColumnLayout:"<div class=\"DatingCSS_Container\"><table cellspacing=\"0\" class=\"DatingCSS_Container\" cellpadding=\"0\" border=\"0\" height=\"100%\"><tr><td class=\"DatingCSS_LeftBar\" width=\"167px\" valign=\"top\"><div style=\"text-align:left;width:auto;\" id=\"{0}_LeftContainer\"></div></td><td style=\"width:15px;\">&nbsp;</td><td style=\"width:815px;\" valign=\"top\"><div id=\"{0}_MidContainer\"></div></td><td>&nbsp;</td></tr></table></div>",
	DatingSingleLayout:"<table cellspacing=\"0\" class=\"DatingCSS_Container\" cellpadding=\"0\" border=\"0\" height=\"100%\"><tr><td valign=\"top\"><div style=\"width:1000px;\"><div style=\"padding:10px;padding-left:15px;\"><div id=\"{0}_MidContainer\"></div></div></div></td></tr></table>",
	IsIE:function() {
		
		var Browser=this.GetBrowser();
		if (Browser=="IE")
		return true;				
	},
	IsIE7 : function (){
		return (navigator.appVersion.indexOf("MSIE 7.")==-1) ? false : true;	
	},
	IsFF2 : function (){
		return (navigator.userAgent.indexOf("Firefox/2.")==-1) ? false : true;	
	},
	IsFF3 : function (){
			return (navigator.userAgent.indexOf("Firefox/3.")==-1) ? false : true;	
	},
	GetById:function(ID){
		return document.getElementById(ID);
	},
	GetByName:function(Name){
			return document.getElementsByName(Name);
	},
	GetByTags:function(tn){
		return document.getElementsByTagName(tn);
	},
	IsFireFox: function () {

		var Browser=this.GetBrowser();

		if (Browser=="FF")
		return true;
	},
	AdjustWindow:function()
	{
		top.window.moveTo(0,0);
		top.window.resizeTo(screen.availWidth,screen.availHeight);
	},
	ShowDefaultOptions:function(MenuContainer,NuggetName,SkinClassName,ObjectId)
	{
		if(MenuContainer.lang!="HELP")
		{		
			var ObjTopMenuBar = MVJS.MenuBar(MenuContainer.id,'',false);
			var ObjViewMenu = MVJS.Menu(MenuContainer.id+"_Menu");
			ObjTopMenuBar.AddItem(MVJS.MenuBarItem(1,"&nbsp;","#",'','IconsCSS_DefaultOptions','MVToolBar_AddressBar_Links',ObjViewMenu));
			var TopMenu=MVJS.Nuggets.TopMenu_Main;
			ObjViewMenu.AddItem(MVJS.MenuItem(1,0,TopMenu.DefaultOptions[0],"javascript:MVJS.LoadPage('MVHelp','"+TopMenu.DefaultOptions[0]+"',{Mid:[{NuggetName:'HelpPages',Parameters:[['NuggetName','"+NuggetName+"']]}]},false,MVJS.SingleLayout);void(0);",''));
			ObjViewMenu.AddItem(MVJS.MenuItem(2,0,TopMenu.DefaultOptions[1],"javascript:MVJS.LoadPage('SuggestFeature','"+TopMenu.DefaultOptions[1]+"',{Mid:[{NuggetName:'ContactUs',Parameters:[['NuggetName','"+NuggetName+"'],['SkinName','"+SkinClassName+"'],['ContactUsType','1']]}]},false);void(0);",''));
			ObjViewMenu.AddItem(MVJS.MenuItem(3,0,TopMenu.DefaultOptions[2],"javascript:MVJS.LoadPage('ReportBug','"+TopMenu.DefaultOptions[2]+"',{Mid:[{NuggetName:'ContactUs',Parameters:[['NuggetName','"+NuggetName+"'],['SkinName','"+SkinClassName+"'],['ContactUsType','2'],['ObjectId','"+ObjectId+"']]}]},false);void(0);",''));
			if(ObjectId!="0")
			{
				ObjViewMenu.AddItem(MVJS.MenuItem(4,0,TopMenu.DefaultOptions[3],"javascript:MVJS.LoadPage('ReportAbuse','"+TopMenu.DefaultOptions[3]+"',{Mid:[{NuggetName:'ContactUs',Parameters:[['NuggetName','"+NuggetName+"'],['SkinName','"+SkinClassName+"'],['ContactUsType','3'],['ObjectId','"+ObjectId+"']]}]},false);void(0);",''));
			}
			MenuContainer.lang="HELP";
			ObjTopMenuBar.ShowItem(0);
		}	
	},
	AdjustMainContainer:function()
	{
		
		var viewportwidth;
		var viewportheight;
		if (typeof(window.innerWidth) != 'undefined')
		{
			viewportwidth = window.innerWidth;
			viewportheight = window.innerHeight;
		}
		else if (typeof(document.documentElement) != 'undefined' && typeof(document.documentElement.clientWidth) !='undefined' && document.documentElement.clientWidth != 0)
		{
			viewportwidth = document.documentElement.clientWidth;
			viewportheight = document.documentElement.clientHeight;
		}
		else
		{
			viewportwidth = document.getElementsByTagName('body')[0].clientWidth;
			viewportheight = document.getElementsByTagName('body')[0].clientHeight;
		}
		viewportheight=viewportheight-16;
		if(viewportwidth < 1000)
		{
			document.getElementById('MVContainer').innerHTML="<br><br><br><br><br><br><br><br><br><div class='MainCompHeading' style='text-align:center;'>MyVishwa requires minimum Screen Resolution of 1024 X 768.<br>Please adjust your Resolution and visit again.</div>";
			return;
		}
		document.getElementById('MVContainer').style.width=viewportwidth;
		document.getElementById('MVContainer').style.height=viewportheight;
		
		var Footer=document.getElementById("MVNuggets_Footer");
		var Header=document.getElementById("MVNuggets_Header");
		var PageOuter=document.getElementById("MVNuggets_PageOuterDiv");
		if(MVJS.IsIE())
		{
			Footer.style.height="20px";
			Header.style.height="119px";
		}
		else
		{
			Footer.style.height="10px";
			Header.style.height="119px";
		}
		PageOuter.style.height=viewportheight-parseInt(Header.style.height)-parseInt(Footer.style.height);
	},
	HandleLinks:function(Input)
	{
		var UrlMatcher=new RegExp("((http|https)(://)|(www)|((http|https)(://)(www)))([\\w+?\\.\\w+])+([a-zA-Z0-9\\~\\!\\@\\#\\$\\%\\^\\&amp;\\*\\(\\)_\\-\\=\\+\\\\\\/\\?\\.\\:\\;\\'\\,]*)?","gi");
		var Match=null;	
		var Replacement="";
		while(Match=UrlMatcher.exec(Input))
		{
			var PreviousChar=Input[Match.index-1];
			
			if(PreviousChar!="<" && PreviousChar!=">" && PreviousChar!="=")
			{
					Replacement="<a class=\"TextLink\" href=\"javascript:MVJS.Nuggets.TopMenu_Main.OpenExternalURL('"+Match[0]+"');void(0);\">"+Match[0]+"</a>";
					Input=Input.replace(Match[0],Replacement);					
					UrlMatcher.lastIndex=Match.index+Replacement.length;
			}

		}
		
		return Input;
	},
	FreeNuggets:function(Container)
	{
		if(typeof(Container)=="string")
		{
			Container=document.getElementById(Container);
		}		
		if(!Container) return;		
		var NuggetDivs=Container.getElementsByTagName("div");
		for(var x=0;x<NuggetDivs.length;x++)
		{
			
			if(NuggetDivs[x].id && NuggetDivs[x].id.length>11)
			{
			
				if(NuggetDivs[x].id.substring(0,12)=="divMVNuggets")
				{
			
					var NuggetName=NuggetDivs[x].id.substring(13,NuggetDivs[x].id.length);					
					eval("if(MVJS.Nuggets."+NuggetName+" && MVJS.Nuggets."+NuggetName+".Dispose){MVJS.Nuggets."+NuggetName+".Dispose();}");					
					eval("delete MVJS.Nuggets."+NuggetName+";");
					eval("delete MVJS.JSON."+NuggetName+";");
					eval("delete MVJS.JSON._"+NuggetName+";");
					NuggetName=NuggetName.substring(0,NuggetName.indexOf("_"));
					eval("delete Obj"+NuggetName+"Client;");
					
				}
			}
		}
	},
	HandleTimeout :function ()
	{
		var PageContainer = MVJS.Nuggets.TopMenu_Main.Tabs.GetTabPage(MVJS.Nuggets.TopMenu_Main.Tabs.GetActiveTabID(),true);
		if(PageContainer)
		{
			PageContainer.innerHTML = "";
			var IFrameElement = document.createElement("iframe");	
			IFrameElement.style.width="100%";
			IFrameElement.style.height="100%";
			IFrameElement.style.border="0px;";
			PageContainer.appendChild(IFrameElement);
			var EditorDocument;
			if (IFrameElement.contentDocument)
			{  
				EditorDocument=IFrameElement.contentDocument;
			}
			else
			{
				EditorDocument = IFrameElement.contentWindow.document;								
			}
			EditorDocument.write('<html><head><title>MyVishwa - We Create Time</title><link rel="stylesheet" href="themes/default/stylesheets/all_style.css" type="text/css"/></head><body topmargin="0" bottommargin="0">'+document.getElementById('MVNuggets_LoadingErrorDiv').innerHTML +'</body></html>');
		}
	},
	IsOpera: function () {

		var Browser=this.GetBrowser();

		if (Browser=="Opera")
		return true;
	},
	CloneObject:function (Obj){
	
	    if(Obj == null || typeof(Obj) != 'object')  return Obj;
	
	    var temp = new Obj.constructor();
	
	    for(var key in Obj){
	    	if(typeof(Obj[key])=="object")
	    	{
	        	temp[key] = this.CloneObject(Obj[key]);
	        }
	        else
	        {
	        	temp[key] = Obj[key];
	        }
	    }
	    return temp;	
	},
	GetBrowser : function() {
		if ( document.all && document.getElementById && !window.opera ) {
		    return "IE";
		}

		if ( !document.all && document.getElementById && !window.opera ) {
		    return "FF";
		}

		if ( document.all && document.getElementById && window.opera ) {
		    return "Opera";
		}
	},
	ScrollTo : function (XCoord,YCoord) {		
		document.getElementById("MVNuggets_PageOuterDiv").scrollTop = XCoord;
		document.getElementById("MVNuggets_PageOuterDiv").scrollTop = YCoord;
	},	
	SetValue : function (ControlID, Value){
		try
		{	
			if(!Value && Value!=0 && Value!=false) Value="";
			var objControl;
			if(typeof(ControlID)=='string')
			{
				if(ControlID=="") return;
				objControl= document.getElementById(ControlID);
			}
			else
			{
				objControl= ControlID;
			}
			var ElementType;
			if(typeof(Value)=="string")
			{
				try{
				Value=Value.replace(/&rsquo;/g,"'");
				Value=Value.replace(/&rdquo;/g,"\"");
				}catch(e){}
			}
			
				if(objControl==null || (objControl.type && objControl.type.toUpperCase()=="RADIO")  || (objControl.type && objControl.type.toUpperCase()=="CHECKBOX"))
				{
					
					if(objControl==null || (objControl.type && objControl.type.toUpperCase()=="CHECKBOX" ))
					{
						if(typeof(Value)!="boolean")
						{							
							objControl = document.getElementsByName(ControlID);
							if(!objControl || objControl.length<=0) return false;
							ElementType = objControl[0].tagName;
							if(ElementType=="INPUT")
							ElementType = objControl[0].type.toUpperCase();
							
						}
						else
						{
							if(!objControl) return false;
							ElementType=objControl.tagName;
							if(ElementType=="INPUT")
							ElementType=objControl.type.toUpperCase();
						}
					}
					else
					{
						objControl = document.getElementsByName(ControlID);						
						ElementType = objControl[0].tagName;
						if(ElementType=="INPUT")
						ElementType = objControl[0].type.toUpperCase();
					}
				}
				else
				{
					ElementType=objControl.tagName;
					if(ElementType=="INPUT")
					ElementType=objControl.type.toUpperCase();
				}

				if(objControl==null)
				{
					return false;
				}

				switch(ElementType)
				{
					case "A":
					case "P":
					case "LI":
					case "SPAN":
					case "TD":
					case "DIV" :
						
						objControl.innerHTML=Value;						
						if(objControl.className=="Error")
						{
							if(objControl.style.display=="block")
							{
								if(Value==""){
									objControl.style.display="none";
								}
								else
								{
									objControl.style.display="block";
								}
								
							}
							else
							{
								
								if(Value!="") objControl.style.display="block";
							}
						}
						break;
					case "SELECT":
						try
						{
							if(!objControl.multiple)
							{
								for(var x=0;x<objControl.options.length;x++)
								{
									if(objControl.options[x].value==Value)
									{
										objControl.selectedIndex=x;break;
									}
								}
							}
							else
							{
								for(var y=0;y<Value.length;y++)
								{
									for(var x=0;x<objControl.options.length;x++)
									{
										if(objControl.options[x].value==Value[y])
										{
											objControl.options[x].selected=true;
										}
									}
								}
							}
						}
						catch(e)
						{
							MVJS.ReportError(e);
						}

					break;
					case "CHECKBOX":
						if(typeof(Value)!="boolean")
						{
							for(var z=0;z<objControl.length;z++)
							objControl[z].checked = false;
							for(var y=0;y<Value.length;y++)
							{
								for(var x=0;x<objControl.length;x++)
								{
									if(objControl[x].value==Value[y])
									{
										objControl[x].checked = true;
									}
								}
							}
						}
						else
						{
							objControl.checked = Value;
						}
					break;
					case "RADIO":
						for(var x=0;x<objControl.length;x++)
						{
							if(objControl[x].value==Value)
							objControl[x].checked=true;
						}
					break;
					default:
						objControl.value=Value;
					break;
			}
		}
		catch(e)
		{
			MVJS.ReportError(e);
		}
				
	},
	ResizeImage : function(ObjImage,Height,Width){
		var NewHeight = ObjImage.height;
		var NewWidth = ObjImage.width;
		if(ObjImage.height <= Height && ObjImage.width <= Width)
		{
			NewHeight = ObjImage.height;
			NewWidth = ObjImage.width;
		}
		else
		{
			if(ObjImage.height <= Height && ObjImage.width >= Width)
			{
				NewWidth = Width;
				NewHeight = ObjImage.height * (NewWidth / ObjImage.width);
			}
			else if(ObjImage.width <= Width && ObjImage.height >= Height)
			{
				NewHeight = Height;
				NewWidth = ObjImage.width * (NewHeight / ObjImage.height);
			}
			else if(ObjImage.width >= Width && ObjImage.height >= Height)
			{
				if(ObjImage.height > ObjImage.width)
				{
					NewHeight = Height;
					NewWidth = ObjImage.width * (NewHeight / ObjImage.height);
				}
				else
				{
					NewWidth = Width;
					NewHeight = ObjImage.height * (NewWidth / ObjImage.width);
				}
			}
			else
			{
				NewHeight = ObjImage.height;
				NewWidth = ObjImage.width;
			}
		}
		ObjImage.height = Math.floor(NewHeight);
		ObjImage.width = Math.floor(NewWidth);
	},
	GetValue : function (ControlID) {
		try
		{
			var Value = "null";
			var ObjTarget;
			var ElementType;
				if(typeof(ControlID)=='string')
				{
					ObjTarget = document.getElementById(ControlID);				
				}
				else
				{
					ObjTarget = ControlID;
				}

				if(ObjTarget == null || (ObjTarget.type && ObjTarget.type.toUpperCase()=="RADIO"))
				{					
					ObjTarget = document.getElementsByName(ControlID);					
					if(!ObjTarget) return false;
					ElementType = ObjTarget[0].tagName;
					if(ElementType=="INPUT")
					ElementType = ObjTarget[0].type.toUpperCase();
				}
				else if(ObjTarget && MVJS.IsIE() && (ObjTarget.type && ObjTarget.type.toUpperCase()=="CHECKBOX"))
				{	
					var StrControlId = (typeof(ControlID)=='string')?ControlID:ControlID.id;
					if(ObjTarget.id && StrControlId && ObjTarget.id==StrControlId)
					{						
						if(typeof(ControlID)=='string')
							ObjTarget = document.getElementById(ControlID);
						else
							ObjTarget = ControlID;
						ElementType = ObjTarget.tagName;
						ElementType = ObjTarget.type.toUpperCase();
					}
					else
					{
						ObjTarget = document.getElementsByName(ControlID);
						ElementType = ObjTarget[0].tagName;
						ElementType = ObjTarget[0].type.toUpperCase();
					}
				}
				else
				{	
					ElementType = ObjTarget.tagName;
					if(ElementType=="INPUT")
					ElementType = ObjTarget.type.toUpperCase();
				}

				if(!ObjTarget)
				{
					return Value;
				}

				switch(ElementType)
				{
					case "DIV":
					case "SPAN":
					case "TD":
					case "A":
						Value = ObjTarget.innerHTML;
					break;
					case "SELECT":
						if(ObjTarget.options.length > 0)
						{
							if(!ObjTarget.multiple)
							{
								if(ObjTarget.selectedIndex > -1)
									Value = ObjTarget.options[ObjTarget.selectedIndex].value;
							}
							else
							{
								var TempValues = "";
								for(var x=0;x<ObjTarget.options.length;x++)
								{
									if(ObjTarget.options[x].selected==true)
									{
										TempValues += "\u21BF\u21BE"+ObjTarget.options[x].value+"\u21C3\u21C2";
									}
								}
								if(TempValues != "")
								Value = TempValues;
							}
						}
						break;
					case "CHECKBOX":
						var TempValues = "";												
						if(ObjTarget.length)
						{
							for(var x=0;x<ObjTarget.length;x++)
							{
								if(ObjTarget[x].checked==true)
								{
									TempValues += "\u21BF\u21BE"+ObjTarget[x].value+"\u21C3\u21C2";
								}
							}
							if(TempValues != "")
							Value = TempValues;
						}
						else
						{
							if(ObjTarget.checked == true)
							Value = "True";
							else
							Value = "False";
						}

					break;
					case "RADIO":
						var TempValue = "null";
						for(var x=0;x<ObjTarget.length;x++)
						{
							if(ObjTarget[x].checked==true)
							{
								TempValue = ObjTarget[x].value;
								break;
							}
						}
						if(TempValue != "")
						Value = TempValue;
					break;
					case "TEXT":
					case "TEXTAREA":
						if(ObjTarget.className != "WaterMarkClass")
						Value = ObjTarget.value;
						else
						Value = '';						
					break;
					default:						
						Value = ObjTarget.value;
					break;
				}
			return MVJS.Trim(Value);
		}
		catch(e)
		{
			MVJS.ReportError(e);
		}		
	},
	ExecuteTimeoutScript:function(strScript)
	{
		try
		{
			eval(strScript)
		}
		catch(e)
		{
			/*Do Nothing*/
		}
	},
	SetTimeoutMessage:function(ControlID,Message,TimeoutDuration)
	{
		if(ControlID)
		{
			if(!Message)
			{
				Message = '';
			}
			if(!TimeoutDuration)
			{
				TimeoutDuration = 3000;
			}
			MVJS.SetValue(ControlID,Message);
			var strScript = "MVJS.ExecuteTimeoutScript(\"MVJS.SetValue('"+ControlID+"','');\");";
			setTimeout(strScript,TimeoutDuration);
		}
	},
	SetCSSClass : function(ControlID,CSSClassName){
		var objControl;
		if(typeof(ControlID)=='string')
		{
			objControl = document.getElementById(ControlID);
		}
		else
		{
			objControl = ControlID;
		}
		if(objControl)
		{
			if(CSSClassName)
			{
				objControl.className = CSSClassName;
			}
		}
	},
	GetCSSClass : function(ControlID){
		var objControl;
		if(typeof(ControlID)=='string')
		{
			objControl = document.getElementById(ControlID);
		}
		else
		{
			objControl = ControlID;
		}
		if(objControl)
		{
			return objControl.className;
		}
	},
	IsClipboardAccssible : function()
	{
		if(window.clipboardData && clipboardData.setData)
		{
			return true;
		}
		else
		{
			return false;
		}
	},
	CopyToClipboard : function(Source)
	{
		try
		{
			if(typeof(Source)!='string')
			{
				Source = this.GetValue(Source);
			}
			if(Source)
			{
				var clipboarddiv=document.getElementById('divclipboardswf');
				if(!clipboarddiv)
				{
					clipboarddiv=document.createElement('div');
					clipboarddiv.setAttribute('name','divclipboardswf');
					clipboarddiv.setAttribute('id','divclipboardswf');
					clipboarddiv.style.display='none';
					document.body.appendChild(clipboarddiv);
				}
				clipboarddiv.innerHTML='<embed src="themes/default/flash/Clipboard.swf" FlashVars="strData='+Source+'" width="200" height="200" type="application/x-shockwave-flash"></embed>';
				return true;
			}
		}
		catch(e)
		{
			/*Do Nothing*/
		}
	},
	Focus : function(ControlID){
		var objControl;
		if(typeof(ControlID)=='string')
		{
			objControl = document.getElementById(ControlID);
		}
		else
		{
			objControl = ControlID;
		}
		
		if(objControl)
		{
			try
			{
				objControl.focus();
			}
			catch(e)
			{
				/*Control Might be hidden*/
			}
		}
	},
	FillDropdown : function (ControlID,ItemArray,ClearExisting){ 
		
		var objControl;
		if(typeof(ControlID)=='string')
		{
			objControl = document.getElementById(ControlID);
		}
		else
		{
			objControl = ControlID;
		}
		
		if(!objControl) return;
		if(ClearExisting) objControl.options.length=0;
		if(typeof(ItemArray)=='string')
		{
			var TempArray = new Array();
			var objSourceControl = document.getElementById(ItemArray);
			for(var x=0; x < objSourceControl.options.length; x++)
			{
				TempArray[TempArray.length] = [objSourceControl.options[x].text,objSourceControl.options[x].value];
			}
			ItemArray = TempArray;
		}
		for(var x = 0; x < ItemArray.length; x++) {
		    var option = document.createElement('option');
		    try{
			ItemArray[x][0]=ItemArray[x][0].replace(/&rsquo;/g,"'");
			ItemArray[x][0]=ItemArray[x][0].replace(/&rdquo;/g,"\"");
			}catch(e){}
			
		    option.text=ItemArray[x][0];
		    option.value=ItemArray[x][1];
		    objControl.options[objControl.options.length]=option;
		}
	},
	GetGUID : function()
	{
		return ((((((1+Math.random())*0x10000)|0).toString(16).substring(1))+((((1+Math.random())*0x10000)|0).toString(16).substring(1))+"-"+((((1+Math.random())*0x10000)|0).toString(16).substring(1))+"-"+((((1+Math.random())*0x10000)|0).toString(16).substring(1))+"-"+((((1+Math.random())*0x10000)|0).toString(16).substring(1))+"-"+((((1+Math.random())*0x10000)|0).toString(16).substring(1))+((((1+Math.random())*0x10000)|0).toString(16).substring(1))+((((1+Math.random())*0x10000)|0).toString(16).substring(1))).toUpperCase());
	},
	GetItemsPacket : function(List)
	{
		try
		{
			var Ids="";
			for(var I=0;I<List.length;I++)
			{
				Ids +="\u21BF\u21BE"+List[I]+"\u21C3\u21C2";
			}
			return Ids;
		}
		catch(e)
		{
			MVJS.ReportError(e);
		}			
	},
	SortList : function(Source)
	{
		try
		{
			var Source=document.getElementById(Source);
			for(var x=0;x<Source.options.length;x++)
			{
				for(var y=x+1;y<Source.options.length;y++)		
				{
					if(Source.options[x].text > Source.options[y].text)
					{
						var Tmp = "";	
						Tmp = Source.options[x].value;
						Source.options[x].value = Source.options[y].value;
						Source.options[y].value = Tmp;
						Tmp = Source.options[x].text;
						Source.options[x].text = Source.options[y].text;
						Source.options[y].text = Tmp;
					}
				}
			}
		}
		catch(e)
		{
			MVJS.ReportError(e);
		}
	},
	FillForm : function(ItemsArray)
	{
		for(var x=0;x<ItemsArray.length;x++)
		{
			MVJS.SetValue(ItemsArray[x][0],ItemsArray[x][1]);
		}
	},
	SetFileTitle : function(FileInputId,TargetControlId)
	{
		var SourceValue = MVJS.GetValue(FileInputId);
		if((SourceValue.lastIndexOf('.') > 0))
		{
			if(SourceValue.lastIndexOf('\\') > 0)
			{
				SourceValue = SourceValue.substring(SourceValue.lastIndexOf('\\') + 1);
			}
			var Extension = SourceValue.substring(SourceValue.lastIndexOf('.') + 1);
			Extension = Extension.toLowerCase();
			var FileTitle = SourceValue.substring(0,(SourceValue.length - Extension.length - 1));
			MVJS.SetValue(TargetControlId,FileTitle);
		}
		else
		{
			return '';
		}
	},
	SetOptionText : function(ControlID,OptionId,OptionValue)
	{
		this.GetOptionText(ControlID,OptionId,OptionValue);
	},
	GetOptionText : function(ControlID,OptionValue,NewText)
	{
		try
		{
			var ObjControl;
			if(typeof(ControlID)=='string')
			{
				ObjControl= document.getElementById(ControlID);
			}
			else
			{
				ObjControl= ControlID;
			}
			if(!ObjControl || ObjControl.options.length == 0)
			{
				return null;
			}
			else
			{
				if(OptionValue)
				{
					for(var i=0; i<ObjControl.options.length; i++)
					{	
						if(ObjControl.options[i].value == OptionValue)
						{
							if(!NewText)
							{
								return ObjControl.options[i].text;
							}
							else
							{
								ObjControl.options[i].text=NewText;
							}
						}
					}
					return null;
					
				}
				else
				{
					if(!NewText)
					{
						return ObjControl.options[ObjControl.selectedIndex].text;
					}
					else
					{
						ObjControl.options[ObjControl.selectedIndex].text=NewText;
					}
				}
			}
		}
		catch(e)
		{
			MVJS.ReportError(e);
		}
	},
	TextAreaCounter:function(Field,CounterContainer,MaxLimit)
	{
		
		
		if(typeof(Field)=="string")
		{
			Field=document.getElementById(Field);
		}
		if(typeof(CounterContainer)=="string")
		{
			CounterContainer=document.getElementById(CounterContainer);
		}
		
		if (Field.value.length > MaxLimit)
		{
			Field.value = Field.value.substring(0, MaxLimit);
		}
		else
		{
			CounterContainer.innerHTML = Field.value.length + "/" + MaxLimit;
		}
		
	},
	StringFormat:function(strString,argsArray)
	{
		for(var x=0;x<argsArray.length;x++)
		{
			if(typeof(argsArray[x])=="undefined" || !argsArray[x])
			{
				argsArray[x]="";
			}
			
			try{strString=strString.replace(new RegExp( "\\{" + x + "\\}", "gi" ),argsArray[x]);}catch(e){}
		}
		return strString;		
	},	
	FormatString:function(strString,argsArray)
	{
		for(var x=0;x<argsArray.length;x++)
		{
			
			try{strString=strString.replace("{"+x+"}",argsArray[x]);}catch(e){}
		}
		return strString;
	},
	XMLSafeString:function(strString)
	{
		if(typeof(strString) == 'string')
		{
			while(strString.indexOf('&amp;') != -1)
			{
				strString=strString.replace(new RegExp('\&amp;', "gi" ),'&');
			}
			strString=strString.replace(new RegExp('\&quot;', "gi" ),'"');
			strString=strString.replace(new RegExp('\&lt;', "gi" ),'<');
			strString=strString.replace(new RegExp('\&gt;', "gi" ),'>');
			strString=strString.replace(new RegExp('\&#39;', "gi" ),"'");
			strString=strString.replace(new RegExp('amp;', "gi" ),'&');
			strString=strString.replace(new RegExp('quot;', "gi" ),'"');
			strString=strString.replace(new RegExp('lt;', "gi" ),'<');
			strString=strString.replace(new RegExp('gt;', "gi" ),'>');
			strString=strString.replace(new RegExp('#39;', "gi" ),"'");
			return strString;
		}
		else
		{
			return strString;
		}
	},
	CheckAll : function(ControlName,Value){
		var Elements=document.getElementsByName(ControlName);
		if(!Elements)
		{
			return;
		}
		for(var x=0;x<Elements.length;x++)
		{
			Elements[x].checked=Value;
		}
	},

	Enable : function (ControlID) {	
		try
		{
			if(typeof(ControlID)=='string' && !document.getElementById(ControlID) || !ControlID)		
			{
				return;
			}
			MVJS.StyleHandler.SetEnable(ControlID,true);
		}
		catch(e)
		{
			/*DO NOTHING*/
		}
	},

	Disable : function (ControlID) {
		try
		{
			if(typeof(ControlID)=='string' && !document.getElementById(ControlID) || !ControlID)	
			{
				return;
			}
			MVJS.StyleHandler.SetEnable(ControlID,false);
		}
		catch(e)
		{
			/*DO NOTHING*/
		}
	},

	Show : function (ControlID) {
		try
		{
			if(typeof(ControlID)=='string' && !document.getElementById(ControlID) || !ControlID)	
			{
				return;
			}
			MVJS.StyleHandler.SetVisible(ControlID,true);
		}
		catch(e)
		{
			/*DO NOTHING*/
		}
	},

	Hide : function (ControlID) {
		try
		{
			if(typeof(ControlID)=='string' && !document.getElementById(ControlID) || !ControlID)	
			{
				return;
			}
			MVJS.StyleHandler.SetVisible(ControlID,false);
		}
		catch(e)
		{
			/*DO NOTHING*/
		}
	},
	ShowTimeoutLogin : function (PopupControlID)
	{
		if(typeof(PopupControlID) == "string" )
		{
			this.TimeoutLoginPop = document.getElementById(PopupControlID);
		}
		else
		{
			this.TimeoutLoginPop=PopupControlID;
		}
		if(!this.TimeoutLoginBk)
		{
			this.TimeoutLoginBk = document.getElementById('TimeoutLoginSelectBuster');						
			this.TimeoutLoginBk.style.top=0;
			this.TimeoutLoginBk.style.left=0;
			MVJS.StyleHandler.SetProperty(this.TimeoutLoginBk,"backgroundColor","#EAF4FF");
			MVJS.StyleHandler.SetOpacity(this.TimeoutLoginBk,5);
		}
		this.TimeoutLoginBk.style.height = "100%";
		this.TimeoutLoginBk.style.width = "100%";
		this.TimeoutLoginBk.style.display="block";
		MVJS.StyleHandler.SetProperty(this.TimeoutLoginBk,"zIndex",2000+MVJS.PopupZIndex); 
		MVJS.StyleHandler.SetProperty(this.TimeoutLoginPop,"zIndex",2001+MVJS.PopupZIndex); 				
		var left = parseInt(screen.availWidth)/2 -150;
		var top = parseInt(screen.availHeight)/2 -100;
		MVJS.CoordinatesHandler.MoveControl(this.TimeoutLoginPop, "LeftTop", left, top,null,false);
		MVJS.Show(this.TimeoutLoginPop);
		
	},
	HideTimeoutLogin : function ()
	{
		this.TimeoutLoginBk.style.height = 0;
		this.TimeoutLoginBk.style.width = 0;
		MVJS.Hide(this.TimeoutLoginBk);
		MVJS.Hide(this.TimeoutLoginPop);
	},
	ShowPopup : function(PopupControlID,ShowModal,ParentControlID,Anchor,ParentAnchor,CloseCallBack,ScrollTop,Level){
		
		if(!CloseCallBack) CloseCallBack= null;
		
		if(ScrollTop)
		MVJS.ScrollTo(0,0);
		
		if(MVJS.IsIE() && document.getElementById('MVNuggets_PageOuterDiv'))
		{
			document.getElementById('MVNuggets_PageOuterDiv').style.overflow = "hidden";
		}
		if(MVJS.IsFF2())
		{
			var Pop = document.getElementById(PopupControlID);
			if(!Pop) return false;
			Pop.className="OverFlowClass";
			if(!Pop.lang || Pop.lang!="Fixed")
			{
				Pop.style.height=parseInt(Pop.style.height)+150;
				Pop.lang="Fixed";
			}
		}
		
		if(MVJS.PopupWindows==null)
		{
			MVJS.PopupWindows=new Array();
			MVJS.PopupBackgrounds=new Array();
			MVJS.PopupBackgroundsTop=new Array();
			MVJS.PopupCloseCallBacks=new Array();
		}
		
		MVJS.PopupCloseCallBacks[MVJS.PopupCloseCallBacks.length]= CloseCallBack;
		
		var PopupWindow=document.getElementById(PopupControlID);
		if(!PopupWindow) return false;
		if(MVJS.PopupWindows.length>0)
		{
			for(var i=0;i<MVJS.PopupWindows.length;i++)
			{
				if(MVJS.PopupWindows[i]==PopupWindow)
				return;
			}
		}
		
		if(Anchor==null) Anchor="CenterCenter";
		if(ParentAnchor==null) ParentAnchor="CenterCenter";		
		if(ShowModal==null) ShowModal=true;
		var divBackground;
		if(ShowModal)
		{
			divBackground=document.createElement("IFRAME");

			var divBackgroundTop=document.createElement("div");
			document.body.appendChild(divBackground);
			document.body.appendChild(divBackgroundTop);
			divBackground.className = "SelectBuster";
			divBackgroundTop.className = "SelectBuster";
			divBackground.style.height = "100%";
			divBackground.style.width = "100%";
			divBackground.style.display="block";
			divBackgroundTop.style.height = "100%";
			divBackgroundTop.style.width = "100%";
			divBackgroundTop.style.display="block";



			var BkTOp = 0;
			if(MVJS.IsFireFox())
			{
				BkTOp = screen.top/2 //parseInt(MVJS.Nuggets.TopMenu_Main.TabPageTop);
				divBackground.style.width = parseInt(divBackground.offsetWidth) + 10;
				divBackground.style.height = parseInt(divBackground.offsetHeight) - BkTOp;			

				divBackgroundTop.style.height = parseInt(divBackgroundTop.offsetHeight) - BkTOp;
			}

			if(MVJS.IsIE())
			{
				/***divBackground.style.width = parseInt(divBackground.offsetWidth) + 20;***/
				/***(BkTOp=parseInt(MVJS.Nuggets.TopMenu_Main.ScrollTop);**/
				var ObjTabPageContainer =document.getElementById('MVNuggets_TabPageContianer');
				if(ObjTabPageContainer && ObjTabPageContainer.childNodes.length>1)
				{
					var InnerDiv = ObjTabPageContainer.childNodes[1];			
					divBackground.style.height=parseInt(InnerDiv.offsetHeight);
				}
			}

			if(MVJS.IsIE())
			{

				if(parseInt(divBackground.style.height)<400) divBackground.style.height="630";
				if(parseInt(divBackgroundTop.style.height)<400) divBackgroundTop.style.height="630";
			}

			MVJS.StyleHandler.SetPosition(divBackground,0,BkTOp);
			MVJS.StyleHandler.SetPosition(divBackgroundTop,0,BkTOp);

			MVJS.StyleHandler.SetProperty(divBackground,"backgroundColor","#EAF4FF");
			MVJS.StyleHandler.SetProperty(divBackgroundTop,"backgroundColor","#EAF4FF");

			if(!ShowModal)
			{
				MVJS.StyleHandler.SetOpacity(divBackground,0);
				MVJS.StyleHandler.SetOpacity(divBackgroundTop,0);
			}
			else
			{
				MVJS.StyleHandler.SetOpacity(divBackground,5);
				MVJS.StyleHandler.SetOpacity(divBackgroundTop,5);
			}		
			MVJS.StyleHandler.SetProperty(divBackground,"position","absolute");
			MVJS.StyleHandler.SetProperty(divBackgroundTop,"position","absolute");		
			MVJS.PopupZIndex++;
			MVJS.StyleHandler.SetProperty(divBackground,"zIndex",1000+MVJS.PopupZIndex);
			MVJS.PopupZIndex++;		
			MVJS.StyleHandler.SetProperty(divBackgroundTop,"zIndex",1000+MVJS.PopupZIndex);
			MVJS.PopupBackgrounds[MVJS.PopupBackgrounds.length]=divBackground;
			MVJS.PopupBackgroundsTop[MVJS.PopupBackgroundsTop.length]=divBackgroundTop;
		
			if(ParentControlID)
			{
				var ParentControl;
				if(typeof(ParentControlID)=="string")
				{
					ParentControl=document.getElementById(ParentControlID);
				}
				else
				{
					ParentControl=ParentControlID;
				}
				if(ParentControl.tagName=="DIV")
				{
					ParentControl.appendChild(divBackground);
					ParentControl.appendChild(divBackgroundTop);
				}
				else
				{
					ParentControl.parentNode.appendChild(divBackground);
					ParentControl.parentNode.appendChild(divBackgroundTop);
				}
			}
			else
			{
				//MVJS.Nuggets.TopMenu_Main.Tabs.GetTabPage(MVJS.GetActiveTabID()).appendChild(divBackground);	
				//MVJS.Nuggets.TopMenu_Main.Tabs.GetTabPage(MVJS.GetActiveTabID()).appendChild(divBackgroundTop);
			}		
		}
		else
		{
			MVJS.PopupBackgrounds[MVJS.PopupBackgrounds.length]=null;
			MVJS.PopupBackgroundsTop[MVJS.PopupBackgroundsTop.length]=null;
		}
		
		MVJS.PopupZIndex++;		
		MVJS.StyleHandler.SetProperty(PopupControlID,"zIndex",1000+MVJS.PopupZIndex); 
		/*** Popup alignment to Center Center **/
		var Coords=MVJS.CoordinatesHandler.GetCoordsByControlID(ParentControlID,ParentAnchor,false);
		
		if(MVJS.IsIE())
		{
		    
			Coords.Y = parseInt(screen.height/4);//Coords.Y - (MVJS.Nuggets.TopMenu_Main.TabPageTop)/2;		
		}
		else
		{
			Coords.Y = parseInt(screen.height/3);//Coords.Y + (MVJS.Nuggets.TopMenu_Main.TabPageTop)/2;		
		}
		MVJS.CoordinatesHandler.MoveControl(PopupControlID, Anchor, Coords.X, Coords.Y,null,false);
		/*** Popup alignment to Center Center **/
		
		MVJS.StyleHandler.SetVisible(PopupControlID,true);	
		
		if(MVJS.IsIE())
		{
			/** Scroll handling for IE ***/
			//PopupWindow.style.top=parseInt(PopupWindow.style.top) + MVJS.Nuggets.TopMenu_Main.ScrollTop;
			PopupWindow.style.top=parseInt(PopupWindow.style.top) + 100;
		}		
		
		MVJS.PopupWindows[MVJS.PopupWindows.length]=PopupWindow;		
		
		var InnerDivs=PopupWindow.getElementsByTagName("div");
		var divTitle;
		
		for(var x=0;x<InnerDivs.length;x++)
		{
			if(InnerDivs[x].className=="Comp_Middle")
			{
				divTitle=InnerDivs[x];
				break;
			}
			if(InnerDivs[x].className=="Comp_MainDiv")
			{
				InnerDivs[x].className='Comp_MainDivPopup';
				break;
			}
		}
		
	
		for(var x=0;x<InnerDivs.length;x++)
		{
			if(InnerDivs[x].className=="Comp_MainDiv")
			{
				InnerDivs[x].className='Comp_MainDivPopup';
				break;
			}
		}
		

		if(divTitle)
		{
			var divCaption=document.createElement("div");
			divCaption.innerHTML=divTitle.innerHTML;
			divCaption.className="InlineDiv";
			divTitle.innerHTML="";
			divTitle.appendChild(divCaption);
			var divClose=document.createElement("div");
			divClose.className="InlineDivRight";
			divClose.style.paddingTop="2px";
			divClose.style.paddingRight="2px";
			var imgClose=document.createElement("img");
			imgClose.border=0;
			imgClose.className="ImagePointerClass";
			if(Level)
			{
			    imgClose.src=Level+"images/dialog_close.gif";
            }
            else
            {
			    imgClose.src="images/dialog_close.gif";                
            }			    
			divClose.appendChild(imgClose);
			divClose.onclick=function(){
				MVJS.HidePopup();
			};
			divTitle.style.cursor="move";
			divClose.style.cursor="default";
			divTitle.appendChild(divClose);
			MVJS.PopupTitle=divTitle;
			MVJS.EventHandler().AttachEvent(document.body,"mousemove",MVJS.PopupMove);
			MVJS.EventHandler().AttachEvent(document.body,"mousedown",MVJS.PopupMouseDown);
			MVJS.EventHandler().AttachEvent(document.body,"mouseup",MVJS.PopupMouseUp);	
			
		}
		
	},
	PopupMouseDown:function(event)
	{
		var SourceElement=(window.event)?window.event.srcElement:event.target;
		if(SourceElement==MVJS.PopupTitle)
		{
			MVJS.PopupClickX=(window.event)?window.event.x:event.pageX;
			MVJS.PopupClickY=(window.event)?window.event.y:event.pageY;
			MVJS.PopupDragging=true;
		}
	},
	PopupMouseUp:function(event)
	{		
		MVJS.PopupDragging=false;
	},
	PopupMove:function(event){
		if(MVJS.PopupDragging)
		{
			var PopupWindow=MVJS.PopupWindows[MVJS.PopupWindows.length-1];
			
			var maxY= document.getElementById("MVNuggets_PageOuterDiv").offsetHeight - MVJS.StyleHandler.GetHeight(PopupWindow) - 40;		
			var maxX=  document.getElementById("MVNuggets_PageOuterDiv").offsetWidth - MVJS.StyleHandler.GetWidth(PopupWindow);
			var yStart;
			if(MVJS.IsIE())
			{
				yStart =0;
				
			}
			else
			{
				yStart = parseInt(MVJS.Nuggets.TopMenu_Main.TabPageTop);
				maxY +=parseInt(MVJS.Nuggets.TopMenu_Main.TabPageTop);
			}				
		
			
			var MouseX=(window.event)?window.event.x:event.pageX;
			var MouseY=(window.event)?window.event.y:event.pageY;			
			var Coords=MVJS.CoordinatesHandler.GetCoordsByControlID(PopupWindow);
			var xCoords = MouseX+Coords.X-MVJS.PopupClickX;
			var yCoords = MouseY+Coords.Y-MVJS.PopupClickY;
			
			
			if(xCoords >0 && xCoords < maxX && yCoords > yStart && yCoords < maxY)
			{
				MVJS.CoordinatesHandler.MoveControl(PopupWindow,"LeftTop",xCoords,yCoords);
				MVJS.PopupClickX=MouseX;
				MVJS.PopupClickY=MouseY;
			}
		}	
	},
	HidePopup : function(){
	
		if(!MVJS.PopupWindows || MVJS.PopupWindows.length==0 ) return;
		if(!MVJS.PopupWindows[MVJS.PopupWindows.length-1]) return;
		if(MVJS.IsIE() && document.getElementById('MVNuggets_PageOuterDiv'))
		{
			document.getElementById('MVNuggets_PageOuterDiv').style.overflow = "";
		}
		MVJS.EventHandler().RemoveEvent(document.body,"mousemove",MVJS.PopupMove);
		MVJS.EventHandler().RemoveEvent(document.body,"mousedown",MVJS.PopupMouseDown);
		MVJS.EventHandler().RemoveEvent(document.body,"mouseup",MVJS.PopupMouseUp);
		var PopupWindow=MVJS.PopupWindows[MVJS.PopupWindows.length-1];
		var InnerDivs=PopupWindow.getElementsByTagName("div");
		var divTitle = null;
		for(var x=0;x<InnerDivs.length;x++)
		{
			if(InnerDivs[x].className=="Comp_Middle")
			{
				divTitle=InnerDivs[x];
				break;
			}
		}
		if(divTitle) divTitle.removeChild(divTitle.childNodes[divTitle.childNodes.length-1]);
		
		
		if(MVJS.PopupBackgrounds && MVJS.PopupBackgrounds.length!=0)
		{
			if(MVJS.PopupBackgrounds[MVJS.PopupBackgrounds.length-1] && MVJS.PopupBackgrounds[MVJS.PopupBackgrounds.length-1].parentNode && MVJS.PopupBackgroundsTop[MVJS.PopupBackgroundsTop.length-1] && MVJS.PopupBackgroundsTop[MVJS.PopupBackgroundsTop.length-1].parentNode)
			{
		
				MVJS.PopupBackgrounds[MVJS.PopupBackgrounds.length-1].parentNode.removeChild(MVJS.PopupBackgrounds[MVJS.PopupBackgrounds.length-1]);
				MVJS.PopupBackgroundsTop[MVJS.PopupBackgroundsTop.length-1].parentNode.removeChild(MVJS.PopupBackgroundsTop[MVJS.PopupBackgroundsTop.length-1]);
			}
		}
		MVJS.StyleHandler.SetVisible(MVJS.PopupWindows[MVJS.PopupWindows.length-1],false); 
		var objCallback;
		if(MVJS.PopupCloseCallBacks[MVJS.PopupCloseCallBacks.length-1])
		{
			objCallback = MVJS.PopupCloseCallBacks[MVJS.PopupCloseCallBacks.length-1];	
		}
		MVJS.PopupWindows.length-=1;
		MVJS.PopupBackgrounds.length-=1;
		MVJS.PopupBackgroundsTop.length-=1;
		MVJS.PopupCloseCallBacks.length-=1;		
		if(this.IsIE() && document.getElementById('AddressBar')) document.getElementById('AddressBar').focus();
		if(objCallback) objCallback();
	},

	Trim : function(SourceString){
		if(SourceString==null) return '';
		if(typeof(SourceString) != 'string')
		{
			SourceString = SourceString.toString();
		}
		try{
			return SourceString.replace(/^\s+|\s+$/g,"");
		}catch(e){return SourceString;}
		
	},
	LoadPage:function(TabID,TabCaption,Components,PersistExisting,LayoutTemplate,IsOldPage,CombineLoadingNugget,CombineLoadingParams)
	{
		MVJS.ScrollTo(0,0);
		var LeftColumnContents="";
		var MidColumnContents="";
		var RightColumnContents="";
		
		var TabPage;
		if(MVJS.ForceNewTab)
		{
			TabID=TabID+parseInt(Math.random()*100);
			TabPage=MVJS.Nuggets.TopMenu_Main.Tabs.GetTabPage(TabID);
			MVJS.Nuggets.TopMenu_Main.Tabs.AddTab(TabID,TabCaption);
			
			MVJS.ForceNewTab=false;
		}
		
		
		if(!PersistExisting)
		{
			if(!LayoutTemplate)
			{
				if(Components.Mid)
				{
					if(Components.Left)
					{
						if(Components.Right)
						{
							if(Components.Top)
							{
								LayoutTemplate=MVJS.ThreeColumnWithTopLayout;
							}
							else
							{
								LayoutTemplate=MVJS.ThreeColumnLayout;
							}
						}
						else
						{
							LayoutTemplate=MVJS.TwoColumnLayout;
						}
					}
					else
					{
						if(Components.Top)
						{
							LayoutTemplate=MVJS.OneColumnTopLayout;
						}
						else
						{
							LayoutTemplate=MVJS.OneColumnLayout;
						}
					}
				}
				
			}
			else
			{
				this.FreeNuggets(TabID+"_MidContainer");
			}
			
			TabPage=MVJS.Nuggets.TopMenu_Main.Tabs.GetTabPage(TabID);
			
			if(!TabPage)
			{
				MVJS.Nuggets.TopMenu_Main.Tabs.AddTab(TabID,TabCaption);
				setTimeout(function () {MVJS.LoadPage(TabID,TabCaption,Components,PersistExisting,LayoutTemplate,IsOldPage,CombineLoadingNugget,CombineLoadingParams);},100);
				return;
			}
			else
			{
				MVJS.Nuggets.TopMenu_Main.Tabs.SetCaption(TabID,TabCaption);
			}
			
			var Layout=MVJS.StringFormat(LayoutTemplate,[TabID]);
			
			TabPage.innerHTML=Layout;
			
			if(!document.getElementById("divLoadingPopup_"+TabID))
			{
				var LoadingTemplate="<div id='divLoadingPopup_"+TabID+"' style='display:none;'><p align='center' class='MainCompHeading'>Please Wait...<br><br><img src='themes/default/images/Explorer/loading.gif' alt='' style='border:0px;'/></p></div>";
				var LoadingLayout=MVJS.StringFormat(LoadingTemplate,[TabID]);
				TabPage.innerHTML+=LoadingLayout;
			}
		}

		
		
		if(Components.Top)
		{
			
			for(var x=0;x<Components.Top.length;x++)
			{
				if(Components.Top[x].IsJSON)
				{
					this.LoadJSONNugget(TabID,TabID+"_TopContainer",Components.Top[x].NuggetName,x==0,Components.Top[x].Parameters);
				}
				else
				{
					this.LoadSingleNugget(TabID,TabID+"_TopContainer",Components.Top[x].NuggetName,x==0,Components.Top[x].Parameters,Components.Top[x].SkinClassName,CombineLoadingNugget);
				}
			}
			
			
		}
		if(Components.Mid)
		{
			
			for(var x=0;x<Components.Mid.length;x++)
			{
				if(Components.Mid[x].IsJSON)
				{
					this.LoadJSONNugget(TabID,TabID+"_MidContainer",Components.Mid[x].NuggetName,x==0,Components.Mid[x].Parameters);
				}
				else
				{
					this.LoadSingleNugget(TabID,TabID+"_MidContainer",Components.Mid[x].NuggetName,x==0,Components.Mid[x].Parameters,Components.Mid[x].SkinClassName,CombineLoadingNugget);
				}
			}
		}
		if(Components.Left)
		{
			
			for(var x=0;x<Components.Left.length;x++)
			{
				if(Components.Left[x].IsJSON)
				{
					this.LoadJSONNugget(TabID,TabID+"_LeftContainer",Components.Left[x].NuggetName,x==0,Components.Left[x].Parameters);
				}
				else
				{
					this.LoadSingleNugget(TabID,TabID+"_LeftContainer",Components.Left[x].NuggetName,x==0,Components.Left[x].Parameters,Components.Left[x].SkinClassName,CombineLoadingNugget);
				}
			}
		}
		if(Components.Right)
		{
			
			for(var x=0;x<Components.Right.length;x++)
			{
				if(Components.Right[x].IsJSON)
				{
					this.LoadJSONNugget(TabID,TabID+"_RightContainer",Components.Right[x].NuggetName,x==0,Components.Right[x].Parameters);
				}
				else
				{
					this.LoadSingleNugget(TabID,TabID+"_RightContainer",Components.Right[x].NuggetName,x==0,Components.Right[x].Parameters,Components.Right[x].SkinClassName,CombineLoadingNugget);
				}
			}
		}
		else
		{		
			var RightContainer=document.getElementById(TabID+"_RightContainer");
			if(RightContainer)
			{
				this.FreeNuggets(RightContainer);
				RightContainer.parentNode.removeChild(RightContainer);
			}
		}
		if(CombineLoadingNugget)
		{
			var Output="";
			Output+="var Obj"+CombineLoadingNugget +"Client=MVCore.NuggetClient();";
			Output+="var Obj"+CombineLoadingNugget+"Packet=MVCore.NuggetPacket('WebServices/"+CombineLoadingNugget+"Service','Load');";
			
			Output+="Obj"+CombineLoadingNugget+"Packet.TabID=\""+TabID+"\";";

			if(CombineLoadingParams)
			{
				for(var x=0;x<CombineLoadingParams.length;x++)
					Output+="Obj"+CombineLoadingNugget+"Packet.Parameters.Add(MVCore.NuggetParameter('"+this.JavascriptSafeString(CombineLoadingParams[x][0],1,0)+"','"+this.JavascriptSafeString(CombineLoadingParams[x][1],1,0)+"'));";
			}

			Output+="Obj"+CombineLoadingNugget+"Client.Invoke(Obj"+CombineLoadingNugget+"Packet);";
			eval(Output);
		}
		if(!IsOldPage) MVJS.Nuggets.TopMenu_Main.Tabs.AddPage(TabID,TabCaption,Components,PersistExisting,LayoutTemplate,CombineLoadingNugget,CombineLoadingParams);
	},
	GetColumnID:function(Column)
	{
		return MVJS.Nuggets.TopMenu_Main.Tabs.Tabs[MVJS.Nuggets.TopMenu_Main.Tabs.ActiveTabIndex].TabID+"_"+Column+"Container";
	},
	LoadJSONNugget: function(TabID,ContainerID,NuggetName,ClearContents,CallBackFunction)
	{

		var NuggetDiv=NuggetName;
		if(NuggetDiv.indexOf("/")>=0) NuggetDiv=NuggetDiv.substring(NuggetDiv.lastIndexOf("/")+1);
		
		eval("MVJS.JSON."+NuggetDiv+"=null;");
		eval("MVJS.JSON._"+NuggetDiv+"=null;");
		eval("MVJS.Nuggets."+NuggetDiv+"=null;");
		
		var divNugget=this.CreateNuggetDiv(TabID,ContainerID,NuggetDiv,ClearContents);
		var divInnerContainer=document.createElement("div");
		divInnerContainer.id=NuggetDiv+"_"+TabID+"_JSONContainer";
		divNugget.appendChild(divInnerContainer);
		var ObjScript=document.createElement("script");
		if(!CallBackFunction)
		{
			CallBackFunction="";
		}
		
		ObjScript.src=MVCore.JSONServiceURL+NuggetName+".ashx?proxy&TabID="+TabID+"&CallBackFunction="+CallBackFunction;
		divNugget.appendChild(ObjScript);
	},
	CreateNuggetDiv:function(TabID,ContainerID,NuggetName,ClearContents)
	{
		var Container=null;
		
		if(ClearContents)
		{
			var oldContainer=document.getElementById(ContainerID);
			if(oldContainer)
			{
				this.FreeNuggets(oldContainer);
				Container=oldContainer.cloneNode(false);
				oldContainer.parentNode.replaceChild(Container,oldContainer);
			}			
		}
		else
		{
			if(typeof(ContainerID)=='string')
			{
				Container=document.getElementById(ContainerID);
			}
			else
			{
				Container=ContainerID;
			}			
		}
		if(!Container) return false;
		var divNugget=document.createElement("div");
		var NuggetDivId="divMVNuggets_"+NuggetName+"_"+TabID;
		divNugget.id=NuggetDivId;
		

		Container.appendChild(divNugget);
		return divNugget;
	},
	LoadSingleNugget : function(TabID,ContainerID,NuggetName,ClearContents,arrParams,SkinClassName,ActualLoad){
		
		var divNugget=this.CreateNuggetDiv(TabID,ContainerID,NuggetName,ClearContents);
		if(ActualLoad==null)
		{
			MVCore.LoadCalls++;
			if(MVJS.Nuggets.TopMenu_Main && MVCore.LoadCalls==1)
			{
				MVJS.ShowPopup("divLoadingPopup_"+TabID);
			}
			eval("Obj"+NuggetName +"Client=MVCore.NuggetClient();");
			
			var ObjClient=eval("Obj"+NuggetName +"Client");	
			var ObjPacket=MVCore.NuggetPacket("WebServices/"+NuggetName+"Service","Load");
			
			if(SkinClassName) ObjPacket.SkinClass=SkinClassName;
			ObjPacket.TabID=TabID;
			if(arrParams)
			{
				for(var x=0;x<arrParams.length;x++)
					ObjPacket.Parameters.Add(MVCore.NuggetParameter(arrParams[x][0],arrParams[x][1]));
			}
			ObjClient.Invoke(ObjPacket);
		}
		
	},
	
	UnloadNugget : function(NuggetName,TabID){
		if(!TabID)
		{
			TabID=MVJS.GetActiveTabID();
		}
		var divNugget=document.getElementById("divMVNuggets_"+NuggetName+"_"+TabID);
		if(divNugget==null) return false;
		divNugget.innerHTML = "";
		divNugget.parentNode.removeChild(divNugget);
		eval("delete MVJS.Nuggets."+NuggetName+"_"+TabID+";");
	},
	ReportError : function(e,NuggetName,MethodName,MethodArguments){
	

			var Message="An Error Has Occured\n";
			Message+="\nError Type:\n" + e.name + "\n";
			Message+="\nError Description:\n" + e.message + "\n";
			if(NuggetName && NuggetName==='DE')
			{
				Message+="\nError While Executing Script\n";
			}
			if(e.stack) Message+="\nError Stack:\n" + e.stack + "\n";
			alert(Message);

			
		/*Do not delete the try catch block */
			try
			{
				if(typeof(NuggetName)=="undefined")
				{
					NuggetName='';
				}
				if(typeof(MethodName)=="undefined")
				{
					MethodName='';
				}
				if(typeof(MethodArguments)=="undefined")
				{
					MethodArguments=[];
				}
				MVJS.Nuggets.TopMenu_Main.LogError(e,NuggetName,MethodName,MethodArguments);
			}
			catch(e)
			{
				/* DO NOTHING  as error could occour before loading top menu */
			}
		
	},
	ChangeTheme : function(ThemeID)
	{    
		var ObjLinks=document.getElementsByTagName("link");

		for(var x=0;x<ObjLinks.length;x++)
		{
			var Style=ObjLinks[x].href;
			var RootStyle=Style.substring(Style.lastIndexOf("/"));
			Style="themes/"+ThemeID + "/stylesheets"+ RootStyle;
			ObjLinks[x].href=Style;
		}
	},
	GetActiveTabID:function()
	{
		return MVJS.Nuggets.TopMenu_Main.Tabs.GetActiveTabID();
	},
	IsTabExist:function (TabID)
	{
		if(TabID && MVJS.Nuggets.TopMenu_Main.Tabs.GetTabIndex(TabID)!=-1)
		{
			return true;
		}
		return false;
	},
	Invoke:function(NuggetName,MethodName,Parameters,SkinClassName,TabID)
	{
		var ObjPacket=MVCore.NuggetPacket("WebServices/"+NuggetName+"Service",MethodName);
		ObjPacket.TabID=TabID;
		if(SkinClassName) ObjPacket.SkinClass=SkinClassName;
		if(Parameters)
		{
			for(var x=0;x<Parameters.length;x++)
			{
				ObjPacket.Parameters.Add(MVCore.NuggetParameter(Parameters[x][0],Parameters[x][1]));	
			}
		}
		var ObjClient = null;
		try
		{
			ObjClient=eval("Obj"+NuggetName+"Client");
		}
		catch(e)
		{
		}
		if(!ObjClient)
		{
			ObjClient=MVCore.NuggetClient();
		}
		ObjClient.Invoke(ObjPacket);
	},
	ClearControls:function(Controls)
	{
		if(!Controls) return false;
		for(var x=0;x<Controls.length;x++)
		{
			MVJS.SetValue(Controls[x],"");
		}
	},
	ClearErrors:function(Container)
	{
		if(!Container) return false;
		if(typeof(Container)=="string") Container=document.getElementById(Container);
		var Controls=Container.getElementsByTagName("div");
		for(var x=0;x<Controls.length;x++)
		{
			if(Controls[x].className=="Error")
			{
				MVJS.SetValue(Controls[x],"");
			}
		}
		Controls=Container.getElementsByTagName("span");
		for(var x=0;x<Controls.length;x++)
		{
			if(Controls[x].className=="Error" && Controls[x].id)
			{
				MVJS.SetValue(Controls[x],"");
			}
		}
	},
	FillListGen:function(List,RowsArray,ItemTemplate)
	{
		var ListArray=[];		
		for(var x=0;x<RowsArray.length;x++)
		{
			ListArray[ListArray.length]=[];			
			ListArray[ListArray.length-1][0]=RowsArray[x][0];
			if(typeof(ItemTemplate)=="string")
			{
				ListArray[ListArray.length-1][1]=MVJS.StringFormat(ItemTemplate,RowsArray[x]);
			}
			else
			{
				ListArray[ListArray.length-1][1]=MVJS.StringFormat(ItemTemplate[x],RowsArray[x]);
			}
		}
		List.FillRows(ListArray);
	},
	ToAscii:function (Value){
		var symbols = " !\"#$%&'()*+'-./0123456789:;<=>?@";
		var loAZ = "abcdefghijklmnopqrstuvwxyz";
		symbols+= loAZ.toUpperCase();
		symbols+= "[\\]^_`";
		symbols+= loAZ;
		symbols+= "{|}~";
		var loc= symbols.indexOf(Value);
		if (loc >-1) {			
			return (32 + loc);
	   	}
		return(0);  /** If not in range 32-126 return ZERO ***/
	},
	JSSafeString : function (StrValue,JsOutput,NoBr){
		if(JsOutput==null)
		{
			JsOutput = true;
		}
		
		if(!JsOutput)
		{	
			try{
			StrValue=StrValue.replace(new RegExp( "\'", "gi" ),"\\'");
			StrValue=StrValue.replace(new RegExp( '\"', "gi" ),'\\"');			
			}catch(e){}
		}
		if(!NoBr)
		{
			try {return StrValue.replace(new RegExp( "\\n", "gi" ),'<br>');	}catch(e){return StrValue;}
		}
	
		
	},
	JavascriptSafeString : function (StrValue,SQDepth,DQDepth,HandleNewLine,IsHyperlinkOutput)
	{
		try
		{
			if(!StrValue) return;
			if(!SQDepth)SQDepth=0;
			if(!DQDepth)DQDepth=0;
			var StrTemp='';
			StrValue=""+StrValue+"";
			StrValue=StrValue.replace(/\\/,"\\\\");
			if(SQDepth >0)
			{
				
				for(var j=0;j<SQDepth;j++)
				{
					StrTemp +='\\';
				}
				if(IsHyperlinkOutput)
				{
					StrTemp +='%27';
				}
				else
				{	
					StrTemp +="'";
				}
				StrValue=StrValue.replace(new RegExp( "'", "gi" ),StrTemp);
			}
			if(DQDepth >0)
			{
				
				StrTemp="";
				for(var j=0;j<DQDepth;j++)
				{
					StrTemp +='\\';
				}
				if(IsHyperlinkOutput)
				{
					StrTemp +='%22';
				}
				else
				{						
					StrTemp +='"';
				}

				StrValue=StrValue.replace(new RegExp( '"', "gi" ),StrTemp);
			}
			
			if(HandleNewLine)
			{
				StrValue=StrValue.replace(new RegExp( "\n", "gi" ),'<br>');	
			}

			return StrValue;
		}
		catch(e)
		{
			return StrValue;
		}
	},
	NewTabOpenHandler:function(evt){
		if(window.event)
		evt = window.event;
		var SourceElement=(window.event)?window.event.srcElement:evt.target;
		if(SourceElement.nodeName == "A")
		{
			var CtrlPressed=evt.ctrlKey;
			var ShiftPressed=evt.shiftKey;
			if(CtrlPressed || ShiftPressed)
			{
				if (evt.stopPropagation){
					evt.stopPropagation();
					evt.preventDefault();
				}else if(typeof evt.cancelBubble != "undefined"){
					evt.cancelBubble = true;
					evt.returnValue = false;
				}				
				MVJS.ForceNewTab=true;
				var CallCode=SourceElement.href;
				try{CallCode=CallCode.replace("javascript:","");}catch(e){}
				eval(CallCode);
				return false;
			}
			else
			{
				MVJS.ForceNewTab=false;
			}
		}
	},
	NumericOnly:function(evt)
	{
	         var charCode = (evt.which) ? evt.which : event.keyCode;
	         if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode !=46)
	            return false;
	
	         return true;
      	},
      	IsArray : function (obj) {
	  return (obj.constructor.toString().indexOf("Array") == -1)?false : true;	      
	},
	GetCheckboxArray:function(ChkGroupName)
	{
		try
		{
			var Result = "";
			var ChkElements = document.getElementsByName(ChkGroupName);
			for(var i=0; i<ChkElements.length; i++)
			{
				if(ChkElements[i].checked == true)
					Result += '1';
				else
					Result += '0';
			}
			return Result;
		}
		catch(e)
		{
			MVJS.ReportError(e);
		}


	},
	SetCheckboxArray:function(ChkGroupName,jsBooleanArray)
	{
		try
		{
			var ChkElements = document.getElementsByName(ChkGroupName);
			for(var i=0; i<ChkElements.length; i++)
			{
				ChkElements[i].checked = jsBooleanArray[i];
			}
		}
		catch(e)
		{
			MVJS.ReportError(e);
		}
	}      	
	
};



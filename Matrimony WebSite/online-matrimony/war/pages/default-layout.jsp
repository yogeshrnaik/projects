<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="com.matrimony.constants.Constants"%>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	boolean isLoggedIn = (session.getAttribute(Constants.USER_SESSION_ATTR) != null);
	// isLoggedIn = true;
	// set the Logged in flag in request scope
	request.setAttribute(Constants.USER_LOGGED_IN, Boolean.valueOf(isLoggedIn));
%>

<html:html>
<head>
<title><bean:message key="website.title" /></title>

<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">

<link href="/styles/matrimony.css" rel="stylesheet" type="text/css" />

<link href="/styles/jquery/themes/ui-lightness/jquery.ui.all.css" rel="stylesheet" type="text/css" />
<%-- <link href="/styles/jquery/demos.css" rel="stylesheet" type="text/css" />  --%>

<script src="/scripts/jquery/jquery-1.5.1.js" type="text/javascript"></script>
<script src="/scripts/jquery/jquery.ui.core.js"></script>
<script src="/scripts/jquery/jquery.ui.widget.js"></script>
<script src="/scripts/jquery/jquery.ui.accordion.js"></script>
<script src="/scripts/jquery/jquery.ui.position.js"></script>
<script src="/scripts/jquery/jquery.ui.autocomplete.js"></script>
<script src="/scripts/jquery/jquery.ui.datepicker.js"></script>

<script>
function pageLoaded() {
	if ("<%=session.getAttribute(Constants.CURRENT_HEADER_MENU)%>" == "register") {
		// if showing Register page
		var registerEmailField = document.getElementById("registerEmailAddress");
		if (registerEmailField) {
			registerEmailField.focus();
		}
	} else if ("<%=session.getAttribute(Constants.CURRENT_HEADER_MENU)%>" == "forgotPassword") {
		// if showing Register page
		var forgotPwdEmailField = document.getElementById("forgotPwdEmailAddress");
		if (forgotPwdEmailField) {
			forgotPwdEmailField.focus();
		}
	} else {
		// if showing Login page
		var loginEmailField = document.getElementById("emailAddress");
		if (loginEmailField) {
			loginEmailField.focus();
		}
	}

	// hide loadingDiv
	// document.getElementById("loadingDiv").style.display = "none";
}
</script>
 
</head>

<body onload="javascript:pageLoaded();">
<script type="text/javascript" src="/scripts/wz_tooltip.js"></script>

<tiles:insert attribute="header" />

<table width="100%" border="0" class="body-table border-top" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="2" width="100%" align="center">
			<!--
				<div style="color:red;font-family: arial; font-size: 12px;">
					<b>Invalid Username/Password</b>
				</div>
			-->
		<table border="0" width="100%">
			<tr>
				<td><br></td>
				<td><br></td>
				<td class="contents-block" valign="top" width="15%">
					<tiles:insert attribute="sidemenu" />
				</td>
				<td><br></td>
				<td><br></td>
				<td class="contents-block" valign="top" width="100%">
					<%--
					<!-- <div id="loadingDiv" class="loading">Loading...</div> -->
					<div id="dialog" title="Dialog Title">I'm in a dialog</div>
					<script>
					  $(document).ready(function() {
					    $("#dialog").dialog({ autoOpen: true });
					  });
					</script>
					--%>
					<tiles:insert attribute="body" />
				</td>
				<td><br></td>
				<td><br></td>

				<logic:equal name="<%=Constants.USER_LOGGED_IN%>" value="false" scope="request">
					<logic:equal name="<%=Constants.CURRENT_HEADER_MENU%>" 
									value="<%=Constants.ABOUT_US_MENU%>" scope="session">
						<!-- Display login block only when not logged in and on Home page -->
						<td class="contents-block" valign="top" width="30%">
							<jsp:include page="/pages/login.jsp" />
						</td>
						<td><br></td>
						<td><br></td>
					</logic:equal>
					<logic:equal name="<%=Constants.CURRENT_HEADER_MENU%>" 
									value="<%=Constants.LOGIN_MENU%>" scope="session">
						<!-- Display login block only when not logged in and on Home page -->
						<td class="contents-block" valign="top" width="30%">
							<jsp:include page="/pages/login.jsp" />
						</td>
						<td><br></td>
						<td><br></td>
					</logic:equal>
				</logic:equal>
			</tr>
		</table>
		</td>
	</tr>
</table>

<tiles:insert attribute="footer" />
</body>
</html:html>

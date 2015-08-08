<%@page import="com.matrimony.constants.Constants"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="contents-header">
			<bean:message key="login.contents.heading" />
		</td>
	</tr>
	<tr>
		<td class="contents">
			<html:form action="/Login" method="post">				
		        <html:errors />
		        <logic:notEmpty name="<%=Constants.LOGIN_NOT_OK_MSG%>" scope="request">
					<div id="loginErrMsg" class="ErrorMessage" style="padding-top: 10px; padding-bottom: 10px;">
						<bean:write name="<%=Constants.LOGIN_NOT_OK_MSG%>"/>
					</div>
				</logic:notEmpty>
				<table border="0" cellpadding="0" cellspacing="3">
					<tr>
						<td align="left" valign="bottom" class="label" style="padding-bottom: 3px">
							<bean:message key="login.label.email.address" />
						</td>
					</tr>
					<tr>
						<td style="padding-bottom: 15px">
							<html:text property="emailAddress" styleId="emailAddress" 
										size="30" styleClass="InputBox" />
						</td>
					</tr>
					<tr>
						<td align="left" valign="bottom" class="label" style="padding-bottom: 3px">
							<bean:message key="login.label.password" />
						</td>
					</tr>
					<tr>
						<td style="padding-bottom: 15px">
							
							<html:password property="password" styleId="password" 
											size="20" styleClass="InputBox" />
						</td>
					</tr>
					<tr>
						<td align="left" style="padding-bottom: 15px">
							<html:submit property="login" styleClass="LoginButton">
								<bean:message key="login.button.login" />
							</html:submit>
							<html:hidden property="action" value="login" />
						</td>
					</tr>
					<tr>
						<td>
							<a href="/Menu.do?action=view&hm=forgotPassword" class="link">
								<bean:message key="login.button.forgot_password" />
							</a>
						</td>
					</tr>
				</table>
			</html:form>
		</td>
	</tr>
</table>

<!--
<script>
var validateFormErrObj = document.getElementById("ValidateFormErrors");
var loginErrMsgObj = document.getElementById("loginErrMsg");
if (validateFormErrObj || loginErrMsgObj) {
	currHeaderMenu = document.getElementById("header_menu_home");
	currHeaderMenu.className = "selected";

	var currHeaderMenu = document.getElementById("header_menu_" + "<%=session.getAttribute(Constants.CURRENT_HEADER_MENU)%>");
	if (currHeaderMenu)
	{
		currHeaderMenu.className = "";
	}
	
}
</script>
-->

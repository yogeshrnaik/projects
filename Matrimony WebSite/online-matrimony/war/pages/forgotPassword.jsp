<%@page import="com.matrimony.constants.Constants"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<script>
	function hideForgotPwdMessages() {
		var forgotPwdErrMsgField = document.getElementById("forgotPwdErrMsg");
		if (forgotPwdErrMsgField) {
			forgotPwdErrMsgField.style.display = 'none';
		}
		var forgotPwdSuccessMsgField = document.getElementById("forgotPwdSuccessMsg");
		if (forgotPwdSuccessMsgField) {
			forgotPwdSuccessMsgField.style.display = 'none';
		}
	}
</script>

<table width="100%" cellpadding="0" cellspacing="0">
<tr>
	<td class="contents-header">
		<bean:message key="forgot_password.contents.heading" />
	</td>
</tr>
<tr>
	<td class="contents">
		<html:form action="/ForgotPassword" method="post">
			Please enter your email address that you have used to register with our site.
	       	<html:errors />
			<logic:notEmpty name="<%=Constants.FORGOT_PASSWORD_NOT_OK_MSG%>" scope="request">
				<div id="forgotPwdErrMsg" class="ErrorMessage" style="padding-top: 10px; padding-bottom: 10px;">
					<bean:write name="<%=Constants.FORGOT_PASSWORD_NOT_OK_MSG%>"/>
				</div>
			</logic:notEmpty>
			<logic:notEmpty name="<%=Constants.FORGOT_PASSWORD_OK_MSG%>" scope="request">
				<div id="forgotPwdSuccessMsg" class="SuccessMessage" style="padding-top: 10px; padding-bottom: 10px;">
					<bean:write name="<%=Constants.FORGOT_PASSWORD_OK_MSG%>"/>
				</div>
			</logic:notEmpty>				
			<table border="0" cellpadding="5" cellspacing="5" style="padding-top: 0px">
				<tr>
					<td align="right" valign="middle" class="label">
						<bean:message key="forgot_password.label.email.address" />
					</td>
					<td>
						<html:text property="forgotPwdEmailAddress" styleId="forgotPwdEmailAddress" 
							size="30" styleClass="InputBox"/>
					</td>
					<td>
						<html:submit property="sendEmail" titleKey="forgot_password.button.send.email"
							styleClass="FormButton" onclick="javascript:hideForgotPwdMessages();" >
							&nbsp; <bean:message key="forgot_password.button.send.email" />&nbsp;
						</html:submit>
						<html:hidden property="action" value="forgotPassword" />
					</td>
				</tr>
			</table>
			Your password will be sent to your email address.
		</html:form>
	</td>
</tr>
</table>

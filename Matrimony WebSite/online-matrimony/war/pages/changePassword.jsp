<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.matrimony.constants.Constants"%>
<%@page import="com.matrimony.vo.enums.UserAttributes"%>

<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td class="contents-header">
			Change Password
		</td>
		<td class="contents-header" align="right" style="padding-right: 10px">
			<u>Profile ID:</u>
			<bean:write name="userForm" property="user.id" />
		</td>
	</tr>
	<tr>
		<td class="contents" colspan="2" width="50%">
			<logic:notEmpty name="<%=Constants.SAVE_OK_MSG%>" scope="request">
				<div id="SuccessMessage" class="SuccessMessage" style="padding-top: 10px; padding-bottom: 10px;">
					<bean:write name="<%=Constants.SAVE_OK_MSG%>"/>
				</div>
			</logic:notEmpty>
			<html:form action="/ChangePassword.do" method="post">
				<table border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td align="right" valign="middle" class="label">
							<bean:message key="changePasswordForm.label.oldPassword" />: *
						</td>
						<td width="35%">
							<html:password property="oldPassword" styleClass="InputBox" size="30" />
						</td>
						<td rowspan="3" valign="top" nowrap>
							<html:errors />
							<logic:notEmpty name="<%=Constants.SAVE_NOT_OK_MSG%>" scope="request">
								<div id="SaveErrorMessage" class="ErrorMessage" 
									style="padding-top: 0px;">
									<bean:write name="<%=Constants.SAVE_NOT_OK_MSG%>"/>
								</div>
							</logic:notEmpty>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							<bean:message key="changePasswordForm.label.newPassword" />: *
						</td>
						<td width="35%">
							<html:password property="newPassword" styleClass="InputBox" size="30" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label" nowrap>
							<bean:message key="changePasswordForm.label.confirmNewPassword" />: *
						</td>
						<td width="35%">
							<html:password property="confirmNewPassword" styleClass="InputBox" size="30" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							<html:submit property="changePassword" styleClass="FormButton">
								&nbsp;&nbsp;<bean:message key="my_profile.button.save" />&nbsp;&nbsp;
							</html:submit>
							<html:hidden property="action" value="changePassword" />
						</td>
						<td>
							<html:reset styleClass="FormButton">
								&nbsp;&nbsp;<bean:message key="my_profile.button.reset" />&nbsp;&nbsp;
							</html:reset>
						</td>
					</tr>
				</table>
			</html:form>
		</td>
	</tr>
</table>
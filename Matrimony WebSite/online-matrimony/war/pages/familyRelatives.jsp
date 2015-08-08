<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.matrimony.constants.Constants"%>
<%@page import="com.matrimony.vo.enums.UserAttributes"%>

<jsp:include page="no-cache.jsp" />

<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td class="contents-header">
			Family &amp; Relatives
		</td>
		<td class="contents-header" align="right" style="padding-right: 10px">
			<u>Profile ID:</u>
			<bean:write name="userForm" property="user.id" />
		</td>
	</tr>
	<tr>
		<td class="contents" colspan="2" width="100%">
			<logic:notEmpty name="<%=Constants.SAVE_OK_MSG%>" scope="request">
				<div id="SuccessMessage" class="SuccessMessage" style="padding-top: 10px; padding-bottom: 10px;">
					<bean:write name="<%=Constants.SAVE_OK_MSG%>"/>
				</div>
			</logic:notEmpty>
			<html:form action="/MyProfile.do" method="post">
				<table border="0" cellpadding="5" cellspacing="5" width="100%">
					<tr>
						<td align="right" valign="top" class="label" width="10%">
							Father:
						</td>
						<td width="45%">
							<html:textarea name="userForm" property="user.matrimonyProfile.family.fatherDetails" 
									styleClass="TextAreaInputBox" rows="5" cols="50" />
						</td>
						<td rowspan="9" valign="top" class="text" style="padding-top: 0px">
							<%-- Add notes --%>
							<b><u>Note:</u></b>
							Your family's information won't be visible to anyone without your approval.

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
						<td align="right" valign="top" class="label">
							Mother:
						</td>
						<td>
							<html:textarea name="userForm" property="user.matrimonyProfile.family.motherDetails" 
									styleClass="TextAreaInputBox" rows="5" cols="50" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="top" class="label">
							Brother:
						</td>
						<td>
							<html:textarea name="userForm" property="user.matrimonyProfile.family.brotherDetails" 
									styleClass="TextAreaInputBox" rows="5" cols="50" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="top" class="label">
							Sister:
						</td>
						<td>
							<html:textarea name="userForm" property="user.matrimonyProfile.family.sisterDetails" 
									styleClass="TextAreaInputBox" rows="5" cols="50" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							<html:submit property="saveFamilyInfo" styleClass="FormButton">
								&nbsp;&nbsp;<bean:message key="my_profile.button.save" />&nbsp;&nbsp;
							</html:submit>
							<html:hidden property="modifiedUserAttributeCode" 
											value="<%=UserAttributes.FAMILY_RELATIVES.getName() %>" />
							<html:hidden property="action" value="save" />
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
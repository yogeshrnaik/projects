<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.matrimony.constants.Constants"%>
<%@page import="com.matrimony.vo.enums.UserAttributes"%>

<jsp:include page="no-cache.jsp" />

<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td class="contents-header">
			Contact Information
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
						<td align="right" valign="middle" class="label" width="20%">
							Email Address: *
						</td>
						<td width="35%">
							<a class="link" href='mailto:<bean:write name="userForm" property="user.email" />'>
								<bean:write name="userForm" property="user.email" />
							</a>
						</td>
						<td rowspan="9" valign="top" class="text" style="padding-top: 0px">
							<%-- Add notes --%>
							<b><u>Note:</u></b>
							Your contact information won't be visible to anyone without your approval.
							
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
							Secondary Email:
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.contact.secondaryEmail" 
								styleClass="InputBox" size="30" styleId="secondaryEmail" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Home Phone:
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.contact.homePhone" 
								styleClass="InputBox" size="30" styleId="homePhone" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Work Phone:
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.contact.workPhone" 
								styleClass="InputBox" size="30" styleId="workPhone" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Mobile Phone:
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.contact.mobilePhone" 
								styleClass="InputBox" size="30" styleId="mobilePhone" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Address:
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.contact.address" 
								styleClass="InputBox" size="30" styleId="address" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							City:
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.contact.city" 
								styleClass="InputBox" size="30" styleId="city" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							PIN / ZIP Code:
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.contact.postalCode" 
								styleClass="InputBox" size="30" styleId="postalCode" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							State:
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.contact.state" 
								styleClass="InputBox" size="30" styleId="state" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Country: *
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.contact.country" 
								styleClass="InputBox" size="30" styleId="country" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Home Town:
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.contact.homeTown" 
								styleClass="InputBox" size="30" styleId="homeTown" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Website:
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.contact.website" 
								styleClass="InputBox" size="30" styleId="website" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							<html:submit property="saveContactDetails" styleClass="FormButton">
								&nbsp;&nbsp;<bean:message key="my_profile.button.save" />&nbsp;&nbsp;
							</html:submit>
							<html:hidden property="modifiedUserAttributeCode" 
											value="<%=UserAttributes.CONTACT_INFO.getName() %>" />
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
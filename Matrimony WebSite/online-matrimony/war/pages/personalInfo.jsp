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
			Personal Information
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
							First Name: *
						</td>
						<td width="35%">
							<html:text name="userForm" property="user.matrimonyProfile.personal.firstName" 
								styleClass="InputBox" size="30" styleId="firstName" />
						</td>
						<td rowspan="9" valign="top" class="text" style="padding-top: 0px">
							<%-- Add notes --%>
							<b><u>Note:</u></b>
							Your personal information won't be visible to anyone without your approval.
							
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
							Last Name: *
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.personal.lastName" 
								styleClass="InputBox" size="30" styleId="lastName" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Gender:
						</td>
						<td class="profile_info">
							<bean:write name="userForm" property="user.matrimonyProfile.personal.gender" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Birth Date:
						</td>
						<td class="profile_info">
							<bean:write name="userForm" property="user.matrimonyProfile.personal.birthDate" format="dd-MMMM-yyyy" />
						</td>
					</tr>
					<%--
					<tr>
						<td align="right" valign="middle" class="label">
							Birth Place: 
						</td>
						<td>
							<html:text name="userForm" property="user.matrimonyProfile.personal.birthPlace" 
								styleClass="InputBox" size="30" styleId="birthPlace" />
						</td>
					</tr>
					 --%>
					<tr>
						<td align="right" valign="middle" class="label">
							Marital Status:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.maritalStatusCode" styleClass="InputBox">
								<logic:iterate name="<%= Constants.MARITAL_STATUS_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>			
					<tr>
						<td align="right" valign="middle" class="label">
							Children:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.childrenStatusCode" styleClass="InputBox">
								<logic:iterate name="<%= Constants.OPTIONS_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>			
					<tr>
						<td align="right" valign="middle" class="label">
							Height:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.height" styleClass="InputBox">
								<html:option value="0">
									<bean:message key="my_profile.height.select" />
								</html:option>
								<logic:iterate name="<%= Constants.HEIGHT_LIST %>" id="heightVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= heightVO.getValue()%>">
										<bean:message key="<%= heightVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Weight:
						</td>
						<td class="profile_info">
							<html:text name="userForm" property="user.matrimonyProfile.personal.weight" 
								styleClass="InputBox" size="10" styleId="weight" /> 
							KG
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Build:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.buildCode" styleClass="InputBox">
								<logic:iterate name="<%= Constants.BUILD_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Skin Tone:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.skinToneCode" styleClass="InputBox">
								<logic:iterate name="<%= Constants.SKIN_TONE_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Looks:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.looksCode" styleClass="InputBox">
								<logic:iterate name="<%= Constants.LOOKS_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Use Specs:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.useSpecsCode" styleClass="InputBox">
								<logic:iterate name="<%= Constants.OPTIONS_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Blood Group:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.bloodGroupCode" styleClass="InputBox">
								<logic:iterate name="<%= Constants.BLOOD_GROUP_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Diet:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.dietCode" styleClass="BigSelectBox">
								<logic:iterate name="<%= Constants.DIET_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Smoking:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.smokingCode" styleClass="InputBox">
								<logic:iterate name="<%= Constants.ADDICATION_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Drinking:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.drinkingCode" styleClass="InputBox">
								<logic:iterate name="<%= Constants.ADDICATION_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Pets:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.petPreferenceCode" styleClass="InputBox">
								<logic:iterate name="<%= Constants.PET_PREFERENCE_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							Special Condition:
						</td>
						<td class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.personal.specialConditionCode" styleClass="BigSelectBox">
								<logic:iterate name="<%= Constants.SPECIAL_CONDITION_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							<html:submit property="savePersonalInfo" styleClass="FormButton">
								&nbsp;&nbsp;<bean:message key="my_profile.button.save" />&nbsp;&nbsp;
							</html:submit>
							<html:hidden property="modifiedUserAttributeCode" 
											value="<%=UserAttributes.PERSONAL_INFO.getName() %>" />
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
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
			My Soul Mate
		</td>
	</tr>
	<tr>
		<td class="contents">
			<logic:notEmpty name="<%=Constants.SAVE_OK_MSG%>" scope="request">
				<div id="SuccessMessage" class="SuccessMessage" style="padding-top: 10px; padding-bottom: 10px;">
					<bean:write name="<%=Constants.SAVE_OK_MSG%>"/>
				</div>
			</logic:notEmpty>
			<html:form action="/MyProfile.do" method="post">
				<table border="0" cellpadding="5" cellspacing="5" width="100%">
					<tr>
						<td align="right" valign="middle" class="label" width="10%">
							Age:
						</td>
						<td width="35%" class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.ageMin" 
								styleClass="SmallSelectBox">
								<html:option value="0">
									<bean:message key="my_profile.age.select" />
								</html:option>
								<logic:iterate name="<%= Constants.AGE_LIST %>" id="ageVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= ageVO.getValue()%>">
										<bean:message key="<%= ageVO.getKey()%>" />
									</html:option>
								</logic:iterate>								
							</html:select>
							to
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.ageMax" 
								styleClass="SmallSelectBox">
								<html:option value="0">
									<bean:message key="my_profile.age.select" />
								</html:option>
								<logic:iterate name="<%= Constants.AGE_LIST %>" id="ageVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= ageVO.getValue()%>">
										<bean:message key="<%= ageVO.getKey()%>" />
									</html:option>
								</logic:iterate>								
							</html:select>
						</td>
						<td rowspan="9" valign="top" class="text" style="padding-top: 0px">
							<%-- Add notes --%>
							<b><u>Note:</u></b>
							Your preferences for your Soul Mate will be visible to everyone.
							
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
							Height:
						</td>
						<td width="35%" class="profile_info">
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.heightMin" 
								styleClass="SmallSelectBox">
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
							to
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.heightMax" 
								styleClass="SmallSelectBox">
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
						<td align="right" valign="top" class="label">
							Build:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.build"
									styleClass="MultipleSelectBox" size="6" multiple="true">
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
						<td align="right" valign="top" class="label">
							Skin Tone:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.skinTone"
									styleClass="MultipleSelectBox" size="5" multiple="true">
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
						<td align="right" valign="top" class="label">
							Looks:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.looks"
									styleClass="MultipleSelectBox" size="5" multiple="true">
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
						<td align="right" valign="top" class="label">
							Specs:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.useSpecs"
									styleClass="MultipleSelectBox" size="2" multiple="true">
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
						<td align="right" valign="top" class="label">
							Marital Status:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.maritalStatus"
									styleClass="MultipleSelectBox" size="3" multiple="true">
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
						<td align="right" valign="top" class="label">
							Children Status:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.childrenStatus"
									styleClass="MultipleSelectBox" size="2" multiple="true">
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
						<td align="right" valign="top" class="label">
							Daily Diet:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.diet"
									styleClass="MultipleSelectBox" size="5" multiple="true">
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
						<td align="right" valign="top" class="label">
							Smoking:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.smoking"
									styleClass="MultipleSelectBox" size="5" multiple="true">
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
						<td align="right" valign="top" class="label">
							Drinking:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.drinking"
									styleClass="MultipleSelectBox" size="5" multiple="true">
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
						<td align="right" valign="top" class="label">
							Pet Preference:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.petPreference"
									styleClass="MultipleSelectBox" size="5" multiple="true">
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
						<td align="right" valign="top" class="label">
							Special Condition:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.specialCondition"
									styleClass="MultipleSelectBox" size="5" multiple="true">
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
						<td align="right" valign="top" class="label">
							Education:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.education"
									styleClass="MultipleSelectBox" size="5" multiple="true">
								<logic:iterate name="<%= Constants.EDUCATION_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top" class="label">
							Profession:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.profession"
									styleClass="MultipleSelectBox" size="5" multiple="true">
								<logic:iterate name="<%= Constants.PROFESSION_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top" class="label">
							Annual Income:
						</td>
						<td width="35%" class="profile_info" nowrap>
							<html:select name="userForm" property="user.matrimonyProfile.soulMate.annualIncome"
									styleClass="BigSelectBox">
								<logic:iterate name="<%= Constants.ANNUAL_INCOME_LIST %>" id="keyValueVO" 
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
							<html:submit property="saveSoulMateInfo" styleClass="FormButton">
								&nbsp;&nbsp;<bean:message key="my_profile.button.save" />&nbsp;&nbsp;
							</html:submit>
							<html:hidden property="modifiedUserAttributeCode" 
											value="<%=UserAttributes.SOUL_MATE.getName() %>" />
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
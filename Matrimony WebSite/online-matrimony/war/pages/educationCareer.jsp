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
			Education &amp; Career
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
							Education:
						</td>
						<td width="45%">
							<html:select name="userForm" property="user.matrimonyProfile.educationCareer.educationCode" styleClass="BigSelectBox">
								<logic:iterate name="<%= Constants.EDUCATION_LIST %>" id="keyValueVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= keyValueVO.getValue()%>">
										<bean:message key="<%= keyValueVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
						<td rowspan="9" valign="top" class="text" style="padding-top: 0px">
							<%-- Add notes --%>
							<b><u>Note:</u></b>
							Your Education, Profession and Annual Income will be visible to everyone.
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
							Education Details:
						</td>
						<td>
							<html:textarea name="userForm" property="user.matrimonyProfile.educationCareer.educationDetails" 
								styleClass="TextAreaInputBox" rows="5" cols="50" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="top" class="label" width="10%">
							Profession:
						</td>
						<td width="45%">
							<html:select name="userForm" property="user.matrimonyProfile.educationCareer.professionCode" styleClass="BigSelectBox">
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
							Profession Details:
						</td>
						<td>
							<html:textarea name="userForm" property="user.matrimonyProfile.educationCareer.professionDetails" 
								styleClass="TextAreaInputBox" rows="5" cols="50" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="top" class="label" nowrap>
							Years of Experience:
						</td>
						<td>
							<html:select name="userForm" property="user.matrimonyProfile.educationCareer.yearsOfExperience" styleClass="InputBox">
								<html:option value="0">
									<bean:message key="my_profile.experience.select" />
								</html:option>
								<logic:iterate name="<%= Constants.EXPERIENCE_LIST %>" id="experienceVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= experienceVO.getValue()%>">
										<bean:message key="<%= experienceVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top" class="label" nowrap>
							Annual Income (in INR):
						</td>
						<td>
							<html:select name="userForm" property="user.matrimonyProfile.educationCareer.annualIncome" styleClass="InputBox">
								<html:option value="0">
									<bean:message key="my_profile.annual_income.select" />
								</html:option>
								<logic:iterate name="<%= Constants.ANNUAL_INCOME_LIST %>" id="incomeVO" 
												type="com.matrimony.vo.KeyValueVO" >
									<html:option value="<%= incomeVO.getValue()%>">
										<bean:message key="<%= incomeVO.getKey()%>" />
									</html:option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle" class="label">
							<html:submit property="saveEducationCareer" styleClass="FormButton">
								&nbsp;&nbsp;<bean:message key="my_profile.button.save" />&nbsp;&nbsp;
							</html:submit>
							<html:hidden property="modifiedUserAttributeCode" 
											value="<%=UserAttributes.EDUCATION_CAREER.getName() %>" />
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
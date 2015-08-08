<%@page import="com.matrimony.constants.Constants"%>
<%@page import="com.matrimony.forms.UserForm"%>
<%@page import="com.matrimony.vo.enums.Build"%>
<%@page import="java.util.List"%>
<%@page import="com.matrimony.vo.enums.MaritalStatus"%>
<%@page import="com.matrimony.vo.enums.OptionsEnum"%>
<%@page import="com.matrimony.vo.enums.Diet"%>
<%@page import="com.matrimony.vo.enums.Addiction"%>
<%@page import="com.matrimony.vo.enums.SkinTone"%>
<%@page import="com.matrimony.vo.enums.Looks"%>
<%@page import="com.matrimony.vo.enums.Education"%>
<%@page import="com.matrimony.vo.enums.Profession"%>
<%@page import="com.matrimony.vo.enums.SpecialCondition"%>
<%@page import="com.matrimony.vo.enums.PetPreference"%>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<jsp:include page="no-cache.jsp" />

<%
	UserForm userForm = (UserForm)session.getAttribute("userForm");
	String profilePhotoBlobKey = userForm.getUser().getMatrimonyProfile().getPhotoVO().getProfilePhotoBlobKey();
	String profilePhotoURL = "/serve?bk=" + profilePhotoBlobKey + "&tn=true";
	
	// String profilePhotoResizedURL = null;
	// if (profilePhotoBlobKey != null && profilePhotoBlobKey.trim().length() > 0) {
	//	profilePhotoResizedURL =  profilePhotoBlobKey + "=s120";
	// }
%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td class="contents-header">
			<bean:message key="my_profile.contents.heading" />
		</td>
	</tr>
	<tr>
		<td class="contents">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td class="profile_info" valign="middle" width="15%" align="center">
						<logic:empty name="userForm" property="user.matrimonyProfile.photoVO.profilePhotoBlobKey">
							<img alt="Upload Photo" src="/images/NoPicture.gif" border="0">
							<br>
							<a href="/MyProfile.do?action=view&reload=true&sm=profilePhoto" class="link">Upload Photo</a>
						</logic:empty>
						<logic:notEmpty name="userForm" property="user.matrimonyProfile.photoVO.profilePhotoBlobKey">
							<img src="<%= profilePhotoURL %>" border="0">
							<br>
							<a href="/MyProfile.do?action=view&reload=true&sm=profilePhoto" class="link">Change Photo</a>
							<%--
							<img src="<bean:write name="userForm" property="user.matrimonyProfile.photoVO.profilePhotoURL" />" border="0">
							--%>
						</logic:notEmpty>
					</td>
					<td class="profile_info" valign="top">
						<table border="0" cellpadding="3" cellspacing="3" width="100%">
							<tr>
								<td colspan="4" style="border-bottom: 1px solid #FF8C1A;" class="para-heading">
									<bean:write name="userForm" property="user.matrimonyProfile.personal.firstName" />
									<bean:write name="userForm" property="user.matrimonyProfile.personal.lastName" />
									(Profile ID: <bean:write name="userForm" property="user.id" />)
								</td>
							</tr>
							<tr>
								<td class="label" valign="middle" style="padding-top: 0px; padding-left: 5px;">
									Gender:
								</td>
								<td class="profile_info" valign="middle" style="padding-top: 0px; padding-left: 5px;">
									<bean:write name="userForm" property="user.matrimonyProfile.personal.gender" />
								</td>
								<td class="label" align="right" valign="middle" style="padding-top: 0px; padding-left: 5px;">
									Born On:
								</td>
								<td class="profile_info" valign="middle" style="padding-top: 0px; padding-left: 5px;">
									<bean:write name="userForm" property="user.matrimonyProfile.personal.birthDate" format="dd-MMMM-yyyy" />
								</td>
							</tr>
							<tr>
								<td class="label" valign="middle" style="padding-top: 0px; padding-left: 5px;">
									Registered On:
								</td>
								<td class="profile_info" valign="middle" style="padding-top: 0px">
									<bean:write name="userForm" property="user.matrimonyProfile.registrationDate" 
										format="dd-MMMM-yyyy" />
								</td>
								<td class="label" align="right" valign="middle" style="padding-top: 0px; padding-left: 5px;">
									Membership Expiry Date:
								</td>
								<logic:empty name="userForm" property="user.matrimonyProfile.membershipExpiryDate">
									<td class="profile_info" valign="middle" style="padding-top: 0px">
										<font color="red">
											You are not a member yet.
										</font>
									</td>
								</logic:empty>
								<logic:notEmpty name="userForm" property="user.matrimonyProfile.membershipExpiryDate">
									<td class="profile_info" valign="middle" style="padding-top: 0px">
										<bean:write name="userForm" property="user.matrimonyProfile.membershipExpiryDate" 
											format="dd-MMMM-yyyy" />
									</td>
								</logic:notEmpty>
							</tr>
							<tr>
								<td class="label" valign="middle" style="padding-top: 0px; padding-left: 5px;">
									Profile Status:
								</td>
								<td class="profile_info" valign="middle" style="padding-top: 0px">
									<bean:message key="<%=\"my_profile.profile_status.\" + userForm.getUser().getMatrimonyProfile().getProfileStatus().toString() %>"/>
								</td>
								<td class="label" align="right" valign="middle" style="padding-top: 0px; padding-left: 5px;">
									Email:
								</td>
								<td class="profile_info" valign="middle" style="padding-top: 0px">
									<a class="link" href='mailto:<bean:write name="userForm" property="user.email" />'>
										<bean:write name="userForm" property="user.email" />
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<div style="height: 15px">&nbsp;</div>
			<table border="0" cellpadding="3" cellspacing="0" width="100%" class="my_profile_table">
				<tr>
					<td colspan="2" align="center" style="border-bottom: 1px solid #FF8C1A;" class="para-heading" width="50%">
						About Me
					</td>
					<td align="left" style="border-bottom: 1px solid #FF8C1A;" class="para-heading">
						About My Soul Mate
					</td>
				</tr>
				<tr class="odd">
					<td class="aboutMeLabel" align="right">
						Age:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.personal.age" />
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:notEmpty name="userForm" property="user.matrimonyProfile.soulMate.ageMin">
							<logic:notEmpty name="userForm" property="user.matrimonyProfile.soulMate.ageMax">
								<logic:notEqual name="userForm" property="user.matrimonyProfile.soulMate.ageMin" value="0">
									<logic:notEqual name="userForm" property="user.matrimonyProfile.soulMate.ageMax" value="0">
										<bean:message key="<%=\"my_profile.age.\" + userForm.getUser().getMatrimonyProfile().getSoulMate().getAgeMin().toString() %>"/>
										to 
										<bean:message key="<%=\"my_profile.age.\" + userForm.getUser().getMatrimonyProfile().getSoulMate().getAgeMax().toString() %>"/>
									</logic:notEqual>
								</logic:notEqual>
							</logic:notEmpty>
						</logic:notEmpty>
						<%--
						<bean:write name="userForm" property="user.matrimonyProfile.soulMate.ageMin" />
						-
						<bean:write name="userForm" property="user.matrimonyProfile.soulMate.ageMax" />
						 --%>
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right">
						Height:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.height.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getHeight() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:notEmpty name="userForm" property="user.matrimonyProfile.soulMate.heightMin">
							<logic:notEmpty name="userForm" property="user.matrimonyProfile.soulMate.heightMax">
								<logic:notEqual name="userForm" property="user.matrimonyProfile.soulMate.heightMin" value="0">
									<logic:notEqual name="userForm" property="user.matrimonyProfile.soulMate.heightMax" value="0">
										<bean:message key="<%=\"my_profile.height.\" + userForm.getUser().getMatrimonyProfile().getSoulMate().getHeightMin().toString() %>"/>
										to 
										<bean:message key="<%=\"my_profile.height.\" + userForm.getUser().getMatrimonyProfile().getSoulMate().getHeightMax().toString() %>"/>
									</logic:notEqual>
								</logic:notEqual>
							</logic:notEmpty>
						</logic:notEmpty>
						<br>
					</td>
				</tr>
				<tr>
					<td class="aboutMeLabel" align="right">
						Build:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.build.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getBuild().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.buildOptions">
							<bean:message key="<%=\"my_profile.build.\" + Build.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<Build> buildOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getBuildOptions();
							int size = buildOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"my_profile.build.\" + buildOpts.get(i).toString()%>"/>
						<%		
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right">
						Skin Tone:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.skin_tone.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getSkinTone().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.skinToneOptions">
							<bean:message key="<%=\"my_profile.skin_tone.\" + SkinTone.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<SkinTone> skinToneOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getSkinToneOptions();
							size = skinToneOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"my_profile.skin_tone.\" + skinToneOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr>
					<td class="aboutMeLabel" align="right">
						Looks:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.looks.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getLooks().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.looksOptions">
							<bean:message key="<%=\"my_profile.looks.\" + Looks.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<Looks> looksOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getLooksOptions();
							size = looksOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"my_profile.looks.\" + looksOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right">
						Specs:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"general.options.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getUseSpecs().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.useSpecsOptions">
							<bean:message key="<%=\"general.options.\" + OptionsEnum.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<OptionsEnum> specsOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getUseSpecsOptions();
							size = specsOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"general.options.\" + specsOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>				
				<tr>
					<td class="aboutMeLabel" align="right">
						Marital Status:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.marital_status.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getMaritalStatus().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.maritalStatusOptions">
							<bean:message key="<%=\"my_profile.marital_status.\" + MaritalStatus.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<MaritalStatus> maritalOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getMaritalStatusOptions();
							size = maritalOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"my_profile.marital_status.\" + maritalOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right">
						Children:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"general.options.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getChildrenStatus().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.childrenStatusOptions">
							<bean:message key="<%=\"general.options.\" + OptionsEnum.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<OptionsEnum> childrenOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getChildrenStatusOptions();
							size = childrenOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"general.options.\" + childrenOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr>
					<td class="aboutMeLabel" align="right">
						Daily Diet:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.diet.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getDiet().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.dietOptions">
							<bean:message key="<%=\"my_profile.diet.\" + Diet.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<Diet> dietOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getDietOptions();
							size = dietOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"my_profile.diet.\" + dietOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right">
						Smoking:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.addiction.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getSmoking().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.smokingOptions">
							<bean:message key="<%=\"my_profile.addiction.\" + Addiction.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<Addiction> smokingOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getSmokingOptions();
							size = smokingOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"my_profile.addiction.\" + smokingOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr>
					<td class="aboutMeLabel" align="right">
						Drinking:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.addiction.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getDrinking().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.drinkingOptions">
							<bean:message key="<%=\"my_profile.addiction.\" + Addiction.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<Addiction> drinkingOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getDrinkingOptions();
							size = drinkingOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"my_profile.addiction.\" + drinkingOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right">
						Pets:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.pet_preference.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getPetPreference().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.petPreferenceOptions">
							<bean:message key="<%=\"my_profile.pet_preference.\" + PetPreference.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<PetPreference> petOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getPetPreferenceOptions();
							size = petOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"my_profile.pet_preference.\" + petOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr>
					<td class="aboutMeLabel" align="right">
						Special Condition:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.special_condition.long_desc.\" + userForm.getUser().getMatrimonyProfile().getPersonal().getSpecialCondition().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.specialConditionOptions">
							<bean:message key="<%=\"my_profile.special_condition.short_desc.\" + SpecialCondition.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<SpecialCondition> specConditionOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getSpecialConditionOptions();
							size = specConditionOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"my_profile.special_condition.short_desc.\" + specConditionOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right">
						Education:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.education.long_desc.\" + userForm.getUser().getMatrimonyProfile().getEducationCareer().getEducation().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.educationOptions">
							<bean:message key="<%=\"my_profile.education.short_desc.\" + Education.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<Education> educationOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getEducationOptions();
							size = educationOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"my_profile.education.short_desc.\" + educationOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr>
					<td class="aboutMeLabel" align="right">
						Profession:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.profession.long_desc.\" + userForm.getUser().getMatrimonyProfile().getEducationCareer().getProfession().toString() %>"/>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.soulMate.professionOptions">
							<bean:message key="<%=\"my_profile.profession.short_desc.\" + Profession.NO_ANSWER.toString() %>" />
						</logic:empty>
						<%
							List<Profession> professionOpts = userForm.getUser().getMatrimonyProfile().getSoulMate().getProfessionOptions();
							size = professionOpts.size();
							for(int i=0; i < size; i++) {
						%>
								<bean:message key="<%=\"my_profile.profession.short_desc.\" + professionOpts.get(i).toString() %>"/>
						<%
								if (i != size - 1) {
									out.print("/");
								} // if
							} // for
						%>
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right">
						Annual Income:
					</td>
					<td class="aboutMeInfo">
						<logic:notEmpty name="userForm" property="user.matrimonyProfile.educationCareer.annualIncome">
							<bean:message key="<%=\"my_profile.annual_income.\" +userForm.getUser().getMatrimonyProfile().getEducationCareer().getAnnualIncome() %>" />
						</logic:notEmpty>
						<br>
					</td>
					<td class="soulMateInfo" align="left">
						<logic:notEmpty name="userForm" property="user.matrimonyProfile.soulMate.annualIncome">
							<bean:message key="<%=\"my_profile.annual_income.\" + userForm.getUser().getMatrimonyProfile().getSoulMate().getAnnualIncome() %>" />
						</logic:notEmpty>
						<br>
					</td>
				</tr>
			</table>
			<div style="height: 15px">&nbsp;</div>			
			<table border="0" cellpadding="3" cellspacing="0" width="100%" class="my_profile_table">
				<tr>
					<td colspan="2" align="left" class="para-heading" width="50%">
						Contact Information
					</td>
				</tr>
				<tr class="odd">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Secondary Email:
					</td>
					<td class="aboutMeInfo">
						<a href="mail:<bean:write name="userForm" property="user.matrimonyProfile.contact.secondaryEmail" />">
							<bean:write name="userForm" property="user.matrimonyProfile.contact.secondaryEmail" />
						</a>
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right" nowrap>
						Home Phone:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.contact.homePhone" />
						<br>
					</td>
				</tr>
				<tr class="odd">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Work Phone:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.contact.workPhone" />
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right" nowrap>
						Mobile Phone:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.contact.mobilePhone" />
						<br>
					</td>
				</tr>
				<tr class="odd">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Address:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.contact.address" />
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right" nowrap>
						City:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.contact.city" />
						<br>
					</td>
				</tr>
				<tr class="odd">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						PIN / ZIP Code:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.contact.postalCode" />
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right" nowrap>
						State:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.contact.state" />
						<br>
					</td>
				</tr>
				<tr class="odd">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Country:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.contact.country" />
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right" nowrap>
						Home Town:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.contact.homeTown" />
						<br>
					</td>
				</tr>
				<tr class="odd">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Website:
					</td>
					<td class="aboutMeInfo">
						<logic:notEmpty name="userForm" property="user.matrimonyProfile.contact.website">
							<a href="<bean:write name="userForm" property="user.matrimonyProfile.contact.website" />" target="_new">
								<bean:write name="userForm" property="user.matrimonyProfile.contact.website" />
							</a>
						</logic:notEmpty>
						<br>
					</td>
				</tr>
			</table>
			<div style="height: 15px">&nbsp;</div>			
			<table border="0" cellpadding="3" cellspacing="0" width="100%" class="my_profile_table">
				<tr>
					<td colspan="2" align="left" class="para-heading" width="50%">
						Family Information
					</td>
				</tr>
				<tr class="odd">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Father:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.family.fatherDetails" />
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Mother:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.family.motherDetails" />
						<br>
					</td>
				</tr>
				<tr class="odd">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Brother:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.family.brotherDetails" />
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Sister:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.family.sisterDetails" />
						<br>
					</td>
				</tr>
			</table>
			<div style="height: 15px">&nbsp;</div>			
			<table border="0" cellpadding="3" cellspacing="0" width="100%" class="my_profile_table">
				<tr>
					<td colspan="2" align="left" class="para-heading" width="50%">
						Education &amp; Career
					</td>
				</tr>
				<tr class="odd">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Education:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.education.long_desc.\" + userForm.getUser().getMatrimonyProfile().getEducationCareer().getEducation().toString() %>"/>
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Education Details:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.educationCareer.educationDetails" />
						<br>
					</td>
				</tr>
				<tr class="odd">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Profession:
					</td>
					<td class="aboutMeInfo">
						<bean:message key="<%=\"my_profile.profession.long_desc.\" + userForm.getUser().getMatrimonyProfile().getEducationCareer().getProfession().toString() %>"/>
						<br>
					</td>
				</tr>
				<tr class="even">
					<td class="aboutMeLabel" align="right" width="10%" nowrap>
						Profession Details:
					</td>
					<td class="aboutMeInfo">
						<bean:write name="userForm" property="user.matrimonyProfile.educationCareer.professionDetails" />
						<br>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
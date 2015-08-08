<%@page import="com.matrimony.constants.Constants"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<script>
	function clearFieldText(fieldObj, defaultValue) {
		if (fieldObj.value == defaultValue) {
			// clear
			fieldObj.value = "";
			fieldObj.className = "InputBox";
		}
	}
	function addFieldText(fieldObj, defaultValue) {
		if (fieldObj.value == "") {
			fieldObj.value = defaultValue;

			// add DefaultText style sheet class
			fieldObj.className = "InputBox DefaultText";
		}
	}
</script>
<script>
$(function() {
	$( "#birthDate" ).datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		showOn: "button",
		buttonImage: "/images/calendar.gif",
		buttonImageOnly: true,
		yearRange: '-100:+0'
	});
});
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
	<td class="contents-header">
		<bean:message key="register.contents.heading"/>
	</td>
</tr>
<tr>
	<td class="contents">
		<html:form action="/Register" method="post">
			<bean:message key="register.note" />
			<table border="0" cellpadding="5" cellspacing="5" width="100%">
				<tr>
					<td align="right" valign="middle" class="label" width="20%">
						<bean:message key="register.label.email.address" />
					</td>
					<td width="35%">
						<html:text property="registerEmailAddress" styleClass="InputBox"
							size="30" styleId="registerEmailAddress" />
						<!-- <input id="registerEmailAddress" type="text" class="InputBox" size="30"> -->
					</td>
					<td rowspan="9" valign="top">
						<html:errors />
						
				        <logic:notEmpty name="<%=Constants.REGISTER_NOT_OK_MSG%>" scope="request">
							<div id="registerErrMsg" class="ErrorMessage" 
								style="padding-top: 0px;">
								<bean:write name="<%=Constants.REGISTER_NOT_OK_MSG%>"/>
							</div>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle" class="label">
						<bean:message key="register.label.confirm.email.address" />
					</td>
					<td>
						<html:text property="confirmEmailAddress" styleClass="InputBox"
							size="30" />
						<!-- <input type="text" class="InputBox" size="30">  -->
					</td>
				</tr>
				<tr>
					<td align="right" valign="top" class="label">
						<bean:message key="register.label.name" />
					</td>
					<td>
						<table border="0" cellpadding="0" cellspacing="0" style="padding:0px">
							<tr>
								<td>
									<html:text property="firstName" styleClass="InputBox" size="15" />
									&nbsp;&nbsp;
								</td>
								<td>
									<html:text property="lastName" styleClass="InputBox" size="15" />
									&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="text" style="padding:0px; padding-left: 5px">
									<bean:message key="register.label.first.name" />
								</td>
								<td class="text" style="padding:0px; padding-left: 5px">
									<bean:message key="register.label.last.name" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle" class="label">
						<bean:message key="register.label.gender" />
					</td>
					<td>
						<html:radio property="gender" value="M">
							<font class="RadioButton"><bean:message key="register.label.gender.male"/></font>
						</html:radio>
					
						<html:radio property="gender" value="F">
							<font class="RadioButton"><bean:message key="register.label.gender.female"/></font>
						</html:radio>
					</td>
				</tr>
				<tr>
					<td align="right" valign="top" class="label">
						<bean:message key="register.label.birth.date" />
					</td>
					<td valign="middle">
						<html:text property="birthDate" styleId="birthDate" styleClass="InputBox" size="10" readonly="true" />
						
						<img border="0" src="/images/question_mark_small.jpg" width="15px" height="15px" 
							title="We need your Birth date to verify that you are at least 18 years old." 
							onmouseover="Tip('We need your Birth date to verify that you are at least 18 years old.')" />
						<br>
						<span class="text">(dd/mm/yyyy)</span>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle" class="label">
						<bean:message key="register.label.password"/>
					</td>
					<td>
						<html:password property="password" styleClass="InputBox" size="20" />
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle" class="label">
						<bean:message key="register.label.confirm.password"/>
					</td>
					<td>
						<html:password property="confirmPassword" styleClass="InputBox" size="20" />
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle" class="label">
						<br>
					</td>
					<td align="left" valign="bottom" class="label">
						<html:checkbox property="agree" value="Y"/>
						&nbsp;
						<font class="label">
							I agree to the
							<a href="/Menu.do?action=view&hm=terms" class="link">
								<bean:message key="register.link.terms" />
							</a>.
						</font>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle" class="label">
						<html:submit property="register" styleClass="FormButton">
							&nbsp;&nbsp;<bean:message key="register.button.register" />&nbsp;&nbsp;
						</html:submit>
						<html:hidden property="action" value="register" />
					</td>
					<td>
						<html:reset styleClass="FormButton">
							&nbsp;&nbsp;<bean:message key="register.button.reset" />&nbsp;&nbsp;
						</html:reset>
					</td>
				</tr>
			</table>
		</html:form>
	</td>
</tr>
</table>
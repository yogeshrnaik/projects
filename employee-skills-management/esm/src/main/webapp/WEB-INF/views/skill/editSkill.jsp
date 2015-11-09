<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<h3>
	<spring:message code="page.editSkill.header" />
</h3>

<c:url var="editAction" value="/editSkill"></c:url>

<form:form action="${editAction}" commandName="skill">
	<table>
		<tr>
			<td><form:label path="id">
					<spring:message text="ID" />
				</form:label></td>
			<td><form:input path="id" readonly="true" size="8"
					disabled="true" /> <form:hidden path="id" /></td>
			<td align="left"><br></td>
		</tr>
		<tr>
			<td><form:label path="name">
					<spring:message code="page.skills.name" />
				</form:label></td>
			<td><form:input path="name" /></td>
			<td align="left"><form:errors path="name" cssClass="error" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit"
				value="<spring:message code="button.edit.skill"/>" /></td>
		</tr>
	</table>
</form:form>
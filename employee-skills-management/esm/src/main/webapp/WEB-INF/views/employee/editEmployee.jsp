<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<h3>
	<spring:message code="page.editEmployee.header" />
</h3>

<c:url var="editAction" value="/editEmployee"></c:url>

<form:form action="${editAction}" commandName="employee">
	<table>
		<tr>
			<td><form:label path="id">
					<spring:message code="page.employees.id" />
				</form:label></td>
			<td><form:input path="id" readonly="true" size="8"
					disabled="true" /> <form:hidden path="id" /></td>
			<td align="left"><br></td>
		</tr>
		<tr>
			<td><form:label path="firstName">
					<spring:message code="page.employees.first.name" />
				</form:label></td>
			<td><form:input path="firstName" /></td>
			<td align="left"><form:errors path="firstName" cssClass="error" /></td>
		</tr>
		<tr>
			<td><form:label path="lastName">
					<spring:message code="page.employees.last.name" />
				</form:label></td>
			<td><form:input path="lastName" /></td>
			<td align="left"><form:errors path="lastName" cssClass="error" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit"
				value="<spring:message code="button.edit.employee"/>" /></td>
		</tr>
	</table>
</form:form>
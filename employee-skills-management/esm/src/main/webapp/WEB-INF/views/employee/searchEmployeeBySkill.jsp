<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h3>
	<spring:message code="page.search.employee.by.skill.header" />
</h3>

<c:url var="searchAction" value="/searchEmployeeBySkill"></c:url>

<form:form action="${searchAction}" commandName="skill">
	<table>
		<tr>
			<td><form:label path="name">
					<spring:message code="page.skills.name" />
				</form:label></td>
			<td><form:input path="name" /></td>
			<td align="left"><form:errors path="name" cssClass="error" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit"
				value="<spring:message code="button.search"/>" /></td>
		</tr>
	</table>
</form:form>

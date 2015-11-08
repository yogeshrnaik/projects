<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Person Page</title>
<style type="text/css">
.error {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
	<h3>Edit a Person</h3>

	<c:url var="editAction" value="/person/edit"></c:url>

	<form:form action="${editAction}" commandName="person">
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
						<spring:message text="Name" />
					</form:label></td>
				<td><form:input path="name" /></td>
				<td align="left"><form:errors path="name" cssClass="error" /></td>
			</tr>
			<tr>
				<td><form:label path="country">
						<spring:message text="Country" />
					</form:label></td>
				<td><form:input path="country" /></td>
				<td align="left"><form:errors path="country" cssClass="error" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="<spring:message text="Edit Person"/>" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>

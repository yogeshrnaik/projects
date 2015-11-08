<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<style type="text/css">
.error {
	color: red;
	font-weight: bold;
}
</style>
<body>
	<h3>Add a Person</h3>

	<c:url var="addAction" value="/person/add"></c:url>

	<form:form action="${addAction}" commandName="person">
		<table>
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
					value="<spring:message text="Add Person"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>

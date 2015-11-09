<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h3>List of Skills</h3>

<c:if test="${!empty skills}">
	<table border="1" cellspacing="5" cellpadding="5">
		<tr>
			<th>Skill ID</th>
			<th>Skill Name</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<c:forEach items="${skills}" var="skill">
			<tr>
				<td>${skill.id}</td>
				<td nowrap>${skill.name}</td>
				<td><a href="<c:url value='/skills/edit/${skill.id}' />">Edit</a></td>
				<td><a href="<c:url value='/skills/remove/${skill.id}' />">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>
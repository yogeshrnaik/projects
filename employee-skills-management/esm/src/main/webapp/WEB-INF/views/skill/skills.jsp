<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h3><spring:message code="page.skills.header" /></h3>

<c:if test="${!empty message}">
	<div class="success">
		${message}
	</div>
	<br/>
</c:if>

<c:if test="${!empty skills}">
	<table border="1" cellspacing="5" cellpadding="5">
		<tr>
			<th><spring:message code="page.skills.id" /></th>
			<th><spring:message code="page.skills.name" /></th>
			<th><spring:message code="link.edit" /></th>
			<th><spring:message code="link.delete" /></th>
		</tr>
		<c:forEach items="${skills}" var="skill">
			<tr>
				<td>${skill.id}</td>
				<td nowrap>${skill.name}</td>
				<td><a href="<c:url value='/showEditSkill/${skill.id}' />"><spring:message code="link.edit" /></a></td>
				<td><a href="<c:url value='/deleteSkill/${skill.id}' />"><spring:message code="link.delete" /></a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>
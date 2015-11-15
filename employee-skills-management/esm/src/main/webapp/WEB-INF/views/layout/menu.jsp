<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<p>
	<a href="${pageContext.request.contextPath}/showSearchEmployeeBySkill">
		<spring:message code="menu.search.employee.by.skill" />
	</a>
</p>
<p>
	<a href="${pageContext.request.contextPath}/skills">
		<spring:message code="menu.manage.skills" />
	</a>
</p>
<p>
	<a href="${pageContext.request.contextPath}/showAddSkill">
		<spring:message code="menu.add.skill" />
	</a>
</p>
<p>
	<a href="${pageContext.request.contextPath}/employees">
		<spring:message code="menu.manage.employees" />
	</a>
</p>
<p>
	<a href="${pageContext.request.contextPath}/showAddEmployee">
		<spring:message code="menu.add.employee" />
	</a>
</p>
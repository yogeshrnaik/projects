<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h3>
  <spring:message code="page.employees.header" />
</h3>

<c:if test="${!empty message}">
  <div class="success">${message}</div>
  <br />
</c:if>

<c:if test="${!empty employees}">
  <table border="1" cellspacing="5" cellpadding="5">
    <tr>
      <th><spring:message code="page.employees.id" /></th>
      <th><spring:message code="page.employees.first.name" /></th>
      <th><spring:message code="page.employees.last.name" /></th>
      <th><spring:message code="page.employees.skills" /></th>
      <th><spring:message code="link.edit" /></th>
      <th><spring:message code="link.delete" /></th>
    </tr>
    <c:forEach items="${employees}" var="employee">
      <tr>
        <td>${employee.id}</td>
        <td nowrap>${employee.firstName}</td>
        <td nowrap>${employee.lastName}</td>
        <td>${employee.skillsString}</td>
        <td>
          <a href="<c:url value='/showEditEmployee/${employee.id}' />"><spring:message code="link.edit" /></a>
        </td>
        <td>
          <a href="<c:url value='/deleteEmployee/${employee.id}' />"><spring:message code="link.delete" /></a>
        </td>
      </tr>
    </c:forEach>
  </table>
</c:if>
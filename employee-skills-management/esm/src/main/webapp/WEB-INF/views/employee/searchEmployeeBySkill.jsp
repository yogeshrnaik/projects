<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h3>
  <spring:message code="page.search.employee.by.skill.header" />
</h3>

<c:url var="searchAction" value="/searchEmployeeBySkill"></c:url>

<form:form action="${searchAction}" commandName="searchDto">
  <table>
    <tr>
      <td>
        <form:label path="skill">
          <spring:message code="page.skills.name" />
        </form:label>
      </td>
      <td>
        <form:input path="skill" />
      </td>
      <td>
        <input type="submit" value="<spring:message code="button.search"/>" />
      </td>
      <td align="left">
        <form:errors path="skill" cssClass="error" />
      </td>
    </tr>
  </table>
</form:form>
<br />
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

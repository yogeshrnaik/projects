<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h3>
  <spring:message code="page.addEmployee.header" />
</h3>

<c:url var="addAction" value="/addEmployee"></c:url>

<form:form action="${addAction}" commandName="employee">
  <table style="width: 50%">
    <tr>
      <td>
        <form:label path="firstName">
          <spring:message code="page.employees.first.name" />
        </form:label>
      </td>
      <td>
        <form:input path="firstName" size="30" />
      </td>
      <td align="left" nowrap>
        <form:errors path="firstName" cssClass="error" />
      </td>
    </tr>
    <tr>
      <td>
        <form:label path="lastName">
          <spring:message code="page.employees.last.name" />
        </form:label>
      </td>
      <td>
        <form:input path="lastName" size="30" />
      </td>
      <td align="left" nowrap>
        <form:errors path="lastName" cssClass="error" />
      </td>
    </tr>
    <tr>
      <td valign="top" nowrap>
        <spring:message code="page.employees.select.skills" />
      </td>
      <td>
        <form:select path="skills" multiple="true" size="10" style="width:100%">
          <form:options items="${allSkills}" itemValue="id" itemLabel="name" />
        </form:select>
      </td>
      <td align="left" nowrap>
        <form:errors path="skills" cssClass="error" />
      </td>
    </tr>
    <tr>
      <td colspan="3">
        <input type="submit" value="<spring:message code="button.add.employee"/>" />
        &nbsp;
        <input type="button" value="<spring:message code="button.cancel"/>" onclick="window.location='${pageContext.request.contextPath}/employees'" />
      </td>
    </tr>
  </table>
</form:form>

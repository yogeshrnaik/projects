<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h3>
  <spring:message code="page.addSkill.header" />
</h3>

<c:if test="${!empty errorMsg}">
  <div class="error">${errorMsg}</div>
  <br />
</c:if>

<c:url var="addAction" value="/addSkill"></c:url>

<form:form action="${addAction}" commandName="skill">
  <table>
    <tr>
      <td>
        <form:label path="name">
          <spring:message code="page.skills.name" />
        </form:label>
      </td>
      <td>
        <form:input path="name" size="40" />
      </td>
      <td align="left">
        <form:errors path="name" cssClass="error" />
      </td>
    </tr>
    <tr>
      <td colspan="3">
        <input type="submit" value="<spring:message code="button.add.skill"/>" />
        &nbsp;
        <input type="button" value="<spring:message code="button.cancel"/>" onclick="window.location='${pageContext.request.contextPath}/skills'" />
      </td>
    </tr>
  </table>
</form:form>

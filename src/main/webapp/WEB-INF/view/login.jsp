<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="water" tagdir="/WEB-INF/tags" %>


<!DOCTYPE html>
<html>
<water:header title="login.title"/>
<body>

<form:form method="POST" action="${pageContext.request.contextPath}/login">
    <h2><spring:message code="login.header"/></h2>

    <div>
        <c:if test="${not empty message}">
            <span><spring:message code="${message}"/></span>
        </c:if>
    </div>

    <div>
            <%--TODO dropdown--%>
        <input name="username" type="text"
               placeholder="<spring:message code="login.apartment"/>" autofocus/>

        <input name="password" type="password"
               placeholder="<spring:message code="login.password"/>"/>

        <button type="submit"><spring:message code="login.submit"/></button>
    </div>

</form:form>

</body>
</html>
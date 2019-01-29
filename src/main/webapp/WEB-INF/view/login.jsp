<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="water" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<water:header title="login.title"/>
<body>
<div class="container-fluid">
    <form:form method="POST" action="${pageContext.request.contextPath}/login">
        <h2><spring:message code="login.header"/></h2>
        <div class="col-sm-3">
            <div class="form-group">
                <label>
                    <select name="username" class="form-control">
                        <c:forEach var="apartment" items="${apartments}">
                            <option value="${apartment.number}"><spring:message
                                    code="login.dropdown.prefix"/> ${apartment.number}</option>
                        </c:forEach>
                    </select>
                </label>
            </div>

            <div class="form-group">
                <input name="password" type="password" class="form-control"
                       placeholder="<spring:message code="login.password"/>"/>
                <span style="color:gray"><spring:message code="login.password.info"/></span>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary"><spring:message
                        code="login.submit"/></button>
            </div>

            <div>
                <c:if test="${not empty message}">
                    <span style="color:red"><spring:message code="${message}"/></span>
                </c:if>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>
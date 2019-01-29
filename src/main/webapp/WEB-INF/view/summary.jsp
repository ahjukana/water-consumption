<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="water" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<water:header showHeader="true"/>
<body>
<c:forEach items="${apartments}" var="apartment">
    <c:out value="${apartment.number}"/> <c:out value="${apartment.residentFirstName}"/> <br>
</c:forEach>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="water" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<water:header showHeader="true"/>
<body>
<h1><spring:message code="error.message"/></h1>

<span>
${status}
${error}
</span>

</body>
</html>

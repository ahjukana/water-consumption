<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="title" type="java.lang.String" required="false" %>
<%@ attribute name="showHeader" type="java.lang.Boolean" required="false" %>

<c:set var="title" value="${empty title ? 'default.title' : title}"/>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><spring:message code="${title}"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <c:if test="${showHeader}">
        <header>
            <nav class="navbar navbar-expand-md navbar-dark bg-secondary">
                <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/"
                               class="nav-link"><spring:message
                                    code="header.insert.measurements"/></a>
                        </li>
                        <c:if test="${hasManagerialRights}">
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/summary"
                                   class="nav-link"><spring:message code="header.view.summary"/></a>
                            </li>
                        </c:if>
                    </ul>
                </div>
                <div class="mx-auto order-0">
                    <button class="navbar-toggler" type="button" data-toggle="collapse"
                            data-target=".dual-collapse2">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                </div>
                <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/logout"
                               class="nav-link"><spring:message code="header.change.pass"/></a>
                        </li>
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/logout"
                               class="btn btn-danger"><spring:message code="header.logout"/></a>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
    </c:if>
</head>
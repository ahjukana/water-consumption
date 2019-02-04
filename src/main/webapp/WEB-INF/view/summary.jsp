<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="water" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<water:header showHeader="true"/>
<body>
<div class="container-fluid">
    <div class="col-md-3 form-group form-inline">
        <label for="year" class="col-sm-2 control-label">
            <spring:message code="add.measurement.year"/>
        </label>
        <select id="year" name="year" class="form-control">
            <c:forEach items="${dropdownYears}" var="year">
                <option value="${year}">${year}</option>
            </c:forEach>
        </select>

        <label for="month" class="col-sm-2 control-label">
            <spring:message code="add.measurement.month"/>
        </label>
        <select id="month" name="month" class="form-control">
            <c:forEach begin="1" end="12" varStatus="loop">
                <option value="${loop.index}"><spring:message
                        code="months.${loop.index}"/></option>
            </c:forEach>
        </select>

        <button type="button" id="submit" class="btn btn-primary"
                onclick="getSummary();">
            <spring:message code="summary.view"/>
        </button>
    </div>
</div>

<div class="container-fluid">
    <h2><spring:message code="months.${param.month}"/> <c:out value="${param.year}"/></h2>
    <table class="table table-bordered table-striped col-md-6">
        <thead>
        <tr>
            <th><spring:message code="summary.header.apartment"/></th>
            <th><spring:message code="summary.header.hot"/></th>
            <th><spring:message code="summary.header.cold"/></th>
            <th><spring:message code="summary.header.total"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="summary" items="${report}">
            <tr>
                <c:choose>
                    <c:when test="${summary.measurementsMissing}">
                        <td>${summary.apartmentNumber}</td>
                    </c:when>
                    <c:otherwise>
                        <td style="color:red">${summary.apartmentNumber}</td>
                    </c:otherwise>
                </c:choose>
                <td>${summary.measurement.hotWater}</td>
                <td>${summary.measurement.coldWater}</td>
                <td>${summary.measurement.totalWater}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
<script type="text/javascript">
  function getSummary() {
    var year = $("#year").val();
    var month = $("#month").val();
    window.location.href = "${pageContext.request.contextPath}/summary?year=" + year + "&month="
        + month;
  }
</script>
</html>

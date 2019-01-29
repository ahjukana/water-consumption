<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="water" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<water:header showHeader="true"/>
<body>
<div class="container-fluid">
    <form:form modelAttribute="newMeasurement" method="POST"
               action="${pageContext.request.contextPath}/saveMeasurement">
        <h2>
            <spring:message code="add.measurement.apartment.prefix"/> ${apartment.number}
            <br>${apartment.residentFirstName} ${apartment.residentSurname}
        </h2>

        <div class="col-md-5 form-group form-inline">
            <label for="year" class="col-sm-2 control-label">
                <spring:message code="add.measurement.year"/>
            </label>
            <select id="year" name="year" class="form-control">
                <option value="2016">2016</option>
                <option value="2222">2017</option>
            </select>
        </div>

        <div class="col-md-5 form-group form-inline">
            <label for="month" class="col-sm-2 control-label">
                <spring:message code="add.measurement.month"/>
            </label>
            <select id="month" name="month" class="form-control">
                <c:forEach begin="1" end="12" varStatus="loop">
                    <option value="${loop.index}"><spring:message
                            code="months.${loop.index}"/></option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-5 form-group form-inline">
            <label for="hotKitchen" class="col-sm-2 control-label">
                <spring:message code="add.measurement.hot.kithcen"/>
            </label>
            <form:input type="text" maxlength="20" class="form-control" id="hotKitchen"
                        path="hotKitchen"/>
        </div>

        <div class="col-md-5 form-group form-inline">
            <label for="coldKitchen" class="col-sm-2 control-label">
                <spring:message code="add.measurement.cold.kithen"/>
            </label>
            <form:input type="text" maxlength="20" class="form-control" id="coldKitchen"
                        path="coldKitchen"/>
        </div>

        <div class="col-md-5 form-group form-inline">
            <label for="hotBathroom" class="col-sm-2 control-label">
                <spring:message code="add.measurement.hot.bathroom"/>
            </label>
            <form:input type="text" maxlength="20" class="form-control" id="hotBathroom"
                        path="hotBathroom"/>
        </div>

        <div class="col-md-5 form-group form-inline">
            <label for="coldBathroom" class="col-sm-2 control-label">
                <spring:message code="add.measurement.cold.bathroom"/>
            </label>
            <form:input type="text" maxlength="20" class="form-control" id="coldBathroom"
                        path="coldBathroom"/>
        </div>

        <div class="col-md-5 form-group form-inline">
            <button type="submit" class="btn btn-primary">
                <spring:message code="add.measurement.submit"/>
            </button>
        </div>

    </form:form>
</div>
</body>
</html>
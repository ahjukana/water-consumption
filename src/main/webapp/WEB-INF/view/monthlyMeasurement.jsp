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
        <h3>
            <spring:message code="add.measurement.apartment.prefix"/> ${apartment.number}
            <br>${apartment.residentFirstName} ${apartment.residentSurname}
        </h3>

        <c:if test="${currentMonthInserted}">
            <h3 style="color:green"><spring:message code="add.measurement.all.inserted"/></h3>
        </c:if>

        <div class="col-md-5 form-group form-inline">
            <label for="year" class="col-sm-2 control-label">
                <spring:message code="add.measurement.year"/>
            </label>
            <select id="year" name="year" class="form-control">
                <c:forEach items="${dropdownYears}" var="year">
                    <option value="${year}">${year}</option>
                </c:forEach>
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
            <button type="submit" id="submit" class="btn btn-primary disabled">
                <spring:message code="add.measurement.submit"/>
            </button>
        </div>

        <div id="newerInfo" style="display:none">
            <span style="color:red"><spring:message code="add.measurement.has.newer"/></span>
        </div>

    </form:form>
</div>
</body>
<script type="text/javascript">

  $(document).ready(function () {
    updateData();
  });

  $("#year, #month").on("change", function () {
    updateData();
  });

  function disableInputs() {
    $("#hotKitchen, #coldKitchen, #hotBathroom, #coldBathroom").prop('disabled', true);
    $("#submit").prop('disabled', true).addClass("disabled");
  }

  function disableInputsAndShowData(measurement) {
    $("#hotKitchen").prop('disabled', true).val(measurement.hotKitchen);
    $("#coldKitchen").prop('disabled', true).val(measurement.coldKitchen);
    $("#hotBathroom").prop('disabled', true).val(measurement.hotBathroom);
    $("#coldBathroom").prop('disabled', true).val(measurement.coldBathroom);

    $("#submit").prop('disabled', true).addClass("disabled");
  }

  function enableInputsAndClearData() {
    $("#hotKitchen, #coldKitchen, #hotBathroom, #coldBathroom").prop('disabled', false).val("");
    $("#submit").prop('disabled', false).removeClass("disabled");
  }

  function hasNewerMeasurement() {
    if ("${lastMeasurement.dateStringForJS}") {
      var lastDate = new Date(${lastMeasurement.dateStringForJS}); // timezone?
      var selectedDate = new Date($("#year").val(), $("#month").val() - 1, 1);
      return lastDate > selectedDate;
    }
    return false;
  }

  function updateData() {
    var selectedYear = $("#year").val();
    var selectedMonth = $("#month").val();
    $.ajax({
      url: "${pageContext.request.contextPath}/getMeasurement",
      data: {selectedYear: selectedYear, selectedMonth: selectedMonth},
      success: function (measurement) {
        if (measurement) {
          disableInputsAndShowData(measurement);
          $("#newerInfo").hide();
        } else {
          enableInputsAndClearData();
          $("#newerInfo").hide();
          if (hasNewerMeasurement()) {
            disableInputs();
            $("#newerInfo").show();
          }
        }
      }
    });
  }

</script>
</html>
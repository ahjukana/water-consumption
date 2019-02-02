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

        <div class="col-md-6 form-group form-inline">
            <label for="year" class="col-sm-2 control-label">
                <spring:message code="add.measurement.year"/>
            </label>
            <select id="year" name="year" class="form-control">
                <c:forEach items="${dropdownYears}" var="year">
                    <option value="${year}">${year}</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-6 form-group form-inline">
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

        <div class="col-md-6 form-group form-inline">
            <label for="hotKitchen" class="col-sm-2 control-label">
                <spring:message code="add.measurement.hot.kithcen"/>
            </label>
            <form:input type="text" maxlength="20" class="form-control" id="hotKitchen"
                        path="hotKitchen"/>
            <div class="calculated" id="calcHotKitchen"></div>
        </div>

        <div class="col-md-6 form-group form-inline">
            <label for="coldKitchen" class="col-sm-2 control-label">
                <spring:message code="add.measurement.cold.kithen"/>
            </label>
            <form:input type="text" maxlength="20" class="form-control" id="coldKitchen"
                        path="coldKitchen"/>
            <div class="calculated" id="calcColdKitchen"></div>
        </div>

        <div class="col-md-6 form-group form-inline">
            <label for="hotBathroom" class="col-sm-2 control-label">
                <spring:message code="add.measurement.hot.bathroom"/>
            </label>
            <form:input type="text" maxlength="20" class="form-control" id="hotBathroom"
                        path="hotBathroom"/>
            <div class="calculated" id="calcHotBathroom"></div>
        </div>

        <div class="col-md-6 form-group form-inline">
            <label for="coldBathroom" class="col-sm-2 control-label">
                <spring:message code="add.measurement.cold.bathroom"/>
            </label>
            <form:input type="text" maxlength="20" class="form-control" id="coldBathroom"
                        path="coldBathroom"/>
            <div class="calculated" id="calcColdBathroom"></div>
        </div>

        <div class="col-md-6 form-group form-inline">
            <button type="submit" id="submit" class="btn btn-primary disabled">
                <spring:message code="add.measurement.submit"/>
            </button>
            <div class="calculated" id="calcHot"></div>
            <div class="calculated" id="calcCold"></div>
            <div class="calculated" id="calcTotal"></div>
        </div>

        <div id="newerInfo" style="display:none">
            <span style="color:red"><spring:message code="add.measurement.has.newer"/></span>
        </div>
    </form:form>
</div>
</body>
<script type="text/javascript">
  var INPUT_SELECTORS = "#hotKitchen, #coldKitchen, #hotBathroom, #coldBathroom";

  $(document).ready(function () {
    updateData();
    clearConsumption();
  });

  $(INPUT_SELECTORS).on("input", function () {
    updateConsumption();
  });

  function updateConsumption() {
    var lastHotKitchen = parseFloat("${lastMeasurement.hotKitchen}");
    var lastColdKitchen = parseFloat("${lastMeasurement.coldKitchen}");
    var lastHotBathroom = parseFloat("${lastMeasurement.hotBathroom}");
    var lastColdBathroom = parseFloat("${lastMeasurement.coldBathroom}");

    var currentHotKitchen = parseFloat($("#hotKitchen").val());
    var currentColdKitchen = parseFloat($("#coldKitchen").val());
    var currentHotBathroom = parseFloat($("#hotBathroom").val());
    var currentColdBathroom = parseFloat($("#coldBathroom").val());

    var hotKitchenConsumption = currentHotKitchen - lastHotKitchen;
    var coldKitchenConsumption = currentColdKitchen - lastColdKitchen;
    var hotBathroomConsumption = currentHotBathroom - lastHotBathroom;
    var coldBathroomConsumption = currentColdBathroom - lastColdBathroom;
    var hotWaterConsumption = hotKitchenConsumption + hotBathroomConsumption;
    var coldWaterConsumption = coldKitchenConsumption + coldBathroomConsumption;
    var totalWaterConsumption = hotWaterConsumption + coldWaterConsumption;

    $("#calcHotKitchen").html(hotKitchenConsumption);
    $("#calcColdKitchen").html(coldKitchenConsumption);
    $("#calcHotBathroom").html(hotBathroomConsumption);
    $("#calcColdBathroom").html(coldBathroomConsumption);

    if (!isNaN(hotWaterConsumption)) {
      $("#calcHot").html("<spring:message code="add.measurement.hot.total"/> " + hotWaterConsumption);
    } else {
      $("#calcHot").html("");
    }

    if (!isNaN(coldWaterConsumption)) {
      $("#calcCold").html("<spring:message code="add.measurement.cold.total"/> " + coldWaterConsumption);
    } else {
      $("#calcCold").html("");
    }

    if (!isNaN(totalWaterConsumption)) {
      $("#calcTotal").html("<spring:message code="add.measurement.total"/> " + totalWaterConsumption);
    } else {
      $("#calcTotal").html("");
    }
  }

  function clearConsumption() {
    $("#calcHotKitchen").html("");
    $("#calcColdKitchen").html("");
    $("#calcHotBathroom").html("");
    $("#calcColdBathroom").html("");
    $("#calcHot").html("");
    $("#calcCold").html("");
    $("#calcTotal").html("");
  }

  $("#year, #month").on("change", function () {
    updateData();
    clearConsumption();
  });

  function disableInputs() {
    $(INPUT_SELECTORS).prop('disabled', true);
    $("#submit").hide();
  }

  function disableInputsAndShowData(measurement) {
    $("#hotKitchen").prop('disabled', true).val(measurement.hotKitchen);
    $("#coldKitchen").prop('disabled', true).val(measurement.coldKitchen);
    $("#hotBathroom").prop('disabled', true).val(measurement.hotBathroom);
    $("#coldBathroom").prop('disabled', true).val(measurement.coldBathroom);

    $("#submit").hide();
  }

  function enableInputsAndClearData() {
    $(INPUT_SELECTORS).prop('disabled', false).val("");
    $("#submit").show();
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
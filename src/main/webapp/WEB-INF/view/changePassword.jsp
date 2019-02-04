<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="water" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<water:header showHeader="true"/>
<body>
<div class="container-fluid">
    <form:form method="POST" id="changePass"
               action="${pageContext.request.contextPath}/changePassword">
        <div class="col-sm-2">
            <div class="form-group">
                <input name="oldPass" type="password" class="form-control"
                       placeholder="<spring:message code="change.pass.old"/>"/>
            </div>

            <div class="form-group">
                <input name="newPass" type="password" class="form-control"
                       placeholder="<spring:message code="change.pass.new"/>"/>
            </div>

            <div class="form-group">
                <button type="button" class="btn btn-primary" onclick="save();">
                    <spring:message code="change.pass.save"/>
                </button>
            </div>

            <div id="info"></div>
        </div>
    </form:form>
</div>
</body>
<script type="text/javascript">
  var wrongPass = "<spring:message code="change.pass.wrong.old"/>";
  var ok = "<spring:message code="change.pass.ok"/>";
  function save() {
    $.ajax({
      type: "POST",
      url: "${pageContext.request.contextPath}/changePassword",
      data: $("#changePass").serialize(),
      success: function (response) {
        if (response.hasErrors) {
          $("#info").text(wrongPass)
        } else {
          $("#info").text(ok)
        }
      }
    });
  }
</script>
</html>
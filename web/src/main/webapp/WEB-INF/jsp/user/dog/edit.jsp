<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<my:pagetemplate title="Edit dog">
<jsp:attribute name="body">
    <form:form method="post"
               action="${pageContext.request.contextPath}/user/dog/edit/${dog.id}" modelAttribute="dog"
               cssClass="form-horizontal" onsubmit="return validate(this);">

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${breed_error?'has-error':''}">
            <form:label path="breed" cssClass="col-sm-2 control-label">Breed</form:label>
            <div class="col-sm-10">
                <form:input path="breed" cssClass="form-control"/>
                <form:errors path="breed" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${gender_error?'has-error':''}">
            <form:label path="gender" cssClass="col-sm-2 control-label">Gender</form:label>
            <div class="col-sm-10">
                <form:select path="gender">
                    <c:forEach items="${genders}" var="gender">
                        <form:option value="${gender}"> ${gender.toString().toLowerCase()} </form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="gender" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${dateOfBirth_error?'has-error':''}">
            <form:label path="dateOfBirth" cssClass="col-sm-2 control-label">Date of Birth</form:label>
            <div class="col-sm-10">
                <form:input path="dateOfBirth" cssClass="form-control" class="validate-isValidDate"/>
                <form:errors path="dateOfBirth" cssClass="help-block"/>
            </div>
        </div>

         <button class="btn btn-primary" type="submit">Edit Dog</button>
        </form:form>

    <script>
        function validate(form) {
            var fnMap = {

                'validate-isValidDate': {
                    checkFn: isValidDate,
                    checkMsg: 'Date must be in format dd.mm.yyyy'
                }
            };

            var reClass = /(^|\s*)validate-[^\s]*/g;
            var control, controls = form.elements;
            var check, checks;
            var fn, value;

            for (var i = 0, iLen = controls.length; i < iLen; i++) {
                control = controls[i];

                // Need a function here to handle more control types
                value = control.value;
                checks = control.className.match(reClass);

                // If there are any validate- classes
                if (checks) {

                    // For each validate class
                    for (var j = 0, jLen = checks.length; j < jLen; j++) {
                        check = checks[j].replace(/\s/g, '');

                        // See if there is a related function
                        if (fnMap.hasOwnProperty(check)) {
                            check = fnMap[check];

                            if (!check.checkFn(value)) {
                                alert(check.checkMsg);
                                control.focus();
                                return false;
                            }
                        }
                    }
                }
            }
        }

        // Just checks the pattern is dd.mm.yyyy
        function isValidDate(s) {
            s.replace(/^\s+|\s+$/g, '');
            return /\d{1,2}[.]\d{1,2}[.]\d{4}/.test(s);
        }
    </script>

</jsp:attribute>
</my:pagetemplate>

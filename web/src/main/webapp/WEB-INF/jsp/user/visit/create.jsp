<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<my:pagetemplate title="Create new visit">
<jsp:attribute name="body">
    <form:form method="post"
               action="${pageContext.request.contextPath}/user/visit/create/${id}" modelAttribute="visit"
               cssClass="form-horizontal" onsubmit="return validate(this);">

          <div class="form-group">
              <form:label path="start" cssClass="col-sm-2 control-label">Visit start</form:label>
              <div class="col-sm-10">
                  <form:input path="start" cssClass="form-control" class="validate-isValidDate"/>
              </div>
          </div>

         <div class="form-group">
             <form:label path="servicesId" cssClass="col-sm-2 control-label">Service</form:label>
             <div class="col-sm-10">
                 <form:select multiple="true" path="servicesId">
                     <c:forEach items="${servicelist}" var="serv">
                         <form:option value="${serv.id}"> ${serv.name}, price: ${serv.price}</form:option>
                     </c:forEach>
                 </form:select>
             </div>
         </div>

         <button class="btn btn-primary" type="submit">Create Visit</button>
        </form:form>

    <script>
        function validate(form) {
            var fnMap = {

                'validate-isValidDate': {
                    checkFn: isValidDate,
                    checkMsg: 'Date must be in format dd.mm.yyyy h:mm'
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

        // Just checks the pattern is dd.mm.yyyy h:mm
        function isValidDate(s) {
            s.replace(/^\s+|\s+$/g, '');
            return /\d{1,2}[.]\d{1,2}[.]\d{4} \d{1,2}:\d{1,2}$/.test(s);
        }

        window.onmousedown = function (e) {
            var el = e.target;
            if (el.tagName.toLowerCase() == 'option' && el.parentNode.hasAttribute('multiple')) {
                e.preventDefault();

                // toggle selection
                if (el.hasAttribute('selected')) el.removeAttribute('selected');
                else el.setAttribute('selected', '');

                // hack to correct buggy behavior
                var select = el.parentNode.cloneNode(true);
                el.parentNode.parentNode.replaceChild(select, el.parentNode);
            }
        }
    </script>
</jsp:attribute>
</my:pagetemplate>

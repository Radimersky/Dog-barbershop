<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<my:pagetemplate title="Create New Employee">
<jsp:attribute name="body">
    <form:form method="post"
               action="${pageContext.request.contextPath}/employee/create/" modelAttribute="employee"
               cssClass="form-horizontal">

        <div class="form-group ${positionName_error?'has-error':''}">
            <form:label path="positionName" cssClass="col-sm-2 control-label">Position name</form:label>
            <div class="col-sm-10">
                <form:input path="positionName" cssClass="form-control"/>
                <form:errors path="positionName" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${salary_error?'has-error':''}">
            <form:label path="salary" cssClass="col-sm-2 control-label">Salary</form:label>
            <div class="col-sm-10">
                <form:input path="salary" cssClass="form-control"/>
                <form:errors path="salary" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${person.name_error?'has-error':''}">
            <form:label path="person.name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="person.name" cssClass="form-control"/>
                <form:errors path="person.name" cssClass="help-block"/>
            </div>
        </div>

         <div class="form-group ${person.surname_error?'has-error':''}">
             <form:label path="person.surname" cssClass="col-sm-2 control-label">Surname</form:label>
             <div class="col-sm-10">
                 <form:input path="person.surname" cssClass="form-control"/>
                 <form:errors path="person.surname" cssClass="help-block"/>
             </div>
         </div>

        <div class="form-group ${person.address_error?'has-error':''}">
            <form:label path="person.address" cssClass="col-sm-2 control-label">Address</form:label>
            <div class="col-sm-10">
                <form:textarea cols="80" rows="10" path="person.address" cssClass="form-control"/>
                <form:errors path="person.address" cssClass="help-block"/>
            </div>
        </div>

         <div class="form-group ${person.phoneNumber_error?'has-error':''}">
             <form:label path="person.phoneNumber" cssClass="col-sm-2 control-label">Phone number</form:label>
             <div class="col-sm-10">
                 <form:input path="person.phoneNumber" cssClass="form-control"/>
                 <form:errors path="person.phoneNumber" cssClass="help-block"/>
             </div>
         </div>

         <div class="form-group ${person.password_error?'has-error':''}">
             <form:label path="person.password" cssClass="col-sm-2 control-label">Password</form:label>
             <div class="col-sm-10">
                 <form:password path="person.password" cssClass="form-control"/>
                 <form:errors path="person.password" cssClass="help-block"/>
             </div>
         </div>

        <button class="btn btn-primary" type="submit">Create New Employee</button>
        </form:form>

</jsp:attribute>
</my:pagetemplate>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:setBundle basename="Texts"/>
<my:pagetemplate title="Create Service Type">
<jsp:attribute name="body">
    <form:form method="post"
               action="${pageContext.request.contextPath}/serviceType/create/" modelAttribute="serviceType"
               cssClass="form-horizontal">

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${description_error?'has-error':''}">
            <form:label path="description" cssClass="col-sm-2 control-label">Description</form:label>
            <div class="col-sm-10">
                <form:textarea cols="80" rows="10" path="description" cssClass="form-control"/>
                <form:errors path="description" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${price_error?'has-error':''}">
            <form:label path="price" cssClass="col-sm-2 control-label">Price</form:label>
            <div class="col-sm-10">
                <form:input path="price" cssClass="form-control"/>
                <form:errors path="price" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${standardLength_error?'has-error':''}">
            <form:label path="standardLength" cssClass="col-sm-2 control-label">Standard length</form:label>
            <div class="col-sm-10">
                <form:input path="standardLengthFormated" cssClass="form-control"/>
                <form:errors path="standardLengthFormated" cssClass="help-block"/>
            </div>
        </div>


        <button class="btn btn-primary" type="submit">Create Service Type</button>
    </form:form>
</jsp:attribute>
</my:pagetemplate>

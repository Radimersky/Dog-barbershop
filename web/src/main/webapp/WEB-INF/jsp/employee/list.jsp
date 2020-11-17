<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<my:pagetemplate>
<jsp:attribute name="body">
    <p>Here you can as Admin manage your employees</p>
    <form:form method="post"
               action="${pageContext.request.contextPath}/employee/list" modelAttribute="person">
       <h3>Create new employee</h3>
            <my:tableButton href="/employee/create/" class='btn btn-success'>
                <span class="glyphicon glyphicon-plus">
                </span> New
            </my:tableButton>

        <h3>Search for employee</h3>

        <div class="form-group">
            <form:label path="phoneNumber" cssClass="col-sm-2 control-label">Phone number</form:label>
            <div class="col-sm-10">
                <form:input path="phoneNumber" cssClass="form-control"/>
                <form:errors path="phoneNumber" cssClass="help-block"/>
            </div>
        </div>

         <div class="form-group">
             <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
             <div class="col-sm-10">
                 <form:input path="name" cssClass="form-control"/>
                 <form:errors path="name" cssClass="help-block"/>
             </div>
         </div>
        <button class="btn btn-primary" type="submit">Search</button>
        </form:form>

    <h3>Employees</h3>
    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Surname</th>
            <th>Address</th>
            <th>Position name</th>
            <th>Salary</th>
            <th>Phone Number</th>
            <th><!--Button Column--></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${employees}" var="employee">
            <tr>
                <td><c:out value="${employee.person.name}"/></td>
                <td><c:out value="${employee.person.surname}"/></td>
                <td>
                    <c:out
                            value="${employee.person.address}"/>
                </td>
                <td><c:out value="${employee.positionName}"/></td>
                <td><c:out value="${employee.salary.toString()}"/></td>
                <td><c:out value="${employee.person.phoneNumber}"/></td>
                <td>
                    <my:tableButton href="/employee/edit/${employee.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-edit">
                        </span> Edit
                    </my:tableButton>
                </td>
                <td>
                    <my:tableButton href="/employee/delete/${employee.id}" class='btn btn-danger'>
                        <span class="glyphicon glyphicon-trash">
                        </span> Delete
                    </my:tableButton>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>

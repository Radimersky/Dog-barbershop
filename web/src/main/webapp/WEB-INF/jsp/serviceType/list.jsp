<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<my:pagetemplate>
<jsp:attribute name="body">

    <h1>Service Types</h1>
    <p>Here you can as Admin manage available types of services</p>
    <my:tableButton href="/serviceType/create/" class='btn btn-success'>
        <span class="glyphicon glyphicon-plus">
        </span> New
    </my:tableButton>

    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Standard length</th>
            <th>Price</th>
            <th>Description</th>
            <th><!--Button Column--></th>
            <th><!--Button Column--></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${serviceTypes}" var="serviceType">
            <tr>
                <td><c:out value="${serviceType.name}"/></td>
                <td><c:out value="${serviceType.standardLengthFormated}"/></td>
                <td><c:out value="${serviceType.price}"/></td>
                <td><c:out value="${serviceType.description}"/></td>

                <td>
                    <my:tableButton href="/serviceType/edit/${serviceType.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-edit">
                        </span> Edit
                    </my:tableButton>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>

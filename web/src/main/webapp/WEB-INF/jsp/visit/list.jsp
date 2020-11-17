<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<my:pagetemplate>
<jsp:attribute name="body">
    <h1>Visit overview</h1>
    <p>Here you can as Employee manage all visits</p>
    <table class="table">
        <thead>
        <tr>
            <th>Dog name</th>
            <th>Owner name</th>
            <th>Owner surname</th>
            <th>Phone</th>
            <th>Visit start</th>
            <th>Visit end</th>
            <th><!--Button Column--></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${visits}" var="visit">
            <tr>
                <td><c:out value="${visit.dog.name}"/></td>
                <td><c:out value="${visit.dog.owner.name}"/></td>
                <td><c:out value="${visit.dog.owner.surname}"/></td>
                <td><c:out value="${visit.dog.owner.phoneNumber}"/></td>
                <td><fmt:formatDate value="${visit.start}" pattern="dd.MM.yyyy h:mm"/></td>
                <td><fmt:formatDate value="${visit.finish}" pattern="dd.MM.yyyy h:mm"/></td>
                <td>
                    <my:tableButton href="/visit/delete/${visit.id}" class='btn btn-danger'>
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


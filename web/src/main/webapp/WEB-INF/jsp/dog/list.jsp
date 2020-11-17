<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<my:pagetemplate>
<jsp:attribute name="body">
    <h1>Dogs</h1>
    <p>Here you can as Employee manage all dogs</p>
    <my:tableButton href="/dog/create/" class='btn btn-success'>
        <span class="glyphicon glyphicon-plus">
        </span> New
    </my:tableButton>
    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Breed</th>
            <th>Gender</th>
            <th>Date of birth</th>
            <th>Owner name</th>
            <th>Owner surname</th>
            <th><!--Button Column--></th>
            <th><!--Button Column--></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${dogs}" var="dog">
            <tr>
                <td><c:out value="${dog.name}"/></td>
                <td><c:out value="${dog.breed}"/></td>
                <td><c:out value="${dog.gender.toString().toLowerCase()}"/></td>
                <td><fmt:formatDate value="${dog.dateOfBirth}" pattern="dd.MM.yyyy"/></td>
                <td><c:out value="${dog.owner.name}"/></td>
                <td><c:out value="${dog.owner.surname}"/></td>
                <td>
                    <my:tableButton href="/dog/edit/${dog.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-edit">
                        </span> Edit
                    </my:tableButton>
                </td>
                <td>
                    <my:tableButton href="/dog/delete/${dog.id}" class='btn btn-danger'>
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

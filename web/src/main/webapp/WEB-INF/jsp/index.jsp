<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<my:pagetemplate>
<jsp:attribute name="body">

<div class="jumbotron">
    <h1>Dog Barbershop</h1>
    <p>Welcome to Dog Barbershop where you can manage your services!</p>

    <c:if test="${cookie['auth'].value.equals('true')}">
        <li><a href="/pa165/logout-page/logout">Logout</a></li>
    </c:if>
    <c:if test="${!cookie['auth'].value.equals('true')}">
        <li><a href="/pa165/login-page">Login</a></li>
    </c:if>


</div>

</jsp:attribute>
</my:pagetemplate>

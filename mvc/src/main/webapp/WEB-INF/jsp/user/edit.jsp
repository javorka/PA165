<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="Edit User">
<jsp:attribute name="body">
    <c:choose>
        <c:when test="${empty id}">
            <c:set var="editAction" value="${pageContext.request.contextPath}/user/edit"/>
        </c:when>
        <c:otherwise>
            <c:set var="editAction" value="${pageContext.request.contextPath}/user/edit/${id}"/>
        </c:otherwise>
    </c:choose>
<div class="container">
    <div class="col-md-6">
        <form:form method="post" modelAttribute="user" action="${editAction}">
            <div class="form-group">
                <label for="email">Email</label>
                <form:input path="email" cssClass="form-control" id="email" placeholder="Email" disabled="true"/>
                <form:errors path="email" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="name">Name</label>
                <form:input path="name" cssClass="form-control" id="name" placeholder="Name"/>
                <form:errors path="name" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <form:password path="password" cssClass="form-control" id="password" placeholder="Password"/>
                <form:errors path="password" cssClass="text-danger" />
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
        </form:form>
    </div>
</div>
</jsp:attribute>
</my:template>

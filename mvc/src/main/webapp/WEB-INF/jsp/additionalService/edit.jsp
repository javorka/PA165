<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xjavorka
  Date: 14.12.16
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container">
    <c:choose>
        <c:when test="${additionalService == null}">
            <h2>New Additional service</h2>
        </c:when>
        <c:otherwise>
            <h2>Edit Additional service - <c:out value="${additionalService.name}"/></h2>
        </c:otherwise>
    </c:choose>
    <hr/>
    <div class="col-md-6">
        <form:form method="post" modelAttribute="additionalServiceCreate" action="${pageContext.request.contextPath}/additionalService/create">
            <div class="form-group">
                <label for="name">Name</label>
                <form:input path="name" cssClass="form-control" id="name" placeholder="Name" />
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <form:textarea path="description" cssClass="form-control" rows="3" id="description" placeholder="Description" />
            </div>
            <div class="form-group">
                <label for="price">Price</label>
                <div class="input-group">
                    <form:input path="price" cssClass="form-control" id="price" placeholder="Price" aria-describedby="currency-addon" />
                    <span class="input-group-addon" id="currency-addon">Kc</span>
                </div>
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
        </form:form>
    </div>
</div>

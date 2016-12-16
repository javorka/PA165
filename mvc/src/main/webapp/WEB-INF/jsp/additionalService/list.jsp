<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: xjavorka
  Date: 14.12.16
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<my:template title="Additional services">
<jsp:attribute name="body">
<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Operation</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${additionalServices}" var="as">
            <tr>
                <td><c:out value="${as.name}"/></td>
                <td><c:out value="${as.description}"/></td>
                <td><c:out value="${as.price}"/></td>
                <td>

                    <a href="${pageContext.request.contextPath}/additionalService/edit/${as.id}"
                       class="btn btn-default">Edit</a>
                    <a href="${pageContext.request.contextPath}/additionalService/delete/${as.id}"
                       class="btn btn-danger">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</jsp:attribute>
</my:template>
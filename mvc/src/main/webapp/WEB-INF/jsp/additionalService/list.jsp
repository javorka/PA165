<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xjavorka
  Date: 14.12.16
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <h2>Additional services</h2>
    <hr/>
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
                    <a href="/product/view/${as.id}" class="btn btn-primary">Edit</a>
                    <a href="/product/view/${as.id}" class="btn btn-primary">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--.
  Author: xtravni2
--%>

<my:template title="All orders">
<jsp:attribute name="body">
    
    <a href="${pageContext.request.contextPath}/orders/create/" class="btn btn-primary">Create</a>
    
    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>placed</th>
            <th>state</th>
            <th>email</th>
            <th>customer</th>
            <th>phone</th>
            <th>address</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td><fmt:formatDate value="${order.dateCreated}" pattern="yyyy-MM-dd"/></td>
                <td>${order.state}</td>
                <td><c:out value="${order.user.email}"/></td>
                <td><c:out value="${order.user.name}"/></td>
                <td><c:out value="${order.phone}"/></td>
                <td><c:out value="${order.address}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/orders/detail/${order.id}" class="btn btn-primary">View</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:template>
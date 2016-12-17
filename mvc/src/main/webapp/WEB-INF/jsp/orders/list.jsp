<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--.
  Author: xtravni2
--%>

<my:template titleMessageKey="title.order.list" activeNav="orderManagement">
<jsp:attribute name="body">
    
    <a href="${pageContext.request.contextPath}/orders/create/" class="btn btn-primary"><f:message key="label.order.create"/></a>
    
    <table class="table">
        <thead>
        <tr>
            <th><f:message key="label.order.date"/></th>
            <th><f:message key="label.order.state"/></th>
            <th><f:message key="label.email"/></th>
            <th><f:message key="label.order.customer"/></th>
            <th><f:message key="label.phone"/></th>
            <th><f:message key="label.address"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td><f:formatDate value="${order.dateCreated}" pattern="yyyy-MM-dd"/></td>
                <td><span class="order-${fn:toLowerCase(order.state)}">${order.state}</span></td>
                <td><c:out value="${order.user.email}"/></td>
                <td><c:out value="${order.user.name}"/></td>
                <td><c:out value="${order.phone}"/></td>
                <td><c:out value="${order.address}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/orders/detail/${order.id}" class="btn btn-primary"><f:message key="button.detail"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:template>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--.
  Author: xtravni2
--%>

<my:template title="Order ${order.id}" activeNav="orderManagement">
<jsp:attribute name="body">


        <table class="table">
            <caption><f:message key="title.order"/></caption>
            <thead>
            <tr>
                <th><f:message key="label.order.date"/></th>
                <th><f:message key="label.order.state"/></th>
                <th><f:message key="label.email"/></th>
                <th><f:message key="label.order.customer"/> name</th>
                <th><f:message key="label.address"/></th>
                <th><f:message key="label.phone"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><f:formatDate value="${order.dateCreated}" pattern="yyyy-MM-dd"/></td>
                <td><span class="order-${fn:toLowerCase(order.state)}">${order.state}</span></td>
                <td><c:out value="${order.user.email}"/></td>
                <td><c:out value="${order.user.name}"/></td>
                <td><c:out value="${order.address}"/></td>
                <td><c:out value="${order.phone}"/></td>
            </tr>
            <th colspan="7">
            </tbody>
        </table>
   <div class="row">
        <c:if test="${order.state=='RECEIVED'}">
            <sec:authorize access="hasAuthority('ADMIN')">
            <div class="col-xs-1">
            <form method="get" action="${pageContext.request.contextPath}/orders/finish/${order.id}">
                <button type="submit" class="btn btn-primary">Finish</button>
            </form>
            </div>
            <div class="col-xs-1">
            <form method="get" action="${pageContext.request.contextPath}/orders/cancel/${order.id}">
                <button type="submit" class="btn btn-danger"><f:message key="button.cancel"/></button>
            </form>
            </div>
            </sec:authorize>
        </c:if>
        <div class="col-xs-1">
            <form method="post" action="${pageContext.request.contextPath}/orders/delete/${order.id}">
            <sec:csrfInput/>
                <button type="submit" class="btn btn-danger"><f:message key="button.delete"/></button>
            </form>
     </div>
   </div>
    <span><br></span>        
    <table class="table">
        <caption>Tires</caption>
        <thead>
        <tr>
            <th><f:message key="label.name"/></th>
            <th><f:message key="label.tire.type"/></th>
            <th><f:message key="label.tire.size"/></th>
            <th><f:message key="label.tire.manufacturer"/></th>
            <th><f:message key="label.tire.vehicle"/></th>
            <th><f:message key="label.count"/></th>
            <th><f:message key="label.price"/></th>
            <th><f:message key="label.total"/></th>
        </tr>
        </thead>
        <tbody>
            
            <c:set var="tire" value="${order.tire}"/>            
            <c:set var="tireTotal" value="${tire.price*order.tireQuantity}"/>

            <tr>
                <td>${tire.name}</td>
                <td>${tire.tireType}</td>
                <td>${tire.size}</td>
                <td>${tire.manufacturer}</td>
                <td>${tire.vehicleType}</td>
                <td>${order.tireQuantity}</td>                
                <td>${tire.price}</td>
                
                <td>${tireTotal}</td>
            </tr>
        <tr>
            <th colspan="9"></th>
       
        </tr>
        </tbody>
    </table>
            
    <c:if test="${not empty order.additionalServices}">
    <table class="table">
        <caption><f:message key="label.additional.services"/></caption>
        <thead>
        <tr>
            <th><f:message key="label.name"/></th>
            <th><f:message key="label.description"/></th>
            <th><f:message key="label.price"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${order.additionalServices}" var="item">
            <tr>
                <td>${item.name}</td>
                <td>${item.description}</td>
                <td><c:out value="${item.price}"/></td> 

            </tr>
        </c:forEach>        
        </tbody>
    </table>
    </c:if>
    <table class="table">
        <tr>
            <th colspan="4"><b><f:message key="label.order.total.price"><f:param value="${order.price}"/></f:message></b></th>
        </tr>
    </table>

</jsp:attribute>
</my:template>
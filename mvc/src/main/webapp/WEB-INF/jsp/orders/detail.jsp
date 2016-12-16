<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%--.
  Author: xtravni2
--%>

<my:template title="Order ${order.id}">
<jsp:attribute name="body">


        <table class="table">
            <caption>Order</caption>
            <thead>
            <tr>
                <th>id</th>
                <th>placed</th>
                <th>state</th>
                <th>email</th>
                <th>customer name</th>
                <th>address</th>
                <th>phone</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${order.id}</td>
                <td><fmt:formatDate value="${order.dateCreated}" pattern="yyyy-MM-dd"/></td>
                <td><b style="color: red;">${order.state}</b></td>
                <td><c:out value="${order.user.email}"/></td>
                <td><c:out value="${order.user.name}"/></td>
                <td><c:out value="${order.address}"/></td>
                <td><c:out value="${order.phone}"/></td>
            </tr>
            <th colspan="7">
            </tbody>
        </table>

    <div class="row">
    <c:choose>
        <c:when test="${order.state=='RECEIVED'}">
            <div class="col-xs-1">
            <form method="get" action="${pageContext.request.contextPath}/orders/finish/${order.id}">
                <button type="submit" class="btn btn-primary">Finish</button>
            </form>
            </div>
            <div class="col-xs-1">
            <form method="get" action="${pageContext.request.contextPath}/orders/cancel/${order.id}">
                <button type="submit" class="btn btn-danger">Cancel</button>
            </form>
            </div>
        </c:when>        
    </c:choose>
        <div class="col-xs-1">
            <form method="post" action="${pageContext.request.contextPath}/orders/delete/${order.id}">
            <sec:csrfInput/>
                <button type="submit" class="btn btn-danger">Delete</button>
            </form>
    </div>

    <table class="table">
        <caption>Tires</caption>
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>type</th>
            <th>size</th>
            <th>manufacturer</th>
            <th>vehicle</th>
            <th>count</th>
            <th>price</th>
            <th>total</th>
        </tr>
        </thead>
        <tbody>
            
            <c:set var="tire" value="${order.tire}"/>            
            <c:set var="tireTotal" value="${tire.price*order.tireQuantity}"/>
            <c:set var="total" value="${tireTotal}"/>

            <tr>
                <td>${tire.id}</td>
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
    <table class="table">
        <caption>Additional service</caption>
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>description</th>
            <th>price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${order.additionalServices}" var="item">
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.description}</td>
                <td><c:out value="${item.price}"/></td> 
                <c:set var="total" value="${total+item.price}"/>

            </tr>
        </c:forEach>
        <tr>
            <th colspan="4"><b>Order Total:</b><c:out value="${total}"/></th>
       
        </tr>
        </tbody>
    </table>

</jsp:attribute>
</my:template>
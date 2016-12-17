<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<my:template title="${user.name} - New Order">
<jsp:attribute name="body">
<div class="col-lg-12">
    <form:form id="${order.id}" method="post" modelAttribute="orderForm"
               action="${pageContext.request.contextPath}/orders/save">
    <div class="form-group">
        <label for="tireId">Tire type</label>
        <form:select path="tireId" cssClass="form-control" id="tireId">
            <form:options items="${tireList}"/>
        </form:select>
    </div>
    
    <div class="form-group">
        <label for="tireQuantity">Count</label>
        <form:input path="order.tireQuantity" cssClass="form-control" id="tireQuantity" placeholder="1"/>
    </div>
    
    <div class="form-group">
        <label for="address">Address</label>
        <form:input path="order.address" cssClass="form-control" id="address" placeholder="Address"/>
    </div>

    <div class="form-group">
        <label for="phone">Address</label>
        <form:input path="order.phone" cssClass="form-control" id="phone" placeholder="Phone"/>
    </div>
   
    <button type="submit" class="btn btn-default">Submit</button>
    </form:form>
</div>
</jsp:attribute>
</my:template>

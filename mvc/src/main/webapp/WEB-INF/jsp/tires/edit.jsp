<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<my:template title="${tire.id == null ? 'New tire' : 'Edit tire'}">
<jsp:attribute name="body">
<div class="col-lg-12">
    <form:form id="${tire.id}" method="post" modelAttribute="tire"
               action="${pageContext.request.contextPath}/tires/save">
    <form:hidden path="id"  style="display:none"/>
    <div class="form-group">
        <label for="name">Name</label>
        <form:input path="name" cssClass="form-control" id="name" placeholder="Name"/>
    </div>
    <div class="form-group">
        <label for="tireType">Tire type</label>
        <form:select path="tireType" cssClass="form-control" id="tireType">
            <form:options/>
        </form:select>
    </div>
    <div class="form-group">
        <label for="size">Size</label>
        <form:input path="size" cssClass="form-control" id="size" placeholder="Size"/>
    </div>
    <div class="form-group">
        <label for="name">Manufacturer</label>
        <form:input path="manufacturer" cssClass="form-control" id="manufacturer" placeholder="Manufacturer"/>
    </div>
    <div class="form-group">
        <label for="name">Vehicle type</label>
        <form:input path="vehicleType" cssClass="form-control" id="vehicleType" placeholder="Vehicle type"/>
    </div>
    <div class="form-group">
        <label for="price">Price</label>
        <div class="input-group">
            <form:input path="price" cssClass="form-control" id="price" placeholder="Price"
                        aria-describedby="currency-addon"/>
            <span class="input-group-addon" id="currency-addon">Kc</span>
        </div>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
    </form:form>
</div>
</jsp:attribute>
</my:template>

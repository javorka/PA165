<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<my:template title="${user.name} - New Order">
<jsp:attribute name="body">
<div class="col-lg-12">
    <form:form id="${order.id}" method="post" modelAttribute="order"
               action="${pageContext.request.contextPath}/orders/save">
    <form:hidden path="id"  style="display:none"/>
    
    <div class="form-group">
        <label for="tires">Tire type</label>
        <form:select path="tire" cssClass="form-control" id="tires">
            <form:options items="${tireList}"/>
        </form:select>
    </div>
   
    <button type="submit" class="btn btn-default">Submit</button>
    </form:form>
</div>
</jsp:attribute>
</my:template>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: xjavorka
  Date: 14.12.16
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<my:template title="Tires">
<jsp:attribute name="body">
<div class="modal fade" id="confirm-delete-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <form class="delete-confirm" method="post">
            <sec:csrfInput/>
            <div class="modal-content">
                <div class="modal-header">
                    <h4>Confirmation</h4>
                </div>
                <div class="modal-body">
                    <p>Do you really want to delete a tire with the name <span class="name-placeholder"></span> ?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <input type="submit" value="Delete" class="btn btn-danger">
                </div>
            </div>
        </form>
    </div>
</div>

<table class="table">
    <thead>
        <tr>
            <th>Name</th>
            <th>Tire Type</th>
            <th>Size</th>
            <th>Manufacturer</th>
            <th>Vehicle type</th>
            <th>Price</th>
            <th>Operation</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${tires}" var="ts">
        <tr>
            <td><c:out value="${ts.name}"/></td>
            <td><c:out value="${ts.tireType}"/></td>
            <td><c:out value="${ts.size}"/></td>
            <td><c:out value="${ts.manufacturer}"/></td>
            <td><c:out value="${ts.vehicleType}"/></td>
            <td><c:out value="${ts.price}"/></td>
            <td>
                <a href="${pageContext.request.contextPath}/tires/edit/${ts.id}"
                   class="btn btn-default">Edit</a>
                <a class="btn btn-danger" data-href="${pageContext.request.contextPath}/tires/delete/${ts.id}"
                   data-toggle="modal" data-target="#confirm-delete-modal" data-name="${ts.name}">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="text-right border-top">
    <a href="${pageContext.request.contextPath}/tires/new" class="btn btn-primary">Add tire</a>
</div>

</jsp:attribute>
<jsp:attribute name="script">
    <script>
        $('#confirm-delete-modal').on('show.bs.modal', function(e) {
            $(this).find('.delete-confirm').attr('action', $(e.relatedTarget).data('href'));
            $(this).find('.name-placeholder').text($(e.relatedTarget).data('name'));
        });
    </script>
</jsp:attribute>
</my:template>
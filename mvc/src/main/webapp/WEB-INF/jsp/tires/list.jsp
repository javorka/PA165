<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<my:template title="${selected == 1 ? 'All tires' : 'Best selling tires'}">
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

<div class="tire-filters">
    <div class="row">
        <div class="col-lg-6">
            <h2>List tires</h2>
            <select class="form-control" id="list-tires">
                <option ${selected == 1 ? 'selected' : ''} value="${pageContext.request.contextPath}/tires/">All tires
                </option>
                <option ${selected == 2 ? 'selected' : ''}
                        value="${pageContext.request.contextPath}/tires/three-best-selling">Best selling
                </option>
            </select>
        </div>
        <div class="col-lg-6">

        </div>
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
        <th></th>
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
            <sec:authorize access="hasAuthority('ADMIN')">
            <td>
                <a href="${pageContext.request.contextPath}/tires/edit/${ts.id}"
                   class="btn btn-default">Edit</a>
                <a class="btn btn-danger" data-href="${pageContext.request.contextPath}/tires/delete/${ts.id}"
                   data-toggle="modal" data-target="#confirm-delete-modal" data-name="${ts.name}">Delete</a>
            </td>
            </sec:authorize>
        </tr>
    </c:forEach>
    </tbody>
</table>
<sec:authorize access="hasAuthority('ADMIN')">
<div class="text-right border-top createButtonWrapper">
    <a href="${pageContext.request.contextPath}/tires/new" class="btn btn-primary">Add tire</a>
</div>
</sec:authorize>

</jsp:attribute>
    <jsp:attribute name="script">
    <script>
        $(function () {
            $('#list-tires').on('change', function () {
                var url = $(this).val();
                if (url) {
                    window.location = url;
                }
                return false;
            });
        });
        $('#confirm-delete-modal').on('show.bs.modal', function (e) {
            $(this).find('.delete-confirm').attr('action', $(e.relatedTarget).data('href'));
            $(this).find('.name-placeholder').text($(e.relatedTarget).data('name'));
        });
    </script>
</jsp:attribute>
</my:template>
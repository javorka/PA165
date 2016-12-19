<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<my:template titleMessageKey="${selected == 1 ? 'title.tire.list' : 'title.tire.list.best'}" activeNav="tires">
<jsp:attribute name="body">
<div class="modal fade" id="confirm-delete-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <form class="delete-confirm" method="post">
            <sec:csrfInput/>
            <div class="modal-content">
                <div class="modal-header">
                    <h4><f:message key="label.confirmation"/></h4>
                </div>
                <div class="modal-body">
                    <p><f:message key="label.tire.delete.confirmation"/> <span class="name-placeholder"></span>?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><f:message
                            key="button.cancel"/></button>
                    <input type="submit" value="Delete" class="btn btn-danger">
                </div>
            </div>
        </form>
    </div>
</div>

<div class="tire-filters">
    <div class="row">
        <div class="col-lg-6">
            <select class="form-control" id="list-tires">
                <option ${selected == 1 ? 'selected' : ''} value="${pageContext.request.contextPath}/tires/"><f:message
                        key="title.tire.list"/>
                </option>
                <option ${selected == 2 ? 'selected' : ''}
                        value="${pageContext.request.contextPath}/tires/three-best-selling"><f:message
                        key="label.tire.best.selling"/>
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
        <th><f:message key="label.name"/></th>
        <th><f:message key="label.tire.type"/></th>
        <th><f:message key="label.tire.size"/></th>
        <th><f:message key="label.tire.manufacturer"/></th>
        <th><f:message key="label.tire.vehicle"/></th>
        <th><f:message key="label.price"/></th>
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
            <td><a href="${pageContext.request.contextPath}/orders/create/${ts.id}"
                   class="btn btn-primary"><f:message key="label.order"/></a>
            <sec:authorize access="hasAuthority('ADMIN')">
                <a href="${pageContext.request.contextPath}/tires/edit/${ts.id}"
                   class="btn btn-default"><f:message key="button.edit"/></a>
                <a class="btn btn-danger" data-href="${pageContext.request.contextPath}/tires/delete/${ts.id}"
                   data-toggle="modal" data-target="#confirm-delete-modal" data-name="${ts.name}"><f:message
                        key="button.delete"/></a>
            </sec:authorize>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<sec:authorize access="hasAuthority('ADMIN')">
<div class="text-right border-top createButtonWrapper">
    <a href="${pageContext.request.contextPath}/tires/new" class="btn btn-primary"><f:message key="label.tire.add"/></a>
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
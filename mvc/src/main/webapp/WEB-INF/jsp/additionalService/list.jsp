<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: xjavorka
  Date: 14.12.16
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<my:template title="Additional services">
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
                    <p>Do you really want to delete an Additional service with the name <span class="name-placeholder"></span> ?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <input type="submit" value="Delete" class="btn btn-danger">
                </div>
            </div>
        </form>
    </div>
</div>
<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Operation</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${additionalServices}" var="as">
            <tr>
                <td><c:out value="${as.name}"/></td>
                <td><c:out value="${as.description}"/></td>
                <td><c:out value="${as.price}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/additionalService/edit/${as.id}"
                       class="btn btn-default">Edit</a>
                    <a class="btn btn-danger" data-href="${pageContext.request.contextPath}/additionalService/delete/${as.id}"
                       data-toggle="modal" data-target="#confirm-delete-modal" data-name="${as.name}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="text-right border-top createButtonWrapper">
        <a href="${pageContext.request.contextPath}/additionalService/new" class="btn btn-primary">Add Additional service</a>
    </div>
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:template title="Users">
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
                    <p>Do you really want to delete person with email <span class="email-placeholder"></span> ?</p>
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
            <th>Email</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/user/edit/${user.id}" class="btn btn-primary">Edit</a>
                    <a class="btn btn-danger" data-href="${pageContext.request.contextPath}/user/delete/${user.id}"
                       data-toggle="modal" data-target="#confirm-delete-modal" data-email="${user.email}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</jsp:attribute>
<jsp:attribute name="script">
    <script>
        $('#confirm-delete-modal').on('show.bs.modal', function(e) {
            $(this).find('.delete-confirm').attr('action', $(e.relatedTarget).data('href'));
            $(this).find('.email-placeholder').text($(e.relatedTarget).data('email'));
        });
    </script>
</jsp:attribute>
</my:template>

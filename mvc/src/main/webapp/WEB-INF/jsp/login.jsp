<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:template title="Login">
<jsp:attribute name="body">
<c:if test="${param.error ne null}">
    <div class="alert alert-danger" role="alert">Failed to sign in</div>
</c:if>
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <form action="${pageContext.request.contextPath}/login" method="post" class="">
                <sec:csrfInput/>
                <div class="form-group"><label for="username"> Email address : <input type="text" class="form-control"
                                                                                  name="username" id="username"/>
                </label></div>
                <div class="form-group"><label for="password"> Password: <input type="password" class="form-control"
                                                                                name="password" id="password"/> </label>
                </div>
                <div><input type="submit" class="btn btn-default" value="Sign In"/></div>
            </form>
        </div>
    </div>
</jsp:attribute>
</my:template>

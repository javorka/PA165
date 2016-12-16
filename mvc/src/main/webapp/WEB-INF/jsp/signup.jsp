<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="Registration">
<jsp:attribute name="body">
<div class="container">
    <div class="col-md-6">
        <form:form method="post" modelAttribute="user" action="${pageContext.request.contextPath}/signup">
            <div class="form-group">
                <label for="email">Email </label>
                <form:input path="email" cssClass="form-control" id="email" placeholder="Email"/>
                <form:errors path="email" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="name">Name </label>
                <form:input path="name" cssClass="form-control" id="name" placeholder="Name"/>
                <form:errors path="name" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="password">Password </label>
                <form:password path="password" cssClass="form-control" id="password" placeholder="Password"/>
                <form:errors path="password" cssClass="text-danger" />
            </div>
            <button type="submit" class="btn btn-default">Register</button>
        </form:form>
    </div>
</div>
</jsp:attribute>
</my:template>

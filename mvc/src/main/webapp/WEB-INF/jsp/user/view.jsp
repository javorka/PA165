<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="User">
<jsp:attribute name="body">

<div class="container">
    <div class="jumbotron">
        <h1>Hi!</h1>
        <p>Welcome, ${user}</p>
        <p>Check this <a href="${pageContext.request.contextPath}/user/edit">edit</a> and <a href="${pageContext.request.contextPath}/user/list">list</a></p>
    </div>
    <p>This is some text.</p>
    <p>This is another text.</p>
</div>
</jsp:attribute>
</my:template>

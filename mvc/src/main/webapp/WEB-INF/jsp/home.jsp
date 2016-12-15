<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<%--
  Created by IntelliJ IDEA.
  User: xjavorka
  Date: 14.12.16
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="Home">
<jsp:attribute name="body">

<div class="container">
    <div class="jumbotron">
        <h1>Hi!</h1>
        <p>Welcome, ${user}</p>
    </div>
    <p>This is some text.</p>
    <p>This is another text.</p>
</div>
</jsp:attribute>
</my:template>

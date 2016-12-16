<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="Home">
<jsp:attribute name="body">

<div class="container">
    <div class="jumbotron">
        <h1>Welcome to TIRESHOP (TM)!</h1>
        <p>Welcome, ${user}</p>
    </div>
    <p>Feel free to browse our selection of tires & additional services.</p>
</div>
</jsp:attribute>
</my:template>

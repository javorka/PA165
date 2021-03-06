<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="titleMessageKey" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ attribute name="script" fragment="true" required="false" %>
<%@attribute name="activeNav" required="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <c:choose>
        <c:when test="${not empty titleMessageKey}">
            <title><f:message key="${titleMessageKey}"/></title>
        </c:when>
        <c:otherwise>
            <title><c:out value="${title}"/></title>
        </c:otherwise>
    </c:choose>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
    <jsp:invoke fragment="head"/>
</head>
<body>
<form action="${pageContext.request.contextPath}/logout" method="POST" id="logoutForm">
    <sec:csrfInput/>
</form>
<!-- navigation bar -->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/"><f:message key="navigation.project"/> </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="${activeNav eq "home" ? "active" : ""}"><a href="${pageContext.request.contextPath}/"><f:message key="navigation.home"/></a></li>
                <li class="${activeNav eq "tires" ? "active" : ""}"><a href="${pageContext.request.contextPath}/tires/"><f:message key="navigation.tires"/></a></li>
                <li class="${activeNav eq "as" ? "active" : ""}"><a href="${pageContext.request.contextPath}/additionalService/"><f:message key="navigation.additional.services"/></a></li>
                <sec:authorize access="hasAuthority('ADMIN')">
                <li class="${activeNav eq "userManagement" ? "active" : ""}"><a href="${pageContext.request.contextPath}/user/list"><f:message key="navigation.admin.user.management"/></a></li>
                <li class="${activeNav eq "orderManagement" ? "active" : ""}"><a href="${pageContext.request.contextPath}/orders/all"><f:message key="navigation.admin.orders.management"/></a></li>
                </sec:authorize>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="isAuthenticated()">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false"><sec:authentication property="principal.username"/><span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.request.contextPath}/orders/"><f:message key="navigation.user.orders"/></a></li>
                            <li><a href="${pageContext.request.contextPath}/user/edit"><f:message key="navigation.user.profile"/></a></li>
                            <li role="separator" class="divider"></li>
                            <li>
                                <a href="#" onclick="document.getElementById('logoutForm').submit();"><f:message key="button.logout"/></a>
                            </li>
                        </ul>

                    </li>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    <li><a href="${pageContext.request.contextPath}/login"><f:message key="button.login"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/signup"><f:message key="button.signup"/></a></li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <!-- page title -->
            <c:choose>
                <c:when test="${not empty titleMessageKey}">
                    <div class="page-header">
                        <h1><f:message key="${titleMessageKey}"/></h1>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${not empty title}">
                        <div class="page-header">
                            <h1><c:out value="${title}"/></h1>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>

        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <!-- alerts -->
            <c:if test="${not empty alert_danger}">
                <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <c:out value="${alert_danger}"/></div>
            </c:if>
            <c:if test="${not empty alert_info}">
                <div class="alert alert-info" role="alert"><c:out value="${alert_info}"/></div>
            </c:if>
            <c:if test="${not empty alert_success}">
                <div class="alert alert-success" role="alert"><c:out value="${alert_success}"/></div>
            </c:if>
            <c:if test="${not empty alert_warning}">
                <div class="alert alert-warning" role="alert"><c:out value="${alert_warning}"/></div>
            </c:if>
        </div>
    </div>

    <!-- page body -->
    <div class="row">
        <div class="col-lg-12">
            <jsp:invoke fragment="body"/>
        </div>
    </div>

    <!-- footer -->
    <div class="row">
        <div class="col-lg-12">
            <footer class="footer">
                <hr/>
                <div class="container">
                    <p class="text-muted">&copy; FI MUNI 2016</p>
                    <b>Authors:</b>
                    <ul>
                        <li>Krajčovič Michal</li>
                        <li>Peter Javorka</li>
                        <li>Spišiak Martin</li>
                        <li>Trávníček Michal</li>
                    </ul>
                </div>
            </footer>
        </div>
    </div>
</div>

<!-- javascripts placed at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<jsp:invoke fragment="script"/>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Quotation</title>
</head>
<body>
    <ul>
        <c:forEach items="${messages}" var="message">
            <li><c:out value="${message}"></c:out></li>
        </c:forEach>
        <li><a href="${ctx}/login">[<spring:message code="Login_Label_Login" />]</a></li>
    </ul>
</body>
</html>
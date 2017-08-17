<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
            <li><span style="color: red; "><c:out value="${message}"></c:out></span></li>
        </c:forEach>
    </ul>
	
	<div>
		<ul>
			<c:forEach items="${exception.stackTrace}" var="stackTrace">
				<li><c:out value="${stackTrace}"></c:out></li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>
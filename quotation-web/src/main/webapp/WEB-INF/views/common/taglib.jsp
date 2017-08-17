<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="q" uri="/quo" %>
<%@ taglib prefix="pagination" uri="/WEB-INF/tag/pagetag.tld" %>
<%@ taglib prefix="grid" uri="/WEB-INF/tag/gridtag.tld" %>
<%@ taglib prefix='sec' uri="http://www.springframework.org/security/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<c:set var="locale" value="${sessionScope.get('org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE')}"/>
<sec:authentication property="principal" var="userContext"/>
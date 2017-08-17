<%--
  Created by IntelliJ IDEA.
  User: yuan_chen1
  Date: 2017/7/11
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="../common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${ctx}/resources/themes/css/login.css" rel="stylesheet"
          type="text/css" media="screen"/>
    <script src="${ctx}/resources/js/core/jquery.min.js"></script>
    <title>Quotation</title>

    <link rel="shortcut icon" href="${ctx}/resources/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${ctx}/resources/favicon.ico" type="image/x-icon">

</head>
<body>
<form:form name="loginForm" id="loginForm"
           action="${ctx}/user/login" method="post">
    <table border="0" cellpadding="0" cellspacing="10" width="484px"
           align="center">
        <tr>
            <td colspan="2" height="50px"></td>
        </tr>
        <tr class="logo">
            <td colspan="2" nowrap="nowrap"><span>Quotation System<br></span></td>
        </tr>
        <tr>
            <td height="314px" colspan="2" class="login_box">
                <table border="0" cellpadding="0" cellspacing="0" width="484"
                       height="100%" align="center">
                    <tr>
                        <td colspan="2" height="50px" class="header"><span><spring:message
                                code="Login_Label_PageTitle"/></span></td>
                    </tr>
                    <tr>
                        <td width="180px" align="right" class="title"><span><spring:message
                                code="Login_Label_LoginId"/>:&nbsp;</span></td>
                        <td align="left" class="inputbox"><input type="text"
                                                                 name="loginId" id="loginId" value="admin" maxlength="20"/></td>
                    </tr>
                    <tr>
                        <td align="right" class="title"><span><spring:message
                                code="Login_Label_Pwd"/>:&nbsp;</span></td>
                        <td align="left" class="inputbox"><input type="password"
                                                                 name="password" id="pwd" value="12345" maxlength="20"/></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="button"
                                               value='<spring:message code="Login_Label_Login" />'
                                               onclick="doSubmit()" id="loginbtn"/></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form:form>
<c:if test="${requestScope.get('error') != null}">
    <div style="margin-top: 20px;color: white;"><spring:message code="LOGIN_FAILED"/></div>
    <div style="color: #fff;margin-top: 10px;">测试用户名密码：admin/123456</div>
</c:if>
<script>
    function doSubmit() {
        $("#loginForm").submit();

        <%--var loginId = $("#name").val();--%>
        <%--$.ajax({--%>
        <%--type : "post",--%>
        <%--url : "${ctx}/login/check",--%>
        <%--data : {"loginId": loginId},--%>
        <%--success : function() {--%>
        <%----%>
        <%--location.href = "${ctx}/main";--%>
        <%--}--%>
        <%--});--%>
    }
</script>
</body>
</html>
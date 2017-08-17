<%--
  Created by IntelliJ IDEA.
  User: yuan_chen1
  Date: 2017/7/26
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common/taglib.jsp" %>

<head>
</head>

<div id="layout">
    <h1 class="contentTitle">Test SampleList</h1>
    <div id="cocontent">
        <div>
            <form:form id="formsample" name="formsample" class="required-validate"
                       action="${ctx}/sp/test/list1" method="post">
                <table style="width:100%" targetType="navTab" class="list">
                    <thead>
                    <tr>
                        <th style="width:10%;" align="left">
                            <div> SAMPLE ID</div>
                        </th>
                        <th style="width:30%;" align="left">
                            <div> SAMPLE CODE</div>
                        </th>
                        <th style="width:45%;" align="left">
                            <div> SAMPLE CONTENT</div>
                        </th>
                        <th style="width:15%;" align="left">
                            <div> OPERATION</div>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="list" items="${samples}">
                        <tr>
                            <td><c:out value="${list.sampleId}"/></td>
                            <td><c:out value="${list.sampleCode}"/></td>
                            <td><c:out value="${list.sampleContent}"/></td>
                            <td>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form:form>
        </div>
    </div>

</div>
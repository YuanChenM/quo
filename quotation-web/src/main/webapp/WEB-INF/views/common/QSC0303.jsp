<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common/taglib.jsp" %>
<head>
    <style type="text/css">
        .content_text{
            padding: 30px;
        }
        .content_text .paragraph {
            padding-left: 2px;
            width: 1600px;
            border: 1px black solid;
            margin-top: 10px;
            background-color: white;
            min-height: 100px;
            padding-top: 5px;
            margin-bottom: 40px;
        }
        .info_title {
            font-size: 20px;
            font-weight: bold;
        }

        .publish_info {
            float: right;
            font-size: 12px;
        }
    </style>
</head>
<div id="layout">
    <div class="pageHeader">
        <spring:message code="QSC0303_Label_PageTitle"/>
    </div>
    <div id="cocontent">
        <div class="content_text">
            <c:forEach var="item" items="${informationList}">
                <div>
                    <span class="info_title">${item.title}</span>
                    <div class="publish_info">
                        <div>Published By ${item.userName}</div>
                        <div style="margin-top: 4px;text-align: right;">
                            <fmt:formatDate pattern="dd MMM YYYY HH:mm:ss" value="${item.updatedDate}" />
                        </div>
                    </div>
                </div>
                <div class="paragraph">
                    ${item.information}
                </div>
            </c:forEach>
        </div>
    </div>
</div>


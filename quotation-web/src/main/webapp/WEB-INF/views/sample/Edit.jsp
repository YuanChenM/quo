<%--
  Created by IntelliJ IDEA.
  User: yuan_chen1
  Date: 2017/7/19
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglib.jsp" %>
<head>
    <script type="text/javascript">
        // submit Form
        function onClickSubmitButton(){
            alertMsg._clearCall();
            var canSubmit = true;
            var sampleCode = $("#sampleCode").val();
            var sampleContent = $("#sampleContent").val();

            if(sampleCode === ""){
                alertMsg.error("SampleCode is not allowed to empty!");
                document.getElementById("sampleCode").parentElement.style.border = "1px solid red";
                canSubmit = false;
            }

            if(sampleContent === ""){
                alertMsg.error("SampleContent is not allowed to empty!");
                document.getElementById("sampleContent").parentElement.style.border = "1px solid red";
                canSubmit = false;
            }

            if(canSubmit){
                $('#EditForm').submit();
            }
        }

        // back
        function backButtonFunction(){
            var url = "${ctx }/sp/list";
            navTab.reload(url);
        }
    </script>
</head>
<div id="layout">
    <h1 class="contentTitle">Sample Add/Edit</h1>
    <div id="content">
        <form:form name="EditForm" commandName="entity" id="EditForm" onsubmit="return navTabSearch(this)" class="required-validate" action="${ctx}/sp/edit/save" method="post">
            <input type="hidden" name="sampleUid" value="${entity.sampleUid}" />
            <table style="width: 100%;">
                <tr>
                    <td class="inputlab" style="width:10%;">
                        <label>sampleId:</label>
                    </td>
                    <td>
                        <form:input path="sampleId" value="${entity.sampleId}" readonly="true"></form:input>
                    </td>
                </tr>
                <tr>
                    <td class="inputlab">
                        <label>sampleCode:</label>
                    </td>
                    <td>
                        <span style="padding-bottom: 2px; padding-top: 2px; padding-left: 0; padding-right: 0">
                            <form:input path="sampleCode" value="${entity.sampleCode}" ></form:input>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td class="inputlab">
                        <label>sampleContent:</label>
                    </td>
                    <td>
                        <span style="padding-bottom: 2px; padding-top: 2px; padding-left: 0; padding-right: 0">
                            <form:input path="sampleContent" value="${entity.sampleContent}" ></form:input>
                        </span>
                    </td>
                </tr>
            </table>
        </form:form>
        <div class="panelBar">
            <div class="toolBar">
                <input type="button" value='save' class="button" style="width:80px" onclick="onClickSubmitButton();"/>
                <input type="button" value='back' class="button" style="width:80px" onclick="backButtonFunction();"/>
            </div>
        </div>
    </div>

</div>
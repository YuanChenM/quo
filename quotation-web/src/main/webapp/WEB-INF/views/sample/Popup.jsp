<%--
  Created by IntelliJ IDEA.
  User: yuan_chen1
  Date: 2017/7/19
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/taglib.jsp" %>

<script type="text/javascript">
    var succuess = '${isSuccess}';
    if (succuess) {
        navTab.reload("${ctx}/sp/list");
        $.pdialog.closeCurrent();
    }

    // submit Form
    function onClickSubmitButton(){
        $('#PopupForm').submit();
    }
</script>

<div id="layout">
    <h1 class="contentTitle">Sample Add/Edit</h1>
    <div id="content">
        <form:form name="PopupForm" commandName="entity" onsubmit="return dialogSearch(this)" id="PopupForm" class="required-validate" action="${ctx}/sp/popup/save">
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
                        <form:input path="sampleCode" value="${entity.sampleCode}" ></form:input>
                    </td>
                </tr>
                <tr>
                    <td class="inputlab">
                        <label>sampleContent:</label>
                    </td>
                    <td>
                        <form:input path="sampleContent" value="${entity.sampleContent}" ></form:input>
                    </td>
                </tr>
            </table>
        </form:form>
        <div class="panelBar">
            <div class="toolBar">
                <input type="button" value='save' class="button" style="width:80px" onclick="onClickSubmitButton();"/>
                <input type="button" value='back' class="button" style="width:80px" onclick="javascript:$.pdialog.closeCurrent();" />
            </div>
        </div>
    </div>
    
</div>
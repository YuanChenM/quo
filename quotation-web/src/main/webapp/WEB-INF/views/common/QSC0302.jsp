<%--
  Created by IntelliJ IDEA.
  User: zhu_yingjie
  Date: 2017/8/10
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@include file="./taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="pageHeader">
    <spring:message code="QSC0302_Label_PageTitle"/>
</div>
<div class="pageContent">
    <div class="panelBar" style="height: 40px;">
        <ul class="toolBar" style="padding-top: 10px;padding-left: 20px;">
            <c:if test="${dataModel == QuotationConst.ScreenMode.VIEW}">
                <a class="button" style="float: none;display: inline-block;" onclick="modifyInformation()">
                    <span style="padding: 0 20px;"><spring:message code="QSC0302_Button_Modify"/> </span>
                </a>
            </c:if>
            <c:if test="${dataModel != QuotationConst.ScreenMode.VIEW}">
                <a class="button" style="float: none;display: inline-block;" id="btn_save" onclick="saveInformation()">
                    <span style="padding: 0 20px;"><spring:message code="QSC0302_Button_Save"/> </span>
                </a>
                <a class="button" style="float: none;display: inline-block;" id="btn_clear" onclick="clearForm()">
                    <span style="padding: 0 20px;"><spring:message code="QSC0302_Button_Clear"/> </span>
                </a>
            </c:if>
        </ul>
    </div>
    <div style="width: 100%;height: 20px;"></div>
    <input type="hidden" id="dateFromDefault" value="<fmt:formatDate value="${qsc0302Entity.dateFrom}" pattern="d/M/yyyy"/>"/>
    <input type="hidden" id="dateToDefault" value="<fmt:formatDate value="${qsc0302Entity.dateTo}" pattern="d/M/yyyy"/>"/>
    <form:form id="detailForm" commandName="qsc0302Entity" action="${ctx}/QSC0302/save" method="post"
               onsubmit="return navTabSearch(this)">
        <input type="hidden" name="informationId" value="${qsc0302Entity.informationId}">
        <table width="50%">
            <tr>
                <td class="inputlab"><spring:message code="QSC0302_Label_Title"/></td>
                <td colspan="2">
                    <form:input type="text" path="title" cssStyle="width:${ScreenDefault.filterInputWidth}"
                                editmodel="${dataModel != QuotationConst.ScreenMode.VIEW}"/>
                </td>
            </tr>
            <tr></tr>
            <tr>
                <td class="inputlab"><spring:message code="QSC0302_Label_Information"/></td>
                <td colspan="2">
                    <form:textarea type="text" path="information" cssStyle="width:${ScreenDefault.filterInputWidth}"
                                   editmodel="${dataModel != QuotationConst.ScreenMode.VIEW}" rows="5" cols="30"/>
                </td>
            </tr>
            <tr></tr>
            <tr>
                <td class="inputlab"><spring:message code="QSC0302_Label_EffectiveDate"/></td>
                <td>
                    <form:input type="text"  path="dateFrom" cssClass="date" size="12"
                                format="d/M/yyyy" cssStyle="width: 100%;" editmodel="${dataModel != QuotationConst.ScreenMode.VIEW}"/>
                    <a class="inputDateButton" href="javascript:;"></a>
                    ~
                </td>
                <td>
                    <form:input type="text"  path="dateTo" cssClass="date" size="12"
                                format="d/M/yyyy" cssStyle="width: 100%" editmodel="${dataModel != QuotationConst.ScreenMode.VIEW}"/>
                    <a class="inputDateButton" href="javascript:;"></a>
                </td>
            </tr>
        </table>
    </form:form>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        setTimeout(function () {
            $("#dateFrom").val($("#dateFromDefault").val());
            $("#dateTo").val($("#dateToDefault").val());
        }, 100);

        if (${isSuccess}) {
            alertMsg.correct("<spring:message code="i0001" arguments="Save"/>");
        }
    });
    function modifyInformation() {
        var url = "${ctx}/QSC0302/edit/${qsc0302Entity.informationId}";
        navTab.reload(url);
    }
    function saveInformation() {
        alertMsg._clearCall();

        if (${dataModel == QuotationConst.ScreenMode.ADD}) {
            var dateFrom = $("#dateFrom").val();
            var dateTo = $("#dateTo").val();
            if (isAfter(dateFrom, dateTo)) {
                alertMsg.error("<spring:message code="w1007"/>");
                $("#dateFrom").css("border","1px solid red");
                $("#dateTo").css("border", "1px solid red");
            } else {
                $("#detailForm").submit();
            }
        } else {
            var action = "${ctx}/QSC0302/update";
            $("#detailForm").attr("action", action);
            $("#detailForm").submit();
        }
    }
    function isAfter(begin, end) {
        var beginArr = begin.split("/");
        var endArr = end.split("/");
        var beginTime = new Date(beginArr[1] + "/" + beginArr[0] + "/" + beginArr[2]);
        var endTime = new Date(endArr[1] + "/" + endArr[0] + "/" + endArr[2]);
        if (beginTime > endTime) {
            return true;
        } else {
            return false;
        }
    }
</script>
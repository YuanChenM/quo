<%--
  Created by IntelliJ IDEA.
  User: zhu_yingjie
  Date: 2017/8/9
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@include file="./taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="pageHeader">
    <spring:message code="QSC0301_Label_PageTitle"/>
</div>
<div class="pageContent">
    <div class="panelBar" style="height: 40px;">
        <ul class="toolBar" style="padding-top: 10px;padding-left: 20px;">
            <a class="button" style="float: none;display: inline-block;" id="btn_new" onclick="createNew()">
                <span style="padding: 0 20px;"><spring:message code="QSC0301_Button_CreateNew"/> </span>
            </a>
            <a class="button" style="float: none;display: inline-block;" id="btn_del" onclick="deleteInformations()">
                <span style="padding: 0 20px;"><spring:message code="QSC0301_Button_Delete"/> </span>
            </a>
        </ul>
    </div>
    <div style="width: 100%;height: 20px;"></div>
    <form:form id="informationListForm" name="informationListForm" commandName="QSC0301Condition" mehtod="POST"
               onsubmit="return navTabSearch(this)" action="${ctx}/QSC0301" style="padding-left: 20px;">
        <table class="list" width="100%">
            <thead class="listfilter">
            <tr>
                <th width="50"></th>
                <th width="300">
                    <a class="filterbuttom">
                        <input type="submit" class="button" value='<spring:message code="QSC0301_Button_Refresh"/>'>
                    </a>
                    <a class="filterbuttom" onclick="clearForm()">
                        <input type="button" class="button" value='<spring:message code="QSC0301_Button_Clear"/>'>
                    </a>
                </th>
                <th></th>
                <th width="150">
                        <form:input type="text"  path="dateFromStart" cssClass="date" size="9"
                                    format="dd-NNN-yyyy" />
                    <a class="inputDateButton" href="javascript:;"></a>
                <th width="150">
                    <form:input type="text"  path="dateToStart" cssClass="date" size="9"
                                format="dd-NNN-yyyy" />
                    <a class="inputDateButton" href="javascript:;"></a>
                </th>
            </tr>
            <tr>
                <th width="50"></th>
                <th width="300"><form:input path="title" cssStyle="width:${ScreenDefault.filterInputWidth}"/></th>
                <th><form:input path="information" cssStyle="width:${ScreenDefault.filterInputWidth}"/></th>
                <th width="150">
                    <form:input type="text"  path="dateFromEnd" cssClass="date" size="9"
                                format="dd-NNN-yyyy" />
                    <a class="inputDateButton" href="javascript:;"></a>
                </th>
                <th width="150">
                    <form:input type="text"  path="dateToEnd" cssClass="date" size="9"
                                format="dd-NNN-yyyy" />
                    <a class="inputDateButton" href="javascript:;"></a>
                </th>
            </tr>
            </thead>
            <thead>
            <tr>
                <th width="50" style="text-align: center;">
                    <input type="checkbox" id="checkAll">
                </th>
                <th width="300"><spring:message code="QSC0301_Grid_Title"/></th>
                <th><spring:message code="QSC0301_Grid_Information"/></th>
                <th width="150"><spring:message code="QSC0301_Grid_DateFrom"/></th>
                <th width="150"><spring:message code="QSC0301_Grid_DateTo"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.content}" var="item">
                <tr>
                    <td style="text-align: center;"><input type="checkbox" name="subBox" value="${item.informationId}"/></td>
                    <td><a href="${ctx}/QSC0302/${item.informationId}" target="navTab">${item.title}</a></td>
                    <td>${item.information}</td>
                    <td><fmt:formatDate value="${item.dateFrom}" pattern="dd MMM yyyy"/></td>
                    <td><fmt:formatDate value="${item.dateTo}" pattern="dd MMM yyyy"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form:form>
    <div class="pageBar">
        <pagination:pageInfo page="${page}" action=""
                             parentFormId="informationListForm" />
    </div>
</div>
<script>
    $(function () {
        $("#checkAll").change(function () {
            $('input[name="subBox"]').attr("checked", this.checked);
            var $subBox = $("input[name='subBox']");
            $subBox.change(function () {
                $("#checkAll").attr("checked", $subBox.length == $("input[name='subBox']:checked").length ? true : false);
            });
        });
    });
    function createNew() {
        var url = "${ctx}/QSC0302/-1"
        navTab.reload(url);
    }

    function deleteInformations() {
        var $subBox = $("input[name='subBox']:checked");
        if ($subBox.length == 0) {
            alertMsg.warn("<spring:message code="w0002" arguments="Information, Delete" argumentSeparator=","/>");
        } else {
            var informationIds = '';
            $subBox.each(function (index, element) {
                informationIds = informationIds + (informationIds == '' ? '' : ',') + parseInt($(element).val());
            });
            console.log(informationIds);
            navTab.reload("${ctx}" + "/QSC0301/delete?informationIds=" + informationIds);
        }
    }
    $(document).ready(function () {
        if (${isSuccess}) {
            alertMsg.correct("<spring:message code="i0001" arguments="Delete"/>")
        }
    });

</script>


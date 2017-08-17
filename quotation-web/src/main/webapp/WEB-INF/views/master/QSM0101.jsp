<%@ page import="com.quotation.common.consts.CodeConst" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="pageHeader">
    <spring:message code="QSM0101_Label"/>
</div>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <a class="button" style="float: none;display: inline-block;" id="btn_download" title="Category Master Download(QFM0102)">
                <span style="padding: 0 20px;"><spring:message code="QSM0101_Button_Download"/> </span>
            </a>
            <a class="button" style="float: none;display: inline-block;" id="btn_upload">
                <span style="padding: 0 20px;"><spring:message code="QSM0101_Button_Upload"/> </span>
            </a>
        </ul>
    </div>
    <table class="list" width="100%">
        <thead>
        <tr>
            <th width="50" style="text-align: center;">
                <input type="checkbox" id="checkAll">
            </th>
            <th width="400"><spring:message code="QSM0101_Grid_Master"/></th>
            <th><spring:message code="QSM0101_Grid_LastUpdateTime"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${masterList}" var="item">
            <tr>
                <td style="text-align: center;"><input type="checkbox" name="subBox" value="${item.masterId}" master-name="${item.masterName}" master-readonly="${item.readOnly == 1}"/></td>
                <td>${item.masterName}</td>
                <td>${item.readOnly == 1 ? 'View only' : item.updatedDate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
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

        $('#btn_download').click(function () {
            var $subBox = $("input[name='subBox']:checked");
            if ($subBox.length == 0) {
                alertMsg.warn("<spring:message code='W0004'/>");
            } else {
                var masterIds = '';
                $subBox.each(function (index, element) {
                    masterIds = masterIds + (masterIds == '' ? '' : ',') + parseInt($(element).val());
                });
                window.location.href = "${ctx}" + "/QSM0101/download?masterIds=" + masterIds;
            }
        });

        $('#btn_upload').click(function () {
            var $subBox = $("input[name='subBox']:checked");
            if ($subBox.length == 0) {
                alertMsg.warn("<spring:message code='W0004'/>");
            } else {
                var containsReadOnly = false;
                $subBox.each(function (index, element) {
                    var readOnly = $(element).attr("master-readonly");
                    if (readOnly === true || readOnly === 'true') {
                        containsReadOnly = true;
                    }
                });
                if (containsReadOnly) {
                    alertMsg.warn("<spring:message code='W2001'/>");
                } else {
                    var masterIds = '';
                    var masterNames = '';
                    $subBox.each(function (index, element) {
                        masterIds = masterIds + (masterIds == '' ? '' : ',') + parseInt($(element).val());
                        masterNames = masterNames + (masterNames == '' ? '' : ',') + ($(element).attr('master-name'));
                    });
                    $.pdialog.open("${ctx}/QSM0101/uploadPopup?masterIds=" + masterIds, "uploadPopup", "Upload " + masterNames, {
                        width: 800, height: 240, mask: true
                    });
                }
            }
        });
    });
</script>

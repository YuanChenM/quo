<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="pageContent">
    <form method="post" action="${ctx}/QSM0101/upload" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this,dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <div class="unit">
                <div class="upload-wrap" style="text-align: center;height: 36px;display: block;">
                    <input id="hidden_file" style="display: none;" type="file" name="file">
                    <input name="masterIdStr" value="${masterIdStr}" hidden>
                    <input id="label_file" type="text" readonly style="float: none;width: 400px;height: 90%;border: 1px solid #ccc;font-size: 16px;"/>
                    <button id="btn_browse" type="button" style="height: 90%;margin-left: 20px;">Browse</button>
                </div>
                <div style="text-align: center;height: 36px;margin-top: 40px;">
                    <input type="submit" value="提交" style="height: 90%;width: 200px;"/>
                </div>
            </div>
        </div>
    </form>
</div>

<script>
    navTab.openTab
    $(function () {
        navTab.openTab
        $('#btn_browse').click(function () {
            $('#hidden_file').click();
        });
        $('#hidden_file').change(function (event) {
            $("#label_file").val(event.originalEvent.target.files[0].name);
        });
    });
</script>

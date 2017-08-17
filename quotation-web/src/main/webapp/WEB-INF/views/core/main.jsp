<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.quotation.web.core.util.JsonUtils" %>
<%@include file="../common/taglib.jsp" %>
<c:set var="officeCodeSet" value="${offices.keySet()}"/>
<c:set var="officesJsonObject" value='<%=JsonUtils.toJson(request.getAttribute("offices"))%>'/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>Quotation</title>

    <link rel="shortcut icon" href="${ctx}/resources/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${ctx}/resources/favicon.ico" type="image/x-icon">
    <link href="${ctx}/resources/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="${ctx}/resources/themes/default/style-quotation.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="${ctx}/resources/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="${ctx}/resources/themes/css/core-quotation.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="${ctx}/resources/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
    <script src="${ctx}/resources/js/core/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/jquery.validate.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/jquery.cookie.js" type="text/javascript"></script>

    <script src="${ctx}/resources/js/core/dwz.core.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.barDrag.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.drag.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.tree.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.contextmenu.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.navTab.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.ui.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.theme.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.switchEnv.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.alertMsg.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.cssTable.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.stable.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.panel.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.checkbox.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.pagination.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.ajax.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.validate.method.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.effects.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.combox.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.comboxex.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.dialog.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.dialogDrag.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.resize.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.taskBar.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.datepicker.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.util.date.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.database.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/jquery.progressbar.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.print.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.tab.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/label/printer.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/common/dateFormat.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/core/dwz.sortDrag.js" type="text/javascript"></script>
    <script type="text/javascript" language="javascript">
    //<![CDATA[
        (function ($) {
            $.setRegional("pagination", {
                page: {
                    page: "<spring:message javaScriptEscape='true' code='Core_Label_Page'/>", record: "<spring:message javaScriptEscape='true' code='Core_Label_Record'/>",
                    first: "<spring:message javaScriptEscape='true' code='Core_Button_First'/>", prev: "<spring:message javaScriptEscape='true' code='Core_Button_Prev'/>",
                    next: "<spring:message javaScriptEscape='true' code='Core_Button_Next'/>", last: "<spring:message javaScriptEscape='true' code='Core_Button_Last'/>"
                }
            });
        })(jQuery);
        $(function () {
            DWZ.init("resources/dwz.frag.xml", {
                loginTitle: "login",
                loginUrl: "login",
                statusCode: {
                    ok: 200,
                    error: 300,
                    timeout: 301
                },
                pageInfo: {
                    pageNum: "page.page",
                    numPerPage: "page.size",
                    orderField: "page.sort",
                    orderDirection: "page.sort.dir"
                },
                debug: false,
                callback: function () {
                    initEnv();
                    $("#themeList").theme({
                        themeBase: "${ctx}/resources/themes"
                    });
                }
            });
        });

        /** */
        function initTitleKey(resourceCode) {
            $("#resourceCode").val(resourceCode);
            alertMsg._clearCall();
        }
    //]]>
    </script>
</head>

<body scroll="no">
<div id="layout">
    <div id="header">
        <div class="headerNav">
            <form id="mainform" action="main" method="get">
                <input type="hidden" value="" id="resourceCode"/>
                <input type="hidden" value="" name="language" id="language"/>
            </form>
            <div class="headerContent">
                <div class="toolbar"></div>

                <ul class="themeList" id="themeList">
                    <li theme="default">
                        <div class="selected"></div>
                    </li>
                    <li theme="tawny">
                        <div></div>
                    </li>
                    <li theme="purple">
                        <div></div>
                    </li>
                    <li theme="red">
                        <div></div>
                    </li>
                    <li theme="orange">
                        <div></div>
                    </li>
                </ul>

                <ul class="userinfo">
                    <li id="switchLanguageBox" class="switchBox"><a href="javascript:" class="pulldown"><span><spring:message code="Main_Label_Language"/></span></a>
                        <ul>
                            <li><a href="#changeLanguge" rel="en_US">English</a></li>
                            <li><a href="#changeLanguge" rel="zh_CN">中文</a></li>
                        </ul>
                    </li>
                    <li id="profileBox" class="switchBox"><a href="javascript:" class="pulldown"><span>${userContext.username}</span></a>
                        <ul>
                            <li><a href="${ctx}/login" title='<spring:message code="Main_Label_MyProfile" />'><spring:message code="Main_Label_MyProfile"/></a></li>
                            <li><a href="${ctx}/login" title='<spring:message code="Main_Label_ChangePassword" />'><spring:message code="Main_Label_ChangePassword"/></a></li>
                            <li><a href="#logout" title='<spring:message code="Main_Label_Logout" />'><spring:message code="Main_Label_Logout"/></a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="loginContent">
                <div><spring:message code="Main_Label_UserID"></spring:message> ${userContext.userId}</div>
                <div><spring:message code="Main_Label_Name"></spring:message> ${userContext.username}</div>
                <div><spring:message code="Main_Label_Office"></spring:message>
                    <select id="select_office">
                        <c:forEach var="officeCode" items="${officeCodeSet}">
                            <option <c:if test="${officeCode eq userContext.activeOffice}">selected</c:if>>${officeCode}</option>
                        </c:forEach>
                    </select>
                </div>
                <c:set var="sectionCodeList" value="${offices.get(userContext.activeOffice)}"/>
                <div><spring:message code="Main_Label_Section"></spring:message>
                    <select id="select_section">
                        <c:forEach var="sectionCode" items="${sectionCodeList}">
                            <option <c:if test="${sectionCode eq userContext.activeSection}">selected</c:if>>${sectionCode}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>

    <div id="leftside">
        <div id="sidebar_s">
            <div class="collapse">
                <div class="toggleCollapse">
                    <div></div>
                </div>
            </div>
        </div>
        <div id="sidebar">
            <div class="toggleCollapse">
                <h2><spring:message code="Main_Label_MenuArea"/></h2>
                <div>left</div>
            </div>
            <div class="menuContent">
                <q:menu styleClass="tree"/>
            </div>
        </div>
    </div>
    <div id="container">
        <div id="navTab" class="tabsPage">
            <div id="tabsPageHeader" class="tabsPageHeader">
                <div class="tabsPageHeaderContent">
                    <ul class="navTab-tab">
                        <li tabid="main" class="main">
                            <a href="javascript:;">
                                <span><span class="home_icon">main</span></span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <ul class="tabsMoreList">
                <li><a href="javascript:;">main</a></li>
            </ul>
            <div class="navTab-panel tabsPageContent layoutBox">
                <div class="page unitBox">
                    <jsp:include page="../common/QSC0303.jsp"></jsp:include>
                </div>
            </div>
        </div>


        <div id="splitFooterBar"></div>

        <div id="tabsPageFooter" class="tabsPageFooter">

            <div class="msgboxHeader" id="msgboxHeaderOff">
                <h2 style="background-position: 100% -50px;"><spring:message code="Main_Label_MessageArea"/></h2>
            </div>
            <div id="messagebox" style="display:none; position:absolute; bottom: 0; width:100%; border-style:solid; border-width: 1px 0 0 0; border-color:#B8D0D6;z-index:1011;">
                <div tabindex="0" class="msgboxHeader" id="msgboxHeaderOn">
                    <h2 style="background-position: 100% -100px;"><spring:message code="Main_Label_MessageArea"/></h2>
                </div>
                <div class="msgboxContent" id="msgboxContent">
                </div>
            </div>
        </div>
    </div><!-- container -->
</div>
<script type="text/javascript">
    //<![CDATA[
    function banBackSpace(e) {
        var ev = e || window.event;
        //get event target
        var obj = ev.target || ev.srcElement;
        var t = obj.type || obj.getAttribute('type');

        var vReadOnly = obj.readOnly;
        var vDisabled = obj.disabled;

        vReadOnly = (vReadOnly === undefined || vReadOnly === "") ? false : vReadOnly;
        vDisabled = (vDisabled === undefined) ? true : vDisabled;
        //Can't input or it's input field but it's ready, then disable backspace.
        var flag1;
        flag1 = (ev.keyCode === 8) && (t === "password" || t === "text" || t === "textarea") && (!(vDisabled !== true && vReadOnly !== "readOnly"));

        var flag2 = (ev.keyCode === 8 && t !== "password" && t !== "text" && t !== "textarea");
        if (flag2 || flag1)
            return false;
    }
    function clearForm() {
        $(':input')
            .not(':button, :submit, :reset, :hidden')
            .val('')
            .removeAttr('checked')
            .removeAttr('selected');
    }
    $(document).ready(function () {
        //For Firefox and Opera
        document.onkeypress = banBackSpace;
        //for IE and Chrome
        document.onkeydown = banBackSpace;


        var officesJsonObj = ${officesJsonObject};
        var activeOffice = '${userContext.activeOffice}';
        var activeSection = '${userContext.activeSection}';

        var officeSelect = $("#select_office");
        var sectionSelect = $("#select_section");

        function changeOffice() {
            window.location.href = '${ctx}/changeOffice?officeCode=' + officeSelect.val() + '&sectionCode=' + sectionSelect.val();
        }

        officeSelect.change(function () {
            var sections = officesJsonObj[officeSelect.val()];
            sectionSelect.empty();
            for (var i = 0; i < sections.length; i++) {
                var option = '<option' + (i == 0 ? ' selected' : '') + '>' + sections[i] + '</option>';
                sectionSelect.append(option);
            }
            changeOffice();
        });

        sectionSelect.change(function () {
            changeOffice();
        });
    });
    //]]>
</script>
</body>
</html>
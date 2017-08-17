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
        function addContent(sampleUid){
            var url = "${ctx}/sp/popup/" + sampleUid;
            var dlgId = "popup01";
            $.pdialog.open(url,dlgId,'PopupSample',{width:400,height:300,mask:true});
        }

        function editContent(sampleUid){
            var url = "${ctx}/sp/popup/" + sampleUid;
            var dlgId = "popup01";
            $.pdialog.open(url,dlgId,'PopupSample',{width:400,height:300,mask:true});
        }

        function deleteContent(sampleUid){
            var url = "${ctx}/sp/delete/" + sampleUid;
            navTab.reload(url);
        }
    </script>
</head>

<div id="layout">
    <h1 class="contentTitle">SampleList</h1>
    <div id="cocontent">
        <div>
            <form:form id="formsample" name="formsample" class="required-validate"
                action="${ctx}/sp/list" method="post">
                <table style="width:100%" targetType="navTab" class="list">
                    <thead>
                        <tr>
                            <th style="width:10%;" align="left"><div> SAMPLE ID </div></th>
                            <th style="width:30%;" align="left"><div> SAMPLE CODE </div></th>
                            <th style="width:45%;" align="left"><div> SAMPLE CONTENT </div></th>
                            <th style="width:15%;" align="left"><div> OPERATION </div></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="list" items="${samples}">
                            <tr>
                                <td><c:out value="${list.sampleId}"/></td>
                                <td><a href="${ctx}/sp/edit/${list.sampleUid}" target="navTab">${list.sampleCode}</a></td>
                                <td><c:out value="${list.sampleContent}"/></td>
                                <td>
                                    <div>
                                        <input type="button" value='edit' class="button" style="width:80px" onclick="editContent('${list.sampleUid}')"/>
                                        <input type="button" value='delete' class="button" style="width:80px" onclick="deleteContent('${list.sampleUid}')"/>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form:form>
        </div>
        <div class="panelBar">
            <div class="toolBar">
                <input type="button" value='add' class="button" style="width:80px" onclick="addContent('new')"/>
            </div>
        </div>    
    </div>
    
</div>
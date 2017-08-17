<%--
  Created by IntelliJ IDEA.
  User: zhu_yingjie
  Date: 2017/8/15
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../common/taglib.jsp" %>
<div class="pageHeader">
    Grid
</div>
<div class="pageContent">
    <%--<table class="list">--%>
        <%--<thead>--%>
            <%--<th width="50"><input type="checkbox" class="checkboxCtrl" group="grid"/></th>--%>
            <%--<th width="50"></th>--%>
            <%--<th>No</th>--%>
            <%--<th>Date</th>--%>
            <%--<th>Remark</th>--%>
            <%--<th>Selling To</th>--%>
        <%--</thead>--%>
        <%--<tbody>--%>
            <%--<c:forEach var="item" items="${gridList}">--%>
                <%--<tr class="parent" id="${item.no}">--%>
                    <%--<td><input type="checkbox" name="grid" value="${item.no}"/></td>--%>
                    <%--<c:if test="${item.subList.size() > 0}">--%>
                        <%--<td onclick="toggleTr(this)">+</td>--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${item.subList == null || item.subList.size() == 0}">--%>
                        <%--<td></td>--%>
                    <%--</c:if>--%>
                    <%--<td>${item.no}</td>--%>
                    <%--<td>${item.date}</td>--%>
                    <%--<td>${item.remark}</td>--%>
                    <%--<td>${item.sellingTo}</td>--%>
                <%--</tr>--%>
                <%--<c:if test="${item.subList.size() > 0}">--%>
                    <%--<c:forEach var="subItem" items="${item.subList}">--%>
                        <%--<tr class="children_${item.no}" style="display: none">--%>
                            <%--<td><input type="checkbox" name="grid" value="${subItem.no}"/></td>--%>
                            <%--<td></td>--%>
                            <%--<td>${subItem.no}</td>--%>
                            <%--<td>${subItem.date}</td>--%>
                            <%--<td>${subItem.remark}</td>--%>
                            <%--<td>${subItem.sellingTo}</td>--%>
                        <%--</tr>--%>
                    <%--</c:forEach>--%>
                <%--</c:if>--%>
            <%--</c:forEach>--%>
        <%--</tbody>--%>
    <%--</table>--%>
    <grid:grid items="${items}" columns="${columns}" group="true"></grid:grid>
</div>
<script type="text/javascript">
    function toggleTr(element) {
        if (element.innerHTML === "+") {
            element.innerHTML = "-";
        } else {
            element.innerHTML = "+";
        }
        var parentElement = element.parentElement;
        var id = parentElement.getAttribute("id");
        $(parentElement).siblings(".children_" + id).toggle();
    }
</script>

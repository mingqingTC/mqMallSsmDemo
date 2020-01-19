<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="common/tag.jsp" %>
<%
    //获取项目根路径http://127.0.0.1:80/项目名称
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":"
            + request.getServerPort()
            + path;
%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀列表页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="common/bootstrap-header.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>商品名称</th>
                    <th>库存</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>创建时间</th>
                    <th>链接</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="secKill" items="${secKillList}">
                    <tr>
                        <td>${secKill.name}</td>
                        <td>${secKill.number}</td>
                        <td>
                            <fmt:formatDate value="${secKill.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${secKill.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${secKill.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <a class="btn btn-info" href="<%=basePath%>/secKill/${secKill.secKillId}/detail"
                               target="_blank">link</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@include file="common/bootstrap-js.jsp" %>
</body>
</html>

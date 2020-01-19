<%@page contentType="text/html;charset=UTF-8" %>
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
    <title>ERROR</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<h1>出错了！！！</h1>
<a href="<%=basePath%>/secKill/0/10/list">点此返回列表页</a>
</body>
</html>

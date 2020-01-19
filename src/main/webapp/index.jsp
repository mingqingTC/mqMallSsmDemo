<%@page contentType="text/html;charset=UTF-8" %>
<%
    //获取项目根路径http://127.0.0.1:80/项目名称
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":"
            + request.getServerPort()
            + path;
    //重定向到列表页
    response.sendRedirect(basePath + "/secKill/0/10/list");
%>
<!DOCTYPE html>
<html>
<head>
    <title>title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>

</body>
</html>


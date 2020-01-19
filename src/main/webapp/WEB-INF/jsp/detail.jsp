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
    <title>秒杀商品详情页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="common/bootstrap-header.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h2>${exposeEntranceResult.secKill.name}</h2>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <span class="glyphicon glyphicon-time" id="clock-icon"></span>
                <span class="glyphicon" id="sec-kill-box"></span>
            </h2>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="killPhoneModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title text-center">
                            <span class="glyphicon glyphicon-phone"></span>
                            <label for="killPhoneKey">秒杀电话:</label>
                        </h3>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-xs-8 col-xs-offset-2">
                                <input type="text" name="killPhone" id="killPhoneKey"
                                       class="form-control" placeholder="填写手机号码"/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <span id="killPhoneMessage" class="glyphicon"></span>
                        <button id="killPhoneBtn" class="btn btn-success">
                            <span class="glyphicon glyphicon-phone"></span>
                            Submit
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="common/bootstrap-js.jsp" %>
<%-- 计时器 --%>
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.js"></script>
<%-- cookie --%>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<%-- 自定义js --%>
<script src="<%=basePath%>/resources/script/detail.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        secKill.detail.init({
            secKillId: ${exposeEntranceResult.secKillId},
            exposed: ${exposeEntranceResult.exposed},
            md5: '${exposeEntranceResult.md5}',
            systemCurrentTime: ${exposeEntranceResult.systemCurrentTime},
            startTime: ${exposeEntranceResult.startTime},
            endTime: ${exposeEntranceResult.endTime}
        })
    });
</script>
</body>
</html>

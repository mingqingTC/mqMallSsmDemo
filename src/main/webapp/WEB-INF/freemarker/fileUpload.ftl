<#setting number_format="#"> <#-- 解决数字自动加逗号 -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="${basePath}"/>
    <title>文件上传</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#include "common/bootstrap-header.ftl" />
</head>
<body>
<div>
    <div class="container">
        <form role="form" method="post" enctype="multipart/form-data">
            <h3>单文件上传</h3>
            <div class="form-group">
                <input type="file" id="imgFile" name="imgFile">
            </div>
            <span id="submitMessage" class="glyphicon"></span>
            <button id="submitBtn" class="btn btn-default" onclick="return false;">提交</button>
        </form>
        <br/>
        <form role="form" method="post" enctype="multipart/form-data">
            <h3>多文件上传</h3>
            <div class="form-group">
                <input type="file" id="file1" name="file1">
                <input type="file" id="file2" name="file2">
                <input type="file" id="file3" name="file3">
            </div>
            <span id="submitMessageMulti" class="glyphicon"></span>
            <button id="submitBtnMulti" class="btn btn-default" onclick="return false;">提交</button>
        </form>
    </div>
</div>
<#include "common/bootstrap-js.ftl" />
<script type="text/javascript">
    $("#submitBtn").click(function () {
        //避免重复点击,上传多次文件
        $("button").attr("disabled", "disabled");
        var formData = new FormData();
        formData.append("imgFile", $("#imgFile")[0].files[0]);
        $.ajax({
            url: '/mqMall/uploadFileSingle',
            type: 'post',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (result) {
                console.log('调用成功');
                $('#submitMessage').hide().html('<label class="label label-danger">' + result['errorInfo'] + '</label>').show(300);
            },
            error: function () {
                console.log('调用失败');
            }
        });
    });
    $("#submitBtnMulti").click(function () {
        //避免重复点击,上传多次文件
        $("button").attr("disabled", "disabled");
        var formData = new FormData();
        formData.append("file1", $("#file1")[0].files[0]);
        formData.append("file2", $("#file2")[0].files[0]);
        formData.append("file3", $("#file3")[0].files[0]);
        $.ajax({
            url: '/mqMall/uploadFileMulti',
            type: 'post',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (result) {
                console.log('调用成功');
                $('#submitMessageMulti').hide().html('<label class="label label-danger">' + result['errorInfo'] + '</label>').show(300);
            },
            error: function () {
                console.log('调用失败');
            }
        });
    });
</script>
</body>
</html>

<#setting number_format="#"> <#-- 解决数字自动加逗号 -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="${basePath}"/>
    <title>文件上传</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#include "common/bootstrap-header.ftl" />
</head>
<body class="skin-blue sidebar-mini">
<div class="container wrapper content-wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-6">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">Quick Example</h3>
                    </div>

                    <form role="form" method="post" enctype="multipart/form-data" action="personalInfo/save">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="name">名字</label>
                                <input type="text" class="form-control" id="name" name="name" placeholder="输入名字">
                            </div>
                            <div class="form-group">
                                <label for="age">年龄</label>
                                <input type="text" class="form-control" id="age" name="age" placeholder="年龄">
                            </div>
                            <div class="form-group">
                                <label for="sex">性别</label>
                                <input type="text" class="form-control" id="sex" name="sex" placeholder="性别">
                            </div>
                            <div class="form-group">
                                <label for="image1">图片1</label>
                                <input type="file" id="image1" name="images">
                            </div>
                            <div class="form-group">
                                <label for="image2">图片2</label>
                                <input type="file" id="image2" name="images">
                            </div>
                        </div>
                        <div class="box-footer">
                            <button type="submit" class="btn btn-primary">提交</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </section>

</div>
<#include "common/bootstrap-js.ftl" />
</body>
</html>

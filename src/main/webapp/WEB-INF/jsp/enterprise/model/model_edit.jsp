<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="/">
    <meta charset="utf-8"/>
    <title></title>
    <meta name="description" content="overview & stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link href="static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="static/css/bootstrap-responsive.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="static/css/font-awesome.min.css"/>
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/css/chosen.css"/>
    <link rel="stylesheet" href="static/css/ace.min.css"/>
    <link rel="stylesheet" href="static/css/ace-responsive.min.css"/>
    <link rel="stylesheet" href="static/css/ace-skins.min.css"/>

    <link rel="stylesheet" href="static/css/jquery.datetimepicker.css"/>


    <link rel="stylesheet" href="static/css/datepicker.css"/>
    <!-- 日期框 -->

    <link rel="stylesheet" href="static/css/bootstrap-multiselect.css"/>
    <!-- 多选下拉框 -->


    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
    <script type="text/javascript" src="static/js/jquery.datetimepicker.js"></script>


    <script type="text/javascript">
        $(top.hangge());

        //保存
        function save() {
            if ($("#name").val() == "") {
                $("#name").tips({
                    side: 3,
                    msg: '请填写模型名称',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#name").focus();
                return false;
            }

            $("#form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }
    </script>

<body>
<h4>模板特别说明：</h4>
<b>1.如何生成模板，请在word中编辑好合同文件，注意将中间的需要替换的内容用&\${}&包起。例如，合同编号是：123456，则合同编号是：&\${合同编号}&，遵循此规范，可以最大减少人工干预。点击这里可以查看示例模板。</b>

<form action="model/${msg }.do" name="form" id="form" method="post" enctype="multipart/form-data">
    <input type="hidden" name="modelId" id="modelId" value="${pd.modelId }"/>

    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">模型名称:</td>
                <td><input type="text" name="name" id="name" value="${pd.name }" maxlength="32"
                           placeholder="这里输入模型名称" title="模型名称"/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">模型文档:
                    <b><span style="color: red">(必须是docx文档,修改请重新上传所有文件)</span></b>
                </td>
                <td>
                    <input multiple type="file" name="FILE" id="id-input-file"/>
                </td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">已上传文件:</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty modelFilesList}">
                            <c:forEach items="${modelFilesList}" var="modelFile">
                                <span style="color: blue">
                                    <a href="${modelFile.url }" target="_blank">${modelFile.fileName}</a></span><br>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <span style="color: red">还未上传文件</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <c:if test="${userName == 'admin' }">
                        <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                        <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                    </c:if>
                </td>
            </tr>
        </table>
    </div>

    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img
            src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">提交中...</h4></div>

</form>


<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
<!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-multiselect.js"></script>
<!-- 多选下拉框 -->
<script type="text/javascript" charset="utf-8" src="plugins/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/umeditor/umeditor.js"></script>
<link href="plugins/umeditor/themes/default/css/umeditor.min.css" type="text/css" rel="stylesheet">

<script type="text/javascript">
    var um = UM.getEditor('myEditor');
    $(top.hangge());
    $(function () {

        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect: true});
        $(".date-picker").datetimepicker({
            lang: 'ch',
            format: "Y-m-d H:i:s"
        }); // 日期+时分秒
        $('#role_ids').multiselect();
    });

    $('#id-input-file').ace_file_input({
        style: 'well',
        btn_choose: 'Drop files here or click to choose',
        btn_change: null,
        no_icon: 'icon-cloud-upload',
        droppable: true,
        onchange: null,
        thumbnail: 'small',
        before_change: function (files, dropped) {

            return true;
        }
    }).on('change', function () {});
</script>
</body>
</html>










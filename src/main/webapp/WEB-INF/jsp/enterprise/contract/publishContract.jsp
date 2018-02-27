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

    <link type="text/css" rel="stylesheet" href="static/css/account.css"/>


    <link rel="stylesheet" href="static/css/datepicker.css"/>
    <!-- 日期框 -->

    <link rel="stylesheet" href="static/css/bootstrap-multiselect.css"/>
    <!-- 多选下拉框 -->

    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
    <script type="text/javascript" src="static/js/jquery.datetimepicker.js"></script>
    <script type="text/javascript" src="static/js/jquery.form.js"></script>



    <script type="text/javascript">
            $(top.hangge());

            //保存
            function save() {

                $("#ctForm").ajaxSubmit({
                    url:'/publishContract/saveContract.do',
                    success:function () {
                        //获取普通参数值属性
                        var paramSpan = $(".replace");
                        //获取表格参数值属性
                        var tableParamSpan = $(".table");

                        //定义一个普通参数值数组
                        var paramValueArray = [];
                        //定义一个表格参数值数组
                        var tableParamArray = [];

                        for(var i=0;i < tableParamSpan.length;i++){
                            var param = $(tableParamSpan[i]).data("param");
                            var value = $(tableParamSpan[i]).val();
                            var paramId = $(tableParamSpan[i]).data("paramid");
                            var rowIndex = $(tableParamSpan[i]).data("row-index");
                            var columnIndex = $(tableParamSpan[i]).data("column-index");
                            var modelFileId = $(tableParamSpan[i]).data("fileid");
                            var tableName = $(tableParamSpan[i]).data("tablename");
                            tableParamArray.push({"param":param,"value":value,"rowIndex":rowIndex,"columnIndex":columnIndex,"paramId":paramId,"modelFileId":modelFileId,"tableName":tableName});
                        }

                        for(var i=0;i < paramSpan.length;i++){
                            var param = $(paramSpan[i]).data("param");
                            var value = $(paramSpan[i]).val();
                            var paramId = $(paramSpan[i]).data("paramid");
                            var modelFileId = $(paramSpan[i]).data("fileid");
                            paramValueArray.push({"param":param,"value":value,"modelFileId":modelFileId,"paramId":paramId});
                        }

                        var obj = {
                            "paramValueList":paramValueArray,
                            "tableParamList":tableParamArray
                        };

                        $.ajax({
                            type: "POST",
                            contentType: "application/json",
                            async : false,//同步
                            url: "/publishContract/saveFileParamValue.do",
                            //将object对象转化为json格式的数组
                            data: JSON.stringify(obj)
                        });
                        window.location.href="/publishContract/goSaveContractFJ.do"
                    }
                });

            }


            //根据模板参数生成对应的表格
            function getTemplateParam(templateId){

                $.ajax({
                    url: "/publishContract/getTemplateParam.do?templateId="+templateId,
                    dataType:"html",
                    success: function(html){
                        $("#templateParamDiv").html(html);
                        $("#templateParamDiv").trigger("create");
                    }
                });
            }

    </script>

<body>

<form action="publishContract/${msg}.do" name="ctForm" id="ctForm" method="post" enctype="multipart/form-data">
    <div id="zhongxin">

        <tr>
            <td>
            <select name="name" id="templateName" title="请选择合同模板">
                <option value=""><span>请选择合同模板</span></option>
                <c:forEach items="${contractTemplateList}" var="item">
                    <option value="${item.name}" onclick="getTemplateParam(${item.templateId})" >
                            ${item.name}
                    </option>
                </c:forEach>
            </select>
            </td>
        </tr>
            <br/>


        <table id="table_report" class="table table-striped table-bordered table-hover">

            <div id = "templateParamDiv">

            </div>

            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="save();">下一页</a>
                    <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
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

    $('.id-input-file').ace_file_input({
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










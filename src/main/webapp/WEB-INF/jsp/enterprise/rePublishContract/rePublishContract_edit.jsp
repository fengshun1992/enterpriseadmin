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

    <script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
    <!-- 引入 -->

    <script type="text/javascript">
        $(top.hangge());

        //合同编号是否可用标志
        var flag = false;

        //ajax判断合同编号是否已存在
        function getResult(data) {
            if (data == 1) {
                document.getElementById("checkCN").innerHTML = "<b style='color: red'>合同编号已存在</b>";
            } else if (data == 2) {
                document.getElementById("checkCN").innerHTML = "<b style='color:red'>合同编号不能为空</b>";
            } else if (data == 3) {
                document.getElementById("checkCN").innerHTML = "<b style='color:green'>合同编号可用</b>"
                flag = true;
            } else if (data == 0) {
                document.getElementById("checkCN").innerHTML = "<b style='color:red'>ajax成功返回值为空</b>"
            } else {
                document.getElementById("checkCN").innerHTML = "<b style='color:red'>未知错误</b>"
            }
        }
        function checkContractNo() {
            var contractNo = $('#contractNo').val();
            var contractId = $('#contractId').val();
            $.ajax({
                type: "post",
                url: '/rePublishContract/checkContractNo.do',
                data: "contractNo=" + contractNo + "&contractId=" + contractId,
                success: function (data) {
                    getResult(data);
                }
            });
        }
        $(document).ready(function () {
            $("#contractNo").blur(function () {
                flag = false;
                checkContractNo();
            });
        })
        $(document).ready(function () {
            checkContractNo();
        })

        //确定提交提示
        function confirm() {
            bootbox.confirm("确定要提交吗?", function (result) {
                if (result) {
                    save();
                }
            });
        }

        //保存
        function save() {
            var array = new Array();
            <c:forEach items="${pd.fileParamsValuesList_1 }" var="t">
                array.push(${t.paramId });
            </c:forEach>
            <c:forEach items="${pd.fileParamsValuesList_2 }" var="t">
                array.push(${t.paramId });
            </c:forEach>
            <c:forEach items="${pd.fileParamsValuesList_3 }" var="t">
                array.push(${t.paramId });
            </c:forEach>
            <c:forEach items="${pd.fileParamsValuesList_4 }" var="t">
                array.push(${t.paramId });
            </c:forEach>

            if ($("#contractName").val() == "") {
                $("#contractName").tips({
                    side: 3,
                    msg: '请填写合同名称',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#contractName").focus();
                return false;
            }

            if ($("#contractNo").val() == "") {
                $("#contractNo").tips({
                    side: 3,
                    msg: '合同编号不能为空',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#contractNo").focus();
                return false;
            }

            if (!flag) {
                $("#contractNo").tips({
                    side: 3,
                    msg: '请填写正确的合同编号',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#contractNo").focus();
                return false;
            }

            for (var i = 0; i < array.length; i++) {
                if ($('#_'+array[i]).val() == "") {
                    $('#_' + array[i]).tips({
                        side: 3,
                        msg: '请填写参数值',
                        bg: '#AE81FF',
                        time: 2
                    });
                    $('#_' + array[i]).focus();
                    return false;
                }
            }

            $("#form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }
    </script>

<body>

<form action="rePublishContract/${msg}.do" name="form" id="form" method="post" enctype="multipart/form-data">
    <input type="hidden" name="templateId" id="templateId" value="${templateId }"/>
    <input type="hidden" name="contractId" id="contractId" value="${contract.contractId }"/>


    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">合同名称:</td>
                <td><input type="text" name="contractName" id="contractName" value="${contract.contractName }" maxlength="32"
                           placeholder="这里输入合同名称" title="合同名称"/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">合同编号:</td>
                <td><input type="text" name="contractNo" id="contractNo" value="${contract.contractNo }" maxlength="32"
                           placeholder="这里输入合同编号" title="合同编号"/>
                    <span id="checkCN"></span></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">word名称:</td>
                <td>${pd.word_1 }</td>
            </tr>
            <c:forEach items="${pd.fileParamsValuesList_1 }" var="item">
                <tr>
                    <td style="width:100px;text-align: right;padding-top: 13px;">${item.param }</td>
                    <td><input type="text" name="_${item.paramId }" id="_${item.paramId }" value="${item.value }" maxlength="32"
                               title="参数值"/>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">word名称:</td>
                <td>${pd.word_2 }</td>
            </tr>
            <c:forEach items="${pd.fileParamsValuesList_2 }" var="item">
                <tr>
                    <td style="width:100px;text-align: right;padding-top: 13px;">${item.param }</td>
                    <td><input type="text" name="_${item.paramId }" id="_${item.paramId }" value="${item.value }" maxlength="32"
                               title="参数值"/>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">word名称:</td>
                <td>${pd.word_3 }</td>
            </tr>
            <c:forEach items="${pd.fileParamsValuesList_3 }" var="item">
                <tr>
                    <td style="width:100px;text-align: right;padding-top: 13px;">${item.param }</td>
                    <td><input type="text" name="_${item.paramId }" id="_${item.paramId }" value="${item.value }" maxlength="32"
                               title="参数值"/>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">word名称:</td>
                <td>${pd.word_4 }</td>
            </tr>
            <c:forEach items="${pd.fileParamsValuesList_4 }" var="item">
                <tr>
                    <td style="width:100px;text-align: right;padding-top: 13px;">${item.param }</td>
                    <td><input type="text" name="_${item.paramId }" id="_${item.paramId }" value="${item.value }" maxlength="32"
                               title="参数值"/>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="confirm();">提交</a>
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










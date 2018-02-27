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
    <script type="text/javascript" src="static/js/jquery.form.js"></script>


    <script type="text/javascript">
        $(top.hangge());

        //驳回
        function refuse() {
            $("#status").val(3);
            $("#stAuditContractForm").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }

        //审核通过
        function pass() {
            $("#status").val(2);
            $("#stAuditContractForm").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }
    </script>

<body>

<form action="stAuditContract/${msg }.do" name="stAuditContractForm" id="stAuditContractForm" method="post" enctype="multipart/form-data">
    <input type="hidden" name="contractId" id="contractId" value="${pd.contractId }"/>

    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">合同名称:</td>
                <td><input type="text" name="name" id="name" value="${pd.contractName }" maxlength="32" readonly/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">已上传合同文件:</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty contractFilesList}">
                            <c:forEach items="${contractFilesList}" var="item">
                                <span style="color: blue">
                                    <a href="${item.url }" target="_blank">${item.fileName}</a></span><br>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <span style="color: red">还未上传合同文件</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">驳回理由:</td>
                <td><input type="text" name="reason" id="reason" value="" maxlength="100"
                           placeholder="如果驳回,请填写理由" title="驳回理由"/></td>
                <td><input type="hidden" name="status" id="status" value="${pd.status }"/></td>
            </tr>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-danger" onclick="refuse();">驳回</a>
                    <a class="btn btn-mini btn-primary" onclick="pass();">审核通过</a>
                    <a class="btn btn-mini btn-default" onclick="top.Dialog.close();">取消</a>
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










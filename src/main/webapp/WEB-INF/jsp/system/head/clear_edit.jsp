<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    <link rel="stylesheet" href="static/css/datepicker.css"/>
    <!-- 日期框 -->
    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>

    <script type="text/javascript">


        //保存
        function save() {
            if ($("#TEL").val() == "") {
                $("#TEL").tips({
                    side: 3,
                    msg: '请输入手机号',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#TEL").focus();
                return false;
            }
            if ($("#TEL_STATES").val() == "") {
                $("#TEL_STATES").tips({
                    side: 3,
                    msg: '请输入状态码',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#TEL_STATES").focus();
                return false;
            }
            if ($("#CONTENT").val() == "") {
                $("#CONTENT").tips({
                    side: 3,
                    msg: '请输入发送内容',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#CONTENT").focus();
                return false;
            }
            if ($("#SEND_STATES").val() == "") {
                $("#SEND_STATES").tips({
                    side: 3,
                    msg: '请输入发送状态码',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SEND_STATES").focus();
                return false;
            }
            if ($("#SEND_TIME").val() == "") {
                $("#SEND_TIME").tips({
                    side: 3,
                    msg: '请输入发送时间',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SEND_TIME").focus();
                return false;
            }
            if ($("#SEND_TYPES").val() == "") {
                $("#SEND_TYPES").tips({
                    side: 3,
                    msg: '请输入发送类型',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SEND_TYPES").focus();
                return false;
            }
            if ($("#SEND_TIMES").val() == "") {
                $("#SEND_TIMES").tips({
                    side: 3,
                    msg: '请输入发送次数',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SEND_TIMES").focus();
                return false;
            }
            if ($("#SEND_UPDATE_TIME").val() == "") {
                $("#SEND_UPDATE_TIME").tips({
                    side: 3,
                    msg: '请输入更新时间',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SEND_UPDATE_TIME").focus();
                return false;
            }
            if ($("#SEND_OPERATORS").val() == "") {
                $("#SEND_OPERATORS").tips({
                    side: 3,
                    msg: '请输入运营商',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SEND_OPERATORS").focus();
                return false;
            }
            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }

    </script>
</head>
<body>
<form action="smscontent/${msg }.do" name="Form" id="Form" method="post">
    <input type="hidden" name="SMSCONTENT_ID" id="SMSCONTENT_ID" value="${pd.TEL}"/>

    <div id="zhongxin">
        <table id="table_report" width="100%">
            <c:choose>
                <c:when test="${result.states eq '-1'}">
                    <tr style="text-align: center;" colspan="10">
                        <td style="width:200px;padding-top: 13px;">${result.msg}</td>
                    </tr>
                    <tr style="text-align: center;" colspan="10">
                        <td>
                            <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">返回</a>
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td style="width:10px;padding-top: 13px;">发送次数:${result.times}</td>
                    </tr>
                    <tr>
                        <td style="width:10px; padding-top: 13px;">发送IP地址:</td>
                    </tr>
                    <tr>
                        <td><textarea style="width: 305px; height: 193px;" type="text" name="CONTENT" id="CONTENT"
                                      value="${pd.CONTENT}">${result.ips}</textarea></td>
                    </tr>
                    <tr>
                        <td style="text-align: center;" colspan="10">
                            <a class="btn btn-mini btn-primary" onclick="cleardel(${pd.TEL});">清除</a>
                            <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </table>
    </div>

    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img
            src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">删除中...</h4></div>

</form>


<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
<!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
<!-- 日期框 -->
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<!-- 确认窗口 -->
<script type="text/javascript">
    $(top.hangge());
    $(function () {

        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect: true});

        //日期框
        $('.date-picker').datepicker();

    });
    //清除手续绑定
    function cleardel(Id) {
        bootbox.confirm("确定要清除吗?", function (result) {
            if (result) {
                top.jzts();
                $("#zhongxin").hide();
                $("#zhongxin2").show();
                var url = "/smscontent/clearDel.do?TEL=" +${pd.TEL};
                $.post(url, function (data) {
                    if (data.states == 1) {
                        bootbox.alert("清除成功！");
                    } else {
                        bootbox.alert("清除失败！");
                    }
                    setTimeout(function () {
                        top.Dialog.close();
                    }, 2000);
                    nextPage(${page.currentPage});
                });
            }
        });
    }
</script>
</body>
</html>
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
    <!--引入属于此页面的js -->
    <script type="text/javascript" src="static/js/myjs/sms.js"></script>
    <script type="text/javascript">


        //保存
        function save() {
            if ($("#SMS_TYPE").val() == "") {
                $("#SMS_TYPE").tips({
                    side: 3,
                    msg: '请输入短信类型',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SMS_TYPE").focus();
                return false;
            }
            if ($("#SMS_TIMES").val() == "") {
                $("#SMS_TIMES").tips({
                    side: 3,
                    msg: '请输入短信发送限制次数',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SMS_TIMES").focus();
                return false;
            }
            if ($("#SMS_TEMPLATE").val() == "") {
                $("#SMS_TEMPLATE").tips({
                    side: 3,
                    msg: '请输入短信模板',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SMS_TEMPLATE").focus();
                return false;
            }
            if ($("#SMS_REMARK").val() == "") {
                $("#SMS_REMARK").tips({
                    side: 3,
                    msg: '请输入短信类型备注',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SMS_REMARK").focus();
                return false;
            }
            if ($("#SMS_SIGN").val() == "") {
                $("#SMS_SIGN").tips({
                    side: 3,
                    msg: '请输入短信签名',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SMS_SIGN").focus();
                return false;
            }
            if ($("#SMS_SEND_TYPE").val() == "") {
                $("#SMS_SEND_TYPE").tips({
                    side: 3,
                    msg: '发送短信通道类型，如all,YunXin,ChuangLan',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#SMS_SEND_TYPE").focus();
                return false;
            }SMS_SEND_TYPE
            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }

    </script>
</head>
<body>
        <!-- 编辑模板  -->

        <div id="dialog-add" style="display: none;">
            <div class="commitopacity"></div>
            <div class="commitbox">
                <div class="commitbox_inner">
                    <div class="commitbox_top">
                        <textarea name="SMS_TEMPLATEs" id="SMS_TEMPLATEs" placeholder="这里输入短信模板"
                                  title="短信模板"></textarea>

                        <div class="commitbox_cen">
                            <div class="left" id="cityname"></div>
                            <div class="right"><span class="save" onClick="savePHONE()">保存</span>&nbsp;&nbsp;<span
                                    class="quxiao" onClick="cancel_pl()">取消</span></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<form action="/template/${msg }.do" name="Form" id="Form" method="post">
    <input type="hidden" name="ID" id="ID" value="${pd.ID}"/>

    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">短信类型:</td>
                <td><input type="text" name="SMS_TYPE" id="SMS_TYPE" value="${pd.SMS_TYPE}" maxlength="32"
                           placeholder="这里输入短信类型" title="短信类型" onblur="hasT('${pd.SMS_TYPE}')"/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">短信运营商备注:</td>
                <td><input type="text" name="SMS_KEY" id="SMS_KEY" value="${pd.SMS_KEY}" maxlength="32"
                           placeholder="这里输入短信运营商备注" title="短信运营商备注" /></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">短信发送限制次数:</td>
                <td><input type="number" name="SMS_TIMES" id="SMS_TIMES" value="${pd.SMS_TIMES}" maxlength="32"
                           placeholder="这里输入短信发送限制次数" title="短信发送限制次数"/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">短信模板:</td>
                <td><textarea name="SMS_TEMPLATE" id="SMS_TEMPLATE" rows="1" cols="50" style="width:400px;height:60px;"
                              placeholder="这里输入短信模板" title="短信模板">${pd.SMS_TEMPLATE}</textarea></td>
                <%--<div style="float: left; padding:2px 0 0 10px;"><a class='btn btn-mini btn-info' title="编辑模板" onclick="dialog_open();"><i class='icon-edit'></i></a></div></td>--%>
                <%--<td><input type="text" name="SMS_TEMPLATE" id="SMS_TEMPLATE" value="${pd.SMS_TEMPLATE}"  placeholder="这里输入短信模板" title="短信模板"/></td>--%>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">短信类型备注:</td>
                <td><input type="text" name="SMS_REMARK" id="SMS_REMARK" value="${pd.SMS_REMARK}" maxlength="32"
                           placeholder="这里输入短信类型备注" title="短信类型备注"/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">短信签名:</td>
                <td><input type="text" name="SMS_SIGN" id="SMS_SIGN" value="${pd.SMS_SIGN}" maxlength="32"
                           placeholder="这里输入短信签名" title="短信签名"/></td>
            </tr>
            <tr>
                <td style="width:110px;text-align: right;padding-top: 13px;">发送短信通道类型:</td>
                <td><input type="text" name="SMS_SEND_TYPE" id="SMS_SEND_TYPE" value="${pd.SMS_SEND_TYPE}" maxlength="32"
                           placeholder="这里输入发送短信通道类型" title="发送短信通道类型"/> 如：all,YunXin,ChuangLan</td>
            </tr>
            <%--<tr>--%>
            <%--<td style="width:70px;text-align: right;padding-top: 13px;">添加时间:</td>--%>
           <input class="span10 date-picker" name="ADD_TIMD" id="ADD_TIMD" value="${pd.ADD_TIMD}" type="hidden" data-date-format="yyyy-mm-dd"  placeholder="添加时间" title="添加时间"/>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td style="width:70px;text-align: right;padding-top: 13px;">创建者:</td>--%>
            <input type="hidden" name="CREATER" id="CREATER" value="${pd.CREATER}" maxlength="32" placeholder="这里输入创建者" title="创建者"/>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td style="width:70px;text-align: right;padding-top: 13px;">修改者:</td>--%>
            <%--<td><input type="text" name="CHANGER" id="CHANGER" value="${pd.CHANGER}" maxlength="32" placeholder="这里输入修改者" title="修改者"/></td>--%>
            <%--</tr>--%>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
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
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
<!-- 日期框 -->
<script type="text/javascript">
    $(top.hangge());
    $(function () {

        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect: true});

        //日期框
        $('.date-picker').datepicker();

    });
    //判断类型是否存在
    function hasT(SMS_TYPE){
        var SMS_TYPE = $.trim($("#SMS_TYPE").val());
        $.ajax({
            type: "POST",
            url: '/template/hasT.do',
            data: {SMS_TYPE:SMS_TYPE,tm:new Date().getTime()},
            dataType:'json',
            cache: false,
            success: function(data){
                if("success" != data.result){
                    $("#SMS_TYPE").tips({
                        side:3,
                        msg:'短信类型已存在',
                        bg:'#AE81FF',
                        time:3
                    });
                    setTimeout("$('#SMS_TYPE').val('')",2000);
                }
            }
        });
    }
</script>
</body>
</html>
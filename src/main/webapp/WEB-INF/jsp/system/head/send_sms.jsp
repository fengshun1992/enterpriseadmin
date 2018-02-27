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
    <%@ include file="../admin/top.jsp" %>
    <!--引入属于此页面的js -->
    <link rel="stylesheet" href="static/css/jquery.datetimepicker.css"/>
    <script type="text/javascript" src="static/js/jquery.datetimepicker.js"></script>
    <!-- 日期框 -->
    <title></title>


    <style type="text/css">
        #dialog-add, #dialog-message, #dialog-comment {
            width: 100%;
            height: 100%;
            position: fixed;
            top: 0px;
            z-index: 99999999;
            display: none;
        }

        .commitopacity {
            position: absolute;
            width: 100%;
            height: 400px;
            background: #7f7f7f;
            filter: alpha(opacity=50);
            -moz-opacity: 0.5;
            -khtml-opacity: 0.5;
            opacity: 0.5;
            top: 0px;
            z-index: 99999;
        }

        .commitbox {
            width: 100%;
            margin: 0px auto;
            position: absolute;
            top: 0px;
            z-index: 99999;
        }

        .commitbox_inner {
            width: 96%;
            height: 225px;
            margin: 6px auto;
            background: #efefef;
            border-radius: 5px;
        }

        .commitbox_top {
            width: 100%;
            height: 220px;
            margin-bottom: 10px;
            padding-top: 10px;
            background: #FFF;
            border-radius: 5px;
            box-shadow: 1px 1px 3px #e8e8e8;
        }

        .commitbox_top textarea {
            width: 95%;
            height: 165px;
            display: block;
            margin: 0px auto;
            border: 0px;
        }

        .commitbox_cen {
            width: 95%;
            height: 40px;
            padding-top: 10px;
        }

        .commitbox_cen div.left {
            float: left;
            background-size: 15px;
            background-position: 0px 3px;
            padding-left: 18px;
            color: #f77500;
            font-size: 16px;
            line-height: 27px;
        }

        .commitbox_cen div.left img {
            width: 30px;
        }

        .commitbox_cen div.right {
            float: right;
            margin-top: 7px;
        }

        .commitbox_cen div.right span {
            cursor: pointer;
        }

        .commitbox_cen div.right span.save {
            border: solid 1px #c7c7c7;
            background: #6FB3E0;
            border-radius: 3px;
            color: #FFF;
            padding: 5px 10px;
        }

        .commitbox_cen div.right span.quxiao {
            border: solid 1px #f77400;
            background: #f77400;
            border-radius: 3px;
            color: #FFF;
            padding: 4px 9px;
        }

        .word {
            font-size: 16px;
            color: #0000aa;
            line-height: 30px;
            height: 30px;
        }
    </style>

    <script type="text/javascript">

        //保存
        function save() {
            if ($("#excel").val() == "" || document.getElementById("excel").files[0] == '请选择xls格式的文件') {

                $("#excel").tips({
                    side: 3,
                    msg: '请选择文件',
                    bg: '#AE81FF',
                    time: 3
                });
                return false;
            }
//				$("#zhongxin").hide();
            top.jzts();
            $("#zhongxin2").show();
            $.ajaxFileUpload({
                type: "POST",
                url: '/coupon/readExcel2.do',//处理图片脚本
                secureuri: false,
                fileElementId: 'excel',//file控件id
                dataType: 'json',
                success: function (data) {
//						alert(data.sbSuccess);
//						alert(data.sbFail);
                    $("#zhongxin2").hide();
                    top.hangge();
                    $("#success").html(data.sbSuccess);
                    $("#fail").html(data.sbFail);
                    $("#myTel").attr('style', 'display: block');
                },
                error: function (data, e) {
                    alert(e);
                }
            });
//				$("#Form").submit();
//				$("#zhongxin").hide();
//				$("#zhongxin2").show();
        }

        function fileType(obj) {
            var fileType = obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
            if (fileType != '.xls') {
                $("#excel").tips({
                    side: 3,
                    msg: '请上传xls格式的文件',
                    bg: '#AE81FF',
                    time: 3
                });
                $("#excel").val('');
                document.getElementById("excel").files[0] = '请选择xls格式的文件';
            }
        }
    </script>
</head>
<body>
<!-- 编辑手机号  -->

<div id="dialog-add">
    <div class="commitopacity"></div>
    <div class="commitbox">
        <div class="commitbox_inner">
            <div class="commitbox_top">
                <textarea name="PHONEs" id="PHONEs" placeholder="请选输入对方手机号,多个请用(,)英文逗号隔开"
                          title="请选输入对方手机号,多个请用(,)英文逗号隔开"></textarea>

                <div class="commitbox_cen">
                    <div class="left" id="cityname"></div>
                    <div class="right"><span class="save" onClick="savePHONE()">保存</span>&nbsp;&nbsp;<span
                            class="quxiao" onClick="cancel_pl()">取消</span></div>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="zhongxin">
    <div>
        <form action="/head/sendSms.do" method="post" id="templateForm">

            <input type="hidden" name="isAll" id="isAll" value="no"/>
            <input type="hidden" name="TYPE" id="TYPE" value="1"/>
            <table style="width:99%;">
                <tr>
                    <td width="180px">
                        <span class="word">短信模板:</span>
                        <select name="SMS_TEMPLATE" id="templateid" data-placeholder="请选择短信模板"
                                style="vertical-align:top;width: 180px;" onchange="template_Sms(this.options[this.options.selectedIndex].value)">
                            <option class="template"  value="0">请选择短信模板</option>
                            <c:forEach items="${templateList}" var="temp">
                                <option
                                        <c:if test="${pd.SMS_TEMPLATE==temp.ID}">selected</c:if> class="template"
                                        id="template_${temp.ID }" value="${temp.ID }">${temp.SMS_REMARK }</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <%--<td><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart"  value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd"  style="width:88px;" placeholder="开始日期" title="最近登录开始"/></td>--%>
                    <%--<td><input class="span10 date-picker" name="lastLoginEnd" name="lastLoginEnd"  value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd"  style="width:88px;" placeholder="结束日期" title="最近登录结束"/></td>--%>
                    <td><span class="word">发送时间:</span><input class="span10 date-picker" name="sendTime" id="sendTime"
                                                              value="${pd.sendTime}" type="text" style="width:150px;"
                                                              placeholder="发送时间" title="发送时间"/></td>
                </tr>
                <tr>
                    <td id="template_content">
                    </td>
                </tr>
                <tr>
                    <td id="template_param">
                    </td>
                </tr>
                <tr>
                    <td style="margin-top:0px;">
                        <div style="float: left;"><textarea name="PHONE" id="PHONE" rows="1" cols="50"
                                                            style="width:530px;height:20px;"
                                                            placeholder="请选输入对方手机号,多个请用(,)英文逗号隔开"
                                                            title="请选输入对方手机号,多个请用(,)英文逗号隔开">${pd.PHONE}</textarea></div>
                        <div style="float: left; padding:2px 0 0 10px;"><a class='btn btn-mini btn-info' title="编辑手机号"
                                                                           onclick="dialog_open();"><i
                                class='icon-edit'></i></a></div>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: center;">
                        <a class="btn btn-mini btn-primary" onclick="sendSms();">发送</a>
                        <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                        <%--<label style="float:left;padding-left: 32px;"><input name="form-field-radio" id="form-field-radio1" onclick="setType('1');" checked="checked" type="radio" value="icon-edit"><span class="lbl">短信接口1</span></label>--%>
                        <%--<label style="float:left;padding-left: 5px;"><input name="form-field-radio" id="form-field-radio2" onclick="setType('2');" type="radio" value="icon-edit"><span class="lbl">短信接口2</span></label>--%>
                        <%--<label style="float:left;padding-left: 15px;"><input name="form-field-checkbox" class="ace-checkbox-2" type="checkbox" id="allusers" onclick="isAll();" /><span class="lbl">全体用户</span></label>--%>
                    </td>
                </tr>
                <tr>
                    <td id="template_message">
                        <span style="color:#cc0000;font-size:16px;">${msg}</span>
                    </td>
                </tr>

            </table>
        </form>

    </div>

    <div>
        <a class="btn btn-mini btn-success" id="button">excel转为电话字符串</a>

        <form action="coupon/readExcel.do" name="Form" id="Form" method="post" enctype="multipart/form-data"
              style="display: none">
            <table style="width:200px;">
                <tr>
                    <td style=""><input type="file" id="excel" name="excel" style="width:50px;"
                                        onchange="fileType(this)"/></td>
                </tr>
                <tr>
                    <td style="text-align: center;">
                        <a class="btn btn-mini btn-primary" onclick="save();">导入</a>
                        <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                        <a class="btn btn-mini btn-success"
                           onclick="window.location.href='/coupon/downExcel2.do'">下载模版</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div style="display: none" id="myTel">
        <table>
            <tr>
                <td>注册过的用户：</td>
                <td>没注册的用户：</td>
            </tr>
            <tr>
                <td><textarea id="success" style="width: 300px;height: 300px;">success</textarea></td>
                <td><textarea id="fail" style="width: 300px;height: 300px;">fail</textarea></td>
            </tr>
        </table>
    </div>
</div>
<div id="zhongxin2" class="center" style="display:none"><br/><img src="static/images/jzx.gif"/><br/><h4
        class="lighter block green">正在筛选...</h4></div>
<div id="zhongxin3" class="center" style="display:none"><br/><img src="static/images/jzx.gif" id='msg'/><br/><h4
        class="lighter block green" id='msg'>正在发送...</h4></div>


<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="static/js/myjs/sms.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
<!-- 下拉框 -->
<%--<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->--%>

<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<!-- 确认窗口 -->
<!-- 引入 -->


<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<!--提示框-->

<script type="text/javascript">

    $(function () {
        //日期框
        $(".date-picker").datetimepicker({
            lang: 'ch',
            format: "Y-m-d H:i:s"
        }); // 日期+时分秒
        //下拉框
//				$(".chzn-select").chosen();
//				$(".chzn-select-deselect").chosen({allow_single_deselect:true});
        $("#button").toggle(
                function () {
                    $("#Form").attr('style', 'display: block');
                },
                function () {
                    $("#Form").attr('style', 'display: none');
                });
        //复选框
        $('table th input:checkbox').on('click', function () {
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                    .each(function () {
                        this.checked = that.checked;
                        $(this).closest('tr').toggleClass('selected');
                    });
        });

        template_Sms(${pd.SMS_TEMPLATE});

    });
    function template_Sms(templateid) {
        if (templateid == '0' || templateid == null || templateid == 'undefined') {
            $("#template_content").html("");
            $("#template_param").html("");
            $("#template_message").html("");
//					$("#template_edit").html("");
//					return;
        } else {
            $.ajax({
                type: "POST",
                url: '/head/selectTemId.do',
                data: {TEMPLATE_ID: templateid},
                dataType: 'json',
                //beforeSend: validateData,
                success: function (data) {
                    var temContenthtml = "";
                    if (data.list != null) {
                        for (var i = 0; i < data.list.length; i++) {
                            temContenthtml += "请输入短信参数" + data.list[i] + ': <input id=param_"' + data.list[i] + '" value="${pd.data.list[i]}" type="text" name="' + data.list[i] + '" /><br/>';
                        }

                    }
//						var edit_html = "<a class='btn btn-mini btn-info' title='编辑' onclick='editTem("+ templateid +")';><i class='icon-edit'></i></a>";
//						edit_html += "<a class='btn btn-mini btn-danger' title='删除' onclick='delUser("+templateid+")';><i class='icon-trash'></i></a>";
//						$("#template_edit").html(edit_html);
                    $("#template_content").html("<span class='word'>短信内容</span>:&nbsp&nbsp" + data.template_content);
                    $("#template_param").html(temContenthtml);
                }
            });
        }
    }


    $('#excel').ace_file_input({
        no_file: '请选择EXCEL ...',
        btn_choose: '选择',
        btn_change: '更改',
        droppable: false,
        onchange: null,
        thumbnail: false, //| true | large
        whitelist: 'xls|xls',
        blacklist: 'gif|png|jpg|jpeg'
        //onchange:''
        //
    });

    //excel转字符转显示
    function uploadBLock() {

    }
    //新增
    function add() {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "新增";
        diag.URL = '/head/goAddT.do';
        diag.Width = 600;
        diag.Height = 530;
        diag.CancelEvent = function () { //关闭事件
            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                <%--if('${page.currentPage}' == '0'){--%>
                top.jzts();
                setTimeout("self.location=self.location", 100);
                <%--}else{--%>
                <%--nextPage(${page.currentPage});--%>
                <%--}--%>
            }
            diag.close();
        };
        diag.show();
    }

    //修改
    function editTem(tem_id) {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "资料";
        diag.URL = '/head/goEditT.do?templateid=' + tem_id;
        diag.Width = 600;
        diag.Height = 530;
        diag.CancelEvent = function () { //关闭事件
            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                top.jzts();
                setTimeout(self.location = '/head/goSendSms', 100);
            }
            diag.close();
        };
        diag.show();
    }

    //删除
    function delUser(tem_id) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "/head/deleteT.do?ID=" + tem_id + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    bootbox.alert(data.msg);
                    setTimeout(self.location = '/head/goSendSms', 100);
                });
            }
        });
    }
</script>
</body>
</html>
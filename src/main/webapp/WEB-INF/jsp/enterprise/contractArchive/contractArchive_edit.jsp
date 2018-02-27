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

        //合同编号是否可用标志
        var flag = false;

        //ajax判断合同编号是否已存在
        function getResult(data) {
            if (data == 1) {
                document.getElementById("checkCN").innerHTML = "<b style='color:green'>合同编号有效</b>"
                flag = true;
            } else if (data == 2) {
                document.getElementById("checkCN").innerHTML = "<b style='color: red'>合同编号重复,不能重新发行</b>";
            } else if (data == 0) {
                document.getElementById("checkCN").innerHTML = "<b style='color:red'>ajax成功返回值为空</b>"
            } else {
                document.getElementById("checkCN").innerHTML = "<b style='color:red'>未知错误</b>"
            }
        }

        function checkContractNo() {
            var contractId = $('#contractId').val();
            $.ajax({
                type: "post",
                url: '/contractArchive/checkContractNo.do',
                data: "contractId=" + contractId,
                success: function (data) {
                    getResult(data);
                }
            });

        }

        $(document).ready(function () {
                checkContractNo();
        })

        //保存
        function save() {
            //合同编号重复,不能重新发行
            if (!flag) {
                return false;
            }

            $("#form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }
    </script>

<body>

<form action="contractArchive/${msg }.do" name="form" id="form" method="post">
    <input type="hidden" name="contractId" id="contractId" value="${pd.contractId }"/>

    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">选择状态:</td>
                <td>
                    <select name="type" id="type" title="请选择归档合同状态">
                        <c:if test="${pd.type == 1}">
                            <option value="2" selected><span>下架</span></option>
                        </c:if>
                        <c:if test="${pd.type == 2}">
                            <option value="1" selected><span>重新发行</span></option>
                        </c:if>
                    </select>
                    <span id="checkCN"></span></td>
                </td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">操作理由:</td>
                <td><input type="text" name="reason" id="reason" value="" maxlength="100"
                           placeholder="请填写理由" title="操作理由"/></td>
            </tr>
            <%--<tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">操作人:</td>
                <td>
                    <c:forEach items="${allUserList}" var="allUser">
                        <c:if test="${pd.userId == allUser.USER_ID}">
                            <span style="color: blue">${allUser.NAME}</span><br>
                        </c:if>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">操作时间:</td>
                <td><span style="color: blue">${pd.opTime }</span></td>
            </tr>--%>
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










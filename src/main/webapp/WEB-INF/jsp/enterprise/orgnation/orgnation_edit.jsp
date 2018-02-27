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
            if ($("#orgName").val() == "") {
                $("#orgName").tips({
                    side: 3,
                    msg: '请填写机构名称',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#orgName").focus();
                return false;
            }
            if ($("#orgFullName").val() == "") {
                $("#orgFullName").tips({
                    side: 3,
                    msg: '请填写机构全称',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#orgFullName").focus();
                return false;
            }
            if ($("#orgType").val() == "") {
                $("#orgType").tips({
                    side: 3,
                    msg: '请选择机构类型',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#orgType").focus();
                return false;
            }
            if ($("#signData").val() == "") {
                $("#signData").tips({
                    side: 3,
                    msg: '请填写机构签名数据',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#signData").focus();
                return false;
            }
            var flag = $("input[name='userIds']:checked");
            if (flag.length == 0) {
                $("#checkbox-id").tips({
                    side: 3,
                    msg: '请选择机构管理人员',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#checkbox-id").focus();
                return false;
            }

            $("#orgnationForm").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }
    </script>

<body>
<form action="orgnation/${msg}.do" name="orgnationForm" id="orgnationForm" method="post" enctype="multipart/form-data">
    <input type="hidden" name="orgId" id="orgId" value="${pd.orgId }"/>

    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:80px;text-align: right;padding-top: 13px;">机构名称:</td>
                <td><input type="text" name="orgName" id="orgName" value="${pd.orgName }" maxlength="32"
                           title="机构名称"/>
                </td>
            </tr>

            <tr>
                <td style="width:80px;text-align: right;padding-top: 13px;">机构全称:</td>
                <td><input type="text" name="orgFullName" id="orgFullName" value="${pd.orgFullName }" maxlength="32"
                           title="机构全称"/>
                </td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">机构类型:</td>
                <td>
                    <select name="orgType" id="orgType" title="请选择机构类型">
                        <option value=""><span>请选择机构类型</span></option>
                        <option value="1"
                                <c:if test="${pd.orgType == 1}">selected</c:if>>发行方
                        </option>
                        <option value="2"
                                <c:if test="${pd.orgType == 2}">selected</c:if>>受托管理方
                        </option>
                        <option value="3"
                                <c:if test="${pd.orgType == 3}">selected</c:if>>合同系统管理方
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:80px;text-align: right;padding-top: 13px;">机构签名数据:</td>
                <td><input type="text" name="signData" id="signData" value="${pd.signData }" maxlength="32"
                           title="机构签名数据"/>
                </td>
            </tr>

            <tr>
                <td style="width:80px;text-align: right;padding-top: 13px;">上传机构签章图片:</td>
                <td><input multiple type="file" name="FILE" id="id-input-file"/></td>

            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">已上传文件:</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty pd.signImageUrl}">
                            <span style="color: blue">${pd.signImageUrl}</span><br>
                        </c:when>
                        <c:otherwise>
                            <span style="color: red">还未上传文件</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">机构管理人员:</td>
                <td>
                    <c:forEach items="${allUserList}" var="allUser">
                        <input type="checkbox" id="checkbox-id" name="userIds" value="${allUser.USER_ID}"/>
                        <span class="lbl">${allUser.NAME}</span>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">已选管理人员:</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty pd.orgUser}">
                            <c:forEach items="${pd.orgUser}" var="user">
                                <c:forEach items="${allUserList}" var="allUser">
                                    <c:if test="${user.userId == allUser.USER_ID}">
                                        <span style="color: blue">${allUser.NAME}</span><br>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <span style="color: red">没有相关数据</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>

            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                    <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                </td>
            </tr>
        </table>
    </div>
    <c:if test="${pd.orgId != null}">
        <div>
            预览<img src="${pd.signImageUrl }" style="width: 200px;height: 200px">
        </div>
    </c:if>
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
        no_file: 'No File Choose!',
        btn_choose: 'Choose',
        btn_change: 'Change',
        droppable: false,
        onchange: null,
        thumbnail: false //| true | large
    });

</script>
</body>
</html>
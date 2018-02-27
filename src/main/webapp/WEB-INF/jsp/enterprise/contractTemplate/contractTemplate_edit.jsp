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
                    msg: '请填写合同模板名称',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#name").focus();
                return false;
            }
            if ($("#modelId").val() == "") {
                $("#modelId").tips({
                    side: 3,
                    msg: '请选择模型',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#modelId").focus();
                return false;
            }
            if ($("#publisherOrgId").val() == "") {
                $("#publisherOrgId").tips({
                    side: 3,
                    msg: '请选择发行方',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#publisherOrgId").focus();
                return false;
            }
            if ($("#delegatorOrgId").val() == "") {
                $("#delegatorOrgId").tips({
                    side: 3,
                    msg: '请选择受托管理人',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#delegatorOrgId").focus();
                return false;
            }

            $("#ctForm").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }
    </script>

<body>

<form action="contractTemplate/${msg }.do" name="ctForm" id="ctForm" method="post">
    <input type="hidden" name="templateId" id="templateId" value="${pd.templateId }"/>

    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">合同模板名称:</td>
                <td><input type="text" name="name" id="name" value="${pd.name }" maxlength="32"
                           placeholder="这里输入合同模板名称" title="合同模板名称"/></td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">选择模型:</td>
                <td>
                    <select name="modelId" id="modelId" title="请选择模型">
                        <option value=""><span>请选择模型</span></option>
                        <c:forEach items="${modelList}" var="item">
                            <option value="${item.modelId}"
                                    <c:if test="${pd.modelId == item.modelId}">selected</c:if>>
                                    ${item.name}
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">发行方:</td>
                <td>
                    <select name="publisherOrgId" id="publisherOrgId" title="请选择发行方">
                        <option value=""><span>请选择发行方</span></option>
                        <c:forEach items="${orgnationList}" var="item">
                            <c:if test="${item.orgType == 1}">
                                <option value="${item.orgId}"
                                    <c:if test="${pd.publisherOrgId == item.orgId}">selected</c:if>>
                                        ${item.orgName}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <a class="btn btn-small btn-success" href="/orgnation/goAdd.do">添加保理公司</a>
                </td>
            </tr>
            <tr>
                <td style="width:100px;text-align: right;padding-top: 13px;">受托管理人:</td>
                <td>
                    <select name="delegatorOrgId" id="delegatorOrgId" title="请选择受托管理人">
                        <option value=""><span>请选择受托管理人</span></option>
                        <c:forEach items="${orgnationList}" var="item">
                            <c:if test="${item.orgType == 2}">
                                <option value="${item.orgId}"
                                        <c:if test="${pd.delegatorOrgId == item.orgId}">selected</c:if>>
                                            ${item.orgName}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <a class="btn btn-small btn-success" href="/orgnation/goAdd.do">添加受托管理人</a>
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










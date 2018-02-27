<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="/">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	
	//保存
	function save(){
			if($("#TEL").val()==""){
			$("#TEL").tips({
				side:3,
	            msg:'请输入手机号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TEL").focus();
			return false;
		}
		if($("#TEL_STATES").val()==""){
			$("#TEL_STATES").tips({
				side:3,
	            msg:'请输入状态码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TEL_STATES").focus();
			return false;
		}
		if($("#CONTENT").val()==""){
			$("#CONTENT").tips({
				side:3,
	            msg:'请输入发送内容',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CONTENT").focus();
			return false;
		}
		if($("#SEND_STATES").val()==""){
			$("#SEND_STATES").tips({
				side:3,
	            msg:'请输入发送状态码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SEND_STATES").focus();
			return false;
		}
		if($("#SEND_TIME").val()==""){
			$("#SEND_TIME").tips({
				side:3,
	            msg:'请输入发送时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SEND_TIME").focus();
			return false;
		}
		if($("#SEND_TYPES").val()==""){
			$("#SEND_TYPES").tips({
				side:3,
	            msg:'请输入发送类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SEND_TYPES").focus();
			return false;
		}
		if($("#SEND_TIMES").val()==""){
			$("#SEND_TIMES").tips({
				side:3,
	            msg:'请输入发送次数',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SEND_TIMES").focus();
			return false;
		}
		if($("#SEND_UPDATE_TIME").val()==""){
			$("#SEND_UPDATE_TIME").tips({
				side:3,
	            msg:'请输入更新时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SEND_UPDATE_TIME").focus();
			return false;
		}
		if($("#SEND_OPERATORS").val()==""){
			$("#SEND_OPERATORS").tips({
				side:3,
	            msg:'请输入运营商',
	            bg:'#AE81FF',
	            time:2
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
		<input type="hidden" name="SMSCONTENT_ID" id="SMSCONTENT_ID" value="${pd.SMSCONTENT_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">手机号:</td>
				<td><input type="text" name="TEL" id="TEL" value="${pd.TEL}" maxlength="32" placeholder="这里输入手机号" title="手机号"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">状态码:</td>
				<td><input type="text" name="TEL_STATES" id="TEL_STATES" value="${pd.TEL_STATES}" maxlength="32" placeholder="这里输入状态码" title="状态码"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">发送内容:</td>
				<td><input type="text" name="CONTENT" id="CONTENT" value="${pd.CONTENT}" maxlength="32" placeholder="这里输入发送内容" title="发送内容"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">发送状态码:</td>
				<td><input type="text" name="SEND_STATES" id="SEND_STATES" value="${pd.SEND_STATES}" maxlength="32" placeholder="这里输入发送状态码" title="发送状态码"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">发送时间:</td>
				<td><input class="span10 date-picker" name="SEND_TIME" id="SEND_TIME" value="${pd.SEND_TIME}" type="text" data-date-format="yyyy-mm-dd"  placeholder="发送时间" title="发送时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">发送类型:</td>
				<td><input type="text" name="SEND_TYPES" id="SEND_TYPES" value="${pd.SEND_TYPES}" maxlength="32" placeholder="这里输入发送类型" title="发送类型"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">发送次数:</td>
				<td><input type="number" name="SEND_TIMES" id="SEND_TIMES" value="${pd.SEND_TIMES}" maxlength="32" placeholder="这里输入发送次数" title="发送次数"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">更新时间:</td>
				<td><input class="span10 date-picker" name="SEND_UPDATE_TIME" id="SEND_UPDATE_TIME" value="${pd.SEND_UPDATE_TIME}" type="text" data-date-format="yyyy-mm-dd"  placeholder="更新时间" title="更新时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">运营商:</td>
				<td><input type="text" name="SEND_OPERATORS" id="SEND_OPERATORS" value="${pd.SEND_OPERATORS}" maxlength="32" placeholder="这里输入运营商" title="运营商"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>
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
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/assets/css/font-awesome.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
		
		<script type="text/javascript">
			
			//保存
			function save(){

				if($("#TYPE").val()=="" || $("#TYPE").val()=="请选择"){
					$("#TYPE").tips({
						side:3,
						msg:'请选择类型',
						bg:'#AE81FF',
						time:2
					});
					$("#TYPE").focus();
					return false;
				}

				if($("#excel").val()=="" || document.getElementById("excel").files[0] =='请选择xls格式的文件'){
					
					$("#excel").tips({
						side:3,
			            msg:'请选择文件',
			            bg:'#AE81FF',
			            time:3
			        });
					return false;
				}
				
				$("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
			
			function fileType(obj){
				var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
			    if(fileType != '.xls'){
			    	$("#excel").tips({
						side:3,
			            msg:'请上传xls格式的文件',
			            bg:'#AE81FF',
			            time:3
			        });
			    	$("#excel").val('');
			    	document.getElementById("excel").files[0] = '请选择xls格式的文件';
			    }
			}
		</script>
	</head>
<body>
	<form action="coupon/readExcel.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
		<table style="width:95%;" >
			<tr>
				<td style="width:70px;text-align: right;">类型:</td>
				<td><select name="TYPE" id="TYPE" data-placeholder="请选择类型" style="vertical-align:top;width: 120px;">
					<option value="请选择"></option>
					<c:forEach items="${codeList}" var="code">
						<option value="${code.CODE }" <c:if test="${pd.TYPE==code.CODE}">selected</c:if>>${code.NAME }</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<%--<tr>--%>
				<%--<td style="width:70px;text-align: right;padding-top: 13px;">过期日期:</td>--%>
				<%--<td><input class="span10 date-picker" name="VALIDTIME" id="VALIDTIME" value="${pd.VALIDTIME}" type="text" data-date-format="yyyy-mm-dd"  style="width:150px;" placeholder="过期日期"/></td>--%>
			<%--</tr>--%>
			<tr>
				<td style="padding-top: 20px;" colspan="2"><input type="file" id="excel" name="excel" style="width:50px;" onchange="fileType(this)" /></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">导入</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
					<a class="btn btn-mini btn-success" onclick="window.location.href='/coupon/downExcel.do'">下载模版</a>
				</td>
			</tr>
			<%--<tr>--%>
				<%--<td style="text-align: center;" colspan="2">--%>
					<%--<textarea id="failTel">${pd.failTel}</textarea>--%>
				<%--</td>--%>
			<%--</tr>--%>
		</table>
		</div>
			<c:choose>
				<c:when test="${pd.failTel !='' && !empty pd.failTel && msg=='success'}">
					<div  style="padding-left: 30px">
						失败的用户：<textarea id="failTel" style="width:250px;height: 150px">${pd.failTel}</textarea>
					</div>
				</c:when>
				<c:when test="${pd.failTel =='' && msg=='success'}">
					<div  style="padding-left: 30px">
						全部导入成功！请返回
					</div>
				</c:when>
				<c:when test="${msg=='fail'}">
					<div  style="padding-left: 30px">
						导入出错！请联系管理员
					</div>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
		<div id="zhongxin2" class="center" style="display:none"><br/><img src="static/images/jzx.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='static/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script type="text/javascript">
		 	window.jQuery || document.write("<script src='static/assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script src="static/js/bootstrap.min.js"></script>
		<!-- ace scripts -->
		<script src="static/assets/js/ace/elements.fileinput.js"></script>
		<script src="static/assets/js/ace/ace.js"></script>

		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			//上传
			$('#excel').ace_file_input({
				no_file:'请选择EXCEL ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'xls|xls',
				blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});
			
		});
		//日期框
		$('.date-picker').datepicker();
		</script>
	
</body>
</html>
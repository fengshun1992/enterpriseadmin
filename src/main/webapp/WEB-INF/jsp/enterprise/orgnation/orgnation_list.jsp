﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%>
	</head>
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
		<form action="orgnation/list.do" method="post" name="orgnationForm" id="orgnationForm">
			<!-- 检索  -->
			<table>
				<tr>
					<td>
					<span class="input-icon">
						<input autocomplete="off" id="nav-search-input" type="text" name="keyWords"
							   value="${pd.keyWords }" placeholder="这里输入关键词"/>
					</span>
					</td>
					<td style="vertical-align:top;">
						<select class="chzn-select" name="orgType" id="orgType"
								data-placeholder="请选择机构类型"
								style="vertical-align:top;width: 150px;">
							<option value=""></option>
							<option value="">全部</option>
							<option value="1" <c:if test="${pd.orgType == 1}">selected</c:if>>发行方</option>
							<option value="2" <c:if test="${pd.orgType == 2}">selected</c:if>>受托管理方</option>
							<option value="3" <c:if test="${pd.orgType == 3}">selected</c:if>>合同系统管理方</option>
						</select>
					</td>

					<c:if test="${QX.cha == 1 }">
						<td style="vertical-align:top;">
							<button class="btn btn-mini btn-light" onclick="search();" title="检索">
								<i id="nav-search-icon" class="icon-search"></i>
							</button>
						</td>
					</c:if>
				</tr>
			</table>
				<!-- 检索  -->
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">
							<label><input type="checkbox" id="zcheckbox"/><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>机构简称</th>
						<th>机构全称</th>
						<%--<th>状态</th>--%>
						<th>机构类型</th>
						<th>签名数据</th>
						<th>签名图片</th>
						<th>机构管理人员</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty orgnationList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${orgnationList}" var="item" varStatus="vs">
							<tr>
							<td class='center' style="width: 30px;">
								<label><input type='checkbox' name='ids' value="${item.orgId }"/><span class="lbl"></span></label>
							</td>
							<td class='center' style="width: 30px;">${vs.index+1}</td>
							<td>${item.orgName }</td>
							<td>${item.orgFullName }</td>
							<td>
								<c:if test="${item.orgType == 1 }">发行人</c:if>
								<c:if test="${item.orgType == 2 }">受托管理人</c:if>
								<c:if test="${item.orgType == 3 }">合同管理机构</c:if>
							</td>
							<td>${item.signData }</td>
							<td><a href="${item.signImageUrl }" target="_blank">
									<img src="${item.signImageUrl }" style="width: 300px;height: 100px"></a>
							</td>
							<td>
								<c:choose>
								<c:when test="${not empty item.orgUser}">
								<c:forEach items="${item.orgUser}" var="user">
									<c:forEach items="${allUserList}" var="allUser">
									   <c:if test="${user.userId == allUser.USER_ID}">${allUser.NAME}<br></c:if>
									</c:forEach>
								</c:forEach>
								</c:when>
								<c:otherwise>
								没有相关数据
								</c:otherwise>
								</c:choose>
							</td>
							<td style="width: 60px;">
								<div class='hidden-phone visible-desktop btn-group'>
									<c:if test="${QX.edit == 1 }">
										<a class='btn btn-mini btn-info' title="编辑"
										   onclick="edit('${item.orgId }');"><i class='icon-edit'></i></a>
									</c:if>

									<c:if test="${QX.del == 1 }">
										<a class='btn btn-mini btn-danger' title="删除"
										   onclick="del('${item.orgId }');">
											<i class='icon-trash'></i></a>
									</c:if>
								</div>
							</td>
							</tr>
						</c:forEach>
						</c:if>
						
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="10" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
			
			<div class="page-header position-relative">
				<table style="width:100%;">
					<tr>
						<td style="vertical-align:top;">
							<c:if test="${QX.add == 1 }">
								<a class="btn btn-small btn-success" onclick="add();">新增</a>
							</c:if>
						</td>
						<c:if test="${QX.cha == 1 }">
							<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
						</c:if>
					</tr>
				</table>
			</div>
		</form>
	</div>
  </div><!--/row-->

</div>
</div>
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->

		<script type="text/javascript">
			$(top.hangge());

            //检索
            function search() {
                top.jzts();
                $("#orgnationForm").submit();
            }

			//新增
			function add(){
				top.jzts();
				var diag = new top.Dialog();
				diag.Drag=true;
				diag.Title ="新增";
				diag.URL = '/orgnation/goAdd.do';
				diag.Width = 800;
				diag.Height = 500;
				diag.CancelEvent = function(){ //关闭事件
					if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						if('${page.currentPage}' == '0'){
							top.jzts();
							setTimeout("self.location=self.location",100);
						}else{
							nextPage(${page.currentPage});
						}
					}
					diag.close();
				};
				diag.show();
			}

			//修改
			function edit(ID){
				top.jzts();
				var diag = new top.Dialog();
				diag.Drag=true;
				diag.Title ="资料";
				diag.URL = '/orgnation/goEdit.do?ID='+ID;
				diag.Width = 800;
				diag.Height = 500;
				diag.CancelEvent = function(){ //关闭事件
					if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						nextPage(${page.currentPage});
					}
					diag.close();
				};
				diag.show();
			}

			//删除
			function del(ID){
				bootbox.confirm("确定要删除吗?", function(result) {
					if(result) {
						top.jzts();
						var url = "/orgnation/delete.do?ID="+ID;
						$.get(url,function(data){
							nextPage(${page.currentPage});
						});
					}
				});
			}
		</script>

		<script type="text/javascript">
			$(function() {
				//日期框
				$('.date-picker').datepicker();
		
				//下拉框
				$(".chzn-select").chosen();
				$(".chzn-select-deselect").chosen({allow_single_deselect:true});
		
				//复选框
				$('table th input:checkbox').on('click' , function(){
					var that = this;
					$(this).closest('table').find('tr > td:first-child input:checkbox')
							.each(function(){
								this.checked = that.checked;
								$(this).closest('tr').toggleClass('selected');
							});
		
				});
		
			});
		
			$(function(){
				document.onkeydown = function(e){
					var ev = document.all ? window.event : e;
					if(ev.keyCode==13) {
						search()
					}
				}
			});
		</script>
</body>
</html>
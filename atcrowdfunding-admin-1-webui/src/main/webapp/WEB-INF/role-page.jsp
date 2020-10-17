<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
	<%@ include file="/WEB-INF/include-head.jsp"%>
<!-- 导入Pagination导航条插件 -->
<link rel="stylesheet" href="css/pagination.css"/>
</head>
<body>
	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
								</div>
							</div>
							<button id="searchBtn" type="button" class="btn btn-warning" style="outline: none;"><i class="glyphicon glyphicon-search"></i> 查询</button>
						</form>
						<button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float: right; margin-left: 10px; outline: none;">
							<i class="glyphicon glyphicon-remove"></i> 批量删除
						</button>
						<button id="addBtn" type="button" class="btn btn-primary" style="float: right; outline: none;"">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input id="summaryCheckbox" type="checkbox" style="cursor: pointer;"></th>
										<th>名称</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody id="roleTableBody"></tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<div id="Pagination" class="pagination"></div>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="/WEB-INF/include-modal-role-confirm.jsp" %>
	<%@ include file="/WEB-INF/include-modal-role-add.jsp" %>
	<%@ include file="/WEB-INF/include-modal-role-edit.jsp" %>
	
	<script type="text/javascript" src="script/jquery.pagination.js"></script>
	<script type="text/javascript" src="script/my-role.js"></script>
	<script type="text/javascript">
		$(function() {
			// 调用分页参数初始化方法
			initGlobalVariable();
			// 基于初始化参数执行分页
			showPage();
			
			// 查询功能
			$("#searchBtn").click(function() {
				var keyword = $("#keywordInput").val();
				if (keyword == null || keyword == "") {
					layer.msg("请输入关键词查询！");
					return;
				}
				window.keyword = keyword;
				showPage();
			});
			
			// 全选/全不选功能
			$("#summaryCheckbox").click(function() {
				var checkboxStatus = this.checked;
				$(".itemCheckbox").prop("checked", checkboxStatus);
			});
			
			// 批量删除
			$("#batchRemoveBtn").click(function() {
				// 获取被选中的.itemCheckbox数组的长度
				var length = $(".itemCheckbox:checked").length;
				if (length == 0) {
					layer.msg("请至少选择一条数据！");
					return;
				}
				window.roleIdArray = new Array();
				// 遍历已经选中的id数据
				$(".itemCheckbox:checked").each(function() {
					var roleId = $(this).attr("roleid");
					window.roleIdArray.push(roleId);
				});
				// 调用打开模态框函数
				showRemoveConfirmModal();
			});
			
			// 给模态框中的OK按钮绑定单击函数
			$("#confirmModalBtn").click(function() {
				$.ajax({
					"url": "role/batch/remove.json",
					"type": "post",
					"data": JSON.stringify(window.roleIdArray),
					"dataType": "json",
					"contentType": "application/json;charset=UTF-8",
					"success": function(response) {
						var result = response.result;
						if (result == "SUCCESS") {
							layer.msg("删除成功！");
							showPage(); // 重新调用分页方法
						}
						if (result == "FAILED") {
							layer.msg(response.message);
						}
						$("#confirmModal").modal("hide"); // 关闭模态框
					},
					"error": function(response) {
						layer.msg(response.message);
					}
				});
			});
			
			// 此操作方法只能删除一次，不能在静态元素上直接绑定单击响应函数
			/* $(".removeBtn").click(function() {
				var roleId = $(this).attr("roleId");
				window.roleIdArray = new Array();
				window.roleIdArray.push(roleId);
				// 调用打开模态框函数
				showRemoveConfirmModal();
			}); */
			
			$("#roleTableBody").on("click", ".removeBtn", function() {
				var roleId = $(this).attr("roleId");
				window.roleIdArray = new Array();
				window.roleIdArray.push(roleId);
				// 调用打开模态框函数
				showRemoveConfirmModal();
			});
			
			$("#addBtn").click(function() {
				$("#addModal").modal("show");
			});
			
			$("#addModalBtn").click(function() {
				var roleName = $.trim($("#roleNameInput").val());
				if (roleName == null || roleName == "") {
					layer.msg("请输入有效角色名称！");
					return;
				}
				$.ajax({
					"url": "role/save/role.json",
					"type": "post",
					"data": {
						"roleName": roleName
					},
					"dataType": "json",
					"success": function(response) {
						var result = response.result;
						if (result == "SUCCESS") {
							layer.msg("保存成功！");
							window.pageNum = 999999;
							showPage();
						}
						if (result == "FAILED") {
							layer.msg(response.message);
						}
						$("#addModal").modal("hide");
					},
					"error": function(response) {
						layer.msg(response.message);
					}
				});
			});
			
			// 更新按钮绑定单击响应函数
			$("#roleTableBody").on("click", ".editBtn", function() {
				// 打开模态框、回显角色名称
				var roleName = $(this).parents("tr").children("td:eq(2)").text();
				$("#roleNameInputEdit").val(roleName);
				$("#editModal").modal("show");
				window.roleId = $(this).attr("roleId");
			});
			$("#editModalBtn").click(function() {
				var roleName = $.trim($("#roleNameInputEdit").val());
				if (roleName == null || roleName == "") {
					layer.msg("请输入有效的角色名称！");
					return;
				}
				$.ajax({
					"url": "role/update/role.json",
					"type": "post",
					"data": {
						"id": window.roleId,
						"name": roleName
					},
					"dataType": "json", // 服务器端返回的数据类型
					"success": function(response) {
						var result = response.result;
						if (result == "SUCCESS") {
							layer.msg("更新成功！");
							showPage();
						}
						if (result == "FAILED") {
							layer.msg(response.message);
						}
						$("#editModal").modal("hide");
					},
					"error": function(respsone) {
						layer.msg(response.message);
					}
				});
			});
		});
	</script>
</body>
</html>

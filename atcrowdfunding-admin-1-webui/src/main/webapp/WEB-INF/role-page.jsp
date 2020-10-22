<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
<%@ include file="/WEB-INF/include-head.jsp"%>
<!-- 导入Pagination导航条插件 -->
<link rel="stylesheet" href="css/pagination.css"/>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
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
						<button id="addBtn" type="button" class="btn btn-primary" style="float: right; outline: none;">
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
	<%@ include file="/WEB-INF/include-modal-assign-auth.jsp" %>
	
	<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
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
			
			$("#roleTableBody").on("click", ".checkBtn", function() {
				// 将角色ID存入到全局变量中
				window.roleId = $(this).attr("roleId");
				$("#roleAssignAuthModal").modal("show");
				// 1、创建setting对象
				var setting = {
						"data": {
							"simpleData": {
								"enable": true,
								"pIdKey": "categoryId"
							},
							"key": {
								"name": "title"
							}
						},
						"check": { // 显示多选框
							"enable": true
						}
				};
				// 2、获取权限数据
				var ajaxResult = $.ajax({
					"url": "assign/get/all/auth.json",
					"type": "post",
					"dataType": "json",
					"async": false
				});
				if (ajaxResult.responseJSON.result == "FAILED") {
					layer.msg(ajaxResult.responseJSON.message);
					return;
				}
				var zNodes = ajaxResult.responseJSON.data;
				// 3、初始化树形结构
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				// 4、将树形结构展开
				$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
				// 5、继续查询指定角色对应的权限数据ID
				var ajaxResult = $.ajax({
					"url": "assign/get/assigned/auth/id/list.json",
					"type": "post",
					"data": {
						"roleId": $(this).attr("roleId"),
						"random": Math.random
					},
					"dataType": "json",
					"async": false
				});
				if (ajaxResult.responseJSON.result == "FAILED") {
					layer.msg(ajaxResult.responseJSON.message);
					return;
				}
				var authIdList = ajaxResult.responseJSON.data;
				for (var x = 0; x < authIdList.length; x++) {
					var authId = authIdList[x];
					var key = "id"; // 节点的属性名
					var treeNode = $.fn.zTree.getZTreeObj("treeDemo").getNodeByParam(key, authId); // authId表示节点的属性值
					// treeNode表示要勾选的节点、true表示勾选状态、false表示不联动
					$.fn.zTree.getZTreeObj("treeDemo").checkNode(treeNode, true, false);
				}
			});
			
			// 分配权限
			$("#roleAssignAuthBtn").click(function() {
				var authIdArray = new Array();
				// 获取当前zTree中已经选中的节点
				var checkedNodes = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes();
				for (var x = 0; x < checkedNodes.length; x++) {
					var node = checkedNodes[x];
					var authId = node.id; // 获取当前节点的属性ID
					authIdArray.push(authId); // 将authId存入数组中
				}
				var requestBody = {
					"roleIdList": [window.roleId],
					"authIdList": authIdArray
				};
				var ajaxResult = $.ajax({
					"url": "assign/do/assign.json",
					"type": "post",
					"data": JSON.stringify(requestBody),
					"dataType": "json",
					"contentType": "application/json;charset=UTF-8",
					"async": false
				});
				if (ajaxResult.responseJSON.result == "SUCCESS") {
					layer.msg("分配权限成功！");
				}
				if (ajaxResult.responseJSON.result == "FAILED") {
					layer.msg(ajaxResult.responseJSON.message);
				}
				$("#roleAssignAuthModal").modal("hide"); // 关闭模态框
			});
		});
	</script>
</body>
</html>

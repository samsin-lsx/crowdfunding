<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
	<%@ include file="/WEB-INF/include-head.jsp"%>
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
						<i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
						<div style="float: right; cursor: pointer;" data-toggle="modal" data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include-modal-menu-add.jsp" %>
	<%@ include file="/WEB-INF/include-modal-menu-edit.jsp" %>
	<%@ include file="/WEB-INF/include-modal-menu-confirm.jsp" %>
	
	<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="script/my-menu.js"></script>
	<script type="text/javascript">
		$(function() {
			initWholeTree();
			
			$("#confirmBtn").click(function() {
				$.ajax({
					"url": "menu/remove.json",
					"type": "post",
					"dataType": "json",
					"data": {
						"id": window.menuId
					},
					"success": function(response) {
						var result = response.result;
						if (result == "SUCCESS") {
							layer.msg("删除菜单成功！");
							initWholeTree();
						}
						if (result == "FAILED") {
							layer.msg(response.message);
						}
					},
					"error": function(response) {
						layer.msg(response.message);
					}
				});
				$("#menuConfirmModal").modal("hide");
			});
			
			$("#menuEditBtn").click(function() {
				var name = $.trim($("#menuEditModal [name='name']").val());
				var url = $.trim($("#menuEditModal [name='url']").val());
				var icon = $("#menuEditModal [name='icon']:checked").val();
				if (name == null || name == "") {
					layer.msg("请输入菜单名称！");
					return;
				}
				if (url == null || url == "") {
					layer.msg("请输入菜单地址！");
					return;
				}
				$.ajax({
					"url": "menu/update.json",
					"type": "post",
					"dataType": "json",
					"data": {
						"id": window.menuId,
						"name": name,
						"url": url,
						"icon": icon
					},
					"success": function(response) {
						var result = response.result;
						if (result == "SUCCESS") {
							layer.msg("更新菜单成功！");
							initWholeTree();
						}
						if (result == "FAILED") {
							layer.msg(response.message);
						}
					},
					"error": function(response) {
						layer.msg(response.message);
					}
				});
				$("#menuEditModal").modal("hide");
			});
			
			$("#menuSaveBtn").click(function() {
				var name = $.trim($("#menuAddModal [name='name']").val());
				var url = $.trim($("#menuAddModal [name='url']").val());
				var icon = $("#menuAddModal [name='icon']:checked").val();
				if (name == null || name == "") {
					layer.msg("请输入菜单名称！");
					return;
				}
				if (url == null || url == "") {
					layer.msg("请输入菜单地址！");
					return;
				}
				$.ajax({
					"url": "menu/save.json",
					"type": "post",
					"dataType": "json",
					"data": {
						"name": name,
						"url": url,
						"pid": window.menuId, // 当前操作的节点是新节点的父节点
						"icon": icon
					},
					"success": function(response) {
						var result = response.result;
						if (result == "SUCCESS") {
							layer.msg("添加菜单成功！");
							initWholeTree();
						}
						if (result == "FAILED") {
							layer.msg(response.message);
						}
					},
					"error": function(response) {
						layer.msg(response.message);
					}
				});
				$("#menuAddModal").modal("hide");
			});
			
		});
	</script>
</body>
</html>

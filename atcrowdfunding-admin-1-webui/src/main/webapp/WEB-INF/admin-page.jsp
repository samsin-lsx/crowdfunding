<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
	<%@ include file="/WEB-INF/include-head.jsp"%>
</head>
<!-- 导入Pagination导航条插件 -->
<link rel="stylesheet" href="css/pagination.css"/>
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
						<form action="admin/query/for/search.html" method="post" class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input name="keyword" class="form-control has-success" type="text" placeholder="请输入帐号、名称、邮箱关键字查询" style="width: 300px;">
								</div>
							</div>
							<button type="submit" class="btn btn-warning" style="outline: none;">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" id="batchRemoveBtn" class="btn btn-danger" style="float: right; margin-left: 10px; outline: none;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<a href="admin/to/add/page.html" class="btn btn-primary" style="float: right;"><i class="glyphicon glyphicon-plus"></i> 新增</a>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input id="summaryBox" type="checkbox"></th>
										<th>登录账号</th>
										<th>用户名称</th>
										<th>邮箱地址</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${empty requestScope['PAGE-INFO'].list }">
										<tr>
											<td colspan="6" style="text-align: center;">抱歉，没有查询到数据！</td>
										</tr>
									</c:if>
									<c:if test="${not empty requestScope['PAGE-INFO'].list }">
										<c:forEach items="${requestScope['PAGE-INFO'].list }" var="admin" varStatus="myStatus">
											<tr>
												<td>${myStatus.count }</td>
												<td><input adminId="${admin.id }" type="checkbox" class="itemBox"></td>
												<td>${admin.loginAcct }</td>
												<td>${admin.userName }</td>
												<td>${admin.email }</td>
												<td>
													<a href="assign/to/assign/role/page.html?adminId=${admin.id }&pageNum=${requestScope['PAGE-INFO'].pageNum }" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
													<a href="admin/to/edit/page.html?adminId=${admin.id }&pageNum=${requestScope['PAGE-INFO'].pageNum }" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></a>
													<button type="button" class="btn btn-danger btn-xs uniqueRemoveBtn" adminId="${admin.id }" style="outline: none;">
														<i class=" glyphicon glyphicon-remove"></i>
													</button>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
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
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/my-admin.js"></script>
<script type="text/javascript">
$(function(){
	// 初始化全局变量
	window.totalRecord = ${requestScope['PAGE-INFO'].total};
	window.pageSize = ${requestScope['PAGE-INFO'].pageSize};
	window.pageNum = ${requestScope['PAGE-INFO'].pageNum};
	window.keyword = "${param.keyword}";
	
	initPagination(); // 对分页导航条进行显示初始化
	
	// 全选/全不选
	$("#summaryBox").click(function() {
		$(".itemBox").prop("checked", this.checked);
	});
	
	// 批量删除
	$("#batchRemoveBtn").click(function() {
		var adminIdArray = new Array(); // 用户ID
		var loginAcctArray = new Array(); // 用户帐号
		$(".itemBox:checked").each(function() {
			var adminId = $(this).attr("adminId");
			adminIdArray.push(adminId);
			loginAcctArray.push($(this).parent("td").next().text());
		});
		if (adminIdArray.length == 0) {
			alert("请您选中要删除的记录！");
			return;
		}
		if (!confirm("您确定要删除【" + loginAcctArray + "】信息吗？")) {
			return;
		}
		doBatchRemove(adminIdArray);
	});
	
	// 单个用户删除
	$(".uniqueRemoveBtn").click(function() {
		var adminId = $(this).attr("adminId"); // 获取当前的用户ID值
		var loginAcct = $(this).parents("tr").children("td:eq(2)").text();
		if (!confirm("您确定要删除【"+ loginAcct +"】数据吗？")) {
			return;
		}
		var adminIdArray = new Array();
		adminIdArray.push(adminId);
		doBatchRemove(adminIdArray);
	});
	
});
</script>
</body>
</html>

// 初始化全局变量
function initGlobalVariable() {
	window.pageSize = 10;
	window.pageNum = 1;
	window.keyword = "";
}

/** 给服务器发送请求获取分页数据（pageInfo），并在页面上显示分页效果（表格主体、页码导航条） */
function showPage() {
	var pageInfo = getPageInfo();
	if (pageInfo == null) { // 如果没有获取到pageInfo信息，则停止后续操作
		return;
	}
	generateTableBody(pageInfo);
	initPagination(pageInfo);
}

// 获取分页数据，返回PageInfo
function getPageInfo() {
	var ajaxResult = $.ajax({
		"url": "role/search/by/keyword.json",
		"type": "post",
		"data": {
			"pageNum": (window.pageNum == undefined) ? 1 : window.pageNum,
			"pageSize": (window.pageSize == undefined) ? 10 : window.pageSize,
			"keyword": (window.keyword == undefined) ? "" : window.keyword
		},
		"dataType": "json",
		"async": false // 为了保证getPageInfo()函数能够在Ajax请求拿到响应后获取PageInfo，需要设置为同步请求
	});
	var resultEntity = ajaxResult.responseJSON;
	var result = resultEntity.result;
	if (result == "SUCCESS") {
		return resultEntity.data;
	}
	if (result == "FAILED") {
		layer.msg(resultEntity.message);
	}
	return null;
}

// 使用PageInfo数据在tbody标签内显示分页数据
function generateTableBody(pageInfo) {
	$("#roleTableBody").empty(); // 渲染数据之前需要清空数据，否则会一直叠加
	var list = pageInfo.list; // 获取集合数据
	if (list == null || list.length == 0) {
		$("#roleTableBody").append("<tr><td colspan='4' style='text-align: center;'>没有查询到数据！</td></tr>");
		return;
	}
	for (var i = 0; i < list.length; i++) {
		var role = list[i];
		var checkBtn = "<button type='button' title='分配权限' class='btn btn-success btn-xs' style='outline: none;'><i class=' glyphicon glyphicon-check'></i></button>";
		var pencilBtn = "<button type='button' roleId='" + role.id + "' class='btn btn-primary btn-xs editBtn' style='outline: none;'><i class='glyphicon glyphicon-pencil'></i></button>";
		var removeBtn = "<button type='button' roleId='" + role.id + "' class='btn btn-danger btn-xs removeBtn' style='outline: none;'><i class='glyphicon glyphicon-remove'></i></button>";
		var numberTd = "<td>" + (i + 1) + "</td>";
		var checkboxTd = "<td><input roleid='" + role.id + "' class='itemCheckbox' type='checkbox' style='cursor: pointer;'></td>";
		var roleNameTd = "<td>" + role.name + "</td>";
		var btnTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
		var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + btnTd + "</td>";
		$("#roleTableBody").append(tr);
	}
}

// 导航条初始化操作
function initPagination(pageInfo) {
	// 显示分页导航条
	$("#Pagination").pagination(pageInfo.total, {
		num_edge_entries: 3, 			// 边缘页数
		num_display_entries: 5, 		// 主体页数
		callback: pageselectCallback,	// 回调行数
		items_per_page: window.pageSize, 	// 每页显示的数量
		current_page: (window.pageNum - 1), // 当前页
		prev_text: "上一页",
		next_text: "下一页"
	});
}

// 实现具体页数的跳转
function pageselectCallback(pageIndex, jq) {
	// pageIndex是从0开始，pageNum是从1开始
	window.pageNum = pageIndex + 1; // 将全局变量中的pageNum修改为最新值
	showPage(); // 调用分页函数重新执行分页
	return false;
}

// 根据roleIdArray查询数据
function getRoleListByRoleIdArray(roleIdArray) {
	var ajaxResult = $.ajax({
		"url": "role/get/list/by/id/list.json",
		"type": "post",
		"data": JSON.stringify(roleIdArray),
		"contentType": "application/json;charset=UTF-8",
		"dataType": "json",
		"async": false
	});
	var resultEntity = ajaxResult.responseJSON;
	var result = resultEntity.result;
	if (result == "SUCCESS") {
		return resultEntity.data;
	}
	if (result == "FAILED") {
		layer.msg(resultEntity.message);
		return null;
	}
	return null;
}

// 显示删除确认模态框
function showRemoveConfirmModal() {
	// 打开模态框
	$("#confirmModal").modal("show");
	// 查询数据
	var roleList = getRoleListByRoleIdArray(window.roleIdArray);
	// 清空模态框内容
	$("#confirmModalTableBody").empty();
	// 填充数据
	for (var x = 0; x < roleList.length; x++) {
		var role = roleList[x];
		var trHTML = "<tr><td>" + role.id + "</td><td>" + role.name + "</td></tr>";
		$("#confirmModalTableBody").append(trHTML);
	}
}


// 批量删除操作
function doBatchRemove(adminIdArray) {
	$.ajax({
		"url": "admin/batch/remove.json",
		"type": "post",
		"contentType": "application/json;charset=UTF-8", // 请求体内容类型，告诉服务器端发送的内容类型
		"data": JSON.stringify(adminIdArray),
		"dataType": "json", // 把服务器端返回的数据当作json格式解析
		"success": function(response) {
			if (response.result == "SUCCESS") {
				window.location.href = "admin/query/for/search.html?pageNum=" + window.pageNum + "&keyword=" + window.keyword;
			}
			if (response.result == "FAILED") {
				alert(response.message);
				return;
			}
		},
		"error": function(response) {
			alert(response.message);
			return;
		}
	});
}

// 导航条初始化操作
function initPagination() {
	// var totalRecord = ${requestScope['PAGE-INFO'].total}; // 获取总记录数
	// 显示分页导航条
	$("#Pagination").pagination(window.totalRecord, {
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
	window.location.href = "admin/query/for/search.html?pageNum=" + (pageIndex + 1) + "&keyword=" + window.keyword;
	return false;
}
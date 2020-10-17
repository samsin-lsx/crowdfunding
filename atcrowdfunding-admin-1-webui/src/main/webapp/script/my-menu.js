// 由setting.view.addDiyDom属性引用，负责显示自定义图标
// treeId对应树形id属性，treeNode对应每一个树形节点
function showMyIcon(treeId, treeNode) {
	var currentNodeId = treeNode.tId; // 获取当前节点的id
	var newClass = treeNode.icon; 	 // 获取新的class值，用于替换旧的class值
	var targetSpanId = currentNodeId + "_ico"; // 在当前节点的id值之后加上“_ico”得到目标span的id值
	$("#" + targetSpanId).removeClass().addClass(newClass).css("background", "");
}

function initWholeTree() {
	// 包含zTree中的设置属性：更改图标
	var setting = {
		"view": {
			"addDiyDom": showMyIcon, // 自定义图标函数
			"addHoverDom": addHoverDom,
			"removeHoverDom": removeHoverDom
		},
		"data": {
			"key": {
				"url": "notExistsProperty" // 控制点击菜单名称不进行跳转
			}
		}
	};
	// 发送ajax请求获取zNodes数据
	$.ajax({
		"url": "menu/get/whole/tree.json",
		"type": "post",
		"dataType": "json",
		"success": function(response) {
			var result = response.result;
			if (result == "SUCCESS") {
				// 用于显示树形结构的节点数据
				var zNodes = response.data;
				// 执行树形结构的初始操作
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			}
			if (result == "FAILED") {
				layer.msg("加载菜单数据失败！" + response.message);
			}
		},
		"error": function(response) {
			layer.msg("加载菜单数据失败！" + response.message);
		}
	});
}

function addHoverDom(treeId, treeNode) {
	// 在执行添加前，先判断是否已经添加过，如果是则停止执行
	var btnGrpSpanId = treeNode.tId + "_btnGrp";
	var btnGrpSpanLength = $("#" + btnGrpSpanId).length;
	if (btnGrpSpanLength > 0) {
		return;
	}
	// 由于按钮组是放在当前节点中的超链接（a标签）后面，需要先定位a标签
	var anchorId = treeNode.tId + "_a";
	// 调用生成按钮组的函数
	var $btnGrpSpan = generateBtnGrp(treeNode);
	// 在a标签后面追加按钮组
	$("#" + anchorId).after($btnGrpSpan);
}

function removeHoverDom(treeId, treeNode) {
	var btnGrpSpanId = treeNode.tId + "_btnGrp";
	$("#" + btnGrpSpanId).remove();
}

/** 生成按钮组 */
function generateBtnGrp(treeNode) {
	var treeNodeId = treeNode.tId; // 获取当前节点的id值
	var btnGrpSpanId = treeNodeId + "_btnGrp"; // 组装按钮组所在的span的值
	var level = treeNode.level; // 获取当前节点的level值
	var $span = $("<span id='" + btnGrpSpanId + "'></span>"); // 生成span标签对应的jQuery对象
	var menuId = treeNode.id; // 获取当前节点在数据库中的id值
	var addBtn = "<a onclick='showConfirmAddModal(this)' id='" + menuId + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left: 10px; padding-top: 0px;' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg'></i></a>";
	var editBtn = "<a onclick='showConfirmEditModal(this)' id='" + menuId + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left: 10px; padding-top: 0px;' title='编辑节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg'></i></a>";
	var removeBtn = "<a onclick='showConfirmRemoveModal(this)' id='" + menuId + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left: 10px; padding-top: 0px;' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg'></i></a>";
	if (level == 0) { // 根节点，只可以添加子节点
		$span.append(addBtn);
	}
	if (level == 1) { // 二级节点
		if (treeNode.children.length > 0) { // 二级节点有子节点时，只可以增加、修改
			$span.append(addBtn + " " + editBtn);
		} else { // 二级节点无子节点时，只可以增加、修改、删除
			$span.append(addBtn + " " + editBtn + " " + removeBtn);
		}
	}
	if (level == 2) { // 三级节点，只可以修改、删除
		$span.append(editBtn + " " + removeBtn)
	}
	return $span;
}

package com.atguigu.crowd.funding.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.crowd.funding.entity.Menu;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.service.api.MenuService;

@RestController
public class MenuHandler {
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/menu/get/whole/tree")
	public ResultEntity<Menu> getWholeTree() {
		List<Menu> menuList = this.menuService.getAll(); // 查询所有的树形节点数据
		// 将menuList转换为Map，其中key = menu的id值，value = menu对象
		Map<Integer, Menu> menuMap = new HashMap<>();
		for (Menu menu : menuList) {
			menuMap.put(menu.getId(), menu);
		}
		Menu rootNode = null; // 设置根节点
		for (Menu menu : menuList) {
			Integer pid = menu.getPid(); // 获取当前menu对象的pid属性
			if (pid == null) {
				rootNode = menu; // 当前节点为根节点，并赋值给根节点
				continue; // 已经找到根节点，无需再往下执行，直接进行下一次循环
			}
			// 如果pid不为null，根据pid查找当前节点的父节点
			Menu father = menuMap.get(pid); // 是父节点
			father.getChildren().add(menu);
		}
		return ResultEntity.successWithData(rootNode);
	}
}

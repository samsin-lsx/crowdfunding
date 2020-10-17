package com.atguigu.crowd.funding.service.api;

import java.util.List;

import com.atguigu.crowd.funding.entity.Menu;

public interface MenuService {
	/**
	 * 获取所有菜单数据
	 * @return 所有菜单数据
	 */
	public List<Menu> getAll();
}

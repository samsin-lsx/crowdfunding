package com.atguigu.crowd.funding.service.api;

import java.util.List;

import com.atguigu.crowd.funding.entity.Menu;

public interface MenuService {
	/**
	 * 获取所有菜单数据
	 * @return 所有菜单数据
	 */
	public List<Menu> getAll();

	/**
	 * 添加菜单功能
	 * @param menu 包含菜单属性
	 */
	public void saveMenu(Menu menu);

	/**
	 * 根据menuId查询对应的菜单信息
	 * @param menuId 菜单id
	 * @return 菜单信息对象
	 */
	public Menu getMenuById(Integer menuId);

	/**
	 * 更新菜单信息
	 * @param menu 菜单对象
	 */
	public void updateMenu(Menu menu);

	/**
	 * 删除菜单信息
	 * @param id 菜单ID
	 */
	public void updateRemove(Integer id);
}

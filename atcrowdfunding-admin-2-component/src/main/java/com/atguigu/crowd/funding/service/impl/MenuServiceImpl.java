package com.atguigu.crowd.funding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.funding.entity.Menu;
import com.atguigu.crowd.funding.entity.MenuExample;
import com.atguigu.crowd.funding.mapper.MenuMapper;
import com.atguigu.crowd.funding.service.api.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> getAll() {
		return this.menuMapper.selectByExample(new MenuExample());
	}

	@Override
	public void saveMenu(Menu menu) {
		this.menuMapper.insert(menu);
	}

	@Override
	public Menu getMenuById(Integer menuId) {
		return this.menuMapper.selectByPrimaryKey(menuId);
	}

	@Override
	public void updateMenu(Menu menu) {
		this.menuMapper.updateByPrimaryKey(menu);
	}

	@Override
	public void updateRemove(Integer id) {
		this.menuMapper.deleteByPrimaryKey(id);
	}

}

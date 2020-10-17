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

}

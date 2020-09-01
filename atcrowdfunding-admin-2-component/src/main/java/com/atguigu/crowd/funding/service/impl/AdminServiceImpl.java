package com.atguigu.crowd.funding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.AdminExample;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.service.api.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public List<Admin> getAll() {
		return this.adminMapper.selectByExample(new AdminExample());
	}

	@Override
	public void updateAdmin() {
		this.adminMapper.updateByPrimaryKey(new Admin(1, "laoli333", "123456", "老李同学", "laoli@qq.com", "2020-08-30"));
		this.adminMapper.updateByPrimaryKey(new Admin(2, "laoxiao333", "123456", "老肖同学", "laoxiao@qq.com", "2020-08-30"));
	}

}

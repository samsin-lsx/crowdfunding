package com.atguigu.crowd.funding.service.api;

import java.util.List;

import com.atguigu.crowd.funding.entity.Admin;

public interface AdminService {
	public List<Admin> getAll();
	public void updateAdmin();
}

package com.atguigu.crowd.funding.service.api;

import java.util.List;

import com.atguigu.crowd.funding.entity.Admin;
import com.github.pagehelper.PageInfo;

public interface AdminService {
	public List<Admin> getAll();
	
	/**
	 * 修改帐号
	 * @param admin 用户基本信息
	 */
	public void updateAdmin(Admin admin);
	
	/**
	 * 根据用户ID查询用户信息
	 * @param adminId 帐号
	 * @return 用户基本信息
	 */
	public Admin getAdminById(Integer adminId);
	
	/**
	 * 新增帐号
	 * @param admin 用户基本信息
	 */
	public void saveAdmin(Admin admin);
	
	/**
	 * 批量删除用户数据
	 * @param adminIdList 用户ID数据
	 */
	public void batchRemove(List<Integer> adminIdList);
	
	/**
	 * 实现管理员登录功能
	 * @param loginAcct 登录帐号
	 * @param userPswd 登录密码
	 * @return 登录的管理员信息
	 */
	public Admin login(String loginAcct, String userPswd);
	
	/**
	 * 分页查询用户数据
	 * @param pageNum 页码
	 * @param pageSize 每页显示的行数
	 * @param keyword 传入的关键字
	 * @return 分页后的数据
	 */
	public PageInfo<Admin> queryForKeywordSearch(Integer pageNum, Integer pageSize, String keyword);
}

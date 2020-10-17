package com.atguigu.crowd.funding.service.api;

import java.util.List;

import com.atguigu.crowd.funding.entity.Role;
import com.github.pagehelper.PageInfo;

public interface RoleService {
	/**
	 * 查询带分页的角色数据
	 * @param pageNum 页码
	 * @param pageSize 每页显示的数据行
	 * @param keyword 查询关键字
	 * @return 封装好的PageInfo对象
	 */
	public PageInfo<Role> queryForKeywordWithPage(Integer pageNum, Integer pageSize, String keyword);

	/**
	 * 根据角色id查询角色名称
	 * @param roleIdList 封装的角色id
	 * @return 如果查询到数据以List集合形式返回，否则集合长度为0
	 */
	public List<Role> getRoleListByIdList(List<Integer> roleIdList);

	/**
	 * 批量删除角色数据
	 * @param roleIdList 角色编号
	 */
	public void batchRemove(List<Integer> roleIdList);

	/**
	 * 添加角色
	 * @param roleName 角色名称
	 */
	public void saveRole(String roleName);

	/**
	 * 修改角色
	 * @param role 包含id、name的角色对象
	 */
	public void editRole(Role role);
}

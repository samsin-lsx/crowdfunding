package com.atguigu.crowd.funding.service.api;

import java.util.List;
import java.util.Map;

import com.atguigu.crowd.funding.entity.Auth;

public interface AuthService {

	/**
	 * 查询所有的权限数据
	 * @return 如果查询到数据则List集合形式返回权限数据
	 */
	List<Auth> getAllAuth();

	/**
	 * 根据角色ID查询对应的权限数据ID
	 * @param roleId 角色ID
	 * @return 返回权限数据ID
	 */
	public List<Integer> selectAssignedAuthIdList(Integer roleId);

	/**
	 * 分配角色权限数据
	 * <li>需要将之前已经分配的权限关系表数据删除</li>
	 * <li>保存新勾选的角色权限表数据</li>
	 * @param assignDataMap 包含角色ID、权限数据集合
	 */
	public void updateRelationShipBetweenRoleAndAuth(Map<String, List<Integer>> assignDataMap);
}

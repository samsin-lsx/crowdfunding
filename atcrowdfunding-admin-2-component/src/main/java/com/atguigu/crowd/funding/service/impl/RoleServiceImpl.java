package com.atguigu.crowd.funding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.entity.RoleExample;
import com.atguigu.crowd.funding.mapper.RoleMapper;
import com.atguigu.crowd.funding.service.api.RoleService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public PageInfo<Role> queryForKeywordWithPage(Integer pageNum, Integer pageSize, String keyword) {
		// 调用PageHelper插件中的开启分页方法
		PageHelper.startPage(pageNum, pageSize);
		// 执行分页查询
		List<Role> list = this.roleMapper.selectRoleForKeyword(keyword);
		// 将List集合封装成PageInfo对象
		return new PageInfo<Role>(list);
	}

	@Override
	public List<Role> getRoleListByIdList(List<Integer> roleIdList) {
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andIdIn(roleIdList);
		return this.roleMapper.selectByExample(roleExample);
	}

	@Override
	public void batchRemove(List<Integer> roleIdList) {
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andIdIn(roleIdList);
		this.roleMapper.deleteByExample(roleExample);
	}

	@Override
	public void saveRole(String roleName) {
		this.roleMapper.insert(new Role(null, roleName));
	}

	@Override
	public void editRole(Role role) {
		this.roleMapper.updateByPrimaryKey(role);
	}

	@Override
	public List<Role> getAssignedRoleList(Integer adminId) {
		return this.roleMapper.selectAssignedRoleList(adminId);
	}

	@Override
	public List<Role> getUnAssignedRoleList(Integer adminId) {
		return this.roleMapper.selectUnAssignRoleList(adminId);
	}

	@Override
	public void updateRelationship(Integer adminId, List<Integer> roleIdList) {
		// 1、删除之前关联的角色数据
		this.roleMapper.deleteOldAdminRoleRelationship(adminId);
		// 2、保存现在选择关联的角色数据
		if (CrowdFundingUtils.collectionEffective(roleIdList)) {
			this.roleMapper.insertNewAdminRoleRelationship(adminId, roleIdList);
		}
	}

}

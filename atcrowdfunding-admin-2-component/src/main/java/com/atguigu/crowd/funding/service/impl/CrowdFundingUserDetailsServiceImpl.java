package com.atguigu.crowd.funding.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.funding.config.SecurityAdmin;
import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.AdminExample;
import com.atguigu.crowd.funding.entity.Auth;
import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.mapper.AuthMapper;
import com.atguigu.crowd.funding.mapper.RoleMapper;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;

@Service
public class CrowdFundingUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private AuthMapper authMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AdminExample adminExample = new AdminExample(); // 1、创建AdminExample对象
		adminExample.createCriteria().andLoginAcctEqualTo(username); // 2、封装查询条件
		List<Admin> list = this.adminMapper.selectByExample(adminExample); // 3、执行查询
		if (!CrowdFundingUtils.collectionEffective(list)) { // 4、判断List是否有效可用
			return null;
		}
		Admin admin = list.get(0); // 5、取出Admin对象
		// String userPswd = admin.getUserPswd(); // 6、取出密码
		// 7、封装角色、权限信息
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<Role> roleList = this.roleMapper.selectAssignedRoleList(admin.getId()); // 根据用户ID查询角色
		for (Role role : roleList) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		}
		List<Auth> authList = this.authMapper.selectAuthListByAdminId(admin.getId());
		for (Auth auth : authList) {
			String authName = auth.getName();
			if (!CrowdFundingUtils.stringEffective(authName)) { // 查询出来的权限数据NAME列不能为空，因为表设计会存在权限分类，此分类名称为NULL
				continue;
			}
			authorities.add(new SimpleGrantedAuthority(authName));
		}
		return new SecurityAdmin(admin, authorities); // 8、返回封装好的User对象
	}

}

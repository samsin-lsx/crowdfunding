package com.atguigu.crowd.funding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.AdminExample;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;

@Service
public class CrowdFundingUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AdminExample adminExample = new AdminExample(); // 1、创建AdminExample对象
		adminExample.createCriteria().andLoginAcctEqualTo(username); // 2、封装查询条件
		List<Admin> list = this.adminMapper.selectByExample(adminExample); // 3、执行查询
		if (!CrowdFundingUtils.collectionEffective(list)) { // 4、判断List是否有效可用
			return null;
		}
		Admin admin = list.get(0); // 5、取出Admin对象
		String userPswd = admin.getUserPswd(); // 6、取出密码
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_老板", "ROLE_总裁", "发工资"); // 7、封装角色、权限信息
		return new User(username, userPswd, authorities); // 8、返回封装好的User对象
	}

}

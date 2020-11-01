package com.atguigu.crowd.funding.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.atguigu.crowd.funding.entity.Admin;

/**
 * 扩展User类
 * 通过创建SecurityAdmin对象时调用构造方法，传入originalAdmin和authorities可以通过getOriginalAdmin()方法获取原始Admin类
 * @author lishangxing
 */
public class SecurityAdmin extends User {

	private static final long serialVersionUID = 1L;
	private Admin originalAdmin;

	public SecurityAdmin(Admin originalAdmin, Collection<GrantedAuthority> authorities) {
		super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);
		this.originalAdmin = originalAdmin;
	}

	public Admin getOriginalAdmin() {
		return originalAdmin;
	}

}

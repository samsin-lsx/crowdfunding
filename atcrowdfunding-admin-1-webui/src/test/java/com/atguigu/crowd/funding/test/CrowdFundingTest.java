package com.atguigu.crowd.funding.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.mapper.RoleMapper;
import com.atguigu.crowd.funding.service.api.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdFundingTest {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private RoleMapper roleMapper;
	
	@Test
	public void testSaveRole() {
		for (int x = 0; x < 100; x++) {
			this.roleMapper.insert(new Role(null, "role - " + x));
		}
	}
	
	@Test
	public void testInsertAdmin() {
		for (int x = 0; x < 500; x++) {
			this.adminMapper.insert(new Admin(null, "loginAcct" + x, "111111", "samsin-lsx-" + x, "samsin-" + x + "@qq.com", "2020-09-04"));
		}
	}
	
	@Test
	public void testAdminMapperSearch() {
		List<Admin> list = this.adminMapper.selectAdminListByKeyword("li");
		for (Admin admin : list) {
			System.out.println(admin);
		}
	}
	
	@Test
	public void testMyBatisGetAll() {
		List<Admin> adminList = this.adminService.getAll();
		for (Admin admin : adminList) {
			System.out.println(admin);
		}
	}
	
	@Test
	public void testConnection() throws SQLException {
		Connection connection = this.dataSource.getConnection();
		System.out.println(connection);
	}
}

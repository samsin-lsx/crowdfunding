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
import com.atguigu.crowd.funding.service.api.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdFundingTest {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private AdminService adminService;
	
	@Test
	public void testTranscation() {
		this.adminService.updateAdmin();
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

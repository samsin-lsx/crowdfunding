package com.atguigu.crowd.funding.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.service.api.AdminService;

@Controller
public class AdminHandler {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/admin/get/all")
	public String getAll(Model model) {
		List<Admin> list = this.adminService.getAll();
		model.addAttribute("list", list);
		return "admin-target";
	}
	
}
